package UI;

import Game.Screen;
import Graphics.Font;

public class DamageBattleText extends BattleText{

	int timer = 0;
	
	public DamageBattleText(int x, int y, String s){
		super(x,y,s);
	}
	
	public void update(){
		timer++;
		if(timer % 1 == 0){
			y--;

		}
		if(timer >= 60) remove();
		
	}
	
	public void render(Screen screen){
		Font.renderBTSM(s, screen, x, y);
	}

}
