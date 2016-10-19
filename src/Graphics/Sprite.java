package Graphics;

import java.util.ArrayList;
import java.util.List;

public class Sprite {

	private int width;
	private int height;
	private int x, y;
	public int[] pixels;
	protected SpriteSheet sheet;

	public static Sprite GreenBlock = new Sprite(64, 0x00FB00);
	public static Sprite RedBlock = new Sprite(64, 0xFB0000);

	public static Sprite options = new Sprite(SpriteSheet.options);

	public static Sprite background = new Sprite(SpriteSheet.background, 1920, 1080, 0, 0);

	public static Sprite smhpb = new Sprite(SpriteSheet.smhpb, 64, 6, 0, 0);
	public static Sprite smhpbfill = new Sprite(SpriteSheet.smhpbfill, 1, 4, 0, 0);

	public static Sprite portrait = new Sprite(SpriteSheet.portrait, 250, 78, 0, 0);
	public static Sprite hpFill = new Sprite(SpriteSheet.barFills, 1, 17, 0, 0);
	public static Sprite manaFill = new Sprite(SpriteSheet.barFills, 1, 12, 1, 0);
	public static Sprite specialFill = new Sprite(SpriteSheet.barFills, 1, 4, 2, 0);
	public static Sprite expFill = new Sprite(SpriteSheet.barFills, 1, 7, 3, 0);

	public static Sprite playerFly1 = new Sprite(SpriteSheet.playerFlying, 64, 64, 0, 0);
	public static Sprite playerFly2 = new Sprite(SpriteSheet.playerFlying, 64, 128, 1, 0);
	public static Sprite playerFly3 = new Sprite(SpriteSheet.playerFlying, 64, 64, 2, 0);

	public static Sprite playerLeftFly1 = new Sprite(playerFly1);
	public static Sprite playerLeftFly2 = new Sprite(playerFly2);
	public static Sprite playerLeftFly3 = new Sprite(playerFly3);

	public static Sprite playerFlyDodge1 = new Sprite(SpriteSheet.PlayerAirDodge, 64, 64, 0, 0);
	public static Sprite playerFlyDodge2 = new Sprite(SpriteSheet.PlayerAirDodge, 64, 64, 1, 0);
	public static Sprite playerFlyDodge3 = new Sprite(SpriteSheet.PlayerAirDodge, 64, 64, 2, 0);

	public static Sprite playerFlyDogeLeft1 = new Sprite(playerFlyDodge1);
	public static Sprite playerFlyDogeLeft2 = new Sprite(playerFlyDodge2);
	public static Sprite playerFlyDogeLeft3 = new Sprite(playerFlyDodge3);

	public static Sprite playerShooting1 = new Sprite(SpriteSheet.PlayerShooting, 64, 64, 0, 0);
	public static Sprite playerShooting2 = new Sprite(SpriteSheet.PlayerShooting, 64, 64, 1, 0);

	public static Sprite playerShooting3h = new Sprite(SpriteSheet.PlayerShooting, 64, 64, 2, 0);
	public static Sprite playerShooting4h = new Sprite(SpriteSheet.PlayerShooting, 64, 64, 3, 0);

	public static Sprite playerShooting3u = new Sprite(SpriteSheet.PlayerShooting, 64, 64, 0, 1);
	public static Sprite playerShooting4u = new Sprite(SpriteSheet.PlayerShooting, 64, 64, 1, 1);

	public static Sprite playerShooting3d = new Sprite(SpriteSheet.PlayerShooting, 64, 64, 2, 1);
	public static Sprite playerShooting4d = new Sprite(SpriteSheet.PlayerShooting, 64, 64, 3, 1);

	public static Sprite playerShooting1l = new Sprite(playerShooting1);
	public static Sprite playerShooting2l = new Sprite(playerShooting2);

	public static Sprite playerShooting3hl = new Sprite(playerShooting3h);
	public static Sprite playerShooting4hl = new Sprite(playerShooting4h);

	public static Sprite playerShooting3ul = new Sprite(playerShooting3u);
	public static Sprite playerShooting4ul = new Sprite(playerShooting4u);

	public static Sprite playerShooting3dl = new Sprite(playerShooting3d);
	public static Sprite playerShooting4dl = new Sprite(playerShooting4d);

	public static Sprite tutorial = new Sprite(SpriteSheet.tutorial, 1280, 720, 0, 0);

	public static Sprite regBar = new Sprite(SpriteSheet.UIBars, 102, 15, 0, 0);
	public static Sprite incrementBar = new Sprite(SpriteSheet.UIBars, 102, 15, 0, 1);
	public static Sprite regFill = new Sprite(SpriteSheet.barFill15px, 1, 15, 0, 0);
	public static Sprite incrementFill = new Sprite(SpriteSheet.barFill13px, 1, 13, 0, 0);

