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

public class GENERICdescription implements XMLElement {
    private String description;
    
    public GENERICdescription() {
	description = null;
    }

    @Override
    public void addAttributes(Attributes arg0) throws SAXException {
    }

    @Override
    public void addChild(XMLElement arg0) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GENERICattributenameTag);
    }


    @Override
    public void addText(String arg0) throws SAXException {
	this.description = arg0;
    }

    @Override
    public void complete() throws SAXException {
	if ((description == null) || (description.length() == 0) )
		throw new SAXException(
		    "Element " + 
		    TagAndAttributeNames.GENERICdescriptionTag +
		    " must have text");
    }

    String getDescription() {
	return description;
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
	return TagAndAttributeNames.GENERICdescriptionTag;
    }

    @Override
    public String getText() {
	return description;
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
