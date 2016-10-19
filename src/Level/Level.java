package Level;

import java.util.ArrayList;
import java.util.List;

import Entity.Entity;
import Entity.Decor.Pot;
import Entity.Mob.Archer;
import Entity.Mob.EvilBat;
import Entity.Mob.Mob;
import Entity.Mob.MountainGiant;
import Entity.Mob.Player;
import Entity.Mob.RhinoDemon;
import Entity.Particle.Particle;
import Entity.PowerUp.FlyingPowerUp;
import Entity.PowerUp.PowerUp;
import Entity.Projectile.Projectile;
import Game.Screen;
import Graphics.Sprite;
import Graphics.SpriteSheet;
import Sounds.Music;
import UI.BattleText;

public class Level {

	protected List<Entity> entities = new ArrayList<Entity>();
	protected List<Mob> mobs = new ArrayList<Mob>();
	protected List<Projectile> projectiles = new ArrayList<Projectile>();
	protected List<Particle> particles = new ArrayList<Particle>();
	protected List<PowerUp> powerUps = new ArrayList<PowerUp>();
	protected List<BattleText> battleText = new ArrayList<BattleText>();

	private int width;
	private int height;
	protected int[] tilesInt;
	protected int[] tiles;
	public Player player;
	public int killCount;
	public int difficulty;
	protected boolean freeze = false;
	public Sprite sprite;
	private boolean continueGame = false;

	public Level(int width, int height, int difficulty) {
		this.setWidth(width);
		this.setHeight(height);
		this.difficulty = difficulty;
		tilesInt = new int[width * height];
		generateLevel();
	}

	public Level(String path, int difficulty) {
		loadLevel(path);
		sprite = new Sprite(new SpriteSheet(path));
		this.difficulty = difficulty;
	}

	protected void generateLevel() {

	}

