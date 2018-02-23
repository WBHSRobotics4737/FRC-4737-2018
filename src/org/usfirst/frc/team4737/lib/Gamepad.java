package org.usfirst.frc.team4737.lib;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public abstract class Gamepad {

	public class GamepadButton extends JoystickButton {

		private String name;

		public GamepadButton(Gamepad gamepad, String name, int buttonNumber) {
			super(gamepad.gamepad, buttonNumber);

			this.name = name;

			gamepad.registerButton(this, name);
		}

		public String getNameID() {
			return name;
		}

	}

	public abstract class Axis {

		protected final Gamepad gamepad;

		protected String name;

		public Axis(Gamepad gamepad, String name) {
			this.gamepad = gamepad;

			this.name = name;

			gamepad.registerAxis(this, name);
		}

		public abstract double get();

		public String getName() {
			return name;
		}

	}

	public class GamepadAxis extends Axis {

		private final int axis;

		private double deadzone;

		public GamepadAxis(Gamepad gamepad, String name, int axis) {
			super(gamepad, name);
			this.axis = axis;

			gamepad.registerAxis(this, name);
		}

		public void setDeadzone(double radius) {
			if (radius < 0)
				throw new IllegalArgumentException("Deadzone cannot be less than 0.");
			this.deadzone = radius;
		}

		public double getRaw() {
			return gamepad.gamepad.getRawAxis(axis);
		}

		@Override
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

	public class DPad {

		public class DPadButton extends Button {

			private final DPad dpad;
			private final int degree;

			private String name;

			public DPadButton(DPad dpad, String name, int degree) {
				this.dpad = dpad;
				this.degree = degree;

				this.name = name;

				dpad.gamepad.registerButton(this, name);
			}

			@Override
			public boolean get() {
				return gamepad.gamepad.getPOV(dpad.id) == degree;
			}

			public String getNameID() {
				return name;
			}

		}

		public class DPadAxis extends Axis {

			private DPadButton[] negative;
			private DPadButton[] positive;

			public DPadAxis(Gamepad gamepad, String name, DPadButton[] negative, DPadButton[] positive) {
				super(gamepad, name);
				this.negative = negative;
				this.positive = positive;
			}

			@Override
			public double get() {
				boolean neg = false;
				for (int i = 0; i < negative.length; i++) {
					if (negative[i].get()) {
						neg = true;
						break;
					}
				}
				boolean pos = false;
				for (int i = 0; i < positive.length; i++) {
					if (positive[i].get()) {
						pos = true;
						break;
					}
				}

				if (neg && pos)
					return 0;
				else if (neg)
					return -1;
				else if (pos)
					return 1;
				else
					return 0;
			}

		}

		private final Gamepad gamepad;
		private final int id;

		private String name;

		public final DPadButton UP;
		public final DPadButton UP_RIGHT;
		public final DPadButton RIGHT;
		public final DPadButton DOWN_RIGHT;
		public final DPadButton DOWN;
		public final DPadButton DOWN_LEFT;
		public final DPadButton LEFT;
		public final DPadButton UP_LEFT;
		public final DPadAxis X;
		public final DPadAxis Y;

		public DPad(Gamepad gamepad, String name, int id) {
			this.gamepad = gamepad;
			this.id = id;

			this.name = name;

			UP = new DPadButton(this, name + "_UP", 0);
			UP_RIGHT = new DPadButton(this, name + "_UP_RIGHT", 45);
			RIGHT = new DPadButton(this, name + "_RIGHT", 90);
			DOWN_RIGHT = new DPadButton(this, name + "_DOWN_RIGHT", 135);
			DOWN = new DPadButton(this, name + "_DOWN", 180);
			DOWN_LEFT = new DPadButton(this, name + "_DOWN_LEFT", 225);
			LEFT = new DPadButton(this, name + "_LEFT", 270);
			UP_LEFT = new DPadButton(this, name + "_UP_LEFT", 315);
			X = new DPadAxis(gamepad, name + "_X", new DPadButton[] { LEFT, DOWN_LEFT, UP_LEFT },
					new DPadButton[] { RIGHT, DOWN_RIGHT, UP_RIGHT });
			Y = new DPadAxis(gamepad, name + "_Y", new DPadButton[] { DOWN, DOWN_LEFT, DOWN_RIGHT },
					new DPadButton[] { UP, UP_LEFT, UP_RIGHT });
			
			gamepad.registerDPad(this, name);
		}

		/**
		   * Get the angle in degrees of the DPad.
		   *
		   * <p>The POV angles start at 0 in the up direction, and increase clockwise (eg right is 90,
		   * upper-left is 315).
		   *
		   * @return the angle of the POV in degrees, or -1 if the POV is not pressed.
		   */
		public int getDegree() {
			return gamepad.gamepad.getPOV(id);
		}

		public String getName() {
			return name;
		}

	}

	public class Thumbstick {

		public final GamepadAxis X, Y;

		private String name;

		public Thumbstick(Gamepad gamepad, String name, int axisX, int axisY) {
			X = new GamepadAxis(gamepad, name + "_X", axisX);
			Y = new GamepadAxis(gamepad, name + "_Y", axisY);

			this.name = name;

			gamepad.registerThumbstick(this, name);
		}

		public String getName() {
			return name;
		}

	}

	public final Joystick gamepad;

	private String name;

	private Map<String, Button> buttonMap;
	private Map<String, Axis> axisMap;
	private Map<String, Thumbstick> thumbstickMap;
	private Map<String, DPad> dpadMap;

	public Gamepad(int usbPort, String name) {
		gamepad = new Joystick(usbPort);

		this.name = name;

		buttonMap = new HashMap<>();
		axisMap = new HashMap<>();
		thumbstickMap = new HashMap<>();
		dpadMap = new HashMap<>();
	}

	protected void registerButton(Button button, String name) {
		buttonMap.put(name.toLowerCase(), button);
	}

	protected void registerAxis(Axis axis, String name) {
		axisMap.put(name.toLowerCase(), axis);
	}

	protected void registerThumbstick(Thumbstick thumbstick, String name) {
		thumbstickMap.put(name.toLowerCase(), thumbstick);
	}

	protected void registerDPad(DPad dpad, String name) {
		dpadMap.put(name.toLowerCase(), dpad);
	}

	public void setRumble(double rumble) {
		this.setRumble(rumble, rumble);
	}

	public void setRumble(double left, double right) {
		gamepad.setRumble(RumbleType.kLeftRumble, left);
		gamepad.setRumble(RumbleType.kRightRumble, right);
	}

	public String getName() {
		return name;
	}

	/**
	 * Enables retrieving buttons by name without knowing which controller is being
	 * used.
	 * 
	 * @param name
	 *            - The name of the button
	 * @return Returns a button mapped with the given name
	 */
	public Button getButton(String name) {
		return buttonMap.get(name.toLowerCase());
	}

	/**
	 * Enables retrieving axes by name without knowing which controller is being
	 * used.
	 * 
	 * @param name
	 *            - The name of the axis
	 * @return Returns an axis mapped with the given name
	 */
	public Axis getAxis(String name) {
		return axisMap.get(name.toLowerCase());
	}

	/**
	 * Enables retrieving thumbsticks by name without knowing which controller is
	 * being used.
	 * 
	 * @param name
	 *            The name of the thumbstick (usually just <code>"LS"</code> or
	 *            <code>"RS"</code>)
	 * @return Returns a thumbstick mapped with the given name
	 */
	public Thumbstick getThumbstick(String name) {
		return thumbstickMap.get(name.toLowerCase());
	}

	/**
	 * Enables retrieving POV DPads by name without knowing which controller is
	 * being used.
	 * 
	 * @param name
	 *            The name of the DPad (usually just <code>"DPAD"</code>)
	 * @return Returns a button mapped with the given name
	 */
	public DPad getDPad(String name) {
		return dpadMap.get(name.toLowerCase());
	}

}
