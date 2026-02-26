package stories;

import data.Story;
import project.BacklogPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * This class is the individual panels for each story in the backlog
 *
 * @author Nick Grant
 * @version 3.0
 */
public class StoryItemPanel extends JPanel {
	
	private final Story story;
	private final BacklogPanel backlogPanel;
	
	public StoryItemPanel(BacklogPanel backlogPanel, Story story, StoryReorderHandler handler) {
		this.story = story;
		this.backlogPanel = backlogPanel;
		setLayout(null); // absolute positioning
		setPreferredSize(new Dimension(0, 60));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		JLabel ID = new JLabel("#" + story.getId());
		ID.setForeground(new Color(37, 168, 157));
		ID.setBounds(10, 25, 30, 20);
		add(ID);
		JLabel subject = new JLabel(story.getTitle());
		subject.setBounds(35, 25, 200, 20);
		add(subject);
		JLabel points = new JLabel("Points: " + story.getPoints());
		points.setBounds(600, 20, 100, 20);
		JButton details = new JButton("Details");
		details.setBounds(10, 5, 80, 15);
		add(details);
		JComboBox<String> statusBtn = new JComboBox<>(new String[]{
			"New", "Ready", "In Progress", "Ready to Test", "Done", "Archived"
		});
		statusBtn.setSelectedItem(story.getStatus());
		statusBtn.addActionListener(e -> {
			story.setStatus((String) statusBtn.getSelectedItem());
		});
		statusBtn.setFocusable(false);
		JButton menuBtn = new JButton("⋮");
		menuBtn.setFocusable(false);
		menuBtn.setBorderPainted(false);
		menuBtn.setContentAreaFilled(false);
		menuBtn.setPreferredSize(new Dimension(20, 30));
		// Attach popup menu
		JPopupMenu menu = buildMenu();
		menuBtn.addActionListener(e -> menu.show(menuBtn, 0, menuBtn.getHeight()));
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int w = backlogPanel.getWidth();
				menuBtn.setBounds(w - 60, 10, 30, 30);
				points.setBounds(w - 130, 20, 100, 20);
				statusBtn.setBounds(w - 270, 18, 110, 30);
			}
		});
		add(menuBtn);
		add(statusBtn);
		add(points);
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		setTransferHandler(handler);
		addMouseListener(handler.createDragStarter(this));
	}
	
	private JPopupMenu buildMenu() {
		JPopupMenu menu = new JPopupMenu();
		JMenuItem edit = new JMenuItem("Edit");
		//edit.addActionListener(e -> windowSwitcher.changeWindow(new EditStoryPanel(story, backlogPanel, windowSwitcher)));
		menu.add(edit);
		JMenuItem delete = new JMenuItem("Delete");
		delete.addActionListener(e -> {
			backlogPanel.deleteStory(story);
			backlogPanel.refresh();
		});
		menu.add(delete);
		return menu;
	}
	
	public Story getStory() {
		return story;
	}
 
}
