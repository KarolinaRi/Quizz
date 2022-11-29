package lt.ku.quizz.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "theme")
public class Theme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 64)
	private String theme;
	
	@OneToMany(mappedBy = "theme")
	private List<Quizz> quizzes;

	public Theme() {
		super();
	}

	public Theme(String theme) {
		super();
		this.theme = theme;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public List<Quizz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quizz> quizzes) {
		this.quizzes = quizzes;
	}
}
