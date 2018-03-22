package org.usfirst.frc.team4737.lib;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * This class is a thin wrapper around the CANTalon that reduces CAN bus / CPU
 * overhead by skipping duplicate set commands. (By default the Talon flushes
 * the Tx buffer on every set call).
 * 
 * Adapted from Team 254's LazyCANTalon (github.com/Team254/FRC-2017-Public)
 *
 */
public class LazyWPITalonSRX extends WPI_TalonSRX {

	protected double lastSet = Double.NaN;
	protected ControlMode lastControlMode = ControlMode.Disabled;

	public LazyWPITalonSRX(int deviceNumber) {
		super(deviceNumber);
	}

	@Override
	public void set(double speed) {
		set(ControlMode.PercentOutput, speed);
	}

	@Override
	public void set(ControlMode mode, double value) {
		if (value != lastSet || getControlMode() != lastControlMode) {
			super.set(mode, value);
			lastSet = value;
			lastControlMode = getControlMode();
		}
	}

}
