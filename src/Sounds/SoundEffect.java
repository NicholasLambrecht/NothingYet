package Sounds;

import javax.sound.sampled.*;

import java.io.IOException;
import java.net.URL;

public enum SoundEffect {
	POTBREAK("./potbreak.wav"), VORPAL("./vorpal.wav"),ENCOUNTER("./beep.wav"), DEATH("./death.wav"),  PICKUP("./pop.wav"), ARROWFIRE("./arrowFire.wav"), HITMARKER("./hitMarker.wav"), SWORDSWING1("./swordSwing1.wav"), SWORDSWING2("./swordSwing2.wav"), SWORDSWING3(
			"./swordSwing3.wav"), ARROWHIT("./arrowHit.wav"), FAIL("./fail.wav"), SELECT("./beep.wav"), CLOCK("./cloak.wav"), PILLAR("./pillar.wav"), SHIELDBGN("./shieldStart.wav"), SHIELDMID("./shieldMid.wav"), SHIELDEND(
			"./shieldEnd.wav"), FIREBALL("./fireball.wav");

	// Nested class for specifying volume
	public static enum Volume {
		MUTE, LOW, MEDIUM, HIGH
	}

	public static Volume volume = Volume.LOW;
	
	
	public static float vol = 0.0f;

	// Each sound effect has its own clip, loaded with its own sound file.
	private Clip clip;

	// Constructor to construct each element of the enum with its own sound
	// file.
	SoundEffect(String soundFileName) {
//		soundFileName = "/Sounds/" + soundFileName;
		System.out.print("Loading: " + soundFileName + " ... ");
		try {
			// Use URL (instead of File) to read from disk and JAR.
			URL url = this.getClass().getClassLoader().getResource(soundFileName);
			// getResource(soundFileName);
			// Set up an audio input stream piped from the sound file.
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			// Get a clip resource
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioInputStream);
			System.out.println("[ Success! ]");
		} catch (UnsupportedAudioFileException e) {
			System.out.println("[ Failed! ]");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("[ Failed! ]");
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			System.out.println("[ Failed! ]");
			e.printStackTrace();
		}
	}

	// Play or Re-play the sound effect from the beginning, by rewinding.
	public void play() {
		boolean MUTEOVERIDE = true; // TODO
		if (volume != Volume.MUTE || !MUTEOVERIDE) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(vol);
			clip.stop(); // Stop the player if it is still running
			clip.setFramePosition(0); // rewind to the beginning
			clip.start(); // Start playing
		}
		if (volume == Volume.MEDIUM) {

		}
	}

	public void stop() {

		clip.stop();

	}

	// Optional static method to pre-load all the sound files.
	public static void init() {
		values(); // calls the constructor for all the elements
	}

	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return clip.isRunning();
	}
}
