package lt.ku.quizz.controllers;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lt.ku.quizz.entities.User;
import lt.ku.quizz.services.QuizzService;
import lt.ku.quizz.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	QuizzService quizzService;
	
	@GetMapping("/")  
	public String userInfo(Model model) {
		model.addAttribute("quizzes", quizzService.getQuizzes());
//		UserDetails u = userService.loadUserByUsername(null);
//		u.getUsername();
		//model.addAttribute("username", currentUserNameSimple(null));
		
		//currentUserNameSimple(null);
		
		return "user_profile";
	}
	@RequestMapping("/resource")
	public String username(@AuthenticationPrincipal User user) {
		return 	user.getUsername();
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
	
//	@RequestMapping(value = "/username", method = RequestMethod.GET)
//	@ResponseBody
//	public String currentUserName(Authentication authentication) {
//	     return authentication.getName();
//	}
}
