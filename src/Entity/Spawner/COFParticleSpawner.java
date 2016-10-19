package Entity.Spawner;

import java.util.Random;

import Entity.Particle.POFParticle;
import Entity.Particle.YCOFParticle;
import Level.Level;

public class COFParticleSpawner extends Spawner {

	public COFParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		Random rand = new Random();
		for (int i = 0; i < amount; i++) {
			int gr = rand.nextInt(4);
			if(gr == 0){
				level.add(new YCOFParticle(x, y, life));
			} else level.add(new POFParticle(x, y, life));
		}
		remove();

	}
}
