package jsx.editors;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.rules.*;

public class JSXTagScanner extends RuleBasedScanner {

	public JSXTagScanner(ColorManager manager) {
		IToken string =
			new Token(
				new TextAttribute(manager.getColor(IJSXColorConstants.STRING)));

		IRule[] rules = new IRule[3];

		// Add rule for double quotes
		rules[0] = new SingleLineRule("\"", "\"", string, '\\');
		// Add a rule for single quotes
		rules[1] = new SingleLineRule("'", "'", string, '\\');
		// Add generic whitespace rule.
		rules[2] = new WhitespaceRule(new JSXWhitespaceDetector());

		setRules(rules);
	}
}
