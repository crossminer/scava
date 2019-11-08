/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.properties;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.scava.plugin.libraryversions.LibraryFilterRule;
import org.eclipse.scava.plugin.ui.verticalList.VerticalList;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.wb.swt.SWTResourceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LibraryUpdateVersionRules extends PropertyPage {
	private Text text;
	private Text text_1;
	private Text text_2;
	private ScrolledComposite rulesScrolledComposite;
	private VerticalList rulesList;
	private List<Rule> rules = new ArrayList<>();

	/**
	 * Create the property page.
	 */
	public LibraryUpdateVersionRules() {
	}

	/**
	 * Create contents of the property page.
	 * 
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(1, false));

		Label lblPleaseUseRegular = new Label(container, SWT.WRAP);
		GridData gd_lblPleaseUseRegular = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_lblPleaseUseRegular.widthHint = 532;
		lblPleaseUseRegular.setLayoutData(gd_lblPleaseUseRegular);
		lblPleaseUseRegular.setText(
				"Please use regular expressions to define the libraries for which you want to be notified of recent versions.");

		rulesScrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.V_SCROLL);
		rulesScrolledComposite.setAlwaysShowScrollBars(true);
		rulesScrolledComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		rulesScrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		rulesScrolledComposite.setExpandHorizontal(true);
		rulesScrolledComposite.setExpandVertical(true);

		rulesList = new VerticalList(rulesScrolledComposite, SWT.NONE);

		rulesScrolledComposite.setContent(rulesList);
		rulesScrolledComposite.setMinSize(rulesList.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Button btnAddNewRule = new Button(container, SWT.NONE);
		btnAddNewRule.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addRule();
			}
		});
		btnAddNewRule.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnAddNewRule.setText("Add new rule");

		try {
			initializeDefaults();
			initializeValues();
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return container;
	}

	private Rule addRule() {
		Composite composite = new Composite(rulesList, SWT.BORDER);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite.setLayout(new GridLayout(3, false));

		Label lblRule = new Label(composite, SWT.NONE);
		lblRule.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblRule.setText("Rule");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		Label lblGroupId = new Label(composite, SWT.NONE);
		lblGroupId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblGroupId.setText("Group ID");

		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE);

		Label lblArtifactId = new Label(composite, SWT.NONE);
		lblArtifactId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblArtifactId.setText("Artifact ID");

		text_1 = new Text(composite, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE);

		Label lblVersion = new Label(composite, SWT.NONE);
		lblVersion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblVersion.setText("Version");

		text_2 = new Text(composite, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnDeleteRule = new Button(composite, SWT.NONE);
		btnDeleteRule.setText("Delete rule");

		rulesList.add(composite);

		Rule rule = new Rule(composite, text, text_1, text_2);
		rules.add(rule);

		btnDeleteRule.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				composite.dispose();
				rules.remove(rule);
			}
		});

		return rule;
	}

	private class Rule {
		final Composite composite;
		final Text groupId;
		final Text artifactId;
		final Text version;

		public Rule(Composite composite, Text groupId, Text artifactId, Text version) {
			super();
			this.composite = composite;
			this.groupId = groupId;
			this.artifactId = artifactId;
			this.version = version;
		}

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

		} catch (CoreException e) {
			e.printStackTrace();
		}
		return super.performOk();
	}

	private void initializeValues() throws CoreException {
		IResource resource = ((IJavaProject) getElement()).getProject();
		String json = resource.getPersistentProperty(Properties.LIBRARY_UPDATE_VERSION_RULES);
		if (json != null) {
			rules.forEach(r -> r.composite.dispose());
			rules.clear();

			Type listType = new TypeToken<ArrayList<LibraryFilterRule>>() {
			}.getType();
			List<LibraryFilterRule> loadedRules = new Gson().fromJson(json, listType);
			loadedRules.forEach(loaded -> {
				Rule rule = addRule();
				rule.groupId.setText(loaded.getGroupId());
				rule.artifactId.setText(loaded.getArtifactId());
				rule.version.setText(loaded.getVersion());
			});
		}
	}

	private void initializeDefaults() throws CoreException {
		rules.forEach(rule -> rule.composite.dispose());
		rules.clear();
	}

	private void storeValues() throws CoreException {
		List<LibraryFilterRule> mapped = rules.stream().map(rule -> new LibraryFilterRule(rule.groupId.getText(),
				rule.artifactId.getText(), rule.version.getText())).collect(Collectors.toList());
		String json = new Gson().toJson(mapped);

		IResource resource = ((IJavaProject) getElement()).getProject();
		resource.setPersistentProperty(Properties.LIBRARY_UPDATE_VERSION_RULES, json);
	}
}