	protected void loadLevel(String path) {

	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void update() {
		if (!freeze) {
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).update();
			}
			for (int i = 0; i < mobs.size(); i++) {
				mobs.get(i).update();
			}
			for (int i = 0; i < projectiles.size(); i++) {
				projectiles.get(i).update();
			}

			for (int i = 0; i < powerUps.size(); i++) {
				powerUps.get(i).update();
			}

			for (int i = 0; i < projectiles.size(); i++) {
				if (projectiles.get(i).e) {
					if (player.projectileCollision(projectiles.get(i))) {
						player.hit(projectiles.get(i));
					}
				} else {
					for (int j = 0; j < mobs.size(); j++) {
						if (mobs.get(j).unitCollision((int) projectiles.get(i).x, (int) projectiles.get(i).y)) {
							mobs.get(j).hit(projectiles.get(i));
						}
					}
				}
				if (!projectiles.get(i).e) {
					for (int j = 0; j < entities.size(); j++) {
						if (entities.get(j) instanceof Pot) {
							Pot potato = (Pot) entities.get(j);
							if (potato.pCollision(projectiles.get(i).x, projectiles.get(i).y)) {
								potato.destroy();
							}
						}
					}
				}
			}
			for (int i = 0; i < mobs.size(); i++) {
				if (player.isRemoved()) mobs.get(i).killAI();
				else mobs.get(i).resumeAI();
				if (mobs.get(i).playerCollision(player.x, player.y)) {
					player.takeDamage(mobs.get(i).getDamage());
				}
			}
			for (int i = 0; i < particles.size(); i++) {
				particles.get(i).update();
			}
			for (int i = 0; i < battleText.size(); i++) {
				battleText.get(i).update();
			}
			checkExits();
			checkEvents();
		}
		remove();
		soundCheck();

	}

	protected void soundCheck() {
		if (!Music.VRDS.isPlaying()) {
			Music.stopAll();
			Music.VRDS.play();
		}
	}

	protected void checkExits() {

	}

	protected void checkEvents() {

	}

	private void continueGame() {
		continueGame = true;
		for (int i = 63; i < 65; i++) {
			for (int j = 833; j < 872; j++) {
				tiles[j + i * width] = 0xFF00FF;
			}
		}
	}

	protected void remove() {

		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
		}
		for (int i = 0; i < powerUps.size(); i++) {
			if (powerUps.get(i).isRemoved()) powerUps.remove(i);
		}

		for (int i = 0; i < mobs.size(); i++) {
			if (mobs.get(i).isRemoved()) {
				if (mobs.get(i) instanceof MountainGiant) {
					if (!continueGame) {
						continueGame();
					}
				}
				mobs.remove(i);
				killCount++;
			}
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		for (int i = 0; i < battleText.size(); i++) {
			if (battleText.get(i).isRemoved()) battleText.remove(i);
		}
	}

	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile(xt, yt).solid()) {
				solid = true;
			}
		}
		return solid;
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = (xScroll >> 5); // >> 4 = 1/(2/2/2/2) (2,4,8,16) == 1/16
		int x1 = ((xScroll + screen.width + 32) >> 5);
		int y0 = (yScroll >> 5);
		int y1 = ((yScroll + screen.height + 32) >> 5);
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (getTile(x, y) != Tile.voidTile) getTile(x, y).render(x, y, screen);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}

		for (int i = 0; i < powerUps.size(); i++) {
			powerUps.get(i).render(screen);
		}

		for (int i = 0; i < mobs.size(); i++) {
			if (mobs.get(i).playerIsNear()) mobs.get(i).render(screen);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for (int i = 0; i < battleText.size(); i++) {
			battleText.get(i).render(screen);
		}
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
			if (particles.size() > 13000) {
				particles.remove(0);
			}
		} else if (e instanceof PowerUp) powerUps.add((PowerUp) e);
		else if (e instanceof Mob) {
			((Mob) e).initDif(difficulty);
			mobs.add((Mob) e);
		} else if (e instanceof Projectile) projectiles.add((Projectile) e);
		else entities.add(e);
	}

	public void addText(BattleText bt) {
		battleText.add(bt);
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) return Tile.voidTile;
		if (tiles[x + y * getWidth()] == 0xFF00FF00) return (Tile.sWall);
		else if (tiles[x + y * getWidth()] == 0xFF740000) return (Tile.sDarkWall);
		else if (tiles[x + y * getWidth()] == 0xFF137400) return (Tile.sDarkGrass);
		else if (tiles[x + y * getWidth()] == 0xFF005200) return (Tile.sDarkDirt);
		else if (tiles[x + y * getWidth()] == 0xFF4e554e) return (Tile.sDarkRock);

		else if (tiles[x + y * getWidth()] == 0xFF00ffff) return (Tile.rWall);
		else if (tiles[x + y * getWidth()] == 0xFF75d9d9) return (Tile.rWallDark);
		else if (tiles[x + y * getWidth()] == 0xFF00bbbb) return (Tile.rWallx);
		else if (tiles[x + y * getWidth()] == 0xFFd2ff00) return (Tile.sWallSlope);
		else if (tiles[x + y * getWidth()] == 0xFFffb400) return (Tile.sWallSlopeR);

		else if (tiles[x + y * getWidth()] == 0xFF001fbb) return (Tile.rWallDark);
		else if (tiles[x + y * getWidth()] == 0xFFf8ff3a) return (Tile.rWallDark);
		else if (tiles[x + y * getWidth()] == 0xFF61ffd8) return (Tile.sWater);
		// 137400 grass //005200 dirt //4e554e stonef8ff3a
		return Tile.voidTile;
	}

	protected void spawnEnemies() {
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (tiles[i + j * getWidth()] == 0xFFFF0000) {
					add(new RhinoDemon(i * 32, j * 32));
				}
				if (tiles[i + j * getWidth()] == 0xFFf8ff3a) {
					add(new EvilBat(i * 32, j * 32));
				}
				if (tiles[i + j * getWidth()] == 0xFF001fbb) {
					add(new RhinoDemon(i * 32, j * 32));
				}
				if (tiles[i + j * getWidth()] == 0xFF0000FF) {
					add(new FlyingPowerUp(i * 32, j * 32));
				}
				if (tiles[i + j * getWidth()] == 0xFFa8771a) {
					add(new Archer(i * 32, j * 32));
				}
			}
		}
	}

	public int getHeight() {
		return height;
	}

	public int setHeight(int height) {
		this.height = height;
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int setWidth(int width) {
		this.width = width;
		return width;
	}

	public void bossActivate() {

	}

	public void trigger() {
		// TODO Auto-generated method stub

	}

	public void reset() {

	}

	public void freeze() {
		freeze = true;
	}

	public void unfreeze() {
		freeze = false;
	}

}