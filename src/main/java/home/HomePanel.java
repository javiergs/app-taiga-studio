package home;

import data.Blackboard;
import data.Project;
import project.ProjectListener;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

/**
 * Lists all projects in the system
 *
 * @author Johnny Parini
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 */
public class HomePanel extends JPanel implements PropertyChangeListener {
	
	public HomePanel() {
		Collection<Project> projects = Blackboard.getInstance().getProjects();
		JPanel body;
		body = (projects.isEmpty()) ? createUIWithoutProjects() : createUIWithProjects();
		// top controls
		JButton newProjectButton = new JButton("New Project");
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topPanel.add(newProjectButton);
		// assemble
		setLayout(new BorderLayout());
		add(body, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);
		// nanny
		ProjectListener nanny = new ProjectListener();
		newProjectButton.addActionListener(e -> nanny.editScreen(null));
	}
	
	private JPanel createUIWithoutProjects() {
		JLabel message = new JLabel("No projects found. Click 'New Project' to create one.");
		message.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel body = new JPanel();
		body.setLayout(new BorderLayout());
		body.add(message, BorderLayout.CENTER);
		return body;
	}
	
	private JPanel createUIWithProjects() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel body = new JPanel();
		body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
		JScrollPane bodyScroll = new JScrollPane(body);
		bodyScroll.setBorder(BorderFactory.createEmptyBorder());
		body.removeAll();
		Collection<Project> projects = Blackboard.getInstance().getProjects();
		for (Project project : projects) {
			HomeItemPanel item = new HomeItemPanel(project);
			body.add(item);
			body.add(Box.createVerticalStrut(8));
		}
		panel.add(bodyScroll, BorderLayout.CENTER);
		return panel;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		removeAll();
		Collection<Project> projects = Blackboard.getInstance().getProjects();
		JPanel body;
		body = (projects.isEmpty()) ? createUIWithoutProjects() : createUIWithProjects();
		setLayout(new BorderLayout());
		add(body, BorderLayout.CENTER);
		// top controls
		JButton newProjectButton = new JButton("New Project");
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topPanel.add(newProjectButton);
		add(topPanel, BorderLayout.NORTH);
		// nanny
		ProjectListener nanny = new ProjectListener();
		newProjectButton.addActionListener(e -> nanny.editScreen(null));
		revalidate();
		repaint();
	}
	
}