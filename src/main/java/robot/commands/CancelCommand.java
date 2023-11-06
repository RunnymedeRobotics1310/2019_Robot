package robot.commands;

import robot.oi.OI;
import robot.subsystems.CargoSubsystem;
import robot.subsystems.DriveSubsystem;
import robot.subsystems.HatchSubsystem;
import robot.subsystems.LiftSubsystem;

/**
 * This command is used to safely stop the robot in its current position, and to cancel any running
 * commands
 */
public class CancelCommand extends LoggingCommandBase {

    private final OI             operatorInput;
    private final DriveSubsystem driveSubsystem;
    private final CargoSubsystem cargoSubsystem;
    private final HatchSubsystem hatchSubsystem;
    private final LiftSubsystem  liftSubsystem;

    /**
     * Cancel the commands running on all subsystems.
     *
     * All subsystems must be passed to this command, and each subsystem should have a stop command
     * that safely stops the robot from moving.
     */
    public CancelCommand(OI operatorInput, DriveSubsystem driveSubsystem, CargoSubsystem cargoSubsystem,
        HatchSubsystem hatchSubsystem, LiftSubsystem liftSubsystem) {

        this.operatorInput  = operatorInput;
        this.driveSubsystem = driveSubsystem;
        this.cargoSubsystem = cargoSubsystem;
        this.hatchSubsystem = hatchSubsystem;
        this.liftSubsystem  = liftSubsystem;

        addRequirements(driveSubsystem, cargoSubsystem, hatchSubsystem, liftSubsystem);
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        /*
         * The Cancel command is not interruptable and only ends when the cancel button is released.
         */
        return InterruptionBehavior.kCancelIncoming;
    }

    @Override
    public void initialize() {

        logCommandStart();

        stopAll();
    }

    @Override
    public void execute() {
        stopAll();
    }

    @Override
    public boolean isFinished() {

        // The cancel command has a minimum timeout of .5 seconds
        if (!isTimeoutExceeded(.5)) {
            return false;
        }

        // Only end once the cancel button is released after .5 seconds has elapsed
        if (!operatorInput.getCancelCommand()) {
            setFinishReason("Cancel button released");
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }

    private void stopAll() {

        // Stop all of the robot movement
        driveSubsystem.stop();
        cargoSubsystem.stop();
        hatchSubsystem.stop();
        liftSubsystem.stop();
    }
}
