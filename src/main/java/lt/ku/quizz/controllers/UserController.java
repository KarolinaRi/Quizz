package lt.ku.quizz.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lt.ku.quizz.entities.User;
import lt.ku.quizz.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/")  
	public String userList(Model model) {
		model.addAttribute("users", userService.getUsers());
		return "user_list";
	}
	
	@GetMapping("/new")  
	public String userNew(Model model) {
		return "user_new";
	}
	
//	@PostMapping("/new")
//	public String addUser(	@RequestParam("username") String username, 
//							@RequestParam("password") String password, 
//							@RequestParam("name") String name,
//							@RequestParam("surname") String surname,
//							@RequestParam("email") String email,
//							@RequestParam("role") String role
//							) {
//		User u = new User(username, password, name, surname, email, role);
//		u = userService.addUser(u);
//		return "redirect:/user/";
//	}
	
	@PostMapping("/new")
	public String addUser(Model model, @Valid @ModelAttribute User user, BindingResult userResult) {
		if(userResult.hasErrors()) {
			
//			model.addAttribute("client", clientService.getClients());
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
	
	
}
