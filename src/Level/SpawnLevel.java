package Level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import Entity.Decor.Lantern;
import Entity.Decor.Pot;
import Entity.Decor.Shrub;
import Entity.Decor.Tree;
import Entity.Mob.Archer;
import Entity.Mob.Cerberus;
import Entity.Mob.EvilBat;
import Entity.Mob.Player;
import Entity.Mob.RhinoDemon;
import Entity.Mob.Rival;
import Entity.PowerUp.FlyingPowerUp;
import Entity.Projectile.Meteor;
import Game.ActionGame;
import Game.Screen;
import Graphics.Font;
import Graphics.Sprite;
import Sounds.Music;
import UI.BattleText;

public class SpawnLevel extends Level {

	private boolean bossActivate = false;
	private String messageText = "";
	int meteorCD = 0;

	public SpawnLevel(String path, int difficulty) {
		super(path, difficulty);
		Music.VRDS.play();
		add(new Cerberus(414 * 32, 50 * 32));
		generateLevel();
		spawnEnemies();
		sprite = Sprite.map1;
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = setWidth(image.getWidth());
			int h = setHeight(image.getHeight());
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("EXCEPTION! Level file");
		}

	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void update() {
		if (!freeze) {
			if (player.x < 85 * 32) {
				player.forceRight();
			} else {
				player.stopForceRight();
				if (player.x < 200 * 32 && player.y < 100 * 32) {

					fireRain();
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
		}
		remove();
		checkEvents();
	}

	private void fireRain() {
		meteorCD++;
		Random rand = new Random();
		if (rand.nextInt(6000) <= meteorCD) {
			int xClicked = player.x - 640 + rand.nextInt(1280);
			int yClicked = player.y;
			int x = (player.x - 1280 + rand.nextInt(2240)) + 20;
			int y = (player.y - 700) - 44;
			double dx = xClicked - x;
			double dy = yClicked - y;
			double direction = Math.atan2(dy, dx);
			add(new Meteor(x, y + 64, direction, this));
			meteorCD = 0;
		}
	}

	protected void checkEvents() {
		int px = player.x / 32;
		int py = player.y / 32;
		int xMin = 97;
		int xMax = 101;
		int yMin = 95;
		int yMax = 98;
		if (px >= xMin && px <= xMax && py >= yMin && py <= yMax) messageText = "Maybe dodge rolling when I jump will help me cross this gap.";
		else if (px >= 227 && px <= 227 && py >= 16 && py <= 17) messageText = "These wings should make it easier to navigate. (C/RT to toggle flight)";
		else if (px >= 314 && px <= 319 && py >= 57 && py <= 64 && !player.flyingUnlock) messageText = "I'm going to need some help to scale a wall this tall.";
		else if (px >= 221 && px <= 223 && py >= 77 && py <= 85) messageText = "Perhaps this old structure is the source of the evil that corrupts these lands.";
		else if (px >= 390 && px <= 398 && py >= 30 && py <= 47) messageText = "I sense a powerful being ahead.";
		else messageText = "";
		if (player.isRemoved()) messageText = "Your strength fails! (Press START/R to continue!";

		if (px >= 375 && px <= 381 && py >= 51 && py <= 52) {
			bossActivate();
		}

		if (bossActivate && player.x < 383 * 32) {
			player.forceRight();
		} else if (bossActivate) {
			tiles[382 + 52 * getWidth()] = 0xFF00FF00;
			tiles[382 + 51 * getWidth()] = 0xFF00FF00;
		}
		if (player.x > 383 * 32) player.stopForceRight();
		if (!bossActivate && Music.VRDS.isPlaying() == false) {
			Music.stopAll();
			Music.VRDS.play();
		} else if (bossActivate && Music.BOSS.isPlaying() == false) {
			Music.stopAll();
			Music.BOSS.play();
		}

	}

	protected void checkExits() {
		if (player.x / 32 >= 421 && player.x / 32 <= 423 && player.y / 32 >= 51 && player.y / 32 <= 52) {
			player.level = new Level2("/level2.png", difficulty);
			player.level.player = player;
			player.x = 19 * 32;
			player.y = 364 * 32;
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
				if (mobs.get(i) instanceof Cerberus) {
					
					tiles[421 + 51 * getWidth()] = 0xFFf2ff88;
					tiles[422 + 51 * getWidth()] = 0xFFf2ff88;
					tiles[421 + 52 * getWidth()] = 0xFFf2ff88;
					tiles[422 + 52 * getWidth()] = 0xFFf2ff88;
				}
				bossActivate = false;
				Music.BOSS.stop();
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
		renderBackground(screen);
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

		Font.render(messageText, screen, 10, 670, 0xDF0000, true);

	}

	public void addText(BattleText bt) {
		battleText.add(bt);
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
				}
			}
		}

		add(new Tree(29 * 32, (100 * 32) - 268, this, 1));

		add(new Tree(34 * 32, (100 * 32) - 268, this, 6));

		add(new Tree(49 * 32, (100 * 32) - 268, this, 3));

		add(new Shrub(26 * 32, (99 * 32) - 10, this));

		add(new Lantern(223 * 32, 65 * 32, this));

		add(new Pot(250 * 32 + 6, 65 * 32, this));
		add(new Pot(251 * 32, 65 * 32, this));
		add(new Pot(251 * 32, 61 * 32, this));
		add(new Pot(261 * 32, 56 * 32, this));
		add(new Pot(262 * 32, 56 * 32, this));
		add(new Pot(257 * 32, 54 * 32, this));
		add(new Pot(239 * 32 + 6, 76 * 32, this));
		add(new Pot(231 * 32 + 6, 78 * 32, this));
		add(new Archer(360 * 32, 51 * 32));
		add(new Tree(150 * 32, (103 * 32) - 268, this, 1));
		add(new Tree(163 * 32, (103 * 32) - 268, this, 4));
		add(new Tree(191 * 32, (102 * 32) - 268, this, 1));
	//	add(new Rival(191*32,102*32));

		add(new EvilBat(250 * 32, 43 * 32));
		add(new EvilBat(206 * 32, 65 * 32));

	}

	public void reset() {
		Level level = new SpawnLevel("/level1.png", player.difficulty);
		player.level = level;
		level.player = player;
		player.x = 25 * 32;
		player.y = 3104;
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

		else if (tiles[x + y * getWidth()] == 0xFF199900) return (Tile.sGrassSlope);
		else if (tiles[x + y * getWidth()] == 0xFF126b00) return (Tile.sGrassSlopeB);

		else if (tiles[x + y * getWidth()] == 0xFF61ffd8) return (Tile.sWater);
		// 137400 grass //005200 dirt //4e554e stonef8ff3a f2ff88
		return Tile.voidTile;
	}

	private void renderBackground(Screen screen) {
		double levelHeight = getHeight() * 32;
		double levelWidth = getWidth() * 32;
		double xOffset = (player.x / levelWidth) * (Sprite.background.getWidth() - ActionGame.width);
		double yOffset = (player.y / levelHeight) * (Sprite.background.getHeight() - ActionGame.height);
		screen.renderBG((int) -xOffset, (int) -yOffset, Sprite.background);
	}

	public void bossActivate() {
		if (bossActivate == false) {
			bossActivate = true;
		}
	}

	protected void generateLevel() {

	}
}
