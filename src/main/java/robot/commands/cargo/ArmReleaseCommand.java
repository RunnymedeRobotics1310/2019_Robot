package robot.commands.cargo;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import robot.Robot;

/**
 *
 */
public class ArmReleaseCommand extends TSafeCommand {

	private static final String COMMAND_NAME = 
			ArmReleaseCommand.class.getSimpleName();

	public ArmReleaseCommand() {

		super(2.0, Robot.oi);

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
		
		Robot.cargoSubsystem.setArmSpeed(0.15);
		
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

	
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		
		if (super.isFinished()) {
			return true;
		}
		
		return false;
	}
	
	@Override
	protected void end() {
		Robot.cargoSubsystem.setArmSpeed(0);
	}

}
