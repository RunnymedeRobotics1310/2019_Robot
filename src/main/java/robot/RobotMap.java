package robot;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.speedcontroller.TCanSpeedController.TCanSpeedControllerType;

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

    // Solenoid ports
    public static final int						HATCH_PICKUP_SOLENOID;
    public static final int						HATCH_PUNCH_SOLENOID;

    public static final int                     ARM_CAN_SPEED_CONTROLLER_ADDRESS;
    public static final int						ARM_CAN_SPEED_CONTROLLER_2_ADDRESS;
    public static final TCanSpeedControllerType ARM_CAN_SPEED_CONTROLLER_TYPE;
    public static final TCanSpeedControllerType ARM_CAN_SPEED_CONTROLLER_2_TYPE;
    public static final boolean                 ARM_CAN_MOTOR_ISINVERTED;
    public static final boolean                 ARM_CAN_MOTOR_2_ISINVERTED;
    
	public static final int                     INTAKE_L_CAN_SPEED_CONTROLLER_ADDRESS;
	public static final TCanSpeedControllerType INTAKE_L_CAN_SPEED_CONTROLLER_TYPE;
    public static final boolean                 INTAKE_L_CAN_MOTOR_ISINVERTED;
    
	public static final int                     INTAKE_R_CAN_SPEED_CONTROLLER_ADDRESS;
	public static final TCanSpeedControllerType INTAKE_R_CAN_SPEED_CONTROLLER_TYPE;
    public static final boolean                 INTAKE_R_CAN_MOTOR_ISINVERTED;

    public static final int                     ROLLER_CAN_SPEED_CONTROLLER_ADDRESS;
    public static final TCanSpeedControllerType ROLLER_CAN_SPEED_CONTROLLER_TYPE;
    public static final boolean                 ROLLER_CAN_MOTOR_ISINVERTED;
    
    

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
    public static final int                     ARM_DOWN_LIMIT_SWITCH;
    public static final int                     ARM_UP_LIMIT_SWITCH;
	public static final int                     CARGO_DETECT_LIMIT_DIO_PORT;
	
    public static final int					    HATCH_LEFT_LIMIT_SWITCH_DIO_PORT;
    public static final int					    HATCH_RIGHT_LIMIT_SWITCH_DIO_PORT;

    public static final int                     LIFT_FRONT_UPPER_LIMIT_DIO_PORT;
    public static final int                     LIFT_FRONT_LOWER_LIMIT_DIO_PORT;
    public static final int                     LIFT_REAR_UPPER_LIMIT_DIO_PORT;
    public static final int                     LIFT_REAR_LOWER_LIMIT_DIO_PORT;
    public static final int                     LIFT_PLATFORM_DETECT_DIO_PORT;
    public static final int                     LIFT_CENTRE_DETECT_DIO_PORT;

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
        
        case RobotConst.PROD_ROBOT:
        	
            // CAN Constants
            // Talon and Victor connected through the CAN Bus
            LEFT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS           = 10;
            LEFT_DRIVE_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            LEFT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_ADDRESS  = 11;
            LEFT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_TYPE     = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            LEFT_DRIVE_CAN_MOTOR_ISINVERTED                   = TConst.NOT_INVERTED;
            LEFT_DRIVE_CAN_ENCODER_ISINVERTED                 = TConst.NOT_INVERTED;

            RIGHT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS          = 20;
            RIGHT_DRIVE_CAN_SPEED_CONTROLLER_TYPE             = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            RIGHT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_ADDRESS = 21;
            RIGHT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_TYPE    = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            RIGHT_DRIVE_CAN_MOTOR_ISINVERTED                  = TConst.INVERTED;
            RIGHT_DRIVE_CAN_ENCODER_ISINVERTED                = TConst.INVERTED;

            HATCH_SLIDE_CAN_SPEED_CONTROLLER_ADDRESS          = 7;
            HATCH_SLIDE_CAN_SPEED_CONTROLLER_TYPE             = TCanSpeedControllerType.TALON_SRX;
            HATCH_SLIDE_CAN_MOTOR_ISINVERTED                  = TConst.INVERTED;
            HATCH_SLIDE_CAN_ENCODER_ISINVERTED                = TConst.INVERTED;
            HATCH_LEFT_LIMIT_SWITCH_DIO_PORT				  = 2;
            HATCH_RIGHT_LIMIT_SWITCH_DIO_PORT				  = 1;

            HATCH_PICKUP_SOLENOID							  = 1;
            HATCH_PUNCH_SOLENOID							  = 0;
            
            ARM_CAN_SPEED_CONTROLLER_ADDRESS                  = 12;
            ARM_CAN_SPEED_CONTROLLER_2_ADDRESS                = 18;

            ARM_CAN_SPEED_CONTROLLER_TYPE                     = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            ARM_CAN_MOTOR_ISINVERTED                          = TConst.INVERTED;
            ARM_CAN_SPEED_CONTROLLER_2_TYPE                   = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            ARM_CAN_MOTOR_2_ISINVERTED                        = TConst.INVERTED;
    
            ARM_DOWN_LIMIT_SWITCH                             = 9;
            ARM_UP_LIMIT_SWITCH                               = 11;
            
            INTAKE_L_CAN_SPEED_CONTROLLER_ADDRESS             = 16;
            INTAKE_L_CAN_SPEED_CONTROLLER_TYPE                = TCanSpeedControllerType.VICTOR_SPX;
            INTAKE_L_CAN_MOTOR_ISINVERTED                     = TConst.NOT_INVERTED;        
            INTAKE_R_CAN_SPEED_CONTROLLER_ADDRESS             = 15;
            INTAKE_R_CAN_SPEED_CONTROLLER_TYPE                = TCanSpeedControllerType.VICTOR_SPX;
            INTAKE_R_CAN_MOTOR_ISINVERTED                     = TConst.NOT_INVERTED;
            ROLLER_CAN_SPEED_CONTROLLER_ADDRESS               = 17;
            ROLLER_CAN_SPEED_CONTROLLER_TYPE                  = TCanSpeedControllerType.VICTOR_SPX;
            ROLLER_CAN_MOTOR_ISINVERTED                       = TConst.NOT_INVERTED;
            CARGO_DETECT_LIMIT_DIO_PORT                       = 0;
    
            LIFT_FRONT_CAN_SPEED_CONTROLLER_ADDRESS           = 13;
            LIFT_FRONT_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            LIFT_FRONT_CAN_MOTOR_ISINVERTED                   = false;
            LIFT_REAR_CAN_SPEED_CONTROLLER_ADDRESS            = 14;
            LIFT_REAR_CAN_SPEED_CONTROLLER_TYPE               = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            LIFT_REAR_CAN_MOTOR_ISINVERTED                    = false;
            LIFT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS           = 8;
            LIFT_DRIVE_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.VICTOR_SPX;
            LIFT_DRIVE_CAN_MOTOR_ISINVERTED                   = false;
            
            LIFT_FRONT_UPPER_LIMIT_DIO_PORT                   = 3;
            LIFT_FRONT_LOWER_LIMIT_DIO_PORT                   = 7;
            LIFT_REAR_UPPER_LIMIT_DIO_PORT                    = 6;
            LIFT_REAR_LOWER_LIMIT_DIO_PORT                    = 5;
            LIFT_PLATFORM_DETECT_DIO_PORT					  = 4;
            LIFT_CENTRE_DETECT_DIO_PORT						  = 10;
            
            GYRO_PORT       = 0;
            GYRO_ISINVERTED = TConst.NOT_INVERTED;
            
        	break;

        case RobotConst.TEST_ROBOT:
        default:
            // CAN Constants
            // Talon and Victor connected through the CAN Bus
            LEFT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS           = 3;
            LEFT_DRIVE_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.TALON_SRX;
            LEFT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_ADDRESS  = 4;
            LEFT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_TYPE     = TCanSpeedControllerType.VICTOR_SPX;
            LEFT_DRIVE_CAN_MOTOR_ISINVERTED                   = TConst.NOT_INVERTED;
            LEFT_DRIVE_CAN_ENCODER_ISINVERTED                 = TConst.NOT_INVERTED;

            RIGHT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS          = 1;
            RIGHT_DRIVE_CAN_SPEED_CONTROLLER_TYPE             = TCanSpeedControllerType.TALON_SRX;
            RIGHT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_ADDRESS = 2;
            RIGHT_DRIVE_CAN_FOLLOWER_SPEED_CONTROLLER_TYPE    = TCanSpeedControllerType.TALON_SRX;
            RIGHT_DRIVE_CAN_MOTOR_ISINVERTED                  = TConst.INVERTED;
            RIGHT_DRIVE_CAN_ENCODER_ISINVERTED                = TConst.INVERTED;

            HATCH_SLIDE_CAN_SPEED_CONTROLLER_ADDRESS          = 20;
            HATCH_SLIDE_CAN_SPEED_CONTROLLER_TYPE             = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            HATCH_SLIDE_CAN_MOTOR_ISINVERTED                  = TConst.NOT_INVERTED;
            HATCH_SLIDE_CAN_ENCODER_ISINVERTED                = TConst.NOT_INVERTED;
            HATCH_LEFT_LIMIT_SWITCH_DIO_PORT				  = 1;
            HATCH_RIGHT_LIMIT_SWITCH_DIO_PORT				  = 3;
            
            // Solenoid ports
            HATCH_PICKUP_SOLENOID							  = 0;
            HATCH_PUNCH_SOLENOID							  = 1;
            
            ARM_CAN_SPEED_CONTROLLER_ADDRESS                  = 30;
            ARM_CAN_SPEED_CONTROLLER_2_ADDRESS                = 13;

            ARM_CAN_SPEED_CONTROLLER_TYPE                     = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            ARM_CAN_MOTOR_ISINVERTED                          = TConst.NOT_INVERTED;
            ARM_CAN_SPEED_CONTROLLER_2_TYPE                   = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            ARM_CAN_MOTOR_2_ISINVERTED                        = TConst.NOT_INVERTED;
    
            ARM_DOWN_LIMIT_SWITCH                             = 4;
            ARM_UP_LIMIT_SWITCH                               = 5;
            
            INTAKE_L_CAN_SPEED_CONTROLLER_ADDRESS             = 30;
            INTAKE_L_CAN_SPEED_CONTROLLER_TYPE                = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            INTAKE_L_CAN_MOTOR_ISINVERTED                     = TConst.NOT_INVERTED;
            INTAKE_R_CAN_SPEED_CONTROLLER_ADDRESS             = 31;
            INTAKE_R_CAN_SPEED_CONTROLLER_TYPE                = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            INTAKE_R_CAN_MOTOR_ISINVERTED                     = TConst.NOT_INVERTED;
            ROLLER_CAN_SPEED_CONTROLLER_ADDRESS               = 17;
            ROLLER_CAN_SPEED_CONTROLLER_TYPE                  = TCanSpeedControllerType.VICTOR_SPX;
            ROLLER_CAN_MOTOR_ISINVERTED                     = TConst.NOT_INVERTED;
            CARGO_DETECT_LIMIT_DIO_PORT                       = 11;
    
            LIFT_FRONT_CAN_SPEED_CONTROLLER_ADDRESS           = 10;
            LIFT_FRONT_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            LIFT_FRONT_CAN_MOTOR_ISINVERTED                   = true;
            LIFT_REAR_CAN_SPEED_CONTROLLER_ADDRESS            = 12;
            LIFT_REAR_CAN_SPEED_CONTROLLER_TYPE               = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            LIFT_REAR_CAN_MOTOR_ISINVERTED                    = true;
            LIFT_DRIVE_CAN_SPEED_CONTROLLER_ADDRESS           = 11;
            LIFT_DRIVE_CAN_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.SPARK_MAX_BRUSHLESS;
            LIFT_DRIVE_CAN_MOTOR_ISINVERTED                   = false;
            
            LIFT_FRONT_UPPER_LIMIT_DIO_PORT                   = 7;
            LIFT_FRONT_LOWER_LIMIT_DIO_PORT                   = 6;
            LIFT_REAR_UPPER_LIMIT_DIO_PORT                    = 8;
            LIFT_REAR_LOWER_LIMIT_DIO_PORT                    = 9;
            LIFT_PLATFORM_DETECT_DIO_PORT					  = 4;
            LIFT_CENTRE_DETECT_DIO_PORT						  = 10;
            
            GYRO_PORT       = 0;
            GYRO_ISINVERTED = TConst.NOT_INVERTED;
            
        }
    }
}