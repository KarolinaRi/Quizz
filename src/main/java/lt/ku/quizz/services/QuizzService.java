package lt.ku.quizz.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.ku.quizz.entities.Question;
import lt.ku.quizz.entities.Quizz;
import lt.ku.quizz.repositories.QuestionRepository;
import lt.ku.quizz.repositories.QuizzRepository;

@Service
public class QuizzService {

	@Autowired
	QuizzRepository quizzRepository;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	QuestionService questionService;
	
	public List<Quizz> getQuizzes(){
		return quizzRepository.findAll();
	}
	
	public Quizz getQuizz(Integer id) {
		return quizzRepository.getById(id);
	}
	
	public List<Quizz> getQuizzesByUsername(String username){
		List<Quizz> allQuizzes = getQuizzes();
		List<Quizz> myQuizzes = new ArrayList<Quizz> ();
		for (Quizz quizz : allQuizzes) {
			if(quizz.getUser().getUsername().equals(username)) {
				myQuizzes.add(quizz);
			}
		}
		return myQuizzes;
	}
	
	public Quizz addQuizz(Quizz quizz) {
		return quizzRepository.save(quizz);
	}
	
	public Quizz updateQuizz(Quizz quizz) {
		Quizz old=this.getQuizz(quizz.getId());
		old.setName(quizz.getName());
		old.setUser(old.getUser());
		old.setQuestions(quizz.getQuestions());
		old.setLanguage(quizz.getLanguage());
		old.setDeleted(quizz.isDeleted());
		quizzRepository.save(old);
		
		return old;
	}
	
	public void deleteQuizz(Integer id) {
		
		Quizz q = getQuizz(id);
		q.setDeleted(Boolean.TRUE);
//		List<Question> questions = questionService.findByQuizz(q);
//		for(int i = 0; i < questions.size(); i++) {
//			Question qq = questionService.getQuestion(questions.get(i).getId());
//			questionService.deleteQuestion(qq.getId());
//			qq.setDeleted(Boolean.TRUE);
//		}
//		q.setDeleted(Boolean.TRUE);
		
		//quizzRepository.deleteById(id);
	}
}
