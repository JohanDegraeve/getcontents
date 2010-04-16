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

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.LinkRegexFilter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/*
 * a pattern needs to be added which can be case sensitive or not
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class GETorFILTERlinkRegexFilter implements HTMLFilter, XMLElement {

    private GENERICpattern mRegex;
    
    public GETorFILTERlinkRegexFilter () {
	mRegex = null;
    }
    
    @Override
    public NodeFilter getHTMLFilter() {
	return new LinkRegexFilter(mRegex.getPattern(),mRegex.caseSensitive());
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child, TagAndAttributeNames.genericPrefix, new String[] {TagAndAttributeNames.GENERICpatternTag}, TagAndAttributeNames.GETorFILTERlinkRegexFilterTag);
	mRegex = (GENERICpattern)child;
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (mRegex == null)
	    throw new SAXException("Element " + TagAndAttributeNames.GETorFILTERlinkRegexFilterTag + " must have a text representing the regular expression.");
    }
    
    @Override
    public Attributes getAttributes() {
	return null;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	return Utilities.createXMLElementList((XMLElement)mRegex);
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERlinkRegexFilterTag;
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
