package robot.commands.lighting;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.subsystems.LightingSubsystem.Colour;

public class DefaultLightingCommand extends Command {

	DriverStation ds;

	public DefaultLightingCommand() {
		requires(Robot.lightingSubsystem);
	}

	protected void initialize() {
		ds = DriverStation.getInstance();
	}

	protected void execute() {

		Alliance alliance = ds.getAlliance();
		
		if (alliance == Alliance.Blue) {
			Robot.lightingSubsystem.setColour(Colour.BLUE);
		}
		else if (alliance == Alliance.Red) {
			Robot.lightingSubsystem.setColour(Colour.RED);
		}
			
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
