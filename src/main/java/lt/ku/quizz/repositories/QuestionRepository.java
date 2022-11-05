package lt.ku.quizz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.ku.quizz.entities.Question;
import lt.ku.quizz.entities.Quizz;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	Question findByQuizz(Quizz quizz);
}
