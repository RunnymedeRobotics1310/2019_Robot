package com.torontocodingcollective.speedcontroller;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.sensors.encoder.TEncoder;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/**
 * Common interface for all TSpeedControllers
 * <p>
 * All TSpeedControllers must implement the {@link SpeedController} interface
 */
public abstract class TSpeedController implements MotorController {

    private boolean isInverted = TConst.NOT_INVERTED;

    protected TSpeedController(boolean isInverted) {
        this.isInverted = isInverted;
    }

    @Override
    public void disable() {
        stopMotor();
    }

    @Override
    public boolean getInverted() {
        return isInverted;
    }

    @Override
    public void setInverted(boolean isInverted) {
        if (isInverted != this.isInverted) {
            stopMotor();
            this.isInverted = isInverted;
        }
    }

    @Override
    public void stopMotor() {
        set(0);
    }

    /**
     * Get the encoder attached to this TSpeedController
     * <p>
     * By default, the encoder will be set with the same inversion setting as the
     * motor and is assumed to be a 2-channel quadrature encoder.
     *
     * @returns TEncoder attached to this device or {@code null} if this device does
     * not support an attached encoder
     */
    public TEncoder getEncoder() {
        System.out.println("GetEncoder is not supported for " + this.getClass().getName());
        return null;
    }

}
