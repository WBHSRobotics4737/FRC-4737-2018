package org.usfirst.frc.team4737.robot.subsystems;

import org.usfirst.frc.team4737.robot.RobotMap;
import org.usfirst.frc.team4737.robot.commands.intakegrip.StopIntakeGrip;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeGrip extends Subsystem {

	private DoubleSolenoid leftPneumatic;
	private DoubleSolenoid rightPneumatic;

	public IntakeGrip() {
		leftPneumatic = new DoubleSolenoid(RobotMap.INTAKE_LEFT_PISTON_MODULE,
				RobotMap.INTAKE_LEFT_PISTON_FORWARD_CHANNEL, RobotMap.INTAKE_LEFT_PISTON_REVERSE_CHANNEL);
		rightPneumatic = new DoubleSolenoid(RobotMap.INTAKE_RIGHT_PISTON_MODULE,
				RobotMap.INTAKE_RIGHT_PISTON_FORWARD_CHANNEL, RobotMap.INTAKE_RIGHT_PISTON_REVERSE_CHANNEL);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new StopIntakeGrip());
	}

	public void closePneumatics() {
		setPneumatics(Value.kReverse, Value.kReverse);
	}

	public void openPneumatics() {
		setPneumatics(Value.kForward, Value.kForward);
	}

	public void disablePneumatics() {
		setPneumatics(Value.kOff, Value.kOff);
	}

	public void setPneumatics(DoubleSolenoid.Value left, DoubleSolenoid.Value right) {
		leftPneumatic.set(left);
		rightPneumatic.set(right);
	}

}
