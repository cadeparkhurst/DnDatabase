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

public class ItemScreen extends JPanel {
	private PageManager manager;
	private ConnectionHelper loginHelper;
	private ResultSet items;
	private GridLayout layout;
	private JTextField characterField;
	private JButton addItemButton;
	private JButton removeItemButton;
	private JTable itemTable;
	private String characterID;
	private JButton backButton;
	private JPanel optionsPanel;
	private JTextField itemName;
	private JTextField itemQuant;
	private JScrollPane itemScrollTable;

	public ItemScreen(PageManager manager) { // This screen shows available characters
		this.manager = manager;

		
		this.optionsPanel = new JPanel();
		this.optionsPanel.setLayout(new GridLayout(0, 5));
		this.characterID = "1";
		this.setLayout(new BorderLayout());
		this.itemName = new JTextField();
		this.itemName.setText("Item Name");
		this.optionsPanel.add(itemName);
		
		this.itemQuant = new JTextField();
		this.itemQuant.setText("Quantity");
		this.optionsPanel.add(itemQuant);
		
		this.addItemButton = new JButton("Add");
		addItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					CallableStatement cs = manager.getConnection().prepareCall("{? = call addItem(?,?,?)}");
					cs.registerOutParameter(1, Types.INTEGER);
					cs.setInt(2, Integer.valueOf(manager.getCharacterChosen()));
					cs.setString(3,itemName.getText());
					try {
						int t = Integer.valueOf(itemQuant.getText());
						cs.setInt(4, t);
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Incorrect value in Quantity field, not an int.");
						return;
					}
					
				cs.execute();
					
					int retval = cs.getInt(1);
					if(retval==0) {
						manager.switchPage("Items");
					}//else if(retval==1) {
//						JOptionPane.showMessageDialog(null, "That character does not exist.");
//						return;
//					}else if(retval==2) {
//						JOptionPane.showMessageDialog(null, "No item with that name exist.");
//						return;
//					}
//					
				}catch(SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
					//ex.printStackTrace();
				}
			}
		});
		this.optionsPanel.add(addItemButton);
		
		this.removeItemButton = new JButton("Remove");
		removeItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CallableStatement cs = manager.getConnection().prepareCall("{? = call removeItemByQuant(?,?,?)}");
					cs.registerOutParameter(1, Types.INTEGER);
					cs.setInt(2, Integer.valueOf(manager.getCharacterChosen()));
					cs.setString(3,itemName.getText());
					
					try {
						int t = Integer.valueOf(itemQuant.getText());
						cs.setInt(4, t);
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Incorrect value in Quantity field, not an int.");
						return;
					}
					
					
					cs.execute();
					
					int retval = cs.getInt(1);
					if(retval==0) {
						manager.switchPage("Items");
					}//else if(retval==1) {
//						JOptionPane.showMessageDialog(null, "That character does not exist.");
//						return;
//					}else if(retval==2) {
//						JOptionPane.showMessageDialog(null, "No item with that name exist.");
//						return;
//					}
					
				}catch(SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
					//ex.printStackTrace();
				}
			}
		});
		this.optionsPanel.add(removeItemButton);
		
		this.backButton = new JButton("Back To Stats");
		backButton.addActionListener(new backListener());
		this.optionsPanel.add(backButton);
		this.add(this.optionsPanel, BorderLayout.SOUTH);

//		this.add(characterField).setLocation(2, 0);
//		this.add(newItemButton).setLocation(0, 0);
		// hide();

	}

	public void updateForCharacter() {
		this.characterID = manager.getCharacterChosen();
		System.out.println(this.characterID);
		this.items = this.getItemNames();
		this.itemQuant.setText("Quantity");
		this.itemName.setText("Item Name");
//		int rows = this.items.size() > 10? 10:this.items.size();
//		this.layout = new GridLayout(2,3);
//		this.setLayout(layout);
		if (this.itemScrollTable != null) {
			this.remove(itemScrollTable);
		}
		try {
			this.itemTable = new JTable(buildTableModel(this.items));
			this.itemScrollTable = new JScrollPane(this.itemTable);
			Dimension size = this.itemScrollTable.getPreferredSize();
			size.setSize(size.width + 1000, size.height);
			this.itemScrollTable.setPreferredSize(size);
			this.add(this.itemScrollTable, BorderLayout.NORTH);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ResultSet getItemNames() {
		try {
			HashMap<String, String> names = new HashMap<String, String>();
			// System.out.println(this.manager.getConnection());
//			if (this.characterID == null) {
//				this.characterID = "2";
//			}
			CallableStatement cstmt = this.manager.getConnection().prepareCall("{call GetItems(?)}");
			cstmt.setInt(1, Integer.valueOf(this.characterID));

			ResultSet rs = cstmt.executeQuery();
			// System.out.println(rs);
//			System.out.println(rs);
//			while (rs.next()) {
//				names.put(rs.getString(1), rs.getString(2));
//			}
			// System.out.println(names);
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

	

	// Taken From
	// https://stackoverflow.com/questions/10620448/most-simple-code-to-populate-jtable-from-resultset
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
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
