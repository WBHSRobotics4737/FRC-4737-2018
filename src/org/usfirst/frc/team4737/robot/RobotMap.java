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

	// #####################
	// Actuator IDs/Channels
	// #####################

	// Drivetrain
	public static final int DRIVE_LEFT_MASTER = 11;
	public static final int DRIVE_LEFT_SLAVE = 12;
	public static final int DRIVE_RIGHT_MASTER = 13;
	public static final int DRIVE_RIGHT_SLAVE = 14;

	// Intake
	public static final int INTAKE_LEFT = 15;
	public static final int INTAKE_RIGHT = 16;

	public static final int INTAKE_LEFT_PISTON_MODULE = 0;
	public static final int INTAKE_LEFT_PISTON_FORWARD_CHANNEL = 0;
	public static final int INTAKE_LEFT_PISTON_REVERSE_CHANNEL = 1;
	public static final int INTAKE_RIGHT_PISTON_MODULE = 0;
	public static final int INTAKE_RIGHT_PISTON_FORWARD_CHANNEL = 2;
	public static final int INTAKE_RIGHT_PISTON_REVERSE_CHANNEL = 3;

	// Elevator
	public static final int ELEVATOR_MOTOR = 17;

	// #########
	// Constants
	// #########

	/**
	 * The voltage to give to the elevator for it to hold constant position with a
	 * cube
	 * 
	 * Increase this value if the elevator droops too much while holding the cube
	 */
	public static final double ELEVATOR_HOLD_V = 1;
	/**
	 * The number of seconds to keep the elevator in place before letting the motor
	 * relax. To disable this, set the value to 0.1
	 */
	public static final double ELEVATOR_HOLD_TIME = 4; // TODO tune
	/**
	 * The maximum speed the elevator is allowed to go down. This prevents
	 * unspooling too quickly, which causes the rope to fall off the spool.
	 */
	public static final double ELEVATOR_MAX_DOWN_SPEED = -0.6;

	/**
	 * The value to multiply the throttle by when slow driving is enabled
	 */
	public static final double DRIVE_SLOW_SCALE = 0.5;

	/*
	 * Adjust these variables to tune autonomous
	 */
	public static final double AUTO_BLIND_TIME = 5;
	public static final double AUTO_BLIND_SPEED = 0.5;
	/**
	 * The steering value to use in autonomous to counteract any natural tendencies
	 * for the robot to veer left or right. A positive value (should) steer the
	 * robot to the right, and negative to the left.
	 * 
	 * Tune this value accordingly. It may need to be adjusted throughout the
	 * competition.
	 */
	public static final double AUTO_BLIND_STEER = 0;

}
