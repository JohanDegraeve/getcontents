/*  
 *  Copyright (C) 2010  Johan Degraeve
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/gpl.txt>.
 *    
 *  Please contact Johan Degraeve at johan.degraeve@johandegraeve.net if you need
 *  additional information or have any questions.
 */
package net.johandegraeve.getcontents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.Ostermiller.util.StringHelper;

import net.johandegraeve.easyxmldata.DefaultXMLElement;
import net.johandegraeve.easyxmldata.EasyXMLDataParser;
import net.johandegraeve.easyxmldata.XMLElement;

/*
 * extending stringhelper because I'm defining an own version of replace, namely a case insensitive one
 * @version 1.0
 * @author Johan Degraeve
 *
 */
class Utilities  {
    
	private static HashMap<String,Integer> htmlEntities = new HashMap<String,Integer>();
	static {
		htmlEntities.put("n"+"b"+"s"+"p", new Integer(160));
		htmlEntities.put("i"+"e"+"x"+"c"+"l", new Integer(161));
		htmlEntities.put("cent", new Integer(162));
		htmlEntities.put("pound", new Integer(163));
		htmlEntities.put("c"+"u"+"r"+"r"+"e"+"n", new Integer(164));
		htmlEntities.put("y"+"e"+"n", new Integer(165));
		htmlEntities.put("b"+"r"+"v"+"b"+"a"+"r", new Integer(166));
		htmlEntities.put("sect", new Integer(167));
		htmlEntities.put("u"+"m"+"l", new Integer(168));
		htmlEntities.put("copy", new Integer(169));
		htmlEntities.put("o"+"r"+"d"+"f", new Integer(170));
		htmlEntities.put("l"+"a"+"quo", new Integer(171));
		htmlEntities.put("not", new Integer(172));
		htmlEntities.put("shy", new Integer(173));
		htmlEntities.put("r"+"e"+"g", new Integer(174));
		htmlEntities.put("m"+"a"+"c"+"r", new Integer(175));
		htmlEntities.put("d"+"e"+"g", new Integer(176));
		htmlEntities.put("plus"+"m"+"n", new Integer(177));
		htmlEntities.put("sup2", new Integer(178));
		htmlEntities.put("sup3", new Integer(179));
		htmlEntities.put("acute", new Integer(180));
		htmlEntities.put("m"+"i"+"c"+"r"+"o", new Integer(181));
		htmlEntities.put("par"+"a", new Integer(182));
		htmlEntities.put("mid"+"dot", new Integer(183));
		htmlEntities.put("c"+"e"+"d"+"i"+"l", new Integer(184));
		htmlEntities.put("sup1", new Integer(185));
		htmlEntities.put("o"+"r"+"d"+"m", new Integer(186));
		htmlEntities.put("r"+"a"+"quo", new Integer(187));
		htmlEntities.put("frac14", new Integer(188));
		htmlEntities.put("frac12", new Integer(189));
		htmlEntities.put("frac34", new Integer(190));
		htmlEntities.put("i"+"quest", new Integer(191));
		htmlEntities.put("A"+"grave", new Integer(192));
		htmlEntities.put("A"+"a"+"cute", new Integer(193));
		htmlEntities.put("A"+"c"+"i"+"r"+"c", new Integer(194));
		htmlEntities.put("A"+"tilde", new Integer(195));
		htmlEntities.put("A"+"u"+"m"+"l", new Integer(196));
		htmlEntities.put("A"+"ring", new Integer(197));
		htmlEntities.put("A"+"E"+"l"+"i"+"g", new Integer(198));
		htmlEntities.put("C"+"c"+"e"+"d"+"i"+"l", new Integer(199));
		htmlEntities.put("E"+"grave", new Integer(200));
		htmlEntities.put("E"+"a"+"cute", new Integer(201));
		htmlEntities.put("E"+"c"+"i"+"r"+"c", new Integer(202));
		htmlEntities.put("E"+"u"+"m"+"l", new Integer(203));
		htmlEntities.put("I"+"grave", new Integer(204));
		htmlEntities.put("I"+"a"+"cute", new Integer(205));
		htmlEntities.put("I"+"c"+"i"+"r"+"c", new Integer(206));
		htmlEntities.put("I"+"u"+"m"+"l", new Integer(207));
		htmlEntities.put("ETH", new Integer(208));
		htmlEntities.put("N"+"tilde", new Integer(209));
		htmlEntities.put("O"+"grave", new Integer(210));
		htmlEntities.put("O"+"a"+"cute", new Integer(211));
		htmlEntities.put("O"+"c"+"i"+"r"+"c", new Integer(212));
		htmlEntities.put("O"+"tilde", new Integer(213));
		htmlEntities.put("O"+"u"+""+"m"+"l", new Integer(214));
		htmlEntities.put("times", new Integer(215));
		htmlEntities.put("O"+"slash", new Integer(216));
		htmlEntities.put("U"+"grave", new Integer(217));
		htmlEntities.put("U"+"a"+"cute", new Integer(218));
		htmlEntities.put("U"+"c"+"i"+"r"+"c", new Integer(219));
		htmlEntities.put("U"+"u"+"m"+"l", new Integer(220));
		htmlEntities.put("Y"+"a"+"cute", new Integer(221));
		htmlEntities.put("THORN", new Integer(222));
		htmlEntities.put("s"+"z"+"l"+"i"+"g", new Integer(223));
		htmlEntities.put("a"+"grave", new Integer(224));
		htmlEntities.put("a"+"a"+"cute", new Integer(225));
		htmlEntities.put("a"+"c"+"i"+"r"+"c", new Integer(226));
		htmlEntities.put("a"+"tilde", new Integer(227));
		htmlEntities.put("a"+"u"+"m"+"l", new Integer(228));
		htmlEntities.put("a"+"ring", new Integer(229));
		htmlEntities.put("a"+"e"+"l"+"i"+"g", new Integer(230));
		htmlEntities.put("c"+"c"+"e"+"d"+"i"+"l", new Integer(231));
		htmlEntities.put("e"+"grave", new Integer(232));
		htmlEntities.put("e"+"a"+"cute", new Integer(233));
		htmlEntities.put("e"+"c"+"i"+"r"+"c", new Integer(234));
		htmlEntities.put("e"+"u"+"m"+"l", new Integer(235));
		htmlEntities.put("i"+"grave", new Integer(236));
		htmlEntities.put("i"+"a"+"cute", new Integer(237));
		htmlEntities.put("i"+"c"+"i"+"r"+"c", new Integer(238));
		htmlEntities.put("i"+"u"+""+"m"+"l", new Integer(239));
		htmlEntities.put("e"+"t"+"h", new Integer(240));
		htmlEntities.put("n"+"tilde", new Integer(241));
		htmlEntities.put("o"+"grave", new Integer(242));
		htmlEntities.put("o"+"a"+"cute", new Integer(243));
		htmlEntities.put("o"+"c"+"i"+"r"+"c", new Integer(244));
		htmlEntities.put("o"+"tilde", new Integer(245));
		htmlEntities.put("o"+"u"+"m"+"l", new Integer(246));
		htmlEntities.put("divide", new Integer(247));
		htmlEntities.put("o"+"slash", new Integer(248));
		htmlEntities.put("u"+"grave", new Integer(249));
		htmlEntities.put("u"+"a"+"cute", new Integer(250));
		htmlEntities.put("u"+"c"+"i"+"r"+"c", new Integer(251));
		htmlEntities.put("u"+"u"+"m"+"l", new Integer(252));
		htmlEntities.put("y"+"a"+"cute", new Integer(253));
		htmlEntities.put("thorn", new Integer(254));
		htmlEntities.put("y"+"u"+"m"+"l", new Integer(255));
		htmlEntities.put("f"+"no"+"f", new Integer(402));
		htmlEntities.put("Alpha", new Integer(913));
		htmlEntities.put("Beta", new Integer(914));
		htmlEntities.put("Gamma", new Integer(915));
		htmlEntities.put("Delta", new Integer(916));
		htmlEntities.put("Epsilon", new Integer(917));
		htmlEntities.put("Z"+"e"+"t"+"a", new Integer(918));
		htmlEntities.put("E"+"t"+"a", new Integer(919));
		htmlEntities.put("T"+"h"+"e"+"t"+"a", new Integer(920));
		htmlEntities.put("I"+"o"+"t"+"a", new Integer(921));
		htmlEntities.put("K"+"a"+"p"+"pa", new Integer(922));
		htmlEntities.put("Lambda", new Integer(923));
		htmlEntities.put("M"+"u", new Integer(924));
		htmlEntities.put("N"+"u", new Integer(925));
		htmlEntities.put("Xi", new Integer(926));
		htmlEntities.put("O"+"m"+"i"+"c"+"r"+"on", new Integer(927));
		htmlEntities.put("Pi", new Integer(928));
		htmlEntities.put("R"+"h"+"o", new Integer(929));
		htmlEntities.put("S"+"i"+"g"+"m"+"a", new Integer(931));
		htmlEntities.put("Tau", new Integer(932));
		htmlEntities.put("Up"+"s"+"i"+"l"+"on", new Integer(933));
		htmlEntities.put("P"+"h"+"i", new Integer(934));
		htmlEntities.put("C"+"h"+"i", new Integer(935));
		htmlEntities.put("P"+"s"+"i", new Integer(936));
		htmlEntities.put("O"+"m"+"e"+"g"+"a", new Integer(937));
		htmlEntities.put("alpha", new Integer(945));
		htmlEntities.put("beta", new Integer(946));
		htmlEntities.put("gamma", new Integer(947));
		htmlEntities.put("delta", new Integer(948));
		htmlEntities.put("epsilon", new Integer(949));
		htmlEntities.put("z"+"e"+"t"+"a", new Integer(950));
		htmlEntities.put("e"+"t"+"a", new Integer(951));
		htmlEntities.put("the"+"t"+"a", new Integer(952));
		htmlEntities.put("i"+"o"+"t"+"a", new Integer(953));
		htmlEntities.put("k"+"a"+"p"+"pa", new Integer(954));
		htmlEntities.put("lambda", new Integer(955));
		htmlEntities.put("m"+"u", new Integer(956));
		htmlEntities.put("n"+"u", new Integer(957));
		htmlEntities.put("xi", new Integer(958));
		htmlEntities.put("o"+"m"+"i"+""+"c"+"r"+"on", new Integer(959));
		htmlEntities.put("pi", new Integer(960));
		htmlEntities.put("r"+"h"+"o", new Integer(961));
		htmlEntities.put("s"+"i"+"g"+"m"+"a"+"f", new Integer(962));
		htmlEntities.put("s"+"i"+"g"+"m"+"a", new Integer(963));
		htmlEntities.put("tau", new Integer(964));
		htmlEntities.put("up"+"s"+"i"+"l"+"on", new Integer(965));
		htmlEntities.put("p"+"h"+"i", new Integer(966));
		htmlEntities.put("c"+"h"+"i", new Integer(967));
		htmlEntities.put("p"+"s"+"i", new Integer(968));
		htmlEntities.put("o"+"m"+"e"+"g"+"a", new Integer(969));
		htmlEntities.put("the"+"t"+"a"+"s"+"y"+"m", new Integer(977));
		htmlEntities.put("up"+"s"+"i"+"h", new Integer(978));
		htmlEntities.put("pi"+"v", new Integer(982));
		htmlEntities.put("bull", new Integer(8226));
		htmlEntities.put("hell"+"i"+"p", new Integer(8230));
		htmlEntities.put("prime", new Integer(8242));
		htmlEntities.put("Prime", new Integer(8243));
		htmlEntities.put("o"+"line", new Integer(8254));
		htmlEntities.put("f"+"r"+""+"a"+"s"+"l", new Integer(8260));
		htmlEntities.put("we"+"i"+"e"+"r"+"p", new Integer(8472));
		htmlEntities.put("image", new Integer(8465));
		htmlEntities.put("real", new Integer(8476));
		htmlEntities.put("trade", new Integer(8482));
		htmlEntities.put("ale"+"f"+"s"+"y"+"m", new Integer(8501));
		htmlEntities.put("l"+"a"+"r"+"r", new Integer(8592));
		htmlEntities.put("u"+"a"+"r"+"r", new Integer(8593));
		htmlEntities.put("r"+"a"+"r"+"r", new Integer(8594));
		htmlEntities.put("d"+"a"+"r"+"r", new Integer(8595));
		htmlEntities.put("ha"+"r"+"r", new Integer(8596));
		htmlEntities.put("c"+"r"+""+"a"+"r"+"r", new Integer(8629));
		htmlEntities.put("lArr", new Integer(8656));
		htmlEntities.put("uArr", new Integer(8657));
		htmlEntities.put("rArr", new Integer(8658));
		htmlEntities.put("dArr", new Integer(8659));
		htmlEntities.put("hArr", new Integer(8660));
		htmlEntities.put("for"+"all", new Integer(8704));
		htmlEntities.put("part", new Integer(8706));
		htmlEntities.put("exist", new Integer(8707));
		htmlEntities.put("empty", new Integer(8709));
		htmlEntities.put("n"+"a"+"b"+"l"+"a", new Integer(8711));
		htmlEntities.put("is"+"in", new Integer(8712));
		htmlEntities.put("not"+"in", new Integer(8713));
		htmlEntities.put("n"+"i", new Integer(8715));
		htmlEntities.put("p"+"rod", new Integer(8719));
		htmlEntities.put("sum", new Integer(8721));
		htmlEntities.put("minus", new Integer(8722));
		htmlEntities.put("low"+"as"+"t", new Integer(8727));
		htmlEntities.put("r"+"a"+"d"+"i"+"c", new Integer(8730));
		htmlEntities.put("prop", new Integer(8733));
		htmlEntities.put("in"+"fin", new Integer(8734));
		htmlEntities.put("an"+"g", new Integer(8736));
		htmlEntities.put("and", new Integer(8743));
		htmlEntities.put("or", new Integer(8744));
		htmlEntities.put("cap", new Integer(8745));
		htmlEntities.put("cup", new Integer(8746));
		htmlEntities.put("int", new Integer(8747));
		htmlEntities.put("there4", new Integer(8756));
		htmlEntities.put("s"+"i"+"m", new Integer(8764));
		htmlEntities.put("c"+"on"+"g", new Integer(8773));
		htmlEntities.put("a"+"s"+"y"+"m"+"p", new Integer(8776));
		htmlEntities.put("n"+"e", new Integer(8800));
		htmlEntities.put("e"+"q"+"u"+"i"+"v", new Integer(8801));
		htmlEntities.put("l"+"e", new Integer(8804));
		htmlEntities.put("g"+"e", new Integer(8805));
		htmlEntities.put("sub", new Integer(8834));
		htmlEntities.put("sup", new Integer(8835));
		htmlEntities.put("n"+"sub", new Integer(8836));
		htmlEntities.put("sub"+"e", new Integer(8838));
		htmlEntities.put("sup"+"e", new Integer(8839));
		htmlEntities.put("o"+"plus", new Integer(8853));
		htmlEntities.put("o"+"times", new Integer(8855));
		htmlEntities.put("per"+"p", new Integer(8869));
		htmlEntities.put("s"+"dot", new Integer(8901));
		htmlEntities.put("l"+"c"+"e"+"i"+"l", new Integer(8968));
		htmlEntities.put("r"+"c"+"e"+"i"+"l", new Integer(8969));
		htmlEntities.put("l"+"floor", new Integer(8970));
		htmlEntities.put("r"+"floor", new Integer(8971));
		htmlEntities.put("lang", new Integer(9001));
		htmlEntities.put("rang", new Integer(9002));
		htmlEntities.put("l"+"o"+"z", new Integer(9674));
		htmlEntities.put("spades", new Integer(9824));
		htmlEntities.put("clubs", new Integer(9827));
		htmlEntities.put("hearts", new Integer(9829));
		htmlEntities.put("d"+"i"+"am"+"s", new Integer(9830));
		htmlEntities.put("quot", new Integer(34));
		htmlEntities.put("amp", new Integer(38));
		htmlEntities.put("lt", new Integer(60));
		htmlEntities.put("gt", new Integer(62));
		htmlEntities.put("OElig", new Integer(338));
		htmlEntities.put("o"+"e"+"l"+"i"+"g", new Integer(339));
		htmlEntities.put("Scar"+"on", new Integer(352));
		htmlEntities.put("scar"+"on", new Integer(353));
		htmlEntities.put("Y"+"u"+"m"+"l", new Integer(376));
		htmlEntities.put("c"+"i"+"r"+"c", new Integer(710));
		htmlEntities.put("tilde", new Integer(732));
		htmlEntities.put("e"+"n"+"s"+"p", new Integer(8194));
		htmlEntities.put("e"+"m"+"s"+"p", new Integer(8195));
		htmlEntities.put("thin"+"s"+"p", new Integer(8201));
		htmlEntities.put("z"+"w"+"n"+"j", new Integer(8204));
		htmlEntities.put("z"+"w"+"j", new Integer(8205));
		htmlEntities.put("l"+"r"+"m", new Integer(8206));
		htmlEntities.put("r"+"l"+"m", new Integer(8207));
		htmlEntities.put("n"+"dash", new Integer(8211));
		htmlEntities.put("m"+"dash", new Integer(8212));
		htmlEntities.put("l"+"s"+"quo", new Integer(8216));
		htmlEntities.put("r"+"s"+"quo", new Integer(8217));
		htmlEntities.put("s"+"b"+"quo", new Integer(8218));
		htmlEntities.put("l"+"d"+"quo", new Integer(8220));
		htmlEntities.put("r"+"d"+"quo", new Integer(8221));
		htmlEntities.put("b"+"d"+"quo", new Integer(8222));
		htmlEntities.put("dagger", new Integer(8224));
		htmlEntities.put("Dagger", new Integer(8225));
		htmlEntities.put("p"+"e"+"r"+"m"+"i"+"l", new Integer(8240));
		htmlEntities.put("l"+"s"+"a"+"quo", new Integer(8249));
		htmlEntities.put("r"+"s"+"a"+"quo", new Integer(8250));
		htmlEntities.put("euro", new Integer(8364));
	}

    

