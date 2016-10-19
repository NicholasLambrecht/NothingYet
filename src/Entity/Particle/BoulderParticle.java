package Entity.Particle;

import Entity.Entity;
import Game.Screen;
import Graphics.Sprite;

public abstract class BoulderParticle extends Entity {

	private Sprite sprite;
	private int life;
	private int time = 0;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	public BoulderParticle(int x, int y, int life) {
		sprite = Sprite.particle_boulder;
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
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) xx, (int) yy - (int) zz, sprite, true);
	}
}
