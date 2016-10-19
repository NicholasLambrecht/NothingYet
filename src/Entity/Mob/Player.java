package Entity.Mob;

import java.util.Random;

import Entity.Particle.ChillParticle;
import Entity.Particle.HealParticle;
import Entity.Projectile.Arrow;
import Entity.Projectile.CircleOfFire;
import Entity.Projectile.Fireball;
import Entity.Projectile.PillarOfFire;
import Entity.Projectile.Projectile;
import Entity.Projectile.Spike;
import Entity.Projectile.SwordAttack;
import Entity.Spawner.SpecParticleSpawner;
import Entity.Spawner.WaterParticleSpawner;
import Game.Screen;
import Graphics.Animation;
import Graphics.Font;
import Graphics.Sprite;
import Input.Keyboard;
import Level.Tile;
import Sounds.SoundEffect;
import UI.BattleText;
import UI.PlayerHealthBar;

public class Player extends Mob {
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
	public boolean incinerationTalent = false;
	public boolean arcanefocusTalent = false;
	public boolean manabladeTalent = false;
	public boolean purgingfireTalent = false;
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
	private int archeryAni = 0;
	private boolean swAtking;
	private Sprite sprite = Sprite.voidSprite;
	private int invulnFrame = 40;
	private int invulnFrames = 40;
	private int dodgeFrame = 40;
	private int dodgeFrames = 40;
	private boolean flicker = false;
	private int timer = 0;
	public boolean returning = false;
	private boolean casting = false;
	private int castAni = 0;
	private boolean shield = false;
	private boolean flag = true;
	private int shieldTimer = 0;
	private int manaTimer = 0;
	public boolean flying = false;
	private int spTimer = 0;
	private int flyAni = 0;
	private boolean flflag = true;
	private int hpRegenTimer = 0;
	public boolean flyingUnlock = false;
	private int dodgeAni = 0;
	private int expLevel = 1;
	private int exp = 0;
	private int expNext = 500;
	private PlayerHealthBar HPB;
	private int maxMp;
	private int maxHp;
	private int maxSp;
	public int difficulty;
	public boolean gameover = false;
	private boolean healthRegen = false;
	private int regenTimer = 0;
	private boolean swimUp = false;
	private int wetWings = 300;
	private boolean forceRight = false;
	private boolean climbing = false;
	int spRegen = 10;
	private boolean godMode = false;
	private boolean glow = false;
	private boolean chilled = true;
	int chillTimer = 0;
	public boolean archery = false;
	public int chx;
	public int chy;
	public int arrows = 4;
	private int maxArrows = 4;
	private int arrowRegen = 0;
	private int archeryFocus = 0;
	public int talentPoints = 0;
	private double maxMove = 6.5;
	public boolean speedTalent = false;
	public boolean rangeTalent = false;
	public boolean vorpalbladeTalent = false;
	private boolean timeStop = false;
	public boolean worldMap = false;
	private Keyboard key;

	private boolean INPUT_LEFT, INPUT_RIGHT, INPUT_UP, INPUT_DOWN, INPUT_FIREBALL, INPUT_SWORDATTACK, INPUT_PILLAR, INPUT_SHIELD, INPUT_EVADELEFT, INPUT_EVADERIGHT;
	private boolean INPUT_FLY, INPUT_ARCHERY, INPUT_SWORDATTACKLEFT, INPUT_SWORDATTACKRIGHT, INPUT_UPr, INPUT_DOWNr;
	private boolean INPUT_PAUSE;

	public Player(Keyboard key) {
		this.key = key;
		xSize = 64;
		ySize = 64;
		sprite = Sprite.voidSprite;
		setHp(100);
		setMp(100);
		setSp(100);
	}

	public Player(int x, int y, int dif, Keyboard key) {
		this.key = key;
		this.x = x;
		this.y = y;
		aniFrame = 0;
		new Animation();
		setHp(100);
		setMp(100);
		setSp(25);
		maxHp = 100;
		maxSp = 25;
		maxMp = 100;
		difficulty = dif;
		if (dif == 1) {
			maxMp = 25;
			// spRegen = 20;
		}

		setHp(maxHp);
		HPB = new PlayerHealthBar(100, 10, getHp(), maxMp, getSp(), expNext);
	}