    static String[] addRootElement(String[] source, String tagName) throws Exception {
	String[]  returnvalue = new String[source.length + 2];
	int i;
	returnvalue[0] = source[0];
	returnvalue[1] = "<" + tagName + ">\n";
	for (i = 2;i < source.length + 1 ;i++)
	    returnvalue[i] = source[i-1];
	returnvalue[i] = "</" + tagName + ">\n";
	return returnvalue;
    }

    static String createSourceInOneString(String[] source) {
	StringBuilder temp;

	//first put the whole source in a GenericXMLElementList
	temp  = new StringBuilder();
	for (int i = 0;i < source.length; i++)
	    temp.append(source[i]);
	return temp.toString();
    }

    static ArrayList<XMLElement> makeList(String[] source, String charsetName) throws Exception {
	XMLElement root = null;
	 ArrayList<XMLElement> resultList = null;
	
	try {
	    root = (DefaultXMLElement) (new EasyXMLDataParser(new String[] {"qskdjfke678290klkfkdqjfkd"},
			new String[] {"testje"},true).parse(net.johandegraeve.getcontents.Utilities.createSourceInOneString(source),charsetName));
	    resultList = net.johandegraeve.easyxmldata.Utilities.createXMLElementList(root);
	  
	} catch (SAXParseException e) {
	    if (e.toString().contains("markup in the document following the root element must be well-formed")) {
		//seems a root element is missing, so we will add one, reparse and remove it
		source = net.johandegraeve.getcontents.Utilities.addRootElement(source, "root");
		try {
		    root = (DefaultXMLElement) (new EasyXMLDataParser(new String[] {"qskdjfke678290klkfkdqjfkd"},
		    		new String[] {"testje"},true).parse(net.johandegraeve.getcontents.Utilities.createSourceInOneString(source),charsetName));
		    resultList = new ArrayList<XMLElement>(root.getChildren());
		} catch (SAXParseException e2) {
		    if (e2.toString().contains("markup in the document following the root element must be well-formed")) {
			throw new SAXParseException(e.toString() + "\nMaybe you need to add an " +
				TagAndAttributeNames.INSTRUCTIONaddXMLDeclarationTag + ".", null);
		    }
		    else
			throw e2;
		}
	    } else {
		throw e;
	    }
	}
	
	return resultList;
    }
    
	
	/**
	 * An case insensitive version of the method {@link com.Ostermiller.util.StringHelper#replace(String, String, String)}
	 * @param s
	 * @param find
	 * @param replace
	 * @return new string with all occurrences of 'find' replaced by 'replace'
	 */
	public static String replaceIgnoreCase(String s, String find, String replace) {
		int findLength;
		// the next statement has the side effect of throwing a null pointer
		// exception if s is null.
		int stringLength = s.length();
		if (find == null || (findLength = find.length()) == 0){
			// If there is nothing to find, we won't try and find it.
			return s;
		}
		if (replace == null){
			// a null string and an empty string are the same
			// for replacement purposes.
			replace = "";
		}
		int replaceLength = replace.length();

		//s and find are turned into lowercase because we'll do caseinsensitive checking
		String lowerCase_s = s.toLowerCase();
		String lowerCase_find = find.toLowerCase();
		
		// We need to figure out how long our resulting string will be.
		// This is required because without it, the possible resizing
		// and copying of memory structures could lead to an unacceptable runtime.
		// In the worst case it would have to be resized n times with each
		// resize having a O(n) copy leading to an O(n^2) algorithm.
		int length;
		if (findLength == replaceLength){
			// special case in which we don't need to count the replacements
			// because the count falls out of the length formula.
			length = stringLength;
		} else {
			int count;
			int start;
			int end;

			// Scan s and count the number of times we find our target.
			count = 0;
			start = 0;
			while((end = lowerCase_s.indexOf(lowerCase_find, start)) != -1){
				count++;
				start = end + findLength;
			}
			if (count == 0){
				// special case in which on first pass, we find there is nothing
				// to be replaced.  No need to do a second pass or create a string buffer.
				return s;
			}
			length = stringLength - (count * (findLength - replaceLength));
		}

		int start = 0;
		int end = lowerCase_s.indexOf(lowerCase_find, start);
		if (end == -1){
			// nothing was found in the string to replace.
			// we can get this if the find and replace strings
			// are the same length because we didn't check before.
			// in this case, we will return the original string
			return s;
		}
		// it looks like we actually have something to replace
		// *sigh* allocate memory for it.
		StringBuffer sb = new StringBuffer(length);

		// Scan s and do the replacements
		while (end != -1){
			sb.append(s.substring(start, end));
			sb.append(replace);
			start = end + findLength;
			end = lowerCase_s.indexOf(lowerCase_find, start);
		}
		end = stringLength;
		sb.append(s.substring(start, end));

		return (sb.toString());
	}

