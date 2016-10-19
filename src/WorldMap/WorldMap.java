package WorldMap;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import Entity.Mob.Player;
import Game.Screen;
import Level.Level3;
import Level.Level4;
import Level.Level5;
import Level.SpawnLevel;
import Sounds.Music;
import Sounds.SoundEffect;

public class WorldMap {

	private static int width;
	private static int height;
	protected int[] tilesInt;
	private static int[] tiles;
	public static Player player;
	public static WMPlayer wmPlayer;
	private static Random rand = new Random();
	public static boolean battleEvent = false;
	private static int efMod = 5;
	private static int timer = 0;
	private static int rdbattlemod = 0;

	public WorldMap(int width, int height, int difficulty) {
		WorldMap.setWidth(width);
		WorldMap.setHeight(height);
		tilesInt = new int[width * height];
	}

	public WorldMap(String path) {
		loadLevel(path);

	}

	private static void generateLevel() {
		int gSnowTL = 1;
		int gSnowTM = 2;
		int gSnowTR = 3;
		int gSnowLM = 4;
		int gSnowRM = 5;
		int gSnowCNL = 6;
		int gSnowCNR = 7;
		int tower2 = 11;
		int tower1 = 10;
		int endWall = 8;
		int endWallR = 9;

		for (int yi = 0; yi < height; yi++) {
			for (int xi = 0; xi < width; xi++) {
				if (getTile(xi, yi) == Tile.snowMountainLGul) {
					tiles[xi + (yi * width) + 1] = 0xFF777779;
					tiles[xi + ((yi + 1) * width)] = 0xFF777780;
					tiles[xi + ((yi + 1) * width) + 1] = 0xFF777781;
				}
				if (getTile(xi, yi) == Tile.grass) { //Each Grass Tile
					if (getTile(xi, yi - 1) == Tile.snow) { //Check Tile Above to see if snow
						if (getTile(xi - 1, yi) == Tile.snow) { //Check the tile to the left 
							setTile(gSnowTL, xi, yi);//Set the sprite to a top left corner gSnow sprite
						} else if (getTile(xi + 1, yi) == Tile.snow) { //Check the tile to the right
							setTile(gSnowTR, xi, yi);//Set the sprite to a top right corner gSnow sprite
						} else {
							setTile(gSnowTM, xi, yi);//Set the tile to a top middle sprite
							if (getTile(xi + 1, yi - 1) == Tile.grassSnowML || getTile(xi + 1, yi - 1) == Tile.grassSnowTL) { //If tile to the right and above this one is grass
								setTile(gSnowCNL, xi + 1, yi); //set the tile to the right to a corner snow tile
							}
							if (getTile(xi - 1, yi - 1) == Tile.grassSnowMR || getTile(xi - 1, yi - 1) == Tile.grassSnowTR) { //If tile to the left and above this one is grass
								setTile(gSnowCNR, xi - 1, yi); //set the tile to the left to a corner snow tile
							}
						}
					} else if (getTile(xi + 1, yi) == Tile.snow) {
						setTile(gSnowLM, xi, yi);
						if (getTile(xi + 1, yi + 1) == Tile.grass) {

						}
					} else if (getTile(xi - 1, yi) == Tile.snow) {
						setTile(gSnowRM, xi, yi);
					} else if (getTile(xi + 1, yi) == Tile.grassSnowTR) {
						setTile(gSnowCNR, xi, yi);
					} else if (getTile(xi - 1, yi) == Tile.grassSnowTL) {
						setTile(gSnowCNL, xi, yi);
					}
				}
				if (getTile(xi, yi) == Tile.tower3) {
					setTile(tower1, xi, yi - 2);
					setTile(tower2, xi, yi - 1);
				}
				if (getTile(xi, yi) == Tile.wall) {
					if (getTile(xi + 1, yi) != Tile.endWallR && getTile(xi - 1, yi) != Tile.endWall && getTile(xi - 1, yi) != Tile.wall) {
						setTile(endWall, xi, yi);
					}
					if (getTile(xi - 1, yi) != Tile.endWall && getTile(xi + 1, yi) != Tile.endWallR && getTile(xi + 1, yi) != Tile.wall) {

						setTile(endWallR, xi, yi);
					}
				}
			}
		}
	}

	private static void setTile(int color, int x, int y) {
		tiles[x + (y * width)] = color;
	}

	protected static void loadLevel(String path) {
		try {
			path = "" + path;
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = setWidth(image.getWidth());
			int h = setHeight(image.getHeight());
			width = w;
			height = h;
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("EXCEPTION! Level file");
		}
		generateLevel();
		generateLevel();
	}

	public static void setPlayer(Player player) {
		WorldMap.player = player;
	}

