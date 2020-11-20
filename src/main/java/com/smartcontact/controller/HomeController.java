package com.smartcontact.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcontact.dao.UserRepository;
import com.smartcontact.entities.User;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
@GetMapping("/home")
public String showhome(Model model) {
//	
//	User user=new User();
	model.addAttribute("title", "SmartContact-Home");
//	user.setUserName("Rahul jadli");
//	user.setUserEmail("rahuljadli1999@gmail");
//	userRepo.save(user);
//	
	return "home";
}
@GetMapping("/about")
public String showAbout(Model model) {
//	
//	User user=new User();
//	user.setUserName("Rahul jadli");
	model.addAttribute("title", "SmartContact-About");
//	user.setUserEmail("rahuljadli1999@gmail");
//	userRepo.save(user);
//	
	return "about";
}


@GetMapping("/signup")
public String showSignUp(Model model) {
//	
	User user=new User();
//	user.setUserName("Rahul jadli");
	model.addAttribute("user", user);
	model.addAttribute("title", "SmartContact-Register");
//	user.setUserEmail("rahuljadli1999@gmail");
//	userRepo.save(user);
//	
	return "signup";
}

@GetMapping("/login")
public String showLogin(Model model) {
//	
//	User user=new User();
//	user.setUserName("Rahul jadli");
	model.addAttribute("title", "SmartContact-Login");
//	user.setUserEmail("rahuljadli1999@gmail");
//	userRepo.save(user);
//	
	return "login";
}
@PostMapping("/registerUser")
public String registerUser(@ModelAttribute("User") User user,Model model,HttpSession session) {
	
	try {
		System.out.println("User details:"+user);
		user.setUserRole("ROLE_USER");
		user.setStatus(true);
		user.setUserProfile("default.png");
		
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		
		
		User save = userRepo.save(user);
		model.addAttribute("user",save);

	} catch (Exception e) {
		model.addAttribute("user",user);
		e.printStackTrace();
		return "signup";
	}
	
	return "home";
}

@RequestMapping("/signin")
public String loginUser() {
	System.out.println("inside this");
	return "login";
}
}
