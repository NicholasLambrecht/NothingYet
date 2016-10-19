package Entity.Projectile;

import Game.Screen;
import Graphics.Sprite;

public class Boulder extends Projectile {

	private int life;
	private int time = 0;
	private int animation = 0;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	public Boulder(int x, int y) {
		super(x, y, 0);
		life = 2500;
		damage = 15;
		sprite = Sprite.boulder1;
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(20) - 10);
		this.xa = (2 * random.nextGaussian()) + 1;
		if (xa > 0) xa = -xa;
		this.ya = -3;
		e = true;
	}

	public void update() {
		animation++;
		if (animation > 7 * 4) animation = 0;
		time++;
		if (time >= Integer.MAX_VALUE - 1) time = 0;
		if (time > life) {
			remove();
		}
		ya += .2;
		if (xa < 0) {
			xa -= .02;
		}
		if (xa > 0) {
			xa += .02;
		}
		if (collision(x, y + ya)) {
			this.xa *= .8;
			this.ya *= -0.30;
			life -= 100;
		}
		if (collision(x + xa, y)) {
			this.xa *= -.8;
			this.ya *= 1.00;
		}
		this.x += xa;
		this.y += ya;
	}

	public boolean collision(double nx, double ny) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((nx + 4) + ((c % 2) * 58));
			double yt = ((ny) + ((c / 2) * 64));
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			//Insert checks here (this checks all 4 corners of the sprite, refine to change hit box)
			if (level.getTile(ix / 32, iy / 32).solid()) solid = true;
		}
		return solid;
	}

	public void hit() {

	}

	public void render(Screen screen) {
		Sprite sprite = Sprite.boulder1;
		int spd = 4;
		if (animation <= 0 * spd) {
			sprite = Sprite.boulder2;
		} else if (animation <= 1 * spd) {
			sprite = Sprite.boulder3;
		} else if (animation <= 2 * spd) {
			sprite = Sprite.boulder4;
		} else if (animation <= 3 * spd) {
			sprite = Sprite.boulder5;
		} else if (animation <= 4 * spd) {
			sprite = Sprite.boulder6;
		} else if (animation <= 5 * spd) {
			sprite = Sprite.boulder7;
		} else if (animation <= 6 * spd) {
			sprite = Sprite.boulder8;
		}
		screen.renderSprite((int) x, (int) (y + 6), sprite, true);
	}
}
