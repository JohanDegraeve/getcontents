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
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.htmlparser.NodeFilter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/*untrimmed
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class GENERICpattern implements  XMLElement {
    
    private String pattern;
    
    private int flags;

    public GENERICpattern() {
	flags = 0;
	pattern = null;
    }
    
    public String getPattern() {
	return pattern;
    }
    
    public int getFlags() {
	return flags;
    }

    public boolean caseSensitive() {
	return ((flags & Pattern.CASE_INSENSITIVE) == 0 ? true:false);
    }
    
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	String[] attrValues = Utilities.getOptionalAttributeValues(
		attributes, 
		new String[] {
			TagAndAttributeNames.case_sensitiveAttribute}, 
		new String[]  {
			"false"});
	//mind that Pattern.CASE_INSENSITIVE means NOT sensitive !!
	if (!attrValues[0].equalsIgnoreCase("true")) flags = flags + Pattern.CASE_INSENSITIVE;
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GENERICpatternTag);
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if ((pattern == null) || (pattern.length() == 0) )
		throw new SAXException(
		    "Element " + 
		    TagAndAttributeNames.GENERICpatternTag +
		    " must have text");
	try {
	    //adding Pattern.UNICODE_CASE because in org.htmlparser.filters.LinkRegexFilter (String regexPattern, boolean caseSensitive)
	    //UNICODE_CASE is being added if not case sensitive
	    Pattern.compile(pattern, (flags == 0 ? 0:flags + Pattern.UNICODE_CASE));
	} catch (PatternSyntaxException e) {
	    throw new SAXException("Compilation regular expression failed. \n Error returned by Pattern.compile(String) = \n" + e.getMessage() + "\n");
	}
    }

    @Override
    public Attributes getAttributes() {
	AttributesImpl returnvalue = new AttributesImpl();
	returnvalue.addAttribute(null, TagAndAttributeNames.case_sensitiveAttribute, TagAndAttributeNames.case_sensitiveAttribute, "CDATA", 
		(flags & Pattern.CASE_INSENSITIVE) == 0 ? "true":"false");
	return returnvalue;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	return null;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICpatternTag;
    }

    @Override
    public String getText() {
	return pattern;
    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
	pattern = text;
    }

    @Override
    public boolean preserveSpaces() {
	return true;
    }
}
