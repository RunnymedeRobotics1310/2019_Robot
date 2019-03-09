package robot.subsystems;

import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst.Camera;
import robot.commands.camera.DefaultCameraCommand;

/**
 *
 */
public class CameraSubsystem extends TSubsystem {

	private Camera curCamera = Camera.HATCH;
	
	MjpegServer switchedCamera;
	UsbCamera hatchCamera;
	UsbCamera cargoCamera;
    public CameraSubsystem() {
        //Uncomment this line to start a USB camera feed
    	switchedCamera = CameraServer.getInstance().addServer("Switched");  // Port 1181
        hatchCamera = CameraServer.getInstance().startAutomaticCapture("Hatch", 0); // Port 1182
        hatchCamera.setVideoMode(PixelFormat.kMJPEG, 320, 240, 15);
        cargoCamera = CameraServer.getInstance().startAutomaticCapture("Cargo", 1); // Port 1183
        
        switchedCamera.setSource(hatchCamera);
        curCamera = Camera.HATCH;
    }

    @Override
    public void init() {
    }
    
    public void setCamera(Camera camera) {
    	switch (camera) {
    	case HATCH:
    		if (curCamera != Camera.HATCH) {
    			switchedCamera.setSource(hatchCamera);
    			curCamera = Camera.HATCH;
    		}
    		break;
    	case CARGO:
    		if (curCamera != Camera.CARGO) {
    			switchedCamera.setSource(cargoCamera);
    			curCamera = Camera.CARGO;
    		}
    		break;
    	default:
    		break;
    	}
    }

    // Periodically update the dashboard and any PIDs or sensors
    @Override
    public void updatePeriodic() {
    	SmartDashboard.putString("Camera View", curCamera.toString());
    }

    @Override
    protected void initDefaultCommand() {
    	setDefaultCommand(new DefaultCameraCommand());
    }

}
