package org.usfirst.frc.team4737.robot.commands.drivetrain;

import org.usfirst.frc.team4737.robot.Robot;
import org.usfirst.frc.team4737.robot.RobotMap;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Drives the robot forward autonomously without sensor input. Drives at a
 * certain speed for a certain time.
 * 
 * This should be used only in conjunction with AutoBlindAccelerate to get up to
 * speed. If the robot does not accelerate first, it may jerk and tip over.
 */
public class AutoBlindDrive extends TimedCommand {

	private double percent;

	/**
	 * 
	 * @param time
	 *            The number of seconds to drive forward for.
	 * @param percent
	 *            The percent output, or speed (between -1.0 and 1.0) to drive at.
	 */
	public AutoBlindDrive(double time, double percent) {
		super(time);
		requires(Robot.DRIVETRAIN);

		this.percent = percent;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.DRIVETRAIN.enableVoltageCompensation();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.DRIVETRAIN.arcadeDrive(percent, RobotMap.AUTO_BLIND_STEER);
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

}
