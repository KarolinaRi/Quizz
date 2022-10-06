package lt.ku.quizz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.ku.quizz.entities.Quizz;

public interface QuizzRepository extends JpaRepository<Quizz, Integer>{

}
