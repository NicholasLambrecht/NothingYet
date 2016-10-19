package Entity.Mob;

import java.util.Random;

import Entity.Spawner.BloodParticleSpawner;
import Game.Screen;
import Graphics.Sprite;
import UI.DamageBattleText;
import UI.HealthBar;

public class EvilBat extends Mob {

	private int ay, ax;
	private double aAx, aAy;
	HealthBar hpb;
	private boolean stunned = false;
	private int stunTimer = 0;
	private int stunCD = 300;
	private int anim = 0;
	private int animSPD = 3;
	int invulnFrame = 0;
	int invulnFrames = 4;

	public EvilBat(int x, int y) {
		sprite = Sprite.bat1;
		this.x = x;
		this.y = y;
		maxHp = 30;
		setHp(30);
		damage = 6;
		expValue = 9;
		hpb = new HealthBar(x, y - 6, getHp());
		expLevel = 1;
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
		// x = 150;
		// y = 1175;
		invulnFrame++;
		if (invulnFrame >= invulnFrames) {
			invulnFrame = invulnFrames;
		}

		if (AI && !stunned && playerIsNear()) runAI();
		if (!playerIsNear()) {
			aAy = 0;
			aAx = 0;
			ax = 0;
			ay = 0;
			// x = 86 * 32;
			// y = 61 * 32;
		}
		if (stunned) {
			stunTimer++;
			if (stunTimer > stunCD) {
				stunned = false;
				stunTimer = 0;
			}
		}

		if (aAy > 5) {
			aAy = 5;
		}
		if (aAx > 5) {
			aAx = 5;
		}
		if (aAx < -5) {
			aAx = -5;
		}
		if (aAy < -5) {
			aAy = -5;
		}

		ax += aAx;
		ay += aAy;

		if (ax > 5) {
			ax = 5;
		}
		if (ay > 5) {
			ay = 5;
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
			if (anim < animSPD * 1) {
				sprite = Sprite.bat1;
			} else if (anim < animSPD * 2) sprite = Sprite.bat2;
			else if (anim < animSPD * 3) sprite = Sprite.bat3;
			else if (anim < animSPD * 4) sprite = Sprite.bat4;
			else if (anim < animSPD * 5) sprite = Sprite.bat5;
			else if (anim < animSPD * 6) sprite = Sprite.bat6;
			else if (anim < animSPD * 7) sprite = Sprite.bat5;
			else if (anim < animSPD * 8) sprite = Sprite.bat4;
			else if (anim < animSPD * 9) sprite = Sprite.bat3;
			else if (anim < animSPD * 10) sprite = Sprite.bat2;
		} else {
			if (anim < animSPD * 1) {
				sprite = Sprite.bat1r;
			} else if (anim < animSPD * 2) sprite = Sprite.bat2r;
			else if (anim < animSPD * 3) sprite = Sprite.bat3r;
			else if (anim < animSPD * 4) sprite = Sprite.bat4r;
			else if (anim < animSPD * 5) sprite = Sprite.bat5r;
			else if (anim < animSPD * 6) sprite = Sprite.bat6r;
			else if (anim < animSPD * 7) sprite = Sprite.bat5r;
			else if (anim < animSPD * 8) sprite = Sprite.bat4r;
			else if (anim < animSPD * 9) sprite = Sprite.bat3r;
			else if (anim < animSPD * 10) sprite = Sprite.bat2r;

		}
	}

	private void runAI() {
		Random rand = new Random();
		if (rand.nextInt(150) == 1) {
			jump();
		}

		if (level.player.x < x) {
			aAx -= .2;
			dir = 0;
		} else if (level.player.x > x) {
			aAx += .2;
			dir = 1;
		}
		if (level.player.y < y) {
			aAy -= .2;
		} else if (level.player.y > y) {
			aAy += .2;
		}
	}

	private void jump() {
		if (collision(x, y + 2)) aAy -= 15;
	}

	public boolean collision(int nx, int ny) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((nx + 26) + (c % 2 * 12));
			double yt = ((ny + 16) + (c / 2 * 32));
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

	public boolean playerCollision(int nx, int ny) {
		ny = ny + 32;
		nx = nx + 32;
		boolean hit = false;
		// if (mobRight >= playerStartX && mobStartX <= playerStartX +
		// playerSizeX && playerStartY + playerSizeY >= mobStartY &&
		// playerStartY <= mobBottom && invulnFrame == invulnFrames) {
		if (nx >= x + 26 && nx <= x + 38 && ny >= y + 16 && ny <= y + 48) {
			hit = true;
		}
		return hit;
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
