package org.usfirst.frc.team4737.robot.commands.elevator;

import org.usfirst.frc.team4737.robot.Robot;
import org.usfirst.frc.team4737.robot.RobotMap;
import org.usfirst.frc.team4737.robot.commands.BuzzController;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class HoldElevator extends TimedCommand {

	public HoldElevator() {
		super(RobotMap.ELEVATOR_HOLD_TIME);
		requires(Robot.ELEVATOR);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.ELEVATOR.setSpeed(RobotMap.ELEVATOR_HOLD_PCT);

		// If one second remaining, buzz controller
		if (RobotMap.ELEVATOR_HOLD_TIME - this.timeSinceInitialized() < 1.0) {
			new BuzzController(1, 0.5, Robot.OI.operator).start();
		}
	}

	// Called once after timeout
	protected void end() {
		new RelaxElevator().start();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

}
