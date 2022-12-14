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

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "quizz")
@SQLDelete(sql = "UPDATE quizz SET deleted = '1'  id = ?", check = ResultCheckStyle.COUNT)
@Where(clause="is_deleted=0")
public class Quizz {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="kurejo_id")
	private User user;
	
	@Column(length = 100)
	@Length(min=3, max=100, message="Klausimyno pavadinimas turi būti sudarytas iš 3 - 100 simbolių")
	private String name;
	
	@OneToMany(mappedBy = "quizz")
	private List<Question> questions;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="language_id")
	private Language language;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="theme_id")
	private Theme theme;
	
	@Column(nullable = false, name ="is_deleted")
	private Boolean deleted = false;

	public Quizz() {
		super();
	}

	public Quizz(User user,
			@Length(min = 3, max = 100, message = "Klausimyno pavadinimas turi būti sudarytas iš 3 - 100 simbolių") String name,
			Language language, Theme theme, Boolean deleted) {
		super();
		this.user = user;
		this.name = name;
		this.language = language;
		this.theme = theme;
		this.deleted = deleted;
	}
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
