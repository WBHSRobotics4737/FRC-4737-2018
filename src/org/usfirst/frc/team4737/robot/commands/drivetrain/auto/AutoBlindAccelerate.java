package org.usfirst.frc.team4737.robot.commands.drivetrain.auto;

import org.usfirst.frc.team4737.robot.Robot;
import org.usfirst.frc.team4737.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Autonomously accelerates/decelerates the robot to a target speed from a
 * starting speed.
 */
public class AutoBlindAccelerate extends Command {

	private double percentPerSecond;
	private double startPercent;
	private double targetPercent;

	private boolean startLow;

	private double lastTime;
	private double percent;

	/**
	 * 
	 * @param percentPerSecond
	 *            The % speed to accelerate by per second. A value of 1 will
	 *            accelerate the robot from a standstill to full speed in one
	 *            second.
	 * @param startPercent
	 *            The initial speed (between -1.0 and 1.0) of the robot.
	 * @param targetPercent
	 *            The speed (between -1.0 and 1.0) to accelerate to.
	 */
	public AutoBlindAccelerate(double percentPerSecond, double startPercent, double targetPercent) {
		requires(Robot.DRIVETRAIN);
		this.percentPerSecond = percentPerSecond;
		this.startPercent = startPercent;
		this.targetPercent = targetPercent;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		lastTime = 0;
		percent = startPercent;
		startLow = startPercent < targetPercent;
		
		Robot.DRIVETRAIN.enableVoltageCompensation();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double time = this.timeSinceInitialized();
		double delta = time - lastTime;
		lastTime = time;

		// Adjust speed based on time passed
		if (startLow)
			percent += percentPerSecond * delta;
		else
			percent -= percentPerSecond * delta;

		// Check bounds
		if (startLow && percent > targetPercent)
			percent = targetPercent;
		if (!startLow && percent < targetPercent)
			percent = targetPercent;
		if (percent < -1)
			percent = -1;
		if (percent > 1)
			percent = 1;

		// Set drive speed
		Robot.DRIVETRAIN.arcadeDrive(percent, RobotMap.AUTO_BLIND_STEER);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return percent == targetPercent;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

}
