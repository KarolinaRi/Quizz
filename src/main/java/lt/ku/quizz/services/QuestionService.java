package lt.ku.quizz.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.ku.quizz.entities.Answer;
import lt.ku.quizz.entities.Question;
import lt.ku.quizz.entities.Quizz;
import lt.ku.quizz.repositories.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	QuizzService quizzService;
	
	@Autowired 
	AnswerService answerService;
	
	public List<Question> getQuestions(){
		return questionRepository.findAll();
	}
	
	public Question getQuestion(Integer id) {
		return questionRepository.getById(id);
	}
	
	public Integer getQuizzId(Question question) {
		return question.getQuizz().getId();
	}
	
	public Question addQuestion(Question question) {
		question.setQuizz(quizzService.getQuizz(quizzService.getQuizzes().size()));
		return questionRepository.save(question);
	}
	
	public Question updateQuestion(Question question) {
		Question old=this.getQuestion(question.getId());
		old.setQuizz(question.getQuizz());
		old.setQuestion(question.getQuestion());
		old.setAnswers(question.getAnswers());
		old.setType(question.getType());
		old.setDeleted(question.isDeleted());
		questionRepository.save(old);
		return old;
	}
	
	public void deleteQuestion(Integer id) {
		//questionRepository.deleteById(id);
		List<Answer> answers = answerService.getAnswers();
		for(int i = 0; i < answers.size(); i++) {
			if(answers.get(i).getId()==id) {
				answerService.deleteAnswer(answers.get(i).getId());
				//answers.get(i).setDeleted(true);
			}
		}
//		Question q = getQuestion(id);
//		q.setDeleted(true);
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
	
	public List<Question> findByQuizz(Quizz quizz){
		List<Question>questions = new ArrayList<Question>();
		for(Question question: getQuestions()) {
			if(question.getQuizz() == quizz) {
				questions.add(question);
			}
		}
		
		return questions;
	}
}
