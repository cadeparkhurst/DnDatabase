import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogInPage extends JPanel{
	private JTextField UsernameField;
	private JPasswordField PasswordField;
	private JButton loginButton;
	private LoginHelper loginHelper;
	private PageManager manager;
	
	
	public LogInPage(PageManager manager, LoginHelper loginHelper) {
		this.manager = manager;
		this.loginHelper = loginHelper;
		this.PasswordField = new JPasswordField(20);
		this.UsernameField = new JTextField(20);
		this.UsernameField.setToolTipText("Username");
		this.PasswordField.setToolTipText("Password");
		this.UsernameField.setBounds(25, 25, 200, 25);
		this.add(this.UsernameField, BorderLayout.NORTH);
		this.add(this.PasswordField, BorderLayout.NORTH);
		this.loginButton = new JButton("Login");
		this.loginButton.setLocation(25, 75);
		this.loginButton.addActionListener(new Listener());
		this.add(loginButton, BorderLayout.SOUTH);
		this.setEnabled(false);
	}
	
	public void toggleState() {
		this.setEnabled(!isEnabled());
	}
	
	class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean connected = loginHelper.connect(UsernameField.getText(),String.valueOf(PasswordField.getPassword()));
			if (connected) {
				manager.setCharacterScreen(new CharactersScreen(manager));
				manager.getPanel().add(manager.getCharacterScreen());
				manager.switchPage("character");
				System.out.println("test1");
				hide();
			}
			

	}
		
		
	
	
	
	}
}