package robot.commands.drive;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.commands.TDefaultDriveCommand;
import com.torontocodingcollective.commands.drive.TDriveTimeCommand;
import com.torontocodingcollective.commands.gyroDrive.TDriveOnHeadingDistanceCommand;
import com.torontocodingcollective.commands.TDifferentialDrive;
import com.torontocodingcollective.commands.TSafeCommand;
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
public class DriveToUltrasonicDistanceCommand extends TSafeCommand {

    private static final String COMMAND_NAME = 
            DriveToUltrasonicDistanceCommand.class.getSimpleName();
    
    OI                oi                = Robot.oi;
    CanDriveSubsystem driveSubsystem    = Robot.driveSubsystem;
    
    private boolean offHAB=false;
    private double distance;//in inches
    private double startSpeed;
    private double secondarySpeed;
    private double startTime;

    public DriveToUltrasonicDistanceCommand(double distance,double startSpeed, double secondarySpeed,double startTime) {
    	super(TConst.NO_COMMAND_TIMEOUT, Robot.oi);
    	this.distance=distance;
    	this.startSpeed=startSpeed;
    	this.secondarySpeed=secondarySpeed;
    	this.startTime=startTime;
    	requires(Robot.driveSubsystem);
    	
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
        driveSubsystem.setSpeed(startSpeed, startSpeed);
        super.initialize();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	//Speed up after the first second
    	if (timeSinceInitialized()>startTime) {
    		driveSubsystem.setSpeed(secondarySpeed, secondarySpeed);
    		offHAB=true;
    	}
    }

    @Override
    protected boolean isFinished() {
        if (driveSubsystem.getUltrasonicDistance()<distance&&offHAB) {
        	return true;
        }
        
        if (timeSinceInitialized()>7) {
        	return true;
        }
        	
        return false;
    }
}
