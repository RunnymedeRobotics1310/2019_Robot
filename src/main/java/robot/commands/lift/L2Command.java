package robot.commands.lift;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.commands.cargo.CargoArmLevelCommand;
import robot.commands.hatch.HatchCentreCommand;

/**
 *
 */
public class L2Command extends TSafeCommand {

    private static final String COMMAND_NAME = 
            L2Command.class.getSimpleName();

    enum State { LIFT, DRIVE_TO_PLATFORM, NUDGE_FORWARD, RAISE_REAR, DRIVE_ON, RAISE_FRONT, FINISH_FORWARD, FINISH };

    private State state = State.LIFT;

    double stepStartTime = 0;
    double driveStartTime = 0;

    public L2Command() {

        super(TConst.NO_COMMAND_TIMEOUT, Robot.oi);

        // Use requires() here to declare subsystem dependencies
        requires(Robot.liftSubsystem);
        requires(Robot.driveSubsystem);
    }

    @Override
    protected String getCommandName() { return COMMAND_NAME; }

    @Override
    protected String getParmDesc() { 
        return super.getParmDesc(); 
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        // Print the command parameters if this is the current
        // called command (it was not sub-classed)
        if (getCommandName().equals(COMMAND_NAME)) {
            logMessage(getParmDesc() + " starting");
        }

        // Move the arm to level 2
        Robot.oi.setArmLevel(2);
        Scheduler.getInstance().add(new CargoArmLevelCommand());
        Scheduler.getInstance().add(new HatchCentreCommand());
    }

    // Called repeatedly when this Command is scheduled to run
    
    public static final double BUMPER_AT_L2_ENCODER_COUNTS = -2050;
    @Override
    protected void execute() {

        double encoderMismatch = Robot.liftSubsystem.getFrontLiftEncoder().get()
                - Robot.liftSubsystem.getRearLiftEncoder().get();

        switch (state) {
        case LIFT:
            Robot.liftSubsystem.setFrontMotorSpeed(-.95 - encoderMismatch * .001);
            Robot.liftSubsystem.setRearMotorSpeed(-.95);
            if (Robot.liftSubsystem.getFrontLiftEncoder().get() <= BUMPER_AT_L2_ENCODER_COUNTS) {
                Robot.liftSubsystem.setFrontMotorSpeed(0);
                Robot.liftSubsystem.setRearMotorSpeed(0);
                state = State.DRIVE_TO_PLATFORM;
                driveStartTime = timeSinceInitialized();
            }
            Scheduler.getInstance().add(new HatchCentreCommand());
            break;
            
        case DRIVE_TO_PLATFORM:
            Robot.liftSubsystem.setDriveMotorSpeed(0.7);
            Robot.driveSubsystem.setSpeed(-0.07,-0.07);
            if (timeSinceInitialized() > driveStartTime + 0.7) {
                state = State.RAISE_REAR;
            }
            break;

        case RAISE_REAR:
            Robot.liftSubsystem.setRearMotorSpeed(1.0);
            Robot.liftSubsystem.setDriveMotorSpeed(0.7);
            Robot.driveSubsystem.setSpeed(-0.07,-0.07);
            if (Robot.liftSubsystem.getRearLiftEncoder().get() > -350) {
                state = State.DRIVE_ON;
                driveStartTime = timeSinceInitialized();
            }
            break;
        
        case DRIVE_ON:
            Robot.liftSubsystem.setRearMotorSpeed(0.2);
            Robot.liftSubsystem.setDriveMotorSpeed(1);
            Robot.driveSubsystem.setSpeed(-0.1,-0.1);
            if (timeSinceInitialized() > driveStartTime + 1.5) {
                state = State.RAISE_FRONT;
                Robot.liftSubsystem.setRearMotorSpeed(0);
            }
            break;
            
        case RAISE_FRONT:
            Robot.liftSubsystem.setFrontMotorSpeed(1.0);
            Robot.liftSubsystem.setDriveMotorSpeed(0.4);
            Robot.driveSubsystem.setSpeed(-0.05,-0.05);
            if (Robot.liftSubsystem.getFrontLiftEncoder().get() > -350) {
                state = State.FINISH_FORWARD;
                stepStartTime = timeSinceInitialized();
                Robot.liftSubsystem.setDriveMotorSpeed(0.0);
            }
            break;
            
        case FINISH_FORWARD:
            Robot.liftSubsystem.setFrontMotorSpeed(0.3);
            Robot.driveSubsystem.setSpeed(-0.05,-0.05);
            if (timeSinceInitialized() - stepStartTime > 0.5) {
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
    protected boolean isFinished() {

        if (super.isFinished()) {
            return true;
        }

        if (state == State.FINISH) {
            return true;
        }
        return false;
    }

    @Override
    protected void end() {
        Robot.liftSubsystem.setRearMotorSpeed(0);
        Robot.liftSubsystem.setFrontMotorSpeed(0);
        Robot.liftSubsystem.setDriveMotorSpeed(0);
        Robot.driveSubsystem.setSpeed(0, 0);
    }

}