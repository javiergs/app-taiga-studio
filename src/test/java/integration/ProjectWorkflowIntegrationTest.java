package integration;

import data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the complete workflow of project management.
 * These tests verify the interaction between Project, Sprint, Backlog, Story, and Task.
 *
 * @version 3.0
 */
@DisplayName("Project Management Integration Tests")
class ProjectWorkflowIntegrationTest {
	
	private Blackboard blackboard;
	private Project project;
	private long DAY = 86400000L;
	
	@BeforeEach
	void setUp() {
		blackboard = Blackboard.getInstance();
		project = new Project("Integration Test Project", "Testing complete workflow");
		blackboard.addProject(project);
	}
	
	@AfterEach
	void tearDown() {
		// Clean up
		blackboard.deleteProject(project);
		blackboard.setActiveProject(null);
	}
	
	@Test
	@DisplayName("Complete project workflow: Create project, add stories, create sprint, manage tasks")
	void testCompleteWorkflow() {
		// Step 1: Create stories and add to backlog
		Story story1 = new Story("User Login", "As a user, I want to login", 5, "New", 1);
		Story story2 = new Story("User Profile", "As a user, I want to view my profile", 3, "New", 2);
		Story story3 = new Story("Settings", "As a user, I want to change settings", 2, "New", 3);
		
		project.getBacklog().addStory(story1);
		project.getBacklog().addStory(story2);
		project.getBacklog().addStory(story3);
		
		assertEquals(3, project.getBacklog().size());
		
		// Step 2: Create and add tasks to stories
		story1.addTask("Create login form", "Design and implement UI");
		story1.addTask("Implement authentication", "Backend authentication logic");
		story1.addTask("Add validation", "Form validation");
		
		story2.addTask("Create profile page", "Design profile layout");
		story2.addTask("Fetch user data", "API integration");
		
		assertEquals(3, story1.getTasks().size());
		assertEquals(2, story2.getTasks().size());
		
		// Step 3: Create a sprint
		Date sprintStart = new Date(System.currentTimeMillis());
		Date sprintEnd = new Date(sprintStart.getTime() + 14 * DAY);
		Sprint sprint = new Sprint(sprintStart, sprintEnd);
		
		// Step 4: Move stories to sprint
		sprint.addStory(story1);
		sprint.addStory(story2);
		
		assertEquals(2, sprint.storyCount());
		assertEquals(3, project.getBacklog().size()); // backlog does not auto-remove stories
		
		// Step 5: Set sprint as active
		project.setActiveSprint(sprint);
		
		assertEquals(sprint, project.getActiveSprint());
		assertEquals(1, project.getSprints().size());
		
		// Step 6: Mark some tasks as complete
		Task task1 = story1.getTask(0);
		Task task2 = story1.getTask(1);
		
		Date completionDate = new Date(sprintStart.getTime() + 2 * DAY);
		task1.setCompletionDate(completionDate);
		task2.setCompletionDate(completionDate);
		
		assertNotNull(task1.getCompletionDate());
		assertNotNull(task2.getCompletionDate());
		
		// Step 7: Update story status
		story1.setStatus("In Progress");
		assertEquals("In Progress", story1.getStatus());
		
		// Step 8: Verify project state
		assertNotNull(project.getActiveSprint());
		assertTrue(project.getActiveSprint().containsStory(story1));
		assertTrue(project.getBacklog().getStories().contains(story3));
		
		// Step 9: Complete sprint and create new one
		Date sprint2Start = new Date(sprintEnd.getTime() + DAY);
		Date sprint2End = new Date(sprint2Start.getTime() + 14 * DAY);
		Sprint sprint2 = new Sprint(sprint2Start, sprint2End);
		
		sprint2.addStory(story3);
		project.addSprint(sprint2);
		project.setActiveSprint(sprint2);
		
		assertEquals(2, project.getSprints().size());
		assertEquals(sprint2, project.getActiveSprint());
	}
	
	@Test
	@DisplayName("Backlog prioritization workflow")
	void testBacklogPrioritization() {
		Story highPriority = new Story("Critical Bug", "Fix production bug", 8, "New", 1);
		Story mediumPriority = new Story("Feature Request", "Add new feature", 5, "New", 2);
		Story lowPriority = new Story("Improvement", "Code refactoring", 3, "New", 3);
		
		Backlog backlog = project.getBacklog();
		backlog.addStory(lowPriority);
		backlog.addStory(mediumPriority);
		backlog.addStory(highPriority);
		
		// Reorder: move high priority to top
		backlog.moveStory(highPriority, 0);
		
		assertEquals(highPriority, backlog.get(0));
		assertEquals(lowPriority, backlog.get(1));
		assertEquals(mediumPriority, backlog.get(2));
	}
	
