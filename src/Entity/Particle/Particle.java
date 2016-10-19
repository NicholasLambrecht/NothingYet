package Entity.Particle;

import Entity.Entity;
import Game.Screen;
import Graphics.Sprite;

public abstract class Particle extends Entity {

	private Sprite sprite;
	private int life;
	private int time = 0;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	public Particle(int x, int y, int life) {
		sprite = Sprite.particle_normal;
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life;
	}

	public void update() {
		time++;
		if(time>life)remove();
		move(x, y);
	}

	private void move(double x, double y) {
	}

	public boolean collision(double nx, double ny) {
		boolean solid = false;
		int ix = (int) Math.ceil(nx);
		int iy = (int) Math.ceil(ny);
		if (level.getTile((ix) / 32, (iy - 3) / 32).solid()) {
			solid = true;
		}

		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) xx, (int) yy - (int) zz, sprite, true);
	}
}
