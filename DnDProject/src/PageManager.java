import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PageManager{
	private JFrame frame;
	private JPanel panel;
	private ConnectionHelper loginHelper;
	private LogInPage loginPage;
	private CharacterDetails detailsPage;
	private Connection connection;
	private CharactersScreen characterScreen;
	private AddCharacterScreen addCharacterScreen;
	private ItemScreen itemScreen;
	private SpellScreen spellScreen;
	private LanguageScreen languageScreen;
	private TraitScreen traitScreen;
	private String characterChosen;
	private ProfChoosing profsScreen;
	private LangChoosing langScreen;
	private LevelUpScreen levelScreen;
	private SkillScreen skillScreen;
	
	
	public PageManager(JFrame frame, JPanel panel) {
		this.setPanel(panel);
		this.setFrame(frame);
		this.loginHelper = new ConnectionHelper("titan.csse.rose-hulman.edu","DnD_goodriat_oriansaj_parkhuca30");
		this.loginHelper.connect();
		this.loginPage = new LogInPage(this, loginHelper);
		this.characterScreen = new CharactersScreen(this);
		this.detailsPage = new CharacterDetails(this);
		this.addCharacterScreen = new AddCharacterScreen(this);
		this.itemScreen = new ItemScreen(this);
		this.spellScreen = new SpellScreen(this);
		this.languageScreen = new LanguageScreen(this);
		this.profsScreen = new ProfChoosing(this);
		this.traitScreen = new TraitScreen(this);
		this.langScreen = new LangChoosing(this);
		this.levelScreen = new LevelUpScreen(this);
		this.skillScreen = new SkillScreen(this);
		
		
		
		this.setCharacterChosen("");
		
		
		
		
		this.getPanel().add(this.loginPage);
		this.getPanel().add(this.characterScreen);
		this.getPanel().add(this.detailsPage);
		this.getPanel().add(this.addCharacterScreen);
		this.getPanel().add(this.itemScreen);
		this.getPanel().add(this.spellScreen);
		this.getPanel().add(this.languageScreen);
		this.getPanel().add(this.profsScreen);
		this.getPanel().add(this.traitScreen);
		this.getPanel().add(this.langScreen);
		this.getPanel().add(this.levelScreen);
		this.getPanel().add(this.skillScreen);

		
		this.switchPage("login");
		System.out.println(this.loginPage.isEnabled());
		this.getFrame().repaint();
	}
	
	public void switchPage(String name) {
		this.characterScreen.setVisible(false);
		this.loginPage.setVisible(false);
		this.detailsPage.setVisible(false);
		this.addCharacterScreen.setVisible(false);
		this.itemScreen.setVisible(false);
		this.spellScreen.setVisible(false);
		this.languageScreen.setVisible(false);
		this.profsScreen.setVisible(false);
		this.traitScreen.setVisible(false);
		this.langScreen.setVisible(false);
		this.levelScreen.setVisible(false);
		this.skillScreen.setVisible(false);
		
		switch(name) {
		case "character": this.characterScreen.updateScreen();
						  this.characterScreen.setVisible(true);
						  break;
		case "login": this.loginPage.setVisible(true); break;
		case "Details": this.detailsPage.updateForCurrentCharacter();
						this.detailsPage.setVisible(true);
						break; 
		case "AddCharacter": this.addCharacterScreen.setVisible(true); break;
		case "Items": this.itemScreen.updateForCharacter(); this.itemScreen.setVisible(true);  break;
		case "Spells": this.spellScreen.updateForCharacter(); this.spellScreen.setVisible(true);  break;
		case "Lang": this.languageScreen.updateForCharacter(); this.languageScreen.setVisible(true);  break;
		case "Trait": this.traitScreen.updateForCharacter(); this.traitScreen.setVisible(true);  break;
		case "Prof": this.profsScreen.setVisible(true); break;
		case "Language": this.langScreen.updatePage(); this.langScreen.setVisible(true); break;
		case "Level": this.levelScreen.setVisible(true); break;
		case "Skill": this.skillScreen.updateForCurrentCharacter(); this.skillScreen.setVisible(true); break;
		default: break;
		}
		
		
		this.getFrame().pack();
		this.getFrame().repaint();
		//System.out.println("test2");
	}

	
	public Connection getConnection() {
		return this.loginHelper.getConnection();
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public String getCharacterChosen() {
		return characterChosen;
	}

	public void setCharacterChosen(String characterChosen) {
		this.characterChosen = characterChosen;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public ProfChoosing getProfPage() {
		return this.profsScreen;
	}
	
}