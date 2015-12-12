package mk.ukim.finki.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "duration", catalog = "duration_db")
public class Duration implements Serializable{
	
	/**
	 * 
	 */
  private static final long serialVersionUID = 1L;
	private String sentenceId;
	private String phonemeId;
	private String syllableId;
	private int phonemePosition;
	private String phonemePrecedent;
	private String phonemeSuccessor;
	private WordPosition wordPosition;
	private BigDecimal duration;
	
	@Id
	@Column(name = "SENTENCE_ID", unique = true, nullable = false)
	public String getSentenceId() {
		return sentenceId;
	}
	
	public void setSentenceId(String sentenceId) {
		this.sentenceId = sentenceId;
	}
	
	@Id
	@Column(name = "PHONEME_ID", unique = true, nullable = false)
	public String getPhonemeId() {
		return phonemeId;
	}
	
	public void setPhonemeId(String phonemeId) {
		this.phonemeId = phonemeId;
	}
	
	@Id
	@Column(name = "SYLLABLE_ID")
	public String getSyllableId() {
		return syllableId;
	}
	
	public void setSyllableId(String syllableId) {
		this.syllableId = syllableId;
	}
	
	@Column(name = "PHONEME_POSITION")
	public int getPhonemePosition() {
		return phonemePosition;
	}
	
	public void setPhonemePosition(int phonemePosition) {
		this.phonemePosition = phonemePosition;
	}
	
	@Column(name = "PHONEME_PRECEDENT")
	public String getPhonemePrecedent() {
		return phonemePrecedent;
	}
	
	public void setPhonemePrecedent(String phonemePrecedent) {
		this.phonemePrecedent = phonemePrecedent;
	}
	
	@Column(name = "PHONEME_SUCCESSOR")
	public String getPhonemeSuccessor() {
		return phonemeSuccessor;
	}
	
	public void setPhonemeSuccessor(String phonemeSuccessor) {
		this.phonemeSuccessor = phonemeSuccessor;
	}
	
	@Column(name = "WORD_POSITION")
	public WordPosition getWordPosition() {
		return wordPosition;
	}
	
	public void setWordPosition(WordPosition wordPosition) {
		this.wordPosition = wordPosition;
	}
	
	@Column(name = "DURATION")
	public BigDecimal getDuration() {
		return duration;
	}
	
	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}
	
}
