package robot;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.speedcontroller.TCanSpeedController.TCanSpeedControllerType;

import robot.subsystems.GhostSolenoid;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * <p>
 * This map is intended to define the wiring only. Robot constants should be put
 * in {@link RobotConst}
 */
public class RobotMap {

	// ******************************************
	// Speed Controllers and encoders
	// CAN addresses
	// ******************************************
	public static final int                     LEFT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS;
	public static final TCanSpeedControllerType LEFT_DRIVE_CAN_SPEED_CONTROLLER_TYPE;
	public static final int                     LEFT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_ADDRESS;
	public static final TCanSpeedControllerType LEFT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_TYPE;
	public static final boolean                 LEFT_DRIVE_CAN_MOTOR_ISINVERTED;

	public static final int                     RIGHT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS;
	public static final TCanSpeedControllerType RIGHT_DRIVE_CAN_SPEED_CONTROLLER_TYPE;
	public static final int                     RIGHT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_ADDRESS;
	public static final TCanSpeedControllerType RIGHT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_TYPE;
	public static final boolean                 RIGHT_DRIVE_CAN_MOTOR_ISINVERTED;

	public static final boolean                 LEFT_DRIVE_CAN_ENCODER_ISINVERTED;
	public static final boolean                 RIGHT_DRIVE_CAN_ENCODER_ISINVERTED;

	public static final int                     HATCH_SLIDE_CAN_SPEED_CONTROLLER_ADDRESS;
	public static final TCanSpeedControllerType HATCH_SLIDE_CAN_SPEED_CONTROLLER_TYPE;
	public static final boolean                 HATCH_SLIDE_CAN_MOTOR_ISINVERTED;                   
	public static final boolean                 HATCH_SLIDE_CAN_ENCODER_ISINVERTED;
	public static final int					    HATCH_LEFT_LIMIT_SWITCH_DIO_PORT;
	public static final int					    HATCH_RIGHT_LIMIT_SWITCH_DIO_PORT;

	public static final int						HATCH_TOP_LEFT_SOLENOID;
	public static final int						HATCH_BOTTOM_LEFT_SOLENOID;
	public static final int						HATCH_TOP_RIGHT_SOLENOID;
	public static final int						HATCH_BOTTOM_RIGHT_SOLENOID;
	public static final int						HATCH_PUNCH_SOLENOID_1;
	public static final int						HATCH_PUNCH_SOLENOID_2;

	public static final int                     ARM_CAN_SPEED_CONTROLLER_ADDRESS;
	public static final TCanSpeedControllerType ARM_CAN_SPEED_CONTROLLER_TYPE;
	public static final boolean                 ARM_CAN_MOTOR_ISINVERTED;

	public static final int                     LIFT_FRONT_CAN_SPEED_CONTROLLER_ADDRESS;
	public static final TCanSpeedControllerType LIFT_FRONT_CAN_SPEED_CONTROLLER_TYPE;
	public static final boolean                 LIFT_FRONT_CAN_MOTOR_ISINVERTED;
	public static final int                     LIFT_REAR_CAN_SPEED_CONTROLLER_ADDRESS;
	public static final TCanSpeedControllerType LIFT_REAR_CAN_SPEED_CONTROLLER_TYPE;
	public static final boolean                 LIFT_REAR_CAN_MOTOR_ISINVERTED;
	public static final int                     LIFT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS;
	public static final TCanSpeedControllerType LIFT_DRIVE_CAN_SPEED_CONTROLLER_TYPE;
	public static final boolean                 LIFT_DRIVE_CAN_MOTOR_ISINVERTED;

	// ******************************************
	// DIO Ports
	// ******************************************
	public static final int                    ARM_DOWN_LIMIT_SWITCH;
	public static final int                    ARM_UP_LIMIT_SWITCH;

