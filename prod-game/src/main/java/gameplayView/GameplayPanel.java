package gameplayView;

import gameplayModel.GridMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

import static gameplayVisual.ImageManager.EFFECTIVE_PIXEL_DIMENSION;

public class GameplayPanel extends JPanel {

	public static final int WIDTH = GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION;
	public static final int HEIGHT = (GridMap.MAPHEIGHT) * EFFECTIVE_PIXEL_DIMENSION;

	public GameplayPanel(KeyListener keyListener) {
		setBackground(new Color(31, 139, 0));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		addKeyListener(keyListener);
	}
}
