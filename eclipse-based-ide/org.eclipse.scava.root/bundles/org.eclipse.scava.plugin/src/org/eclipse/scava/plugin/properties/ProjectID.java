package org.eclipse.scava.plugin.properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.scava.plugin.usermonitoring.ProjectStructureHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

public class ProjectID extends PropertyPage {
	private Text textID;

	/**
	 * Create the property page.
	 */
	public ProjectID() {
	}

	/**
	 * Create contents of the property page.
	 * 
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(2, false));

		Label lblGithub = new Label(container, SWT.NONE);
		lblGithub.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblGithub.setText("GitHub URL");

		textID = new Text(container, SWT.BORDER);
		textID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		try {
			initializeDefaults();
			initializeValues();
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return container;
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		try {
			initializeDefaults();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean performOk() {

		try {
			storeValues();
			ProjectStructureHandler.addProjectToAnalise(((IJavaProject) getElement()).getProject());

		} catch (CoreException e) {
			e.printStackTrace();
		}
		return super.performOk();
	}

	private void initializeValues() throws CoreException {
		IResource resource = ((IJavaProject) getElement()).getProject();
		String githubUrl = resource.getPersistentProperty(Properties.PROJECT_GITHUB_URL);
		if (githubUrl != null) {
			textID.setText(githubUrl);
		}
	}

	private void initializeDefaults() throws CoreException {
		textID.setText("");
	}

	private void storeValues() throws CoreException {
		IResource resource = ((IJavaProject) getElement()).getProject();
		resource.setPersistentProperty(Properties.PROJECT_GITHUB_URL, textID.getText());
	}

}
