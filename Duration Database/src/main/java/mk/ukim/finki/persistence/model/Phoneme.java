package mk.ukim.finki.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phoneme", catalog = "duration_db")
public class Phoneme {
	
	private String id;
	private String transcription;
	private String type;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "TRANSCRIPTION")
	public String getTranscription() {
		return transcription;
	}
	
	public void setTranscription(String transcription) {
		this.transcription = transcription;
	}
	
	@Column(name = "PHONEME_TYPE")
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
