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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import net.johandegraeve.easyxmldata.DefaultXMLElement;
import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/*
 * 	TO BE DELETED
 * the input must have an xml declaration as first instance, the instruction will add the new element before the second line in the source.
 * It also implements interface XMLGetter, because then it can also be used as xmlgetterinstruction, strange but yes
 *
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONaddRootElement extends Instruction implements XMLElement, XMLGetter {
    
    private String tagName;
    
    public String gettagName() {
	return tagName;
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.INSTRUCTIONaddRootElementTag);
    }

    @Override
    public void addText(String text) throws SAXException {
	tagName = text;
    }

    @Override
    public void complete() throws SAXException {
	if ((tagName == null) || (tagName.length() == 0) )
		throw new SAXException(
		    "Element " + 
		    TagAndAttributeNames.INSTRUCTIONaddRootElementTag +
		    " must have text");
    }

    @Override
    String[] execute(String[] source) throws Exception {
	String[]  returnvalue = new String[source.length + 2];
	int i;
	returnvalue[0] = source[0];
	returnvalue[1] = "<" + tagName + ">\n";
	for (i = 2;i < source.length + 1 ;i++)
	    returnvalue[i] = source[i-1];
	returnvalue[i] = "</" + tagName + ">\n";
	return returnvalue;
    }

    /* takes as input XMLXMLGetterResultList, and returns XMLXMLGetterResultList
     * @see net.johandegraeve.getcontents.XMLGetter#getList(net.johandegraeve.getcontents.GenericXMLGetterResultList)
     */
    @Override
    public GenericXMLGetterResultList getList(GenericXMLGetterResultList list) {
	XMLXMLGetterResultList xmlList = (XMLXMLGetterResultList) list;
	DefaultXMLElement rootElement = new DefaultXMLElement(tagName);
	for (int i = 0; i < xmlList.size();i++) {
	    try {
		rootElement.addChild(((XMLXMLGetterResult)xmlList.elementAt(i)).getDefaultXMLElement());
	    } catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return new XMLXMLGetterResultList(rootElement);
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
    public String getText() {
	return tagName;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.INSTRUCTIONaddRootElementTag;
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
