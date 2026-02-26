package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Sprint class.
 * These tests verify sprint creation, date management, and story management within sprints.
 *
 * @version 3.0
 */
@DisplayName("Sprint Unit Tests")
class SprintUnitTest {
	
	private Sprint sprint;
	private Date beginDate;
	private Date endDate;
	private Story story1;
	private Story story2;
	
	@BeforeEach
	void setUp() {
		long DAY = 86400000L;
		beginDate = new Date(System.currentTimeMillis());
		endDate = new Date(beginDate.getTime() + 14 * DAY);
		sprint = new Sprint(beginDate, endDate);
		
		story1 = new Story("Story 1", "Description 1");
		story2 = new Story("Story 2", "Description 2");
	}
	
	@Test
	@DisplayName("Sprint should be created with dates")
	void testSprintCreation() {
		assertNotNull(sprint);
		assertEquals(beginDate, sprint.getBegin());
		assertEquals(endDate, sprint.getEnd());
		assertEquals(0, sprint.storyCount());
	}
	
	@Test
	@DisplayName("Sprint should be created with stories and dates")
	void testSprintCreationWithStories() {
		var stories = java.util.List.of(story1, story2);
		Sprint sprintWithStories = new Sprint(stories, beginDate, endDate);
		
		assertEquals(2, sprintWithStories.storyCount());
		assertTrue(sprintWithStories.containsStory(story1));
		assertTrue(sprintWithStories.containsStory(story2));
	}
	
	@Test
	@DisplayName("Sprint should be created with string parameter")
	void testSprintCreationWithString() {
		Sprint namedSprint = new Sprint("Sprint 1", beginDate, endDate);
		assertNotNull(namedSprint);
		assertEquals(0, namedSprint.storyCount());
	}
	
	@Test
	@DisplayName("Sprint should add story")
	void testAddStory() {
		assertTrue(sprint.addStory(story1));
		assertEquals(1, sprint.storyCount());
		assertTrue(sprint.containsStory(story1));
	}
	
	@Test
	@DisplayName("Sprint should add multiple stories")
	void testAddMultipleStories() {
		sprint.addStory(story1);
		sprint.addStory(story2);
		
		assertEquals(2, sprint.storyCount());
		assertTrue(sprint.containsStory(story1));
		assertTrue(sprint.containsStory(story2));
	}
	
	@Test
	@DisplayName("Sprint should throw exception when adding null story")
	void testAddNullStory() {
		assertThrows(NullPointerException.class, () -> sprint.addStory(null));
	}
	
	@Test
	@DisplayName("Sprint should remove story")
	void testRemoveStory() {
		sprint.addStory(story1);
		sprint.addStory(story2);
		
		assertTrue(sprint.removeStory(story1));
		assertEquals(1, sprint.storyCount());
		assertFalse(sprint.containsStory(story1));
		assertTrue(sprint.containsStory(story2));
	}
	
	@Test
	@DisplayName("Sprint should return false when removing non-existent story")
	void testRemoveNonExistentStory() {
		sprint.addStory(story1);
		assertFalse(sprint.removeStory(story2));
		assertEquals(1, sprint.storyCount());
	}
	
	@Test
	@DisplayName("Sprint should throw exception when removing null story")
	void testRemoveNullStory() {
		assertThrows(NullPointerException.class, () -> sprint.removeStory(null));
	}
	
	@Test
	@DisplayName("Sprint should check if it contains story")
	void testContainsStory() {
		assertFalse(sprint.containsStory(story1));
		
		sprint.addStory(story1);
		assertTrue(sprint.containsStory(story1));
		assertFalse(sprint.containsStory(story2));
	}
	
	@Test
	@DisplayName("Sprint should return correct story count")
	void testStoryCount() {
		assertEquals(0, sprint.storyCount());
		
		sprint.addStory(story1);
		assertEquals(1, sprint.storyCount());
		
		sprint.addStory(story2);
		assertEquals(2, sprint.storyCount());
		
		sprint.removeStory(story1);
		assertEquals(1, sprint.storyCount());
	}
	
	@Test
	@DisplayName("Sprint should move story within sprint")
	void testMoveStory() {
		Story story3 = new Story("Story 3", "Description 3");
		sprint.addStory(story1);
		sprint.addStory(story2);
		sprint.addStory(story3);
		
		sprint.moveStory(story1, 2);
		
		var stories = sprint.getStories();
		assertEquals(story2, stories.get(0));
		assertEquals(story3, stories.get(1));
		assertEquals(story1, stories.get(2));
	}
	
	@Test
	@DisplayName("Sprint should handle moving story to same position")
	void testMoveSamePosition() {
		sprint.addStory(story1);
		sprint.addStory(story2);
		
		sprint.moveStory(story1, 0);
		
		var stories = sprint.getStories();
		assertEquals(story1, stories.get(0));
		assertEquals(story2, stories.get(1));
	}
	
	@Test
	@DisplayName("Sprint should throw exception when moving null story")
	void testMoveNullStory() {
		sprint.addStory(story1);
		assertThrows(NullPointerException.class, () -> sprint.moveStory(null, 0));
	}
	
	@Test
	@DisplayName("Sprint should handle moving non-existent story")
	void testMoveNonExistentStory() {
		sprint.addStory(story1);
		sprint.moveStory(story2, 0);
		assertEquals(1, sprint.storyCount());
	}
	
	@Test
	@DisplayName("Sprint should clamp move index to valid range")
	void testMoveIndexBounds() {
		Story story3 = new Story("Story 3", "Description 3");
		sprint.addStory(story1);
		sprint.addStory(story2);
		sprint.addStory(story3);
		
		// Move to index beyond size
		sprint.moveStory(story1, 100);
		var stories = sprint.getStories();
		assertEquals(story1, stories.get(2));
		
		// Move to the negative index
		sprint.moveStory(story1, -5);
		stories = sprint.getStories();
		assertEquals(story1, stories.getFirst());
	}
	
	@Test
	@DisplayName("Sprint should return unmodifiable story list")
	void testGetStoriesUnmodifiable() {
		sprint.addStory(story1);
		var stories = sprint.getStories();
		
		assertThrows(UnsupportedOperationException.class, () -> stories.add(story2));
	}
	
	@Test
	@DisplayName("Sprint should get begin date")
	void testGetBegin() {
		assertEquals(beginDate, sprint.getBegin());
	}
	
	@Test
	@DisplayName("Sprint should get end date")
	void testGetEnd() {
		assertEquals(endDate, sprint.getEnd());
	}
	
	@Test
	@DisplayName("Sprint should handle null stories list in constructor")
	void testNullStoriesInConstructor() {
		Sprint sprintWithNull = new Sprint((List<Story>) null, beginDate, endDate);
		assertNotNull(sprintWithNull);
		assertEquals(0, sprintWithNull.storyCount());
	}
}

