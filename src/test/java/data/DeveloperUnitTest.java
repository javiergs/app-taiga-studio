package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Developer class.
 * These tests verify developer creation and name management.
 *
 * @version 3.0
 */
@DisplayName("Developer Unit Tests")
class DeveloperUnitTest {
	
	private Developer developer;
	
	@BeforeEach
	void setUp() {
		developer = new Developer("John Doe");
	}
	
	@Test
	@DisplayName("Developer should be created with name")
	void testDeveloperCreation() {
		assertNotNull(developer);
		assertEquals("John Doe", developer.getName());
	}
	
	@Test
	@DisplayName("Developer should allow name modification")
	void testSetName() {
		developer.setName("Jane Smith");
		assertEquals("Jane Smith", developer.getName());
	}
	
	@Test
	@DisplayName("Developer should reject null name")
	void testSetNullName() {
		String originalName = developer.getName();
		developer.setName(null);
		assertEquals(originalName, developer.getName());
	}
	
	@Test
	@DisplayName("Developer should reject empty name")
	void testSetEmptyName() {
		String originalName = developer.getName();
		developer.setName("");
		assertEquals(originalName, developer.getName());
	}
	
	@Test
	@DisplayName("Developer should reject whitespace-only name")
	void testSetWhitespaceName() {
		String originalName = developer.getName();
		developer.setName("   ");
		assertEquals(originalName, developer.getName());
	}
	
	@Test
	@DisplayName("Developer should accept name with leading/trailing spaces")
	void testSetNameWithSpaces() {
		developer.setName("  Bob Jones  ");
		assertEquals("  Bob Jones  ", developer.getName());
	}
}

