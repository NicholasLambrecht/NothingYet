package Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JFrame;

import Entity.Mob.Player;
import Graphics.Font;
import Graphics.Sprite;
import Input.Keyboard;
import Input.Mouse;
import Level.Level;
import Level.SpawnLevel;
import Sounds.Music;
import Sounds.SoundEffect;
import UI.Map;
import WorldMap.WMPlayer;
import WorldMap.WorldMap;

public class ActionGame extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private static int scale = 1;
	public static int width = 1280 / scale;
	public static int height = 720 / scale;
	public static String title = "Action Game";
	private boolean running = false;
	private Thread thread;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private Screen screen;
	private Keyboard key;
	private JFrame frame;
	private Mouse mouse;
	private Level level;
	private Player player;
	public boolean spawned = false;
	private boolean crouchScout = false;
	private int crouchScoutc = 0;
	private int scrollMod = 120;
	private int scrollModu = 120;
	private boolean tutorial = false;
	private boolean flag = true;
	private boolean startScreen = true;
	private boolean mainMenu = true;
	private boolean difficulty = false;
	private int cursorPos = 0;
	boolean upScout = false;
	int upScoutc = 0;
	private int dif;
	private int flagTimer = 0;
	private boolean map = false;
	private Map map1;
	private boolean mflag = false;
	private boolean tflag = false;
	private boolean talent = false;
	private boolean tcflag = false;
	private int talentIndexD = 0;
	private int talentIndexR = 0;
	private boolean paused = false;
	private boolean pflag = true;
	private WMPlayer wmPlayer;
	private boolean gpadUP = false;
	private boolean gpadDOWN = false;
	private boolean gpadLEFT = false;
	private boolean gpadRIGHT = false;
	private boolean gpadA = false;
	private boolean gpadB = false;
	private boolean gpadSTART = false;
	private boolean gpadPOVLEFT = false;
	private boolean gpadPOVRIGHT = false;
	private boolean maximizeFlag = true;
	private boolean minimizeFlag = false;
	private boolean options = false;
	private boolean maximize = false;
	private boolean minimize = false;
	private long window;

	public ActionGame() {
		Sprite.loadPlayerSprites();
		Sprite.loadRivalSprites();
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		frame = new JFrame();
		key = new Keyboard();
		mouse = new Mouse();
		screen = new Screen(width, height);
		addKeyListener(key);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		WorldMap.load("/worldmap.png");

	}

	public void saveOptions() {
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream("config.properties");

			// set the properties value
			if (minimizeFlag) {
				prop.setProperty("fullscreen", "false");
			} else {
				prop.setProperty("fullscreen", "true");
			}
			prop.setProperty("musicVol", "" + Music.vol);
			prop.setProperty("soundeffectVol", "" + SoundEffect.vol);

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public void loadOptions() {
		Properties prop = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			if (prop.get("fullscreen").equals("false")) {
				minimize = true;
			}
			if (prop.get("musicVol") != null) {
				Music.vol = Float.parseFloat((String) prop.get("musicVol"));
			}

			if (prop.get("soundeffectVol") != null) {
				SoundEffect.vol = Float.parseFloat((String) prop.get("soundeffectVol"));
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		// loadOptions();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}

		}
		stop();
	}

	public void update() {
		key.update();
		mouse.update();
		if (startScreen) {
			updateStartScreen();
		} else if (player.worldMap) {
			if (!talent)
				wmPlayer.update();
			WorldMap.update();
		} else {

		}
		if (key.r || gpadSTART) {
			if (paused && pflag) {
				paused = false;
				pflag = false;
			} else if (!paused && pflag) {
				pflag = false;
				paused = true;
			}
		} else
			pflag = true;
		if (paused && key.esc) {
			this.stop();
			running = false;
		}
		if (!paused && !startScreen) {
			if (key.n || gpadPOVLEFT) {
				if (!talent && tflag) {
					talent = true;
					tflag = false;
				} else if (talent && tflag) {
					talent = false;
					tflag = false;
				}
			} else
				tflag = true;
			if (talent) {
				if ((key.up || key.down || key.left || key.right || key.f || gpadUP || gpadDOWN || gpadRIGHT || gpadLEFT || gpadA)) {
					if ((key.up || gpadUP) && tcflag) {
						talentIndexD--;
						if (talentIndexD < 0)
							talentIndexD = 0;
						tcflag = false;
					}
					if ((key.down || gpadDOWN) && tcflag) {
						talentIndexD++;
						if (talentIndexD > 7)
							talentIndexD = 7;
						tcflag = false;
					}
					if ((key.left || gpadLEFT) && tcflag) {
						talentIndexR--;
						if (talentIndexR < 0)
							talentIndexR = 0;
						tcflag = false;
					}
					int maxR = 2;
					if ((key.right || gpadRIGHT) && tcflag) {
						talentIndexR++;

						tcflag = false;
					}
					if (talentIndexR > maxR)
						talentIndexR = maxR;

					if ((key.f || gpadA) && tcflag && player.talentPoints > 0) {
						tcflag = false;
						if (talentIndexD == 0 && talentIndexR == 0) {
							player.unlockIncinerationTalent();
						}
						if (talentIndexD == 0 && talentIndexR == 1) {
							player.unlockArcaneFocusTalent();
						}
						if (talentIndexD == 0 && talentIndexR == 2) {
							player.unlockManaBladeTalent();
						}
						if (talentIndexD == 1 && talentIndexR == 0) {
							player.unlockSpeedTalent();
						}
						if (talentIndexD == 1 && talentIndexR == 1) {
							player.unlockRangeTalent();
						}
						if (talentIndexD == 1 && talentIndexR == 2) {
							player.unlockVorpalBladeTalent();
						}
						if (talentIndexD == 2 && talentIndexR == 0) {
							player.unlockPurgingFireTalent();
						}
					}
				} else
					tcflag = true;

			} else if (player.worldMap == false)
				updateGame();
		}

	}

	private void updateStartScreen() {
		if (!flag)
			flagTimer++;
		else
			flagTimer = 0;

		if (Music.MENU.isPlaying() == false) {
			Music.stopAll();
			Music.MENU.play();
		}

		if (flagTimer >= 30) {
			flag = true;
			flagTimer = 0;
		}
		if ((key.up || gpadUP) && flag && mainMenu) {
			cursorPos--;
			SoundEffect.SELECT.play();
			flag = false;
			if (cursorPos < 0)
				cursorPos = 2;
		} else if ((key.up || gpadUP) && flag && difficulty) {
			cursorPos--;
			SoundEffect.SELECT.play();
			flag = false;
			if (cursorPos < 0)
				cursorPos = 1;
		} else if ((key.up || gpadUP) && flag && options) {
			cursorPos--;
			SoundEffect.SELECT.play();
			flag = false;
			if (cursorPos < 0)
				cursorPos = 2;
		} else if ((key.down || gpadDOWN) && flag && mainMenu) {
			SoundEffect.SELECT.play();
			cursorPos++;
			flag = false;
			if (cursorPos > 2)
				cursorPos = 0;
		} else if ((key.down || gpadDOWN) && flag && options) {
			SoundEffect.SELECT.play();
			cursorPos++;
			flag = false;
			if (cursorPos > 2)
				cursorPos = 0;
		} else if ((key.down || gpadDOWN) && flag && difficulty) {
			SoundEffect.SELECT.play();
			cursorPos++;
			flag = false;
			if (cursorPos > 1)
				cursorPos = 0;
		} else if ((key.left || gpadLEFT) && options && flag) {
			if (cursorPos == 1) {
				Music.vol -= .1;
				Music.stopAll();
				if (Music.vol < -20.0f) {
					SoundEffect.FAIL.play();
					Music.vol = -20.0f;
					flag = false;
				} else {
					SoundEffect.SELECT.play();
				}
			}
			if (cursorPos == 2) {
				SoundEffect.vol -= .1;
				if (SoundEffect.vol < -20.0f) {
					SoundEffect.FAIL.play();
					SoundEffect.vol = -20.0f;
					flag = false;
				} else {
					SoundEffect.SELECT.play();
				}
			}
		} else if ((key.right || gpadRIGHT) && options && flag) {
			if (cursorPos == 1) {
				Music.vol += .1;
				Music.stopAll();
				if (Music.vol > 5.9f) {
					Music.vol = 5.999f;
					SoundEffect.FAIL.play();
					flag = false;
				} else {
					SoundEffect.SELECT.play();
				}
			}
			if (cursorPos == 2) {
				SoundEffect.vol += .1;
				if (SoundEffect.vol > 5.9f) {
					SoundEffect.vol = 5.999f;
					SoundEffect.FAIL.play();
					flag = false;
				} else {
					SoundEffect.SELECT.play();
				}
			}
		} else if ((key.enter || key.space || gpadA) && mainMenu && flag) {
			if (cursorPos == 0) {
				mainMenu = false;
				difficulty = true;
				flag = false;
				SoundEffect.SELECT.play();
			} else if (cursorPos == 2) {
				mainMenu = false;
				options = true;
				flag = false;
				SoundEffect.SELECT.play();
				cursorPos = 0;
			} else {
				flag = false;
				SoundEffect.FAIL.play();
			}
		} else if ((key.enter || key.space || gpadA) && options && flag) {
			if (cursorPos == 0) {
				if (maximizeFlag == true)
					maximize = true;
				else if (minimizeFlag == true)
					minimize = true;
				flag = false;
				SoundEffect.SELECT.play();
			} else if (cursorPos == 2) {
				mainMenu = false;
				options = true;
				flag = false;
				SoundEffect.SELECT.play();
			} else {
				flag = false;
				SoundEffect.FAIL.play();
			}
		} else if (key.esc || gpadB) {
			if (mainMenu) {

			} else if (options) {
				SoundEffect.SELECT.play();
				options = false;
				mainMenu = true;
				saveOptions();
			} else if (difficulty) {
				SoundEffect.SELECT.play();
				difficulty = false;
				mainMenu = true;
			}
		} else if ((gpadA || key.enter || key.space) && difficulty && flag) {
			Music.stopAll();
			SoundEffect.SELECT.play();
			dif = cursorPos;
			// player = new Player(806 * 32, 78 * 32, key, dif, controller);
			// player = new Player(54 * 32, 206 * 32, key, dif, controller);
			player = new Player(26 * 32, 3104, dif, key);
			// level = new Level2("/level2.png", dif);
			level = new SpawnLevel("/level1.png", dif);
			// level = new Level5("/level5.png", dif);
			level.setPlayer(player);
			player.level = level;
			difficulty = false;
			mainMenu = false;
			flag = false;
			startScreen = false;
			// player.gainEXP(1000);
			map1 = new Map(1280 - 110, 110, level);
			WorldMap.player = player;
			wmPlayer = new WMPlayer(180 * 32, 24 * 32);
			WorldMap.wmPlayer = wmPlayer;
		} else if (!key.up && !key.down && !key.enter && !key.space && !gpadUP && !gpadDOWN && !gpadA && !gpadB && !key.esc)
			flag = true;
	}

	private void updateGame() {
		level.update();
		player.update();
		map1.update();

		if (player.gameover) {
			Music.stopAll();
			startScreen = true;
			mainMenu = true;
			cursorPos = 0;
			Music.MENU.play();
		}
		if (level != player.level) {
			level = player.level;
			map1.level = level;
		}

		if (key.m || gpadPOVRIGHT) {
			if (!map && mflag) {
				map = true;
				mflag = false;
			} else if (mflag) {
				map = false;
				mflag = false;
			}
		} else
			mflag = true;
		if (key.three)
			player.remove();
		if (key.t) {
			if (tutorial && flag) {
				flag = false;
				tutorial = false;
			} else if (flag) {
				tutorial = true;
				flag = false;
			}
		} else
			flag = true;
		if ((key.down || gpadDOWN) && !player.flying && !player.archery) {
			crouchScoutc++;
		} else {
			scrollMod += 8;
			if (scrollMod > 80) {
				scrollMod = 80;
			}
			crouchScoutc = 0;
		}
		if (crouchScoutc > 60) {
			crouchScout = true;
		} else
			crouchScout = false;

		if ((key.up) && !player.flying && !player.flying) {
			upScoutc++;
		} else {
			scrollModu -= 8;
			if (scrollModu < 0) {
				scrollModu = 0;
			}
			upScoutc = 0;
		}
		if (upScoutc > 60) {
			upScout = true;
		} else
			upScout = false;

		if (crouchScout) {
			scrollMod -= 8;
			if (scrollMod < -94) {
				scrollMod = -94;
			}
		}
		if (upScout) {
			scrollModu += 8;
			if (scrollModu > 94) {
				scrollModu = 94;
			}
		}
	}

	public void render() {

		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		if (startScreen) {
			renderStartScreen(screen);
		} else if (player.worldMap) {
			int xScroll = wmPlayer.x + 32 - screen.width / 2;
			int yScroll = wmPlayer.y - scrollMod - scrollModu - screen.height / 2;
			WorldMap.render(xScroll, yScroll, screen);
			wmPlayer.render(screen);
		} else
			renderGame(screen);
		if (talent)
			renderTalentTree(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.ORANGE);
		if (!startScreen)
			g.drawString("X: " + player.x / 32 + " Y: " + player.y / 32, width - 100, 20);
		g.dispose();
		bs.show();
	}

	private void renderStartScreen(Screen screen) {
		if (mainMenu) {
			screen.renderSprite(0, 0, Sprite.MainMenu, false);
			screen.renderSprite(508, 177 + 52 * cursorPos, Sprite.cursor, false);
		} else if (difficulty) {
			screen.renderSprite(0, 0, Sprite.diffMenu, false);
			screen.renderSprite(508, 177 + 52 * cursorPos, Sprite.cursor, false);

		} else if (options) {
			if (!minimizeFlag) {
				screen.renderSprite(0, 0, Sprite.options, false);
			} else
				screen.renderSprite(0, 0, Sprite.optionsFS, false);
			screen.renderSprite(358, 177 + 52 * cursorPos, Sprite.cursor, false);
			int muDX = 671;
			int muFill = (int) (((20 + Music.vol) / 26) * 222);
			for (int i = 0; i < muFill; i++) {
				screen.renderSprite(muDX + i, 237, Sprite.hpFill, false);
			}
			int seFill = (int) (((20 + SoundEffect.vol) / 26) * 222);
			for (int i = 0; i < seFill; i++) {
				screen.renderSprite(muDX + i, 288, Sprite.hpFill, false);
			}

		}
	}

	private void renderGame(Screen screen) {
		int xScroll = player.x + 32 - screen.width / 2;
		int yScroll = player.y - scrollMod - scrollModu - screen.height / 2;
		level.render(xScroll, yScroll, screen);
		player.render(screen);

		if (map)
			map1.render(screen);
		screen.renderSprite(width - 175, height - 68, Sprite.Abutton, false);
		Font.renderBTSTSM("JUMP", screen, width - 140, height - 60);

		screen.renderSprite(width - 206, height - 95, Sprite.Xbutton, false);
		Font.renderBTSTSM("ATTACK", screen, width - 284, height - 87);

		screen.renderSprite(width - 175, height - 125, Sprite.Ybutton, false);
		Font.renderBTSTSM("SHIELD", screen, width - 140, height - 116);
		screen.renderSprite(width - 145, height - 95, Sprite.Bbutton, false);
		Font.renderBTSTSM("SPELL", screen, width - 108, height - 87);

		if (tutorial)
			renderTutorial(screen);
		if (paused) {
			Font.renderBTST("PAUSED", screen, 600, 320);
			Font.renderBTSTSM("PRESS START TO RESUME", screen, 550, 360);
		}
	}

	private void renderTalentTree(Screen screen) {
		int ttx = 100;
		int tty = 125;
		int fk = -19;
		screen.renderSprite(ttx, tty, Sprite.talentTree, false);
		Font.renderBTSTSM("TALENT PNTS " + player.talentPoints, screen, ttx + 10, tty + 71 + fk);

		if (player.incinerationTalent) {
			int xStart = 25;
			int yStart = 80;
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 35, 1);
			screen.renderRect(ttx + xStart + 35, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 34, 65280, 34, 1);
		}
		if (player.arcanefocusTalent) {
			int xStart = 67;
			int yStart = 80;
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 35, 1);
			screen.renderRect(ttx + xStart + 35, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 34, 65280, 34, 1);
		}
		if (player.manabladeTalent) {
			int xStart = 109;
			int yStart = 80;
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 35, 1);
			screen.renderRect(ttx + xStart + 35, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 34, 65280, 34, 1);
		}
		if (player.speedTalent) {
			int xStart = 25;
			int yStart = 123;
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 35, 1);
			screen.renderRect(ttx + xStart + 35, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 34, 65280, 34, 1);
		}
		if (player.rangeTalent) {
			int xStart = 67;
			int yStart = 123;
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 34, 1);
			screen.renderRect(ttx + xStart + 35, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 34, 65280, 34, 1);
		}
		if (player.vorpalbladeTalent) {
			int xStart = 109;
			int yStart = 123;
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 34, 1);
			screen.renderRect(ttx + xStart + 35, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 34, 65280, 34, 1);
		}
		if (player.purgingfireTalent) {
			int xStart = 25;
			int yStart = 166;
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 1, 65280, 34, 1);
			screen.renderRect(ttx + xStart + 35, tty + yStart + 1, 65280, 1, 34);
			screen.renderRect(ttx + xStart + 1, tty + yStart + 34, 65280, 34, 1);
		}
		int grdTLx = 25;
		int grdTLy = 80;
		int grdDX = 42;
		int selBoxX = grdTLx + grdDX * talentIndexR;
		int selBoxY = grdTLy + (grdDX + 1) * talentIndexD;
		screen.renderRect(ttx + selBoxX, tty + selBoxY, 16711680, 1, 36);
		screen.renderRect(ttx + selBoxX, tty + selBoxY, 16711680, 36, 1);
		screen.renderRect(ttx + selBoxX + 36, tty + selBoxY, 16711680, 1, 36);
		screen.renderRect(ttx + selBoxX, tty + selBoxY + 35, 16711680, 36, 1);
		screen.renderSprite(ttx + 173, tty + 140, Sprite.talentDes, false);
		String talentName = "";
		String talentDescription = "";
		String talentEffect = "";
		if (talentIndexD == 0) {
			if (talentIndexR == 0) {
				talentName = "INCINERATION";
				talentDescription = "SCULPT YOUR FIREBALLS EFFICIENTLY FOR MORE\nEFFECTIVE USE OF YOUR MANA";
				talentEffect = "FIREBALLS DEAL 25 PERCENT MORE DAMAGE AND\nCOST 25 PERCENT LESS MANA TO CAST";
			} else if (talentIndexR == 1) {
				talentName = "ARCANE FOCUS";
				talentDescription = "DEEP BREATHS AND CONCENTRATION ALLOW YOU TO \nTAKE YOUR TIME WITH YOUR BOW";
				talentEffect = "USE STAMINA TO STOP TIME WHILE AIMING AND\nDEAL 25 PERCENT MORE DAMAGE WITH ARROWS";
			} else if (talentIndexR == 2) {
				talentName = "MANA BLADE";
				talentDescription = "CHARGE YOUR BLADE WITH MANA TO CAST FIERY\nATTACKS WHILE SLASHING YOUR FOES";
				talentEffect = "20 PERCENT CHANCE TO CAST A RANDOM SPELL\nWHEN YOU HIT AN ENEMY WITH A SWORD ATTACK";
			}
		} else if (talentIndexD == 1) {
			if (talentIndexR == 0) {
				talentName = "HASTE";
				talentDescription = "YOUR CONSISTANT RUNNING HAS IMPROVED YOUR\nABILITY TO MOVE FARTHER IN A SHORTER AMOUNT\nOF TIME WITH EACH STEP";
				talentEffect = "INCREASES YOUR MAXIMUM SPEED BY 25 PERCENT";
			} else if (talentIndexR == 1) {
				talentName = "SEAR";
				talentDescription = "HARNESS YOUR GIFT OF FLAME TO DEVASTATE\nYOUR FOES";
				talentEffect = "INCREASES PILLAR OF FLAME RANGE AND DAMAGE";
			} else if (talentIndexR == 2) {
				talentName = "VORPAL BLADE";
				talentDescription = "A BLADE SO SHARP IT CAN SLICE CLEANLY\nTHROUGH DIAMOND AND FLESH";
				talentEffect = "ENEMIES WITH LESS THAT 10 PERCENT HEALTH\nDIE INSTANTLY TO SWORD ATTACKS";
			}
		} else if (talentIndexD == 2) {
			if (talentIndexR == 0) {
				int FSDamage = player.getEXPLevel() + 5;
				talentName = "PURGING FIRE";
				talentDescription = "CHANNEL YOUR FIRE SHIELD AND MAKE IT BURN\nNEARBY FOES";
				talentEffect = "INFLICTS BURN CONDITION ON NEARBY ENEMIES\nFOR " + FSDamage + " DAMAGE PER SECOND";
			}
		}
		Font.render(talentName, screen, ttx + 173 + 10, tty + 148, 0xFFFF0000, true);
		Font.renderBTSTSM(talentDescription, screen, ttx + 173 + 10, tty + 140 + 45);
		Font.renderBTSTSM(talentEffect, screen, ttx + 173 + 10, tty + 140 + 225);
	}

	private void renderTutorial(Screen screen) {
		screen.renderSprite(0, 0, Sprite.tutorial, false);
	}

	public static void main(String[] args) {
		ActionGame game = new ActionGame();
		game.frame.setTitle(title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();
	}
}
