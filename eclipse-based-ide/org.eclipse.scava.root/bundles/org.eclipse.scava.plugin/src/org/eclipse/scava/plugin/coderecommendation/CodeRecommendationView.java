package org.eclipse.scava.plugin.coderecommendation;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.scava.plugin.mvc.implementation.jface.JFaceTitleAreaDialogView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import io.swagger.client.model.ApiCallResult;
import org.eclipse.swt.widgets.Label;

public class CodeRecommendationView extends JFaceTitleAreaDialogView implements ICodeRecommendationView {
	private Composite resultListComposite;
	private Text previewText;
	private ApiCallResult selectedRecommendation;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public CodeRecommendationView(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.RESIZE | SWT.TITLE);
		setBlockOnOpen(false);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Code recommendation");
		setTitleImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/logo-titleimage-titlearedialog.png"));
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setBackgroundMode(SWT.INHERIT_DEFAULT);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		ScrolledComposite scrolledComposite = new ScrolledComposite(container,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledComposite = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
		gd_scrolledComposite.widthHint = 218;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		resultListComposite = new Composite(scrolledComposite, SWT.NONE);
		resultListComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		resultListComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		resultListComposite.setLayout(new GridLayout(1, false));
		
		Label lblNewLabel = new Label(resultListComposite, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("No results were found");
		scrolledComposite.setContent(resultListComposite);
		scrolledComposite.setMinSize(resultListComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		previewText = new Text(container,
				SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		previewText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		previewText.setSize(329, 268);

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, "Insert at cursor", true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(603, 495);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Code recommendation");
	}

	@Override
	public void showRecommendations(List<ApiCallResult> recommendations) {
		if( !recommendations.isEmpty() ) {
			for(Control child : resultListComposite.getChildren()) {
				child.dispose();
			}
			
			recommendations.forEach(recommendation -> listRecommendation(recommendation));
			
			resultListComposite.requestLayout();
		}
	}

	private void listRecommendation(ApiCallResult recommendation) {
		Button btnResult = new Button(resultListComposite, SWT.NONE);
		btnResult.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnResult.setText(recommendation.getPattern());
		btnResult.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String lines = String.join("\n", recommendation.getCodeLines());
				previewText.setText(lines);
				selectedRecommendation = recommendation;
			}

		});
	}

	@Override
	protected void okPressed() {
		InsertRequestEvent event = new InsertRequestEvent(CodeRecommendationView.this, selectedRecommendation);
		getEventBus().post(event);
		super.okPressed();
	}
	
	
}
