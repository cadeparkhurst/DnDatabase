import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	/** 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		PageManager manager = new PageManager(frame, panel);
		frame.add(panel);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.repaint();
		
	}

}