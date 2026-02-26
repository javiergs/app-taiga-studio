package data;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Blackboard class serves as a central repository for managing projects and their associated data.
 *
 * @author Isaac-Pruett
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 */
public class Blackboard extends PropertyChangeSupport {
	
	private static Blackboard instance;
	private Map<String, Project> projects;
	private Project activeProject;
	
	private Blackboard() {
		super(new Object());
		projects = new ConcurrentHashMap<>();
	}
	
	public static Blackboard getInstance() {
		if (instance == null) {
			instance = new Blackboard();
		}
		return instance;
	}
	
	public void addProject(Project project) {
		if (project != null)
			projects.put(project.getId() + "", project);
		 firePropertyChange("projects", null, null);
	}
	
	public void deleteProject(Project project) {
		if (project == null) return;
		data.Blackboard bb = data.Blackboard.getInstance();
		bb.removeProject(project);        // <-- remove the exact one
		bb.setActiveProject(null);        // optional
		firePropertyChange("projects", null, null);
		//projects.remove(p.getId());
		//if (activeProject != null && (activeProject.getId() + "").equals(p.getId())) {
		//	activeProject = null;
		//}
	}
	
	private void removeProject(Project project) {
		projects.remove(project.getId() + "");
	}
	
	public Collection<Project> getProjects() {
		return new ArrayList<>(projects.values());
	}
	
	public Project getProject(String id) {
		return projects.get(id);
	}
	
	public Project getActiveProject() {
		return activeProject;
	}
	
	public void setActiveProject(Project project) {
		if (project != null && projects.containsKey(project.getId() + "")) {
			activeProject = project;
		} else {
			activeProject = null;
		}
		firePropertyChange("activeProject", null, activeProject);
	}
	
}
