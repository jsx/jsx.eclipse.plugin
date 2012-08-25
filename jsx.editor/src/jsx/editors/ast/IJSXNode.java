package jsx.editors.ast;

public interface IJSXNode {
	
	int getOffset(String source);
	int getWordLength();

}
