package org.usfirst.frc.team4737.robot.subsystems;

import org.usfirst.frc.team4737.robot.RobotMap;
import org.usfirst.frc.team4737.robot.commands.intake.StopIntake;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

	private WPI_TalonSRX leftMotor;
	private WPI_TalonSRX rightMotor;

	public Intake() {
		leftMotor = new WPI_TalonSRX(RobotMap.INTAKE_LEFT);
		rightMotor = new WPI_TalonSRX(RobotMap.INTAKE_RIGHT);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new StopIntake());
	}

	/**
	 * 
	 * @param speed
	 *            The speed (between -1.0 and 1.0) to run the intake motors at.
	 */
	public void setSpeed(double speed) {
		setLRSpeed(speed, speed);
	}

	public void setLRSpeed(double left, double right) {
		leftMotor.set(left);
		rightMotor.set(right);
	}

}
