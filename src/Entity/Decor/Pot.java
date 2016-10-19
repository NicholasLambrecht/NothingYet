package Entity.Decor;

import java.util.Random;

import Entity.Projectile.ArrowPickup;
import Entity.Projectile.HeartPickup;
import Game.Screen;
import Graphics.Sprite;
import Level.Level;
import Sounds.SoundEffect;

public class Pot extends Decor {

	public Pot(int x, int y, Level level) {
		super(x, y, level);
		Random rand = new Random();
		if (rand.nextInt(3) == 1) sprite = Sprite.Pot1;
		else sprite = Sprite.Pot2;
	}

	public void destroy() {
		SoundEffect.POTBREAK.play();
		destroyed = true;
		remove();
		drop();
	}

	private void drop() {
		Random rand = new Random();
		if (rand.nextInt(4) == 0) {
			level.add(new ArrowPickup(x, y, level));
		}
		if (rand.nextInt(4) == 0) {
			level.add(new HeartPickup(x, y, level));
			level.add(new HeartPickup(x, y, level));
			level.add(new HeartPickup(x, y, level));
		}
		if (rand.nextInt(4) == 0) {
			level.add(new HeartPickup(x, y, level));
			level.add(new HeartPickup(x, y, level));
			level.add(new HeartPickup(x, y, level));
		}
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
	}

	public void render(Screen screen) {
		if (playerIsNear()) screen.renderSprite(x, y, sprite, true);
	}

	public boolean pCollision(double xx, double yy) {
		boolean hit = false;
		if (xx >= x && xx <= x + 32 && yy >= y - 20 && yy <= y + 32) {
			hit = true;
		}
		return hit;
	}
}
