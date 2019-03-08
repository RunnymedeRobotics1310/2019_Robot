package robot.subsystems;

import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;

/**
 *
 */
public class CameraSubsystem extends TSubsystem {

	MjpegServer switchedCamera;
	UsbCamera camera1;
	UsbCamera camera2;
    public CameraSubsystem() {
        //Uncomment this line to start a USB camera feed
    	switchedCamera = CameraServer.getInstance().addServer("Switched");  // Port 1181
        camera1 = CameraServer.getInstance().startAutomaticCapture("Hatch", 0); // Port 1182
        camera1.setVideoMode(PixelFormat.kMJPEG, 320, 240, 15);
        camera2 = CameraServer.getInstance().startAutomaticCapture("Cargo", 1); // Port 1183
        
        switchedCamera.setSource(camera1);
    }

    public MjpegServer getSwitchedCamera() {
    	return switchedCamera;
    }
    
    public UsbCamera getCamera1() {
    	return camera1;
    }
    
    public UsbCamera getCamera2() {
    	return camera2;
    }
    
    @Override
    public void init() {
    }

    // Periodically update the dashboard and any PIDs or sensors
    @Override
    public void updatePeriodic() {
    }

    @Override
    protected void initDefaultCommand() {
    }

}
