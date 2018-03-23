package org.usfirst.frc.team4737.robot.subsystems;

import org.usfirst.frc.team4737.lib.DriveDeadReckoner;
import org.usfirst.frc.team4737.lib.JerkLimitedSpeedController;
import org.usfirst.frc.team4737.lib.LazyWPITalonSRX;
import org.usfirst.frc.team4737.robot.RobotMap;
import org.usfirst.frc.team4737.robot.commands.drivetrain.TeleopRacingDrive;
import org.usfirst.frc.team4737.robot.commands.drivetrain.TeleopTankDrive;

import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drivetrain extends Subsystem {

	private class CombinedPIDOutput {

		private double throttle, steer;

		private PIDOutput throttleOutput = new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				throttle = output;
				output();
			}
		};
		private PIDOutput steerOutput = new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				steer = output;
				output();
			}
		};

		private void output() {
			if (currentDrive != null)
				currentDrive.arcadeDrive(throttle, steer);
		}

	}

	// Talons and sensors

	private WPI_TalonSRX lfTalon;
	private WPI_TalonSRX rfTalon;
	private WPI_TalonSRX lbTalon;
	private WPI_TalonSRX rbTalon;
	private WPI_TalonSRX[] talons;

	private Encoder leftEnc;
	private Encoder rightEnc;

	//	private AHRS navX;
	private ADXRS450_Gyro gyro;

	// Controllers

	private JerkLimitedSpeedController jlLeft;
	private JerkLimitedSpeedController jlRight;

	private DifferentialDrive rawDrive;
	private DifferentialDrive smoothDrive;
	private DifferentialDrive currentDrive;

	private PIDController leftDistControl;
	private PIDController rightDistControl;

	private PIDController avgDistControl;
	private PIDController headingControl;

	// Other

	private DriveDeadReckoner position;

	public Drivetrain() {
		// Talons and sensors

		lfTalon = createDriveTalon(RobotMap.DRIVE_LEFT_MASTER);
		lbTalon = createSlaveTalon(RobotMap.DRIVE_LEFT_SLAVE, lfTalon);
		rfTalon = createDriveTalon(RobotMap.DRIVE_RIGHT_MASTER);
		rbTalon = createSlaveTalon(RobotMap.DRIVE_RIGHT_SLAVE, rfTalon);
		talons = new WPI_TalonSRX[] { lfTalon, lbTalon, rfTalon, rbTalon };

		leftEnc = new Encoder(RobotMap.LEFT_ENC_A, RobotMap.LEFT_ENC_B, true);
		rightEnc = new Encoder(RobotMap.RIGHT_ENC_A, RobotMap.RIGHT_ENC_B, false);
		// Set encoders to units of feet
		leftEnc.setDistancePerPulse(RobotMap.ENC_FEET_PER_PULSE);
		rightEnc.setDistancePerPulse(RobotMap.ENC_FEET_PER_PULSE);

		//		navX = new AHRS(Port.kMXP, (byte) 200);
		//		navX.reset();
		gyro = new ADXRS450_Gyro(Port.kOnboardCS0);
		gyro.calibrate();

		// Controllers

		jlLeft = new JerkLimitedSpeedController(lfTalon, RobotMap.SMOOTH_MAX_SPEED_PCT, RobotMap.SMOOTH_MAX_ACCEL_PCT,
				RobotMap.SMOOTH_MAX_JERK_PCT);
		jlRight = new JerkLimitedSpeedController(rfTalon, RobotMap.SMOOTH_MAX_SPEED_PCT, RobotMap.SMOOTH_MAX_ACCEL_PCT,
				RobotMap.SMOOTH_MAX_JERK_PCT);
		jlRight.setInverted(true);

		smoothDrive = new DifferentialDrive(jlLeft, jlRight);
		rawDrive = new DifferentialDrive(lfTalon, rfTalon);

		currentDrive = rawDrive;

		leftDistControl = new PIDController(RobotMap.DRIVE_DIST_kP, 0, RobotMap.DRIVE_DIST_kD, leftEnc, jlLeft);
		rightDistControl = new PIDController(RobotMap.DRIVE_DIST_kP, 0, RobotMap.DRIVE_DIST_kD, rightEnc, jlRight);

		CombinedPIDOutput combinedOutput = new CombinedPIDOutput();
		avgDistControl = new PIDController(RobotMap.DRIVE_DIST_kP, 0, RobotMap.DRIVE_DIST_kD, new PIDSource() {
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			@Override
			public double pidGet() {
				return leftEnc.getDistance() + rightEnc.getDistance();
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		}, combinedOutput.throttleOutput);
		headingControl = new PIDController(RobotMap.DRIVE_ANGLE_kP, 0, RobotMap.DRIVE_ANGLE_kD, new PIDSource() {
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			@Override
			public double pidGet() {
				double angle = gyro.getAngle() % 360;
				while (angle < 0)
					angle += 360;
				return angle;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		}, combinedOutput.steerOutput);
		headingControl.setInputRange(0, 360);
		headingControl.setContinuous();

		double distTolerance = 1.0 / 12.0;
		double angleTolerance = 5.0;
		leftDistControl.setAbsoluteTolerance(distTolerance);
		rightDistControl.setAbsoluteTolerance(distTolerance);
		avgDistControl.setAbsoluteTolerance(distTolerance);
		headingControl.setAbsoluteTolerance(angleTolerance);

		// Other

		position = new DriveDeadReckoner(leftEnc, rightEnc, gyro, 0.005);
	}

	private WPI_TalonSRX createDriveTalon(int id) {
		WPI_TalonSRX talon = new LazyWPITalonSRX(id);
		talon.configVoltageCompSaturation(12, 100);
		talon.enableVoltageCompensation(true);
		talon.setNeutralMode(NeutralMode.Brake);
		return talon;
	}

	private WPI_TalonSRX createSlaveTalon(int id, IMotorController master) {
		WPI_TalonSRX talon = createDriveTalon(id);
		talon.follow(master);
		return talon;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new TeleopRacingDrive());
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("gy_A", gyro.getAngle());
	}

	public void setBrakeMode() {
		for (WPI_TalonSRX talon : talons)
			talon.setNeutralMode(NeutralMode.Brake);
	}

	public void setCoastMode() {
		for (WPI_TalonSRX talon : talons)
			talon.setNeutralMode(NeutralMode.Coast);
	}

	public void enableVoltageCompensation() {
		for (WPI_TalonSRX talon : talons)
			talon.enableVoltageCompensation(true);
	}

	public void disableVoltageCompensation() {
		for (WPI_TalonSRX talon : talons)
			talon.enableVoltageCompensation(false);
	}

	public void setSmoothDrive() {
		if (currentDrive == smoothDrive)
			return;
		rawDrive.setSafetyEnabled(false);
		smoothDrive.setSafetyEnabled(true);
		currentDrive = smoothDrive;
	}

	public void setRawDrive() {
		if (currentDrive == rawDrive)
			return;
		smoothDrive.setSafetyEnabled(false);
		rawDrive.setSafetyEnabled(true);
		currentDrive = rawDrive;
	}

	public boolean isRawDrive() {
		return currentDrive == rawDrive;
	}

	/**
	 * Controls the drivetrain using two tank-drive joystick inputs
	 * 
	 * @param leftInput
	 *            - Left joystick input from -1.0 to 1.0
	 * @param rightInput
	 *            - Right joystick input from -1.0 to 1.0
	 */
	public void tankDrive(double leftInput, double rightInput) {
		disableTargets();
		if (currentDrive == null)
			setRawDrive();

		currentDrive.tankDrive(leftInput, rightInput, true);
	}

	public void arcadeDrive(double throttle, double steer) {
		disableTargets();
		if (currentDrive == null)
			setRawDrive();

		currentDrive.arcadeDrive(throttle, steer, true);
	}

	public void setDistanceTarget(double leftDist, double rightDist) {
		disableCombinedTarget();
		rawDrive.setSafetyEnabled(false);
		smoothDrive.setSafetyEnabled(false);
		currentDrive = null;
		enableVoltageCompensation();

		// Controllers are given a 'globalized' value because resetting the encoders
		// will break dead reckoning
		leftDistControl.setSetpoint(leftEnc.getDistance() + leftDist);
		rightDistControl.setSetpoint(rightEnc.getDistance() + rightDist);
		leftDistControl.enable();
		rightDistControl.enable();
	}

	public void setCombinedTarget(double distance, double angle, boolean globalAngle) {
		disableDistanceTarget();
		setSmoothDrive();
		enableVoltageCompensation();

		// Globalize encoders
		avgDistControl.setSetpoint((leftEnc.getDistance() + rightEnc.getDistance()) / 2.0 + distance);
		double setpointAngle;
		if (globalAngle) {
			setpointAngle = angle % 360;
		} else {
			setpointAngle = (angle + gyro.getAngle()) % 360;
		}
		while (setpointAngle < 0)
			setpointAngle += 360;
		headingControl.setSetpoint(setpointAngle);
		avgDistControl.enable();
		headingControl.enable();
	}

	private void disableDistanceTarget() {
		leftDistControl.reset();
		rightDistControl.reset();
	}

	private void disableCombinedTarget() {
		avgDistControl.reset();
		headingControl.reset();
	}

	public void disableTargets() {
		disableDistanceTarget();
		disableCombinedTarget();
	}

	public boolean targetsMet() {
		if (leftDistControl.isEnabled())
			if (!leftDistControl.onTarget())
				return false;
		if (rightDistControl.isEnabled())
			if (!rightDistControl.onTarget())
				return false;
		if (avgDistControl.isEnabled())
			if (!avgDistControl.onTarget())
				return false;
		if (headingControl.isEnabled())
			if (!headingControl.onTarget())
				return false;
		return true;
	}

}
