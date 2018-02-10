/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4737.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// Drivetrain
	public static final int DRIVE_LEFT_MASTER = 11;
	public static final int DRIVE_LEFT_SLAVE = 12;
	public static final int DRIVE_RIGHT_MASTER = 13;
	public static final int DRIVE_RIGHT_SLAVE = 14;

	// Intake
	public static final int INTAKE_LEFT = 15;
	public static final int INTAKE_RIGHT = 16;

	// Elevator
	public static final int ELEVATOR_MOTOR_A = 17;
	public static final int ELEVATOR_MOTOR_B = 18;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
