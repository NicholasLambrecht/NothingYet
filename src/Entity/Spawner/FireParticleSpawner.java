package Entity.Spawner;

import Entity.Particle.RFireParticle;
import Entity.Particle.YFireParticle;
import Level.Level;

import java.util.Random;

public class FireParticleSpawner extends Spawner {

	public FireParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		Random rand = new Random();
		for (int i = 0; i < amount; i++) {
			int gr = rand.nextInt(4);
			//if(gr == 0){
				level.add(new YFireParticle(x, y, life));
			//} else level.add(new RFireParticle(x, y, life));
		}
		remove();

	}
}