	private boolean inWater() {
		boolean water = false;
		if (level.getTile(x / 32, y / 32) == Tile.sWater) {
			water = true;
		} else if (level.getTile((x / 32) + 1, y / 32) == Tile.sWater) {
			water = true;
		} else if (level.getTile((x / 32) + 1, (y / 32) + 2) == Tile.sWater) {
			if (falling || swimUp) {
				level.add(new WaterParticleSpawner(x + 24, y + 32, 10, 5, level));
			}
			water = true;
		} else if (level.getTile((x / 32), (y / 32) + 2) == Tile.sWater) {
			water = true;
		}
		return water;
	}

	private void updateControllers() {
		INPUT_UP = false;
		INPUT_DOWN = false;
		INPUT_RIGHT = false;
		INPUT_LEFT = false;
		INPUT_FIREBALL = false;
		INPUT_SWORDATTACK = false;
		INPUT_PILLAR = false;
		INPUT_SHIELD = false;
		INPUT_EVADELEFT = false;
		INPUT_EVADERIGHT = false;
		INPUT_FLY = false;
		INPUT_ARCHERY = false;
		INPUT_SWORDATTACKLEFT = false;
		INPUT_SWORDATTACKRIGHT = false;
		INPUT_PAUSE = false;
		INPUT_UPr = false;
		INPUT_DOWNr = false;

		if (key.up) INPUT_UP = true;
		if (key.down) INPUT_DOWN = true;
		if (key.left) INPUT_LEFT = true;
		if (key.right) INPUT_RIGHT = true;
		if (key.j) INPUT_FIREBALL = true;
		if (key.k) INPUT_PILLAR = true;
		if (key.l) INPUT_SHIELD = true;
		if (key.f) INPUT_SWORDATTACK = true;
		if (key.c) INPUT_FLY = true;
		if (key.v) INPUT_ARCHERY = true;
		if (key.r) INPUT_PAUSE = true;
		if (key.e) INPUT_EVADERIGHT = true;
		if (key.q) INPUT_EVADELEFT = true;

	}

