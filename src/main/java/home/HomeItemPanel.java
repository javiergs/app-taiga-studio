package home;

import data.Project;
import project.ProjectListener;

import javax.swing.*;
import java.awt.*;

/**
 * A single item in the project list, showing the title and description of the project and an edit + delete button
 *
 * @author Johnny Parini
 * @author Dr.Gonzalez-Sanchez
 * @version 3.1
 */
public class HomeItemPanel extends JPanel {
	
	private final int ROW_HEIGHT = 130;
	
	public HomeItemPanel(Project project) {
		setLayout(new BorderLayout(10, 10));
		this.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(new Color(120, 120, 120)),
			BorderFactory.createEmptyBorder(10, 12, 10, 12)
		));
		this.setBackground(new Color(151, 185, 255));
		
		// fixed height
		this.setPreferredSize(new Dimension(0, ROW_HEIGHT));
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, ROW_HEIGHT));
		this.setMinimumSize(new Dimension(0, ROW_HEIGHT));
		
		JLabel title = new JLabel(project.getTitle());
		JTextArea description = new JTextArea(project.getDescription());
		description.setEditable(false);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		description.setOpaque(false);
		description.setRows(3);
		description.setBorder(null);
		description.setFocusable(false);
		title.setFocusable(false);
		
		JPanel text = new JPanel(new BorderLayout());
		text.setOpaque(false);
		text.add(title, BorderLayout.NORTH);
		text.add(description, BorderLayout.CENTER);
		
		JButton editButton = new JButton("Edit");
		JButton deleteButton = new JButton("Delete");
		
		// Right-side button strip
		JPanel actions = new JPanel(new GridLayout(2, 1, 0, 8));
		actions.setOpaque(false);

// optional: control total width of the block
		actions.setPreferredSize(new Dimension(90, ROW_HEIGHT));
		actions.setMaximumSize(new Dimension(90, ROW_HEIGHT));
		actions.setMinimumSize(new Dimension(90, ROW_HEIGHT));
		
		actions.add(editButton);
		actions.add(deleteButton);
		
		this.add(text, BorderLayout.CENTER);
		this.add(actions, BorderLayout.EAST);
		
		// projectListener
		ProjectListener projectListener = new ProjectListener();
		
		editButton.addActionListener(e -> projectListener.editScreen(project));
		
		
		deleteButton.addActionListener(e -> {
			int choice = JOptionPane.showConfirmDialog(
				this,
				"Delete project \"" + safe(project.getTitle()) + "\"?\nThis cannot be undone.",
				"Confirm Delete",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE
			);
			if (choice == JOptionPane.YES_OPTION) {
				projectListener.delete(project);
			}
		});
		
		// keep your click-to-open behavior
		HomeItemMouseListener mouseNanny = new HomeItemMouseListener(project);
		title.addMouseListener(mouseNanny);
		description.addMouseListener(mouseNanny);
		addMouseListener(mouseNanny);
	}
	
	private String safe(String s) {
		return (s == null || s.isBlank()) ? "(untitled)" : s;
	}
}