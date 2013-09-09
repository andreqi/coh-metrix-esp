package pucp.edu.test;

import static org.junit.Assert.*;

import org.junit.Test;

import pucp.edu.cohmetrixesp.metrics.CohSyllable;
import pucp.edu.cohmetrixesp.metrics.SyllableMatcher;

public class CohSylableTest {

	@Test
	public void conversionTest() {
		assertEquals("mala representancion basica", "E",
				CohSyllable.getCVRepresentation(""));
		assertEquals("no distingue entre mayusculas y minusculas", "CVCCVCVCVE",
				CohSyllable.getCVRepresentation("Cerradura"));
		assertEquals("no detecta las tildes", "CVAE",
				CohSyllable.getCVRepresentation("lió"));
	}
	@Test
	public void syllableCountingTest() {
			assertEquals("mala representancion basica", 4,
				SyllableMatcher.getNumberOfSyllable(CohSyllable.getCVRepresentation("cerradura")));
			assertEquals("mala representancion basica", 3,
				SyllableMatcher.getNumberOfSyllable(CohSyllable.getCVRepresentation("ángela")));
			assertEquals("mala representancion basica", 4,
				SyllableMatcher.getNumberOfSyllable(CohSyllable.getCVRepresentation("cerradura")));
	}

}
