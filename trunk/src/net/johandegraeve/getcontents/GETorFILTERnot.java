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

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.HasSiblingFilter;
import org.htmlparser.filters.NotFilter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

public class GETorFILTERnot implements HTMLFilter, XMLElement {

    private HTMLFilter mPredicate;
    
    public GETorFILTERnot() {
	mPredicate = null;
    }
    
    @Override
    public NodeFilter getHTMLFilter() {
	return new NotFilter(mPredicate.getHTMLFilter());
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (mPredicate != null)
	    throw new SAXException("Element " + TagAndAttributeNames.GETorFILTERnotTag + 
		    " can only yave one child filter");
	Utilities.verifyChildType(child, 
		TagAndAttributeNames.GETorFILTERPrefix,
		TagAndAttributeNames.htmlfilterTags,
		TagAndAttributeNames.GETorFILTERnotTag);
	
	//if ever any other kind of child would need to be added to this element, then it needs to be created before this mParentFilter	
	mPredicate = (HTMLFilter) child;
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (mPredicate == null) 
	    throw new SAXException("Element " + TagAndAttributeNames.GETorFILTERnotTag + " must have a filter as child");
    }

    @Override
    public Attributes getAttributes() {
	return null;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	return Utilities.createXMLElementList((XMLElement) mPredicate);
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERnotTag;
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
