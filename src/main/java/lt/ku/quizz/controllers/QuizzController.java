package lt.ku.quizz.controllers;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import lt.ku.quizz.entities.User;
import lt.ku.quizz.repositories.AnswerRepository;
import lt.ku.quizz.repositories.QuestionRepository;
import lt.ku.quizz.repositories.QuizzRepository;
import lt.ku.quizz.services.AnswerService;
import lt.ku.quizz.services.LanguageService;
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
	AnswerService answerService;
	
	@Autowired 
	LanguageService languageService;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	QuizzRepository quizzRepository;
	
	@Autowired
	AnswerRepository answerRepository;
	
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
    	model.addAttribute("users", userService.getUsers());
		model.addAttribute("languages", languageService.getLanguages());
		return "quizz_new";
	}
	
	@PostMapping("/new")
	public String addQuizz(@RequestParam("name") String name, @RequestParam("language") Language language, @RequestParam("user") User user) {
		Quizz q = new Quizz(user, name, language);
		System.out.println("quizz kiekis: " + quizzService.getQuizzes().size());
		quizzRepository.save(q);
		
		return "redirect:/quizz/new/question/";
	}

	
	@GetMapping("/new/question")
	public String questionNew(Model model) {
		model.addAttribute("quizzes", quizzService.getQuizzes());
		
		return "question_new";
	}
	
	@PostMapping("/new/question")
	public String addQuestion(@RequestParam("question") String question, @RequestParam("quizz") Quizz quizz) {
		Question q = new Question(quizz, question);
		
		questionRepository.save(q);
		return "redirect:/quizz/new/question/answer/";
	}

	@GetMapping("/new/question/answer")
	public String answerNew(Model model) {
		model.addAttribute("questions", questionService.getQuestions());
	//	model.addAttribute("question", questionService.getQuestion(question.getId()));
		//model.addAttribute("lastQquestion", questionService.getQuestion(quizzService.getQuizzes().size()-1));
		
		return "answer_new";
	}
	
	@PostMapping("/new/question/answer")
	public String addAnswer(@RequestParam("answer") String answer, @RequestParam("question") Question question, 
			@RequestParam("correct") Boolean correct) {
		//model.addAttribute("question", questionService.getQuestion(question.getId()));
		Answer a = new Answer(question, answer, correct);
		
		answerRepository.save(a);
		return "redirect:/quizz/new/question/";
	}
	
	@GetMapping("/update/{id}")
	public String quizzNew(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("quizz", quizzService.getQuizz(id));
		model.addAttribute("users", userService.getUsers());
		model.addAttribute("questions", questionService.getQuestions());
		model.addAttribute("answers", answerService.getAnswers());
		model.addAttribute("languages", languageService.getLanguages());
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
		Quizz quizz = quizzService.getQuizz(id);
		for(Question question: quizz.getQuestions()) {
			int answerIdCorrect = questionService.findAnswerIdCorrect(question.getId());
			if(answerIdCorrect == Integer.parseInt(req.getParameter("q_" + question.getId()))) {
				score++;
			}
		}
		req.setAttribute("score", score);
		return "result";
	}
}

