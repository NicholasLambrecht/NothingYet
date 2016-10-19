package Level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.Decor.Lantern;
import Entity.Decor.Pot;
import Entity.Mob.EvilBat;
import Entity.Mob.Mob;
import Entity.Mob.MountainGiant;
import Entity.Mob.RhinoDemon;
import Entity.Particle.SnowParticle;
import Entity.PowerUp.FlyingPowerUp;
import Entity.Projectile.Boulder;
import Entity.Projectile.Spike;
import Game.Screen;
import Graphics.Sprite;
import Sounds.Music;

public class Level2 extends Level {

	private int spawnBoulder = 0;
	private boolean avalanche = false;
	Mob boss;
	int xOffset = 0;
	int yOffset = 0;
	private boolean bossStarted = false;

	public Level2(String path, int difficulty) {
		super(path, difficulty);
		sprite = Sprite.map2;
		generateLevel();
		spawnEnemies();
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(Level2.class.getResource(path));
			int w = setWidth(image.getWidth());
			int h = setHeight(image.getHeight());
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("EXCEPTION! Level file");
		}

	}

	public void reset() {
		Level level = new Level2("/level2.png", player.difficulty);
		player.level = level;
		level.player = player;
		player.x = 19 * 32;
		player.y = 364 * 32;
	}

	public void update() {
		if (!freeze) {
			if (player.x > 325 * 32) {
				snow();
			}
			spawnBoulder++;
			if (spawnBoulder > 60) {
				if (avalanche) {
					add(new Boulder(819 * 32, 25 * 32 - 25));
					add(new Boulder(818 * 32, 25 * 32 - 14));
					add(new Boulder(817 * 32, 22 * 32 - 12));
					// add(new Boulder(825 * 32, 22 * 32));
					spawnBoulder = 35;
				} else {
					add(new Boulder(725 * 32, 73 * 32));
					spawnBoulder = 0;
				}
			}
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
				if (mobs.get(i).playerCollision(player.x, player.y)) {
					player.takeDamage(mobs.get(i).getDamage());
				}
			}
			// if (player.returning) {
			// for (int i = 0; i < mobs.size(); i++) {
			// if (mobs.get(i).playerIsNear() && !(mobs.get(i) instanceof
			// MountainGiant)) mobs.get(i).takeDamage(50);
			// else mobs.get(i).resumeAI();
			// }
			// }
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

		if (!bossStarted && Music.VRDS.isPlaying() == false) {
			Music.stopAll();
			Music.VRDS.play();
		} else if (bossStarted && Music.BOSS2.isPlaying() == false && player.y / 32 < 62) {
			Music.stopAll();
			Music.BOSS2.play();
		}
	}

	private void snow() {
		add(new SnowParticle(xOffset, yOffset, 1500));
	}

	protected void checkEvents() {
		if (player.x / 32 < 22) {
			player.forceRight();
		} else player.stopForceRight();

		if (player.y < 63 * 32 && !bossStarted) {
			startBoss();
		}
	}

	private void startBoss() {
		bossStarted = true;
		MountainGiant b = (MountainGiant) boss;
		b.trigger();
		tiles[789 + 64 * getWidth()] = 0xFFd8d8d8;
		tiles[790 + 64 * getWidth()] = 0xFFd8d8d8;
		tiles[791 + 64 * getWidth()] = 0xFFd8d8d8;
		tiles[792 + 64 * getWidth()] = 0xFFd8d8d8;
	}

	public void trigger() {
		avalanche = true;
		for (int i = 814; i < 825; i++) {
			for (int j = 27; j < 64; j++) {
				tiles[i + j * getWidth()] = 0xFF000000;
				if (j % 4 == 0 && i % 7 == 0) {
					add(new Boulder(i * 32, j * 32 - 100));
				}
			}
		}
	}

	public void stompWall() {
		for (int i = 772; i < 774; i++) {
			for (int j = 27; j < 64; j++) {
				tiles[i + j * getWidth()] = 0xFFa9a9a9;
			}
		}
	}

	protected void checkExits() {
		if (player.x > 848 * 32) {
			player.gotoWorldMap();
			Music.stopAll();
		}
	}

	protected void spawnEnemies() {
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (tiles[i + j * getWidth()] == 0xFFFF0000) {
					add(new RhinoDemon(i * 32, j * 32));
				} else if (tiles[i + j * getWidth()] == 0xFFf8ff3a) {
					add(new EvilBat(i * 32, j * 32));
				} else if (tiles[i + j * getWidth()] == 0xFF0000FF) {
					add(new FlyingPowerUp(i * 32, j * 32));
				} else if (tiles[i + j * getWidth()] == 0xFFf2ff88) {
					add(new Lantern(i * 32, j * 32, this));
				} else if (tiles[i + j * getWidth()] == 0xFF007218 || tiles[i + j * getWidth()] == 0xFF00a323) {
					add(new Pot(i * 32, j * 32, this));
				}
			}
		}
		boss = new MountainGiant(775 * 32, 61 * 32);
		add(boss);
		// BOSS: 768x 57y (+1 y or more?)

	}

	public void render(int xScroll, int yScroll, Screen screen) {
		renderBackground(screen);
		xOffset = xScroll;
		yOffset = yScroll;

		screen.setOffset(xScroll, yScroll);
		int x0 = (xScroll >> 5); // >> 4 = 1/(2/2/2/2) (2,4,8,16) == 1/16
		int x1 = ((xScroll + screen.width + 32) >> 5);
		int y0 = (yScroll >> 5);
		int y1 = ((yScroll + screen.height + 32) >> 5);

		for (int i = 0; i < projectiles.size(); i++) {
			if ((projectiles.get(i) instanceof Spike)) projectiles.get(i).render(screen);
		}

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
			if (!(projectiles.get(i) instanceof Spike)) projectiles.get(i).render(screen);
		}
		for (int i = 0; i < battleText.size(); i++) {
			battleText.get(i).render(screen);
		}

		// Font.render(messageText, screen, 10, 670, 0xDF0000, true);

	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) return Tile.voidTile;
		if (tiles[x + y * getWidth()] == 0xFF00FF00) return (Tile.sWall);
		else if (tiles[x + y * getWidth()] == 0xFF740000) return (Tile.sDarkWall);
		else if (tiles[x + y * getWidth()] == 0xFF137400) return (Tile.sDarkGrass);
		else if (tiles[x + y * getWidth()] == 0xFF005200) return (Tile.sDarkDirt);
		else if (tiles[x + y * getWidth()] == 0xFF4e554e) return (Tile.sDarkRock);
		else if (tiles[x + y * getWidth()] == 0xFFf2ff88) return (Tile.sDarkWall);
		else if (tiles[x + y * getWidth()] == 0xFF00ffff) return (Tile.rWall);
		else if (tiles[x + y * getWidth()] == 0xFF75d9d9) return (Tile.rWallDark);
		else if (tiles[x + y * getWidth()] == 0xFF00bbbb) return (Tile.rWallx);
		else if (tiles[x + y * getWidth()] == 0xFFd2ff00) return (Tile.sWallSlope);
		else if (tiles[x + y * getWidth()] == 0xFFffb400) return (Tile.sWallSlopeR);
		else if (tiles[x + y * getWidth()] == 0xFFffb400) return (Tile.sWallSlopeR);
		else if (tiles[x + y * getWidth()] == 0xFF61ffd8) return (Tile.sWater);
		else if (tiles[x + y * getWidth()] == 0xFFd8d8d8) return (Tile.mGrass);
		else if (tiles[x + y * getWidth()] == 0xFFa9a9a9) return (Tile.mDirt);
		else if (tiles[x + y * getWidth()] == 0xFF7d7d7d) return (Tile.mRock);
		else if (tiles[x + y * getWidth()] == 0xFF6d6d6d) return (Tile.mRockDark);
		else if (tiles[x + y * getWidth()] == 0xFF393939) return (Tile.mRockWall);

		// 137400 grass //005200 dirt //4e554e stonef8ff3a f2ff88
		return Tile.voidTile;
	}

	private void renderBackground(Screen screen) {
		double levelHeight = getHeight() * 32;
		double levelWidth = getWidth() * 32;
		double xOffset = (player.x / levelWidth) * (1920 - 1280);
		double yOffset = (player.y / levelHeight) * (1080 - 800);
		screen.renderBG((int) -xOffset, (int) -yOffset, Sprite.background);
	}

	protected void generateLevel() {

	}
}
