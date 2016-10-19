package Level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.Decor.Lantern;
import Entity.Decor.Pot;
import Entity.Mob.Archer;
import Entity.Mob.EvilBat;
import Entity.Mob.Mage;
import Entity.Mob.MountainGiant;
import Entity.Mob.RhinoDemon;
import Entity.PowerUp.FlyingPowerUp;
import Game.Screen;
import Graphics.Font;
import Graphics.Sprite;
import Sounds.Music;
import WorldMap.WorldMap;

public class Level5 extends Level {

	private String message = "";

	public Level5(String path, int difficulty) {
		super(path, difficulty);
		sprite = Sprite.map4;
		generateLevel();
		spawnEnemies();
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(Level5.class.getResource(path));
			int w = setWidth(image.getWidth());
			int h = setHeight(image.getHeight());
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("EXCEPTION! Level file");
		}

	}

	protected void soundCheck() {
		if (!Music.SBJ.isPlaying()) {
			Music.stopAll();
			Music.SBJ.play();
		}
	}

	protected void checkExits() {
		if (player.x > 202 * 32) {
			WorldMap.wmPlayer.y -=3*32; 
			player.gotoWorldMap();
			Music.VICTORY.stop();
			
		}
		if (player.x < 54 * 32) player.forceRight();
		else player.stopForceRight();
	}

	protected void spawnEnemies() {
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (tiles[i + j * getWidth()] == 0xFFFF0000) {
					add(new RhinoDemon(i * 32, j * 32));
					tiles[i + j * getWidth()] = 0xFFf2ff88;
				} else if (tiles[i + j * getWidth()] == 0xFFf8ff3a) {
					add(new EvilBat(i * 32, j * 32));
					tiles[i + j * getWidth()] = 0xFFf2ff88;
				} else if (tiles[i + j * getWidth()] == 0xFF0000FF) {
					add(new FlyingPowerUp(i * 32, j * 32));
					tiles[i + j * getWidth()] = 0xFFf2ff88;
				} else if (tiles[i + j * getWidth()] == 0xFFf2ff88) {
					add(new Lantern(i * 32, j * 32, this));
					tiles[i + j * getWidth()] = 0xFFf2ff88;
				} else if (tiles[i + j * getWidth()] == 0xFFa8771a) {
					add(new Archer(i * 32, j * 32));
					tiles[i + j * getWidth()] = 0xFFf2ff88;
				} else if (tiles[i + j * getWidth()] == 0xFFa2a000) {
					add(new Mage(i * 32, j * 32));
					tiles[i + j * getWidth()] = 0xFFf2ff88;
				} else if (tiles[i + j * getWidth()] == 0xFF007218 || tiles[i + j * getWidth()] == 0xFF00a323) {
					add(new Pot(i * 32, j * 32, this));
					tiles[i + j * getWidth()] = 0xFFf2ff88;
				}
			}
		}
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

		Font.render(message, screen, 10, 670, 0xDF0000, true);

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

		if (player.isRemoved()) {
			player.x = 54 * 32;
			player.y = 206 * 32;
		}

		for (int i = 0; i < mobs.size(); i++) {
			if (mobs.get(i).isRemoved()) {
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
		double xOffset = (player.x / levelWidth) * 640;
		double yOffset = (player.y / levelHeight) * 360;
		screen.renderBG((int) -xOffset, (int) -yOffset, Sprite.background);
	}

	protected void generateLevel() {

	}
}
