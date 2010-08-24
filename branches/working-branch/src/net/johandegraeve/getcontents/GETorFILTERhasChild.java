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

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Class for hasChildFilter from HTML Parser package.<br>
 * A hasChildFilters filters on nodes that have a child matching another HTML filter.
 *
 * @author Johan Degraeve
 *
 */
public class GETorFILTERhasChild implements HTMLFilter, XMLElement {

    /**
     * the child filter
     */
    private HTMLFilter mChildFilter;
    
    /**
     * constructor
     */
    public GETorFILTERhasChild() {
	mChildFilter = null;
    }
    
    /**
     * returns a childFilter
     * @see net.johandegraeve.getcontents.HTMLFilter#getHTMLFilter()
     */
    @Override
    public NodeFilter getHTMLFilter() {
	return new HasChildFilter(mChildFilter.getHTMLFilter()/*, mRecursive*/);
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    /**
     * if child is an HTML filter then child is assigned to mChildFilter, otherwise an exception is thrown
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child, 
		TagAndAttributeNames.GETorFILTERPrefix,
		TagAndAttributeNames.htmlfilterTags,
		TagAndAttributeNames.GETorFILTERhasChildTag);
	
	//if every any other kind of child would need to be added to this element, then it needs to be created before this mChildFilter	
	mChildFilter = (HTMLFilter) child;
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
    }

    /**
     * throws an exception of mChildFilter = null
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (mChildFilter == null) 
	    throw new SAXException("Element " + TagAndAttributeNames.GETorFILTERhasChildTag + " must have a filter as child");
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
     * @return the mChildFilter in an ArrayList
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	return Utilities.createXMLElementList((XMLElement)mChildFilter);
    }

    /**
     * @return the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERhasChildTag;
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
     * does  nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    /**
     * @return false;
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	return false;
    }
}
