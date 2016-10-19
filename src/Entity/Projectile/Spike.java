package Entity.Projectile;

import Game.Screen;
import Graphics.Sprite;

public class Spike extends Projectile {

	int dir = 1;
	int life = 256;
	double aAx = 0;
	int ax = 0;
	int oj = 0;
	int timer = 0;
	int dy = 0;
	int delay;
	int startY;

	public Spike(int x, int y, int delay) {
		super(x, y, 0);
		speed = 10;
		damage = 24;
		knockback = 5;
		oj = y;
		sprite = Sprite.icicle4;
		nx = 0;
		ny = speed * 1;
		life = 256 + delay;
		this.delay = delay;
		startY = y;
		e = true;
	}

	public void update() {
		timer++;
		if (timer > delay) {
			if (timer > life) remove();
			if (startY - 119 > y) ny = -speed * 1;
//			if (timer > life * 3 / 6) {
//				ny = -speed * 1;
//			}
			move();
		}
	}

	protected void move() {
		y -= ny;
	}

	public void hit() {
	}

	public boolean collision(int nx, int ny) {
		boolean solid = false;
		double xt = (x + nx);
		double yt = (y + ny);
		int ix = (int) Math.ceil(xt);
		int iy = (int) Math.ceil(yt);
		// Insert checks here (this checks all 4 corners of the sprite, refine
		// to change hit box)
		if (level.getTile(ix / 32, iy / 32).solid()) solid = true;
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y, sprite, true);
	}

	public void remove() {
		removed = true;
	}
}
