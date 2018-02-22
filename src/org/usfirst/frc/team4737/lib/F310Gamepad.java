package org.usfirst.frc.team4737.lib;

public class F310Gamepad extends Gamepad {

//	private class LogitechGamepadTriggerAxis extends GamepadAxis {
//
//		private final boolean positive;
//
//		public LogitechGamepadTriggerAxis(Gamepad gamepad, String name, int axis, boolean positive) {
//			super(gamepad, name, axis);
//
//			this.positive = positive;
//		}
//
//		@Override
//		public double get() {
//			double value = super.get();
//			if (value > 0 && positive)
//				return value;
//			else if (value < 0 && !positive)
//				return -value;
//			else
//				return 0;
//		}
//
//	}

	public final GamepadButton A;
	public final GamepadButton B;
	public final GamepadButton X;
	public final GamepadButton Y;
	public final GamepadButton LB;
	public final GamepadButton RB;
	public final GamepadButton BACK;
	public final GamepadButton START;
	public final GamepadButton L3;
	public final GamepadButton R3;

	public final Thumbstick LS;
	public final Thumbstick RS;

//	public final LogitechGamepadTriggerAxis LT;
//	public final LogitechGamepadTriggerAxis RT;
	public final GamepadAxis LT;
	public final GamepadAxis RT;

	public final DPad DPAD;

	public F310Gamepad(int usbPort) {
		super(usbPort, "LogitechGamepad");
		A = new GamepadButton(this, "A", 1);
		B = new GamepadButton(this, "B", 2);
		X = new GamepadButton(this, "X", 3);
		Y = new GamepadButton(this, "Y", 4);
		LB = new GamepadButton(this, "LB", 5);
		RB = new GamepadButton(this, "RB", 6);
		BACK = new GamepadButton(this, "BACK", 7);
		registerButton(BACK, "SELECT");
		START = new GamepadButton(this, "START", 8);
		L3 = new GamepadButton(this, "L3", 9);
		registerButton(L3, "LS");
		R3 = new GamepadButton(this, "R3", 10);
		registerButton(R3, "RS");

		LS = new Thumbstick(this, "LS", 0, 1);
		RS = new Thumbstick(this, "RS", 4, 5);

//		LT = new LogitechGamepadTriggerAxis(this, "LT", 2, true);
//		RT = new LogitechGamepadTriggerAxis(this, "RT", 3, false);
		LT = new GamepadAxis(this, "LT", 2);
		RT = new GamepadAxis(this, "RT", 3);

		DPAD = new DPad(this, "DPAD", 0);
	}

}
