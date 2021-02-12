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

public class ProfChoosing extends JPanel{
	private PageManager manager;
	private GridLayout layout;
	private JButton submitButton;
	private JList<String> skillsList;
	private ArrayList<String> skillsToShow;
	private JLabel title;
	private int numSkillProfs;

	
	public ProfChoosing(PageManager manager) { //This screen shows available characters
		this.manager = manager;
		this.layout = new GridLayout();
		numSkillProfs=0;
		
		this.title = new JLabel();
		this.add(title);
		
		
		this.skillsList = new JList<String>();
		skillsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.add(skillsList);
		
		
		submitButton = new JButton("OK");
		submitButton.addActionListener(new backListener());
		this.add(submitButton);
		this.setLayout(layout);
		this.setEnabled(false);
	}
	
	
	
	public void updatePage(int classID) {
		skillsToShow = getOfferedProfs(classID);
		this.numSkillProfs = getNumSkillProfs(classID);
		this.title.setText("Please Select "+numSkillProfs+" proficiencies. \n (Hold ctrl to select non consecutive rows)");
		String[] temp = {};
		skillsList.setListData(skillsToShow.toArray(temp));
	}
	
	private int getNumSkillProfs(int cid) {
		int numProfs =0;
		try {
			CallableStatement cstmt = this.manager.getConnection().prepareCall("{? = call getNumSkillProfs(?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setInt(2, cid);
			
			ResultSet rs = cstmt.executeQuery();
			
			rs.next();
			numProfs = rs.getInt("numSkillProfs");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return numProfs;
	}
	
	
	public ArrayList<String> getOfferedProfs(int classID){
		ArrayList<String> skills = new ArrayList<>();
		
		try {
			CallableStatement cstmt = manager.getConnection().prepareCall("{? = call getOfferedProfs(?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setInt(2, classID);
			
			ResultSet rs = cstmt.executeQuery();
			
			while(rs.next()) {
				skills.add(rs.getString("SkillName"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return skills;
	}
	
	
	
	
	class backListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			List<String> selected = skillsList.getSelectedValuesList();
			
			if(selected.size()!=numSkillProfs) {
				JOptionPane.showMessageDialog(null, "Please select the correct number of skills.");
				return;
			}
			
			
			
			for(String i : selected) {
				try {
					CallableStatement cstmt = manager.getConnection().prepareCall("{? = call addProficiency(?,?)}");
					cstmt.registerOutParameter(1, Types.INTEGER);
					cstmt.setInt(2, Integer.valueOf(manager.getCharacterChosen()));
					cstmt.setString(3, i);
					
					
					cstmt.execute();
					
					int retval = cstmt.getInt(1);
					if(retval==0) {
						manager.switchPage("Language");
					}else {
						JOptionPane.showMessageDialog(null, "There was an error selecting profs, please try again");
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