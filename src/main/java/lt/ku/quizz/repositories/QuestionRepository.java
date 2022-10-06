package lt.ku.quizz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.ku.quizz.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{

}
