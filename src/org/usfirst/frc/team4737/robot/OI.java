/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4737.robot;

import org.usfirst.frc.team4737.lib.Gamepad;
import org.usfirst.frc.team4737.lib.XboxController;
import org.usfirst.frc.team4737.lib.F310Gamepad;
import org.usfirst.frc.team4737.robot.commands.drivetrain.*;
import org.usfirst.frc.team4737.robot.commands.elevator.*;
import org.usfirst.frc.team4737.robot.commands.intake.*;
import org.usfirst.frc.team4737.robot.commands.intakegrip.*;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());

	public Gamepad driver;
	public Gamepad operator;

	public OI() {
		driver = new XboxController(0);
		operator = new F310Gamepad(1);

		// User override to take control of the intake
		new Trigger() {
			public boolean get() {
				return operator.getAxis("LT").get() != 0 || operator.getAxis("RT").get() != 0
						|| operator.getAxis("RS_X").get() != 0;
			}
		}.whileActive(new ControlIntake());

		// User override to take control of driving
		new Trigger() {
			public boolean get() {
				return driver.getThumbstick("LS").Y.get() != 0 || driver.getThumbstick("RS").Y.get() != 0;
			}
		}.whileActive(new TeleopRacingDrive());

		// User override to take control of the elevator
		new Trigger() {
			public boolean get() {
				return operator.getAxis("LS_Y").get() != 0;
			}
		}.whileActive(new ControlElevator());

		// Allow operator to instantly relax elevator in case of motor overheat
		operator.getButton("Y").whileHeld(new RelaxElevator());

		// Take control of intake grip
		operator.getButton("A").whileHeld(new ControlIntakeGrip());
		operator.getButton("B").whileHeld(new ControlIntakeGrip());

		// operator.getDPad("DPAD").LEFT.whileActive(new TwistIntake(1));
		// operator.getDPad("DPAD").RIGHT.whileActive(new TwistIntake(-1));

	}

}
