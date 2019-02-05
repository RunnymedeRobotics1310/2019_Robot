package robot.subsystems;

import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch.DefaultState;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TSpeedController;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.RobotMap;
import robot.commands.hatch.DefaultHatchCommand;

/**
 * Subsystem for the hatch mechanism. Involves the belt slider and pneumatic placement/grabbing mechanisim.
 */
public class HatchSubsystem extends TSubsystem {

	TSpeedController slideMotor = new TCanSpeedController(
			RobotMap.HATCH_SLIDE_CAN_SPEED_CONTROLLER_TYPE,RobotMap.HATCH_SLIDE_CAN_SPEED_CONTROLLER_ADDRESS);
//	TLimitSwitch leftSlideLimit = new TLimitSwitch(RobotMap.HATCH_LEFT_LIMIT_SWITCH, DefaultState.TRUE);
//	TLimitSwitch rightSlideLimit = new TLimitSwitch(RobotMap.HATCH_RIGHT_LIMIT_SWITCH, DefaultState.TRUE);

	public void init() {

	}

	protected void initDefaultCommand() {
		setDefaultCommand(new DefaultHatchCommand());
	}

	public void setSlideSpeed (double slideSpeed) {
		slideMotor.set(slideSpeed);
	}

//	public boolean leftSlideLimitDeteceted() {
//		return leftSlideLimit.atLimit();
//	}
//
//	public boolean rightSlideLimitDeteceted() {
//		return rightSlideLimit.atLimit();
//	}

	public void updatePeriodic() {
		//FIXME
		if (Robot.oi.getHatchSlideLeft()>0) {
				Robot.hatchSubsystem.setSlideSpeed(Robot.oi.getHatchSlideLeft());
		}
		else if (Robot.oi.getHatchSlideLeft()>0) {
				Robot.hatchSubsystem.setSlideSpeed(-Robot.oi.getHatchSlideRight());
		}
		else {
			Robot.hatchSubsystem.setSlideSpeed(0);
		}



		SmartDashboard.putNumber("Slide Motor", slideMotor.get());
	}
}