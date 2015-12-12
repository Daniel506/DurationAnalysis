package mk.ukim.finki.persistence.service;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import mk.ukim.finki.persistence.dao.ConsonantDao;
import mk.ukim.finki.persistence.dao.PhonemeDao;
import mk.ukim.finki.persistence.dao.VowelDao;
import mk.ukim.finki.persistence.model.Consonant;
import mk.ukim.finki.persistence.model.Phoneme;
import mk.ukim.finki.persistence.model.PhonemeTranscription;
import mk.ukim.finki.persistence.model.PhonemeType;
import mk.ukim.finki.persistence.model.Vowel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Service
public class PhonemeService {
	
	@Autowired
	private ConsonantDao consonantDao;
	
	@Autowired
	private VowelDao vowelDao;
	
	@Autowired
	private PhonemeDao phonemeDao;

	private Document readAllophones() {
		
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream allophonesXML = classLoader.getResource("language/allophones_mk.xml").openStream();
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    
			DocumentBuilder dBuilder;
      dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(allophonesXML);
      
      return doc;
      
    } catch (SAXException | ParserConfigurationException | IOException e) {
        e.printStackTrace();
        return null;
    }
	}
	
	public void insertAllophonesToDB() {
		Document allophones = readAllophones();
		allophones.getDocumentElement().normalize();

		NodeList nodeList = allophones.getElementsByTagName("vowel");
		savePhonemesToPersistenceModel(nodeList, PhonemeType.VOWEL);
    saveVowelsToPersistenceModel(nodeList);
    
    nodeList = allophones.getElementsByTagName("consonant");
		savePhonemesToPersistenceModel(nodeList, PhonemeType.CONSONANT);
    saveConsonantsToPersistenceModel(nodeList);
	}

	private void savePhonemesToPersistenceModel(NodeList nodeList, PhonemeType phonemeType) {
		for (int i = 0; i < nodeList.getLength(); i++) {
    	Phoneme phonemeToSave = getPhoneme(nodeList.item(i), phonemeType.name());
			phonemeDao.save(phonemeToSave);
    }
    
    System.out.println("Insertion of phonemes finished!");
	  
  }

	private Phoneme getPhoneme(Node item, String type) {
		Element vowelElement = (Element) item;
		
	  String id = vowelElement.getAttribute("ph");
	  String transcription = PhonemeTranscription.getTranscriptionFromValue(id);
	  
	  Phoneme phoneme = new Phoneme();
	  phoneme.setId(id);
	  phoneme.setTranscription(transcription);
	  phoneme.setType(type);
	  
	  return phoneme;
  }

	private void saveVowelsToPersistenceModel(NodeList nodeList) {

    for (int i = 0; i < nodeList.getLength(); i++) {
    	Vowel vowelToSave = getVowel(nodeList.item(i));
			vowelDao.save(vowelToSave);
    }
    
    System.out.println("Insertion of vowels finished!");
  }
	
	private Vowel getVowel(Node item) {
	  
		Element vowelElement = (Element) item;
		
	  String id = vowelElement.getAttribute("ph");
		String vrnd = vowelElement.getAttribute("vrnd");
		int vfront = Integer.valueOf(vowelElement.getAttribute("vfront"));
		int vheight = Integer.valueOf(vowelElement.getAttribute("vheight"));
		String vlng = vowelElement.getAttribute("vlng");
		
		Vowel vowel = new Vowel();
		vowel.setId(id);
		vowel.setVfront(vfront);
		vowel.setVheight(vheight);
		vowel.setVlng(vlng);
		vowel.setVround(Boolean.FALSE);
		
		if (vrnd.equals("+")) {
			vowel.setVround(Boolean.TRUE);
		}
		
	  return vowel;
  }

	private void saveConsonantsToPersistenceModel(NodeList nodeList) {

    for (int i = 0; i < nodeList.getLength(); i++) {
    	consonantDao.save(getConsonant(nodeList.item(i)));
    }
    
    System.out.println("Insertion of consonants finished!");
  }

	private Consonant getConsonant(Node item) {
		Element consonantElement = (Element) item;
		
	  String id = consonantElement.getAttribute("ph");
		String cvox = consonantElement.getAttribute("cvox");
		String cplace = consonantElement.getAttribute("cplace");
		String ctype = consonantElement.getAttribute("ctype");
		
		Consonant consonant = new Consonant();
		consonant.setId(id);
		consonant.setCplace(cplace);
		consonant.setCtype(ctype);
		consonant.setCvox(Boolean.FALSE);

		if (cvox.equals("+")) {
			consonant.setCvox(Boolean.TRUE);
		}
		
	  return consonant;
  }

}
