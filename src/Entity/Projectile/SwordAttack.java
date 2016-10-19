package Entity.Projectile;

import java.util.Random;

import Entity.Mob.Mob;
import Entity.Particle.BloodParticle;
import Game.Screen;
import Graphics.Sprite;
import Level.Level;
import Sounds.SoundEffect;

public class SwordAttack extends Projectile {

	int dir = 1;
	double life = 0;
	int ax = 0;
	int timer = 0;
	int dx = 0;
	int dy = 0;
	double sx = 0;
	double sy = 0;

	public SwordAttack(int x, int y, Level level, int dir) {
		super(x, y, 0);
		this.dir = dir;
		range = 100;
		speed = 12;
		damage = 40;
		knockback = 0;
		this.level = level;
		sprite = Sprite.voidParticle;
		damage = (level.player.getEXPLevel() * 5 + 25);
		nx = x;
		ny = y;
		y = 0;
		x = 0;
	}

	public void update() {
		int frameTime = 3;
		x = -50;
		y = -50;
		nx = level.player.x;
		ny = level.player.y;
		if (dir == 1) {
			if (timer <= frameTime) {
				// sprite = Sprite.sword1;
				dx = 25;
				sx = nx + dx;
				dy = 7;
				x = nx + 31;
				sy = ny + dy;

			} else if (timer < frameTime * 2) { // RIGHT
				// sprite = Sprite.sword2;
				dx = 25;
				sx = nx + dx;
				dy = 4;
				sy = ny + dy;

			} else if (timer < frameTime * 3) { // RIGHT
				dx = 51;
				sx = nx + dx;
				dy = 23;
				sy = ny + dy;
				x = nx + 59 + 37;
				y = ny + dy + 2;
				sprite = Sprite.sword3;
			} else if (timer >= frameTime * 3) { // RIGHT
				dx = -6;
				sx = nx + dx;
				dy = 28;
				sy = ny + dy;
				x = nx + 59;
				y = ny + 23 + 2;
				sprite = Sprite.sword7;
			}
		} else {
			if (timer <= frameTime) {
				dx = 25;
				sx = nx + 24 - dx;
				dy = 7;
				sy = ny + dy;

			} else if (timer < frameTime * 2) { // RIGHT
				dx = 25;
				sx = nx + 24 - dx;
				dy = 4;
				sy = ny + dy;

			} else if (timer < frameTime * 3) { // RIGHT
				dx = 51;
				sx = nx + 18 - dx;
				dy = 23;
				sy = ny + dy;
				x = nx - 17;
				y = ny + dy + 2;
				sprite = Sprite.sword3l;
			} else if (timer >= frameTime * 3) { // RIGHT
				dx = -22;
				sx = nx + 18 - dx;
				dy = 28;
				sy = ny + dy;
				x = nx + 15;
				y = ny + 23 + 2;
				sprite = Sprite.sword7l;
			}
		}

		if (timer > 4 * frameTime) {
			remove();
		}
		timer++;
	}

	protected void move() {
	}

	public void hit() {
		if (level.player.manabladeTalent) {
			Random rand = new Random();
			int r = rand.nextInt(10);
			if (r == 0) {
				level.player.manabladeFireball();
			} else if (r == 1) {
				level.player.pillars_of_fire();
			}
		}
	}

	public void effect(Mob mob) {
		if (level.player.vorpalbladeTalent) {
			if (mob.getHp() < mob.maxHp / 10) {
				mob.die();
				SoundEffect.VORPAL.play();
				int mobx = mob.sprite.getWidth();
				int moby = mob.sprite.getHeight();
				level.add(new HeartPickup(mob.x,mob.y,level));
				level.add(new HeartPickup(mob.x,mob.y,level));
				level.add(new HeartPickup(mob.x,mob.y,level));
				for (int nx = 0; nx < mobx; nx++) {
					for (int ny = 0; ny < moby; ny++) {
						if (mob.sprite.pixels[nx + (ny * mob.sprite.getWidth())] != 0xFFFF00FF) {
							level.add(new BloodParticle(mob.x + nx, mob.y + ny, 300));
						}
					}
				}
			}
		}
	}

	public boolean collision(int nx, int ny) {
		return false;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) sx, (int) sy, sprite, true);

		//	screen.renderSprite((int) x, (int) y, sprite.particle_blood, true);
		//	System.out.println(x + "," + y);

	}

	public void remove() {
		removed = true;
	}
}
