package com.codingdojo.groupproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.codingdojo.groupproject.models.LoginUser;
import com.codingdojo.groupproject.models.RegisterUser;
import com.codingdojo.groupproject.models.User;
import com.codingdojo.groupproject.services.UserService;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private HttpSession session;
	
	
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("newLogin", new LoginUser());
		return "index.jsp";
	}
	
	@GetMapping("/register")
	public String indexReg(Model model) {
		model.addAttribute("newRegister", new RegisterUser());
		return "registrationpage.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newRegister") RegisterUser newRegister, BindingResult result, Model model) {
		User user = userService.register(newRegister, result);
		
		if(result.hasErrors()) {
			return "registrationpage.jsp";
		}
		
		session.setAttribute("currentuser", user.getId());
		return "redirect:/games";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model) {
		User user = userService.login(newLogin, result);
		if(result.hasErrors()) {
			return "index.jsp";
		}
		session.setAttribute("currentuser", user.getId());
		return "redirect:/games";
	}
	
	@GetMapping("/home")
	public String home(Model model) {
		if(session.getAttribute("currentuser") == null) {
			model.addAttribute("warning", "You are not logged in, please log in.");
			return "redirect:/";
		} else {
			User user = userService.findUserById((long)session.getAttribute("currentuser"));
			model.addAttribute("user", user);
			model.addAttribute("users", userService.findUsers());
			return "home.jsp";
		}
	}
	
	@GetMapping("/logout")
	public String logout(Model model) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/profile/{userId}")
	public String viewUser(@PathVariable("userId") Long userId, Model model) {
		if(session.getAttribute("currentuser")== null) {
			return "redirect:/";
		}else {
			User profileUser = userService.findUserById(userId);
			model.addAttribute("user", profileUser);
			return "userprofile.jsp";
		}
	}
	
}
