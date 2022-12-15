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
import org.springframework.web.bind.annotation.RequestParam;

import lt.ku.quizz.entities.Theme;
import lt.ku.quizz.repositories.ThemeRepository;
import lt.ku.quizz.services.ThemeService;

@Controller
@RequestMapping("/user/theme")
public class ThemeController {
	
	@Autowired
	ThemeService themeService;
	
	@Autowired
	ThemeRepository themeRepository;
	
	@GetMapping("/")  
	public String themeList(Model model) {
		model.addAttribute("themes", themeService.getThemes());
		return "theme_list";
	}
	
	@GetMapping("/new")  
	public String themeNew(Model model) {
		model.addAttribute("theme", new Theme());
		return "theme_new";
	}
	
	@PostMapping("/new")
	public String addTheme(@Valid @ModelAttribute Theme theme, BindingResult themeResult) {
		if(themeResult.hasErrors()) {
			return "theme_new";
		}
		themeService.addTheme(theme);
//		Theme t = new Theme(theme);
//		themeService.addTheme(t);
//		model.addAttribute("themes", themeService.getThemes());
		return "redirect:/theme/";
	}
	
	@GetMapping("/update/{id}")
	public String themeNew(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("theme", themeService.getTheme(id));
		return "theme_update";
	}
	
	@PostMapping("/update/{id}")
	public String themeUpdate(@PathVariable("id") Integer id,  @RequestParam("theme") String theme) {
		Theme t= themeService.getTheme(id);
		t.setTheme(theme);
		themeService.updateTheme(t);
		return "redirect:/theme/";
	}
	
	@GetMapping("/delete/{id}")
	public String themeDelete(@PathVariable("id") Integer id) {
		themeService.deleteTheme(id);
		return "redirect:/theme/";
	}

}
