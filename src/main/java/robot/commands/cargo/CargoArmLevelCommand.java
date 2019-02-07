package robot.commands.cargo;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import robot.Robot;

/**
 *
 */
public class CargoArmLevelCommand extends TSafeCommand {

	private static final String COMMAND_NAME = 
			CargoArmLevelCommand.class.getSimpleName();

	private boolean armUp;
	
	public CargoArmLevelCommand() {

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
		
		double currentLevel = Robot.cargoSubsystem.getCurrentLevel();
		double targetLevel = 1;
		
		if (currentLevel < targetLevel) {
			Robot.cargoSubsystem.setArmSpeed(0.2);
			armUp = true;
		}
		
		if (currentLevel > targetLevel) {
			Robot.cargoSubsystem.setArmSpeed(-0.2);
			armUp = false;
		}
		
		
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		
	
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		
		double currentLevel = Robot.cargoSubsystem.getCurrentLevel();
		double targetLevel = 1;
		
		if (armUp == true && currentLevel >= targetLevel) {
			return true;
		}
		
		if (armUp == false && currentLevel <= targetLevel) {
			return true;
		}
		
		return false;
	}
	
	@Override
	protected void end() {
		Robot.cargoSubsystem.setArmSpeed(0);
	}

}
