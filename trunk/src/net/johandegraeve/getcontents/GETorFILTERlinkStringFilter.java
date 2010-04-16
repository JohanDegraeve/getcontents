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
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.naming.directory.AttributeInUseException;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.LinkStringFilter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/*
 * mpattern untrimmed
 * @author Johan Degraeve
 *
 */
public class GETorFILTERlinkStringFilter implements HTMLFilter, XMLElement {
    
    private boolean mCaseSensitive;
    private String mPattern;

    @Override
    public NodeFilter getHTMLFilter() {
	return new LinkStringFilter(mPattern,mCaseSensitive);
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	mCaseSensitive = Boolean.parseBoolean(Utilities.getOptionalAttributeValues(
		attributes, 
		new String[] {
			TagAndAttributeNames.case_sensitiveAttribute,
		}, 
		new String[]  {
			"false",
		})[0]); 
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child, "", new String[] {""}, TagAndAttributeNames.GETorFILTERlinkStringFilterTag);
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (mPattern == null)
	    throw new SAXException("Element " + TagAndAttributeNames.GETorFILTERlinkStringFilterTag + " must have a text to search.");
    }

    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.case_sensitiveAttribute, TagAndAttributeNames.case_sensitiveAttribute, "CDATA", Boolean.toString(mCaseSensitive));
	return attr;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	return null;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERlinkStringFilterTag;
    }

    @Override
    public String getText() {
	return mPattern;
    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
	mPattern = text;
    }

    @Override
    public boolean preserveSpaces() {
	return true;
    }
}
