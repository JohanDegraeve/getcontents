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

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

public class STRING_PROCESSORhtml2text  implements XMLElement, StringProcessor {

    public String[] processString(String[] source) {
	if (source == null) return null;
	String[] returnvalue = new String[source.length];
	for (int i = 0; i < returnvalue.length;i++) {
	    returnvalue[i] = source[i].replaceAll("\\<.*?>","");
	}
	return returnvalue;
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.STRING_PROCESSORhtml2textTag);
    }

    @Override
    public void addText(String text) throws SAXException {
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
	return TagAndAttributeNames.STRING_PROCESSORhtml2textTag;
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
