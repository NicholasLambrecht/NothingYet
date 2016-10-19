package Entity.Projectile;

import Game.Screen;
import Graphics.Sprite;
import Level.Level;
import Sounds.SoundEffect;

public class HeartPickup extends Projectile {

	int timer = 0;
	private boolean homing = false;

	public HeartPickup(int x, int y, Level level) {
		super(x, y, 0);
		range = 100;
		damage = -1;
		knockback = 0;
		this.level = level;
		int ogX = x;
		int ogY = y;
		sprite = Sprite.heart;
		this.x = x + random.nextGaussian() * 20;
		this.y = y + random.nextGaussian() * 20;
		if (level.getTile(x / 32, y / 32).solid()) {
			x = ogX;
			y = ogY;
		}
		nx = 0;
		ny = .3;
		e = true;
	}

	public void update() {
		timer++;
		if (timer > 600) remove();
		homing();
		move();
		while (collision((int) (x), (int) (y)) && !homing) {
			y--;
		}
	}

	private void homing() {
		int px = level.player.x + 36;
		int py = level.player.y + 36;
		int dx = (int) (px - (x - sprite.getWidth() / 2));
		int dy = (int) (py - (y - sprite.getHeight() / 2));
		int distance = (int) Math.pow(dx, 2) + (int) Math.pow(dy, 2);
		if (distance < 10000 || homing) {
			homing = true;
			if (dx < 0) {
				nx -= .1;
			} else nx += .1;
			if (dy < 0) {
				ny -= .1;
			} else ny += .1;
		} else {
			ny += .55;
			nx *= .2;
		}
		if (distance > 5000 && !homing) {
			ny *= .5;
			nx *= .5;

		}
	}

	protected void move() {
		if (!collision((int) (x + nx), (int) (y + ny)) || homing) {
			x += nx;
			y += ny;
		} else ny *= -.5;
	}

	public boolean collision(int nx, int ny) {
		boolean solid = false;

		double xt = (nx);
		double yt = (ny + 5);
		int ix = (int) Math.ceil(xt);
		int iy = (int) Math.ceil(yt);
		// Insert checks here (this checks all 4 corners of the sprite,
		// refine to change hit box)
		if (level.getTile(ix / 32, iy / 32).solid()) solid = true;

		return solid;
	}

	public void hit() {
		SoundEffect.PICKUP.play();
		remove();

	}

	public void render(Screen screen) {

		screen.renderSprite((int) x - (sprite.getWidth() / 2), (int) y - (sprite.getHeight() / 2), sprite, true);
	}

	public void remove() {
		removed = true;
	}
}
