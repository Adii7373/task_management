package com.thrones;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String uname;
	private String password;
	
	// One user can have many tasks
	@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
	private List<Task> tasks = new ArrayList<>() ;
	
	
	public User()
	{
		this.tasks = new ArrayList<>();
	}
	
	
	public void addTask(Task task)
	{
		tasks.add(task); // ✅ parent → child

		task.setUser(this);  // ✅ child → parent (back reference)
	}
	
	
	public void removeTask(Task task)
	{
		tasks.remove(task);
		task.setUser(null);
	}
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", uname=" + uname + ", password=" + password + "]";
	}
	
	
	
	
	
	
	
 
	
	
	

}
