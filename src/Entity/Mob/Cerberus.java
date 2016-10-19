package Entity.Mob;

import Entity.Projectile.Arrow;
import Entity.Projectile.BossFireball;
import Entity.Projectile.Projectile;
import Entity.Spawner.BloodParticleSpawner;
import Game.ActionGame;
import Game.Screen;
import Graphics.Font;
import Graphics.Sprite;
import UI.BossHealthBar;
import UI.DamageBattleText;

public class Cerberus extends Mob {

	private int ay, ax;
	private double aAx, aAy;
	private boolean charging = false;
	private int chargingTime = 0;
	private boolean stunned = false;
	private int stunTimer = 0;
	private int stunCD = 300;
	private int invulnFrames = 10;
	private int invulnFrame = 0;
	private int stunFrame = 0;
	private boolean windUp = false;
	private int windUpTimer = 0;
	private int cd = 30;
	private int frameCounter = 0;
	int chargeTimer = 0;
	private int source = 108;
	private boolean stunnable = true;

	private int dir = 0;
	BossHealthBar hpb;

	public Cerberus(int x, int y) {
		this.x = x;
		this.y = y;
		sprite = Sprite.kingThoctar;
		maxHp = 800;
		setHp(800);
		hpb = new BossHealthBar((ActionGame.width - Sprite.bossHPBar.getWidth()) / 2, 600, getHp());
		damage = 20;
		expValue = 300;
		expLevel = 3;
	}

	public void initDif(int difficulty) {
		if (difficulty == 1) {
			setHp(getHp() * 2);
			damage = damage * 2;
			hpb.updateMaX(getHp());
		}
	}

