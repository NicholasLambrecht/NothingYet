package Entity.Mob;

import java.util.Random;

import Entity.Projectile.Arrow;
import Entity.Projectile.CircleOfFire;
import Entity.Projectile.Fireball;
import Entity.Projectile.PillarOfFire;
import Entity.Projectile.Projectile;
import Entity.Projectile.Spike;
import Entity.Projectile.SwordAttack;
import Entity.Spawner.BloodParticleSpawner;
import Game.ActionGame;
import Game.Screen;
import Graphics.RivalAnimation;
import Graphics.Sprite;
import Sounds.SoundEffect;
import UI.BossHealthBar;
import UI.DamageBattleText;

public class Rival extends Mob {
	public int x, y;
	public int aniFrame;
	private double ax;
	private double ay;
	public double aAx;
	private double aAy;
	private int sp;
	private int mp;
	private boolean attacking = false;
	private boolean crouching = false;
	private boolean jumping = false;
	private boolean idle = true;
	private boolean gettingup = false;
	private boolean turning = false;
	private boolean stopping = false;
	private boolean Cattacking = false;
	private boolean falling = false;
	private boolean landing = false;
	private boolean dodging = false;
	private boolean freeze = false;
	private int aniSpeed = 1;
	private int dir = 0;
	private int idleAni = 0;
	private int moveAni = 0;
	private int turnAni = 0;
	private int stopAni = 0;
	private int guAni = 12 * 5 * aniSpeed;
	private int atkAni = 0;
	private int catkAni = 0;
	private int crouchAni = 0;
	private int jumpAni = 0;
	private int fallAni = 0;
	private int landAni = 0;
	private int swAtk = 0;
	private int swimAni = 0;
	private boolean swAtking;
	private Sprite sprite = Sprite.voidSprite;
	private int invulnFrame = 40;
	private int invulnFrames = 40;
	private int dodgeFrame = 40;
	private int dodgeFrames = 40;
	private boolean flicker = false;
	public boolean returning = false;
	private boolean casting = false;
	private int castAni = 0;
	private boolean shield = false;
	private int shieldTimer = 0;
	public boolean flying = false;
	private int flyAni = 0;
	public boolean flyingUnlock = false;
	private int dodgeAni = 0;
	private int maxHp;
	public int difficulty;
	public boolean gameover = false;
	private boolean swimUp = false;
	private boolean forceRight = false;
	private boolean climbing = false;
	int spRegen = 10;
	private boolean glow = false;
	int chillTimer = 0;
	public boolean archery = false;
	public int chx;
	public int chy;
	public int arrows = 4;
	private int maxArrows = 4;
	private int arrowRegen = 0;
	public int talentPoints = 0;
	private double maxMove = 6.5;
	public boolean speedTalent = false;
	public boolean rangeTalent = false;
	public boolean vorpalbladeTalent = false;
	private boolean timeStop = false;
	public boolean worldMap = false;
	private BossHealthBar hpb;
	private boolean walking = false;

