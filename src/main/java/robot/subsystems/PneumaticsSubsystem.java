package robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 */
public class PneumaticsSubsystem extends SubsystemBase {

    // uncomment the compressor to enable pneumatics control
    Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);

    public PneumaticsSubsystem() {
        enableCompressor();
    };

    public void disableCompressor() {
        compressor.disable();
    }

    public void enableCompressor() {
        compressor.enableDigital();
    }

    // Periodically update the dashboard and any PIDs or sensors
    @Override
    public void periodic() {
        SmartDashboard.putString("Compressor Enabled", compressor.getConfigType().toString());
    }
}
