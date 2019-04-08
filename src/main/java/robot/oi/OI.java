package robot.oi;

import com.torontocodingcollective.oi.TAxis;
import com.torontocodingcollective.oi.TButton;
import com.torontocodingcollective.oi.TGameController;
import com.torontocodingcollective.oi.TGameController_Xbox;
import com.torontocodingcollective.oi.TOi;
import com.torontocodingcollective.oi.TRumbleManager;
import com.torontocodingcollective.oi.TStick;
import com.torontocodingcollective.oi.TStickPosition;
import com.torontocodingcollective.oi.TToggle;
import com.torontocodingcollective.oi.TTrigger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst.Camera;

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

	private DriveSelector   driveSelector    = new DriveSelector();

//	private TButtonPressDetector armUpDetector = new TButtonPressDetector(driverController, TButton.RIGHT_BUMPER);
//	private TButtonPressDetector armDownDetector = new TButtonPressDetector(driverController, TButton.LEFT_BUMPER);

	private boolean         armManualDriveMode = true;
	private double 			armLevelSetPoint   = 0;   

	private boolean         liftModeEnabled;

	/* *************************************************
	 * Initializers and General Controls
    /* *************************************************/
	public void init() {
		compressorToggle.set(true);
		driverController.axisDeadband=0.1;
		operatorController.axisDeadband=0.1;
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

	public Camera getSelectedCamera() {
		return Camera.HATCH;
	}
	/* *************************************************
	 * Drive Subsystem buttons
    /* *************************************************/
	@Override
	public TStickPosition getDriveStickPosition(TStick stick) {
		return driverController.getStickPosition(stick);
	}

//	public boolean getDriveToPosition(){
//		return driverController.getButton(TButton.A);
//	}

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

	/* *************************************************
	 * Hatch Subsystem buttons
    /* *************************************************/
	public boolean getHatchEjectRight() {
		if (!liftModeEnabled) {
			return operatorController.getButton(TButton.B);
		}
		else {
			return false;
		}
	}
	
	public boolean getHatchEjectLeft() {
		if (!liftModeEnabled) {
			return operatorController.getButton(TButton.X);
		}
		else {
			return false;
		}
	}
	
	public boolean getHatchRocketEject() {
		if (!liftModeEnabled) {
			return operatorController.getButton(TButton.RIGHT_BUMPER);
		}
		else {
			return false;
		}
	}
	
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
			return operatorController.getButton(TButton.LEFT_BUMPER);
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

	/* *************************************************
	 * Arm / Cargo Subsystem buttons
    /* *************************************************/
	public boolean getArmDriveMode() {
		return armManualDriveMode;
	}
	
	public void setArmDriveMode(boolean manualMode) {
		armManualDriveMode=manualMode;
	}
	public double getArmUp(){
		return driverController.getTrigger(TTrigger.RIGHT);
	}

	public double getArmDown(){
		return driverController.getTrigger(TTrigger.LEFT);
	}

	public boolean cargoIntake() {
		if (operatorController.getAxis(TStick.LEFT, TAxis.Y)>0.3) {
			return true;
		}
		return driverController.getButton(TButton.X);
	}

	public boolean cargoEject() {
		if (operatorController.getAxis(TStick.LEFT, TAxis.Y)<-0.3) {
			return true;
		}
		return driverController.getButton(TButton.Y);
	}

	public boolean cargoEjectFast() {
		return driverController.getButton(TButton.B);
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

	public boolean getLiftDriveForward() {
		if (liftModeEnabled) {
			return operatorController.getButton(TButton.A);
		}
		else {
			return false;
		}
	}

	public boolean syncedExtendLift() {
		if (liftModeEnabled) {
			return operatorController.getButton(TButton.Y);
		}
		else {
			return false;
		}
	}

	public boolean syncedRetractLift() {
		if (liftModeEnabled) {
			return operatorController.getButton(TButton.B);
		}
		else {
			return false;
		}
	}

	public boolean startLevel3() {
		if (liftModeEnabled) {
			return operatorController.getButton(TStick.RIGHT);
		}
		else {
			return false;
		}
	}
	
	public boolean startLevel2() {
		if (liftModeEnabled) {
			return operatorController.getButton(TStick.LEFT);
		}
		else {
			return false;
		}
	}


	/* *************************************************
	 * Update and SmartDashboard
    /* *************************************************/
	@Override
	public void updatePeriodic() {

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
		} else if (driverController.getButton(TButton.A)) {
			armLevelSetPoint = 1;
			armManualDriveMode = false;
		} else if (driverController.getButton(TButton.LEFT_BUMPER)) {
			armLevelSetPoint = 2;
			armManualDriveMode = false;
		} else if (driverController.getButton(TButton.RIGHT_BUMPER)) {
			armLevelSetPoint = 4;
			armManualDriveMode = false;
//		} else if (armUpDetector.get()) {
//			// If we were previously using a manual
//			// drive, then the arm set point can
//			// be incorrect. 
//			if (armManualDriveMode) {
//				double currentArmLevel = Robot.cargoSubsystem.getCurrentLevel();
//				armLevelSetPoint = Math.floor(currentArmLevel);
//			}
//			armLevelSetPoint ++;
//			if (armLevelSetPoint >= RobotConst.ARM_LEVELS.length) {
//				armLevelSetPoint = RobotConst.ARM_LEVELS.length - 1;
//			}
//			armManualDriveMode = false;
//		}
//		else if (armDownDetector.get()) {
//			if (armManualDriveMode) {
//				double currentArmLevel = Robot.cargoSubsystem.getCurrentLevel();
//				armLevelSetPoint = Math.ceil(currentArmLevel);
//			}
//			armLevelSetPoint = armLevelSetPoint - 1;
//			if (armLevelSetPoint < 0) {
//				armLevelSetPoint = 0;
//			}
//			armManualDriveMode = false;
		}
		if (getArmUp() > 0) {
			armManualDriveMode = true;
		}
		else if (getArmDown() > 0) {
			armManualDriveMode = true;
		}
		
		// Update the Lift Mode
		if (getLiftModeEnabled()) {
			//Scheduler.getInstance().add(new HatchCentreCommand());
			liftModeEnabled=true;
		}
		if (getHatchModeEnabled()) {
			liftModeEnabled=false;
		}

		// Update all Toggles
		compressorToggle.updatePeriodic();
		driverRumble.updatePeriodic();



		// Update all SmartDashboard values
		SmartDashboard.putBoolean("Speed PID Toggle", getSpeedPidEnabled());
		SmartDashboard.putBoolean("Compressor Toggle", getCompressorEnabled());
		SmartDashboard.putString("Driver Controller", driverController.toString());
		SmartDashboard.putNumber("Arm Level",armLevelSetPoint);
		SmartDashboard.putBoolean("Arm Manual Drive Mode", getArmDriveMode());
		SmartDashboard.putBoolean("LiftModeEnabled", liftModeEnabled);
	}

	@Override
	public boolean getSpeedPidEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
