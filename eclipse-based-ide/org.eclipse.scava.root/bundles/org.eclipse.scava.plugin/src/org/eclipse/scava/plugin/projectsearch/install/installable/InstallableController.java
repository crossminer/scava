/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.install.installable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.scava.plugin.Constants;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.swt.widgets.Display;

import io.swagger.client.model.Artifact;

public class InstallableController extends ModelViewController<InstallableModel, InstallableView>
		implements IInstallableViewEventListener {
	private CancellableTextProgressMonitor progressMonitor;
	private boolean installed;
	
	public InstallableController(Controller parent, InstallableModel model, InstallableView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();

		Artifact project = getModel().getProject();
		getView().setProject(project);

		String fullName = Optional.ofNullable(project.getFullName()).orElse(Optional.ofNullable(project.getName()).orElse(Constants.NO_DATA));
		String destinationProjectFolder = fullName.replaceAll("/", "__");
		
		getView().setDestinationProjectFolder(destinationProjectFolder);

		String path = getModel().getDestination();
		getView().setDestinationPath(path);

		checkAndHandleInstallCondidition();
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	protected void onReceiveRoutedEventFromParentController(IRoutedEvent routedEvent) {

		if (routedEvent instanceof NewBaseDestinationPathGivenEvent) {
			NewBaseDestinationPathGivenEvent event = (NewBaseDestinationPathGivenEvent) routedEvent;

			if (!getModel().isCustomDestinationUsed()) {
				Artifact project = getModel().getProject();
				String fullName = Optional.ofNullable(project.getFullName()).orElse(Optional.ofNullable(project.getName()).orElse(Constants.NO_DATA));
				fullName = fullName.replaceAll("/", "__");
				
				String newPath = event.getPath() + File.separator + fullName;

				onDestinationChanged(newPath);
			}

			return;
		}

		if (routedEvent instanceof ProjectInstallRequestEvent) {
			ProjectInstallRequestEvent event = (ProjectInstallRequestEvent) routedEvent;

			if (event.getProject().getId().equals(getModel().getProject().getId())) {
				installProjectAsync();
			}

			return;
		}

		super.onReceiveRoutedEventFromParentController(routedEvent);
	}

	@Override
	public void onDestinationChanged(String path) {
		if (!getModel().getDestination().equals(path)) {
			getModel().setDestination(path);
			getView().setDestinationPath(path);
		}

		checkAndHandleInstallCondidition();
	}

	@Override
	public void onInstall() {
		installProjectAsync();
	}

	private void checkInstallConditions() throws ConditionFailException, AlreadyInstalledException {
		// install
		if (progressMonitor != null) {
			throw new ConditionFailException("The previous install did not finish properly or still running.");
		}

		// url
		String cloneUrl = getModel().getProject().getCloneUrl();
		if (cloneUrl == null || cloneUrl.isEmpty()) {
			throw new ConditionFailException("Clone URL is not provided or empty.");
		}

		// branch
		String masterBranch = getModel().getProject().getMasterBranch();
		if (masterBranch == null || masterBranch.isEmpty()) {
			throw new ConditionFailException("Master branch is not provided or empty.");
		}

		// destination path
		String destinationPath = getModel().getDestination();

		if (destinationPath == null || destinationPath.length() <= 0) {
			throw new ConditionFailException("You have to specify the destination path.");
		}

		File destinationFolder = new File(destinationPath);

		try {
			Paths.get(destinationPath);
			destinationFolder.getCanonicalPath();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ConditionFailException("The specified destination path is not valid.");
		}

		if (!destinationFolder.isAbsolute()) {
			throw new ConditionFailException("You have to specify an absolute destination path.");
		}

		if (destinationFolder.exists()) {
			if (!destinationFolder.isDirectory()) {
				throw new ConditionFailException("The specified destination path does not exist or is not a folder.");
			}

			if (destinationFolder.list().length > 0) {

				try {
					Git git = Git.open(destinationFolder);

					String repositoryUrl = git.getRepository().getConfig().getString("remote", "origin", "url");
					if (cloneUrl.equals(repositoryUrl)) {
						throw new AlreadyInstalledException("Already installed to the specified destination directory.");
					}
				} catch (IOException e) {
					// not relevant from our perspective
				}

				throw new ConditionFailException(
						"The specified destination directory must not contain any files or directories.");
			}
		}
	}

	private void installProjectAsync() {
		if (!checkAndHandleInstallCondidition()) {
			return;
		}

		progressMonitor = new CancellableTextProgressMonitor() {

			@Override
			protected void onUpdate(String taskName, int cmp, int totalWork, int pcnt) {
				super.onUpdate(taskName, cmp, totalWork, pcnt);
				Display.getDefault().syncExec(() -> {
					String process = taskName + ":  " + pcnt + "% (" + cmp + "/" + totalWork + ")";
					getView().setInstallProgress(process, pcnt);
				});
			}
		};

		CloneCommand gitCommand = Git.cloneRepository().setURI(getModel().getProject().getCloneUrl())
				.setDirectory(new File(getModel().getDestination()))
				.setBranch(getModel().getProject().getMasterBranch()).setProgressMonitor(progressMonitor);

		getView().installStarted();
		routeEventToParentController(new ProjectInstallBeginEvent(this, getModel().getProject()));

		new Thread(() -> {
			Git git = null;
			try {
				git = gitCommand.call();

				Display.getDefault().syncExec(() -> {
					installed = true;
					getView().installFinished();
					routeEventToParentController(new ProjectInstallEndEvent(this, getModel().getProject(), true));
				});
			} catch (Exception e) {
				e.printStackTrace();

				if (git != null) {
					git.close();
				}

				// Delete non complete clone
				File file = new File(getModel().getDestination());
				if (file.exists()) {
					try {
						FileUtils.deleteDirectory(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				Display.getDefault().syncExec(() -> {
					if (progressMonitor != null && progressMonitor.isCancelled()) {
						getView().installCancelled("Cancelled");
					} else {
						getView().installCancelled("Error during cloning repository: " + e);
					}
					routeEventToParentController(new ProjectInstallEndEvent(this, getModel().getProject(), false));
				});

			} finally {
				if (git != null) {
					git.close();
				}
				progressMonitor = null;
			}
		}).start();
	}

	@Override
	public void onCancel() {
		if (progressMonitor != null) {
			progressMonitor.setCancelled(true);
		}
	}

	@Override
	public void onUseCustomDestination(boolean useCustom) {
		getModel().setCustomDestinationUsed(useCustom);

		if (!useCustom) {
			routeEventToParentController(new DefaultBasePathRequestEvent(this));
		} else {
			checkAndHandleInstallCondidition();
		}
	}

	private boolean checkAndHandleInstallCondidition() {
		if (installed) {
			return false;
		}

		try {
			checkInstallConditions();
			routeEventToParentController(new ProjectReadyToInstall(this, getModel().getProject()));
			getView().readyToInstall();
			return true;
		} catch (ConditionFailException e) {
			routeEventToParentController(new ProjectNotReadyToInstall(this, getModel().getProject()));
			getView().notReadyToInstall(e.getMessage());
			return false;
		} catch (AlreadyInstalledException e) {
			routeEventToParentController(new ProjectInstallEndEvent(this, getModel().getProject(), true));
			getView().alreadyInstalled();
			return false;
		}
	}

	private class ConditionFailException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8602813200023726340L;

		public ConditionFailException(String message) {
			super(message);
		}

	}

	private class AlreadyInstalledException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 187058380555723933L;

		public AlreadyInstalledException(String message) {
			super(message);
		}

	}
}
