package robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import robot.Constants.LiftConstants;

/**
 * The Subsystem would hello
 */
public class LiftSubsystem extends SubsystemBase {

    CANSparkMax     frontMotor      = new CANSparkMax(LiftConstants.FRONT_MOTOR_CAN_ADDRESS, MotorType.kBrushless);
    CANSparkMax     rearMotor       = new CANSparkMax(LiftConstants.REAR_MOTOR_CAN_ADDRESS, MotorType.kBrushless);

    VictorSPX       driveMotor      = new VictorSPX(LiftConstants.DRIVE_MOTOR_CAN_ADDRESS);

    RelativeEncoder frontEncoder    = frontMotor.getEncoder();
    RelativeEncoder rearEncoder     = rearMotor.getEncoder();

    LimitSwitch     frontUpperLimit = new LimitSwitch(new DigitalInput(LiftConstants.FRONT_UPPER_LIMIT_DIO_PORT), true);
    LimitSwitch     frontLowerLimit = new LimitSwitch(new DigitalInput(LiftConstants.FRONT_LOWER_LIMIT_DIO_PORT), true);
    LimitSwitch     rearUpperLimit  = new LimitSwitch(new DigitalInput(LiftConstants.REAR_UPPER_LIMIT_DIO_PORT), true);
    LimitSwitch     rearLowerLimit  = new LimitSwitch(new DigitalInput(LiftConstants.REAR_LOWER_LIMIT_DIO_PORT), true);
    LimitSwitch     platformDetect  = new LimitSwitch(new DigitalInput(LiftConstants.PLATFORM_DETECT_DIO_PORT), true);
    LimitSwitch     centreDetect    = new LimitSwitch(new DigitalInput(LiftConstants.CENTER_DETECT_DIO_PORT), true);

    public LiftSubsystem() {

        frontMotor.setInverted(LiftConstants.FRONT_MOTOR_ISINVERTED);
        rearMotor.setInverted(LiftConstants.REAR_MOTOR_ISINVERTED);

        driveMotor.setInverted(LiftConstants.DRIVE_MOTOR_ISINVERTED);
    }

    public boolean getFrontUpperLimit() {
        return frontUpperLimit.atLimit();
    }

    public boolean getFrontlowerLimit() {
        return frontLowerLimit.atLimit();
    }

    public boolean getRearUpperLimit() {
        return rearUpperLimit.atLimit();
    }

    public boolean getRearlowerLimit() {
        return rearLowerLimit.atLimit();
    }

    public boolean getPlatformDetect() {
        return platformDetect.atLimit();
    }

    public boolean getCentreDetect() {
        return centreDetect.atLimit();
    }

    public void setFrontMotorSpeed(double speed) {

        // A negative speed drives the ers down
        if (speed < 0) {
            if (frontLowerLimit.atLimit()) {
                frontMotor.set(0);
            }
            else {
                frontMotor.set(speed);
            }
        }
        else if (speed > 0) {
            if (frontUpperLimit.atLimit()) {
                frontMotor.set(0);
            }
            else {
                frontMotor.set(speed);
            }
        }
        else {
            frontMotor.set(0);
        }
    }

    public void setRearMotorSpeed(double speed) {

        // Ignore speeds < .01

        // A negative speed drives the ers down
        if (speed < 0) {
            if (rearLowerLimit.atLimit()) {
                rearMotor.set(0);
            }
            else {
                rearMotor.set(speed);
            }
        }
        else if (speed > 0) {
            if (rearUpperLimit.atLimit()) {
                rearMotor.set(0);
            }
            else {
                rearMotor.set(speed);
            }
        }
        else {
            rearMotor.set(0);
        }
    }

    public void setDriveMotorSpeed(double speed) {
        driveMotor.set(ControlMode.PercentOutput, speed);
    }

    // Periodically update the dashboard and any PIDs or sensors
    @Override
    public void periodic() {

        if (frontUpperLimit.atLimit()) {
            frontEncoder.setPosition(0);
        }

        if (rearUpperLimit.atLimit()) {
            rearEncoder.setPosition(0);
        }

        // Monitor for limits
        // This is done in case a command starts the motor and
        // does not update the motor speed at the end of the command

        // Put data on the SmartDashboard

        SmartDashboard.putNumber("Lift Front Motor", frontMotor.get());
        SmartDashboard.putNumber("Lift Rear Motor", rearMotor.get());
        SmartDashboard.putNumber("Lift Drive Motor", driveMotor.getMotorOutputPercent());

        SmartDashboard.putNumber("Lift Front Motor Encoder Count", frontEncoder.getPosition());
        SmartDashboard.putNumber("Lift Rear Motor Encoder Count", rearEncoder.getPosition());

        SmartDashboard.putBoolean("Front Up", frontUpperLimit.atLimit());
        SmartDashboard.putBoolean("Front Down", frontLowerLimit.atLimit());
        SmartDashboard.putBoolean("Rear Up", rearUpperLimit.atLimit());
        SmartDashboard.putBoolean("Rear Down", rearLowerLimit.atLimit());

        SmartDashboard.putBoolean("Platform Detected", platformDetect.atLimit());
        SmartDashboard.putBoolean("Centre Detected", centreDetect.atLimit());
    }



}