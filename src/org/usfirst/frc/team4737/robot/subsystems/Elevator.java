package org.usfirst.frc.team4737.robot.subsystems;

import org.usfirst.frc.team4737.robot.RobotMap;
import org.usfirst.frc.team4737.robot.commands.StopElevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator extends Subsystem {

	private WPI_TalonSRX motor;

	private double current;
	private double lastCurrent;
	private final double retention = 0.1;

	public Elevator() {
		motor = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new StopElevator());
	}

	@Override
	public void periodic() {
		// Measure current and apply a basic noise filter
		double temp = current;
		current = motor.getOutputCurrent() * (1 - retention) + lastCurrent * retention;
		lastCurrent = temp;

		SmartDashboard.putNumber("elevator_current", current);
	}

	public double getMotorCurrent() {
		return current;
	}

	/**
	 * 
	 * @param speed
	 *            ranges from -1.0 to 1.0
	 */
	public void setSpeed(double speed) {
		motor.set(ControlMode.PercentOutput, speed);
	}
	
	public void setBrakeMode() {
		motor.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setCoastMode() {
		motor.setNeutralMode(NeutralMode.Coast);
	}

}
