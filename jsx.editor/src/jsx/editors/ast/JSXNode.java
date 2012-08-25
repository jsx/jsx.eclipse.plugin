package jsx.editors.ast;

import java.util.LinkedHashMap;

public class JSXNode implements IJSXNode {

	public String name = null;
	public int lineNumber = 0;
	public int columnNumber = 0;
	
	public JSXNode(LinkedHashMap element) {
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return name;
	}

	@Override
	public int getOffset(String source) {
		int offset = 0;
		for ( int i = 0; i < this.lineNumber - 1; i++ ){
			offset = source.indexOf('\n', offset) + 1;
		}
		return offset + columnNumber;
	}
	
	@Override
	public int getWordLength() {
		return ( name != null ) ? name.length() : 0;
	}
}
