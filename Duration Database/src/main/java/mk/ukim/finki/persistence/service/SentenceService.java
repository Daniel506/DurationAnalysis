package mk.ukim.finki.persistence.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.persistence.dao.SentenceDao;
import mk.ukim.finki.persistence.model.Sentence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SentenceService {
	
	@Autowired
	private SentenceDao sentenceDao;
	
	@Autowired
	private TranscritptService transcritptService;
	
	private List<Sentence> sentences = new ArrayList<Sentence>();
	
	public String readSentences() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream wordTranscriptions = classLoader.getResource("language/txt.done.data").openStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(wordTranscriptions));
			StringBuilder stringBuilder = new StringBuilder();
      String line;
      
      while ((line = reader.readLine()) != null) {
      	line = line.replace("(", "");
      	line = line.replace(")", "");
      	Sentence sentence = getSentence(line);
        getSentences().add(sentence);
        stringBuilder.append(line);
      }
      reader.close();
      return stringBuilder.toString();
      
		} catch (IOException e) {
	    e.printStackTrace();
    }
		
		return null;
	}
	
	private Sentence getSentence(String line) {
	  line = line.trim();
	  String[] splittedLine = line.split(" ");
	  
	  Sentence sentence = new Sentence();
	  sentence.setId(splittedLine[0]);
	  String content = line.substring(line.indexOf("\""));
	  content = content.replace("\"", "");
	  content = transcritptService.transcript(content);
		sentence.setSentence(content);
	  
	  return sentence;
  }

	public void processSentences() {
		readSentences();
		for (Sentence sentence : getSentences()) {
			System.out.println("Insert sentence: " + sentence.getId());
			sentenceDao.save(sentence);
		}
	}

	public List<Sentence> getSentences() {
	  return sentences;
  }

	public void setSentences(List<Sentence> sentences) {
	  this.sentences = sentences;
  }
}
