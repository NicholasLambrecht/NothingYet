package Entity.Spawner;

import Entity.Particle.YParticle;
import Level.Level;

public class BoulderParticleSpawner extends Spawner {

	public BoulderParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		for (int i = 0; i < amount; i++) {
			level.add(new YParticle(x, y, life));
		}
		remove();

	}
}
