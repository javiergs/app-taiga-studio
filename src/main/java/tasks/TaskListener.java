package tasks;

import data.Story;
import data.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * This class is the nanny for creating and editing tasks
 *
 * @author Alexander Bloomer
 * @author Collin Howard
 * @version 3.0
 */
public class TaskListener {
	
	private final JFrame main;
	
	public TaskListener(JFrame main) {
		this.main = main;
	}
	
	public void SaveTaskButton(Task oldTask, String subject, String body, Story story) {
		if (subject.equals("")) {
			return;
		}
		Task task = story.getTasks().stream().filter(t -> (t.getId() + "").equals(oldTask.getId())).findFirst().orElse(null);
		task.setSubject(subject);
		task.setBody(body);
		main.setContentPane(new TaskListPanel(main, this, story));
		main.revalidate();
		main.repaint();
		for (Task t : story.getTasks()) {
			System.out.println(t);
		}
	}
	
	public void CreateTaskButton(String subject, String body, Story story) {
		if (subject.equals("")) {
			return;
		}
		story.addTask(subject, body);
		main.setContentPane(new TaskListPanel(main, this, story));
		main.revalidate();
		main.repaint();
		
		for (Task t : story.getTasks()) {
			System.out.println(t);
		}
	}
	
	public void OpenEditTaskPanel(ActionEvent e, Story story) {
		String id = e.getActionCommand();
		Task task = story.getTasks().stream().filter(t -> (t.getId() + "").equals(id)).findFirst().orElse(null);
		main.setContentPane(new TaskPanel(task, this, story));
		main.revalidate();
		main.repaint();
	}
	
	public void OpenCreateTaskPanel(ActionEvent e, Story story) {
		main.setContentPane(new TaskPanel(null, this, story));
		main.revalidate();
		main.repaint();
	}
	
}