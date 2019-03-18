package org.eclipse.scava.plugin.librarysearch.list;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import io.swagger.client.model.Artifact;

public class LibraryListElement extends Composite {
	private Label lblComment;
	private Button btnAction;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public LibraryListElement(Composite parent, LibraryListInfo libraryInfo) {
		super(parent, SWT.BORDER);
		setBackground(SWTResourceManager.getColor(248, 248, 255));
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setLayout(new GridLayout(2, false));

		Artifact library = libraryInfo.getLibrary();

		Label lblTheLibrarysTitle = new Label(this, SWT.WRAP);
		lblTheLibrarysTitle.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		lblTheLibrarysTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblTheLibrarysTitle.setText(library.getFullName());

		btnAction = new Button(this, SWT.CENTER);
		btnAction.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		btnAction.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 2));
		btnAction.setText(libraryInfo.getActionLabel());
		btnAction.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				libraryInfo.getAction().run();
			}

		});
		btnAction.setVisible(!libraryInfo.getActionLabel().isEmpty());
		btnAction.setEnabled(libraryInfo.getAction() != LibraryListInfo.DEFAULT_ACTION);

		Composite detailsComposite = new Composite(this, SWT.NONE);
		GridData gd_detailsComposite = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_detailsComposite.exclude = libraryInfo.isShowCompact();
		detailsComposite.setLayoutData(gd_detailsComposite);
		detailsComposite.setLayout(new GridLayout(2, false));

		Label lblStarred = new Label(detailsComposite, SWT.NONE);
		lblStarred.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblStarred.setSize(40, 13);
		lblStarred.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblStarred.setText("Starred:");

		Label label = new Label(detailsComposite, SWT.NONE);
		label.setSize(6, 13);
		label.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		label.setText(Integer.toString(library.getStarred().size()));

		Label lblDependencies = new Label(detailsComposite, SWT.NONE);
		lblDependencies.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDependencies.setSize(76, 13);
		lblDependencies.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblDependencies.setText("Dependencies:");

		Label label_2 = new Label(detailsComposite, SWT.NONE);
		label_2.setSize(6, 13);
		label_2.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		label_2.setText(Integer.toString(library.getDependencies().size()));
		
				lblComment = new Label(this, SWT.NONE);
				lblComment.setText(libraryInfo.getComment());
				lblComment.setAlignment(SWT.RIGHT);
				lblComment.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
				lblComment.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC));
				GridData gd_lblComment = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
				gd_lblComment.exclude = libraryInfo.getComment().isEmpty();
				lblComment.setLayoutData(gd_lblComment);

		this.setCursor(new Cursor(getDisplay(), SWT.CURSOR_HAND));
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void registerSelectionListeners(MouseListener listener) {
		registerListenerOn(this, listener);
	}

	private void registerListenerOn(Control control, MouseListener listener) {
		if (control instanceof Button) {
			return;
		}

		control.addMouseListener(listener);

		if (control instanceof Composite) {
			Composite composite = (Composite) control;
			for (Control child : composite.getChildren()) {
				registerListenerOn(child, listener);
			}
		}
	}
}
