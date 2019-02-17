package robot.oi;

import com.torontocodingcollective.oi.TButton;
import com.torontocodingcollective.oi.TButtonPressDetector;
import com.torontocodingcollective.oi.TGameController;
import com.torontocodingcollective.oi.TGameController_Logitech;
import com.torontocodingcollective.oi.TGameController_Xbox;
import com.torontocodingcollective.oi.TOi;
import com.torontocodingcollective.oi.TRumbleManager;
import com.torontocodingcollective.oi.TStick;
import com.torontocodingcollective.oi.TStickPosition;
import com.torontocodingcollective.oi.TToggle;
import com.torontocodingcollective.oi.TTrigger;

import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.commands.hatch.*;

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
public class OI extends TOi {

	private TGameController driverController = new TGameController_Xbox(0);
	private TRumbleManager  driverRumble     = new TRumbleManager("Driver", driverController);

	private TGameController operatorController = new TGameController_Xbox(1);
	private TRumbleManager  operatorRumble     = new TRumbleManager("Operator", operatorController);

	private TToggle         compressorToggle = new TToggle(driverController, TStick.LEFT);
	private TToggle         speedPidToggle   = new TToggle(driverController, TStick.RIGHT);

	private DriveSelector   driveSelector    = new DriveSelector();

	private TButtonPressDetector armUpDetector = new TButtonPressDetector(driverController, TButton.RIGHT_BUMPER);
	private TButtonPressDetector armDownDetector = new TButtonPressDetector(driverController, TButton.LEFT_BUMPER);

	private boolean         armManualDriveMode = true;
	private double 			armLevelSetPoint   = 0;   
	//private TToggle         cargoToggle        = new TToggle(driverController, TButton.A);

	private boolean         liftModeEnabled;

	/* *************************************************
	 * Initializers and General Controls
    /* *************************************************/
	public void init() {
		compressorToggle.set(false);
		speedPidToggle.set(false);
	}

	@Override
	public boolean getCancelCommand() {
		return driverController.getButton(TButton.BACK);
	}

	public boolean getCompressorEnabled() {
		return compressorToggle.get();
	}

	@Override
	public boolean getReset() {
		return driverController.getButton(TButton.START);
	}

	public boolean getLiftModeEnabled() {
		return operatorController.getButton(TButton.START);
	}

	public boolean getHatchModeEnabled() {
		return operatorController.getButton(TButton.BACK);
	}

	public void startDriverRumble() {
		driverRumble.rumbleOn();
	}
	
	public void endDriverRumble() {
		driverRumble.rumbleOff();
	}
	
	public void startOperatorRumble() {
		operatorRumble.rumbleOn();
	}

	public void endOperatorRumble() {
		operatorRumble.rumbleOn();
	}
	
	/* *************************************************
	 * Drive Subsystem buttons
    /* *************************************************/
	@Override
	public TStickPosition getDriveStickPosition(TStick stick) {
		return driverController.getStickPosition(stick);
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

	public void setSpeedPidEnabled(boolean state) {
		speedPidToggle.set(state);
	}

	/* *************************************************
	 * Hatch Subsystem buttons
    /* *************************************************/
	public double getHatchSlideLeft() {
		if (!liftModeEnabled) {
			return operatorController.getTrigger(TTrigger.LEFT);
		}
		else {
			return 0;
		}
	}

	public double getHatchSlideRight() {
		if (!liftModeEnabled) {
			return operatorController.getTrigger(TTrigger.RIGHT);
		}
		else {
			return 0;
		}
	}

	public boolean getHatchSlideCentre(){
		if (!liftModeEnabled) {
			return operatorController.getButton(TButton.X);
		}
		else {
			return false;
		}
	}
	
	public boolean getHatchMechExtend() {
		if (!liftModeEnabled) {
			return operatorController.getButton(TButton.Y);
		}
		else {
			return false;
		}
	}
	
	public boolean getHatchMechRetract() {
		if (!liftModeEnabled) {
			return operatorController.getButton(TButton.A);
		}
		else {
			return false;
		}
	}
	
	public boolean getHatchMechEject() {
		if (!liftModeEnabled) {
			return operatorController.getButton(TButton.B);
		}
		else {
			return false;
		}
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
		return driverController.getButton(TButton.A);
	}

	public boolean cargoEject() {
		return driverController.getButton(TButton.Y);
	}

	public boolean intakeOff(){
		return  driverController.getButton(TButton.B);
	}

	public void setArmLevel(double level) {
		armLevelSetPoint = level;
	}
	
	public double getArmLevel() {
		return armLevelSetPoint;
	}


	/* *************************************************
	 * Lift Subsystem buttons
    /* *************************************************/
	public boolean getRetractFrontLift() {
		if (liftModeEnabled) {
			return operatorController.getButton(TButton.RIGHT_BUMPER);
		}
		else {
			return false;
		}

	}

	public double getExtendFrontLift() {
		if (liftModeEnabled) {
			return operatorController.getTrigger(TTrigger.RIGHT);
		}
		else {
			return 0;
		}
	}

	public boolean getRetractRearLift() {
		if (liftModeEnabled) {
			return operatorController.getButton(TButton.LEFT_BUMPER);
		}
		else {
			return false;
		}
	}

	public double getExtendRearLift() {
		if (liftModeEnabled) {
			return operatorController.getTrigger(TTrigger.LEFT);
		}
		else {
			return 0;
		}
	}


	/* *************************************************
	 * Update and SmartDashboard
    /* *************************************************/
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


		// Update the Lift Mode
		if (getLiftModeEnabled()) {
			liftModeEnabled=true;
		}
		if (getHatchModeEnabled()) {
			Scheduler.getInstance().add(new HatchCentreCommand());
			liftModeEnabled=false;
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
		SmartDashboard.putBoolean("LiftModeEnabled", liftModeEnabled);
		
		
	}
}
