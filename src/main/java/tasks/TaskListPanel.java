package tasks;

import data.Story;
import data.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Panel that displays a list of tasks with edit buttons and a create button for new tasks.
 * Panel has drag and drop functionality that will move the tasks around.
 *
 * @author Alexander Bloomer
 * @author Collin Howard
 * @author Kevin Mokarapiromya
 * @version 3.0
 */
public class TaskListPanel extends JPanel {
	
	private final JPanel taskPanel;
	
	public TaskListPanel(JFrame main, TaskListener nanny, Story story) {
		List<Task> tasks = story.getTasks();
		setLayout(new BorderLayout());
		taskPanel = new JPanel();
		taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
		for (Task t : tasks) {
			taskPanel.add(createTaskEntry(t, nanny, story));
		}
		JButton create = new JButton("Create");
		create.addActionListener(e -> nanny.OpenCreateTaskPanel(e, story));
		add(taskPanel, BorderLayout.CENTER);
		add(create, BorderLayout.SOUTH);
	}
	
	private JPanel createTaskEntry(Task t, TaskListener nanny, Story story) {
		JPanel entry = new JPanel(new BorderLayout());
		entry.setBorder(BorderFactory.createEtchedBorder());
		JLabel drag = new JLabel(" * ");
		drag.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		JTextField taskText = new JTextField(t.getSubject() + ": " + t.getBody());
		taskText.setEditable(false);
		JButton edit = new JButton("Edit");
		edit.setActionCommand(String.valueOf(t.getId()));
		edit.addActionListener(e -> nanny.OpenEditTaskPanel(e, story));
		entry.add(drag, BorderLayout.WEST);
		entry.add(taskText, BorderLayout.CENTER);
		entry.add(edit, BorderLayout.EAST);
		// TransferHandler is now a separate class file
		entry.setTransferHandler(new TaskTransferHandler(taskPanel));
		drag.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// drag originates from the entry panel (parent of the drag label)
				JPanel parent = (JPanel) ((JComponent) e.getComponent()).getParent();
				TransferHandler handler = parent.getTransferHandler();
				handler.exportAsDrag(parent, (InputEvent) e, TransferHandler.MOVE);
			}
		});
		return entry;
	}
}