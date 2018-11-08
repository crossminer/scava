package org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.scava.plugin.mvc.implementation.swt.SWTCompositeView;
import org.eclipse.scava.plugin.utils.ScrolledComposites;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import io.swagger.client.model.RecommendedLibrary;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class LibrarySearchRecommendedLibsView extends SWTCompositeView implements ILibrarySearchRecommendedLibsView {
	private Composite composite;
	private ScrolledComposite scrolledComposite;
	private Label lblNoLibrariesWere;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public LibrarySearchRecommendedLibsView() {
		super(SWT.NONE);
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(1, false));

		scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new GridLayout(1, false));

		Label lblSuggestedLibraries = new Label(composite, SWT.NONE);
		lblSuggestedLibraries.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblSuggestedLibraries.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblSuggestedLibraries.setText("Suggested libraries:");

		lblNoLibrariesWere = new Label(composite, SWT.NONE);
		lblNoLibrariesWere.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lblNoLibrariesWere.setText("No libraries were found");
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void showRecommendedLibraries(List<RecommendedLibrary> libraries) {
		if (!libraries.isEmpty()) {
			lblNoLibrariesWere.dispose();
		}

		libraries.forEach(l -> show(l));

		requestLayout();
		ScrolledComposites.updateOnlyVerticalScrollableComposite(scrolledComposite);
	}

	private void show(RecommendedLibrary library) {
		Composite libraryComposite = new Composite(composite, SWT.BORDER);
		libraryComposite.setLayout(new GridLayout(2, false));
		libraryComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblName = new Label(libraryComposite, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblName.setText(library.getLibraryName());

		Link link = new Link(libraryComposite, SWT.NONE);
		link.setText("<a>Open in browser</a>");
		link.addListener(SWT.Selection, e -> {
			try {
				PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(library.getUrl()));
			} catch (PartInitException | MalformedURLException e1) {
				e1.printStackTrace();
			}
		});

		Button installButton = new Button(libraryComposite, SWT.NONE);
		installButton.setText("Install");
		installButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				InstallLibrary event = new InstallLibrary(LibrarySearchRecommendedLibsView.this,
						library.getLibraryName());
				getEventBus().post(event);
			}

		});
		installButton.setEnabled(library.getLibraryName().split(":").length == 3);
	}

	@Override
	public void showInstalledMessage(String libraryName) {
		MessageDialog.openInformation(getShell(), "Install", "Library '" + libraryName + "' has been installed");
	}
}
