import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Stats1 extends JPanel{
	private GridLayout highLevel;
	private PageManager manager;
	private JTextField name;
	
	
	public Stats1() {
		this.manager = manager;
		this.name = new JTextField(20);
		this.setEnabled(false);
		
	}
	
	
	
	
//	private Arraylist<String> getStats(){
//		String query = "SELECT * \nFROM Character\n";
//		ArrayList<String> stats = new ArrayList<String>>();
//		try {
//			
//			System.out.println(this.manager.getConnection());
//			Statement stmt = this.manager.getConnection().createStatement();
//			      ResultSet rs = stmt.executeQuery(query);
//			System.out.println(rs);
//			rs.next();
//				stats.add(rs.getString(1));
//				stats.add(rs.getString(2));
//				stats.add(rs.getString(13));
//			
//			System.out.println(stats);
//			return stats;
//			
//		} catch (SQLException e) {
//			
//			return stats;
//		}
//	}
}