package Entity.Particle;

import Game.Screen;
import Graphics.Sprite;

public class SpecParticle extends Particle {

	private Sprite sprite;

	private int life;
	private int time = 0;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	public SpecParticle(int x, int y, int life) {
		super(x, y, life);
		sprite = Sprite.particle_normalg;
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(20) - 10);
		this.xa = 2.5 * random.nextGaussian();
		this.ya = 2.5 * random.nextGaussian();
		this.zz = 0.0;
	}

	public void update() {
		time++;
		if (time >= Integer.MAX_VALUE - 1) time = 0;
		if (time > life) {
			remove();
		}
		move(xx + xa, (yy + ya));
	}

	private void move(double x, double y) {
		if (collision(x, y)) {
			remove();
		} else {
			this.xx += xa;
			this.yy += ya;
			this.zz += za;
		}
	}

	public boolean collision(double nx, double ny) {
		boolean solid = false;
		int ix = (int) Math.ceil(nx);
		int iy = (int) Math.ceil(ny);
		if (level.getTile((ix - 1) / 32, (iy) / 32).solid()) {
			solid = true;
		}

		//		for (int c = 0; c < 4; c++) {
		//			double xt = (x-11 + nx +c*-32);
		//			double yt = (y+17 + ny *c);
		//			int ix = (int) Math.ceil(xt);
		//			int iy = (int) Math.ceil(yt);
		//			if (c % 2 == 0)
		//				ix = (int) Math.floor(xt);
		//			if (c / 2 == 0)
		//				iy = (int) Math.floor(yt);
		//
		//			if (level.getTile(ix/64, iy/64).solid()) {
		//				solid = true;
		//			}
		//		}
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) xx, (int) yy - (int) zz, sprite, true);
	}
}
