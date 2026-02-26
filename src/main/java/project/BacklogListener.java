package project;

import data.Backlog;
import data.Blackboard;
import stories.NewStoryListener;
import stories.NewStoryNanny;
import stories.NewStoryPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener for the "New Story" button in the BacklogPanel.
 *
 * @author Jonathan Garcia
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 */
public class BacklogListener implements ActionListener {
	
	private final BacklogPanel backlogPanel;
	
	public BacklogListener(BacklogPanel backlogPanel) {
		this.backlogPanel = backlogPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Backlog backlog = Blackboard.getInstance().getActiveProject().getBacklog();
		NewStoryNanny nanny = new NewStoryNanny();
		JFrame f = new JFrame("New Story");
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Runnable onSuccess = () -> {
			backlogPanel.refresh();
			f.dispose();
		};
		Runnable onCancel = f::dispose;
		NewStoryPanel panel = new NewStoryPanel();
		new NewStoryListener(panel, nanny, backlog, onSuccess, onCancel);
		f.setContentPane(panel);
		f.setSize(800, 800);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}
