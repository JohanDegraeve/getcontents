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

import java.text.DateFormatSymbols;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.Ostermiller.util.StringHelper;

import net.johandegraeve.easyxmldata.XMLElement;

public class GENERICAmPmStrings implements XMLElement {
    
    String[] AmPmStrings;
    
    String[] getAmPmStrings() {
	return AmPmStrings;
    }
    
    public GENERICAmPmStrings() {
	AmPmStrings = new DateFormatSymbols().getAmPmStrings();
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {	
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GENERICAmPmStringsTag);
    }

    @Override
    public void addText(String text) throws SAXException {
	AmPmStrings = StringHelper.split(text, ",");
	if (AmPmStrings.length != 2)
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICAmPmStringsTag + " must have text with am string and pm string separated" + 
		    "by comma. Example : \"AM,PM\"");
	AmPmStrings[0] = AmPmStrings[0].trim();
	AmPmStrings[1] = AmPmStrings[1].trim();
    }
    
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
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
	return TagAndAttributeNames.GENERICAmPmStringsTag;
    }

    @Override
    public String getText() {
	return AmPmStrings[0] + "," + AmPmStrings[1];
    }

    @Override
    public boolean preserveSpaces() {
	return false;
    }

}
