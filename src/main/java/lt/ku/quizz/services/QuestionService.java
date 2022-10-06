package lt.ku.quizz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.ku.quizz.entities.Answer;
import lt.ku.quizz.entities.Question;
import lt.ku.quizz.repositories.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	QuestionRepository questionRepository;
	
	public List<Question> getQuestions(){
		return questionRepository.findAll();
	}
	
	public Question getQuestion(Integer id) {
		return questionRepository.getReferenceById(id);
	}
	
	public Question addQuestion(Question question) {
		return questionRepository.save(question);
	}
	
	public Question updateQuestion(Question question) {
		Question old=this.getQuestion(question.getId());
		old.setQuizz(question.getQuizz());
		old.setQuestion(question.getQuestion());
		old.setAnswers(question.getAnswers());
//		old.setTipas(question.getTipas());
		questionRepository.save(old);
		return old;
	}
	
	public void deleteQuestion(Integer id) {
		questionRepository.deleteById(id);
	}
	
	public int findAnswerIdCorrect(int questionId) {
		Question question = questionRepository.findById(questionId).get();
		for(Answer answer : question.getAnswers()) {
			if(answer.isCorrect()){
				return answer.getId();
			}
		}
		return -1;
	}
}
