package UI;

import Game.Screen;
import Graphics.Sprite;
import Level.Level;

public class Map extends UIElement {
	public Level level;
	private int px;
	private int py;
	private int timer = 0;
	private boolean flicker = false;

	public Map(int x, int y, Level level) {
		super(x, y, Sprite.voidSprite);
		this.level = level;
		this.sprite = level.sprite;
		xa = x;
		ya = y;
		px = level.player.x / 32;
		py = level.player.y / 32;
	}

	public void update() {
		px = level.player.x / 32;
		py = level.player.y / 32;
		timer++;
		if (timer > 60) {
			timer = 0;
			if (flicker) flicker = false;
			else flicker = true;
		}
		sprite = level.sprite;

	}

	public void render(Screen screen) {
		screen.renderSprite(xa - 107, ya - 107, Sprite.MapBG, false);
		screen.renderMap(xa, ya, sprite, px, py);
		int xOffset = 0;
		int yOffset = 0;
		if (px < 100 / 3) xOffset = -100 + px * 3;
		if (py < 100 / 3) yOffset = ya - 10 - py * 3;
		if (py > (level.sprite.getHeight() - 100) / 3) yOffset = -33 - py + ((level.sprite.getHeight() - 100) / 3);
		if (px > (level.sprite.getWidth() - 100) / 3) xOffset = 100 - (((level.sprite.getWidth() + 100) / 3) - px);

		if (flicker) {
			screen.renderSprite(xa + xOffset, ya - yOffset, Sprite.particle_normal, false);
		} else screen.renderSprite(xa + xOffset, ya - yOffset, Sprite.particle_blood, false);
	}
}
