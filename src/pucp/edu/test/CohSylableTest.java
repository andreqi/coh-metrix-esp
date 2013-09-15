package pucp.edu.test;

import static org.junit.Assert.*;

import org.junit.Test;

import pucp.edu.cohmetrixesp.structs.CohSyllable;
import pucp.edu.cohmetrixesp.utils.SyllableMatcher;

public class CohSylableTest {

	@Test
	public void conversionTest() {
		assertEquals("mala representancion basica", "E",
				CohSyllable.getCVRepresentation(""));
		assertEquals("no distingue entre mayusculas y minusculas", "CVCCVCVCVE",
				CohSyllable.getCVRepresentation("Cerradura"));
		assertEquals("no detecta las tildes", "VCCVCVCAVE",
				CohSyllable.getCVRepresentation("estefan�a"));
	}
	@Test
	public void syllableCountingTest() {
			assertEquals("mala representancion basica", 4,
				SyllableMatcher.getNumberOfSyllable(CohSyllable.getCVRepresentation("cerradura")));
			assertEquals("mala representancion basica", 4,
				SyllableMatcher.getNumberOfSyllable(CohSyllable.getCVRepresentation("cerradura")));
			assertEquals("mala representancion basica", 3,
				SyllableMatcher.getNumberOfSyllable(CohSyllable.getCVRepresentation("augurio")));
			assertEquals("mala representancion basica", 5,
					SyllableMatcher.getNumberOfSyllable(CohSyllable.getCVRepresentation("estefan�a")));
			assertEquals("mala representancion basica", 3,
				SyllableMatcher.getNumberOfSyllable(CohSyllable.getCVRepresentation("�ngela")));
	}

}
