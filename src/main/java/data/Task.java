package data;

import java.util.Date;

/**
 * The Task class represents a task with an ID, subject, body, and completion date.
 *
 * @author Alexander Bloomer
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 */
public class Task {
	
	private static long ID_COUNTER = 0;
	
	private long id;
	private String subject;
	private String body;
	private Date completionDate;
	private Developer assignee;
	
	public Task(String subject, String body, Developer assignee) {
		ID_COUNTER++;
		id = ID_COUNTER;
		setSubject(subject);
		setBody(body);
		setAssignee(assignee);
	}
	
	public Task(String subject) {
		this(subject, "");
	}
	
	public Task(String subject, String body) {
		this(subject, body, null);
	}
	
	public Developer getAssignee() {
		return assignee;
	}
	
	public void setAssignee(Developer assignee) {
		if (assignee != null) this.assignee = assignee;
	}
	
	public long getId() {
		return id;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		if (subject != null) this.subject = subject;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		if (body != null) this.body = body;
	}
	
	public Date getCompletionDate() {
		return completionDate == null ? null : new Date(completionDate.getTime());
	}
	
	public void setCompletionDate(Date date) {
		if (date == null) this.completionDate = null;
		else this.completionDate = date;
	}
	
	@Override
	public String toString() {
		return "Task{" +
			"id='" + id + '\'' +
			", subject='" + subject + '\'' +
			", body='" + body + '\'' +
			", completionDate=" + completionDate +
			'}';
	}
	
}