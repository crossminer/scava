package org.eclipse.scava.plugin.apidocumentation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.scava.plugin.mvc.implementation.rcp.RCPViewPartView;
import org.eclipse.scava.plugin.utils.ScrolledComposites;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wb.swt.SWTResourceManager;

public class ApiDocumentationView extends RCPViewPartView implements IApiDocumentationView {
	public static final String ID = "org.eclipse.scava.plugin.apidocumentation.ApiDocumentationView"; //$NON-NLS-1$
	private Composite resultListComposite;
	private ScrolledComposite scrolledComposite;
	
	/**
	 * @deprecated Do not instantiate manually
	 */
	@Deprecated()
	public ApiDocumentationView() {
		
	}
	
	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackgroundMode(SWT.INHERIT_DEFAULT);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		container.setLayout(new GridLayout(1, false));
		{
			Label lblNewLabel = new Label(container, SWT.NONE);
			lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
			lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
			lblNewLabel.setText("API documentation and QnA posts");
		}
		{
			scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			scrolledComposite.setExpandHorizontal(true);
			scrolledComposite.setExpandVertical(true);
			{
				resultListComposite = new Composite(scrolledComposite, SWT.NONE);
				resultListComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
				resultListComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				resultListComposite.setLayout(new GridLayout(1, false));
				{
					Label lblNoResultsWere = new Label(resultListComposite, SWT.NONE);
					lblNoResultsWere.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
					lblNoResultsWere.setText("No results were found");
				}
			}
			scrolledComposite.setContent(resultListComposite);
			scrolledComposite.setMinSize(resultListComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		}

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void showResults(List<WebResult> results) {
		if (!results.isEmpty()) {
			for (Control c : resultListComposite.getChildren()) {
				c.dispose();
			}

			results.forEach(r -> show(r));

			resultListComposite.requestLayout();
			ScrolledComposites.updateOnlyVerticalScrollableComposite(scrolledComposite);
		}
	}

	private void show(WebResult result) {
		Composite libraryComposite = new Composite(resultListComposite, SWT.BORDER);
		libraryComposite.setLayout(new GridLayout(1, false));
		libraryComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblName = new Label(libraryComposite, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblName.setText(result.getLabel());

		Link link = new Link(libraryComposite, SWT.NONE);
		link.setText("<a>Open in browser</a>");
		link.addListener(SWT.Selection, e -> {
			try {
				PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser()
						.openURL(new URL(result.getAddress()));
			} catch (PartInitException | MalformedURLException e1) {
				e1.printStackTrace();
			}
		});
	}

}
