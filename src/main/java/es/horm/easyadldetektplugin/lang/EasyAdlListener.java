// Generated from java-escape by ANTLR 4.11.1
package es.horm.easyadldetektplugin.lang;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link EasyAdlParser}.
 */
public interface EasyAdlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link EasyAdlParser#architectureDescription}.
	 * @param ctx the parse tree
	 */
	void enterArchitectureDescription(EasyAdlParser.ArchitectureDescriptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EasyAdlParser#architectureDescription}.
	 * @param ctx the parse tree
	 */
	void exitArchitectureDescription(EasyAdlParser.ArchitectureDescriptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EasyAdlParser#systemDefinition}.
	 * @param ctx the parse tree
	 */
	void enterSystemDefinition(EasyAdlParser.SystemDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EasyAdlParser#systemDefinition}.
	 * @param ctx the parse tree
	 */
	void exitSystemDefinition(EasyAdlParser.SystemDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EasyAdlParser#componentDefinition}.
	 * @param ctx the parse tree
	 */
	void enterComponentDefinition(EasyAdlParser.ComponentDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EasyAdlParser#componentDefinition}.
	 * @param ctx the parse tree
	 */
	void exitComponentDefinition(EasyAdlParser.ComponentDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EasyAdlParser#operation}.
	 * @param ctx the parse tree
	 */
	void enterOperation(EasyAdlParser.OperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link EasyAdlParser#operation}.
	 * @param ctx the parse tree
	 */
	void exitOperation(EasyAdlParser.OperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link EasyAdlParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterOperator(EasyAdlParser.OperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link EasyAdlParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitOperator(EasyAdlParser.OperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link EasyAdlParser#system}.
	 * @param ctx the parse tree
	 */
	void enterSystem(EasyAdlParser.SystemContext ctx);
	/**
	 * Exit a parse tree produced by {@link EasyAdlParser#system}.
	 * @param ctx the parse tree
	 */
	void exitSystem(EasyAdlParser.SystemContext ctx);
	/**
	 * Enter a parse tree produced by {@link EasyAdlParser#component}.
	 * @param ctx the parse tree
	 */
	void enterComponent(EasyAdlParser.ComponentContext ctx);
	/**
	 * Exit a parse tree produced by {@link EasyAdlParser#component}.
	 * @param ctx the parse tree
	 */
	void exitComponent(EasyAdlParser.ComponentContext ctx);
	/**
	 * Enter a parse tree produced by {@link EasyAdlParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(EasyAdlParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link EasyAdlParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(EasyAdlParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link EasyAdlParser#modifiers}.
	 * @param ctx the parse tree
	 */
	void enterModifiers(EasyAdlParser.ModifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link EasyAdlParser#modifiers}.
	 * @param ctx the parse tree
	 */
	void exitModifiers(EasyAdlParser.ModifiersContext ctx);
}