package com.smartcontact.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import com.smartcontact.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontact.dao.ContactRepository;
import com.smartcontact.dao.UserRepository;
import com.smartcontact.entities.Contact;
import com.smartcontact.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userrepo;
	@Autowired
	private ContactRepository contactRepo;
	@Autowired
	private BCryptPasswordEncoder bCryptEncoder;
	
	
	@ModelAttribute
	public void commonTask(Principal principal,Model model) {
		User user=userrepo.findByUserEmail(principal.getName());
		model.addAttribute("user", user);
		System.out.println("Details"+user);
	}
	
	@GetMapping("/dashboard")
	public String showDashboard(Principal principal,Model model) {
		model.addAttribute("title","User-Dashboard");
		
		return "/normal/user_dashboard";
	}
	@GetMapping("/add_contact")
	public String addContact(Principal principal,Model model) {
		model.addAttribute("title","User-Add Contact");
		model.addAttribute("contact",new Contact());
		
		return "normal/add_contact";
	}
	
	@PostMapping("/addcontact")
	public String addContactDetails(@ModelAttribute Contact contact,@RequestParam("profilePic") MultipartFile file,Principal principal,HttpSession session) {
		try {
			
			String email=principal.getName();
			User user=this.userrepo.findByUserEmail(email);
			
			
			if(file.isEmpty()) {
				contact.setContactProfile("contact.png");
				
			}
			else {
			
				String filename=file.getOriginalFilename();
				contact.setContactProfile(filename);
				
				contact.setUser(user);
				user.getContacts().add(contact);
			
				this.userrepo.save(user);
				
				System.out.println("addded");
				
				
				File save = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(save.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
				System.out.println("File Uploadeed");
				Message message=new Message("Contact has  been successfully added","alert alert-success");
				session.setAttribute("message", message);
			}
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
			Message message=new Message("Contact has not been added","alert alert-danger");
			session.setAttribute("message", message);
		}
				return "normal/add_contact";
		
	}
	
	
@GetMapping("/showcontact/{page}")
public String showContacts(@PathVariable("page") Integer page,Principal principal,Model model) {
	String userEmail=principal.getName();
	User user=this.userrepo.findByUserEmail(userEmail);

	Pageable pageable  = PageRequest.of(page, 5);
	
	Page<Contact> contacts =this.contactRepo.findContactByUser(user.getId(),pageable);
	model.addAttribute("contacts", contacts);
	model.addAttribute("currentPage", page);
	model.addAttribute("totalPage",contacts.getTotalPages());
	
	System.out.println(contacts);
	return "normal/show_contact";
}

@GetMapping("/contactdetail/{cId}")
public String showContact(@PathVariable("cId") Integer cId,Model model,Principal principal)
{
	Optional<Contact> contactOp = contactRepo.findById(cId);
	String username = principal.getName();
	User user = userrepo.findByUserEmail(username);
	Contact contact=contactOp.get();
	
	if(contact.getUser().getId()==user.getId()) {
		model.addAttribute("contact", contact);
			
	}
	
	return "normal/show_contactRecord";
}

@GetMapping("/deleteContact/{cId}")
public String deleteContact(@PathVariable("cId") Integer cId,Model model,HttpSession session,Principal principal)
{
	Optional<Contact> contactOp = contactRepo.findById(cId);
	Contact contact = contactOp.get();
	
	
	
	
	String useremail = principal.getName();
	User user = userrepo.findByUserEmail(useremail);
	System.out.println("Here W are");
	if(contact.getUser().getId()==user.getId()) {
	contact.setUser(null);
	contactRepo.delete(contact);
	session.setAttribute("message", new Message("contact deleted Successfully","success"));
	}
	
	return "redirect:/user/showcontact/0";
}
@GetMapping("/editContact/{cId}")
public String editContact(@PathVariable("cId") Integer cId,Model model,HttpSession session) {
	
	Optional<Contact> contactOp = contactRepo.findById(cId);
	Contact contact = contactOp.get();
	session.setAttribute("cId", cId);
	model.addAttribute("contact", contact);
	
	return "normal/update_contact";
}
	
@PostMapping("/updateContactRecord")
public String updateContactRecord(@ModelAttribute Contact contact,@RequestParam("profilePic") MultipartFile file,@RequestParam("cId") Integer cId) {

	Optional<Contact> contactOp = contactRepo.findById(cId);
	Contact contact1 = contactOp.get();
	if(contact.getContactProfile()!=contact1.getContactProfile() && file.getOriginalFilename() !="" )
	{
		System.out.println(file.getOriginalFilename());
		contact1.setContactProfile(file.getOriginalFilename());
		System.out.println("Inside File uploader");
	}
	
	if(contact.getContactDesc()!=contact1.getContactDesc())
		contact1.setContactDesc(contact.getContactDesc());
	
	contact1.setContactEmail(contact.getContactEmail());
	contact1.setContactName(contact.getContactName());
	
	contactRepo.save(contact1);
	System.out.println("Contact has been Updated successfully");
	return "redirect:/user/contactdetail/"+cId;
}

@GetMapping("/userProfile")
public String userProfile(Principal principal,Model model) {
	String userEmail = principal.getName();
	User user = userrepo.findByUserEmail(userEmail);
	model.addAttribute("user", user);
	return "normal/user_profile";
}


@GetMapping("/userSetting")
public String userSetting() {
	return "normal/user_setting";
}


@PostMapping("/changePassword")
public String changePassword(@RequestParam("oldPassword") String oldPassword
		,@RequestParam("newPassword") String newPassword,
		@RequestParam("reNewPassword") String reNewPassword,Principal principal,HttpSession session) {
	
	
	String userEmail = principal.getName();
	User currentUser = userrepo.findByUserEmail(userEmail);
	System.out.println("before checking password");
	
	if( this.bCryptEncoder.matches(oldPassword, currentUser.getUserPassword()) ) {
		currentUser.setUserPassword(bCryptEncoder.encode(reNewPassword));
		System.out.println("Changed password");
		this.userrepo.save(currentUser);
		session.setAttribute("message", new Message("Password changed","success"));

		System.out.println("Changed password");
		return "redirect:/user/dashboard";

	}
	else {
		session.setAttribute("message", new Message("Password Miss-match","danger"));
		
		return "redirect:/user/userSetting";		
	}
}

}
