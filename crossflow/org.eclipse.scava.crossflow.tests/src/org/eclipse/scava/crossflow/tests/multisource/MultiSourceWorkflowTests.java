package org.eclipse.scava.crossflow.tests.multisource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class MultiSourceWorkflowTests extends WorkflowTests {

	@Test
	public void testMultiSource() throws Exception {

		MSWorkflow wf = new MSWorkflow();
		wf.setInstanceId("MSWF");
		wf.run();

		waitFor(wf);

		// System.out.println(wf.getSink().getResults());

		List<Entry<String, Boolean>> sourceOrder = wf.getSink().getResults();

		List<Entry<String, Boolean>> ascorder = sourceOrder.stream()
				.sorted((s1, s2) -> s1.getKey().compareTo(s2.getKey())).collect(Collectors.toList());
		List<Entry<String, Boolean>> descorder = sourceOrder.stream()
				.sorted((s1, s2) -> s2.getKey().compareTo(s1.getKey())).collect(Collectors.toList());

		// System.out.println(ascorder);
		// System.out.println(descorder);

		// ensure that the results are not perfectly ordered (aka the sources run in a
		// parallel manner)
		assertNotEquals(sourceOrder, ascorder);
		assertNotEquals(sourceOrder, descorder);

		// ensure that all non-terminal elements (for each source) are returned is while
		// the source is still not terminated (aka the sources are non-blocking)
		for (int i = 0; i < sourceOrder.size() - 2; i = i + 2) {
			assertFalse(sourceOrder.get(i).getValue());
			// System.out.println(i + " " + sourceOrder.get(i));
			assertFalse(sourceOrder.get(i + 1).getValue());
			// System.out.println(i + 1 + " " + sourceOrder.get(i + 1));
		}
		// check terminal elements are returned after source is terminated
		int size = sourceOrder.size();
		assertTrue(sourceOrder.get(size - 1).getValue());
		// System.out.println((size - 1) + " " + sourceOrder.get(size - 1));
		assertTrue(sourceOrder.get(size - 2).getValue());
		// System.out.println((size - 2) + " " + sourceOrder.get(size - 1));
	}

}
