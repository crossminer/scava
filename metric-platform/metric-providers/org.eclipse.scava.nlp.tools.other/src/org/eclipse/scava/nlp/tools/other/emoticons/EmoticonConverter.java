/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.other.emoticons;

import java.util.regex.Pattern;

public class EmoticonConverter
{
	private static Pattern happy;
	private static Pattern happyEmoticon;
	private static Pattern sad;
	private static Pattern sadEmoticon;
	private static Pattern laugh;
	private static Pattern laughEmoticon;
	private static Pattern cry;
	private static Pattern cryEmoticon;
	private static Pattern cryJoy;
	private static Pattern wink;
	private static Pattern shock;
	private static Pattern cheeky;
	private static Pattern cheekyWink;
	private static Pattern indecision;
	private static Pattern indecisionEmoticon;
	private static Pattern skeptikal;
	private static Pattern heart;
	private static Pattern heartEmoticon;
	private static Pattern brokenHeart;
	private static Pattern angry;
	private static Pattern kiss;
	private static Pattern kissEmoticon;
	private static Pattern embarrassing;
	private static Pattern embarrassingEmoticon;
	private static Pattern horror;
	private static Pattern horrorEmoticon;
	private static Pattern separateEmoticons;
	private static Pattern spacing;
	
	static {
		happy=Pattern.compile("(?<!\\w)(:\\)|:-\\)|=\\)|\\^_\\^|:\\]|=\\])(?!\\w)");	//:) :-) =) ^_^ :] =]
		happyEmoticon=Pattern.compile("(😊|☺|😅)");
		sad=Pattern.compile("(?<!\\w)(:\\(|:-\\(|=\\()(?!\\w)");				// :( :-( =(
		sadEmoticon=Pattern.compile("(🙁|😞)");
		laugh=Pattern.compile("(?<!\\w)(:D|8D|xD|XD|=D|=3)(?!\\w)");	// :D 8D xD XD =D =3
		laughEmoticon=Pattern.compile("(😆|😀)");
		cry=Pattern.compile("(?<!\\w)(:'\\(|:'-\\()(?!\\w)");			// :'( :'-(
		cryEmoticon=Pattern.compile("😥");
		cryJoy=Pattern.compile("(?<!\\w):'\\)(?!\\w)");			// :')
		wink=Pattern.compile("(?<!\\w)(;-\\)|;\\)|;D|;-\\]|;\\])(?!\\w)");	// ;-) ;) ;D ;-] ;]
		shock=Pattern.compile("(?<!\\w)(:-O|:O|:o|:-o|:-0|O_O|O_o|o_O)(?!\\w)");
		cheeky=Pattern.compile("(?<!\\w)(:P|:-P|:p|:-p|:b|:-b|d:|=p|=P)(?!\\w)");
		cheekyWink=Pattern.compile("(?<!\\w)(;P|;-P|;p|;-p|;b|;-b)(?!\\w)");
		indecision=Pattern.compile("(?<!\\w)(:\\||:-\\||-_-)(?!\\w)");
		indecisionEmoticon=Pattern.compile("😑");
		skeptikal=Pattern.compile("(?<!\\w)(:S|:/|:-/|:\\\\|=/|=\\\\|>\\.<)(?!\\w)");	//:S :/ :-/ :\ =/ =\ >.<
		heart=Pattern.compile("(?<!\\w)(<3)(?!\\w)");
		heartEmoticon=Pattern.compile("(💖|💘|💙|💗|💛|💓|💜|💚|♥|😍|💕)");
		brokenHeart=Pattern.compile("(?<!\\w)(</3|<\\\\3)(?!\\w)"); // </3 <\3
		angry=Pattern.compile("(?<!\\w)(:@|>:\\(|>:\\[|:\\{)(?!\\w)");
		kiss=Pattern.compile("(?<!\\w)(:\\*|:-\\*)(?!\\w)");
		kissEmoticon=Pattern.compile("(😙|😘|😚)");
		embarrassing=Pattern.compile("(?<!\\w)(:\\$)(?!\\w)");
		embarrassingEmoticon=Pattern.compile("😖");
		horror=Pattern.compile("(?<!\\w)(D-':|D:|D:<|D=|DX|D8)(?!\\w)");
		horrorEmoticon=Pattern.compile("(😨|😧)");
		separateEmoticons=Pattern.compile("(🙂|☹️|😃|😭|😂|😉|😮|😛|😜|😐|😕|😡|😳|😱|❤|💔|😗|👍|👎|💩|😋|🙄|🙃)");
		spacing=Pattern.compile(" +");
	}
	
	public static String transform(String text) {
		text=happy.matcher(text).replaceAll("🙂");
		text=happyEmoticon.matcher(text).replaceAll("🙂");
		text=sad.matcher(text).replaceAll("☹️");
		text=sadEmoticon.matcher(text).replaceAll("☹️");
		text=laugh.matcher(text).replaceAll("😃");
		text=laughEmoticon.matcher(text).replaceAll("😃");
		text=cry.matcher(text).replaceAll("😭");
		text=cryEmoticon.matcher(text).replaceAll("😭");
		text=cryJoy.matcher(text).replaceAll("😂");
		text=wink.matcher(text).replaceAll("😉");
		text=shock.matcher(text).replaceAll("😮");
		text=cheeky.matcher(text).replaceAll("😛");
		text=cheekyWink.matcher(text).replaceAll("😜");
		text=indecision.matcher(text).replaceAll("😐");
		text=indecisionEmoticon.matcher(text).replaceAll("😐");
		text=skeptikal.matcher(text).replaceAll("😕");
		text=heart.matcher(text).replaceAll("❤");
		text=heartEmoticon.matcher(text).replaceAll("❤");
		text=brokenHeart.matcher(text).replaceAll("💔");
		text=angry.matcher(text).replaceAll("😡");
		text=kiss.matcher(text).replaceAll("😗");
		text=kissEmoticon.matcher(text).replaceAll("😗");
		text=embarrassing.matcher(text).replaceAll("😳");
		text=embarrassingEmoticon.matcher(text).replaceAll("😳");
		text=horror.matcher(text).replaceAll("😱");
		text=horrorEmoticon.matcher(text).replaceAll("😱");
		text=separateEmoticons.matcher(text).replaceAll(" $1 "); //It is not the normal space but the insecable one ALT + 0160
		text=spacing.matcher(text).replaceAll(" ");
		return text;
	}
	
}
