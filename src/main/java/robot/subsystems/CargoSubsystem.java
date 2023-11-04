package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch.DefaultState;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import robot.RobotConst;
import robot.RobotContainer;
import robot.RobotMap;

/**
 * Subsystem for arm mechanism.
 * 64 encoder counts per revolution, approx. 10 counts / degree
 * 60 revolutions = 1 full 360 degree arm turn, 1 revolution = 6 degrees
 */

public class CargoSubsystem extends SubsystemBase {

    TCanSpeedController armMotor               = new TCanSpeedController(RobotMap.ARM_CAN_SPEED_CONTROLLER_TYPE,
        RobotMap.ARM_CAN_SPEED_CONTROLLER_ADDRESS, RobotMap.ARM_CAN_MOTOR_ISINVERTED);

    TLimitSwitch        armDownLimit           = new TLimitSwitch(RobotMap.ARM_DOWN_LIMIT_SWITCH, DefaultState.TRUE);
    TEncoder            armEncoder             = armMotor.getEncoder();
    TLimitSwitch        armUpLimit             = new TLimitSwitch(RobotMap.ARM_UP_LIMIT_SWITCH, DefaultState.TRUE);

    TCanSpeedController leftIntakeMotor        = new TCanSpeedController(
        RobotMap.INTAKE_L_CAN_SPEED_CONTROLLER_TYPE, RobotMap.INTAKE_L_CAN_SPEED_CONTROLLER_ADDRESS,
        RobotMap.INTAKE_L_CAN_MOTOR_ISINVERTED);
    TCanSpeedController rightIntakeMotor       = new TCanSpeedController(
        RobotMap.INTAKE_R_CAN_SPEED_CONTROLLER_TYPE, RobotMap.INTAKE_R_CAN_SPEED_CONTROLLER_ADDRESS,
        RobotMap.INTAKE_R_CAN_MOTOR_ISINVERTED);
    TLimitSwitch        cargoDetectLimitSwitch = new TLimitSwitch(RobotMap.CARGO_DETECT_LIMIT_DIO_PORT, DefaultState.TRUE);

//	TCanSpeedController rollerMotor = new TCanSpeedController(
//    		RobotMap.ROLLER_CAN_SPEED_CONTROLLER_TYPE,RobotMap.ROLLER_CAN_SPEED_CONTROLLER_ADDRESS, RobotMap.ROLLER_CAN_MOTOR_ISINVERTED);


    public CargoSubsystem() {
        resetToStartingPos();
    };

    public void resetToStartingPos() {
        armEncoder.set(1140);
        RobotContainer.oi.setArmLevel(getCurrentLevel());
    }

    public int getEncoderCounts() {
        return armEncoder.get();
    }

    public double getCurrentLevel() {

        double encoderCounts = armEncoder.get();

        for (int i = 0; i < RobotConst.ARM_LEVELS.length; i++) {
            if (encoderCounts < RobotConst.ARM_LEVELS[i] - RobotConst.ARM_TOLERANCE) {
                return i - 0.5;
            }
            if (encoderCounts < RobotConst.ARM_LEVELS[i] + RobotConst.ARM_TOLERANCE) {
                return i;
            }
        }
        return RobotConst.ARM_LEVELS.length - 1 + 0.5;
    }

    public void setArmSpeed(double armSpeed) {
        double armAngle           = (Math.PI * (armEncoder.get() - 500) / 11) / 180;

        double calculatedArmSpeed = armSpeed + Math.cos(armAngle) * 0.07;


        if (calculatedArmSpeed > 0) {
            if (armUpLimit.atLimit()) {
                armMotor.set(0);
                System.out.println("AT UPPER LIMT");
            }
            else {
                armMotor.set(calculatedArmSpeed);
            }
        }
        else if (calculatedArmSpeed < 0) {
            if (armDownLimit.atLimit()) {
                armMotor.set(0);
                System.out.println("AT LOWER LIMT");
            }
            else {
                armMotor.set(calculatedArmSpeed);
            }
        }
        else {
            armMotor.set(0);
            System.out.println("DEFAULT");
        }
    }

    public boolean armDownLimitDetected() {
        return armDownLimit.atLimit();
    }

    public boolean armUpLimitDetected() {
        return armUpLimit.atLimit();
    }

    public void startIntake() {
        leftIntakeMotor.set(RobotConst.INTAKE_SPEED);
        rightIntakeMotor.set(-RobotConst.INTAKE_SPEED);
    }

    public void stopIntake() {
        leftIntakeMotor.set(0);
        rightIntakeMotor.set(0);
    }

    public void ejectCargo(boolean fast) {
        if (fast) {
            leftIntakeMotor.set(-1);
            rightIntakeMotor.set(1);
        }
        else {
            leftIntakeMotor.set(-0.5);
            rightIntakeMotor.set(0.5);
        }
    }

    public boolean isCargoDetected() {
        return cargoDetectLimitSwitch.atLimit();
    }

    public void rollerActive() {
//		rollerMotor.set(0.5);
    }

    public void rollerInactive() {
//		rollerMotor.set(0);
    }

    public void resetEncoder() {
        armEncoder.reset();
    }

    // Periodically update the dashboard and any PIDs or sensors
    @Override
    public void updatePeriodic() {

        // Monitor for limits
        // This is done in case a command starts the motor and
        // does not update the motor speed at the end of the command
        double armSpeed = armMotor.get();
        if (armSpeed > 0 && armUpLimit.atLimit()) {
            armMotor.set(0);
        }
        if (armSpeed < 0 && armDownLimit.atLimit()) {
            armMotor.set(0);
        }

        if (armDownLimitDetected()) {
            armEncoder.reset();
        }



        SmartDashboard.putNumber("Arm Motor", armMotor.get());
        SmartDashboard.putBoolean("Arm Down", armDownLimit.atLimit());
        SmartDashboard.putBoolean("Arm Up", armUpLimit.atLimit());
        SmartDashboard.putNumber("Arm Encoder", armEncoder.get());
        SmartDashboard.putNumber("Current Arm Level", getCurrentLevel());
    }

}