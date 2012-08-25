package jsx.preferences;

import jsx.JSXEditorPlugin;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class JSXEditorPreferenceInitializer extends
		AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = JSXEditorPlugin.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.JSX_PATH, "");
		store.setDefault(PreferenceConstants.NODE_PATH, "");
	}

}
