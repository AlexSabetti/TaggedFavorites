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
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.groupproject.models.LoginUser;
import com.codingdojo.groupproject.models.Media;
import com.codingdojo.groupproject.models.RegisterUser;
import com.codingdojo.groupproject.models.Tag;
import com.codingdojo.groupproject.models.User;
import com.codingdojo.groupproject.services.MediaService;
import com.codingdojo.groupproject.services.TagService;
import com.codingdojo.groupproject.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	@Autowired
	private MediaService mediaService;
	@Autowired
	private UserService userService;
	@Autowired
	private TagService tagService;
	@Autowired
	private HttpSession session;
	
	
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("newLogin", new LoginUser());
		model.addAttribute("newRegister", new RegisterUser());
		return "index.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newRegister") RegisterUser newRegister, BindingResult result, Model model) {
		User user = userService.register(newRegister, result);
		
		if(result.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
			return "index.jsp";
		}
		
		session.setAttribute("currentuser", user.getId());
		return "redirect:/taggedfavorites/home";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model) {
		User user = userService.login(newLogin, result);
		if(result.hasErrors()) {
			model.addAttribute("newRegister", new RegisterUser());
			return "index.jsp";
		}
		session.setAttribute("currentuser", user.getId());
		return "redirect:/taggedfavorites/home";
	}
	
	@GetMapping("/taggedfavorites/home")
	public String home(Model model) {
		if(session.getAttribute("currentuser") == null) {
			model.addAttribute("warning", "You are not logged in, please log in.");
			return "redirect:/";
		} else {
			User user = userService.findUserById((long)session.getAttribute("currentuser"));
			model.addAttribute("user", user);
			return "home.jsp";
		}
	}
	
	@GetMapping("/taggedfavorites/{mediaId}/remove")
	public String removeFavorite(@PathVariable("mediaId") Long mediaId, Model model) {
		if(session.getAttribute("currentuser")== null) {
			return "redirect:/";
		}else {
			User user = userService.findUserById((long) session.getAttribute("currentuser"));
			Media media = mediaService.findMedia(mediaId);
			userService.removeFavoriteMedia(user, media);
			return "redirect:/taggedfavorites/home";
		}
	}
	
	@GetMapping("/taggedfavorites/{mediaId}/delete")
	public String deleteMedia(@PathVariable("mediaId") Long mediaId, Model model) {
		if(session.getAttribute("currentuser")== null) {
			return "redirect:/";
		}else {
			
			Media media = mediaService.findMedia(mediaId);
			mediaService.deleteMedia(media);
			return "redirect:/taggedfavorites/home";
		}
	}
	
	@GetMapping("/games/new")
	public String createGame(Model model) {
		if(session.getAttribute("currentuser") == null) {
			model.addAttribute("warning", "You are not logged in, please log in.");
			return "redirect:/";
		} else {
			User user = userService.findUserById((long)session.getAttribute("currentuser"));
			model.addAttribute("user", user);
			model.addAttribute("newMedia", new Media());
			model.addAttribute("tags", tagService.findAllTags());
			return "createOne.jsp";
		}
	}
	
	@PostMapping("/games/new")
	public String newGame(@Valid @ModelAttribute("newMedia") Media media, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "createOne.jsp";
		} else {
			User user = userService.findUserById((long) session.getAttribute("currentuser"));
			userService.addFavoriteMedia(user, mediaService.createMedia(media));
			return "redirect:/taggedfavorites/home";
		}
	}
	
	@GetMapping("/taggedfavorites/{mediaId}/edit")
	public String editGame(@PathVariable("mediaId") Long id, Model model) {
		if(session.getAttribute("currentuser") == null) {
			model.addAttribute("warning", "You are not logged in, please log in.");
			return "redirect:/";
		} else {
			User user = userService.findUserById((long)session.getAttribute("currentuser"));
			Media media = mediaService.findMedia(id);
			List<Tag> toModel = tagService.findAllTags();
			for(Tag tag : media.getTags()) {
				for(Tag freshTag : toModel) {
					if(tag.getConflictId() == freshTag.getConflictId() && tag.getConflictId() > 0) {
						toModel.remove(freshTag);
					} else if(tag.getId() == freshTag.getId()) {
						toModel.remove(freshTag);
					}
					
				}
			}
			model.addAttribute("user", user);
			model.addAttribute("newMedia", media);
			model.addAttribute("tags", toModel);
			return "editOne.jsp";
		}
	}
	
	@PostMapping("/taggedfavorites/{mediaId}/edit")
	public String editing(@Valid @ModelAttribute("newMedia") Media media, @PathVariable("mediaId") Long id, BindingResult result, Model model, @RequestParam String tags) {
		if(result.hasErrors()) {
			return "editOne.jsp";
		} else {
			List<Tag> actualtags = media.getTags();
			for(String name : tags.split(",")) {
				if(!actualtags.contains(tagService.findTagByName(name))) {
					media.getTags().add(tagService.findTagByName(name));
				}
			}
			media.setId(id);
			mediaService.updateMedia(media);
			return "redirect:/taggedfavorites/home";
		}
	}
	
	@GetMapping("/test")
	public String test(Model model) {
		if(session.getAttribute("currentuser") == null) {
			model.addAttribute("warning", "You are not logged in, please log in.");
			return "redirect:/";
		} else {
			model.addAttribute("user", userService.findUserById((long) session.getAttribute("currentuser")));
			model.addAttribute("newMedia", new Media());
			model.addAttribute("taglist", tagService.findAllTags());
			return "test.jsp";
		}
	}
	
	@PostMapping("/test/create")
	public String testCreate(@Valid @ModelAttribute("newMedia") Media media, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "redirect:/test";
		} else {
			User user = userService.findUserById((long) session.getAttribute("currentuser"));
			userService.addFavoriteMedia(user, mediaService.createMedia(media));
			return "redirect:/test";
		}
	}
	
	
	
	@GetMapping("/logout")
	public String logout(Model model) {
		session.invalidate();
		return "redirect:/";
	}
	
}
