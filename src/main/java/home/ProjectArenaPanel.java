package home;

import data.Blackboard;
import project.BacklogPanel;
import project.BurndownChart;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Main screen for a project, which includes the backlog and all controls to edit it
 *
 * @author Nick Grant
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 */
public class ProjectArenaPanel extends JPanel implements PropertyChangeListener {
	
	public ProjectArenaPanel() {
		JPanel body = (Blackboard.getInstance().getActiveProject() == null) ?
			createUIWithoutProjects() : createUIWithProjects();
		setLayout(new BorderLayout());
		add(body, BorderLayout.CENTER);
	}
	
	private JPanel createUIWithProjects() {
		var project = Blackboard.getInstance().getActiveProject();
		JButton exit = new JButton("Return");
		JPanel header = new JPanel(new BorderLayout());
		header.add(new JLabel(project.getTitle()), BorderLayout.WEST);
		header.add(exit, BorderLayout.EAST);
		exit.addActionListener(e -> new ProjectArenaListener().returnHome());
		JPanel chartPanel = new JPanel(new BorderLayout());
		System.out.println("active sprint: " + project.getActiveSprint());
		
		if (project.getActiveSprint() != null) {
			System.out.println("Adding burndown chart to ProjectArenaPanel");
			BurndownChart chart = new BurndownChart();
			chartPanel.add(chart, BorderLayout.CENTER);
		}
		BacklogPanel backlogPanel = new BacklogPanel();
		JPanel content = new JPanel(new BorderLayout(5, 5));
		content.add(chartPanel, BorderLayout.NORTH);
		content.add(backlogPanel, BorderLayout.CENTER);
		JScrollPane scroll = new JScrollPane(content);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		JPanel body = new JPanel(new BorderLayout());
		JPanel twoColumn = new JPanel(new GridLayout(1, 2, 10, 10));
		twoColumn.add(scroll);
		
		// temporal place for adding sprints
		JTabbedPane sprintTabs = new JTabbedPane();
		sprintTabs.addTab("Sprint 1", new JLabel("Sprint 1 content"));
		sprintTabs.addTab("Sprint 2", new JLabel("Sprint 2 content"));
		twoColumn.add(sprintTabs);
		
		body.add(header, BorderLayout.NORTH);
		body.add(twoColumn, BorderLayout.CENTER);
		return body;
	}
	
	private JPanel createUIWithoutProjects() {
		JLabel message = new JLabel("No active project enabled");
		message.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel body = new JPanel(new BorderLayout());
		body.add(message, BorderLayout.CENTER);
		return body;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		removeAll();
		JPanel body = (Blackboard.getInstance().getActiveProject() == null) ?
			createUIWithoutProjects() : createUIWithProjects();
		setLayout(new BorderLayout());
		add(body, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
}