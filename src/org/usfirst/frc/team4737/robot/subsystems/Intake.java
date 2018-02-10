package org.usfirst.frc.team4737.robot.subsystems;

import org.usfirst.frc.team4737.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private WPI_TalonSRX leftMotor;
	private WPI_TalonSRX rightMotor;

	public Intake() {
		leftMotor = new WPI_TalonSRX(RobotMap.INTAKE_LEFT);
		rightMotor = new WPI_TalonSRX(RobotMap.INTAKE_RIGHT);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
