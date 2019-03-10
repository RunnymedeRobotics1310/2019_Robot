package robot.commands.hatch;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import robot.Robot;

/**
 * The default hatch command TODO: commenting
 */
public class HatchCentreCommand extends TSafeCommand {

	private static final String COMMAND_NAME = 
			DefaultHatchCommand.class.getSimpleName();

	private boolean isLeftOfCentre;

	public HatchCentreCommand() {

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
		if (!Robot.hatchSubsystem.isCentered()) {
			if (Robot.hatchSubsystem.getSlideMotorEncoderCount()>0) {
				isLeftOfCentre=true;
				Robot.hatchSubsystem.setSlideSpeed(-0.15);
			}
			else if (Robot.hatchSubsystem.getSlideMotorEncoderCount()<0) {
				isLeftOfCentre=false;
				Robot.hatchSubsystem.setSlideSpeed(0.);
			}
		}
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
		return Robot.hatchSubsystem.isCentered();
	}

	@Override
	protected void end() {
		Robot.hatchSubsystem.setSlideSpeed(0);
	}

}