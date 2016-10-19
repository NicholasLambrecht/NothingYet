package UI;

import Game.Screen;
import Graphics.Sprite;

public class PlayerHealthBar {
	private int xa, ya;
	double maxHPV, HPvalue;
	double maxMPV, MPvalue;
	double maxSPV, SPvalue;
	double expToNext, exp;
	Sprite sprite;

	public PlayerHealthBar(int x, int y, int HP, int MP, int SP, int EXP) {
		maxHPV = HP;
		HPvalue = maxHPV;
		maxMPV = MP;
		MPvalue = maxMPV;
		maxSPV = SP;
		SPvalue = maxSPV;
		expToNext = EXP;
		exp = 0;
		xa = x; //We need coordinates for this.
		ya = y; //Still need coordinates for this.
		sprite = Sprite.portrait;

		

	}

	public void update(int newValueHP, int newValueMP, int newValueSP, int newEXP) {
		HPvalue = newValueHP;
		MPvalue = newValueMP;
		SPvalue = newValueSP;
		exp = newEXP;
	}

	public void updateMaX(int newMaxHP, int newMaxMP, int newMaxSP, int newExpToNext) {
		maxHPV = newMaxHP;
		maxMPV = newMaxMP;
		maxSPV = newMaxSP;
		expToNext = newExpToNext;
	}

	public void render(Screen screen) {
		screen.renderSprite(xa, ya, sprite, false);
		for (int i = 0; i < (((HPvalue / maxHPV) * 169)); i++) {
			screen.renderSprite(xa + 79 + i, ya + 2, Sprite.hpFill, false);
		}
		for (int i = 0; i < (((MPvalue / maxMPV) * 170)); i++) {
			screen.renderSprite(xa + 78 + i, ya + 21, Sprite.manaFill, false);
		}
		for (int i = 0; i < (((SPvalue / maxSPV) * 169)); i++) {
			screen.renderSprite(xa + 79 + i, ya + 33, Sprite.specialFill, false);
		}
		for (int i = 0; i < (((exp / expToNext) * 76)); i++) {
			screen.renderSprite(xa + 1 + i, ya + 70, Sprite.expFill, false);
		}
	}

}
