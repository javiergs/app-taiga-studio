package tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Arrays;

/**
 * Panel that displays a list of tasks with edit buttons and a create button for new tasks.
 * Panel has drag and drop functionality that will move the tasks around.
 *
 * @author Alexander Bloomer
 * @author Collin Howard
 * @author Kevin Mokarapiromya
 * @version 3.0
 */
public class TaskTransferHandler extends TransferHandler {
	
	private final JPanel taskPanel;
	
	public TaskTransferHandler(JPanel taskPanel) {
		this.taskPanel = taskPanel;
	}
	
	@Override
	public int getSourceActions(JComponent component) {
		return MOVE;
	}
	
	@Override
	protected Transferable createTransferable(JComponent component) {
		return new StringSelection("");
	}
	
	@Override
	public boolean canImport(TransferSupport support) {
		return true;
	}
	
	@Override
	public boolean importData(TransferSupport support) {
		Component src = support.getComponent(); // the component being dropped (the entry panel)
		Point dropPoint = support.getDropLocation().getDropPoint();
		Component target = taskPanel.getComponentAt(dropPoint);
		int index = (target != null) ? Arrays.asList(taskPanel.getComponents()).indexOf(target) : -1;
		if (index != -1) {
			taskPanel.remove(src);
			taskPanel.add(src, index);
			taskPanel.revalidate();
			taskPanel.repaint();
			return true;
		}
		return false;
	}
	
}