	public static void update() {
		//		generateLevel();
		//	wmPlayer.x = 35*32;
		if (wmPlayer.moving && !battleEvent) {
			rdbattlemod++;

			if (rand.nextInt(16400) <= rdbattlemod - 16 * 6) {
				battleEvent = true;
				rdbattlemod = 0;
				// wmPlayer.inputDelay = 0;
				// wmPlayer.inputFlag = false;
				SoundEffect.ENCOUNTER.play();
				Music.CAPTAIN.stop();
			}
		} else if (battleEvent) {
			timer++;
			if (timer >= 160) {
				timer = 0;
				battleEvent = false;
			} else if (timer == 120) {
					randomBattle();

			}
		}
		if (!battleEvent) {
			if (!Music.CAPTAIN.isPlaying()) {
				Music.stopAll();
				Music.CAPTAIN.play();
			}
		}
		checkExits();
		checkEvents();
	}

	private static void randomBattle() {
		if (getTile(wmPlayer.x / 32, wmPlayer.y / 32) == Tile.snow) {
			snowBattle();
		} else grassBattle();
	}

	private static void snowBattle() {
		Level3 level = new Level3("/level3.png", player.difficulty);
		player.level = level;
		level.player = player;
		player.x = 25 * 32;
		player.y = 74 * 32;
		player.worldMap = false;
	}

	private static void grassBattle() {
		Level4 level = new Level4("/level4.png", player.difficulty);
		player.level = level;
		level.player = player;
		player.x = 54 * 32;
		player.y = 71 * 32;
		player.worldMap = false;
	}

	private static void checkExits() {
		if (player.x > 848 * 32) {
			player.gotoWorldMap();
		}
		if(wmPlayer.x == 124*32 && wmPlayer.y ==79*32){
			Level5 level = new Level5("/level5.png", player.difficulty);
			player.level = level;
			level.player = player;
			player.x = 54 * 32;
			player.y = 209 * 32;
			player.worldMap = false;
		}
	}

	private static void checkEvents() {

	}

	public static boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
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

	public static void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = (xScroll >> efMod); // >> 4 = 1/(2/2/2/2) (2,4,8,16) == 1/16
		int x1 = ((xScroll + screen.width + 32) >> efMod);
		int y0 = (yScroll >> efMod);
		int y1 = ((yScroll + screen.height + 32) >> efMod);
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (getTile(x, y) != Tile.voidTile) getTile(x, y).render(x, y, screen);
			}
		}
	}

	public static Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) return Tile.voidTile;
		else if (tiles[x + y * getWidth()] == 0xFF009207) return (Tile.grass);
		else if (tiles[x + y * getWidth()] == 0xFFa37700) return (Tile.grassMountain);
		else if (tiles[x + y * getWidth()] == 0xFFbebebe) return (Tile.snow);
		else if (tiles[x + y * getWidth()] == 0xFF777777) return (Tile.snowMountain);
		else if (tiles[x + y * getWidth()] == 0xFF777778) return (Tile.snowMountainLGul);
		else if (tiles[x + y * getWidth()] == 0xFF777779) return (Tile.snowMountainLGur);
		else if (tiles[x + y * getWidth()] == 0xFF777780) return (Tile.snowMountainLGbl);
		else if (tiles[x + y * getWidth()] == 0xFF777781) return (Tile.snowMountainLGbr);
		else if (tiles[x + y * getWidth()] == 0xFF0024FF) return (Tile.wall);
		else if (tiles[x + y * getWidth()] == 0xFFff0000) return (Tile.tower3);
		else if (tiles[x + y * getWidth()] == 1) return Tile.grassSnowTL;
		else if (tiles[x + y * getWidth()] == 2) return Tile.grassSnowTM;
		else if (tiles[x + y * getWidth()] == 3) return Tile.grassSnowTR;
		else if (tiles[x + y * getWidth()] == 4) return Tile.grassSnowMR;
		else if (tiles[x + y * getWidth()] == 5) return Tile.grassSnowML;
		else if (tiles[x + y * getWidth()] == 6) return Tile.snowGrassTL;
		else if (tiles[x + y * getWidth()] == 7) return Tile.snowGrassTR;
		else if (tiles[x + y * getWidth()] == 8) return Tile.endWall;
		else if (tiles[x + y * getWidth()] == 9) return Tile.endWallR;
		else if (tiles[x + y * getWidth()] == 10) return Tile.tower1;
		else if (tiles[x + y * getWidth()] == 11) return Tile.tower2;

		// 137400 grass //005200 dirt //4e554e stonef8ff3a a37700 bebebe
		return Tile.voidTile;
	}

	public static int getHeight() {
		return height;
	}

	public static int setHeight(int height) {
		WorldMap.height = height;
		return height;
	}

	public static int getWidth() {
		return width;
	}

	public static int setWidth(int width) {
		WorldMap.width = width;
		return width;
	}

	public static void load(String string) {
		loadLevel(string);
	}
}