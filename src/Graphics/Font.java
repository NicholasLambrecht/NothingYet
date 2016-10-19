package Graphics;

import Game.Screen;

public class Font {

	public static SpriteSheet fontNorm = new SpriteSheet("/font.png", 32, 32);
	public static SpriteSheet fontPixel = new SpriteSheet("/pixelFont.png", 32, 32);
	public static SpriteSheet fontSMPixel = new SpriteSheet("/pixelFontsm.png", 16, 16);
	private static Sprite[] characters = Sprite.split(fontNorm);
	private static Sprite[] charactersPixel = Sprite.split(fontPixel);
	private static Sprite[] charactersPixelSM = Sprite.split(fontSMPixel);

	private static String charIndex = " !\"#$%&'()*+,-.?" + //
			"0123456789:;<=>/" + //
			"@ABCDEFGHIJKLMNO" + //
			"PQRSTUVWXYZ[\\]^_" + //
			"`abcdefghijklmno" + //
			"pqrstuvwxyz{|}~ ";

	private static String charIndexPixel = "ABCDEFGHIJKLM" + //
			"NOPQRSTUVWXYZ" + //
			"1234567890 !.";

	public Font() {

	}

	public static void render(String text, Screen screen, int x, int y, int color, boolean stroke) {
		for (int i = 0; i < text.length(); i++) {
			char currentChar = text.charAt(i);
			int index = charIndex.indexOf(currentChar);
			screen.renderFontCharacter(x + i * 16, y, characters[index], color, false, stroke);
		}
	}

	public static void renderBT(String text, Screen screen, int x, int y) {
		for (int i = 0; i < text.length(); i++) {
			char currentChar = text.charAt(i);
			int index = charIndexPixel.indexOf(currentChar);
			screen.renderFont(x + i * 22, y, charactersPixel[index], true);
		}
	}

	public static void renderBTST(String text, Screen screen, int x, int y) {
		for (int i = 0; i < text.length(); i++) {
			char currentChar = text.charAt(i);
			int index = charIndexPixel.indexOf(currentChar);
			screen.renderFont(x + i * 22, y, charactersPixel[index], false);
		}
	}

	public static void renderBTSTSM(String text, Screen screen, int x, int y) {
		int dy = y;
		int dx = 0;
		for (int i = 0; i < text.length(); i++) {
			char currentChar = text.charAt(i);
			int index = charIndexPixel.indexOf(currentChar);
			if (currentChar == '\n') {
				dy += 16;
				dx = 0;
			} else {
				screen.renderFont(x + dx, dy, charactersPixelSM[index], false);
				dx += 11;
			}
		}
	}

	public static void renderBTSM(String text, Screen screen, int x, int y) {
		for (int i = 0; i < text.length(); i++) {
			char currentChar = text.charAt(i);
			int index = charIndexPixel.indexOf(currentChar);
			screen.renderFont(x + i * 11, y, charactersPixelSM[index], true);
		}
	}

}
