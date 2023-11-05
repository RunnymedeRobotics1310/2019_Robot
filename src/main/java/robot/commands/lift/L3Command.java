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
public class L3Command extends LoggingCommandBase {

    private final OI             operatorInput;

    private final LiftSubsystem  liftSubsystem;
    private final DriveSubsystem driveSubsystem;
    private final HatchSubsystem hatchSubsystem;

    enum State {
        LIFT, DRIVE_TO_PLATFORM, NUDGE_FORWARD, RAISE_REAR, DRIVE_ON, RAISE_FRONT, RAISE_FRONT2, FINISH_FORWARD, FINISH
    };

    private State              state                       = State.LIFT;

    long                       stateStartTime              = 0;


    public static final double BUMPER_AT_L3_ENCODER_COUNTS = -5350;

    public L3Command(OI operatorInput, LiftSubsystem liftSubsystem, DriveSubsystem driveSubsystem,
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

        // Synchronized lift
        double encoderMismatch = liftSubsystem.getFrontEncoder()
            - liftSubsystem.getRearEncoder();

        switch (state) {

        case LIFT:

            // Sync lift to L3
            liftSubsystem.setFrontMotorSpeed(-.95 - encoderMismatch * .001);
            liftSubsystem.setRearMotorSpeed(-.95);

            if (liftSubsystem.getFrontEncoder() <= BUMPER_AT_L3_ENCODER_COUNTS) {
                state = State.DRIVE_TO_PLATFORM;
            }
            break;

        case DRIVE_TO_PLATFORM:

            // Keep lifting slowly while driving on
            liftSubsystem.setFrontMotorSpeed(-0.3 - encoderMismatch * .001);
            liftSubsystem.setRearMotorSpeed(-0.3);
            liftSubsystem.setDriveMotorSpeed(0.7);

            driveSubsystem.setMotorSpeeds(-0.11, -0.11);

            if (liftSubsystem.getPlatformDetect()) {
                state = State.RAISE_REAR;
            }
            break;

        case RAISE_REAR:

            // Lift the rear legs and stop the front legs
            liftSubsystem.setFrontMotorSpeed(0);
            liftSubsystem.setRearMotorSpeed(1.0);
            liftSubsystem.setDriveMotorSpeed(0.5);

            // Slow the drive so that the lifting legs (rear) do not get stuck
            driveSubsystem.setMotorSpeeds(-0.05, -0.05);

            // End when the rear encoder is high enough
            if (liftSubsystem.getRearEncoder() > -350) {
                state = State.DRIVE_ON;
            }
            break;

        case DRIVE_ON:

            // keep lifting the leading legs (rear) as the robot drives on
            liftSubsystem.setRearMotorSpeed(0.2);
            liftSubsystem.setDriveMotorSpeed(1);

            driveSubsystem.setMotorSpeeds(-0.2, -0.2);

            if (liftSubsystem.getCentreDetect()) {
                state = State.RAISE_FRONT;
            }
            break;

        case RAISE_FRONT:

            liftSubsystem.setRearMotorSpeed(0);
            liftSubsystem.setFrontMotorSpeed(1.0);
            liftSubsystem.setDriveMotorSpeed(0.4);

            driveSubsystem.setMotorSpeeds(-0.09, -0.09);

            if (liftSubsystem.getFrontEncoder() > -350) {
                state          = State.FINISH_FORWARD;
                stateStartTime = System.currentTimeMillis();
            }
            break;

        case FINISH_FORWARD:

            liftSubsystem.setDriveMotorSpeed(0.0);
            liftSubsystem.setFrontMotorSpeed(0.3);
            driveSubsystem.setMotorSpeeds(-0.09, -0.09);

            if (System.currentTimeMillis() > stateStartTime + 1000) {
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

        super.logCommandEnd(interrupted);

        liftSubsystem.setRearMotorSpeed(0);
        liftSubsystem.setFrontMotorSpeed(0);
        liftSubsystem.setDriveMotorSpeed(0);

        driveSubsystem.setMotorSpeeds(0, 0);
    }

}