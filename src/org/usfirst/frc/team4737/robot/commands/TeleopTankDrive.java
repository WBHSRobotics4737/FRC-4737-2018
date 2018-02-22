package org.usfirst.frc.team4737.robot.commands;

import org.usfirst.frc.team4737.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopTankDrive extends Command {

    public TeleopTankDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.DRIVETRAIN);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.DRIVETRAIN.tankDrive(
    			Robot.OI.driver.getThumbstick("LS").Y.get(),
    			Robot.OI.driver.getThumbstick("RS").Y.get());
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