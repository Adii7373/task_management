package com.thrones;

 

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
 import jakarta.servlet.http.HttpSession;

@Controller
public class Maincontroller {

	
	
	@GetMapping("/")
	public String root()
	{
		return "index";
	}
	
	
	
	@GetMapping("/login")
	public String Showlogin()
	{
		return "login";
	}
	
	
	@GetMapping("/register")
	public String Showregister()
	{
		return "register";
	}
	
	
	@PostMapping("/login")
	public String dologin(@RequestParam("uname")String uname , @RequestParam("password") String password, HttpSession httpsession)
	{
		Session session1 = Hibernateutil.getSessionFactory().openSession();
		User user = (User) session1.createQuery("FROM User WHERE uname=:uname AND password=:password").setParameter("uname", uname).setParameter("password", password).uniqueResult();
		session1.close();
		
	
		if(user!=null)
		{
			httpsession.setAttribute("user", user);
			return "redirect:/welcome";
			
		}
		
		else
		{
		
		
		return "redirect:/login?error=true";
		}
	}
	
	
	@PostMapping("/register")
	public String doregister(@RequestParam("uname") String uname, @RequestParam("password") String password)
	{
		User user = new User();
		Session session = Hibernateutil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		user.setUname(uname);
		user.setPassword(password);
		
		session.persist(user);
		tx.commit();
		session.close();
		
		
		return "redirect:/login";
	}
	
	
	@GetMapping("/welcome")
	public String Welcome()
	{
		return "welcome";
	}
	
	@GetMapping("/task")
	public String Task()
	{
		return "task";
	}
	
	@PostMapping("/task")
	public String dotask(@RequestParam("taskname") String taskname, @RequestParam("taskdescription") String taskdescription
			, @RequestParam("taskstatus") String taskstatus,
			HttpSession httpsession)
	{
		
		

		
		Task tasks = new Task();
		Session session = Hibernateutil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		tasks.setTaskname(taskname);
		tasks.setTaskdescription(taskdescription);
		tasks.setTaskstatus(taskstatus);
	 
//		So when you call user1.addTask(task):
//
//			The task is put into the user’s list → user1.getTasks() knows about the task.
//
//			The task’s user is set → task.getUser() knows about the user.
		
//	user1.addTask();
	
		
		// get logged user id from session
		User user = (User) httpsession.getAttribute("user");
		
		User user1 = session.find(User.class,user.getId());
		user1.addTask(tasks);
		
		tx.commit();
		session.close();
		
		
		return "task";
		
	}
	
	
	@PostMapping("/delete")
	public String Deleteuser(HttpSession httpsession)
	{
		Session session = Hibernateutil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		User users =(User) httpsession.getAttribute("user");
		
		
		if(users!=null)
		{
			User manageduser = session.find(User.class, users.getId());
			
			if(manageduser!=null)
			{
				session.remove(manageduser);
				httpsession.invalidate();
			}
		}
		
		tx.commit();
		
		
		return "redirect:/register";
	}
	
	
	
	
	
	
		@GetMapping("/update")
		public String updateform()
		{
			return "update";
		}
		
		
		@PostMapping("/update")
		public String doupdate(@RequestParam("id") int id  ,@RequestParam("taskname")String taskname,
				@RequestParam("taskdescription") String taskdescription , @RequestParam("taskstatus") String taskstatus)
		{
			
			Session session = Hibernateutil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			
			Task tasks = session.find(Task.class, id);
			
			if(tasks!=null)
			{
				if(taskname!=null && !taskname.trim().isEmpty())
				{
					tasks.setTaskname(taskname);
				}
				
				if(taskdescription!=null && !taskdescription.trim().isEmpty())
				{
					tasks.setTaskdescription(taskdescription);
				}
				
				if(taskstatus!=null && !taskstatus.trim().isEmpty())
				{
					tasks.setTaskstatus(taskstatus);
				}
			
				session.merge(tasks);
				
			}
			
			tx.commit();
			session.close();
			
			
			
			return "update";
		}
		
		
		 
	 

		
	
		
		
		
	
}
