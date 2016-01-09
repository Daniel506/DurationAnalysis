package mk.ukim.finki.persistence.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mk.ukim.finki.persistence.dao.DurationDao;
import mk.ukim.finki.persistence.model.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhonemeDurationService {

	@Autowired
	private DurationDao durationDao;
	
	private Map<Integer, Double> phonemeDurations = new LinkedHashMap<Integer, Double>(); 
	private List<Integer> pauses = new ArrayList<Integer>();
	
	public void calculateDurations() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		
		InputStream folderStream = classLoader.getResource("language/lab").openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(folderStream));
		String line;
		
		int sentenceIndex = 0;
		while ((line = reader.readLine()) != null) {
        String fileName = line;
				readLabFile("language/lab/" + fileName, sentenceIndex++);
        calculateDurationsInSentence(fileName.replace(".lab", ""));
        phonemeDurations.clear();
        pauses.clear();
    }
	}
	
	private void calculateDurationsInSentence(String sentenceId) {
		Double previousEndTime = null;
		List<Duration> durationPhonemes = durationDao.findDurations(sentenceId);
		System.out.println("Processing: " + sentenceId);
		
		int i = 0;
		for(Map.Entry<Integer, Double> phoneme : phonemeDurations.entrySet()) {
			
			Double phonemeEndTime = phoneme.getValue();
			if (pauses.contains(phoneme.getKey())) {
				previousEndTime = phonemeEndTime;
				continue;
			}
			
			Duration duration = durationPhonemes.get(i++);
			if (previousEndTime == null) {
				duration.setDuration(BigDecimal.valueOf(phonemeEndTime));
			} else {
				Double durationTime = phonemeEndTime.doubleValue() - previousEndTime.doubleValue();
				duration.setDuration(BigDecimal.valueOf(durationTime));
			}
			previousEndTime = phonemeEndTime;
			durationDao.update(duration);
			
		}
	}
	
	private void readLabFile(String location, int index) {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream wordTranscriptions = classLoader.getResource(location).openStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(wordTranscriptions));
      String line;
      
      int i = 0;
      while ((line = reader.readLine()) != null) {
      	
      	if (line.contains("#")) continue;
      	String[] labValues = line.split(" ");
      	
      	if (labValues[2].equals("_")) {
      		pauses.add(i);
      	}
      	
        phonemeDurations.put(i++, Double.parseDouble(labValues[0]));
      }
      reader.close();
      
		} catch (IOException e) {
	    e.printStackTrace();
    }
	}

}
