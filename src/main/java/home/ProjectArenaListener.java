package home;

import data.Blackboard;


/**
 * Button Nanny to return to the home panel from the Scrum Screen.
 *
 * @author Johnny Parini
 * @author Dr.Gonzalez-Sanchez
 * @version 1.0
 */
public class ProjectArenaListener {
  
	public void returnHome() {
		Blackboard.getInstance().setActiveProject(null);
		MainManager.changeView(MainManager.HOME_PANEL);
	}
	
}
