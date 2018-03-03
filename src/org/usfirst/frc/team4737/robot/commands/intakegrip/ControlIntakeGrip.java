package org.usfirst.frc.team4737.robot.commands.intakegrip;

import org.usfirst.frc.team4737.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ControlIntakeGrip extends Command {

	public ControlIntakeGrip() {
		requires(Robot.INTAKEGRIP);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		boolean grab = Robot.OI.operator.getButton("A").get();
		boolean release = Robot.OI.operator.getButton("B").get();

		if (grab) {
			Robot.INTAKEGRIP.closePneumatics();
		} else if (release) {
			Robot.INTAKEGRIP.openPneumatics();
		}
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
	}

}
