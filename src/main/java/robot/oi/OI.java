package robot.oi;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import robot.Constants.Side;
import robot.commands.CancelCommand;
import robot.commands.hatch.HatchCenterCommand;
import robot.commands.hatch.HatchEjectBusCommand;
import robot.commands.hatch.HatchEjectRocketCommand;
import robot.commands.lift.L2HopUp;
import robot.commands.lift.L3Command;
import robot.subsystems.CargoSubsystem;
import robot.subsystems.DriveSubsystem;
import robot.subsystems.HatchSubsystem;
import robot.subsystems.LiftSubsystem;
import robot.subsystems.PneumaticsSubsystem;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

/**
 * Driver Controller (inherited from TOi)
 *
 * Sticks: Right Stick = Drive Stick Left Stick = Drive Stick Right Stick Press
 * = Toggle PIDs Left Stick Press = Toggle Compressor
 *
 * Buttons: Start Button = Reset Encoders and Gyro Back Button = Cancel any
 * Command
 *
 * Bumpers/Triggers:
 *
 * POV: Any Angle = Rotate to the Pressed Angle
 *
 */
public class OI extends SubsystemBase {

    public GameController driverController   = new GameController(0);

    public GameController operatorController = new GameController(1);

    private boolean       compressorToggle   = true;

    private boolean       armManualDriveMode = true;
    private double        armLevelSetPoint   = 0;

    private boolean       hatchModeEnabled   = true;
    private boolean       liftModeEnabled    = false;

    /*
     * *************************************************
     * Initializers and General Controls
     * /*
     *************************************************/
    public boolean getCancelCommand() {
        return driverController.getStartButton();
    }

    public boolean getCompressorEnabled() {
        return compressorToggle;
    }

    public boolean getLiftModeEnabled() {
        return operatorController.getStartButton();
    }

    public boolean getHatchModeEnabled() {
        return operatorController.getBackButton();
    }

    /*
     * *************************************************
     * Hatch Subsystem buttons
     * /*
     *************************************************/
    public boolean getHatchEjectRight() {
        if (hatchModeEnabled) {
            return operatorController.getBButton();
        }
        else {
            return false;
        }
    }

    public boolean getHatchEjectLeft() {
        if (hatchModeEnabled) {
            return operatorController.getXButton();
        }
        else {
            return false;
        }
    }

    public boolean getHatchRocketEject() {
        if (hatchModeEnabled) {
            return operatorController.getRightBumper();
        }
        else {
            return false;
        }
    }

    public double getHatchSlideLeft() {
        if (hatchModeEnabled) {
            return operatorController.getLeftTriggerAxis();
        }
        else {
            return 0;
        }
    }

    public double getHatchSlideRight() {
        if (hatchModeEnabled) {
            return operatorController.getRightTriggerAxis();
        }
        else {
            return 0;
        }
    }

    public boolean getHatchSlideCentre() {
        if (hatchModeEnabled) {
            return operatorController.getLeftBumper();
        }
        else {
            return false;
        }
    }

    public boolean getHatchMechExtend() {
        if (hatchModeEnabled) {
            return operatorController.getYButton();
        }
        else {
            return false;
        }
    }

    public boolean getHatchMechRetract() {
        if (hatchModeEnabled) {
            return operatorController.getAButton();
        }
        else {
            return false;
        }
    }

    /*
     * *************************************************
     * Arm / Cargo Subsystem buttons
     * /*
     *************************************************/
    public boolean getArmManualDriveMode() {
        return armManualDriveMode;
    }

    public void setArmDriveMode(boolean manualMode) {
        armManualDriveMode = manualMode;
    }

    public double getArmUp() {
        return driverController.getRightTriggerAxis();
    }

    public double getArmDown() {
        return driverController.getLeftTriggerAxis();
    }

    public boolean cargoIntake() {
        if (hatchModeEnabled) {
            if (operatorController.getLeftY() > 0.3) {
                return true;
            }
        }
        return driverController.getXButton();
    }

    public boolean cargoEject() {
        if (hatchModeEnabled) {
            if (operatorController.getLeftY() < -0.3) {
                return true;
            }
        }
        return driverController.getYButton();
    }

    public boolean cargoEjectFast() {
        return driverController.getBButton();
    }

    public void setArmLevel(double level) {
        armLevelSetPoint = level;
    }

    public double getArmLevel() {
        return armLevelSetPoint;
    }


    /*
     * *************************************************
     * Lift Subsystem buttons
     * /*
     *************************************************/
    public boolean getRetractFrontLift() {
        if (liftModeEnabled) {
            return operatorController.getRightBumper();
        }
        return false;
    }

