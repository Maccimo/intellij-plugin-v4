package org.antlr.intellij.plugin.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.antlr.intellij.plugin.ANTLRv4TokenType;
import org.antlr.intellij.plugin.parser.ANTLRv4TokenTypes;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/** Root of all PSI nodes for ANTLRv4 language except for file root. */
public abstract class RuleRefNode extends LeafPsiElement implements PsiNamedElement {
	protected String name = null; // an override to input text ID if we rename via intellij

	public RuleRefNode(IElementType type, CharSequence text) {
		super(type, text);
	}

	@Override
	public String getName() {
		if ( name!=null ) return name;
		return getText();
	}

	@Override
	public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
		/*
		From doc: "Creating a fully correct AST node from scratch is
		          quite difficult. Thus, surprisingly, the easiest way to
		          get the replacement node is to create a dummy file in the
		          custom language so that it would contain the necessary
		          node in its parse tree, build the parse tree and
		          extract the necessary node from it.
		 */
//		System.out.println("rename "+this+" to "+name);
		this.replace(MyPsiUtils.createLeafFromText(getProject(),
												   getContext(),
												   name, ANTLRv4TokenTypes.RULE_REF));
		this.name = name;
		return this;
	}

	public abstract ANTLRv4TokenType getRuleRefType();

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + getElementType().toString() + ")";
	}
}
