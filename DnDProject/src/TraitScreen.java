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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



public class TraitScreen extends JPanel{
	private PageManager manager;
	private ConnectionHelper loginHelper;
	private ResultSet traits;
	private GridLayout layout;
	private JTextField characterField;
	private JTable traitTable;
	private String characterID;
	private JButton backButton;
	
	public TraitScreen(PageManager manager) { //This screen shows available characters
		this.manager = manager;
		
		
		this.characterID = "1";
		
		this.backButton = new JButton("Back To Stats");
		backButton.addActionListener(new backListener());
		this.add(backButton);
		

		
		
		

		
	}
	public void updateForCharacter() {
		this.characterID = manager.getCharacterChosen();
		System.out.println(this.characterID);
		this.traits = this.getTraits();
		this.layout = new GridLayout(2,3);
		this.setLayout(layout);
		if (this.traitTable != null) {
			this.remove(traitTable);
		}
		try {
			this.traitTable = new JTable(buildTableModel(this.traits));
			Dimension size = this.traitTable.getPreferredSize();
			size.setSize(size.width+700, size.height);
			this.traitTable.setPreferredSize(size);
			this.add(this.traitTable).setLocation(2, 2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public ResultSet getTraits(){
		try {
			CallableStatement cstmt = this.manager.getConnection().prepareCall("{call getTraits(?)}");
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
