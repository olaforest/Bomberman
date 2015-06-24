package gameplayView;

import gameplayModel.GridMap;
import gameplayModel.GridObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;


/**
 * This class generates the panel where the game and all the grid object are displayed.
 * 
 * @author Olivier Laforest
 */
@SuppressWarnings("serial")
public class GameplayPanel extends JPanel {
	
	public static final int WIDTH = GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH;
	public static final int HEIGHT = (GridMap.MAPHEIGHT) * GridObject.EFFECTIVE_PIXEL_HEIGHT;
	
	/**
	 * This method construct the gamepanel.
	 * 
	 * @param keyListener which generates the Key events
	 */
	public GameplayPanel(KeyListener keyListener){
		
		setBackground(new Color(31, 139,0));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		addKeyListener(keyListener);
	}
}
