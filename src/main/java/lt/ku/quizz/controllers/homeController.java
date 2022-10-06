package lt.ku.quizz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lt.ku.quizz.services.UserService;

@Controller
public class homeController {
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public String home() {
		return "home";
	}
}
