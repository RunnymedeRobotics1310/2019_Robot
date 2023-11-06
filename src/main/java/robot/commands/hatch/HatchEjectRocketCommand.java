package robot.commands.hatch;

import robot.commands.LoggingCommandBase;
import robot.subsystems.HatchSubsystem;

public class HatchEjectRocketCommand extends LoggingCommandBase {

    private final HatchSubsystem hatchSubsystem;

    public HatchEjectRocketCommand(HatchSubsystem hatchSubsystem) {

        this.hatchSubsystem = hatchSubsystem;

        // Use requires() here to declare subsystem dependencies
        addRequirements(hatchSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {

        logCommandStart();

        hatchSubsystem.extendHatchMech();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

        // Wait .7 seconds and extend the punch
        if (isTimeoutExceeded(.7)) {
            hatchSubsystem.extendPunchMech();
        }

        // Wait .9 seconds and retract the hatch mechanism
        if (isTimeoutExceeded(.9)) {
            hatchSubsystem.retractHatchMech();
        }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {

        if (isTimeoutExceeded(1.6)) {
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