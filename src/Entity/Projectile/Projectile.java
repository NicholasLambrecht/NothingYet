package Entity.Projectile;

import Entity.Entity;
import Entity.Mob.Mob;
import Graphics.Sprite;

public abstract class Projectile extends Entity {

	protected final int xOrigin, yOrigin;
	public double angle;
	public Sprite sprite;
	public double x;
	public double y;
	protected double nx, ny;
	protected double speed, range;
	public int damage;
	protected double distance;
	protected boolean resolved;
	public int knockback;
	public boolean e = false;

	public Projectile(int x, int y, double direction) {
		xOrigin = x;
		yOrigin = y;
		angle = direction;
		this.x = x;
		this.y = y;
	}

	public void hit() {
		remove();

	}

	public boolean mobCollision(int nx, int ny) {
		boolean hit = false;
		if (nx >= x && nx <= x + 64 && ny >= y && ny <= y + 64) {
			hit = true;
		}
		return hit;
	}

	public void effect(Mob mob) {
		// TODO Auto-generated method stub
		
	}
}
