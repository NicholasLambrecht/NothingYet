package Entity.Mob;

import java.util.Random;

import Entity.Projectile.Arrow;
import Entity.Projectile.Projectile;
import Entity.Projectile.SwordAttack;
import Entity.Spawner.BloodParticleSpawner;
import Game.Screen;
import Graphics.Sprite;
import UI.DamageBattleText;
import UI.HealthBar;

public class RhinoDemon extends Mob {

	private int ay, ax;
	private double aAx, aAy;
	HealthBar hpb;
	private boolean charging = false;
	private int chargingTime = 0;
	private boolean stunned = false;
	private int stunTimer = 0;
	private int stunCD = 300;
	int invulnFrame = 0;
	int invulnFrames = 4;
	private int frameCounter = 0;
	private boolean attacking = false;
	private int aniSpeed = 1;
	private int cd = 0;

	public RhinoDemon(int x, int y) {
		sprite = Sprite.rhinoWalk1;
		this.x = x;
		this.y = y;
		maxHp = 150;
		setHp(150);
		expValue = 15;
		damage = 10;
		hpb = new HealthBar(x, y - 6, 150);
		expLevel = 1;
	}

	public void initDif(int difficulty) {
		if (difficulty == 1) {
			setHp(getHp() * 2);
			expValue = expValue / 3;
			damage = damage * 2;
			hpb.updateMaX(getHp());
		}
	}

	public boolean playerIsNear() {
		boolean result = false;
		int px = level.player.x;
		int py = level.player.y;

		int dx = Math.abs(px - x);
		int dy = Math.abs(py - y);

		if (dx < 800 && dy < 532) {
			result = true;
		}

		return result;
	}

	public void update() {
		// if(playerIsNear()){
		// System.out.println(charging+"||"+attacking);
		// }
		burnDamage();
		frameCounter += aniSpeed;
		if (frameCounter > 40) {
			frameCounter = 0;
		}
		// x = 150;
		// y = 1175;

		invulnFrame++;
		if (invulnFrame >= invulnFrames) {
			invulnFrame = invulnFrames;
		}

		if (AI && !stunned && !charging && playerIsNear()) runAI();
		if (stunned) {
			charging = false;
			stunTimer++;
			if (stunTimer > stunCD) {
				stunned = false;
				stunTimer = 0;
			}
		}

		if (!collision(x, y + 1)) {
			aAy += 1;
		} else if (aAy > 0) {
			aAy = 0;
		}
		if (aAy > 10) {
			aAy = 10;
		}

		if (!charging) {
			aAx = aAx * .9;
			if (aAx > 6) {
				aAx = 6;
			}
			if (aAx < -6) {
				aAx = -6;
			}
		} else {
			chargingTime++;
			if (chargingTime > 60) {
				charging = false;
				chargingTime = 0;
				aAx *= .5;
			}
		}
		ax += aAx;
		ay += aAy;

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

		if (ax > 0) {
			for (int i = 0; i < ax; i++) {
				x = x + 1;
				if (collision(x, y - 1)) {
					x--;
					i = (int) ax;
					if (charging) {
					//	stunned = true;
					}
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
					if (charging) {
					//	stunned = true;
					}
				}
			}
		}

		ax = 0;
		while (collision(x, y)) {
			y--;
		}
		hpb.update(x, y - 6, getHp());
		// x = 200; y = 800;
	}

	private void runAI() {
		int cdTimer = 60;
		cd++;
		if (cd > cdTimer) {
			cd = cdTimer;
		}
		Random rand = new Random();
		if (rand.nextInt(150) == 1) {
			jump();
		}
		if (rand.nextInt(150) == 0 && level.player.x > x && cd >= cdTimer) {
			charging = true;
			frameCounter = 0;
			aAx = 10;
			dir = 1;
			cd = 0;
		}
		if (rand.nextInt(150) == 0 && level.player.x < x && cd >= cdTimer) {
			charging = true;
			frameCounter = 0;
			aAx = -10;
			dir = 0;
			cd = 0;
		}
		if (Math.abs(level.player.x - x) < 5 && cd >= cdTimer) {
			attacking = true;
			frameCounter = 20;
			cd = 0;
		}
		if (attacking) {
			if (frameCounter < 20) attacking = false;
		}

		if (level.player.x < x) {
			aAx -= .2;
			dir = 0;
		} else if (level.player.x > x) {
			aAx += .2;
			dir = 1;
		}
	}

