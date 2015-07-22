package menuView;

import gameplayModel.GridObjects.AnimatedObjects.Bomberman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


/**
 * This class creates the Account Creation Panel, and generates all 
 * GUI elements within the panel. This includes a header title, text fields 
 * for user input, and two buttons allowing for account creation or return to
 * the login menu. Also sets background to an external image file.
 *
 */
@SuppressWarnings("serial")
public class CreateAccountPanel extends JPanel {
	
	private int TEXTFIELD_LENGTH = 15;
	
	private JLabel realNameLabel, usernameLabel, passwordLabel, confirmPWLabel, bombermanLabel;
	protected JTextField realName;

	protected JTextField username;
	private JButton createAccount, back;
	protected JPasswordField password;

	protected JPasswordField confirmPassword;
	private BufferedImage img;
	
	/**
	 * Constructs a new Create Account Panel with all GUI elements defined default parameters
	 * 
	 * @param listener an ActionListener which is applied to all GUI elements (Textfields, Buttons) which may trigger an user event
	 * 		  
	 */
	public CreateAccountPanel (ActionListener listener) {
		
		try {
			InputStream in = Bomberman.class.getResourceAsStream("/bomber.png");
		      img = ImageIO.read(in);
		    } catch(IOException e) {
		      e.printStackTrace();
		    }
	
		realNameLabel = new JLabel("Real Name:");
		realNameLabel.setForeground(Color.white);
		
		usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.white);
		
		passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.white);
		
		confirmPWLabel = new JLabel("Confirm Password:");
		confirmPWLabel.setForeground(Color.white);
		
		bombermanLabel = new JLabel("Bomberman");
		bombermanLabel.setFont(new Font("Eurostile", 0, 48)); // NOI18N
        bombermanLabel.setForeground(Color.white);
		
        realName = new JTextField(TEXTFIELD_LENGTH);
		realName.addActionListener(listener);
        
		username = new JTextField(TEXTFIELD_LENGTH);
		username.addActionListener(listener);
		
		password = new JPasswordField();
		password.setEchoChar('*');
		password.addActionListener(listener);
		
		confirmPassword = new JPasswordField();
		confirmPassword.setEchoChar('*');
		confirmPassword.addActionListener(listener);
		
		createAccount = new JButton("Create Account");
		createAccount.addActionListener(listener);
		
		back = new JButton("Return to Login");
		back.addActionListener(listener);
		
		setupLayout();
        
		setBackground(Color.black);
	}
	
	 protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    // paint the background image and scale it to fill the entire space
		    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	 }
	
	/**
	 * Resets the text fields after the user has attempted to create and 
	 * account with information that doesn't meet account requirements
	 * 
	 */
	public void resetTextFields() {
		realName.setText(null);
		username.setText(null);
		password.setText(null);
		confirmPassword.setText(null);
	}
	
	/**
	 * 
	 * @return JButton ID of the "Create Account" button 
	 */
	public JButton getCreateAccButton(){
		return createAccount;
	}
	/**
	 * 
	 * @return JButton ID of the "Return to Login" button 
	 */
	public JButton getBackButton(){
		return back;
	}
	
	/**
	 * @return Data within the "Real Name:" text field as a String
	 */
	public String getRealName(){
		return realName.getText();
	}
	/**
	 * @return Data within the "Username:" text field as a String
	 */
	public String getUsername(){
		return username.getText();
	}
	/**
	 * @return Data within the "Password:" text field as a String
	 */
	public String getPassword() {
		return new String(password.getPassword());
	}
	/**
	 * @return Data within the "Confirm Password:" text field as a String
	 */
	public String getConfirmedPassword(){
		return new String(confirmPassword.getPassword());
	}
	
	private void setupLayout() {
		
		GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(bombermanLabel)
                        .addGap(110))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        	.addComponent(confirmPWLabel, GroupLayout.Alignment.TRAILING)
                            .addComponent(passwordLabel, GroupLayout.Alignment.TRAILING)
                            .addComponent(usernameLabel, GroupLayout.Alignment.TRAILING)
                            .addComponent(realNameLabel, GroupLayout.Alignment.TRAILING))
                        .addGap(12)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        	.addComponent(realName)
                        	.addComponent(username)
                            .addComponent(password)
                            .addComponent(confirmPassword))
                        .addGap(100))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    		.addComponent(createAccount)
                    		.addGap(180))))
                   .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                    		.addGap(5)
                		    .addComponent(back))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50)
                .addComponent(bombermanLabel)
                .addGap(25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(realName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(realNameLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirmPWLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(createAccount))
                .addGap(10) 
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(back))
                    
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addContainerGap(33, Short.MAX_VALUE))
        );
	}
}