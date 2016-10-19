package Entity.Spawner;

import Entity.Particle.POFParticle;
import Entity.Particle.YPOFParticle;
import Level.Level;

import java.util.Random;

public class POFParticleSpawner extends Spawner {

	public POFParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		Random rand = new Random();
		for (int i = 0; i < amount; i++) {
			int gr = rand.nextInt(4);
			if(gr == 0){
				level.add(new YPOFParticle(x, y, life));
			} else level.add(new POFParticle(x, y, life));
		}
		remove();

	}
}
