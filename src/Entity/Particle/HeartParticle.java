package Entity.Particle;

import Game.Screen;
import Graphics.Sprite;

public class HeartParticle extends Particle {

	private Sprite sprite;

	private int life;
	private int time = 0;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	public HeartParticle(int x, int y, int life) {
		super(x, y, life);
		sprite = Sprite.particle_blood;
		this.x = x + 16 + random.nextInt(32);
		this.y = y + random.nextInt(64);
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(20) - 10);
		this.xa = 0;
		this.ya = random.nextGaussian();
		if (ya > 0) ya = -ya;
		this.zz = 0.0;
	}

	public void update() {
		time++;
		if (time >= Integer.MAX_VALUE - 1) time = 0;
		if (time > life) {
			remove();
		}

		move(x + xa, (y + ya));
	}

	private void move(double x, double y) {
		if (collision(x, y)) {
			remove();
		}
		this.x += xa;
		this.y += ya;
	}

	public boolean collision(double nx, double ny) {
		boolean solid = false;
		int ix = (int) Math.ceil(nx);
		int iy = (int) Math.ceil(ny);
		if (level.getTile((ix - 1) / 32, (iy) / 32).solid()) {
			solid = true;
		}

		// for (int c = 0; c < 4; c++) {
		// double xt = (x-11 + nx +c*-32);
		// double yt = (y+17 + ny *c);
		// int ix = (int) Math.ceil(xt);
		// int iy = (int) Math.ceil(yt);
		// if (c % 2 == 0)
		// ix = (int) Math.floor(xt);
		// if (c / 2 == 0)
		// iy = (int) Math.floor(yt);
		//
		// if (level.getTile(ix/64, iy/64).solid()) {
		// solid = true;
		// }
		// }
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y, sprite, true);
		screen.renderSprite((int) x + 2, (int) y, sprite, true);
		screen.renderSprite((int) x + 4, (int) y, sprite, true);
		screen.renderSprite((int) x + 2, (int) y - 2, sprite, true);
		screen.renderSprite((int) x + 2, (int) y + 2, sprite, true);
	}
}
