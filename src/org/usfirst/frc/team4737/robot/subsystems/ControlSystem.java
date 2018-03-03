package org.usfirst.frc.team4737.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ControlSystem extends Subsystem {

	private Compressor compressor;
	public PowerDistributionPanel pdp;

	public ControlSystem() {
		compressor = new Compressor(0);
		pdp = new PowerDistributionPanel();
		
		// Enable the compressor
		enableCompressor();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void enableCompressor() {
		compressor.setClosedLoopControl(true);
	}
	
	public void disableCompressor() {
		compressor.setClosedLoopControl(false);
	}

}
