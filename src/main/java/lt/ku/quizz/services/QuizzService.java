package lt.ku.quizz.services;

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
	
	public List<Quizz> getQuizzes(){
		return quizzRepository.findAll();
	}
	
	public Quizz getQuizz(Integer id) {
		return quizzRepository.getReferenceById(id);
	}
	
	public Quizz addQuizz(Quizz quizz) {
		return quizzRepository.save(quizz);
	}
	
	public Question addQuestion(Question question) {
		return questionRepository.save(question);
	}
	
	public Quizz updateQuizz(Quizz quizz) {
		Quizz old=this.getQuizz(quizz.getId());
		old.setName(quizz.getName());
		old.setUser(quizz.getUser());
		old.setQuestions(quizz.getQuestions());
		old.setLanguage(quizz.getLanguage());
		quizzRepository.save(old);
		return old;
	}
	
	public void deleteQuizz(Integer id) {
		quizzRepository.deleteById(id);
	}
}
