/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.gremlin;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.preferences.Preferences;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.DatabaseManager;

public class GremlinAdapter {

	private String ORIENTDB_PATH;
	private final DatabaseManager databaseManager;

	private final GremlinUtils gremlinUtils;

	public GremlinAdapter() {

		String path = Activator.getDefault().getPreferenceStore().getString(Preferences.DATABASE_PATH);
		ORIENTDB_PATH = path;
		this.databaseManager = new DatabaseManager("plocal:" + ORIENTDB_PATH);

		GraphTraversalSource graphTraversal = databaseManager.getTraversal();
		this.gremlinUtils = new GremlinUtils(graphTraversal);

		//addDatabasePathChangeListener(); 

		System.out.println("=================");
		System.out.println(Activator.getDefault().getPreferenceStore().getDefaultString(Preferences.DATABASE_PATH));
		System.out.println(Activator.getDefault().getPreferenceStore().getString(Preferences.DATABASE_PATH));
		System.out.println(ORIENTDB_PATH);
		System.out.println("=================");
		
	}

	private void addDatabasePathChangeListener() {
		
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(e -> {
			if (e.getProperty().equals(Preferences.DATABASE_PATH)) {
				String newValue = (String) e.getNewValue();
				String oldValue = (String) e.getOldValue();
				
				System.out.println("=================");
				System.out.println(newValue);
				System.out.println(oldValue);
				System.out.println("=================");
				
				
				if (!ORIENTDB_PATH.equals(newValue) && (!ORIENTDB_PATH.equals(Activator.getDefault().getPreferenceStore().getDefaultString(Preferences.DATABASE_PATH)))) {
					boolean openConfirm = MessageDialog.openConfirm(Activator.getDefault().getWorkbench().getDisplay().getActiveShell(), "Restart", "This action need an eclipse restart.");
					if (openConfirm) {
						Activator.getDefault().getWorkbench().restart();
					} else {
						Activator.getDefault().getPreferenceStore().setValue(Preferences.DATABASE_PATH, ORIENTDB_PATH);
					}
				}
			}
		});
	}

	public GremlinAdapter(String databasePath) {
		this.ORIENTDB_PATH = databasePath;
		this.databaseManager = new DatabaseManager(ORIENTDB_PATH);

		GraphTraversalSource graphTraversal = databaseManager.getTraversal();
		this.gremlinUtils = new GremlinUtils(graphTraversal);

	}

	public GremlinUtils getGremlinUtils() {
		return gremlinUtils;
	}

	public void dropDatabase() throws Exception {
		databaseManager.erase();
	}

	public void closeDatabaseConnection() throws Exception {
		databaseManager.close();
	}

}
