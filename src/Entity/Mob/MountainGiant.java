package Entity.Mob;

import java.util.Random;

import Entity.Projectile.Arrow;
import Entity.Projectile.BossBoulder;
import Entity.Projectile.BossFireball;
import Entity.Projectile.Projectile;
import Entity.Projectile.Spike;
import Entity.Spawner.BloodParticleSpawner;
import Game.ActionGame;
import Game.Screen;
import Graphics.Font;
import Graphics.Sprite;
import Level.Level2;
import Level.Tile;
import UI.BossHealthBar;
import UI.DamageBattleText;

public class MountainGiant extends Mob {

	private int ay, ax;
	private double aAx, aAy;
	private boolean charging = false;
	private boolean stunned = false;
	private int invulnFrames = 10;
	private int invulnFrame = 0;
	private boolean windUp = false;
	private int windUpTimer = 0;
	private int cd = 30;
	private int frameCounter = 0;
	int chargeTimer = 0;
	private int source = 108;
	private boolean stomping = false;
	private int stompingTimer = 0;
	private boolean climbing = false;
	private boolean wallPunching = false;
	private boolean punching = false;
	private boolean avalanche = false;
	private int punchAni = 0;
	private boolean AI = false;
	private boolean triggerCycle = false;
	private boolean stompWall = false;
	private int dir = 0;
	BossHealthBar hpb;

	public MountainGiant(int x, int y) {
		this.x = x;
		this.y = y;
		sprite = Sprite.kingThoctar;
		maxHp = 1200;
		setHp(maxHp);
		hpb = new BossHealthBar((ActionGame.width - Sprite.bossHPBar.getWidth()) / 2, 600, getHp());
		damage = 27;
		expValue = 400;
		expLevel = 7;
	}

	public void initDif(int difficulty) {
		if (difficulty == 1) {
			setHp(getHp() * 2);
			expValue = expValue / 3;
			maxHp = getHp();
			damage = damage * 2;
			hpb.updateMaX(getHp());
		}
	}

