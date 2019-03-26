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
public class L3Command extends TSafeCommand {

    private static final String COMMAND_NAME = 
            L3Command.class.getSimpleName();

    enum State { LIFT, DRIVE_TO_PLATFORM, NUDGE_FORWARD, RAISE_REAR, DRIVE_ON, RAISE_FRONT, RAISE_FRONT2, FINISH_FORWARD, FINISH };

    private State state = State.LIFT;

    double stepStartTime = 0;

    public L3Command() {

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
    
    public static final double BUMPER_AT_L3_ENCODER_COUNTS = -5500;
    @Override
    protected void execute() {

        double encoderMismatch = Robot.liftSubsystem.getFrontLiftEncoder().get()
                - Robot.liftSubsystem.getRearLiftEncoder().get();

        switch (state) {
        case LIFT:
            Robot.liftSubsystem.setFrontMotorSpeed(-.95 - encoderMismatch * .001);
            Robot.liftSubsystem.setRearMotorSpeed(-.95);
            if (Robot.liftSubsystem.getFrontLiftEncoder().get() <= BUMPER_AT_L3_ENCODER_COUNTS) {
                state = State.DRIVE_TO_PLATFORM;
            }
            Scheduler.getInstance().add(new HatchCentreCommand());
            break;
            
        case DRIVE_TO_PLATFORM:
            Robot.liftSubsystem.setFrontMotorSpeed(-0.3 - encoderMismatch * .001);
            Robot.liftSubsystem.setRearMotorSpeed(-0.3);
            Robot.liftSubsystem.setDriveMotorSpeed(0.7);
            Robot.driveSubsystem.setSpeed(-0.11,-0.11);
            if (Robot.liftSubsystem.getPlatformDetect()) {
                state = State.RAISE_REAR;
            }
            break;

        case RAISE_REAR:
            Robot.liftSubsystem.setRearMotorSpeed(1.0);
            Robot.liftSubsystem.setDriveMotorSpeed(0.5);
            Robot.driveSubsystem.setSpeed(-0.05,-0.05);
            if (Robot.liftSubsystem.getRearLiftEncoder().get() > -350) {
                state = State.DRIVE_ON;
            }
            break;
        
        case DRIVE_ON:
            Robot.liftSubsystem.setRearMotorSpeed(0.2);
            Robot.liftSubsystem.setDriveMotorSpeed(1);
            Robot.driveSubsystem.setSpeed(-0.2,-0.2);
            if (Robot.liftSubsystem.getCentreDetect()) {
                state = State.RAISE_FRONT;
                Robot.liftSubsystem.setRearMotorSpeed(0);
            }
            break;
            
        case RAISE_FRONT:
            Robot.liftSubsystem.setFrontMotorSpeed(0.3);
            Robot.liftSubsystem.setDriveMotorSpeed(0.4);
            Robot.driveSubsystem.setSpeed(-0.09,-0.09);
            if (Robot.liftSubsystem.getFrontLiftEncoder().get() > -5100) {
                state = State.FINISH;
                stepStartTime = timeSinceInitialized();
                Robot.liftSubsystem.setDriveMotorSpeed(0.0);
            }
            break;
            
        case RAISE_FRONT2:
            Robot.liftSubsystem.setFrontMotorSpeed(1.0);
            Robot.liftSubsystem.setDriveMotorSpeed(0.4);
            Robot.driveSubsystem.setSpeed(-0.09,-0.09);
            if (Robot.liftSubsystem.getFrontLiftEncoder().get() > -350) {
                state = State.FINISH_FORWARD;
                stepStartTime = timeSinceInitialized();
                Robot.liftSubsystem.setDriveMotorSpeed(0.0);
            }
            break;
            
        case FINISH_FORWARD:
            Robot.liftSubsystem.setFrontMotorSpeed(0.3);
            Robot.driveSubsystem.setSpeed(-0.09,-0.09);
            if (timeSinceInitialized() - stepStartTime > 1.0) {
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