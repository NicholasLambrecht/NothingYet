package Entity.Projectile;

import Entity.Spawner.ParticleSpawner;
import Game.Screen;
import Graphics.Sprite;
import Level.Level;

public class BossFireball extends Projectile {

	double targetRange;
	int dir = 1;
	int timer = 0;

	public BossFireball(int x, int y, double direction, Level level) {
		super(x, y, direction);
		range = 100;
		speed = 40;
		damage = 15;
		knockback = 15;
		this.level = level;
		sprite = Sprite.particle_normal;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		if (nx > 0) dir = 0;
		e = true;
	}

	public void update() {
		timer++;
		if (timer > 600) remove();
		if (collision((int) (nx), (int) (ny))) {
			level.add(new ParticleSpawner((int) x, (int) y, 120, 25, level));
			remove();
		}
		move();
	}

	protected void move() {
		x += nx;
		y += ny;
		level.add(new ParticleSpawner((int) x + 8, (int) y + 7, -4, 60, level));
	}

	public boolean collision(int nx, int ny) {
		boolean solid = false;
		for (int c = 0; c < 2; c++) {
			double xt = ((x + nx + 8) + (c % 2 * 6));
			double yt = ((y + ny));
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			//Insert checks here (this checks all 4 corners of the sprite, refine to change hit box)
			if (level.getTile(ix / 32, iy / 32).solid()) solid = true;
		}
		return solid;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int) x, (int) y, sprite);
	}
	
	public boolean mobcollision(int nx, int ny) {
		boolean hit = false;
		nx += 32;
		ny += 32;
		if (nx >= x && nx <= x + 64 && ny >= y && ny <= y + 64) {
			hit = true;
		}
		return hit;
	}

	public void remove() {
		removed = true;
	}
}
