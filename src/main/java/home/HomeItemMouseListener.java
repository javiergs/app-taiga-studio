package home;

import data.Blackboard;
import data.Project;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles the logic of clicking on a project in the project list, which should enter the project backlog
 *
 * @author Johnny Parini
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 */
public class HomeItemMouseListener implements MouseListener {
	
	private Project project;
	
	public HomeItemMouseListener(Project project) {
		this.project = project;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (project!=null) {
			Blackboard.getInstance().setActiveProject(project);
			MainManager.changeView(MainManager.PROJECT_ARENA_PANEL);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
}
