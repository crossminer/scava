package org.eclipse.scava.plugin.usermonitoring.database;

import org.eclipse.scava.plugin.usermonitoring.event.EventManager;
import org.eclipse.scava.plugin.usermonitoring.event.IEvent;
import org.eclipse.scava.plugin.usermonitoring.metric.MetricManager;

import java.io.File;
import java.util.List;
import org.apache.tinkerpop.gremlin.orientdb.OrientGraph;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.orientechnologies.orient.core.db.ODatabaseType;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;

public class DatabaseManager implements IDatabaseManager {
	
	private static final String ORIENTDB_PATH = "plocal:"+System.getProperty("user.home")+"\\Desktop\\oridb\\databases";
	private final OrientDB orient;
	private Vertex lastVertex;
	private MetricManager metricManager;
	private GraphTraversalSource graphTraversalSource;
	boolean tool = true;
	
	public DatabaseManager() {

		orient = new OrientDB(ORIENTDB_PATH, OrientDBConfig.defaultConfig());
		
		if (orient.exists("test") && messageDialog()) {
			orient.drop("test");
		}

		boolean notExistBefore = orient.createIfNotExists("test", ODatabaseType.PLOCAL, OrientDBConfig.defaultConfig());
		orient.close();
		
		
		graphTraversalSource = OrientGraph.open(ORIENTDB_PATH+"\\test","admin","admin").traversal();
		if (notExistBefore) {
			System.out.println("Create a new database because it didn't exist before.");
			lastVertex = null;
		} else {
			lastVertex = getLastVertex();
		}
		
	}

	public void insertVertex(IEvent event) {

		System.out.println("Insert event to database: "+event);

		
		
		Vertex vertex = event.toNode(graphTraversalSource);
		if (lastVertex != null) {
			lastVertex.addEdge(EdgeType.NEXT, vertex);
		}
		lastVertex = vertex;
		//nodeInfo();
	}

	public MetricManager getMetricManager() {

		if (metricManager == null) {
			metricManager = new MetricManager(this, graphTraversalSource);
		}
		return metricManager;
	}

	private Vertex getLastVertex() {
		List<Vertex> list = graphTraversalSource.V().hasLabel("event").order().by("TimeStamp",Order.decr).limit(1).toList();
		
		
		return list.isEmpty()? null : list.get(0);

	}

	
	public static long folderSize(File directory) {
		long length = 0;
		for (File file : directory.listFiles()) {
			if (file.isFile())
				length += file.length();
			else
				length += folderSize(file);
		}
		return length;
	}
	
    private void nodeInfo() {
        IStatusLineManager statusLineManager = EventManager.getEditor().getEditorSite().getActionBars()
                .getStatusLineManager();
        IContributionItem com;
        
        com = new ControlContribution("test") {
      
        	@Override
            protected Control createControl(Composite parent) {

                final Button c = new Button(parent, SWT.READ_ONLY | SWT.BORDER);
                c.setEnabled(false);
                int i = 0;
                c.setText("Nodes: " + i + " Size: " + Math.round(5.4 / 1024.0) + " KB");

                return c;
            }

			
        };
        
        if(tool) {
			statusLineManager.add(com);
			this.tool = false;
		}
		

		statusLineManager.update(true);
        
    }

	private boolean messageDialog() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
		Shell shell = activeWorkbenchWindow.getShell();

		return MessageDialog.openQuestion(shell, "Delete", "Would you like to delete previous database entries?");
	}

}
