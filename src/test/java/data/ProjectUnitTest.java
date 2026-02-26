package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Project class.
 * These tests verify project creation, property management, and sprint/backlog management.
 *
 * @version 3.0
 */
@DisplayName("Project Unit Tests")
class ProjectUnitTest {
	
	private Project project;
	private Sprint sprint1;
	private Sprint sprint2;
	
	@BeforeEach
	void setUp() {
		project = new Project("Test Project", "Test Description");
		
		long DAY = 86400000L;
		Date begin1 = new Date(System.currentTimeMillis());
		Date end1 = new Date(begin1.getTime() + 14 * DAY);
		sprint1 = new Sprint(begin1, end1);
		
		Date begin2 = new Date(end1.getTime() + DAY);
		Date end2 = new Date(begin2.getTime() + 14 * DAY);
		sprint2 = new Sprint(begin2, end2);
	}
	
	@Test
	@DisplayName("Project should be created with title and description")
	void testProjectCreation() {
		assertNotNull(project);
		assertEquals("Test Project", project.getTitle());
		assertEquals("Test Description", project.getDescription());
		assertNotNull(project.getId());
		assertNotNull(project.getBacklog());
		assertNotNull(project.getSprints());
		assertEquals(0, project.getSprints().size());
	}
	
	@Test
	@DisplayName("Project should be created with default values")
	void testDefaultProjectCreation() {
		Project defaultProject = new Project();
		assertEquals("New Project", defaultProject.getTitle());
		assertEquals("Project Description", defaultProject.getDescription());
		assertNotNull(defaultProject.getBacklog());
	}
	
	@Test
	@DisplayName("Project should have unique ID")
	void testUniqueId() {
		Project project1 = new Project("Project 1", "Description 1");
		Project project2 = new Project("Project 2", "Description 2");
		assertNotEquals(project1.getId(), project2.getId());
	}
	
	@Test
	@DisplayName("Project should allow title modification")
	void testSetTitle() {
		project.setTitle("Updated Title");
		assertEquals("Updated Title", project.getTitle());
	}
	
	@Test
	@DisplayName("Project should allow description modification")
	void testSetDescription() {
		project.setDescription("Updated Description");
		assertEquals("Updated Description", project.getDescription());
	}
	
	@Test
	@DisplayName("Project should have backlog")
	void testBacklog() {
		Backlog backlog = project.getBacklog();
		assertNotNull(backlog);
		assertEquals(0, backlog.size());
		
		Story story = new Story("Story 1", "Description 1");
		backlog.addStory(story);
		assertEquals(1, project.getBacklog().size());
	}
	
	@Test
	@DisplayName("Project should allow setting backlog")
	void testSetBacklog() {
		Backlog newBacklog = new Backlog();
		Story story = new Story("Story 1", "Description 1");
		newBacklog.addStory(story);
		
		project.setBacklog(newBacklog);
		assertEquals(1, project.getBacklog().size());
	}
	
	@Test
	@DisplayName("Project should handle null backlog gracefully")
	void testSetNullBacklog() {
		Backlog originalBacklog = project.getBacklog();
		project.setBacklog(null);
		assertEquals(originalBacklog, project.getBacklog());
	}
	
	@Test
	@DisplayName("Project should add sprint")
	void testAddSprint() {
		project.addSprint(sprint1);
		assertEquals(1, project.getSprints().size());
		assertTrue(project.getSprints().contains(sprint1));
	}
	
	@Test
	@DisplayName("Project should add multiple sprints")
	void testAddMultipleSprints() {
		project.addSprint(sprint1);
		project.addSprint(sprint2);
		
		assertEquals(2, project.getSprints().size());
		assertTrue(project.getSprints().contains(sprint1));
		assertTrue(project.getSprints().contains(sprint2));
	}
	
	@Test
	@DisplayName("Project should handle null sprint gracefully")
	void testAddNullSprint() {
		project.addSprint(sprint1);
		project.addSprint(null);
		assertEquals(1, project.getSprints().size());
	}
	
	@Test
	@DisplayName("Project should remove sprint")
	void testRemoveSprint() {
		project.addSprint(sprint1);
		project.addSprint(sprint2);
		
		project.removeSprint(sprint1);
		assertEquals(1, project.getSprints().size());
		assertFalse(project.getSprints().contains(sprint1));
	}
	
	@Test
	@DisplayName("Project should clear active sprint when removed")
	void testRemoveActiveSprint() {
		project.setActiveSprint(sprint1);
		assertEquals(sprint1, project.getActiveSprint());
		
		project.removeSprint(sprint1);
		assertNull(project.getActiveSprint());
	}
	
	@Test
	@DisplayName("Project should set active sprint")
	void testSetActiveSprint() {
		assertNull(project.getActiveSprint());
		
		project.setActiveSprint(sprint1);
		assertEquals(sprint1, project.getActiveSprint());
		assertTrue(project.getSprints().contains(sprint1));
	}
	
	@Test
	@DisplayName("Project should add sprint when setting as active if not in list")
	void testSetActiveSprintNotInList() {
		assertEquals(0, project.getSprints().size());
		
		project.setActiveSprint(sprint1);
		
		assertEquals(sprint1, project.getActiveSprint());
		assertEquals(1, project.getSprints().size());
		assertTrue(project.getSprints().contains(sprint1));
	}
	
	@Test
	@DisplayName("Project should handle null active sprint gracefully")
	void testSetNullActiveSprint() {
		project.setActiveSprint(sprint1);
		project.setActiveSprint(null);
		assertEquals(sprint1, project.getActiveSprint());
	}
	
	@Test
	@DisplayName("Project should return defensive copy of sprints")
	void testGetSprintsDefensiveCopy() {
		project.addSprint(sprint1);
		
		var sprints = project.getSprints();
		sprints.clear();
		
		assertEquals(1, project.getSprints().size());
	}
	
	@Test
	@DisplayName("Project should manage sprints and active sprint together")
	void testSprintManagement() {
		// Add sprint and set as active
		project.addSprint(sprint1);
		project.setActiveSprint(sprint1);
		
		assertEquals(1, project.getSprints().size());
		assertEquals(sprint1, project.getActiveSprint());
		
		// Add second sprint
		project.addSprint(sprint2);
		assertEquals(2, project.getSprints().size());
		
		// Change active sprint
		project.setActiveSprint(sprint2);
		assertEquals(sprint2, project.getActiveSprint());
		
		// Remove non-active sprint
		project.removeSprint(sprint1);
		assertEquals(1, project.getSprints().size());
		assertEquals(sprint2, project.getActiveSprint());
	}
}

