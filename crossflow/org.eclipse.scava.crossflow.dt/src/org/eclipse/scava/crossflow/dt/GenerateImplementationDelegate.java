/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Konstantinos Barmpis - adaption for CROSSFLOW
 *     Jonathan Co - adaption for command line execution
 *     Horacio Hoyos Rodriguez - UI
 ******************************************************************************/
package org.eclipse.scava.crossflow.dt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.scava.crossflow.GenerateImplementations;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.osgi.framework.Bundle;

/**
 * The action delegate for the CrossflowBaseClassGenerationAction menu
 * 
 * @author Horacio Hoyos Rodriguez
 * @author Konstantinos Barmpis
 *
 */
public class GenerateImplementationDelegate implements IObjectActionDelegate {

	public static final String REUSABLECOMPONENTEXTENSIONPOINT = "org.eclipse.scava.crossflow.ReusableComponentsExtensionPoint";
	
	private Shell shell;
	protected ISelection selection = null;

	private List<IConfigurationElement> getConfigurationElementsFor(final String extensionPointId) {
		ArrayList<IConfigurationElement> els = new ArrayList<IConfigurationElement>();
		if (Platform.isRunning()) {
			IConfigurationElement[] e = Platform.getExtensionRegistry().getConfigurationElementsFor(extensionPointId);

			els.addAll(Arrays.asList(e));
		}
		return els;
	}
	
	@Override
	public void run(IAction action) {
		IFile selectedFile = (IFile) ((IStructuredSelection) selection).getFirstElement();
		File projectFolder = selectedFile.getProject().getLocation().toFile();
		try {
			// create resources folder for xml file
			File resourcesFolder = new File(projectFolder, GenerateImplementations.RESOURCES_FOLDER_NAME);
			resourcesFolder.mkdirs();
			File libs = new File(resourcesFolder, "lib");
			libs.mkdirs();

			// generate implementations
			GenerateImplementations impls = new GenerateImplementations(projectFolder,
					selectedFile.getLocation().toOSString());
			// add all reusable component models from the relevant extension point to the resource set
			
			List<IConfigurationElement> reusableComponentsCEs = getConfigurationElementsFor(
					GenerateImplementationDelegate.REUSABLECOMPONENTEXTENSIONPOINT);

			List<File> reusableComponentModels = new ArrayList<>();
			
			for (IConfigurationElement ice : reusableComponentsCEs) {
				Bundle bundle = Platform.getBundle(ice.getDeclaringExtension().getContributor().getName());
				URL model = bundle.getEntry(ice.getAttribute("definition"));
				// System.out.println(model);
				URL resolved = null;
				try {
					resolved = FileLocator.resolve(model);
				} catch (IOException e) {
					e.printStackTrace();
				}
			
				reusableComponentModels.add(new File(resolved.getFile()));
			}			
			//
			impls.setReusableComponentModels(reusableComponentModels);
			
			//
			displayInformation(impls.run());
			// refresh workspace
			selectedFile.getProject().refreshLocal(IFile.DEPTH_INFINITE, new NullProgressMonitor());

		} catch (Exception e) {
			ExceptionHandler.handle(e, shell);
		}
	}

	private void displayInformation(Map<String, String[]> result) {
		StringBuilder message = new StringBuilder();
		message.append("The base classes and scaffold implementations have been created for the "
				+ "specifed languages in the following locations:");
		message.append(System.lineSeparator());
		for (Map.Entry<String, String[]> entry : result.entrySet()) {
			message.append(String.format("\t- %s", entry.getKey()));
			message.append(System.lineSeparator());
			message.append(String.format("\t\t base: %s", entry.getValue()[1]));
			message.append(System.lineSeparator());
			message.append(String.format("\t\t scaffold: %s", entry.getValue()[0]));
			message.append(System.lineSeparator());
		}
		message.append(System.lineSeparator());
		message.append(String.format(
				"Next, you should implement the flow logic in the scaffold classes and "
						+ "update the workflow information in the file '%s/%s.xml'. Additionally, you can add "
						+ "initial input files to the '%1$s/in' folder.",
				GenerateImplementations.RESOURCES_FOLDER_NAME, GenerateImplementations.WORKFLOW_DESCRIPTION_NAME));
		message.append(System.lineSeparator());
		message.append(String.format(
				"If you are using a script language, make sure the required dependencys' "
						+ "jar are added to the %s/lib folder (you can use ivy/maven/other to autoamte this).",
				GenerateImplementations.RESOURCES_FOLDER_NAME));
		message.append(System.lineSeparator());
		message.append("In order to deploy your flow, use the 'Generate Deployment Artifacts' command "
				+ "to create the required artifacts. This will generate a zip file under the "
				+ "'artifacts' folder in your project, which can then be uploaded to the server "
				+ "manually or via the 'Deploy Artifacts' commands.");
		MessageDialog.openInformation(shell, "Implementation Generation", message.toString());
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

}
