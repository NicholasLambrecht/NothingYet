package UI;

import Game.Screen;
import Graphics.Font;

public class BattleText {
	
	int x, y;
	String s;
	boolean removed = false;
	int timer = 0;
	
	public BattleText(int x, int y, String s){
		this.x = x;
		this.y = y;
		this.s = s;
	}
	
	public void update(){
		timer++;
		if(timer % 3 == 0){
			y--;
		}
		if(timer >= 150) remove();
	}
	
	public void render(Screen screen){
		Font.renderBT(s, screen, x, y);
	}

	public boolean isRemoved() {
		return removed;
	}
	
	public void remove(){
		removed = true;
	}

}
