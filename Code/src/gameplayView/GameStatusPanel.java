package gameplayView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gameplayController.GameplayController;
import gameplayModel.GameContext;
import gameplayModel.GridObject;

/**
 * This class generates the panel where the game time, score and lives left of the player are displayed over the game panel.
 * 
 * @author Olivier Laforest
 *
 */
@SuppressWarnings("serial")
public class GameStatusPanel extends JPanel {
	
	public static final int HEADERHEIGHT = GridObject.EFFECTIVE_PIXEL_HEIGHT/2 + GridObject.EFFECTIVE_PIXEL_HEIGHT;
	
	private int stdHeight;
	
	private JLabel timeLabel, timeNumLabel, scoreLabel, livesLeftLabel, livesLeftNumLabel;
	
	private GameContext gameContext;
	
	/**
	 * This constructor sets up the different panel GUI elements as well as their placement, text dimension, font and color.
	 * 
	 * @param context
	 */
	public GameStatusPanel(GameContext context) {
		
		gameContext = context;
		
		timeLabel = new JLabel("TIME");
		timeLabel.setFont(new Font("Monospace 821 BT", 0, 28));
		
		timeNumLabel = new JLabel(Integer.toString(gameContext.getGameTime()/1000));
		timeNumLabel.setFont(new Font("Monospace 821 BT", 0, 28));
		
		scoreLabel = new JLabel(Integer.toString(gameContext.getScore()));
		scoreLabel.setFont(new Font("Monospace 821 BT", 0, 28));
		
		livesLeftLabel = new JLabel("LEFT ");
		livesLeftLabel.setFont(new Font("Monospace 821 BT", 0, 28));
		
		livesLeftNumLabel = new JLabel(Integer.toString(gameContext.getLivesLeft()));
		livesLeftNumLabel.setFont(new Font("Monospace 821 BT", 0, 28));
		
		stdHeight = (HEADERHEIGHT - timeLabel.getPreferredSize().height)/2;
		
		timeLabel.setBounds(GridObject.EFFECTIVE_PIXEL_WIDTH/2, stdHeight, timeLabel.getPreferredSize().width + 10, timeLabel.getPreferredSize().height);

		timeNumLabel.setBounds(((int) timeLabel.getLocation().getX()) + timeLabel.getWidth() + 55 - timeNumLabel.getPreferredSize().width,
				stdHeight, timeNumLabel.getPreferredSize().width, timeNumLabel.getPreferredSize().height);
		
		scoreLabel.setBounds(325 - scoreLabel.getPreferredSize().width, stdHeight, scoreLabel.getPreferredSize().width, scoreLabel.getPreferredSize().height);
		
		livesLeftLabel.setBounds(370, stdHeight, livesLeftLabel.getPreferredSize().width, livesLeftLabel.getPreferredSize().height);
		
		livesLeftNumLabel.setBounds(GameplayController.VIEW_PORT_WIDTH - GridObject.EFFECTIVE_PIXEL_WIDTH/2 - livesLeftNumLabel.getPreferredSize().width,
				stdHeight, livesLeftNumLabel.getPreferredSize().width, livesLeftNumLabel.getPreferredSize().height);
		
		setBackground(new Color(173, 174, 173));
		setPreferredSize(new Dimension(GameplayController.VIEW_PORT_WIDTH, HEADERHEIGHT));
		setLayout(null);
		
		add(timeLabel);
		add(timeNumLabel);
		add(scoreLabel);
		add(livesLeftLabel);
		add(livesLeftNumLabel);
	}
	
	/**
	 * This method updates the game time, the game score and the number of lives left with the new values coming from the gameContext.
	 */
	public void updateGameStatus() {
		
		timeNumLabel.setText(Integer.toString(gameContext.getGameTime()/1000));
		timeNumLabel.setBounds(((int) timeLabel.getLocation().getX()) + timeLabel.getWidth() + 55 - timeNumLabel.getPreferredSize().width,
				stdHeight, timeNumLabel.getPreferredSize().width, timeNumLabel.getPreferredSize().height);
		
		scoreLabel.setText(Integer.toString(gameContext.getScore()));
		scoreLabel.setBounds(325 - scoreLabel.getPreferredSize().width, stdHeight, scoreLabel.getPreferredSize().width, scoreLabel.getPreferredSize().height);
		
		livesLeftNumLabel.setText(Integer.toString(gameContext.getLivesLeft()));
		livesLeftNumLabel.setBounds(GameplayController.VIEW_PORT_WIDTH - GridObject.EFFECTIVE_PIXEL_WIDTH/2 - livesLeftNumLabel.getPreferredSize().width,
				stdHeight, livesLeftNumLabel.getPreferredSize().width, livesLeftNumLabel.getPreferredSize().height);
	}
}
