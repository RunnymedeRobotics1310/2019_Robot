package robot;

public class RobotConst {

    public static final String   TEST_ROBOT                      = "TestRobot";
    public static final String   PROD_ROBOT                      = "ProdRobot";


    // *********************************************************
    // Drive Constants
    // *********************************************************
    // Forward for the elevator is counter-clockwise when looking
    // from the back of the robot towards the front
    public static final double   MAX_LOW_GEAR_SPEED;
    public static final double   MAX_HIGH_GEAR_SPEED;

    public static final double   DRIVE_GYRO_PID_KP;
    public static final double   DRIVE_GYRO_PID_KI;
    public static final double   DRIVE_MAX_ROTATION_OUTPUT       = 0.6;

    public static final double   DRIVE_SPEED_PID_KP;
    public static final double   DRIVE_SPEED_PID_KI;

    public static final double   ENCODER_COUNTS_PER_INCH;


    // *********************************************************
    // Cargo System Constants
    // *********************************************************
    public static final double[] ARM_LEVELS                      = { 0, 480, 860, 1450, 1950, 2200 };
    public static final double   ARM_TOLERANCE                   = 20;

    public static final double   INTAKE_SPEED                    = 0.5;

    // *********************************************************
    // Hatch System Constants
    // *********************************************************

    public static final int      LEFT_HATCH_LIMIT_ENCODER_COUNT  = 1950;
    public static final int      RIGHT_HATCH_LIMIT_ENCODER_COUNT = -2050;

    // *********************************************************
    // For Ultrasonic Calibration
    // *********************************************************
    public static final double   ULTRASONIC_RECESS               = 6.0;
    public static final double   ULTRASONIC_VOLTAGE_20IN         = 0.23;                             // 0.18;
    public static final double   ULTRASONIC_VOLTAGE_40IN         = 0.48;                             // 0.37;
    public static final double   ULTRASONIC_VOLTAGE_80IN         = 0.97;                             // 0.73;

    public static final double   VISION_CENTER_X                 = 320;                              // half
                                                                                                     // of
                                                                                                     // 640x480;

    public static enum Direction {
        FORWARD, BACKWARD
    };

    // The TorontoCodingCollective framework was developed to run on different
    // robots through the use of multiple mappings and constants.
    public static final String robot = PROD_ROBOT;

    static {

        switch (robot) {

        case PROD_ROBOT:

            // The low gear speed should be set just below the
            // maximum loaded speed of the robot
            MAX_LOW_GEAR_SPEED = 320.0; // Encoder counts/sec
            MAX_HIGH_GEAR_SPEED = 900.0;

            // Typically set the integral gain at 1/20 of the
            // proportional gain. The gain can often be increased
            // above this value, but typically gives good
            // stability and acceptable performance
            DRIVE_GYRO_PID_KP = .015;
            DRIVE_GYRO_PID_KI = DRIVE_GYRO_PID_KP / 20.0;

            DRIVE_SPEED_PID_KP = 0.4;
            DRIVE_SPEED_PID_KI = DRIVE_SPEED_PID_KP / 20.0;

            ENCODER_COUNTS_PER_INCH = 43.75;

            break;

        case TEST_ROBOT:
        default:

            // The low gear speed should be set just below the
            // maximum loaded speed of the robot
            MAX_LOW_GEAR_SPEED = 320.0; // Encoder counts/sec
            MAX_HIGH_GEAR_SPEED = 900.0;

            // Typically set the integral gain at 1/20 of the
            // proportional gain. The gain can often be increased
            // above this value, but typically gives good
            // stability and acceptable performance
            DRIVE_GYRO_PID_KP = .07;
            DRIVE_GYRO_PID_KI = DRIVE_GYRO_PID_KP / 20.0;

            DRIVE_SPEED_PID_KP = 0.4;
            DRIVE_SPEED_PID_KI = DRIVE_SPEED_PID_KP / 20.0;

            ENCODER_COUNTS_PER_INCH = 55.6;

            break;
        }

    }
}