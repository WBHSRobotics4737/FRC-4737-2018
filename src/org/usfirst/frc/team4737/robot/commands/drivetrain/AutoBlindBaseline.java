package org.usfirst.frc.team4737.robot.commands.drivetrain;

import org.usfirst.frc.team4737.robot.Robot;
import org.usfirst.frc.team4737.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoBlindBaseline extends Command {

	public AutoBlindBaseline() {
		requires(Robot.DRIVETRAIN);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.DRIVETRAIN.setBrakeMode();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.DRIVETRAIN.arcadeDrive(RobotMap.AUTO_BLIND_SPEED, 0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return this.timeSinceInitialized() > RobotMap.AUTO_BLIND_TIME;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.DRIVETRAIN.arcadeDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		this.end();
	}

}
