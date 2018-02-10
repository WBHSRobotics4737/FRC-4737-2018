package org.usfirst.frc.team4737.robot.subsystems;

import org.usfirst.frc.team4737.robot.RobotMap;
import org.usfirst.frc.team4737.robot.commands.StopIntake;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private WPI_TalonSRX leftMotorMaster;
	private WPI_TalonSRX rightMotorSlave;

	public Intake() {
		leftMotorMaster = new WPI_TalonSRX(RobotMap.INTAKE_LEFT);
		rightMotorSlave = new WPI_TalonSRX(RobotMap.INTAKE_RIGHT);

		rightMotorSlave.setInverted(true);
		rightMotorSlave.follow(leftMotorMaster);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new StopIntake());
	}

	/**
	 * 
	 * @param speed
	 *            ranges from -1 to 1.
	 */
	public void setSpeed(double speed) {
		leftMotorMaster.set(speed);
	}

}
