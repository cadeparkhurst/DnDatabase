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

public class SkillScreen extends JPanel{
	//private GridLayout layout;
	private PageManager manager;
	private JLabel acro;
	private JLabel anim;
	private JLabel arc;
	private JLabel ath;
	private JLabel dec;
	private JLabel his;
	private JLabel ins;
	private JLabel inti;
	private JLabel inv;
	private JLabel med;
	private JLabel nat;
	private JLabel perc;
	private JLabel perf;
	private JLabel pers;
	private JLabel reg;
	private JLabel soh;
	private JLabel stl;
	private JLabel sur;
	private JButton backButton;
	private String characterID;
	private HashMap<String,String> currentCharDetails;
	private JButton deleteMe;
	
	
	public SkillScreen(PageManager manager) {
		this.manager = manager;
		this.backButton = new JButton("Back To Stats");
		backButton.addActionListener(new backListener());
		this.add(backButton);
		
		initJLabels();
		
		
		//this.layout = new GridLayout(7,4);
		//this.setLayout(layout);
		placeLabels();
		
	}
	public void placeLabels() {
		this.add(acro);
		this.add(anim);
		this.add(arc);
		this.add(ath);
		this.add(dec);
		this.add(his);
		this.add(ins);
		this.add(inti);
		this.add(inv);
		this.add(med);
		this.add(nat);
		this.add(perc);
		this.add(perf);
		this.add(pers);
		this.add(reg);
		this.add(soh);
		this.add(stl);
		this.add(sur);
	}
	
	public void initJLabels() {
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		acro = new JLabel();
		anim = new JLabel();
		arc = new JLabel();
		ath = new JLabel();
		dec = new JLabel();
		his = new JLabel();
		ins = new JLabel();
		inti = new JLabel();
		inv = new JLabel();
		med = new JLabel();
		nat = new JLabel();
		perc = new JLabel();
		perf = new JLabel();
		pers = new JLabel();
		reg = new JLabel();
		soh = new JLabel();
		stl = new JLabel();
		sur = new JLabel();
		acro.setBorder(border);
		anim.setBorder(border);
		arc.setBorder(border);
		ath.setBorder(border);
		dec.setBorder(border);
		his.setBorder(border);
		ins.setBorder(border);
		inti.setBorder(border);
		inv.setBorder(border);
		med.setBorder(border);
		nat.setBorder(border);
		perc.setBorder(border);
		perf.setBorder(border);
		pers.setBorder(border);
		reg.setBorder(border);
		soh.setBorder(border);
		stl.setBorder(border);
		sur.setBorder(border);
	}
	
	public void updateForCurrentCharacter() {
		this.characterID = manager.getCharacterChosen();
		//System.out.println("Current details: "+ characterID);
		this.currentCharDetails = this.getDetails();
		acro.setText("Acrobatics "+this.currentCharDetails.get("Acrobatics"));
		anim.setText("Animal Handling "+this.currentCharDetails.get("Animal Handling"));
		arc.setText("Arcana "+this.currentCharDetails.get("Arcana"));
		ath.setText("Athletics "+this.currentCharDetails.get("Athletics"));
		dec.setText("Deception"+this.currentCharDetails.get("Deception"));
		his.setText("History "+this.currentCharDetails.get("History"));
		ins.setText("Insight "+this.currentCharDetails.get("Insight"));
		inv.setText("Investigation "+this.currentCharDetails.get("Investigation"));
		inti.setText("Intimidation "+this.currentCharDetails.get("Intimidation"));
		med.setText("Medicine "+this.currentCharDetails.get("Medicine"));
		nat.setText("Nature "+this.currentCharDetails.get("Nature"));
		perc.setText("Perception "+this.currentCharDetails.get("Perception"));
		perf.setText("Performance "+this.currentCharDetails.get("Performance"));
		pers.setText("Persuasion "+this.currentCharDetails.get("Persuasion"));
		reg.setText("Religion "+this.currentCharDetails.get("Religion"));
		soh.setText("Sleight of Hand"+this.currentCharDetails.get("Sleight of Hand"));
		stl.setText("Stealth "+this.currentCharDetails.get("Stealth"));
		sur.setText("Survival "+this.currentCharDetails.get("Survival"));
	}
	
	
	
	
	private HashMap<String,String> getDetails(){
		HashMap<String,String> stats = new HashMap<>();
		HashMap<String,String> skillValues = new HashMap<String, String>();
		try {
			CallableStatement cstmt = this.manager.getConnection().prepareCall("{? = call getCharacterInfo(?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setInt(2, Integer.valueOf(this.characterID));
			
			ResultSet rs = cstmt.executeQuery();
			
			rs.next();
			stats.put("Dex", rs.getString("Dex"));
			stats.put("Int", rs.getString("Int"));
			stats.put("Wis", rs.getString("Wis"));
			stats.put("Cha", rs.getString("Cha"));
			stats.put("Con", rs.getString("Con"));
			stats.put("Str",rs.getString("Str"));
			stats.put("Level", rs.getString("Level"));
			
//			int ret = cstmt.getInt(1);
//			if(ret==1) {
//				JOptionPane.showMessageDialog(null, "That character does not exist.");
//				return null;
//			}
			
			CallableStatement cstmt2 = this.manager.getConnection().prepareCall("{? = call getAllSkillsProfs(?)}");
			cstmt2.registerOutParameter(1, Types.INTEGER);
			cstmt2.setInt(2, Integer.valueOf(this.characterID));
			
			ResultSet rs2 = cstmt2.executeQuery();
			
			while(rs2.next()) {
				skillValues.put(rs2.getString("Name"), String.valueOf(getBonus(rs2.getString("relatedStat"), rs2.getObject("CharacterID") != null, stats)));
			}
			
//			if(cstmt2.getInt(1)!=0) {
//				JOptionPane.showMessageDialog(null, "That character does not exist.");
//				return null;
//			}
			
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			//e.printStackTrace();
		}
		
		return skillValues;
	}
	
	private int getBonus(String baseStat, boolean hasProf, HashMap<String,String> stats) {
		int profBonus = (int) Math.ceil((Integer.valueOf(stats.get("Level"))) / 4)+1;
		int value = (int) Math.floor((Integer.valueOf(stats.get(baseStat))-10)/2);
		if (hasProf) {
			value += profBonus;
		}
		return value;
	}
	
	class backListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.switchPage("Details");
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
