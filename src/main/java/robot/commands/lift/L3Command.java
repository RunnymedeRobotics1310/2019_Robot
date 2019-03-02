package robot.commands.lift;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.commands.cargo.CargoArmLevelCommand;
import robot.commands.hatch.HatchCentreCommand;

/**
 *
 */
public class L3Command extends TSafeCommand {

	private static final String COMMAND_NAME = 
			L3Command.class.getSimpleName();

	enum State { LIFT, DRIVE_TO_PLATFORM, NUDGE_FORWARD, RAISE_REAR, DRIVE_ON, RAISE_FRONT, FINISH_FORWARD, FINISH };

	private State state = State.LIFT;

	double currentTime = 0;

	public L3Command() {

		super(TConst.NO_COMMAND_TIMEOUT, Robot.oi);

		// Use requires() here to declare subsystem dependencies
		requires(Robot.liftSubsystem);
		requires(Robot.driveSubsystem);
	}

	@Override
	protected String getCommandName() { return COMMAND_NAME; }

	@Override
	protected String getParmDesc() { 
		return super.getParmDesc(); 
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Print the command parameters if this is the current
		// called command (it was not sub-classed)
		if (getCommandName().equals(COMMAND_NAME)) {
			logMessage(getParmDesc() + " starting");
		}

		// Move the arm to level 2
		Robot.oi.setArmLevel(2);
		Scheduler.getInstance().add(new CargoArmLevelCommand());

		Scheduler.getInstance().add(new HatchCentreCommand());
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		switch (state) {
		case LIFT:
			double encoderMismatch = Robot.liftSubsystem.getFrontLiftEncoder().get()
			- Robot.liftSubsystem.getRearLiftEncoder().get();
			Robot.liftSubsystem.setFrontMotorSpeed(-0.3 - encoderMismatch * .001);
			Robot.liftSubsystem.setRearMotorSpeed(-0.3);
			if (Robot.liftSubsystem.getFrontLiftUpperLimit()) {
				state = State.DRIVE_TO_PLATFORM;
			}
			break;
		case DRIVE_TO_PLATFORM:
			double encoderMismatch2 = Robot.liftSubsystem.getFrontLiftEncoder().get()
			- Robot.liftSubsystem.getRearLiftEncoder().get();
			Robot.liftSubsystem.setFrontMotorSpeed(-0.3 - encoderMismatch2 * .001);
			Robot.liftSubsystem.setRearMotorSpeed(-0.3);
			Robot.liftSubsystem.setDriveMotorSpeed(0.5);
			Robot.driveSubsystem.setSpeed(-0.05,-0.05);
			if (Robot.liftSubsystem.getPlatformDetect()) {
				currentTime = timeSinceInitialized();
				state = State.NUDGE_FORWARD;
			}
			break;
		case NUDGE_FORWARD:
			double encoderMismatch3 = Robot.liftSubsystem.getFrontLiftEncoder().get()
			- Robot.liftSubsystem.getRearLiftEncoder().get();
			Robot.liftSubsystem.setFrontMotorSpeed(-0.3 - encoderMismatch3 * .001);
			Robot.liftSubsystem.setRearMotorSpeed(-0.3);
			Robot.liftSubsystem.setDriveMotorSpeed(0.5);
			Robot.driveSubsystem.setSpeed(-0.05,-0.05);
			if (timeSinceInitialized() - currentTime > 0.5) {
				state = State.RAISE_FRONT;
				Robot.liftSubsystem.setDriveMotorSpeed(0);
				Robot.driveSubsystem.setSpeed(0,0);
			}
			break;
		case RAISE_REAR:
			Robot.liftSubsystem.setRearMotorSpeed(0.8);
			if (Robot.liftSubsystem.getRearLiftUpperLimit()) {
				state = State.DRIVE_ON;
			}
			break;
		case DRIVE_ON:
			Robot.liftSubsystem.setDriveMotorSpeed(0.5);
			Robot.driveSubsystem.setSpeed(-0.05,-0.05);
			break;
		case RAISE_FRONT:
			Robot.liftSubsystem.setFrontMotorSpeed(0.8);
			if (Robot.liftSubsystem.getFrontLiftUpperLimit()) {
				state = State.FINISH_FORWARD;
			}
			break;
		case FINISH_FORWARD:
			
			break;
		case FINISH:
		default:
			break;
		}


	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {

		if (super.isFinished()) {
			return true;
		}

		if (state == State.FINISH) {
			return true;
		}
		return false;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.setSpeed(0, 0);
	}

}