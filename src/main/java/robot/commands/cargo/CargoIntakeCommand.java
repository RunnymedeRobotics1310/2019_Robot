package robot.commands.cargo;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import robot.Robot;

/**
 *
 */
public class CargoIntakeCommand extends TSafeCommand {

	private static final String COMMAND_NAME = 
			CargoIntakeCommand.class.getSimpleName();
	
	private enum Step { INTAKE, CARGO_DETECTED, FINISHED };
	
	private Step curStep = Step.INTAKE;
	private double stepStartTime = 0;

	public CargoIntakeCommand() {
		super(TConst.NO_COMMAND_TIMEOUT, Robot.oi);
		// Use requires() here to declare subsystem dependencies
		requires(Robot.cargoSubsystem);
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
		
		// Start the intake wheels
		Robot.cargoSubsystem.startIntake();
		curStep = Step.INTAKE;
		
		// Start the driver rumble
		Robot.oi.startDriverRumble();
		Robot.oi.startOperatorRumble();
		
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		switch (curStep) {
		
		case INTAKE:
			// Run the intake until a ball is detected
			if (Robot.cargoSubsystem.isCargoDetected()) {
				stepStartTime = timeSinceInitialized();
				curStep = Step.CARGO_DETECTED;
			}
			break;
		
		case CARGO_DETECTED:
			// Wait .25 seconds to make sure the ball is totally in the intake
			if (timeSinceInitialized() > stepStartTime + .25) {
				Robot.cargoSubsystem.setArmSpeed(0.4);
				if(timeSinceInitialized() - stepStartTime> .5) {
					Robot.cargoSubsystem.setArmSpeed(0);
				}
				curStep = Step.FINISHED;
			}
			break;
		
		case FINISHED:
		default:
			break;
		}
		
	
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		
		// Stop the intake if the cancel is pressed
		if (super.isFinished()) {
			return true;
		}
		
		// Stop if the arm is not at level 0
		if (Robot.cargoSubsystem.getCurrentLevel() > 0) {
			return true;
		}
		
		// Stop if a ball is detected
		if (curStep == Step.FINISHED) {
			return true;
		}
		
		return false;
	}
	
	@Override
	protected void end() {

		// Stop the intake motors
		Robot.cargoSubsystem.stopIntake();
		
		// End the driver rumble
		Robot.oi.endDriverRumble();
		Robot.oi.endOperatorRumble();
		
		Robot.cargoSubsystem.setArmSpeed(0);
	}

}
