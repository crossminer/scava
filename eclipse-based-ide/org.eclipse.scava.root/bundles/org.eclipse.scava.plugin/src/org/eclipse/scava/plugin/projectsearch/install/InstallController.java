/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.install;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.scava.plugin.projectsearch.ProjectSearchInstallFinishedEvent;
import org.eclipse.scava.plugin.projectsearch.ProjectSearchSearchRequestEvent;
import org.eclipse.scava.plugin.projectsearch.install.installable.DefaultBasePathRequestEvent;
import org.eclipse.scava.plugin.projectsearch.install.installable.InstallableController;
import org.eclipse.scava.plugin.projectsearch.install.installable.InstallableModel;
import org.eclipse.scava.plugin.projectsearch.install.installable.InstallableView;
import org.eclipse.scava.plugin.projectsearch.install.installable.NewBaseDestinationPathGivenEvent;
import org.eclipse.scava.plugin.projectsearch.install.installable.ProjectInstallBeginEvent;
import org.eclipse.scava.plugin.projectsearch.install.installable.ProjectInstallEndEvent;
import org.eclipse.scava.plugin.projectsearch.install.installable.ProjectInstallRequestEvent;
import org.eclipse.scava.plugin.projectsearch.install.installable.ProjectNotReadyToInstall;
import org.eclipse.scava.plugin.projectsearch.install.installable.ProjectReadyToInstall;

import io.swagger.client.model.Artifact;

public class InstallController extends ModelViewController<InstallModel, InstallView>
		implements IInstallViewEventListener {

	private final Set<Artifact> installing = new HashSet<>();
	private final Set<Artifact> notReadyToInstall = new HashSet<>();

	public InstallController(Controller parent, InstallModel model, InstallView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();

		Set<Artifact> projects = getModel().getSelectedForInstall();
		for (Artifact project : projects) {
			InstallableModel model = new InstallableModel(project, "");
			InstallableView view = new InstallableView();
			InstallableController controller = new InstallableController(this, model, view);
			controller.init();

			getView().addInstallable(view);
		}

		String workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString();
		onChangeBasePath(workspacePath);
	}

	@Override
	public void requestViewClose() {
		if (!installing.isEmpty()) {
			return;
		}

		if (!getModel().isAllProjectInstalled()) {
			boolean confirmed = MessageDialog.openConfirm(getView().getShell(), "Confirm",
					"You have not installed all the selected libraries yet.\nAre you sure, you want to close the installer?");
			if (!confirmed) {
				return;
			}
		}

		routeEventToParentController(new ProjectSearchInstallFinishedEvent(this));
	}

	@Override
	protected void onReceiveRoutedEventFromSubController(IRoutedEvent routedEvent, Controller forwarderController) {
		if (routedEvent instanceof ProjectInstallBeginEvent) {
			ProjectInstallBeginEvent event = (ProjectInstallBeginEvent) routedEvent;

			installing.add(event.getProject());
			notReadyToInstall.remove(event.getProject());

			getView().setEnabledInstall(false);
			getView().setEnabledChangeBasePath(false);
			getView().setEnabledClose(false);

			return;
		}

		if (routedEvent instanceof ProjectInstallEndEvent) {
			ProjectInstallEndEvent event = (ProjectInstallEndEvent) routedEvent;

			installing.remove(event.getProject());
			notReadyToInstall.remove(event.getProject());

			if (event.isSuccess()) {
				getModel().setInstalled(event.getProject());
			}

			if (installing.isEmpty()) {
				getView().setEnabledClose(true);

				if (!getModel().isAllProjectInstalled() ) {
					getView().setEnabledChangeBasePath(true);
					
					if( notReadyToInstall.isEmpty() ) {
						getView().setEnabledInstall(true);
					}
				}
			}

			return;
		}

		if (routedEvent instanceof ProjectReadyToInstall) {
			ProjectReadyToInstall event = (ProjectReadyToInstall) routedEvent;

			notReadyToInstall.remove(event.getProject());

			if (installing.isEmpty()) {
				if (!getModel().isAllProjectInstalled() && notReadyToInstall.isEmpty()) {
					getView().setEnabledInstall(true);
				}
			}
		}

		if (routedEvent instanceof ProjectNotReadyToInstall) {
			ProjectNotReadyToInstall event = (ProjectNotReadyToInstall) routedEvent;

			notReadyToInstall.add(event.getProject());

			getView().setEnabledInstall(false);
		}

		if (routedEvent instanceof DefaultBasePathRequestEvent) {
			DefaultBasePathRequestEvent event = (DefaultBasePathRequestEvent) routedEvent;

			routeEventToSubControllers(new NewBaseDestinationPathGivenEvent(this, getModel().getBasePath()));
		}

		super.onReceiveRoutedEventFromSubController(routedEvent, forwarderController);
	}

	@Override
	public void onChangeBasePath(String path) {
		File file = new File(path);

		if (!file.exists() || !file.isDirectory()) {
			MessageDialog.openError(getView().getShell(), "Error",
					"The path " + path + " does not exist or is not a directory.\nPlease check the base path!");
			return;
		}

		getModel().setBasePath(path);
		getView().setBasePath(path);
		routeEventToSubControllers(new NewBaseDestinationPathGivenEvent(this, path));
	}

	@Override
	public void onInstallAll() {
		for (Artifact project : getModel().getNotInstalled()) {
			if (!installing.contains(project)) {
				routeEventToSubControllers(new ProjectInstallRequestEvent(this, project));
			}
		}
	}

	@Override
	public void onBack() {
		routeEventToParentController(new ProjectSearchSearchRequestEvent(this));
	}
}
