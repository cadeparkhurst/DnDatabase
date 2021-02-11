import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



public class SpellScreen extends JPanel{
	private PageManager manager;
	private ConnectionHelper loginHelper;
	private ResultSet spells;
	private GridLayout layout;
	private JTextField characterField;
	private JButton newSpellButton;
	private JTable spellTable;
	private String characterID;
	private JButton backButton;
	
	public SpellScreen(PageManager manager) { //This screen shows available characters
		this.manager = manager;
		
		this.newSpellButton = new JButton("New Spell");
		
		this.characterID = "1";
		
		newSpellButton.addActionListener(new newSpellButtonListener());
		this.backButton = new JButton("Back To Stats");
		backButton.addActionListener(new backListener());
		this.add(backButton);
		
//		this.add(characterField).setLocation(2, 0);
//		this.add(newItemButton).setLocation(0, 0);
		//hide();
		
		
		

		
	}
	public void updateForCharacter() {
		this.characterID = manager.getCharacterChosen();
		System.out.println(this.characterID);
		this.spells = this.getSpellData();
//		int rows = this.items.size() > 10? 10:this.items.size();
		this.layout = new GridLayout(2,3);
		this.setLayout(layout);
		if (this.spellTable != null) {
			this.remove(spellTable);
		}
		try {
			this.spellTable = new JTable(buildTableModel(this.spells));
			this.add(this.spellTable).setLocation(2, 2);;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public ResultSet getSpellData(){
		try {
			HashMap<String, String> spells = new HashMap<String, String>();
			//System.out.println(this.manager.getConnection());
//			if (this.characterID == null) {
//				this.characterID = "2";
//			}
			CallableStatement cstmt = this.manager.getConnection().prepareCall("{call getSpells(?)}");
			cstmt.setInt(1, Integer.valueOf(this.characterID));
			
			ResultSet rs = cstmt.executeQuery();
			//System.out.println(rs);
//			System.out.println(rs);
//			while (rs.next()) {
//				names.put(rs.getString(1), rs.getString(2));
//			}
			//System.out.println(names);
			return rs;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
//	private HashMap<JButton, String> generateButtons() {
//		HashMap<JButton,String> buttons = new HashMap<>();
//		
//		int index = 1;
//		for(String name: items.keySet()) {
//			JButton button;
//			button = new JButton();
//			button.setText(name);
//			this.add(button).setLocation(2, index);
//			button.addActionListener(new CharButtonListener());
//			buttons.put(button, name);
//			index++;
//		}
//		return buttons;
//	}
	
	
	class newSpellButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Switch to new char action listen");
			manager.switchPage("AddCharacter");
		}	
	}
	
	
	// Taken From https://stackoverflow.com/questions/10620448/most-simple-code-to-populate-jtable-from-resultset
	public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {
	    ResultSetMetaData metaData = rs.getMetaData();
	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }
	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    return new DefaultTableModel(data, columnNames);
	}
	
	
	public void toggleState() {
		setEnabled(!isEnabled());
	}
	
	class backListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchPage("Details");
		}	
	}
	
}
