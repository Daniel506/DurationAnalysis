package mk.ukim.finki.persistence.model;

public enum PhonemeTranscription {
	a("а"),
	e("е"),
	i("и"),
	o("о"),
	u("у"),
	b("б"),
	v("в"),
	g("г"), 
	d("д"), 
	gj("ѓ"), 
	zh("ж"), 
	z("з"), 
	dz("ѕ"), 
	j("ј"), 
	k("к"), 
	l("л"), 
	lj("љ"), 
	m("м"), 
	n("н"), 
	nj("њ"), 
	p("п"), 
	r("р"), 
	s("с"), 
	t("т"), 
	kj("ќ"), 
	f("ф"), 
	h("х"), 
	c("ц"), 
	ch("ч"), 
	dj("џ"),
	sh("ш");
	
	private String transcription;

	private PhonemeTranscription(String value) {
		this.transcription = value;
  }

	public String getTranscription() {
		return transcription;
	}

	public static String getTranscriptionFromValue(String value) {
		for (PhonemeTranscription phoneme : values()) {
			if (phoneme.name().equals(value)) {
				return phoneme.getTranscription();
			}
		}
		
		return null;
	}
	
	public static boolean isComplexPhoneme(String value) {
		
		if (value.equals(ch.name()) || value.equals(dj.name()) || value.equals(dz.name()) || value.equals(gj.name())
				|| value.equals(kj.name()) || value.equals(lj.name()) || value.equals(nj.name()) || value.equals(sh.name()) 
				|| value.equals(zh.name())) {
			return true;
		}
				
		return false;
	}
}
