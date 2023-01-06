package lt.ku.quizz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lt.ku.quizz.entities.Theme;
import lt.ku.quizz.repositories.ThemeRepository;

@Service
public class ThemeService {

	@Autowired
	ThemeRepository themeRepository;
	
	public List<Theme> getThemes(){
		return themeRepository.findAll();
	}
	
	public Theme getTheme(Integer id) {
		return themeRepository.getById(id);
	}
	
	public Theme addTheme(Theme theme ) {
		return themeRepository.save(theme);
	}
	
	public Theme updateTheme(Theme theme) {
		Theme old=this.getTheme(theme.getId());
		old.setTheme(theme.getTheme());
		themeRepository.save(old);
		return old;
	}
	
	public void deleteTheme(Integer id) {
		themeRepository.deleteById(id);
	}
}
