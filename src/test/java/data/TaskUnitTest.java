package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Task class.
 * These tests verify task creation, property management, and completion tracking.
 *
 * @version 3.0
 */
@DisplayName("Task Unit Tests")
class TaskUnitTest {
	
	private Task task;
	private Developer developer;
	
	@BeforeEach
	void setUp() {
		task = new Task("Implement login", "Create login form with validation");
		developer = new Developer("John Doe");
	}
	
	@Test
	@DisplayName("Task should be created with subject and body")
	void testTaskCreation() {
		assertNotNull(task);
		assertEquals("Implement login", task.getSubject());
		assertEquals("Create login form with validation", task.getBody());
		assertNotNull(task.getId());
	}
	
	@Test
	@DisplayName("Task should be created with subject only")
	void testTaskCreationWithSubjectOnly() {
		Task simpleTask = new Task("Simple task");
		assertNotNull(simpleTask);
		assertEquals("Simple task", simpleTask.getSubject());
		assertEquals("", simpleTask.getBody());
	}
	
	@Test
	@DisplayName("Task should allow subject modification")
	void testSetSubject() {
		task.setSubject("Updated subject");
		assertEquals("Updated subject", task.getSubject());
	}
	
	@Test
	@DisplayName("Task should allow body modification")
	void testSetBody() {
		task.setBody("Updated body");
		assertEquals("Updated body", task.getBody());
	}
	
	@Test
	@DisplayName("Task should handle null subject gracefully")
	void testSetNullSubject() {
		String originalSubject = task.getSubject();
		task.setSubject(null);
		assertEquals(originalSubject, task.getSubject());
	}
	
	@Test
	@DisplayName("Task should handle null body gracefully")
	void testSetNullBody() {
		String originalBody = task.getBody();
		task.setBody(null);
		assertEquals(originalBody, task.getBody());
	}
	
	@Test
	@DisplayName("Task should track completion date")
	void testCompletionDate() {
		assertNull(task.getCompletionDate());
		
		Date now = new Date();
		task.setCompletionDate(now);
		
		assertNotNull(task.getCompletionDate());
		assertEquals(now.getTime(), task.getCompletionDate().getTime());
	}
	
	@Test
	@DisplayName("Task should allow clearing completion date")
	void testClearCompletionDate() {
		task.setCompletionDate(new Date());
		assertNotNull(task.getCompletionDate());
		
		task.setCompletionDate(null);
		assertNull(task.getCompletionDate());
	}
	
	@Test
	@DisplayName("Task should support assignee")
	void testAssignee() {
		assertNull(task.getAssignee());
		
		task.setAssignee(developer);
		assertNotNull(task.getAssignee());
		assertEquals("John Doe", task.getAssignee().getName());
	}
	
	@Test
	@DisplayName("Task should handle null assignee gracefully")
	void testNullAssignee() {
		task.setAssignee(developer);
		task.setAssignee(null);
		assertNotNull(task.getAssignee()); // Should keep previous assignee
	}
	
	@Test
	@DisplayName("Task should have unique IDs")
	void testUniqueIds() {
		Task task1 = new Task("Task 1");
		Task task2 = new Task("Task 2");
		assertTrue(task1.getId() > 0);
		assertTrue(task2.getId() > 0);
		assertNotEquals(task1.getId(), task2.getId());
	}
	
	@Test
	@DisplayName("Task toString should include all properties")
	void testToString() {
		String result = task.toString();
		assertTrue(result.contains("Task{"));
		assertTrue(result.contains("id="));
		assertTrue(result.contains("subject='Implement login'"));
		assertTrue(result.contains("body='Create login form with validation'"));
	}
}

