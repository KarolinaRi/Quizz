package lt.ku.quizz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.ku.quizz.entities.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {

}
