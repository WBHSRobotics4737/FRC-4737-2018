package org.usfirst.frc.team4737.robot.commands.intakegrip;

import org.usfirst.frc.team4737.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class OpenIntakeGrip extends InstantCommand {

    public OpenIntakeGrip() {
        super();
        requires(Robot.INTAKEGRIP);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.INTAKEGRIP.openPneumatics();
    }

}
