package stories;

import data.Story;
import project.BacklogPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for editing a story's details.
 *
 * @author Anonymous Student
 * @version 3.0
 */
public class EditStoryPanel extends JPanel {
	
	public EditStoryPanel(Story story, BacklogPanel backlogPanel) {
		setLayout(new BorderLayout());
		JPanel form = new JPanel();
		form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
		JTextField titleField = new JTextField(story.getTitle());
		titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		form.add(new JLabel("Subject:"));
		form.add(titleField);
		JTextArea descriptionArea = new JTextArea(story.getDescription());
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		JScrollPane descScroll = new JScrollPane(descriptionArea);
		descScroll.setPreferredSize(new Dimension(400, 200));
		form.add(new JLabel("Description:"));
		form.add(descScroll);
		SpinnerNumberModel pointsModel = new SpinnerNumberModel(story.getPoints(), 0, 100, 1);
		JSpinner pointsSpinner = new JSpinner(pointsModel);
		form.add(new JLabel("Points:"));
		form.add(pointsSpinner);
		SpinnerNumberModel priorityModel = new SpinnerNumberModel(story.getPriority(), 0, 10, 1);
		JSpinner prioritySpinner = new JSpinner(priorityModel);
		form.add(new JLabel("Priority:"));
		form.add(prioritySpinner);
		JComboBox<String> statusBox = new JComboBox<>(new String[]{"New", "Ready", "In Progress", "Ready to Test", "Done", "Archived"});
		statusBox.setSelectedItem(story.getStatus());
		form.add(new JLabel("Status:"));
		form.add(statusBox);
		add(form, BorderLayout.CENTER);
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton save = new JButton("Save");
		JButton cancel = new JButton("Cancel");
		save.addActionListener(e -> {
			story.setTitle(titleField.getText());
			story.setDescription(descriptionArea.getText());
			story.setPoints((Integer) pointsSpinner.getValue());
			story.setPriority((Integer) prioritySpinner.getValue());
			story.setStatus((String) statusBox.getSelectedItem());
			backlogPanel.refresh();
		});
		
		cancel.addActionListener(e -> {
			backlogPanel.refresh();
		});
		
		buttons.add(cancel);
		buttons.add(save);
		
		add(buttons, BorderLayout.SOUTH);
	}
 
}
