package gameplayView;

import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;

import gameplayModel.GridMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class GameplayPanel extends JPanel {

	public static final int WIDTH = GridMap.MAP_WIDTH * EFFECTIVE_PIXEL_DIM;
	public static final int HEIGHT = (GridMap.MAP_HEIGHT) * EFFECTIVE_PIXEL_DIM;

	public GameplayPanel(KeyListener keyListener) {
		setBackground(new Color(31, 139, 0));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		addKeyListener(keyListener);
	}
}
