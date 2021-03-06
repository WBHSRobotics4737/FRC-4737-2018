package org.usfirst.frc.team4737.robot.commands.intake;

import org.usfirst.frc.team4737.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ControlIntake extends Command {

	public ControlIntake() {
		requires(Robot.INTAKE);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double speed = Robot.OI.operator.getAxis("LT").get() - Robot.OI.operator.getAxis("RT").get();
		double twist = Robot.OI.operator.getAxis("RS_X").get();
		Robot.INTAKE.setLRSpeed(speed - twist, speed + twist);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.INTAKE.setSpeed(0);
	}
	
}
