package org.usfirst.frc.team4737.lib;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;

public class JerkLimitedTalonSRXController implements SpeedController {

	private class ControlTask extends TimerTask {
		private JerkLimitedTalonSRXController wrapper;

		private ControlTask(JerkLimitedTalonSRXController talon) {
			this.wrapper = talon;
		}

		@Override
		public void run() {
			// TODO pass a measured dt
			wrapper.calculate();
			wrapper.updateSpeed();
		}
	}

	private WPI_TalonSRX talon;
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

	private double jerk;

	public JerkLimitedTalonSRXController(WPI_TalonSRX talon, double maxSpeed, double maxAccel, double maxJerk) {
		this.talon = talon;
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
						this.jerk = -jerk;
					} else {
						jerk = Math.min(-(accel * accel) / (2.0 * (targetSpeed - speed)), maxJerk);
						accel = Math.min(accel + jerk * period, maxAccel);
						this.jerk = jerk;
					}
				} else {
					accel = accelIfJerk;
					this.jerk = jerk;
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
						this.jerk = -jerk;
					} else {
						jerk = Math.max(-(accel * accel) / (2.0 * (targetSpeed - speed)), -maxJerk);
						accel = Math.max(accel + jerk * period, -maxAccel);
						this.jerk = jerk;
					}
				} else {
					accel = accelIfJerk;
					this.jerk = jerk;
				}
			}

			// Try to accelerate
			speed = Math.max(speed + (accel + lastAccel) / 2.0 * period, targetSpeed);
		} else {
			jerk = 0;
		}

	}

	private void updateSpeed() {
		talon.set(speed);
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
		talon.stopMotor();
		targetSpeed = 0;
		speed = 0;
		accel = 0;
	}

	public static void main(String[] args) {
		System.out.println("Running JerkLimitedTalonSRXWrapper Test...");
		try {
			FileWriter writer = new FileWriter("testfiles/JerkLimitedTraj.csv");

			writer.write("time,speed,targetSpeed,maxSpeed,accel,maxAccel,jerk,maxJerk\n");

			JerkLimitedTalonSRXController test = new JerkLimitedTalonSRXController(null, 1, 15, 80);
			test.controlLoop.cancel();

			double t = 0;
			double nextT = 0;

			test.set(1);
			for (nextT += 0.5; t < nextT; t += test.period) {
				test.calculate();
				writer.write(String.format("%f,%f,%f,%f,%f,%f,%f,%f\n", t, test.speed, test.targetSpeed, test.maxSpeed,
						test.accel, test.maxAccel, test.jerk, test.maxJerk));
			}
			test.set(-1);
			for (nextT += 0.5; t < nextT; t += test.period) {
				test.calculate();
				writer.write(String.format("%f,%f,%f,%f,%f,%f,%f,%f\n", t, test.speed, test.targetSpeed, test.maxSpeed,
						test.accel, test.maxAccel, test.jerk, test.maxJerk));
			}
			for (nextT += 1; t < nextT; t += test.period) {
				test.set((t - (nextT - 1)) * 2 - 1);
				test.calculate();
				writer.write(String.format("%f,%f,%f,%f,%f,%f,%f,%f\n", t, test.speed, test.targetSpeed, test.maxSpeed,
						test.accel, test.maxAccel, test.jerk, test.maxJerk));
			}
			for (nextT += 1; t < nextT; t += test.period) {
				test.set((t - (nextT - 1)) * -2 + 1);
				test.calculate();
				writer.write(String.format("%f,%f,%f,%f,%f,%f,%f,%f\n", t, test.speed, test.targetSpeed, test.maxSpeed,
						test.accel, test.maxAccel, test.jerk, test.maxJerk));
			}
			for (nextT += 2; t < nextT; t += test.period) {
				test.set(-Math.cos(t * 2.0 * Math.PI));
				test.calculate();
				writer.write(String.format("%f,%f,%f,%f,%f,%f,%f,%f\n", t, test.speed, test.targetSpeed, test.maxSpeed,
						test.accel, test.maxAccel, test.jerk, test.maxJerk));
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done.");
	}

}
