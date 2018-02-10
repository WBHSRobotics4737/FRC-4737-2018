package org.usfirst.frc.team4737.robot.subsystems;

import org.usfirst.frc.team4737.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private WPI_TalonSRX motorA;
	private WPI_TalonSRX motorB;

	public Elevator() {
		motorA = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_A);
		motorB = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_B);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
