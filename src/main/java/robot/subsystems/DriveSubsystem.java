package robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {

    // The motors on the left side of the drive.
    private final CANSparkMax leftPrimaryMotor   = new CANSparkMax(DriveConstants.LEFT_MOTOR_CAN_ADDRESS, MotorType.kBrushless);
    private final CANSparkMax leftFollowerMotor  = new CANSparkMax(DriveConstants.LEFT_MOTOR_CAN_ADDRESS + 1,
        MotorType.kBrushless);

    // The motors on the right side of the drive.
    private final CANSparkMax rightPrimaryMotor  = new CANSparkMax(DriveConstants.RIGHT_MOTOR_CAN_ADDRESS, MotorType.kBrushless);
    private final CANSparkMax rightFollowerMotor = new CANSparkMax(DriveConstants.RIGHT_MOTOR_CAN_ADDRESS + 1,
        MotorType.kBrushless);

    // Motor speeds
    private double            leftSpeed          = 0;
    private double            rightSpeed         = 0;

    /** Creates a new DriveSubsystem. */
    public DriveSubsystem() {

        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        leftPrimaryMotor.setInverted(DriveConstants.LEFT_MOTOR_REVERSED);
        leftFollowerMotor.setInverted(DriveConstants.LEFT_MOTOR_REVERSED);

        leftPrimaryMotor.setIdleMode(IdleMode.kBrake);
        leftFollowerMotor.setIdleMode(IdleMode.kBrake);

        leftFollowerMotor.follow(leftPrimaryMotor);


        rightPrimaryMotor.setInverted(DriveConstants.RIGHT_MOTOR_REVERSED);
        rightFollowerMotor.setInverted(DriveConstants.RIGHT_MOTOR_REVERSED);

        rightPrimaryMotor.setIdleMode(IdleMode.kBrake);
        rightFollowerMotor.setIdleMode(IdleMode.kBrake);

        rightFollowerMotor.follow(rightPrimaryMotor);

    }

    /**
     * Set the left and right speed of the primary and follower motors
     *
     * @param leftSpeed
     * @param rightSpeed
     */
    public void setMotorSpeeds(double leftSpeed, double rightSpeed) {

        this.leftSpeed  = leftSpeed;
        this.rightSpeed = rightSpeed;

        leftPrimaryMotor.set(leftSpeed);
        rightPrimaryMotor.set(rightSpeed);

        // NOTE: The follower motors are set to follow the primary motors
    }

    /** Safely stop the subsystem from moving */
    public void stop() {
        setMotorSpeeds(0, 0);
    }

    @Override
    public void periodic() {

        /*
         * Update all dashboard values in the periodic routine
         */
        SmartDashboard.putNumber("Right Motor", rightSpeed);
        SmartDashboard.putNumber("Left  Motor", leftSpeed);
    }

    @Override
    public String toString() {

        // Create an appropriate text readable string describing the state of the subsystem
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName())
            .append(" [").append(Math.round(leftSpeed * 100.0d) / 100.0d)
            .append(',').append(Math.round(rightSpeed * 100.0d) / 100.0d).append(']');

        return sb.toString();
    }

}
