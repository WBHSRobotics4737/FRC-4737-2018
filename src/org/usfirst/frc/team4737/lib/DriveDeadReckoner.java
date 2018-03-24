package org.usfirst.frc.team4737.lib;

import java.util.Timer;
import java.util.TimerTask;

import org.jblas.DoubleMatrix;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;

public class DriveDeadReckoner {

	private class UpdateTask extends TimerTask {

		private DriveDeadReckoner ddr;
		private double start;
		private double last;

		private UpdateTask(DriveDeadReckoner ddr) {
			this.ddr = ddr;
			start = -1;
			last = 0;
		}

		@Override
		public void run() {
			double curr = edu.wpi.first.wpilibj.Timer.getFPGATimestamp();
			if (start == -1) {
//				if (gyro.isCalibrating())
//					return;
				start = curr;
				last = curr;
			}

			ddr.update(curr - last);

			last = curr;
		}

	}

	private Encoder lEnc;
	private Encoder rEnc;
	private ADXRS450_Gyro gyro;

	Timer updateLoop;

	private double lastLDist;
	private double lastRDist;
	private double lastActualHeading;

	// TODO include buffer of past several seconds of data with timestamps
	// Add function to get position at prev time to allow delayed correction using
	// cameras
	private double x;
	private double y;
	private double heading;

	public DriveDeadReckoner(Encoder lEnc, Encoder rEnc, ADXRS450_Gyro navX, double period) {
		this.lEnc = lEnc;
		this.rEnc = rEnc;
		this.gyro = navX;

		updateLoop = new Timer();
		updateLoop.schedule(new UpdateTask(this), 0L, (long) (period * 1000));
	}

	private void update(double dt) {
		double newLDist = lEnc.getDistance();
		double newRDist = rEnc.getDistance();
		double actualHeading = gyro.getAngle() * (Math.PI / 180.0);

		double dl = newLDist - lastLDist;
		double dr = newRDist - lastRDist;
		double da = actualHeading - lastActualHeading; // Arc angle of circular path

		heading += da;

		double offset = 0; // TODO determine center of rotation offset based on navX pitch

		calculate(dt, dl, dr, da, heading, offset);

		lastLDist = newLDist;
		lastRDist = newRDist;
		lastActualHeading = actualHeading;
	}

	void calculate(double dt, double dl, double dr, double da, double heading, double offset) {
		double s = (dl + dr) / 2.0; // Arc length of circular path

		double ldsx = s * Math.cos(da);
		double ldsy = s * Math.sin(da);

		// Column vector of local displacement
		DoubleMatrix local_dS = new DoubleMatrix(new double[] { ldsx, ldsy, 1 });

		// Convert local displacement to global displacement
		DoubleMatrix trans = new DoubleMatrix(new double[][] { { 1, 0, 0 }, { 0, 1, -offset }, { 0, 0, 1 } });
		DoubleMatrix rot = new DoubleMatrix(new double[][] { { Math.cos(heading), -Math.sin(heading), 0 },
				{ Math.sin(heading), Math.cos(heading), 0 }, { 0, 0, 1 } });
		DoubleMatrix dS = trans.mmul(rot.mmul(local_dS));

		x += dS.get(0, 0);
		y += dS.get(1, 0);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getHeading() {
		return heading;
	}

	public void reset() {
		x = 0;
		y = 0;
		heading = 0;
	}

}
