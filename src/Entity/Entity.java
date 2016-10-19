package Entity;

import Game.Screen;
import Level.Level;

import java.util.Random;


public abstract class Entity {

	public int x;
	public int y;
	public Level level;
	protected boolean removed = false;
	protected final Random random = new Random();

	public void update() {
	}

	public void render(Screen screen) {
	}

	public void remove() {
		// Remove from the level
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void init(Level level) {
		this.level = level;
		
	}
}