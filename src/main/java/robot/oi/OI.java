package robot.oi;

import com.torontocodingcollective.oi.TButton;
import com.torontocodingcollective.oi.TButtonPressDetector;
import com.torontocodingcollective.oi.TGameController;
import com.torontocodingcollective.oi.TGameController_Logitech;
import com.torontocodingcollective.oi.TOi;
import com.torontocodingcollective.oi.TRumbleManager;
import com.torontocodingcollective.oi.TStick;
import com.torontocodingcollective.oi.TStickPosition;
import com.torontocodingcollective.oi.TToggle;
import com.torontocodingcollective.oi.TTrigger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;

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
 * Bumpers/Triggers: Left Bumper = Turbo shift
 * 
 * POV: Any Angle = Rotate to the Pressed Angle
 * 
 */
public class OI extends TOi {

	private TGameController driverController = new TGameController_Logitech(0);
	private TRumbleManager  driverRumble     = new TRumbleManager("Driver", driverController);

	private TToggle         compressorToggle = new TToggle(driverController, TStick.LEFT);
	private TToggle         speedPidToggle   = new TToggle(driverController, TStick.RIGHT);

	private DriveSelector   driveSelector    = new DriveSelector();

	private TButtonPressDetector armUpDetector = new TButtonPressDetector(driverController, TButton.RIGHT_BUMPER);
	private TButtonPressDetector armDownDetector = new TButtonPressDetector(driverController, TButton.LEFT_BUMPER);

	private double 			armLevelSetPoint = 0;   

	private TToggle         cargoToggle      = new TToggle(driverController, TButton.A);

	private boolean         armManualDriveMode     = true;

	@Override
	public boolean getCancelCommand() {
		return driverController.getButton(TButton.BACK);
	}

	public boolean getCompressorEnabled() {
		return compressorToggle.get();
	}

	@Override
	public TStickPosition getDriveStickPosition(TStick stick) {
		return driverController.getStickPosition(stick);
	}

	@Override
	public boolean getReset() {
		return driverController.getButton(TButton.START);
	}

	@Override
	public int getRotateToHeading() {
		return driverController.getPOV();
	}

	/**
	 * Get the selected drive type
	 * 
	 * @return {@link DriveControlType} selected on the SmartDashboard. The default
	 *         drive type is {@link DriveControlType#ARCADE}
	 */
	public DriveControlType getSelectedDriveType() {
		return driveSelector.getDriveControlType();
	}

	/**
	 * Get the selected single stick side
	 * 
	 * @return {@link TStick} selected on the SmartDashboard. The default single
	 *         stick drive is {@link TStick#RIGHT}
	 */
	public TStick getSelectedSingleStickSide() {
		return driveSelector.getSingleStickSide();
	}

	@Override
	public boolean getSpeedPidEnabled() {
		return speedPidToggle.get();
	}

	public boolean getTurboOn() {
		return driverController.getButton(TButton.LEFT_BUMPER);
	}

	public void init() {
		compressorToggle.set(true);
		speedPidToggle.set(false);
	}

	public void setSpeedPidEnabled(boolean state) {
		speedPidToggle.set(state);
	}

	/* *************************************************
	 * Arm / Cargo Subsystem buttons
    /* *************************************************/
	public boolean getArmDriveMode() {
		return armManualDriveMode;
	}

	public double getArmUp(){
		return driverController.getTrigger(TTrigger.RIGHT);
	}

	public double getArmDown(){
		return driverController.getTrigger(TTrigger.LEFT);
	}

	public boolean cargoIntake() {
		return cargoToggle.get();
	}

	public boolean cargoEject() {
		return driverController.getButton(TButton.Y);
	}

	public void setArmLevel(double level) {
		armLevelSetPoint = level;
	}
	
	public double getArmLevel() {
		return armLevelSetPoint;
	}


	@Override
	public void updatePeriodic() {

		// Set the arm manual mode
		if (armUpDetector.get()) {
			// If we were previously using a manual
			// drive, then the arm set point can
			// be incorrect. 
			if (armManualDriveMode) {
				double currentArmLevel = Robot.cargoSubsystem.getCurrentLevel();
				armLevelSetPoint = Math.floor(currentArmLevel);
			}
			armLevelSetPoint ++;
			if (armLevelSetPoint > 5) {
				armLevelSetPoint = 5;
			}
			armManualDriveMode = false;
		}
		if (armDownDetector.get()) {
			if (armManualDriveMode) {
				double currentArmLevel = Robot.cargoSubsystem.getCurrentLevel();
				armLevelSetPoint = Math.ceil(currentArmLevel);
			}
			armLevelSetPoint = armLevelSetPoint - 1;
			if (armLevelSetPoint < 0) {
				armLevelSetPoint = 0;
			}
			armManualDriveMode = false;
		}
		if (getArmUp() > 0) {
			armManualDriveMode = true;
		}
		else if (getArmDown() > 0) {
			armManualDriveMode = true;
		}

		// Update all Toggles
		compressorToggle.updatePeriodic();
		speedPidToggle.updatePeriodic();
		driverRumble.updatePeriodic();

		// Update all SmartDashboard values
		SmartDashboard.putBoolean("Speed PID Toggle", getSpeedPidEnabled());
		SmartDashboard.putBoolean("Compressor Toggle", getCompressorEnabled());
		SmartDashboard.putString("Driver Controller", driverController.toString());
		SmartDashboard.putNumber("Arm Level",armLevelSetPoint);
		SmartDashboard.putBoolean("Arm Manual Drive Mode", getArmDriveMode());
	}
}
