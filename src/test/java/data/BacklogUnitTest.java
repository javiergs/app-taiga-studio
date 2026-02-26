package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Backlog class.
 * These tests verify backlog management including adding, removing, and reordering stories.
 *
 * @version 3.0
 */
@DisplayName("Backlog Unit Tests")
class BacklogUnitTest {
	
	private Backlog backlog;
	private Story story1;
	private Story story2;
	private Story story3;
	
	@BeforeEach
	void setUp() {
		backlog = new Backlog();
		story1 = new Story("Story 1", "Description 1");
		story2 = new Story("Story 2", "Description 2");
		story3 = new Story("Story 3", "Description 3");
	}
	
	@Test
	@DisplayName("Backlog should start empty")
	void testEmptyBacklog() {
		assertEquals(0, backlog.size());
		assertTrue(backlog.getStories().isEmpty());
	}
	
	@Test
	@DisplayName("Backlog should add story")
	void testAddStory() {
		backlog.addStory(story1);
		assertEquals(1, backlog.size());
		assertEquals(story1, backlog.get(0));
	}
	
	@Test
	@DisplayName("Backlog should add multiple stories")
	void testAddMultipleStories() {
		backlog.addStory(story1);
		backlog.addStory(story2);
		backlog.addStory(story3);
		
		assertEquals(3, backlog.size());
		assertEquals(story1, backlog.get(0));
		assertEquals(story2, backlog.get(1));
		assertEquals(story3, backlog.get(2));
	}
	
	@Test
	@DisplayName("Backlog should handle null story gracefully")
	void testAddNullStory() {
		backlog.addStory(story1);
		backlog.addStory(null);
		assertEquals(1, backlog.size());
	}
	
	@Test
	@DisplayName("Backlog should delete story")
	void testDeleteStory() {
		backlog.addStory(story1);
		backlog.addStory(story2);
		
		assertTrue(backlog.deleteStory(story1));
		assertEquals(1, backlog.size());
		assertEquals(story2, backlog.get(0));
	}
	
	@Test
	@DisplayName("Backlog should return false when deleting non-existent story")
	void testDeleteNonExistentStory() {
		backlog.addStory(story1);
		assertFalse(backlog.deleteStory(story2));
		assertEquals(1, backlog.size());
	}
	
	@Test
	@DisplayName("Backlog should get story by index")
	void testGetStoryByIndex() {
		backlog.addStory(story1);
		backlog.addStory(story2);
		
		assertEquals(story1, backlog.get(0));
		assertEquals(story2, backlog.get(1));
	}
	
	@Test
	@DisplayName("Backlog should return defensive copy of stories")
	void testGetStoriesDefensiveCopy() {
		backlog.addStory(story1);
		backlog.addStory(story2);
		
		var stories = backlog.getStories();
		stories.clear();
		
		assertEquals(2, backlog.size());
	}
	
	@Test
	@DisplayName("Backlog should move story to new position")
	void testMoveStoryDown() {
		backlog.addStory(story1);
		backlog.addStory(story2);
		backlog.addStory(story3);
		
		backlog.moveStory(story1, 2);
		
		assertEquals(story2, backlog.get(0));
		assertEquals(story3, backlog.get(1));
		assertEquals(story1, backlog.get(2));
	}
	
	@Test
	@DisplayName("Backlog should move story up")
	void testMoveStoryUp() {
		backlog.addStory(story1);
		backlog.addStory(story2);
		backlog.addStory(story3);
		
		backlog.moveStory(story3, 0);
		
		assertEquals(story3, backlog.get(0));
		assertEquals(story1, backlog.get(1));
		assertEquals(story2, backlog.get(2));
	}
	
	@Test
	@DisplayName("Backlog should handle null story in move")
	void testMoveNullStory() {
		backlog.addStory(story1);
		backlog.addStory(story2);
		
		backlog.moveStory(null, 1);
		
		assertEquals(story1, backlog.get(0));
		assertEquals(story2, backlog.get(1));
	}
	
	@Test
	@DisplayName("Backlog should handle non-existent story in move")
	void testMoveNonExistentStory() {
		backlog.addStory(story1);
		backlog.addStory(story2);
		
		backlog.moveStory(story3, 0);
		
		assertEquals(2, backlog.size());
		assertEquals(story1, backlog.get(0));
	}
	
	@Test
	@DisplayName("Backlog should handle moving story to same position")
	void testMoveSamePosition() {
		backlog.addStory(story1);
		backlog.addStory(story2);
		
		backlog.moveStory(story1, 0);
		
		assertEquals(story1, backlog.get(0));
		assertEquals(story2, backlog.get(1));
	}
	
	@Test
	@DisplayName("Backlog should clamp move index to valid range")
	void testMoveIndexBounds() {
		backlog.addStory(story1);
		backlog.addStory(story2);
		backlog.addStory(story3);
		
		// Move to index beyond size
		backlog.moveStory(story1, 100);
		assertEquals(story1, backlog.get(2));
		
		// Move to negative index
		backlog.moveStory(story1, -5);
		assertEquals(story1, backlog.get(0));
	}
	
	@Test
	@DisplayName("Backlog size should reflect story count")
	void testSize() {
		assertEquals(0, backlog.size());
		
		backlog.addStory(story1);
		assertEquals(1, backlog.size());
		
		backlog.addStory(story2);
		assertEquals(2, backlog.size());
		
		backlog.deleteStory(story1);
		assertEquals(1, backlog.size());
	}
}

