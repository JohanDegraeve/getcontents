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

import net.johandegraeve.easyxmldata.XMLElement;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class GETorFILTERtagName implements XMLElement, HTMLFilter, XMLFilter  {

    private String mName;
    
    public String getName() {
	return mName;
    }

    @Override
    public NodeFilter getHTMLFilter() {
	return new TagNameFilter(mName);
    }

    @Override
    public XMLElementFilter getXMLFilter() {
	return new XMLElementFilter() {
	    @Override
	    public boolean accept(XMLElement element) {
		return (element.getTagName().equals(mName));
	    }

	};
    }
    
    public GETorFILTERtagName() {
	mName = null;
    }
    
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {

    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GETorFILTERtagNameTag);
    }

    @Override
    public void addText(String text) throws SAXException {
	mName = text;
    }

    @Override
    public void complete() throws SAXException {
	if ((mName == null) || (mName.length() == 0) )
		throw new SAXException(
		    "Element " + 
		    TagAndAttributeNames.GETorFILTERtagNameTag +
		    " must have text");
    }

    @Override
    public Attributes getAttributes() {
	return null;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	return null;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERtagNameTag;
    }

    @Override
    public String getText() {
	return mName;
    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    @Override
    public boolean preserveSpaces() {
	return false;
    }
}
