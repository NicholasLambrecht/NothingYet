package Entity.Projectile;

import Game.Screen;
import Graphics.Sprite;
import Level.Level;
import Sounds.SoundEffect;

public class Arrow extends Projectile {

	int timer = 0;
	boolean stopped = false;

	public Arrow(int x, int y, double direction, Level level) {
		super(x, y, direction);
		range = 1000;
		speed = 20;
		damage = level.player.getEXPLevel() * 30 + 150;
		this.level = level;
		sprite = Sprite.Fireball;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		timer++;
		if (timer > range) remove();
		if (!stopped) move();
	}

	protected void move() {
		if (collision(x + ny, y + ny)) {
			stopped = true;
			damage = 0;
			SoundEffect.ARROWHIT.play();
		} else {
			ny += 1 / (speed * 10);
			x += nx;
			y += ny;
		}
	}

	public boolean collision(double d, double e) {
		boolean solid = false;
		double xt = (d);
		double yt = (e);
		int ix = (int) Math.ceil(xt);
		int iy = (int) Math.ceil(yt);
		if (level.getTile(ix / 32, iy / 32).solid()) solid = true;
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y, Sprite.particle_arrow, true);
		screen.renderSprite((int) (x - (nx / 20)), (int) (y - 2 * (ny / 20)), Sprite.particle_arrow, true);
		screen.renderSprite((int) (x - (nx / 20)), (int) (y + (ny / 20)), Sprite.particle_arrow, true);
		screen.renderSprite((int) (x - 2 * (nx / 20)), (int) (y - 2 * (ny / 20)), Sprite.particle_arrowshaft, true);
		screen.renderSprite((int) (x - 4 * (nx / 20)), (int) (y - 4 * (ny / 20)), Sprite.particle_arrowshaft, true);
		screen.renderSprite((int) (x - 6 * (nx / 20)), (int) (y - 6 * (ny / 20)), Sprite.particle_arrowshaft, true);
		screen.renderSprite((int) (x - 8 * (nx / 20)), (int) (y - 8 * (ny / 20)), Sprite.particle_arrowshaft, true);
		screen.renderSprite((int) (x - 10 * (nx / 20)), (int) (y - 10 * (ny / 20)), Sprite.particle_arrowshaft, true);
		screen.renderSprite((int) (x - 12 * (nx / 20)), (int) (y - 12 * (ny / 20)), Sprite.particle_arrowshaft, true);
		screen.renderSprite((int) (x - 14 * (nx / 20)), (int) (y - 14 * (ny / 20)), Sprite.particle_arrowshaft, true);
		screen.renderSprite((int) (x - 16 * (nx / 20)), (int) (y - 16 * (ny / 20)), Sprite.particle_arrowshaft, true);
		screen.renderSprite((int) (x - 18 * (nx / 20)), (int) (y - 18 * (ny / 20)), Sprite.particle_arrowshaft, true);
	}

	public void hit() {
		remove();
		if (damage > 0) SoundEffect.HITMARKER.play();

	}

	public void remove() {
		removed = true;
	}
}
