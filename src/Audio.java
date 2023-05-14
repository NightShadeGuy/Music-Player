import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JSlider;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Audio {
	
	File file;
	Clip clip; 
	
	InputStream inputStream;
	Player player;
	int nextClip = 0;
	
	JSlider volumeSlider = new JSlider(-40, 6, -6);  // default set to -6 dB = 50% volume
	FloatControl volume;
	
	//blueprint
	MusicData music1 = new MusicData(
			"350ml @ BGM DOVA-SYNDROME OFFICIAL YouTube CHANNEL",
			"src/Images/beatImage.png",
			"src/MusicList/beat.wav",
			new Color(240, 98, 146));
	
	MusicData music2 = new MusicData(
			"Zamir – Clockwork (ft. Chevy & Rosarrie) (Lyrics) [CC]",
			"src/Images/Zamir – Clockwork.png",
			"src/MusicList/Zamir – Clockwork (ft. Chevy & Rosarrie) (Lyrics) [CC].wav",
			new Color(34, 165, 226));
	
	MusicData music3 = new MusicData(
			"natori - Overdose",
			"src/Images/natori - Overdose.png",
			"src/MusicList/natori - Overdose.wav",  
			new Color(14, 89, 123));
	
	MusicData music4 = new MusicData(
			"Eli Noir - Real (Lyrics) [CC]",
			"src/Images/Eli Noir - Real.png",
			"src/MusicList/Eli Noir - Real (Lyrics) [CC].wav",  
			new Color(0xec8484));
			
	MusicData[] data = {music1, music2, music3, music4};
	
	Audio() throws JavaLayerException, LineUnavailableException, IOException, UnsupportedAudioFileException {
	    file = new File(data[nextClip].musicLocation);
	    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
	    clip = AudioSystem.getClip();
	    clip.open(audioStream);	 	
	}
	
	protected void updateMusic(int index) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
	    file = new File(data[index].musicLocation);
	    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
	    clip = AudioSystem.getClip();
	    clip.open(audioStream);	 
	    
	    volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	
	
	protected void sound() {
	    volumeSlider.setBounds(245, 335, 150, 50);
	    volumeSlider.setBackground(data[0].bgColor);
	    volumeSlider.setVisible(false);
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);	
	}  
}
