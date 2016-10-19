package Entity.Particle;

import Entity.Entity;
import Game.Screen;
import Graphics.Sprite;

public class ArrowShaftParticle extends Entity {

	private Sprite sprite;

	private int life;
	private int time = 0;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	public ArrowShaftParticle(int x, int y, int life) {
		sprite = Sprite.particle_arrowshaft;
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life;
		this.xa = x;
		this.ya = y;
	}

	public void update() {
		time++;
		if (time >= Integer.MAX_VALUE - 1) time = 0;
		if (time > life) {
			remove();
		}
	}

	public boolean collision(double x, double y) {
		boolean solid = false;
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y, sprite, true);

	}
}
