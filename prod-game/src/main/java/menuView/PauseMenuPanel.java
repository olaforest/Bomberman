package menuView;

import gameplayModel.gridObjects.animatedObjects.Bomberman;

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
 */
@SuppressWarnings("serial")
public class PauseMenuPanel extends JPanel {


	private final JButton resume;
	private final JButton save;
	private final JButton load;
	private final JButton leaderboard;
	private final JButton mainMenu;
	private final JButton exit;
	private final JLabel gamePaused;
	private BufferedImage img;

	/**
	 * Constructs a new Pause Menu Panel with all GUI elements defined with default parameters
	 *
	 * @param listener an ActionListener which is applied to all GUI elements (Buttons) which may trigger an user event
	 */
	public PauseMenuPanel(ActionListener listener) {

		try {
			InputStream in = Bomberman.class.getResourceAsStream("/bomber2.png");
			img = ImageIO.read(in);
		} catch (IOException e) {
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

		mainMenu = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		mainMenu.setText("Return to Main Menu");
		mainMenu.addActionListener(listener);

		exit = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		exit.setText("Exit Game");
		exit.addActionListener(listener);

		gamePaused = new JLabel();
		gamePaused.setFont(new java.awt.Font("Eurostile", Font.BOLD, 28)); // NOI18N
		gamePaused.setText("Game Paused");

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
	public JButton getResumeGame() {
		return resume;
	}

	/**
	 * @return JButton ID of the "Save Game" button
	 */
	public JButton getSave() {
		return save;
	}

	/**
	 * @return JButton ID of the "Load Game" button
	 */
	public JButton getLoad() {
		return load;
	}

	/**
	 * @return JButton ID of the "Leaderboard" button
	 */
	public JButton getLeaderboard() {
		return leaderboard;
	}

	/**
	 * @return JButton ID of the "Return to Main Menu" button
	 */
	public JButton getMainMenu() {
		return mainMenu;
	}

	/**
	 * @return JButton ID of the "Exit Game" button
	 */
	public JButton getExit() {
		return exit;
	}


	private void setupLayout() {
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
										.addComponent(mainMenu)
										.addComponent(leaderboard)
										.addComponent(exit)
										.addComponent(gamePaused))
								.addGap(150, 150, 150))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
						.addGroup(layout.createSequentialGroup()
								.addGap(90, 90, 90)
								.addComponent(gamePaused)
								.addGap(18, 18, 18)
								.addComponent(resume)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(save)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(load)
								.addGap(18, 18, 18)
								.addComponent(leaderboard)
								.addGap(18, 18, 18)
								.addComponent(mainMenu)
								.addGap(18, 18, 18)
								.addComponent(exit)
								.addContainerGap(49, Short.MAX_VALUE))
		);
	}
}