	/**
	 * Utility to create a character from a string, string s can contain either a unicode representation or just a normal string.<br>
	 * If s is a unicode representation starting with \\u , then an attempt will be pade to parse the remaining part of the string with radix 16
	 * and to cast the resulting integer to char. The result will be returned.<br>
	 * If is does not start with \\u, then the first character of the string s is returned.
	 * @param s
	 * @return char
	 * @throws SAXException
	 */
	static char createCharFromString(String s) throws SAXException {
	    char c;
	    try {
		if (s.toLowerCase().startsWith("\\u") ) {
		    s = s.substring(2);
		    //c = new Character(Integer.parseInt(tempC));
		    c = (char) Integer.parseInt(s,16);
		}
		else {
		    c = s.charAt(0);
		}
	    } catch (StringIndexOutOfBoundsException e2) {
		throw new SAXException("Element " + TagAndAttributeNames.STRING_PROCESSORmidPadTag + " has an invalid value for attribute character. The value should be one character." +
		" You can enter a unicode value of a character, for example character=\"\\u0041\" means you want to use the character A.");
	    }  catch (NumberFormatException e3) {
		throw new SAXException("Element " + TagAndAttributeNames.STRING_PROCESSORmidPadTag + " has an invalid value for attribute character. The value should be one character." +
		" You can enter a unicode value of a character, for example character=\"\\u0041\" means you want to use the character A.");
	    }
	    return c;
	}
 
}
