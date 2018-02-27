package org.usfirst.frc.team4737.robot.commands.elevator;

import org.usfirst.frc.team4737.robot.Robot;
import org.usfirst.frc.team4737.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ControlElevator extends Command {

	public ControlElevator() {
		requires(Robot.ELEVATOR);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.ELEVATOR.setBrakeMode();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Control elevator movement, holding it in place by default
		double input = Robot.OI.operator.getAxis("LS_Y").get();

		// Check if we're hitting the top
		//		if (input > 0 && Robot.ELEVATOR.isAtTop()) {
		//			input = 0;
		//		}

		// Make sure we don't go down too fast
		if (input < RobotMap.ELEVATOR_MAX_DOWN_SPEED) {
			input = RobotMap.ELEVATOR_MAX_DOWN_SPEED;
		}

		// Start hold command if no input
		if (input == 0) {
			if (this.isRunning()) {
				new HoldElevator().start();
				this.cancel();
			}
			return;
		}

		// Set elevator speed
		Robot.ELEVATOR.setSpeed(input);

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

}
