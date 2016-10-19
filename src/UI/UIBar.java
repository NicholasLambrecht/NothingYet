package UI;

import Game.Screen;
import Graphics.Sprite;


public class UIBar {
	private int xa, ya;
	double maxV, value;
	Sprite sprite;
	Sprite barFill;
	
	public UIBar (int x, int y, int maxValue){
		maxV = maxValue;
		value = maxV;
		xa = x; //We need coordinates for this.
		ya = y; //Still need coordinates for this.
		sprite = Sprite.incrementBar;
		barFill = Sprite.incrementFill;
	}
	
	public void update(int newValue) {
		value = newValue;
	}
	
	public void render(Screen screen){
		screen.renderSprite(xa, ya, sprite, false);
		for (int i = 0; i < (((value / maxV) * 100)); i++) {
			screen.renderSprite(xa + 1 + i, ya + 1, barFill, false);
		}
	}
	

	
}
