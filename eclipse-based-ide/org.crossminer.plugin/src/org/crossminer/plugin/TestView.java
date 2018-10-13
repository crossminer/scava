package org.crossminer.plugin;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

public class TestView extends ViewPart {

	public TestView() {
		try {
			getViewSite();
			IActionBars bars = getViewSite().getActionBars();
			bars.getStatusLineManager().setMessage("Szamos meno");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
