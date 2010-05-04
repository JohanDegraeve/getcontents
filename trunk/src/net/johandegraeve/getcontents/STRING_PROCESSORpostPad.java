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
 * ppend the given character to the String until the result is the desired length. 
 * If a String is longer than the desired length, it will not be truncated, however no padding will be added.<br>
 * Uses {@link com.Ostermiller.util.StringHelper#postpad(String, int, char)} 
 *
 * @author Johan Degraeve
 *
 */
public class STRING_PROCESSORpostPad implements XMLElement,
	StringProcessor {

    /**
     * minimum length that new strings should have
     */
    private int length;
    /**
     * character to pad
     */
    private char c;
    
    /**
     * constructor, setting {@link #c} to one blank space and {@link #length} to 0
     */
    public STRING_PROCESSORpostPad() {
	length=0;
	c=' ';
    }
    
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	try {
	    length = Integer.parseInt(Utilities.getMandatoryAttributeValues(this, attributes, new String[] {TagAndAttributeNames.lengthAttribute})[0]);
	} catch (NumberFormatException e) {
	    throw new SAXException("Element " + TagAndAttributeNames.STRING_PROCESSORpostPadTag + " has an invalid length. Value should be integer greater than zero");
	}
	if (length < 1)
	    throw new SAXException("Element " + TagAndAttributeNames.STRING_PROCESSORpostPadTag +  " has an invalid length. Value should be integer greater than zero");
	
	String tempC = Utilities.getOptionalAttributeValues(attributes, new String[] {TagAndAttributeNames.characterAttribute}, new String[] {" "})[0];
	c = net.johandegraeve.getcontents.Utilities.createCharFromString(tempC);
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.STRING_PROCESSORpostPadTag);
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
    }

    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.lengthAttribute, TagAndAttributeNames.lengthAttribute, "CDATA", Integer.toString(length));
	attr.addAttribute(null, TagAndAttributeNames.characterAttribute, TagAndAttributeNames.characterAttribute, "CDATA", Character.toString(c));
	return attr;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	return null;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.STRING_PROCESSORpostPadTag;
    }

    @Override
    public String getText() {
	return null;
    }

	    @Override
	    public String[] processString(String[] source) {
		for (int i = 0; i < source.length; i ++)
		    source[i] = StringHelper.postpad(source[i],length,c);
		return source;
	    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    @Override
    public boolean preserveSpaces() {
	return false;
    }

}