	public static final int                    LIFT_FRONT_UPPER_LIMIT_DIO_PORT;
	public static final int                    LIFT_FRONT_LOWER_LIMIT_DIO_PORT;
	public static final int                    LIFT_REAR_UPPER_LIMIT_DIO_PORT;
	public static final int                    LIFT_REAR_LOWER_LIMIT_DIO_PORT;

	// ******************************************
	// Gyro Ports
	// ******************************************
	public static final int                     GYRO_PORT;
	public static final boolean                 GYRO_ISINVERTED;

	// ******************************************
	// Pneumatics Ports
	// ******************************************
	public static final int                     SHIFTER_PNEUMATIC_PORT = 0;

	// Initializers if this code will be deployed to more than one
	// robot with different mappings
	static {

		switch (RobotConst.robot) {

		case RobotConst.TEST_ROBOT:
			// CAN Constants
			// Talon and Victor connected through the CAN Bus
			LEFT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS           = 3;
			LEFT_DRIVE_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.TALON_SRX;
			LEFT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_ADDRESS  = 4;
			LEFT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_TYPE     = TCanSpeedControllerType.VICTOR_SPX;
			LEFT_DRIVE_CAN_MOTOR_ISINVERTED                   = TConst.INVERTED;
			LEFT_DRIVE_CAN_ENCODER_ISINVERTED                 = TConst.INVERTED;

			RIGHT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS          = 1;
			RIGHT_DRIVE_CAN_SPEED_CONTROLLER_TYPE             = TCanSpeedControllerType.TALON_SRX;
			RIGHT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_ADDRESS = 2;
			RIGHT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_TYPE    = TCanSpeedControllerType.TALON_SRX;
			RIGHT_DRIVE_CAN_MOTOR_ISINVERTED                  = TConst.NOT_INVERTED;
			RIGHT_DRIVE_CAN_ENCODER_ISINVERTED                = TConst.NOT_INVERTED;

			HATCH_SLIDE_CAN_SPEED_CONTROLLER_ADDRESS          = 10;
			HATCH_SLIDE_CAN_SPEED_CONTROLLER_TYPE             = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			HATCH_SLIDE_CAN_MOTOR_ISINVERTED                  = TConst.NOT_INVERTED;
			HATCH_SLIDE_CAN_ENCODER_ISINVERTED                = TConst.NOT_INVERTED;
			HATCH_LEFT_LIMIT_SWITCH_DIO_PORT				  = 0;
			HATCH_RIGHT_LIMIT_SWITCH_DIO_PORT				  = 6;

			HATCH_TOP_LEFT_SOLENOID							  = 50;
			HATCH_BOTTOM_LEFT_SOLENOID						  = 51;
			HATCH_TOP_RIGHT_SOLENOID						  = 52;
			HATCH_BOTTOM_RIGHT_SOLENOID						  = 53;
			HATCH_PUNCH_SOLENOID_1							  = 54;
			HATCH_PUNCH_SOLENOID_2							  = 55;

			ARM_CAN_SPEED_CONTROLLER_ADDRESS                  = 30;
			ARM_CAN_SPEED_CONTROLLER_TYPE                     = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			ARM_CAN_MOTOR_ISINVERTED                          = TConst.NOT_INVERTED;

			ARM_DOWN_LIMIT_SWITCH                             = 9;
			ARM_UP_LIMIT_SWITCH                               = 8;

			LIFT_FRONT_CAN_SPEED_CONTROLLER_ADDRESS           = 16;
			LIFT_FRONT_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			LIFT_FRONT_CAN_MOTOR_ISINVERTED                   = false;
			LIFT_REAR_CAN_SPEED_CONTROLLER_ADDRESS            = 12;
			LIFT_REAR_CAN_SPEED_CONTROLLER_TYPE               = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			LIFT_REAR_CAN_MOTOR_ISINVERTED                    = false;
			LIFT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS           = 17;
			LIFT_DRIVE_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			LIFT_DRIVE_CAN_MOTOR_ISINVERTED                   = false;

			LIFT_FRONT_UPPER_LIMIT_DIO_PORT                   = 2;
			LIFT_FRONT_LOWER_LIMIT_DIO_PORT                   = 3;
			LIFT_REAR_UPPER_LIMIT_DIO_PORT                    = 4;
			LIFT_REAR_LOWER_LIMIT_DIO_PORT                    = 7;

			GYRO_PORT       = 0;
			GYRO_ISINVERTED = TConst.NOT_INVERTED;
			break;
		case RobotConst.PROD_ROBOT:
			// CAN Constants
			// Talon and Victor connected through the CAN Bus
            LEFT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS           = 10;
            LEFT_DRIVE_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            LEFT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_ADDRESS  = 11;
            LEFT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_TYPE     = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            LEFT_DRIVE_CAN_MOTOR_ISINVERTED                   = TConst.INVERTED;
            LEFT_DRIVE_CAN_ENCODER_ISINVERTED                 = TConst.INVERTED;

            RIGHT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS          = 20;
            RIGHT_DRIVE_CAN_SPEED_CONTROLLER_TYPE             = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            RIGHT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_ADDRESS = 21;
            RIGHT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_TYPE    = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            RIGHT_DRIVE_CAN_MOTOR_ISINVERTED                  = TConst.NOT_INVERTED;
            RIGHT_DRIVE_CAN_ENCODER_ISINVERTED                = TConst.NOT_INVERTED;
            
			HATCH_SLIDE_CAN_SPEED_CONTROLLER_ADDRESS          = 7;
			HATCH_SLIDE_CAN_SPEED_CONTROLLER_TYPE             = TCanSpeedControllerType.TALON_SRX;
			HATCH_SLIDE_CAN_MOTOR_ISINVERTED                  = TConst.NOT_INVERTED;
			HATCH_SLIDE_CAN_ENCODER_ISINVERTED                = TConst.NOT_INVERTED;
			HATCH_LEFT_LIMIT_SWITCH_DIO_PORT				  = 0;
			HATCH_RIGHT_LIMIT_SWITCH_DIO_PORT				  = 6;

			HATCH_TOP_LEFT_SOLENOID							  = 50;
			HATCH_BOTTOM_LEFT_SOLENOID						  = 51;
			HATCH_TOP_RIGHT_SOLENOID						  = 52;
			HATCH_BOTTOM_RIGHT_SOLENOID						  = 53;
			HATCH_PUNCH_SOLENOID_1							  = 54;
			HATCH_PUNCH_SOLENOID_2							  = 55;

			ARM_CAN_SPEED_CONTROLLER_ADDRESS                  = 30;
			ARM_CAN_SPEED_CONTROLLER_TYPE                     = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			ARM_CAN_MOTOR_ISINVERTED                          = TConst.NOT_INVERTED;

			ARM_DOWN_LIMIT_SWITCH                             = 9;
			ARM_UP_LIMIT_SWITCH                               = 8;

			LIFT_FRONT_CAN_SPEED_CONTROLLER_ADDRESS           = 16;
			LIFT_FRONT_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			LIFT_FRONT_CAN_MOTOR_ISINVERTED                   = false;
			LIFT_REAR_CAN_SPEED_CONTROLLER_ADDRESS            = 12;
			LIFT_REAR_CAN_SPEED_CONTROLLER_TYPE               = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			LIFT_REAR_CAN_MOTOR_ISINVERTED                    = false;
			LIFT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS           = 17;
			LIFT_DRIVE_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			LIFT_DRIVE_CAN_MOTOR_ISINVERTED                   = false;

			LIFT_FRONT_UPPER_LIMIT_DIO_PORT                   = 2;
			LIFT_FRONT_LOWER_LIMIT_DIO_PORT                   = 3;
			LIFT_REAR_UPPER_LIMIT_DIO_PORT                    = 4;
			LIFT_REAR_LOWER_LIMIT_DIO_PORT                    = 7;

			GYRO_PORT       = 0;
			GYRO_ISINVERTED = TConst.NOT_INVERTED;
			break;
		default:
			// CAN Constants
			// Talon and Victor connected through the CAN Bus
			LEFT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS           = 3;
			LEFT_DRIVE_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.TALON_SRX;
			LEFT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_ADDRESS  = 4;
			LEFT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_TYPE     = TCanSpeedControllerType.VICTOR_SPX;
			LEFT_DRIVE_CAN_MOTOR_ISINVERTED                   = TConst.INVERTED;
			LEFT_DRIVE_CAN_ENCODER_ISINVERTED                 = TConst.INVERTED;

			RIGHT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS          = 1;
			RIGHT_DRIVE_CAN_SPEED_CONTROLLER_TYPE             = TCanSpeedControllerType.TALON_SRX;
			RIGHT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_ADDRESS = 2;
			RIGHT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_TYPE    = TCanSpeedControllerType.TALON_SRX;
			RIGHT_DRIVE_CAN_MOTOR_ISINVERTED                  = TConst.NOT_INVERTED;
			RIGHT_DRIVE_CAN_ENCODER_ISINVERTED                = TConst.NOT_INVERTED;

			HATCH_SLIDE_CAN_SPEED_CONTROLLER_ADDRESS          = 10;
			HATCH_SLIDE_CAN_SPEED_CONTROLLER_TYPE             = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			HATCH_SLIDE_CAN_MOTOR_ISINVERTED                  = TConst.NOT_INVERTED;
			HATCH_SLIDE_CAN_ENCODER_ISINVERTED                = TConst.NOT_INVERTED;
			HATCH_LEFT_LIMIT_SWITCH_DIO_PORT				  = 0;
			HATCH_RIGHT_LIMIT_SWITCH_DIO_PORT				  = 6;

			HATCH_TOP_LEFT_SOLENOID							  = 50;
			HATCH_BOTTOM_LEFT_SOLENOID						  = 51;
			HATCH_TOP_RIGHT_SOLENOID						  = 52;
			HATCH_BOTTOM_RIGHT_SOLENOID						  = 53;
			HATCH_PUNCH_SOLENOID_1							  = 54;
			HATCH_PUNCH_SOLENOID_2							  = 55;

			ARM_CAN_SPEED_CONTROLLER_ADDRESS                  = 30;

			ARM_CAN_SPEED_CONTROLLER_TYPE                     = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			ARM_CAN_MOTOR_ISINVERTED                          = TConst.NOT_INVERTED;

			ARM_DOWN_LIMIT_SWITCH                             = 9;
			ARM_UP_LIMIT_SWITCH                               = 8;

			LIFT_FRONT_CAN_SPEED_CONTROLLER_ADDRESS           = 16;
			LIFT_FRONT_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			LIFT_FRONT_CAN_MOTOR_ISINVERTED                   = false;
			LIFT_REAR_CAN_SPEED_CONTROLLER_ADDRESS            = 12;
			LIFT_REAR_CAN_SPEED_CONTROLLER_TYPE               = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			LIFT_REAR_CAN_MOTOR_ISINVERTED                    = false;
			LIFT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS           = 17;
			LIFT_DRIVE_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
			LIFT_DRIVE_CAN_MOTOR_ISINVERTED                   = false;

			LIFT_FRONT_UPPER_LIMIT_DIO_PORT                   = 2;
			LIFT_FRONT_LOWER_LIMIT_DIO_PORT                   = 3;
			LIFT_REAR_UPPER_LIMIT_DIO_PORT                    = 4;
			LIFT_REAR_LOWER_LIMIT_DIO_PORT                    = 7;

			GYRO_PORT       = 0;
			GYRO_ISINVERTED = TConst.NOT_INVERTED;
		}
	}
}