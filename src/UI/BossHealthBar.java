package UI;

import Game.Screen;
import Graphics.Font;
import Graphics.Sprite;


public class BossHealthBar {
	private int xa, ya;
	double maxHPV, HPvalue;
	double maxMPV, MPvalue;
	double maxSPV, SPvalue;
	Sprite sprite;
	
	public BossHealthBar (int x, int y, int HP){
		maxHPV = HP;
		HPvalue = maxHPV;
		xa = x; //We need coordinates for this.
		ya = y; //Still need coordinates for this.
		sprite = Sprite.bossHPBar;
	}
	
	public void update(int newValueHP) {
		HPvalue = newValueHP;

	}
	
	public void updateMaX(int newMaxHP){
		maxHPV = newMaxHP;
	}
	
	public void render(Screen screen){
		screen.renderSprite(xa, ya, sprite, false);
		for (int i = 0; i < (((HPvalue / maxHPV) * 874)); i++) {
			screen.renderSprite(xa + 3 + i, ya + 3, Sprite.bossFill, false);
		}
		Font.renderBTSTSM((int)HPvalue + "",  screen, xa+400, ya + 7);

	}
	

	
}
