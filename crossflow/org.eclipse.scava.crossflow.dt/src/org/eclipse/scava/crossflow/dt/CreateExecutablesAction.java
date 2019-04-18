package org.eclipse.scava.crossflow.dt;

import java.io.File;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.scava.crossflow.GenerateExecutables;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import org.eclipse.jdt.ui.jarpackager.*;

public class CreateExecutablesAction implements IObjectActionDelegate {

	private Shell shell;
	protected ISelection selection = null;

	@Override
	public void run(IAction action) {
		IFile selectedFile = (IFile) ((IStructuredSelection) selection).getFirstElement();
		File projectFolder = selectedFile.getProject().getLocation().toFile();
		String projectFolderLocation = projectFolder.getPath();
		try {

			// generate ant script
			new GenerateExecutables().run(projectFolderLocation,
					selectedFile.getFullPath().toString().replaceFirst("\\\\" + projectFolder.getName(), ""),
					"unknown_deps_location");

			// use ant script to generate experiment jars and zip
			generateAll();

			// remove ant script
			File ant = new File(projectFolder, "build-experiment-zip.xml");
			ant.delete();

			// create client jar
			createClientJar(projectFolderLocation);

			// refresh workspace
			selectedFile.getProject().refreshLocal(IFile.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (Exception e) {
			MessageDialog.openError(shell, "Error", e.getMessage());
		}
	}

	private void createClientJar(String path) {

		// TODO if we want to help user not have to use eclipse export as jar

//IType mainType, IFile[] filestoExport

//		Shell parentShell = shell;
//		JarPackageData description = new JarPackageData();
//		IPath location = new Path(path);
//		description.setJarLocation(location);
//		description.setSaveManifest(true);
//		description.setManifestMainClass(IType);
//		description.setElements(filestoExport);
//		IJarExportRunnable runnable = description.createJarExportRunnable(parentShell);
//		try {
//			new ProgressMonitorDialog(parentShell).run(true, true, runnable);
//		} catch (InvocationTargetException e) {
//			// An error has occurred while executing the operation
//		} catch (InterruptedException e) {
//			// operation has been canceled.
//		}

	}

	private void generateAll() throws Exception {

		IFile selectedFile = (IFile) ((IStructuredSelection) selection).getFirstElement();
		File projectFolder = selectedFile.getProject().getLocation().toFile();

		AntRunner runner = new AntRunner();
		runner.setBuildFileLocation(new File(projectFolder, "build-experiment-zip.xml").getPath());
		runner.setArguments("-Dmessage=Building -verbose");

		runner.run(null);

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
