package jsx.editors;

import org.eclipse.jface.text.rules.*;

public class JSXPartitionScanner extends RuleBasedPartitionScanner {
	public final static String JSX_DEFAULT = "__jsx_default";
	public final static String JSX_COMMENT = "__jsx_comment";
	public final static String JSX_STRING = "__jsx_string";

	public JSXPartitionScanner() {

		IToken jsxComment = new Token(JSX_COMMENT);

		IPredicateRule[] rules = new IPredicateRule[2];

		rules[0] = new SingleLineRule("//", "\n", jsxComment, (char) 0, true);
		rules[1] = new MultiLineRule("/*", "*/", jsxComment, (char) 0, true);

		setPredicateRules(rules);
	}
}
