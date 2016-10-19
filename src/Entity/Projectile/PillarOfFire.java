package Entity.Projectile;

import Entity.Spawner.POFParticleSpawner;
import Game.Screen;
import Graphics.Sprite;
import Level.Level;

public class PillarOfFire extends Projectile {

	double targetRange;
	int dir = 1;
	double life = 0;
	double aAx = 5;
	int ax = 0;
	int oj = 0;
	int timer = 0;

	public PillarOfFire(int x, int y, Level level) {
		super(x, y, 3.141592653589 * 3 / 2);
		range = 100;
		speed = 12;
		damage = 30;
		knockback = 5;
		oj = y;
		this.level = level;
		sprite = Sprite.particle_normal;
		nx = 0;
		damage = (level.player.getEXPLevel() * 6 + 30);
		ny = speed * Math.sin(angle);

	}

	public void update() {
		timer++;
		if (timer > 60) remove();
		life = life + Math.PI / 24;
		if (life >= Math.PI * 2) {
			life = 0;
		}
		ax = (int) (23 * Math.sin(life * 6));

		level.add(new POFParticleSpawner((int) x, (int) y, 60, 60, level));
		move();
		if (y > oj + 2000) remove();
	}

	protected void move() {
		x += ax;
		y += ny;
	}

	public void hit() {
	}

	public boolean collision(int nx, int ny) {
		boolean solid = false;
		double xt = (x + nx);
		double yt = (y + ny);
		int ix = (int) Math.ceil(xt);
		int iy = (int) Math.ceil(yt);
		// Insert checks here (this checks all 4 corners of the sprite, refine
		// to change hit box)
		if (level.getTile(ix / 32, iy / 32).solid()) solid = true;
		return solid;
	}

	public void render(Screen screen) {
		// screen.renderProjectile((int) x, (int) y, sprite);

	}

	public void remove() {
		removed = true;
	}
}
