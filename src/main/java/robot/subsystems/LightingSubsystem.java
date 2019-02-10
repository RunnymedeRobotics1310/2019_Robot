package robot.subsystems;

import com.mindsensors.CANLight;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.lighting.DefaultLightingCommand;

public class LightingSubsystem extends TSubsystem {
	
	private CANLight frameLights;

	public enum Colour {RED, BLUE, YELLOW, GREEN, WHITE }; 
	
	private Colour curColour = Colour.WHITE;
	
	/* (non-Javadoc)
	 * @see com.toronto.subsystems.T_Subsystem#robotInit()
	 */
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultLightingCommand());
	}
	
	@Override
	public void init() {
		frameLights = new CANLight(RobotMap.CANLIGHT_CAN_ADDRESS);
		setColour(Colour.WHITE);
	}
	
	public void setColour(Colour colour) {
		curColour = colour;
		switch (colour) {
		case RED:
			frameLights.showRGB(255, 0, 0);
			break;
		case BLUE:
			frameLights.showRGB(0, 0, 255);
			break;
		case GREEN:
			frameLights.showRGB(0, 255, 0);
			break;
		case YELLOW:
			frameLights.showRGB(255, 255, 0);
			break;
		default:
			frameLights.showRGB(255,255,255);
		}
	}

	@Override
	public void updatePeriodic() {

		SmartDashboard.putString("Framelights", curColour.toString());
	}

}
