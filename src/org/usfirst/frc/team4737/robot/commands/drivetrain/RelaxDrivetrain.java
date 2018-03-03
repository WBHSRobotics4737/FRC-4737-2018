package org.usfirst.frc.team4737.robot.commands.drivetrain;

import org.usfirst.frc.team4737.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Configures the drivetrain to coast mode. This allows the robot to be pushed
 * around more easily.
 */
public class RelaxDrivetrain extends InstantCommand {

	public RelaxDrivetrain() {
		requires(Robot.DRIVETRAIN);
		this.setRunWhenDisabled(true);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.DRIVETRAIN.setCoastMode();
	}

}
