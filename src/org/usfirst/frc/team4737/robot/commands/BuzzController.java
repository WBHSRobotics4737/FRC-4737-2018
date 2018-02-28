package org.usfirst.frc.team4737.robot.commands;

import org.usfirst.frc.team4737.lib.Gamepad;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class BuzzController extends TimedCommand {

	private double strength;
	private Gamepad controller;

	public BuzzController(double timeout, double strength, Gamepad controller) {
		super(timeout);
		// requires(none);
		this.strength = strength;
		this.controller = controller;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		controller.setRumble(strength);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Called once after timeout
	protected void end() {
		controller.setRumble(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
	
}
