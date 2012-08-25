package jsx.editors.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class JSXClassNode extends JSXNode implements IJSXNode {
	
	public ArrayList<JSXMemberNode> members = null;
	
	public JSXClassNode( LinkedHashMap element ) {
		super( element );
		this.name = (String)element.get("name");
		LinkedHashMap token = (LinkedHashMap)element.get("token");
		this.lineNumber = Integer.parseInt(token.get("_lineNumber").toString());
		this.columnNumber = Integer.parseInt(token.get("_columnNumber").toString());
		ArrayList members = (ArrayList)element.get("members");
		parseMembers(members);
	}
	
	protected void parseMembers( ArrayList elements ) {
		this.members = new ArrayList<JSXMemberNode>();
		Iterator ite = elements.iterator();
		while( ite.hasNext()) {
			LinkedHashMap map = (LinkedHashMap)ite.next();
			JSXMemberNode node;
			if( map.containsKey("name") ){
				node = new JSXVarNode( map );
			} else { // map.containsKey("nameToken")
				node = new JSXFunctionNode( map );
			}
			this.members.add( node );
		}
	}
}
