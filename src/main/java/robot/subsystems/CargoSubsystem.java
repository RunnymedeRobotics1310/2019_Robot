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
 */
public class CargoSubsystem extends TSubsystem {

    TCanSpeedController armMotor = new TCanSpeedController(RobotMap.ARM_CAN_SPEED_CONTROLLER_TYPE,RobotMap.ARM_CAN_SPEED_CONTROLLER_ADDRESS);
    TLimitSwitch armDownLimit = new TLimitSwitch(RobotMap.ARM_DOWN_LIMIT_SWITCH, DefaultState.TRUE);
    TEncoder armEncoder = armMotor.getEncoder();
    TLimitSwitch armUpLimit = new TLimitSwitch(RobotMap.ARM_UP_LIMIT_SWITCH, DefaultState.TRUE);
    
    int currentArmLevel = 0;
    
    @Override
    public void init() {
    };

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DefaultArmCommand());
    }

    public void setArmSpeed (double armSpeed){
    	armMotor.set(armSpeed);
    	if (armSpeed < 0) {
    		if (armUpLimit.atLimit()) {
    			armMotor.set(0);
    		}
    		else {
    			armMotor.set(armSpeed);
    		}
    	}
    	else if (armSpeed > 0) {
    		if (armDownLimit.atLimit()) {
    			armMotor.set(0);
    		}
    		else {
    			armMotor.set(armSpeed);
    		}
    	}
    	else {
    		armMotor.set(0);
    	}
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

    	// Monitor for limits
    	// This is done in case a command starts the motor and 
    	// does not update the motor speed at the end of the command
    	double armSpeed = armMotor.get();
    	if (armSpeed < 0 && armUpLimit.atLimit()) {
    		armMotor.set(0);
    	}
    	if (armSpeed > 0 && armDownLimit.atLimit()) {
    		armMotor.set(0);
    	}
    	
         SmartDashboard.putNumber("Arm Motor", armMotor.get());
         SmartDashboard.putBoolean("Arm Down", armDownLimit.atLimit());
         SmartDashboard.putBoolean("Arm Up", armUpLimit.atLimit());
         SmartDashboard.putNumber("Arm Encoder",armEncoder.get());
    }

}