package robot.commands.lift;

import robot.commands.LoggingCommand;
import robot.oi.OI;
import robot.subsystems.LiftSubsystem;

/**
 *
 */
public class DefaultLiftCommand extends LoggingCommand {

    private final OI            operatorInput;
    private final LiftSubsystem liftSubsystem;


    public DefaultLiftCommand(OI operatorInput, LiftSubsystem liftSubsystem) {

        this.operatorInput = operatorInput;
        this.liftSubsystem = liftSubsystem;

        addRequirements(liftSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        logCommandStart();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

        /*
         * Set the lift motor speeds
         */
        if (operatorInput.syncedExtendLift()) {

            double encoderMismatch = liftSubsystem.getFrontEncoder()
                - liftSubsystem.getRearEncoder();

            liftSubsystem.setFrontMotorSpeed(-0.9 - encoderMismatch * .001);
            liftSubsystem.setRearMotorSpeed(-0.9);
        }
        else if (operatorInput.syncedRetractLift()) {

            double encoderMismatch = liftSubsystem.getFrontEncoder()
                - liftSubsystem.getRearEncoder();

            liftSubsystem.setFrontMotorSpeed(0.5 - encoderMismatch * .001);
            liftSubsystem.setRearMotorSpeed(0.5);
        }
        else {

            // Front lift (manual)
            if (operatorInput.getRetractFrontLift()) {
                liftSubsystem.setFrontMotorSpeed(0.8);
            }
            else if (operatorInput.getExtendFrontLift() > 0) {
                liftSubsystem.setFrontMotorSpeed(-operatorInput.getExtendFrontLift());
            }
            else {
                liftSubsystem.setFrontMotorSpeed(0);
            }

            // Rear lift (manual)
            if (operatorInput.getRetractRearLift()) {
                liftSubsystem.setRearMotorSpeed(0.8);
            }
            else if (operatorInput.getExtendRearLift() > 0) {
                liftSubsystem.setRearMotorSpeed(-operatorInput.getExtendRearLift());
            }
            else {
                liftSubsystem.setRearMotorSpeed(0);
            }
        }

        /*
         * Set the drive motor speeds
         */
        if (operatorInput.getLiftDriveForward()) {
            liftSubsystem.setDriveMotorSpeed(0.5);
        }
        else {
            liftSubsystem.setDriveMotorSpeed(0);
        }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

}
