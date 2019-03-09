package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch.DefaultState;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.lift.DefaultLiftCommand;

/**
 * The Lift Subsystem would hello
 */
public class LiftSubsystem extends TSubsystem {

	TCanSpeedController frontLiftMotor = new TCanSpeedController(
			RobotMap.LIFT_FRONT_CAN_SPEED_CONTROLLER_TYPE,
			RobotMap.LIFT_FRONT_CAN_SPEED_CONTROLLER_ADDRESS,
			RobotMap.LIFT_FRONT_CAN_MOTOR_ISINVERTED);

	TCanSpeedController rearLiftMotor = new TCanSpeedController(
			RobotMap.LIFT_REAR_CAN_SPEED_CONTROLLER_TYPE,
			RobotMap.LIFT_REAR_CAN_SPEED_CONTROLLER_ADDRESS,
			RobotMap.LIFT_REAR_CAN_MOTOR_ISINVERTED);

	TCanSpeedController liftDriveMotor = new TCanSpeedController(
			RobotMap.LIFT_DRIVE_CAN_SPEED_CONTROLLER_TYPE,
			RobotMap.LIFT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS,
			RobotMap.LIFT_DRIVE_CAN_MOTOR_ISINVERTED);

	 TEncoder frontLiftEncoder = frontLiftMotor.getEncoder();
	 TEncoder rearLiftEncoder = rearLiftMotor.getEncoder();
	
	TLimitSwitch frontLiftUpperLimit = new TLimitSwitch(RobotMap.LIFT_FRONT_UPPER_LIMIT_DIO_PORT, DefaultState.TRUE);
	TLimitSwitch frontLiftLowerLimit = new TLimitSwitch(RobotMap.LIFT_FRONT_LOWER_LIMIT_DIO_PORT, DefaultState.TRUE);
	TLimitSwitch rearLiftUpperLimit = new TLimitSwitch(RobotMap.LIFT_REAR_UPPER_LIMIT_DIO_PORT, DefaultState.TRUE);
	TLimitSwitch rearLiftLowerLimit = new TLimitSwitch(RobotMap.LIFT_REAR_LOWER_LIMIT_DIO_PORT, DefaultState.TRUE);
	TLimitSwitch platformDetect = new TLimitSwitch(RobotMap.LIFT_PLATFORM_DETECT_DIO_PORT, DefaultState.TRUE);
	TLimitSwitch centreDetect = new TLimitSwitch(RobotMap.LIFT_CENTRE_DETECT_DIO_PORT, DefaultState.TRUE);

	@Override
	public void init() {
	};

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DefaultLiftCommand());
	}
	
	public TEncoder getFrontLiftEncoder() {
		return frontLiftEncoder;
	}
	
	public TEncoder getRearLiftEncoder() {
		return rearLiftEncoder;
	}
	
	public boolean getFrontLiftUpperLimit() {
		return frontLiftUpperLimit.atLimit();
	}
	public boolean getFrontLiftlowerLimit() {
		return frontLiftLowerLimit.atLimit();
	}
	public boolean getRearLiftUpperLimit() {
		return rearLiftUpperLimit.atLimit();
	}
	public boolean getRearLiftlowerLimit() {
		return rearLiftLowerLimit.atLimit();
	}
	public boolean getPlatformDetect() {
		return platformDetect.atLimit();
	}
	
	public boolean getCentreDetect() {
		return centreDetect.atLimit();
	}

	public void setFrontMotorSpeed(double speed) {

		// A negative speed drives the lifters down
		if (speed < 0) {
			if (frontLiftLowerLimit.atLimit()) {
				frontLiftMotor.set(0);
			} else {
				frontLiftMotor.set(speed);
			}
		} else if (speed > 0) {
			if (frontLiftUpperLimit.atLimit()) {
				frontLiftMotor.set(0);
			} else {
				frontLiftMotor.set(speed);
			}
		}else {
			frontLiftMotor.set(0);
		}
	}

	public void setRearMotorSpeed(double speed) {

		// Ignore speeds < .01

		// A negative speed drives the lifters down
		if (speed < 0) {
			if (rearLiftLowerLimit.atLimit()) {
				rearLiftMotor.set(0);
			} else {
				rearLiftMotor.set(speed);
			}
		} else if (speed > 0) {
			if (rearLiftUpperLimit.atLimit()) {
				rearLiftMotor.set(0);
			} else {
				rearLiftMotor.set(speed);
			}
		} else {
			rearLiftMotor.set(0);
		}
	}

	public void setDriveMotorSpeed(double speed) {
		// Ignore speeds < .01
		if (speed > 0) {
			liftDriveMotor.set(speed);
		} else if (speed < 0) {
			liftDriveMotor.set(speed);
		} else {
			liftDriveMotor.set(0);
		}
	}

	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {
		
		if (frontLiftUpperLimit.atLimit()) {
			frontLiftEncoder.reset();
		}

		if (rearLiftUpperLimit.atLimit()) {
			rearLiftEncoder.reset();
		}

		// Monitor for limits
		// This is done in case a command starts the motor and 
		// does not update the motor speed at the end of the command

		// Put data on the SmartDashboard

		SmartDashboard.putNumber ("Front Lift Motor", frontLiftMotor.get());
		SmartDashboard.putNumber ("Rear  Lift Motor", rearLiftMotor.get());
		SmartDashboard.putNumber ("Lift Drive Motor", liftDriveMotor.get());
		
		SmartDashboard.putNumber ("Front Lift Motor Encoder Count", frontLiftEncoder.get());
		SmartDashboard.putNumber ("Rear  Lift Motor Encoder Count", rearLiftEncoder.get());

		SmartDashboard.putBoolean("Front Up",   frontLiftUpperLimit.atLimit());
		SmartDashboard.putBoolean("Front Down", frontLiftLowerLimit.atLimit());
		SmartDashboard.putBoolean("Rear Up",    rearLiftUpperLimit.atLimit());
		SmartDashboard.putBoolean("Rear Down",  rearLiftLowerLimit.atLimit());
		
		SmartDashboard.putBoolean("Platform Detected",   platformDetect.atLimit());
		SmartDashboard.putBoolean("Centre Detected",   centreDetect.atLimit());
	}




}