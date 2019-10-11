package org.eclipse.scava.plugin.preferences;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class ButtonFieldEditor extends StringButtonFieldEditor {

	Text textControl;

	/**
	 * Creates a button field editor.
	 *
	 * @param name      the name of the preference this field editor works on
	 * @param labelText the label text of the field editor
	 * @param parent    the parent of the field editor's control
	 * @wbp.parser.entryPoint
	 */
	public ButtonFieldEditor(String name, String labelText, Composite parent) {
		init(name, labelText);
		setChangeButtonText(JFaceResources.getString("Erase database"));//$NON-NLS-1$
		setValidateStrategy(VALIDATE_ON_FOCUS_LOST);
		createControl(parent);

		textControl = getTextControl();
		textControl.setEditable(false);
		textControl.setEnabled(false);

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	protected void doLoad() {
		super.doLoad();

		File file = new File(Activator.getDefault().getPreferenceStore().getString(Preferences.DATABASE_PATH));

		System.out.println(file.getAbsolutePath());

		if (file != null) {
			long folderSize = folderSize(file);
			setStringValue(folderSize / 1000000 + " MB");
		} else {
			setStringValue(0 / 1000000 + " MB");
		}

	}

	@Override
	protected String changePressed() {

		File file = new File(Activator.getDefault().getPreferenceStore().getString(Preferences.DATABASE_PATH));

		boolean openConfirm = MessageDialog.openConfirm(Activator.getDefault().getWorkbench().getDisplay().getActiveShell(), "Restart", "This action need an eclipse restart.");
		if (openConfirm) {
			try {
				Activator.getDefault().getMainController().getUserMonitor().getGremlinAdapter().closeDatabaseConnection();
				deleteDirectory(file);
				PlatformUI.getWorkbench().restart();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private boolean deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectory(files[i]);
					} else {
						boolean succes = files[i].delete();
					}
				}
			}
		}
		return (directory.delete());
	}

	private long folderSize(File directory) {
		long length = 0;
		for (File file : directory.listFiles()) {
			if (file != null) {
				if (file.isFile())
					length += file.length();
				else
					length += folderSize(file);
			}
		}
		return length;
	}

	@Override
	protected boolean doCheckState() {
		return true;
	}

}
