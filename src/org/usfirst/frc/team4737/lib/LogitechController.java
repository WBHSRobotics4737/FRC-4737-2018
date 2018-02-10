package org.usfirst.frc.team4737.lib;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class LogitechController {
	
	public class Axis {
        private final Joystick controller;
        private final int axis;

        private double deadzone;

        public Axis(Joystick controller, int axis) {
            this.controller = controller;
            this.axis = axis;
        }

        public void setDeadzone(double radius) {
            if (radius < 0) throw new IllegalArgumentException("Deadzone cannot be less than 0.");
            this.deadzone = radius;
        }

        public double getRaw() {
            return controller.getRawAxis(axis);
        }

        public double get() {
            double raw = getRaw();
            if (raw < -deadzone) {
                return scale(raw, -1, -deadzone, -1, 0);
            } else if (raw > deadzone) {
                return scale(raw, deadzone, 1, 0, 1);
            } else {
                return 0;
            }
        }

        private double scale(double val, double valLow, double valHigh, double newLow, double newHigh) {
            double reduced = (val - valLow) / (valHigh - valLow);
            return reduced * (newHigh - newLow) + newLow;
        }
    }

    public class Thumbstick {
        public final Axis X, Y;

        public Thumbstick(Joystick controller, int axisX, int axisY) {
            X = new Axis(controller, axisX);
            Y = new Axis(controller, axisY);
        }
    }
    
    public final Joystick controller;
    public final JoystickButton A;
    public final JoystickButton B;
    public final JoystickButton X;
    public final JoystickButton Y;
    public final JoystickButton RB;
    public final JoystickButton LB;
    public final Thumbstick LS;
    public final Thumbstick RS;
    public final Axis LT;
    public final Axis RT;
    
    public LogitechController(int usbPort) {
    	controller = new Joystick(usbPort);
    	A = new JoystickButton(controller, -1); // TODO map the correct button IDs
    	B = new JoystickButton(controller, -1);
    	X = new JoystickButton(controller, -1);
    	Y = new JoystickButton(controller, -1);
    	RB = new JoystickButton(controller, -1);
    	LB = new JoystickButton(controller, -1);
    	LS = new Thumbstick(controller, -1, -1);
    	RS = new Thumbstick(controller, -1, -1);
    	LT = new Axis(controller, -1);
    	RT = new Axis(controller, -1);
    }
    
    // TODO add rumble if possible

}
