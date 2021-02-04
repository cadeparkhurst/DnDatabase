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
	private String characterChosen;
	
	public PageManager(JFrame frame, JPanel panel) {
		this.setPanel(panel);
		this.setFrame(frame);
		this.loginHelper = new ConnectionHelper("titan.csse.rose-hulman.edu","DnD_goodriat_oriansaj_parkhuca30");
		this.loginHelper.connect();
		this.loginPage = new LogInPage(this, loginHelper);
		this.characterScreen = new CharactersScreen(this);
		this.detailsPage = new CharacterDetails(this);
		//this.setCharacterChosen("");
		
		
		
		
		this.getPanel().add(this.loginPage);
		this.getPanel().add(this.characterScreen);
		this.getPanel().add(this.detailsPage);
		
		this.switchPage("login");
		//System.out.println(this.loginPage.isEnabled());
		this.getFrame().repaint();
	}
	
	public void switchPage(String name) {
		this.characterScreen.setVisible(false);
		this.loginPage.setVisible(false);
		this.detailsPage.setVisible(false);
		
		switch(name) {
		case "character": this.characterScreen.setVisible(true);; break;
		case "login": this.loginPage.setVisible(true); break;
		case "Details": this.detailsPage.setVisible(true);
						this.detailsPage.updateForCurrentCharacter();
						break; 
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
	
}