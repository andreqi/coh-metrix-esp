package pucp.edu.test;

import static org.junit.Assert.*;

import org.junit.Test;

import pucp.edu.cohmetrixesp.metrics.MetricsEngine;

public class MetricsEngineTest {

	@Test
	public void test() {
		MetricsEngine engine = MetricsEngine.getInstance();
		engine.process(
				"/home/andre/Dropbox/Coh-Metrix-Esp/Entregables/Tesis 2/Corpus/Cuentos/txt",
				"/home/andre/Desktop/tmp/");
	}

}
