package robot.oi;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoSelector {

    public static SendableChooser<String> robotStartPosition;

    public static final String            ROBOT_LEFT   = "Robot Left";
    public static final String            ROBOT_CENTER = "Robot Center";
    public static final String            ROBOT_RIGHT  = "Robot Right";

    public static SendableChooser<String> pattern;

    public static final String            PATTERN_CARGO  = "Cargo";
    public static final String            PATTERN_NONE   = "None";
    public static final String            PATTERN_TEST   = "Test";

    static {

        // Robot Position Options
        robotStartPosition = new SendableChooser<String>();
        robotStartPosition.addObject(ROBOT_LEFT, ROBOT_LEFT);
        robotStartPosition.addDefault(ROBOT_CENTER, ROBOT_CENTER);
        robotStartPosition.addObject(ROBOT_RIGHT, ROBOT_RIGHT);

        SmartDashboard.putData("Robot Start", robotStartPosition);

        // Robot Pattern Options
        pattern = new SendableChooser<String>();
        pattern.addDefault(PATTERN_NONE, PATTERN_NONE);
        pattern.addObject(PATTERN_CARGO, PATTERN_CARGO);
        pattern.addObject(PATTERN_TEST, PATTERN_TEST);

        SmartDashboard.putData("Auto Pattern", pattern);
    }

    /**
     * Get the auto pattern.
     * 
     * @return "Straight" or "Box"
     */
    public static String getPattern() {

        return pattern.getSelected();
    }
    
    /**
     * Get the robot starting position on the field.
     * 
     * @return 'L' for left, 'R' for right or 'C' for center
     */
    public static String getRobotStartPosition() {

        return robotStartPosition.getSelected();
    }

    public static void init() {}
}
