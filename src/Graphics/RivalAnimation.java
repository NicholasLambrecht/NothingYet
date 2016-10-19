package Graphics;

public class RivalAnimation {

	public static Sprite[] cloud = new Sprite[26];
	public static Sprite[] rivalMoveRight = new Sprite[34];
	public static Sprite[] rivalMoveLeft = new Sprite[34];
	public static Sprite[] rivalIdleRight = new Sprite[7];
	public static Sprite[] rivalIdleLeft = new Sprite[7];
	public static Sprite[] rivalStopRight = new Sprite[13];
	public static Sprite[] rivalStopLeft = new Sprite[13];
	public static Sprite[] rivalCrouchLeft = new Sprite[17];
	public static Sprite[] rivalCrouchRight = new Sprite[17];
	public static Sprite[] rivalAttackLeft = new Sprite[4];
	public static Sprite[] rivalAttackRight = new Sprite[4];
	public static Sprite[] rivalCattackLeft = new Sprite[4];
	public static Sprite[] rivalCattackRight = new Sprite[4];
	public static Sprite[] rivalJumpLeft = new Sprite[10];
	public static Sprite[] rivalJumpRight = new Sprite[10];
	public static Sprite[] rivalFallLeft = new Sprite[9];
	public static Sprite[] rivalFallRight = new Sprite[9];
	public static Sprite[] rivalLandLeft = new Sprite[5];
	public static Sprite[] rivalLandRight = new Sprite[5];
	public static Sprite[] rivalDodgeLeft = new Sprite[12];
	public static Sprite[] rivalDodgeRight = new Sprite[12];
	public static Sprite[] rivalSwordAtkLeft = new Sprite[11];
	public static Sprite[] rivalSwordAtkRight = new Sprite[11];
	public static Sprite[] rivalSwordAtkJumpLeft = new Sprite[9];
	public static Sprite[] rivalSwordAtkJumpRight = new Sprite[9];

	public RivalAnimation() {
		for (int i = 0; i < 11; i++) {
			rivalSwordAtkRight[i] = Sprite.rivalSprites.get((2 * i) + 304);
			rivalSwordAtkLeft[i] = new Sprite(rivalSwordAtkRight[i]); 
		}
		for (int i = 0; i < 9; i++) {
			rivalSwordAtkJumpRight[i] = Sprite.rivalSprites.get((2 * i) + 368);
			rivalSwordAtkJumpLeft[i] = new Sprite(rivalSwordAtkRight[i]); 
		}
		for (int i = 0; i < 34; i++) {
			rivalMoveRight[i] = Sprite.rivalSprites.get((2 * i) + 48);
		}
		for (int i = 0; i < 34; i++) {
			rivalMoveLeft[i] = Sprite.rivalSprites.get((2 * i) + 49);
		}
		for (int i = 0; i < 7; i++) {
			rivalIdleRight[i] = Sprite.rivalSprites.get((2 * i));
		}
		for (int i = 0; i < 7; i++) {
			rivalIdleLeft[i] = Sprite.rivalSprites.get((2 * i) + 1);
		}
		for (int i = 0; i < 17; i++) {
			rivalCrouchRight[i] = Sprite.rivalSprites.get((2 * i) + 128);
		}
		for (int i = 0; i < 17; i++) {
			rivalCrouchLeft[i] = Sprite.rivalSprites.get((2 * i) + 129);
		}
		for (int i = 0; i < 13; i++) {
			rivalStopRight[i] = Sprite.rivalSprites.get((2 * i) + 176);
		}
		for (int i = 0; i < 13; i++) {
			rivalStopLeft[i] = Sprite.rivalSprites.get((2 * i) + 177);
		}
		for (int i = 0; i < 4; i++) {
			rivalAttackRight[i] = Sprite.rivalSprites.get((2 * i) + 448);
		}
		for (int i = 0; i < 4; i++) {
			rivalAttackLeft[i] = Sprite.rivalSprites.get((2 * i) + 449);
		}
		for (int i = 0; i < 4; i++) {
			rivalCattackRight[i] = Sprite.rivalSprites.get((2 * i) + 456);
		}
		for (int i = 0; i < 4; i++) {
			rivalCattackLeft[i] = Sprite.rivalSprites.get((2 * i) + 457);
		}
		for (int i = 0; i < 10; i++) {
			rivalJumpRight[i] = Sprite.rivalSprites.get((2 * i) + 208);
		}
		for (int i = 0; i < 10; i++) {
			rivalJumpLeft[i] = Sprite.rivalSprites.get((2 * i) + 209);
		}
		for (int i = 0; i < 9; i++) {
			rivalFallRight[i] = Sprite.rivalSprites.get((2 * i) + 272);
		}
		for (int i = 0; i < 9; i++) {
			rivalFallLeft[i] = Sprite.rivalSprites.get((2 * i) + 273);
		}
		for (int i = 0; i < 5; i++) {
			rivalLandRight[i] = Sprite.rivalSprites.get((2 * i) + 290);
		}
		for (int i = 0; i < 5; i++) {
			rivalLandLeft[i] = Sprite.rivalSprites.get((2 * i) + 291);
		}

//		for (int i = 0; i < 11; i++) {
//			rivalDodgeRight[i] = new Sprite(SpriteSheet.rivalDodgeSprites, 64, 64, i % 6, i / 6);
//			rivalDodgeLeft[i] = new Sprite(rivalDodgeRight[i]);
//		}

	}
}