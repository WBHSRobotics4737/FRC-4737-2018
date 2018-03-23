package org.usfirst.frc.team4737.robot.commands.auto;

import org.usfirst.frc.team4737.lib.ParallelCommandGroup;
import org.usfirst.frc.team4737.robot.Robot;
import org.usfirst.frc.team4737.robot.commands.drivetrain.auto.AutoDriveCombined;
import org.usfirst.frc.team4737.robot.commands.drivetrain.auto.AutoDriveForward;
import org.usfirst.frc.team4737.robot.commands.elevator.AutoRaiseElevator;
import org.usfirst.frc.team4737.robot.commands.intake.AutoDropCube;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSwitch extends CommandGroup {

    public AutoSwitch() {
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
    	
    	addSequential(new AutoDriveForward(5));
    	addSequential(new AutoDriveCombined(0, Robot.getInstance().leftSwitch() ? 90 : -90, true));
    	addSequential(new AutoDriveForward(4.5));
    	addSequential(new ParallelCommandGroup(new AutoRaiseElevator(1.5), new AutoDriveCombined(0, 0, true)));
    	addSequential(new AutoDriveForward(9 - 27.2 / 2.0));
    	addSequential(new AutoDropCube());
    }
}
