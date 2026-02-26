package project;

import data.Blackboard;
import data.Project;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * UI for creating and editing projects.
 * It allows users to input a project title and description.
 *
 * @author Owen Sam
 * @author Owen Ledrick
 * @version 3.2
 */
public class ProjectPanel extends JPanel implements PropertyChangeListener {
	
	private final ProjectListener listener = new ProjectListener();
	private final JTextField titleField = new JTextField(20);
	private final JTextArea descriptionArea = new JTextArea(5, 20);
	private final JButton closeButton = new JButton("Close");
	private final JButton saveButton  = new JButton("Save");
	
	private Project project;
	
	public ProjectPanel() {
		this.project = resolveProject();
		buildUI();
		wireActions();
		loadProjectIntoFields(project);
	}
	
	private Project resolveProject() {
		Project active = Blackboard.getInstance().getActiveProject();
		return (active != null) ? active : new Project();
	}
	
	private void buildUI() {
		setLayout(new BorderLayout());
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		center.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		center.add(labeledField("Project Name", titleField));
		center.add(Box.createVerticalStrut(12));
		center.add(labeledArea("Description", descriptionArea));
		add(center, BorderLayout.CENTER);
		add(buildButtonBar(), BorderLayout.SOUTH);
		titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
	}
	
	private JPanel buildButtonBar() {
		JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bar.add(closeButton);
		bar.add(saveButton);
		return bar;
	}
	
	private JPanel labeledField(String label, JComponent field) {
		JLabel l = new JLabel(label);
		l.setAlignmentX(Component.LEFT_ALIGNMENT);
		field.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(l);
		p.add(Box.createVerticalStrut(6));
		p.add(field);
		return p;
	}
	
	private JPanel labeledArea(String label, JTextArea area) {
		JLabel l = new JLabel(label);
		l.setAlignmentX(Component.LEFT_ALIGNMENT);
		JScrollPane scroll = new JScrollPane(area);
		scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
		scroll.setPreferredSize(new Dimension(0, 120));
		scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(l);
		p.add(Box.createVerticalStrut(6));
		p.add(scroll);
		return p;
	}
	
	private void wireActions() {
		closeButton.addActionListener(e -> listener.close());
		saveButton.addActionListener(e -> listener.save(
			project, // always non-null in this panel
			titleField.getText().trim(),
			descriptionArea.getText().trim()
		));
	}
	
	private void loadProjectIntoFields(Project p) {
		titleField.setText(safe(p.getTitle()));
		descriptionArea.setText(safe(p.getDescription()));
		saveButton.setEnabled(true);
	}
	
	private void clearFields() {
		titleField.setText("");
		descriptionArea.setText("");
		saveButton.setEnabled(true);
	}
	
	private String safe(String s) {
		return (s == null) ? "" : s;
	}
	
	private void onActiveProjectChanged(Project active) {
		this.project = (active != null) ? active : new Project();
		if (active == null) clearFields();
		else loadProjectIntoFields(active);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		onActiveProjectChanged((Project) evt.getNewValue());
		revalidate();
		repaint();
	}

}