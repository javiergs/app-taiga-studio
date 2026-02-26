package home;

import project.ProjectPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Entry point for the application.
 *
 * @author Johnny Parini
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 */
public class Main extends JFrame {
	
	public Main() {
		// views
		MainManager manager = new MainManager(this);
		MainManager.addHomePanel(new HomePanel());
		MainManager.addProjectArenaPanel(new ProjectArenaPanel());
		MainManager.addProjectPanel(new ProjectPanel());
		// assemble
		StatusBarPanel statusBar = new StatusBarPanel();
		setLayout(new BorderLayout());
		add(new JPanel(), BorderLayout.CENTER);
		add(statusBar, BorderLayout.SOUTH);
		// start on a project list
		MainManager.changeView(MainManager.HOME_PANEL);
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.setTitle("Taiga Studio 3.0");
		main.setSize(1024, 768);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
	}
	
}
