package org.usfirst.frc.team4737.robot.subsystems;

import org.usfirst.frc.team4737.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drivetrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private WPI_TalonSRX leftFrontMaster;
	private WPI_TalonSRX rightFrontMaster;
	private WPI_TalonSRX leftBackSlave;
	private WPI_TalonSRX rightBackSlave;
	
	public Drivetrain() {
		leftFrontMaster = new WPI_TalonSRX(RobotMap.leftDriveMaster);
		leftBackSlave = new WPI_TalonSRX(RobotMap.leftDriveMaster);
		rightFrontMaster = new WPI_TalonSRX(RobotMap.leftDriveMaster);
		rightBackSlave = new WPI_TalonSRX(RobotMap.leftDriveMaster);
		
		leftBackSlave.follow(leftFrontMaster);
		rightBackSlave.follow(rightFrontMaster);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * 
     * @param leftInput - Left joystick input from -1.0 to 1.0
     * @param rightInput - Right joystick input from -1.0 to 1.0
     */
    public void tankDrive(double leftInput, double rightInput) {
    	leftFrontMaster.set(leftInput);
    	rightFrontMaster.set(rightInput);
    }
    
}

