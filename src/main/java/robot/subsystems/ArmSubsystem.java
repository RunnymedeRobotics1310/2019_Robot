package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TCanSparkEncoder;
import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch.DefaultState;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.arm.DefaultArmCommand;

/**
 * Subsystem for arm mechanism.
 * 64 encoder counts per revolution
 * 60 revolutions = 1 full 360 degree arm turn, 1 revolution = 6 degrees
 * max arm thing = ~ 0.6 arm turn / ~ 216 degrees / ~ 36 revolutions / ~ 2304 encoder counts
 * Level 0 = 0 enc ; Level 1 = ? ; Level 2 = ? ; Level 3 = ? ; Level 4 = ? ; Level 5 = ~ 2304 enc
 */
public class ArmSubsystem extends TSubsystem {

    TCanSpeedController armMotor = new TCanSpeedController(RobotMap.ARM_CAN_SPEED_CONTROLLER_TYPE,RobotMap.ARM_CAN_SPEED_CONTROLLER_ADDRESS);
    TLimitSwitch armDownLimit = new TLimitSwitch(RobotMap.ARM_DOWN_LIMIT_SWITCH, DefaultState.TRUE);
    TEncoder armEncoder = armMotor.getEncoder();
    TLimitSwitch armUpLimit = new TLimitSwitch(RobotMap.ARM_UP_LIMIT_SWITCH, DefaultState.TRUE);
    
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
    
    public boolean armDownLimitDetected() {
    	return armDownLimit.atLimit();
    }
    
    public boolean armUpLimitDetected() {
    	return armUpLimit.atLimit();
    }
    
    public double getCurrentLevel() {
    	return armMotor.get();
    }

    // Periodically update the dashboard and any PIDs or sensors
    @Override
    public void updatePeriodic() {

         SmartDashboard.putNumber("Arm Motor", armMotor.get());
         SmartDashboard.putBoolean("Arm Down", armDownLimit.atLimit());
         SmartDashboard.putBoolean("Arm Up", armUpLimit.atLimit());
         SmartDashboard.putNumber("Arm Encoder",armEncoder.get());
    }

}