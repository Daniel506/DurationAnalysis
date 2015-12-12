package mk.ukim.finki.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "word", catalog = "duration_db")
public class Word {
	private String id;
	private String syllables;
	private int length;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "SYLLABLES")
	public String getSyllables() {
		return syllables;
	}
	
	public void setSyllables(String syllables) {
		this.syllables = syllables;
	}

	@Column(name = "WORD_LENGTH")
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	
}
