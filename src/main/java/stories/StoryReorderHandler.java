package stories;

import data.Backlog;
import data.Story;
import project.BacklogPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Handles drag-and-drop reordering of stories in the backlog.
 *
 * @author Nick Grant
 * @version 3.0
 *
 */
public class StoryReorderHandler extends TransferHandler {
	
	private final Backlog backlog;
	private final BacklogPanel panel;
	
	public StoryReorderHandler(Backlog backlog, BacklogPanel panel) {
		this.backlog = backlog;
		this.panel = panel;
	}
 
	public MouseAdapter createDragStarter(JComponent row) {
		return new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				row.getTransferHandler().exportAsDrag(row, e, MOVE);
			}
		};
	}
 
	@Override
	public int getSourceActions(JComponent c) {
		return MOVE;
	}
 
	@Override
	protected Transferable createTransferable(JComponent c) {
		StoryItemPanel row = (StoryItemPanel) c;
		return new BacklogStoryTransferable(row.getStory());
	}
 
	@Override
	public boolean canImport(TransferSupport support) {
		return support.isDataFlavorSupported(BacklogStoryTransferable.STORY_FLAVOR);
	}
 
	@Override
	public boolean importData(TransferSupport support) {
		try {
			Story dragged = (Story) support.getTransferable()
				.getTransferData(BacklogStoryTransferable.STORY_FLAVOR);
			DropLocation dl = support.getDropLocation();
			Point dropPoint = dl.getDropPoint();
			SwingUtilities.convertPointToScreen(dropPoint, support.getComponent());
			SwingUtilities.convertPointFromScreen(dropPoint, panel.getListPanel());
			int dropIndex = panel.getDropIndex(dropPoint);
			java.util.List<Story> stories = backlog.getStories();
			int originalIndex = stories.indexOf(dragged);
			if (originalIndex == -1) return false;
			if (dropIndex > originalIndex) dropIndex--;
			if (dropIndex == originalIndex) return false;
			backlog.moveStory(dragged, dropIndex);
			panel.refresh();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
 
}
