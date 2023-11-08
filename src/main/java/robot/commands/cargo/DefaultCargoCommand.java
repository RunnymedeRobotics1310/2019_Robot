package robot.commands.cargo;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import robot.commands.LoggingCommandBase;
import robot.oi.OI;
import robot.subsystems.CargoSubsystem;

/**
 *
 */
public class DefaultCargoCommand extends LoggingCommandBase {

    private final OI             operatorInput;
    private final CargoSubsystem cargoSubsystem;

    public DefaultCargoCommand(OI operatorInput, CargoSubsystem cargoSubsystem) {

        this.operatorInput  = operatorInput;
        this.cargoSubsystem = cargoSubsystem;

        // Use requires() here to declare subsystem dependencies
        addRequirements(cargoSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {

        logCommandStart();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

        if (operatorInput.getArmManualDriveMode() == false) {
            if (operatorInput.getArmLevel() != cargoSubsystem.getCurrentLevel()) {
                CommandScheduler.getInstance().schedule(new CargoArmLevelCommand(operatorInput, cargoSubsystem));
            }
        }

        if (operatorInput.getArmUp() > 0) {
            cargoSubsystem.setArmSpeed(operatorInput.getArmUp() / 2.0);
            operatorInput.setArmLevel(cargoSubsystem.getCurrentLevel());
        }
        else if (operatorInput.getArmDown() > 0) {
            cargoSubsystem.setArmSpeed(-operatorInput.getArmDown() / 2.0);
            operatorInput.setArmLevel(cargoSubsystem.getCurrentLevel());

        }
        else {
            cargoSubsystem.setArmSpeed(0);
        }

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
        return false;
    }

}