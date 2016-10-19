package Entity.PowerUp;

import Entity.Entity;
import Game.Screen;
import Graphics.Sprite;

public class PowerUp extends Entity{
	
	Sprite sprite;
	
	public PowerUp(int x, int y){
		this.x = x;
		this.y = y;
	}

	public boolean unitCollision(int nx, int ny) {
		boolean hit = false;
		//if (mobRight >= playerStartX && mobStartX <= playerStartX + playerSizeX && playerStartY + playerSizeY >= mobStartY && playerStartY <= mobBottom && invulnFrame == invulnFrames) {
		if (nx >= x && nx <= x + 64 && ny >= y && ny <= y + 64) {
			hit = true;
		}
		return hit;
	}
	
	public void update(){
		if (unitCollision(level.player.x,level.player.y)){
			level.player.unlockFlying();
			remove();
		}
	}
	
	public void render(Screen screen){
		screen.renderSprite(x, y, sprite, true);
	}
	
	
	
}
