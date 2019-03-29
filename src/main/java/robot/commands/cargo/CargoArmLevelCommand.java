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
	
	private double targetLevel;
	
	public CargoArmLevelCommand() {

		super(TConst.NO_COMMAND_TIMEOUT, Robot.oi);

		// Use requires() here to declare subsystem dependencies
		requires(Robot.cargoSubsystem);
	}
	
	public CargoArmLevelCommand(int level) {

		super(TConst.NO_COMMAND_TIMEOUT, Robot.oi);

		// Use requires() here to declare subsystem dependencies
		requires(Robot.cargoSubsystem);
		Robot.oi.setArmLevel(level);
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
		double currentLevel = Robot.cargoSubsystem.getCurrentLevel();
		targetLevel = Robot.oi.getArmLevel();
		if (getCommandName().equals(COMMAND_NAME)) {
			logMessage(getParmDesc() + " starting at: " + currentLevel + " Target: " + targetLevel);
		}
		
		
		
		
		if (currentLevel < targetLevel) {
			Robot.cargoSubsystem.setArmSpeed(0.15);
			armUp = true;
		}
		
		if (currentLevel > targetLevel) {
			Robot.cargoSubsystem.setArmSpeed(-0.15);
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
		
		if (super.isFinished()) {
			return true;
		}
		
		if (Robot.oi.getArmDriveMode() == true) {
			return true;
		}
		if (Robot.oi.getArmLevel() != targetLevel) {
			return true;
		}
		
		double currentLevel = Robot.cargoSubsystem.getCurrentLevel();
		
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
		logMessage("Ending at: " + Robot.cargoSubsystem.getCurrentLevel());
	}

}
