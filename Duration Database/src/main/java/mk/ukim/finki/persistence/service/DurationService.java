package mk.ukim.finki.persistence.service;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.persistence.dao.DurationDao;
import mk.ukim.finki.persistence.dao.WordDao;
import mk.ukim.finki.persistence.model.Duration;
import mk.ukim.finki.persistence.model.Sentence;
import mk.ukim.finki.persistence.model.Word;
import mk.ukim.finki.persistence.model.WordPosition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DurationService {
	
	@Autowired
	private SentenceService sentenceService;
	
	@Autowired
	private WordDao wordDao;
	
	@Autowired
	private DurationDao durationDao;
	
	public void processSentences() {
		sentenceService.readSentences();
		List<Sentence> sentences = sentenceService.getSentences();
		for (int i = 0; i < sentences.size(); i++) {
			Sentence sentence = sentences.get(i);
			System.out.println("Processing: " + sentence.getId());
			processSentence(sentence, i);
		}
	}

	private void processSentence(Sentence sentence, int order) {
		String sentenceString = sentence.getSentence();
		sentenceString = filterSentence(sentenceString);
		String[] words = sentenceString.split(" ");
		int length = words.length;
		int last = length - 1;
		for (int i = 0; i < length; i++) {
			WordPosition position = getPosition(last, i);
			List<Duration> durations = processWord(words[i], i, order);
			for (Duration duration : durations) {
				duration.setWordPosition(position);
				duration.setSentenceId(sentence.getId());
				durationDao.save(duration);
			}
		}
  }

	private String filterSentence(String sentence) {
		sentence = sentence.replace(",", "");
		sentence = sentence.replace(".", "");
		sentence = sentence.replace("?", "");
		sentence = sentence.replace("!", "");
		sentence = sentence.replace("-", " ");
	  return sentence;
  }

	private WordPosition getPosition(int last, int index) {
	  WordPosition position = null;
	  
	  if (index == 0) {
	  	position = WordPosition.INITIAL;
	  } else if (index == last){
	  	position = WordPosition.FINAL;
	  } else {
	  	position = WordPosition.MIDDLE;
	  }
	  return position;
  }

	private List<Duration> processWord(String word, int wordPosition, int sentenceOrder) {
		List<Duration> vowelDurations = new ArrayList<Duration>();
		
		char[] wordArray = word.toCharArray();
		int length = wordArray.length;
		int syllableIndex = 0;
		int j = 0;
		
		String syllableId = null;
		Character precedent = null;
		Character successor = null;
		
		for (int i = 0; i < length; i++) {
			
			Character phoneme = wordArray[i];
			if (i > 0) {
				precedent = wordArray[i - 1];
			}
			
			if (i < length - 1) {
				successor = wordArray[i + 1];
			}

			String wordSyllables = getWordSyllables(word);
			String [] syllables = wordSyllables.split("-");
			
			syllableId = syllables[j];
			if (i >= (syllables[j].length() + syllableIndex - 1)) {
				syllableIndex += syllables[j++].length();
			}

			int phonemePosition = sentenceOrder * 100000 + wordPosition * 100 + i;
			vowelDurations.add(createDurationModel(phoneme, phonemePosition, precedent, successor, syllableId));
		}
		
		return vowelDurations;
  }

	private Duration createDurationModel(
			Character phoneme, 
			Integer position, 
			Character precedent, 
			Character successor, 
			String syllableId) {
	  
		Duration duration = new Duration();
	  duration.setPhonemeId(phoneme.toString());
	  duration.setSyllableId(syllableId);
	  duration.setPhonemePosition(position);
	  
	  if (precedent != null) {
	  	duration.setPhonemePrecedent(precedent.toString());
	  }
	  
	  if (successor != null) {
	  	duration.setPhonemeSuccessor(successor.toString());
	  }
	  
	  return duration;
  }

	private String getWordSyllables(String id) {
		Word word = wordDao.find(id);
		if (word == null) {
			System.out.println("Word " + id);
		}
	  return word.getSyllables();
  }
	
	
}
