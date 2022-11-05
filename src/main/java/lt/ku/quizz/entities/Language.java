package lt.ku.quizz.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="language")
public class Language {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 20)
	@Length(min = 3, max = 20)
	private String language;
	
	@OneToMany(mappedBy = "language")
	private List<Quizz> quizzes;

	public Language() {
		super();
	}

	public Language(@Length(min = 3, max = 20) String language, List<Quizz> quizzes) {
		super();
		this.language = language;
		this.quizzes = quizzes;
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

	public List<Quizz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quizz> quizzes) {
		this.quizzes = quizzes;
	}

	
}
