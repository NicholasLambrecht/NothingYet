package Entity.Mob;

import java.util.Random;

import Entity.Projectile.EArrow;
import Entity.Spawner.BloodParticleSpawner;
import Game.Screen;
import Graphics.Sprite;
import Sounds.SoundEffect;
import UI.DamageBattleText;
import UI.HealthBar;

public class Archer extends Mob {

	private int ay, ax;
	private double aAx, aAy;
	HealthBar hpb;
	private int anim = 0;
	private int animSPD = 3;
	int invulnFrame = 0;
	int invulnFrames = 4;
	private boolean arrowLoaded = true;
	private int loading = 0;
	private int shootingWU = 0;
	private boolean shooting = false;
	private int spd = 2;
	private boolean walking = false;
	private int walkingAni = 0;

	public Archer(int x, int y) {
		sprite = Sprite.archer;
		this.x = x;
		this.y = y;
		setHp(120);
		maxHp = 120;
		damage = 15;
		expValue = 14;
		hpb = new HealthBar(x, y - 6, getHp());
		expLevel = 12;
	}

	public boolean playerIsNear() {
		boolean result = false;
		int px = level.player.x;
		int py = level.player.y;

		int dx = Math.abs(px - x);
		int dy = Math.abs(py - y);

		if (dx < 1350 && dy < 532) {
			result = true;
		}
		return result;
	}

	public void initDif(int difficulty) {
		if (difficulty == 1) {
			setHp(getHp() * 2);
			expValue = expValue / 3;
			damage = damage * 2;
			hpb.updateMaX(getHp());
		}
	}

	public void update() {
		burnDamage();
		if (walking) {
			walkingAni++;
		} else {
			aAx = 0;
			ax = 0;
			walkingAni = 0;
		}
		if (walkingAni > 30) {
			walkingAni = 0;
		}
		invulnFrame++;
		if (invulnFrame >= invulnFrames) {
			invulnFrame = invulnFrames;
		}

		if (AI && playerIsNear() && !shooting) runAI();
		if (shooting) {
			shootingWU++;
			if (shootingWU >= 180 / spd) {
				shoot();
				arrowLoaded = false;
				shooting = false;
				shootingWU = 0;

			}
		}

		if (!collision(x, y + 1)) {
			aAy += 1;
		} else if (aAy > 0) {
			aAy = 0;
		}

		ax += aAx;
		ay += aAy;

		if (ax > 2) {
			ax = 2;
		}

		int nx = x + ax;

		if (ay > 0) {
			int nLy = (int) ay;
			for (int i = 0; i < nLy; i++) {
				if (!collision(x, y + 1)) {
					y++;
				} else break;
			}
		} else if (ay < 0) {
			int nLy = (int) -ay;
			for (int i = 0; i < nLy; i++) {
				if (!collision(x, y - 1)) {
					y--;
				} else break;
			}
		}
		ay = 0;
		if (!collision(nx, y - 1)) {
			if (ax > 0) {
				for (int i = 0; i < ax; i++) {
					x = x + 1;
					if (collision(x, y - 1)) {
						x--;
						i = (int) ax;
					}
				}
			}
			if (ax < 0) {
				int aLx = (int) -ax;
				for (int i = 0; i < aLx; i++) {
					x = x - 1;
					if (collision(x, y - 1)) {
						x++;
						i = (int) aLx;
					}
				}
			}
		}
		ax = 0;
		while (collision(x, y)) {
			y--;
		}

		anim++;
		if (anim > animSPD * 11) anim = 0;
		findSprite();

		hpb.update(x, y - 6, getHp());
		// x = 200; y = 800;
	}

	private void shoot() {
		level.add(new EArrow(x, y + 32, dir, level, damage));
		SoundEffect.ARROWFIRE.play();
	}

