package jsx.editors;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import jsx.JSXEditorPlugin;
import jsx.editors.ast.IJSXNode;
import jsx.editors.ast.JSXClassNode;

import net.arnx.jsonic.JSON;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

public class JSXOutlinePage extends ContentOutlinePage {
	
	private RootModel root = new RootModel();
	private JSXEditor editor = null;
	
	public JSXOutlinePage( JSXEditor editor ) {
		this.editor = editor;
	}
	
	public void createControl( Composite parent ) {
		super.createControl(parent);
		
		TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider( new JSXContentProvider());
		viewer.setLabelProvider( new JSXLabelProvider());
		
		viewer.addSelectionChangedListener( new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {

				IStructuredSelection sel = (IStructuredSelection)event.getSelection();
				IJSXNode element = (IJSXNode) sel.getFirstElement();
				if( element != null )
				{
					IDocumentProvider provider = editor.getDocumentProvider();
					IDocument document = provider.getDocument(editor.getEditorInput());
					editor.selectAndReveal(element.getOffset(document.get()), element.getWordLength());
				}
			}
		});
		
		viewer.setInput( root );
		refresh();
	}
	
	public void refresh() {
		IFileEditorInput input = (IFileEditorInput) editor.getEditorInput();
		IFile file = input.getFile();
		IPath loc = file.getLocation();
		System.out.println(loc);
		ArrayList elements = null;
		Runtime rt = Runtime.getRuntime();
		try {
			Process p = rt.exec("/Users/tateno.masahiro/.nodebrew/current/bin/node /Users/tateno.masahiro/jsx/JSX/bin/jsx --mode parse " + loc.toString());
			InputStream in = p.getInputStream();
			elements = JSON.decode(in);
			
			System.out.println(elements);
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		ArrayList documentElement = new ArrayList();
		if( elements != null ) {
			for( int i = 0; i < elements.size() ; i++ ){
				JSXClassNode node = new JSXClassNode((LinkedHashMap)elements.get(i));
				documentElement.add( node );
			}
		}
		root.documentElement = documentElement;
		getTreeViewer().refresh();
	}
	
	private class RootModel {
		private ArrayList documentElement;
	}
	
	private class JSXContentProvider implements ITreeContentProvider {
		
		public Object[] getChildren( Object parentElement ) {
			if( parentElement instanceof ArrayList ) {
				return ((ArrayList) parentElement).toArray();
			}
			if( parentElement instanceof JSXClassNode ) {
				return ((JSXClassNode) parentElement).members.toArray();
			}
			return new Object[0];
		}
		
		public Object getParent( Object element ) {
			return null;
		}
		
		public boolean hasChildren( Object element ) {
			return ( getChildren( element ).length > 0 );
		}
		
		public Object[] getElements( Object inputElement ) {
			return getChildren( root.documentElement );
		}
		
		public void dispose() {
		}
		
		public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
		}
	}
	
	private class JSXLabelProvider extends LabelProvider {
		
		private Image elementImage = JSXEditorPlugin.getImageDescriptor("icons/element.gif").createImage();
		
		public Image getImage( Object element ) {
			return null;
		}
		
		public String getText( Object element )	 {
			return super.getText( element );
		}
		
		public void dispose() {
			elementImage.dispose();
			super.dispose();
		}
	}

}
