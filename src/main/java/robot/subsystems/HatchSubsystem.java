package robot.subsystems;

import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.commands.pneumatics.DefaultPneumaticsCommand;
import com.torontocodingcollective.speedcontroller.TSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import robot.RobotMap;
import robot.commands.hatch.HorizontalSlideCommand;

/**
 * Subsystem for the hatch mechanism. Involves the belt slider and pneumatic placement/grabbing mechanisim.
 */
public class HatchSubsystem extends TSubsystem {

    TSpeedController slideMotor = new TCanSpeedController(RobotMap.HATCH_SLIDE_CAN_SPEED_CONTROLLER_TYPE,RobotMap.HATCH_SLIDE_CAN_SPEED_CONTROLLER_ADDRESS);

    public void init() {

    }

    protected void initDefaultCommand() {
        setDefaultCommand(new HorizontalSlideCommand());
    }

    public void setSlideSpeed (double slideSpeed) {
        slideMotor.set(slideSpeed);
    }


    public void updatePeriodic() {
        SmartDashboard.putNumber("Slide Motor", slideMotor.get());
    }
}