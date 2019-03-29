package robot.commands.drive;

import com.torontocodingcollective.commands.TDefaultDriveCommand;
import com.torontocodingcollective.commands.drive.TDriveTimeCommand;
import com.torontocodingcollective.commands.gyroDrive.TDriveOnHeadingDistanceCommand;
import com.torontocodingcollective.commands.TDifferentialDrive;
import com.torontocodingcollective.oi.TStick;
import com.torontocodingcollective.oi.TStickPosition;
import com.torontocodingcollective.speedcontroller.TSpeeds;

import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.oi.OI;
import robot.subsystems.CanDriveSubsystem;

/**
 * Default drive command for a drive base
 */
public class DriveToUltrasonicDistanceCommand extends TDefaultDriveCommand {

    private static final String COMMAND_NAME = 
            DriveToUltrasonicDistanceCommand.class.getSimpleName();
    
    OI                oi                = Robot.oi;
    CanDriveSubsystem driveSubsystem    = Robot.driveSubsystem;
    
    private boolean offHAB=false;

    public DriveToUltrasonicDistanceCommand() {
        // The drive logic will be handled by the TDefaultDriveCommand
        // which also contains the requires(driveSubsystem) statement
        super(Robot.oi, Robot.driveSubsystem);
    }
    
    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    protected String getParmDesc() {
        return super.getParmDesc();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        
        // Print the command parameters if this is the current
        // called command (it was not sub-classed)
        if (getCommandName().equals(COMMAND_NAME)) {
            logMessage(getParmDesc() + " starting");
        }
        driveSubsystem.setSpeed(0.4, 0.4);
        super.initialize();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	//Speed up after the first second
    	if (timeSinceInitialized()>1.5) {
    		driveSubsystem.setSpeed(0.6, 0.6);
    		offHAB=true;
    	}
    }

    @Override
    protected boolean isFinished() {
        if (driveSubsystem.getUltrasonicDistance()<70&&offHAB) {
        	return true;
        }
        
        if (timeSinceInitialized()>7) {
        	return true;
        }
        	
        return false;
    }
}
