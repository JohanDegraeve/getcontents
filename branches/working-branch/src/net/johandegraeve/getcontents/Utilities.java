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

import net.johandegraeve.easyxmldata.DefaultXMLElement;
import net.johandegraeve.easyxmldata.EasyXMLDataParser;
import net.johandegraeve.easyxmldata.XMLElement;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.Ostermiller.util.StringHelper;

/**
 * A few useful methods
 * 
 * @author Johan Degraeve
 *
 */
public class Utilities  {
    

    /**
     * hard to explain in doc, see the code
     */
    private static final String [][] replacementStrings = new String[][] {
	{"\\t","\t"},
	{"\\b","\b"},
	{"\\n","\n"},
	{"\\r","\r"},
	{"\\f","\f"},
	{"\\\'","\'"},
	{"\\\"","\""},
	{"\\\\","\\"}
    };

    /**
     * adds a root element, to be used in case the source represents an incomplete xml, ie the result of a previous 
     * filtering instruction.
     * @param source array of strings representing the XML element missing a root element
     * @param tagName the name of the tag that the root should have
     * @return two strings will be added to the source, one in the beginning, one at the end
     * @throws Exception
     */
    public static String[] addRootElement(String[] source, String tagName) throws Exception {
	String[]  returnvalue = new String[source.length + 2];
	int i;
	returnvalue[0] = source[0];
	returnvalue[1] = "<" + tagName + ">\n";
	for (i = 2;i < source.length + 1 ;i++)
	    returnvalue[i] = source[i-1];
	returnvalue[i] = "</" + tagName + ">\n";
	return returnvalue;
    }

    /**
     * reads all strings in source and returns it in one string
     * @param source
     * @return the complete source
     */
    public static String createSourceInOneString(String[] source) {
	StringBuilder temp;

	//first put the whole source in a GenericXMLElementList
	temp  = new StringBuilder();
	for (int i = 0;i < source.length; i++)
	    temp.append(source[i]);
	return temp.toString();
    }

    /**
     * tries to create an XMLElement using source as input, source can either be a url (http or file)
     * or may represent the actual XML in string format.
     * @param source concatenation of all strings represents the source
     * @param charsetName will be used only if source contains the XML itself (and not a url), 
     * see also {@link net.johandegraeve.easyxmldata.EasyXMLDataParser#parse(String,  String)}
     * @return an XMLElement 
     * @throws Exception 
     */
    public static ArrayList<XMLElement> makeList(String[] source, String charsetName) throws Exception {
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
	 * A case insensitive version of the method {@link com.Ostermiller.util.StringHelper#replace(String, String, String)}
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
	public static char createCharFromString(String s) throws SAXException {
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
	
	/**
	 * unescape invisible characters, eg replace &quot;\t&quot; by a real tab character<br>
	 * Characters escape sequences treated are :  &quot;\t&quot;, &quot;\b&quot;, &quot;\n&quot;, &quot;\r&quot;, &quot;\f&quot;, &quot;\'&quot;, &quot;\\&quot;,&quot;\&quot;&quot;
	 * @param source the input
	 * @return source with escaped characters replaced
	 */
	static String unescapeInvisibleCharacters(String source) {
	    	for (int k = 0; k < replacementStrings.length;k++) {
	    	    source = StringHelper.replace(source, replacementStrings[k][0], replacementStrings[k][1]);
	    	}
	    return source;
	}
 
}
