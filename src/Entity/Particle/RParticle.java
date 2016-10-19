package Entity.Particle;

import Game.Screen;
import Graphics.Sprite;

public class RParticle extends Particle {

	private Sprite sprite;

	private int life;
	private int time = 0;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	public RParticle(int x, int y, int life) {
		super(x,y,life);
		sprite = Sprite.particle_normal;
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(20) - 10);
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
	}

	public void update() {
		time++;
		if (time >= Integer.MAX_VALUE - 1)
			time = 0;
		if (time > life) {
			remove();
		}
		if(life > 20){
			ya+=.1;
		}

		za -= .1;
		if (zz < 0) {
			zz = 0;
			za *= -0.25;
			xa *= .50;
		}
		move(xx + xa, (yy + ya));
	}

	private void move(double x, double y) {
		if (collision(x, y + 2)) {
			this.xa *= -0.5;
			this.ya *= -0.5;
			this.za *= -0.5;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;

	}

	public boolean collision(double nx, double ny) {
		boolean solid = false;
		int ix = (int) Math.ceil(nx);
		int iy = (int) Math.ceil(ny);
		if(level.getTile((ix)/32,(iy-3)/32).solid()){
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
