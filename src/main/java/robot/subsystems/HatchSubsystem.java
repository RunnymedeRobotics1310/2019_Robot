package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch.DefaultState;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TSpeedController;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.hatch.DefaultHatchCommand;
import robot.commands.hatch.HatchCentreCommand;

/**
 * Subsystem for the hatch mechanism. Involves the belt slider and pneumatic placement/grabbing mechanisim.
 */
public class HatchSubsystem extends TSubsystem {

	TSpeedController slideMotor = new TCanSpeedController(
			RobotMap.HATCH_SLIDE_CAN_SPEED_CONTROLLER_TYPE,RobotMap.HATCH_SLIDE_CAN_SPEED_CONTROLLER_ADDRESS,
			RobotMap.HATCH_SLIDE_CAN_MOTOR_ISINVERTED);
	TEncoder slideEncoder = slideMotor.getEncoder();
	TLimitSwitch leftSlideLimit = new TLimitSwitch(RobotMap.HATCH_LEFT_LIMIT_SWITCH_DIO_PORT, DefaultState.TRUE);
	TLimitSwitch rightSlideLimit = new TLimitSwitch(RobotMap.HATCH_RIGHT_LIMIT_SWITCH_DIO_PORT, DefaultState.TRUE);
	Solenoid pickupSolenoid = new Solenoid(RobotMap.HATCH_PICKUP_SOLENOID);//Testing
	Solenoid punchSolenoid =new Solenoid( RobotMap.HATCH_PUNCH_SOLENOID);
	
	boolean extendHatch = true;
	boolean ejectHatch = true;
	
	public void init() {
		slideEncoder.setInverted(true);
		pickupSolenoid.set(!extendHatch);
		punchSolenoid.set(!ejectHatch);
	}

	protected void initDefaultCommand() {
		setDefaultCommand(new DefaultHatchCommand());
	}

	public void setSlideSpeed (double slideSpeed) {
		slideSpeed = slideSpeed;
		if (slideSpeed>0&&(!leftSlideLimitDetected())) {
			slideMotor.set(slideSpeed);
		}
		else if (slideSpeed<0&&(!rightSlideLimitDetected())) {
			slideMotor.set(slideSpeed);
		}
		else {
			slideMotor.set(0);
		}
	}
	
	public boolean isCentered() {
		if (Math.abs(getSlideMotorEncoderCount())<200) {
			return true;
		}
		return false;
	}
	
	public void resetEncoder() {
		slideEncoder.reset();
	}
	
	public void extendPunchMech () {
		punchSolenoid.set(ejectHatch);
	}
	
	public void retractPunchMech () {
		punchSolenoid.set(!ejectHatch);
	}
	
	public void extendHatchMech() {
		pickupSolenoid.set(extendHatch);
	}
	
	public void retractHatchMech() {
		pickupSolenoid.set(!extendHatch);
	}

	public boolean leftSlideLimitDetected() {
		return leftSlideLimit.atLimit();
	}

	public boolean rightSlideLimitDetected() {
		return rightSlideLimit.atLimit();
	}
	
	public int getSlideMotorEncoderCount() {
		return slideEncoder.get();
	}

	public void updatePeriodic() {
		
		if (leftSlideLimit.atLimit()) {
			slideEncoder.set(RobotConst.LEFT_HATCH_LIMIT_ENCODER_COUNT);
			if (slideMotor.get() > 0) {
				slideMotor.set(0);
			}
		}
		
		if (rightSlideLimit.atLimit()) {
			slideEncoder.set(RobotConst.RIGHT_HATCH_LIMIT_ENCODER_COUNT);
			if (slideMotor.get() < 0) {
				slideMotor.set(0);
			}
		}
		SmartDashboard.putNumber("Slide Motor", slideMotor.get());
		SmartDashboard.putNumber("Slide Encoder Count", getSlideMotorEncoderCount());
		SmartDashboard.putBoolean("Top left Solenoid Extended", pickupSolenoid.get());
		SmartDashboard.putBoolean("Punch Solenoid 2 Extended", punchSolenoid.get());
		SmartDashboard.putBoolean("Left Slide Limit", leftSlideLimit.atLimit());
		SmartDashboard.putBoolean("right Slide Limit", rightSlideLimit.atLimit());
		
	}
}