package Level;

import Game.Screen;
import Graphics.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;
	private boolean solid, slope;

	public static Tile sWall = new Tile(Sprite.sWall, true);
	public static Tile sGrass = new Tile(Sprite.sGrass, true);
	public static Tile sDirt = new Tile(Sprite.sDirt, true);
	public static Tile sRock = new Tile(Sprite.sRock, true);

	public static Tile sGrassSlope = new Tile(Sprite.sGrassSlope, false, true);
	public static Tile sGrassSloper = new Tile(Sprite.sGrassSloper, false, true);

	public static Tile sGrassSlopeB = new Tile(Sprite.sGrassSlopeB, true);
	public static Tile sGrassSlopeBr = new Tile(Sprite.sGrassSlopeBr, true);

	public static Tile mRockDark = new Tile(Sprite.mRockDark, true);
	public static Tile mGrass = new Tile(Sprite.mGrass, true);
	public static Tile mDirt = new Tile(Sprite.mDirt, true);
	public static Tile mRock = new Tile(Sprite.mRock, true);
	public static Tile mRockWall = new Tile(Sprite.mRockWall, false);

	public static Tile sDarkWall = new Tile(Sprite.sWallDark, false);
	public static Tile sDarkGrass = new Tile(Sprite.sGrassDark, true);
	public static Tile sDarkDirt = new Tile(Sprite.sDirtDark, true);
	public static Tile sDarkRock = new Tile(Sprite.sRockDark, true);

	public static Tile rWall = new Tile(Sprite.rWall, true);
	public static Tile rWallDark = new Tile(Sprite.rWallDark, false);
	public static Tile rWallx = new Tile(Sprite.rWallx, true);

	public static Tile sWallSlope = new Tile(Sprite.sWallSlope, true, true);
	public static Tile sWallSlopeR = new Tile(Sprite.sWallSlopeR, true, true);
	public static Tile sWater = new Tile(Sprite.sWater, false);

	public static Tile voidTile = new Tile(Sprite.voidSprite);

	public Tile(Sprite sprite) {
		this.sprite = sprite;
		solid = false;
		slope = false;
	}

	public Tile(Sprite sprite, boolean solid) {
		this.sprite = sprite;
		this.solid = solid;
		slope = false;
	}

	public Tile(Sprite sprite, boolean solid, boolean slope) {
		this.sprite = sprite;
		this.solid = solid;
		this.slope = slope;
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 5, y << 5, sprite);
	}

	public boolean sloped() {
		return slope;
	}

	public boolean solid() {
		return solid;
	}

	public boolean isVoidTile() {
		return false;
	}

}
