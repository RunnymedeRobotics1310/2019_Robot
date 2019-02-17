package robot.subsystems;

import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch.DefaultState;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TSpeedController;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.RobotMap;
import robot.commands.hatch.DefaultHatchCommand;
import robot.commands.hatch.HatchCentreCommand;
import robot.commands.hatch.HatchEjectCommand;

/**
 * Subsystem for the hatch mechanism. Involves the belt slider and pneumatic placement/grabbing mechanisim.
 */
public class HatchSubsystem extends TSubsystem {

	TSpeedController slideMotor = new TCanSpeedController(
			RobotMap.HATCH_SLIDE_CAN_SPEED_CONTROLLER_TYPE,RobotMap.HATCH_SLIDE_CAN_SPEED_CONTROLLER_ADDRESS);
	TLimitSwitch leftSlideLimit = new TLimitSwitch(RobotMap.HATCH_LEFT_LIMIT_SWITCH_DIO_PORT, DefaultState.TRUE);
	TLimitSwitch rightSlideLimit = new TLimitSwitch(RobotMap.HATCH_RIGHT_LIMIT_SWITCH_DIO_PORT, DefaultState.TRUE);
	Solenoid pickupSolenoid = new Solenoid(RobotMap.HATCH_PICKUP_SOLENOID);//Testing
	Solenoid punchSolenoid =new Solenoid( RobotMap.HATCH_PUNCH_SOLENOID);
	
	public void init() {
	}

	protected void initDefaultCommand() {
		setDefaultCommand(new DefaultHatchCommand());
	}

	public void setSlideSpeed (double slideSpeed) {
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
	
	public void ejectHatch () {
		punchSolenoid.set(false);
	}
	
	public void retractPunchMech () {
		punchSolenoid.set(true);
	}

	public boolean leftSlideLimitDetected() {
		return leftSlideLimit.atLimit();
	}

	public boolean rightSlideLimitDetected() {
		return rightSlideLimit.atLimit();
	}
	
	public int getSlideMotorEncoderCount() {
		return slideMotor.getEncoder().get();
	}

	public void updatePeriodic() {
		
//		setSlideSpeed((Robot.oi.getHatchSlideLeft()/5)-(Robot.oi.getHatchSlideRight()/5));
//
//		if (Robot.oi.getHatchSlideCentre()) {
//			Scheduler.getInstance().add(new HatchCentreCommand());
//			return;
//		}
		
		// Updates and sets the Solenoids for the hatch mech
		if (Robot.oi.getHatchMechExtend()) {
			pickupSolenoid.set(false);
		}
		else if (Robot.oi.getHatchMechRetract()) {
			pickupSolenoid.set(true);
		}
		
		if (Robot.oi.getHatchMechEject()) {
			punchSolenoid.set(true);
		}
		else
		{
			punchSolenoid.set(false);
		}
		
		SmartDashboard.putNumber("Slide Motor", slideMotor.get());
		SmartDashboard.putNumber("Slide Encoder Count", getSlideMotorEncoderCount());
		SmartDashboard.putBoolean("Top left Solenoid Extended", pickupSolenoid.get());
		SmartDashboard.putBoolean("Punch Solenoid 2 Extended", punchSolenoid.get());
		
	}
}