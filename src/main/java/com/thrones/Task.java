package com.thrones;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Task {
	
	
	// Many tasks belongs to one user
	
	@ManyToOne
	@JoinColumn(name="user_id") //
	private User user;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int  id;
	private String taskname;
	private String taskdescription;
	private String taskstatus;
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getTaskdescription() {
		return taskdescription;
	}
	public void setTaskdescription(String taskdescription) {
		this.taskdescription = taskdescription;
	}
	public String getTaskstatus() {
		return taskstatus;
	}
	public void setTaskstatus(String taskstatus) {
		this.taskstatus = taskstatus;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@Override
	public String toString() {
		return "Task [user=" + user + ", id=" + id + ", taskname=" + taskname + ", taskdescription=" + taskdescription
				+ ", taskstatus=" + taskstatus + "]";
	}
	
	
	
	@PrePersist
	public void prePersist()
	{
		if(taskstatus==null || taskstatus.isBlank())
		{
			taskstatus="Pending";
		}
	}
	 
	
	
	
	
	
	
	

}