	public static Sprite mob1 = new Sprite(SpriteSheet.mob1, 64, 64, 0, 0);
	public static Sprite mob1r = new Sprite(mob1);

	public static Sprite kingThoctar = new Sprite(SpriteSheet.KingThoctar, 128, 128, 0, 0);
	public static Sprite kingThactarr = new Sprite(kingThoctar);

	public static Sprite Fireball = new Sprite(SpriteSheet.tiles, 16, 16, 0, 0);
	public static Sprite Fireball2 = new Sprite(Fireball);
	public static Sprite particle_normal = new Sprite(1, 0xFF0000);
	public static Sprite particle_fire = new Sprite(1, 0xFF0000);
	public static Sprite particle_normaly = new Sprite(1, 0xFFFF00);
	public static Sprite particle_normalg = new Sprite(1, 0x00FF00);
	public static Sprite particle_normalb = new Sprite(1, 0x0da0e5);
	public static Sprite particle_smy = new Sprite(1, 0xffff00);
	public static Sprite particle_boulder = new Sprite(1, 0xc4c4c4);

	public static Sprite particle_blood = new Sprite(2, 0xFF0000);
	public static Sprite particle_chill = new Sprite(2, 0xa1f9ff);
	public static Sprite particle_arrow = new Sprite(3, 0xaaaaaa);
	public static Sprite particle_arrowshaft = new Sprite(3, 0x8A6A00);

	public static Sprite heart = new Sprite(SpriteSheet.Heart);

	public static Sprite Pot1 = new Sprite(SpriteSheet.Misc, 32, 32, 10, 3);
	public static Sprite Pot2 = new Sprite(SpriteSheet.Misc, 32, 32, 11, 3);
	// public static Sprite sword1 = new Sprite(SpriteSheet.swords, 34, 21, 0,
	// 0);
	// public static Sprite sword2 = new Sprite(SpriteSheet.swords, 41, 21, 0,
	// 1);
	public static Sprite Abutton = new Sprite(SpriteSheet.buttons, 28, 28, 0, 1);
	public static Sprite Bbutton = new Sprite(SpriteSheet.buttons, 28, 28, 1, 1);
	public static Sprite Xbutton = new Sprite(SpriteSheet.buttons, 28, 28, 0, 0);
	public static Sprite Ybutton = new Sprite(SpriteSheet.buttons, 28, 28, 1, 0);

	public static Sprite optionsFS = new Sprite(SpriteSheet.optionsFS);

	public static List<Sprite> playerSprites = new ArrayList<Sprite>();
	public static List<Sprite> rivalSprites = new ArrayList<Sprite>();

	public static Sprite swordAtk = new Sprite(SpriteSheet.playerSword, 103, 32, 0, 0);
	public static Sprite swordAtkL = new Sprite(swordAtk);

	public static Sprite sWorldGrass = new Sprite(SpriteSheet.WorldMapTiles, 32, 32, 1, 1);
	public static Sprite sWorldDirt = new Sprite(SpriteSheet.WorldMapTiles, 32, 32, 1, 2);

	public static Sprite sWall = new Sprite(SpriteSheet.walls, 32, 32, 0, 0);
	public static Sprite sGrass = new Sprite(SpriteSheet.walls, 32, 32, 0, 1);
	public static Sprite sDirt = new Sprite(SpriteSheet.walls, 32, 32, 0, 2);
	public static Sprite sRock = new Sprite(SpriteSheet.walls, 32, 32, 0, 3);

	public static Sprite sWallDark = new Sprite(SpriteSheet.walls, 32, 32, 1, 0);
	public static Sprite sGrassDark = new Sprite(SpriteSheet.walls, 32, 32, 1, 1);
	public static Sprite sDirtDark = new Sprite(SpriteSheet.walls, 32, 32, 1, 2);
	public static Sprite sRockDark = new Sprite(SpriteSheet.walls, 32, 32, 1, 3);

	public static Sprite rWall = new Sprite(SpriteSheet.walls, 32, 32, 2, 0);
	public static Sprite rWallDark = new Sprite(SpriteSheet.walls, 32, 32, 3, 0);
	public static Sprite rWallx = new Sprite(SpriteSheet.walls, 32, 32, 2, 1);

