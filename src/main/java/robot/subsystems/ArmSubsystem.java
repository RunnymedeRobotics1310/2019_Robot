package robot.subsystems;

import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TSpeedController;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.arm.DefaultArmCommand;

/**
 *
 */
public class ArmSubsystem extends TSubsystem {

    // uncomment the compressor to enable pneumatics control
    TSpeedController armMotor = new TCanSpeedController(RobotMap.ARM_CAN_SPEED_CONTROLLER_TYPE,RobotMap.ARM_CAN_SPEED_CONTROLLER_ADDRESS);

    @Override
    public void init() {
    };

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DefaultArmCommand());
    }

    public void setArmSpeed (double armSpeed){
        armMotor.set(armSpeed);
    }

    // Periodically update the dashboard and any PIDs or sensors
    @Override
    public void updatePeriodic() {

         SmartDashboard.putNumber("Arm Motor", armMotor.get());
           
        
    }

}
