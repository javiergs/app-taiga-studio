package home;

import data.Blackboard;
import project.ProjectPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Manages the views of the application
 *
 * @author Dr. Gonzalez-Sanchez
 * @version 1.0
 */
public class MainManager {
	
	public static final int HOME_PANEL = 0;
	public static final int PROJECT_ARENA_PANEL = 1;
	public static final int PROJECT_PANEL = 2;
	public static final int TASKBOARD_PANEL = 3;
	
	private static JFrame frame;
	private static JPanel[] views;
	
	public MainManager(JFrame frame) {
		this.frame = frame;
		this.views = new JPanel[4];
	}
	
	public static void addHomePanel(HomePanel homePanel) {
		views[HOME_PANEL] = homePanel;
		JPanel panel = new JPanel();
		JLabel label = new JLabel("There is not enough information to display this view yet.");
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
		for (int i = 0; i < views.length; i++) {
			if (views[i] == null) {
				views[i] = panel;
			}
		}
		Blackboard.getInstance().addPropertyChangeListener(homePanel);
	}
	
	public static void addProjectArenaPanel(ProjectArenaPanel projectArenaPanel) {
		System.out.println("Adding ProjectArenaPanel to MainManager");
		views[PROJECT_ARENA_PANEL] = projectArenaPanel;
		Blackboard.getInstance().addPropertyChangeListener(projectArenaPanel);
	}
	
	public static void addProjectPanel(ProjectPanel projectPanel) {
		views[PROJECT_PANEL] = projectPanel;
		Blackboard.getInstance().addPropertyChangeListener(projectPanel);
	}
	
	public static void changeView (int view) {
		if (frame == null) return;
		frame.getContentPane().removeAll();
		if (views!=null && views[view] == null) {
			JPanel panel = new JPanel();
			JLabel label = new JLabel("View not implemented yet");
			panel.add(label);
			frame.getContentPane().add(panel, BorderLayout.CENTER);
		} else {
			frame.getContentPane().add(views[view], BorderLayout.CENTER);
		}
		frame.revalidate();
		frame.repaint();
	}
	
}
