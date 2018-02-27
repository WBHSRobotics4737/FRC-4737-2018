package org.usfirst.frc.team4737.robot.commands.drivetrain;

import org.usfirst.frc.team4737.robot.Robot;
import org.usfirst.frc.team4737.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopRacingDrive extends Command {

	public TeleopRacingDrive() {
		requires(Robot.DRIVETRAIN);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.DRIVETRAIN.setBrakeMode();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		boolean slow = Robot.OI.driver.getButton("LB").get();
		double throttle = (Robot.OI.driver.getAxis("RT").get() - Robot.OI.driver.getAxis("LT").get())
				* (slow ? RobotMap.DRIVE_SLOW_SCALE : 1);
		double steer = Robot.OI.driver.getThumbstick("LS").X.get() * (slow ? RobotMap.DRIVE_SLOW_SCALE : 1);
		
		Robot.DRIVETRAIN.arcadeDrive(throttle, steer);
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
