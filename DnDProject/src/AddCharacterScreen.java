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
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AddCharacterScreen extends JPanel{
	private PageManager manager;
	private GridLayout layout;
	private JTextField nameField;
	private JTextField Background;
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
	
	public AddCharacterScreen(PageManager manager) { //This screen shows available characters
		this.manager = manager;

		
		this.nameField = new JTextField("Enter Name Here");
		this.Background = new JTextField("");
		this.str = new JTextField("");
		this.dex = new JTextField("");
		this.intel = new JTextField("");
		this.wis = new JTextField("");
		this.cha = new JTextField("");
		this.con = new JTextField("");
		this.alignment = new JTextField("");
		this.maxhp = new JTextField("");
		this.className = new JTextField("");
		this.raceField = new JTextField("");
		
		this.add(nameField);
		this.add(Background);
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
		this.setLayout(layout);
		this.setEnabled(false);
	}
	
	
	
	

	class SubmitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchPage("character");
		}	
	}
	
	
	
	
	public void toggleState() {
		setEnabled(!isEnabled());
	}
}