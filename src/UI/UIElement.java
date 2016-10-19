package UI;

import Game.Screen;
import Graphics.Sprite;


public abstract class UIElement {
	protected int xa;
	protected int ya;
	Sprite sprite;
	
	public UIElement (int x, int y, Sprite sprite){
		xa = x; 
		ya = y; 
		this.sprite = sprite;
	}
	
	public void update() {
		
	}
	
	public void render(Screen screen){
		screen.renderSprite(xa, ya, sprite, true);
	}
}
