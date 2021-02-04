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

public class CharactersScreen extends JPanel{
	private PageManager manager;
	private LoginHelper loginHelper;
	private HashMap<String, String> characters;
	private GridLayout layout;
	private JTextField characterField;
	private JButton newCharButton;
	
	public CharactersScreen(PageManager manager) { //This screen shows available characters
		this.manager = manager;
		this.characters = getCharacterNames();
		int rows = this.characters.size() > 10? 10:this.characters.size();
		this.characterField = new JTextField(20);
		this.newCharButton = new JButton("New Character");
		this.layout = new GridLayout(rows+2,3);
		
		this.setLayout(layout);
		this.add(characterField).setLocation(2, 0);
		this.add(newCharButton).setLocation(0, 0);
		generateButtons();
		hide();
		
		
		

		
	}
	
	public HashMap<String, String> getCharacterNames(){
		try {
			HashMap<String, String> names = new HashMap<String, String>();
			//System.out.println(this.manager.getConnection());
			CallableStatement cstmt = this.manager.getConnection().prepareCall("{call GetCharNames}");
			
			ResultSet rs = cstmt.executeQuery();
			//System.out.println(rs);
			
			while (rs.next()) {
				names.put(rs.getString(1), rs.getString(2));
			}
			//System.out.println(names);
			return names;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private void generateButtons() {
		JButton button;
		int index = 1;
		for(String name: characters.keySet()) {
			button = new JButton();
			button.setText(name);
			this.add(button).setLocation(2, index);
			button.addActionListener(new Listener());
			index++;
		}
	}
	
	class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			manager.setCharacterChosen(characters.get(e.getSource()));
	//		if (connected) {
				manager.setCharacterScreen(new CharactersScreen(manager));
				manager.getPanel().add(manager.getCharacterScreen());
				manager.switchPage("character");
				//System.out.println("test3");
				hide();
				manager.switchPage("Stats1");
//			}
			

	}
		
		
	
	
	
	}
	
	
	
	
	public void toggleState() {
		setEnabled(!isEnabled());
	}
}