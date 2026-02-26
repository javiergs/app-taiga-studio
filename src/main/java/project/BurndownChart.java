package project;

import data.Blackboard;
import data.Sprint;
import data.Story;
import data.Task;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * Burndown chart for the current sprint.
 * X-axis: days, Y-axis: remaining story points.
 *
 * @author Isaac-Pruett
 * @version 3.1
 */
public class BurndownChart extends JPanel {
	
	private static final int POINT_SIZE = 6;
	
	// Layout
	private static final int LEFT_PAD = 70;
	private static final int RIGHT_PAD = 20;
	private static final int TOP_PAD = 20;
	private static final int BOTTOM_PAD = 70;
	
	// If you want local time instead of UTC, set to your preferred zone:
	private static final ZoneId ZONE = ZoneId.systemDefault();
	
	public BurndownChart() {
		setOpaque(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Sprint sprint = getActiveSprintSafely();
		if (sprint == null) {
			drawEmptyState(g, "No active sprint.");
			return;
		}
		
		int days = sprintLengthDaysInclusive(sprint);
		if (days <= 0) {
			drawEmptyState(g, "Sprint dates are invalid.");
			return;
		}
		
		// Compute data once
		List<Story> stories = sprint.getStories();
		int totalPoints = stories.stream().mapToInt(Story::getPoints).sum();
		if (totalPoints <= 0) totalPoints = 1;
		
		int[] remainingByDay = computeRemainingPointsByDay(sprint, stories, totalPoints, days);
		
		// Draw
		Graphics2D g2 = (Graphics2D) g.create();
		try {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			Rectangle plot = plotArea();
			drawAxes(g2, sprint, totalPoints, days, plot);
			drawIdealLine(g2, totalPoints, days, plot);
			drawActualLine(g2, remainingByDay, totalPoints, days, plot);
		} finally {
			g2.dispose();
		}
	}
	
	private Sprint getActiveSprintSafely() {
		Blackboard bb = Blackboard.getInstance();
		if (bb.getActiveProject() == null) return null;
		return bb.getActiveProject().getActiveSprint();
	}
	
	/**
	 * Number of day "slots" to plot.
	 * If begin and end are the same date, returns 1.
	 */
	private int sprintLengthDaysInclusive(Sprint sprint) {
		LocalDate start = toLocalDate(sprint.getBegin());
		LocalDate end = toLocalDate(sprint.getEnd());
		if (start == null || end == null) return 0;
		
		long days = java.time.temporal.ChronoUnit.DAYS.between(start, end);
		// if end is after start by N days, we plot N+1 points (day 0..N)
		return (int) (days >= 0 ? days + 1 : 0);
	}
	
	private int[] computeRemainingPointsByDay(Sprint sprint, List<Story> stories, int totalPoints, int days) {
		int[] remaining = new int[days];
		// day 0 starts at total points
		for (int i = 0; i < days; i++) remaining[i] = totalPoints;
		
		LocalDate start = toLocalDate(sprint.getBegin());
		
		for (Story story : stories) {
			int storyPoints = Math.max(0, story.getPoints());
			List<Task> tasks = story.getTasks();
			int taskCount = (tasks == null) ? 0 : tasks.size();
			if (taskCount == 0 || storyPoints == 0) continue;
			
			// If you want fractional precision, switch remaining[] to double[].
			int pointsPerTask = Math.max(1, storyPoints / taskCount);
			
			for (Task task : tasks) {
				if (task == null || task.getCompletionDate() == null) continue;
				
				LocalDate completed = toLocalDate(task.getCompletionDate());
				if (completed == null || completed.isBefore(start)) continue;
				
				int dayIndex = (int) java.time.temporal.ChronoUnit.DAYS.between(start, completed);
				if (dayIndex < 0) continue;
				
				// When a task completes on dayIndex, remaining should drop starting that day (and onward).
				for (int d = Math.min(dayIndex, days - 1); d < days; d++) {
					remaining[d] = Math.max(0, remaining[d] - pointsPerTask);
				}
			}
		}
		
		return remaining;
	}
	
	private Rectangle plotArea() {
		int w = getWidth();
		int h = getHeight();
		return new Rectangle(
			LEFT_PAD,
			TOP_PAD,
			Math.max(1, w - LEFT_PAD - RIGHT_PAD),
			Math.max(1, h - TOP_PAD - BOTTOM_PAD)
		);
	}
	
	private void drawAxes(Graphics2D g2, Sprint sprint, int maxPoints, int days, Rectangle plot) {
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
		
		// Y axis
		g2.drawLine(plot.x, plot.y, plot.x, plot.y + plot.height);
		// X axis
		g2.drawLine(plot.x, plot.y + plot.height, plot.x + plot.width, plot.y + plot.height);
		
		// X labels (dates) - show fewer labels if many days
		int step = Math.max(1, days / 10); // about 10 labels max
		int fh = g2.getFontMetrics().getHeight();
		LocalDate start = toLocalDate(sprint.getBegin());
		
		for (int i = 0; i < days; i += step) {
			int x = xForDay(i, days, plot);
			g2.drawLine(x, plot.y + plot.height, x, plot.y + plot.height + 4);
			
			LocalDate d = start.plusDays(i);
			String label = d.getMonthValue() + "/" + d.getDayOfMonth();
			g2.drawString(label, x - 14, plot.y + plot.height + fh + 6);
		}
		
		// Y labels (points) - a few ticks
		int yTicks = 5;
		for (int t = 0; t <= yTicks; t++) {
			int points = (int) Math.round(maxPoints * (t / (double) yTicks));
			int y = yForPoints(points, maxPoints, plot);
			g2.drawLine(plot.x - 4, y, plot.x, y);
			g2.drawString(String.valueOf(points), plot.x - 35, y + 4);
		}
	}
	
	private void drawIdealLine(Graphics2D g2, int maxPoints, int days, Rectangle plot) {
		g2.setColor(Color.GRAY);
		
		int x0 = xForDay(0, days, plot);
		int y0 = yForPoints(maxPoints, maxPoints, plot);
		
		int x1 = xForDay(days - 1, days, plot);
		int y1 = yForPoints(0, maxPoints, plot);
		
		g2.drawLine(x0, y0, x1, y1);
		// endpoints
		drawPoint(g2, x0, y0);
		drawPoint(g2, x1, y1);
	}
	
	private void drawActualLine(Graphics2D g2, int[] remaining, int maxPoints, int days, Rectangle plot) {
		g2.setColor(new Color(0, 140, 0));
		
		for (int i = 0; i < days - 1; i++) {
			int x1 = xForDay(i, days, plot);
			int y1 = yForPoints(remaining[i], maxPoints, plot);
			
			int x2 = xForDay(i + 1, days, plot);
			int y2 = yForPoints(remaining[i + 1], maxPoints, plot);
			
			g2.drawLine(x1, y1, x2, y2);
			drawPoint(g2, x1, y1);
		}
		
		// last point
		int xl = xForDay(days - 1, days, plot);
		int yl = yForPoints(remaining[days - 1], maxPoints, plot);
		drawPoint(g2, xl, yl);
	}
	
	private void drawPoint(Graphics2D g2, int x, int y) {
		int r = POINT_SIZE / 2;
		g2.fillOval(x - r, y - r, POINT_SIZE, POINT_SIZE);
	}
	
	private int xForDay(int dayIndex, int days, Rectangle plot) {
		if (days <= 1) return plot.x;
		double t = dayIndex / (double) (days - 1);
		return plot.x + (int) Math.round(t * plot.width);
	}
	
	private int yForPoints(int points, int maxPoints, Rectangle plot) {
		double t = points / (double) maxPoints; // 1.0 at top (max points)
		return plot.y + (int) Math.round((1.0 - t) * plot.height);
	}
	
	private LocalDate toLocalDate(java.util.Date d) {
		if (d == null) return null;
		Instant inst = d.toInstant();
		return inst.atZone(ZONE).toLocalDate();
	}
	
	private void drawEmptyState(Graphics g, String msg) {
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("SansSerif", Font.PLAIN, 12));
		g.drawString(msg, LEFT_PAD, TOP_PAD + 20);
	}

}