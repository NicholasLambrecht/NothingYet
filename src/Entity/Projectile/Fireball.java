package Entity.Projectile;

import Entity.Spawner.ParticleSpawner;
import Game.Screen;
import Graphics.Sprite;
import Level.Level;

public class Fireball extends Projectile {

	double targetRange;
	int dir = 1;
	int timer = 0;
	
	public Fireball(int x, int y, double direction, Level level) {
		super(x, y, direction);
		range = 100;
		speed = 12;
		damage = 15;
		knockback = 15;
		this.level = level;
		sprite = Sprite.Fireball;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		damage = (level.player.getEXPLevel() * 3 + 15);
		if(nx > 0) dir = 0;
	}

	public void update() {
		timer++;
		if(timer > 600) remove();
		if(timer % 5 == 0) {
			double damageReduc = (timer / 5); 
			damage = (int) ((level.player.getEXPLevel() * 3 + 15) - damageReduc);
			if(damage <= 0) damage = 0;
		}
		if (collision((int) (nx), (int) (ny))) {
			level.add(new ParticleSpawner((int) x + 8, (int) y + 8, 120, 25, level));
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
		if(dir == 0)
		screen.renderProjectile((int) x, (int) y, sprite);
		else
			screen.renderProjectile((int) x, (int) y, Sprite.Fireball2);
	}

	public void remove() {
		removed = true;
	}
}
