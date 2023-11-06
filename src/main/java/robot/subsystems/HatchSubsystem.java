package robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import robot.Constants.HatchConstants;
import robot.RobotMap;

/**
 * Subsystem for the hatch mechanism. Involves the belt slider and pneumatic placement/grabbing
 * mechanisim.
 */
public class HatchSubsystem extends SubsystemBase {

    private static final boolean EXTEND_HATCH_PICKUP = true;
    private static final boolean EJECT_HATCH         = true;

    TalonSRX                     slideMotor          = new TalonSRX(
        HatchConstants.SLIDE_MOTOR_CAN_ADDRESS);

    LimitSwitch                  slideLeftLimit      = new LimitSwitch(
        new DigitalInput(HatchConstants.SLIDE_LEFT_LIMIT_SWITCH_DIO_PORT),
        HatchConstants.SLIDE_LEFT_LIMIT_DEFAULT_STATE);

    LimitSwitch                  slideRightLimit     = new LimitSwitch(
        new DigitalInput(HatchConstants.SLIDE_RIGHT_LIMIT_SWITCH_DIO_PORT),
        HatchConstants.SLIDE_RIGHT_LIMIT_DEFAULT_STATE);

    Solenoid                     pickupSolenoid      = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.HATCH_PICKUP_SOLENOID);
    Solenoid                     rightPunchSolenoid  = new Solenoid(PneumaticsModuleType.CTREPCM,
        RobotMap.HATCH_PUNCH_SOLENOID_RIGHT);
    Solenoid                     leftPunchSolenoid   = new Solenoid(PneumaticsModuleType.CTREPCM,
        RobotMap.HATCH_PUNCH_SOLENOID_LEFT);

    double                       slideMotorSpeed     = 0;
    int                          slideEncoderOffset  = 0;

    public HatchSubsystem() {

        slideMotor.setNeutralMode(NeutralMode.Brake);
        slideMotor.setInverted(HatchConstants.SLIDE_MOTOR_ISINVERTED);

        // Assume that the slide starts in the center
        slideMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        slideEncoderOffset = (int) Math.round(slideMotor.getSelectedSensorPosition());

        pickupSolenoid.set(!EXTEND_HATCH_PICKUP);
        rightPunchSolenoid.set(!EJECT_HATCH);
        leftPunchSolenoid.set(!EJECT_HATCH);
    }

    public void setSlideSpeed(double slideSpeed) {

        slideMotorSpeed = slideSpeed;

        if (slideSpeed > 0 && (!leftSlideLimitDetected())) {
            slideMotor.set(ControlMode.PercentOutput, slideSpeed);
        }
        else if (slideSpeed < 0 && (!rightSlideLimitDetected())) {
            slideMotor.set(ControlMode.PercentOutput, slideSpeed);
        }
        else {
            slideMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    public boolean isCentered() {
        if (Math.abs(getSlideEncoder()) < 200) {
            return true;
        }
        return false;
    }

    public void resetEncoder() {
        slideEncoderOffset = (int) Math.round(slideMotor.getSelectedSensorPosition());
    }

    public void extendPunchMech() {
        rightPunchSolenoid.set(EJECT_HATCH);
        leftPunchSolenoid.set(EJECT_HATCH);
    }

    public void extendPunchMechRight() {
        rightPunchSolenoid.set(EJECT_HATCH);
    }

    public void extendPunchMechLeft() {
        leftPunchSolenoid.set(EJECT_HATCH);
    }

    public void retractPunchMech() {
        rightPunchSolenoid.set(!EJECT_HATCH);
        leftPunchSolenoid.set(!EJECT_HATCH);
    }

    public void extendHatchMech() {
        pickupSolenoid.set(EXTEND_HATCH_PICKUP);
    }

    public void retractHatchMech() {
        pickupSolenoid.set(!EXTEND_HATCH_PICKUP);
    }

    public boolean leftSlideLimitDetected() {
        return slideLeftLimit.atLimit();
    }

    public boolean rightSlideLimitDetected() {
        return slideRightLimit.atLimit();
    }

    public void setSlideEncoder(int value) {
        slideEncoderOffset = 0;
        slideEncoderOffset = -getSlideEncoder() + value;
    }

    public int getSlideEncoder() {

        int encoderCounts = (int) Math.round(slideMotor.getSelectedSensorPosition());

        if (HatchConstants.SLIDE_ENCODER_ISINVERTED) {
            encoderCounts *= -1;
        }

        return encoderCounts + slideEncoderOffset;
    }

    @Override
    public void periodic() {

        if (slideLeftLimit.atLimit()) {
            setSlideEncoder(HatchConstants.SLIDE_LEFT_LIMIT_ENCODER_COUNT);
            if (slideMotorSpeed > 0) {
                setSlideSpeed(0);
            }
        }

        if (slideRightLimit.atLimit()) {
            setSlideEncoder(HatchConstants.SLIDE_RIGHT_LIMIT_ENCODER_COUNT);
            if (slideMotorSpeed < 0) {
                setSlideSpeed(0);
            }
        }
        SmartDashboard.putNumber("Slide Motor", slideMotorSpeed);
        SmartDashboard.putNumber("Slide Encoder Count", getSlideEncoder());
        SmartDashboard.putBoolean("Top left Solenoid Extended", pickupSolenoid.get());
        SmartDashboard.putBoolean("Punch Solenoid 2 Extended", rightPunchSolenoid.get());
        SmartDashboard.putBoolean("Left Slide Limit", slideLeftLimit.atLimit());
        SmartDashboard.putBoolean("right Slide Limit", slideRightLimit.atLimit());
    }

    public void stop() {

        // Stop all of the motors
        setSlideSpeed(0);
    }

    @Override
    public String toString() {

        // Create an appropriate text readable string describing the state of the subsystem
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName());
        sb.append(" slide");
        if (leftSlideLimitDetected()) {
            sb.append(" LEFT");
        }
        if (rightSlideLimitDetected()) {
            sb.append(" RIGHT");
        }
        sb.append(" speed ").append(slideMotorSpeed).append(" pos ").append(getSlideEncoder());

        if (pickupSolenoid.get()) {
            sb.append(" PICKUP EXTENDED");
        }

        if (rightPunchSolenoid.get() || leftPunchSolenoid.get()) {
            sb.append(" PUNCH");
            if (leftPunchSolenoid.get()) {
                sb.append(" L");
            }
            if (rightPunchSolenoid.get()) {
                sb.append(" R");
            }
        }

        return sb.toString();
    }

}