package robot.commands.hatch;

import robot.Constants.Side;
import robot.commands.LoggingCommand;
import robot.subsystems.HatchSubsystem;

public class HatchEjectBusCommand extends LoggingCommand {

    private final HatchSubsystem hatchSubsystem;

    private Side                 firstSide;

    public HatchEjectBusCommand(Side firstSide, HatchSubsystem hatchSubsystem) {

        this.firstSide      = firstSide;
        this.hatchSubsystem = hatchSubsystem;

        // Use requires() here to declare subsystem dependencies
        addRequirements(hatchSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {

        logCommandStart("First Punch Side " + firstSide.toString());

        switch (firstSide) {
        case LEFT:
            hatchSubsystem.extendPunchMechLeft();
            break;
        case RIGHT:
            hatchSubsystem.extendPunchMechRight();
            break;
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

        if (isTimeoutExceeded(.55)) {

            switch (firstSide) {
            case LEFT:
                hatchSubsystem.extendPunchMechRight();
                break;
            case RIGHT:
                hatchSubsystem.extendPunchMechLeft();
                break;
            }
        }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {

        if (isTimeoutExceeded(1.0)) {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {

        logCommandEnd(interrupted);

        hatchSubsystem.retractPunchMech();
    }

}