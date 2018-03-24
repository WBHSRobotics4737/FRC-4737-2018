package org.usfirst.frc.team4737.robot.commands.auto;

import org.usfirst.frc.team4737.lib.ParallelCommandGroup;
import org.usfirst.frc.team4737.robot.Robot;
import org.usfirst.frc.team4737.robot.commands.drivetrain.auto.AutoDriveCombined;
import org.usfirst.frc.team4737.robot.commands.drivetrain.auto.AutoDriveForward;
import org.usfirst.frc.team4737.robot.commands.elevator.AutoRaiseElevator;
import org.usfirst.frc.team4737.robot.commands.elevator.RelaxElevator;
import org.usfirst.frc.team4737.robot.commands.intake.AutoDropCube;
import org.usfirst.frc.team4737.robot.commands.intakegrip.CloseIntakeGrip;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

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
    	
    	// Make sure robot has grip on the cube
    	addSequential(new CloseIntakeGrip());
    	
    	// Navigate to the switch
    	addSequential(new AutoDriveForward(5));
//    	addSequential(new AutoDriveCombined(0, Robot.getInstance().leftSwitch() ? 90 : -90, true));
    	addSequential(new AutoDriveForward(4.5));
    	
    	// Begin raising elevator, finish navigating
    	addSequential(new ParallelCommandGroup(new AutoRaiseElevator(1.5, 10)/*, new AutoDriveCombined(0, 0, true)*/));
    	addSequential(new AutoDriveForward(9 - 27.2 / 2.0));
    	
    	// Drop cube
    	addSequential(new AutoDropCube());
    	
    	// Back off and drop elevator
    	addSequential(new AutoDriveForward(-1.5));
    	addSequential(new RelaxElevator());
    }
}
