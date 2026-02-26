package data;

import java.util.ArrayList;
import java.util.List;

/**
 * The Backlog class represents a collection of UserStory objects.
 * It provides methods to add, delete, retrieve, and move user stories within the backlog.
 *
 * @author Nick Grant
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 *
 */

public class Backlog {
	
	private final List<Story> stories = new ArrayList<>();
	
	public void addStory(Story story) {
		if (story != null)
			stories.add(story);
	}
	
	public boolean deleteStory(Story story) {
		return stories.remove(story);
	}
	
	public List<Story> getStories() {
		return new ArrayList<>(stories);
	}
	
	public Story get(int index) {
		return stories.get(index);
	}
	
	public int size() {
		return stories.size();
	}
	
	public void moveStory(Story story, int newIndex) {
		if (story == null) return;
		int oldIndex = stories.indexOf(story);
		if (oldIndex < 0 || oldIndex == newIndex) {
			return;
		}
		stories.remove(oldIndex);
		newIndex = Math.max(0, Math.min(newIndex, stories.size()));
		stories.add(newIndex, story);
	}
	
}