	public void takeDamage(int damage) {
		if (invulnFrame >= invulnFrames && damage > 0) {
			setHp(getHp() - damage);
			damaged = true;
			if (getHp() <= 0) {
				die();
			}
			if (damage > 5) level.add(new BloodParticleSpawner((int) x + 64, (int) y + 64, 800, 100, level));
			if (damage > 0) level.addText(new DamageBattleText(x, y, damage + ""));
			invulnFrame = 0;
		}
	}

	private void findSprite() {
		if (dir == 0) {
			sprite = Sprite.archer;
			if (!arrowLoaded) {
				if (loading < 20) {
					sprite = Sprite.archerShoot3;
				} else if (loading < 40) {
					sprite = Sprite.archerReload1;
				} else if (loading < 60) {
					sprite = Sprite.archerReload2;
				} else if (loading < 80) {
					sprite = Sprite.archerReload3;
				}
			}
			if (shooting) {
				if (shootingWU < 90) {
					sprite = Sprite.archer;
				}
				if (shootingWU < 181) {
					sprite = Sprite.archerShoot1;
				}
			}
			if (walking) {
				if (walkingAni < 10) {
					sprite = Sprite.archerWalk1;
				} else if (walkingAni < 20) {
					sprite = Sprite.archerWalk2;
				} else sprite = Sprite.archerWalk3;
			}
		} else {
			sprite = Sprite.archerR;
			if (!arrowLoaded) {
				if (loading < 20) {
					sprite = Sprite.archerShoot3R;
				} else if (loading < 40) {
					sprite = Sprite.archerReload1R;
				} else if (loading < 60) {
					sprite = Sprite.archerReload2R;
				} else if (loading < 80) {
					sprite = Sprite.archerReload3R;
				}
			}
			if (shooting) {
				if (shootingWU < 90) {
					sprite = Sprite.archerR;
				}
				if (shootingWU < 181) {
					sprite = Sprite.archerShoot1R;
				}
			}
			if (walking) {
				if (walkingAni < 10) {
					sprite = Sprite.archerWalk1R;
				} else if (walkingAni < 20) {
					sprite = Sprite.archerWalk2R;
				} else sprite = Sprite.archerWalk3R;
			}
		}
	}

	private void runAI() {
		Random rand = new Random();

		int dx = level.player.x - x;
		walking = false;

		if (!arrowLoaded) {
			loading++;
			if (loading > 160 / spd) {
				arrowLoaded = true;
				loading = 0;
			}
		} else {
			if (Math.abs(dx) > 94) {
				shooting = true;
				if (level.player.x > x) {
					dir = 1;
				}
				if (level.player.x < x) {
					dir = 0;
				}
			} else {
				walking = true;
				if (level.player.x < x) {
					aAx += .2;
					dir = 1;

				}
				if (level.player.x > x) {
					aAx -= .2;
					dir = 0;
				}
				if (rand.nextInt(150) == 1) {
					jump();
				}
			}
		}
	}

	private void jump() {
		if (collision(x, y + 2)) aAy -= 15;
	}

	public boolean collision(int nx, int ny) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((nx + 16) + (c % 2 * 32));
			double yt = ((ny) + (c / 2 * 64));
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			// Insert checks here (this checks all 4 corners of the sprite,
			// refine to change hit box)
			if (level.getTile(ix / 32, iy / 32).solid()) solid = true;
			if (c % 2 == 0) {
				if (level.getTile(ix / 32 + 1, iy / 32).solid()) solid = true;
			}
			if (c / 2 == 1) {
				if (level.getTile(ix / 32, iy / 32 - 1).solid()) solid = true;
			}
		}
		return solid;
	}

	public boolean playerCollision(int nx, int ny) {
		return false;
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

	protected void knockback(int knockback, int dir) {
		if (dir == 1) {
			aAx += knockback / 5;
		} else {
			aAx -= knockback / 5;
		}
	}

	public void render(Screen screen) {
		hpb.render(screen);
		screen.renderSprite(x, y, sprite, true);
	}
}
