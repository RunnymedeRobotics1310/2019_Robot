package robot.commands.lift;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import robot.commands.LoggingCommandBase;
import robot.commands.hatch.HatchCenterCommand;
import robot.oi.OI;
import robot.subsystems.DriveSubsystem;
import robot.subsystems.HatchSubsystem;
import robot.subsystems.LiftSubsystem;

/**
 *
 */
public class L2HopUp extends LoggingCommandBase {

    private final OI             operatorInput;

    private final LiftSubsystem  liftSubsystem;
    private final DriveSubsystem driveSubsystem;
    private final HatchSubsystem hatchSubsystem;

    enum State {
        REAR_UP, DRIVE_TO_PLATFORM, SHUFFLE, DRIVE_ON, RAISE_FRONT, FINISH_FORWARD, FINISH
    };

    private State              state                       = State.REAR_UP;

    private long               stateStartTime              = 0;

    public static final double BUMPER_AT_L2_ENCODER_COUNTS = -1500;

    public L2HopUp(OI operatorInput, LiftSubsystem liftSubsystem, DriveSubsystem driveSubsystem,
        HatchSubsystem hatchSubsystem) {

        this.operatorInput  = operatorInput;

        this.liftSubsystem  = liftSubsystem;
        this.driveSubsystem = driveSubsystem;
        this.hatchSubsystem = hatchSubsystem;

        addRequirements(liftSubsystem, driveSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {

        logCommandStart();

        // Move the arm to level 2 - note, setting the operator value will launch the command to
        // move the arm.
        operatorInput.setArmLevel(2);
        CommandScheduler.getInstance().schedule(new HatchCenterCommand(hatchSubsystem));
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

        switch (state) {

        case REAR_UP:

            // Put the back of the robot in the air
            liftSubsystem.setFrontMotorSpeed(0);
            liftSubsystem.setRearMotorSpeed(-.95);

            if (liftSubsystem.getRearEncoder() <= BUMPER_AT_L2_ENCODER_COUNTS) {

                liftSubsystem.setFrontMotorSpeed(0);
                liftSubsystem.setRearMotorSpeed(0);

                state          = State.DRIVE_TO_PLATFORM;
                stateStartTime = System.currentTimeMillis();
            }

            break;

        case DRIVE_TO_PLATFORM:

            // Back up onto the platform for .7 seconds
            driveSubsystem.setMotorSpeeds(-.15, -.15);

            if (System.currentTimeMillis() > stateStartTime + 700) {
                state          = State.SHUFFLE;
                stateStartTime = System.currentTimeMillis();
            }

            break;

        case SHUFFLE:

            // put the rear wheels down on the platform and lift the front
            // while backing up.
            liftSubsystem.setRearMotorSpeed(1.0);
            liftSubsystem.setFrontMotorSpeed(-.95);
            liftSubsystem.setDriveMotorSpeed(1.0);

            driveSubsystem.setMotorSpeeds(-0.07, -0.07);

            // Once the front is up, continue to drive on.
            if (liftSubsystem.getFrontEncoder() <= BUMPER_AT_L2_ENCODER_COUNTS - 200) {
                state          = State.DRIVE_ON;
                stateStartTime = System.currentTimeMillis();
            }
            break;

        case DRIVE_ON:

            // Drive on for 1.7 seconds and then raise the front wheels
            liftSubsystem.setFrontMotorSpeed(0);
            liftSubsystem.setRearMotorSpeed(0);
            liftSubsystem.setDriveMotorSpeed(0);

            driveSubsystem.setMotorSpeeds(-0.2, -0.2);

            // Stop after 1.7 seconds and assume the robot is up.
            if (System.currentTimeMillis() > stateStartTime + 1700) {
                state          = State.RAISE_FRONT;
                stateStartTime = System.currentTimeMillis();
            }
            break;

        case RAISE_FRONT:

            liftSubsystem.setFrontMotorSpeed(1.0);
            liftSubsystem.setDriveMotorSpeed(0);

            driveSubsystem.setMotorSpeeds(-0.2, -0.2);

            if (System.currentTimeMillis() > stateStartTime + 1000) {
                state          = State.FINISH_FORWARD;
                stateStartTime = System.currentTimeMillis();
            }
            break;

        case FINISH_FORWARD:

            // Back onto the platform until the timeout
            driveSubsystem.setMotorSpeeds(-0.2, -0.2);

            if (System.currentTimeMillis() > stateStartTime + 2000) {
                state = State.FINISH;
            }
            break;

        case FINISH:
        default:
            break;
        }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {

        if (state == State.FINISH) {
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {

        logCommandEnd(interrupted);

        liftSubsystem.setRearMotorSpeed(0);
        liftSubsystem.setFrontMotorSpeed(0);
        liftSubsystem.setDriveMotorSpeed(0);

        driveSubsystem.setMotorSpeeds(0, 0);
    }
}