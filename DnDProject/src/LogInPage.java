import java.awt.BorderLayout;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JButton;
//import javax.swing.JComponent;
//import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogInPage extends JPanel{
	private JTextField UsernameField;
	private JPasswordField PasswordField;
	private JButton loginButton;
	private JButton registerButton;
	private ConnectionHelper loginHelper;
	private PageManager manager;
	private final Base64.Encoder enc = Base64.getEncoder();
	private final Base64.Decoder dec = Base64.getDecoder();
	private final Random RANDOM = new SecureRandom();
	
	
	public LogInPage(PageManager manager, ConnectionHelper loginHelper) {
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
		
		this.registerButton = new JButton("Register");
		this.registerButton.setLocation(25, 100);
		this.registerButton.addActionListener(new RegisterListener());
		this.add(registerButton, BorderLayout.SOUTH);
		
		this.setEnabled(false);
	}
	
	public void toggleState() {
		this.setEnabled(!isEnabled());
	}
	
	public boolean login(String username, String Password) {
		try {
			CallableStatement cstmt = this.manager.getConnection().prepareCall("{? = call LOGIN(?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, username);
			
			ResultSet rs = cstmt.executeQuery();
			
			String PasswordHash = null;
			String PasswordSalt = null;
			while(rs.next()) {
				PasswordHash = rs.getString("PasswordHash");
				PasswordSalt = rs.getString("PasswordSalt");
			}
			if(PasswordHash == null || PasswordHash.equals("")) {
				JOptionPane.showMessageDialog(null, "Login Failed");
			}
			boolean success = PasswordHash.equals(hashPassword(dec.decode(PasswordSalt),Password));
			
			if(success) {
				return true;
			}else {
				JOptionPane.showMessageDialog(null, "Login Failed");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}
	
	public boolean register(String username, String password) {
		try {
			CallableStatement cstmt = this.manager.getConnection().prepareCall("{? = call Register(?,?,?)}");
			
			cstmt.registerOutParameter(1, Types.INTEGER);
			
			cstmt.setString(2, username);
			byte[] salt = getNewSalt();
			cstmt.setString(3, getStringFromBytes(salt));
			cstmt.setString(4, hashPassword(salt,password));

			cstmt.execute();
			
			int retval = cstmt.getInt(1);
			
			if(retval==0) {
				return true;
			}else {
				JOptionPane.showMessageDialog(null, "Registration Failed");
				return false;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		

	}

	
	
	class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//boolean connected = loginHelper.connect(UsernameField.getText(),String.valueOf(PasswordField.getPassword()));
			boolean loginSuccessful = login(UsernameField.getText(), String.valueOf(PasswordField.getPassword()));
			if (loginSuccessful) {
				//manager.setCharacterScreen(new CharactersScreen(manager));
				//manager.getPanel().add(manager.getCharacterScreen());
				manager.switchPage("character");
			}
		}	
	}
	
	class RegisterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//boolean connected = loginHelper.connect(UsernameField.getText(),String.valueOf(PasswordField.getPassword()));
			boolean Registered = register(UsernameField.getText(), String.valueOf(PasswordField.getPassword()));
			if(Registered) {
				JOptionPane.showMessageDialog(null, "Registration Successful");
			}
		}
	}	
}
	