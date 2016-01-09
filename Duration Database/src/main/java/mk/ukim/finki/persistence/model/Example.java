package mk.ukim.finki.persistence.model;

import java.io.IOException;

import mk.ukim.finki.persistence.service.DurationService;
import mk.ukim.finki.persistence.service.PhonemeDurationService;
import mk.ukim.finki.persistence.service.PhonemeService;
import mk.ukim.finki.persistence.service.SentenceService;
import mk.ukim.finki.persistence.service.WordService;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Example {

	public static void main(String[] args) {
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml")) {
//			PhonemeService phonemeService = context.getBean(PhonemeService.class);
//			phonemeService.insertAllophonesToDB();
//			WordService wordService = context.getBean(WordService.class);
//			wordService.processWords();
//			SentenceService sentenceService = context.getBean(SentenceService.class);
//			sentenceService.processSentences();
//			DurationService durationService = context.getBean(DurationService.class);
//			durationService.processSentences();
			PhonemeDurationService phonemeDurationService = context.getBean(PhonemeDurationService.class);
			try {
	      phonemeDurationService.calculateDurations();
      } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
		}
	}


}
