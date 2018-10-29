package org.eclipse.scava.plugin.usermonitoring.metric.adapter;

import static org.junit.Assert.assertEquals;

import org.eclipse.scava.plugin.usermonitoring.event.document.DocumentEvent;
import org.eclipse.scava.plugin.usermonitoring.event.eclipse.EclipseCloseEvent;
import org.eclipse.scava.plugin.usermonitoring.event.scava.ScavaLibraryUsageEvent;
import org.eclipse.scava.plugin.usermonitoring.event.scava.ScavaSearchSuccesEvent;
import org.junit.BeforeClass;
import org.junit.Test;

public class GremlinAdapterTest {

	static GremlinAdapter adapter;

	@BeforeClass
	public static void connect() {
		adapter = new GremlinAdapter();
		try {
			adapter.dropDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter.insertVertex(new DocumentEvent(null, "Test"));
	}

	@Test
	public void insertDocumentEventVertexAddedToTheDatabaseTest() {

		long edgeCount = adapter.getVertexCount();
		adapter.insertVertex(new DocumentEvent(null, "Test"));
		long edgeCount2 = adapter.getVertexCount();

		assertEquals(edgeCount + 1, edgeCount2);

	}

	@Test
	public void insertScavaLibraryUsageEventVertexAddedToTheDatabaseTest() {

		long edgeCount = adapter.getVertexCount();
		adapter.insertVertex(new ScavaLibraryUsageEvent());
		long edgeCount2 = adapter.getVertexCount();

		assertEquals(edgeCount + 1, edgeCount2);
	}

	@Test
	public void insertScavaSearchSuccesEventVertexAddedToTheDatabaseTest() {

		long edgeCount = adapter.getVertexCount();
		adapter.insertVertex(new ScavaSearchSuccesEvent());
		long edgeCount2 = adapter.getVertexCount();

		assertEquals(edgeCount + 1, edgeCount2);
	}

	@Test
	public void insertEclipseCloseEventVertexAddedToTheDatabaseTest() {

		long edgeCount = adapter.getVertexCount();
		adapter.insertVertex(new EclipseCloseEvent());
		long edgeCount2 = adapter.getVertexCount();

		assertEquals(edgeCount + 1, edgeCount2);
	}

}
