
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javazoom.jl.decoder.JavaLayerException;
import javax.sound.sampled.*;

public class MusicFrame extends JFrame implements  ActionListener, ChangeListener, MouseListener{
    Audio audio;
    MenuNavBar floatBar;
    Random random;
	
    ImageIcon playIcon, pauseIcon, shuffleIcon, noShuffleIcon,
              repeatIcon, repeatIconAgain, volumeIcon;
    
    JPanel northPanel, centerPanel, southPanel;
	
	JLabel musicTitle = new JLabel();
	
    JCheckBox play;
    JCheckBox repeatPlay;
    JCheckBox shufflePlay;
    
    JButton[] features = new JButton[3];
	
	 MusicFrame() throws LineUnavailableException, IOException, UnsupportedAudioFileException, JavaLayerException {
		this.setTitle("Music Player");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setResizable(false);
		this.setSize(420, 500);
		this.setLocationRelativeTo(null);
			
		ImageIcon icon = new ImageIcon("src/IconList/itunes.png");
		this.setIconImage(icon.getImage()); //set App Icon
		
		audio = new Audio();	
		floatBar = new MenuNavBar();
		updateImage(audio.nextClip); 
		
		
		musicTitle.setText(audio.data[audio.nextClip].title);
		musicTitle.setBounds(0, 0, 405, 400);
		musicTitle.setHorizontalTextPosition(JLabel.CENTER);
		musicTitle.setVerticalTextPosition(JLabel.BOTTOM);
		musicTitle.setFont(new Font("Rockwell", Font.PLAIN, 15));
		musicTitle.setIconTextGap(20);
		musicTitle.setForeground(Color.WHITE);
		
		audio.sound();
		audio.volumeSlider.addChangeListener(this);
			
		playIcon = new ImageIcon("src/IconList/play-button.png");
		pauseIcon = new ImageIcon("src/IconList/pause-button.png");
		repeatIcon = new ImageIcon("src/IconList/repeat.png");
		repeatIconAgain = new ImageIcon("src/IconList/repeat (1).png");
		shuffleIcon = new ImageIcon("src/IconList/shuffle.png");
		noShuffleIcon = new ImageIcon("src/IconList/no-shuffle.png");
		
		play = new JCheckBox();
		play.setIcon(playIcon);
		play.setSelectedIcon(pauseIcon);
		play.setLayout(new BorderLayout());
		play.addActionListener(this);
		
		repeatPlay = new JCheckBox();
		repeatPlay.setIcon(repeatIcon);
		repeatPlay.setSelectedIcon(repeatIconAgain);
		repeatPlay.setLayout(new BorderLayout());
		repeatPlay.addActionListener(this);
		
		shufflePlay = new JCheckBox();
		shufflePlay.setIcon(noShuffleIcon);
		shufflePlay.setSelectedIcon(shuffleIcon);
		shufflePlay.setLayout(new BorderLayout());
		shufflePlay.addActionListener(this);
		
		ImageIcon previousIcon = new ImageIcon("src/IconList/previous.png");
		ImageIcon nextIcon = new ImageIcon("src/IconList/next-button.png");
		ImageIcon volumeIcon = new ImageIcon("src/IconList/volume.png");
	
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(200, 80)); 
		northPanel.setBackground(audio.data[audio.nextClip].bgColor);
		northPanel.setLayout(new FlowLayout());
		northPanel.setVisible(false); //hide by default
		
		southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(200, 60));
		southPanel.setLayout(new FlowLayout());
		
		centerPanel = new JPanel();
		centerPanel.setLayout(null);
		centerPanel.setBackground(audio.data[0].bgColor);
		centerPanel.addMouseListener(this);
		
		//add event for musicLibrary
		floatBar.musicLibrary.addActionListener(this);
		floatBar.list.addListSelectionListener(    
				//run event when when the selected list changes
	    		new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						audio.nextClip = floatBar.list.getSelectedIndex();
						audio.clip.stop();
						reUpdate(audio.nextClip); 
						
						try {
							audio.updateMusic(audio.nextClip);
							audio.clip.start();
							
						} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {

							e1.printStackTrace();
						}			
					}
	    			
	    		}
	    );
			
		southPanel.add(shufflePlay);
		
		for(int i = 0; i < features.length; i++) {
			features[i] = new JButton();	
			if(i == 0) {
				features[i].setIcon(previousIcon);
			} else if(i == 1) {
				features[i].setIcon(nextIcon);			
				southPanel.add(play);
			} else if(i == 2) {
				features[i].setIcon(volumeIcon);
			}
			
			features[i].setPreferredSize(new Dimension(60, 35));
			features[i].setBackground(new Color(235, 235, 235));
			features[i].setBorder(BorderFactory.createLineBorder(new Color(235, 235, 235)));
			features[i].setFocusable(false);	
			features[i].addActionListener(this);
			southPanel.add(features[i]);		
		} 
		
		northPanel.add(floatBar.musicPanel);
		centerPanel.add(musicTitle);
		centerPanel.add(audio.volumeSlider);
		southPanel.add(repeatPlay);
		
			
	
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		//this.add(floatBar.musicPanel);
		this.setJMenuBar(floatBar.menuBar); //add all components of the MenuNavBar
		
		this.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == shufflePlay) {
			if(shufflePlay.isSelected()) {			
				//autoPlayNext();
			}else {
				System.out.println("Randomizer deactivate");
			}
		}
		
		
		if(e.getSource() == play) {	
			  if(play.isSelected() && !repeatPlay.isSelected()) {
				  audio.clip.start();
			  } else {
				  audio.clip.stop();	
			  }
		  }
		
		
		if(e.getSource() == repeatPlay) {
			if(repeatPlay.isSelected()) {
				System.out.println("It should play the current music repeatedly");  
				audio.clip.loop(1);
			}else {
				System.out.println("It should be autoplay");
							
			}
		}
		
		for(int i = 0; i < features.length; i++) {
			if(e.getSource() == features[i]) {
				//previous button
				if(i == 0) {
					audio.nextClip--;
					
						if(audio.nextClip < 0) {
							audio.nextClip = 0;
						}			
						reUpdate(audio.nextClip);		
						audio.clip.stop(); // stop the current music
								
								try {
									audio.updateMusic(audio.nextClip);
								} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
									e1.printStackTrace();
								}
		         //next button
				} else if(i == 1) {
					audio.nextClip++;
					
						if(audio.nextClip >= audio.data.length) {
							audio.nextClip = audio.data.length -1;
						} 										
						reUpdate(audio.nextClip);	
						audio.clip.stop(); // stop the current music
						
							
								try {
									audio.updateMusic(audio.nextClip);
								} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
									e1.printStackTrace();
								}
				//volume button	
			   } else if(i == 2) {
					audio.volumeSlider.setVisible(true);
			   }
			}
		}
		
		    //music library 
		if(e.getSource() == floatBar.musicLibrary) {
		    northPanel.setVisible(true);
		    musicTitle.setBounds(0, -68, 405, 400);
		    audio.volumeSlider.setBounds(245, 250, 150, 50);		
		}
		
		
	}
	
	 private void reUpdate(int index) {
	   musicTitle.setText(audio.data[index].title);	
	   audio.volumeSlider.setBackground(audio.data[index].bgColor);
	   northPanel.setBackground(audio.data[index].bgColor);
	   centerPanel.setBackground(audio.data[index].bgColor);
	   updateImage(index);
	}
	 
		
	
	 private void updateImage(int index) {
	   ImageIcon titleImage = new ImageIcon(audio.data[index].image);
	   // Get the original image
	   Image originalImage = titleImage.getImage();

	   // Resize the image
	   int newWidth = 405;
	   int newHeight = 200;
	   Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

	   // Create a new ImageIcon with the resized image
           ImageIcon resizedImageIcon = new ImageIcon(resizedImage);

           // Set the new ImageIcon as the label's icon
	   musicTitle.setIcon(resizedImageIcon);
	}
	
	private void autoPlayNext()  { 
	    // Create a LineListener to monitor the status of the clip
	    random = new Random();
	    audio.clip.addLineListener(event -> {
			
			// If the clip has stopped, start the next clip
              if (event.getType() == LineEvent.Type.STOP) {
            	    int randomIndex = random.nextInt(audio.data.length);  	                     	  
	                System.out.println(randomIndex);        
	                reUpdate(randomIndex);
					try {
						audio.updateMusic(randomIndex);
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
						e.printStackTrace();
					}
					//run the entire autoPlayNext function again
					autoPlayNext();								
              }
          });
		 // Start playing the first clip
		 audio.clip.start();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == audio.volumeSlider) {
			//change to volume -6 dB = 50% volume
			 audio.volume.setValue(audio.volumeSlider.getValue());  
			 
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//set to default style
		northPanel.setVisible(false);
		musicTitle.setBounds(0, 0, 405, 400);
		audio.volumeSlider.setBounds(245, 335, 150, 50);	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {	
		audio.volumeSlider.setVisible(false);  //hide the volume 
	}
}


