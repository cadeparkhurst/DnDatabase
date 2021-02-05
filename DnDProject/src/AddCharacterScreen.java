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
				cstmt.setInt(2, Integer.valueOf(str.getText()));
				cstmt.setInt(3, Integer.valueOf(dex.getText()));
				cstmt.setInt(4, Integer.valueOf(intel.getText()));
				cstmt.setInt(5, Integer.valueOf(wis.getText()));
				cstmt.setInt(6, Integer.valueOf(cha.getText()));
				cstmt.setInt(7, Integer.valueOf(con.getText()));
				cstmt.setString(8, alignment.getText());
				cstmt.setInt(9, Integer.valueOf(maxhp.getText()));
				cstmt.setInt(10, Integer.valueOf(maxhp.getText()));
				//FIND CLASS  ID
					CallableStatement cs1 = manager.getConnection().prepareCall("{? = call getClassID(?)}");
					cs1.registerOutParameter(1, Types.INTEGER);
					cs1.setString(2, className.getText());
					ResultSet rs = cs1.executeQuery();
					rs.next();			
				cstmt.setInt(11, rs.getInt("ClassID"));
				
				cstmt.setString(12, background.getText());
				cstmt.setString(13, raceField.getText());
				cstmt.setString(14, nameField.getText());
					
				cstmt.execute();
					
				int retval = cstmt.getInt(1);
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