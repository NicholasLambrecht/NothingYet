package Entity.Decor;

import Game.Screen;
import Graphics.Sprite;
import Level.Level;

public class Tree extends Decor {

	public Tree(int x, int y, Level level, int type) {
		super(x, y, level);
		sprite = Sprite.smTree1;
		if(type == 1) sprite = Sprite.smTree1;
		if(type == 2) sprite = Sprite.smTree2;
		if(type == 3) sprite = Sprite.smTree3;
		if(type == 4) sprite = Sprite.smTree4;
		if(type == 5) sprite = Sprite.lgTree1;
		if(type == 6) sprite = Sprite.lgTree2;
	}

	public void destroy() {
		destroyed = true;
		sprite = destroyedSprite;
	}

	public boolean playerIsNear() {
		boolean result = false;
		int px = level.player.x;
		int py = level.player.y;

		int dx = Math.abs(px - x);
		int dy = Math.abs(py - y);

		if (dx < 945 && dy < 532) {
			result = true;
		}

		return result;
	}

	public void update() {
		// if (playerIsNear()) level.add(new FireParticleSpawner(x + 15, y + 26,
		// 2, 5, level));
	}

	public void render(Screen screen) {
		if (playerIsNear()) screen.renderSprite(x, y, sprite, true);
	}
}
