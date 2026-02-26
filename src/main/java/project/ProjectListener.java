package project;

import data.Blackboard;
import data.Project;
import home.MainManager;

/**
 * Handles the logic of the project list screen,
 * including switching to the edit screen and saving/deleting projects
 *
 * @author Owen Sam
 * @author Owen Ledrick
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 *
 */
public class ProjectListener {

	public void editScreen(Project project) {
		Blackboard.getInstance().setActiveProject(project);
		MainManager.changeView(MainManager.PROJECT_PANEL);
	}
	
	public void close() {
		Blackboard.getInstance().setActiveProject(null);
		MainManager.changeView(MainManager.HOME_PANEL);
	}
	
	public void save(Project project, String title, String description) {
		project.setTitle(title);
		project.setDescription(description);
		Blackboard.getInstance().addProject(project);
		MainManager.changeView(MainManager.HOME_PANEL);
	}
	
	public void delete(Project project) {
		Blackboard.getInstance().deleteProject(project);
		MainManager.changeView(MainManager.HOME_PANEL);
	}
	
}