	public static Sprite mGrass = new Sprite(SpriteSheet.walls, 32, 32, 4, 1);
	public static Sprite mRockDark = new Sprite(SpriteSheet.walls, 32, 32, 4, 0);
	public static Sprite mDirt = new Sprite(SpriteSheet.walls, 32, 32, 4, 2);
	public static Sprite mRock = new Sprite(SpriteSheet.walls, 32, 32, 4, 3);
	public static Sprite mRockWall = new Sprite(SpriteSheet.walls, 32, 32, 5, 0);

	public static Sprite sWallSlope = new Sprite(SpriteSheet.walls, 32, 32, 2, 2);
	public static Sprite sWallSlopeR = new Sprite(sWallSlope);

	public static Sprite sGrassSlope = new Sprite(SpriteSheet.walls, 32, 32, 6, 1);
	public static Sprite sGrassSloper = new Sprite(sGrassSlope);

	public static Sprite sGrassSlopeB = new Sprite(SpriteSheet.walls, 32, 32, 6, 2);
	public static Sprite sGrassSlopeBr = new Sprite(sGrassSlopeB);

	public static Sprite sWater = new Sprite(SpriteSheet.walls, 32, 32, 2, 3);

	public static Sprite wings = new Sprite(SpriteSheet.wings, 64, 64, 0, 0);

	public static Sprite bossHPBar = new Sprite(SpriteSheet.BossHPBar, 880, 22, 0, 0);
	public static Sprite bossFill = new Sprite(SpriteSheet.bossFill, 1, 16, 0, 0);

	public static Sprite voidSprite = new Sprite(32, 0x222222);

	public static Sprite MainMenu = new Sprite(SpriteSheet.MainMenu);

	public static Sprite diffMenu = new Sprite(SpriteSheet.diffMenu);

	public static Sprite cursor = new Sprite(SpriteSheet.cursor);

	public static Sprite bat1 = new Sprite(SpriteSheet.bat, 64, 64, 0, 0);
	public static Sprite bat2 = new Sprite(SpriteSheet.bat, 64, 64, 1, 0);
	public static Sprite bat3 = new Sprite(SpriteSheet.bat, 64, 64, 2, 0);
	public static Sprite bat4 = new Sprite(SpriteSheet.bat, 64, 64, 3, 0);
	public static Sprite bat5 = new Sprite(SpriteSheet.bat, 64, 64, 4, 0);
	public static Sprite bat6 = new Sprite(SpriteSheet.bat, 64, 64, 5, 0);

	public static Sprite bat1r = new Sprite(bat1);
	public static Sprite bat2r = new Sprite(bat2);
	public static Sprite bat3r = new Sprite(bat3);
	public static Sprite bat4r = new Sprite(bat4);
	public static Sprite bat5r = new Sprite(bat5);
	public static Sprite bat6r = new Sprite(bat6);

	public static Sprite cerberusWalk1 = new Sprite(SpriteSheet.cerberus, 216, 128, 0, 0);
	public static Sprite cerberusWalk2 = new Sprite(SpriteSheet.cerberus, 216, 128, 1, 0);
	public static Sprite cerberusWalk3 = new Sprite(SpriteSheet.cerberus, 216, 128, 2, 0);

	public static Sprite cerberusWindUp1 = new Sprite(SpriteSheet.cerberus, 216, 128, 2, 0);
	public static Sprite cerberusWindUp2 = new Sprite(SpriteSheet.cerberus, 216, 128, 3, 0);
	public static Sprite cerberusWindUp3 = new Sprite(SpriteSheet.cerberus, 216, 128, 0, 1);

	public static Sprite cerberusLeap1 = new Sprite(SpriteSheet.cerberus, 216, 128, 1, 1);
	public static Sprite cerberusCollide1 = new Sprite(SpriteSheet.cerberus, 216, 128, 2, 1);
	public static Sprite cerberusCollide2 = new Sprite(SpriteSheet.cerberus, 216, 128, 3, 1);

	public static Sprite cerberusStun1 = new Sprite(SpriteSheet.cerberus, 216, 128, 0, 2);
	public static Sprite cerberusStun2 = new Sprite(SpriteSheet.cerberus, 216, 128, 1, 2);
	public static Sprite cerberusStun3 = new Sprite(SpriteSheet.cerberus, 216, 128, 2, 2);

	public static Sprite cerberusWalk1r = new Sprite(cerberusWalk1);
	public static Sprite cerberusWalk2r = new Sprite(cerberusWalk2);
	public static Sprite cerberusWalk3r = new Sprite(cerberusWalk3);

	public static Sprite cerberusWindUp1r = new Sprite(cerberusWindUp1);
	public static Sprite cerberusWindUp2r = new Sprite(cerberusWindUp2);
	public static Sprite cerberusWindUp3r = new Sprite(cerberusWindUp3);

