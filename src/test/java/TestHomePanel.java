import data.*;
import home.HomePanel;
import home.MainManager;
import home.ProjectArenaPanel;
import project.ProjectPanel;

import javax.swing.*;
import java.util.Date;
import java.util.List;

/**
 * Test class for the ProjectListPanel.
 * This class creates a JFrame and adds the ProjectListPanel to it for testing purposes.
 *
 * @author Johnny Parini
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 */

public class TestHomePanel extends JFrame {
	
	public TestHomePanel() {
		// Add blackboard data
		Blackboard blackboard = Blackboard.getInstance();
		Project project1 = new data.Project("Project Alpha", "This is the first test project.");
		blackboard.addProject(project1);
		Project project2 = new data.Project("Project Beta", "This is the second test project.");
		blackboard.addProject(project2);
		Project project3 = new data.Project("Project Gamma", "This is the third test project.");
		blackboard.addProject(project3);
		Project project4 = new data.Project("Project Delta", "This is the fourth test project.");
		blackboard.addProject(project4);
		Project project5 = new data.Project("Project Epsilon", "This is the fifth test project.");
		blackboard.addProject(project5);
		Project project6 = new data.Project("Project Zeta", "This is the sixth test project.");
		blackboard.addProject(project6);
		// add backlog data to project 1
		project1.getBacklog().addStory(new data.Story("User Story 1", "As a user, I want to be able to log in so that I can access my account	."));
		project1.getBacklog().addStory(new data.Story("User Story 2", "As a user, I want to be able to view my profile so that I can see my information."));
		project1.getBacklog().addStory(new data.Story("User Story 3", "As a user, I want to be able to edit my profile so that I can update my information."));
		project1.getBacklog().addStory(new data.Story("User Story 4", "As a user, I want to be able to log out so that I can secure my account."));
		// Sprint data for project 1
		// 1) Make project1 active
		blackboard.setActiveProject(project1);

		// 2) Create and attach an active sprint (7 days total)
		long DAY = 86400000L;
		Date begin = new Date(System.currentTimeMillis());
		Date end = new Date(begin.getTime() + 6 * DAY);
		Sprint sprint1 = new Sprint("Sprint 1", begin, end);
		project1.setActiveSprint(sprint1);
		// 3) Pull stories from backlog (or keep references when you created them)
		List<Story> backlogStories = project1.getBacklog().getStories();
		// 4) Give points, tasks, and put them into sprint
		Story s1 = backlogStories.get(0);
		s1.setPoints(8);
		s1.getTasks().add(new Task("Implement login UI"));
		s1.getTasks().add(new Task("Validate credentials"));
		s1.getTasks().add(new Task("Hook to auth service"));
		Story s2 = backlogStories.get(1);
		s2.setPoints(5);
		s2.getTasks().add(new Task("Profile page layout"));
		s2.getTasks().add(new Task("Fetch user data"));
		Story s3 = backlogStories.get(2);
		s3.setPoints(3);
		s3.getTasks().add(new Task("Edit form fields"));
		s3.getTasks().add(new Task("Save changes"));
		Story s4 = backlogStories.get(3);
		s4.setPoints(2);
		s4.getTasks().add(new Task("Logout button"));
		s4.getTasks().add(new Task("Clear session"));
		sprint1.addStory(s1);
		sprint1.addStory(s2);
		sprint1.addStory(s3);
		sprint1.addStory(s4);
		project1.setActiveSprint(sprint1);
		// Day 1 completions
		Task t = s1.getTask(0);
		if (t != null) t.setCompletionDate(new Date(begin.getTime() + 1L * DAY));
		
		t = s2.getTask(0);
		if (t != null) t.setCompletionDate(new Date(begin.getTime() + 1L * DAY));
// Day 2 completions
		t = s1.getTask(1);
		if (t != null) t.setCompletionDate(new Date(begin.getTime() + 2L * DAY));
// Day 3 completions
		t = s3.getTask(0);
		if (t != null) t.setCompletionDate(new Date(begin.getTime() + 3L * DAY));
// Day 4 completions
		t = s2.getTask(1);
		if (t != null) t.setCompletionDate(new Date(begin.getTime() + 4L * DAY));
// Day 5 completions
		t = s4.getTask(0);
		if (t != null) t.setCompletionDate(new Date(begin.getTime() + 5L * DAY));
		t = s4.getTask(1);
		if (t != null) t.setCompletionDate(new Date(begin.getTime() + 5L * DAY));
		
		
		// Initialize HomePanel
		HomePanel home = new HomePanel();
		// Initialize MainManager
		MainManager manager = new MainManager(this);
		MainManager.addHomePanel(home);
		MainManager.addProjectArenaPanel(new ProjectArenaPanel());
		MainManager.addProjectPanel(new ProjectPanel());
		MainManager.changeView(MainManager.HOME_PANEL);
	}
	
	public static void main(String[] args) {
		TestHomePanel main = new TestHomePanel();
		main.setTitle("Testing HomePanel");
		main.setSize(1024, 768);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
	}
	
}