package Game;

import Graphics.Sprite;

public class Screen {
	public int width;
	public int height;
	public int[] pixels;
	public final int MAP_SIZE = 16;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	public int xOffset, yOffset;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderMap(int xp, int yp, Sprite sprite, int px, int py) {
		py *= 3;
		px *= 3;
		while (py - 100 <= 0) {
			py++;
		}
		while (py + 100 > sprite.getHeight()) {
			py--;
		}
		while (px - 100 <= 0) {
			px++;
		}
		while (px + 100 > sprite.getWidth()) {
			px--;
		}

		for (int y = py - 100; y < py + 100; y++) {
			if (y < 0) y = 0;
			int ya = y + yp - py;
			for (int x = px - 100; x < px + 100; x++) {
				int xa = x + xp - px;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != 0xffff00ff) {
					pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
				}
			}
		}
	}

	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != 0xffff00ff) {
					pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
				}
			}
		}
	}

	public void renderBG(int xp, int yp, Sprite sprite) {
		for (int y = yp; y < sprite.getHeight() - (1080 - ActionGame.height) - yp; y++) {
			if (y < 0) y = 0;
			if (y > sprite.getHeight()) break;
			int ya = y + yp;
			for (int x = xp; x < sprite.getWidth() - (1920 - ActionGame.width) - xp; x++) {
				if (x < 0) x = 0;
				if (x > sprite.getWidth()) break;
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
	}

	public void renderProjectile(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < -sprite.getWidth() || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = sprite.pixels[x + y * 16];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
				// pixels[xa + ya * width] = sprite.pixels[x + y * sprite.SIZE];
			}
		}
	}

	public void renderFontCharacter(int xp, int yp, Sprite sprite, int color, boolean fixed, boolean stroke) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != 0xffff00ff) {
					if (stroke) {
						pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
					}
					if (col == 0xff00ff00) {
						pixels[xa + ya * width] = color;
					}
				}
			}
		}
	}

	public void renderFont(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != 0xffff00ff) {
					pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
				}
			}
		}
	}

	public void renderTile(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < -sprite.getWidth() || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != 0xffff00ff) {
					pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
				}
			}
		}
	}

	public void renderRect(int xp, int yp, int col, int xsi, int ysi) {
		for (int y = 0; y < ysi; y++) {
			int ya = y + yp;
			for (int x = 0; x < xsi; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				pixels[xa + ya * width] = col;
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}
