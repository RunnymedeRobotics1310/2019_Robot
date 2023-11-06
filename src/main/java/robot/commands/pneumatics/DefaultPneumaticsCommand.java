package robot.commands.pneumatics;

import robot.commands.LoggingCommandBase;
import robot.oi.OI;
import robot.subsystems.PneumaticsSubsystem;

/**
 *
 */
public class DefaultPneumaticsCommand extends LoggingCommandBase {

    private final OI                  operatorInput;
    private final PneumaticsSubsystem pneumaticsSubsystem;

    public DefaultPneumaticsCommand(OI operatorInput, PneumaticsSubsystem pneumaticsSubsystem) {

        this.operatorInput       = operatorInput;
        this.pneumaticsSubsystem = pneumaticsSubsystem;

        // Use requires() here to declare subsystem dependencies
        addRequirements(pneumaticsSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {

        logCommandStart();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

        if (operatorInput.getCompressorEnabled()) {
            pneumaticsSubsystem.enableCompressor();
        }
        else {
            pneumaticsSubsystem.disableCompressor();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }
}
