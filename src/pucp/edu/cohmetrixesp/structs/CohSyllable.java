package pucp.edu.cohmetrixesp.structs;

import java.text.Normalizer;

public class CohSyllable {
	String vowel = "aeiou";
	static CohSyllable instance;
	static CohSyllable getInstace() {
		if (instance == null) return new CohSyllable();
		return instance;
	} 
	
	private CohSyllable() { }
	
	public boolean isVowel(char c){
		return vowel.contains(""+c);
	}
	
	public boolean isVowelA(char c) {
		String normalized = Normalizer.normalize("" + c,Normalizer.Form.NFD);
		String nxt = normalized.replaceAll("[^\\p{ASCII}]", "");
		return vowel.contains(nxt);
	}
	
	public boolean isConsonant(char c) {
		return !isVowel(c) && ! isVowelA(c);
	}
	 
	static public String getCVRepresentation(String word) {
		StringBuilder builder = new StringBuilder();
		for (int i=0; i<word.length(); i++) {
			char c = word.charAt(i);
			if (getInstace().isVowel(c)) {
				builder.append('V');
			} else if (getInstace().isVowelA(c)) {
				builder.append('A');
			} else {
				builder.append('C');
			}
			
		}
		builder.append('E');
		return builder.toString();
	}
	
}