	public static Sprite cerberusStun1r = new Sprite(cerberusStun1);
	public static Sprite cerberusStun2r = new Sprite(cerberusStun2);
	public static Sprite cerberusStun3r = new Sprite(cerberusStun3);

	public static Sprite cerberusLeap1r = new Sprite(cerberusLeap1);
	public static Sprite cerberusCollide1r = new Sprite(cerberusCollide1);
	public static Sprite cerberusCollide2r = new Sprite(cerberusCollide2);

	public static Sprite lantern = new Sprite(SpriteSheet.decor, 32, 32, 0, 0);

	public static Sprite sword1 = new Sprite(SpriteSheet.sword1);
	public static Sprite sword2 = new Sprite(SpriteSheet.sword2);
	public static Sprite sword3 = new Sprite(SpriteSheet.sword3);
	public static Sprite sword4 = new Sprite(SpriteSheet.sword4);
	public static Sprite sword5 = new Sprite(SpriteSheet.sword5);
	public static Sprite sword6 = new Sprite(SpriteSheet.sword6);
	public static Sprite sword7 = new Sprite(SpriteSheet.sword7);

	public static Sprite sword1l = new Sprite(sword1);
	public static Sprite sword2l = new Sprite(sword2);
	public static Sprite sword3l = new Sprite(sword3);
	public static Sprite sword4l = new Sprite(sword4);
	public static Sprite sword5l = new Sprite(sword5);
	public static Sprite sword6l = new Sprite(sword6);
	public static Sprite sword7l = new Sprite(sword7);

	public static Sprite boulder1 = new Sprite(SpriteSheet.boulder, 64, 64, 0, 0);
	public static Sprite boulder2 = new Sprite(SpriteSheet.boulder, 64, 64, 1, 0);
	public static Sprite boulder3 = new Sprite(SpriteSheet.boulder, 64, 64, 2, 0);
	public static Sprite boulder4 = new Sprite(SpriteSheet.boulder, 64, 64, 3, 0);
	public static Sprite boulder5 = new Sprite(SpriteSheet.boulder, 64, 64, 0, 1);
	public static Sprite boulder6 = new Sprite(SpriteSheet.boulder, 64, 64, 1, 1);
	public static Sprite boulder7 = new Sprite(SpriteSheet.boulder, 64, 64, 2, 1);
	public static Sprite boulder8 = new Sprite(SpriteSheet.boulder, 64, 64, 3, 1);
	public static Sprite boulder9 = new Sprite(SpriteSheet.boulder, 64, 64, 0, 2);
	public static Sprite boulder10 = new Sprite(SpriteSheet.boulder, 64, 64, 1, 2);
	public static Sprite boulder11 = new Sprite(SpriteSheet.boulder, 64, 64, 2, 2);
	public static Sprite boulder12 = new Sprite(SpriteSheet.boulder, 64, 64, 3, 2);
	public static Sprite boulder13 = new Sprite(SpriteSheet.boulder, 64, 64, 0, 3);
	public static Sprite boulder14 = new Sprite(SpriteSheet.boulder, 64, 64, 1, 3);
	public static Sprite boulder15 = new Sprite(SpriteSheet.boulder, 64, 64, 2, 3);
	public static Sprite boulder16 = new Sprite(SpriteSheet.boulder, 64, 64, 3, 3);

	public static Sprite golemWalk1 = new Sprite(SpriteSheet.Golem, 167, 167, 0, 0);
	public static Sprite golemWalk2 = new Sprite(SpriteSheet.Golem, 167, 167, 1, 0);
	public static Sprite golemWalk3 = new Sprite(SpriteSheet.Golem, 167, 167, 2, 0);
	public static Sprite golemWalk4 = new Sprite(SpriteSheet.Golem, 167, 167, 3, 0);
	public static Sprite golemWalk5 = new Sprite(SpriteSheet.Golem, 167, 167, 4, 0);

	public static Sprite golemSlam1 = new Sprite(SpriteSheet.Golem, 167, 167, 0, 1);
	public static Sprite golemSlam2 = new Sprite(SpriteSheet.Golem, 167, 167, 1, 1);
	public static Sprite golemSlam3 = new Sprite(SpriteSheet.Golem, 167, 167, 2, 1);
	public static Sprite golemSlam4 = new Sprite(SpriteSheet.Golem, 167, 167, 3, 1);
	public static Sprite golemSlam5 = new Sprite(SpriteSheet.Golem, 167, 167, 4, 1);
	public static Sprite golemSlam6 = new Sprite(SpriteSheet.Golem, 167, 167, 5, 1);
	public static Sprite golemSlam7 = new Sprite(SpriteSheet.Golem, 167, 167, 6, 1);

