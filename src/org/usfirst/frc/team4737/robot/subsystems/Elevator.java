package org.usfirst.frc.team4737.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private CANTalon elevatorMotor;
	private CANTalon elevatorSpool;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

