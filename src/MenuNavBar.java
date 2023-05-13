
import java.awt.Color;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javazoom.jl.decoder.JavaLayerException;


public class MenuNavBar extends JFrame implements ActionListener {
	Audio audio;
	
	JMenuBar menuBar;
	JMenu file;
	JMenuItem openFile;
	JMenuItem musicLibrary;
	JMenuItem exit;
	
	ImageIcon musicLibraryIcon, openFileIcon;
	
	JPanel musicPanel;
	JList list;
	int index;
     
	MenuNavBar() throws LineUnavailableException, IOException, UnsupportedAudioFileException, JavaLayerException {
		audio = new Audio();	
		menuBar = new JMenuBar();
		
		openFileIcon = new ImageIcon("src/IconList/open-file.png");
		musicLibraryIcon = new ImageIcon("src/IconList/music-library-icon.png");
		
		file = new JMenu();
		file.setText("File");
		file.addActionListener(this);

		
		openFile = new JMenuItem();
		openFile.setText("Open File...");
		openFile.setIcon(openFileIcon);
		openFile.setBorder(BorderFactory.createBevelBorder(1, Color.pink, Color.pink));
		openFile.addActionListener(this);
		
		musicLibrary = new JMenuItem();
		musicLibrary.setText("Music Library");
		musicLibrary.setIcon(musicLibraryIcon);
		musicLibrary.setBorder(BorderFactory.createBevelBorder(1, Color.pink, Color.pink));
		musicLibrary.addActionListener(this);
		
		exit = new JMenuItem();	
		exit.setText("Exit");
		exit.setBorder(BorderFactory.createBevelBorder(1, Color.pink, Color.pink));
		exit.addActionListener(this);
		
		//Shortcut key
	    file.setMnemonic('F');      	// alt F

	    openFile.setMnemonic('O');  	// Just press O
	    musicLibrary.setMnemonic('M');  // Just press M
	    exit.setMnemonic('E');      	// Just press E
	    
    
		String[] musicTitle = {audio.data[0].title, audio.data[1].title,
				               audio.data[2].title, audio.data[3].title};
		
		list = new JList(musicTitle);
		list.setVisibleRowCount(2);
		list.setFont(new Font("Roboto", Font.BOLD, 13));
		list.setForeground(Color.white);
		list.setBackground(new Color(45, 45, 45));
		list.setFixedCellHeight(30);
	    list.setFixedCellWidth(350);
	 
		
	    
		musicPanel = new JPanel();
		musicPanel.setBackground(new Color(35, 35, 35));
		musicPanel.setLayout(new FlowLayout());	
		musicPanel.add(new JScrollPane(list));
	    
	    	    
	    file.add(openFile);
	    file.add(musicLibrary);
	    
	    file.add(exit);
		
		menuBar.add(file);

	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == openFile) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("C:/Users/User/Downloads/")); //set the path to current file
			

			// show the file chooser dialog
			int result = chooser.showOpenDialog(null);

			// check if the user selected a file
			if (result == JFileChooser.APPROVE_OPTION) {
			    // get the selected file
			    File selectedFile = chooser.getSelectedFile();

				    // check if the selected file is a WAV file
				    if (selectedFile.getName().toLowerCase().endsWith(".wav")) {
				    	
				        // add the WAV file to your program
				        // do whatever you need to do with the selected WAV file
				    } else {
				        // display an error message if the selected file is not a WAV file
				    	System.out.println(selectedFile);
				        JOptionPane.showMessageDialog(null, "Please select a WAV file.");
				        
				    }
			}
			
		}
		

		if(e.getSource() == exit) {
			System.exit(0);
		}
		
		
		
	}
}
