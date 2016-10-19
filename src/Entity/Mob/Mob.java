package Entity.Mob;

import java.util.Random;

import Entity.Entity;
import Entity.Projectile.Arrow;
import Entity.Projectile.HeartPickup;
import Entity.Projectile.Projectile;
import Entity.Spawner.BloodParticleSpawner;
import Entity.Spawner.FireParticleSpawner;
import Game.Screen;
import Graphics.Sprite;
import Level.Level;
import Sounds.SoundEffect;
import UI.DamageBattleText;

public abstract class Mob extends Entity {

	public Sprite sprite;
	public int dir = 0;
	protected boolean moving = false;
	protected int xSize, ySize;
	private int hp;
	protected boolean damaged = false;
	protected int damage;
	protected boolean AI = true;
	protected int expValue;
	protected int invulnFrame;
	protected int invulnFrames;
	protected int expLevel;
	public boolean ablaze = false;
	public int burnDamage = 0;
	public int burnInterval = 0;
	public int burnTimer = 0;
	int HV = 1;
	public int maxHp;

	public void update() {
		burnDamage();
	}

	protected void burnDamage() {
		burnInterval++;
		if(burnTimer == 0) ablaze = false;
		burnTimer--;
		if (ablaze && burnInterval > 60) {
			burnInterval = 0;
			setHp(getHp() - burnDamage);
			if (getHp() <= 0) {
				die();
			}
			if (damage > 0) level.addText(new DamageBattleText(x + 20, y, burnDamage + ""));
		} else if (ablaze){
			
			level.add(new FireParticleSpawner((int) (x +sprite.getWidth()/2+ (random.nextGaussian() * sprite.getWidth()) / 4), y +sprite.getHeight()/2+ (int)(random.nextGaussian() * sprite.getHeight())/4, 20, 10, level));
		}
	}

	public void render(Screen screen) {
	}

	public boolean playerIsNear() {
		boolean result = false;
		int px = level.player.x;
		int py = level.player.y;

		int dx = Math.abs(px - x);
		int dy = Math.abs(py - y);

		if (dx < 800 && dy < 100) {
			result = true;
		}

		return result;
	}

	public boolean unitCollision(int nx, int ny) {
		boolean hit = false;
		// if (mobRight >= playerStartX && mobStartX <= playerStartX +
		// playerSizeX && playerStartY + playerSizeY >= mobStartY &&
		// playerStartY <= mobBottom && invulnFrame == invulnFrames) {
		if (nx >= x && nx <= x + 64 && ny >= y && ny <= y + 64) {
			hit = true;
		}
		return hit;
	}

	public boolean collision(int nx, int ny) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((nx) + (c % 2 * xSize));
			double yt = ((ny) + (c / 2 * ySize));
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			// Insert checks here (this checks all 4 corners of the sprite,
			// refine to change hit box)
			if (level.getTile(ix / 32, iy / 32).solid()) solid = true;
		}
		return solid;
	}

	public void takeDamage(int damage) {
		setHp(getHp() - damage);
		damaged = true;
		if (getHp() <= 0) {
			die();
		}
		if (damage > 5) level.add(new BloodParticleSpawner((int) x + 32, (int) y + 64, 800, 100, level));
		if (damage > 0) level.addText(new DamageBattleText(x, y, damage + ""));
	}

	public void killAI() {
		AI = false;
	}

	public void resumeAI() {
		AI = true;
	}

	protected void knockback(int knockback, int dir) {
		if (dir == 1) {
			for (int i = 0; i < knockback; i++) {
				if (!collision(x + 1, y - 1)) {
					x++;
				} else break;
			}
		} else {
			for (int i = 0; i < knockback; i++) {
				if (!collision(x - 1, y - 1)) {
					x--;
				} else break;
			}
		}
	}

	public void hit(Projectile projectile) {
		takeDamage(projectile.damage);
		if (projectile.angle > 3) {
			knockback(projectile.knockback, 0);
		} else knockback(projectile.knockback, 1);
		if (projectile instanceof Arrow) invulnFrame = invulnFrames;
		projectile.effect(this);
		projectile.hit();
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {

		this.hp = hp;
	}

	public void die() {
		Random rand = new Random();
		for (int i = 0; i < rand.nextInt(5) / HV; i++) {
			level.add(new HeartPickup(x, y, level));
		}

		SoundEffect.DEATH.play();

		remove();
		int levelDif = level.player.getEXPLevel() - expLevel;
		if (levelDif == 0) levelDif++;
		int exp = 0;
		if (levelDif < 0) {
			exp = expValue * -levelDif;
		} else if (levelDif > 0) {
			exp = expValue / levelDif;
		}
		level.player.gainEXP(exp);
	}

	public int getDamage() {
		// TODO Auto-generated method stub
		return damage;
	}

	public void init(Level level) {
		this.level = level;
	}

	public boolean playerCollision(int nx, int ny) {
		nx = nx + 32;
		ny = ny + 32;
		boolean hit = false;
		// if (mobRight >= playerStartX && mobStartX <= playerStartX +
		// playerSizeX && playerStartY + playerSizeY >= mobStartY &&
		// playerStartY <= mobBottom && invulnFrame == invulnFrames) {
		if (nx >= x && nx <= x + 64 && ny >= y && ny <= y + 64) {
			hit = true;
		}
		return hit;
	}

	public void initDif(int difficulty) {
		if (difficulty == 1) {
			setHp(getHp() * 2);
			expValue = expValue / 2;
			damage = damage * 2;
			HV = HV / 5;
		}
	}

}
