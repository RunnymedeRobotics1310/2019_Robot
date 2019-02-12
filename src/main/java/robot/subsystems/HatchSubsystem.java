package robot.subsystems;

import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch.DefaultState;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TSpeedController;
import com.torontocodingcollective.subsystem.TSubsystem;

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
	TLimitSwitch leftSlideLimit = new TLimitSwitch(RobotMap.HATCH_LEFT_LIMIT_SWITCH, DefaultState.TRUE);
	TLimitSwitch rightSlideLimit = new TLimitSwitch(RobotMap.HATCH_RIGHT_LIMIT_SWITCH, DefaultState.TRUE);
	GhostSolenoid topLeftSolenoid = new GhostSolenoid(RobotMap.HATCH_TOP_LEFT_SOLENOID);//Testing
	GhostSolenoid bottomLeftSolenoid = new GhostSolenoid(RobotMap.HATCH_BOTTOM_LEFT_SOLENOID);
	GhostSolenoid topRightSolenoid = new GhostSolenoid(RobotMap.HATCH_TOP_RIGHT_SOLENOID);
	GhostSolenoid bottomRightSolenoid = new GhostSolenoid(RobotMap.HATCH_BOTTOM_RIGHT_SOLENOID);
	GhostSolenoid punchSolenoid1 =  new GhostSolenoid(RobotMap.HATCH_PUNCH_SOLENOID_1);
	GhostSolenoid punchSolenoid2 =new GhostSolenoid( RobotMap.HATCH_PUNCH_SOLENOID_2);
	
	public void init() {
	}

	protected void initDefaultCommand() {
		setDefaultCommand(new DefaultHatchCommand());
	}

	public void setSlideSpeed (double slideSpeed) {
		slideMotor.set(slideSpeed);
	}
	
	public void ejectHatch () {
		punchSolenoid1.set(true);
		punchSolenoid2.set(true);
	}
	
	public void retractPunchMech () {
		punchSolenoid1.set(false);
		punchSolenoid2.set(false);
	}

	public boolean leftSlideLimitDeteceted() {
		return leftSlideLimit.atLimit();
	}

	public boolean rightSlideLimitDeteceted() {
		return rightSlideLimit.atLimit();
	}
	
	public int getSlideMotorEncoderCount() {
		return slideMotor.getEncoder().get();
	}

	public void updatePeriodic() {
		//FIXME
		
		if (Robot.oi.getHatchSlideCentre()) {
			Scheduler.getInstance().add(new HatchCentreCommand());
			return;
		}
		
		// Updates and sets the Solenoids for the hatch mech
		if (Robot.oi.getHatchMechExtend()) {
			topLeftSolenoid.set(true);
			bottomLeftSolenoid.set(true);
			topRightSolenoid.set(true);
			bottomRightSolenoid.set(true);
		}
		else if (Robot.oi.getHatchMechRetract()) {
			topLeftSolenoid.set(false);
			bottomLeftSolenoid.set(false);
			topRightSolenoid.set(false);
			bottomRightSolenoid.set(false);
		}
		
		if (Robot.oi.getHatchMechEject()) {
			Scheduler.getInstance().add(new HatchEjectCommand());
		}
		
		
		SmartDashboard.putNumber("Slide Motor", slideMotor.get());
		SmartDashboard.putNumber("Slide Encoder Count", getSlideMotorEncoderCount());
		SmartDashboard.putBoolean("Top left Solenoid Extended", topLeftSolenoid.get());
		SmartDashboard.putBoolean("Bottom left Solenoid Extended", bottomLeftSolenoid.get());
		SmartDashboard.putBoolean("Top Right Solenoid Extended", topRightSolenoid.get());
		SmartDashboard.putBoolean("bottom Right Solenoid Extended", bottomRightSolenoid.get());
		SmartDashboard.putBoolean("Punch Solenoid 1 Extended", punchSolenoid1.get());
		SmartDashboard.putBoolean("Punch Solenoid 2 Extended", punchSolenoid2.get());
		
	}
}