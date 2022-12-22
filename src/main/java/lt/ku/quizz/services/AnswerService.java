package lt.ku.quizz.services;

import java.util.List;

import javax.persistence.PreRemove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.ku.quizz.entities.Answer;
import lt.ku.quizz.repositories.AnswerRepository;

@Service
public class AnswerService {

	@Autowired
	AnswerRepository answerRepository;
	
	public List<Answer> getAnswers(){
		return answerRepository.findAll();
	}
	
	public Answer getAnswer(Integer id) {
		return answerRepository.getById(id);
	}
	
	public Answer addAnswer(Answer answer) {
		return answerRepository.save(answer);
	}
	
	public Answer updateAnswer(Answer answer) {
		Answer old=this.getAnswer(answer.getId());
		old.setQuestion(answer.getQuestion());
		old.setAnswer(answer.getAnswer());
		old.setCorrect(answer.isCorrect());
		//old.setDeleted(answer.isDeleted());
		answerRepository.save(old);
		return old;
	}
	@PreRemove
	public void deleteAnswer(Integer id) {
		//answerRepository.deleteById(id);
		Answer a = getAnswer(id);
		a.setDeleted("DELETED");
	}
}
