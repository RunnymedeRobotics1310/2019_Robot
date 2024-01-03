package robot.commands.hatch;

import robot.commands.LoggingCommand;
import robot.subsystems.HatchSubsystem;

/**
 * HatchCenter command returns the hatch to the center
 */
public class HatchCenterCommand extends LoggingCommand {

    private final HatchSubsystem hatchSubsystem;

    private boolean              startLeftOfCenter;

    public HatchCenterCommand(HatchSubsystem hatchSubsystem) {

        this.hatchSubsystem = hatchSubsystem;

        // Use requires() here to declare subsystem dependencies
        addRequirements(hatchSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {

        logCommandStart();

        if (!hatchSubsystem.isCentered()) {

            if (hatchSubsystem.getSlideEncoder() > 0) {
                startLeftOfCenter = true;
                hatchSubsystem.setSlideSpeed(-0.2);
            }
            else {
                startLeftOfCenter = false;
                hatchSubsystem.setSlideSpeed(0.15);
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {

        if (hatchSubsystem.isCentered()) {
            setFinishReason("is centered");
            return true;
        }

        // Check that the hatch did not move past the center from where it started.
        if (startLeftOfCenter && hatchSubsystem.getSlideEncoder() < 0) {
            setFinishReason("past center");
            return true;
        }

        if (!startLeftOfCenter && hatchSubsystem.getSlideEncoder() > 0) {
            setFinishReason("past center");
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {

        logCommandEnd(interrupted);

        hatchSubsystem.setSlideSpeed(0);
    }
}