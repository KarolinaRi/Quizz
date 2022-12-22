package lt.ku.quizz.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "answer")
@SQLDelete(sql = "UPDATE answer SET deleted = 'DELETED'  id = ?", check = ResultCheckStyle.COUNT)
@Where(clause="is_deleted=0")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="question_id")
	private Question question;

	@Column(length = 100)
	@Length(min=3, max=100, message="Atsakymas turi būti sudarytas iš 3 - 100 simbolių")
	private String answer;
	
	@Column(nullable = false)
	private Boolean correct = false;
	
	@Column(nullable=false, name="is_deleted")
   // @Enumerated(EnumType.STRING)
	private String deleted = "ACTIVE";

	public Answer() {
		super();
	}

	public Answer(Question question,
			@Length(min = 3, max = 100, message = "Atsakymas turi būti sudarytas iš 3 - 100 simbolių") String answer,
			Boolean correct, String deleted) {
		super();
		this.question = question;
		this.answer = answer;
		this.correct = correct;
		this.deleted = deleted;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Boolean isCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

	public String isDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
//	@PreRemove
//	public void deleteAnswer() {
//		this.deleted = deleted.DELETED;
//	}
}
