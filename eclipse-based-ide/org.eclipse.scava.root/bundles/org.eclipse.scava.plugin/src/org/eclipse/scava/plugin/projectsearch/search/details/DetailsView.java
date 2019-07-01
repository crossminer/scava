/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.details;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.scava.plugin.knowledgebase.access.SimilarityMethod;
import org.eclipse.scava.plugin.mvc.event.direct.IEventInvoker;
import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import io.swagger.client.model.Artifact;
import io.swagger.client.model.ArtifactType;
import io.swagger.client.model.Tag;

public class DetailsView extends CompositeView<IDetailsViewEventListener> {
	private Composite descriptionComposite;
	private Composite parametersComposite;
	private Composite dependenciesComposite;
	private Composite subprojectsComposite;
	private Label lblName;
	private Composite leftColumnComposite;
	private Composite bodyComposite;
	private CLabel lblCheckOnGithub;
	private CLabel lblCheckOnWeb;
	private CLabel lblCheckOnWebdashboard;
	private CLabel lblCompound;
	private CLabel lblCrossim;
	private CLabel lblDependency;
	private CLabel lblCrossrec;
	private CLabel lblReadme;
	private CLabel lblRepopalcompound;
	private CLabel lblRepopalcompoundv;
	private CLabel lblNoDescriptionFound;
	private Label lblDescription;
	private Label lblAsd;
	private Label lblAaaaa;
	private Text txtAsd;
	private StyledText styledText;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public DetailsView() {
		super(SWT.NONE);
		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				layout();
			}
		});
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		setLayout(gridLayout);

		Composite headerComposite = new Composite(this, SWT.NONE);
		headerComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		headerComposite.setBackground(SWTResourceManager.getColor(8, 117, 184));
		FillLayout fl_headerComposite = new FillLayout(SWT.HORIZONTAL);
		fl_headerComposite.marginWidth = 15;
		fl_headerComposite.marginHeight = 10;
		headerComposite.setLayout(fl_headerComposite);
		headerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblName = new Label(headerComposite, SWT.WRAP);
		lblName.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblName.setForeground(SWTResourceManager.getColor(255, 255, 255));
		lblName.setText(
				"Here comes the really really long full name of the project which can be so long that it must be wrapped into a new line");

		bodyComposite = new Composite(this, SWT.NONE);
		bodyComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		bodyComposite.setLayout(new GridLayout(2, false));

		leftColumnComposite = new Composite(bodyComposite, SWT.NONE);
		leftColumnComposite.setLayout(new GridLayout(1, false));
		leftColumnComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		Label lblDescriptionreadme = new Label(leftColumnComposite, SWT.NONE);
		lblDescriptionreadme.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblDescriptionreadme.setText("Description (readme)");

		descriptionComposite = new Composite(leftColumnComposite, SWT.NONE);
		descriptionComposite.setLayout(new GridLayout(1, false));
		descriptionComposite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false, 1, 1));

		lblNoDescriptionFound = new CLabel(descriptionComposite, SWT.NONE);
		GridData gd_lblNoDescriptionFound = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNoDescriptionFound.exclude = true;
		lblNoDescriptionFound.setLayoutData(gd_lblNoDescriptionFound);
		lblNoDescriptionFound.setText("?");
		
		lblDescription = new Label(descriptionComposite, SWT.WRAP | SWT.HORIZONTAL);
		lblDescription.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));

		Label lblParameters = new Label(leftColumnComposite, SWT.NONE);
		lblParameters.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblParameters.setText("Parameters");

		parametersComposite = new Composite(leftColumnComposite, SWT.NONE);
		GridLayout gl_parametersComposite = new GridLayout(4, false);
		gl_parametersComposite.verticalSpacing = 0;
		gl_parametersComposite.horizontalSpacing = 0;
		parametersComposite.setLayout(gl_parametersComposite);
		parametersComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label lblDependencies = new Label(leftColumnComposite, SWT.NONE);
		lblDependencies.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblDependencies.setText("Dependencies");

		dependenciesComposite = new Composite(leftColumnComposite, SWT.NONE);
		dependenciesComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		dependenciesComposite.setBounds(0, 0, 224, 10);
		GridLayout gl_dependenciesComposite = new GridLayout(2, false);
		gl_dependenciesComposite.horizontalSpacing = 0;
		gl_dependenciesComposite.verticalSpacing = 0;
		dependenciesComposite.setLayout(gl_dependenciesComposite);

		Label lblSubprojects = new Label(leftColumnComposite, SWT.NONE);
		lblSubprojects.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblSubprojects.setText("Subprojects");

		subprojectsComposite = new Composite(leftColumnComposite, SWT.NONE);
		GridLayout gl_subprojectsComposite = new GridLayout(2, false);
		gl_subprojectsComposite.horizontalSpacing = 0;
		gl_subprojectsComposite.verticalSpacing = 0;
		subprojectsComposite.setLayout(gl_subprojectsComposite);
		subprojectsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		Composite rightColumnComposite = new Composite(bodyComposite, SWT.NONE);
		rightColumnComposite.setLayout(new GridLayout(1, false));
		rightColumnComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		lblCheckOnGithub = new CLabel(rightColumnComposite, SWT.NONE);
		lblCheckOnGithub.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/sites/github_inactive_24x24.png"));
		lblCheckOnGithub.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblCheckOnGithub.setForeground(SWTResourceManager.getColor(126, 126, 126));
		lblCheckOnGithub.setText("Check on Github");

		lblCheckOnWeb = new CLabel(rightColumnComposite, SWT.NONE);
		lblCheckOnWeb.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/sites/web_inactive_24x24.png"));
		lblCheckOnWeb.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblCheckOnWeb.setForeground(SWTResourceManager.getColor(126, 126, 126));
		lblCheckOnWeb.setText("Check on Web");

		lblCheckOnWebdashboard = new CLabel(rightColumnComposite, SWT.NONE);
		lblCheckOnWebdashboard.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/sites/dashboard_inactive_24x24.png"));
		lblCheckOnWebdashboard.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblCheckOnWebdashboard.setForeground(SWTResourceManager.getColor(126, 126, 126));
		lblCheckOnWebdashboard.setText("Check on WebDashboard");

		Composite searchSimilarsComposite = new Composite(rightColumnComposite, SWT.NONE);
		GridLayout gl_searchSimilarsComposite = new GridLayout(1, false);
		gl_searchSimilarsComposite.marginWidth = 0;
		searchSimilarsComposite.setLayout(gl_searchSimilarsComposite);

		Label lblSearchSimilarsWithMethod = new Label(searchSimilarsComposite, SWT.NONE);
		lblSearchSimilarsWithMethod.setText("Search similars with method");
		lblSearchSimilarsWithMethod.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));

		lblCompound = new CLabel(searchSimilarsComposite, SWT.NONE);
		lblCompound.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/similarityMethod/compound_24x24.png"));
		lblCompound.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblCompound.setText("Compound");

		lblCrossim = new CLabel(searchSimilarsComposite, SWT.NONE);
		lblCrossim.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/similarityMethod/crossim_24x24.png"));
		lblCrossim.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblCrossim.setText("Crossim");

		lblDependency = new CLabel(searchSimilarsComposite, SWT.NONE);
		lblDependency.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/similarityMethod/dependencies_24x24.png"));
		lblDependency.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblDependency.setText("Dependency");

		lblCrossrec = new CLabel(searchSimilarsComposite, SWT.NONE);
		lblCrossrec.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/similarityMethod/crossrec_24x24.png"));
		lblCrossrec.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblCrossrec.setText("Crossrec");

		lblReadme = new CLabel(searchSimilarsComposite, SWT.NONE);
		lblReadme.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/similarityMethod/readme_24x24.png"));
		lblReadme.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblReadme.setText("Readme");

		lblRepopalcompound = new CLabel(searchSimilarsComposite, SWT.NONE);
		lblRepopalcompound.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/similarityMethod/repopal_24x24.png"));
		lblRepopalcompound.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblRepopalcompound.setText("RepoPalCompound");

		lblRepopalcompoundv = new CLabel(searchSimilarsComposite, SWT.NONE);
		lblRepopalcompoundv.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/similarityMethod/repopalv2_24x24.png"));
		lblRepopalcompoundv.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblRepopalcompoundv.setText("RepoPalCompoundV2");
		
	}
	
	private class Row {
		private final String[] params;

		public Row(String... params) {
			super();
			this.params = params;
		}
		
		public void insert(Composite parent, int numberOfRow) {
			for (int i = 0; i < params.length; i++) {
				String param = params[i];
				
				new Label(parent, SWT.NONE).setText("");
								
				StyledText lbl = new StyledText(parent, SWT.FULL_SELECTION | SWT.READ_ONLY | SWT.WRAP);
				lbl.setAlwaysShowScrollBars(false);
				lbl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
				if( param.matches("^[a-zA-Z0-9]+:\\/\\/.*$") ) {
					lbl.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.UNDERLINE_LINK));
					lbl.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
					lbl.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
					lbl.addMouseListener(new MouseAdapter() {

						@Override
						public void mouseDown(MouseEvent e) {
							eventManager.invoke(l -> l.onOpenInBrowserRequest(param));
						}
					});
				}else {
					lbl.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
				}
				lbl.setText(param);
				
				if (numberOfRow % 2 == 0) {
					lbl.setBackground(SWTResourceManager.getColor(206, 234, 255));
				}
			}
		}
	}

	private void insertRows(Composite parent, Row... rows) {
		insertRows(parent, Arrays.asList(rows));
	}
	
	private void insertRows(Composite parent, Iterable<Row> rows) {
		int num = 0;
		for (Row row : rows) {
			row.insert(parent, num++);
		}
		
		layout();
	}

	@Override
	protected void checkSubclass() {
		
	}
	
	public void setProject(Artifact project) {
		lblName.setText(Optional.ofNullable(project.getFullName()).orElse("-"));
				
		insertRows(parametersComposite,
				new Row("ID", Optional.ofNullable(project.getId()).orElse("-")),
				new Row("Tags", Optional.ofNullable(project.getTags()).map(l -> l.stream().map(Tag::getTag).collect(Collectors.joining("; "))).orElse("-")),
				new Row("Name", Optional.ofNullable(project.getName()).orElse("-")),
				new Row("Short name", Optional.ofNullable(project.getShortName()).orElse("-")),
				new Row("Year", Optional.ofNullable(project.getYear()).filter(y -> y != 0).map(y -> Integer.toString(y)).orElse("-")),
				new Row("Active", Optional.ofNullable(project.isActive()).map(a -> a ? "yes" : "no").orElse("-")),
				new Row("Homepage", Optional.ofNullable(project.getHomePage()).orElse("-")),
				new Row("Type", Optional.ofNullable(project.getType()).map(ArtifactType::getName).orElse("-")),
				new Row("Is it private?", Optional.ofNullable(project.isPrivate_()).map(p -> p ? "yes" : "no").orElse("-")),
				new Row("Is it a fork?", Optional.ofNullable(project.isFork()).map(f -> f ? "yes" : "no").orElse("-")),
				new Row("HTML URL", Optional.ofNullable(project.getHtmlUrl()).orElse("-")),
				new Row("Clone URL", Optional.ofNullable(project.getCloneUrl()).orElse("-")),
				new Row("Git URL", Optional.ofNullable(project.getGitUrl()).orElse("-")),
				new Row("SSH url", Optional.ofNullable(project.getSshUrl()).orElse("-")),
				new Row("SVN url", Optional.ofNullable(project.getSvnUrl()).orElse("-")),
				new Row("Mirror url", Optional.ofNullable(project.getMirrorUrl()).orElse("-")),
				new Row("Size", Optional.ofNullable(project.getSize()).filter(s -> s != 0).map(s -> Long.toString(s)).orElse("-")),
				new Row("Master branch", Optional.ofNullable(project.getMasterBranch()).orElse("-")),
				new Row("Web dashboard ID", Optional.ofNullable(project.getWebDashboardId()).orElse("-")),
				new Row("Metric platform ID", Optional.ofNullable(project.getMetricPlatformId()).orElse("-")),
				new Row("List of committers", "currently not supported on client side"));
				
		List<String> dependencies = project.getDependencies();
		if( dependencies == null ) {
			insertRows(dependenciesComposite, new Row("-"));
		}else {
			insertRows(dependenciesComposite, dependencies.stream().map(d -> new Row(d)).collect(Collectors.toList()));
		}
		
		insertRows(subprojectsComposite, new Row("currently not supported on client side"));
		
		if( project.getHtmlUrl() != null ) {
			setLink(lblCheckOnGithub, l -> l.onCheckOnGithub());
			lblCheckOnGithub.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/sites/github_icon_24x24.png"));
			lblCheckOnGithub.setForeground(null);
		}
		if(project.getHomePage() != null && !project.getHomePage().isEmpty()) {
			setLink(lblCheckOnWeb, l -> l.onCheckOnWeb());
			lblCheckOnWeb.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/sites/web_icon_24x24.png"));
			lblCheckOnWeb.setForeground(null);
		}
		if( project.getWebDashboardId() != null ) {
			setLink(lblCheckOnWebdashboard, l -> l.onCheckOnWebDashboard());
			lblCheckOnWebdashboard.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/sites/dashboard_icon_24x24.png"));
			lblCheckOnWebdashboard.setForeground(null);
		}
		
		setLink(lblCompound, l -> l.onSearchSimilars(SimilarityMethod.Compound));
		setLink(lblCrossim, l -> l.onSearchSimilars(SimilarityMethod.CrossSim));
		setLink(lblDependency, l -> l.onSearchSimilars(SimilarityMethod.Dependency));
		setLink(lblCrossrec, l -> l.onSearchSimilars(SimilarityMethod.CrossRec));
		setLink(lblReadme, l -> l.onSearchSimilars(SimilarityMethod.Readme));
		setLink(lblRepopalcompound, l -> l.onSearchSimilars(SimilarityMethod.RepoPalCompound));
		setLink(lblRepopalcompoundv, l -> l.onSearchSimilars(SimilarityMethod.RepoPalCompoundV2));
		
		String description = project.getDescription();
		if( description != null ) {
			lblDescription.setText(description);
		}else {
			((GridData) lblNoDescriptionFound.getLayoutData()).exclude = false;
		}
		
		layout();
	}
	
	private void setLink(CLabel label, IEventInvoker<IDetailsViewEventListener> invoker) {
		label.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		label.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				eventManager.invoke(invoker);
			}
			
		});
	}
}
