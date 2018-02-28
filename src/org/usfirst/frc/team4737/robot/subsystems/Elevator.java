package org.usfirst.frc.team4737.robot.subsystems;

import org.usfirst.frc.team4737.robot.RobotMap;
import org.usfirst.frc.team4737.robot.commands.elevator.StopElevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
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

	private boolean hold;

	public Elevator() {
		motor = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR);
		motor.setInverted(true);
		// Prevent motor from stalling too hard
		motor.configContinuousCurrentLimit(30, 30);
		motor.enableCurrentLimit(true);
		
		// Use voltage compensation to keep inputs reliable
		motor.configVoltageCompSaturation(12, 30);
		motor.enableVoltageCompensation(true);
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
//		SmartDashboard.putNumber("elevator_voltage", motor.getMotorOutputVoltage());

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
		motor.set(ControlMode.PercentOutput, speed + (hold ? RobotMap.ELEVATOR_HOLD_V / motor.getBusVoltage() : 0));
	}

	public void setHoldPosition(boolean hold) {
		this.hold = hold;
	}

	public void setBrakeMode() {
		motor.setNeutralMode(NeutralMode.Brake);
	}

	public void setCoastMode() {
		motor.setNeutralMode(NeutralMode.Coast);
	}

}
