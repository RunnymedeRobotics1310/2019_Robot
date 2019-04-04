package robot.commands.cargo;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import robot.Robot;
import robot.RobotConst;

/**
 *
 */
public class CargoArmLevelCommand extends TSafeCommand {

	private static final String COMMAND_NAME = 
			CargoArmLevelCommand.class.getSimpleName();

	private boolean armUp;

	private double targetLevel;
	private double targetEncoderCounts;

	private double diff;

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
		Robot.oi.setArmDriveMode(false);
		targetLevel = Robot.oi.getArmLevel();
		targetEncoderCounts=RobotConst.ARM_LEVELS[(int) targetLevel];
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
		// Informs the arm speed based on distance to the target.

		diff = targetEncoderCounts - Robot.cargoSubsystem.getEncoderCounts();
		double correctionSpeed = diff/2000;
		if (correctionSpeed>0.4) {
			correctionSpeed=0.4;
		}
		else if (correctionSpeed<-0.4) {
			correctionSpeed=-0.4;
		}
		else {
			if (correctionSpeed<0.05&&correctionSpeed>0.0) {
				correctionSpeed=0.05;
			}
			else if (correctionSpeed>-0.05&&correctionSpeed<0.0) {
				correctionSpeed=-0.05;
			}
		}
		Robot.cargoSubsystem.setArmSpeed(correctionSpeed);


		// Always allow the driver to intake or eject the ball.
    	if (Robot.oi.cargoIntake()) {
			if (!Robot.cargoSubsystem.isCargoDetected()){
				Robot.cargoSubsystem.startIntake();
			}
			else {
				Robot.cargoSubsystem.stopIntake();
			}
		}
    	else {
	    	if (Robot.oi.cargoEject()) {
	    		Robot.cargoSubsystem.ejectCargo(false);
			}
	    	else if (Robot.oi.cargoEjectFast()) {
	    		Robot.cargoSubsystem.ejectCargo(true);
			}
			else {
				Robot.cargoSubsystem.stopIntake();
			}
    	}

    	// Possible alternative non-PID system

		//		double currentLevel = Robot.cargoSubsystem.getCurrentLevel();
		//		if (currentLevel < targetLevel) {
		//			if (targetLevel - currentLevel > 0.5) {
		//				Robot.cargoSubsystem.setArmSpeed(0.3);
		//			}
		//			else {
		//				Robot.cargoSubsystem.setArmSpeed(0.12);
		//			}
		//		}
		//
		//		if (currentLevel > targetLevel) {
		//			if (currentLevel - targetLevel > 0.5) {
		//				Robot.cargoSubsystem.setArmSpeed(-0.3);
		//			}
		//			else {
		//				Robot.cargoSubsystem.setArmSpeed(-0.12);
		//			}
		//		}


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
