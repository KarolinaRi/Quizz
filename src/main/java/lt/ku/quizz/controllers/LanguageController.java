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

import lt.ku.quizz.entities.Language;
import lt.ku.quizz.services.LanguageService;

@Controller
@RequestMapping("/language")
public class LanguageController {

	@Autowired
	LanguageService languageService;
	
	@GetMapping("/")  
	public String languageList(Model model) {
		model.addAttribute("languages", languageService.getLanguages());
		return "language_list";
	}
	
	@GetMapping("/new")  
	public String languageNew(Model model) {
		return "language_new";
	}
	
	@PostMapping("/new")
	public String addLanguage(Model model, @RequestParam("language") String language) {
		Language l = new Language(language);
		languageService.addLanguage(l);
		model.addAttribute("language", languageService.getLanguages());
		return "redirect:/language/";
	}
	
	@GetMapping("/update/{id}")
	public String languageNew(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("language", languageService.getLanguage(id));
		return "language_update";
	}
	
	@PostMapping("/update/{id}")
	public String languageUpdate(@PathVariable("id") Integer id, @ModelAttribute Language l) {
		languageService.updateLanguage(l);
		return "redirect:/language/";
	}
	
	@GetMapping("/delete/{id}")
	public String themeDelete(@PathVariable("id") Integer id) {
		languageService.deleteLanguage(id);
		return "redirect:/language/";
	}
}
