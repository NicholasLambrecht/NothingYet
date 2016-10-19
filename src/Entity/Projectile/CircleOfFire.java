package Entity.Projectile;

import Entity.Mob.Mob;
import Entity.Spawner.COFParticleSpawner;
import Game.Screen;
import Graphics.Sprite;
import Level.Level;

public class CircleOfFire extends Projectile {

	double targetRange;
	int dir = 1;
	double life = 0;
	double aAx = 5;
	int ax = 0;
	int timer = 0;
	int radius = 30;

	public CircleOfFire(int x, int y, Level level) {
		super(x, y, 3.141592653589 * 3 / 2);
		range = 100;
		speed = 12;
		damage = 0;
		knockback = 0;
		this.level = level;
		sprite = Sprite.particle_normal;
		life = ny = speed * Math.sin(angle);
	}

	public void update() {
		timer++;
		if (collision((int) (nx), (int) (ny))) {
			remove();
		}
		ax = (int) (23 * Math.sin(life * 6));
		while (life < 2 * Math.PI) {
			life = life + Math.PI / 6 + random.nextGaussian() * 2.5;
			level.add(new COFParticleSpawner((int) (x + radius * Math.cos(life)), (int) (y + radius * Math.sin(life)), 15, 5, level));
		}

		move();
		if (timer > 5) remove();
	}

	protected void move() {
	}

	public void hit() {
	}

	public void effect(Mob mob) {
		if (level.player.purgingfireTalent) {
			mob.ablaze = true;
			mob.burnDamage = level.player.getEXPLevel() + 5;
			mob.burnTimer = 600;
		}
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
