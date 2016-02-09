package mk.ukim.finki.service.report;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import mk.ukim.finki.persistence.dao.DurationDao;
import mk.ukim.finki.persistence.model.Duration;
import mk.ukim.finki.persistence.model.PhonemeTranscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportService {

	@Autowired
	private DurationDao durationDao;
	
	private String exportPhonemeDuration(String phoneme) {
		List<Duration> durations = durationDao.findDurationByPhoneme(phoneme);
		StringBuilder builder = new StringBuilder();
		builder.append(phoneme).append(",");
		
		for (Duration duration : durations) {
			builder.append(duration.getDuration()).append(",");
		}
		
		return builder.toString();
	}
	
	public void exportPhonemeDurations() {
		PrintWriter writer = null;
    try {
	    writer = new PrintWriter("export.csv", "UTF-8");
			for (PhonemeTranscription phoneme : PhonemeTranscription.values()) {
				System.out.println("Processing phoneme: " + phoneme.name());
				String phonemeDuration = exportPhonemeDuration(phoneme.name());
				writer.println(phonemeDuration);
			}
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
    	e.printStackTrace();
    } finally {
    	writer.close();
    }
		
	}
}
