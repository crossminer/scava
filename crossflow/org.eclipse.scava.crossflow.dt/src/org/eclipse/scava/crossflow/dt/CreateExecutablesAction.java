package org.eclipse.scava.crossflow.dt;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.ui.jarpackager.IJarExportRunnable;
import org.eclipse.jdt.ui.jarpackager.JarPackageData;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.scava.crossflow.GenerateExecutables;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

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
			generateAll(true);

			// remove ant script
			File ant = new File(projectFolder, "build-experiment-zip.xml");
			ant.delete();

			// create client jar
			createClientJar(true, projectFolderLocation);

			// refresh workspace
			selectedFile.getProject().refreshLocal(IFile.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (Exception e) {
			MessageDialog.openError(shell, "Error", e.getMessage());
		}
	}

	private void createClientJar(boolean replace, String path) throws Exception {

		//FIXME WiP
		
		IFile selectedFile = (IFile) ((IStructuredSelection) selection).getFirstElement();
		IProject project = selectedFile.getProject();

		StructuredSelection fSelection = new StructuredSelection(project);

		JarPackageData fJarPackage = new JarPackageData();
		fJarPackage.setIncludeDirectoryEntries(true);

		Object[] elems = { project };

		fJarPackage.setElements(elems);

		fJarPackage.setExportJavaFiles(true);

		fJarPackage.setJarLocation(ResourcesPlugin.getWorkspace().getRoot().getLocation()
				.append(project.getFullPath().append("/worker.jar")));

		fJarPackage.setOverwrite(true);
		
		IJarExportRunnable r = fJarPackage.createJarExportRunnable(shell);

		if (replace || !new File(project.getLocation().toFile(), "worker.jar").exists())
			r.run(null);

	}

	private void generateAll(boolean replace) throws Exception {

		IFile selectedFile = (IFile) ((IStructuredSelection) selection).getFirstElement();
		File projectFolder = selectedFile.getProject().getLocation().toFile();
		File expFolder = new File(projectFolder, "experiment");

		if (replace || (expFolder.exists()
				&& !Arrays.asList(expFolder.listFiles()).stream().anyMatch(f -> f.getName().endsWith(".zip")))) {

			if (replace)
				Arrays.asList(expFolder.listFiles()).stream().filter(f -> f.getName().endsWith(".zip"))
						.forEach(f -> f.delete());

			AntRunner runner = new AntRunner();
			runner.setBuildFileLocation(new File(projectFolder, "build-experiment-zip.xml").getPath());
			runner.setArguments("-Dmessage=Building -verbose");

			runner.run(null);

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
