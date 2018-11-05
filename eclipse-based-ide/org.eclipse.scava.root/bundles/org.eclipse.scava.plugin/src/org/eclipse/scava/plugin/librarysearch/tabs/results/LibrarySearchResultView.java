package org.eclipse.scava.plugin.librarysearch.tabs.results;

import org.eclipse.scava.plugin.librarysearch.details.ILibraryDetailsView;
import org.eclipse.scava.plugin.librarysearch.list.ILibraryListView;
import org.eclipse.scava.plugin.mvc.implementation.swt.IHasComposite;
import org.eclipse.scava.plugin.mvc.implementation.swt.SWTCompositeView;
import org.eclipse.scava.plugin.utils.ScrolledComposites;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class LibrarySearchResultView extends SWTCompositeView implements ILibrarySearchResultView {
	private Label lblDescription;
	private ScrolledComposite detailsScrolledComposite;
	private ScrolledComposite resultsScrolledComposite;
	private Composite resultListComposite;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public LibrarySearchResultView() {
		super(SWT.NONE);
		setBackground(SWTResourceManager.getColor(255, 255, 255));
		setLayout(new GridLayout(2, false));

		ControlAdapter updateScrolledCompositeOnResize = new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				ScrolledComposites.updateOnlyVerticalScrollableComposite((ScrolledComposite) e.getSource());
			}
		};

		resultsScrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_resultsScrolledComposite = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
		gd_resultsScrolledComposite.widthHint = 226;
		resultsScrolledComposite.setLayoutData(gd_resultsScrolledComposite);
		resultsScrolledComposite.setExpandHorizontal(true);
		resultsScrolledComposite.setExpandVertical(true);
		resultsScrolledComposite.addControlListener(updateScrolledCompositeOnResize);

		Composite composite = new Composite(resultsScrolledComposite, SWT.NONE);
		composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		composite.setBackground(SWTResourceManager.getColor(255, 255, 255));
		composite.setLayout(new GridLayout(1, false));

		lblDescription = new Label(composite, SWT.WRAP);
		lblDescription.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDescription.setText("Description");

		Label lblNewLabel_1 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblNewLabel_1.setText("New Label");

		resultListComposite = new Composite(composite, SWT.NONE);
		resultListComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		resultListComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		resultsScrolledComposite.setContent(composite);
		resultsScrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		detailsScrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.V_SCROLL);
		detailsScrolledComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		detailsScrolledComposite.setBackground(SWTResourceManager.getColor(255, 255, 255));
		detailsScrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		detailsScrolledComposite.setExpandHorizontal(true);
		detailsScrolledComposite.setExpandVertical(true);
		detailsScrolledComposite.addControlListener(updateScrolledCompositeOnResize);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void setDescription(String description) {
		lblDescription.setText(description);
	}

	@Override
	public void showLibraries(ILibraryListView view) {
		Composite composite = ((IHasComposite) view).getComposite();
		composite.setParent(resultListComposite);

		composite.requestLayout();
		ScrolledComposites.updateOnlyVerticalScrollableComposite(resultsScrolledComposite);
	}

	@Override
	public void showDetails(ILibraryDetailsView view) {
		Composite composite = ((IHasComposite) view).getComposite();

		composite.setParent(detailsScrolledComposite);
		detailsScrolledComposite.setContent(composite);
		ScrolledComposites.updateOnlyVerticalScrollableComposite(detailsScrolledComposite);
	}
}