	public void update() {
		// setHp(800);
		burnDamage();

		if (getHp() < maxHp / 2 && !avalanche) {
			wallPunch();
		}

		if (AI && !stunned && !charging && playerIsNear() && !windUp && !climbing && !wallPunching && !stomping) runAI();

		if (climbing) y -= 4;

		if (dir == 1) {
			source = x + 216 - 64;
		} else if (dir == 0) {
			source = x + 64;
		}

		if (punching) {
			punchAni++;
			if (punchAni > 50) {
				punching = false;
				avalanche = true;
				wallPunching = false;
				level.trigger();
			}
		}

		if (windUp) {
			int spd = 2;
			// if (getHp() < maxHp / 4) spd = 4;
			windUpTimer += spd;
			if (windUpTimer > 60) {
				throwBoulder();
				windUp = false;
				windUpTimer = 0;
				if (triggerCycle == false) {
					stomping = true;
					triggerCycle = true;
					stompingTimer = -100;
					dir = 0;
				}
			}
			if (level.player.flying) {
				if (cd >= 30) shoot();
				windUpTimer += 3;
				if (windUpTimer > 45) {
					windUpTimer = 0;
				}
			}
		} else windUpTimer = 0;

		if (stomping) {
			int spd = 2;
			// if (getHp() < maxHp / 4) spd = 4;
			stompingTimer += spd;
			if (stompingTimer > 70) {
				stomp();
				stomping = false;
				stompingTimer = 0;
				if (stompWall == false) {
					stompWall = true;
					Level2 temp = (Level2) level;
					temp.stompWall();
					AI = true;
				}
			}
		}

		cd++;
		if (cd >= 30) {
			cd = 30;
		}
		int spd = 2;
		frameCounter += spd;
		if (frameCounter > 90) {
			frameCounter = 0;
		}

		invulnFrame++;
		if (invulnFrame >= invulnFrames) {
			invulnFrame = invulnFrames;
		}

		if (!collision(x, y + 1) && !climbing) {
			aAy += 1;
		} else if (aAy > 0) {
			aAy = 0;
		}
		if (aAy > 10) {
			aAy = 10;
		}

		aAx = aAx * .9;
		if (aAx > 6) {
			aAx = 6;
		}
		if (aAx < -6) {
			aAx = -6;
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
		hpb.update(getHp());
		// x = 200; y = 800;
	}

	private void wallPunch() {
		wallPunching = true;
		if (x < 829 * 32) {
			aAx += .3;
			dir = 1;
		} else {
			punching = true;
		}
	}

	private void stomp() {
		int delay = 10;
		if (getHp() < maxHp / 4) {
			delay = 2;
		}
		if (dir == 1) {
			for (int i = 0; i < 20; i++) {
				int yLoc = y;
				int xLoc = x + 167 - ((x + 167) % 32) + (i * 32);
				while (!(level.getTile((xLoc / 32), (yLoc) / 32) == Tile.mGrass)) {
					yLoc += 32;
					if (yLoc > y + 6 * 32) break;
				}
				level.add(new Spike(xLoc, yLoc, i * delay));
			}
		} else {
			for (int i = 0; i < 20; i++) {
				int yLoc = y;
				int xLoc = x - (x % 32) - (i * 32);
				while (!(level.getTile((xLoc / 32), (yLoc) / 32) == Tile.mGrass)) {
					yLoc += 32;
					if (yLoc > y + 6 * 32) break;
				}
				level.add(new Spike(xLoc, yLoc, i * delay));

			}
		}
	}

	private void throwBoulder() {
		if (dir == 1) {
			int nx = x + 100;
			int ny = y;
			if (getHp() < maxHp / 4) {
				level.add(new BossBoulder(nx, ny + 25, 1));
				level.add(new BossBoulder(nx, ny - 25, 1));
			}
			level.add(new BossBoulder(nx, ny, 1));
		} else {
			int nx = x;
			int ny = y;
			if (getHp() < maxHp / 4) {
				level.add(new BossBoulder(nx, ny + 25, 1));
				level.add(new BossBoulder(nx, ny - 25, 1));
			}
			level.add(new BossBoulder(nx, ny, 0));
		}
	}

	private void runAI() {
		int chargeCD = 290;
		int aiX = x + 76;
		if (getHp() < maxHp / 8) chargeCD = 100;
		Random move = new Random();
		chargeTimer++;
		if (chargeTimer >= chargeCD * 10) {
			chargeTimer = chargeCD * 10;
		}
		if (chargeTimer >= chargeCD && move.nextInt(4) < 2) {
			windUp = true;
			chargeTimer = 0;
		} else if (chargeTimer >= chargeCD) {
			stomping = true;
			stompingTimer = 0;
			chargeTimer = 0;
		}

		if (level.player.x + 32 < aiX && !avalanche) {
			aAx -= .2;
			dir = 0;
		} else if (level.player.x + 32 > aiX && !avalanche) {
			aAx += .2;
			dir = 1;
		} else if (avalanche) {
			dir = 0;
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
		int xOffset = 0;
		int yOffset = 0;

		if (dir == 0) {
			if (frameCounter >= 0) {
				sprite = Sprite.golemWalk1;
			}
			if (frameCounter >= 10) {
				sprite = Sprite.golemWalk2;
			}
			if (frameCounter >= 20) {
				sprite = Sprite.golemWalk3;
			}
			if (frameCounter >= 30) {
				sprite = Sprite.golemWalk4;
			}
			if (frameCounter >= 40) {
				sprite = Sprite.golemWalk5;
			}
			if (frameCounter >= 50) {
				sprite = Sprite.golemWalk4;
			}
			if (frameCounter >= 60) {
				sprite = Sprite.golemWalk3;
			}
			if (frameCounter >= 70) {
				sprite = Sprite.golemWalk2;
			}
			if (frameCounter >= 80) {
				sprite = Sprite.golemWalk1;
			}

			if (climbing) {
				if (frameCounter >= 0) {
					sprite = Sprite.golemClimb1;
				}
				if (frameCounter >= 10) {
					sprite = Sprite.golemClimb2;
				}
				if (frameCounter >= 20) {
					sprite = Sprite.golemClimb3;
				}
				if (frameCounter >= 30) {
					sprite = Sprite.golemClimb4;
				}
			}

			if (windUp) {
				if (windUpTimer >= 0) {
					sprite = Sprite.golemThrow1;
				}
				if (windUpTimer > 10) {
					sprite = Sprite.golemThrow2;
				}
				if (windUpTimer > 20) {
					sprite = Sprite.golemThrow3;
				}
				if (windUpTimer > 30) {
					sprite = Sprite.golemThrow4;
					yOffset = -29;
				}
				if (windUpTimer > 40) {
					yOffset = -29;
					sprite = Sprite.golemThrow5;
				}
				if (windUpTimer > 50) {
					sprite = Sprite.golemThrow6;
				}
			}

			if (stomping) {
				if (stompingTimer >= 0) {
					xOffset = 0;
					yOffset = 0;
					sprite = Sprite.golemSlam1;
				}
				if (stompingTimer > 10) {
					xOffset = 0;
					yOffset = 0;
					sprite = Sprite.golemSlam2;
				}
				if (stompingTimer > 20) {
					xOffset = -5;
					yOffset = 0;
					sprite = Sprite.golemSlam3;
				}
				if (stompingTimer > 30) {
					xOffset = -70;
					yOffset = 0;
					sprite = Sprite.golemSlam4;
				}
				if (stompingTimer > 40) {
					xOffset = -70;
					yOffset = 0;
					sprite = Sprite.golemSlam5;
				}
				if (stompingTimer > 50) {
					xOffset = -70;
					yOffset = 0;
					sprite = Sprite.golemSlam6;
				}
				if (stompingTimer > 60) {
					xOffset = -70;
					yOffset = 0;
					sprite = Sprite.golemSlam7;
				}

			}

			if (punching) {
				if (punchAni >= 0) {
					sprite = Sprite.golemPunch1;
				}
				if (punchAni >= 10) {
					sprite = Sprite.golemPunch2;
				}
				if (punchAni >= 20) {
					sprite = Sprite.golemPunch3;
				}
				if (punchAni >= 30) {
					sprite = Sprite.golemPunch4;
				}
				if (punchAni >= 40) {
					sprite = Sprite.golemPunch5;
				}

			}

		} else if (dir == 1) {
			if (frameCounter >= 0) {
				sprite = Sprite.golemWalk1r;
			}
			if (frameCounter >= 10) {
				sprite = Sprite.golemWalk2r;
			}
			if (frameCounter >= 20) {
				sprite = Sprite.golemWalk3r;
			}
			if (frameCounter >= 30) {
				sprite = Sprite.golemWalk4r;
			}
			if (frameCounter >= 40) {
				sprite = Sprite.golemWalk5r;
			}
			if (frameCounter >= 50) {
				sprite = Sprite.golemWalk4r;
			}
			if (frameCounter >= 60) {
				sprite = Sprite.golemWalk3r;
			}
			if (frameCounter >= 70) {
				sprite = Sprite.golemWalk2r;
			}
			if (frameCounter >= 80) {
				sprite = Sprite.golemWalk1r;
			}

			if (climbing) {
				if (frameCounter >= 0) {
					sprite = Sprite.golemClimb1r;
				}
				if (frameCounter >= 10) {
					sprite = Sprite.golemClimb2r;
				}
				if (frameCounter >= 20) {
					sprite = Sprite.golemClimb3r;
				}
				if (frameCounter >= 30) {
					sprite = Sprite.golemClimb4r;
				}
			}

			if (windUp) {
				if (windUpTimer >= 0) {
					xOffset = 0;
					sprite = Sprite.golemThrow1r;
				}
				if (windUpTimer > 10) {
					xOffset = 30;
					sprite = Sprite.golemThrow2r;
				}
				if (windUpTimer > 20) {
					xOffset = -4;
					sprite = Sprite.golemThrow3r;
				}
				if (windUpTimer > 30) {
					sprite = Sprite.golemThrow4r;
					xOffset = 26;
					yOffset = -29;
				}
				if (windUpTimer > 40) {
					sprite = Sprite.golemThrow5r;
					xOffset = -31;
					yOffset = -29;
				}
				if (windUpTimer > 55) {
					sprite = Sprite.golemThrow6r;
					xOffset = -8;
					yOffset = 0;
				}

			}

			if (stomping) {
				if (stompingTimer >= 0) {
					xOffset = 0;
					yOffset = 0;
					sprite = Sprite.golemSlam1r;
				}
				if (stompingTimer > 10) {
					xOffset = 0;
					yOffset = 0;
					sprite = Sprite.golemSlam2r;
				}
				if (stompingTimer > 20) {
					xOffset = 5;
					yOffset = 0;
					sprite = Sprite.golemSlam3r;
				}
				if (stompingTimer > 30) {
					xOffset = 70;
					yOffset = 0;
					sprite = Sprite.golemSlam4r;
				}
				if (stompingTimer > 40) {
					xOffset = 70;
					yOffset = 0;
					sprite = Sprite.golemSlam5r;
				}
				if (stompingTimer > 50) {
					xOffset = 70;
					yOffset = 0;
					sprite = Sprite.golemSlam6r;
				}
				if (stompingTimer > 60) {
					xOffset = 70;
					yOffset = 0;
					sprite = Sprite.golemSlam7r;
				}
			}

			if (punching) {
				if (punchAni >= 0) {
					sprite = Sprite.golemPunch1r;
				}
				if (punchAni >= 10) {
					sprite = Sprite.golemPunch2r;
				}
				if (punchAni >= 20) {
					sprite = Sprite.golemPunch3r;
				}
				if (punchAni >= 30) {
					sprite = Sprite.golemPunch4r;
				}
				if (punchAni >= 40) {
					sprite = Sprite.golemPunch5r;
				}

			}
		}
		if (invulnFrame % 2 == 0) {
			screen.renderSprite(x + xOffset, y + 1 + yOffset, sprite, true);
		}
		if (stompWall) {
			hpb.render(screen);
			Font.renderBTSTSM("MOUNTAIN GIANT", screen, 201, 589);
		}
	}

	public boolean playerIsNear() {
		boolean result = false;
		int px = level.player.x;
		int py = level.player.y;
		// if (py < y) py += 825;
		int dx = Math.abs(px - x);
		int dy = Math.abs(py - y);

		if (dx < 2550 && dy < 825) {
			result = true;
		}
		return result;
	}

	public void hit(Projectile projectile) {
		if (projectile instanceof Arrow) {
			projectile.damage = projectile.damage / 2;
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

	public void killAI() {
		// AI = false;
	}

	public void takeDamage(int damage) {
		if (invulnFrame >= invulnFrames && damage > 0) {
			if (getHp() < maxHp / 8) damage = (int) (damage * 1.5);
			if (windUp || stomping) damage = damage * 2;
			damage = damage / 5;
			if (wallPunching || !triggerCycle) damage = 0;
			setHp(getHp() - damage);
			damaged = true;
			if (getHp() <= 0) {
				die();
			}
			if (damage > 10) level.add(new BloodParticleSpawner(x + 65, (int) y + 64, 800, 100, level));
			if (damage > 0 || wallPunching) level.addText(new DamageBattleText(x, y, damage + ""));
			invulnFrame = 0;
		}
	}

	public boolean collision(int nx, int ny) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((nx + 56) + (c % 2 * 22));
			double yt = ((ny) + (c / 2 * 167));
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			// Insert checks here (this checks all 4 corners of the sprite,
			// refine to change hit box)
			if (level.getTile(ix / 32, iy / 32).solid()) solid = true;
			if (c / 2 == 1 && c % 2 == 0) {
				if (dir == 0) {
					if (level.getTile(((ix - 1) / 32), (iy / 32) - 0).solid()) {
						climbing = true;
					} else climbing = false;
				} else climbing = false;
				if (level.getTile(ix / 32 + 1, iy / 32).solid()) solid = true;
				if (level.getTile(ix / 32 + 2, iy / 32).solid()) solid = true;
			}
		}
		return solid;
	}

	public boolean unitCollision(int nx, int ny) {
		boolean hit = false;
		// if (mobRight >= playerStartX && mobStartX <= playerStartX +
		// playerSizeX && playerStartY + playerSizeY >= mobStartY &&
		// playerStartY <= mobBottom && invulnFrame == invulnFrames) {
		if (nx >= x + 35 && nx <= x + 61 + 70 && ny >= y + 24 && ny <= y + 167) {
			hit = true;
		}
		return hit;
	}

	public boolean playerCollision(int nx, int ny) {
		boolean hit = false;
		nx = nx + 32;
		ny = ny + 32;
		if (stomping || windUp) {
			if (nx >= x + 32 && nx <= x + 115 && ny >= y && ny <= y + 167) { // RIGHT
				hit = true;
			}

		}
		return hit;
	}

	public void remove() {
		removed = true;
	}

	public void trigger() {
		dir = 1;
		windUp = true;
	}

}
