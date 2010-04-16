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

import net.johandegraeve.easyxmldata.XMLElement;

public class GENERICfind implements XMLElement {

    private String find;
    
    public String getFind() {
	return find;
    }
    
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GENERICfindTag);
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (find == null)
	    throw new SAXException("Element of type " + TagAndAttributeNames.GENERICfindTag + " must have text");
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
	return TagAndAttributeNames.GENERICfindTag;
    }

    @Override
    public String getText() {
	return find;
    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
	find = text;
    }

    @Override
    public boolean preserveSpaces() {
	return true;
    }

}