	public Rival(int x, int y) {
		this.x = x;
		this.y = y;
		aniFrame = 0;
		new RivalAnimation();
		setHp(100);
		maxHp = 2000;
		setHp(maxHp);
		hpb = new BossHealthBar((ActionGame.width - Sprite.bossHPBar.getWidth()) / 2, 600, getHp());
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

	public void update() {
		hpb.update(getHp());
		if (getHp() > maxHp) setHp(maxHp);

		checkActions();
		if (AI && playerIsNear()) runAI();
		checkCombatActions();
		checkUpdatedActions();

		gravity();
		ax += aAx;
		ay += aAy;
		movementChecks();
		collisionCheck();

		updateFrames();
		setSprite();

		if (invulnFrame < invulnFrames && !dodging) {
			if (flicker) flicker = false;
			else flicker = true;
		} else flicker = false;
	}

	private void runAI() {
		if (moving && !walking && !dodging && !forceRight) {
			stopping = true;
		}

		if (jumping) {
			turning = false;
		}
		if (level.player.x < x) {
			landing = false;
			if (dir == 1) {
				turning = true;
				aAx = 0;
			}
			walking = true;
			aAx += -1.06;
			if ((!turning) || jumping) {
				aAx += -.09;
			}
			dir = 0;
			stopping = false;
		} else if (level.player.x > x) {
			landing = false;
			if (dir == 0) {
				turning = true;
				aAx = 0;
			}
			walking = true;
			aAx += 1.06;
			if ((!turning) || jumping) {
				aAx += .09;
			}
			dir = 1;
			stopping = false;
		} else walking = false;

		if (aAx != 0) {
			moving = true;
		} else moving = false;
	}

	private void shootArrow() {
		Random rand = new Random();
		int xClicked = chx - 2 + rand.nextInt(4);
		int yClicked = chy - 2 + rand.nextInt(4);
		int xOrigin = x + 32;
		int yOrigin = y + 32;
		double dx = xClicked - xOrigin;
		double dy = yClicked - yOrigin;
		double direction = Math.atan2(dy, dx);
		Arrow a = new Arrow(xOrigin, yOrigin, direction, level);
		a.damage = (int) (a.damage * 1.25);
		level.add(a);
	}

	private void collisionCheck() {
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

		int nx = (int) (x + ax);
		if (ax != 0 && !collision(nx, y)) {
			if (ax > 0) {
				for (int i = 0; i < ax; i++) {
					x = x + 1;
					if (collision(x, y)) {
						x--;
						i = (int) ax;
					}
				}
			}
			if (ax < 0) {
				int aLx = (int) -ax;
				for (int i = 0; i < aLx; i++) {
					x = x - 1;
					if (collision(x, y)) {
						x++;
						i = (int) aLx;
					}
				}
			}
		}
		while (collision(x, y)) {
			y--;
		}
	}

	private void movementChecks() {
		if (landing && !dodging) ax = 0;
		if (ay <= 0) falling = false;
		else if (!attacking) falling = true;
		maxMove = 6.5;
		if (ax > maxMove && !dodging) ax = maxMove;
		if (ax < -maxMove && !dodging) ax = -maxMove;
		if (!walking && !dodging && !forceRight) {
			if (!stopping) ax = 0;
			else {
				if (ax > 0) {
					ax = ax / 2;
					ax = (int) Math.floor(ax);
					if (ax == 1) ax = 0;
				} else if (ax < 0) {
					ax = ax / 2;
					ax = (int) Math.ceil(ax);
					if (ax == 1) ax = 0;
				}
			}
			aAx = 0;
		}
		if (turning && !dodging && !walking) {
			ax = ax / 1.1;
			if (ax > 0 && !dodging && !walking) {
				ax = ax / 10;
				ax = (int) Math.ceil(ax);
			} else if (ax < 0 && !dodging && !walking) {
				ax = ax / 10;
				ax = (int) Math.floor(ax);
			}
		}
	}

	private void checkUpdatedActions() {
		if (!moving && !crouching && !attacking && !turning && !flying) {
			aniFrame = 0;
			idle = true;
		} else idle = false;
	}

	private void gravity() {

		if (!collision(x, y + 2) && !flying && !climbing) {
			aAy += 1;
		} else if (!jumping && aAy > 0 && !flying && !climbing) {
			landing = true;
			aAy = 0;
		}
	}

	private void checkCombatActions() {
		//
		//		fireball();
		//
		//		pillars_of_fire();
		//
		//		crouchFireball();

		if (shield) {
			shieldTimer++;
			level.add(new CircleOfFire(x + 32, y + 32, level));
			if (shieldTimer > 20) {
				shieldTimer = 0;
			}
		}
	}

	private void crouchFireball() {
		Cattacking = true;
		if (dir == 0) shoot(x, y + 35, dir);
		else shoot(x + 48, y + 35, dir);
		mp -= 5;
		SoundEffect.FIREBALL.play();
	}

	private void fireball() {
		attacking = true;
		if (dir == 0) shoot(x + 16, y + 18, dir);
		else shoot(x + 48, y + 18, dir);
		mp -= 5;
		SoundEffect.FIREBALL.play();
	}

	public void manabladeFireball() {
		if (dir == 0) shoot(x + 16, y + 18, dir);
		else shoot(x + 48, y + 18, dir);
		SoundEffect.FIREBALL.play();
	}

	private void shield() {
		if (shield) {
			SoundEffect.SHIELDEND.play();
			shield = false;
		} else if (mp > 10) {
			SoundEffect.SHIELDBGN.play();
			shield = true;
		}
	}

	private void checkActions() {
	}

	private void swordAttack() {
		level.add(new SwordAttack(x, y, level, dir));
		Random rand = new Random();
		int r = rand.nextInt(3);
		if (r == 0) SoundEffect.SWORDSWING1.play();
		else if (r == 1) SoundEffect.SWORDSWING2.play();
		else SoundEffect.SWORDSWING3.play();
	}

	public void pillars_of_fire() {
		int result = 288;
		PillarOfFire pof1 = new PillarOfFire(x + 96, y + result, level);
		level.add(pof1);
		result = 288;
		PillarOfFire pof = new PillarOfFire(x - 80, y + result, level);
		level.add(pof);
		SoundEffect.PILLAR.play();
		if (rangeTalent) {
			PillarOfFire pof3 = new PillarOfFire(x - 160, y + result, level);
			level.add(pof3);
			PillarOfFire pof4 = new PillarOfFire(x + 176, y + result, level);
			level.add(pof4);
			pof1.damage = (int) (pof1.damage * 1.5);
			pof.damage = (int) (pof.damage * 1.5);
			pof3.damage = (int) (pof3.damage * 1.5);
			pof4.damage = (int) (pof4.damage * 1.5);
		}
	}

	void shoot(int x, int y, double dir) {
		double angle;
		if (dir == 0) {
			angle = 3.141592653589;
		} else angle = 00.0;
		Projectile p = new Fireball(x, y, angle, level);
		level.add(p);
	}

	public void hit(Projectile projectile) {
		if (projectile instanceof Arrow) {
			projectile.damage = (int) (projectile.damage / 4);
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

	private void updateFrames() {
		if (dodgeFrame < dodgeFrames) invulnFrame = 38;
		invulnFrame++;
		if (invulnFrame > invulnFrames) {
			invulnFrame = invulnFrames;
			returning = false;
		}
		dodgeFrame++;
		if (dodgeFrame > dodgeFrames) {
			dodgeFrame = dodgeFrames;
		}
		if (idle) {
			idleAni++;
		} else idleAni = 0;

		if (stopping) {
			stopAni++;
		} else stopAni = 0;

		if (attacking) {
			atkAni++;
		} else atkAni = 0;

		if (gettingup) {
			guAni++;
		}

		if (moving && !turning && !stopping) {
			moveAni++;
		} else moveAni = 0;

		if (turning) {
			turnAni++;
		} else turnAni = 0;

		if (jumping) {
			jumpAni++;
		} else jumpAni = 0;

		if (falling && !jumping) {
			fallAni++;
		} else fallAni = 0;

		if (landing) {
			landAni++;
		} else landAni = 0;

		if (crouching) {
			crouchAni++;
		} else crouchAni = 0;

		if (Cattacking) {
			catkAni++;
		} else catkAni = 0;

		if (casting) {
			castAni++;
		} else castAni = 0;

		if (castAni > 40) {
			castAni = 0;
			casting = false;
		}

		if (swAtking) {
			swAtk++;
		} else swAtk = 0;

		if (dodging) {
			dodgeAni++;
		} else dodgeAni = 0;

		if (landAni == 1) SoundEffect.CLOCK.play();

		if (flying) {
			flyAni++;
		} else flyAni = 0;

		if (swimUp) {
			swimAni++;
		} else swimAni = 0;

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

	private void setSprite() {
		sprite = RivalAnimation.rivalIdleLeft[0];

		if (dir == 0) {
			if (idle) {
				int spd = 10;
				if (idleAni > aniSpeed * (spd * 5 + 100)) idleAni = 0;
				if (idleAni > 0 * aniSpeed) {
					sprite = RivalAnimation.rivalIdleLeft[0];
				}
				for (int i = 0; i < 5; i++) {
					if (idleAni > (i * spd + 100) * aniSpeed) {
						sprite = RivalAnimation.rivalIdleLeft[i + 1];
					}
				}
			}

			if (stopping) {
				int spd = 2;
				for (int i = 0; i < 13; i++) {
					if (idleAni > i * spd * aniSpeed) {
						sprite = RivalAnimation.rivalStopLeft[i];
					}
				}
				if (stopAni > spd * aniSpeed * 13) {
					stopping = false;
				}
			}

			if (attacking) {
				int spd = 4;
				for (int i = 0; i < 4; i++) {
					if (atkAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalAttackLeft[i];
					}
				}
				if (atkAni > spd * 4 * aniSpeed) {
					attacking = false;
					atkAni = 0;
				}
			}

			if (gettingup) {
				int spd = 5;
				for (int i = 12; i < 17; i++) {
					if (guAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalCrouchLeft[i];
					}
				}
				if (guAni > spd * 17 * aniSpeed) {
					gettingup = false;
				}
			} else guAni = 12 * 5 * aniSpeed;

			if (moving && !turning && !stopping && !dodging) {
				int spd = 3;
				sprite = RivalAnimation.rivalMoveLeft[0];
				if (moveAni > aniSpeed * spd * 24) moveAni = spd * 16 * aniSpeed;
				for (int i = 0; i < 24; i++) {
					if (moveAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalMoveLeft[i];
					}
				}
			}

			if (turning) {
				int spd = 2;
				for (int i = 0; i < 10; i++) {
					if (turnAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalMoveLeft[i + 24];
					}
				}
				if (turnAni > spd * 10 * aniSpeed) {
					turning = false;
				}
			}

			if (jumping) {
				int spd = 4;
				for (int i = 0; i < 8; i++) {
					if (jumpAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalJumpLeft[i];
					}
				}
				if (jumpAni > spd * 8 * aniSpeed) {
					jumping = false;
				}
			} else jumpAni = 0;

			if (swimUp) {
				int spd = 4;
				for (int i = 0; i < 8; i++) {
					if (swimAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalJumpLeft[i];
					}
				}
			} else swimAni = 0;

			if (falling && !jumping) {
				int spd = 3;
				for (int i = 0; i < 9; i++) {
					if (fallAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalFallLeft[i];
					}
				}
			} else fallAni = 0;

			if (landing) {
				int spd = 4;
				for (int i = 0; i < 5; i++) {
					if (landAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalLandLeft[i];
					}
				}
				if (landAni > spd * 5 * aniSpeed) {
					landing = false;
				}
			}
			;
			if (crouching) {
				int spd = 3;
				sprite = RivalAnimation.rivalCrouchLeft[0];
				for (int i = 0; i < 12; i++) {
					if (crouchAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalCrouchLeft[i];
					}
				}
			} else crouchAni = 0;
			if (Cattacking) {
				int spd = 3;
				for (int i = 0; i < 4; i++) {
					if (catkAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalCattackLeft[i];
					}
				}
				if (catkAni > spd * 4 * aniSpeed) {
					Cattacking = false;
					catkAni = 0;
				}
			}

			if (dodging && !flying) {
				int spd = 3;
				for (int i = 0; i < 11; i++) {
					if (dodgeAni > spd * i) {
						sprite = RivalAnimation.rivalDodgeLeft[i];
					}
				}
				if (dodgeAni > spd * 12) {
					dodgeAni = 0;
				}
			}

			if (swAtking) {
				int spd = 3;
				for (int i = 0; i < 9; i++) {
					if (swAtk > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalSwordAtkLeft[i];
					}
				}
				if (swAtk > spd * 9 * aniSpeed) {
					swAtking = false;
					swAtk = 0;
				}
			}

		} else if (dir == 1) {
			if (idle) {
				int spd = 10;
				if (idleAni > aniSpeed * (spd * 5 + 100)) idleAni = 0;
				if (idleAni >= 0 * aniSpeed) {
					sprite = RivalAnimation.rivalIdleRight[0];
				}
				for (int i = 0; i < 5; i++) {
					if (idleAni > (i * spd + 100) * aniSpeed) {
						sprite = RivalAnimation.rivalIdleRight[i + 1];
					}
				}
			}

			if (stopping) {
				int spd = 2;
				for (int i = 0; i < 13; i++) {
					if (idleAni > i * spd * aniSpeed) {
						sprite = RivalAnimation.rivalStopRight[i];
					}
				}
				if (stopAni > spd * aniSpeed * 13) {
					stopping = false;
				}
			}

			if (attacking) {
				int spd = 4;
				for (int i = 0; i < 4; i++) {
					if (atkAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalAttackRight[i];
					}
				}
				if (atkAni > spd * 4 * aniSpeed) {
					attacking = false;
					atkAni = 0;
				}
			}

			if (gettingup) {
				int spd = 5;
				for (int i = 12; i < 17; i++) {
					if (guAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalCrouchRight[i];
					}
				}
				if (guAni > spd * 17 * aniSpeed) {
					gettingup = false;
				}
			}

			if (moving && !turning && !stopping && !dodging) {
				int spd = 3;
				sprite = RivalAnimation.rivalMoveRight[0];
				if (moveAni > aniSpeed * spd * 24) moveAni = spd * 16 * aniSpeed;
				for (int i = 0; i < 24; i++) {
					if (moveAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalMoveRight[i];
					}
				}
			}

			if (turning) {
				int spd = 2;
				for (int i = 0; i < 10; i++) {
					if (turnAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalMoveRight[i + 24];
					}
				}
				if (turnAni > spd * 10 * aniSpeed) {
					turning = false;
				}
			}

			if (jumping) {
				int spd = 4;
				for (int i = 0; i < 8; i++) {
					if (jumpAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalJumpRight[i];
					}
				}
				if (jumpAni > spd * 8 * aniSpeed) {
					jumping = false;
				}
			}

			if (swimUp) {
				int spd = 4;
				for (int i = 0; i < 8; i++) {
					if (swimAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalJumpRight[i];
					}
				}
			} else swimAni = 0;

			if (falling && !jumping) {
				int spd = 3;
				for (int i = 0; i < 9; i++) {
					if (fallAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalFallRight[i];
					}
				}
			}

			if (landing) {
				int spd = 4;
				for (int i = 0; i < 5; i++) {
					if (landAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalLandRight[i];
					}
				}
				if (landAni > spd * 5 * aniSpeed) {
					landing = false;
				}
			}

			if (crouching) {
				int spd = 3;
				sprite = RivalAnimation.rivalCrouchRight[0];
				for (int i = 0; i < 12; i++) {
					if (crouchAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalCrouchRight[i];
					}
				}
			}

			if (Cattacking) {
				int spd = 3;
				for (int i = 0; i < 4; i++) {
					if (catkAni > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalCattackRight[i];
					}
				}
				if (catkAni > spd * 4 * aniSpeed) {
					Cattacking = false;
					catkAni = 0;
				}
			}

			if (dodging && !flying) {
				int spd = 3;
				for (int i = 0; i < 11; i++) {
					if (dodgeAni > spd * i) {
						sprite = RivalAnimation.rivalDodgeRight[i];
					}
				}
				if (dodgeAni > spd * 11) {
					dodgeAni = 0;
				}
			}
			if (swAtking) {
				int spd = 3;
				for (int i = 0; i < 9; i++) {
					if (swAtk > spd * i * aniSpeed) {
						sprite = RivalAnimation.rivalSwordAtkRight[i];
					}
				}
				if (swAtk > spd * 9 * aniSpeed) {
					swAtking = false;
					swAtk = 0;
				}
			}
		}
	}

	public boolean collision(int nx, int ny) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((nx) + 21 + (c % 2 * 22));
			double yt = ((ny) + 14 + (c / 2 * 48));
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			// Insert checks here (this checks all 4 corners of the sprite,
			// refine to change hit box)
			if (level.getTile(ix / 32, iy / 32).solid()) solid = true;
			if ((c / 2) == 1) {
				if (level.getTile(ix / 32, iy / 32 - 1).solid()) solid = true;
			}
		}
		climbing = false;
		return solid;
	}

	public void remove() {
		removed = true;
		dir = 0;
		if (difficulty == 1) {
			gameover = true;
		}
	}

	public void render(Screen screen) {
		int ny = 0;
		hpb.render(screen);
		if (flyAni >= 12) ny = -44;
		if (flying && dodging) ny = 0;
		if (!flicker) {
			if (glow) {
				for (int i = 1; i + 1 < sprite.pixels.length; i++) {
					if (sprite.pixels[i + 1] != 0xffff00ff || sprite.pixels[i] != 0xffff00ff) {
						int px = i % sprite.getWidth();
						int py = i / sprite.getWidth();
						screen.renderSprite(px + x, py + y - 1 + ny, Sprite.particle_blood, true);
					}
					if (i > sprite.pixels.length * 2) {
						if (sprite.pixels[i - sprite.pixels.length] != 0xffff00ff || sprite.pixels[i - sprite.pixels.length] != 0xffff00ff) {
							int px = i % sprite.getWidth();
							int py = i / sprite.getWidth();
							screen.renderSprite(px + x, py + y + ny, Sprite.particle_blood, true);
						}
					}
				}
			}
			screen.renderSprite(x, y + ny, sprite, true);
		} else {
			for (int i = 0; i < sprite.pixels.length; i++) {
				if (sprite.pixels[i] != 0xffff00ff) {
					int px = i % sprite.getWidth();
					int py = i / sprite.getWidth();
					screen.renderSprite(px + x, py + y + ny, Sprite.particle_normal, true);
				}
			}
		}
		if (archery) {
			screen.renderSprite(chx - 9, chy - 9, Sprite.Reticle, true);
		}
		for (int i = 0; i < arrows; i++) {
			screen.renderSprite(180 + (i * 17), 62, Sprite.Arrow, false);
		}
	}

	public void forceRight() {
		forceRight = true;

	}

	public void stopForceRight() {
		forceRight = false;
	}

}
