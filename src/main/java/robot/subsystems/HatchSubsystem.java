package robot.subsystems;

import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TSpeedController;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.hatch.DefaultHatchCommand;

/**
 * Subsystem for the hatch mechanism. Involves the belt slider and pneumatic placement/grabbing mechanisim.
 */
public class HatchSubsystem extends TSubsystem {

    TSpeedController slideMotor = new TCanSpeedController(
    		RobotMap.HATCH_SLIDE_CAN_SPEED_CONTROLLER_TYPE,RobotMap.HATCH_SLIDE_CAN_SPEED_CONTROLLER_ADDRESS);

    public void init() {

    }

    protected void initDefaultCommand() {
        setDefaultCommand(new DefaultHatchCommand());
    }

    public void setSlideSpeed (double slideSpeed) {
        slideMotor.set(slideSpeed);
    }


    public void updatePeriodic() {
        SmartDashboard.putNumber("Slide Motor", slideMotor.get());
    }
}