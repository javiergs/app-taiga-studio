package tasks;

import data.Story;
import data.Task;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Panel for creating a new task or editing an existing task with input fields for subject and body.
 *
 * @author Alexander Bloomer
 * @author Collin Howard
 * @version 3.0
 */
public class TaskPanel extends JPanel {
	
	public TaskPanel(Task task, TaskListener nanny, Story story) {
		boolean isCreate = task == null;
		String subjectText = "";
		String bodyText = "";
		if (!isCreate) {
			subjectText = task.getSubject();
			bodyText = task.getBody();
		}
		setLayout(new BorderLayout(10, 10));
		JLabel title = isCreate ? new JLabel("Create Task", SwingConstants.CENTER) : new JLabel("Edit Task", SwingConstants.CENTER);
		title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
		JPanel center = new JPanel();
		JTextField subject;
		subject = new JTextField(subjectText);
		subject.setPreferredSize(new Dimension(0, 20));
		subject.setBorder(new LineBorder(Color.blue));
		JTextArea body;
		body = new JTextArea(bodyText);
		body.setLineWrap(true);
		body.setPreferredSize(new Dimension(0, 100));
		body.setBorder(new LineBorder(Color.blue));
		JButton saveButton = isCreate ? new JButton("CREATE") : new JButton("SAVE");
		if (isCreate) {
			saveButton.addActionListener(e -> nanny.CreateTaskButton(subject.getText(), body.getText(), story));
		} else {
			saveButton.addActionListener(e -> nanny.SaveTaskButton(task, subject.getText(), body.getText(), story));
		}
		center.setLayout(new BorderLayout(10, 10));
		center.add(subject, BorderLayout.NORTH);
		center.add(body, BorderLayout.CENTER);
		add(title, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(saveButton, BorderLayout.SOUTH);
	}
	
}