    public double getExtendFrontLift() {
        if (liftModeEnabled) {
            return operatorController.getRightTriggerAxis();
        }
        return 0;
    }

    public boolean getRetractRearLift() {
        if (liftModeEnabled) {
            return operatorController.getLeftBumper();
        }
        return false;
    }

    public double getExtendRearLift() {
        if (liftModeEnabled) {
            return operatorController.getLeftTriggerAxis();
        }
        return 0;
    }

    public boolean getLiftDriveForward() {
        if (liftModeEnabled) {
            return operatorController.getAButton();
        }
        return false;
    }

    public boolean syncedExtendLift() {
        if (liftModeEnabled) {
            return operatorController.getYButton();
        }
        return false;
    }

    public boolean syncedRetractLift() {
        if (liftModeEnabled) {
            return operatorController.getBButton();
        }
        return false;
    }

    public boolean startLevel3() {
        if (liftModeEnabled) {
            return operatorController.getLeftStickButton();
        }
        return false;
    }

    public boolean startLevel2() {
        if (liftModeEnabled) {
            return operatorController.getRightStickButton();
        }
        return false;
    }


    /*
     * *************************************************
     * Update and SmartDashboard
     * /*
     *************************************************/
    @Override
    public void periodic() {

        if (driverController.getPOV() >= 0) {
            switch (driverController.getPOV()) {
            case 0:
                armLevelSetPoint = 3;
                armManualDriveMode = false;
                break;

            case 180:
                armLevelSetPoint = 0;
                armManualDriveMode = false;
                break;

            default:
                // do nothing
                break;
            }
        }
        else if (driverController.getAButton()) {
            armLevelSetPoint   = 1;
            armManualDriveMode = false;
        }
        else if (driverController.getLeftBumper()) {
            armLevelSetPoint   = 2;
            armManualDriveMode = false;
        }
        else if (driverController.getRightBumper()) {
            armLevelSetPoint   = 4;
            armManualDriveMode = false;
        }
        if (getArmUp() > 0) {
            armManualDriveMode = true;
        }
        else if (getArmDown() > 0) {
            armManualDriveMode = true;
        }

        // Update the Lift Mode
        if (getLiftModeEnabled()) {
            liftModeEnabled  = true;
            hatchModeEnabled = false;
        }
        if (getHatchModeEnabled()) {
            liftModeEnabled  = false;
            hatchModeEnabled = true;
        }

        // Update all Toggles
        if (driverController.getLeftStickButtonPressed()) {
            compressorToggle = !compressorToggle;
        }

        // Update all SmartDashboard values
        SmartDashboard.putBoolean("Compressor Toggle", getCompressorEnabled());
        SmartDashboard.putString("Driver Controller", driverController.toString());
        SmartDashboard.putString("Operator Controller", operatorController.toString());
        SmartDashboard.putNumber("Arm Level", armLevelSetPoint);
        SmartDashboard.putBoolean("Arm Manual Drive Mode", getArmManualDriveMode());
        SmartDashboard.putBoolean("Lift Mode", liftModeEnabled);
        SmartDashboard.putBoolean("Hatch Mode", hatchModeEnabled);
    }

    public void configureButtonBindings(DriveSubsystem driveSubsystem, CargoSubsystem cargoSubsystem,
        HatchSubsystem hatchSubsystem, LiftSubsystem liftSubsystem, PneumaticsSubsystem pneumaticsSubsystem) {

        /*
         * Cancel command
         */
        new Trigger(() -> getCancelCommand())
            .onTrue(new CancelCommand(this, driveSubsystem, cargoSubsystem, hatchSubsystem, liftSubsystem));

        /*
         * Hatch Commands
         */
        new Trigger(() -> getHatchSlideCentre())
            .onTrue(new HatchCenterCommand(hatchSubsystem));

        new Trigger(() -> getHatchRocketEject())
            .onTrue(new HatchEjectRocketCommand(hatchSubsystem));

        new Trigger(() -> getHatchEjectLeft())
            .onTrue(new HatchEjectBusCommand(Side.LEFT, hatchSubsystem));

        new Trigger(() -> getHatchEjectRight())
            .onTrue(new HatchEjectBusCommand(Side.LEFT, hatchSubsystem));

        /*
         * Climb commands
         */
        new Trigger(() -> startLevel3())
            .onTrue(new L3Command(this, liftSubsystem, driveSubsystem, hatchSubsystem));

        new Trigger(() -> startLevel2())
            .onTrue(new L2HopUp(this, liftSubsystem, driveSubsystem, hatchSubsystem));


    }
}