	@Test
	@DisplayName("Sprint planning workflow")
	void testSprintPlanning() {
		// Create multiple stories with points
		Story s1 = new Story("Story 1", "Description", 5, "New", 1);
		Story s2 = new Story("Story 2", "Description", 3, "New", 2);
		Story s3 = new Story("Story 3", "Description", 8, "New", 3);
		Story s4 = new Story("Story 4", "Description", 2, "New", 4);
		
		project.getBacklog().addStory(s1);
		project.getBacklog().addStory(s2);
		project.getBacklog().addStory(s3);
		project.getBacklog().addStory(s4);
		
		// Sprint planning: select stories that fit in sprint (max 10 points)
		Date start = new Date();
		Date end = new Date(start.getTime() + 7 * DAY);
		Sprint sprint = new Sprint(start, end);
		
		int totalPoints = 0;
		int maxCapacity = 10;
		
		for (Story story : project.getBacklog().getStories()) {
			if (totalPoints + story.getPoints() <= maxCapacity) {
				sprint.addStory(story);
				totalPoints += story.getPoints();
			}
		}
		
		assertEquals(3, sprint.storyCount()); // s1(5) + s2(3) + s4(2) = 10 points
		assertTrue(sprint.containsStory(s1));
		assertTrue(sprint.containsStory(s2));
		assertTrue(sprint.containsStory(s4));
		assertFalse(sprint.containsStory(s3)); // Would exceed capacity
		
		project.setActiveSprint(sprint);
	}
	
	@Test
	@DisplayName("Task assignment and tracking workflow")
	void testTaskAssignment() {
		Story story = new Story("Feature", "Implement feature", 8, "New", 1);
		project.getBacklog().addStory(story);
		
		// Create developers
		Developer dev1 = new Developer("Alice");
		Developer dev2 = new Developer("Bob");
		
		// Create and assign tasks
		Task task1 = new Task("Backend", "Implement API");
		task1.setAssignee(dev1);
		
		Task task2 = new Task("Frontend", "Create UI");
		task2.setAssignee(dev2);
		
		Task task3 = new Task("Testing", "Write tests");
		task3.setAssignee(dev1);
		
		story.addTask(task1);
		story.addTask(task2);
		story.addTask(task3);
		
		assertEquals(3, story.getTasks().size());
		assertEquals("Alice", task1.getAssignee().getName());
		assertEquals("Bob", task2.getAssignee().getName());
		
		// Track progress
		assertNull(task1.getCompletionDate());
		
		task1.setCompletionDate(new Date());
		assertNotNull(task1.getCompletionDate());
		
		// Count completed tasks
		long completedTasks = story.getTasks().stream()
			.filter(t -> t.getCompletionDate() != null)
			.count();
		
		assertEquals(1, completedTasks);
	}
	
	@Test
	@DisplayName("Multiple projects in Blackboard workflow")
	void testMultipleProjects() {
		Project project2 = new Project("Second Project", "Another project");
		Project project3 = new Project("Third Project", "Yet another project");
		
		blackboard.addProject(project2);
		blackboard.addProject(project3);
		
		assertTrue(blackboard.getProjects().size() >= 3);
		
		// Switch active projects
		blackboard.setActiveProject(project);
		assertEquals(project, blackboard.getActiveProject());
		
		blackboard.setActiveProject(project2);
		assertEquals(project2, blackboard.getActiveProject());
		
		// Clean up
		blackboard.deleteProject(project2);
		blackboard.deleteProject(project3);
	}
	
	@Test
	@DisplayName("Story lifecycle workflow")
	void testStoryLifecycle() {
		Story story = new Story("User Story", "As a user...", 5, "New", 1);
		
		// Add to backlog
		project.getBacklog().addStory(story);
		assertEquals("New", story.getStatus());
		
		// Move to sprint
		Date start = new Date();
		Date end = new Date(start.getTime() + 7 * DAY);
		Sprint sprint = new Sprint(start, end);
		sprint.addStory(story);
		project.setActiveSprint(sprint);
		
		// Start work
		story.setStatus("In Progress");
		assertEquals("In Progress", story.getStatus());
		
		// Add tasks
		story.addTask("Task 1", "Implementation");
		story.addTask("Task 2", "Testing");
		
		// Complete tasks
		story.getTask(0).setCompletionDate(new Date());
		story.getTask(1).setCompletionDate(new Date());
		
		// Mark story as done
		story.setStatus("Done");
		assertEquals("Done", story.getStatus());
		
		// Verify all tasks are complete
		boolean allTasksComplete = story.getTasks().stream()
			.allMatch(t -> t.getCompletionDate() != null);
		
		assertTrue(allTasksComplete);
	}
	
	@Test
	@DisplayName("Sprint reordering workflow")
	void testSprintStoryReordering() {
		Story s1 = new Story("Story 1", "Desc 1");
		Story s2 = new Story("Story 2", "Desc 2");
		Story s3 = new Story("Story 3", "Desc 3");
		
		Date start = new Date();
		Date end = new Date(start.getTime() + 7 * DAY);
		Sprint sprint = new Sprint(start, end);
		
		sprint.addStory(s1);
		sprint.addStory(s2);
		sprint.addStory(s3);
		
		// Reorder within sprint
		sprint.moveStory(s1, 2);
		
		List<Story> stories = sprint.getStories();
		assertEquals(s2, stories.get(0));
		assertEquals(s3, stories.get(1));
		assertEquals(s1, stories.get(2));
	}
}

