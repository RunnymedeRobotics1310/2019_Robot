package robot.commands.cargo;

import robot.RobotConst;
import robot.commands.LoggingCommand;
import robot.oi.OI;
import robot.subsystems.CargoSubsystem;

/**
 *
 */
public class CargoArmLevelCommand extends LoggingCommand {

    private final OI             operatorInput;
    private final CargoSubsystem cargoSubsystem;

    private double               targetLevel;
    private double               targetEncoderCounts;
    private boolean              armUp;

    private double               diff;

    public CargoArmLevelCommand(OI operatorInput, CargoSubsystem cargoSubsystem) {

        this.operatorInput  = operatorInput;
        this.cargoSubsystem = cargoSubsystem;

        // Use requires() here to declare subsystem dependencies
        addRequirements(cargoSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {

        // Turn off manual drive
        operatorInput.setArmDriveMode(false);

        double currentLevel = cargoSubsystem.getCurrentLevel();
        targetLevel         = operatorInput.getArmLevel();
        targetEncoderCounts = RobotConst.ARM_LEVELS[(int) targetLevel];

        logCommandStart("starting at: " + currentLevel + " Target: " + targetLevel);

        // Start moving towards the target
        if (currentLevel < targetLevel) {
            cargoSubsystem.setArmSpeed(0.15);
            armUp = true;
        }

        if (currentLevel > targetLevel) {
            cargoSubsystem.setArmSpeed(-0.15);
            armUp = false;
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        // Informs the arm speed based on distance to the target.

        diff = targetEncoderCounts - cargoSubsystem.getEncoderCounts();
        double correctionSpeed = diff / 2000;
        if (correctionSpeed > 0.4) {
            correctionSpeed = 0.4;
        }
        else if (correctionSpeed < -0.4) {
            correctionSpeed = -0.4;
        }
        else {
            if (correctionSpeed < 0.05 && correctionSpeed > 0.0) {
                correctionSpeed = 0.05;
            }
            else if (correctionSpeed > -0.05 && correctionSpeed < 0.0) {
                correctionSpeed = -0.05;
            }
        }
        cargoSubsystem.setArmSpeed(correctionSpeed);


        // Always allow the driver to intake or eject the ball.
        if (operatorInput.cargoIntake()) {
            if (!cargoSubsystem.isCargoDetected()) {
                cargoSubsystem.startIntake();
            }
            else {
                cargoSubsystem.stopIntake();
            }
        }
        else {
            if (operatorInput.cargoEject()) {
                cargoSubsystem.ejectCargo(false);
            }
            else if (operatorInput.cargoEjectFast()) {
                cargoSubsystem.ejectCargo(true);
            }
            else {
                cargoSubsystem.stopIntake();
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {

        // If the user put the arm in manual mode, then stop
        if (operatorInput.getArmManualDriveMode() == true) {
            setFinishReason("Switching to manual drive");
            return true;
        }

        // If the user changed the target, then stop
        if (operatorInput.getArmLevel() != targetLevel) {
            setFinishReason("Changed target level");
            return true;
        }

        double currentLevel = cargoSubsystem.getCurrentLevel();

        if (armUp == true && currentLevel >= targetLevel) {
            return true;
        }

        if (armUp == false && currentLevel <= targetLevel) {
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {

        logCommandEnd(interrupted);

        cargoSubsystem.setArmSpeed(0);
    }

}
