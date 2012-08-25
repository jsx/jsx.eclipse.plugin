package jsx.editors;

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;
import org.eclipse.swt.SWT;

public class JSXScanner extends RuleBasedScanner {

	public JSXScanner(ColorManager manager) {
		IToken _strong = new Token( new TextAttribute(manager.getColor(IJSXColorConstants.STRONG), null, SWT.BOLD));
		IToken _log = new Token( new TextAttribute(manager.getColor(IJSXColorConstants.LOG), null, SWT.BOLD));
		IToken _default = new Token( new TextAttribute(manager.getColor(IJSXColorConstants.DEFAULT)));
		IToken _string = new Token( new TextAttribute(manager.getColor(IJSXColorConstants.STRING)));

		WordRule wr = new WordRule(new JSXWordDetector(), _default);
		wr.addWord("class", _strong);
		wr.addWord("interface", _strong);
		wr.addWord("abstract", _strong);
		wr.addWord("extends", _strong);
		wr.addWord("implements", _strong);
		wr.addWord("function", _strong);
		wr.addWord("override", _strong);
		wr.addWord("static", _strong);
		wr.addWord("void", _strong);
		wr.addWord("var", _strong);
		wr.addWord("as", _strong);
		wr.addWord("if", _strong);
		wr.addWord("else", _strong);
		wr.addWord("import", _strong);
		wr.addWord("new", _strong);
		wr.addWord("this", _strong);
		wr.addWord("return", _strong);
		wr.addWord("null", _strong);
		wr.addWord("switch", _strong);
		wr.addWord("case", _strong);
		wr.addWord("default", _strong);
		wr.addWord("for", _strong);
		wr.addWord("while", _strong);
		wr.addWord("break", _strong);
		wr.addWord("continue", _strong);
		wr.addWord("each", _strong);
		wr.addWord("log", _log);
		
		IRule[] rules = new IRule[4];
		rules[0] = wr;
		// Add rule for double quotes
		rules[1] = new SingleLineRule("\"", "\"", _string, '\\');
		// Add a rule for single quotes
		rules[2] = new SingleLineRule("'", "'", _string, '\\');
		// Add generic whitespace rule.
		rules[3] = new WhitespaceRule(new JSXWhitespaceDetector());


		setRules(rules);
	}
}
