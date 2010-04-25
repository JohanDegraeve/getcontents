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

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * a pattern String, basically just a string with one attribute named "case_sensitive" with value "false" or "true"<br>
 * In the complete method, a pattern compilation is done to verify the correctness : 
 * The pattern should compile with 
 * Pattern.compile(pattern,  case_sensitive == true ? 0: Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE));
 *
 * @author Johan Degraeve
 *
 */
public class GENERICpattern implements  XMLElement {
    
    /**
     * the pattern
     */
    private String pattern;
    
    /**
     * as defined in Java class jva.util.regex.Pattern
     */
    private int flags;

    /**
     * constructor, flags = 0 meaning case_sensitive = true, pattern = null
     */
    public GENERICpattern() {
	flags = 0;
	pattern = null;
    }
    
    /**
     * @return pattern
     */
    public String getPattern() {
	return pattern;
    }
    
    /**
     * @return true if flags does not contain case_insensitive flag, false otherwise
     */
    public boolean caseSensitive() {
	return ((flags & Pattern.CASE_INSENSITIVE) == 0 ? true:false);
    }
    
    /**
     * if case_sensitive is in the attributes, then flags is set according to the value of the attribute
     * which is :<br>
     * - if case_sensitive = false, then flags = flags + Pattern.CASE_INSENSITIVE<br>
     * - if case_sensitive = true, then flags is not changed.<br>
     * if case_sensitive is not in the attributes, then case_sensitive = true is used<br>
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	String[] attrValues = Utilities.getOptionalAttributeValues(
		attributes, 
		new String[] {
			TagAndAttributeNames.case_sensitiveAttribute}, 
		new String[]  {
			"true"});
	//mind that Pattern.CASE_INSENSITIVE means NOT sensitive !!
	if (!attrValues[0].equalsIgnoreCase("true")) flags = flags + Pattern.CASE_INSENSITIVE;
    }

    /**
     * throws an exception
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GENERICpatternTag);
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
    }

    /**
     * throws an exception of pattern = null or pattern.length  = 0 or if
     * Pattern.compile(pattern, (flags == 0 ? 0:flags + Pattern.UNICODE_CASE)) throws an exception
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
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

    /**
     * @return case_sensitive true or false in an attribute
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl returnvalue = new AttributesImpl();
	returnvalue.addAttribute(null, TagAndAttributeNames.case_sensitiveAttribute, TagAndAttributeNames.case_sensitiveAttribute, "CDATA", 
		(flags & Pattern.CASE_INSENSITIVE) == 0 ? "true":"false");
	return returnvalue;
    }

    /**
     * @return null;
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	return null;
    }

    /**
     * @return the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICpatternTag;
    }

    /**
     * @return pattern
     * @see net.johandegraeve.easyxmldata.XMLElement#getText()
     */
    @Override
    public String getText() {
	return pattern;
    }

    /**
     * assigns text to pattern
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
	pattern = text;
    }

    /**
     * @return true
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	return true;
    }
}
