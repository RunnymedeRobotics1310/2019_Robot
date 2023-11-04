package robot.subsystems;

public class GhostSolenoid {

    boolean state = false;

    public GhostSolenoid(int port) {
    }

    public void set(boolean state) {
        this.state = state;
    }

    public boolean get() {
        return state;
    }
}
