package org.usfirst.frc.team4737.robot.commands.elevator;

import org.usfirst.frc.team4737.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Default command for the elevator. Disables all motor output, but puts
 * elevator in brake mode to prevent it from falling too fast. Keep in mind that
 * enabling this command does not put the elevator in a safe state. That is, it
 * does not stop it from chopping off fingers.
 */
public class StopElevator extends Command {

	public StopElevator() {
		requires(Robot.ELEVATOR);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.ELEVATOR.setBrakeMode();
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
