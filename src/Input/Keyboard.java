package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right, m, e, f, z, q, one, two, three, t, c, l, r, p, j, k, sb, upR, enter, four, six, space, tab, n, esc,v;
	public boolean x;

	public void update() {
		up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_SPACE];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		m = keys[KeyEvent.VK_M];
		e = keys[KeyEvent.VK_E];
		q = keys[KeyEvent.VK_Q];
		t = keys[KeyEvent.VK_T];
		r = keys[KeyEvent.VK_R];
		p = keys[KeyEvent.VK_P];

		z = keys[KeyEvent.VK_Z];
		j = keys[KeyEvent.VK_J];
		t = keys[KeyEvent.VK_T];
		k = keys[KeyEvent.VK_K];
		l = keys[KeyEvent.VK_L];
		c = keys[KeyEvent.VK_C];
		v=keys[KeyEvent.VK_V];
		
		space = keys[KeyEvent.VK_F];
		f = keys[KeyEvent.VK_F];
		enter = keys[KeyEvent.VK_ENTER];
		esc = keys[KeyEvent.VK_ESCAPE];

		upR = keys[KeyEvent.VK_UP];

		tab = keys[KeyEvent.VK_V];

		n = keys[KeyEvent.VK_N];

		four = keys[KeyEvent.VK_4];
		six = keys[KeyEvent.VK_6];
		x = keys[KeyEvent.VK_X];

		one = keys[KeyEvent.VK_1];
		two = keys[KeyEvent.VK_2];
		three = keys[KeyEvent.VK_3];
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {

	}
}
