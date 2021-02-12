import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

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
	private JLabel level;
	private JButton backButton;
	private JButton itemButton;
	private JButton spellButton;
	private JButton langButton;
	private JButton traitButton;
	private String characterID;
	private HashMap<String,String> currentCharDetails;
	private JButton deleteMe;
	
	
	public CharacterDetails(PageManager manager) {
		this.manager = manager;
		this.backButton = new JButton("Back To Characters");
		backButton.addActionListener(new backListener());
		this.add(backButton, BorderLayout.PAGE_START);
		
		initJLabels();
		
		
		//this.layout = new GridLayout(7,4);
		//this.setLayout(layout);
		placeLabels();
		this.setEnabled(false);
		this.itemButton = new JButton("To Items");
		itemButton.addActionListener(new toItemsListener());
		this.add(itemButton, BorderLayout.PAGE_START);
		
		this.spellButton = new JButton("To Spells");
		spellButton.addActionListener(new toSpellsListener());
		this.add(spellButton, BorderLayout.PAGE_START);
		
		this.langButton = new JButton("To Languages");
		langButton.addActionListener(new toLangListener());
		this.add(langButton, BorderLayout.PAGE_START);
		
		this.traitButton = new JButton("To Traits");
		traitButton.addActionListener(new toTraitListener());
		this.add(traitButton, BorderLayout.PAGE_START);
		
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
					e.printStackTrace();
				}
				
			}
			
		});
		this.add(deleteMe);
	}
	public void placeLabels() {
		this.add(name);
		this.add(race);
		this.add(background);
		this.add(alignment);
		this.add(MaxHP);
		this.add(HP);
		this.add(Str);
		this.add(Dex);
		this.add(Con);
		this.add(Int);
		this.add(Wis);
		this.add(Cha);
		this.add(level);
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
		this.level = new JLabel();
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
		level.setBorder(border);
	}
	
	public void updateForCurrentCharacter() {
		this.characterID = manager.getCharacterChosen();
		System.out.println("Current details: "+ characterID);
		this.currentCharDetails = this.getDetails();
		this.name.setText("Name: "+currentCharDetails.get("Name"));
		System.out.println(this.name.getText());
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
		this.level.setText("Level: "+ currentCharDetails.get("Level"));
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
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return stats;
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
}