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
import java.util.Locale;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.StringFilter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * StringFilter as defined in HTML parser package.<br>
 * created with Locale.English
 *
 * @author Johan Degraeve
 *
 */
public class GETorFILTERstringFilter implements XMLElement, HTMLFilter {
    
    /**
     * the pattern
     */
    private GENERICpattern mPattern;
    
    /**
     * constructor setting mPattern to null
     */
    public GETorFILTERstringFilter() {
	mPattern = null;
    }

    /**
     * @return a StringFilter with mPattern and Locale = Locale.English
     * @see net.johandegraeve.getcontents.HTMLFilter#getHTMLFilter()
     */
    @Override
    public NodeFilter getHTMLFilter() {
	return new StringFilter(mPattern.getPattern(),mPattern.caseSensitive(),Locale.ENGLISH);
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    /**
     * if child is a pattern, then child is assigned to mPattern, otherwise an exception is thrown
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child, TagAndAttributeNames.genericPrefix, 
		new String[] {TagAndAttributeNames.GENERICpatternTag}, 
		TagAndAttributeNames.GETorFILTERstringFilterTag);
	mPattern = (GENERICpattern) child;
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
    }

    /**
     * an exception is thrown if mPattern = null
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (mPattern == null)
	    throw new SAXException("Element " + TagAndAttributeNames.GETorFILTERstringFilterTag + " must have a pattern as child.");
    }

    /**
     * @return null
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	return null;
    }

    /**
     * @return mPattern in an ArrayList
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	return Utilities.createXMLElementList((XMLElement)mPattern);
    }

    /**
     * @return the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERstringFilterTag;
    }

    /**
     * @return null
     * @see net.johandegraeve.easyxmldata.XMLElement#getText()
     */
    @Override
    public String getText() {
	return null;
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    /**
     * @return false
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	return false;
    }

}
