package robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import robot.Constants.ArmConstants;
import robot.Constants.IntakeConstants;
import robot.RobotConst;
import robot.RobotContainer;
import robot.RobotMap;

/**
 * Subsystem for arm mechanism.
 * 64 encoder counts per revolution, approx. 10 counts / degree
 * 60 revolutions = 1 full 360 degree arm turn, 1 revolution = 6 degrees
 */

public class CargoSubsystem extends SubsystemBase {

    CANSparkMax     armMotor               = new CANSparkMax(ArmConstants.ARM_MOTOR_CAN_ADDRESS, MotorType.kBrushless);
    RelativeEncoder armEncoder             = armMotor.getEncoder();

    LimitSwitch     armDownLimit           = new LimitSwitch(
        new DigitalInput(RobotMap.ARM_DOWN_LIMIT_SWITCH),
        ArmConstants.ARM_DOWN_LIMIT_DEFAULT_STATE);

    LimitSwitch     armUpLimit             = new LimitSwitch(
        new DigitalInput(RobotMap.ARM_UP_LIMIT_SWITCH),
        ArmConstants.ARM_UP_LIMIT_DEFAULT_STATE);

    VictorSPX       leftIntakeMotor        = new VictorSPX(IntakeConstants.LEFT_INTAKE_MOTOR_CAN_ADDRESS);
    VictorSPX       rightIntakeMotor       = new VictorSPX(IntakeConstants.RIGHT_INTAKE_MOTOR_CAN_ADDRESS);

    LimitSwitch     cargoDetectLimitSwitch = new LimitSwitch(
        new DigitalInput(RobotMap.CARGO_DETECT_LIMIT_DIO_PORT),
        IntakeConstants.CARGO_DETECT_LIMIT_DEFAULT_STATE);

    public CargoSubsystem() {

        armMotor.setIdleMode(IdleMode.kBrake);
        armMotor.setInverted(ArmConstants.ARM_MOTOR_ISINVERTED);
        resetToStartingPos();

        leftIntakeMotor.setNeutralMode(NeutralMode.Brake);
        leftIntakeMotor.setInverted(IntakeConstants.LEFT_INTAKE_MOTOR_ISINVERTED);

        rightIntakeMotor.setNeutralMode(NeutralMode.Brake);
        rightIntakeMotor.setInverted(IntakeConstants.RIGHT_INTAKE_MOTOR_ISINVERTED);
    }

    public void resetToStartingPos() {
        armEncoder.setPosition(1140);
        RobotContainer.oi.setArmLevel(getCurrentLevel());
    }

    public int getEncoderCounts() {
        return (int) Math.round(armEncoder.getPosition());
    }

    public double getCurrentLevel() {

        int encoderCounts = getEncoderCounts();

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

        double armAngle           = (Math.PI * (getEncoderCounts() - 500) / 11.0d) / 180.0d;

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
        leftIntakeMotor.set(ControlMode.PercentOutput, RobotConst.INTAKE_SPEED);
        rightIntakeMotor.set(ControlMode.PercentOutput, -RobotConst.INTAKE_SPEED);
    }

    public void stopIntake() {
        leftIntakeMotor.set(ControlMode.PercentOutput, 0);
        rightIntakeMotor.set(ControlMode.PercentOutput, 0);
    }

    public void ejectCargo(boolean fast) {
        if (fast) {
            leftIntakeMotor.set(ControlMode.PercentOutput, -1);
            rightIntakeMotor.set(ControlMode.PercentOutput, 1);
        }
        else {
            leftIntakeMotor.set(ControlMode.PercentOutput, -.5);
            rightIntakeMotor.set(ControlMode.PercentOutput, .5);
        }
    }

    public boolean isCargoDetected() {
        return cargoDetectLimitSwitch.atLimit();
    }

    public void resetEncoder() {
        armEncoder.setPosition(0);
    }

    // Periodically update the dashboard and any PIDs or sensors
    @Override
    public void periodic() {

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
            resetEncoder();
        }

        SmartDashboard.putNumber("Arm Motor", armMotor.get());
        SmartDashboard.putBoolean("Arm Down", armDownLimit.atLimit());
        SmartDashboard.putBoolean("Arm Up", armUpLimit.atLimit());
        SmartDashboard.putNumber("Arm Encoder", armEncoder.getPosition());
        SmartDashboard.putNumber("Current Arm Level", getCurrentLevel());
    }

}