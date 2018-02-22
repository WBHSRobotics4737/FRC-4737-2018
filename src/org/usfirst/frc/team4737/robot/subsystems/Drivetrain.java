package org.usfirst.frc.team4737.robot.subsystems;

import org.usfirst.frc.team4737.robot.RobotMap;
import org.usfirst.frc.team4737.robot.commands.TeleopTankDrive;

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

		drive = new DifferentialDrive(leftFrontMaster, rightFrontMaster);

	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new TeleopTankDrive());
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

}
