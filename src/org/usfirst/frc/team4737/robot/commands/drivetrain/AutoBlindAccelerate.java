package org.usfirst.frc.team4737.robot.commands.drivetrain;

import org.usfirst.frc.team4737.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoBlindAccelerate extends Command {

	private double percentPerSecond;
	private double startPercent;
	private double targetPercent;
	
	private boolean startLow;
	
	private double lastTime;
	private double percent;
	
    public AutoBlindAccelerate(double percentPerSecond, double startPercent, double targetPercent) {
        requires(Robot.DRIVETRAIN);
        this.percentPerSecond = percentPerSecond;
        this.startPercent = startPercent;
        this.targetPercent = targetPercent;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lastTime = 0;
    	percent = startPercent;
    	startLow = startPercent < targetPercent;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double time = this.timeSinceInitialized();
    	double delta = time - lastTime;
    	lastTime = time;
    	
    	// Adjust speed
    	if (startLow) percent += percentPerSecond * delta;
    	else percent -= percentPerSecond * delta; 
    	
    	// Check bounds
    	if (startLow && percent > targetPercent) percent = targetPercent;
    	if (!startLow && percent < targetPercent) percent = targetPercent;
    	if (percent < -1) percent = -1;
    	if (percent > 1) percent = 1;
    	
    	// Apply speed
    	Robot.DRIVETRAIN.arcadeDrive(percent, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return percent == targetPercent;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
}
