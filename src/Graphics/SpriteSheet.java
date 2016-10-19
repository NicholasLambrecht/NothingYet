package Graphics;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
	private String path;
	public int[] pixels;
	public int SPRITE_WIDTH, SPRITE_HEIGHT;
	private int width, height;

	// public static SpriteSheet swords = new
	// SpriteSheet("/SwordSpriteSheet.png");
	public static SpriteSheet rivalSprites = new SpriteSheet("/rivalSprites.png", 64, 64);
	public static SpriteSheet player = new SpriteSheet("/playerSprites2.png", 64, 64);
	public static SpriteSheet playerSword = new SpriteSheet("/swordAluSprites.png");
	public static SpriteSheet walls = new SpriteSheet("/WallTiles.png");
	public static SpriteSheet tiles = new SpriteSheet("/SpriteSheet.png");
	public static SpriteSheet mob1 = new SpriteSheet("/Mob1.png");

	public static SpriteSheet barFill15px = new SpriteSheet("/gradiant.png");
	public static SpriteSheet barFill13px = new SpriteSheet("/gradiantblue.png");
	public static SpriteSheet UIBars = new SpriteSheet("/Healthbar_energybar.png");

	public static SpriteSheet smhpb = new SpriteSheet("/smhpb.png");
	public static SpriteSheet smhpbfill = new SpriteSheet("/hpbfill.png");

	public static SpriteSheet tutorial = new SpriteSheet("/thingy.png");

	public static SpriteSheet portrait = new SpriteSheet("/portrait.png");
	public static SpriteSheet barFills = new SpriteSheet("/barFills.png");

	public static SpriteSheet playerFlying = new SpriteSheet("/playingFlying.png");

	public static SpriteSheet background = new SpriteSheet("/background.png");
	public static SpriteSheet wings = new SpriteSheet("/wings.png");

	public static SpriteSheet KingThoctar = new SpriteSheet("/boss1.png");

	public static SpriteSheet BossHPBar = new SpriteSheet("/BossHealthBar.png");
	public static SpriteSheet bossFill = new SpriteSheet("/bossFill.png");

	public static SpriteSheet PlayerDodgeSprites = new SpriteSheet("/dodge.png");

	public static SpriteSheet PlayerAirDodge = new SpriteSheet("/flyDodge.png");

	public static SpriteSheet MainMenu = new SpriteSheet("/mainMenu.png");

	public static SpriteSheet diffMenu = new SpriteSheet("/difficulty.png");

	public static SpriteSheet cursor = new SpriteSheet("/cursor.png");

	public static SpriteSheet bat = new SpriteSheet("/bat.png");

	public static SpriteSheet swords = new SpriteSheet("/swords.png");

	public static SpriteSheet sword1 = new SpriteSheet("/sword1.png");
	public static SpriteSheet sword2 = new SpriteSheet("/sword2.png");
	public static SpriteSheet sword3 = new SpriteSheet("/sword3.png");
	public static SpriteSheet sword4 = new SpriteSheet("/sword4.png");
	public static SpriteSheet sword5 = new SpriteSheet("/sword5.png");
	public static SpriteSheet sword6 = new SpriteSheet("/sword6.png");
	public static SpriteSheet sword7 = new SpriteSheet("/sword7.png");

	public static SpriteSheet trees = new SpriteSheet("/Shrubberies.png");

	public static SpriteSheet decor = new SpriteSheet("/decor.png");

	public static SpriteSheet cerberus = new SpriteSheet("/Cerberus.png");

	public static SpriteSheet boulder = new SpriteSheet("/boulder.png");
	public static SpriteSheet Golem = new SpriteSheet("/Golem.png");
	public static SpriteSheet GolemThrow = new SpriteSheet("/golemThrow.png");

	public static SpriteSheet Spike = new SpriteSheet("/icicles.png");

	public static SpriteSheet Rhino = new SpriteSheet("/rhino.png");

	public static SpriteSheet Shrub = new SpriteSheet("/Shrubbers.png");
	public static SpriteSheet Heart = new SpriteSheet("/heartSprite.png");

	public static SpriteSheet Arrow = new SpriteSheet("/arrow.png");
	public static SpriteSheet Reticle = new SpriteSheet("/reticle.png");

	public static SpriteSheet PlayerShooting = new SpriteSheet("/shooting.png");

	public static SpriteSheet Misc = new SpriteSheet("/misc.png");

	public static SpriteSheet MapBG = new SpriteSheet("/mapborder.png");
	public static SpriteSheet speedTalent = new SpriteSheet("/speedTalent.png");
	public static SpriteSheet rangeTalent = new SpriteSheet("/rangeTalent.png");
	public static SpriteSheet arcanefocusTalent = new SpriteSheet("/arcanefocusTalent.png");
	public static SpriteSheet manabladeTalent = new SpriteSheet("/manabladeTalent.png");
	public static SpriteSheet incinerationTalent = new SpriteSheet("/incinerationTalent.png");
	public static SpriteSheet WorldMapTiles = new SpriteSheet("/WorldMapTiles.png");
	public static SpriteSheet PlayerWorldMap = new SpriteSheet("/playerWorldMapSprites.png");
	public static SpriteSheet mapTiles = new SpriteSheet("/mapTiles.png");

	public static SpriteSheet eArrow = new SpriteSheet("/eArrow.png");

	public static SpriteSheet archer = new SpriteSheet("/archer.png");
	public static SpriteSheet mage = new SpriteSheet("/mage.png");

	public static SpriteSheet talentDes = new SpriteSheet("/talentDes.png");

	public static SpriteSheet options = new SpriteSheet("/mainMenuOptions.png");

	public static SpriteSheet buttons = new SpriteSheet("/buttons.png");

	public static SpriteSheet optionsFS = new SpriteSheet("/mainMenuOptionsFS.png");

	public SpriteSheet(String path) {
		this.path = "" + path;
		SPRITE_WIDTH = 0;
		SPRITE_HEIGHT = 0;
		load2();
	}

	public SpriteSheet(String path, int x, int y) {
		this.path = "" + path;
		SPRITE_WIDTH = x;
		SPRITE_HEIGHT = y;
		load();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getPixels() {
		return pixels;
	}

	private void load() {
		try {
			path = "" + path;
			System.out.print("Loading: " + path + " ...  ");

			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			System.out.println("[ Success! ]");
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("[ Failed! ] Check the path!");
		} catch (Exception e) {
			System.err.println("[ Failed! ] Check the path!");
		}

	}

	private void load2() {
		try {
			path = "" + path;
			System.out.print("Loading: " + path + " ...  ");

			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			System.out.println("[ Success! ]");
			width = image.getWidth();
			height = image.getHeight();
			SPRITE_WIDTH = width;
			SPRITE_HEIGHT = height;
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("[ Failed! ] Check the path!");
		} catch (Exception e) {
			System.err.println("[ Failed! ] Check the path!");
		}

	}

}
