package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {
	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseL = -1;
	private static int mouseL2 = -1;
	private static int mouseR = -1;

	public static int getX() {
		return mouseX;
	}

	public static int getY() {
		return mouseY;
	}

	public static int getL() {
		return mouseL;
	}

	public static int getL2() {
		return mouseL2;
	}

	public static int getR() {
		return mouseR;

	}

	public void update() {

	}

	public void mouseClicked(MouseEvent event) {

	}

	public void mousePressed(MouseEvent event) {
		if (event.getButton() == 1){
			mouseL = 1;
		}
		if (event.getButton() == 3){
			mouseR = 1;
		}
//		switch (event.getModifiers()) {
//		case InputEvent.BUTTON1_MASK: {
//			mouseL = event.getButton();
//			if (mouseL == -1) {
//				mouseL = event.getButton();
//			} else
//			//	mouseL = -2;
//			break;
//		}
//		case InputEvent.BUTTON2_MASK: {
//			mouseR = event.getButton();
//			if (mouseR == -1) {
//				mouseR = event.getButton();
//			} else
//			//	mouseR = -2;
//			break;
//		}
//		}
	}

	public void mouseReleased(MouseEvent event) {
		mouseL = -1;
		mouseR = -1;
	}

	public void mouseEntered(MouseEvent event) {

	}

	public void mouseDragged(MouseEvent event) {
		mouseX = event.getX();
		mouseY = event.getY();
	}

	public void mouseMoved(MouseEvent event) {
		mouseX = event.getX();
		mouseY = event.getY();
	}

	public void mouseExited(MouseEvent arg0) {

	}

}
