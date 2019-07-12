// Generated from org\eclipse\scava\codeparsers\php\PhpLexer.g4 by ANTLR 4.7
package org.eclipse.scava.business.impl.crossindex.codetokenizers;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PhpLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SeaWhitespace=1, HtmlText=2, XmlStart=3, PHPStart=4, HtmlScriptOpen=5, 
		HtmlStyleOpen=6, HtmlComment=7, HtmlDtd=8, HtmlOpen=9, Shebang=10, Error=11, 
		XmlText=12, XmlClose=13, PHPStartInside=14, HtmlClose=15, HtmlSlashClose=16, 
		HtmlSlash=17, HtmlEquals=18, HtmlStartQuoteString=19, HtmlStartDoubleQuoteString=20, 
		HtmlHex=21, HtmlDecimal=22, HtmlSpace=23, HtmlName=24, ErrorInside=25, 
		PHPStartInsideQuoteString=26, HtmlEndQuoteString=27, HtmlQuoteString=28, 
		ErrorHtmlQuote=29, PHPStartDoubleQuoteString=30, HtmlEndDoubleQuoteString=31, 
		HtmlDoubleQuoteString=32, ErrorHtmlDoubleQuote=33, ScriptText=34, ScriptClose=35, 
		PHPStartInsideScript=36, StyleBody=37, PHPEnd=38, Whitespace=39, MultiLineComment=40, 
		SingleLineComment=41, ShellStyleComment=42, Abstract=43, Array=44, As=45, 
		BinaryCast=46, BoolType=47, BooleanConstant=48, Break=49, Callable=50, 
		Case=51, Catch=52, Class=53, Clone=54, Const=55, Continue=56, Declare=57, 
		Default=58, Do=59, DoubleCast=60, DoubleType=61, Echo=62, Else=63, ElseIf=64, 
		Empty=65, EndDeclare=66, EndFor=67, EndForeach=68, EndIf=69, EndSwitch=70, 
		EndWhile=71, Eval=72, Exit=73, Extends=74, Final=75, Finally=76, FloatCast=77, 
		For=78, Foreach=79, Function=80, Global=81, Goto=82, If=83, Implements=84, 
		Import=85, Include=86, IncludeOnce=87, InstanceOf=88, InsteadOf=89, Int8Cast=90, 
		Int16Cast=91, Int64Type=92, IntType=93, Interface=94, IsSet=95, List=96, 
		LogicalAnd=97, LogicalOr=98, LogicalXor=99, Namespace=100, New=101, Null=102, 
		ObjectType=103, Parent_=104, Partial=105, Print=106, Private=107, Protected=108, 
		Public=109, Require=110, RequireOnce=111, Resource=112, Return=113, Static=114, 
		StringType=115, Switch=116, Throw=117, Trait=118, Try=119, Typeof=120, 
		UintCast=121, UnicodeCast=122, Unset=123, Use=124, Var=125, While=126, 
		Yield=127, Get=128, Set=129, Call=130, CallStatic=131, Constructor=132, 
		Destruct=133, Wakeup=134, Sleep=135, Autoload=136, IsSet__=137, Unset__=138, 
		ToString__=139, Invoke=140, SetState=141, Clone__=142, DebugInfo=143, 
		Namespace__=144, Class__=145, Traic__=146, Function__=147, Method__=148, 
		Line__=149, File__=150, Dir__=151, Lgeneric=152, Rgeneric=153, DoubleArrow=154, 
		Inc=155, Dec=156, IsIdentical=157, IsNoidentical=158, IsEqual=159, IsNotEq=160, 
		IsSmallerOrEqual=161, IsGreaterOrEqual=162, PlusEqual=163, MinusEqual=164, 
		MulEqual=165, Pow=166, PowEqual=167, DivEqual=168, Concaequal=169, ModEqual=170, 
		ShiftLeftEqual=171, ShiftRightEqual=172, AndEqual=173, OrEqual=174, XorEqual=175, 
		BooleanOr=176, BooleanAnd=177, ShiftLeft=178, ShiftRight=179, DoubleColon=180, 
		ObjectOperator=181, NamespaceSeparator=182, Ellipsis=183, Less=184, Greater=185, 
		Ampersand=186, Pipe=187, Bang=188, Caret=189, Plus=190, Minus=191, Asterisk=192, 
		Percent=193, Divide=194, Tilde=195, SuppressWarnings=196, Dollar=197, 
		Dot=198, QuestionMark=199, OpenRoundBracket=200, CloseRoundBracket=201, 
		OpenSquareBracket=202, CloseSquareBracket=203, OpenCurlyBracket=204, CloseCurlyBracket=205, 
		Comma=206, Colon=207, SemiColon=208, Eq=209, Quote=210, BackQuote=211, 
		VarName=212, Label=213, Octal=214, Decimal=215, Real=216, Hex=217, Binary=218, 
		BackQuoteString=219, SingleQuoteString=220, DoubleQuote=221, StartNowDoc=222, 
		StartHereDoc=223, ErrorPhp=224, CurlyDollar=225, StringPart=226, Comment=227, 
		PHPEndSingleLineComment=228, CommentEnd=229, HereDocText=230, XmlText2=231;
	public static final int
		PhpComments=2, ErrorLexem=3, SkipChannel=4;
	public static final int
		XML=1, INSIDE=2, HtmlQuoteStringMode=3, HtmlDoubleQuoteStringMode=4, SCRIPT=5, 
		STYLE=6, PHP=7, InterpolationString=8, SingleLineCommentMode=9, HereDoc=10;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN", "PhpComments", "ErrorLexem", "SkipChannel"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "XML", "INSIDE", "HtmlQuoteStringMode", "HtmlDoubleQuoteStringMode", 
		"SCRIPT", "STYLE", "PHP", "InterpolationString", "SingleLineCommentMode", 
		"HereDoc"
	};

	public static final String[] ruleNames = {
		"SeaWhitespace", "HtmlText", "XmlStart", "PHPStartEcho", "PHPStart", "HtmlScriptOpen", 
		"HtmlStyleOpen", "HtmlComment", "HtmlDtd", "HtmlOpen", "Shebang", "NumberSign", 
		"Error", "XmlText", "XmlClose", "XmlText2", "PHPStartEchoInside", "PHPStartInside", 
		"HtmlClose", "HtmlSlashClose", "HtmlSlash", "HtmlEquals", "HtmlStartQuoteString", 
		"HtmlStartDoubleQuoteString", "HtmlHex", "HtmlDecimal", "HtmlSpace", "HtmlName", 
		"ErrorInside", "PHPStartEchoInsideQuoteString", "PHPStartInsideQuoteString", 
		"HtmlEndQuoteString", "HtmlQuoteString", "ErrorHtmlQuote", "PHPStartEchoDoubleQuoteString", 
		"PHPStartDoubleQuoteString", "HtmlEndDoubleQuoteString", "HtmlDoubleQuoteString", 
		"ErrorHtmlDoubleQuote", "ScriptText", "ScriptClose", "PHPStartInsideScriptEcho", 
		"PHPStartInsideScript", "ScriptText2", "StyleBody", "PHPEnd", "Whitespace", 
		"MultiLineComment", "SingleLineComment", "ShellStyleComment", "Abstract", 
		"Array", "As", "BinaryCast", "BoolType", "BooleanConstant", "Break", "Callable", 
		"Case", "Catch", "Class", "Clone", "Const", "Continue", "Declare", "Default", 
		"Do", "DoubleCast", "DoubleType", "Echo", "Else", "ElseIf", "Empty", "EndDeclare", 
		"EndFor", "EndForeach", "EndIf", "EndSwitch", "EndWhile", "Eval", "Exit", 
		"Extends", "Final", "Finally", "FloatCast", "For", "Foreach", "Function", 
		"Global", "Goto", "If", "Implements", "Import", "Include", "IncludeOnce", 
		"InstanceOf", "InsteadOf", "Int8Cast", "Int16Cast", "Int64Type", "IntType", 
		"Interface", "IsSet", "List", "LogicalAnd", "LogicalOr", "LogicalXor", 
		"Namespace", "New", "Null", "ObjectType", "Parent_", "Partial", "Print", 
		"Private", "Protected", "Public", "Require", "RequireOnce", "Resource", 
		"Return", "Static", "StringType", "Switch", "Throw", "Trait", "Try", "Typeof", 
		"UintCast", "UnicodeCast", "Unset", "Use", "Var", "While", "Yield", "Get", 
		"Set", "Call", "CallStatic", "Constructor", "Destruct", "Wakeup", "Sleep", 
		"Autoload", "IsSet__", "Unset__", "ToString__", "Invoke", "SetState", 
		"Clone__", "DebugInfo", "Namespace__", "Class__", "Traic__", "Function__", 
		"Method__", "Line__", "File__", "Dir__", "Lgeneric", "Rgeneric", "DoubleArrow", 
		"Inc", "Dec", "IsIdentical", "IsNoidentical", "IsEqual", "IsNotEq", "IsSmallerOrEqual", 
		"IsGreaterOrEqual", "PlusEqual", "MinusEqual", "MulEqual", "Pow", "PowEqual", 
		"DivEqual", "Concaequal", "ModEqual", "ShiftLeftEqual", "ShiftRightEqual", 
		"AndEqual", "OrEqual", "XorEqual", "BooleanOr", "BooleanAnd", "ShiftLeft", 
		"ShiftRight", "DoubleColon", "ObjectOperator", "NamespaceSeparator", "Ellipsis", 
		"Less", "Greater", "Ampersand", "Pipe", "Bang", "Caret", "Plus", "Minus", 
		"Asterisk", "Percent", "Divide", "Tilde", "SuppressWarnings", "Dollar", 
		"Dot", "QuestionMark", "OpenRoundBracket", "CloseRoundBracket", "OpenSquareBracket", 
		"CloseSquareBracket", "OpenCurlyBracket", "CloseCurlyBracket", "Comma", 
		"Colon", "SemiColon", "Eq", "Quote", "BackQuote", "VarName", "Label", 
		"Octal", "Decimal", "Real", "Hex", "Binary", "BackQuoteString", "SingleQuoteString", 
		"DoubleQuote", "StartNowDoc", "StartHereDoc", "ErrorPhp", "VarNameInInterpolation", 
		"DollarString", "CurlyDollar", "CurlyString", "EscapedChar", "DoubleQuoteInInterpolation", 
		"StringPart", "Comment", "PHPEndSingleLineComment", "CommentQuestionMark", 
		"CommentEnd", "HereDocText", "PhpStartEchoFragment", "PhpStartFragment", 
		"NameChar", "NameStartChar", "ExponentPart", "Digit", "HexDigit"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, "'/>'", null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, "'abstract'", "'array'", "'as'", 
		"'binary'", null, null, "'break'", "'callable'", "'case'", "'catch'", 
		"'class'", "'clone'", "'const'", "'continue'", "'declare'", "'default'", 
		"'do'", "'real'", "'double'", "'echo'", "'else'", "'elseif'", "'empty'", 
		"'enddeclare'", "'endfor'", "'endforeach'", "'endif'", "'endswitch'", 
		"'endwhile'", "'eval'", "'die'", "'extends'", "'final'", "'finally'", 
		"'float'", "'for'", "'foreach'", "'function'", "'global'", "'goto'", "'if'", 
		"'implements'", "'import'", "'include'", "'include_once'", "'instanceof'", 
		"'insteadof'", "'int8'", "'int16'", "'int64'", null, "'interface'", "'isset'", 
		"'list'", "'and'", "'or'", "'xor'", "'namespace'", "'new'", "'null'", 
		"'object'", "'parent'", "'partial'", "'print'", "'private'", "'protected'", 
		"'public'", "'require'", "'require_once'", "'resource'", "'return'", "'static'", 
		"'string'", "'switch'", "'throw'", "'trait'", "'try'", "'clrtypeof'", 
		null, "'unicode'", "'unset'", "'use'", "'var'", "'while'", "'yield'", 
		"'__get'", "'__set'", "'__call'", "'__callstatic'", "'__construct'", "'__destruct'", 
		"'__wakeup'", "'__sleep'", "'__autoload'", "'__isset'", "'__unset'", "'__tostring'", 
		"'__invoke'", "'__set_state'", "'__clone'", "'__debuginfo'", "'__namespace__'", 
		"'__class__'", "'__trait__'", "'__function__'", "'__method__'", "'__line__'", 
		"'__file__'", "'__dir__'", "'<:'", "':>'", "'=>'", "'++'", "'--'", "'==='", 
		"'!=='", "'=='", null, "'<='", "'>='", "'+='", "'-='", "'*='", "'**'", 
		"'**='", "'/='", "'.='", "'%='", "'<<='", "'>>='", "'&='", "'|='", "'^='", 
		"'||'", "'&&'", "'<<'", "'>>'", "'::'", "'->'", "'\\'", "'...'", null, 
		null, "'&'", "'|'", "'!'", "'^'", "'+'", "'-'", "'*'", "'%'", null, "'~'", 
		"'@'", null, "'.'", null, "'('", "')'", "'['", "']'", null, "'}'", "','", 
		"':'", "';'", null, "'''", "'`'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "SeaWhitespace", "HtmlText", "XmlStart", "PHPStart", "HtmlScriptOpen", 
		"HtmlStyleOpen", "HtmlComment", "HtmlDtd", "HtmlOpen", "Shebang", "Error", 
		"XmlText", "XmlClose", "PHPStartInside", "HtmlClose", "HtmlSlashClose", 
		"HtmlSlash", "HtmlEquals", "HtmlStartQuoteString", "HtmlStartDoubleQuoteString", 
		"HtmlHex", "HtmlDecimal", "HtmlSpace", "HtmlName", "ErrorInside", "PHPStartInsideQuoteString", 
		"HtmlEndQuoteString", "HtmlQuoteString", "ErrorHtmlQuote", "PHPStartDoubleQuoteString", 
		"HtmlEndDoubleQuoteString", "HtmlDoubleQuoteString", "ErrorHtmlDoubleQuote", 
		"ScriptText", "ScriptClose", "PHPStartInsideScript", "StyleBody", "PHPEnd", 
		"Whitespace", "MultiLineComment", "SingleLineComment", "ShellStyleComment", 
		"Abstract", "Array", "As", "BinaryCast", "BoolType", "BooleanConstant", 
		"Break", "Callable", "Case", "Catch", "Class", "Clone", "Const", "Continue", 
		"Declare", "Default", "Do", "DoubleCast", "DoubleType", "Echo", "Else", 
		"ElseIf", "Empty", "EndDeclare", "EndFor", "EndForeach", "EndIf", "EndSwitch", 
		"EndWhile", "Eval", "Exit", "Extends", "Final", "Finally", "FloatCast", 
		"For", "Foreach", "Function", "Global", "Goto", "If", "Implements", "Import", 
		"Include", "IncludeOnce", "InstanceOf", "InsteadOf", "Int8Cast", "Int16Cast", 
		"Int64Type", "IntType", "Interface", "IsSet", "List", "LogicalAnd", "LogicalOr", 
		"LogicalXor", "Namespace", "New", "Null", "ObjectType", "Parent_", "Partial", 
		"Print", "Private", "Protected", "Public", "Require", "RequireOnce", "Resource", 
		"Return", "Static", "StringType", "Switch", "Throw", "Trait", "Try", "Typeof", 
		"UintCast", "UnicodeCast", "Unset", "Use", "Var", "While", "Yield", "Get", 
		"Set", "Call", "CallStatic", "Constructor", "Destruct", "Wakeup", "Sleep", 
		"Autoload", "IsSet__", "Unset__", "ToString__", "Invoke", "SetState", 
		"Clone__", "DebugInfo", "Namespace__", "Class__", "Traic__", "Function__", 
		"Method__", "Line__", "File__", "Dir__", "Lgeneric", "Rgeneric", "DoubleArrow", 
		"Inc", "Dec", "IsIdentical", "IsNoidentical", "IsEqual", "IsNotEq", "IsSmallerOrEqual", 
		"IsGreaterOrEqual", "PlusEqual", "MinusEqual", "MulEqual", "Pow", "PowEqual", 
		"DivEqual", "Concaequal", "ModEqual", "ShiftLeftEqual", "ShiftRightEqual", 
		"AndEqual", "OrEqual", "XorEqual", "BooleanOr", "BooleanAnd", "ShiftLeft", 
		"ShiftRight", "DoubleColon", "ObjectOperator", "NamespaceSeparator", "Ellipsis", 
		"Less", "Greater", "Ampersand", "Pipe", "Bang", "Caret", "Plus", "Minus", 
		"Asterisk", "Percent", "Divide", "Tilde", "SuppressWarnings", "Dollar", 
		"Dot", "QuestionMark", "OpenRoundBracket", "CloseRoundBracket", "OpenSquareBracket", 
		"CloseSquareBracket", "OpenCurlyBracket", "CloseCurlyBracket", "Comma", 
		"Colon", "SemiColon", "Eq", "Quote", "BackQuote", "VarName", "Label", 
		"Octal", "Decimal", "Real", "Hex", "Binary", "BackQuoteString", "SingleQuoteString", 
		"DoubleQuote", "StartNowDoc", "StartHereDoc", "ErrorPhp", "CurlyDollar", 
		"StringPart", "Comment", "PHPEndSingleLineComment", "CommentEnd", "HereDocText", 
		"XmlText2"
	};
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

	public boolean AspTags = true;
	boolean _scriptTag;
	boolean _styleTag;
	String _heredocIdentifier;
	int _prevTokenType;
	String _htmlNameText;
	boolean _phpScript;
	boolean _insideString;
	@Override
	public Token nextToken()
	{
	    CommonToken token = (CommonToken)super.nextToken();
	    if (token.getType() == PHPEnd || token.getType() == PHPEndSingleLineComment)
	    {
	        if (_mode == SingleLineCommentMode)
	        {
	            // SingleLineCommentMode for such allowed syntax:
	            // <?php echo "Hello world"; // comment ?>
	            popMode(); // exit from SingleLineComment mode.
	        }
	        popMode(); // exit from PHP mode.
	        
	        if (token.getText().equals("</script>"))
	        {
	            _phpScript = false;
	            token.setType(ScriptClose);
	        }
	        else
	        {
	            // Add semicolon to the end of statement if it is absente.
	            // For example: <?php echo "Hello world" ?>
	            if (_prevTokenType == SemiColon || _prevTokenType == Colon
	                || _prevTokenType == OpenCurlyBracket || _prevTokenType == CloseCurlyBracket)
	            {
	                token.setChannel(SkipChannel);
	            }
	            else
	            {
	                token = new CommonToken(SemiColon);
	            }
	        }
	    }
	    else if (token.getType() == HtmlName)
	    {
	        _htmlNameText = token.getText();
	    }
	    else if (token.getType() == HtmlDoubleQuoteString)
	    {
	        if (token.getText().equals("php") && _htmlNameText.equals("language"))
	        {
	            _phpScript = true;
	        }
	    }
	    else if (_mode == HereDoc)
	    {
	        // Heredoc and Nowdoc syntax support: http://php.net/manual/en/language.types.string.php#language.types.string.syntax.heredoc
	        switch (token.getType())
	        {
	            case StartHereDoc:
	            case StartNowDoc:
	                _heredocIdentifier = token.getText().substring(3).trim().replace("\'","");
	                break;
	            case HereDocText:
	                if (CheckHeredocEnd(token.getText()))
	                {
	                    popMode();
	                    if (token.getText().trim().endsWith(";"))
	                    {
	                        token = new CommonToken(SemiColon);
	                    }
	                    else
	                    {
	                        token.setChannel(SkipChannel);
	                    }
	                }
	                break;
	        }
	    }
	    else if (_mode == PHP)
	    {
	        if (_channel != HIDDEN)
	        {
	            _prevTokenType = token.getType();
	        }
	    }

	    return token;
	}

	boolean CheckHeredocEnd(String text)
	{
	    text = text.trim();
	    boolean semi = (text.length() > 0) ? (text.charAt(text.length() - 1) == ';') : false;
	    String identifier = semi ? text.substring(0, text.length() - 1) : text;
	    boolean result = identifier.equals(_heredocIdentifier);
	    return result;
	}

	public PhpLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "PhpLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 5:
			HtmlScriptOpen_action((RuleContext)_localctx, actionIndex);
			break;
		case 6:
			HtmlStyleOpen_action((RuleContext)_localctx, actionIndex);
			break;
		case 18:
			HtmlClose_action((RuleContext)_localctx, actionIndex);
			break;
		case 212:
			CloseCurlyBracket_action((RuleContext)_localctx, actionIndex);
			break;
		case 234:
			CurlyDollar_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void HtmlScriptOpen_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 _scriptTag = true; 
			break;
		}
	}
	private void HtmlStyleOpen_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			 _styleTag = true; 
			break;
		}
	}
	private void HtmlClose_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:

			popMode();
			if (_scriptTag)
			{
			    if (!_phpScript)
			    {
			        pushMode(SCRIPT);
			    }
			    else
			    {
			        pushMode(PHP);
			    }
			    _scriptTag = false;
			}
			else if (_styleTag)
			{
			    pushMode(STYLE);
			    _styleTag = false;
			}

			break;
		}
	}
	private void CloseCurlyBracket_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:

			if (_insideString)
			{
			    _insideString = false;
			    setChannel(SkipChannel);
			    popMode();
			}

			break;
		}
	}
	private void CurlyDollar_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 4:
			_insideString = true;
			break;
		}
	}
	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10:
			return Shebang_sempred((RuleContext)_localctx, predIndex);
		case 45:
			return PHPEnd_sempred((RuleContext)_localctx, predIndex);
		case 229:
			return StartNowDoc_sempred((RuleContext)_localctx, predIndex);
		case 230:
			return StartHereDoc_sempred((RuleContext)_localctx, predIndex);
		case 234:
			return CurlyDollar_sempred((RuleContext)_localctx, predIndex);
		case 244:
			return PhpStartEchoFragment_sempred((RuleContext)_localctx, predIndex);
		case 245:
			return PhpStartFragment_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean Shebang_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return  _input.LA(-2) <= 0 || _input.LA(-2) == '\r' || _input.LA(-2) == '\n' ;
		}
		return true;
	}
	private boolean PHPEnd_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return AspTags;
		case 2:
			return _phpScript;
		}
		return true;
	}
	private boolean StartNowDoc_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return  _input.LA(1) == '\r' || _input.LA(1) == '\n' ;
		}
		return true;
	}
	private boolean StartHereDoc_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return  _input.LA(1) == '\r' || _input.LA(1) == '\n' ;
		}
		return true;
	}
	private boolean CurlyDollar_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return _input.LA(1) == '$';
		}
		return true;
	}
	private boolean PhpStartEchoFragment_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return AspTags;
		}
		return true;
	}
	private boolean PhpStartFragment_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return AspTags;
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\u00e9\u083f\b\1\b"+
		"\1\b\1\b\1\b\1\b\1\b\1\b\1\b\1\b\1\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5"+
		"\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16"+
		"\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25"+
		"\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34"+
		"\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4"+
		"%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4,\t,\4-\t-\4.\t.\4/\t/\4\60"+
		"\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t\64\4\65\t\65\4\66\t\66\4\67"+
		"\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t=\4>\t>\4?\t?\4@\t@\4A\tA\4B\t"+
		"B\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4"+
		"N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT\4U\tU\4V\tV\4W\tW\4X\tX\4Y\t"+
		"Y\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4`\t`\4a\ta\4b\tb\4c\tc\4d\td"+
		"\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\4k\tk\4l\tl\4m\tm\4n\tn\4o\to\4p"+
		"\tp\4q\tq\4r\tr\4s\ts\4t\tt\4u\tu\4v\tv\4w\tw\4x\tx\4y\ty\4z\tz\4{\t{"+
		"\4|\t|\4}\t}\4~\t~\4\177\t\177\4\u0080\t\u0080\4\u0081\t\u0081\4\u0082"+
		"\t\u0082\4\u0083\t\u0083\4\u0084\t\u0084\4\u0085\t\u0085\4\u0086\t\u0086"+
		"\4\u0087\t\u0087\4\u0088\t\u0088\4\u0089\t\u0089\4\u008a\t\u008a\4\u008b"+
		"\t\u008b\4\u008c\t\u008c\4\u008d\t\u008d\4\u008e\t\u008e\4\u008f\t\u008f"+
		"\4\u0090\t\u0090\4\u0091\t\u0091\4\u0092\t\u0092\4\u0093\t\u0093\4\u0094"+
		"\t\u0094\4\u0095\t\u0095\4\u0096\t\u0096\4\u0097\t\u0097\4\u0098\t\u0098"+
		"\4\u0099\t\u0099\4\u009a\t\u009a\4\u009b\t\u009b\4\u009c\t\u009c\4\u009d"+
		"\t\u009d\4\u009e\t\u009e\4\u009f\t\u009f\4\u00a0\t\u00a0\4\u00a1\t\u00a1"+
		"\4\u00a2\t\u00a2\4\u00a3\t\u00a3\4\u00a4\t\u00a4\4\u00a5\t\u00a5\4\u00a6"+
		"\t\u00a6\4\u00a7\t\u00a7\4\u00a8\t\u00a8\4\u00a9\t\u00a9\4\u00aa\t\u00aa"+
		"\4\u00ab\t\u00ab\4\u00ac\t\u00ac\4\u00ad\t\u00ad\4\u00ae\t\u00ae\4\u00af"+
		"\t\u00af\4\u00b0\t\u00b0\4\u00b1\t\u00b1\4\u00b2\t\u00b2\4\u00b3\t\u00b3"+
		"\4\u00b4\t\u00b4\4\u00b5\t\u00b5\4\u00b6\t\u00b6\4\u00b7\t\u00b7\4\u00b8"+
		"\t\u00b8\4\u00b9\t\u00b9\4\u00ba\t\u00ba\4\u00bb\t\u00bb\4\u00bc\t\u00bc"+
		"\4\u00bd\t\u00bd\4\u00be\t\u00be\4\u00bf\t\u00bf\4\u00c0\t\u00c0\4\u00c1"+
		"\t\u00c1\4\u00c2\t\u00c2\4\u00c3\t\u00c3\4\u00c4\t\u00c4\4\u00c5\t\u00c5"+
		"\4\u00c6\t\u00c6\4\u00c7\t\u00c7\4\u00c8\t\u00c8\4\u00c9\t\u00c9\4\u00ca"+
		"\t\u00ca\4\u00cb\t\u00cb\4\u00cc\t\u00cc\4\u00cd\t\u00cd\4\u00ce\t\u00ce"+
		"\4\u00cf\t\u00cf\4\u00d0\t\u00d0\4\u00d1\t\u00d1\4\u00d2\t\u00d2\4\u00d3"+
		"\t\u00d3\4\u00d4\t\u00d4\4\u00d5\t\u00d5\4\u00d6\t\u00d6\4\u00d7\t\u00d7"+
		"\4\u00d8\t\u00d8\4\u00d9\t\u00d9\4\u00da\t\u00da\4\u00db\t\u00db\4\u00dc"+
		"\t\u00dc\4\u00dd\t\u00dd\4\u00de\t\u00de\4\u00df\t\u00df\4\u00e0\t\u00e0"+
		"\4\u00e1\t\u00e1\4\u00e2\t\u00e2\4\u00e3\t\u00e3\4\u00e4\t\u00e4\4\u00e5"+
		"\t\u00e5\4\u00e6\t\u00e6\4\u00e7\t\u00e7\4\u00e8\t\u00e8\4\u00e9\t\u00e9"+
		"\4\u00ea\t\u00ea\4\u00eb\t\u00eb\4\u00ec\t\u00ec\4\u00ed\t\u00ed\4\u00ee"+
		"\t\u00ee\4\u00ef\t\u00ef\4\u00f0\t\u00f0\4\u00f1\t\u00f1\4\u00f2\t\u00f2"+
		"\4\u00f3\t\u00f3\4\u00f4\t\u00f4\4\u00f5\t\u00f5\4\u00f6\t\u00f6\4\u00f7"+
		"\t\u00f7\4\u00f8\t\u00f8\4\u00f9\t\u00f9\4\u00fa\t\u00fa\4\u00fb\t\u00fb"+
		"\4\u00fc\t\u00fc\3\2\6\2\u0205\n\2\r\2\16\2\u0206\3\2\3\2\3\3\6\3\u020c"+
		"\n\3\r\3\16\3\u020d\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3"+
		"\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\7"+
		"\t\u023f\n\t\f\t\16\t\u0242\13\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\7"+
		"\n\u024d\n\n\f\n\16\n\u0250\13\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3"+
		"\f\3\f\7\f\u025c\n\f\f\f\16\f\u025f\13\f\3\r\3\r\7\r\u0263\n\r\f\r\16"+
		"\r\u0266\13\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\6\17\u026f\n\17\r\17\16"+
		"\17\u0270\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22"+
		"\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\25"+
		"\3\25\3\26\3\26\3\27\3\27\3\30\5\30\u0293\n\30\3\30\3\30\3\30\3\30\3\31"+
		"\5\31\u029a\n\31\3\31\3\31\3\31\3\31\3\32\3\32\6\32\u02a2\n\32\r\32\16"+
		"\32\u02a3\3\33\6\33\u02a7\n\33\r\33\16\33\u02a8\3\34\6\34\u02ac\n\34\r"+
		"\34\16\34\u02ad\3\34\3\34\3\35\3\35\7\35\u02b4\n\35\f\35\16\35\u02b7\13"+
		"\35\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3!\3!"+
		"\5!\u02c9\n!\3!\3!\3\"\6\"\u02ce\n\"\r\"\16\"\u02cf\3#\3#\3#\3#\3$\3$"+
		"\3$\3$\3$\3%\3%\3%\3%\3%\3&\3&\5&\u02e2\n&\3&\3&\3\'\6\'\u02e7\n\'\r\'"+
		"\16\'\u02e8\3(\3(\3(\3(\3)\6)\u02f0\n)\r)\16)\u02f1\3*\3*\3*\3*\3*\3*"+
		"\3*\3*\5*\u02fc\n*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3-\3-\3-"+
		"\3-\3.\7.\u0311\n.\f.\16.\u0314\13.\3.\3.\3.\3.\3.\3.\3.\3.\5.\u031e\n"+
		".\3.\3.\3.\3.\3/\3/\3/\5/\u0327\n/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3"+
		"/\5/\u0335\n/\3\60\6\60\u0338\n\60\r\60\16\60\u0339\3\60\3\60\3\61\3\61"+
		"\3\61\3\61\7\61\u0342\n\61\f\61\16\61\u0345\13\61\3\61\3\61\3\61\3\61"+
		"\3\61\3\62\3\62\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\64\3\64"+
		"\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65\3\66"+
		"\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\3\67\38\38\38\38\38\38\38\38"+
		"\58\u0378\n8\39\39\39\39\39\39\39\39\39\59\u0383\n9\3:\3:\3:\3:\3:\3:"+
		"\3;\3;\3;\3;\3;\3;\3;\3;\3;\3<\3<\3<\3<\3<\3=\3=\3=\3=\3=\3=\3>\3>\3>"+
		"\3>\3>\3>\3?\3?\3?\3?\3?\3?\3@\3@\3@\3@\3@\3@\3A\3A\3A\3A\3A\3A\3A\3A"+
		"\3A\3B\3B\3B\3B\3B\3B\3B\3B\3C\3C\3C\3C\3C\3C\3C\3C\3D\3D\3D\3E\3E\3E"+
		"\3E\3E\3F\3F\3F\3F\3F\3F\3F\3G\3G\3G\3G\3G\3H\3H\3H\3H\3H\3I\3I\3I\3I"+
		"\3I\3I\3I\3J\3J\3J\3J\3J\3J\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3L\3L\3L"+
		"\3L\3L\3L\3L\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3N\3N\3N\3N\3N\3N\3O\3O"+
		"\3O\3O\3O\3O\3O\3O\3O\3O\3P\3P\3P\3P\3P\3P\3P\3P\3P\3Q\3Q\3Q\3Q\3Q\3R"+
		"\3R\3R\3R\3S\3S\3S\3S\3S\3S\3S\3S\3T\3T\3T\3T\3T\3T\3U\3U\3U\3U\3U\3U"+
		"\3U\3U\3V\3V\3V\3V\3V\3V\3W\3W\3W\3W\3X\3X\3X\3X\3X\3X\3X\3X\3Y\3Y\3Y"+
		"\3Y\3Y\3Y\3Y\3Y\3Y\3Z\3Z\3Z\3Z\3Z\3Z\3Z\3[\3[\3[\3[\3[\3\\\3\\\3\\\3]"+
		"\3]\3]\3]\3]\3]\3]\3]\3]\3]\3]\3^\3^\3^\3^\3^\3^\3^\3_\3_\3_\3_\3_\3_"+
		"\3_\3_\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3a\3a\3a\3a\3a\3a\3a\3a"+
		"\3a\3a\3a\3b\3b\3b\3b\3b\3b\3b\3b\3b\3b\3c\3c\3c\3c\3c\3d\3d\3d\3d\3d"+
		"\3d\3e\3e\3e\3e\3e\3e\3f\3f\3f\3f\3f\3f\3f\3f\5f\u04c4\nf\3g\3g\3g\3g"+
		"\3g\3g\3g\3g\3g\3g\3h\3h\3h\3h\3h\3h\3i\3i\3i\3i\3i\3j\3j\3j\3j\3k\3k"+
		"\3k\3l\3l\3l\3l\3m\3m\3m\3m\3m\3m\3m\3m\3m\3m\3n\3n\3n\3n\3o\3o\3o\3o"+
		"\3o\3p\3p\3p\3p\3p\3p\3p\3q\3q\3q\3q\3q\3q\3q\3r\3r\3r\3r\3r\3r\3r\3r"+
		"\3s\3s\3s\3s\3s\3s\3t\3t\3t\3t\3t\3t\3t\3t\3u\3u\3u\3u\3u\3u\3u\3u\3u"+
		"\3u\3v\3v\3v\3v\3v\3v\3v\3w\3w\3w\3w\3w\3w\3w\3w\3x\3x\3x\3x\3x\3x\3x"+
		"\3x\3x\3x\3x\3x\3x\3y\3y\3y\3y\3y\3y\3y\3y\3y\3z\3z\3z\3z\3z\3z\3z\3{"+
		"\3{\3{\3{\3{\3{\3{\3|\3|\3|\3|\3|\3|\3|\3}\3}\3}\3}\3}\3}\3}\3~\3~\3~"+
		"\3~\3~\3~\3\177\3\177\3\177\3\177\3\177\3\177\3\u0080\3\u0080\3\u0080"+
		"\3\u0080\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082"+
		"\3\u0082\3\u0082\3\u0082\5\u0082\u058c\n\u0082\3\u0083\3\u0083\3\u0083"+
		"\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0084\3\u0084\3\u0084\3\u0084"+
		"\3\u0084\3\u0084\3\u0085\3\u0085\3\u0085\3\u0085\3\u0086\3\u0086\3\u0086"+
		"\3\u0086\3\u0087\3\u0087\3\u0087\3\u0087\3\u0087\3\u0087\3\u0088\3\u0088"+
		"\3\u0088\3\u0088\3\u0088\3\u0088\3\u0089\3\u0089\3\u0089\3\u0089\3\u0089"+
		"\3\u0089\3\u008a\3\u008a\3\u008a\3\u008a\3\u008a\3\u008a\3\u008b\3\u008b"+
		"\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008c\3\u008c\3\u008c\3\u008c"+
		"\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c"+
		"\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d"+
		"\3\u008d\3\u008d\3\u008d\3\u008e\3\u008e\3\u008e\3\u008e\3\u008e\3\u008e"+
		"\3\u008e\3\u008e\3\u008e\3\u008e\3\u008e\3\u008f\3\u008f\3\u008f\3\u008f"+
		"\3\u008f\3\u008f\3\u008f\3\u008f\3\u008f\3\u0090\3\u0090\3\u0090\3\u0090"+
		"\3\u0090\3\u0090\3\u0090\3\u0090\3\u0091\3\u0091\3\u0091\3\u0091\3\u0091"+
		"\3\u0091\3\u0091\3\u0091\3\u0091\3\u0091\3\u0091\3\u0092\3\u0092\3\u0092"+
		"\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0093\3\u0093\3\u0093\3\u0093"+
		"\3\u0093\3\u0093\3\u0093\3\u0093\3\u0094\3\u0094\3\u0094\3\u0094\3\u0094"+
		"\3\u0094\3\u0094\3\u0094\3\u0094\3\u0094\3\u0094\3\u0095\3\u0095\3\u0095"+
		"\3\u0095\3\u0095\3\u0095\3\u0095\3\u0095\3\u0095\3\u0096\3\u0096\3\u0096"+
		"\3\u0096\3\u0096\3\u0096\3\u0096\3\u0096\3\u0096\3\u0096\3\u0096\3\u0096"+
		"\3\u0097\3\u0097\3\u0097\3\u0097\3\u0097\3\u0097\3\u0097\3\u0097\3\u0098"+
		"\3\u0098\3\u0098\3\u0098\3\u0098\3\u0098\3\u0098\3\u0098\3\u0098\3\u0098"+
		"\3\u0098\3\u0098\3\u0099\3\u0099\3\u0099\3\u0099\3\u0099\3\u0099\3\u0099"+
		"\3\u0099\3\u0099\3\u0099\3\u0099\3\u0099\3\u0099\3\u0099\3\u009a\3\u009a"+
		"\3\u009a\3\u009a\3\u009a\3\u009a\3\u009a\3\u009a\3\u009a\3\u009a\3\u009b"+
		"\3\u009b\3\u009b\3\u009b\3\u009b\3\u009b\3\u009b\3\u009b\3\u009b\3\u009b"+
		"\3\u009c\3\u009c\3\u009c\3\u009c\3\u009c\3\u009c\3\u009c\3\u009c\3\u009c"+
		"\3\u009c\3\u009c\3\u009c\3\u009c\3\u009d\3\u009d\3\u009d\3\u009d\3\u009d"+
		"\3\u009d\3\u009d\3\u009d\3\u009d\3\u009d\3\u009d\3\u009e\3\u009e\3\u009e"+
		"\3\u009e\3\u009e\3\u009e\3\u009e\3\u009e\3\u009e\3\u009f\3\u009f\3\u009f"+
		"\3\u009f\3\u009f\3\u009f\3\u009f\3\u009f\3\u009f\3\u00a0\3\u00a0\3\u00a0"+
		"\3\u00a0\3\u00a0\3\u00a0\3\u00a0\3\u00a0\3\u00a1\3\u00a1\3\u00a1\3\u00a2"+
		"\3\u00a2\3\u00a2\3\u00a3\3\u00a3\3\u00a3\3\u00a4\3\u00a4\3\u00a4\3\u00a5"+
		"\3\u00a5\3\u00a5\3\u00a6\3\u00a6\3\u00a6\3\u00a6\3\u00a7\3\u00a7\3\u00a7"+
		"\3\u00a7\3\u00a8\3\u00a8\3\u00a8\3\u00a9\3\u00a9\3\u00a9\3\u00a9\5\u00a9"+
		"\u06b9\n\u00a9\3\u00aa\3\u00aa\3\u00aa\3\u00ab\3\u00ab\3\u00ab\3\u00ac"+
		"\3\u00ac\3\u00ac\3\u00ad\3\u00ad\3\u00ad\3\u00ae\3\u00ae\3\u00ae\3\u00af"+
		"\3\u00af\3\u00af\3\u00b0\3\u00b0\3\u00b0\3\u00b0\3\u00b1\3\u00b1\3\u00b1"+
		"\3\u00b2\3\u00b2\3\u00b2\3\u00b3\3\u00b3\3\u00b3\3\u00b4\3\u00b4\3\u00b4"+
		"\3\u00b4\3\u00b5\3\u00b5\3\u00b5\3\u00b5\3\u00b6\3\u00b6\3\u00b6\3\u00b7"+
		"\3\u00b7\3\u00b7\3\u00b8\3\u00b8\3\u00b8\3\u00b9\3\u00b9\3\u00b9\3\u00ba"+
		"\3\u00ba\3\u00ba\3\u00bb\3\u00bb\3\u00bb\3\u00bc\3\u00bc\3\u00bc\3\u00bd"+
		"\3\u00bd\3\u00bd\3\u00be\3\u00be\3\u00be\3\u00bf\3\u00bf\3\u00c0\3\u00c0"+
		"\3\u00c0\3\u00c0\3\u00c1\3\u00c1\3\u00c2\3\u00c2\3\u00c3\3\u00c3\3\u00c4"+
		"\3\u00c4\3\u00c5\3\u00c5\3\u00c6\3\u00c6\3\u00c7\3\u00c7\3\u00c8\3\u00c8"+
		"\3\u00c9\3\u00c9\3\u00ca\3\u00ca\3\u00cb\3\u00cb\3\u00cc\3\u00cc\3\u00cd"+
		"\3\u00cd\3\u00ce\3\u00ce\3\u00cf\3\u00cf\3\u00d0\3\u00d0\3\u00d1\3\u00d1"+
		"\3\u00d2\3\u00d2\3\u00d3\3\u00d3\3\u00d4\3\u00d4\3\u00d5\3\u00d5\3\u00d6"+
		"\3\u00d6\3\u00d6\3\u00d7\3\u00d7\3\u00d8\3\u00d8\3\u00d9\3\u00d9\3\u00da"+
		"\3\u00da\3\u00db\3\u00db\3\u00dc\3\u00dc\3\u00dd\3\u00dd\3\u00dd\7\u00dd"+
		"\u073f\n\u00dd\f\u00dd\16\u00dd\u0742\13\u00dd\3\u00de\3\u00de\7\u00de"+
		"\u0746\n\u00de\f\u00de\16\u00de\u0749\13\u00de\3\u00df\3\u00df\6\u00df"+
		"\u074d\n\u00df\r\u00df\16\u00df\u074e\3\u00e0\6\u00e0\u0752\n\u00e0\r"+
		"\u00e0\16\u00e0\u0753\3\u00e1\6\u00e1\u0757\n\u00e1\r\u00e1\16\u00e1\u0758"+
		"\3\u00e1\3\u00e1\7\u00e1\u075d\n\u00e1\f\u00e1\16\u00e1\u0760\13\u00e1"+
		"\3\u00e1\3\u00e1\6\u00e1\u0764\n\u00e1\r\u00e1\16\u00e1\u0765\5\u00e1"+
		"\u0768\n\u00e1\3\u00e1\5\u00e1\u076b\n\u00e1\3\u00e1\6\u00e1\u076e\n\u00e1"+
		"\r\u00e1\16\u00e1\u076f\3\u00e1\3\u00e1\5\u00e1\u0774\n\u00e1\3\u00e2"+
		"\3\u00e2\3\u00e2\3\u00e2\6\u00e2\u077a\n\u00e2\r\u00e2\16\u00e2\u077b"+
		"\3\u00e3\3\u00e3\3\u00e3\3\u00e3\6\u00e3\u0782\n\u00e3\r\u00e3\16\u00e3"+
		"\u0783\3\u00e4\3\u00e4\7\u00e4\u0788\n\u00e4\f\u00e4\16\u00e4\u078b\13"+
		"\u00e4\3\u00e4\3\u00e4\3\u00e5\3\u00e5\3\u00e5\3\u00e5\7\u00e5\u0793\n"+
		"\u00e5\f\u00e5\16\u00e5\u0796\13\u00e5\3\u00e5\3\u00e5\3\u00e6\3\u00e6"+
		"\3\u00e6\3\u00e6\3\u00e7\3\u00e7\3\u00e7\3\u00e7\3\u00e7\7\u00e7\u07a3"+
		"\n\u00e7\f\u00e7\16\u00e7\u07a6\13\u00e7\3\u00e7\3\u00e7\3\u00e7\7\u00e7"+
		"\u07ab\n\u00e7\f\u00e7\16\u00e7\u07ae\13\u00e7\3\u00e7\3\u00e7\3\u00e7"+
		"\3\u00e7\3\u00e7\3\u00e8\3\u00e8\3\u00e8\3\u00e8\3\u00e8\7\u00e8\u07ba"+
		"\n\u00e8\f\u00e8\16\u00e8\u07bd\13\u00e8\3\u00e8\3\u00e8\7\u00e8\u07c1"+
		"\n\u00e8\f\u00e8\16\u00e8\u07c4\13\u00e8\3\u00e8\3\u00e8\3\u00e8\3\u00e8"+
		"\3\u00e9\3\u00e9\3\u00e9\3\u00e9\3\u00ea\3\u00ea\3\u00ea\7\u00ea\u07d1"+
		"\n\u00ea\f\u00ea\16\u00ea\u07d4\13\u00ea\3\u00ea\3\u00ea\3\u00eb\3\u00eb"+
		"\3\u00eb\3\u00eb\3\u00ec\3\u00ec\3\u00ec\3\u00ec\3\u00ec\3\u00ec\3\u00ec"+
		"\3\u00ed\3\u00ed\3\u00ed\3\u00ed\3\u00ee\3\u00ee\3\u00ee\3\u00ee\3\u00ee"+
		"\3\u00ef\3\u00ef\3\u00ef\3\u00ef\3\u00ef\3\u00f0\6\u00f0\u07f2\n\u00f0"+
		"\r\u00f0\16\u00f0\u07f3\3\u00f1\6\u00f1\u07f7\n\u00f1\r\u00f1\16\u00f1"+
		"\u07f8\3\u00f1\3\u00f1\3\u00f2\3\u00f2\3\u00f2\3\u00f3\3\u00f3\3\u00f3"+
		"\3\u00f3\3\u00f3\3\u00f4\3\u00f4\3\u00f4\3\u00f4\3\u00f4\3\u00f5\7\u00f5"+
		"\u080b\n\u00f5\f\u00f5\16\u00f5\u080e\13\u00f5\3\u00f5\5\u00f5\u0811\n"+
		"\u00f5\3\u00f5\3\u00f5\5\u00f5\u0815\n\u00f5\3\u00f6\3\u00f6\3\u00f6\3"+
		"\u00f6\3\u00f6\3\u00f6\5\u00f6\u081d\n\u00f6\3\u00f7\3\u00f7\3\u00f7\3"+
		"\u00f7\3\u00f7\5\u00f7\u0824\n\u00f7\3\u00f7\3\u00f7\5\u00f7\u0828\n\u00f7"+
		"\3\u00f8\3\u00f8\3\u00f8\3\u00f8\5\u00f8\u082e\n\u00f8\3\u00f9\5\u00f9"+
		"\u0831\n\u00f9\3\u00fa\3\u00fa\5\u00fa\u0835\n\u00fa\3\u00fa\6\u00fa\u0838"+
		"\n\u00fa\r\u00fa\16\u00fa\u0839\3\u00fb\3\u00fb\3\u00fc\3\u00fc\7\u0240"+
		"\u024e\u0312\u0343\u080c\2\u00fd\r\3\17\4\21\5\23\2\25\6\27\7\31\b\33"+
		"\t\35\n\37\13!\f#\2%\r\'\16)\17+\u00e9-\2/\20\61\21\63\22\65\23\67\24"+
		"9\25;\26=\27?\30A\31C\32E\33G\2I\34K\35M\36O\37Q\2S U!W\"Y#[$]%_\2a&c"+
		"\2e\'g(i)k*m+o,q-s.u/w\60y\61{\62}\63\177\64\u0081\65\u0083\66\u0085\67"+
		"\u00878\u00899\u008b:\u008d;\u008f<\u0091=\u0093>\u0095?\u0097@\u0099"+
		"A\u009bB\u009dC\u009fD\u00a1E\u00a3F\u00a5G\u00a7H\u00a9I\u00abJ\u00ad"+
		"K\u00afL\u00b1M\u00b3N\u00b5O\u00b7P\u00b9Q\u00bbR\u00bdS\u00bfT\u00c1"+
		"U\u00c3V\u00c5W\u00c7X\u00c9Y\u00cbZ\u00cd[\u00cf\\\u00d1]\u00d3^\u00d5"+
		"_\u00d7`\u00d9a\u00dbb\u00ddc\u00dfd\u00e1e\u00e3f\u00e5g\u00e7h\u00e9"+
		"i\u00ebj\u00edk\u00efl\u00f1m\u00f3n\u00f5o\u00f7p\u00f9q\u00fbr\u00fd"+
		"s\u00fft\u0101u\u0103v\u0105w\u0107x\u0109y\u010bz\u010d{\u010f|\u0111"+
		"}\u0113~\u0115\177\u0117\u0080\u0119\u0081\u011b\u0082\u011d\u0083\u011f"+
		"\u0084\u0121\u0085\u0123\u0086\u0125\u0087\u0127\u0088\u0129\u0089\u012b"+
		"\u008a\u012d\u008b\u012f\u008c\u0131\u008d\u0133\u008e\u0135\u008f\u0137"+
		"\u0090\u0139\u0091\u013b\u0092\u013d\u0093\u013f\u0094\u0141\u0095\u0143"+
		"\u0096\u0145\u0097\u0147\u0098\u0149\u0099\u014b\u009a\u014d\u009b\u014f"+
		"\u009c\u0151\u009d\u0153\u009e\u0155\u009f\u0157\u00a0\u0159\u00a1\u015b"+
		"\u00a2\u015d\u00a3\u015f\u00a4\u0161\u00a5\u0163\u00a6\u0165\u00a7\u0167"+
		"\u00a8\u0169\u00a9\u016b\u00aa\u016d\u00ab\u016f\u00ac\u0171\u00ad\u0173"+
		"\u00ae\u0175\u00af\u0177\u00b0\u0179\u00b1\u017b\u00b2\u017d\u00b3\u017f"+
		"\u00b4\u0181\u00b5\u0183\u00b6\u0185\u00b7\u0187\u00b8\u0189\u00b9\u018b"+
		"\u00ba\u018d\u00bb\u018f\u00bc\u0191\u00bd\u0193\u00be\u0195\u00bf\u0197"+
		"\u00c0\u0199\u00c1\u019b\u00c2\u019d\u00c3\u019f\u00c4\u01a1\u00c5\u01a3"+
		"\u00c6\u01a5\u00c7\u01a7\u00c8\u01a9\u00c9\u01ab\u00ca\u01ad\u00cb\u01af"+
		"\u00cc\u01b1\u00cd\u01b3\u00ce\u01b5\u00cf\u01b7\u00d0\u01b9\u00d1\u01bb"+
		"\u00d2\u01bd\u00d3\u01bf\u00d4\u01c1\u00d5\u01c3\u00d6\u01c5\u00d7\u01c7"+
		"\u00d8\u01c9\u00d9\u01cb\u00da\u01cd\u00db\u01cf\u00dc\u01d1\u00dd\u01d3"+
		"\u00de\u01d5\u00df\u01d7\u00e0\u01d9\u00e1\u01db\u00e2\u01dd\2\u01df\2"+
		"\u01e1\u00e3\u01e3\2\u01e5\2\u01e7\2\u01e9\u00e4\u01eb\u00e5\u01ed\u00e6"+
		"\u01ef\2\u01f1\u00e7\u01f3\u00e8\u01f5\2\u01f7\2\u01f9\2\u01fb\2\u01fd"+
		"\2\u01ff\2\u0201\2\r\2\3\4\5\6\7\b\t\n\13\f\30\5\2\13\f\17\17\"\"\4\2"+
		"%%>>\4\2\f\f\17\17\3\2>>\3\2AA\4\2))>>\4\2$$>>\5\2C\\aac|\6\2\62;C\\a"+
		"ac|\3\2\629\3\2\62\63\3\2bb\4\2))^^\4\2\13\13\"\"\6\2$$&&^^}}\5\2\f\f"+
		"\17\17AA\4\2/\60aa\5\2\u00b9\u00b9\u0302\u0371\u2041\u2042\n\2<<C\\c|"+
		"\u2072\u2191\u2c02\u2ff1\u3003\ud801\uf902\ufdd1\ufdf2\uffff\4\2--//\3"+
		"\2\62;\5\2\62;CHch\2\u086f\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\3\'\3\2\2\2\3)\3\2"+
		"\2\2\3+\3\2\2\2\4-\3\2\2\2\4/\3\2\2\2\4\61\3\2\2\2\4\63\3\2\2\2\4\65\3"+
		"\2\2\2\4\67\3\2\2\2\49\3\2\2\2\4;\3\2\2\2\4=\3\2\2\2\4?\3\2\2\2\4A\3\2"+
		"\2\2\4C\3\2\2\2\4E\3\2\2\2\5G\3\2\2\2\5I\3\2\2\2\5K\3\2\2\2\5M\3\2\2\2"+
		"\5O\3\2\2\2\6Q\3\2\2\2\6S\3\2\2\2\6U\3\2\2\2\6W\3\2\2\2\6Y\3\2\2\2\7["+
		"\3\2\2\2\7]\3\2\2\2\7_\3\2\2\2\7a\3\2\2\2\7c\3\2\2\2\be\3\2\2\2\tg\3\2"+
		"\2\2\ti\3\2\2\2\tk\3\2\2\2\tm\3\2\2\2\to\3\2\2\2\tq\3\2\2\2\ts\3\2\2\2"+
		"\tu\3\2\2\2\tw\3\2\2\2\ty\3\2\2\2\t{\3\2\2\2\t}\3\2\2\2\t\177\3\2\2\2"+
		"\t\u0081\3\2\2\2\t\u0083\3\2\2\2\t\u0085\3\2\2\2\t\u0087\3\2\2\2\t\u0089"+
		"\3\2\2\2\t\u008b\3\2\2\2\t\u008d\3\2\2\2\t\u008f\3\2\2\2\t\u0091\3\2\2"+
		"\2\t\u0093\3\2\2\2\t\u0095\3\2\2\2\t\u0097\3\2\2\2\t\u0099\3\2\2\2\t\u009b"+
		"\3\2\2\2\t\u009d\3\2\2\2\t\u009f\3\2\2\2\t\u00a1\3\2\2\2\t\u00a3\3\2\2"+
		"\2\t\u00a5\3\2\2\2\t\u00a7\3\2\2\2\t\u00a9\3\2\2\2\t\u00ab\3\2\2\2\t\u00ad"+
		"\3\2\2\2\t\u00af\3\2\2\2\t\u00b1\3\2\2\2\t\u00b3\3\2\2\2\t\u00b5\3\2\2"+
		"\2\t\u00b7\3\2\2\2\t\u00b9\3\2\2\2\t\u00bb\3\2\2\2\t\u00bd\3\2\2\2\t\u00bf"+
		"\3\2\2\2\t\u00c1\3\2\2\2\t\u00c3\3\2\2\2\t\u00c5\3\2\2\2\t\u00c7\3\2\2"+
		"\2\t\u00c9\3\2\2\2\t\u00cb\3\2\2\2\t\u00cd\3\2\2\2\t\u00cf\3\2\2\2\t\u00d1"+
		"\3\2\2\2\t\u00d3\3\2\2\2\t\u00d5\3\2\2\2\t\u00d7\3\2\2\2\t\u00d9\3\2\2"+
		"\2\t\u00db\3\2\2\2\t\u00dd\3\2\2\2\t\u00df\3\2\2\2\t\u00e1\3\2\2\2\t\u00e3"+
		"\3\2\2\2\t\u00e5\3\2\2\2\t\u00e7\3\2\2\2\t\u00e9\3\2\2\2\t\u00eb\3\2\2"+
		"\2\t\u00ed\3\2\2\2\t\u00ef\3\2\2\2\t\u00f1\3\2\2\2\t\u00f3\3\2\2\2\t\u00f5"+
		"\3\2\2\2\t\u00f7\3\2\2\2\t\u00f9\3\2\2\2\t\u00fb\3\2\2\2\t\u00fd\3\2\2"+
		"\2\t\u00ff\3\2\2\2\t\u0101\3\2\2\2\t\u0103\3\2\2\2\t\u0105\3\2\2\2\t\u0107"+
		"\3\2\2\2\t\u0109\3\2\2\2\t\u010b\3\2\2\2\t\u010d\3\2\2\2\t\u010f\3\2\2"+
		"\2\t\u0111\3\2\2\2\t\u0113\3\2\2\2\t\u0115\3\2\2\2\t\u0117\3\2\2\2\t\u0119"+
		"\3\2\2\2\t\u011b\3\2\2\2\t\u011d\3\2\2\2\t\u011f\3\2\2\2\t\u0121\3\2\2"+
		"\2\t\u0123\3\2\2\2\t\u0125\3\2\2\2\t\u0127\3\2\2\2\t\u0129\3\2\2\2\t\u012b"+
		"\3\2\2\2\t\u012d\3\2\2\2\t\u012f\3\2\2\2\t\u0131\3\2\2\2\t\u0133\3\2\2"+
		"\2\t\u0135\3\2\2\2\t\u0137\3\2\2\2\t\u0139\3\2\2\2\t\u013b\3\2\2\2\t\u013d"+
		"\3\2\2\2\t\u013f\3\2\2\2\t\u0141\3\2\2\2\t\u0143\3\2\2\2\t\u0145\3\2\2"+
		"\2\t\u0147\3\2\2\2\t\u0149\3\2\2\2\t\u014b\3\2\2\2\t\u014d\3\2\2\2\t\u014f"+
		"\3\2\2\2\t\u0151\3\2\2\2\t\u0153\3\2\2\2\t\u0155\3\2\2\2\t\u0157\3\2\2"+
		"\2\t\u0159\3\2\2\2\t\u015b\3\2\2\2\t\u015d\3\2\2\2\t\u015f\3\2\2\2\t\u0161"+
		"\3\2\2\2\t\u0163\3\2\2\2\t\u0165\3\2\2\2\t\u0167\3\2\2\2\t\u0169\3\2\2"+
		"\2\t\u016b\3\2\2\2\t\u016d\3\2\2\2\t\u016f\3\2\2\2\t\u0171\3\2\2\2\t\u0173"+
		"\3\2\2\2\t\u0175\3\2\2\2\t\u0177\3\2\2\2\t\u0179\3\2\2\2\t\u017b\3\2\2"+
		"\2\t\u017d\3\2\2\2\t\u017f\3\2\2\2\t\u0181\3\2\2\2\t\u0183\3\2\2\2\t\u0185"+
		"\3\2\2\2\t\u0187\3\2\2\2\t\u0189\3\2\2\2\t\u018b\3\2\2\2\t\u018d\3\2\2"+
		"\2\t\u018f\3\2\2\2\t\u0191\3\2\2\2\t\u0193\3\2\2\2\t\u0195\3\2\2\2\t\u0197"+
		"\3\2\2\2\t\u0199\3\2\2\2\t\u019b\3\2\2\2\t\u019d\3\2\2\2\t\u019f\3\2\2"+
		"\2\t\u01a1\3\2\2\2\t\u01a3\3\2\2\2\t\u01a5\3\2\2\2\t\u01a7\3\2\2\2\t\u01a9"+
		"\3\2\2\2\t\u01ab\3\2\2\2\t\u01ad\3\2\2\2\t\u01af\3\2\2\2\t\u01b1\3\2\2"+
		"\2\t\u01b3\3\2\2\2\t\u01b5\3\2\2\2\t\u01b7\3\2\2\2\t\u01b9\3\2\2\2\t\u01bb"+
		"\3\2\2\2\t\u01bd\3\2\2\2\t\u01bf\3\2\2\2\t\u01c1\3\2\2\2\t\u01c3\3\2\2"+
		"\2\t\u01c5\3\2\2\2\t\u01c7\3\2\2\2\t\u01c9\3\2\2\2\t\u01cb\3\2\2\2\t\u01cd"+
		"\3\2\2\2\t\u01cf\3\2\2\2\t\u01d1\3\2\2\2\t\u01d3\3\2\2\2\t\u01d5\3\2\2"+
		"\2\t\u01d7\3\2\2\2\t\u01d9\3\2\2\2\t\u01db\3\2\2\2\n\u01dd\3\2\2\2\n\u01df"+
		"\3\2\2\2\n\u01e1\3\2\2\2\n\u01e3\3\2\2\2\n\u01e5\3\2\2\2\n\u01e7\3\2\2"+
		"\2\n\u01e9\3\2\2\2\13\u01eb\3\2\2\2\13\u01ed\3\2\2\2\13\u01ef\3\2\2\2"+
		"\13\u01f1\3\2\2\2\f\u01f3\3\2\2\2\r\u0204\3\2\2\2\17\u020b\3\2\2\2\21"+
		"\u020f\3\2\2\2\23\u0217\3\2\2\2\25\u021c\3\2\2\2\27\u0221\3\2\2\2\31\u022d"+
		"\3\2\2\2\33\u0238\3\2\2\2\35\u0249\3\2\2\2\37\u0253\3\2\2\2!\u0257\3\2"+
		"\2\2#\u0260\3\2\2\2%\u0269\3\2\2\2\'\u026e\3\2\2\2)\u0272\3\2\2\2+\u0277"+
		"\3\2\2\2-\u027b\3\2\2\2/\u0280\3\2\2\2\61\u0285\3\2\2\2\63\u0288\3\2\2"+
		"\2\65\u028d\3\2\2\2\67\u028f\3\2\2\29\u0292\3\2\2\2;\u0299\3\2\2\2=\u029f"+
		"\3\2\2\2?\u02a6\3\2\2\2A\u02ab\3\2\2\2C\u02b1\3\2\2\2E\u02b8\3\2\2\2G"+
		"\u02bc\3\2\2\2I\u02c1\3\2\2\2K\u02c6\3\2\2\2M\u02cd\3\2\2\2O\u02d1\3\2"+
		"\2\2Q\u02d5\3\2\2\2S\u02da\3\2\2\2U\u02df\3\2\2\2W\u02e6\3\2\2\2Y\u02ea"+
		"\3\2\2\2[\u02ef\3\2\2\2]\u02f3\3\2\2\2_\u0301\3\2\2\2a\u0306\3\2\2\2c"+
		"\u030b\3\2\2\2e\u0312\3\2\2\2g\u0334\3\2\2\2i\u0337\3\2\2\2k\u033d\3\2"+
		"\2\2m\u034b\3\2\2\2o\u0351\3\2\2\2q\u0356\3\2\2\2s\u035f\3\2\2\2u\u0365"+
		"\3\2\2\2w\u0368\3\2\2\2y\u036f\3\2\2\2{\u0382\3\2\2\2}\u0384\3\2\2\2\177"+
		"\u038a\3\2\2\2\u0081\u0393\3\2\2\2\u0083\u0398\3\2\2\2\u0085\u039e\3\2"+
		"\2\2\u0087\u03a4\3\2\2\2\u0089\u03aa\3\2\2\2\u008b\u03b0\3\2\2\2\u008d"+
		"\u03b9\3\2\2\2\u008f\u03c1\3\2\2\2\u0091\u03c9\3\2\2\2\u0093\u03cc\3\2"+
		"\2\2\u0095\u03d1\3\2\2\2\u0097\u03d8\3\2\2\2\u0099\u03dd\3\2\2\2\u009b"+
		"\u03e2\3\2\2\2\u009d\u03e9\3\2\2\2\u009f\u03ef\3\2\2\2\u00a1\u03fa\3\2"+
		"\2\2\u00a3\u0401\3\2\2\2\u00a5\u040c\3\2\2\2\u00a7\u0412\3\2\2\2\u00a9"+
		"\u041c\3\2\2\2\u00ab\u0425\3\2\2\2\u00ad\u042a\3\2\2\2\u00af\u042e\3\2"+
		"\2\2\u00b1\u0436\3\2\2\2\u00b3\u043c\3\2\2\2\u00b5\u0444\3\2\2\2\u00b7"+
		"\u044a\3\2\2\2\u00b9\u044e\3\2\2\2\u00bb\u0456\3\2\2\2\u00bd\u045f\3\2"+
		"\2\2\u00bf\u0466\3\2\2\2\u00c1\u046b\3\2\2\2\u00c3\u046e\3\2\2\2\u00c5"+
		"\u0479\3\2\2\2\u00c7\u0480\3\2\2\2\u00c9\u0488\3\2\2\2\u00cb\u0495\3\2"+
		"\2\2\u00cd\u04a0\3\2\2\2\u00cf\u04aa\3\2\2\2\u00d1\u04af\3\2\2\2\u00d3"+
		"\u04b5\3\2\2\2\u00d5\u04bb\3\2\2\2\u00d7\u04c5\3\2\2\2\u00d9\u04cf\3\2"+
		"\2\2\u00db\u04d5\3\2\2\2\u00dd\u04da\3\2\2\2\u00df\u04de\3\2\2\2\u00e1"+
		"\u04e1\3\2\2\2\u00e3\u04e5\3\2\2\2\u00e5\u04ef\3\2\2\2\u00e7\u04f3\3\2"+
		"\2\2\u00e9\u04f8\3\2\2\2\u00eb\u04ff\3\2\2\2\u00ed\u0506\3\2\2\2\u00ef"+
		"\u050e\3\2\2\2\u00f1\u0514\3\2\2\2\u00f3\u051c\3\2\2\2\u00f5\u0526\3\2"+
		"\2\2\u00f7\u052d\3\2\2\2\u00f9\u0535\3\2\2\2\u00fb\u0542\3\2\2\2\u00fd"+
		"\u054b\3\2\2\2\u00ff\u0552\3\2\2\2\u0101\u0559\3\2\2\2\u0103\u0560\3\2"+
		"\2\2\u0105\u0567\3\2\2\2\u0107\u056d\3\2\2\2\u0109\u0573\3\2\2\2\u010b"+
		"\u0577\3\2\2\2\u010d\u0581\3\2\2\2\u010f\u058d\3\2\2\2\u0111\u0595\3\2"+
		"\2\2\u0113\u059b\3\2\2\2\u0115\u059f\3\2\2\2\u0117\u05a3\3\2\2\2\u0119"+
		"\u05a9\3\2\2\2\u011b\u05af\3\2\2\2\u011d\u05b5\3\2\2\2\u011f\u05bb\3\2"+
		"\2\2\u0121\u05c2\3\2\2\2\u0123\u05cf\3\2\2\2\u0125\u05db\3\2\2\2\u0127"+
		"\u05e6\3\2\2\2\u0129\u05ef\3\2\2\2\u012b\u05f7\3\2\2\2\u012d\u0602\3\2"+
		"\2\2\u012f\u060a\3\2\2\2\u0131\u0612\3\2\2\2\u0133\u061d\3\2\2\2\u0135"+
		"\u0626\3\2\2\2\u0137\u0632\3\2\2\2\u0139\u063a\3\2\2\2\u013b\u0646\3\2"+
		"\2\2\u013d\u0654\3\2\2\2\u013f\u065e\3\2\2\2\u0141\u0668\3\2\2\2\u0143"+
		"\u0675\3\2\2\2\u0145\u0680\3\2\2\2\u0147\u0689\3\2\2\2\u0149\u0692\3\2"+
		"\2\2\u014b\u069a\3\2\2\2\u014d\u069d\3\2\2\2\u014f\u06a0\3\2\2\2\u0151"+
		"\u06a3\3\2\2\2\u0153\u06a6\3\2\2\2\u0155\u06a9\3\2\2\2\u0157\u06ad\3\2"+
		"\2\2\u0159\u06b1\3\2\2\2\u015b\u06b8\3\2\2\2\u015d\u06ba\3\2\2\2\u015f"+
		"\u06bd\3\2\2\2\u0161\u06c0\3\2\2\2\u0163\u06c3\3\2\2\2\u0165\u06c6\3\2"+
		"\2\2\u0167\u06c9\3\2\2\2\u0169\u06cc\3\2\2\2\u016b\u06d0\3\2\2\2\u016d"+
		"\u06d3\3\2\2\2\u016f\u06d6\3\2\2\2\u0171\u06d9\3\2\2\2\u0173\u06dd\3\2"+
		"\2\2\u0175\u06e1\3\2\2\2\u0177\u06e4\3\2\2\2\u0179\u06e7\3\2\2\2\u017b"+
		"\u06ea\3\2\2\2\u017d\u06ed\3\2\2\2\u017f\u06f0\3\2\2\2\u0181\u06f3\3\2"+
		"\2\2\u0183\u06f6\3\2\2\2\u0185\u06f9\3\2\2\2\u0187\u06fc\3\2\2\2\u0189"+
		"\u06fe\3\2\2\2\u018b\u0702\3\2\2\2\u018d\u0704\3\2\2\2\u018f\u0706\3\2"+
		"\2\2\u0191\u0708\3\2\2\2\u0193\u070a\3\2\2\2\u0195\u070c\3\2\2\2\u0197"+
		"\u070e\3\2\2\2\u0199\u0710\3\2\2\2\u019b\u0712\3\2\2\2\u019d\u0714\3\2"+
		"\2\2\u019f\u0716\3\2\2\2\u01a1\u0718\3\2\2\2\u01a3\u071a\3\2\2\2\u01a5"+
		"\u071c\3\2\2\2\u01a7\u071e\3\2\2\2\u01a9\u0720\3\2\2\2\u01ab\u0722\3\2"+
		"\2\2\u01ad\u0724\3\2\2\2\u01af\u0726\3\2\2\2\u01b1\u0728\3\2\2\2\u01b3"+
		"\u072a\3\2\2\2\u01b5\u072c\3\2\2\2\u01b7\u072f\3\2\2\2\u01b9\u0731\3\2"+
		"\2\2\u01bb\u0733\3\2\2\2\u01bd\u0735\3\2\2\2\u01bf\u0737\3\2\2\2\u01c1"+
		"\u0739\3\2\2\2\u01c3\u073b\3\2\2\2\u01c5\u0743\3\2\2\2\u01c7\u074a\3\2"+
		"\2\2\u01c9\u0751\3\2\2\2\u01cb\u0773\3\2\2\2\u01cd\u0775\3\2\2\2\u01cf"+
		"\u077d\3\2\2\2\u01d1\u0785\3\2\2\2\u01d3\u078e\3\2\2\2\u01d5\u0799\3\2"+
		"\2\2\u01d7\u079d\3\2\2\2\u01d9\u07b4\3\2\2\2\u01db\u07c9\3\2\2\2\u01dd"+
		"\u07cd\3\2\2\2\u01df\u07d7\3\2\2\2\u01e1\u07db\3\2\2\2\u01e3\u07e2\3\2"+
		"\2\2\u01e5\u07e6\3\2\2\2\u01e7\u07eb\3\2\2\2\u01e9\u07f1\3\2\2\2\u01eb"+
		"\u07f6\3\2\2\2\u01ed\u07fc\3\2\2\2\u01ef\u07ff\3\2\2\2\u01f1\u0804\3\2"+
		"\2\2\u01f3\u080c\3\2\2\2\u01f5\u0816\3\2\2\2\u01f7\u081e\3\2\2\2\u01f9"+
		"\u082d\3\2\2\2\u01fb\u0830\3\2\2\2\u01fd\u0832\3\2\2\2\u01ff\u083b\3\2"+
		"\2\2\u0201\u083d\3\2\2\2\u0203\u0205\t\2\2\2\u0204\u0203\3\2\2\2\u0205"+
		"\u0206\3\2\2\2\u0206\u0204\3\2\2\2\u0206\u0207\3\2\2\2\u0207\u0208\3\2"+
		"\2\2\u0208\u0209\b\2\2\2\u0209\16\3\2\2\2\u020a\u020c\n\3\2\2\u020b\u020a"+
		"\3\2\2\2\u020c\u020d\3\2\2\2\u020d\u020b\3\2\2\2\u020d\u020e\3\2\2\2\u020e"+
		"\20\3\2\2\2\u020f\u0210\7>\2\2\u0210\u0211\7A\2\2\u0211\u0212\7z\2\2\u0212"+
		"\u0213\7o\2\2\u0213\u0214\7n\2\2\u0214\u0215\3\2\2\2\u0215\u0216\b\4\3"+
		"\2\u0216\22\3\2\2\2\u0217\u0218\5\u01f5\u00f6\2\u0218\u0219\3\2\2\2\u0219"+
		"\u021a\b\5\4\2\u021a\u021b\b\5\5\2\u021b\24\3\2\2\2\u021c\u021d\5\u01f7"+
		"\u00f7\2\u021d\u021e\3\2\2\2\u021e\u021f\b\6\6\2\u021f\u0220\b\6\5\2\u0220"+
		"\26\3\2\2\2\u0221\u0222\7>\2\2\u0222\u0223\7u\2\2\u0223\u0224\7e\2\2\u0224"+
		"\u0225\7t\2\2\u0225\u0226\7k\2\2\u0226\u0227\7r\2\2\u0227\u0228\7v\2\2"+
		"\u0228\u0229\3\2\2\2\u0229\u022a\b\7\7\2\u022a\u022b\3\2\2\2\u022b\u022c"+
		"\b\7\b\2\u022c\30\3\2\2\2\u022d\u022e\7>\2\2\u022e\u022f\7u\2\2\u022f"+
		"\u0230\7v\2\2\u0230\u0231\7{\2\2\u0231\u0232\7n\2\2\u0232\u0233\7g\2\2"+
		"\u0233\u0234\3\2\2\2\u0234\u0235\b\b\t\2\u0235\u0236\3\2\2\2\u0236\u0237"+
		"\b\b\b\2\u0237\32\3\2\2\2\u0238\u0239\7>\2\2\u0239\u023a\7#\2\2\u023a"+
		"\u023b\7/\2\2\u023b\u023c\7/\2\2\u023c\u0240\3\2\2\2\u023d\u023f\13\2"+
		"\2\2\u023e\u023d\3\2\2\2\u023f\u0242\3\2\2\2\u0240\u0241\3\2\2\2\u0240"+
		"\u023e\3\2\2\2\u0241\u0243\3\2\2\2\u0242\u0240\3\2\2\2\u0243\u0244\7/"+
		"\2\2\u0244\u0245\7/\2\2\u0245\u0246\7@\2\2\u0246\u0247\3\2\2\2\u0247\u0248"+
		"\b\t\2\2\u0248\34\3\2\2\2\u0249\u024a\7>\2\2\u024a\u024e\7#\2\2\u024b"+
		"\u024d\13\2\2\2\u024c\u024b\3\2\2\2\u024d\u0250\3\2\2\2\u024e\u024f\3"+
		"\2\2\2\u024e\u024c\3\2\2\2\u024f\u0251\3\2\2\2\u0250\u024e\3\2\2\2\u0251"+
		"\u0252\7@\2\2\u0252\36\3\2\2\2\u0253\u0254\7>\2\2\u0254\u0255\3\2\2\2"+
		"\u0255\u0256\b\13\b\2\u0256 \3\2\2\2\u0257\u0258\7%\2\2\u0258\u0259\6"+
		"\f\2\2\u0259\u025d\7#\2\2\u025a\u025c\n\4\2\2\u025b\u025a\3\2\2\2\u025c"+
		"\u025f\3\2\2\2\u025d\u025b\3\2\2\2\u025d\u025e\3\2\2\2\u025e\"\3\2\2\2"+
		"\u025f\u025d\3\2\2\2\u0260\u0264\7%\2\2\u0261\u0263\n\5\2\2\u0262\u0261"+
		"\3\2\2\2\u0263\u0266\3\2\2\2\u0264\u0262\3\2\2\2\u0264\u0265\3\2\2\2\u0265"+
		"\u0267\3\2\2\2\u0266\u0264\3\2\2\2\u0267\u0268\b\r\n\2\u0268$\3\2\2\2"+
		"\u0269\u026a\13\2\2\2\u026a\u026b\3\2\2\2\u026b\u026c\b\16\13\2\u026c"+
		"&\3\2\2\2\u026d\u026f\n\6\2\2\u026e\u026d\3\2\2\2\u026f\u0270\3\2\2\2"+
		"\u0270\u026e\3\2\2\2\u0270\u0271\3\2\2\2\u0271(\3\2\2\2\u0272\u0273\7"+
		"A\2\2\u0273\u0274\7@\2\2\u0274\u0275\3\2\2\2\u0275\u0276\b\20\f\2\u0276"+
		"*\3\2\2\2\u0277\u0278\7A\2\2\u0278\u0279\3\2\2\2\u0279\u027a\b\21\r\2"+
		"\u027a,\3\2\2\2\u027b\u027c\5\u01f5\u00f6\2\u027c\u027d\3\2\2\2\u027d"+
		"\u027e\b\22\4\2\u027e\u027f\b\22\5\2\u027f.\3\2\2\2\u0280\u0281\5\u01f7"+
		"\u00f7\2\u0281\u0282\3\2\2\2\u0282\u0283\b\23\6\2\u0283\u0284\b\23\5\2"+
		"\u0284\60\3\2\2\2\u0285\u0286\7@\2\2\u0286\u0287\b\24\16\2\u0287\62\3"+
		"\2\2\2\u0288\u0289\7\61\2\2\u0289\u028a\7@\2\2\u028a\u028b\3\2\2\2\u028b"+
		"\u028c\b\25\f\2\u028c\64\3\2\2\2\u028d\u028e\7\61\2\2\u028e\66\3\2\2\2"+
		"\u028f\u0290\7?\2\2\u02908\3\2\2\2\u0291\u0293\7^\2\2\u0292\u0291\3\2"+
		"\2\2\u0292\u0293\3\2\2\2\u0293\u0294\3\2\2\2\u0294\u0295\7)\2\2\u0295"+
		"\u0296\3\2\2\2\u0296\u0297\b\30\17\2\u0297:\3\2\2\2\u0298\u029a\7^\2\2"+
		"\u0299\u0298\3\2\2\2\u0299\u029a\3\2\2\2\u029a\u029b\3\2\2\2\u029b\u029c"+
		"\7$\2\2\u029c\u029d\3\2\2\2\u029d\u029e\b\31\20\2\u029e<\3\2\2\2\u029f"+
		"\u02a1\7%\2\2\u02a0\u02a2\5\u0201\u00fc\2\u02a1\u02a0\3\2\2\2\u02a2\u02a3"+
		"\3\2\2\2\u02a3\u02a1\3\2\2\2\u02a3\u02a4\3\2\2\2\u02a4>\3\2\2\2\u02a5"+
		"\u02a7\5\u01ff\u00fb\2\u02a6\u02a5\3\2\2\2\u02a7\u02a8\3\2\2\2\u02a8\u02a6"+
		"\3\2\2\2\u02a8\u02a9\3\2\2\2\u02a9@\3\2\2\2\u02aa\u02ac\t\2\2\2\u02ab"+
		"\u02aa\3\2\2\2\u02ac\u02ad\3\2\2\2\u02ad\u02ab\3\2\2\2\u02ad\u02ae\3\2"+
		"\2\2\u02ae\u02af\3\2\2\2\u02af\u02b0\b\34\2\2\u02b0B\3\2\2\2\u02b1\u02b5"+
		"\5\u01fb\u00f9\2\u02b2\u02b4\5\u01f9\u00f8\2\u02b3\u02b2\3\2\2\2\u02b4"+
		"\u02b7\3\2\2\2\u02b5\u02b3\3\2\2\2\u02b5\u02b6\3\2\2\2\u02b6D\3\2\2\2"+
		"\u02b7\u02b5\3\2\2\2\u02b8\u02b9\13\2\2\2\u02b9\u02ba\3\2\2\2\u02ba\u02bb"+
		"\b\36\13\2\u02bbF\3\2\2\2\u02bc\u02bd\5\u01f5\u00f6\2\u02bd\u02be\3\2"+
		"\2\2\u02be\u02bf\b\37\4\2\u02bf\u02c0\b\37\5\2\u02c0H\3\2\2\2\u02c1\u02c2"+
		"\5\u01f7\u00f7\2\u02c2\u02c3\3\2\2\2\u02c3\u02c4\b \6\2\u02c4\u02c5\b"+
		" \5\2\u02c5J\3\2\2\2\u02c6\u02c8\7)\2\2\u02c7\u02c9\7)\2\2\u02c8\u02c7"+
		"\3\2\2\2\u02c8\u02c9\3\2\2\2\u02c9\u02ca\3\2\2\2\u02ca\u02cb\b!\f\2\u02cb"+
		"L\3\2\2\2\u02cc\u02ce\n\7\2\2\u02cd\u02cc\3\2\2\2\u02ce\u02cf\3\2\2\2"+
		"\u02cf\u02cd\3\2\2\2\u02cf\u02d0\3\2\2\2\u02d0N\3\2\2\2\u02d1\u02d2\13"+
		"\2\2\2\u02d2\u02d3\3\2\2\2\u02d3\u02d4\b#\13\2\u02d4P\3\2\2\2\u02d5\u02d6"+
		"\5\u01f5\u00f6\2\u02d6\u02d7\3\2\2\2\u02d7\u02d8\b$\4\2\u02d8\u02d9\b"+
		"$\5\2\u02d9R\3\2\2\2\u02da\u02db\5\u01f7\u00f7\2\u02db\u02dc\3\2\2\2\u02dc"+
		"\u02dd\b%\6\2\u02dd\u02de\b%\5\2\u02deT\3\2\2\2\u02df\u02e1\7$\2\2\u02e0"+
		"\u02e2\7$\2\2\u02e1\u02e0\3\2\2\2\u02e1\u02e2\3\2\2\2\u02e2\u02e3\3\2"+
		"\2\2\u02e3\u02e4\b&\f\2\u02e4V\3\2\2\2\u02e5\u02e7\n\b\2\2\u02e6\u02e5"+
		"\3\2\2\2\u02e7\u02e8\3\2\2\2\u02e8\u02e6\3\2\2\2\u02e8\u02e9\3\2\2\2\u02e9"+
		"X\3\2\2\2\u02ea\u02eb\13\2\2\2\u02eb\u02ec\3\2\2\2\u02ec\u02ed\b(\13\2"+
		"\u02edZ\3\2\2\2\u02ee\u02f0\n\5\2\2\u02ef\u02ee\3\2\2\2\u02f0\u02f1\3"+
		"\2\2\2\u02f1\u02ef\3\2\2\2\u02f1\u02f2\3\2\2\2\u02f2\\\3\2\2\2\u02f3\u02f4"+
		"\7>\2\2\u02f4\u02fb\7\61\2\2\u02f5\u02f6\7u\2\2\u02f6\u02f7\7e\2\2\u02f7"+
		"\u02f8\7t\2\2\u02f8\u02f9\7k\2\2\u02f9\u02fa\7r\2\2\u02fa\u02fc\7v\2\2"+
		"\u02fb\u02f5\3\2\2\2\u02fb\u02fc\3\2\2\2\u02fc\u02fd\3\2\2\2\u02fd\u02fe"+
		"\7@\2\2\u02fe\u02ff\3\2\2\2\u02ff\u0300\b*\f\2\u0300^\3\2\2\2\u0301\u0302"+
		"\5\u01f5\u00f6\2\u0302\u0303\3\2\2\2\u0303\u0304\b+\4\2\u0304\u0305\b"+
		"+\5\2\u0305`\3\2\2\2\u0306\u0307\5\u01f7\u00f7\2\u0307\u0308\3\2\2\2\u0308"+
		"\u0309\b,\6\2\u0309\u030a\b,\5\2\u030ab\3\2\2\2\u030b\u030c\7>\2\2\u030c"+
		"\u030d\3\2\2\2\u030d\u030e\b-\21\2\u030ed\3\2\2\2\u030f\u0311\13\2\2\2"+
		"\u0310\u030f\3\2\2\2\u0311\u0314\3\2\2\2\u0312\u0313\3\2\2\2\u0312\u0310"+
		"\3\2\2\2\u0313\u0315\3\2\2\2\u0314\u0312\3\2\2\2\u0315\u0316\7>\2\2\u0316"+
		"\u0317\7\61\2\2\u0317\u031d\3\2\2\2\u0318\u0319\7u\2\2\u0319\u031a\7v"+
		"\2\2\u031a\u031b\7{\2\2\u031b\u031c\7n\2\2\u031c\u031e\7g\2\2\u031d\u0318"+
		"\3\2\2\2\u031d\u031e\3\2\2\2\u031e\u031f\3\2\2\2\u031f\u0320\7@\2\2\u0320"+
		"\u0321\3\2\2\2\u0321\u0322\b.\f\2\u0322f\3\2\2\2\u0323\u0327\7A\2\2\u0324"+
		"\u0325\7\'\2\2\u0325\u0327\6/\3\2\u0326\u0323\3\2\2\2\u0326\u0324\3\2"+
		"\2\2\u0327\u0328\3\2\2\2\u0328\u0335\7@\2\2\u0329\u032a\7>\2\2\u032a\u032b"+
		"\7\61\2\2\u032b\u032c\7u\2\2\u032c\u032d\7e\2\2\u032d\u032e\7t\2\2\u032e"+
		"\u032f\7k\2\2\u032f\u0330\7r\2\2\u0330\u0331\7v\2\2\u0331\u0332\7@\2\2"+
		"\u0332\u0333\3\2\2\2\u0333\u0335\6/\4\2\u0334\u0326\3\2\2\2\u0334\u0329"+
		"\3\2\2\2\u0335h\3\2\2\2\u0336\u0338\t\2\2\2\u0337\u0336\3\2\2\2\u0338"+
		"\u0339\3\2\2\2\u0339\u0337\3\2\2\2\u0339\u033a\3\2\2\2\u033a\u033b\3\2"+
		"\2\2\u033b\u033c\b\60\6\2\u033cj\3\2\2\2\u033d\u033e\7\61\2\2\u033e\u033f"+
		"\7,\2\2\u033f\u0343\3\2\2\2\u0340\u0342\13\2\2\2\u0341\u0340\3\2\2\2\u0342"+
		"\u0345\3\2\2\2\u0343\u0344\3\2\2\2\u0343\u0341\3\2\2\2\u0344\u0346\3\2"+
		"\2\2\u0345\u0343\3\2\2\2\u0346\u0347\7,\2\2\u0347\u0348\7\61\2\2\u0348"+
		"\u0349\3\2\2\2\u0349\u034a\b\61\22\2\u034al\3\2\2\2\u034b\u034c\7\61\2"+
		"\2\u034c\u034d\7\61\2\2\u034d\u034e\3\2\2\2\u034e\u034f\b\62\6\2\u034f"+
		"\u0350\b\62\23\2\u0350n\3\2\2\2\u0351\u0352\7%\2\2\u0352\u0353\3\2\2\2"+
		"\u0353\u0354\b\63\6\2\u0354\u0355\b\63\23\2\u0355p\3\2\2\2\u0356\u0357"+
		"\7c\2\2\u0357\u0358\7d\2\2\u0358\u0359\7u\2\2\u0359\u035a\7v\2\2\u035a"+
		"\u035b\7t\2\2\u035b\u035c\7c\2\2\u035c\u035d\7e\2\2\u035d\u035e\7v\2\2"+
		"\u035er\3\2\2\2\u035f\u0360\7c\2\2\u0360\u0361\7t\2\2\u0361\u0362\7t\2"+
		"\2\u0362\u0363\7c\2\2\u0363\u0364\7{\2\2\u0364t\3\2\2\2\u0365\u0366\7"+
		"c\2\2\u0366\u0367\7u\2\2\u0367v\3\2\2\2\u0368\u0369\7d\2\2\u0369\u036a"+
		"\7k\2\2\u036a\u036b\7p\2\2\u036b\u036c\7c\2\2\u036c\u036d\7t\2\2\u036d"+
		"\u036e\7{\2\2\u036ex\3\2\2\2\u036f\u0370\7d\2\2\u0370\u0371\7q\2\2\u0371"+
		"\u0372\7q\2\2\u0372\u0373\7n\2\2\u0373\u0377\3\2\2\2\u0374\u0375\7g\2"+
		"\2\u0375\u0376\7c\2\2\u0376\u0378\7p\2\2\u0377\u0374\3\2\2\2\u0377\u0378"+
		"\3\2\2\2\u0378z\3\2\2\2\u0379\u037a\7v\2\2\u037a\u037b\7t\2\2\u037b\u037c"+
		"\7w\2\2\u037c\u0383\7g\2\2\u037d\u037e\7h\2\2\u037e\u037f\7c\2\2\u037f"+
		"\u0380\7n\2\2\u0380\u0381\7u\2\2\u0381\u0383\7g\2\2\u0382\u0379\3\2\2"+
		"\2\u0382\u037d\3\2\2\2\u0383|\3\2\2\2\u0384\u0385\7d\2\2\u0385\u0386\7"+
		"t\2\2\u0386\u0387\7g\2\2\u0387\u0388\7c\2\2\u0388\u0389\7m\2\2\u0389~"+
		"\3\2\2\2\u038a\u038b\7e\2\2\u038b\u038c\7c\2\2\u038c\u038d\7n\2\2\u038d"+
		"\u038e\7n\2\2\u038e\u038f\7c\2\2\u038f\u0390\7d\2\2\u0390\u0391\7n\2\2"+
		"\u0391\u0392\7g\2\2\u0392\u0080\3\2\2\2\u0393\u0394\7e\2\2\u0394\u0395"+
		"\7c\2\2\u0395\u0396\7u\2\2\u0396\u0397\7g\2\2\u0397\u0082\3\2\2\2\u0398"+
		"\u0399\7e\2\2\u0399\u039a\7c\2\2\u039a\u039b\7v\2\2\u039b\u039c\7e\2\2"+
		"\u039c\u039d\7j\2\2\u039d\u0084\3\2\2\2\u039e\u039f\7e\2\2\u039f\u03a0"+
		"\7n\2\2\u03a0\u03a1\7c\2\2\u03a1\u03a2\7u\2\2\u03a2\u03a3\7u\2\2\u03a3"+
		"\u0086\3\2\2\2\u03a4\u03a5\7e\2\2\u03a5\u03a6\7n\2\2\u03a6\u03a7\7q\2"+
		"\2\u03a7\u03a8\7p\2\2\u03a8\u03a9\7g\2\2\u03a9\u0088\3\2\2\2\u03aa\u03ab"+
		"\7e\2\2\u03ab\u03ac\7q\2\2\u03ac\u03ad\7p\2\2\u03ad\u03ae\7u\2\2\u03ae"+
		"\u03af\7v\2\2\u03af\u008a\3\2\2\2\u03b0\u03b1\7e\2\2\u03b1\u03b2\7q\2"+
		"\2\u03b2\u03b3\7p\2\2\u03b3\u03b4\7v\2\2\u03b4\u03b5\7k\2\2\u03b5\u03b6"+
		"\7p\2\2\u03b6\u03b7\7w\2\2\u03b7\u03b8\7g\2\2\u03b8\u008c\3\2\2\2\u03b9"+
		"\u03ba\7f\2\2\u03ba\u03bb\7g\2\2\u03bb\u03bc\7e\2\2\u03bc\u03bd\7n\2\2"+
		"\u03bd\u03be\7c\2\2\u03be\u03bf\7t\2\2\u03bf\u03c0\7g\2\2\u03c0\u008e"+
		"\3\2\2\2\u03c1\u03c2\7f\2\2\u03c2\u03c3\7g\2\2\u03c3\u03c4\7h\2\2\u03c4"+
		"\u03c5\7c\2\2\u03c5\u03c6\7w\2\2\u03c6\u03c7\7n\2\2\u03c7\u03c8\7v\2\2"+
		"\u03c8\u0090\3\2\2\2\u03c9\u03ca\7f\2\2\u03ca\u03cb\7q\2\2\u03cb\u0092"+
		"\3\2\2\2\u03cc\u03cd\7t\2\2\u03cd\u03ce\7g\2\2\u03ce\u03cf\7c\2\2\u03cf"+
		"\u03d0\7n\2\2\u03d0\u0094\3\2\2\2\u03d1\u03d2\7f\2\2\u03d2\u03d3\7q\2"+
		"\2\u03d3\u03d4\7w\2\2\u03d4\u03d5\7d\2\2\u03d5\u03d6\7n\2\2\u03d6\u03d7"+
		"\7g\2\2\u03d7\u0096\3\2\2\2\u03d8\u03d9\7g\2\2\u03d9\u03da\7e\2\2\u03da"+
		"\u03db\7j\2\2\u03db\u03dc\7q\2\2\u03dc\u0098\3\2\2\2\u03dd\u03de\7g\2"+
		"\2\u03de\u03df\7n\2\2\u03df\u03e0\7u\2\2\u03e0\u03e1\7g\2\2\u03e1\u009a"+
		"\3\2\2\2\u03e2\u03e3\7g\2\2\u03e3\u03e4\7n\2\2\u03e4\u03e5\7u\2\2\u03e5"+
		"\u03e6\7g\2\2\u03e6\u03e7\7k\2\2\u03e7\u03e8\7h\2\2\u03e8\u009c\3\2\2"+
		"\2\u03e9\u03ea\7g\2\2\u03ea\u03eb\7o\2\2\u03eb\u03ec\7r\2\2\u03ec\u03ed"+
		"\7v\2\2\u03ed\u03ee\7{\2\2\u03ee\u009e\3\2\2\2\u03ef\u03f0\7g\2\2\u03f0"+
		"\u03f1\7p\2\2\u03f1\u03f2\7f\2\2\u03f2\u03f3\7f\2\2\u03f3\u03f4\7g\2\2"+
		"\u03f4\u03f5\7e\2\2\u03f5\u03f6\7n\2\2\u03f6\u03f7\7c\2\2\u03f7\u03f8"+
		"\7t\2\2\u03f8\u03f9\7g\2\2\u03f9\u00a0\3\2\2\2\u03fa\u03fb\7g\2\2\u03fb"+
		"\u03fc\7p\2\2\u03fc\u03fd\7f\2\2\u03fd\u03fe\7h\2\2\u03fe\u03ff\7q\2\2"+
		"\u03ff\u0400\7t\2\2\u0400\u00a2\3\2\2\2\u0401\u0402\7g\2\2\u0402\u0403"+
		"\7p\2\2\u0403\u0404\7f\2\2\u0404\u0405\7h\2\2\u0405\u0406\7q\2\2\u0406"+
		"\u0407\7t\2\2\u0407\u0408\7g\2\2\u0408\u0409\7c\2\2\u0409\u040a\7e\2\2"+
		"\u040a\u040b\7j\2\2\u040b\u00a4\3\2\2\2\u040c\u040d\7g\2\2\u040d\u040e"+
		"\7p\2\2\u040e\u040f\7f\2\2\u040f\u0410\7k\2\2\u0410\u0411\7h\2\2\u0411"+
		"\u00a6\3\2\2\2\u0412\u0413\7g\2\2\u0413\u0414\7p\2\2\u0414\u0415\7f\2"+
		"\2\u0415\u0416\7u\2\2\u0416\u0417\7y\2\2\u0417\u0418\7k\2\2\u0418\u0419"+
		"\7v\2\2\u0419\u041a\7e\2\2\u041a\u041b\7j\2\2\u041b\u00a8\3\2\2\2\u041c"+
		"\u041d\7g\2\2\u041d\u041e\7p\2\2\u041e\u041f\7f\2\2\u041f\u0420\7y\2\2"+
		"\u0420\u0421\7j\2\2\u0421\u0422\7k\2\2\u0422\u0423\7n\2\2\u0423\u0424"+
		"\7g\2\2\u0424\u00aa\3\2\2\2\u0425\u0426\7g\2\2\u0426\u0427\7x\2\2\u0427"+
		"\u0428\7c\2\2\u0428\u0429\7n\2\2\u0429\u00ac\3\2\2\2\u042a\u042b\7f\2"+
		"\2\u042b\u042c\7k\2\2\u042c\u042d\7g\2\2\u042d\u00ae\3\2\2\2\u042e\u042f"+
		"\7g\2\2\u042f\u0430\7z\2\2\u0430\u0431\7v\2\2\u0431\u0432\7g\2\2\u0432"+
		"\u0433\7p\2\2\u0433\u0434\7f\2\2\u0434\u0435\7u\2\2\u0435\u00b0\3\2\2"+
		"\2\u0436\u0437\7h\2\2\u0437\u0438\7k\2\2\u0438\u0439\7p\2\2\u0439\u043a"+
		"\7c\2\2\u043a\u043b\7n\2\2\u043b\u00b2\3\2\2\2\u043c\u043d\7h\2\2\u043d"+
		"\u043e\7k\2\2\u043e\u043f\7p\2\2\u043f\u0440\7c\2\2\u0440\u0441\7n\2\2"+
		"\u0441\u0442\7n\2\2\u0442\u0443\7{\2\2\u0443\u00b4\3\2\2\2\u0444\u0445"+
		"\7h\2\2\u0445\u0446\7n\2\2\u0446\u0447\7q\2\2\u0447\u0448\7c\2\2\u0448"+
		"\u0449\7v\2\2\u0449\u00b6\3\2\2\2\u044a\u044b\7h\2\2\u044b\u044c\7q\2"+
		"\2\u044c\u044d\7t\2\2\u044d\u00b8\3\2\2\2\u044e\u044f\7h\2\2\u044f\u0450"+
		"\7q\2\2\u0450\u0451\7t\2\2\u0451\u0452\7g\2\2\u0452\u0453\7c\2\2\u0453"+
		"\u0454\7e\2\2\u0454\u0455\7j\2\2\u0455\u00ba\3\2\2\2\u0456\u0457\7h\2"+
		"\2\u0457\u0458\7w\2\2\u0458\u0459\7p\2\2\u0459\u045a\7e\2\2\u045a\u045b"+
		"\7v\2\2\u045b\u045c\7k\2\2\u045c\u045d\7q\2\2\u045d\u045e\7p\2\2\u045e"+
		"\u00bc\3\2\2\2\u045f\u0460\7i\2\2\u0460\u0461\7n\2\2\u0461\u0462\7q\2"+
		"\2\u0462\u0463\7d\2\2\u0463\u0464\7c\2\2\u0464\u0465\7n\2\2\u0465\u00be"+
		"\3\2\2\2\u0466\u0467\7i\2\2\u0467\u0468\7q\2\2\u0468\u0469\7v\2\2\u0469"+
		"\u046a\7q\2\2\u046a\u00c0\3\2\2\2\u046b\u046c\7k\2\2\u046c\u046d\7h\2"+
		"\2\u046d\u00c2\3\2\2\2\u046e\u046f\7k\2\2\u046f\u0470\7o\2\2\u0470\u0471"+
		"\7r\2\2\u0471\u0472\7n\2\2\u0472\u0473\7g\2\2\u0473\u0474\7o\2\2\u0474"+
		"\u0475\7g\2\2\u0475\u0476\7p\2\2\u0476\u0477\7v\2\2\u0477\u0478\7u\2\2"+
		"\u0478\u00c4\3\2\2\2\u0479\u047a\7k\2\2\u047a\u047b\7o\2\2\u047b\u047c"+
		"\7r\2\2\u047c\u047d\7q\2\2\u047d\u047e\7t\2\2\u047e\u047f\7v\2\2\u047f"+
		"\u00c6\3\2\2\2\u0480\u0481\7k\2\2\u0481\u0482\7p\2\2\u0482\u0483\7e\2"+
		"\2\u0483\u0484\7n\2\2\u0484\u0485\7w\2\2\u0485\u0486\7f\2\2\u0486\u0487"+
		"\7g\2\2\u0487\u00c8\3\2\2\2\u0488\u0489\7k\2\2\u0489\u048a\7p\2\2\u048a"+
		"\u048b\7e\2\2\u048b\u048c\7n\2\2\u048c\u048d\7w\2\2\u048d\u048e\7f\2\2"+
		"\u048e\u048f\7g\2\2\u048f\u0490\7a\2\2\u0490\u0491\7q\2\2\u0491\u0492"+
		"\7p\2\2\u0492\u0493\7e\2\2\u0493\u0494\7g\2\2\u0494\u00ca\3\2\2\2\u0495"+
		"\u0496\7k\2\2\u0496\u0497\7p\2\2\u0497\u0498\7u\2\2\u0498\u0499\7v\2\2"+
		"\u0499\u049a\7c\2\2\u049a\u049b\7p\2\2\u049b\u049c\7e\2\2\u049c\u049d"+
		"\7g\2\2\u049d\u049e\7q\2\2\u049e\u049f\7h\2\2\u049f\u00cc\3\2\2\2\u04a0"+
		"\u04a1\7k\2\2\u04a1\u04a2\7p\2\2\u04a2\u04a3\7u\2\2\u04a3\u04a4\7v\2\2"+
		"\u04a4\u04a5\7g\2\2\u04a5\u04a6\7c\2\2\u04a6\u04a7\7f\2\2\u04a7\u04a8"+
		"\7q\2\2\u04a8\u04a9\7h\2\2\u04a9\u00ce\3\2\2\2\u04aa\u04ab\7k\2\2\u04ab"+
		"\u04ac\7p\2\2\u04ac\u04ad\7v\2\2\u04ad\u04ae\7:\2\2\u04ae\u00d0\3\2\2"+
		"\2\u04af\u04b0\7k\2\2\u04b0\u04b1\7p\2\2\u04b1\u04b2\7v\2\2\u04b2\u04b3"+
		"\7\63\2\2\u04b3\u04b4\78\2\2\u04b4\u00d2\3\2\2\2\u04b5\u04b6\7k\2\2\u04b6"+
		"\u04b7\7p\2\2\u04b7\u04b8\7v\2\2\u04b8\u04b9\78\2\2\u04b9\u04ba\7\66\2"+
		"\2\u04ba\u00d4\3\2\2\2\u04bb\u04bc\7k\2\2\u04bc\u04bd\7p\2\2\u04bd\u04be"+
		"\7v\2\2\u04be\u04c3\3\2\2\2\u04bf\u04c0\7g\2\2\u04c0\u04c1\7i\2\2\u04c1"+
		"\u04c2\7g\2\2\u04c2\u04c4\7t\2\2\u04c3\u04bf\3\2\2\2\u04c3\u04c4\3\2\2"+
		"\2\u04c4\u00d6\3\2\2\2\u04c5\u04c6\7k\2\2\u04c6\u04c7\7p\2\2\u04c7\u04c8"+
		"\7v\2\2\u04c8\u04c9\7g\2\2\u04c9\u04ca\7t\2\2\u04ca\u04cb\7h\2\2\u04cb"+
		"\u04cc\7c\2\2\u04cc\u04cd\7e\2\2\u04cd\u04ce\7g\2\2\u04ce\u00d8\3\2\2"+
		"\2\u04cf\u04d0\7k\2\2\u04d0\u04d1\7u\2\2\u04d1\u04d2\7u\2\2\u04d2\u04d3"+
		"\7g\2\2\u04d3\u04d4\7v\2\2\u04d4\u00da\3\2\2\2\u04d5\u04d6\7n\2\2\u04d6"+
		"\u04d7\7k\2\2\u04d7\u04d8\7u\2\2\u04d8\u04d9\7v\2\2\u04d9\u00dc\3\2\2"+
		"\2\u04da\u04db\7c\2\2\u04db\u04dc\7p\2\2\u04dc\u04dd\7f\2\2\u04dd\u00de"+
		"\3\2\2\2\u04de\u04df\7q\2\2\u04df\u04e0\7t\2\2\u04e0\u00e0\3\2\2\2\u04e1"+
		"\u04e2\7z\2\2\u04e2\u04e3\7q\2\2\u04e3\u04e4\7t\2\2\u04e4\u00e2\3\2\2"+
		"\2\u04e5\u04e6\7p\2\2\u04e6\u04e7\7c\2\2\u04e7\u04e8\7o\2\2\u04e8\u04e9"+
		"\7g\2\2\u04e9\u04ea\7u\2\2\u04ea\u04eb\7r\2\2\u04eb\u04ec\7c\2\2\u04ec"+
		"\u04ed\7e\2\2\u04ed\u04ee\7g\2\2\u04ee\u00e4\3\2\2\2\u04ef\u04f0\7p\2"+
		"\2\u04f0\u04f1\7g\2\2\u04f1\u04f2\7y\2\2\u04f2\u00e6\3\2\2\2\u04f3\u04f4"+
		"\7p\2\2\u04f4\u04f5\7w\2\2\u04f5\u04f6\7n\2\2\u04f6\u04f7\7n\2\2\u04f7"+
		"\u00e8\3\2\2\2\u04f8\u04f9\7q\2\2\u04f9\u04fa\7d\2\2\u04fa\u04fb\7l\2"+
		"\2\u04fb\u04fc\7g\2\2\u04fc\u04fd\7e\2\2\u04fd\u04fe\7v\2\2\u04fe\u00ea"+
		"\3\2\2\2\u04ff\u0500\7r\2\2\u0500\u0501\7c\2\2\u0501\u0502\7t\2\2\u0502"+
		"\u0503\7g\2\2\u0503\u0504\7p\2\2\u0504\u0505\7v\2\2\u0505\u00ec\3\2\2"+
		"\2\u0506\u0507\7r\2\2\u0507\u0508\7c\2\2\u0508\u0509\7t\2\2\u0509\u050a"+
		"\7v\2\2\u050a\u050b\7k\2\2\u050b\u050c\7c\2\2\u050c\u050d\7n\2\2\u050d"+
		"\u00ee\3\2\2\2\u050e\u050f\7r\2\2\u050f\u0510\7t\2\2\u0510\u0511\7k\2"+
		"\2\u0511\u0512\7p\2\2\u0512\u0513\7v\2\2\u0513\u00f0\3\2\2\2\u0514\u0515"+
		"\7r\2\2\u0515\u0516\7t\2\2\u0516\u0517\7k\2\2\u0517\u0518\7x\2\2\u0518"+
		"\u0519\7c\2\2\u0519\u051a\7v\2\2\u051a\u051b\7g\2\2\u051b\u00f2\3\2\2"+
		"\2\u051c\u051d\7r\2\2\u051d\u051e\7t\2\2\u051e\u051f\7q\2\2\u051f\u0520"+
		"\7v\2\2\u0520\u0521\7g\2\2\u0521\u0522\7e\2\2\u0522\u0523\7v\2\2\u0523"+
		"\u0524\7g\2\2\u0524\u0525\7f\2\2\u0525\u00f4\3\2\2\2\u0526\u0527\7r\2"+
		"\2\u0527\u0528\7w\2\2\u0528\u0529\7d\2\2\u0529\u052a\7n\2\2\u052a\u052b"+
		"\7k\2\2\u052b\u052c\7e\2\2\u052c\u00f6\3\2\2\2\u052d\u052e\7t\2\2\u052e"+
		"\u052f\7g\2\2\u052f\u0530\7s\2\2\u0530\u0531\7w\2\2\u0531\u0532\7k\2\2"+
		"\u0532\u0533\7t\2\2\u0533\u0534\7g\2\2\u0534\u00f8\3\2\2\2\u0535\u0536"+
		"\7t\2\2\u0536\u0537\7g\2\2\u0537\u0538\7s\2\2\u0538\u0539\7w\2\2\u0539"+
		"\u053a\7k\2\2\u053a\u053b\7t\2\2\u053b\u053c\7g\2\2\u053c\u053d\7a\2\2"+
		"\u053d\u053e\7q\2\2\u053e\u053f\7p\2\2\u053f\u0540\7e\2\2\u0540\u0541"+
		"\7g\2\2\u0541\u00fa\3\2\2\2\u0542\u0543\7t\2\2\u0543\u0544\7g\2\2\u0544"+
		"\u0545\7u\2\2\u0545\u0546\7q\2\2\u0546\u0547\7w\2\2\u0547\u0548\7t\2\2"+
		"\u0548\u0549\7e\2\2\u0549\u054a\7g\2\2\u054a\u00fc\3\2\2\2\u054b\u054c"+
		"\7t\2\2\u054c\u054d\7g\2\2\u054d\u054e\7v\2\2\u054e\u054f\7w\2\2\u054f"+
		"\u0550\7t\2\2\u0550\u0551\7p\2\2\u0551\u00fe\3\2\2\2\u0552\u0553\7u\2"+
		"\2\u0553\u0554\7v\2\2\u0554\u0555\7c\2\2\u0555\u0556\7v\2\2\u0556\u0557"+
		"\7k\2\2\u0557\u0558\7e\2\2\u0558\u0100\3\2\2\2\u0559\u055a\7u\2\2\u055a"+
		"\u055b\7v\2\2\u055b\u055c\7t\2\2\u055c\u055d\7k\2\2\u055d\u055e\7p\2\2"+
		"\u055e\u055f\7i\2\2\u055f\u0102\3\2\2\2\u0560\u0561\7u\2\2\u0561\u0562"+
		"\7y\2\2\u0562\u0563\7k\2\2\u0563\u0564\7v\2\2\u0564\u0565\7e\2\2\u0565"+
		"\u0566\7j\2\2\u0566\u0104\3\2\2\2\u0567\u0568\7v\2\2\u0568\u0569\7j\2"+
		"\2\u0569\u056a\7t\2\2\u056a\u056b\7q\2\2\u056b\u056c\7y\2\2\u056c\u0106"+
		"\3\2\2\2\u056d\u056e\7v\2\2\u056e\u056f\7t\2\2\u056f\u0570\7c\2\2\u0570"+
		"\u0571\7k\2\2\u0571\u0572\7v\2\2\u0572\u0108\3\2\2\2\u0573\u0574\7v\2"+
		"\2\u0574\u0575\7t\2\2\u0575\u0576\7{\2\2\u0576\u010a\3\2\2\2\u0577\u0578"+
		"\7e\2\2\u0578\u0579\7n\2\2\u0579\u057a\7t\2\2\u057a\u057b\7v\2\2\u057b"+
		"\u057c\7{\2\2\u057c\u057d\7r\2\2\u057d\u057e\7g\2\2\u057e\u057f\7q\2\2"+
		"\u057f\u0580\7h\2\2\u0580\u010c\3\2\2\2\u0581\u0582\7w\2\2\u0582\u0583"+
		"\7k\2\2\u0583\u0584\7p\2\2\u0584\u0585\7v\2\2\u0585\u058b\3\2\2\2\u0586"+
		"\u058c\7:\2\2\u0587\u0588\7\63\2\2\u0588\u058c\78\2\2\u0589\u058a\78\2"+
		"\2\u058a\u058c\7\66\2\2\u058b\u0586\3\2\2\2\u058b\u0587\3\2\2\2\u058b"+
		"\u0589\3\2\2\2\u058b\u058c\3\2\2\2\u058c\u010e\3\2\2\2\u058d\u058e\7w"+
		"\2\2\u058e\u058f\7p\2\2\u058f\u0590\7k\2\2\u0590\u0591\7e\2\2\u0591\u0592"+
		"\7q\2\2\u0592\u0593\7f\2\2\u0593\u0594\7g\2\2\u0594\u0110\3\2\2\2\u0595"+
		"\u0596\7w\2\2\u0596\u0597\7p\2\2\u0597\u0598\7u\2\2\u0598\u0599\7g\2\2"+
		"\u0599\u059a\7v\2\2\u059a\u0112\3\2\2\2\u059b\u059c\7w\2\2\u059c\u059d"+
		"\7u\2\2\u059d\u059e\7g\2\2\u059e\u0114\3\2\2\2\u059f\u05a0\7x\2\2\u05a0"+
		"\u05a1\7c\2\2\u05a1\u05a2\7t\2\2\u05a2\u0116\3\2\2\2\u05a3\u05a4\7y\2"+
		"\2\u05a4\u05a5\7j\2\2\u05a5\u05a6\7k\2\2\u05a6\u05a7\7n\2\2\u05a7\u05a8"+
		"\7g\2\2\u05a8\u0118\3\2\2\2\u05a9\u05aa\7{\2\2\u05aa\u05ab\7k\2\2\u05ab"+
		"\u05ac\7g\2\2\u05ac\u05ad\7n\2\2\u05ad\u05ae\7f\2\2\u05ae\u011a\3\2\2"+
		"\2\u05af\u05b0\7a\2\2\u05b0\u05b1\7a\2\2\u05b1\u05b2\7i\2\2\u05b2\u05b3"+
		"\7g\2\2\u05b3\u05b4\7v\2\2\u05b4\u011c\3\2\2\2\u05b5\u05b6\7a\2\2\u05b6"+
		"\u05b7\7a\2\2\u05b7\u05b8\7u\2\2\u05b8\u05b9\7g\2\2\u05b9\u05ba\7v\2\2"+
		"\u05ba\u011e\3\2\2\2\u05bb\u05bc\7a\2\2\u05bc\u05bd\7a\2\2\u05bd\u05be"+
		"\7e\2\2\u05be\u05bf\7c\2\2\u05bf\u05c0\7n\2\2\u05c0\u05c1\7n\2\2\u05c1"+
		"\u0120\3\2\2\2\u05c2\u05c3\7a\2\2\u05c3\u05c4\7a\2\2\u05c4\u05c5\7e\2"+
		"\2\u05c5\u05c6\7c\2\2\u05c6\u05c7\7n\2\2\u05c7\u05c8\7n\2\2\u05c8\u05c9"+
		"\7u\2\2\u05c9\u05ca\7v\2\2\u05ca\u05cb\7c\2\2\u05cb\u05cc\7v\2\2\u05cc"+
		"\u05cd\7k\2\2\u05cd\u05ce\7e\2\2\u05ce\u0122\3\2\2\2\u05cf\u05d0\7a\2"+
		"\2\u05d0\u05d1\7a\2\2\u05d1\u05d2\7e\2\2\u05d2\u05d3\7q\2\2\u05d3\u05d4"+
		"\7p\2\2\u05d4\u05d5\7u\2\2\u05d5\u05d6\7v\2\2\u05d6\u05d7\7t\2\2\u05d7"+
		"\u05d8\7w\2\2\u05d8\u05d9\7e\2\2\u05d9\u05da\7v\2\2\u05da\u0124\3\2\2"+
		"\2\u05db\u05dc\7a\2\2\u05dc\u05dd\7a\2\2\u05dd\u05de\7f\2\2\u05de\u05df"+
		"\7g\2\2\u05df\u05e0\7u\2\2\u05e0\u05e1\7v\2\2\u05e1\u05e2\7t\2\2\u05e2"+
		"\u05e3\7w\2\2\u05e3\u05e4\7e\2\2\u05e4\u05e5\7v\2\2\u05e5\u0126\3\2\2"+
		"\2\u05e6\u05e7\7a\2\2\u05e7\u05e8\7a\2\2\u05e8\u05e9\7y\2\2\u05e9\u05ea"+
		"\7c\2\2\u05ea\u05eb\7m\2\2\u05eb\u05ec\7g\2\2\u05ec\u05ed\7w\2\2\u05ed"+
		"\u05ee\7r\2\2\u05ee\u0128\3\2\2\2\u05ef\u05f0\7a\2\2\u05f0\u05f1\7a\2"+
		"\2\u05f1\u05f2\7u\2\2\u05f2\u05f3\7n\2\2\u05f3\u05f4\7g\2\2\u05f4\u05f5"+
		"\7g\2\2\u05f5\u05f6\7r\2\2\u05f6\u012a\3\2\2\2\u05f7\u05f8\7a\2\2\u05f8"+
		"\u05f9\7a\2\2\u05f9\u05fa\7c\2\2\u05fa\u05fb\7w\2\2\u05fb\u05fc\7v\2\2"+
		"\u05fc\u05fd\7q\2\2\u05fd\u05fe\7n\2\2\u05fe\u05ff\7q\2\2\u05ff\u0600"+
		"\7c\2\2\u0600\u0601\7f\2\2\u0601\u012c\3\2\2\2\u0602\u0603\7a\2\2\u0603"+
		"\u0604\7a\2\2\u0604\u0605\7k\2\2\u0605\u0606\7u\2\2\u0606\u0607\7u\2\2"+
		"\u0607\u0608\7g\2\2\u0608\u0609\7v\2\2\u0609\u012e\3\2\2\2\u060a\u060b"+
		"\7a\2\2\u060b\u060c\7a\2\2\u060c\u060d\7w\2\2\u060d\u060e\7p\2\2\u060e"+
		"\u060f\7u\2\2\u060f\u0610\7g\2\2\u0610\u0611\7v\2\2\u0611\u0130\3\2\2"+
		"\2\u0612\u0613\7a\2\2\u0613\u0614\7a\2\2\u0614\u0615\7v\2\2\u0615\u0616"+
		"\7q\2\2\u0616\u0617\7u\2\2\u0617\u0618\7v\2\2\u0618\u0619\7t\2\2\u0619"+
		"\u061a\7k\2\2\u061a\u061b\7p\2\2\u061b\u061c\7i\2\2\u061c\u0132\3\2\2"+
		"\2\u061d\u061e\7a\2\2\u061e\u061f\7a\2\2\u061f\u0620\7k\2\2\u0620\u0621"+
		"\7p\2\2\u0621\u0622\7x\2\2\u0622\u0623\7q\2\2\u0623\u0624\7m\2\2\u0624"+
		"\u0625\7g\2\2\u0625\u0134\3\2\2\2\u0626\u0627\7a\2\2\u0627\u0628\7a\2"+
		"\2\u0628\u0629\7u\2\2\u0629\u062a\7g\2\2\u062a\u062b\7v\2\2\u062b\u062c"+
		"\7a\2\2\u062c\u062d\7u\2\2\u062d\u062e\7v\2\2\u062e\u062f\7c\2\2\u062f"+
		"\u0630\7v\2\2\u0630\u0631\7g\2\2\u0631\u0136\3\2\2\2\u0632\u0633\7a\2"+
		"\2\u0633\u0634\7a\2\2\u0634\u0635\7e\2\2\u0635\u0636\7n\2\2\u0636\u0637"+
		"\7q\2\2\u0637\u0638\7p\2\2\u0638\u0639\7g\2\2\u0639\u0138\3\2\2\2\u063a"+
		"\u063b\7a\2\2\u063b\u063c\7a\2\2\u063c\u063d\7f\2\2\u063d\u063e\7g\2\2"+
		"\u063e\u063f\7d\2\2\u063f\u0640\7w\2\2\u0640\u0641\7i\2\2\u0641\u0642"+
		"\7k\2\2\u0642\u0643\7p\2\2\u0643\u0644\7h\2\2\u0644\u0645\7q\2\2\u0645"+
		"\u013a\3\2\2\2\u0646\u0647\7a\2\2\u0647\u0648\7a\2\2\u0648\u0649\7p\2"+
		"\2\u0649\u064a\7c\2\2\u064a\u064b\7o\2\2\u064b\u064c\7g\2\2\u064c\u064d"+
		"\7u\2\2\u064d\u064e\7r\2\2\u064e\u064f\7c\2\2\u064f\u0650\7e\2\2\u0650"+
		"\u0651\7g\2\2\u0651\u0652\7a\2\2\u0652\u0653\7a\2\2\u0653\u013c\3\2\2"+
		"\2\u0654\u0655\7a\2\2\u0655\u0656\7a\2\2\u0656\u0657\7e\2\2\u0657\u0658"+
		"\7n\2\2\u0658\u0659\7c\2\2\u0659\u065a\7u\2\2\u065a\u065b\7u\2\2\u065b"+
		"\u065c\7a\2\2\u065c\u065d\7a\2\2\u065d\u013e\3\2\2\2\u065e\u065f\7a\2"+
		"\2\u065f\u0660\7a\2\2\u0660\u0661\7v\2\2\u0661\u0662\7t\2\2\u0662\u0663"+
		"\7c\2\2\u0663\u0664\7k\2\2\u0664\u0665\7v\2\2\u0665\u0666\7a\2\2\u0666"+
		"\u0667\7a\2\2\u0667\u0140\3\2\2\2\u0668\u0669\7a\2\2\u0669\u066a\7a\2"+
		"\2\u066a\u066b\7h\2\2\u066b\u066c\7w\2\2\u066c\u066d\7p\2\2\u066d\u066e"+
		"\7e\2\2\u066e\u066f\7v\2\2\u066f\u0670\7k\2\2\u0670\u0671\7q\2\2\u0671"+
		"\u0672\7p\2\2\u0672\u0673\7a\2\2\u0673\u0674\7a\2\2\u0674\u0142\3\2\2"+
		"\2\u0675\u0676\7a\2\2\u0676\u0677\7a\2\2\u0677\u0678\7o\2\2\u0678\u0679"+
		"\7g\2\2\u0679\u067a\7v\2\2\u067a\u067b\7j\2\2\u067b\u067c\7q\2\2\u067c"+
		"\u067d\7f\2\2\u067d\u067e\7a\2\2\u067e\u067f\7a\2\2\u067f\u0144\3\2\2"+
		"\2\u0680\u0681\7a\2\2\u0681\u0682\7a\2\2\u0682\u0683\7n\2\2\u0683\u0684"+
		"\7k\2\2\u0684\u0685\7p\2\2\u0685\u0686\7g\2\2\u0686\u0687\7a\2\2\u0687"+
		"\u0688\7a\2\2\u0688\u0146\3\2\2\2\u0689\u068a\7a\2\2\u068a\u068b\7a\2"+
		"\2\u068b\u068c\7h\2\2\u068c\u068d\7k\2\2\u068d\u068e\7n\2\2\u068e\u068f"+
		"\7g\2\2\u068f\u0690\7a\2\2\u0690\u0691\7a\2\2\u0691\u0148\3\2\2\2\u0692"+
		"\u0693\7a\2\2\u0693\u0694\7a\2\2\u0694\u0695\7f\2\2\u0695\u0696\7k\2\2"+
		"\u0696\u0697\7t\2\2\u0697\u0698\7a\2\2\u0698\u0699\7a\2\2\u0699\u014a"+
		"\3\2\2\2\u069a\u069b\7>\2\2\u069b\u069c\7<\2\2\u069c\u014c\3\2\2\2\u069d"+
		"\u069e\7<\2\2\u069e\u069f\7@\2\2\u069f\u014e\3\2\2\2\u06a0\u06a1\7?\2"+
		"\2\u06a1\u06a2\7@\2\2\u06a2\u0150\3\2\2\2\u06a3\u06a4\7-\2\2\u06a4\u06a5"+
		"\7-\2\2\u06a5\u0152\3\2\2\2\u06a6\u06a7\7/\2\2\u06a7\u06a8\7/\2\2\u06a8"+
		"\u0154\3\2\2\2\u06a9\u06aa\7?\2\2\u06aa\u06ab\7?\2\2\u06ab\u06ac\7?\2"+
		"\2\u06ac\u0156\3\2\2\2\u06ad\u06ae\7#\2\2\u06ae\u06af\7?\2\2\u06af\u06b0"+
		"\7?\2\2\u06b0\u0158\3\2\2\2\u06b1\u06b2\7?\2\2\u06b2\u06b3\7?\2\2\u06b3"+
		"\u015a\3\2\2\2\u06b4\u06b5\7>\2\2\u06b5\u06b9\7@\2\2\u06b6\u06b7\7#\2"+
		"\2\u06b7\u06b9\7?\2\2\u06b8\u06b4\3\2\2\2\u06b8\u06b6\3\2\2\2\u06b9\u015c"+
		"\3\2\2\2\u06ba\u06bb\7>\2\2\u06bb\u06bc\7?\2\2\u06bc\u015e\3\2\2\2\u06bd"+
		"\u06be\7@\2\2\u06be\u06bf\7?\2\2\u06bf\u0160\3\2\2\2\u06c0\u06c1\7-\2"+
		"\2\u06c1\u06c2\7?\2\2\u06c2\u0162\3\2\2\2\u06c3\u06c4\7/\2\2\u06c4\u06c5"+
		"\7?\2\2\u06c5\u0164\3\2\2\2\u06c6\u06c7\7,\2\2\u06c7\u06c8\7?\2\2\u06c8"+
		"\u0166\3\2\2\2\u06c9\u06ca\7,\2\2\u06ca\u06cb\7,\2\2\u06cb\u0168\3\2\2"+
		"\2\u06cc\u06cd\7,\2\2\u06cd\u06ce\7,\2\2\u06ce\u06cf\7?\2\2\u06cf\u016a"+
		"\3\2\2\2\u06d0\u06d1\7\61\2\2\u06d1\u06d2\7?\2\2\u06d2\u016c\3\2\2\2\u06d3"+
		"\u06d4\7\60\2\2\u06d4\u06d5\7?\2\2\u06d5\u016e\3\2\2\2\u06d6\u06d7\7\'"+
		"\2\2\u06d7\u06d8\7?\2\2\u06d8\u0170\3\2\2\2\u06d9\u06da\7>\2\2\u06da\u06db"+
		"\7>\2\2\u06db\u06dc\7?\2\2\u06dc\u0172\3\2\2\2\u06dd\u06de\7@\2\2\u06de"+
		"\u06df\7@\2\2\u06df\u06e0\7?\2\2\u06e0\u0174\3\2\2\2\u06e1\u06e2\7(\2"+
		"\2\u06e2\u06e3\7?\2\2\u06e3\u0176\3\2\2\2\u06e4\u06e5\7~\2\2\u06e5\u06e6"+
		"\7?\2\2\u06e6\u0178\3\2\2\2\u06e7\u06e8\7`\2\2\u06e8\u06e9\7?\2\2\u06e9"+
		"\u017a\3\2\2\2\u06ea\u06eb\7~\2\2\u06eb\u06ec\7~\2\2\u06ec\u017c\3\2\2"+
		"\2\u06ed\u06ee\7(\2\2\u06ee\u06ef\7(\2\2\u06ef\u017e\3\2\2\2\u06f0\u06f1"+
		"\7>\2\2\u06f1\u06f2\7>\2\2\u06f2\u0180\3\2\2\2\u06f3\u06f4\7@\2\2\u06f4"+
		"\u06f5\7@\2\2\u06f5\u0182\3\2\2\2\u06f6\u06f7\7<\2\2\u06f7\u06f8\7<\2"+
		"\2\u06f8\u0184\3\2\2\2\u06f9\u06fa\7/\2\2\u06fa\u06fb\7@\2\2\u06fb\u0186"+
		"\3\2\2\2\u06fc\u06fd\7^\2\2\u06fd\u0188\3\2\2\2\u06fe\u06ff\7\60\2\2\u06ff"+
		"\u0700\7\60\2\2\u0700\u0701\7\60\2\2\u0701\u018a\3\2\2\2\u0702\u0703\7"+
		">\2\2\u0703\u018c\3\2\2\2\u0704\u0705\7@\2\2\u0705\u018e\3\2\2\2\u0706"+
		"\u0707\7(\2\2\u0707\u0190\3\2\2\2\u0708\u0709\7~\2\2\u0709\u0192\3\2\2"+
		"\2\u070a\u070b\7#\2\2\u070b\u0194\3\2\2\2\u070c\u070d\7`\2\2\u070d\u0196"+
		"\3\2\2\2\u070e\u070f\7-\2\2\u070f\u0198\3\2\2\2\u0710\u0711\7/\2\2\u0711"+
		"\u019a\3\2\2\2\u0712\u0713\7,\2\2\u0713\u019c\3\2\2\2\u0714\u0715\7\'"+
		"\2\2\u0715\u019e\3\2\2\2\u0716\u0717\7\61\2\2\u0717\u01a0\3\2\2\2\u0718"+
		"\u0719\7\u0080\2\2\u0719\u01a2\3\2\2\2\u071a\u071b\7B\2\2\u071b\u01a4"+
		"\3\2\2\2\u071c\u071d\7&\2\2\u071d\u01a6\3\2\2\2\u071e\u071f\7\60\2\2\u071f"+
		"\u01a8\3\2\2\2\u0720\u0721\7A\2\2\u0721\u01aa\3\2\2\2\u0722\u0723\7*\2"+
		"\2\u0723\u01ac\3\2\2\2\u0724\u0725\7+\2\2\u0725\u01ae\3\2\2\2\u0726\u0727"+
		"\7]\2\2\u0727\u01b0\3\2\2\2\u0728\u0729\7_\2\2\u0729\u01b2\3\2\2\2\u072a"+
		"\u072b\7}\2\2\u072b\u01b4\3\2\2\2\u072c\u072d\7\177\2\2\u072d\u072e\b"+
		"\u00d6\24\2\u072e\u01b6\3\2\2\2\u072f\u0730\7.\2\2\u0730\u01b8\3\2\2\2"+
		"\u0731\u0732\7<\2\2\u0732\u01ba\3\2\2\2\u0733\u0734\7=\2\2\u0734\u01bc"+
		"\3\2\2\2\u0735\u0736\7?\2\2\u0736\u01be\3\2\2\2\u0737\u0738\7)\2\2\u0738"+
		"\u01c0\3\2\2\2\u0739\u073a\7b\2\2\u073a\u01c2\3\2\2\2\u073b\u073c\7&\2"+
		"\2\u073c\u0740\t\t\2\2\u073d\u073f\t\n\2\2\u073e\u073d\3\2\2\2\u073f\u0742"+
		"\3\2\2\2\u0740\u073e\3\2\2\2\u0740\u0741\3\2\2\2\u0741\u01c4\3\2\2\2\u0742"+
		"\u0740\3\2\2\2\u0743\u0747\t\t\2\2\u0744\u0746\t\n\2\2\u0745\u0744\3\2"+
		"\2\2\u0746\u0749\3\2\2\2\u0747\u0745\3\2\2\2\u0747\u0748\3\2\2\2\u0748"+
		"\u01c6\3\2\2\2\u0749\u0747\3\2\2\2\u074a\u074c\7\62\2\2\u074b\u074d\t"+
		"\13\2\2\u074c\u074b\3\2\2\2\u074d\u074e\3\2\2\2\u074e\u074c\3\2\2\2\u074e"+
		"\u074f\3\2\2\2\u074f\u01c8\3\2\2\2\u0750\u0752\5\u01ff\u00fb\2\u0751\u0750"+
		"\3\2\2\2\u0752\u0753\3\2\2\2\u0753\u0751\3\2\2\2\u0753\u0754\3\2\2\2\u0754"+
		"\u01ca\3\2\2\2\u0755\u0757\5\u01ff\u00fb\2\u0756\u0755\3\2\2\2\u0757\u0758"+
		"\3\2\2\2\u0758\u0756\3\2\2\2\u0758\u0759\3\2\2\2\u0759\u075a\3\2\2\2\u075a"+
		"\u075e\7\60\2\2\u075b\u075d\5\u01ff\u00fb\2\u075c\u075b\3\2\2\2\u075d"+
		"\u0760\3\2\2\2\u075e\u075c\3\2\2\2\u075e\u075f\3\2\2\2\u075f\u0768\3\2"+
		"\2\2\u0760\u075e\3\2\2\2\u0761\u0763\7\60\2\2\u0762\u0764\5\u01ff\u00fb"+
		"\2\u0763\u0762\3\2\2\2\u0764\u0765\3\2\2\2\u0765\u0763\3\2\2\2\u0765\u0766"+
		"\3\2\2\2\u0766\u0768\3\2\2\2\u0767\u0756\3\2\2\2\u0767\u0761\3\2\2\2\u0768"+
		"\u076a\3\2\2\2\u0769\u076b\5\u01fd\u00fa\2\u076a\u0769\3\2\2\2\u076a\u076b"+
		"\3\2\2\2\u076b\u0774\3\2\2\2\u076c\u076e\5\u01ff\u00fb\2\u076d\u076c\3"+
		"\2\2\2\u076e\u076f\3\2\2\2\u076f\u076d\3\2\2\2\u076f\u0770\3\2\2\2\u0770"+
		"\u0771\3\2\2\2\u0771\u0772\5\u01fd\u00fa\2\u0772\u0774\3\2\2\2\u0773\u0767"+
		"\3\2\2\2\u0773\u076d\3\2\2\2\u0774\u01cc\3\2\2\2\u0775\u0776\7\62\2\2"+
		"\u0776\u0777\7z\2\2\u0777\u0779\3\2\2\2\u0778\u077a\5\u0201\u00fc\2\u0779"+
		"\u0778\3\2\2\2\u077a\u077b\3\2\2\2\u077b\u0779\3\2\2\2\u077b\u077c\3\2"+
		"\2\2\u077c\u01ce\3\2\2\2\u077d\u077e\7\62\2\2\u077e\u077f\7d\2\2\u077f"+
		"\u0781\3\2\2\2\u0780\u0782\t\f\2\2\u0781\u0780\3\2\2\2\u0782\u0783\3\2"+
		"\2\2\u0783\u0781\3\2\2\2\u0783\u0784\3\2\2\2\u0784\u01d0\3\2\2\2\u0785"+
		"\u0789\7b\2\2\u0786\u0788\n\r\2\2\u0787\u0786\3\2\2\2\u0788\u078b\3\2"+
		"\2\2\u0789\u0787\3\2\2\2\u0789\u078a\3\2\2\2\u078a\u078c\3\2\2\2\u078b"+
		"\u0789\3\2\2\2\u078c\u078d\7b\2\2\u078d\u01d2\3\2\2\2\u078e\u0794\7)\2"+
		"\2\u078f\u0793\n\16\2\2\u0790\u0791\7^\2\2\u0791\u0793\13\2\2\2\u0792"+
		"\u078f\3\2\2\2\u0792\u0790\3\2\2\2\u0793\u0796\3\2\2\2\u0794\u0792\3\2"+
		"\2\2\u0794\u0795\3\2\2\2\u0795\u0797\3\2\2\2\u0796\u0794\3\2\2\2\u0797"+
		"\u0798\7)\2\2\u0798\u01d4\3\2\2\2\u0799\u079a\7$\2\2\u079a\u079b\3\2\2"+
		"\2\u079b\u079c\b\u00e6\25\2\u079c\u01d6\3\2\2\2\u079d\u079e\7>\2\2\u079e"+
		"\u079f\7>\2\2\u079f\u07a0\7>\2\2\u07a0\u07a4\3\2\2\2\u07a1\u07a3\t\17"+
		"\2\2\u07a2\u07a1\3\2\2\2\u07a3\u07a6\3\2\2\2\u07a4\u07a2\3\2\2\2\u07a4"+
		"\u07a5\3\2\2\2\u07a5\u07a7\3\2\2\2\u07a6\u07a4\3\2\2\2\u07a7\u07a8\7)"+
		"\2\2\u07a8\u07ac\t\t\2\2\u07a9\u07ab\t\n\2\2\u07aa\u07a9\3\2\2\2\u07ab"+
		"\u07ae\3\2\2\2\u07ac\u07aa\3\2\2\2\u07ac\u07ad\3\2\2\2\u07ad\u07af\3\2"+
		"\2\2\u07ae\u07ac\3\2\2\2\u07af\u07b0\7)\2\2\u07b0\u07b1\6\u00e7\5\2\u07b1"+
		"\u07b2\3\2\2\2\u07b2\u07b3\b\u00e7\26\2\u07b3\u01d8\3\2\2\2\u07b4\u07b5"+
		"\7>\2\2\u07b5\u07b6\7>\2\2\u07b6\u07b7\7>\2\2\u07b7\u07bb\3\2\2\2\u07b8"+
		"\u07ba\t\17\2\2\u07b9\u07b8\3\2\2\2\u07ba\u07bd\3\2\2\2\u07bb\u07b9\3"+
		"\2\2\2\u07bb\u07bc\3\2\2\2\u07bc\u07be\3\2\2\2\u07bd\u07bb\3\2\2\2\u07be"+
		"\u07c2\t\t\2\2\u07bf\u07c1\t\n\2\2\u07c0\u07bf\3\2\2\2\u07c1\u07c4\3\2"+
		"\2\2\u07c2\u07c0\3\2\2\2\u07c2\u07c3\3\2\2\2\u07c3\u07c5\3\2\2\2\u07c4"+
		"\u07c2\3\2\2\2\u07c5\u07c6\6\u00e8\6\2\u07c6\u07c7\3\2\2\2\u07c7\u07c8"+
		"\b\u00e8\26\2\u07c8\u01da\3\2\2\2\u07c9\u07ca\13\2\2\2\u07ca\u07cb\3\2"+
		"\2\2\u07cb\u07cc\b\u00e9\13\2\u07cc\u01dc\3\2\2\2\u07cd\u07ce\7&\2\2\u07ce"+
		"\u07d2\t\t\2\2\u07cf\u07d1\t\n\2\2\u07d0\u07cf\3\2\2\2\u07d1\u07d4\3\2"+
		"\2\2\u07d2\u07d0\3\2\2\2\u07d2\u07d3\3\2\2\2\u07d3\u07d5\3\2\2\2\u07d4"+
		"\u07d2\3\2\2\2\u07d5\u07d6\b\u00ea\27\2\u07d6\u01de\3\2\2\2\u07d7\u07d8"+
		"\7&\2\2\u07d8\u07d9\3\2\2\2\u07d9\u07da\b\u00eb\30\2\u07da\u01e0\3\2\2"+
		"\2\u07db\u07dc\7}\2\2\u07dc\u07dd\6\u00ec\7\2\u07dd\u07de\b\u00ec\31\2"+
		"\u07de\u07df\3\2\2\2\u07df\u07e0\b\u00ec\6\2\u07e0\u07e1\b\u00ec\5\2\u07e1"+
		"\u01e2\3\2\2\2\u07e2\u07e3\7}\2\2\u07e3\u07e4\3\2\2\2\u07e4\u07e5\b\u00ed"+
		"\30\2\u07e5\u01e4\3\2\2\2\u07e6\u07e7\7^\2\2\u07e7\u07e8\13\2\2\2\u07e8"+
		"\u07e9\3\2\2\2\u07e9\u07ea\b\u00ee\30\2\u07ea\u01e6\3\2\2\2\u07eb\u07ec"+
		"\7$\2\2\u07ec\u07ed\3\2\2\2\u07ed\u07ee\b\u00ef\32\2\u07ee\u07ef\b\u00ef"+
		"\f\2\u07ef\u01e8\3\2\2\2\u07f0\u07f2\n\20\2\2\u07f1\u07f0\3\2\2\2\u07f2"+
		"\u07f3\3\2\2\2\u07f3\u07f1\3\2\2\2\u07f3\u07f4\3\2\2\2\u07f4\u01ea\3\2"+
		"\2\2\u07f5\u07f7\n\21\2\2\u07f6\u07f5\3\2\2\2\u07f7\u07f8\3\2\2\2\u07f8"+
		"\u07f6\3\2\2\2\u07f8\u07f9\3\2\2\2\u07f9\u07fa\3\2\2\2\u07fa\u07fb\b\u00f1"+
		"\22\2\u07fb\u01ec\3\2\2\2\u07fc\u07fd\7A\2\2\u07fd\u07fe\7@\2\2\u07fe"+
		"\u01ee\3\2\2\2\u07ff\u0800\7A\2\2\u0800\u0801\3\2\2\2\u0801\u0802\b\u00f3"+
		"\33\2\u0802\u0803\b\u00f3\22\2\u0803\u01f0\3\2\2\2\u0804\u0805\t\4\2\2"+
		"\u0805\u0806\3\2\2\2\u0806\u0807\b\u00f4\6\2\u0807\u0808\b\u00f4\f\2\u0808"+
		"\u01f2\3\2\2\2\u0809\u080b\n\4\2\2\u080a\u0809\3\2\2\2\u080b\u080e\3\2"+
		"\2\2\u080c\u080d\3\2\2\2\u080c\u080a\3\2\2\2\u080d\u0814\3\2\2\2\u080e"+
		"\u080c\3\2\2\2\u080f\u0811\7\17\2\2\u0810\u080f\3\2\2\2\u0810\u0811\3"+
		"\2\2\2\u0811\u0812\3\2\2\2\u0812\u0815\7\f\2\2\u0813\u0815\7\17\2\2\u0814"+
		"\u0810\3\2\2\2\u0814\u0813\3\2\2\2\u0815\u01f4\3\2\2\2\u0816\u081c\7>"+
		"\2\2\u0817\u0818\7A\2\2\u0818\u081d\7?\2\2\u0819\u081a\6\u00f6\b\2\u081a"+
		"\u081b\7\'\2\2\u081b\u081d\7?\2\2\u081c\u0817\3\2\2\2\u081c\u0819\3\2"+
		"\2\2\u081d\u01f6\3\2\2\2\u081e\u0827\7>\2\2\u081f\u0823\7A\2\2\u0820\u0821"+
		"\7r\2\2\u0821\u0822\7j\2\2\u0822\u0824\7r\2\2\u0823\u0820\3\2\2\2\u0823"+
		"\u0824\3\2\2\2\u0824\u0828\3\2\2\2\u0825\u0826\6\u00f7\t\2\u0826\u0828"+
		"\7\'\2\2\u0827\u081f\3\2\2\2\u0827\u0825\3\2\2\2\u0828\u01f8\3\2\2\2\u0829"+
		"\u082e\5\u01fb\u00f9\2\u082a\u082e\t\22\2\2\u082b\u082e\5\u01ff\u00fb"+
		"\2\u082c\u082e\t\23\2\2\u082d\u0829\3\2\2\2\u082d\u082a\3\2\2\2\u082d"+
		"\u082b\3\2\2\2\u082d\u082c\3\2\2\2\u082e\u01fa\3\2\2\2\u082f\u0831\t\24"+
		"\2\2\u0830\u082f\3\2\2\2\u0831\u01fc\3\2\2\2\u0832\u0834\7g\2\2\u0833"+
		"\u0835\t\25\2\2\u0834\u0833\3\2\2\2\u0834\u0835\3\2\2\2\u0835\u0837\3"+
		"\2\2\2\u0836\u0838\5\u01ff\u00fb\2\u0837\u0836\3\2\2\2\u0838\u0839\3\2"+
		"\2\2\u0839\u0837\3\2\2\2\u0839\u083a\3\2\2\2\u083a\u01fe\3\2\2\2\u083b"+
		"\u083c\t\26\2\2\u083c\u0200\3\2\2\2\u083d\u083e\t\27\2\2\u083e\u0202\3"+
		"\2\2\2L\2\3\4\5\6\7\b\t\n\13\f\u0206\u020d\u0240\u024e\u025d\u0264\u0270"+
		"\u0292\u0299\u02a3\u02a8\u02ad\u02b5\u02c8\u02cf\u02e1\u02e8\u02f1\u02fb"+
		"\u0312\u031d\u0326\u0334\u0339\u0343\u0377\u0382\u04c3\u058b\u06b8\u0740"+
		"\u0747\u074e\u0753\u0758\u075e\u0765\u0767\u076a\u076f\u0773\u077b\u0783"+
		"\u0789\u0792\u0794\u07a4\u07ac\u07bb\u07c2\u07d2\u07f3\u07f8\u080c\u0810"+
		"\u0814\u081c\u0823\u0827\u082d\u0830\u0834\u0839\34\2\3\2\7\3\2\t@\2\7"+
		"\t\2\2\6\2\3\7\2\7\4\2\3\b\3\5\2\2\2\5\2\6\2\2\t\16\2\3\24\4\7\5\2\7\6"+
		"\2\t$\2\2\4\2\7\13\2\3\u00d6\5\7\n\2\7\f\2\t\u00d6\2\t\u00e4\2\3\u00ec"+
		"\6\t\u00df\2\t\u00e5\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}