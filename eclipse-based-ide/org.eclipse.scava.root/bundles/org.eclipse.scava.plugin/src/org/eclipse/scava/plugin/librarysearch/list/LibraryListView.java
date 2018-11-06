package org.eclipse.scava.plugin.librarysearch.list;

import java.util.List;

import org.eclipse.scava.plugin.mvc.implementation.swt.SWTCompositeView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;

public class LibraryListView extends SWTCompositeView implements ILibraryListView {
	private Composite librariesComposite;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public LibraryListView(String emptyString) {
		super(SWT.NONE);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		librariesComposite = new Composite(this, SWT.NONE);
		librariesComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		librariesComposite.setBackgroundMode(SWT.DEFAULT);
		librariesComposite.setLayout(new GridLayout(1, false));

		Label lblEmpty = new Label(librariesComposite, SWT.WRAP);
		lblEmpty.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lblEmpty.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		lblEmpty.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblEmpty.setText(emptyString);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void show(List<LibraryListInfo> libraries) {
		if (!libraries.isEmpty()) {
			clearLibraries();

			libraries.forEach(libraryInfo -> show(libraryInfo));
		}

		requestLayout();
	}

	private void show(LibraryListInfo libraryInfo) {
		LibraryListElement element = new LibraryListElement(librariesComposite, libraryInfo);
		element.registerSelectionListeners(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				LibrarySelectionEvent event = new LibrarySelectionEvent(LibraryListView.this, libraryInfo.getLibrary());
				getEventBus().post(event);
			}

		});
		element.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

	}

	private void clearLibraries() {
		for (Control child : librariesComposite.getChildren()) {
			child.dispose();
		}
	}
}
