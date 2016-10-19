package Entity.Particle;

import Game.Screen;
import Graphics.Sprite;

public class YFireParticle extends Particle {

	private Sprite sprite;

	private int life;
	private int time = 0;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	public YFireParticle(int x, int y, int life) {
		super(x, y, life);
		sprite = Sprite.particle_fire;
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(20) - 10);
		life = life * 3;
		this.xa = random.nextGaussian()/2;
		this.ya = random.nextGaussian()/2;
		if (ya > 0) ya = -ya;
	}

	public void update() {
		time++;
		if (time >= Integer.MAX_VALUE - 1) time = 0;
		if (time > life) {
			remove();
		}

		if (time >= life / 3) {
			sprite = Sprite.particle_normaly;
		}
		if (time >= (life * 2) / 3) {
			sprite = Sprite.particle_smy;
		}

		//ya = .1;

		za -= .1;
		if (zz < 0) {
			zz = 0;
			za *= -0.25;
			xa *= .50;
		}
		move(xx + xa, (yy + ya));
	}

	private void move(double x, double y) {

		this.xx += xa;
		this.yy += ya;
		this.zz += za;

	}

	public boolean collision(double nx, double ny) {
		return false;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) xx, (int) yy - (int) zz, sprite, true);
	}
}
