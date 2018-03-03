package org.usfirst.frc.team4737.robot.commands.elevator;

import org.usfirst.frc.team4737.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Relaxes the elevator. Allows it to fall down on its own and disables any
 * motor output.
 */
public class RelaxElevator extends Command {

	public RelaxElevator() {
		requires(Robot.ELEVATOR);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.ELEVATOR.setCoastMode();
		Robot.ELEVATOR.setHoldPosition(false);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.ELEVATOR.setSpeed(0);
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
