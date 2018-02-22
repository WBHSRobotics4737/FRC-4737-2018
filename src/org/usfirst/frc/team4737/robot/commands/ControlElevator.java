package org.usfirst.frc.team4737.robot.commands;

import org.usfirst.frc.team4737.robot.Robot;
import org.usfirst.frc.team4737.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ControlElevator extends Command {

	public ControlElevator() {
		requires(Robot.ELEVATOR);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Control elevator movement, holding it in place by default
		double input = Robot.OI.operator.getAxis("LS_Y").get();

		// Check if we're hitting the top
		if (input > 0 && Robot.ELEVATOR.getMotorCurrent() > RobotMap.ELEVATOR_TOPSTALL_AMPS) {
			input = 0;
		}

		Robot.ELEVATOR.setSpeed(input + RobotMap.ELEVATOR_HOLD_PCT);

		SmartDashboard.putNumber("elevator_input", input);

		// Start hold command if no input
		if (input == 0) {
			new HoldElevator().start();
			this.cancel();
		}
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
