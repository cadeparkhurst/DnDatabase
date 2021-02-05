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

public class ItemScreen extends JPanel{
	private PageManager manager;
	private ConnectionHelper loginHelper;
	private HashMap<String, String> items;
	private GridLayout layout;
	private JTextField characterField;
	private JButton newCharButton;
	private HashMap<JButton,String> ButtonToName;
	
	public ItemScreen(PageManager manager) { //This screen shows available characters
		this.manager = manager;
		this.items = getItemNames();
		int rows = this.items.size() > 10? 10:this.items.size();
		this.characterField = new JTextField(20);
		this.newCharButton = new JButton("New Character");
		this.layout = new GridLayout(rows+2,3);
		
		newCharButton.addActionListener(new newCharButtonListener());
		
		this.setLayout(layout);
		this.add(characterField).setLocation(2, 0);
		this.add(newCharButton).setLocation(0, 0);
		this.ButtonToName = generateButtons();
		//hide();
		
		
		

		
	}
	
	public HashMap<String, String> getItemNames(){
		try {
			HashMap<String, String> names = new HashMap<String, String>();
			//System.out.println(this.manager.getConnection());
			CallableStatement cstmt = this.manager.getConnection().prepareCall("{call GetItems}");
			
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
	
	
	private HashMap<JButton, String> generateButtons() {
		HashMap<JButton,String> buttons = new HashMap<>();
		
		int index = 1;
		for(String name: items.keySet()) {
			JButton button;
			button = new JButton();
			button.setText(name);
			this.add(button).setLocation(2, index);
			button.addActionListener(new CharButtonListener());
			buttons.put(button, name);
			index++;
		}
		return buttons;
	}
	
	class CharButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.setCharacterChosen(items.get(ButtonToName.get(e.getSource())));
			//manager.setCharacterScreen(new CharactersScreen(manager));
			
		//	manager.getPanel().add(manager.getCharacterScreen());
			System.out.println("CHAR CHOSEN:" + manager.getCharacterChosen());
			
			manager.switchPage("Details");
		}	
	}
	
	class newCharButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Switch to new char action listen");
			manager.switchPage("AddCharacter");
		}	
	}
	
	
	
	
	public void toggleState() {
		setEnabled(!isEnabled());
	}
}