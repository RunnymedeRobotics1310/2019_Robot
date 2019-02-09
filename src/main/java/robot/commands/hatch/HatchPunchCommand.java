package robot.commands.hatch;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import robot.Robot;
import robot.RobotMap;

public class HatchPunchCommand extends TSafeCommand{

	private static final String COMMAND_NAME = 
			DefaultHatchCommand.class.getSimpleName();

	public HatchPunchCommand() {

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
		
		if (RobotMap.HATCH_TOP_LEFT_SOLENOID.get()) {
			RobotMap.HATCH_TOP_LEFT_SOLENOID.set(false);
		}
		if (RobotMap.HATCH_BOTTOM_LEFT_SOLENOID.get()) {
			RobotMap.HATCH_BOTTOM_LEFT_SOLENOID.set(false);
		}
		if (RobotMap.HATCH_TOP_RIGHT_SOLENOID.get()) {
			RobotMap.HATCH_TOP_RIGHT_SOLENOID.set(false);
		}
		if (RobotMap.HATCH_BOTTOM_RIGHT_SOLENOID.get()) {
			RobotMap.HATCH_BOTTOM_RIGHT_SOLENOID.set(false);
		}
		
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {	
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		Robot.hatchSubsystem.setSlideSpeed(0);
	}

}