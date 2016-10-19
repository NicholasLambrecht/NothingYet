package Entity.Spawner;

import Entity.Particle.RParticle;
import Entity.Particle.YParticle;
import Level.Level;

import java.util.Random;

public class ParticleSpawner extends Spawner {

	public ParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		Random rand = new Random();
		for (int i = 0; i < amount; i++) {
			int gr = rand.nextInt(4);
			if(gr == 0){
				level.add(new YParticle(x, y, life));
			} else level.add(new RParticle(x, y, life));
		}
		remove();

	}
}
