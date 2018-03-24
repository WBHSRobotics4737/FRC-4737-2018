package org.usfirst.frc.team4737.robot.commands.intake;

import org.usfirst.frc.team4737.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReverseIntake extends Command {

	public ReverseIntake(double timeout) {
		super(timeout);
		requires(Robot.INTAKE);
	}
	
	public ReverseIntake() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.INTAKE);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.INTAKE.setSpeed(-1);
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
