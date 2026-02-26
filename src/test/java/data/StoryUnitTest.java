package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Story class.
 * These tests verify story creation, property management, and task management.
 *
 * @version 3.0
 */
@DisplayName("Story Unit Tests")
class StoryUnitTest {
	
	private Story story;
	
	@BeforeEach
	void setUp() {
		story = new Story("Login Feature", "User should be able to login");
	}
	
	@Test
	@DisplayName("Story should be created with title and description")
	void testStoryCreation() {
		assertNotNull(story);
		assertEquals("Login Feature", story.getTitle());
		assertEquals("User should be able to login", story.getDescription());
		assertEquals(0, story.getPoints());
		assertEquals("New", story.getStatus());
		assertEquals(0, story.getPriority());
	}
	
	@Test
	@DisplayName("Story should be created with all parameters")
	void testStoryCreationWithAllParams() {
		Story fullStory = new Story("Full Story", "Description", 8, "In Progress", 2);
		assertEquals("Full Story", fullStory.getTitle());
		assertEquals("Description", fullStory.getDescription());
		assertEquals(8, fullStory.getPoints());
		assertEquals("In Progress", fullStory.getStatus());
		assertEquals(2, fullStory.getPriority());
	}
	
	@Test
	@DisplayName("Story should have unique ID")
	void testUniqueId() {
		Story story1 = new Story("Story 1", "Description 1");
		Story story2 = new Story("Story 2", "Description 2");
		assertNotEquals(story1.getId(), story2.getId());
	}
	
	@Test
	@DisplayName("Story should allow title modification")
	void testSetTitle() {
		story.setTitle("Updated Title");
		assertEquals("Updated Title", story.getTitle());
	}
	
	@Test
	@DisplayName("Story should throw exception for null title")
	void testSetNullTitle() {
		assertThrows(NullPointerException.class, () -> story.setTitle(null));
	}
	
	@Test
	@DisplayName("Story should allow description modification")
	void testSetDescription() {
		story.setDescription("Updated Description");
		assertEquals("Updated Description", story.getDescription());
	}
	
	@Test
	@DisplayName("Story should throw exception for null description")
	void testSetNullDescription() {
		assertThrows(NullPointerException.class, () -> story.setDescription(null));
	}
	
	@Test
	@DisplayName("Story should allow points modification")
	void testSetPoints() {
		story.setPoints(5);
		assertEquals(5, story.getPoints());
	}
	
	@Test
	@DisplayName("Story should allow status modification")
	void testSetStatus() {
		story.setStatus("In Progress");
		assertEquals("In Progress", story.getStatus());
	}
	
	@Test
	@DisplayName("Story should throw exception for null status")
	void testSetNullStatus() {
		assertThrows(NullPointerException.class, () -> story.setStatus(null));
	}
	
	@Test
	@DisplayName("Story should allow priority modification")
	void testSetPriority() {
		story.setPriority(3);
		assertEquals(3, story.getPriority());
	}
	
	@Test
	@DisplayName("Story should start with empty task list")
	void testEmptyTaskList() {
		assertNotNull(story.getTasks());
		assertEquals(0, story.getTasks().size());
	}
	
	@Test
	@DisplayName("Story should allow adding tasks")
	void testAddTask() {
		Task task = new Task("Test task", "Test body");
		story.addTask(task);
		assertEquals(1, story.getTasks().size());
		assertEquals(task, story.getTask(0));
	}
	
	@Test
	@DisplayName("Story should allow adding tasks with subject and body")
	void testAddTaskWithStrings() {
		story.addTask("Subject", "Body");
		assertEquals(1, story.getTasks().size());
		assertEquals("Subject", story.getTask(0).getSubject());
		assertEquals("Body", story.getTask(0).getBody());
	}
	
	@Test
	@DisplayName("Story should handle null task gracefully")
	void testAddNullTask() {
		story.addTask(null);
		assertEquals(0, story.getTasks().size());
	}
	
	@Test
	@DisplayName("Story should get task by index")
	void testGetTask() {
		Task task1 = new Task("Task 1");
		Task task2 = new Task("Task 2");
		story.addTask(task1);
		story.addTask(task2);
		
		assertEquals(task1, story.getTask(0));
		assertEquals(task2, story.getTask(1));
	}
	
	@Test
	@DisplayName("Story should return null for invalid task index")
	void testGetTaskInvalidIndex() {
		assertNull(story.getTask(-1));
		assertNull(story.getTask(0));
		assertNull(story.getTask(100));
	}
	
	@Test
	@DisplayName("Story should return defensive copy of tasks")
	void testGetTasksDefensiveCopy() {
		Task task = new Task("Task 1");
		story.addTask(task);
		
		var tasks = story.getTasks();
		tasks.clear();
		
		assertEquals(1, story.getTasks().size());
	}
	
	@Test
	@DisplayName("Story toString should include all properties")
	void testToString() {
		story.setPoints(5);
		story.setPriority(2);
		String result = story.toString();
		
		assertTrue(result.contains("Story{"));
		assertTrue(result.contains("title='Login Feature'"));
		assertTrue(result.contains("description='User should be able to login'"));
		assertTrue(result.contains("points=5"));
		assertTrue(result.contains("status='New'"));
		assertTrue(result.contains("priority=2"));
	}
	
	@Test
	@DisplayName("Story constructor should reject null title")
	void testConstructorNullTitle() {
		assertThrows(NullPointerException.class,
			() -> new Story(null, "Description"));
	}
	
	@Test
	@DisplayName("Story constructor should reject null description")
	void testConstructorNullDescription() {
		assertThrows(NullPointerException.class,
			() -> new Story("Title", null));
	}
	
	@Test
	@DisplayName("Story constructor should reject null status")
	void testConstructorNullStatus() {
		assertThrows(NullPointerException.class,
			() -> new Story("Title", "Description", 5, null, 2));
	}
}

