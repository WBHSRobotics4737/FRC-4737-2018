package org.usfirst.frc.team4737.robot.subsystems;

import org.usfirst.frc.team4737.robot.RobotMap;
import org.usfirst.frc.team4737.robot.commands.drivetrain.TeleopTankDrive;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Drivetrain extends Subsystem {

	private WPI_TalonSRX leftFrontMaster;
	private WPI_TalonSRX rightFrontMaster;
	private WPI_TalonSRX leftBackSlave;
	private WPI_TalonSRX rightBackSlave;

	private DifferentialDrive drive;

	public Drivetrain() {
		leftFrontMaster = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_MASTER);
		leftBackSlave = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_SLAVE);
		rightFrontMaster = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_MASTER);
		rightBackSlave = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_SLAVE);

		leftBackSlave.follow(leftFrontMaster);
		rightBackSlave.follow(rightFrontMaster);

		leftFrontMaster.configOpenloopRamp(0.25, 30);
		rightFrontMaster.configOpenloopRamp(0.25, 30);
		
		leftFrontMaster.configVoltageCompSaturation(12, 30);
		leftBackSlave.configVoltageCompSaturation(12, 30);
		rightFrontMaster.configVoltageCompSaturation(12, 30);
		rightBackSlave.configVoltageCompSaturation(12, 30);

		drive = new DifferentialDrive(leftFrontMaster, rightFrontMaster);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new TeleopTankDrive());
	}

	public void setBrakeMode() {
		leftFrontMaster.setNeutralMode(NeutralMode.Brake);
		leftBackSlave.setNeutralMode(NeutralMode.Brake);
		rightFrontMaster.setNeutralMode(NeutralMode.Brake);
		rightBackSlave.setNeutralMode(NeutralMode.Brake);
	}

	public void setCoastMode() {
		leftFrontMaster.setNeutralMode(NeutralMode.Coast);
		leftBackSlave.setNeutralMode(NeutralMode.Coast);
		rightFrontMaster.setNeutralMode(NeutralMode.Coast);
		rightBackSlave.setNeutralMode(NeutralMode.Coast);
	}
	
	public void enableVoltageCompensation() {
		leftFrontMaster.enableVoltageCompensation(true);
		leftBackSlave.enableVoltageCompensation(true);
		rightFrontMaster.enableVoltageCompensation(true);
		rightBackSlave.enableVoltageCompensation(true);
	}
	
	public void disableVoltageCompensation() {
		leftFrontMaster.enableVoltageCompensation(false);
		leftBackSlave.enableVoltageCompensation(false);
		rightFrontMaster.enableVoltageCompensation(false);
		rightBackSlave.enableVoltageCompensation(false);
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
		drive.tankDrive(leftInput, rightInput);
	}
	
	public void arcadeDrive(double throttle, double steer) {
		drive.arcadeDrive(throttle, steer);
	}

}
