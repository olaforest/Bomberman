package menuView;

import gameplayModel.Bomberman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class creates the Pause Menu Panel, and generates all 
 * GUI elements within the panel. This menu can only be accessed while the user is currently
 * in a game.
 * 
 * @author Eric Liou
 *
 */
@SuppressWarnings("serial")
public class PauseMenuPanel extends JPanel {

	
	private JButton resume, save, load, leaderboard, mainmenu, exit;
	private JLabel gamepaused;
	private BufferedImage img;	
	
	/**
	 * Constructs a new Pause Menu Panel with all GUI elements defined with default parameters 
	 * 
	 * @param listener an ActionListener which is applied to all GUI elements (Buttons) which may trigger an user event
	 */
	public PauseMenuPanel(ActionListener listener){
		
		try {
			InputStream in = Bomberman.class.getResourceAsStream("/bomber2.png");
		      img = ImageIO.read(in);
		    } catch(IOException e) {
		      e.printStackTrace();
		    }
		
		
		resume = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		resume.setText("Resume Game");
		resume.addActionListener(listener);
		
		save = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		save.setText("Save Game");
		save.addActionListener(listener);
		
		load = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		load.setText("Load Game");
		load.addActionListener(listener);
		
		leaderboard = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		leaderboard.setText("Leaderboard");
		leaderboard.addActionListener(listener);
		
		mainmenu = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		mainmenu.setText("Return to Main Menu");
		mainmenu.addActionListener(listener);
		
		exit = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		exit.setText("Exit Game");
		exit.addActionListener(listener);
		
		gamepaused = new JLabel();
		gamepaused.setFont(new java.awt.Font("Eurostile", 1, 28)); // NOI18N
        gamepaused.setText("Game Paused");
        
        setupLayout();
		
	}
	
	 protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    // paint the background image and scale it to fill the entire space
		    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	 }

	 
	/**
	 * @return JButton ID of the "Resume game" button
	 */
	public JButton getResumeGame(){
		return resume;
	}
	/**
	 * @return JButton ID of the "Save Game" button
	 */
	public JButton getSave(){
		return save;
	}
	/**
	 * @return JButton ID of the "Load Game" button
	 */
	public JButton getLoad(){
		return load;
	}
	/**
	 * @return JButton ID of the "Leaderboard" button
	 */
	public JButton getLeaderboard(){
		return leaderboard;
	}
	/**
	 * @return JButton ID of the "Return to Main Menu" button
	 */
	public JButton getMainMenu(){
		return mainmenu;
	}
	/**
	 * @return JButton ID of the "Exit Game" button
	 */
	public JButton getExit(){
		return exit;
	}
	
	
	private void setupLayout(){
		GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(resume)
                    .addComponent(save)
                    .addComponent(load)
                    .addComponent(mainmenu)
                    .addComponent(leaderboard)
                    .addComponent(exit)
                    .addComponent(gamepaused))
                .addGap(150, 150, 150))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(gamepaused)
                .addGap(18, 18, 18)
                .addComponent(resume)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(load)
                .addGap(18, 18, 18)
                .addComponent(leaderboard)
                .addGap(18, 18, 18)
                .addComponent(mainmenu)
                .addGap(18, 18, 18)
                .addComponent(exit)
                .addContainerGap(49, Short.MAX_VALUE))
        );
	}
}
