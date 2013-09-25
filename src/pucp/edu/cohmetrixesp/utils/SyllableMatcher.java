package pucp.edu.cohmetrixesp.utils;

public class SyllableMatcher {
	static SyllableMatcher instance;
	/* TODO: no separar consonantes especiales como br */
	static String[] V = { "VC", "V", "VV", "VVC", "AC", "A", "AV", "AVC"  };
	static String[][] EV = {  { "E", "CV", "CCV" },{ "E", "CV", "CCV", "A" },
			{ "E", "CV", "CCV" }, { "E", "CV", "CCV" },
			{ "E", "CV", "CCV" },{ "E", "CV", "CCV", "A" }, 
			{ "E", "CV", "CCV" }, { "E", "CV", "CCV" }
	};

	static String[] CV = { "CV", "CVC", "CVCC", "CVV", "CVVC", "CVVV", "CVVVC" };
	static String[][] ECV = { { "E", "CV", "CCV" }, { "E", "CV", "CCV" },
			{ "E", "CV" }, { "E", "CV", "CCV" }, { "E", "CV", "CCV" },
			{ "E", "CV", "CCV" }, { "E", "CV", "CCV" } };

	static String[] CCV = { "CCV", "CCVC", "CCVCC", "CCVV", "CCVVC" };
	static String[][] ECCV = { { "E", "CV", "CCV" }, { "E", "CV", "CCV" },
			{ "E", "CV" }, { "E", "CV", "CCV" }, { "E", "CV", "CCV" } };

	public static SyllableMatcher getInstance() {
		if (instance == null)
			return new SyllableMatcher();
		return instance;
	}

	private SyllableMatcher() {
	}

	public String splitSyllable(String pat, String inicio, String[] pEnd) {
		String regexIni = "^" + inicio+".*";
		if (!pat.matches(regexIni) && !pat.matches(regexIni.replace('V', 'A')))
			return null;
		
		String tPat = pat.substring(inicio.length());
		for (String p : pEnd) {
			regexIni = "^" + p+".*";
			if (tPat.matches(regexIni)){
				return tPat;
			}
			
			regexIni = "^" + p+".*";
			regexIni = regexIni.replace('V', 'A');
			if (tPat.matches(regexIni)){
				return tPat;
			}

		}
		return null;
	}

	
	public static int getNumberOfSyllable(String CVRepresentation) {
		String pi = new String(CVRepresentation);
		int ans = 0, into = 0;
		while (pi.compareTo("E") != 0) {
			into = 0;
			switch (pi.charAt(0)) {
			case 'V':
			case 'A':
				for (int i = 0; i < V.length; i++) {
					String split = getInstance().splitSyllable(pi, V[i], EV[i]);
					if (split != null) {
						into++;
						pi = split;
						break;
					}
				}
				break;
			case 'C':
				if (pi.charAt(1) == 'V' || pi.charAt(1) == 'A') {
					for (int i = 0; i < CV.length; i++) {
						String split = getInstance().splitSyllable(pi, CV[i],
								ECV[i]);
						if (split != null) {
							into++;
							pi = split;
							break;
						}
					}
					break;
				}	
				for (int i = 0; i < CCV.length; i++) {
					String split = getInstance().splitSyllable(pi, CCV[i],
							ECCV[i]);
					if (split != null) {
						into++;
						pi = split;
						break;
					}
				}
				break;
			}
			if (into == 0) {
				// si no cumple ninguna regla, sacar la primera letra como silaba
				pi = pi.substring(1);
			}
			ans++;
		}
		return ans;
	}
}
