package Entity.Decor;

import Entity.Entity;
import Game.Screen;
import Graphics.Sprite;
import Level.Level;

public abstract class Decor extends Entity {

	protected boolean destroyed;
	protected Sprite regularSprite;
	protected Sprite destroyedSprite;
	protected Sprite sprite;

	public Decor(int x, int y, Level level) {
		this.x = x;
		this.y = y;
		this.level = level;
		destroyed = false;
	}

	public void destroy() {
		destroyed = true;
		sprite = destroyedSprite;
	}

	public void update() {

	}

	public void render(Screen screen) {
		screen.renderSprite(x, y, sprite, true);
	}
}
