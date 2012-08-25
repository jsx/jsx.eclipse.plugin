package jsx.editors;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.ui.IFileEditorInput;

import net.arnx.jsonic.JSON;


public class JSXAssistProcessor implements IContentAssistProcessor {
	
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		IDocument document = viewer.getDocument();
		String source = document.get();
		int lineNumber = 1;
		int columnNumber = 0;
		int _offset = 1;
		while ( _offset < offset ) {
			if( source.charAt(_offset) == '\n' ) {
				lineNumber ++;
				columnNumber = 1;
			} else {
				columnNumber ++;
			}
		}
		
//		IFileEditorInput input = (IFileEditorInput) editor.getEditorInput();
//		IFile file = input.getFile();
//		IPath loc = file.getLocation();
		Runtime rt = Runtime.getRuntime();
		try {
//			Process p = rt.exec("/Users/tateno.masahiro/.nodebrew/current/bin/node /Users/tateno.masahiro/jsx/JSX/bin/jsx --mode parse "+loc.toString());
			Process p = rt.exec("/Users/tateno.masahiro/.nodebrew/current/bin/node /Users/tateno.masahiro/jsx/JSX/bin/jsx --mode parse ");
			InputStream in = p.getInputStream();
			ArrayList classes = JSON.decode(in);
			parseClass(classes, proposals, offset);
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		
		return (ICompletionProposal[])proposals.toArray(new ICompletionProposal[proposals.size()]);
	}
	
	public void parseClass( ArrayList obj, List<ICompletionProposal> proposals, int offset) {
		Iterator ite = obj.iterator();
		while( ite.hasNext()) {
			LinkedHashMap map = (LinkedHashMap)ite.next();
			String className = (String)map.get("name");
			proposals.add( new CompletionProposal(className, offset, 0, 
					className.length(), null, className, null, null ));
			ArrayList members = (ArrayList)map.get("members");
			parseMember(members, proposals, offset);
		}
	}
	
	public void parseMember( ArrayList obj, List<ICompletionProposal> proposals, int offset) {
		Iterator ite = obj.iterator();
		while( ite.hasNext()) {
			LinkedHashMap map = (LinkedHashMap)ite.next();
			String funcName = (String)((ArrayList)map.get("nameToken")).get(0);
			proposals.add( new CompletionProposal(funcName, offset, 0, 
					funcName.length(), null, funcName, null, null ));
		}
	}

	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return null;
	}

	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}

	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	public String getErrorMessage() {
		return null;
	}

	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}
	
}
