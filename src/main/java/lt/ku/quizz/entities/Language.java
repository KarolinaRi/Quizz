package lt.ku.quizz.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="language")
public class Language {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 20)
	@Length(min = 3, max = 20, message ="Kalbos pavadinimas turi būti sudarytas iš 3 - 20 simbolių")
	private String language;
	
	@OneToMany(mappedBy = "language")
	private List<Quizz> quizzes = new ArrayList<Quizz>();

	public Language() {
		super();
	}

	public Language(@Length(min = 3, max = 20, message ="Kalbos pavadinimas turi būti sudarytas iš 3 - 20 simbolių") String language) {
		super();
		this.language = language;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
