package Entity.PowerUp;

import Game.Screen;
import Graphics.Sprite;

public class FlyingPowerUp extends PowerUp {

	Sprite sprite;

	public FlyingPowerUp(int x, int y) {
		super(x, y);
		sprite = Sprite.wings;
	}
	
	public void update(){
		if (unitCollision(level.player.x,level.player.y)){
			level.player.unlockFlying();
			remove();
		}
	}

	public boolean unitCollision(int nx, int ny) {
		boolean hit = false;
		//if (mobRight >= playerStartX && mobStartX <= playerStartX + playerSizeX && playerStartY + playerSizeY >= mobStartY && playerStartY <= mobBottom && invulnFrame == invulnFrames) {
		if (nx >= x && nx <= x + 64 && ny >= y && ny <= y + 64) {
			hit = true;
		}
		return hit;
	}
	
	public void render(Screen screen){
		screen.renderSprite(x, y, sprite, true);
	}

}
