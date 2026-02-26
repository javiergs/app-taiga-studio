package home;

import javax.swing.*;
import java.awt.*;

/**
 * StatusBar WILL BE simple JPanel that displays status messages to the user.
 *
 * @author Dr. Gonzalez-Sanchez
 * @version 0.1
 */
public class StatusBarPanel extends JPanel {
	
	public StatusBarPanel() {
		setBackground(new Color(176, 250, 192));
		setForeground(Color.white);
		JLabel statusLabel = new JLabel("We will use this space to display status messages to the user.");
		add(statusLabel);
	}
	
}
