package WorldMap;

import Game.Screen;
import Graphics.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;
	private boolean solid;
	
	public static Tile wall = new Tile(Sprite.wall,true);
	public static Tile endWall = new Tile(Sprite.endWall,true);
	public static Tile endWallR = new Tile(Sprite.endWallR,true);
	
	public static Tile tower1 = new Tile(Sprite.tower1);
	public static Tile tower2 = new Tile(Sprite.tower2);
	public static Tile tower3 = new Tile(Sprite.tower3);
	

	public static Tile snowMountainLGur = new Tile(Sprite.snowMountainLGur, true);
	public static Tile snowMountainLGul = new Tile(Sprite.snowMountainLGul, true);
	public static Tile snowMountainLGbr = new Tile(Sprite.snowMountainLGbr, true);
	public static Tile snowMountainLGbl = new Tile(Sprite.snowMountainLGbl, true);

	public static Tile grassSnowTL = new Tile(Sprite.grassSnowTL, false);
	public static Tile grassSnowTR = new Tile(Sprite.grassSnowTR, false);
	public static Tile grassSnowTM = new Tile(Sprite.grassSnowTM, false);
	public static Tile grassSnowML = new Tile(Sprite.grassSnowML, false);
	public static Tile grassSnowMR = new Tile(Sprite.grassSnowMR, false);

	public static Tile snowGrassTL = new Tile(Sprite.snowGrassTL, false);
	public static Tile snowGrassTR = new Tile(Sprite.snowGrassTR, false);

	public static Tile snowMountain = new Tile(Sprite.snowMountain, true);
	public static Tile snow = new Tile(Sprite.snow, false);
	public static Tile grassMountain = new Tile(Sprite.grassMountain, true);
	public static Tile grass = new Tile(Sprite.grass, false);
	public static Tile voidTile = new Tile(Sprite.voidSprite);

	public Tile(Sprite sprite) {
		this.sprite = sprite;
		solid = false;

	}

	public Tile(Sprite sprite, boolean solid) {
		this.sprite = sprite;
		this.solid = solid;

	}

	public Tile(Sprite sprite, boolean solid, boolean slope) {
		this.sprite = sprite;
		this.solid = solid;

	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 5, y << 5, sprite);
	}

	public boolean solid() {
		return solid;
	}

	public boolean isVoidTile() {
		return false;
	}

}
