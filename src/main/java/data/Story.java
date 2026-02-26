package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user story in an Agile project management context.
 * A Story has a unique ID, title, description, point value, status, priority, and a list of associated tasks.
 *
 * @author Jonathan Garcia
 * @author Collin Howard
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 */
public class Story {
	
	private static long ID_COUNTER = 0;
	
	private final long id;
	private String title;
	private String description;
	private int points;
	private String status;
	private int priority;
	private List<Task> tasks;
	
	public Story(String title, String description) {
		this(title, description, 0, "New", 0);
	}
	
	public Story(String title, String description, int points, String status, int priority) {
		ID_COUNTER++;
		this.id = ID_COUNTER;
		this.title = Objects.requireNonNull(title, "title");
		this.description = Objects.requireNonNull(description, "description");
		this.points = points;
		this.status = Objects.requireNonNull(status, "status");
		this.priority = priority;
		this.tasks = new ArrayList<>();
	}
	
	public long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = Objects.requireNonNull(title, "title");
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = Objects.requireNonNull(description, "description");
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = Objects.requireNonNull(status, "status");
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public List<Task> getTasks() {
		return new ArrayList<>(tasks);
	}
	
	public void addTask(Task task) {
		if (task != null)
			tasks.add(task);
	}
	
	public void setTasks(List<Task> tasks) {
		for (int i = 0; i < tasks.size(); i++) {
			addTask(tasks.get(i));
		}
	}
	
	public String toString() {
		return String.format("Story{id=%d, title='%s', description='%s', points=%d, status='%s', priority=%d, tasks=%s}",
			id, title, description, points, status, priority, tasks);
	}
	
	public void addTask(String subject, String body) {
		addTask(new Task(subject, body));
	}
	
	public Task getTask(int i) {
		if (i < 0 || i >= tasks.size())
			return null;
		return tasks.get(i);
	}
}