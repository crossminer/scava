package org.eclipse.scava.plugin;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.eclipse.scava.plugin.usermonitoring.gremlin.GremlinAdapter;
import org.eclipse.scava.plugin.usermonitoring.gremlin.GremlinUtils;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MetricCalculationTest {
	static GremlinAdapter gremlinAdapter;
	static GremlinUtils gremlinUtils;
	static String testPath = "F:\\Work\\XM\\orientdb-tp3-3.0.11\\databases\\OrientDataBase";
	static String databasePath = System.getProperty("user.dir") + File.separator + "OrientDataBase";

	@BeforeClass
	public static void initDatabase() {

		gremlinAdapter = new GremlinAdapter("plocal:" + testPath);
		gremlinUtils = gremlinAdapter.getGremlinUtils();

		Vertex vertex = gremlinUtils.getFirstEvent();
		// System.out.println(vertex);

		fillResources(gremlinUtils.getGraphTraversalSource());

	}

	public static boolean equalsVertex(Vertex first, Vertex second) {

		Iterator<VertexProperty<Object>> firstProperties = first.properties();
		Iterator<VertexProperty<Object>> secondProperties = second.properties();

		while (firstProperties.hasNext() || secondProperties.hasNext()) {

			String firstKey = firstProperties.next().key();
			String secondKey = secondProperties.next().key();

			if (!firstKey.equals(secondKey)) {
				return false;
			}

			VertexProperty<Object> firstProperty = first.property(firstKey);
			VertexProperty<Object> secondProperty = second.property(secondKey);

			if (!firstProperty.value().toString().equals(secondProperty.value().toString())) {
				return false;
			}

		}

		return true;

	}

	public static void initEvents(GraphTraversalSource g) {
		Vertex v = g.addV("event").property("TimeStamp", new Date(1556013600000l)).property("VertexType", "UploadEvent").next();
		System.out.println(v.id());
		g.addV("event").property("Type", "DEACTIVATED").property("TimeStamp", new Date(1556013945558l)).property("VertexType", "WindowEvent");
		g.addV("event").property("Type", "CLOSED").property("Title", "Outline").property("TimeStamp", new Date(1556013947564l)).property("VertexType", "PartEvent");
		g.addV("event").property("TimeStamp", new Date(1556013948095l)).property("VertexType", "EclipseCloseEvent");
		g.addV("event").property("Type", "OPENED").property("Title", "Search").property("TimeStamp", new Date(1556013941439l)).property("VertexType", "PartEvent");
		g.addV("event").property("Type", "ACTIVATED").property("TimeStamp", new Date(1556013947349l)).property("VertexType", "WindowEvent");
		g.addV("event").property("Type", "CLOSED").property("Title", "Demo.java").property("TimeStamp", new Date(1556013947595l)).property("VertexType", "PartEvent");
		g.addV("event").property("Type", "OPENED").property("TimeStamp", new Date(1556013941444l)).property("VertexType", "WindowEvent");
		g.addV("event").property("Type", "CLOSED").property("Title", "Package Explorer").property("TimeStamp", new Date(1556013947505l)).property("VertexType", "PartEvent");
		g.addV("event").property("Type", "CLOSED").property("Title", "Search").property("TimeStamp", new Date(1556013947652l)).property("VertexType", "PartEvent");
		g.addV("event").property("Remains", "").property("TimeStamp", new Date(1556013941449l)).property("VertexType", "UploadEvent");
		g.addV("event").property("Type", "CLOSED").property("Title", "Task List").property("TimeStamp", new Date(1556013947528l)).property("VertexType", "PartEvent");
		g.addV("event").property("Type", "CLOSED").property("TimeStamp", new Date(1556013947764l)).property("VertexType", "WindowEvent");
	}

	public static void initResource(GraphTraversalSource g) {
		// Parts
		g.addV("resource").property("Title", "Search").property("VertexType", "Part").next();
		g.addV("resource").property("Title", "Package Explorer").property("VertexType", "Part");
		g.addV("resource").property("Title", "Task List").property("VertexType", "Part");
		g.addV("resource").property("Title", "Demo.java").property("VertexType", "Part");
		g.addV("resource").property("Title", "Outline").property("VertexType", "Part");
		// Files
		g.addV("resource").property("Title", ".Sorter.java").property("VertexType", "File");
		g.addV("resource").property("Title", ".testClass.java").property("VertexType", "File");
		g.addV("resource").property("Title", "testebb.asdasdasd.java").property("VertexType", "File");
		g.addV("resource").property("Title", ".Demo.java").property("VertexType", "File");
		g.addV("resource").property("Title", "testebb.asdasdasdasd.java").property("VertexType", "File");
		// Window
		g.addV("resource").property("Title", "Workspace").property("VertexType", "Window");
		// Projects
		g.addV("resource").property("ProjectName", "CrossminerDemo2017Sept").property("ProjectId", "https://github.com/Yuyupo/Tudasbazis").property("VertexType", "Project");
		g.addV("resource").property("ProjectName", "testebb").property("ProjectId", "testgithub").property("VertexType", "Project");
		// Packages
		g.addV("resource").property("PackageName", "testebb").property("VertexType", "Package");
		g.addV("resource").property("PackageName", "").property("VertexType", "Package");
		g.addV("resource").property("PackageName", "").property("VertexType", "Package");

	}

	public static void fillResources(GraphTraversalSource g) {

		initResource(g);

		String insertString = "g.addV(#)";

		g.V().forEachRemaining(v -> {
			// System.out.println(v.label());
			Iterator<VertexProperty<Object>> properties = v.properties();
			String replace = insertString.replace("#", "\"" + v.label() + "\"");

			while (properties.hasNext()) {

				String key = properties.next().key();
				if (key.equals("TimeStamp")) {

					Date date = (Date) v.property(key).value();

					replace += ".property(\"" + key + "\", new Date(" + date.getTime() + "))";
					continue;
				}

				replace += ".property(\"" + key + "\",\"" + v.property(key).value() + "\")";

				// System.out.println(insertString);
				// System.out.println(key);
				// System.out.println(v.property(key).value());

			}

			if (v.label().equals("event")) {
				// System.out.println(replace);
			}

			Iterator<Edge> edges = v.edges(Direction.BOTH, EdgeType.NEXT, EdgeType.CONTAIN, EdgeType.RELATED_PROJECT, EdgeType.SUBJECT_RESOURCE, EdgeType.CHANGED_FROM, EdgeType.CHANGED_TO);

			while (edges.hasNext()) {

				Edge next = edges.next();
			//	System.out.println(next.inVertex());
			//	System.out.println(next.outVertex());
			//	System.out.println(next.label());
			//	System.out.println(next.outVertex().id());
				
			}

		});

		String label = null;

	}

	@Test
	public void fail() {

		System.out.println("first test");
		assertEquals(5l, 5l);
	}

	@AfterClass
	public static void removeDatabase() {

		try {
			gremlinAdapter.closeDatabaseConnection();
			 deleteDirectory(new File(databasePath));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("End");
	}

	public static boolean deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectory(files[i]);
					} else {
						boolean succes = files[i].delete();
						// System.out.println("Delete: " + files[i].getAbsolutePath() + " " + succes);

					}
				}
			}
		}
		return (directory.delete());
	}

}
