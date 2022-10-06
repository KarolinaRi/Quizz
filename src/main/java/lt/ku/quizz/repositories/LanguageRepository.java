package lt.ku.quizz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.ku.quizz.entities.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer>{

}
