package lt.ku.quizz.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "question")
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "quizz_id")
	private Quizz quizz;
	
	
	@Column(length = 100)
	@Length(min = 3, max = 100, message = "Klausimo ilgis turi buti nuo 3 iki 100 simboliu")
	private String question;
	
	@OneToMany(mappedBy = "question")
	private List<Answer> answers;

	public Question() {
		super();
	}

	public Question(Quizz quizz,
			@Length(min = 3, max = 100, message = "Klausimo ilgis turi buti nuo 3 iki 100 simboliu") String question,
			List<Answer> answers) {
		super();
		this.quizz = quizz;
		this.question = question;
		this.answers = answers;
	}
	
	public Question(Quizz quizz,
			@Length(min = 3, max = 100, message = "Klausimo ilgis turi buti nuo 3 iki 100 simboliu") String question) {
		super();
		this.quizz = quizz;
		this.question = question;
	}

//	public Question(Quizz quizz,
//		@Length(min = 3, max = 100, message = "Klausimo ilgis turi buti nuo 3 iki 100 simboliu") String question,
//		List<String> tipas, List<Answer> answers) {
//	super();
//	this.quizz = quizz;
//	this.question = question;
//	this.tipas = tipas;
//	this.answers = answers;
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Quizz getQuizz() {
		return quizz;
	}

	public void setQuizz(Quizz quizz) {
		this.quizz = quizz;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
}
