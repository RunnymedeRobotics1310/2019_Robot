package robot.commands.lift;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;

/**
 *
 */
public class DefaultLiftCommand extends TSafeCommand {

	private static final String COMMAND_NAME = 
			DefaultLiftCommand.class.getSimpleName();

	public DefaultLiftCommand() {

		super(TConst.NO_COMMAND_TIMEOUT, Robot.oi);

		// Use requires() here to declare subsystem dependencies
		requires(Robot.liftSubsystem);
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
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Robot.oi.startLevel3()) {
			Scheduler.getInstance().add(new L3Command());
		}
		
		if (Robot.oi.startLevel2()) {
			Scheduler.getInstance().add(new L2HopUp());
		}
		
		if (Robot.oi.syncedExtendLift()) {
			double encoderMismatch = Robot.liftSubsystem.getFrontLiftEncoder().get()
					- Robot.liftSubsystem.getRearLiftEncoder().get();
			Robot.liftSubsystem.setFrontMotorSpeed(-1.0 - encoderMismatch * .001);
			Robot.liftSubsystem.setRearMotorSpeed(-1.0);
		}
		else if (Robot.oi.syncedRetractLift()){
			double encoderMismatch = Robot.liftSubsystem.getFrontLiftEncoder().get()
					- Robot.liftSubsystem.getRearLiftEncoder().get();
			Robot.liftSubsystem.setFrontMotorSpeed(0.5 - encoderMismatch * .001);
			Robot.liftSubsystem.setRearMotorSpeed(0.5);
		}
		else {
			if (Robot.oi.getRetractFrontLift()) {
				Robot.liftSubsystem.setFrontMotorSpeed(0.8);
			}
			else if (Robot.oi.getExtendFrontLift() > 0) {
				Robot.liftSubsystem.setFrontMotorSpeed(-Robot.oi.getExtendFrontLift());
			}
			else {
				Robot.liftSubsystem.setFrontMotorSpeed(0);
			}
			if (Robot.oi.getRetractRearLift()) {
				Robot.liftSubsystem.setRearMotorSpeed(0.8);
			}
			else if (Robot.oi.getExtendRearLift() > 0) {
				Robot.liftSubsystem.setRearMotorSpeed(-Robot.oi.getExtendRearLift());
			}
			else {
				Robot.liftSubsystem.setRearMotorSpeed(0);
			}
		}
		if (Robot.oi.getLiftDriveForward()) {
			Robot.liftSubsystem.setDriveMotorSpeed(0.5);
		}
		else {
			Robot.liftSubsystem.setDriveMotorSpeed(0);
		}
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

}
