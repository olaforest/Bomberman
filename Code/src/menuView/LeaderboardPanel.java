package menuView;

import gameplayModel.Bomberman;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import menuModel.*;

/**
 * This class creates the Leaderboard Panel, and generates all 
 * GUI elements within the panel. This includes a header title, button for returning
 * to the previous menu and labels which will display the Username and scores of the top 10 players. 
 * Also sets background to an external image file. The Leaderboard Panel can be accessed through
 * the Pause Menu or Main Menu
 * 
 * 
 * @author Eric Liou
 *
 */
@SuppressWarnings("serial")
public class LeaderboardPanel extends JPanel{

	private JLabel user1, score1;
	private JLabel user2, score2;
	private JLabel user3, score3;
	private JLabel user4, score4;
	private JLabel user5, score5;
	private JLabel user6, score6;
	private JLabel user7, score7;
	private JLabel user8, score8;
	private JLabel user9, score9;
	private JLabel user10, score10;
	private JLabel title;
	
	private JButton goBack;
	
	private Leaderboard leaderboard;
	
	private BufferedImage img;
	
	/**
	 * Constructs a new Leaderboard Panel with all GUI elements defined with default parameters
	 * 
	 * @param listener an ActionListener which is applied to all GUI elements (Buttons) which may trigger an user event
	 * @param leaderboard a Leaderboard object which will calculate, keep track, and update the top 10 players
	 * 		  
	 */
	public LeaderboardPanel(ActionListener listener, Leaderboard leaderboard){
	
		try {
			InputStream in = Bomberman.class.getResourceAsStream("/bomber2.png");
		      img = ImageIO.read(in);
		    } catch(IOException e) {
		      e.printStackTrace();
		    }
		
		user1 = new JLabel("A");
		user1.setFont(new Font("Eurostile", 1, 16));
		user1.setForeground(Color.white);
		
		user2 = new JLabel("A");
		user2.setFont(new Font("Eurostile", 1, 16));
		user2.setForeground(Color.white);
		
		user3 = new JLabel("A");
		user3.setFont(new Font("Eurostile", 1, 16));
		user3.setForeground(Color.white);
		
		user4 = new JLabel("A");
		user4.setFont(new Font("Eurostile", 1, 16));
		user4.setForeground(Color.white);
		
		user5 = new JLabel("A");
		user5.setFont(new Font("Eurostile", 1, 16));
		user5.setForeground(Color.white);
		
		user6 = new JLabel("A");
		user6.setFont(new Font("Eurostile", 1, 16));
		user6.setForeground(Color.white);
		
		user7 = new JLabel("A");
		user7.setFont(new Font("Eurostile", 1, 16));
		user7.setForeground(Color.white);
		
		user8 = new JLabel("A");
		user8.setFont(new Font("Eurostile", 1, 16));
		user8.setForeground(Color.white);
		
		user9 = new JLabel("A");
		user9.setFont(new Font("Eurostile", 1, 16));
		user9.setForeground(Color.white);
		
		user10 = new JLabel(" ");
		user10.setFont(new Font("Eurostile", 1, 16));
		user10.setForeground(Color.white);
		
		score1 = new JLabel("000");
		score1.setFont(new Font("Eurostile", 1, 16));
		score1.setForeground(Color.white);
		
		score2 = new JLabel("000");
		score2.setFont(new Font("Eurostile", 1, 16));
		score2.setForeground(Color.white);
		
		score3 = new JLabel("000");
		score3.setFont(new Font("Eurostile", 1, 16));
		score3.setForeground(Color.white);
		
		score4 = new JLabel("000");
		score4.setFont(new Font("Eurostile", 1, 16));
		score4.setForeground(Color.white);
		
		score5 = new JLabel("000");
		score5.setFont(new Font("Eurostile", 1, 16));
		score5.setForeground(Color.white);
		
		score6 = new JLabel("000");
		score6.setFont(new Font("Eurostile", 1, 16));
		score6.setForeground(Color.white);
		
		score7 = new JLabel("000");
		score7.setFont(new Font("Eurostile", 1, 16));
		score7.setForeground(Color.white);
		
		score8 = new JLabel("000");
		score8.setFont(new Font("Eurostile", 1, 16));
		score8.setForeground(Color.white);
		
		score9 = new JLabel("000");
		score9.setFont(new Font("Eurostile", 1, 16));
		score9.setForeground(Color.white);
		
		score10 = new JLabel("   ");
		score10.setFont(new Font("Eurostile", 1, 16));
		score10.setForeground(Color.white);
		
		title = new JLabel();
        title.setFont(new Font("Eurostile", 1, 28));
        title.setForeground(new Color(255, 255, 255));
        title.setText("Top 10 Players");
		
        goBack = new JButton();
        goBack.setText("Return");
        goBack.addActionListener(listener);
        
        this.leaderboard = leaderboard;
		setupLayout();
	}
	
