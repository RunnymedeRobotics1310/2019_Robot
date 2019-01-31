package com.torontocodingcollective.sensors.limitSwitch;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * TLimitSwitch implements a limit switch with the supplied default state
 */
public class TLimitSwitch {

    public enum DefaultState {
        /** Digital input with {@code true} as the default state */
        TRUE,
        /** Digital input with {@code false} as the default state */
        FALSE
    }

    private final boolean     defaultState;

    private final DigitalInput limitSwitch;
    private final TalonSRX     talonSRX;
    private final CANSparkMax  sparkMax;
    
    private TLimitSwitchType limitSwitchType = null;

    public TLimitSwitch(int port, DefaultState defaultState) {

        limitSwitch = new DigitalInput(port);
        sparkMax    = null;
        talonSRX    = null;

        if (defaultState == DefaultState.TRUE) {
            this.defaultState = true;
        } else {
            this.defaultState = false;
        }
    }

    public TLimitSwitch(TalonSRX talonSRX, DefaultState defaultState) {

        limitSwitch   = null;
        sparkMax      = null;
        this.talonSRX = talonSRX;

        if (defaultState == DefaultState.TRUE) {
            this.defaultState = true;
        } else {
            this.defaultState = false;
        }
    }

    public TLimitSwitch(CANSparkMax sparkMax, TLimitSwitchType limitSwitchType, 
    		DefaultState defaultState) {

        limitSwitch   = null;
        this.sparkMax = sparkMax;
        talonSRX      = null;

        this.limitSwitchType = limitSwitchType;
        
        if (defaultState == DefaultState.TRUE) {
            this.defaultState = true;
        } else {
            this.defaultState = false;
        }
    }

    public boolean atLimit() {
    	
    	if (limitSwitch != null) {
    		return limitSwitch.get() != defaultState;
    	}
    	
    	if (sparkMax != null) {
    		switch (limitSwitchType) {
    		case REVERSE:
    			if (defaultState == true) {
	        		return sparkMax.getReverseLimitSwitch(
	        				CANDigitalInput.LimitSwitchPolarity.kNormallyOpen)
	        				.get();
    			}
    			else {
	        		return sparkMax.getReverseLimitSwitch(
	        				CANDigitalInput.LimitSwitchPolarity.kNormallyClosed)
	        				.get();
    			}
    		case FORWARD:
    			if (defaultState == true) {
	        		return sparkMax.getForwardLimitSwitch(
	        				CANDigitalInput.LimitSwitchPolarity.kNormallyOpen)
	        				.get();
    			}
    			else {
	        		return sparkMax.getForwardLimitSwitch(
	        				CANDigitalInput.LimitSwitchPolarity.kNormallyClosed)
	        				.get();
    			}
    		default:
    			return false;
    		}
    	}
    	
    	if (talonSRX != null) {
    		switch (limitSwitchType) {
    		case REVERSE:
    			if (defaultState == true) {
	        		return talonSRX.limsparkMax.getReverseLimitSwitch(
	        				CANDigitalInput.LimitSwitchPolarity.kNormallyOpen)
	        				.get();
    			}
    			else {
	        		return sparkMax.getReverseLimitSwitch(
	        				CANDigitalInput.LimitSwitchPolarity.kNormallyClosed)
	        				.get();
    			}
    		case FORWARD:
    			if (defaultState == true) {
	        		return sparkMax.getForwardLimitSwitch(
	        				CANDigitalInput.LimitSwitchPolarity.kNormallyOpen)
	        				.get();
    			}
    			else {
	        		return sparkMax.getForwardLimitSwitch(
	        				CANDigitalInput.LimitSwitchPolarity.kNormallyClosed)
	        				.get();
    			}
    		default:
    			return false;
    		}
    	}
    	
    	return false;
    }


}