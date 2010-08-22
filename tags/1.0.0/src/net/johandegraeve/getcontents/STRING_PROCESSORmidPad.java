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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.Ostermiller.util.StringHelper;


/**
 * Pad the beginning and end of the given String with spaces until the String is of the given length.<br>
 * Uses {@link com.Ostermiller.util.StringHelper#midpad(String, int, char)} 
 *
 * @author Johan Degraeve
 *
 */
public class STRING_PROCESSORmidPad implements XMLElement,
	StringProcessor {

    /**
     * length to be achieved by adding character {@link #c}
     */
    private int length;
    /**
     * character to add at beginning and end of string until string has size {@link #length}
     */
    private char c;
    
    /**
     * constructor assigning {@link #c} to one blank space
     */
    public STRING_PROCESSORmidPad() {
	length=0;
	c=' ';
    }
    
    /**
     * requires length and character as mandatory attributes
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	try {
	    length = Integer.parseInt(Utilities.getMandatoryAttributeValues(this, attributes, new String[] {TagAndAttributeNames.lengthAttribute})[0]);
	} catch (NumberFormatException e) {
	    throw new SAXException("Element " + TagAndAttributeNames.STRING_PROCESSORmidPadTag + " has an invalid length. Value should be integer greater than zero");
	}
	if (length < 1)
	    throw new SAXException("Element " + TagAndAttributeNames.STRING_PROCESSORmidPadTag +  " has an invalid length. Value should be integer greater than zero");
	
	String tempC = Utilities.getOptionalAttributeValues(attributes, new String[] {TagAndAttributeNames.characterAttribute}, new String[] {" "})[0];
	c = net.johandegraeve.getcontents.Utilities.createCharFromString(tempC);
    }

    /**
     * throws an exception
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.STRING_PROCESSORmidPadTag);
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
     * @return {@link #c} and {@link #length} in an attribute
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.lengthAttribute, TagAndAttributeNames.lengthAttribute, "CDATA", Integer.toString(length));
	attr.addAttribute(null, TagAndAttributeNames.characterAttribute, TagAndAttributeNames.characterAttribute, "CDATA", Character.toString(c));
	return attr;
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
     * @return {@link TagAndAttributeNames#STRING_PROCESSORmidPadTag}
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.STRING_PROCESSORmidPadTag;
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
     * @return an array of string with source strings midPadded
     * @see net.johandegraeve.getcontents.StringProcessor#processString(java.lang.String[])
     */
    @Override
    public String[] processString(String[] source) {
	for (int i = 0; i < source.length; i ++)
	    source[i] = StringHelper.midpad(source[i],length,c);
	return source;
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
