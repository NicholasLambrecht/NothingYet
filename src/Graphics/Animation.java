package Graphics;

public class Animation {

	public static Sprite[] cloud = new Sprite[26];
	public static Sprite[] playerMoveRight = new Sprite[34];
	public static Sprite[] playerMoveLeft = new Sprite[34];
	public static Sprite[] playerIdleRight = new Sprite[7];
	public static Sprite[] playerIdleLeft = new Sprite[7];
	public static Sprite[] playerStopRight = new Sprite[13];
	public static Sprite[] playerStopLeft = new Sprite[13];
	public static Sprite[] playerCrouchLeft = new Sprite[17];
	public static Sprite[] playerCrouchRight = new Sprite[17];
	public static Sprite[] playerAttackLeft = new Sprite[4];
	public static Sprite[] playerAttackRight = new Sprite[4];
	public static Sprite[] playerCattackLeft = new Sprite[4];
	public static Sprite[] playerCattackRight = new Sprite[4];
	public static Sprite[] playerJumpLeft = new Sprite[10];
	public static Sprite[] playerJumpRight = new Sprite[10];
	public static Sprite[] playerFallLeft = new Sprite[9];
	public static Sprite[] playerFallRight = new Sprite[9];
	public static Sprite[] playerLandLeft = new Sprite[5];
	public static Sprite[] playerLandRight = new Sprite[5];
	public static Sprite[] playerDodgeLeft = new Sprite[12];
	public static Sprite[] playerDodgeRight = new Sprite[12];
	public static Sprite[] playerSwordAtkLeft = new Sprite[11];
	public static Sprite[] playerSwordAtkRight = new Sprite[11];
	public static Sprite[] playerSwordAtkJumpLeft = new Sprite[9];
	public static Sprite[] playerSwordAtkJumpRight = new Sprite[9];

	public Animation() {
		for (int i = 0; i < 11; i++) {
			playerSwordAtkRight[i] = Sprite.playerSprites.get((2 * i) + 304);
			playerSwordAtkLeft[i] = new Sprite(playerSwordAtkRight[i]); 
		}
		for (int i = 0; i < 9; i++) {
			playerSwordAtkJumpRight[i] = Sprite.playerSprites.get((2 * i) + 368);
			playerSwordAtkJumpLeft[i] = new Sprite(playerSwordAtkRight[i]); 
		}
		for (int i = 0; i < 34; i++) {
			playerMoveRight[i] = Sprite.playerSprites.get((2 * i) + 48);
		}
		for (int i = 0; i < 34; i++) {
			playerMoveLeft[i] = Sprite.playerSprites.get((2 * i) + 49);
		}
		for (int i = 0; i < 7; i++) {
			playerIdleRight[i] = Sprite.playerSprites.get((2 * i));
		}
		for (int i = 0; i < 7; i++) {
			playerIdleLeft[i] = Sprite.playerSprites.get((2 * i) + 1);
		}
		for (int i = 0; i < 17; i++) {
			playerCrouchRight[i] = Sprite.playerSprites.get((2 * i) + 128);
		}
		for (int i = 0; i < 17; i++) {
			playerCrouchLeft[i] = Sprite.playerSprites.get((2 * i) + 129);
		}
		for (int i = 0; i < 13; i++) {
			playerStopRight[i] = Sprite.playerSprites.get((2 * i) + 176);
		}
		for (int i = 0; i < 13; i++) {
			playerStopLeft[i] = Sprite.playerSprites.get((2 * i) + 177);
		}
		for (int i = 0; i < 4; i++) {
			playerAttackRight[i] = Sprite.playerSprites.get((2 * i) + 448);
		}
		for (int i = 0; i < 4; i++) {
			playerAttackLeft[i] = Sprite.playerSprites.get((2 * i) + 449);
		}
		for (int i = 0; i < 4; i++) {
			playerCattackRight[i] = Sprite.playerSprites.get((2 * i) + 456);
		}
		for (int i = 0; i < 4; i++) {
			playerCattackLeft[i] = Sprite.playerSprites.get((2 * i) + 457);
		}
		for (int i = 0; i < 10; i++) {
			playerJumpRight[i] = Sprite.playerSprites.get((2 * i) + 208);
		}
		for (int i = 0; i < 10; i++) {
			playerJumpLeft[i] = Sprite.playerSprites.get((2 * i) + 209);
		}
		for (int i = 0; i < 9; i++) {
			playerFallRight[i] = Sprite.playerSprites.get((2 * i) + 272);
		}
		for (int i = 0; i < 9; i++) {
			playerFallLeft[i] = Sprite.playerSprites.get((2 * i) + 273);
		}
		for (int i = 0; i < 5; i++) {
			playerLandRight[i] = Sprite.playerSprites.get((2 * i) + 290);
		}
		for (int i = 0; i < 5; i++) {
			playerLandLeft[i] = Sprite.playerSprites.get((2 * i) + 291);
		}

		for (int i = 0; i < 11; i++) {
			playerDodgeRight[i] = new Sprite(SpriteSheet.PlayerDodgeSprites, 64, 64, i % 6, i / 6);
			playerDodgeLeft[i] = new Sprite(playerDodgeRight[i]);
		}

	}
}