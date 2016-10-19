package Entity.Projectile;

import Game.Screen;
import Graphics.Sprite;
import Level.Level;
import Sounds.SoundEffect;

public class EArrow extends Projectile {

	int timer = 0;
	boolean stopped = false;

	public EArrow(int x, int y, int direction, Level level,int damage) {
		super(x, y, direction);
		speed = 20;
		range = 1350/speed;
		this.damage = damage;
		this.level = level;
		if(direction == 1){
			sprite = Sprite.eArrowr;
			nx = speed;
		} else{
			nx = -speed;
			sprite = Sprite.eArrow;
		}
		e = true;
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
		for (int c = 0; c < 2; c++) {
			double xt = ((d) + (c % 2 * 0));
			double yt = ((e));
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			// Insert checks here (this checks all 4 corners of the sprite,
			// refine to change hit box)
			if (level.getTile(ix / 32, iy / 32).solid()) solid = true;
		}
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y, sprite, true);
	}

	public void hit() {
		remove();
		if (damage > 0) SoundEffect.HITMARKER.play();

	}

	public void remove() {
		removed = true;
	}
}
