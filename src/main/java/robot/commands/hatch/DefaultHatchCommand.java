package robot.commands.hatch;

import robot.commands.LoggingCommandBase;
import robot.oi.OI;
import robot.subsystems.HatchSubsystem;

/**
 * The default hatch command TODO: commenting
 */
public class DefaultHatchCommand extends LoggingCommandBase {

    private final OI             operatorInput;
    private final HatchSubsystem hatchSubsystem;

    public DefaultHatchCommand(OI operatorInput, HatchSubsystem hatchSubsystem) {

        this.operatorInput  = operatorInput;
        this.hatchSubsystem = hatchSubsystem;

        // Use requires() here to declare subsystem dependencies
        addRequirements(hatchSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {

        logCommandStart();

        hatchSubsystem.retractPunchMech();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

        hatchSubsystem.setSlideSpeed(
            (operatorInput.getHatchSlideLeft() / 3) - (operatorInput.getHatchSlideRight() / 3));

        if (operatorInput.getReset()) {
            hatchSubsystem.resetEncoder();
        }

        // Updates and sets the Solenoids for the hatch mech
        if (operatorInput.getHatchMechExtend()) {
            hatchSubsystem.extendHatchMech();
        }
        else if (operatorInput.getHatchMechRetract()) {
            hatchSubsystem.retractHatchMech();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }
}