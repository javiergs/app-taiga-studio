package project;

import data.Backlog;
import data.Blackboard;
import data.Story;
import stories.*;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for displaying the backlog of a project,
 * allowing users to view, add, and reorder user stories.
 *
 * @author Nick Grant
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 */
public class BacklogPanel extends JPanel {
	
	private final JPanel listPanel;
	
	public BacklogPanel() {
		setLayout(new BorderLayout());
		JButton newStoryButton = getNewStoryButton();
		JPanel header = new JPanel(new BorderLayout());
		header.add(newStoryButton, BorderLayout.EAST);
		listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		
		listPanel.setBackground(Color.PINK);
		setBackground(Color.PINK);
		
		// assemble
		add(header, BorderLayout.NORTH);
		add(listPanel, BorderLayout.CENTER);
		refresh();
	}
	
	private JButton getNewStoryButton() {
		Backlog backlog = Blackboard.getInstance().getActiveProject().getBacklog();
		JButton newStoryBtn = new JButton("+ USER STORY");
		newStoryBtn.addActionListener(e -> {
			NewStoryNanny nanny = new NewStoryNanny();
			JFrame f = new JFrame("New Story");
			f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			Runnable onSuccess = () -> {
				refresh();
				f.dispose();
			};
			Runnable onCancel = f::dispose;
			NewStoryPanel panel = new NewStoryPanel();
			new NewStoryListener(panel, nanny, backlog, onSuccess, onCancel);
			f.setContentPane(panel);
			f.setSize(800, 800);
			f.setLocationRelativeTo(null);
			f.setVisible(true);
		});
		return newStoryBtn;
	}
	
	public void refresh() {
		Backlog backlog = Blackboard.getInstance().getActiveProject().getBacklog();
		listPanel.removeAll();
		StoryReorderHandler handler = new StoryReorderHandler(backlog, this);
		listPanel.setTransferHandler(handler);
		for (Story s : backlog.getStories()) {
			StoryItemPanel row = new StoryItemPanel(this, s, handler);
			listPanel.add(row);
		}
		listPanel.revalidate();
		listPanel.repaint();
	}
	
	public int getDropIndex(Point p) {
		int index = 0;
		for (Component c : listPanel.getComponents()) {
			Rectangle r = c.getBounds();
			if (p.y < r.y + r.height / 2) {
				return index;
			}
			index++;
		}
		return listPanel.getComponentCount();
	}
	
	public JPanel getListPanel() {
		return listPanel;
	}
	
	public void deleteStory(Story s) {
		Backlog backlog = Blackboard.getInstance().getActiveProject().getBacklog();
		backlog.deleteStory(s);
	}
	
}