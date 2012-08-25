package jsx.editors.ast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JSXVarNode extends JSXMemberNode implements IJSXNode {

	public JSXVarNode(LinkedHashMap element) {
		super(element);
		this.name = (String)element.get("name");
	}
}
