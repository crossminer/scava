/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.librarysuggestion;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.scava.plugin.librarysuggestion.library.DisposeLibraryRequestEvent;
import org.eclipse.scava.plugin.librarysuggestion.library.DoNotUseLibraryForSearchRequestEvent;
import org.eclipse.scava.plugin.librarysuggestion.library.Library;
import org.eclipse.scava.plugin.librarysuggestion.library.LibraryController;
import org.eclipse.scava.plugin.librarysuggestion.library.LibraryModel;
import org.eclipse.scava.plugin.librarysuggestion.library.LibraryType;
import org.eclipse.scava.plugin.librarysuggestion.library.LibraryView;
import org.eclipse.scava.plugin.librarysuggestion.library.PickForInstallLibraryRequestEvent;
import org.eclipse.scava.plugin.librarysuggestion.library.UnpickForInstallLibraryRequestEvent;
import org.eclipse.scava.plugin.librarysuggestion.library.UseLibraryForSearchRequestEvent;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.swt.widgets.Display;

import com.squareup.okhttp.Call;

public class LibrarySuggestionController extends ModelViewController<LibrarySuggestionModel, LibrarySuggestionView>
		implements ILibrarySuggestionViewEventListener {
	private Collection<Library> suggestedLibraries = new HashSet<>();
	private Call suggestionUpdateCall;

	public LibrarySuggestionController(Controller parent, LibrarySuggestionModel model, LibrarySuggestionView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();

		Collection<Library> usedLibraries = getModel().getUsedLibraries();

		usedLibraries.forEach(library -> getModel().setLibraryBaseOfSearch(library));

		Collection<Library> baseLibrariesForSearch = getModel().getBaseLibrariesForSearch();
		Collection<Library> pickedLibraries = getModel().getPickedLibraries();

		usedLibraries.removeAll(baseLibrariesForSearch);

		usedLibraries.forEach(library -> {
			LibraryModel model = new LibraryModel(library, LibraryType.UsedInProject);
			LibraryView view = new LibraryView();
			LibraryController controller = new LibraryController(this, model, view);
			controller.init();

			getView().addUsedLibrary(view);
		});
		getView().setNumberOfLibsUsedInProject(usedLibraries.size());

		baseLibrariesForSearch.forEach(library -> {
			LibraryModel model = new LibraryModel(library, LibraryType.UsedForSearch);
			LibraryView view = new LibraryView();
			LibraryController controller = new LibraryController(this, model, view);
			controller.init();

			getView().addBasedOnLibrary(view);
		});
		getView().setNumberOfLibsSearchBasedOn(baseLibrariesForSearch.size());

		pickedLibraries.forEach(library -> {
			LibraryModel model = new LibraryModel(library, LibraryType.PickedForInstall);
			LibraryView view = new LibraryView();
			LibraryController controller = new LibraryController(this, model, view);
			controller.init();

			getView().addPickedLibrary(view);
		});
		getView().setNumberOfLibsPickedForInstall(pickedLibraries.size());

		updateSuggestedLibrariesAsync();

		getView().setEnableInstall(!getModel().getPickedLibraries().isEmpty());
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	protected void onReceiveRoutedEventFromSubController(IRoutedEvent routedEvent, Controller forwarderController) {

		if (routedEvent instanceof UseLibraryForSearchRequestEvent) {
			UseLibraryForSearchRequestEvent event = (UseLibraryForSearchRequestEvent) routedEvent;
			Library library = event.getLibrary();

			getModel().setLibraryBaseOfSearch(library);
			routeEventToSubControllers(new DisposeLibraryRequestEvent(this, library));

			LibraryModel model = new LibraryModel(library, LibraryType.UsedForSearch);
			LibraryView view = new LibraryView();
			LibraryController controller = new LibraryController(this, model, view);
			controller.init();

			getView().addBasedOnLibrary(view);

			updateSuggestedLibrariesAsync();

			getView().setNumberOfLibsUsedInProject(
					getModel().getUsedLibraries().size() - getModel().getBaseLibrariesForSearch().size());
			getView().setNumberOfLibsSearchBasedOn(getModel().getBaseLibrariesForSearch().size());

			return;
		}

		if (routedEvent instanceof DoNotUseLibraryForSearchRequestEvent) {
			DoNotUseLibraryForSearchRequestEvent event = (DoNotUseLibraryForSearchRequestEvent) routedEvent;
			Library library = event.getLibrary();

			getModel().unsetLibraryBaseOfSearch(library);
			routeEventToSubControllers(new DisposeLibraryRequestEvent(this, library));

			LibraryModel model = new LibraryModel(library, LibraryType.UsedInProject);
			LibraryView view = new LibraryView();
			LibraryController controller = new LibraryController(this, model, view);
			controller.init();

			getView().addUsedLibrary(view);

			updateSuggestedLibrariesAsync();

			getView().setNumberOfLibsUsedInProject(
					getModel().getUsedLibraries().size() - getModel().getBaseLibrariesForSearch().size());
			getView().setNumberOfLibsSearchBasedOn(getModel().getBaseLibrariesForSearch().size());

			return;
		}

		if (routedEvent instanceof PickForInstallLibraryRequestEvent) {
			PickForInstallLibraryRequestEvent event = (PickForInstallLibraryRequestEvent) routedEvent;
			Library library = event.getLibrary();

			getModel().setLibraryPicked(library);
			routeEventToSubControllers(new DisposeLibraryRequestEvent(this, library));

			suggestedLibraries.remove(library);

			LibraryModel model = new LibraryModel(library, LibraryType.PickedForInstall);
			LibraryView view = new LibraryView();
			LibraryController controller = new LibraryController(this, model, view);
			controller.init();

			getView().addPickedLibrary(view);
			getView().setEnableInstall(!getModel().getPickedLibraries().isEmpty());

			getView().setNumberOfLibsPickedForInstall(getModel().getPickedLibraries().size());
			getView().setNumberOfLibsSuggested(suggestedLibraries.size());

			return;
		}

		if (routedEvent instanceof UnpickForInstallLibraryRequestEvent) {
			UnpickForInstallLibraryRequestEvent event = (UnpickForInstallLibraryRequestEvent) routedEvent;
			Library library = event.getLibrary();

			getModel().unsetLibraryPicked(library);
			routeEventToSubControllers(new DisposeLibraryRequestEvent(this, library));

			suggestedLibraries.add(library);

			LibraryModel model = new LibraryModel(library, LibraryType.Suggested);
			LibraryView view = new LibraryView();
			LibraryController controller = new LibraryController(LibrarySuggestionController.this, model, view);
			controller.init();

			getView().addSuggestedLibrary(view);
			getView().setEnableInstall(!getModel().getPickedLibraries().isEmpty());

			getView().setNumberOfLibsPickedForInstall(getModel().getPickedLibraries().size());
			getView().setNumberOfLibsSuggested(suggestedLibraries.size());

			return;
		}

		super.onReceiveRoutedEventFromSubController(routedEvent, forwarderController);
	}

	private void updateSuggestedLibrariesAsync() {
		if (suggestionUpdateCall != null) {
			suggestionUpdateCall.cancel();
		}

		suggestedLibraries.removeAll(getModel().getPickedLibraries());
		suggestedLibraries.forEach(l -> routeEventToSubControllers(new DisposeLibraryRequestEvent(this, l)));

		getView().showLoadingResults();
		getView().setNumberOfLibsSuggested(0);

		Collection<Library> baseLibrariesForSearch = getModel().getBaseLibrariesForSearch();
		suggestionUpdateCall = getModel().getSuggestedLibrariesAsync(libs -> {
			Display.getDefault().asyncExec(() -> {

				if (!getModel().getBaseLibrariesForSearch().equals(baseLibrariesForSearch) || isDisposed()) {
					return;
				}

				libs.removeAll(getModel().getUsedLibraries());

				suggestedLibraries = new HashSet<>(libs);

				libs.removeAll(getModel().getPickedLibraries());

				if (libs.isEmpty()) {
					getView().showNoResults();
				} else {
					libs.forEach(l -> {
						LibraryModel model = new LibraryModel(l, LibraryType.Suggested);
						LibraryView view = new LibraryView();
						LibraryController controller = new LibraryController(this, model, view);
						controller.init();

						getView().addSuggestedLibrary(view);
					});
				}

				getView().setNumberOfLibsSuggested(libs.size());
			});
		}, exception -> {
			Display.getDefault().asyncExec(() -> {

				if (!getModel().getBaseLibrariesForSearch().equals(baseLibrariesForSearch) || isDisposed()) {
					return;
				}
				getView().showTryAgain();
				getView().setNumberOfLibsSuggested(0);
			});
		});
	}

	@Override
	protected void disposeController() {
		super.disposeController();

		if (suggestionUpdateCall != null) {
			suggestionUpdateCall.cancel();
		}
	}

	@Override
	public void onUseAllForSearch() {
		getModel().getUsedLibraries().forEach(library -> {
			getModel().setLibraryBaseOfSearch(library);

			routeEventToSubControllers(new DisposeLibraryRequestEvent(this, library));

			LibraryModel model = new LibraryModel(library, LibraryType.UsedForSearch);
			LibraryView view = new LibraryView();
			LibraryController controller = new LibraryController(this, model, view);
			controller.init();

			getView().addBasedOnLibrary(view);
		});

		updateSuggestedLibrariesAsync();

		getView().setNumberOfLibsUsedInProject(
				getModel().getUsedLibraries().size() - getModel().getBaseLibrariesForSearch().size());
		getView().setNumberOfLibsSearchBasedOn(getModel().getBaseLibrariesForSearch().size());
	}

	@Override
	public void onUseNoneForSearch() {
		getModel().getBaseLibrariesForSearch().forEach(library -> {
			getModel().unsetLibraryBaseOfSearch(library);

			routeEventToSubControllers(new DisposeLibraryRequestEvent(this, library));

			LibraryModel model = new LibraryModel(library, LibraryType.UsedInProject);
			LibraryView view = new LibraryView();
			LibraryController controller = new LibraryController(this, model, view);
			controller.init();

			getView().addUsedLibrary(view);
		});

		updateSuggestedLibrariesAsync();

		getView().setNumberOfLibsUsedInProject(
				getModel().getUsedLibraries().size() - getModel().getBaseLibrariesForSearch().size());
		getView().setNumberOfLibsSearchBasedOn(getModel().getBaseLibrariesForSearch().size());
	}

	@Override
	public void onPickAllToInstall() {
		suggestedLibraries.forEach(library -> {

			getModel().setLibraryPicked(library);

			routeEventToSubControllers(new DisposeLibraryRequestEvent(this, library));

			LibraryModel model = new LibraryModel(library, LibraryType.PickedForInstall);
			LibraryView view = new LibraryView();
			LibraryController controller = new LibraryController(this, model, view);
			controller.init();

			getView().addPickedLibrary(view);
		});

		suggestedLibraries.clear();

		getView().setEnableInstall(!getModel().getPickedLibraries().isEmpty());

		getView().setNumberOfLibsPickedForInstall(getModel().getPickedLibraries().size());
		getView().setNumberOfLibsSuggested(suggestedLibraries.size());
	}

	@Override
	public void onPickNoneToInstall() {
		Collection<Library> pickedLibraries = getModel().getPickedLibraries();
		pickedLibraries.forEach(library -> {
			getModel().unsetLibraryPicked(library);

			routeEventToSubControllers(new DisposeLibraryRequestEvent(this, library));

			LibraryModel model = new LibraryModel(library, LibraryType.Suggested);
			LibraryView view = new LibraryView();
			LibraryController controller = new LibraryController(LibrarySuggestionController.this, model, view);
			controller.init();

			getView().addSuggestedLibrary(view);
		});

		suggestedLibraries.addAll(pickedLibraries);

		getView().setEnableInstall(!getModel().getPickedLibraries().isEmpty());

		getView().setNumberOfLibsPickedForInstall(getModel().getPickedLibraries().size());
		getView().setNumberOfLibsSuggested(suggestedLibraries.size());
	}

	@Override
	public void onInstall() {
		try {
			getModel().installPickedLibraries();
			MessageDialog.openInformation(getView().getShell(), "Install",
					"The installation of the selected libraries has been finished successfully.");
			dispose();
		} catch (IOException | XmlPullParserException e) {
			e.printStackTrace();
			MessageDialog.openError(getView().getShell(), "Error", "Could not install the selected libraries.\n" + e);
		}
	}

	@Override
	public void onTryAgainLoadSugestions() {
		updateSuggestedLibrariesAsync();
	}
}
