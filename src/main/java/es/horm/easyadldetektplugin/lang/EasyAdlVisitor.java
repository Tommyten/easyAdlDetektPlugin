// Generated from java-escape by ANTLR 4.11.1
package es.horm.easyadldetektplugin.lang;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link EasyAdlParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface EasyAdlVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link EasyAdlParser#architectureDescription}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArchitectureDescription(EasyAdlParser.ArchitectureDescriptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyAdlParser#systemDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSystemDefinition(EasyAdlParser.SystemDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyAdlParser#componentDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponentDefinition(EasyAdlParser.ComponentDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyAdlParser#operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperation(EasyAdlParser.OperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyAdlParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperator(EasyAdlParser.OperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyAdlParser#system}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSystem(EasyAdlParser.SystemContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyAdlParser#component}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponent(EasyAdlParser.ComponentContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyAdlParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(EasyAdlParser.ArgumentContext ctx);
}