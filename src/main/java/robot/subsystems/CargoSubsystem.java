package robot.subsystems;

import com.torontocodingcollective.subsystem.TSubsystem;

/**
 * Subsystem for cargo intake / ejecting cargo.
 */
public class CargoSubsystem extends TSubsystem {


    public void init() {

    };

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DefaultCargoCommand());
    }

    public void
    
    public void updatePeriodic() {

    }
}