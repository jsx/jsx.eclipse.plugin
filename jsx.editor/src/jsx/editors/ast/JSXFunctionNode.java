package jsx.editors.ast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JSXFunctionNode extends JSXMemberNode implements IJSXNode {

	public JSXFunctionNode(LinkedHashMap element) {
		super(element);
		ArrayList token = (ArrayList)element.get("nameToken");
		this.name = (String)token.get(0);
		this.lineNumber = Integer.parseInt(token.get(3).toString());
		this.columnNumber = Integer.parseInt(token.get(4).toString());
	}
	
}
