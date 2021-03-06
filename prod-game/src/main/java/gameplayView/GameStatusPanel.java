package gameplayView;

import gameplayController.GameplayController;
import gameplayModel.GameContext;

import javax.swing.*;
import java.awt.*;

import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;

public class GameStatusPanel extends JPanel {

	public static final int HEADERHEIGHT = EFFECTIVE_PIXEL_DIM / 2 + EFFECTIVE_PIXEL_DIM;
	private final int stdHeight;
	private final JLabel timeLabel;
	private final JLabel timeNumLabel;
	private final JLabel scoreLabel;
	private final JLabel livesLeftNumLabel;
	private final GameContext gameContext;

	public GameStatusPanel(GameContext context) {

		gameContext = context;

		timeLabel = new JLabel("TIME");
		timeLabel.setFont(new Font("Monospace 821 BT", 0, 28));

		timeNumLabel = new JLabel(Integer.toString(gameContext.getGameTime() / 1000));
		timeNumLabel.setFont(new Font("Monospace 821 BT", 0, 28));

		scoreLabel = new JLabel(Integer.toString(gameContext.getScore()));
		scoreLabel.setFont(new Font("Monospace 821 BT", 0, 28));

		JLabel livesLeftLabel = new JLabel("LEFT ");
		livesLeftLabel.setFont(new Font("Monospace 821 BT", 0, 28));

		livesLeftNumLabel = new JLabel(Integer.toString(gameContext.getLivesLeft()));
		livesLeftNumLabel.setFont(new Font("Monospace 821 BT", 0, 28));

		stdHeight = (HEADERHEIGHT - timeLabel.getPreferredSize().height) / 2;

		timeLabel.setBounds(EFFECTIVE_PIXEL_DIM / 2, stdHeight, timeLabel.getPreferredSize().width + 10, timeLabel.getPreferredSize().height);

		timeNumLabel.setBounds(((int) timeLabel.getLocation().getX()) + timeLabel.getWidth() + 55 - timeNumLabel.getPreferredSize().width,
				stdHeight, timeNumLabel.getPreferredSize().width, timeNumLabel.getPreferredSize().height);

		scoreLabel.setBounds(325 - scoreLabel.getPreferredSize().width, stdHeight, scoreLabel.getPreferredSize().width, scoreLabel.getPreferredSize().height);

		livesLeftLabel.setBounds(370, stdHeight, livesLeftLabel.getPreferredSize().width, livesLeftLabel.getPreferredSize().height);

		livesLeftNumLabel.setBounds(GameplayController.VIEW_PORT_WIDTH - EFFECTIVE_PIXEL_DIM / 2 - livesLeftNumLabel.getPreferredSize().width,
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

	public void updateGameStatus() {
		timeNumLabel.setText(Integer.toString(gameContext.getGameTime() / 1000));
		timeNumLabel.setBounds(((int) timeLabel.getLocation().getX()) + timeLabel.getWidth() + 55 - timeNumLabel.getPreferredSize().width,
				stdHeight, timeNumLabel.getPreferredSize().width, timeNumLabel.getPreferredSize().height);

		scoreLabel.setText(Integer.toString(gameContext.getScore()));
		scoreLabel.setBounds(325 - scoreLabel.getPreferredSize().width, stdHeight, scoreLabel.getPreferredSize().width, scoreLabel.getPreferredSize().height);

		livesLeftNumLabel.setText(Integer.toString(gameContext.getLivesLeft()));
		livesLeftNumLabel.setBounds(GameplayController.VIEW_PORT_WIDTH - EFFECTIVE_PIXEL_DIM / 2 - livesLeftNumLabel.getPreferredSize().width,
				stdHeight, livesLeftNumLabel.getPreferredSize().width, livesLeftNumLabel.getPreferredSize().height);
	}
}
