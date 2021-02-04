import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PageManager{
	private JFrame frame;
	private JPanel panel;
	private LoginHelper loginHelper;
	private LogInPage loginPage;
	private Connection connection;
	private CharactersScreen characterScreen;
	private String characterChosen;
	
	public PageManager(JFrame frame, JPanel panel) {
		this.setPanel(panel);
		this.setFrame(frame);
		this.loginHelper = new LoginHelper("titan.csse.rose-hulman.edu","DnD_goodriat_oriansaj_parkhuca30");
		this.loginPage = new LogInPage(this, loginHelper);
		this.setCharacterChosen("");
		
		
		
		
		this.getPanel().add(this.loginPage);
		
		this.loginPage.setVisible(true);;
		//System.out.println(this.loginPage.isEnabled());
		this.getFrame().repaint();
	}
	
	public void switchPage(String name) {
		this.characterScreen.setVisible(false);
		this.loginPage.setVisible(false);
		
		switch(name) {
		case "character": this.characterScreen.setVisible(true);; break;
		case "login": this.loginPage.setVisible(true); break;
		default: break;
		}
		
//		if {
//			this.characterScreen.hide();
//		}
//		if (name.equals("login")) {
//			this.loginPage.show();
//		}
//		if (name.equals("login")) {
//			this.loginPage.hide();
//		}
//		if (name.equals("stats1")) {
//			this.loginPage.show();
//		}
//		if (name.equals("stats1")) {
//			this.loginPage.hide();
//		}
		
		
		this.getFrame().pack();
		this.getFrame().repaint();
		//System.out.println("test2");
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
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

	public CharactersScreen getCharacterScreen() {
		return characterScreen;
	}

	public void setCharacterScreen(CharactersScreen characterScreen) {
		this.characterScreen = characterScreen;
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