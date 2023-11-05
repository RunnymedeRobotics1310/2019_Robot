package robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch {

    private final DigitalInput digitalInput;
    private final boolean      defaultState;

    /**
     * Wrap a digital input in the limit switch interface to make the code more readable.
     * This class provides the {@link #atLimit()} method to determine if the limit switch has
     * detected a limit condition.
     *
     * @param digitalInput to use for this limit switch
     * @param defaultState when <b>not activated</b>. {@code true} if the {@link DigitalInput} is
     * high(1) when not activated,
     * {@code false} if the {@link DigitalInput} is low(0)
     * when not activated.
     */
    public LimitSwitch(DigitalInput digitalInput, boolean defaultState) {
        this.digitalInput = digitalInput;
        this.defaultState = defaultState;
    }

    /**
     * Determine if this limit switch has hit the limit
     *
     * @return {@code true} if at the limit {@code false} otherwise
     */
    public boolean atLimit() {
        return digitalInput.get() != defaultState;
    }
}
