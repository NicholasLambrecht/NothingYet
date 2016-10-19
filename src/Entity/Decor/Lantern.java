package Entity.Decor;

import Entity.Spawner.FireParticleSpawner;
import Game.Screen;
import Graphics.Sprite;
import Level.Level;

public class Lantern extends Decor {

	public Lantern(int x, int y, Level level) {
		super(x, y, level);
		sprite = Sprite.lantern;
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

		if (dx < 800 && dy < 532) {
			result = true;
		}

		return result;
	}

	public void update() {
		if (playerIsNear()) level.add(new FireParticleSpawner(x + 15, y + 26, 2, 5, level));
	}

	public void render(Screen screen) {
		if (playerIsNear()) screen.renderSprite(x, y + 8, sprite, true);
	}
}
