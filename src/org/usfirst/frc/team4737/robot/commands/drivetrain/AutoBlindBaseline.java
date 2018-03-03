package org.usfirst.frc.team4737.robot.commands.drivetrain;

import org.usfirst.frc.team4737.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBlindBaseline extends CommandGroup {

	public AutoBlindBaseline() {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.

		/*
		 * Accelerates the robot to the configured speed, drives for the configured
		 * time, and decelerates to a stop.
		 * 
		 * If the robot drives too far, decrease the time. If the robot drives too fast,
		 * decrease the speed. If the robot speeds up too fast, decrease the
		 * acceleration. Likewise, if it takes too long to speed up, increase
		 * acceleration.
		 * 
		 * If the robot veers left or right, adjust the AUTO_BLIND_STEER value in RobotMap.
		 */
		double acceleration = 0.1;
		addSequential(new AutoBlindAccelerate(acceleration, 0, RobotMap.AUTO_BLIND_SPEED));
		addSequential(new AutoBlindDrive(RobotMap.AUTO_BLIND_TIME, RobotMap.AUTO_BLIND_SPEED));
		addSequential(new AutoBlindAccelerate(acceleration, RobotMap.AUTO_BLIND_SPEED, 0));
	}

}
