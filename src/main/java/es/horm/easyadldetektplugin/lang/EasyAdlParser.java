// Generated from java-escape by ANTLR 4.11.1
package es.horm.easyadldetektplugin.lang;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class EasyAdlParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, COMMENT=2, SYSTEM=3, COMPONENT=4, ID=5, ARGUMENT=6, NL=7, WS=8, 
		INDENT=9, DEDENT=10;
	public static final int
		RULE_architectureDescription = 0, RULE_systemDefinition = 1, RULE_componentDefinition = 2, 
		RULE_operation = 3, RULE_operator = 4, RULE_system = 5, RULE_component = 6, 
		RULE_argument = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"architectureDescription", "systemDefinition", "componentDefinition", 
			"operation", "operator", "system", "component", "argument"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, "COMMENT", "SYSTEM", "COMPONENT", "ID", "ARGUMENT", "NL", 
			"WS", "INDENT", "DEDENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "java-escape"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public EasyAdlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArchitectureDescriptionContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(EasyAdlParser.EOF, 0); }
		public List<SystemDefinitionContext> systemDefinition() {
			return getRuleContexts(SystemDefinitionContext.class);
		}
		public SystemDefinitionContext systemDefinition(int i) {
			return getRuleContext(SystemDefinitionContext.class,i);
		}
		public List<ComponentDefinitionContext> componentDefinition() {
			return getRuleContexts(ComponentDefinitionContext.class);
		}
		public ComponentDefinitionContext componentDefinition(int i) {
			return getRuleContext(ComponentDefinitionContext.class,i);
		}
		public ArchitectureDescriptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_architectureDescription; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).enterArchitectureDescription(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).exitArchitectureDescription(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EasyAdlVisitor ) return ((EasyAdlVisitor<? extends T>)visitor).visitArchitectureDescription(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArchitectureDescriptionContext architectureDescription() throws RecognitionException {
		ArchitectureDescriptionContext _localctx = new ArchitectureDescriptionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_architectureDescription);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(18);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case SYSTEM:
					{
					setState(16);
					systemDefinition();
					}
					break;
				case COMPONENT:
					{
					setState(17);
					componentDefinition();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(20); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SYSTEM || _la==COMPONENT );
			setState(22);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SystemDefinitionContext extends ParserRuleContext {
		public SystemContext system() {
			return getRuleContext(SystemContext.class,0);
		}
		public TerminalNode INDENT() { return getToken(EasyAdlParser.INDENT, 0); }
		public TerminalNode DEDENT() { return getToken(EasyAdlParser.DEDENT, 0); }
		public List<ComponentDefinitionContext> componentDefinition() {
			return getRuleContexts(ComponentDefinitionContext.class);
		}
		public ComponentDefinitionContext componentDefinition(int i) {
			return getRuleContext(ComponentDefinitionContext.class,i);
		}
		public List<TerminalNode> NL() { return getTokens(EasyAdlParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(EasyAdlParser.NL, i);
		}
		public SystemDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_systemDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).enterSystemDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).exitSystemDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EasyAdlVisitor ) return ((EasyAdlVisitor<? extends T>)visitor).visitSystemDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SystemDefinitionContext systemDefinition() throws RecognitionException {
		SystemDefinitionContext _localctx = new SystemDefinitionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_systemDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			system();
			setState(25);
			match(T__0);
			setState(26);
			match(INDENT);
			setState(31); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(27);
				componentDefinition();
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NL) {
					{
					setState(28);
					match(NL);
					}
				}

				}
				}
				setState(33); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==COMPONENT );
			setState(35);
			match(DEDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComponentDefinitionContext extends ParserRuleContext {
		public ComponentContext component() {
			return getRuleContext(ComponentContext.class,0);
		}
		public TerminalNode INDENT() { return getToken(EasyAdlParser.INDENT, 0); }
		public TerminalNode DEDENT() { return getToken(EasyAdlParser.DEDENT, 0); }
		public List<OperationContext> operation() {
			return getRuleContexts(OperationContext.class);
		}
		public OperationContext operation(int i) {
			return getRuleContext(OperationContext.class,i);
		}
		public List<TerminalNode> NL() { return getTokens(EasyAdlParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(EasyAdlParser.NL, i);
		}
		public ComponentDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_componentDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).enterComponentDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).exitComponentDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EasyAdlVisitor ) return ((EasyAdlVisitor<? extends T>)visitor).visitComponentDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComponentDefinitionContext componentDefinition() throws RecognitionException {
		ComponentDefinitionContext _localctx = new ComponentDefinitionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_componentDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			component();
			setState(38);
			match(T__0);
			setState(39);
			match(INDENT);
			setState(43); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(40);
				operation();
				setState(41);
				match(NL);
				}
				}
				setState(45); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
			setState(47);
			match(DEDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OperationContext extends ParserRuleContext {
		public List<OperatorContext> operator() {
			return getRuleContexts(OperatorContext.class);
		}
		public OperatorContext operator(int i) {
			return getRuleContext(OperatorContext.class,i);
		}
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public OperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).enterOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).exitOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EasyAdlVisitor ) return ((EasyAdlVisitor<? extends T>)visitor).visitOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperationContext operation() throws RecognitionException {
		OperationContext _localctx = new OperationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_operation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			operator();
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 112L) != 0) {
				{
				setState(52);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case COMPONENT:
				case ARGUMENT:
					{
					setState(50);
					argument();
					}
					break;
				case ID:
					{
					setState(51);
					operator();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OperatorContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(EasyAdlParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(EasyAdlParser.ID, i);
		}
		public OperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).enterOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).exitOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EasyAdlVisitor ) return ((EasyAdlVisitor<? extends T>)visitor).visitOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorContext operator() throws RecognitionException {
		OperatorContext _localctx = new OperatorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_operator);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(58); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(57);
					match(ID);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(60); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SystemContext extends ParserRuleContext {
		public TerminalNode SYSTEM() { return getToken(EasyAdlParser.SYSTEM, 0); }
		public TerminalNode ID() { return getToken(EasyAdlParser.ID, 0); }
		public SystemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_system; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).enterSystem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).exitSystem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EasyAdlVisitor ) return ((EasyAdlVisitor<? extends T>)visitor).visitSystem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SystemContext system() throws RecognitionException {
		SystemContext _localctx = new SystemContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_system);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(SYSTEM);
			setState(63);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComponentContext extends ParserRuleContext {
		public TerminalNode COMPONENT() { return getToken(EasyAdlParser.COMPONENT, 0); }
		public TerminalNode ID() { return getToken(EasyAdlParser.ID, 0); }
		public ComponentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_component; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).enterComponent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).exitComponent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EasyAdlVisitor ) return ((EasyAdlVisitor<? extends T>)visitor).visitComponent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComponentContext component() throws RecognitionException {
		ComponentContext _localctx = new ComponentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_component);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(COMPONENT);
			setState(66);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentContext extends ParserRuleContext {
		public TerminalNode ARGUMENT() { return getToken(EasyAdlParser.ARGUMENT, 0); }
		public ComponentContext component() {
			return getRuleContext(ComponentContext.class,0);
		}
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).enterArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EasyAdlListener ) ((EasyAdlListener)listener).exitArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EasyAdlVisitor ) return ((EasyAdlVisitor<? extends T>)visitor).visitArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_argument);
		try {
			setState(70);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ARGUMENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(68);
				match(ARGUMENT);
				}
				break;
			case COMPONENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				component();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\nI\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0001"+
		"\u0000\u0001\u0000\u0004\u0000\u0013\b\u0000\u000b\u0000\f\u0000\u0014"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0003\u0001\u001e\b\u0001\u0004\u0001 \b\u0001\u000b\u0001"+
		"\f\u0001!\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0004\u0002,\b\u0002\u000b\u0002"+
		"\f\u0002-\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0005\u00035\b\u0003\n\u0003\f\u00038\t\u0003\u0001\u0004\u0004\u0004"+
		";\b\u0004\u000b\u0004\f\u0004<\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0003\u0007G\b"+
		"\u0007\u0001\u0007\u0000\u0000\b\u0000\u0002\u0004\u0006\b\n\f\u000e\u0000"+
		"\u0000I\u0000\u0012\u0001\u0000\u0000\u0000\u0002\u0018\u0001\u0000\u0000"+
		"\u0000\u0004%\u0001\u0000\u0000\u0000\u00061\u0001\u0000\u0000\u0000\b"+
		":\u0001\u0000\u0000\u0000\n>\u0001\u0000\u0000\u0000\fA\u0001\u0000\u0000"+
		"\u0000\u000eF\u0001\u0000\u0000\u0000\u0010\u0013\u0003\u0002\u0001\u0000"+
		"\u0011\u0013\u0003\u0004\u0002\u0000\u0012\u0010\u0001\u0000\u0000\u0000"+
		"\u0012\u0011\u0001\u0000\u0000\u0000\u0013\u0014\u0001\u0000\u0000\u0000"+
		"\u0014\u0012\u0001\u0000\u0000\u0000\u0014\u0015\u0001\u0000\u0000\u0000"+
		"\u0015\u0016\u0001\u0000\u0000\u0000\u0016\u0017\u0005\u0000\u0000\u0001"+
		"\u0017\u0001\u0001\u0000\u0000\u0000\u0018\u0019\u0003\n\u0005\u0000\u0019"+
		"\u001a\u0005\u0001\u0000\u0000\u001a\u001f\u0005\t\u0000\u0000\u001b\u001d"+
		"\u0003\u0004\u0002\u0000\u001c\u001e\u0005\u0007\u0000\u0000\u001d\u001c"+
		"\u0001\u0000\u0000\u0000\u001d\u001e\u0001\u0000\u0000\u0000\u001e \u0001"+
		"\u0000\u0000\u0000\u001f\u001b\u0001\u0000\u0000\u0000 !\u0001\u0000\u0000"+
		"\u0000!\u001f\u0001\u0000\u0000\u0000!\"\u0001\u0000\u0000\u0000\"#\u0001"+
		"\u0000\u0000\u0000#$\u0005\n\u0000\u0000$\u0003\u0001\u0000\u0000\u0000"+
		"%&\u0003\f\u0006\u0000&\'\u0005\u0001\u0000\u0000\'+\u0005\t\u0000\u0000"+
		"()\u0003\u0006\u0003\u0000)*\u0005\u0007\u0000\u0000*,\u0001\u0000\u0000"+
		"\u0000+(\u0001\u0000\u0000\u0000,-\u0001\u0000\u0000\u0000-+\u0001\u0000"+
		"\u0000\u0000-.\u0001\u0000\u0000\u0000./\u0001\u0000\u0000\u0000/0\u0005"+
		"\n\u0000\u00000\u0005\u0001\u0000\u0000\u000016\u0003\b\u0004\u000025"+
		"\u0003\u000e\u0007\u000035\u0003\b\u0004\u000042\u0001\u0000\u0000\u0000"+
		"43\u0001\u0000\u0000\u000058\u0001\u0000\u0000\u000064\u0001\u0000\u0000"+
		"\u000067\u0001\u0000\u0000\u00007\u0007\u0001\u0000\u0000\u000086\u0001"+
		"\u0000\u0000\u00009;\u0005\u0005\u0000\u0000:9\u0001\u0000\u0000\u0000"+
		";<\u0001\u0000\u0000\u0000<:\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000"+
		"\u0000=\t\u0001\u0000\u0000\u0000>?\u0005\u0003\u0000\u0000?@\u0005\u0005"+
		"\u0000\u0000@\u000b\u0001\u0000\u0000\u0000AB\u0005\u0004\u0000\u0000"+
		"BC\u0005\u0005\u0000\u0000C\r\u0001\u0000\u0000\u0000DG\u0005\u0006\u0000"+
		"\u0000EG\u0003\f\u0006\u0000FD\u0001\u0000\u0000\u0000FE\u0001\u0000\u0000"+
		"\u0000G\u000f\u0001\u0000\u0000\u0000\t\u0012\u0014\u001d!-46<F";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}