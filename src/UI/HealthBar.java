package UI;

import Game.Screen;
import Graphics.Font;
import Graphics.Sprite;

public class HealthBar {
	private int xa, ya;
	double maxV, value;
	Sprite sprite;
	Sprite barFill;

	public HealthBar(int x, int y, int maxValue) {
		maxV = maxValue + 0.0;
		value = maxV;
		xa = x; //We need coordinates for this.
		ya = y; //Still need coordinates for this.
		sprite = Sprite.smhpb;
		barFill = Sprite.smhpbfill;
	}

	public void update(int x, int y, int newValue) {
		value = newValue;
		this.xa = x;
		this.ya = y;
	}
	
	public void updateMaX(int newMaxHP){
		maxV = newMaxHP;

	}

	public void render(Screen screen) {
		screen.renderSprite(xa, ya, sprite, true);
		double ratio = value/maxV;
		for (int i = 0; i < ((ratio * 64)); i++) {
			screen.renderSprite(xa + 1 + i, ya + 1, barFill, true);
		}
	
	}

}
