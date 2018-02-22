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
	public static final int ELEVATOR_MOTOR = 17;

	/**
	 * The stall current of the elevator when fully extended
	 */
	public static final double ELEVATOR_TOPSTALL_AMPS = 40; // TODO measure
	/**
	 * The power (-1 to 1) to give to the elevator for it to hold position with a
	 * POWER CUBE
	 */
	public static final double ELEVATOR_HOLD_PCT = 0; // TODO measure

	public static final double ELEVATOR_HOLD_TIME = 3;

}
