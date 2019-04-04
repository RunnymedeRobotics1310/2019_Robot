package robot.commands.hatch;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import robot.Robot;
import robot.RobotMap;

public class HatchEjectRocketCommand extends TSafeCommand{

	private static final String COMMAND_NAME = 
			DefaultHatchCommand.class.getSimpleName();

	public HatchEjectRocketCommand() {

		super(TConst.NO_COMMAND_TIMEOUT, Robot.oi);
		// Use requires() here to declare subsystem dependencies
		requires(Robot.hatchSubsystem);
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
		Robot.hatchSubsystem.extendHatchMech();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (timeSinceInitialized()>0.7) {
			Robot.hatchSubsystem.extendPunchMech();
		}
		if (timeSinceInitialized()>0.9) {
			Robot.hatchSubsystem.retractHatchMech();
		}
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (timeSinceInitialized()>1.6) {
			return true;
		}
		return false;
	}
	
	@Override
	protected void end() {
		Robot.hatchSubsystem.retractPunchMech();
	}

}