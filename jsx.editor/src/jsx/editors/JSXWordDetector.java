package jsx.editors;

import org.eclipse.jface.text.rules.IWordDetector;

public class JSXWordDetector implements IWordDetector {

	@Override
	public boolean isWordStart(char c) {
		// if c is a-zA-Z _
		System.out.println( "c:"+(int)c );
		return ( 
				('a' <= c && c <= 'z') ||
				( 'A' <= c && c <= 'Z') ||
				( '_' == c )
		);
	}

	@Override
	public boolean isWordPart(char c) {
		return ( 
				('0' <= c && c <= '9') ||
				('a' <= c && c <= 'z') ||
				( 'A' <= c && c <= 'Z') ||
				( '_' == c )
		);
	}

}
