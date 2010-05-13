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

import org.htmlparser.Node;
import org.htmlparser.util.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Getter to get the children
 *
 * @author Johan Degraeve
 *
 */
public class GETorFILTERchildren implements HTMLGetter, XMLElement, XMLGetter {

    /**
     * @return the children in a NodeList, null if there are no children
     * @see net.johandegraeve.getcontents.HTMLGetter#getList(org.htmlparser.Node)
     */
    @Override
    public NodeList getList(Node node) {
	return node.getChildren();
    }

    /**
     * @return  the children in a XMLXMLGetterResultList, null if there are no children
     * @see net.johandegraeve.getcontents.XMLGetter#getList(net.johandegraeve.getcontents.GenericXMLGetterResultList)
     */
    @Override
    public XMLXMLGetterResultList getList(GenericXMLGetterResultList list) {
	XMLXMLGetterResultList returnvalue = new  XMLXMLGetterResultList();
	    
	if (list instanceof XMLXMLGetterResultList) {
	    for (int i = 0;i < list.size();i ++) {
		XMLXMLGetterResultList children = 
		    ((XMLXMLGetterResult)(list.elementAt(i))).getChildren();
		if (children != null)
		    returnvalue.add( 
			    ((XMLXMLGetterResult)(list.elementAt(i))).getChildren()
				   );
	    }
	} else  {
	    //it's a stringxmlgetterresultlist, no children, bad luck
	    
	}
	
	if (returnvalue.size() > 0) {
	    return returnvalue;
	} else 
	    return null;
    }

    /** 
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    /**
     * throws an Exception.
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GETorFILTERchildrenTag);
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
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
     * @return null
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
	return TagAndAttributeNames.GETorFILTERchildrenTag;
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
