package data;

import java.util.*;

/**
 * Contains a list of UserStory objects and has a start and end date.
 * Provides methods to add, remove, check for, and move user stories within the sprint,
 * as well as to set and retrieve the sprint's dates.
 *
 * @author Finley Room
 * @author Dr.Gonzalez-Sanchez
 * @version 3.1
 */
public class Sprint {
	
	private List<Story> stories;
	private Date begin;
	private Date end;
	
	public Sprint(Date begin, Date end) {
		stories = new ArrayList<>();
		setDates(begin, end);
	}
	
	public Sprint(List<Story> stories, Date begin, Date end) {
		this.stories = new ArrayList<>();
		if (stories != null)
			this.stories.addAll(stories);
		setDates(begin, end);
	}
	
	public Sprint(String s, Date begin, Date end) {
		this(new ArrayList<>(), begin, end);
	}
	
	public boolean addStory(Story story) {
		return stories.add(Objects.requireNonNull(story, "story"));
	}
	
	public boolean removeStory(Story story) {
		return stories.remove(Objects.requireNonNull(story, "story"));
	}
	
	public boolean containsStory(Story story) {
		return stories.contains(story);
	}
	
	public int storyCount() {
		return stories.size();
	}
	
	public void moveStory(Story story, int newIndex) {
		Objects.requireNonNull(story, "story");
		int oldIndex = stories.indexOf(story);
		if (oldIndex < 0) return;
		if (newIndex < 0) newIndex = 0;
		if (newIndex >= stories.size()) newIndex = stories.size() - 1;
		if (oldIndex == newIndex) return;
		stories.remove(oldIndex);
		stories.add(newIndex, story);
	}
	
	public Date getBegin() {
		return begin;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public void setDates(Date begin, Date end) {
		if (begin == null || end == null || this.begin == null || this.end == null )
			return;
		if (this.begin.after(this.end)) {
			throw new IllegalArgumentException("Sprint begin date must be <= end date");
		}
		this.begin = begin;
		this.end = end;
	}
	
	public List<Story> getStories() {
		return Collections.unmodifiableList(stories);
	}
	
}