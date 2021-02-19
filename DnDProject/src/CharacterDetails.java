import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class CharacterDetails extends JPanel{
	//private GridLayout layout;
	private PageManager manager;
	private JLabel name;
	private JLabel race;
	private JLabel background;
	private JLabel alignment;
	private JLabel MaxHP;
	private JLabel HP;
	private JLabel Dex;
	private JLabel Int;
	private JLabel Wis;
	private JLabel Con;
	private JLabel Cha;
	private JLabel Str;
	private JScrollPane classes;
	private JTable classesTable;
	private JLabel level;
	private JButton backButton;
	private JButton itemButton;
	private JButton spellButton;
	private JButton langButton;
	private JButton traitButton;
	private JButton skillButton;
	private String characterID;
	private HashMap<String,String> currentCharDetails;
	private JButton deleteMe;
	private JButton levelUp;
	private JPanel statsPanel;
	
	
	public CharacterDetails(PageManager manager) {
		this.manager = manager;
		this.setLayout(new BorderLayout());
		this.statsPanel = new JPanel();
		this.statsPanel.setLayout(new GridLayout(0, 7));
		
		
		initJLabels();
		
		
		//this.layout = new GridLayout(7,4);
		//this.setLayout(layout);
		placeLabels();
		this.setEnabled(false);
		this.itemButton = new JButton("To Items");
		itemButton.addActionListener(new toItemsListener());
		this.statsPanel.add(itemButton, BorderLayout.PAGE_START);
		
		this.spellButton = new JButton("To Spells");
		spellButton.addActionListener(new toSpellsListener());
		this.statsPanel.add(spellButton, BorderLayout.PAGE_START);
		
		this.langButton = new JButton("To Languages");
		langButton.addActionListener(new toLangListener());
		this.statsPanel.add(langButton, BorderLayout.PAGE_START);
		
		this.traitButton = new JButton("To Traits");
		traitButton.addActionListener(new toTraitListener());
		this.statsPanel.add(traitButton, BorderLayout.PAGE_START);
		
		this.skillButton = new JButton("To Skills");
		skillButton.addActionListener(new toSkillListener());
		this.statsPanel.add(skillButton, BorderLayout.PAGE_START);
		
		this.levelUp = new JButton("Level Up");
		this.levelUp.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				manager.switchPage("Level");
			}
			
		});
		this.statsPanel.add(levelUp);
		
		this.backButton = new JButton("Back To Characters");
		backButton.addActionListener(new backListener());
		this.statsPanel.add(backButton, BorderLayout.PAGE_START);
		
		this.deleteMe = new JButton("DELETE THIS CHARACTER");
		this.deleteMe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				  int a=JOptionPane.showConfirmDialog(null, "Are you sure?", "Deleting your character forever", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);  
				  if(a==JOptionPane.NO_OPTION) {
					  return;
				  }
				try {
					CallableStatement cs = manager.getConnection().prepareCall("{? = call deleteCharacter(?)}");
					cs.registerOutParameter(1, Types.INTEGER);
					cs.setInt(2, Integer.valueOf(manager.getCharacterChosen()));
					
					cs.execute();
					int ret = cs.getInt(1);
					if(ret==0) {
						manager.switchPage("character");
					}else {
						JOptionPane.showMessageDialog(null, "Something Went Wrong Deleting Character, character does not exist.");
						return;
					}
					
				}catch(SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					//e.printStackTrace();
				}
			}
		});
		this.statsPanel.add(deleteMe);
		this.add(statsPanel, BorderLayout.WEST);
	}
	public void placeLabels() {
		this.statsPanel.add(name);
		this.statsPanel.add(race);
		this.statsPanel.add(background);
		this.statsPanel.add(alignment);
		this.statsPanel.add(MaxHP);
		this.statsPanel.add(HP);
		this.statsPanel.add(Str);
		this.statsPanel.add(Dex);
		this.statsPanel.add(Con);
		this.statsPanel.add(Int);
		this.statsPanel.add(Wis);
		this.statsPanel.add(Cha);
		this.add(classes,BorderLayout.EAST);
		classes.setSize(15, 10);
		//this.add(level);
	}
	
	public void initJLabels() {
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		this.name = new JLabel();
		this.race = new JLabel();
		this.background = new JLabel();
		this.MaxHP = new JLabel();
		this.HP = new JLabel();
		this.alignment = new JLabel();
		this.Dex = new JLabel();
		this.Int = new JLabel();
		this.Wis = new JLabel();
		this.Con = new JLabel();
		this.Cha = new JLabel();
		this.Str = new JLabel();
		this.classes=new JScrollPane();
		this.classesTable = new JTable();
		//this.level = new JLabel();
		name.setBorder(border);
		race.setBorder(border);
		background.setBorder(border);
		MaxHP.setBorder(border);
		HP.setBorder(border);
		alignment.setBorder(border);
		Dex.setBorder(border);
		Int.setBorder(border);
		Wis.setBorder(border);
		Con.setBorder(border);
		Cha.setBorder(border);
		Str.setBorder(border);
		//level.setBorder(border);
	}
	
	public void updateForCurrentCharacter() {
		this.characterID = manager.getCharacterChosen();
		this.currentCharDetails = this.getDetails();
		this.name.setText("Name: "+currentCharDetails.get("Name"));
		this.race.setText("Race: "+ currentCharDetails.get("Race"));
		this.background.setText("Background: "+currentCharDetails.get("Background"));
		this.alignment.setText("Alignment: "+ currentCharDetails.get("Alignment"));
		this.MaxHP.setText("Max  HP: "+ currentCharDetails.get("MaxHP"));
		this.HP.setText("HP: "+ currentCharDetails.get("HP"));
		this.Dex.setText("Dex: "+currentCharDetails.get("Dex"));
		this.Int.setText("Int: "+currentCharDetails.get("Int"));
		this.Wis.setText("Wis: "+currentCharDetails.get("Wis"));
		this.Con.setText("Con: "+currentCharDetails.get("Con"));
		this.Cha.setText("Cha: "+currentCharDetails.get("Cha"));
		this.Str.setText("Str: "+currentCharDetails.get("Str"));
		
		try {
			CallableStatement c = manager.getConnection().prepareCall("{? = call getAllClassLevels(?)}");
			c.registerOutParameter(1, Types.INTEGER);
			c.setInt(2, Integer.valueOf(manager.getCharacterChosen()));
			
			ResultSet rs = c.executeQuery();
			
			this.classesTable = new JTable(buildTableModel(rs));
			
//			int retval=c.getInt(1);
//			if(retval==1) {
//				JOptionPane.showMessageDialog(null, "That character does not exist in our records");
//				return;
//			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			//e.printStackTrace();
		}
		this.classes.setViewportView(this.classesTable);
		
		//this.level.setText("Level: "+ currentCharDetails.get("Level"));
	}
	
	
	
	
	private HashMap<String,String> getDetails(){
		HashMap<String,String> stats = new HashMap<>();
		try {
			CallableStatement cstmt = this.manager.getConnection().prepareCall("{? = call getCharacterInfo(?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setInt(2, Integer.valueOf(this.characterID));
			
			ResultSet rs = cstmt.executeQuery();
			rs.next();
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
			stats.put("Level", rs.getString("Level"));
			
//			int retval=cstmt.getInt(1);
//			if(retval==1) {
//				JOptionPane.showMessageDialog(null, "That character does not exist in our records.");
//			}
			
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			//e.printStackTrace();
		}
		
		return stats;
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
	
	class backListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchPage("character");
		}	
	}
	
	class toItemsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchPage("Items");
		}	
	}
	
	class toSpellsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchPage("Spells");
		}	
	}
	
	class toLangListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchPage("Lang");
		}	
	}
	
	class toTraitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchPage("Trait");
		}	
	}
	
	class toSkillListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchPage("Skill");
		}	
	}
}