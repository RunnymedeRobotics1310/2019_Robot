// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    // Global constants
    public static final double DEFAULT_COMMAND_TIMEOUT_SECONDS = 5;

    public enum Side {
        LEFT, RIGHT
    };

    public static final class OperatorConstants {

        public static final int    DRIVER_CONTROLLER_PORT         = 0;
        public static final int    OPERATOR_CONTROLLER_PORT       = 1;
        public static final double GAME_CONTROLLER_STICK_DEADBAND = 0.2;
    }

    public static final class DriveConstants {

        public static enum DriveMode {
            TANK, SINGLE_STICK_ARCADE, DUAL_STICK_ARCADE;
        }

        public static final int     LEFT_MOTOR_CAN_ADDRESS  = 10;
        public static final boolean LEFT_MOTOR_REVERSED     = false;
        public static final boolean LEFT_ENCODER_REVERSED   = false;

        public static final int     RIGHT_MOTOR_CAN_ADDRESS = 20;
        public static final boolean RIGHT_MOTOR_REVERSED    = true;
        public static final boolean RIGHT_ENCODER_REVERSED  = true;

        public static final double  ENCODER_COUNTS_PER_INCH = 43.75;
    }

    public static final class ArmConstants {

        public static final double[] ARM_LEVELS                   = { 0, 480, 860, 1450, 1950, 2200 };
        public static final double   ARM_TOLERANCE                = 20;

        public static final int      ARM_MOTOR_CAN_ADDRESS        = 12;
        public static final boolean  ARM_MOTOR_ISINVERTED         = true;

        public static final int      ARM_DOWN_LIMIT_DIO_PORT      = 9;
        public static final boolean  ARM_DOWN_LIMIT_DEFAULT_STATE = true;

        public static final int      ARM_UP_LIMIT_DIO_PORT        = 8;
        public static final boolean  ARM_UP_LIMIT_DEFAULT_STATE   = true;
    }

    public static final class IntakeConstants {

        public static final double  INTAKE_SPEED                     = 0.5;

        public static final int     LEFT_INTAKE_MOTOR_CAN_ADDRESS    = 16;
        public static final boolean LEFT_INTAKE_MOTOR_ISINVERTED     = false;

        public static final int     RIGHT_INTAKE_MOTOR_CAN_ADDRESS   = 15;
        public static final boolean RIGHT_INTAKE_MOTOR_ISINVERTED    = false;

        public static final int     CARGO_DETECT_LIMIT_DIO_PORT      = 0;
        public static final boolean CARGO_DETECT_LIMIT_DEFAULT_STATE = true;
    }

    public static final class HatchConstants {

        public static final int     SLIDE_MOTOR_CAN_ADDRESS           = 7;
        public static final boolean SLIDE_MOTOR_ISINVERTED            = true;

        public static final boolean SLIDE_ENCODER_ISINVERTED          = true;
        public static final int     SLIDE_LEFT_LIMIT_ENCODER_COUNT    = 1950;
        public static final int     SLIDE_RIGHT_LIMIT_ENCODER_COUNT   = -2050;

        public static final int     SLIDE_LEFT_LIMIT_SWITCH_DIO_PORT  = 2;
        public static final boolean SLIDE_LEFT_LIMIT_DEFAULT_STATE    = true;

        public static final int     SLIDE_RIGHT_LIMIT_SWITCH_DIO_PORT = 1;
        public static final boolean SLIDE_RIGHT_LIMIT_DEFAULT_STATE   = true;

        public static final int     HATCH_PICKUP_SOLENOID             = 0;
        public static final int     HATCH_PUNCH_SOLENOID_RIGHT        = 1;
        public static final int     HATCH_PUNCH_SOLENOID_LEFT         = 2;
    }

    public static final class LiftConstants {

        public static final int     FRONT_MOTOR_CAN_ADDRESS    = 13;
        public static final boolean FRONT_MOTOR_ISINVERTED     = false;

        public static final int     REAR_MOTOR_CAN_ADDRESS     = 14;
        public static final boolean REAR_MOTOR_ISINVERTED      = false;

        public static final int     DRIVE_MOTOR_CAN_ADDRESS    = 8;
        public static final boolean DRIVE_MOTOR_ISINVERTED     = false;

        public static final int     FRONT_UPPER_LIMIT_DIO_PORT = 3;
        public static final int     FRONT_LOWER_LIMIT_DIO_PORT = 7;

        public static final int     REAR_UPPER_LIMIT_DIO_PORT  = 6;
        public static final int     REAR_LOWER_LIMIT_DIO_PORT  = 5;

        public static final int     PLATFORM_DETECT_DIO_PORT   = 4;
        public static final int     CENTER_DETECT_DIO_PORT     = 10;
    }

}
