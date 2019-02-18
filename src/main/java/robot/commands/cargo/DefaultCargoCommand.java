package robot.commands.cargo;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;

/**
 *
 */
public class DefaultCargoCommand extends TSafeCommand {

	private static final String COMMAND_NAME = 
			DefaultCargoCommand.class.getSimpleName();

	public DefaultCargoCommand() {

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
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
		if (Robot.oi.getArmDriveMode() == false ) {
			if (Robot.oi.getArmLevel() != Robot.cargoSubsystem.getCurrentLevel()) {
				Scheduler.getInstance().add(new CargoArmLevelCommand());
				return;
			}
		}

		if (Robot.oi.getArmUp() >= 0.80) {
			Robot.cargoSubsystem.setArmSpeed(-0.25);
			Robot.oi.setArmLevel(Robot.cargoSubsystem.getCurrentLevel());

		} else if (Robot.oi.getArmDown() >= 0.80) {
			Robot.cargoSubsystem.setArmSpeed(0.15);
			Robot.oi.setArmLevel(Robot.cargoSubsystem.getCurrentLevel());

		} else {
			Robot.cargoSubsystem.setArmSpeed(0);
			
		}
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

}