package lt.ku.quizz.controllers;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lt.ku.quizz.entities.Answer;
import lt.ku.quizz.entities.Language;
import lt.ku.quizz.entities.Question;
import lt.ku.quizz.entities.Quizz;
import lt.ku.quizz.entities.Theme;
import lt.ku.quizz.entities.User;
import lt.ku.quizz.repositories.AnswerRepository;
import lt.ku.quizz.repositories.QuestionRepository;
import lt.ku.quizz.repositories.QuizzRepository;
import lt.ku.quizz.repositories.UserRepository;
import lt.ku.quizz.services.AnswerService;
import lt.ku.quizz.services.LanguageService;
import lt.ku.quizz.services.QuestionService;
import lt.ku.quizz.services.QuizzService;
import lt.ku.quizz.services.ThemeService;
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
	AnswerService answerService;
	
	@Autowired 
	LanguageService languageService;
	
	@Autowired 
	ThemeService themeService;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	QuizzRepository quizzRepository;
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	UserRepository userRepository; 
	
	@GetMapping("/")  
	public String quizzList(Model model) {
		model.addAttribute("quizzes", quizzService.getQuizzes());
		model.addAttribute("users", userService.getUsers());
		model.addAttribute("questions", questionService.getQuestions());
		model.addAttribute("languages", languageService.getLanguages());
		model.addAttribute("themes", themeService.getThemes());
		return "quizz_list";
	}
	
	@GetMapping("/new")  
	public String quizzNew(Model model, Integer id) {
    	//model.addAttribute("users", userService.getUsers());
		model.addAttribute("languages", languageService.getLanguages());
		model.addAttribute("themes", themeService.getThemes());
		return "quizz_new";
	}
	
	@PostMapping("/new")
	public String addQuizz(@RequestParam("name") String name, @RequestParam("language") Language language, @RequestParam("theme") Theme theme) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			String username = ((UserDetails)principal).getUsername();
			User user = userRepository.findByUsername(username);
			Quizz q = new Quizz(user, name, language, theme, false);
			quizzRepository.save(q);
		}
		
		return "redirect:/quizz/new/question/";
	}

	
	@GetMapping("/new/question")
	public String questionNew(Model model) {
		model.addAttribute("quizzes", quizzService.getQuizzes());
		
		return "question_new";
	}
	
	@PostMapping("/new/question")
	public String addQuestion(@RequestParam("question") String question, @RequestParam("type") String type,
			@RequestParam("answerQuantity") Integer answerQuantity, Model model) {
	    Quizz quizz = quizzService.getQuizz(quizzService.getQuizzes().size());
		Question q = new Question(quizz, question, type, answerQuantity, false);
		System.out.println(quizz.getId());

		questionRepository.save(q);
		return "redirect:/quizz/new/question/answer";
	}

	@GetMapping("/new/question/answer")
	public String answerNew(Model model) {
		model.addAttribute("questions", questionService.getQuestions());
		model.addAttribute("question", questionService.getQuestion(quizzService.getQuizzes().size()));
		model.addAttribute("themes", themeService.getThemes());
		return "answer_new";
	}
	
	@PostMapping("/new/question/answer")
	public String addAnswer(@RequestParam("answer") String answer, @RequestParam("correct") Boolean correct) {
		Question question = questionService.getQuestion(quizzService.getQuizzes().size());
		Answer a = new Answer(question, answer, correct, false);
		answerRepository.save(a);
		return "redirect:/quizz/new/question";
	}
	
	@GetMapping("/update/{id}")
	public String quizzNew(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("quizz", quizzService.getQuizz(id));
		model.addAttribute("users", userService.getUsers());
		model.addAttribute("questions", questionService.getQuestions());
		model.addAttribute("answers", answerService.getAnswers());
		model.addAttribute("languages", languageService.getLanguages());
		model.addAttribute("themes", themeService.getThemes());
		return "quizz_update";
	}
	
	@PostMapping("/update/{id}")
	public String quizzUpdate(@PathVariable("id") Integer id, @ModelAttribute Quizz q, List<Question> questions, List<Answer> answers) {
		quizzService.updateQuizz(q);
		System.out.println("queID: ");
		questions = q.getQuestions();
		for(int i = 0; i < questions.size(); i++) {
			System.out.println(questionService.getQuizzId(questions.get(i)));
		}
		//System.out.println(que.getId());
		//questionService.updateQuestion(questionService.getQuestion(que.getId()));
		//answerService.updateAnswer(an);
		return "redirect:/quizz/";
	}
	
	@GetMapping("/delete/{id}")
	public String quizzDelete(@PathVariable("id") Integer id) {
		
		quizzService.deleteQuizz(id);
		return "redirect:/quizz/";
	}
	
	@GetMapping("/play/{id}")
	public String play(@PathVariable("id") Integer id, Model model) {
		Quizz quizz = quizzService.getQuizz(id);
		model.addAttribute("quizz", quizz);
		model.addAttribute("questions", questionService.findByQuizz(quizz));
		
		return "quizz_play";
	}
	
	@PostMapping("/play/{id}")
	public String submit(HttpServletRequest req, @PathVariable("id") Integer id) {
		int score = 0;		
		int quantity = 0;
		Quizz quizz = quizzService.getQuizz(id);
		for(Question question: quizz.getQuestions()) {
			quantity++;
			int answerIdCorrect = questionService.findAnswerIdCorrect(question.getId());
			if(answerIdCorrect == Integer.parseInt(req.getParameter("q_" + question.getId()))) {
				score++;
			}
		}
		req.setAttribute("quizz", quizz);
		req.setAttribute("questions", questionService.findByQuizz(quizz));
		req.setAttribute("score", score);
		req.setAttribute("quantity", quantity);
		return "result";
	}
}

