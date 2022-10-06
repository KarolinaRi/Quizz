package lt.ku.quizz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.ku.quizz.entities.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{

}
