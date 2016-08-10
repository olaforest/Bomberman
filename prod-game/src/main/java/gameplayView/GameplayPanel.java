package gameplayView;

import gameplayModel.GridMap;
import gameplayModel.GridObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class GameplayPanel extends JPanel {

	public static final int WIDTH = GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH;
	public static final int HEIGHT = (GridMap.MAPHEIGHT) * GridObject.EFFECTIVE_PIXEL_HEIGHT;

	public GameplayPanel(KeyListener keyListener) {
		setBackground(new Color(31, 139, 0));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		addKeyListener(keyListener);
	}
}
