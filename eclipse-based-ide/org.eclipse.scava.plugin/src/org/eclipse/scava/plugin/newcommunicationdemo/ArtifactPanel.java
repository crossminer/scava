package org.eclipse.scava.plugin.newcommunicationdemo;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Link;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Function;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import net.apispark.webapi.representation.Artifact;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;

public class ArtifactPanel extends Composite {
	private Artifact artifact;
	private Composite detailsComposite;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ArtifactPanel(Artifact artifact, SimpleEvent refreshParent, Composite parent, int style) {
		super(parent, style);
		
		this.artifact = artifact;
		
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(3, false));
		
		Link title = new Link(this, SWT.NONE);
		title.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(artifact.getHtml_url()));
				} catch (PartInitException | MalformedURLException e1) {
					e1.printStackTrace();
				}
			}
		});
		title.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		title.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		title.setText("<a href=\""+artifact.getHomePage()+"\">"+artifact.getFullName()+"</a>");
		
		Label label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/SCAVA-icon-16.png"));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		label.setText("starred: "+artifact.getStarred().size());
		
		Label description = new Label(this, SWT.WRAP);
		description.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		description.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		description.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		description.setText(artifact.getDescription().substring(0, Math.min(artifact.getDescription().length(), 50)));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Link link = new Link(this, SWT.NONE);
		link.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if( detailsComposite.getLayoutData() instanceof GridData ) {
					GridData data = (GridData)detailsComposite.getLayoutData();
					data.exclude = !data.exclude;
					detailsComposite.setVisible(!data.exclude);
					
					refreshParent.invoke();
				}
			}
		});
		link.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		link.setText("<a>Show more</a>");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		detailsComposite = new Composite(this, SWT.BORDER);
		GridData gd_detailsComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_detailsComposite.exclude = true;
		gd_detailsComposite.widthHint = 382;
		detailsComposite.setLayoutData(gd_detailsComposite);
		detailsComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		ArtifactTreeView artifactTreeView = new ArtifactTreeView(detailsComposite, SWT.NONE);
		Tree tree = artifactTreeView.getTree();
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gd_label_1.widthHint = 481;
		label_1.setLayoutData(gd_label_1);
		artifactTreeView.setInput(artifact);
	}

	
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
