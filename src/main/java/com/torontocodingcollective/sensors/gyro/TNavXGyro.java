package com.torontocodingcollective.sensors.gyro;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TNavXGyro extends TGyro {

	public enum GyroAxis {YAW, PITCH, ROLL };
	
    private final AHRS navXGyro;
    private GyroAxis curAxis;

    public TNavXGyro() {
        this(false);
    }

    public TNavXGyro(boolean inverted) {
        super(inverted);
        this.navXGyro = new AHRS(Port.kMXP);
    }

    @Override
    public void calibrate() {
        super.setGyroAngle(0);
    }

    @Override
    public double getAngle() {
    	SmartDashboard.putNumber("NavX X",   navXGyro.getRawGyroX());
    	SmartDashboard.putNumber("NavX Y",   navXGyro.getRawGyroY());
    	SmartDashboard.putNumber("NavX Z",   navXGyro.getRawGyroZ());
        return super.getAngle(navXGyro.getAngle());
    }

    @Override
    public double getPitch() {
        return navXGyro.getRoll();
    }

    @Override
    public double getRate() {
        return super.getRate(navXGyro.getRate());
    }

    @Override
    public boolean supportsPitch() {
        return true;
    }

}
