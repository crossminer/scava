/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.eclipse.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.scava.plugin.ui.metric.MetricDisplay;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;

import javax.inject.Inject;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.preferences.Preferences;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.ProjectUtils;
import org.eclipse.ui.PlatformUI;

public class MetricHandler extends AbstractHandler {

	private final IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();

	@Inject
	UISynchronize sync;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// PlatformUI.getWorkbench().getHelpSystem().displayHelpResource("C:/Users/Szamos/eclipse-workspace/testplugin/html/toc.html");

		if (preferenceStore.getBoolean(Preferences.HELP_MENU_OPEN)) {
			JCheckBox checkbox = new JCheckBox("Do not show this message again.");
			String message = "Would you like to open the help menu?";
			Object[] params = { message, checkbox };
			int option = JOptionPane.showConfirmDialog(null, params, "Help menu", JOptionPane.YES_NO_OPTION);

			if (option == 0) {
				PlatformUI.getWorkbench().getHelpSystem().displayHelp();
			}

			boolean dontShow = checkbox.isSelected();
			preferenceStore.setValue(Preferences.HELP_MENU_OPEN, !dontShow);

		}

//		try {
//			ProjectUtils.getProjectsForMetricCalculation();
//		} catch (CoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		Job job = new Job("Long Running Job") {
			protected IStatus run(IProgressMonitor monitor) {

				System.out.println("Start job");

				Random rnd = new Random();
				List<Double> testList = new ArrayList<>();

				for (int i = 0; i < 20000000; i++) {
					testList.add(rnd.nextDouble());
				}

				monitor.setTaskName("Valami");

				Collections.sort(testList);

				monitor.setTaskName("Rendez�s");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				OptionalDouble average = testList.stream().mapToDouble(a -> a).average();

				System.out.println(average.isPresent() ? average.getAsDouble() : 0.0);
				return Status.OK_STATUS;
			}

		};

		return null;
	}

}