	public void update() {
		// setHp(800);
		burnDamage();
		if (AI && !stunned && !charging && playerIsNear() && !windUp) runAI();
		if (stunned) {
			stunTimer++;
			stunFrame++;
			if (stunTimer > stunCD) {
				stunned = false;
				stunTimer = 0;
			}
		}

		if (dir == 1) {
			source = x + 216 - 64;
		} else if (dir == 0) {
			source = x + 64;
		}

		if (windUp) {
			windUpTimer++;
			if (windUpTimer > 90) {
				charge();
				windUp = false;
			}
			if (level.player.flying) {
				if (cd >= 30) shoot();
				windUpTimer += 3;
				if (windUpTimer > 85) {
					windUpTimer = 0;
				}
			}
		} else windUpTimer = 0;

		cd++;
		if (cd >= 30) {
			cd = 30;
		}

		frameCounter++;
		if (frameCounter > 40) {
			frameCounter = 0;
		}

		invulnFrame++;
		if (invulnFrame >= invulnFrames) {
			invulnFrame = invulnFrames;
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
						if (charging && stunnable) stunned = true;
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
						if (charging && stunnable) stunned = true;
					}
				}
			}
		} else if (charging && stunnable) {
			stunned = true;
			stunFrame = 0;
		}
		ax = 0;
		while (collision(x, y)) {
			y--;
		}
		hpb.update(getHp());
		// x = 200; y = 800;
	}

	private void runAI() {
		int chargeCD = 60;
		int aiX = x + 106;
		chargeTimer++;
		if (chargeTimer >= chargeCD * 10) {
			chargeTimer = chargeCD * 10;
		}
		if (chargeTimer >= chargeCD && level.player.x > x && !charging) {
			windUp = true;
		} else if (chargeTimer >= chargeCD && level.player.x < x && !charging) {
			windUp = true;
		}
		if (level.player.x < aiX) {
			aAx -= .2;
			dir = 0;
		} else if (level.player.x > aiX) {
			aAx += .2;
			dir = 1;
		}
	}

	private void charge() {
		stunnable = true;
		if (dir == 1) {
			charging = true;
			aAx = 19;
			dir = 1;
			chargeTimer = 0;
		} else if (dir == 0) {
			charging = true;
			aAx = -19;
			dir = 0;
			chargeTimer = 0;
		}
	}

	private void shoot() {
		int xClicked = level.player.x;
		int yClicked = level.player.y;
		double dx = xClicked - source + 20;
		double dy = yClicked - y - 44;
		double direction = Math.atan2(dy, dx);
		level.add(new BossFireball(source, y + 64, direction, level));
		cd = 0;
	}

	public void render(Screen screen) {
		if (dir == 0) {
			if (frameCounter >= 0) {
				sprite = Sprite.cerberusWalk1;
			}
			if (frameCounter >= 10) {
				sprite = Sprite.cerberusWalk2;
			}
			if (frameCounter >= 20) {
				sprite = Sprite.cerberusWalk3;
			}
			if (frameCounter >= 30) {
				sprite = Sprite.cerberusWalk2;
			}
			int yx = 5;

			if (stunned) {
				if (stunFrame > 0) {
					sprite = Sprite.cerberusCollide1;
				}
				if (stunFrame > 8) {
					sprite = Sprite.cerberusCollide2;
				}
				if (stunFrame > 16) {
					sprite = Sprite.cerberusStun1;
				}
				if (stunFrame > 32) {
					sprite = Sprite.cerberusStun2;
				}
				if (stunFrame > 48) {
					sprite = Sprite.cerberusStun3;
				}
				if (stunFrame > 64) {
					stunFrame = 17;
					sprite = Sprite.cerberusStun1;
				}
				Font.renderBTSM("STUNNED!", screen, x + 15, y + yx);
				yx += 10;
			}
			if (charging) {
				sprite = Sprite.cerberusLeap1;
				Font.renderBTSM("CHARGING!", screen, x + 15, y + yx);
			}
			if (windUp) {
				if (windUpTimer >= 0) {
					sprite = Sprite.cerberusWindUp1;
				}
				if (windUpTimer > 30) {
					sprite = Sprite.cerberusWindUp2;
				}
				if (windUpTimer > 60) {
					sprite = Sprite.cerberusWindUp3;
				}
			}
		} else if (dir == 1) {
			if (frameCounter >= 0) {
				sprite = Sprite.cerberusWalk1r;
			}
			if (frameCounter >= 10) {
				sprite = Sprite.cerberusWalk2r;
			}
			if (frameCounter >= 20) {
				sprite = Sprite.cerberusWalk3r;
			}
			if (frameCounter >= 30) {
				sprite = Sprite.cerberusWalk2r;
			}
			int yx = 5;

			if (stunned) {
				if (stunFrame > 0) {
					sprite = Sprite.cerberusCollide1r;
				}
				if (stunFrame > 8) {
					sprite = Sprite.cerberusCollide2r;
				}
				if (stunFrame > 16) {
					sprite = Sprite.cerberusStun1r;
				}
				if (stunFrame > 32) {
					sprite = Sprite.cerberusStun2r;
				}
				if (stunFrame > 48) {
					sprite = Sprite.cerberusStun3r;
				}
				if (stunFrame > 64) {
					stunFrame = 17;
					sprite = Sprite.cerberusStun1r;
				}
				Font.renderBTSM("STUNNED!", screen, x + 15, y + yx);
				yx += 10;
			}
			if (charging) {
				sprite = Sprite.cerberusLeap1r;
				Font.renderBTSM("CHARGING!", screen, x + 15, y + yx);
			}
			if (windUp) {
				if (windUpTimer >= 0) {
					sprite = Sprite.cerberusWindUp1r;
				}
				if (windUpTimer > 30) {
					sprite = Sprite.cerberusWindUp2r;
				}
				if (windUpTimer > 60) {
					sprite = Sprite.cerberusWindUp3r;
				}
			}
		}
		if (invulnFrame % 2 == 0) {
			screen.renderSprite(x, y + 1, sprite, true);
		}
		hpb.render(screen);
		Font.renderBTSTSM("CERBERUS", screen, 201, 589);
	}

	public boolean playerIsNear() {
		boolean result = false;
		int px = level.player.x;
		int py = level.player.y;

		int dx = Math.abs(px - x);
		int dy = Math.abs(py - y);

		if (dx < 1250 && dy < 375) {
			result = true;
		}
		return result;
	}

	public void hit(Projectile projectile) {
		if (projectile instanceof Arrow){
			projectile.damage = (int) (projectile.damage /4);
			invulnFrame = invulnFrames;
		}
		projectile.effect(this);
		takeDamage(projectile.damage);
		if (projectile.angle > 3) {
			knockback(projectile.knockback, 0);
		} else knockback(projectile.knockback, 1);
		projectile.hit();
	}

	protected void knockback(int knockback, int dir) {

	}

	public void takeDamage(int damage) {
		if (invulnFrame >= invulnFrames && damage > 0) {
			if (!playerIsNear()) damage = 0;
			if (!stunned) damage = damage / 5;
			setHp(getHp() - damage);
			damaged = true;
			if (getHp() <= 0) {
				die();
			}
			if (damage > 5) level.add(new BloodParticleSpawner((int) source, (int) y + 64, 800, 100, level));
			if (damage > 0) level.addText(new DamageBattleText(x, y, damage + ""));
			invulnFrame = 0;
		}
	}

	public void killAI() {
		// AI = false;
	}

	public boolean collision(int nx, int ny) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((nx + 14) + (c % 2 * 160));
			double yt = ((ny) + (c / 2 * 128));
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

	public boolean unitCollision(int nx, int ny) {
		boolean hit = false;
		// if (mobRight >= playerStartX && mobStartX <= playerStartX +
		// playerSizeX && playerStartY + playerSizeY >= mobStartY &&
		// playerStartY <= mobBottom && invulnFrame == invulnFrames) {
		if (nx >= x + 55 && nx <= x + 71 + 70 && ny >= y && ny <= y + 128) {
			hit = true;
		}
		return hit;
	}

	public boolean playerCollision(int nx, int ny) {
		boolean hit = false;
		if (dir == 1 && nx >= x + 64 && nx <= x + 128 && ny >= y + 16 && ny <= y + 128) { // RIGHT
			hit = true;
			if (!level.player.isDodging()) stunnable = false;

		}
		if (dir == 0 && nx >= x && nx <= x + 64 && ny >= y + 16 && ny <= y + 128) { // LEFT
			hit = true;
			if (!level.player.isDodging()) stunnable = false;

		}
		return hit;
	}

	public void remove() {
		removed = true;
	}

}
