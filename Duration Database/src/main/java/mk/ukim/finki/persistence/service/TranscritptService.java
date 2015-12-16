package mk.ukim.finki.persistence.service;

import org.springframework.stereotype.Service;

import mk.ukim.finki.persistence.model.PhonemeTranscription;

@Service
public class TranscritptService {

	public String transcript(String string) {
		string = string.toLowerCase();
		for (PhonemeTranscription phoneme : PhonemeTranscription.values()) {
			string = string.replace(phoneme.getTranscription(), phoneme.name());
		}
		return string;
	}
}
