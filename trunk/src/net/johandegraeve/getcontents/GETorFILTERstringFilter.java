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
import java.util.Locale;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.RegexFilter;
import org.htmlparser.filters.StringFilter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/*
 * StringFilter created with Locale.English
 *
 * @author Johan Degraeve
 *
 */
public class GETorFILTERstringFilter implements XMLElement, HTMLFilter {
    
    private boolean caseSensitive;
    private GENERICpattern mPattern;
    
    public GETorFILTERstringFilter() {
	mPattern = null;
	caseSensitive = false;
    }

    @Override
    public NodeFilter getHTMLFilter() {
	return new StringFilter(mPattern.getPattern(),caseSensitive,Locale.ENGLISH);
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	String[] attrValues = Utilities.getOptionalAttributeValues(
		attributes, 
		new String[] {
			TagAndAttributeNames.case_sensitiveAttribute,
		}, 
		new String[]  {
			"false",
		});
	if (attrValues[0].equalsIgnoreCase("true")) 
	    caseSensitive = true;
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child, TagAndAttributeNames.genericPrefix, 
		new String[] {TagAndAttributeNames.GENERICpatternTag}, 
		TagAndAttributeNames.GETorFILTERstringFilterTag);
	mPattern = (GENERICpattern) child;
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (mPattern == null)
	    throw new SAXException("Element " + TagAndAttributeNames.GETorFILTERstringFilterTag + " must have a pattern as child.");
    }

    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, 
		TagAndAttributeNames.case_sensitiveAttribute, 
		TagAndAttributeNames.case_sensitiveAttribute, 
		"CDATA", 
		(caseSensitive ? "true":"false"));
	return attr;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	return Utilities.createXMLElementList((XMLElement)mPattern);
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERstringFilterTag;
    }

    @Override
    public String getText() {
	return null;
    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
	// XXX Auto-generated method stub
	
    }

    @Override
    public boolean preserveSpaces() {
	// XXX Auto-generated method stub
	return false;
    }

}
