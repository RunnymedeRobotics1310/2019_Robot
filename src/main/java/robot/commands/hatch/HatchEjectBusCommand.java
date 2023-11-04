package robot.commands.hatch;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TSafeCommand;

import robot.Robot;
import robot.RobotConst.Side;

public class HatchEjectBusCommand extends TSafeCommand {

    private static final String COMMAND_NAME = DefaultHatchCommand.class.getSimpleName();

    private Side                firstSide;

    public HatchEjectBusCommand(Side side) {

        super(TConst.NO_COMMAND_TIMEOUT, Robot.oi);
        // Use requires() here to declare subsystem dependencies
        requires(Robot.hatchSubsystem);

        firstSide = side;
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }

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

        switch (firstSide) {
        case LEFT:
            Robot.hatchSubsystem.extendPunchMechLeft();
            break;
        case RIGHT:
            Robot.hatchSubsystem.extendPunchMechRight();
            break;
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        if (timeSinceInitialized() > 0.055) {

            switch (firstSide) {
            case LEFT:
                Robot.hatchSubsystem.extendPunchMechRight();
                break;
            case RIGHT:
                Robot.hatchSubsystem.extendPunchMechLeft();
                break;
            }
        }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        if (timeSinceInitialized() > 0.5) {
            return true;
        }
        return false;
    }

    @Override
    protected void end() {
        Robot.hatchSubsystem.retractPunchMech();
    }

}