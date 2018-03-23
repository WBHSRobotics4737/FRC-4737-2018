package org.usfirst.frc.team4737.lib;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.SpeedController;

public class JerkLimitedSpeedController implements SpeedController {

	private class ControlTask extends TimerTask {
		private JerkLimitedSpeedController wrapper;

		private ControlTask(JerkLimitedSpeedController talon) {
			this.wrapper = talon;
		}

		@Override
		public void run() {
			// TODO pass a measured dt
			wrapper.calculate();
			wrapper.updateSpeed();
		}
	}

	private SpeedController control;
	private double maxSpeed;
	private double maxAccel;
	private double maxJerk;
	private double period;

	private Timer controlLoop;
	private TimerTask currentTask;
	private boolean active;

	private double targetSpeed;
	private boolean inverted;

	private double speed;
	private double accel;

	public JerkLimitedSpeedController(SpeedController control, double maxSpeed, double maxAccel, double maxJerk) {
		this.control = control;
		this.maxSpeed = maxSpeed;
		this.maxAccel = maxAccel;
		this.maxJerk = maxJerk;
		this.period = 0.005;

		controlLoop = new Timer();
		startUpdates();

		speed = 0;
		accel = 0;
	}

	private void calculate() {
		// TODO figure out why moving a targetSpeed creates immense jerk-noise and
		// accel-noise

		double lastAccel = accel;

		if (speed < targetSpeed) {

			double jerk = maxJerk;
			double accelIfJerk = Math.min(accel + jerk * period, maxAccel);

			if (accel < 0) {
				accel = accelIfJerk;
			} else {
				double accel2AtTargetIfJerk = accelIfJerk * accelIfJerk
						+ 2.0 * -jerk * (targetSpeed - (speed + (accel + accelIfJerk) / 2.0 * period));

				if (accel2AtTargetIfJerk >= 0) {
					double accelIfDejerk = Math.max(accel - jerk * period, 0);
					double accel2AtTargetIfDejerk = accelIfDejerk * accelIfDejerk
							+ 2.0 * -jerk * (targetSpeed - (speed + (accel + accelIfDejerk) / 2.0 * period));
					if (accel2AtTargetIfDejerk >= 0) {
						accel = accelIfDejerk;
					} else {
						jerk = Math.min(-(accel * accel) / (2.0 * (targetSpeed - speed)), maxJerk);
						accel = Math.min(accel + jerk * period, maxAccel);
					}
				} else {
					accel = accelIfJerk;
				}
			}

			// Try to accelerate
			speed = Math.min(speed + (accel + lastAccel) / 2.0 * period, targetSpeed);
		} else if (speed > targetSpeed) {

			double jerk = -maxJerk;
			double accelIfJerk = Math.max(accel + jerk * period, -maxAccel);

			if (accel > 0) {
				accel = accelIfJerk;
			} else {
				double accel2AtTargetIfJerk = accelIfJerk * accelIfJerk
						+ 2.0 * -jerk * (targetSpeed - (speed + (accel + accelIfJerk) / 2.0 * period));

				if (accel2AtTargetIfJerk >= 0) {
					double accelIfDejerk = Math.min(accel - jerk * period, 0);
					double accel2AtTargetIfDejerk = accelIfDejerk * accelIfDejerk
							+ 2.0 * -jerk * (targetSpeed - (speed + (accel + accelIfDejerk) / 2.0 * period));
					if (accel2AtTargetIfDejerk >= 0) {
						accel = accelIfDejerk;
					} else {
						jerk = Math.max(-(accel * accel) / (2.0 * (targetSpeed - speed)), -maxJerk);
						accel = Math.max(accel + jerk * period, -maxAccel);
					}
				} else {
					accel = accelIfJerk;
				}
			}

			// Try to accelerate
			speed = Math.max(speed + (accel + lastAccel) / 2.0 * period, targetSpeed);
		}

	}

	private void updateSpeed() {
		control.set(inverted ? -speed : speed);
	}

	private void startUpdates() {
		controlLoop.schedule(currentTask = new ControlTask(this), 0L, (long) (period * 1000));
		active = true;
	}

	public void pauseUpdates() {
		currentTask.cancel();
		active = false;
	}

	@Override
	public void pidWrite(double output) {
		set(output);
	}

	@Override
	public void set(double speed) {
		targetSpeed = Math.min(Math.max(speed, -maxSpeed), maxSpeed);

		if (!active)
			startUpdates();
	}

	@Override
	public double get() {
		return speed;
	}

	@Override
	public void setInverted(boolean isInverted) {
		inverted = isInverted;
	}

	@Override
	public boolean getInverted() {
		return inverted;
	}

	@Override
	public void disable() {
		stopMotor();
	}

	@Override
	public void stopMotor() {
		control.stopMotor();
		targetSpeed = 0;
		speed = 0;
		accel = 0;
	}

}
