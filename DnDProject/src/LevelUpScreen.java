import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LevelUpScreen extends JPanel{
	private PageManager manager;
	private GridLayout layout;
	private JLabel enterClass;
	private JTextField enterClassText;
	private JLabel enterDice;
	private JTextField enterDiceText;
	
	private JButton submitButton;
	

	
	public LevelUpScreen(PageManager manager) { //This screen shows available characters
		this.manager = manager;
		this.layout = new GridLayout();
		
	
		
		this.enterClass = new JLabel("Enter the class in which you are gaining a level:");
		this.add(enterClass);
		this.enterClassText = new JTextField();
		this.add(enterClassText);
		
		this.enterDice = new JLabel("Enter the rolled value of the corresponding hit dice.");
		this.add(enterDice);
		this.enterDiceText = new JTextField();
		this.add(enterDiceText);
		
		
		submitButton = new JButton("Sumbit");
		submitButton.addActionListener(new backListener());
		this.add(submitButton);
		this.setLayout(layout);
		this.setEnabled(false);
	}
	
	
	
	
	class backListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				//TO DO ADD PARAM CHECKING
				CallableStatement cs = manager.getConnection().prepareCall("{? = call addLevelIn(?,?,?)}");
				cs.registerOutParameter(1, Types.INTEGER);
				cs.setInt(2, Integer.valueOf(manager.getCharacterChosen()));
				cs.setString(3, enterClassText.getText());
				
				try {
					int t =  Integer.valueOf(enterDiceText.getText());
					cs.setInt(4, t);
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Incorrect value in rolled value field, not an int.");
					return;
				}
				
				cs.execute();
				int retval = cs.getInt(1);
				if(retval==0) {
					manager.switchPage("Details");
				}
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		}	
	}
	
	
	public void toggleState() {
		setEnabled(!isEnabled());
	}
}