package org.usfirst.frc.team4737.lib;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.jblas.DoubleMatrix;

import com.kauailabs.navx.frc.AHRS;

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
				start = curr;
				last = curr;
			}

			ddr.update(curr - last);

			last = curr;
		}

	}

	private Encoder lEnc;
	private Encoder rEnc;
	private AHRS navX;

	Timer updateLoop;

	private double lastLDist;
	private double lastRDist;
	private double lastActualHeading;

	// TODO include buffer of past several seconds of data with timestamps
	// Add function to get position at prev time to allow delayed correction using
	// cameras
	 double x;
	 double y;
	 double heading;

	// Test debug
	private double ldsx;
	private double ldsy;

	public DriveDeadReckoner(Encoder lEnc, Encoder rEnc, AHRS navX, double period) {
		this.lEnc = lEnc;
		this.rEnc = rEnc;
		this.navX = navX;

		updateLoop = new Timer();
		updateLoop.schedule(new UpdateTask(this), 0L, (long) (period * 1000));
	}

	private void update(double dt) {
		double newLDist = lEnc.getDistance();
		double newRDist = rEnc.getDistance();
		double actualHeading = -navX.getAngle() * (Math.PI / 180.0);

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
		double ldsx;
		double ldsy;
		// TODO find out a good value for this
		// We want to maintain accuracy while avoiding precision loss due to high values
		// of r
		// This might not actually be an issue
		if (Math.abs(da) < 0) {
			ldsx = (dl + dr) / 2.0;
			ldsy = 0;
		} else {
			double s = (dl + dr) / 2.0; // Arc length of circular path
			double a = da / dt;

//			double r = s / Math.abs(da); // Radius of circular path
//
//			ldsx = r * Math.cos(da);
//			ldsy = r * Math.sin(da);
			
			ldsx = s * Math.cos(da);
			ldsy = s * Math.sin(da);
		}
		this.ldsx = ldsx;
		this.ldsy = ldsy;

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
	
	public void resetPos() {
		x = 0;
		y = 0;
		heading = 0;
	}

	public static void main(String[] args) {
		System.out.println("Running DriveDeadReckoner Test...");
		try {
			FileWriter writer = new FileWriter("testfiles/reckoner.csv");

			writer.write("time,x,y,heading,ldsx,ldsy\n");
			double period = 0.01;

			DriveDeadReckoner ddr = new DriveDeadReckoner(null, null, null, period);
			ddr.updateLoop.cancel();

			double t = 0;
			double nextT = 0;
			double heading = 0;
			for (nextT += 2; t < nextT; t += period) {
				double dl = 1.0 * period;
				double dr = 1.0 * period; 
				double da = (dr - dl) / (28.0 / 12.0);
				heading += da;
				ddr.calculate(period, dl, dr, da, heading, 0);

				writer.write(String.format("%f,%f,%f,%f,%f,%f\n", t, ddr.x, ddr.y, heading, ddr.ldsx, ddr.ldsy));
			}
			for (nextT += 2; t < nextT; t += period) {
				double dl = 1.0 * period;
				double dr = 2.0 * period;
				double da = (dr - dl) / (28.0 / 12.0);
				heading += da;
				ddr.calculate(period, dl, dr, da, heading, 0);

				writer.write(String.format("%f,%f,%f,%f,%f,%f\n", t, ddr.x, ddr.y, heading, ddr.ldsx, ddr.ldsy));
			}
			for (nextT += 2; t < nextT; t += period) {
				double dl = 3.0 * period;
				double dr = 2.0 * period;
				double da = (dr - dl) / (28.0 / 12.0);
				heading += da;
				ddr.calculate(period, dl, dr, da, heading, 0);

				writer.write(String.format("%f,%f,%f,%f,%f,%f\n", t, ddr.x, ddr.y, heading, ddr.ldsx, ddr.ldsy));
			}
			for (nextT += 28.0 / 12.0 * Math.PI * 2.0 / 3 * 5 / 8; t < nextT; t += period) {
				double dl = 0.0 * period;
				double dr = 3.0 * period;
				double da = (dr - dl) / (28.0 / 12.0);
				heading += da;
				ddr.calculate(period, dl, dr, da, heading, 0);

				writer.write(String.format("%f,%f,%f,%f,%f,%f\n", t, ddr.x, ddr.y, heading, ddr.ldsx, ddr.ldsy));
			}
			for (nextT += 2; t < nextT; t += period) {
				double dl = (1.0 + (t - nextT + 2) * 3) * period;
				double dr = 1.0 * period;
				double da = (dr - dl) / (28.0 / 12.0);
				heading += da;
				ddr.calculate(period, dl, dr, da, heading, 0);

				writer.write(String.format("%f,%f,%f,%f,%f,%f\n", t, ddr.x, ddr.y, heading, ddr.ldsx, ddr.ldsy));
			}
			for (nextT += 4; t < nextT; t += period) {
				double dl = -2.0 * period;
				double dr = Math.sin(t * Math.PI) * period;
				double da = (dr - dl) / (28.0 / 12.0);
				heading += da;
				ddr.calculate(period, dl, dr, da, heading, 0);

				writer.write(String.format("%f,%f,%f,%f,%f,%f\n", t, ddr.x, ddr.y, heading, ddr.ldsx, ddr.ldsy));
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done.");
	}

}
