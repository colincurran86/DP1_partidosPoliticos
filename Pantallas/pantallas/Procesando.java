package pantallas;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class Procesando extends JPanel {

	/**
	 * Create the panel.
	 */
	public Procesando() {
		setLayout(null);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(54, 78, 541, 25);
		add(progressBar);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(54, 159, 541, 377);
		add(textArea);

	}
}
