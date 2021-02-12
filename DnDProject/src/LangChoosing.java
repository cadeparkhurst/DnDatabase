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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LangChoosing extends JPanel{
	private PageManager manager;
	private GridLayout layout;
	private JButton submitButton;
	private JList<String> langssList;
	private List<String> Langs;
	private JLabel title;
	private int numLangs;

	
	public LangChoosing(PageManager manager) { //This screen shows available characters
		this.manager = manager;
		this.layout = new GridLayout();
		numLangs=0;
		
	
		
		this.title = new JLabel();
		this.add(title);
		
		
		this.langssList = new JList<String>();
		langssList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.add(langssList);
		
		
		submitButton = new JButton("OK");
		submitButton.addActionListener(new backListener());
		this.add(submitButton);
		this.setLayout(layout);
		this.setEnabled(false);
	}
	
	
	
	public void updatePage() {
		Langs = setFirstLangs();
		this.numLangs = getNumLangs();
		this.title.setText("Please Select "+numLangs+" Languages. \n (Hold ctrl to select non consecutive rows)");
		setLangs();
		String[] temp = {};
		langssList.setListData(Langs.toArray(temp));
	}
	
	public ArrayList<String> setFirstLangs(){
		ArrayList<String> ls = new ArrayList<String>();
		ls.add("Abyssal");
		ls.add("Celestial");
		ls.add("Common");
		ls.add("Deep Speech");
		ls.add("Draconic");
		ls.add("Dwarvish");
		ls.add("Elvish");
		ls.add("Giant");
		ls.add("Gnomish");
		ls.add("Goblin");
		ls.add("Halfling");
		ls.add("Infernal");
		ls.add("orc");
		ls.add("Primordial");
		ls.add("Undercommon");
		return ls;
	}
	
	public void setLangs(){
		ArrayList<String> knowns = new ArrayList<String>();
		try {
			CallableStatement cs = manager.getConnection().prepareCall("{? = call getLanguages(?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, Integer.valueOf(manager.getCharacterChosen()));
			
			ResultSet rs = cs.executeQuery();
			
			while(rs.next()) {
				knowns.add(rs.getString("LanguageName"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		for(int i =0; i<Langs.size(); i++) {
			if(knowns.contains(Langs.get(i))) {
				Langs.remove(i);
			}
		}
	}
	
	private int getNumLangs() {
		int numLangs =0;
		try {
			CallableStatement cstmt = this.manager.getConnection().prepareCall("{? = call getNumLangs(?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setInt(2, Integer.valueOf(manager.getCharacterChosen()));
			
			ResultSet rs = cstmt.executeQuery();
			
			rs.next();
			numLangs = rs.getInt("NumLanguagesGained");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return numLangs;
	}
	
	
	
	
	class backListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			List<String> selected = langssList.getSelectedValuesList();
			
			if(selected.size()!=numLangs) {
				JOptionPane.showMessageDialog(null, "Please select the correct number of Languages.");
				return;
			}
			for(String i : selected) {
				try {
					CallableStatement cstmt = manager.getConnection().prepareCall("{? = call learnLanguage(?,?)}");
					cstmt.registerOutParameter(1, Types.INTEGER);
					cstmt.setInt(2, Integer.valueOf(manager.getCharacterChosen()));
					cstmt.setString(3, i);
					
					
					cstmt.execute();
					
					int retval = cstmt.getInt(1);
					if(retval==0) {
						manager.switchPage("character");
					}else {
						JOptionPane.showMessageDialog(null, "There was an error learning languages, please try again");
					}
					
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}	
	}
	
	
	public void toggleState() {
		setEnabled(!isEnabled());
	}
}