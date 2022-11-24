package lt.ku.quizz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.ku.quizz.entities.Question;
import lt.ku.quizz.entities.Quizz;

public interface QuizzRepository extends JpaRepository<Quizz, Integer>{
	//Quizz findByQuestions(List<Question> questions);

}
