package menuView;

import gameplayModel.GridObjects.AnimatedObjects.Bomberman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class creates the Main Menu Panel, and generates all
 * GUI elements within the panel. This includes all buttons for navigating through the game.
 *
 * @author Eric Liou
 */
@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel {


	private JButton resume, newGame, leaderboard, logout, exit, edit;
	private JLabel bombermanLabel;
	private BufferedImage img;

	/**
	 * Constructs a new Main Menu Panel with all GUI elements defined with default parameters
	 *
	 * @param listener an ActionListener which is applied to all GUI elements (Buttons) which may trigger an user event
	 */
	public MainMenuPanel(ActionListener listener) {

		try {
			InputStream in = Bomberman.class.getResourceAsStream("/bomber2.png");
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		resume = new JButton("Resume Game");
		resume.addActionListener(listener);

		newGame = new JButton("New Game");
		newGame.addActionListener(listener);

		leaderboard = new JButton("Leaderboard");
		leaderboard.addActionListener(listener);

		logout = new JButton("Logout");
		logout.addActionListener(listener);

		exit = new JButton("Exit");
		exit.addActionListener(listener);

		edit = new JButton("Account Options");
		edit.addActionListener(listener);

		bombermanLabel = new JLabel("Bomberman");
		bombermanLabel.setFont(new Font("Eurostile", Font.BOLD, 48)); // NOI18N
		bombermanLabel.setForeground(Color.white);


		setupLayout();
		setBackground(Color.black);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// paint the background image and scale it to fill the entire space
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}


	private void setupLayout() {

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addContainerGap(106, Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
														.addComponent(newGame, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(resume, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(leaderboard, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(edit, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(logout, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(exit, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
												.addGap(160, 160, 160))
										.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
												.addComponent(bombermanLabel)
												.addGap(80, 80, 80))))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addContainerGap(67, Short.MAX_VALUE)
								.addComponent(bombermanLabel)
								.addGap(31, 31, 31)
								.addComponent(newGame, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGap(12, 12, 12)
								.addComponent(resume, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(leaderboard, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(edit, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(logout, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(exit, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGap(89, 89, 89))
		);
	}

	/**
	 * @return JButton source ID of the "Resume Game" button
	 */
	public JButton getResumeButton() {
		return resume;
	}

	/**
	 * @return JButton source ID of the "New Game" button
	 */
	public JButton getNewGameButton() {
		return newGame;
	}

	/**
	 * @return JButton source ID of the "Leaderboard" button
	 */
	public JButton getLeaderboardButton() {
		return leaderboard;
	}

	/**
	 * @return JButton source ID of the "Logout" button
	 */
	public JButton getLogoutButton() {
		return logout;
	}

	/**
	 * @return JButton source ID of the "Exit" button
	 */
	public JButton getExitButton() {
		return exit;
	}

	/**
	 * @return JButton source ID of the "Account Options" button
	 */
	public JButton getOptionsButton() {
		return edit;
	}
}