	private void jump() {
		if (collision(x, y + 2)) aAy -= 15;
	}

	public boolean collision(int nx, int ny) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((nx) + (c % 2 * 60));
			double yt = ((ny) + (c / 2 * 60));
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
		boolean hit = false;
		nx = nx +32;
		ny = ny + 32;
		// if (mobRight >= playerStartX && mobStartX <= playerStartX +
		// playerSizeX && playerStartY + playerSizeY >= mobStartY &&
		// playerStartY <= mobBottom && invulnFrame == invulnFrames) {
		if (charging || (attacking && frameCounter > 30) && !stunned) {
			if (nx >= x && nx <= x + 64 && ny >= y && ny <= y + 64) {
				hit = true;
			}
		}
		return hit;
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

	public void hit(Projectile projectile) {
		takeDamage(projectile.damage);
		if (projectile.angle > 3) {
			knockback(projectile.knockback, 0);

		} else knockback(projectile.knockback, 1);
		if (projectile instanceof SwordAttack) {
			stunned = true;
			stunCD = 90;
		}
		if(projectile instanceof Arrow) invulnFrame = invulnFrames;
		projectile.effect(this);
		projectile.hit();
	}

	protected void knockback(int knockback, int dir) {
		if (dir == 1) {
			aAx += knockback / 5;
		} else {
			aAx -= knockback / 5;
		}
	}

	public void render(Screen screen) {
		if (dir == 1) {
			if (charging) {
				if (frameCounter > 30) {
					sprite = Sprite.rhinoCharge4;
				} else if (frameCounter > 20) {
					sprite = Sprite.rhinoCharge3;
				} else if (frameCounter > 10) {
					sprite = Sprite.rhinoCharge2;
				} else sprite = Sprite.rhinoCharge1;
			} else if (stunned) {
				if (frameCounter > 30) {
					sprite = Sprite.rhinoStun4;
				} else if (frameCounter > 20) {
					sprite = Sprite.rhinoStun3;
				} else if (frameCounter > 10) {
					sprite = Sprite.rhinoStun2;
				} else sprite = Sprite.rhinoStun1;
			} else {
				if (frameCounter > 30) {
					sprite = Sprite.rhinoWalk4;
				} else if (frameCounter > 20) {
					sprite = Sprite.rhinoWalk3;
				} else if (frameCounter > 10) {
					sprite = Sprite.rhinoWalk2;
				} else sprite = Sprite.rhinoWalk1;
			}
			if (attacking) {
				if (frameCounter > 30) {
					sprite = Sprite.rhinoAttack2;
				} else {
					sprite = Sprite.rhinoAttack1;
				}
			}

		} else {
			if (charging) {
				if (frameCounter > 30) {
					sprite = Sprite.rhinoCharge4r;
				} else if (frameCounter > 20) {
					sprite = Sprite.rhinoCharge3r;
				} else if (frameCounter > 10) {
					sprite = Sprite.rhinoCharge2r;
				} else sprite = Sprite.rhinoCharge1r;
			} else if (stunned) {
				if (frameCounter > 30) {
					sprite = Sprite.rhinoStun4r;
				} else if (frameCounter > 20) {
					sprite = Sprite.rhinoStun3r;
				} else if (frameCounter > 10) {
					sprite = Sprite.rhinoStun2r;
				} else sprite = Sprite.rhinoStun1r;
			} else {
				if (frameCounter > 30) {
					sprite = Sprite.rhinoWalk4r;
				} else if (frameCounter > 20) {
					sprite = Sprite.rhinoWalk3r;
				} else if (frameCounter > 10) {
					sprite = Sprite.rhinoWalk2r;
				} else sprite = Sprite.rhinoWalk1r;
			}
			if (attacking) {
				if (frameCounter > 30) {
					sprite = Sprite.rhinoAttack2r;
				} else {
					sprite = Sprite.rhinoAttack1r;
				}
			}
		}
		hpb.render(screen);
		screen.renderSprite(x, y + 1, sprite, true);
	}
}
