package jsx.preferences;

import jsx.JSXEditorPlugin;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class JSXEditorPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public JSXEditorPreferencePage() {
		super(GRID);
		setPreferenceStore(JSXEditorPlugin.getDefault().getPreferenceStore());
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(
				PreferenceConstants.JSX_PATH, "jsx パス:", getFieldEditorParent()));
		addField(new StringFieldEditor(
				PreferenceConstants.NODE_PATH, "node パス:", getFieldEditorParent()));

	}

}
