package lt.ku.quizz.controllers;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;

import lt.ku.quizz.entities.Answer;
import lt.ku.quizz.entities.Question;
import lt.ku.quizz.entities.Quizz;
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
	public String quizzNew(Model model) {
		model.addAttribute("languages", languageService.getLanguages());
		model.addAttribute("themes", themeService.getThemes());
		model.addAttribute("quizz", new Quizz());
		return "quizz_new";
	}
	
	@PostMapping("/new")
	public String AddQuizz(@Valid @ModelAttribute Quizz quizz, BindingResult quizzResult) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			if(quizzResult.hasErrors()) {
				return "quizz_new";
			}
			String username = ((UserDetails)principal).getUsername();
			User user = userRepository.findByUsername(username);
			quizz.setUser(user);
			quizzService.addQuizz(quizz);
		}
		
		return "redirect:/quizz/new/question/";
	}

	
	@GetMapping("/new/question")
	public String questionNew(Model model) {
		model.addAttribute("question", new Question());
		return "question_new";
	}
	
	@PostMapping("/new/question")
	public String addQuestion(@RequestParam("question") String question, @RequestParam("type") String type,
			@RequestParam("answerQuantity") Integer answerQuantity, Model model) {
		int id = quizzService.getQuizzes().size();
	    Quizz quizz = quizzService.getQuizz(id);
		Question q = new Question(quizz, question, type, answerQuantity, false);
		System.out.println(quizz.getId());
		questionService.addQuestion(q);
		return "redirect:/quizz/new/question/answer";
	}

	@GetMapping("/new/question/answer")
	public String answerNew(Model model) {
		model.addAttribute("questions", questionService.getQuestions());
		model.addAttribute("question", questionService.getQuestion(questionService.getQuestions().size()));
		model.addAttribute("themes", themeService.getThemes());
		return "answer_new";
	}
	
	@PostMapping("/new/question/answer")
	public String addAnswer(@RequestParam("answer") String answer, @RequestParam("correct") Boolean correct) {
		Question question = questionService.getQuestion(questionService.getQuestions().size());
		Answer a = new Answer(question, answer, correct, false);
		answerService.addAnswer(a);
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
		return "redirect:/quizz/";
	}
	
	@GetMapping("/update/question/update/{id}")
	public String questionNew(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("quizz", quizzService.getQuizz(id));
		model.addAttribute("question", questionService.getQuestion(id));
		return "question_update";
	}
	
	@PostMapping("/update/question/update/{id}")
	public String questionUpdate(@PathVariable("id") Integer id, @Valid @ModelAttribute Question q, BindingResult result) {
		if(result.hasErrors())
			return "question_update";
		questionService.updateQuestion(q);
		System.out.println(id);
		return "redirect:/quizz/update/{quizz.id}";
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

