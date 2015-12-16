package mk.ukim.finki.persistence.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.persistence.dao.SyllableDao;
import mk.ukim.finki.persistence.dao.WordDao;
import mk.ukim.finki.persistence.model.Syllable;
import mk.ukim.finki.persistence.model.Word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordService {
	
	@Autowired
	private SyllableDao syllableDao;
	
	@Autowired
	private WordDao wordDao;
	
	@Autowired
	private SentenceService sentenceService;
	
	@Autowired
	private TranscritptService transcritptService;
	
	private List<Syllable> syllables = new ArrayList<Syllable>();
	private List<String> syllableIds = new ArrayList<String>();
	private List<Word> words = new ArrayList<Word>();
	private List<String> wordIds = new ArrayList<String>();
	
	public void processWords() {
		List<String> words = readWords();
		String sentences = sentenceService.readSentences();
		sentences = transcritptService.transcript(sentences);
		for (String word : words) {
			word = word.replace("'","");
		  String wordId = word.replace("-", "");

		  if (sentences.contains(wordId)) {
		  	String [] wordSyllables = word.split("-");
			
				processSyllables(wordSyllables);
				processWords(word);
		  }
		}
	}

	private void processWords(String wordId) {
		System.out.println("Processing word: " + wordId);
	  Word word = getWord(wordId);
	  if (!wordIds.contains(word.getId())) {
	  	words.add(word);
	  	wordIds.add(word.getId());
	  	wordDao.save(word);
	  }
  }
	
	private Word getWord(String wordSyllables) {
	  Word word = new Word();
	  word.setSyllables(wordSyllables);
	  String wordId = wordSyllables.replace("-", "");
	  
	  word.setId(wordId);
	  word.setLength(wordId.length());
	  return word;
  }

	private List<String> readWords () {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream wordTranscriptions = classLoader.getResource("language/word-transcription-final.txt").openStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(wordTranscriptions));
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      List<String> words = new ArrayList<String>();
      
      while ((line = reader.readLine()) != null) {
          stringBuilder.append(line);
          String [] word = line.split(" ");
          words.add(word[1]);
      }
      reader.close();
      return words;
    
		} catch (IOException e) {
	    e.printStackTrace();
    }
		
		return null;
	}
	

	private void processSyllables(String [] wordSyllables) {
	  for (String wordSyllable : wordSyllables) {
	  	Syllable syllable = getSyllable(wordSyllable);
	  	if (!syllableIds.contains(syllable.getId())) {
	  		syllables.add(syllable);
	  		syllableIds.add(syllable.getId());
				System.out.println("inserting syllable " + syllable.getId() );
				syllableDao.save(syllable);
	  	}
	  }
  }

	private Syllable getSyllable(String syllableId) {
	  Syllable syllable = new Syllable();
	  syllable.setId(syllableId);
	  syllable.setPattern(getPattern(syllableId));
	  syllable.setLength(syllableId.length());
	  
	  return syllable;
  }

	private String getPattern(String syllable) {
		StringBuilder pattern = new StringBuilder();
	  char [] phonemes = syllable.toCharArray();
	  for (char phoneme : phonemes) {
	  	if (isVowel(phoneme)) {
	  		pattern.append("V");
	  	} else {
	  		pattern.append("C");
	  	}
	  }
	  return pattern.toString();
  }

	private boolean isVowel(char phoneme) {
		char [] vowels = {'a', 'e', 'i', 'o', 'u'};
		
		for (char vowel : vowels) {
			if (phoneme == vowel) {
				return true;
			}
		}
		
	  return false;
  }
}