	public static Sprite golemPunch1 = new Sprite(SpriteSheet.Golem, 167, 167, 0, 2);
	public static Sprite golemPunch2 = new Sprite(SpriteSheet.Golem, 167, 167, 1, 2);
	public static Sprite golemPunch3 = new Sprite(SpriteSheet.Golem, 167, 167, 2, 2);
	public static Sprite golemPunch4 = new Sprite(SpriteSheet.Golem, 167, 167, 3, 2);
	public static Sprite golemPunch5 = new Sprite(SpriteSheet.Golem, 167, 167, 4, 2);

	public static Sprite golemClimb1 = new Sprite(SpriteSheet.Golem, 167, 167, 0, 3);
	public static Sprite golemClimb2 = new Sprite(SpriteSheet.Golem, 167, 167, 1, 3);
	public static Sprite golemClimb3 = new Sprite(SpriteSheet.Golem, 167, 167, 2, 3);
	public static Sprite golemClimb4 = new Sprite(SpriteSheet.Golem, 167, 167, 3, 3);

	public static Sprite golemThrow1 = new Sprite(SpriteSheet.Golem, 167, 167, 0, 4);
	public static Sprite golemThrow2 = new Sprite(SpriteSheet.Golem, 167, 167, 1, 4);
	public static Sprite golemThrow3 = new Sprite(SpriteSheet.Golem, 167, 167, 2, 4);

	public static Sprite golemThrow6 = new Sprite(SpriteSheet.Golem, 167, 167, 3, 4);

	public static Sprite golemThrow4 = new Sprite(SpriteSheet.GolemThrow, 95, 195, 0, 0);
	public static Sprite golemThrow5 = new Sprite(SpriteSheet.GolemThrow, 127, 195, 1, 0);

	public static Sprite golemWalk1r = new Sprite(golemWalk1);
	public static Sprite golemWalk2r = new Sprite(golemWalk2);
	public static Sprite golemWalk3r = new Sprite(golemWalk3);
	public static Sprite golemWalk4r = new Sprite(golemWalk4);
	public static Sprite golemWalk5r = new Sprite(golemWalk5);

	public static Sprite golemSlam1r = new Sprite(golemSlam1);
	public static Sprite golemSlam2r = new Sprite(golemSlam2);
	public static Sprite golemSlam3r = new Sprite(golemSlam3);
	public static Sprite golemSlam4r = new Sprite(golemSlam4);
	public static Sprite golemSlam5r = new Sprite(golemSlam5);
	public static Sprite golemSlam6r = new Sprite(golemSlam6);
	public static Sprite golemSlam7r = new Sprite(golemSlam7);

	public static Sprite golemPunch1r = new Sprite(golemPunch1);
	public static Sprite golemPunch2r = new Sprite(golemPunch2);
	public static Sprite golemPunch3r = new Sprite(golemPunch3);
	public static Sprite golemPunch4r = new Sprite(golemPunch4);
	public static Sprite golemPunch5r = new Sprite(golemPunch5);

	public static Sprite golemClimb1r = new Sprite(golemClimb1);
	public static Sprite golemClimb2r = new Sprite(golemClimb2);
	public static Sprite golemClimb3r = new Sprite(golemClimb3);
	public static Sprite golemClimb4r = new Sprite(golemClimb4);

	public static Sprite golemThrow1r = new Sprite(golemThrow1);
	public static Sprite golemThrow2r = new Sprite(golemThrow2);
	public static Sprite golemThrow3r = new Sprite(golemThrow3);

	public static Sprite golemThrow6r = new Sprite(golemThrow6);

	public static Sprite golemThrow4r = new Sprite(golemThrow4);
	public static Sprite golemThrow5r = new Sprite(golemThrow5);

	public static Sprite icicle1 = new Sprite(SpriteSheet.Spike, 32, 128, 0, 0);
	public static Sprite icicle2 = new Sprite(SpriteSheet.Spike, 32, 128, 1, 0);
	public static Sprite icicle3 = new Sprite(SpriteSheet.Spike, 32, 128, 2, 0);
	public static Sprite icicle4 = new Sprite(SpriteSheet.Spike, 32, 128, 3, 0);

	public static Sprite rhinoWalk1 = new Sprite(SpriteSheet.Rhino, 64, 64, 0, 0);
	public static Sprite rhinoWalk2 = new Sprite(SpriteSheet.Rhino, 64, 64, 1, 0);
	public static Sprite rhinoWalk3 = new Sprite(SpriteSheet.Rhino, 64, 64, 2, 0);
	public static Sprite rhinoWalk4 = new Sprite(SpriteSheet.Rhino, 64, 64, 3, 0);

