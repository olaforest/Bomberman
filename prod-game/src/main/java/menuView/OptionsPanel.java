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
 * This class creates the Account Options Panel, and generates all
 * GUI elements within the panel. This includes text boxes for the user to input their
 * new name and password as well as buttons for confirming user changes or returning to the Main Menu.
 *
 * @author Eric Liou
 */
@SuppressWarnings("serial")
public class OptionsPanel extends JPanel {

	private int TEXTFIELD_LENGTH = 12;

	private JButton save, goBack;
	private JLabel newName, password, confirmPassword;
	private JTextField name;
	private JPasswordField pw, confirmPw;
	private BufferedImage img;

	/**
	 * Constructs a new Account Options Panel with all GUI elements defined with default parameters
	 *
	 * @param listener an ActionListener which is applied to all GUI elements (Buttons, Text fields) which may trigger an user event
	 */
	public OptionsPanel(ActionListener listener) {

		try {
			InputStream in = Bomberman.class.getResourceAsStream("/bomber2.png");
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		save = new JButton("Save and Return");
		save.addActionListener(listener);

		goBack = new JButton("Return to Main Menu");
		goBack.addActionListener(listener);

		newName = new JLabel("New Name:");
		newName.setForeground(Color.white);
		newName.setFont(new Font("Eurostile", Font.BOLD, 15));

		password = new JLabel("New Password:");
		password.setForeground(Color.white);
		password.setFont(new Font("Eurostile", Font.BOLD, 15));

		confirmPassword = new JLabel("Confirm Password:");
		confirmPassword.setForeground(Color.white);
		confirmPassword.setFont(new Font("Eurostile", Font.BOLD, 15));

		name = new JTextField(TEXTFIELD_LENGTH);
		name.addActionListener(listener);

		pw = new JPasswordField(TEXTFIELD_LENGTH);
		pw.setEchoChar('*');
		pw.addActionListener(listener);

		confirmPw = new JPasswordField(TEXTFIELD_LENGTH);
		confirmPw.setEchoChar('*');
		confirmPw.addActionListener(listener);

		setupLayout();
	}

	/**
	 * @return JButton ID of the "Save and Return" button
	 */
	public JButton getSaveButton() {
		return save;
	}

	/**
	 * @return JButton ID of the "Return to Main Menu" button
	 */
	public JButton getReturnButton() {
		return goBack;
	}

	/**
	 * @return Data within "New Name:" text field as a String
	 */
	public String getNewName() {
		return name.getText();
	}

	/**
	 * @return Data within "New Password:" text field as a String
	 */
	public String getNewPassword() {
		return new String(pw.getPassword());
	}

	/**
	 * @return Data within "Confirm Password:" text field as a String
	 */
	public String getNewConfirmPassword() {
		return new String(confirmPw.getPassword());
	}

	/**
	 * Clears text fields of all characters
	 */
	public void resetTextFields() {
		name.setText(null);
		pw.setText(null);
		confirmPw.setText(null);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// paint the background image and scale it to fill the entire space
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}

	private void setupLayout() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
						.addGroup(layout.createSequentialGroup()
								.addGap(83, 83, 83)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
										.addGroup(layout.createSequentialGroup()
												.addComponent(confirmPassword)
												.addGap(5, 5, 5)
												.addComponent(confirmPw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGroup(layout.createSequentialGroup()
												.addGap(20, 20, 20)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(password)
														.addComponent(newName))
												.addGap(6, 6, 6)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(pw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addContainerGap(99, Short.MAX_VALUE))))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
										.addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
												.addComponent(goBack)
												.addGap(150, 150, 150))
										.addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
												.addComponent(save)
												.addGap(150, 150, 150))))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addGap(116, 116, 116)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(newName))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(pw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(password))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(confirmPw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(confirmPassword))
								.addGap(31, 31, 31)
								.addComponent(save)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(goBack)
								.addContainerGap(98, Short.MAX_VALUE))
		);
	}
}
