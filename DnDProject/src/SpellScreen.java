import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
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
	private JScrollPane spellScrollTable;
	private String characterID;
	private JButton backButton;
	private JPanel optionsPanel;
	private JTextField spellName;
	
	public SpellScreen(PageManager manager) { //This screen shows available characters
		this.manager = manager;
		
		this.newSpellButton = new JButton("New Spell");
		this.optionsPanel = new JPanel();
		this.optionsPanel.setLayout(new GridLayout(0,3));
		this.characterID = "1";
//		this.layout = new GridLayout(0,1);
		this.setLayout(new BorderLayout());
		this.spellName = new JTextField();
		this.optionsPanel.add(spellName);
		
		newSpellButton.addActionListener(new newSpellButtonListener());
		this.optionsPanel.add(newSpellButton);
		this.backButton = new JButton("Back To Stats");
		backButton.addActionListener(new backListener());
		this.optionsPanel.add(backButton);
		this.add(this.optionsPanel, BorderLayout.SOUTH);
		
//		this.add(characterField).setLocation(2, 0);
//		this.add(newItemButton).setLocation(0, 0);
		//hide();
		
		
		

		
	}
	public void updateForCharacter() {
		this.characterID = manager.getCharacterChosen();
	//	System.out.println(this.characterID);
		this.spells = this.getSpellData();
//		int rows = this.items.size() > 10? 10:this.items.size();
		
		if (this.spellScrollTable != null) {
			this.remove(this.spellScrollTable);
		}
		try {
			this.spellTable = new JTable(buildTableModel(this.spells));
			
			this.spellScrollTable = new JScrollPane(this.spellTable);
			Dimension size = this.spellScrollTable.getPreferredSize();
			size.setSize(size.width+1000, size.height);
			this.spellScrollTable.setPreferredSize(size);
			this.add(this.spellScrollTable,BorderLayout.NORTH);
		} catch (SQLException e) {
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
			JOptionPane.showMessageDialog(null, e.getMessage());
		//	e.printStackTrace();
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
			String spell = spellName.getText();
			try {
				CallableStatement cstmt2 = manager.getConnection().prepareCall("{? = call getSpellID(?)}");
				cstmt2.registerOutParameter(1, Types.INTEGER);
				cstmt2.setString(2, spell);
				
				ResultSet rs = cstmt2.executeQuery();
				rs.next();
				int spellID = rs.getInt("SpellID");
				
				int ret=cstmt2.getInt(1);
				if(ret==1) {
					JOptionPane.showMessageDialog(null, "That spell is not currently supported (Maybe double check your spelling).");
				}
				
				CallableStatement cstmt = manager.getConnection().prepareCall("{? = call learnSpell(?,?)}");
				cstmt.registerOutParameter(1, Types.INTEGER);
				cstmt.setInt(2, Integer.valueOf(manager.getCharacterChosen()));
				cstmt.setInt(3, spellID);
				
				cstmt.execute();
				
				int retval = cstmt.getInt(1);			
				if(retval==0) {
					 updateForCharacter();
					 manager.switchPage("Spells");
				}//else if(retval==1) {
//					JOptionPane.showMessageDialog(null, "Character must not be null and must exist in the Character table");
//				}else if(retval==2) {
//					JOptionPane.showMessageDialog(null, "That spell is not supported (please check spelling).");
//				}else if(retval==3) {
//					JOptionPane.showMessageDialog(null, "This character already knows that spell.");
//				}else if(retval==4) {
//					JOptionPane.showMessageDialog(null, "This charcter is not able to learn this spell.");
//				}
				
				
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			
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
