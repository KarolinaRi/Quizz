package lt.ku.quizz.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import lt.ku.quizz.entities.Answer;
import lt.ku.quizz.entities.Question;
import lt.ku.quizz.entities.Quizz;
import lt.ku.quizz.entities.User;
import lt.ku.quizz.services.LanguageService;
//import lt.ku.klausimynas.entities.User;
import lt.ku.quizz.services.QuestionService;
import lt.ku.quizz.services.QuizzService;
import lt.ku.quizz.services.UserService;

@Controller
@RequestMapping("/quizz/")
public class QuizzController {

	@Autowired
	QuizzService quizzService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired 
	LanguageService languageService;
	
	@GetMapping("/")  
	public String quizzList(Model model) {
		model.addAttribute("quizzes", quizzService.getQuizzes());
		model.addAttribute("users", userService.getUsers());
		model.addAttribute("questions", questionService.getQuestions());
		model.addAttribute("languages", languageService.getLanguages());
		return "quizz_list";
	}
	
	@GetMapping("/new")  
	public String quizzNew(Model model, Integer id) {
		Quizz q= new Quizz();
		for(int i = 0; i <= 4; i++) {
			quizzService.addQuestion(new Question());
		}
		
		model.addAttribute("quizz", q);
		model.addAttribute("users", userService.getUsers());
		model.addAttribute("languages", languageService.getLanguages());
//		model.addAttribute("questions", new Question());
		return "quizz_new";
	}
	
	@PostMapping("/new")
	public String addQuizz( 
							@RequestParam("question") Question question, Model model
							) {
		Quizz q = new Quizz();
//		question.setQuizz(q);
		
		if(q.getQuestions() == null) {
			q.setQuestions(new ArrayList<Question>());
		}
		q.getQuestions().add(question);
		questionService.addQuestion(question);
		quizzService.addQuizz(q);
		
		return "redirect:/quizz/";
	}
	 
	
	
//	@PostMapping("/new")
//	public String addQuizz(
//			@ModelAttribute Quizz quizz, 			
//			@RequestParam("userId") Integer userId,
//			@RequestParam("questions") List<Question> questions,
//			@RequestParam("name") String name,
//			@RequestParam("language") String language) {
//		quizz.setName(name);
//		quizz.setLanguage(language);
//		quizz.setUser(userService.getUser(userId));
//		quizz.setQuestions(questionService.getQuestions());
//		quizzService.addQuizz(quizz);
//		return "redirect:/quizz/";
//	}
	
	
	@GetMapping("/update/{id}")
	public String quizzNew(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("quizz", quizzService.getQuizz(id));
		model.addAttribute("users", userService.getUsers());
		model.addAttribute("questions", questionService.getQuestions());
		model.addAttribute("languages", languageService.getLanguages());
		return "quizz_update";
	}
	
	@PostMapping("/update/{id}")
	public String quizzUpdate(@PathVariable("id") Integer id, @ModelAttribute Quizz q) {
		quizzService.updateQuizz(q);
		return "redirect:/quizz/";
	}
	
	@GetMapping("/delete/{id}")
	public String quizzDelete(@PathVariable("id") Integer id) {
		
		quizzService.deleteQuizz(id);
		return "redirect:/quizz/";
	}
	
	@GetMapping("/play/{id}")
	public String play(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("questions", questionService.getQuestions());
		
		return "quizz_play";
	}
	
	@PostMapping("/play/{id}")
	public String submit(HttpServletRequest req) {
		int score = 0;		
		String[] questionIds = req.getParameterValues("questionId");
		for(String questionId : questionIds) {
			System.out.println(questionId.toString());
			int answerIdCorrect = questionService.findAnswerIdCorrect(Integer.parseInt(questionId));
			if(answerIdCorrect == Integer.parseInt(req.getParameter("q_" + questionId))) {
				score++;
			}
		}
		req.setAttribute("score", score);
		return "result";
	}
	
//	@PostMapping("/quizznew")
//	public String addQuestion(@RequestParam("quizz") Quizz quizz, 
//							@RequestParam("question") String question, 
//							@RequestParam("answers") List<Answer> answers
////							@RequestParam("tipas") List<String> tipas
//							) {
//		Question q = new Question(quizz, question, answers);
//		q = questionService.addQuestion(q);		
//		return "redirect:/quizz/new/";
//	}
	
}
