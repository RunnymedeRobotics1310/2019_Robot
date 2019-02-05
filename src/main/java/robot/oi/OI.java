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

    private TGameController driverController = new TGameController_Logitech(0);
    private TRumbleManager  driverRumble     = new TRumbleManager("Driver", driverController);
    
    private TGameController operatorController = new TGameController_Logitech(1);
    private TRumbleManager  operatorRumble     = new TRumbleManager("Driver", operatorController);

    private TToggle         compressorToggle = new TToggle(driverController, TStick.LEFT);
    private TToggle         speedPidToggle   = new TToggle(driverController, TStick.RIGHT);

    private DriveSelector   driveSelector    = new DriveSelector();
    
    private TButtonPressDetector armUpDetector = new TButtonPressDetector(driverController, TButton.RIGHT_BUMPER);
    private TButtonPressDetector armDownDetector = new TButtonPressDetector(driverController, TButton.LEFT_BUMPER);
    
    private int 			armLevelSetPoint = 0;

    public double getHatchSlideLeft() {
    	return operatorController.getTrigger(TTrigger.LEFT);
    }
    
    public double getHatchSlideRight() {
    	return operatorController.getTrigger(TTrigger.RIGHT);
    }
    
    
    
    
    
    
    /* *************************************************
     * Initializers and General Controls
    /* *************************************************/
    public void init() {
        compressorToggle.set(true);
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
    
    public int getArmLevel() {
    	return armLevelSetPoint;
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
     * Cargo Subsystem buttons
    /* *************************************************/
    public double getArmUp() {
        return driverController.getTrigger(TTrigger.RIGHT);
    }

    public double getArmDown() {
        return driverController.getTrigger(TTrigger.LEFT);
    }
    
    /* *************************************************
     * Lift Subsystem buttons
    /* *************************************************/
   public boolean getRetractFrontLift() {
    	
    	// Put some code here
    	return operatorController.getButton(TButton.RIGHT_BUMPER);
    	
    }
    
    public double getExtendFrontLift() {
		return operatorController.getTrigger(TTrigger.RIGHT);
    	
    }
    
    public boolean getRetractRearLift() {
    	
    	// Put some code here
    	return operatorController.getButton(TButton.LEFT_BUMPER);
    	
    }
    
    public double getExtendRearLift() {
		return operatorController.getTrigger(TTrigger.LEFT);
    	
    }
    
    
    /* *************************************************
     * Update and SmartDashboard
    /* *************************************************/
    @Override
    public void updatePeriodic() {

        // Update all Toggles
        compressorToggle.updatePeriodic();
        speedPidToggle.updatePeriodic();
        driverRumble.updatePeriodic();
       
        if (armUpDetector.get()) {
        	armLevelSetPoint ++;
        	if (armLevelSetPoint > 5) {
        		armLevelSetPoint = 5;
        	}
        }
        if (armDownDetector.get()) {
        	armLevelSetPoint = armLevelSetPoint - 1;
        	if (armLevelSetPoint < 0) {
        		armLevelSetPoint = 0;
        	}
        }
        

        // Update all SmartDashboard values
        SmartDashboard.putBoolean("Speed PID Toggle", getSpeedPidEnabled());
        SmartDashboard.putBoolean("Compressor Toggle", getCompressorEnabled());
        SmartDashboard.putString("Driver Controller", driverController.toString());
        SmartDashboard.putNumber("Arm Level",armLevelSetPoint);
    }
}
