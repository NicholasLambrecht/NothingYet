package Entity.Spawner;

import Entity.Mob.RhinoDemon;
import Level.Level;

public class EnemySpawner extends Spawner{

	public EnemySpawner(int x, int y, Type type, int amount, Level level) {
		super(x, y, type, amount, level);
		for (int i = 0; i < amount; i++) {
			level.add(new RhinoDemon(x, y));
		}
		remove();
	}

}
