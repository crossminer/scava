package org.crossmeter.plugin.usermonitoring.database;

import java.io.File;
import java.io.IOException;

import org.crossmeter.plugin.usermonitoring.event.EventManager;
import org.crossmeter.plugin.usermonitoring.event.EventType;
import org.crossmeter.plugin.usermonitoring.event.IEvent;
import org.crossmeter.plugin.usermonitoring.event.document.DocumentEvent;
import org.crossmeter.plugin.usermonitoring.event.part.PartEvent;
import org.crossmeter.plugin.usermonitoring.event.window.WindowEvent;
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
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;


public class DatabaseManager implements IDatabaseManager {

	private static final File databaseDirectory = new File(
			(System.getProperty("user.home") + "/Desktop/" + "neo4j/data/databases/graph.db"));
	private GraphDatabaseService graphDb;
	private Node lastNode;
	private boolean forceDelete = false;
	private boolean tool = true;

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

	public DatabaseManager() {
		
		forceDelete = messageDialog();
		
		try {

			initializeDatabase();
			
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void insertEventNode(IEvent event) {

		try (Transaction tx = graphDb.beginTx()) {

			Node node = event.toNode(graphDb);

			if (lastNode != null) {
				lastNode.createRelationshipTo(node, DatabaseProperties.RelationTypes.next);
			}

			lastNode = node;
			

			tx.success();
		}

		if (EventManager.getEditor() != null) {
			nodeInfo();
		}
	}

	private void initializeDatabase() throws IOException {
		System.out.println("Create database...");

		if (forceDelete) {
			FileUtils.deleteRecursively(databaseDirectory);
		}
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(databaseDirectory);
		getLastNode();
		
		
	}

	private void getLastNode() {
		
		if (forceDelete) {
			lastNode = null;
		}else {
			
			
			try (Transaction tx = graphDb.beginTx()) {
				ResourceIterable<Node> allNodes = graphDb.getAllNodes();
				for (Node node : allNodes) {
					lastNode = node;
					
				}

				tx.success();
			}
			
		}
		
		
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
				try (Transaction tx = graphDb.beginTx()) {
					ResourceIterable<Node> allNodes = graphDb.getAllNodes();
					for (Node node : allNodes) {
						i++;
					}
					tx.success();
				}
				
				
				c.setText("Nodes: " + i + " Size: " + Math.round(folderSize(databaseDirectory) / 1024.0) + " KB");

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
		
		return MessageDialog.openQuestion(shell, "Delete" , "Would you like to delete previous database entries?");
	}
	
	private void shutDown() {	
		System.out.println("Shutting down database ...");
		graphDb.shutdown();
	}

}
