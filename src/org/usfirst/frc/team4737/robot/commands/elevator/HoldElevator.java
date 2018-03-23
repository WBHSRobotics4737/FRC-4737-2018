package org.usfirst.frc.team4737.robot.commands.elevator;

import org.usfirst.frc.team4737.robot.Robot;
import org.usfirst.frc.team4737.robot.RobotMap;
import org.usfirst.frc.team4737.robot.commands.BuzzController;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class HoldElevator extends TimedCommand {

	private boolean startedBuzz;

	public HoldElevator(double time, boolean buzz) {
		super(time);
		requires(Robot.ELEVATOR);
		startedBuzz = buzz;
	}

	public HoldElevator() {
		super(RobotMap.ELEVATOR_HOLD_TIME);
		requires(Robot.ELEVATOR);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.ELEVATOR.setHoldPosition(true);
		Robot.ELEVATOR.setBrakeMode();

		startedBuzz = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.ELEVATOR.setSpeed(0);

		// If one second remaining, buzz controller to notify operator
		if (RobotMap.ELEVATOR_HOLD_TIME - this.timeSinceInitialized() < 1.0 && !startedBuzz) {
			new BuzzController(1, 0.3, Robot.OI.operator).start();
			startedBuzz = true;
		}
	}

	// Called once after timeout
	protected void end() {
		new RelaxElevator().start();
		Robot.ELEVATOR.setHoldPosition(false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.ELEVATOR.setHoldPosition(false);
	}

}
