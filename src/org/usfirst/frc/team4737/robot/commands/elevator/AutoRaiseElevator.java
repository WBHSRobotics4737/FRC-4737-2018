package org.usfirst.frc.team4737.robot.commands.elevator;

import org.usfirst.frc.team4737.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AutoRaiseElevator extends TimedCommand {

	private double holdTime;
	
	public AutoRaiseElevator(double time, double holdTime) {
		super(time);
		requires(Robot.ELEVATOR);
		this.holdTime = holdTime;
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
		new HoldElevator(holdTime, false).start();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

}
