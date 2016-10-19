package Entity.Spawner;

import Entity.Entity;
import Level.Level;

public class Spawner extends Entity {

	public enum Type {
		GHOST, PARTICLE;
	}

	@SuppressWarnings("unused")
	private Type type;

	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
		for (int i = 0; i < amount; i++) {

		}
	}
}
