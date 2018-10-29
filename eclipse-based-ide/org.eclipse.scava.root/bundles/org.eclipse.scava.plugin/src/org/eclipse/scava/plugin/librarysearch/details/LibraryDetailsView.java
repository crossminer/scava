package org.eclipse.scava.plugin.librarysearch.details;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.knowledgebase.access.SimilarityMethod;
import org.eclipse.scava.plugin.librarysearch.list.LibraryListInfo;
import org.eclipse.scava.plugin.mvc.implementation.swt.SWTCompositeView;
import org.eclipse.scava.plugin.preferences.Preferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wb.swt.SWTResourceManager;

import io.swagger.client.model.Artifact;

public class LibraryDetailsView extends SWTCompositeView implements ILibraryDetailsView {
	private static final int SHORT_DESCRIPTION_LENGTH = 250;
	private Label lblTitle;
	private Label lblDescriptionContent;
	private Label lblSimilarsProjects;
	private Artifact library;
	private Link linkDescriptionReadMore;
	private Label lblDependenciesTitle;
	private Label lblDependenciesContent;
	private Label lblStarredBy;
	private Link linkCheckWebDashboard;
	private CCombo comboSimilarityMethod;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public LibraryDetailsView() {
		super(SWT.NONE);
		setLayout(new GridLayout(3, false));
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setBackgroundMode(SWT.DEFAULT);
		
		
		lblTitle = new Label(this, SWT.NONE);
		lblTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
		lblTitle.setSize(440, 28);
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.NORMAL));
		lblTitle.setText("A library's full name as title");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		lblStarredBy = new Label(this, SWT.NONE);
		lblStarredBy.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		lblStarredBy.setText("Starred by 55 user.");
		new Label(this, SWT.NONE);

		Link linkCheckGitHub = new Link(this, SWT.NONE);
		linkCheckGitHub.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		linkCheckGitHub.setSize(91, 15);
		linkCheckGitHub.addListener(SWT.Selection, e -> openInExternalBrowser(library.getHtmlUrl()));
		linkCheckGitHub.setText("<a>Check on GitHub</a>");
		new Label(this, SWT.NONE);

		linkCheckWebDashboard = new Link(this, SWT.NONE);
		linkCheckWebDashboard.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		linkCheckWebDashboard.setSize(134, 15);
		linkCheckWebDashboard.setText("<a>Check on WebDashboard</a>");
		linkCheckWebDashboard.addListener(SWT.Selection,
				e -> {
					IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
					String webdashboardBasePath = preferences.getString(Preferences.WEBDASHBOARD_BASE_PATH);
					openInExternalBrowser(webdashboardBasePath + library.getWebDashboardId());
				});

		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblDescriptionTitle = new Label(this, SWT.NONE);
		lblDescriptionTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
		lblDescriptionTitle.setSize(76, 20);
		lblDescriptionTitle.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblDescriptionTitle.setText("Description");
		new Label(this, SWT.NONE);

		lblDescriptionContent = new Label(this, SWT.WRAP | SWT.HORIZONTAL);
		lblDescriptionContent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblDescriptionContent.setSize(417, 285);
		lblDescriptionContent.setText("Content goes here");
		new Label(this, SWT.NONE);

		linkDescriptionReadMore = new Link(this, SWT.NONE);
		linkDescriptionReadMore.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		linkDescriptionReadMore.setSize(57, 15);
		linkDescriptionReadMore.setText("<a>Read more</a>");
		linkDescriptionReadMore.addListener(SWT.Selection, e -> {
			lblDescriptionContent.setText(library.getDescription());
			linkDescriptionReadMore.setVisible(false);
		});

		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		lblDependenciesTitle = new Label(this, SWT.NONE);
		lblDependenciesTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		lblDependenciesTitle.setText("Dependencies");
		lblDependenciesTitle.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		new Label(this, SWT.NONE);

		lblDependenciesContent = new Label(this, SWT.WRAP | SWT.HORIZONTAL);
		lblDependenciesContent.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		lblDependenciesContent.setText("Content goes here");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblSimilarsTitle = new Label(this, SWT.NONE);
		lblSimilarsTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
		lblSimilarsTitle.setSize(103, 20);
		lblSimilarsTitle.setText("Similar projects");
		lblSimilarsTitle.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		lblSimilarsProjects = new Label(this, SWT.NONE);
		lblSimilarsProjects.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblSimilarsProjects.setSize(179, 45);
		lblSimilarsProjects.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		lblSimilarsProjects.setText("The first similar project's full name\n" + "The second similar project's name\n"
				+ "The third similar project's name");
		new Label(this, SWT.NONE);

		Link linkSimilarsSearch = new Link(this, SWT.NONE);
		linkSimilarsSearch.setSize(78, 15);
		linkSimilarsSearch.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		linkSimilarsSearch.setText("<a>Search similars with method</a>");
		
		comboSimilarityMethod = new CCombo(this, SWT.BORDER);
		comboSimilarityMethod.setItems(Arrays.stream(SimilarityMethod.values()).map(e -> e.toString()).toArray(String[]::new));
		comboSimilarityMethod.select(0);
		comboSimilarityMethod.setEditable(false);
		
		linkSimilarsSearch.addListener(SWT.Selection, e -> searchSimilars());
	}

	private void searchSimilars() {
		int selectionIndex = comboSimilarityMethod.getSelectionIndex();
		SimilarityMethod similarityMethod = SimilarityMethod.values()[selectionIndex];
		
		SimilarsRequestEvent event = new SimilarsRequestEvent(this, library, similarityMethod);
		getEventBus().post(event);
	}

	protected void openGitHub() {
		openInExternalBrowser(library.getGitUrl());
	}

	protected void openInExternalBrowser(String url) {
		try {
			PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(url));
		} catch (PartInitException | MalformedURLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void showDetails(Artifact library) {
		this.library = library;

		lblTitle.setText(library.getFullName());

		lblStarredBy.setText("Starred by " + library.getStarred().size() + " user.");

		linkCheckWebDashboard.setEnabled(library.getWebDashboardId() != null);

		String description = library.getDescription();
		if (description != null) {
			int numberOfChars = SHORT_DESCRIPTION_LENGTH;
			for (; numberOfChars < description.length(); numberOfChars++) {
				if (Character.isWhitespace(description.charAt(numberOfChars))) {
					break;
				}
			}
			description = description.substring(0, Math.min(description.length(), numberOfChars));

			if (numberOfChars <= SHORT_DESCRIPTION_LENGTH) {
				linkDescriptionReadMore.setVisible(false);
			}
		} else {
			description = "No description available.";
			linkDescriptionReadMore.setVisible(false);
		}

		lblDescriptionContent.setText(description);

		String dependencies = String.join("\n", library.getDependencies());
		lblDependenciesContent.setText(dependencies);
		
		requestLayout();
	}

	@Override
	public void showSimilars(List<LibraryListInfo> similarLibraries) {
		StringBuilder sb = new StringBuilder();

		similarLibraries.forEach(lib -> {
			sb.append(lib.getLibrary().getFullName());
			sb.append('\n');
		});

		lblSimilarsProjects.setText(sb.toString());
		
		requestLayout();
	}

}
