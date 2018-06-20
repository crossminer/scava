package org.eclipse.scava.plugin.newcommunicationdemo;

import java.beans.BeanInfo;
import java.beans.Expression;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.preferences.Preferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import net.apispark.webapi.Config;
import net.apispark.webapi.Sdk;
import net.apispark.webapi.representation.Artifact;
import net.apispark.webapi.representation.ArtifactList;
import net.apispark.webapi.representation.Stargazers;
import net.apispark.webapi.resource.client.ApiArtifactsSearchArtifact_queryClientResource;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.wb.swt.SWTResourceManager;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import swing2swt.layout.BoxLayout;

public class ArtifactSearchView extends TitleAreaDialog {
	private Text searchField;
	private Composite artifactsPanel;
	private ScrolledComposite artifactsScrolledComposite;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public ArtifactSearchView(Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
		setShellStyle(SWT.SHELL_TRIM);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitleImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/SCAVA-logo-small2.png"));
		setMessage("search for artifacts by a keyword");
		setTitle("Artifact search");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label lblQueryString = new Label(container, SWT.NONE);
		lblQueryString.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblQueryString.setText("Search for:");

		searchField = new Text(container, SWT.BORDER);
		searchField.setText("android");
		searchField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		artifactsScrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.V_SCROLL);
		artifactsScrolledComposite.setExpandHorizontal(true);
		artifactsScrolledComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		artifactsScrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		artifactsScrolledComposite.setExpandVertical(true);
		
		artifactsPanel = new Composite(artifactsScrolledComposite, SWT.NONE);
		artifactsPanel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		artifactsPanel.setLayout(new GridLayout(1, true));
		
		artifactsScrolledComposite.setContent(artifactsPanel);
		artifactsScrolledComposite.setMinSize(artifactsPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.PROCEED_ID, "Search", true);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SearchClicked();
			}
		});
		button.setText("Search");
		Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		button_1.setText("Close");
	}

	protected void SearchClicked() {
		String queryStrign = searchField.getText();

		if (queryStrign.isEmpty()) {
			new MessageDialog(getParentShell(), "Error", null, "Query string can not be empty!", MessageDialog.ERROR, 0,
					"OK").open();
			return;
		}

		Sdk sdk = new Sdk();
		Config config = sdk.getConfig();

		String basePath = buildBasePath();

		config.setBasePath(basePath);
		ApiArtifactsSearchArtifact_queryClientResource resource = sdk.apiArtifactsSearchArtifact_query(queryStrign);
		ArtifactList artifactList = resource.getProjectUsingGET();
		
		clearArtifacts();
		showArtifacts(artifactList);
	}


	private void clearArtifacts() {
		Control[] children = artifactsPanel.getChildren();
		for (Control control : children) {
			control.dispose();
		}
	}

	private void showArtifacts(ArtifactList artifactList) {
		artifactList.forEach(artifact -> {
			ArtifactPanel artifactPanel = new ArtifactPanel(artifact, () -> { refreshScrollComposite(); }, artifactsPanel, SWT.FILL);
			artifactPanel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		});
		
		refreshScrollComposite();
		
	}

	private void refreshScrollComposite() {
		artifactsScrolledComposite.layout(true, true);
		artifactsScrolledComposite.setMinSize(artifactsPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private String buildBasePath() {
		// It is disabled, because the dummy server and the KB needs different addresses
		/*String basePath = "";
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();

		// Host address
		String serverAddress = preferences.getString(Preferences.SERVER_ADDRESS);
		basePath += serverAddress;

		basePath += ":";

		// Port
		int serverPort = preferences.getInt(Preferences.SERVER_PORT);
		basePath += serverPort;

		return basePath;*/
		return "http://10.6.13.58:8080";
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Artifact search");
		newShell.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/SCAVA-icon-32.png"));
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(666, 497);
	}
}
