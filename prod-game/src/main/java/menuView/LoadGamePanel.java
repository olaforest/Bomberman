package menuView;

import gameplayModel.GridObjects.AnimatedObjects.Bomberman;
import menuModel.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


/**
 * This class creates the Load Game Panel, and generates all 
 * GUI elements within the panel. This includes a list of all saved games for the current user, 
 * as well as information (save name, level, date saved) about the highlighted saved game. Buttons for
 * navigation include "Load Game", which will generate the appropriate gameContext, and "Return" which will return
 * the user to the previous menu. Load Game can be accessed from the Pause Menu or Main Menu.
 * 
 * @author Eric Liou
 *
 */
@SuppressWarnings("serial")
public class LoadGamePanel extends JPanel {

	@SuppressWarnings("rawtypes")
	private JList list;
	private JButton goBack, loadGame;
	private JLabel savename, level, datesaved; //headers
	private JLabel name, levelNumber, date; //variable
	private JScrollPane listScroller;
	private DefaultListModel<String> model;
	private Player currentPlayer;
	private int index;
	private BufferedImage img;
	
	
	/**
	 * Constructs a new Load Game Panel with all GUI elements defined with default parameters
	 * 
	 * @param listener an ActionListener which is applied to all GUI elements (List, Buttons) which may trigger an user event
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LoadGamePanel(ActionListener listener){
	
		try {
			InputStream in = Bomberman.class.getResourceAsStream("/bomber2.png");
		      img = ImageIO.read(in);
		    } catch(IOException e) {
		      e.printStackTrace();
		    }
		
		ListSelectionListener listSelectionListener = new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent evt){
				index = evt.getLastIndex();
				name.setText(currentPlayer.getSavedGameList().get(index).getGameName());
				date.setText(currentPlayer.getSavedGameList().get(index).getGameDate());
				levelNumber.setText(Integer.toString((currentPlayer.getSavedGameList().get(index).getGameContext().getLevel())+1));
			}
		};
		
		model = new DefaultListModel<>();
		
		list = new JList(model);
		list.setModel(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.addListSelectionListener(listSelectionListener);
		
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 130));
		
		goBack = new JButton("Return");
		goBack.addActionListener(listener);
		
		loadGame = new JButton("Load Game");
		loadGame.addActionListener(listener);
		
		savename = new JLabel();
        savename.setFont(new Font("Eurostile", Font.BOLD, 15)); // NOI18N
        savename.setText("Save Name:");
        savename.setForeground(Color.white);
        
		level = new JLabel();
        level.setFont(new Font("Eurostile", Font.BOLD, 15)); // NOI18N
        level.setText("Level:");
        level.setForeground(Color.white);
        
		datesaved = new JLabel();
		datesaved.setFont(new Font("Eurostile", Font.BOLD, 15)); // NOI18N
		datesaved.setText("Date Saved:");
		datesaved.setForeground(Color.white);
		
		levelNumber = new JLabel();
		levelNumber.setFont(new Font("Eurostile", 0, 13));
		levelNumber.setText("          ");
		levelNumber.setForeground(Color.white);
		
		name = new JLabel();
		name.setFont(new Font("Eurostile", 0, 13));
		name.setText("      ");
		name.setForeground(Color.white);
		
		date = new JLabel();
		date.setFont(new Font("Eurostile", 0, 13));
		date.setText("                                  ");
		date.setForeground(Color.white);
		
		setupLayout();
		
	}
	
	 protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    // paint the background image and scale it to fill the entire space
		    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	 }
	
	 
	/**
	 * Checks for any new saved games and generates list saved games for current player
	 * 
	 * @param currentPlayer the currently logged in player which contains an ArrayList of all saved games of respective player
	 */
	@SuppressWarnings("unchecked")
	public void updateLoad(Player currentPlayer){
		this.currentPlayer=currentPlayer;
		model.clear();
		for(int i = 0; i < currentPlayer.getSavedGameList().size() ; i++)
			model.addElement(currentPlayer.getSavedGameList().get(i).getGameName());
		list.setModel(model);
	}
	
	/**
	 * @return JButton source ID of the "Return" button
	 */
	public JButton getReturn(){
		return goBack;
	}
	
	/**
	 * @return JButton source ID of the "Load Game" button
	 */
	public JButton getLoad(){
		return loadGame;
	}
	
	/**
	 * @return index of highlighted saved game from the saved game list
	 */
	public int getSaveIndex(){
		return index;
	}
	
	private void setupLayout(){
		GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(listScroller, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(savename)
                            .addComponent(datesaved)
                            .addComponent(levelNumber)
                            .addComponent(date)
                            .addComponent(name)
                            .addComponent(level)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(loadGame)
                            .addComponent(goBack)))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(130, 130, 130))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGap(183, 183, 183))
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(listScroller, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(savename)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(level)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(levelNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datesaved)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(date)))
                .addGap(30, 30, 30)
                .addComponent(loadGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(goBack)
                .addGap(91, 91, 91))
        );
		
	}
}
