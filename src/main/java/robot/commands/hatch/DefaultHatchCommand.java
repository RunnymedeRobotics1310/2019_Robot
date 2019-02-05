package robot.commands.hatch;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import robot.Robot;

/**
 * The default hatch command TODO: commenting
 */
public class DefaultHatchCommand extends TSafeCommand {

	private static final String COMMAND_NAME = 
			DefaultHatchCommand.class.getSimpleName();

	public DefaultHatchCommand() {

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
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		//FIXME left will ovveride right for now.
		if (Robot.oi.getHatchSlideLeft()>0) {
			Robot.hatchSubsystem.setSlideSpeed(Robot.oi.getHatchSlideLeft());
		}
		else if (Robot.oi.getHatchSlideRight()>0) {
			Robot.hatchSubsystem.setSlideSpeed(Robot.oi.getHatchSlideRight());
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

}