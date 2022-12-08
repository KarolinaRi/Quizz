package lt.ku.quizz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lt.ku.quizz.entities.User;
import lt.ku.quizz.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	public List<User> getUsers(){
		return userRepository.findAll();
	}
		
	public User getUser(Integer id) {
		return userRepository.getById(id);
	}
	
	
	public User updateUser(User user) {
		User old=this.getUser(user.getId());
		old.setName(user.getName());
		old.setSurname(user.getSurname());
		old.setEmail(user.getEmail());
		old.setUsername(user.getUsername());
		old.setPassword(user.getPassword());
		old.setRole(user.getRole());
		userRepository.save(old);
		return old;
	}
	
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}
	
	public User addUser(User user) {
		user.setPassword((new BCryptPasswordEncoder()).encode(user.getPassword()));
		
		return userRepository.save(user);
	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user==null) {
			throw new UsernameNotFoundException("Vartotojas nerastas");
		}
		return user;
	}
}
