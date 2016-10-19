package WorldMap;
import Game.Screen;
import Graphics.Sprite;
import Input.Keyboard;

public class WMPlayer {
	public int x;
	public int y;
	private Sprite sprite;
	private Keyboard input;
	private int dir = 0;
	public boolean inputFlag = false;
	public boolean moving = false;
	public int inputDelay = 0;
	private int walkTimer = 0;
	private boolean INPUT_UP, INPUT_DOWN, INPUT_RIGHT, INPUT_LEFT;

	public WMPlayer(int x, int y) {
		this.x = x;
		this.y = y;
		this.sprite = Sprite.voidSprite;
		this.input = input;
	}
	
	public void setInput(Keyboard key){
		this.input = key;
	}

	public void update() {
		INPUT_UP = false;
		INPUT_DOWN = false;
		INPUT_LEFT = false;
		INPUT_RIGHT = false;
		


		if (input.up) INPUT_UP = true;
		if (input.down) INPUT_DOWN = true;
		if (input.left) INPUT_LEFT = true;
		if (input.right) INPUT_RIGHT = true;

		if (WorldMap.battleEvent == false) {
			if ((INPUT_UP || INPUT_DOWN || INPUT_LEFT || INPUT_RIGHT) && !moving) {
				if (inputFlag) {
					if (INPUT_UP) {
						if (!WorldMap.getTile(x / 32, y / 32 - 1).solid()) {
							moving = true;

						}
						dir = 2;
					} else if (INPUT_DOWN) {
						if (!WorldMap.getTile(x / 32, y / 32 + 1).solid()) {
							moving = true;

						}
						dir = 0;
					} else if (INPUT_RIGHT) {
						if (!WorldMap.getTile(x / 32 + 1, y / 32).solid()) {
							moving = true;

						}
						dir = 1;
					} else if (INPUT_LEFT) {
						if (!WorldMap.getTile(x / 32 - 1, y / 32).solid()) {
							moving = true;

						}
						dir = 3;
					}
					walkTimer = 0;
					inputFlag = false;
				} else {
					moving = false;
					inputDelay++;
					if (inputDelay > 15) {
						inputFlag = true;
						inputDelay = 5;
					}
				}
			} else if (walkTimer > 8) {
				inputFlag = true;
				inputDelay = 0;
				moving = false;
			}

			//	x = 180 * 32;
			//	y = 24 * 32;
		}
		if (moving) {
			if (dir == 2) {
				if (walkTimer < 8) {
					y -= 4;
				}
				walkTimer++;
			}
			if (dir == 0) {
				if (walkTimer < 8) {
					y += 4;
				}
				walkTimer++;
			}
			if (dir == 3) {
				if (walkTimer < 8) {
					x -= 4;
				}
				walkTimer++;
			}
			if (dir == 1) {
				if (walkTimer < 8) {
					x += 4;
				}
				walkTimer++;
			}
		}

		if (dir == 0) {
			if (!moving) sprite = Sprite.playerWMDown2;
			else if (walkTimer % 2 == 0) {
				sprite = Sprite.playerWMDown3;
			} else sprite = Sprite.playerWMDown1;
		} else if (dir == 1) {
			if (!moving) sprite = Sprite.playerWMRight2;
			else if (walkTimer % 2 == 0) {
				sprite = Sprite.playerWMRight3;
			} else sprite = Sprite.playerWMRight1;
		} else if (dir == 2) {
			if (!moving) sprite = Sprite.playerWMUp2;
			else if (walkTimer % 2 == 0) {
				sprite = Sprite.playerWMUp3;
			} else sprite = Sprite.playerWMUp1;
		} else if (dir == 3) {
			if (!moving) sprite = Sprite.playerWMLeft2;
			else if (walkTimer % 2 == 0) {
				sprite = Sprite.playerWMLeft1;
			} else sprite = Sprite.playerWMLeft3;
		}
	}

	public void render(Screen screen) {
		screen.renderSprite(x + 4, y, sprite, true);
	}
}
