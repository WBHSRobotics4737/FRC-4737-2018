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

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Drivetrain extends Subsystem {

	// Talons and sensors

	private WPI_TalonSRX lfTalon;
	private WPI_TalonSRX rfTalon;
	private WPI_TalonSRX lbTalon;
	private WPI_TalonSRX rbTalon;
	private WPI_TalonSRX[] talons;

	private Encoder leftEnc;
	private Encoder rightEnc;

	private AHRS navX;

	// Controllers

	private JerkLimitedSpeedController jlLeft;
	private JerkLimitedSpeedController jlRight;

	private DifferentialDrive rawDrive;
	private DifferentialDrive smoothDrive;
	private DifferentialDrive currentDrive;

	// Other

	private DriveDeadReckoner position;

	public Drivetrain() {
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

		navX = new AHRS(Port.kMXP, (byte) 200);
		navX.reset();

		jlLeft = new JerkLimitedSpeedController(lfTalon, RobotMap.SMOOTH_MAX_SPEED_PCT, RobotMap.SMOOTH_MAX_ACCEL_PCT,
				RobotMap.SMOOTH_MAX_JERK_PCT);
		jlLeft = new JerkLimitedSpeedController(rfTalon, RobotMap.SMOOTH_MAX_SPEED_PCT, RobotMap.SMOOTH_MAX_ACCEL_PCT,
				RobotMap.SMOOTH_MAX_JERK_PCT);

		smoothDrive = new DifferentialDrive(jlLeft, jlRight);
		rawDrive = new DifferentialDrive(lfTalon, rfTalon);

		currentDrive = rawDrive;
		
		position = new DriveDeadReckoner(leftEnc, rightEnc, navX, 0.005);
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

	/**
	 * Controls the drivetrain using two tank-drive joystick inputs
	 * 
	 * @param leftInput
	 *            - Left joystick input from -1.0 to 1.0
	 * @param rightInput
	 *            - Right joystick input from -1.0 to 1.0
	 */
	public void tankDrive(double leftInput, double rightInput) {
		currentDrive.tankDrive(leftInput, rightInput, true);
	}

	public void arcadeDrive(double throttle, double steer) {
		currentDrive.arcadeDrive(throttle, steer, true);
	}

}
