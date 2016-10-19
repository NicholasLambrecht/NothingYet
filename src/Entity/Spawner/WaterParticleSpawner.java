package Entity.Spawner;

import Entity.Particle.WaterParticle;
import Level.Level;

public class WaterParticleSpawner extends Spawner {

	public WaterParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		for (int i = 0; i < amount; i++) {
			level.add(new WaterParticle(x, y, life));
		}
		remove();

	}
}
