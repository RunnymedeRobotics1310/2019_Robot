package robot.commands.hatch;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import robot.Robot;
import robot.RobotMap;

public class HatchEjectCommand extends TSafeCommand{

	private static final String COMMAND_NAME = 
			DefaultHatchCommand.class.getSimpleName();
	private static int counter;

	public HatchEjectCommand() {

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
		Robot.hatchSubsystem.ejectHatch();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		counter++;
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (counter>=20) {
			return true;
		}
		return false;
	}
	
	@Override
	protected void end() {
		Robot.hatchSubsystem.retractPunchMech();
	}

}