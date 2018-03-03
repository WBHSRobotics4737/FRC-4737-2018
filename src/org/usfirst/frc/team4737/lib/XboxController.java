package org.usfirst.frc.team4737.lib;

public class XboxController extends Gamepad {

	public final GamepadButton A;
	public final GamepadButton B;
	public final GamepadButton X;
	public final GamepadButton Y;
	public final GamepadButton LB;
	public final GamepadButton RB;
	public final GamepadButton SELECT;
	public final GamepadButton START;
	public final GamepadButton L3;
	public final GamepadButton R3;

	public final Thumbstick LS;
	public final Thumbstick RS;

	public final GamepadAxis LT;
	public final GamepadAxis RT;

	public final DPad DPAD;

	public XboxController(int usbPort) {
		super(usbPort, "XboxController");
		A = new GamepadButton(this, "A", 1);
		B = new GamepadButton(this, "B", 2);
		X = new GamepadButton(this, "X", 3);
		Y = new GamepadButton(this, "Y", 4);
		LB = new GamepadButton(this, "LB", 5);
		RB = new GamepadButton(this, "RB", 6);
		SELECT = new GamepadButton(this, "SELECT", 7);
		registerButton(SELECT, "BACK");
		START = new GamepadButton(this, "START", 8);
		L3 = new GamepadButton(this, "L3", 9);
		registerButton(L3, "LS");
		R3 = new GamepadButton(this, "R3", 10);
		registerButton(R3, "RS");

		LS = new Thumbstick(this, "LS", 0, 1, false, false);
		LS.X.setDeadzone(0.1);
		LS.Y.setDeadzone(0.1);
		RS = new Thumbstick(this, "RS", 4, 5, false, false);
		RS.X.setDeadzone(0.1);
		RS.Y.setDeadzone(0.1);

		LT = new GamepadAxis(this, "LT", 2, false);
		RT = new GamepadAxis(this, "RT", 3, false);

		DPAD = new DPad(this, "DPAD", 0);
	}

}
