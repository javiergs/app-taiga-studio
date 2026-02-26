package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains information about the project's title, description, backlog, and sprints.
 * Manage the active sprint and the list of sprints associated with the project.
 *
 * @author Owen Sam
 * @author Owen Ledrick
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 */
public class Project {
	
	private static long ID_COUNTER = 0;
	
	private String title;
	private String description;
	private long id;
	private Backlog backlog;
	private List<Sprint> sprints;
	private Sprint activeSprint;
	
	public Project() {
		this("New Project", "Project Description");
	}
	
	public Project(String title, String description) {
		ID_COUNTER++;
		this.id = ID_COUNTER;
		this.title = title;
		this.description = description;
		this.backlog = new Backlog();
		this.sprints = new ArrayList<>();
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public long getId() {
		return id;
	}
	
	public Backlog getBacklog() {
		return backlog;
	}
	
	public void setBacklog(Backlog backlog) {
		if (backlog != null)
			this.backlog = backlog;
	}
	
	public List<Sprint> getSprints() {
		return new ArrayList<>(sprints);
	}
	
	public Sprint getActiveSprint() {
		return activeSprint;
	}
	
	public void setActiveSprint(Sprint activeSprint) {
		if (activeSprint == null)
			return;
		
		if (sprints.contains(activeSprint))
			this.activeSprint = activeSprint;
		else {
			sprints.add(activeSprint);
			this.activeSprint = activeSprint;
		}
	}
	
	public void addSprint(Sprint sprint) {
		if (sprint != null)
			sprints.add(sprint);
	}
	
	public void removeSprint(Sprint sprint) {
		sprints.remove(sprint);
		if (activeSprint != null && activeSprint.equals(sprint)) {
			activeSprint = null;
		}
	}
	
}