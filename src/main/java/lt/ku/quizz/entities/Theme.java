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
@Table(name = "theme")
public class Theme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 64)
	@Length(min=3, max=64, message="Temos pavadinimas turi buti sudarytas nuo 3 iki 64 simboliu")
	private String theme;
	
	@OneToMany(mappedBy = "theme")
	private List<Quizz> quizzes = new ArrayList<Quizz>();

	public Theme() {
		super();
	}

	public Theme(
			@Length(min = 3, max = 64, message = "Temos pavadinimas turi buti sudarytas nuo 3 iki 64 simboliu") String theme) {
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
}
