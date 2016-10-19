package Entity.Spawner;

import Entity.Particle.BloodParticle;
import Level.Level;

public class BloodParticleSpawner extends Spawner {

	public BloodParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		for (int i = 0; i < amount; i++) {
			level.add(new BloodParticle(x, y, life));
		}
		remove();

	}
}
