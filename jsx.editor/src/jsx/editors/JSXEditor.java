package jsx.editors;

import java.util.ResourceBundle;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class JSXEditor extends TextEditor {

	private ColorManager colorManager;
	
	//	IContentOutlinePage‚ÌŽÀ‘•ƒNƒ‰ƒX
	private JSXOutlinePage outlinePage;

	public JSXEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new JSXConfiguration(colorManager, this));
		setDocumentProvider(new JSXDocumentProvider());
	}
	public void dispose() {
		if( outlinePage != null ) {
			outlinePage.dispose();
		}
		colorManager.dispose();
		super.dispose();
	}
	
	private static final ResourceBundle RESOURCE_BUNDLE
	= ResourceBundle.getBundle("jsx.messages");
	
	protected void createActions() {
		super.createActions();
		
		IAction action = new ContentAssistAction(RESOURCE_BUNDLE, "ContentAssistProposal", this);
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		setAction("ContentAssistProposal", action);
	}
	
	public Object getAdapter( Class adapter ) {
		if( IContentOutlinePage.class.equals(adapter)){
			if( outlinePage == null ){
				outlinePage = new JSXOutlinePage( this );
			}
			return outlinePage;
		}
		return super.getAdapter(adapter);
	}
	
}