	public static Sprite rhinoWalk1r = new Sprite(rhinoWalk1);
	public static Sprite rhinoWalk2r = new Sprite(rhinoWalk2);
	public static Sprite rhinoWalk3r = new Sprite(rhinoWalk3);
	public static Sprite rhinoWalk4r = new Sprite(rhinoWalk4);

	public static Sprite rhinoCharge1 = new Sprite(SpriteSheet.Rhino, 71, 64, 0, 1);
	public static Sprite rhinoCharge2 = new Sprite(SpriteSheet.Rhino, 71, 64, 1, 1);
	public static Sprite rhinoCharge3 = new Sprite(SpriteSheet.Rhino, 71, 64, 2, 1);
	public static Sprite rhinoCharge4 = new Sprite(SpriteSheet.Rhino, 71, 64, 3, 1);

	public static Sprite rhinoCharge1r = new Sprite(rhinoCharge1);
	public static Sprite rhinoCharge2r = new Sprite(rhinoCharge2);
	public static Sprite rhinoCharge3r = new Sprite(rhinoCharge3);
	public static Sprite rhinoCharge4r = new Sprite(rhinoCharge4);

	public static Sprite rhinoAttack1 = new Sprite(SpriteSheet.Rhino, 71, 64, 0, 2);
	public static Sprite rhinoAttack2 = new Sprite(SpriteSheet.Rhino, 71, 64, 1, 2);

	public static Sprite rhinoAttack1r = new Sprite(rhinoAttack1);
	public static Sprite rhinoAttack2r = new Sprite(rhinoAttack2);

	public static Sprite rhinoHurt = new Sprite(SpriteSheet.Rhino, 71, 64, 2, 2);
	public static Sprite rhinoHurtr = new Sprite(rhinoHurt);

	public static Sprite rhinoStun1 = new Sprite(SpriteSheet.Rhino, 71, 64, 0, 3);
	public static Sprite rhinoStun2 = new Sprite(SpriteSheet.Rhino, 71, 64, 1, 3);
	public static Sprite rhinoStun3 = new Sprite(SpriteSheet.Rhino, 71, 64, 2, 3);
	public static Sprite rhinoStun4 = new Sprite(SpriteSheet.Rhino, 71, 64, 3, 3);

	public static Sprite rhinoStun1r = new Sprite(rhinoStun1);
	public static Sprite rhinoStun2r = new Sprite(rhinoStun2);
	public static Sprite rhinoStun3r = new Sprite(rhinoStun3);
	public static Sprite rhinoStun4r = new Sprite(rhinoStun4);

	public static Sprite smTree1 = new Sprite(SpriteSheet.trees, 128, 268, 0, 0);
	public static Sprite smTree2 = new Sprite(SpriteSheet.trees, 128, 268, 1, 0);
	public static Sprite smTree3 = new Sprite(SpriteSheet.trees, 128, 268, 2, 0);
	public static Sprite smTree4 = new Sprite(SpriteSheet.trees, 128, 268, 3, 0);

	public static Sprite lgTree1 = new Sprite(SpriteSheet.trees, 310, 268, 0, 1);
	public static Sprite lgTree2 = new Sprite(SpriteSheet.trees, 365, 268, 1, 1);

	public static Sprite shrub = new Sprite(SpriteSheet.Shrub);
	public static Sprite Arrow = new Sprite(SpriteSheet.Arrow);
	public static Sprite Reticle = new Sprite(SpriteSheet.Reticle);
	public static Sprite voidParticle = new Sprite(0, 0x00ff00ff);
	public static Sprite particle_snow = new Sprite(2, 0x00ffffff);
	public static Sprite MapBG = new Sprite(SpriteSheet.MapBG);
	public static Sprite map1 = new Sprite(new SpriteSheet("/maplevel1.png"));
	public static Sprite map2 = new Sprite(new SpriteSheet("/maplevel2.png"));
	public static Sprite map3 = new Sprite(new SpriteSheet("/maplevel3.png"));
	public static Sprite map4 = new Sprite(new SpriteSheet("/mapLevel4.png"));
	public static Sprite talentTree = new Sprite(new SpriteSheet("/talentTree.png"));
	public static Sprite speedTalent = new Sprite(SpriteSheet.speedTalent);
	public static Sprite rangeTalent = new Sprite(SpriteSheet.rangeTalent);
	public static Sprite incinerationTalent = new Sprite(SpriteSheet.incinerationTalent);
	public static Sprite arcanefocusTalent = new Sprite(SpriteSheet.arcanefocusTalent);
	public static Sprite manabladeTalent = new Sprite(SpriteSheet.manabladeTalent);

