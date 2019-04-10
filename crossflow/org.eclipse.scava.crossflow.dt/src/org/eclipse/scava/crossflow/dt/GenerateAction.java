package org.eclipse.scava.crossflow.dt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.scava.crossflow.GenerateBaseClasses;
import org.eclipse.scava.crossflow.UpdateClasspath;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class GenerateAction implements IObjectActionDelegate {

	private Shell shell;
	protected ISelection selection = null;

	@Override
	public void run(IAction action) {
		IFile selectedFile = (IFile) ((IStructuredSelection) selection).getFirstElement();
		File projectFolder = selectedFile.getProject().getLocation().toFile();
		String projectFolderLocation = projectFolder.getPath();
		try {
			
			// generate base classes
			new GenerateBaseClasses().run(projectFolderLocation, selectedFile.getName());
			
			// update classpath
			File classpath = getClasspathFile(projectFolder);
			if (classpath != null && classpath.exists()) {
				new UpdateClasspath().run(classpath);
			}
			
			// update manifest with dependencies
			File manifest = getManifestFile(projectFolder);
			if (manifest != null && manifest.exists()) {
				updateManifest(manifest);
			}

			// run ant builds for master and worker jars
			//FIXME

			// refresh workspace
			selectedFile.getProject().refreshLocal(IFile.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (Exception e) {
			MessageDialog.openError(shell, "Error", e.getMessage());
		}
	}

	private void updateManifest(File manifest) throws Exception {

		BufferedReader r = new BufferedReader(new FileReader(manifest));

		List<String> contents = new LinkedList<String>();
		String line;
		while ((line = r.readLine()) != null) {
			contents.add(line);
		}
		r.close();

		if (!contents.stream().anyMatch(s -> s.equals("Require-Bundle: org.eclipse.scava.crossflow.runtime"))) {

			String dependencies = "Require-Bundle: org.eclipse.scava.crossflow.runtime";

			BufferedWriter w = new BufferedWriter(new FileWriter(manifest, true));
			w.append(dependencies + "\r\n");
			w.close();

		}

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	protected File getClasspathFile(File directory) {
		if (containsFile(directory, ".classpath")) {
			return new File(directory.getAbsolutePath() + "/.classpath");
		}
		return null;
	}

	protected File getManifestFile(File directory) {
		File mff = new File(directory.getPath() + "/META-INF/");
		System.out.println(mff);
		if (containsFile(mff, "MANIFEST.MF")) {
			return new File(mff.getAbsolutePath() + "/MANIFEST.MF");
		}
		return null;
	}

	protected boolean containsFile(File directory, String file) {
		for (String f : directory.list()) {
			if (f.equals(file))
				return true;
		}
		return false;
	}

}
