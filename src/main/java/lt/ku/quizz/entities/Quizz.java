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

@Entity
@Table(name = "quizz")
public class Quizz {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="kurejo_id")
	private User user;
	
	@Column(length = 100)
	private String name;
	
	@OneToMany(mappedBy = "quizz")
	private List<Question> questions;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="language_id")
	private Language language;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="theme_id")
	private Theme theme;

	public Quizz() {
		super();
	}

	public Quizz(User user, String name, Language language, Theme theme) {
		super();
		this.user = user;
		this.name = name;
		this.language = language;
		this.theme = theme;
	}

	public Quizz(User user, String name, List<Question> questions, Language language) {
		super();
		this.user = user;
		this.name = name;
		this.questions = questions;
		this.language = language;
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
}