	public void update() {
		updateControllers();
		if (!timeStop) arrowRegen++;
		if (arrowRegen > 1200) {
			arrows++;
			arrowRegen = 0;
		}
		if (arrows > maxArrows) {
			arrows = maxArrows;
		}
		if (sp < 0) sp = 0;

		if (getHp() > maxHp) setHp(maxHp);
		if (chillTimer > 0) {
			if (!timeStop) chillTimer--;
			chilled = true;
			if (chillTimer % 10 == 0) level.add(new ChillParticle(x, y, 30));
		} else {
			chilled = false;
			chillTimer = 0;
		}
		if (godMode) {
			setHp(maxHp);
			sp = maxSp;
			mp = maxMp;
			maxArrows = 50;
			arrowRegen += 12;
		}
		if (!removed && !freeze || forceRight) {
			if (INPUT_ARCHERY && !moving && arrows > 0 && !flying) {
				if (arcanefocusTalent) {
					level.freeze();
					archeryFocus++;
					if (archeryFocus > 10) {
						archeryFocus = 0;
						sp--;
					}
				}
				archery = true;
				int chs = 4;
				if (INPUT_LEFT || INPUT_SWORDATTACKLEFT) chx -= chs;
				if (INPUT_RIGHT || INPUT_SWORDATTACKRIGHT) chx += chs;
				if (INPUT_UP || INPUT_UPr) chy -= chs;
				if (INPUT_DOWN || INPUT_DOWNr) chy += chs;

				if (sp <= 0) level.unfreeze();

			} else if (archery) {
				SoundEffect.ARROWFIRE.play();
				arrows--;
				shootArrow();
				archery = false;
				archeryAni = 0;
			} else {
				level.unfreeze();
				chx = x + 32;
				if (dir == 1) chx = x + 96;
				else chx = x - 32;
				chy = y + 32;
			}
			if (!archery) {
				if (!flying) {
					checkActions();
					incrementValues();
				} else {
					checkFlyingAction();
				}
				checkCombatActions();
				checkUpdatedActions();
			}
			gravity();
			ax += aAx;
			ay += aAy;
			movementChecks();
			collisionCheck();
		} else if (removed) {
			returning();
		}

		updateFrames();
		setSprite();

		if (healthRegen) {
			healthRegen();
			glow = true;
		} else glow = false;
		// regenTimer = 180*25;
		if (regenTimer <= 0) {
			regenTimer = 0;
			healthRegen = false;
		} else {
			regenTimer--;
			healthRegen = true;
		}

		HPB.update(getHp(), getMp(), getSp(), exp);
		HPB.updateMaX(maxHp, maxMp, maxSp, expNext);

		if (invulnFrame < invulnFrames && !isDodging()) {
			if (flicker) flicker = false;
			else flicker = true;
		} else flicker = false;
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

	private void returning() {
		flying = false;
		if (returning) {
			crouching = false;
		} else {
			if (!collision(x, y)) {
				y += 1;
			}
			crouching = true;
		}
		timer++;
		if (timer > 300) {
			timer = 300;
			if (INPUT_PAUSE) {
				setHp(maxHp);
				returning = true;
				removed = false;
				invulnFrame = 0;
				timer = 0;
				level.reset();
				level.add(new SpecParticleSpawner((int) x + 32, (int) y + 60, 520, 7500, level));
			}
		} else if (invulnFrame == invulnFrames) invulnFrame = 0;
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
		if (landing && !isDodging()) ax = 0;
		if (ay <= 0) falling = false;
		else if (!attacking) falling = true;
		if (chilled) maxMove = 1.0;
		else if (speedTalent) maxMove = 8.5;
		else maxMove = 6.5;
		if (ax > maxMove && !isDodging()) ax = maxMove;
		if (ax < -maxMove && !isDodging()) ax = -maxMove;
		if (!INPUT_RIGHT && !INPUT_LEFT && !isDodging() && !forceRight) {
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
		if (turning && !isDodging() && !INPUT_LEFT && !INPUT_RIGHT) {
			ax = ax / 1.1;
			if (ax > 0 && !isDodging() && !INPUT_LEFT && !INPUT_RIGHT) {
				ax = ax / 10;
				ax = (int) Math.ceil(ax);
			} else if (ax < 0 && !isDodging() && !INPUT_LEFT && !INPUT_RIGHT) {
				ax = ax / 10;
				ax = (int) Math.floor(ax);
			}
		}
	}

	private void checkUpdatedActions() {
		if (crouching && !INPUT_DOWN) {
			gettingup = true;
			crouching = false;
		}
		if (INPUT_DOWN && !moving) {
			aAy += .5;
			landing = false;
			crouching = true;
			// swAtking = false;
		} else {
			crouching = false;
		}

		if (isDodging()) {
			aAx = aAx / 2;
		}

		if (!moving && !crouching && !attacking && !turning && !flying) {
			aniFrame = 0;
			idle = true;
		} else idle = false;
	}

	private void gravity() {
		boolean water = inWater();
		if (water) wetWings = 0;
		wetWings++;
		if (wetWings > 180) {
			wetWings = 180;
		} else if (wetWings <= 180) {
			sp--;
			if (sp <= 0) sp = 0;
			if (idle) {
				level.add(new WaterParticleSpawner(x + 32, y + 26, 12, 1, level));
			}
		}
		if (water && !collision(x, y + 2) && !jumping) {
			aAy += .2;
		} else if (water && collision(x, y + 2) && aAy > 0) {
			landing = true;
			aAy = 0;
		}
		if (!collision(x, y + 2) && !flying && !water && !climbing) {
			aAy += 1;
		} else if (!jumping && aAy > 0 && !flying && !water && !climbing) {
			landing = true;
			aAy = 0;
		}
	}

	private void checkCombatActions() {
		if (INPUT_SHIELD && flag) {
			shield();
		} else if (!INPUT_SHIELD) flag = true;

		if (INPUT_FLY && flflag && !isDodging() && flyingUnlock) {
			fly();
		} else if (!INPUT_FLY) flflag = true;

		if ((!moving || flying) && !crouching && INPUT_FIREBALL && !jumping && !attacking && mp >= 5) {
			fireball();
		} else if (INPUT_PILLAR && !moving && !crouching && !attacking && !casting && mp >= 10) {
			pillars_of_fire();
			mp -= 10;
			casting = true;
		} else if (crouching && INPUT_FIREBALL && !Cattacking && mp >= 5) {
			crouchFireball();
		}

		if (shield) {
			shieldTimer++;
			level.add(new CircleOfFire(x + 32, y + 32, level));
			if (shieldTimer > 20) {
				mp--;
				shieldTimer = 0;
			}
			if (mp <= 0) {
				shield = false;
				flag = false;
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
		if (incinerationTalent) mp += 1;
		SoundEffect.FIREBALL.play();
	}

	public void manabladeFireball() {
		if (dir == 0) shoot(x + 16, y + 18, dir);
		else shoot(x + 48, y + 18, dir);
		SoundEffect.FIREBALL.play();
	}

	private void fly() {
		if (flying) {
			flying = false;
			falling = true;
			// jumping = true;
			aAy += 1;
			ay = 0;
		} else if (sp > 10) {
			aAx = 0;
			aAy = 0;
			ax = 0;
			ay = 0;
			flying = true;
		}
		flflag = false;
	}

	private void shield() {
		if (shield) {
			SoundEffect.SHIELDEND.play();
			shield = false;
		} else if (mp > 10) {
			SoundEffect.SHIELDBGN.play();
			shield = true;
		}
		flag = false;
	}

	private void incrementValuesFly() {
		spTimer++;
		if (spTimer >= 3) {
			spTimer = 0;
			sp--;
		}
		if (sp <= 0) flying = false;
		falling = true;
	}

	private void checkFlyingAction() {
		aAy = 0;
		if (INPUT_EVADERIGHT) {
			setDodging(true);
			ax = +10;
			dir = 1;
			dodgeFrame = 0;
		}
		if (INPUT_EVADELEFT) {
			setDodging(true);
			ax = -10;
			dir = 0;
			dodgeFrame = 0;
		}
		if (isDodging() && dodgeFrame == 40) setDodging(false);
		if (INPUT_RIGHT && !isDodging() && !swAtking || forceRight) {
			ax += 1.5;
			dir = 1;
		}
		if (INPUT_LEFT && !isDodging() && !swAtking && !forceRight) {
			ax -= 1.5;
			dir = 0;
		}
		if (INPUT_UP) {
			if (!chilled) {
				ay -= 6.5;
			} else ay -= 1.0;
		}
		if (INPUT_DOWN) {
			if (!chilled) {
				ay += 6.5;
			} else ay += 1.0;
		}
		if (aAx != 0 || ay != 0) {
			moving = true;
		} else moving = false;
		incrementValuesFly();
	}

	private void checkActions() {
		if (moving && !INPUT_LEFT && !INPUT_RIGHT && !isDodging() && !forceRight) {
			stopping = true;
		}

		if (jumping) {
			turning = false;
		}
		if (!attacking && !crouching) {
			if (INPUT_EVADELEFT && !turning && !isDodging() && !attacking && !casting && sp > 10) {
				setDodging(true);
				aAx = -10;
				sp -= 10;
				swAtking = false;
				dir = 0;
				dodgeFrame = 0;
			}
			if (INPUT_EVADERIGHT && !turning && !isDodging() && !attacking && !casting && sp > 10) {
				setDodging(true);
				aAx = 10;
				sp -= 10;
				swAtking = false;
				dir = 1;
				dodgeFrame = 0;
			}
			if (INPUT_SWORDATTACK && !isDodging() && !swAtking && !moving) {
				swAtking = true;
				swordAttack();
			}
			if (INPUT_SWORDATTACKRIGHT && !isDodging() && !swAtking && !moving) {
				swAtking = true;
				dir = 1;
				swordAttack();
			}
			if (INPUT_SWORDATTACKLEFT && !isDodging() && !swAtking && !moving) {
				swAtking = true;
				dir = 0;
				swordAttack();
			}
			if (isDodging() && dodgeFrame == 40) setDodging(false);
			if (INPUT_UP && !falling && !jumping && !casting && !swAtking && !inWater() && !attacking) {
				landing = false;
				jumping = true;
				aAy = -20;
			} else if (!INPUT_UP && jumping && !inWater()) {
				aAy *= .2;
				jumping = false;
			}
			if (INPUT_UP && !casting && !swAtking && inWater()) {
				aAy = -3.5;
				jumping = false;
				swimUp = true;
			} else swimUp = false;
			if (INPUT_RIGHT && !isDodging() && !casting && !swAtking || forceRight) {
				landing = false;
				if (dir == 0) {
					turning = true;
					aAx = 0;
				}
				aAx += 1.06;
				if ((!turning) || jumping) {
					aAx += .09;
				}
				dir = 1;
				stopping = false;
			}
			if (INPUT_LEFT && !isDodging() && !casting && !swAtking && !forceRight) {
				landing = false;
				if (dir == 1) {
					turning = true;
					aAx = 0;
				}
				aAx += -1.06;
				if ((!turning) || jumping) {
					aAx += -.09;
				}
				dir = 0;
				stopping = false;
			}
			if (aAx != 0) {
				moving = true;
			} else moving = false;

		}
	}

	private void swordAttack() {
		level.add(new SwordAttack(x, y, level, dir));
		Random rand = new Random();
		int r = rand.nextInt(3);
		if (r == 0) SoundEffect.SWORDSWING1.play();
		else if (r == 1) SoundEffect.SWORDSWING2.play();
		else SoundEffect.SWORDSWING3.play();
	}

	private void incrementValues() {
		spTimer++;
		if (spTimer >= spRegen) {
			spTimer = 0;
			sp++;
		}

		if (getHp() < maxHp / 4) {
			healthRegen();
			glow = true;
		} else glow = false;

		manaTimer++;
		if (!casting && !shield && manaTimer >= 7) {
			manaTimer = 0;
			mp++;
		}

		if (mp >= maxMp) {
			mp = maxMp;
		}
		if (sp >= maxSp) {
			sp = maxSp;
		}
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
		if (incinerationTalent) p.damage = (int) (p.damage * 1.25);
		level.add(p);
	}

	public void takeDamage(int damage) {
		if (damage < 0) {
			setHp(getHp() - damage);
		} else if (invulnFrame >= 40 && !isDodging()) {
			if (shield) damage = damage / 4;
			setHp(getHp() - damage);
			damaged = true;
			if (!removed) invulnFrame = 0;
			if (getHp() <= 0) {
				remove();
			}
	//		if (false) level.add(new PillarOfFire(x + 12, y + 64, level));
		}
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

		if (isDodging()) {
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

	public boolean particleCollision(int nx, int ny) {
		boolean hit = false;
		// if (mobRight >= playerStartX && mobStartX <= playerStartX +
		// playerSizeX && playerStartY + playerSizeY >= mobStartY &&
		// playerStartY <= mobBottom && invulnFrame == invulnFrames) {
		if (nx >= x + 16 && nx <= x + 48 && ny >= y && ny <= y + 64) {
			hit = true;
		}
		return hit;
	}

	public boolean projectileCollision(Projectile p) {
		boolean hit = false;
		int xx = (int) (p.x + p.sprite.getWidth() / 2);
		int yy = (int) (p.y + p.sprite.getHeight() / 2);

		if (xx >= x + 12 && xx <= x + 48 && yy >= y + 12 && yy <= y + 64) {
			hit = true;
			if (p instanceof Spike) chillTimer = 300;
		}
		if (crouching) {
			hit = false;
			if (xx >= x + 12 && xx <= x + 48 && yy >= y + 33 && yy <= y + 64) {
				hit = true;
			}
		}

		return hit;
	}

	public void hit(Projectile projectile) {
		takeDamage(projectile.damage);
		knockback(projectile.knockback, -dir);
		projectile.hit();
	}

	private void setSprite() {
		sprite = Animation.playerIdleLeft[0];

		if (dir == 0) {
			if (idle) {
				int spd = 10;
				if (idleAni > aniSpeed * (spd * 5 + 100)) idleAni = 0;
				if (idleAni > 0 * aniSpeed) {
					sprite = Animation.playerIdleLeft[0];
				}
				for (int i = 0; i < 5; i++) {
					if (idleAni > (i * spd + 100) * aniSpeed) {
						sprite = Animation.playerIdleLeft[i + 1];
					}
				}
			}

			if (stopping) {
				int spd = 2;
				for (int i = 0; i < 13; i++) {
					if (idleAni > i * spd * aniSpeed) {
						sprite = Animation.playerStopLeft[i];
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
						sprite = Animation.playerAttackLeft[i];
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
						sprite = Animation.playerCrouchLeft[i];
					}
				}
				if (guAni > spd * 17 * aniSpeed) {
					gettingup = false;
				}
			} else guAni = 12 * 5 * aniSpeed;

			if (moving && !turning && !stopping && !isDodging()) {
				int spd = 3;
				sprite = Animation.playerMoveLeft[0];
				if (moveAni > aniSpeed * spd * 24) moveAni = spd * 16 * aniSpeed;
				for (int i = 0; i < 24; i++) {
					if (moveAni > spd * i * aniSpeed) {
						sprite = Animation.playerMoveLeft[i];
					}
				}
			}

			if (turning) {
				int spd = 2;
				for (int i = 0; i < 10; i++) {
					if (turnAni > spd * i * aniSpeed) {
						sprite = Animation.playerMoveLeft[i + 24];
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
						sprite = Animation.playerJumpLeft[i];
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
						sprite = Animation.playerJumpLeft[i];
					}
				}
			} else swimAni = 0;

			if (falling && !jumping) {
				int spd = 3;
				for (int i = 0; i < 9; i++) {
					if (fallAni > spd * i * aniSpeed) {
						sprite = Animation.playerFallLeft[i];
					}
				}
			} else fallAni = 0;

			if (landing) {
				int spd = 4;
				for (int i = 0; i < 5; i++) {
					if (landAni > spd * i * aniSpeed) {
						sprite = Animation.playerLandLeft[i];
					}
				}
				if (landAni > spd * 5 * aniSpeed) {
					landing = false;
				}
			}
			;
			if (crouching) {
				int spd = 3;
				sprite = Animation.playerCrouchLeft[0];
				for (int i = 0; i < 12; i++) {
					if (crouchAni > spd * i * aniSpeed) {
						sprite = Animation.playerCrouchLeft[i];
					}
				}
			} else crouchAni = 0;
			if (Cattacking) {
				int spd = 3;
				for (int i = 0; i < 4; i++) {
					if (catkAni > spd * i * aniSpeed) {
						sprite = Animation.playerCattackLeft[i];
					}
				}
				if (catkAni > spd * 4 * aniSpeed) {
					Cattacking = false;
					catkAni = 0;
				}
			}

			if (flying && !isDodging()) {
				int spd = 4;
				if (flyAni < spd * 3) {
					sprite = Sprite.playerLeftFly1;
				} else if (flyAni <= spd * 6) sprite = Sprite.playerLeftFly2;
				else {
					sprite = Sprite.playerLeftFly1;
					flyAni = 0;
				}
			}
			if (isDodging() && !flying) {
				int spd = 3;
				for (int i = 0; i < 11; i++) {
					if (dodgeAni > spd * i) {
						sprite = Animation.playerDodgeLeft[i];
					}
				}
				if (dodgeAni > spd * 12) {
					dodgeAni = 0;
				}
			} else if (isDodging() && flying) {
				int spd = dodgeFrames / 3;
				if (dodgeAni > spd * 0) {
					sprite = Sprite.playerFlyDogeLeft1;
				}
				if (dodgeAni > spd * 1) {
					sprite = Sprite.playerFlyDogeLeft2;
				}
				if (dodgeAni > spd * 2) {
					sprite = Sprite.playerFlyDogeLeft3;
				}
				if (dodgeAni >= spd * 3) {
					dodgeAni = 0;
				}
			}

			if (swAtking) {
				int spd = 3;
				for (int i = 0; i < 9; i++) {
					if (swAtk > spd * i * aniSpeed) {
						sprite = Animation.playerSwordAtkLeft[i];
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
					sprite = Animation.playerIdleRight[0];
				}
				for (int i = 0; i < 5; i++) {
					if (idleAni > (i * spd + 100) * aniSpeed) {
						sprite = Animation.playerIdleRight[i + 1];
					}
				}
			}

			if (stopping) {
				int spd = 2;
				for (int i = 0; i < 13; i++) {
					if (idleAni > i * spd * aniSpeed) {
						sprite = Animation.playerStopRight[i];
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
						sprite = Animation.playerAttackRight[i];
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
						sprite = Animation.playerCrouchRight[i];
					}
				}
				if (guAni > spd * 17 * aniSpeed) {
					gettingup = false;
				}
			}

			if (moving && !turning && !stopping && !isDodging()) {
				int spd = 3;
				sprite = Animation.playerMoveRight[0];
				if (moveAni > aniSpeed * spd * 24) moveAni = spd * 16 * aniSpeed;
				for (int i = 0; i < 24; i++) {
					if (moveAni > spd * i * aniSpeed) {
						sprite = Animation.playerMoveRight[i];
					}
				}
			}

			if (turning) {
				int spd = 2;
				for (int i = 0; i < 10; i++) {
					if (turnAni > spd * i * aniSpeed) {
						sprite = Animation.playerMoveRight[i + 24];
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
						sprite = Animation.playerJumpRight[i];
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
						sprite = Animation.playerJumpRight[i];
					}
				}
			} else swimAni = 0;

			if (falling && !jumping) {
				int spd = 3;
				for (int i = 0; i < 9; i++) {
					if (fallAni > spd * i * aniSpeed) {
						sprite = Animation.playerFallRight[i];
					}
				}
			}

			if (landing) {
				int spd = 4;
				for (int i = 0; i < 5; i++) {
					if (landAni > spd * i * aniSpeed) {
						sprite = Animation.playerLandRight[i];
					}
				}
				if (landAni > spd * 5 * aniSpeed) {
					landing = false;
				}
			}

			if (crouching) {
				int spd = 3;
				sprite = Animation.playerCrouchRight[0];
				for (int i = 0; i < 12; i++) {
					if (crouchAni > spd * i * aniSpeed) {
						sprite = Animation.playerCrouchRight[i];
					}
				}
			}

			if (Cattacking) {
				int spd = 3;
				for (int i = 0; i < 4; i++) {
					if (catkAni > spd * i * aniSpeed) {
						sprite = Animation.playerCattackRight[i];
					}
				}
				if (catkAni > spd * 4 * aniSpeed) {
					Cattacking = false;
					catkAni = 0;
				}
			}

			if (flying && !isDodging()) {
				int spd = 4;
				if (flyAni < spd * 3) {
					sprite = Sprite.playerFly1;
				} else if (flyAni <= spd * 6) sprite = Sprite.playerFly2;
				else {
					sprite = Sprite.playerFly1;
					flyAni = 0;
				}
			}

			if (isDodging() && !flying) {
				int spd = 3;
				for (int i = 0; i < 11; i++) {
					if (dodgeAni > spd * i) {
						sprite = Animation.playerDodgeRight[i];
					}
				}
				if (dodgeAni > spd * 11) {
					dodgeAni = 0;
				}
			} else if (isDodging() && flying) {
				int spd = dodgeFrames / 3;
				if (dodgeAni > spd * 0) {
					sprite = Sprite.playerFlyDodge1;
				}
				if (dodgeAni > spd * 1) {
					sprite = Sprite.playerFlyDodge2;
				}
				if (dodgeAni > spd * 2) {
					sprite = Sprite.playerFlyDodge3;
				}
				if (dodgeAni >= spd * 3) {
					dodgeAni = 0;
				}
			}
			if (swAtking) {
				int spd = 3;
				for (int i = 0; i < 9; i++) {
					if (swAtk > spd * i * aniSpeed) {
						sprite = Animation.playerSwordAtkRight[i];
					}
				}
				if (swAtk > spd * 9 * aniSpeed) {
					swAtking = false;
					swAtk = 0;
				}
			}
		}
		if (archery) {
			int spd = 2;
			int xClicked = chx;
			int yClicked = chy;
			int xOrigin = x + 32;
			int yOrigin = y + 32;
			double dx = xClicked - xOrigin;
			double dy = yClicked - yOrigin;
			double direction = Math.atan2(dy, dx);
			archeryAni++;
			if (archeryAni < 4 * spd && Math.abs(direction) < Math.PI / 2) {
				sprite = Sprite.playerShooting1;
			} else if (archeryAni < 4 * spd && Math.abs(direction) >= Math.PI / 2) {
				sprite = Sprite.playerShooting1l;
			} else if (archeryAni < 8 * spd && Math.abs(direction) < Math.PI / 2) {
				sprite = Sprite.playerShooting2;
			} else if (archeryAni < 8 * spd && Math.abs(direction) >= Math.PI / 2) {
				sprite = Sprite.playerShooting2l;
			} else {
				if (Math.abs(direction) <= Math.PI / 2) {
					sprite = Sprite.playerShooting3h;
					if (direction < -.25) {
						sprite = Sprite.playerShooting3u;
					}
					if (direction > .25) {
						sprite = Sprite.playerShooting3d;
					}
				} else {
					sprite = Sprite.playerShooting3hl;
					if (direction > -2.885 && direction < -Math.PI / 2) {
						sprite = Sprite.playerShooting3ul;
					}
					if (direction < 2.885 && direction > Math.PI / 2) {
						sprite = Sprite.playerShooting3dl;
					}
				}
			}
			// System.out.println(direction);
		}
		// y = 33 * 32;

	}

	public boolean mobCollision(int mobStartX, int mobStartY) {
		boolean hit = false;
		int mobSizeX = 64;
		int mobSizeY = 64;
		int playerSizeX = 32;
		int playerSizeY = 48;
		int playerStartX = 16 + x;
		int playerStartY = 16 + y;
		int mobRight = mobStartX + mobSizeX;
		int mobBottom = mobStartY + mobSizeY;
		if (mobRight >= playerStartX && mobStartX <= playerStartX + playerSizeX && playerStartY + playerSizeY >= mobStartY && playerStartY <= mobBottom && invulnFrame == invulnFrames) {
			hit = true;
			if (!removed) invulnFrame = 0;
		}

		return hit;
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
		if (flyAni >= 12) ny = -44;
		if (flying && isDodging()) ny = 0;

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
		HPB.render(screen);
		Font.renderBTSTSM("LVL " + expLevel, screen, 179, 50);
	}

	public void setMp(int mp) {

		this.mp = mp;
	}

	public void setSp(int sp) {

		this.sp = sp;
	}

	public int getSp() {
		return sp;
	}

	public int getMp() {
		return mp;
	}

	public void knockbackMST(int knockback, int dir) {

	}

	public void unlockFlying() {
		flyingUnlock = true;
		flying = true;
	}

	public int getExp() {
		return exp;
	}

	public int getEXPLevel() {
		return expLevel;
	}

	public void gainEXP(int val) {
		exp += val;

		while (exp >= expNext) {
			levelUp();
			exp = exp - expNext;
			expNext = expNext * 3 / 2;
		}
	}

	private void levelUp() {
		expLevel++;
		int hpBonus = 10;
		int mpBonus = 5;
		maxArrows++;
		maxHp = maxHp + hpBonus;
		maxMp += mpBonus;
		regenTimer = 180 * 25;
		HPB.updateMaX(maxHp, maxMp, maxSp, expNext);
		level.addText(new BattleText(x, y, "LEVEL UP!"));
		talentPoints++;
	}

	private void healthRegen() {
		if (!archery) {
			hpRegenTimer++;
			if (hpRegenTimer % 10 == 0) {
				level.add(new HealParticle(x, y, 30));
			}
			if (hpRegenTimer >= 180) {
				setHp(getHp() + 1);
				hpRegenTimer = 0;
			}
		}
	}

	public void setDif(int dif) {
		difficulty = dif;
	}

	public boolean isDodging() {
		return dodging;
	}

	public void setDodging(boolean dodging) {
		this.dodging = dodging;
	}

	public void forceRight() {
		forceRight = true;

	}

	public void stopForceRight() {
		forceRight = false;
	}

	public void freeze() {
		freeze = true;
	}

	public void unfreeze() {
		freeze = false;
	}

	public void unlockSpeedTalent() {
		maxMove = 8.5;
		if (!speedTalent) talentPoints--;
		speedTalent = true;
	}

	public void unlockRangeTalent() {
		if (!rangeTalent) talentPoints--;
		rangeTalent = true;
	}

	public void unlockIncinerationTalent() {
		if (!incinerationTalent) talentPoints--;
		incinerationTalent = true;
	}

	public void unlockManaBladeTalent() {
		if (!manabladeTalent) talentPoints--;
		manabladeTalent = true;
	}

	public void unlockArcaneFocusTalent() {
		if (!arcanefocusTalent) talentPoints--;
		arcanefocusTalent = true;
	}

	public void unlockVorpalBladeTalent() {
		if (!vorpalbladeTalent) talentPoints--;
		vorpalbladeTalent = true;
	}
	
	public void unlockPurgingFireTalent() {
		if (!purgingfireTalent) talentPoints--;
		purgingfireTalent = true;
	}

	public void gotoWorldMap() {
		worldMap = true;
	}

}
