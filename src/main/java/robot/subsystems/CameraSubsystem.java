package robot.subsystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.RobotConst;
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
	
	// NetworkTable 
	private NetworkTableInstance    inst    = NetworkTableInstance.getDefault();
	private NetworkTable            table   = inst.getTable("GRIP/myContoursReport");
	private NetworkTableEntry       centerX	= table.getEntry("centerX");
	private NetworkTableEntry       centerY	= table.getEntry("centerY");
	
	private double[] centerXArray = null;

    public CameraSubsystem() {
    	
    	switchedCamera = CameraServer.getInstance().addServer("Switched");  // Port 1181
        hatchCamera = CameraServer.getInstance().startAutomaticCapture("Hatch", 0); // Port 1182
        hatchCamera.setVideoMode(PixelFormat.kMJPEG, 320, 240, 15);
        hatchCamera.setExposureManual(40);
        
        switchedCamera.setSource(hatchCamera);
        
        curCamera = Camera.HATCH;
    }

    @Override
    public void init() {
    }
    
    public void setCamera(Camera camera) {
    	// switch (camera) {
    	// case HATCH:
    	// 	if (curCamera != Camera.HATCH) {
    	// 		switchedCamera.setSource(hatchCamera);
    	// 		curCamera = Camera.HATCH;
    	// 	}
    	// 	break;
    	// case CARGO:
    	// 	if (curCamera != Camera.CARGO) {
    	// 		switchedCamera.setSource(cargoCamera);
    	// 		curCamera = Camera.CARGO;
    	// 	}
    	// 	break;
    	// default:
    	// 	break;
    	// }
    }

	public boolean targetsFound() {
	    
	    // NOTE: The targets cannot be found if the robot is moving.
	    //
        //       Since the camera lags behind the robot,   
        //       any targets found when the robot is moving will be 
        //       in the wrong position.  
        //
        //       The robot must be still for about 200ms to guarantee
        //       the targets.
//        if (Robot.driveSubsystem.getStoppedTime() < 0.2) {
//            return false;
//        }

        
		if (centerXArray != null && centerXArray.length == 2) {
			return true;
		}
		return false;
	}

	private double getTargetAveragesX() {
		
		if (!targetsFound()) {
			return 0;
		}
		
		// Check targetsFound() before using
		return (centerXArray[0] + centerXArray[1]) / 2.0;
	}

	private double getRawDegreesOff() {
		// Calculates degrees off and doesn't correct for error margins
		// Check targetsFound() before using
		// But getDegreesOff() should be used instead

		/* 
		To better visualize the below math, paste the following into https://www.desmos.com/scientific :
		\tan^{-1}\left(\frac{\frac{x-320}{320}\left(146.25\right)}{307}\right)
		x represents the avgX variable
		- or + is left or right
		146.25: half the camera view field in cm - when we measured
		307 distance from camera to 146.25 cm viewfield line
		Field of view is 51 degrees
		Microsoft Skype LifeCam HD 3000
		*/

		return Math.toDegrees(Math.atan((((getTargetAveragesX()-RobotConst.VISION_CENTER_X)/320) * 146.25) / 307));
	}

	public double getDegreesOff() {
		// Calculates degrees off and considers error margins
		// Check targetsFound() before using
		// Make sure to add this to the current gyro angle

		// Users should use alignmentNeeded before using - this is just in case
		if (!targetsFound()) {
			return 0;
        }
        
		return getRawDegreesOff();
	}

	public boolean alignmentNeeded() {
		// Includes error margin correction

	    if (targetsFound() && Math.abs(getDegreesOff()) > 2) {
			return true;
		}

		return false;
	}
	
    // Periodically update the dashboard and any PIDs or sensors
    @Override
    public void updatePeriodic() {
        
		// Setup the vision targets array so we use the same values each loop
		if (centerX != null) {
			centerXArray = centerX.getDoubleArray(new double[] {});
			double[] centerYArray = centerY.getDoubleArray(new double[] {});
			if (centerXArray.length != centerYArray.length) {
				centerXArray = null;
			}
			else {
				filterCenterXArray(centerYArray);
				if (centerXArray != null) {
					if (centerXArray.length == 0) {
						centerXArray = null;
					}
				}
			}
		} else {
			centerXArray = null;
		}
		
    	SmartDashboard.putString("Camera", curCamera.toString());
    	SmartDashboard.putBoolean("Targets Found", targetsFound());
    	SmartDashboard.putNumber("Target Center", getTargetAveragesX());
    	SmartDashboard.putBoolean("On Target", targetsFound() && !alignmentNeeded());
        SmartDashboard.putNumber("Degrees Off", getDegreesOff());
    }

    @Override
    protected void initDefaultCommand() {
    	setDefaultCommand(new DefaultCameraCommand());
    }
    
    private void filterCenterXArray(double[] centerYArray) {

    	if (centerXArray == null) {
    		return;
    	}
    	
    	if (centerXArray.length != centerYArray.length) {
    		centerXArray = null;
    		return;
    	}
    	
    	// Filter the array to only use the values that are closest to the middle
    	List<Double> xValues = new ArrayList<>();
    	for (int i=0; i<centerXArray.length; i++) {
    		if (centerYArray[i] > 200) {
    			xValues.add(centerXArray[i]);
    		}
    	}
    	
    	if (xValues.size() < 2) {
    		centerXArray = null;
    		return;
    	}
    	
    	Collections.sort(xValues);

    	// Get the closest index to center
    	double minDistance = 100000;
    	int minIndex = -1;
    	
    	for (int i=0; i<xValues.size(); i++) {
    		
    		double distance = Math.abs(xValues.get(i) - RobotConst.VISION_CENTER_X);

    		if (distance < minDistance) {
    			minDistance = distance;
    			minIndex = i;
    		}
    	}
    	
    	// If the XArray contains bizarre values, make sure that the 
    	// system does not throw an exception for index out of bounds.
    	if (minIndex == -1) {
    		centerXArray = null;
    		return;
    	}
    	
    	// Once the minimum is found, find the minimum distance of the index
    	// over the min distance index, or the index under the min distance index.
    	
    	int nextClosestIndex = minIndex;
    	
    	if (minIndex == 0) {
    		nextClosestIndex = 1;
    	}
    	else if (minIndex == xValues.size()-1) {
    		nextClosestIndex = xValues.size()-2;
    	}
    	else {
    		double dist1 = Math.abs(xValues.get(minIndex-1) - RobotConst.VISION_CENTER_X);
    		double dist2 = Math.abs(xValues.get(minIndex+1) - RobotConst.VISION_CENTER_X);
    		if (dist1 < dist2) {
    			nextClosestIndex = minIndex - 1;
    		}
    		else {
    			nextClosestIndex = minIndex + 1;
    		}
    	}
    	
    	centerXArray = new double[] {
    			xValues.get(minIndex), xValues.get(nextClosestIndex) };
    }


}
