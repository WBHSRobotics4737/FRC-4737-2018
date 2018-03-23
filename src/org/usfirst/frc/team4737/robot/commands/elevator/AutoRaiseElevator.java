package org.usfirst.frc.team4737.robot.commands.elevator;

import org.usfirst.frc.team4737.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AutoRaiseElevator extends TimedCommand {

	public AutoRaiseElevator(double time) {
		super(time);
		requires(Robot.ELEVATOR);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.ELEVATOR.setSpeed(1.0);
	}

	// Called once after timeout
	protected void end() {
		new HoldElevator(5, false).start();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

}
