package org.eclipse.scava.plugin.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class Main extends PropertyPage {

	/**
	 * Create the property page.
	 */
	public Main() {
	}

	/**
	 * Create contents of the property page.
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(1, false));
		
		Label lblCrossminerRelatedProperties = new Label(container, SWT.NONE);
		lblCrossminerRelatedProperties.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lblCrossminerRelatedProperties.setFont(SWTResourceManager.getFont("Segoe UI", 17, SWT.BOLD));
		lblCrossminerRelatedProperties.setText("CROSSMINER related properties");

		return container;
	}
}
