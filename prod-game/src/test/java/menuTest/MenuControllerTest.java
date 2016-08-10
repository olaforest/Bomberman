package menuTest;

import menuController.MenuController;
import menuView.CreateAccountPanel;
import menuView.LoginMenuPanel;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MenuControllerTest {

	private MenuController menuCtrl;
	private LoginMenuPanel login;
	private TestCreateAccountPanel create;
	private ActionListener subjectUnderTest;

	@Before
	public void setup() {
		subjectUnderTest = new MyActionListener();
		menuCtrl = new MenuController();
		login = new LoginMenuPanel(subjectUnderTest);
		create = new TestCreateAccountPanel(subjectUnderTest);
	}

	@Test
	public void testEventListener() {

		ActionEvent mockEvent = new ActionEvent(login.getLoginButton(), 0, null);
		subjectUnderTest.actionPerformed(mockEvent);
		assertTrue(mockEvent.getSource() == login.getLoginButton());
	}

	@Test
	public void testCreateAccount() {
		ActionEvent mockEvent = new ActionEvent(create.getCreateAccButton(), 1001, "Click");
		subjectUnderTest.actionPerformed(mockEvent);
		System.out.println(mockEvent.getActionCommand());
		System.out.println(mockEvent.paramString());
		System.out.println(create.getUsername());

		assertTrue(mockEvent.getSource() == create.getCreateAccButton());
		assertNotNull(menuCtrl.getCurrentPlayer());
	}

	private class MyActionListener implements ActionListener {
		private List events = new ArrayList();

		public void actionPerformed(ActionEvent e) {
			this.events.add(e);
		}

		public int getEventCount() {
			return this.events.size();
		}

		public List getEvents() {
			return this.events;
		}
	}

	private class TestCreateAccountPanel extends CreateAccountPanel {

		public TestCreateAccountPanel(ActionListener listener) {
			super(listener);
			realName = new JTextField(15);
			realName.addActionListener(listener);
			realName.setText("Bob");

			username = new JTextField(15);
			username.addActionListener(listener);
			username.setText("NewTestUser1");

			password = new JPasswordField();
			password.setEchoChar('*');
			password.addActionListener(listener);
			password.setText("abcABC1!");

			confirmPassword = new JPasswordField();
			confirmPassword.setEchoChar('*');
			confirmPassword.addActionListener(listener);
			confirmPassword.setText("abcABC1!");
		}

	}


}
