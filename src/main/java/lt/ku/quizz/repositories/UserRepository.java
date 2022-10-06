package lt.ku.quizz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.ku.quizz.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByUsername(String username);
}
