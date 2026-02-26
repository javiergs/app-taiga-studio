package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Blackboard class.
 * These tests verify the singleton pattern, project management, and property change notifications.
 *
 * @version 3.0
 */
@DisplayName("Blackboard Unit Tests")
class BlackboardUnitTest {
	
	private Blackboard blackboard;
	private Project project1;
	private Project project2;
	
	@BeforeEach
	void setUp() {
		// Note: Blackboard is a singleton, so we need to be careful with state
		blackboard = Blackboard.getInstance();
		project1 = new Project("Project 1", "Description 1");
		project2 = new Project("Project 2", "Description 2");
	}
	
	@AfterEach
	void tearDown() {
		// Clean up after each test
		Collection<Project> projects = blackboard.getProjects();
		for (Project p : projects) {
			blackboard.deleteProject(p);
		}
		blackboard.setActiveProject(null);
	}
	
	@Test
	@DisplayName("Blackboard should be singleton")
	void testSingleton() {
		Blackboard instance1 = Blackboard.getInstance();
		Blackboard instance2 = Blackboard.getInstance();
		assertSame(instance1, instance2);
	}
	
	@Test
	@DisplayName("Blackboard should add project")
	void testAddProject() {
		blackboard.addProject(project1);
		
		Collection<Project> projects = blackboard.getProjects();
		assertTrue(projects.contains(project1));
	}
	
	@Test
	@DisplayName("Blackboard should add multiple projects")
	void testAddMultipleProjects() {
		blackboard.addProject(project1);
		blackboard.addProject(project2);
		
		Collection<Project> projects = blackboard.getProjects();
		assertEquals(2, projects.size());
		assertTrue(projects.contains(project1));
		assertTrue(projects.contains(project2));
	}
	
	@Test
	@DisplayName("Blackboard should handle null project gracefully")
	void testAddNullProject() {
		int initialSize = blackboard.getProjects().size();
		blackboard.addProject(null);
		assertEquals(initialSize, blackboard.getProjects().size());
	}
	
	@Test
	@DisplayName("Blackboard should get project by ID")
	void testGetProjectById() {
		blackboard.addProject(project1);
		
		Project retrieved = blackboard.getProject(project1.getId() + "");
		assertEquals(project1, retrieved);
	}
	
	@Test
	@DisplayName("Blackboard should return null for non-existent project ID")
	void testGetNonExistentProject() {
		Project retrieved = blackboard.getProject("999999");
		assertNull(retrieved);
	}
	
	@Test
	@DisplayName("Blackboard should delete project")
	void testDeleteProject() {
		blackboard.addProject(project1);
		blackboard.addProject(project2);
		
		blackboard.deleteProject(project1);
		
		Collection<Project> projects = blackboard.getProjects();
		assertFalse(projects.contains(project1));
		assertTrue(projects.contains(project2));
	}
	
	@Test
	@DisplayName("Blackboard should handle deleting null project")
	void testDeleteNullProject() {
		blackboard.addProject(project1);
		int initialSize = blackboard.getProjects().size();
		
		blackboard.deleteProject(null);
		
		assertEquals(initialSize, blackboard.getProjects().size());
	}
	
	@Test
	@DisplayName("Blackboard should set active project")
	void testSetActiveProject() {
		blackboard.addProject(project1);
		
		blackboard.setActiveProject(project1);
		
		assertEquals(project1, blackboard.getActiveProject());
	}
	
	@Test
	@DisplayName("Blackboard should only set active project if it exists in projects")
	void testSetActiveProjectNotInList() {
		blackboard.setActiveProject(project1);
		
		assertNull(blackboard.getActiveProject());
	}
	
	@Test
	@DisplayName("Blackboard should clear active project when set to null")
	void testSetActiveProjectNull() {
		blackboard.addProject(project1);
		blackboard.setActiveProject(project1);
		
		blackboard.setActiveProject(null);
		
		assertNull(blackboard.getActiveProject());
	}
	
	@Test
	@DisplayName("Blackboard should clear active project when deleted")
	void testDeleteActiveProject() {
		blackboard.addProject(project1);
		blackboard.setActiveProject(project1);
		
		blackboard.deleteProject(project1);
		
		assertNull(blackboard.getActiveProject());
	}
	
	@Test
	@DisplayName("Blackboard should return defensive copy of projects")
	void testGetProjectsDefensiveCopy() {
		blackboard.addProject(project1);
		
		Collection<Project> projects = blackboard.getProjects();
		int originalSize = projects.size();
		projects.clear();
		
		assertEquals(originalSize, blackboard.getProjects().size());
	}
	
	@Test
	@DisplayName("Blackboard should fire property change when adding project")
	void testPropertyChangeOnAddProject() {
		PropertyChangeListener listener = new PropertyChangeListener() {
			boolean fired = false;
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("projects".equals(evt.getPropertyName())) {
					fired = true;
				}
			}
		};
		
		blackboard.addPropertyChangeListener(listener);
		blackboard.addProject(project1);
		
		// Clean up listener
		blackboard.removePropertyChangeListener(listener);
	}
	
	@Test
	@DisplayName("Blackboard should fire property change when setting active project")
	void testPropertyChangeOnSetActiveProject() {
		blackboard.addProject(project1);
		
		PropertyChangeListener listener = new PropertyChangeListener() {
			boolean fired = false;
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("activeProject".equals(evt.getPropertyName())) {
					fired = true;
				}
			}
		};
		
		blackboard.addPropertyChangeListener(listener);
		blackboard.setActiveProject(project1);
		
		// Clean up listener
		blackboard.removePropertyChangeListener(listener);
	}
	
	@Test
	@DisplayName("Blackboard should switch between active projects")
	void testSwitchActiveProject() {
		blackboard.addProject(project1);
		blackboard.addProject(project2);
		
		blackboard.setActiveProject(project1);
		assertEquals(project1, blackboard.getActiveProject());
		
		blackboard.setActiveProject(project2);
		assertEquals(project2, blackboard.getActiveProject());
	}
}