	public static Sprite playerWMUp1 = new Sprite(SpriteSheet.PlayerWorldMap, 24, 32, 0, 0);
	public static Sprite playerWMUp2 = new Sprite(SpriteSheet.PlayerWorldMap, 24, 32, 1, 0);
	public static Sprite playerWMUp3 = new Sprite(SpriteSheet.PlayerWorldMap, 24, 32, 2, 0);

	public static Sprite playerWMDown1 = new Sprite(SpriteSheet.PlayerWorldMap, 24, 32, 0, 2);
	public static Sprite playerWMDown2 = new Sprite(SpriteSheet.PlayerWorldMap, 24, 32, 1, 2);
	public static Sprite playerWMDown3 = new Sprite(SpriteSheet.PlayerWorldMap, 24, 32, 2, 2);

	public static Sprite playerWMRight1 = new Sprite(SpriteSheet.PlayerWorldMap, 24, 32, 0, 1);
	public static Sprite playerWMRight2 = new Sprite(SpriteSheet.PlayerWorldMap, 24, 32, 1, 1);
	public static Sprite playerWMRight3 = new Sprite(SpriteSheet.PlayerWorldMap, 24, 32, 2, 1);

	public static Sprite playerWMLeft1 = new Sprite(playerWMRight1);
	public static Sprite playerWMLeft2 = new Sprite(playerWMRight2);
	public static Sprite playerWMLeft3 = new Sprite(playerWMRight3);

	public static Sprite grass = new Sprite(SpriteSheet.mapTiles, 32, 32, 4, 0);
	public static Sprite grassHill = new Sprite(SpriteSheet.mapTiles, 32, 32, 4, 1);
	public static Sprite grassMountain = new Sprite(SpriteSheet.mapTiles, 32, 32, 4, 2);

	public static Sprite snow = new Sprite(SpriteSheet.mapTiles, 32, 32, 4, 6);
	public static Sprite snowHill = new Sprite(SpriteSheet.mapTiles, 32, 32, 4, 7);
	public static Sprite snowMountain = new Sprite(SpriteSheet.mapTiles, 32, 32, 4, 8);

	public static Sprite snowMountainLGul = new Sprite(SpriteSheet.mapTiles, 32, 32, 5, 6);
	public static Sprite snowMountainLGur = new Sprite(SpriteSheet.mapTiles, 32, 32, 6, 6);
	public static Sprite snowMountainLGbl = new Sprite(SpriteSheet.mapTiles, 32, 32, 5, 7);
	public static Sprite snowMountainLGbr = new Sprite(SpriteSheet.mapTiles, 32, 32, 6, 7);

	public static Sprite grassSnowTL = new Sprite(SpriteSheet.mapTiles, 32, 32, 5, 4);
	public static Sprite grassSnowTM = new Sprite(SpriteSheet.mapTiles, 32, 32, 6, 4);
	public static Sprite grassSnowTR = new Sprite(SpriteSheet.mapTiles, 32, 32, 7, 4);
	public static Sprite grassSnowML = new Sprite(SpriteSheet.mapTiles, 32, 32, 6, 5);
	public static Sprite grassSnowMR = new Sprite(SpriteSheet.mapTiles, 32, 32, 7, 5);
	
	public static Sprite wall = new Sprite(SpriteSheet.mapTiles,32,32,8,0);
	public static Sprite endWall = new Sprite(SpriteSheet.mapTiles,32,32,7,0);
	public static Sprite endWallR = new Sprite(endWall);
	public static Sprite tower1 = new Sprite(SpriteSheet.mapTiles,32,32,8,1);
	public static Sprite tower2 = new Sprite(SpriteSheet.mapTiles,32,32,8,2);
	public static Sprite tower3 = new Sprite(SpriteSheet.mapTiles,32,32,8,3);


	public static Sprite snowGrassTL = new Sprite(SpriteSheet.mapTiles, 32, 32, 7, 6);
	public static Sprite snowGrassTR = new Sprite(snowGrassTL);

	public static Sprite eArrow = new Sprite(SpriteSheet.eArrow);
	public static Sprite eArrowr = new Sprite(eArrow);
	public static Sprite archer = new Sprite(SpriteSheet.archer, 64, 64, 0, 0);
	public static Sprite archerShoot1 = new Sprite(SpriteSheet.archer, 64, 64, 1, 0);
	public static Sprite archerShoot2 = new Sprite(SpriteSheet.archer, 64, 64, 2, 0);
	public static Sprite archerShoot3 = new Sprite(SpriteSheet.archer, 64, 64, 3, 0);
	public static Sprite archerReload1 = new Sprite(SpriteSheet.archer, 64, 64, 4, 0);
	public static Sprite archerReload2 = new Sprite(SpriteSheet.archer, 64, 64, 5, 0);
	public static Sprite archerReload3 = new Sprite(SpriteSheet.archer, 64, 64, 6, 0);

