package mk.ukim.finki.persistence.service;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.persistence.dao.DurationDao;
import mk.ukim.finki.persistence.dao.WordDao;
import mk.ukim.finki.persistence.model.Duration;
import mk.ukim.finki.persistence.model.PhonemeTranscription;
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
		for (int i = 0; i < 178; i++) {
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
		
		List<String> wordArray = getPhonemesArray(word);
		int size = wordArray.size();
		int syllableIndex = 0;
		int j = 0;
		
		
		for (int i = 0; i < size; i++) {
			
			String syllableId = null;
			String precedent = null;
			String successor = null;
			
			String phoneme = wordArray.get(i);
					
			if (i > 0) {
				precedent = wordArray.get(i - 1);
			}
			
			int last = size - 1;
			if (i < last) {
				successor = wordArray.get(i + 1);
			}

			String wordSyllables = getWordSyllables(word);
			String [] syllables = wordSyllables.split("-");
			
			syllableId = syllables[j];
			if (i >= (syllables[j].length() + syllableIndex - 1)) {
				syllableIndex += syllables[j++].length();
				if (getPhonemesArray(syllables[j - 1]).size() != syllables[j - 1].length()) {
					syllableId = syllables[j];
				}
			}
			

			int phonemePosition = sentenceOrder * 100000 + wordPosition * 100 + i;
			vowelDurations.add(createDurationModel(phoneme, phonemePosition, precedent, successor, syllableId));
		}
		
		return vowelDurations;
  }

	private List<String> getPhonemesArray(String word) {
		char[] wordCharArray = word.toCharArray();
		List<String> phonemesList = new ArrayList<String>();
		
		int i = 0;
		while (i < wordCharArray.length) {
			String phoneme = null;
			if (i < wordCharArray.length - 1) {
				phoneme = String.valueOf(wordCharArray[i]) + String.valueOf(wordCharArray[i+1]);
			} else {
				phoneme = String.valueOf(wordCharArray[i]);
			}
			boolean complexPhoneme = PhonemeTranscription.isComplexPhoneme(phoneme);
			
			if (word.startsWith("iljad") || word.startsWith("odja")) {
				complexPhoneme = false;
			}
			
			if (complexPhoneme) {
				phonemesList.add(phoneme);
				i += 2;
			} else {
				phonemesList.add(String.valueOf(wordCharArray[i]));
				i++;
			}
		}
	  return phonemesList;
  }

	private Duration createDurationModel(
			String phoneme, 
			Integer position, 
			String precedent, 
			String successor, 
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
