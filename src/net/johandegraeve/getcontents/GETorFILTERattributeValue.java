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
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * get text
 * 
 * @author Johan Degraeve
 *
 */
public class GETorFILTERattributeValue implements XMLElement, HTMLGetter {
    
    /**
     * the attributename of which value should be retrieved
     */
    private String attributename;

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    /**
     * throws an exception
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GETorFILTERattributeValue);
    }

    /**
     * assigns text to {@link #attributename}
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
	attributename = text;
    }

    /**
     * checks if {@link #attributename} has a valid value
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (attributename == null)
	    throw new SAXException("Element of type " + TagAndAttributeNames.GETorFILTERattributeValue +
		    "must have text being the attributename for which the value is to be retrieved");
	if (attributename.length() == 0)
	    throw new SAXException("Element of type " + TagAndAttributeNames.GETorFILTERattributeValue +
	    "must have text being the attributename for which the value is to be retrieved");
    }

    /**
     * get the attributevalue for the specified attributename<br>
     * @return a nodelist with one TextNode that has the attributevalue as text, empty string if not found
     * 
     * 
     * @see net.johandegraeve.getcontents.HTMLGetter#getList(org.htmlparser.Node)
     */
    @Override
    public NodeList getList(Node element) {
	if (element == null) return new NodeList( new TextNode(""));
	if (!(element instanceof TagNode)) return new NodeList( new TextNode(""));

	if( ((TagNode)element).getAttribute(attributename) == null)
	    return new NodeList( new TextNode(""));;
	    
	return new NodeList(new TextNode(((TagNode)element).getAttribute(attributename)));    
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
	return TagAndAttributeNames.GETorFILTERattributeValue;
    }

    /**
     * @return {@link #attributename}
     * @see net.johandegraeve.easyxmldata.XMLElement#getText()
     */
    @Override
    public String getText() {
	return attributename;
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
