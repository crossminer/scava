package org.eclipse.scava.plugin.librarysearch.tabs.finish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scava.plugin.mvc.implementation.swt.SWTCompositeView;
import org.eclipse.scava.plugin.utils.ScrolledComposites;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class LibrarySearchFinishView extends SWTCompositeView implements ILibrarySearchFinishView {
	private Composite currentlyUsedLibrariesComposite;
	private ScrolledComposite currentlyUsedLibrariesScrolledComposite;
	private Button btnSelectAllCurrentlyUsedLibraries;
	private Label lblMissingPom;
	private Button btnSearchForAdditional;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public LibrarySearchFinishView() {
		super(SWT.NONE);
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setBackground(SWTResourceManager.getColor(255, 255, 255));
		setLayout(new GridLayout(1, false));

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Label lblMaybeWeCan = new Label(composite, SWT.WRAP);
		lblMaybeWeCan.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		lblMaybeWeCan.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblMaybeWeCan.setText(
				"Maybe, we can offer you some additional libraries, that you might find useful in your project.");
		new Label(composite, SWT.NONE);

		new Label(composite, SWT.NONE);

		Label lblSelectWhichLibraries = new Label(composite, SWT.WRAP);
		lblSelectWhichLibraries.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblSelectWhichLibraries.setText("Select the libraries, that the search should base on:");

		Label lblLibrariesThatAr = new Label(composite, SWT.WRAP);
		lblLibrariesThatAr.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));
		GridData gd_lblLibrariesThatAr = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_lblLibrariesThatAr.widthHint = 193;
		lblLibrariesThatAr.setLayoutData(gd_lblLibrariesThatAr);
		lblLibrariesThatAr.setText("Currently used libraries");

		currentlyUsedLibrariesScrolledComposite = new ScrolledComposite(composite, SWT.BORDER | SWT.V_SCROLL);
		currentlyUsedLibrariesScrolledComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		currentlyUsedLibrariesScrolledComposite.setBackground(SWTResourceManager.getColor(255, 255, 255));
		GridData gd_currentlyUsedLibrariesScrolledComposite = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_currentlyUsedLibrariesScrolledComposite.widthHint = 118;
		currentlyUsedLibrariesScrolledComposite.setLayoutData(gd_currentlyUsedLibrariesScrolledComposite);
		currentlyUsedLibrariesScrolledComposite.setExpandHorizontal(true);
		currentlyUsedLibrariesScrolledComposite.setExpandVertical(true);

		currentlyUsedLibrariesComposite = new Composite(currentlyUsedLibrariesScrolledComposite, SWT.NONE);
		currentlyUsedLibrariesComposite.setLayout(new GridLayout(1, false));
		currentlyUsedLibrariesScrolledComposite.setContent(currentlyUsedLibrariesComposite);
		currentlyUsedLibrariesScrolledComposite
				.setMinSize(currentlyUsedLibrariesComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		btnSelectAllCurrentlyUsedLibraries = new Button(composite, SWT.CHECK);
		btnSelectAllCurrentlyUsedLibraries.setEnabled(false);
		btnSelectAllCurrentlyUsedLibraries.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnSelectAllCurrentlyUsedLibraries.setSelection(true);
		btnSelectAllCurrentlyUsedLibraries.setText("Select all");
		btnSelectAllCurrentlyUsedLibraries.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				checkAllChildren(btnSelectAllCurrentlyUsedLibraries, currentlyUsedLibrariesComposite);
			}

		});

		lblMissingPom = new Label(composite, SWT.NONE);
		lblMissingPom.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		GridData gd_lblMissingPom = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_lblMissingPom.widthHint = 627;
		lblMissingPom.setLayoutData(gd_lblMissingPom);
		lblMissingPom.setText("Selected project does not contain a pom.xml file.");

		btnSearchForAdditional = new Button(composite, SWT.NONE);
		btnSearchForAdditional.setEnabled(false);
		btnSearchForAdditional.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnSearchForAdditional.setText("Search for additional libraries");
		btnSearchForAdditional.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				onAdditionalSearchClicked();
			}

		});

		Label lblNewLabel = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayout(new GridLayout(2, false));
		GridData gd_composite_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_composite_1.heightHint = 67;
		composite_1.setLayoutData(gd_composite_1);

		Label lblOrYouCan = new Label(composite_1, SWT.WRAP);
		lblOrYouCan.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		GridData gd_lblOrYouCan = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lblOrYouCan.widthHint = 263;
		lblOrYouCan.setLayoutData(gd_lblOrYouCan);
		lblOrYouCan.setText("Or you can just simply install the selected libraries to your project.");

		Button btnInstallSelectedLibraries = new Button(composite_1, SWT.NONE);
		btnInstallSelectedLibraries.setToolTipText("");
		btnInstallSelectedLibraries.setEnabled(false);
		btnInstallSelectedLibraries.setForeground(SWTResourceManager.getColor(0, 0, 255));
		GridData gd_btnInstallSelectedLibraries = new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1);
		gd_btnInstallSelectedLibraries.widthHint = 150;
		btnInstallSelectedLibraries.setLayoutData(gd_btnInstallSelectedLibraries);
		btnInstallSelectedLibraries.setText("Install selected libraries");

	}

	/**
	 * 
	 */
	private void onAdditionalSearchClicked() {
		Control[] children = currentlyUsedLibrariesComposite.getChildren();
		List<String> selectedLibraries = Arrays.stream(children).map(child -> (Button) child)
				.filter(child -> child.getSelection()).map(child -> child.getText()).collect(Collectors.toList());

		ILibrarySearchFinishView.RecommendedSearchRequestEvent event = new ILibrarySearchFinishView.RecommendedSearchRequestEvent(
				this, selectedLibraries);
		getEventBus().post(event);
	}

	private void checkAllChildren(Button currentButton, Composite composite) {
		currentButton.setGrayed(false);
		for (Control child : composite.getChildren()) {
			if (child instanceof Button) {
				Button button = (Button) child;
				button.setSelection(currentButton.getSelection());
			}
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void showCurrentlyUsedLibrares(List<String> libraries) {
		showListOn(libraries, currentlyUsedLibrariesComposite, btnSelectAllCurrentlyUsedLibraries);

		ScrolledComposites.updateOnlyVerticalScrollableComposite(currentlyUsedLibrariesScrolledComposite);

		btnSelectAllCurrentlyUsedLibraries.setEnabled(true);
		btnSearchForAdditional.setEnabled(true);
		lblMissingPom.setVisible(false);

		requestLayout();
	}

	private void showListOn(List<String> list, Composite parent, Button checkAll) {
		for (Control child : parent.getChildren())
			child.dispose();

		list.forEach(element -> showOn(element, parent, checkAll));

		parent.requestLayout();
	}

	private void showOn(String label, Composite composite, Button checkAll) {
		Button btnEls = new Button(composite, SWT.CHECK);
		btnEls.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnEls.setText(label);
		btnEls.setSelection(true);
		btnEls.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isAnyChildrenChecked = isAnyChildrenChecked(composite);
				boolean isAllChildrenChecked = isAllChildrenChecked(composite);
				checkAll.setSelection(isAnyChildrenChecked);
				checkAll.setGrayed(!isAllChildrenChecked && isAnyChildrenChecked);
			}

		});
	}

	private boolean isAllChildrenChecked(Composite composite) {
		for (Control child : composite.getChildren()) {
			if (child instanceof Button) {
				Button button = (Button) child;
				if (!button.getSelection()) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isAnyChildrenChecked(Composite composite) {
		for (Control child : composite.getChildren()) {
			if (child instanceof Button) {
				Button button = (Button) child;
				if (button.getSelection()) {
					return true;
				}
			}
		}
		return false;
	}
}
