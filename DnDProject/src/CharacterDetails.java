import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CharacterDetails extends JPanel{
	private GridLayout layout;
	private PageManager manager;
	private JLabel name;
	private JLabel race;
	private JLabel background;
	private JLabel MaxHP;
	private JLabel HP;
	private String characterID;
	private HashMap<String,String> currentCharDetails;
	
	
	public CharacterDetails(PageManager manager) {
		this.manager = manager;
		
		this.layout = new GridLayout(2,3);
		this.setLayout(layout);
		
		this.name = new JLabel();
		this.race = new JLabel();
		this.background = new JLabel();
		this.add(name).setLocation(0, 0);
		this.add(race).setLocation(0,1);
		this.add(background).setLocation(0,2);;
		this.setEnabled(false);
		
	}
	
	public void updateForCurrentCharacter() {
		this.characterID = manager.getCharacterChosen();
		this.currentCharDetails = this.getDetails();
		this.name.setText("Name: "+currentCharDetails.get("Name"));
		this.race.setText("Race: "+ currentCharDetails.get("Race"));
		this.background.setText("Background: "+currentCharDetails.get("Background"));
	}
	
	
	
	
	private HashMap<String,String> getDetails(){
		HashMap<String,String> stats = new HashMap<>();
		try {
			CallableStatement cstmt = this.manager.getConnection().prepareCall("{? = call getCharacterInfo(?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, this.characterID);
			
			ResultSet rs = cstmt.executeQuery();
			rs.next();
			System.out.println("Race: "+rs.getString("Race"));
			stats.put("Name", rs.getString("Name"));
			stats.put("Background", rs.getString("Background"));
			stats.put("Race", rs.getString("Race"));
			stats.put("Alignment",rs.getString("Alignment"));
			stats.put("MaxHP", rs.getString("MaxHP"));
			stats.put("HP", rs.getString("HP"));
			stats.put("Dex", rs.getString("Dex"));
			stats.put("Int", rs.getString("Int"));
			stats.put("Wis", rs.getString("Wis"));
			stats.put("Cha", rs.getString("Cha"));
			stats.put("Con", rs.getString("Con"));
			stats.put("Str",rs.getString("Str"));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return stats;
	}
}