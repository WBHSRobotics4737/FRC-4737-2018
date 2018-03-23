package org.usfirst.frc.team4737.lib;

import edu.wpi.first.wpilibj.command.*;

/**
 * @author Brian Semrau
 * @version Feb. 15, 2017
 */
public class ParallelCommandGroup extends CommandGroup {

	public ParallelCommandGroup(Command... commands) {
		for (Command command : commands)
			addParallel(command);
	}

}
