package jsx.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class JSXConfiguration extends SourceViewerConfiguration {
	private JSXDoubleClickStrategy doubleClickStrategy;
	private JSXTagScanner tagScanner;
	private JSXScanner scanner;
	private ColorManager colorManager;
	private JSXEditor editor;

	public JSXConfiguration(ColorManager colorManager, JSXEditor editor) {
		this.colorManager = colorManager;
		this.editor = editor;
	}
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			JSXPartitionScanner.JSX_COMMENT
		};
	}
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new JSXDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected JSXScanner getJSXScanner() {
		if (scanner == null) {
			scanner = new JSXScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IJSXColorConstants.DEFAULT))));
		}
		return scanner;
	}
	protected JSXTagScanner getJSXTagScanner() {
		if (tagScanner == null) {
			tagScanner = new JSXTagScanner(colorManager);
			tagScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IJSXColorConstants.TAG))));
		}
		return tagScanner;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		
		DefaultDamagerRepairer dr;
		dr = new DefaultDamagerRepairer(getJSXScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(IJSXColorConstants.JSX_COMMENT)));
		reconciler.setDamager(ndr, JSXPartitionScanner.JSX_COMMENT);
		reconciler.setRepairer(ndr, JSXPartitionScanner.JSX_COMMENT);
		
		return reconciler;
	}
	
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();
		assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
		
		JSXAssistProcessor processor = new JSXAssistProcessor(editor);
		assistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);
		assistant.install(sourceViewer);
		
		return assistant;
	}

}
