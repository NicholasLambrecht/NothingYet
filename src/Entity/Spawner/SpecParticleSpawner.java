package Entity.Spawner;

import Entity.Particle.SpecParticle;
import Level.Level;

public class SpecParticleSpawner extends Spawner {

	public SpecParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		for (int i = 0; i < amount; i++) {
			level.add(new SpecParticle(x, y, life));
		}
		remove();

	}
}
