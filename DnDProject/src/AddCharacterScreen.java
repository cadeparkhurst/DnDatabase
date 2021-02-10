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

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AddCharacterScreen extends JPanel{
	private PageManager manager;
	private GridLayout layout;
	private JTextField nameField;
	private JTextField background;
	private JTextField str;
	private JTextField dex;
	private JTextField intel;
	private JTextField wis;
	private JTextField cha;
	private JTextField con;
	private JTextField alignment;
	private JTextField maxhp;
	private JTextField className;
	private JTextField raceField;
	private JButton submit;
	private JButton backButton;
	
	public AddCharacterScreen(PageManager manager) { //This screen shows available characters
		this.manager = manager;
		this.layout = new GridLayout();
		
		this.nameField = new JTextField("Enter Name Here");
		this.background = new JTextField("Background name");
		this.str = new JTextField("Strength");
		this.dex = new JTextField("Dexterity");
		this.intel = new JTextField("Intelligence");
		this.wis = new JTextField("Wisdom");
		this.cha = new JTextField("Charisma");
		this.con = new JTextField("Constitution");
		this.alignment = new JTextField("Alignment");
		this.maxhp = new JTextField("MaxHP");
		this.className = new JTextField("Class Name");
		this.raceField = new JTextField("Race Name");
		
		this.add(nameField);
		this.add(background);
		this.add(str);
		this.add(dex);
		this.add(intel);
		this.add(wis);
		this.add(cha);
		this.add(con);
		this.add(alignment);
		this.add(maxhp);
		this.add(className);
		this.add(raceField);
		
		submit = new JButton("Submit Character");
		submit.addActionListener(new SubmitListener());
		this.add(submit);
		
		this.backButton = new JButton("Back To Characters");
		backButton.addActionListener(new backListener());
		this.add(backButton);
		
		
		this.setLayout(layout);
		this.setEnabled(false);
	}
	
	
	
	

	class SubmitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				CallableStatement cstmt = manager.getConnection().prepareCall("{? = call AddCharacter(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
					
				cstmt.registerOutParameter(1, Types.INTEGER);
				try {
					int t = Integer.valueOf(str.getText());
					if(t>18 || t<3) {
						JOptionPane.showMessageDialog(null, "Strength value not between 18 and 3, it is: "+t);
						return;
					}else {
						cstmt.setInt(2, t);
					}
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Incorrect value in Strength field, not an int.");
					return;
				}
				try {
					int t = Integer.valueOf(dex.getText());
					if(t>18 || t<3) {
						JOptionPane.showMessageDialog(null, "Dexterity value not between 18 and 3, it is: "+t);
						return;
					}else {
						cstmt.setInt(3, t);
					}
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Incorrect value in Dexterity field, not an int.");
					return;
				}
				try {
					int t = Integer.valueOf(intel.getText());
					if(t>18 || t<3) {
						JOptionPane.showMessageDialog(null, "Intelligence value not between 18 and 3, it is: "+t);
						return;
					}else {
						cstmt.setInt(4, t);
					}
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Incorrect value in Intelligence field, not an int.");
					return;
				}
				try {
					int t = Integer.valueOf(wis.getText());
					if(t>18 || t<3) {
						JOptionPane.showMessageDialog(null, "Wisdom value not between 18 and 3, it is: "+t);
						return;
					}else {
						cstmt.setInt(5, t);
					}
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Incorrect value in Wisdom field, not an int.");
					return;
				}
				try {
					int t = Integer.valueOf(cha.getText());
					if(t>18 || t<3) {
						JOptionPane.showMessageDialog(null, "Charisma value not between 18 and 3, it is: "+t);
						return;
					}else {
						cstmt.setInt(6, t);
					}
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Incorrect value in Charisma field, not an int.");
					return;
				}
				try {
					int t = Integer.valueOf(con.getText());
					if(t>18 || t<3) {
						JOptionPane.showMessageDialog(null, "Constitution value not between 18 and 3, it is: "+t);
						return;
					}else {
						cstmt.setInt(7, t);
					}
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Incorrect value in Constitution field, not an int.");
					return;
				}
				//ADD CHECKS FOR ALIGNMENT
				String ali = alignment.getText();
				cstmt.setString(8, ali);
				
				try {
					int t = Integer.valueOf(maxhp.getText());
					cstmt.setInt(9, t);
					cstmt.setInt(10, t);
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Incorrect value in MaxHP field, not an int.");
					return;
				}
				
				//FIND CLASS  ID
					CallableStatement cs1 = manager.getConnection().prepareCall("{? = call getClassID(?)}");
					cs1.registerOutParameter(1, Types.INTEGER);
					String[] classes = {"Barbarian", 
							"Bard", 
							"Cleric", 
							"Druid", 
							"Fighter", 
							"Monk", 
							"Paladin", 
							"Ranger", 
							"Rogue", 
							"Sorcerer", 
							"Warlock", 
							"Wizard"};
					if(!(Arrays.asList(classes).contains(className.getText()))) {
						JOptionPane.showMessageDialog(null, "Incorrect Class Name");
						return;
					}
					cs1.setString(2, className.getText());
					ResultSet rs = cs1.executeQuery();
					rs.next();			
					int retval = cstmt.getInt(1);
					if(retval==0) {
						cstmt.setInt(11, rs.getInt("ClassID"));
					}else {
						JOptionPane.showMessageDialog(null, "Invalid Class Name");
						return;
					}

				//Need to check these
				cstmt.setString(12, background.getText());
				cstmt.setString(13, raceField.getText());
				cstmt.setString(14, nameField.getText());
					
				cstmt.execute();
					
				retval = cstmt.getInt(1);
				if(retval==0) {
					manager.switchPage("character");
				}else {
					JOptionPane.showMessageDialog(null, "There was an error adding your character, with error code: "+retval);
				}
			}catch (SQLException exception) {
				exception.printStackTrace();
			}
		}	
	}
	
	
	class backListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchPage("character");
		}	
	}
	
	public void toggleState() {
		setEnabled(!isEnabled());
	}
}