package lt.ku.quizz.entities;

import java.util.ArrayList;
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

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "question")
@Where(clause="is_deleted=0")
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

	@Column(nullable = false)
	private String type; // = {"True/False", "Pasirenkami"};
	
	private Integer answerQuantity;
	
	@Column(nullable=false, name="is_deleted")
	private Boolean deleted = false;
		
	public Question() {
		super();
	}

//	public Question(Quizz quizz,
//			@Length(min = 3, max = 100, message = "Klausimo ilgis turi buti nuo 3 iki 100 simboliu") String question,
//			List<Answer> answers, String type, Integer answerQuantity, Boolean deleted) {
//		super();
//		this.quizz = quizz;
//		this.question = question;
//		this.answers = answers;
//		this.type = type;
//		this.answerQuantity = answerQuantity;
//		this.deleted = deleted;
//	}
	
	public Question(Quizz quizz,
			@Length(min = 3, max = 100, message = "Klausimo ilgis turi buti nuo 3 iki 100 simboliu") String question, String type, Integer answerQuantity,
			Boolean deleted) {
		super();
		this.quizz = quizz;
		this.question = question;
		this.type = type;
		this.answerQuantity = answerQuantity;
		this.deleted = deleted;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getAnswerQuantity() {
		return answerQuantity;
	}

	public void setAnswerQuantity(Integer answerQuantity) {
		this.answerQuantity = answerQuantity;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
}
