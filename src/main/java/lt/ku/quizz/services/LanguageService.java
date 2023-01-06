package lt.ku.quizz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lt.ku.quizz.entities.Language;
import lt.ku.quizz.repositories.LanguageRepository;

@Service
public class LanguageService {

	@Autowired
	LanguageRepository languageRepository;
	
	public List<Language> getLanguages(){
		return languageRepository.findAll();
	}
	
	public Language getLanguage(Integer id) {
		return languageRepository.getById(id);
	}
	
	public Language addLanguage(Language language ) {
		return languageRepository.save(language);
	}
	
	public Language updateLanguage(Language language) {
		Language old=this.getLanguage(language.getId());
		old.setLanguage(language.getLanguage());
		languageRepository.save(old);
		return old;
	}
	
	public void deleteLanguage(Integer id) {
		languageRepository.deleteById(id);
	}
}
