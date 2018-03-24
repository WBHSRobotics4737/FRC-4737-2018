package org.usfirst.frc.team4737.robot.commands.intake;

import org.usfirst.frc.team4737.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AutoDropCube extends CommandGroup {
	
	private class DropCube extends TimedCommand {

		public DropCube(double timeout) {
			super(timeout);
			requires(Robot.INTAKEGRIP);
		}
		
		@Override
		protected void initialize() {
			Robot.INTAKEGRIP.closePneumatics();
		}

		@Override
		protected void end() {
		}
		
	}

	public AutoDropCube() {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		//      addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		//      addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.

		addParallel(new ReverseIntake());
		addSequential(new DropCube(1.5));
		
		addSequential(new StopIntake());
	}
}
