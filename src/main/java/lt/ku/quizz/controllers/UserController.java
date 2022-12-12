package lt.ku.quizz.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lt.ku.quizz.entities.Theme;
import lt.ku.quizz.entities.User;
import lt.ku.quizz.services.QuizzService;
import lt.ku.quizz.services.ThemeService;
import lt.ku.quizz.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	QuizzService quizzService;
	
	@Autowired
	ThemeService themeService;
	
	@GetMapping("/")  
	public String userInfo(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			String username = ((UserDetails)principal).getUsername();
			model.addAttribute("quizzes", quizzService.getQuizzesByUsername(username));
			for(User u : userService.getUsers()) {
				if(u.getUsername().equals(username)) {
					model.addAttribute("user", u);
				}
			}
		}
			
		else {
			String username = principal.toString();
			model.addAttribute("quizzes", quizzService.getQuizzesByUsername(username));
		}
		return "user_profile";
	}
	
	@GetMapping("/new")  
	public String userNew(Model model) {
		return "user_new";
	}
	
	@PostMapping("/new")
	public String addUser(Model model, @Valid @ModelAttribute User user, BindingResult userResult) {
		if(userResult.hasErrors()) {
			return "user_new";
		}
		userService.addUser(user);
		model.addAttribute("user", userService.getUsers());
		return "redirect:/user/";
	}
	
	@GetMapping("/update/{id}")
	public String userNew(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("user", userService.getUser(id));
		return "user_update";
	}
	
	@PostMapping("/update/{id}")
	public String userUpdate(@PathVariable("id") Integer id, @ModelAttribute User u) {
		userService.updateUser(u);
		return "redirect:/user/";
	}
	
	@GetMapping("/delete/{id}")
	public String userDelete(@PathVariable("id") Integer id) {
		userService.deleteUser(id);
		return "redirect:/user/";
	}
	
	@GetMapping("/theme")
	public List<Theme> themes() {
		//model.addAttribute("themes", themeService.getThemes());
		List<Theme> themes = themeService.getThemes();
		return themes;
	}
	
	@PostMapping("/theme")
	public String getThemes() {
		
		return "redirect:/themes";
	}
	
	
}
