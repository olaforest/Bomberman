package menuView;

import gameplayModel.Bomberman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class creates the Login Menu Panel, and generates all 
 * GUI elements within the panel. This includes text boxes for the user to input their 
 * username and password as well as buttons for logging in or creating a new account. This is 
 * the default starting panel of the game.
 * 
 * @author Eric Liou
 *
 */
@SuppressWarnings("serial")
public class LoginMenuPanel extends JPanel {
	
	private int TEXTFIELD_LENGTH = 15;
	
	private JLabel usernameLabel, passwordLabel, bombermanLabel;
	//private JTextField username, password, confirmPassword;
	private JTextField username;
	private JButton login, createAccount;
	private JPasswordField password;
	private BufferedImage img;
	
	/**
	 * Constructs a new Login Menu Panel with all GUI elements defined with default parameters
	 * 
	 * @param listener an ActionListener which is applied to all GUI elements (Test Fields, Buttons) which may trigger an user event
	 */
	public LoginMenuPanel (ActionListener listener) {
		
		try {
			InputStream in = Bomberman.class.getResourceAsStream("/bomber.png");
		      img = ImageIO.read(in);
		    } catch(IOException e) {
		      e.printStackTrace();
		    }
		
		password = new JPasswordField();
		password.setEchoChar('*');
		password.addActionListener(listener);
		
		usernameLabel = new JLabel("Username: ");
        usernameLabel.setForeground(Color.white);
		
		passwordLabel = new JLabel("Password: ");
        passwordLabel.setForeground(Color.white);
				
		bombermanLabel = new JLabel("Bomberman");
		bombermanLabel.setFont(new Font("Eurostile", 0, 48)); // NOI18N
        bombermanLabel.setForeground(Color.white);
		
		username = new JTextField(TEXTFIELD_LENGTH);
		username.addActionListener(listener);
		
		login = new JButton("Login");
		login.addActionListener(listener);
		
		createAccount = new JButton("Create Account");
		createAccount.addActionListener(listener);
		
		setupLayout();
        
		setBackground(Color.black);
	}
	
	 protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    // paint the background image and scale it to fill the entire space
		    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	 }
	
	/**
	 * Resets the text fields after the user has attempted to login with 
	 * an account containing invalid information
	 */
	public void resetTextFields() {
			username.setText(null);
			password.setText(null);
	}
	 
	private void setupLayout() {
		
		GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(110, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(bombermanLabel)
                        .addGap(110))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(passwordLabel, GroupLayout.Alignment.TRAILING)
                            .addComponent(usernameLabel, GroupLayout.Alignment.TRAILING))
                        .addGap(12)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(username)
                            .addComponent(password))
                        .addGap(110))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    		.addComponent(login)
                    		.addGap(12)
                    		.addComponent(createAccount)
                    		.addGap(130))))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80)
                .addComponent(bombermanLabel)
                .addGap(45)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE))
                    //.addComponent(confirmPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    //.addComponent(confirmPWLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(createAccount))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addContainerGap(33, Short.MAX_VALUE))
        );
	}
	/**
	 * @return JButton ID of the "Create Account" button
	 */
	public JButton getCreateAccButton(){
		return createAccount;
	}
	/**
	 * @return JButton ID of the "Login" button
	 */
	public JButton getLoginButton(){
		return login;
	}
	/**
	 * @return Data within "Username" text field as a String
	 */
	public String getUsername(){
		return username.getText();
	}
	/**
	 * @return Data within "Password" text field as a String
	 */
	public String getPassword() {
		String stringPas = new String(password.getPassword());
		return stringPas;
	}
	
}





