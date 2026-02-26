package stories;

import data.Story;

/**
 * Result of attempting to create a new story, including success status, message, and created Story if successful.
 *
 * @author Jonathan Garcia
 * @version 3.0
 */

public class NewStoryResult {
	
	private final boolean ok;
	private final String message;
	private final Story story;
	
	public NewStoryResult(boolean ok, String message, Story story) {
		this.ok = ok;
		this.message = message;
		this.story = story;
	}
	
	public boolean isOk() {
		return ok;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Story getStory() {
		return story;
	}
}
