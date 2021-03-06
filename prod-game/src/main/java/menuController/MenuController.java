package menuController;

import gameplayController.GameplayController;
import gameplayModel.LevelManager;
import gameplayModel.gridObjects.animatedObjects.Bomb;
import menuModel.Leaderboard;
import menuModel.Player;
import menuModel.SavedGame;
import menuView.*;
import storage.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuController implements ActionListener {

	private Player currentPlayer;
	private GameplayController gameplayCtrl;

	private final JFrame menuFrame;
	private final JPanel mainPanel;
	private final CardLayout layout;
	private final LoginMenuPanel loginPanel;
	private final CreateAccountPanel createAccPanel;
	private final LeaderboardPanel leaderboard;
	private final MainMenuPanel mainMenuPanel;
	private final Leaderboard updater;
	private final PauseMenuPanel pauseMenuPanel;
	private final LoadGamePanel loadGamePanel;
	private final OptionsPanel optionsPanel;

	private final Database database;

	private int leaderboardReturn, loadGameReturn, chosenLevel;
	private int prePauseScore;

	public MenuController() {

		database = new Database();

		setupLookAndFeel();

		layout = new CardLayout();

		updater = new Leaderboard(database);

		loginPanel = new LoginMenuPanel(this);
		createAccPanel = new CreateAccountPanel(this);
		mainMenuPanel = new MainMenuPanel(this);
		leaderboard = new LeaderboardPanel(this, updater);
		pauseMenuPanel = new PauseMenuPanel(this);
		loadGamePanel = new LoadGamePanel(this);
		optionsPanel = new OptionsPanel(this);

		mainPanel = new JPanel();
		mainPanel.setLayout(layout);
		mainPanel.add(loginPanel, "Login");
		mainPanel.add(createAccPanel, "Create");
		mainPanel.add(mainMenuPanel, "Main");
		mainPanel.add(leaderboard, "Leaderboard");
		mainPanel.add(pauseMenuPanel, "Pause");
		mainPanel.add(optionsPanel, "Options");
		mainPanel.add(loadGamePanel, "LoadGame");


		menuFrame = new JFrame("Bomberman");
		menuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		menuFrame.getContentPane().add(mainPanel);
		menuFrame.pack();
		menuFrame.setLocation(400, 200);
		menuFrame.setVisible(true);

		leaderboardReturn = 0;
		loadGameReturn = 0;
		chosenLevel = 0;

		prePauseScore = 0;
	}


	public void actionPerformed(ActionEvent event) {

//***********************Login Panel Buttons****************************
/* If the event source is from the "Login" button: The Text fields are checked to see if there is an existing user that matches the user input
 * 												   If the text fields are valid, display panel is changed to the Main Menu Panel
 * If the event source is from the "Create Account" button: The display panel is changed to the Create Account Panel
 */
		if (event.getSource() == loginPanel.getLoginButton()) {

			if ((currentPlayer = database.getPlayer(loginPanel.getUsername(), loginPanel.getPassword())) != null)
				layout.show(mainPanel, "Main");
			else {
				JOptionPane.showMessageDialog(null, "Incorrect Password Or Username", null, JOptionPane.ERROR_MESSAGE);
				loginPanel.resetTextFields();
			}
		} else if (event.getSource() == loginPanel.getCreateAccButton()) {
			layout.show(mainPanel, "Create");

//*********************Create Account buttons****************************	
/* If the event source is from the "Create Account" button: Textfield input is stored and passed to verifyAccount()
 * If the event source is from the "Return to Login" button: The display panel is changed to the Login Panel			
 */
		} else if (event.getSource() == createAccPanel.getBackButton()) {
			layout.show(mainPanel, "Login");

		} else if (event.getSource() == createAccPanel.getCreateAccButton()) {
			verifyNewAccount();

//*************************MainMenu Buttons******************************	
/* If the event source is from the "New Game" button: The current player's unlocked level is checked. If 0, a new game
 * 													  at level one is created. Else, an OptionPane is generated which
 * 												      prompts the user to select the desired starting level.
 * If the event source is from the "Resume Game" button: The display panel is changed to the Load Game Panel. loadGameReturn
 * 														 is set to 0 and treated as a flag to keep track of previous menu.
 * If the event source is from the "Leaderboard" button: The display panel is changed to the Leaderboard Panel. leaderboardReturn
 * 													     is set to 0 and treated as a flag to keep track of previous menu.
 * If the event source is from the "Account Options" button: The display panel is changed to the Options Panel.
 * If the event source is from the "Logout" button: the current player's data is written to the CSV and the display panel
 * 													is changed to the Login Menu Panel.
 * If the event source is from the "Exit" button: the current player's data is written to the CSV and the game exits
 * 
 */
		} else if (event.getSource() == mainMenuPanel.getResumeButton()) {
			loadGamePanel.updateLoad(currentPlayer);
			layout.show(mainPanel, "LoadGame");
			loadGameReturn = 0;

		} else if (event.getSource() == mainMenuPanel.getNewGameButton()) {

			Bomb.resetRange();
			int maxLevel = currentPlayer.getLevelUnlocked();
			if (maxLevel != 0) {
				String[] levelArray = new String[maxLevel];
				for (int i = 0; i < maxLevel; i++)
					levelArray[i] = String.valueOf(i + 1);
				String selectedLevel = (String) JOptionPane.showInputDialog(null, "Level Select", "Choose Starting Level:", JOptionPane.QUESTION_MESSAGE, null, levelArray, levelArray[0]);
				chosenLevel = Integer.parseInt(selectedLevel) - 1;
			} else
				chosenLevel = 0;
			menuFrame.setVisible(false);
			gameplayCtrl = new GameplayController();

		} else if (event.getSource() == mainMenuPanel.getLeaderboardButton()) {
			leaderboard.updateLeaderboard();
			layout.show(mainPanel, "Leaderboard");
			leaderboardReturn = 0;

		} else if (event.getSource() == mainMenuPanel.getOptionsButton()) {
			layout.show(mainPanel, "Options");

		} else if (event.getSource() == mainMenuPanel.getLogoutButton()) {
			database.generateCSV();
			currentPlayer = null;
			layout.show(mainPanel, "Login");
			loginPanel.resetTextFields();

		} else if (event.getSource() == mainMenuPanel.getExitButton()) {
			database.generateCSV();
			System.exit(0);

//************************Leaderboard buttons**************************
/* When "Return" is pressed, the value of leaderboardReturn is checked. If 0, the display panel is changed to
 * the Main Menu. If 1, the display panel is changed to the Pause Menu.
 * 
 */
		} else if (event.getSource() == leaderboard.getReturnButton()) {
			if (leaderboardReturn == 0)
				layout.show(mainPanel, "Main");
			else if (leaderboardReturn == 1)
				layout.show(mainPanel, "Pause");

//***************************Load buttons*******************************	
/* If the event source is from the "Return" button: loadGameReturn is checked and the display panel is changed 
 * 													depending on the value
 * If the event source is from the "Load Game" button: the index of the highlighted save is checked and the corresponding
 * 													   game save number is generated by hiding the MenuFrame and generating the 
 * 													   correct GameplayFrame
 */
		} else if (event.getSource() == loadGamePanel.getReturn()) {
			if (loadGameReturn == 0)
				layout.show(mainPanel, "Main");
			else if (loadGameReturn == 1)
				layout.show(mainPanel, "Pause");

		} else if (event.getSource() == loadGamePanel.getLoad()) {
			menuFrame.setVisible(false);
			final SavedGame savedGame = currentPlayer.getSavedGameList().get(loadGamePanel.getSaveIndex());
			gameplayCtrl = new GameplayController(savedGame.getGameContext(), new LevelManager(savedGame.getLevelIndex()));
			prePauseScore = gameplayCtrl.getGameContext().getScore();
			gameplayCtrl.resumeGame();

//****************************Pause buttons****************************
/*  This menu is only accessed from the gameplay when the user presses the pause key (ENTER). Upon press, the GameplayFrame is 
 * 	set not visible and the MenuFrame is set active.
 *  
 *  If the event source is from the "Resume Game" button: MenuFrame is set not visible and game is resumed
 *  If the event source is from the "Load Game" button: The display panel is changed to the Load Game Panel.
 *  If the event source is from the "Save Game" button: A OptionPane is generated and prompts user for save game name
 *  If the event source is from the "Leaderboard" button: The display panel is changed to the Leaderboard Panel.
 *  If the event source is from the "Return to Main Menu" button: The current player's data is saved to the CSV and the display
 *  															  panel is changed to the Main Menu.
 *  If the event source is from the "Exit" button: The current player's data is saved to the CSV and the application is closed
 */
		} else if (event.getSource() == pauseMenuPanel.getResumeGame()) {
			menuFrame.setVisible(false);
			gameplayCtrl.resumeGame();

		} else if (event.getSource() == pauseMenuPanel.getSave()) {
			String saveName = JOptionPane.showInputDialog(null, "Enter Save Name: ", "Save Game", JOptionPane.QUESTION_MESSAGE);
			currentPlayer.addSavedGame(new SavedGame(saveName, new Date().toString(), gameplayCtrl.getGameContext()));

		} else if (event.getSource() == pauseMenuPanel.getExit()) {
			database.generateCSV();
			System.exit(0);

		} else if (event.getSource() == pauseMenuPanel.getMainMenu()) {
			int choice = JOptionPane.showConfirmDialog(null, "All unsaved progress will be lost. Continue?", null, JOptionPane.OK_CANCEL_OPTION);
			if (choice == 0) {
				database.generateCSV();
				layout.show(mainPanel, "Main");
			}

		} else if (event.getSource() == pauseMenuPanel.getLeaderboard()) {
			leaderboard.updateLeaderboard();
			layout.show(mainPanel, "Leaderboard");
			leaderboardReturn = 1;

		} else if (event.getSource() == pauseMenuPanel.getLoad()) {
			loadGamePanel.updateLoad(currentPlayer);
			layout.show(mainPanel, "LoadGame");
			loadGameReturn = 1;
		}

//************************Options Buttons*************************************
/* If the event source is from the "Save and Return" button: Textfield input is checked for validity. If invalid, warning message 
 * 															 is generated. Else, current player's information is updated and saved
 * 
 * If the event event source is from the "Return to Main Menu" button: The display panel is changed to the Main Menu Panel
 * 
 */
		else if (event.getSource() == optionsPanel.getSaveButton()) {
			if (checkPassword(optionsPanel.getNewPassword())) {
				JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long and include the following: a digit, an uppercase and lowercase character, and a special character", null, JOptionPane.INFORMATION_MESSAGE);
				optionsPanel.resetTextFields();
			} else if (!optionsPanel.getNewPassword().equals(optionsPanel.getNewConfirmPassword())) {
				JOptionPane.showMessageDialog(null, "Confirmed password does not match", null, JOptionPane.ERROR_MESSAGE);
				optionsPanel.resetTextFields();
			} else {
				currentPlayer.setRealName(optionsPanel.getNewName());
				currentPlayer.setPassword(optionsPanel.getNewPassword());
				JOptionPane.showMessageDialog(null, "Account Updated!", null, JOptionPane.INFORMATION_MESSAGE);
				layout.show(mainPanel, "Main");
			}
		} else if (event.getSource() == optionsPanel.getReturnButton()) {
			layout.show(mainPanel, "Main");
		}
	}


	private void verifyNewAccount() {

		if (!checkUser(createAccPanel.getUsername())) {
			JOptionPane.showMessageDialog(null, "Username must be at least 6 characters and consist of only digits and characters", null, JOptionPane.INFORMATION_MESSAGE);
		} else if (checkPassword(createAccPanel.getPassword()))
			JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long and include the following: a digit, an uppercase and lowercase character, and a special character", null, JOptionPane.INFORMATION_MESSAGE);
		else if (!createAccPanel.getPassword().equals(createAccPanel.getConfirmedPassword()))
			JOptionPane.showMessageDialog(null, "Confirmed password does not match", null, JOptionPane.ERROR_MESSAGE);
		else {
			//New player is generated and added to the database. If the user already exists, database returns null.
			if (database.addPlayer(new Player(createAccPanel.getRealName(), createAccPanel.getUsername(), createAccPanel.getPassword())) == null) {
				JOptionPane.showMessageDialog(null, "Username already exists", null, JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Account Created!", null, JOptionPane.INFORMATION_MESSAGE);
				currentPlayer = database.getPlayer(createAccPanel.getUsername(), createAccPanel.getPassword());
				layout.show(mainPanel, "Main");
			}
		}
		createAccPanel.resetTextFields();
	}

	//Checks any username input for validity(at least 6 characters; can only contain digits and letters)
	private boolean checkUser(String user) {
		Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(user);
		boolean b = m.find();
		return (!b && !(user.length() < 6));
	}

	//Checks any password input for validity(at least 8 characters and contains: one upper and one lower-case letter, one digit, one special character)
	private boolean checkPassword(String password) {
		if (!password.matches(".*[A-Z].*")) return true;

		if (!password.matches(".*[a-z].*")) return true;

		if (!password.matches(".*\\d.*")) return true;

		Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(password);
		boolean b = m.find();
		return !b || password.length() < 8;
	}

	/**
	 * Pauses current game by stopping the game timer
	 */
	public void pause() {
		menuFrame.setVisible(true);
		layout.show(mainPanel, "Pause");
		int newPauseScore = gameplayCtrl.getGameContext().getScore();
		currentPlayer.updateScore(newPauseScore - prePauseScore);
		prePauseScore = newPauseScore;
	}

	/**
	 * Ends the current game and returns to the Main Menu Panel
	 */
	public void gameOver() {
		menuFrame.setVisible(true);
		layout.show(mainPanel, "Main");
		int newPauseScore = gameplayCtrl.getGameContext().getScore();
		currentPlayer.updateScore(newPauseScore - prePauseScore);
		prePauseScore = 0;
	}

	/**
	 * @return The selected level of a new game by the current player
	 */
	public int getSelectedLevel() {
		return chosenLevel;
	}

	/**
	 * @return The current logged in player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}


	private void setupLookAndFeel() {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MenuController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}
}