	public static Sprite archerWalk1 = new Sprite(SpriteSheet.archer, 64, 64, 0, 1);
	public static Sprite archerWalk2 = new Sprite(SpriteSheet.archer, 64, 64, 1, 1);
	public static Sprite archerWalk3 = new Sprite(SpriteSheet.archer, 64, 64, 2, 1);

	public static Sprite archerR = new Sprite(archer);
	public static Sprite archerShoot1R = new Sprite(archerShoot1);
	public static Sprite archerShoot2R = new Sprite(archerShoot2);
	public static Sprite archerShoot3R = new Sprite(archerShoot3);
	public static Sprite archerReload1R = new Sprite(archerReload1);
	public static Sprite archerReload2R = new Sprite(archerReload2);
	public static Sprite archerReload3R = new Sprite(archerReload3);
	public static Sprite archerWalk1R = new Sprite(archerWalk1);
	public static Sprite archerWalk2R = new Sprite(archerWalk2);
	public static Sprite archerWalk3R = new Sprite(archerWalk3);

	public static Sprite mage = new Sprite(SpriteSheet.mage, 40, 56, 0, 0);
	public static Sprite mageAttack1 = new Sprite(SpriteSheet.mage, 40, 56, 1, 0);
	public static Sprite mageAttack2 = new Sprite(SpriteSheet.mage, 40, 56, 2, 0);

	public static Sprite mageR = new Sprite(mage);
	public static Sprite mageAttack1R = new Sprite(mageAttack1);
	public static Sprite mageAttack2R = new Sprite(mageAttack2);

	public static Sprite talentDes = new Sprite(SpriteSheet.talentDes);

	public static void loadPlayerSprites() {
		int playerSpriteSize = 64;
		System.out.println("Loading player sprites...");
		for (int j = 0; j < SpriteSheet.player.getHeight() / playerSpriteSize; j++) {
			for (int i = 0; i < SpriteSheet.player.getWidth() / playerSpriteSize; i++) {
				Sprite sprite = (new Sprite(SpriteSheet.player, playerSpriteSize, playerSpriteSize, i, j));
				playerSprites.add(sprite);
				playerSprites.add(new Sprite(sprite));
			}
			System.out.print("|");
		}
		System.out.println("[35/35]");
		System.out.println("Done!");
	}

	public static void loadRivalSprites() {
		int playerSpriteSize = 64;
		System.out.println("Loading rival sprites...");
		for (int j = 0; j < SpriteSheet.rivalSprites.getHeight() / playerSpriteSize; j++) {
			for (int i = 0; i < SpriteSheet.rivalSprites.getWidth() / playerSpriteSize; i++) {
				Sprite sprite = (new Sprite(SpriteSheet.rivalSprites, playerSpriteSize, playerSpriteSize, i, j));
				rivalSprites.add(sprite);
				rivalSprites.add(new Sprite(sprite));
			}
			System.out.print("|");
		}
		System.out.println("[35/35]");
		System.out.println("Done!");
	}

	public Sprite(SpriteSheet sheet, int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		this.x = x * width;
		this.y = y * height;
		this.sheet = sheet;
		// System.out.println(width + " " + height + " " + x + " " + y);
		load();
	}

	public Sprite(SpriteSheet sheet) {
		this.width = sheet.getWidth();
		this.height = sheet.getHeight();
		pixels = new int[width * height];
		x = 0;
		y = 0;
		this.sheet = sheet;
		load();
	}

	public Sprite(int size, int color) {
		this.width = size;
		this.height = size;
		pixels = new int[width * height];
		setColor(color);
	}

	private void setColor(int color) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = color;
		}
	}

	public Sprite(Sprite sprite) {
		width = sprite.width;
		height = sprite.height;
		pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sprite.pixels[width - 1 - x + y * sprite.width];
			}
		}
	}

	public Sprite(int[] pixels, int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.getWidth()];
			}
		}
	}

	public static Sprite[] split(SpriteSheet sheet) {
		int spriteNum = ((sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT));
		Sprite[] sprites = new Sprite[spriteNum];
		int current = 0;
		int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
		for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
			for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {
				for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					}
				}

				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}

		return sprites;
	}
}