	 protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    // paint the background image and scale it to fill the entire space
		    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	 }
	
	 
	/**
	 * @return JButton ID of the "Return" button
	 */
	public JButton getReturnButton(){
		return goBack;
	}
	
	/**
	 * Checks database for any changes and updates the Leaderboard Panel to display the current top 10 highest scoring players
	 */
	public void updateLeaderboard(){
		leaderboard.updateLeaderboard();
		
		user1.setText(leaderboard.names[0]);
		user2.setText(leaderboard.names[1]);
		user3.setText(leaderboard.names[2]);
		user4.setText(leaderboard.names[3]);
		user5.setText(leaderboard.names[4]);
		user6.setText(leaderboard.names[5]);
		user7.setText(leaderboard.names[6]);
		user8.setText(leaderboard.names[7]);
		user9.setText(leaderboard.names[8]);
		user10.setText(leaderboard.names[9]);
		
		score1.setText(String.valueOf(leaderboard.scores[0]));
		score2.setText(String.valueOf(leaderboard.scores[1]));
		score3.setText(String.valueOf(leaderboard.scores[2]));
		score4.setText(String.valueOf(leaderboard.scores[3]));
		score5.setText(String.valueOf(leaderboard.scores[4]));
		score6.setText(String.valueOf(leaderboard.scores[5]));
		score7.setText(String.valueOf(leaderboard.scores[6]));
		score8.setText(String.valueOf(leaderboard.scores[7]));
		score9.setText(String.valueOf(leaderboard.scores[8]));
		score10.setText(String.valueOf(leaderboard.scores[9]));
	}
	
	
	private void setupLayout(){	
		GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addGap(150, 150, 150)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(goBack)
                        .addComponent(title)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(user1)
                            .addGap(77, 77, 77)
                            .addComponent(score1))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(user2)
                            .addGap(77, 77, 77)
                            .addComponent(score2))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(user3)
                            .addGap(77, 77, 77)
                            .addComponent(score3))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(user4)
                            .addGap(77, 77, 77)
                            .addComponent(score4))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(user5)
                            .addGap(77, 77, 77)
                            .addComponent(score5))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(user6)
                            .addGap(77, 77, 77)
                            .addComponent(score6))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(user7)
                            .addGap(77, 77, 77)
                            .addComponent(score7))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(user8)
                            .addGap(77, 77, 77)
                            .addComponent(score8))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(user9)
                            .addGap(77, 77, 77)
                            .addComponent(score9))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(user10)
                            .addGap(77, 77, 77)
                            .addComponent(score10)))
                    .addContainerGap(119, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(user1)
                        .addComponent(score1))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(user2)
                        .addComponent(score2))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(user3)
                        .addComponent(score3))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(user4)
                        .addComponent(score4))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(user5)
                        .addComponent(score5))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(user6)
                        .addComponent(score6))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(user7)
                        .addComponent(score7))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(user8)
                        .addComponent(score8))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(user9)
                        .addComponent(score9))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(user10)
                        .addComponent(score10))
                    .addGap(18, 18, 18)
                    .addComponent(goBack)
                    .addGap(40, 40, 40))
            );
	}
}
