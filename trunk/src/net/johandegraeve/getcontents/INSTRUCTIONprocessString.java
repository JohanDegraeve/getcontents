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

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class INSTRUCTIONprocessString extends Instruction {

    private ArrayList<StringProcessor> processorList;

    @Override
    String[] execute(String[] source) throws Exception {
	if (source == null) return null;
	
	//apply all processors to the list
	for (int i = 0;i < processorList.size();i++) {
	    source = processorList.get(i).processString(source);
	}
	return source;
    }
    
    public INSTRUCTIONprocessString() {
	processorList = new ArrayList<StringProcessor>();
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (child instanceof StringProcessor)
	    processorList.add((StringProcessor)child);
	else {
	    StringBuilder exceptionString = new StringBuilder();
	    exceptionString.append("Element " + TagAndAttributeNames.INSTRUCTIONprocessStringTag +
		    " can only have a StringProcessor as child element.\nAllowed stringprocessors are : \n");
	    for (int i = 0; i < TagAndAttributeNames.stringProcessorTags.length; i ++)
		exceptionString.append(TagAndAttributeNames.stringProcessorTags[i] + "\n");
	    exceptionString.append("But received element is of type " + Utilities.getClassname(child.getClass()) + ". (if it's a DefaultXMLElement then" +
		    " probably your XML is having an element with an unknown tag");
	    throw new SAXException(exceptionString.toString());
	}
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (processorList.size() == 0) {
	    throw new SAXException("Element " + TagAndAttributeNames.INSTRUCTIONprocessStringTag +
		    " must have at least one string processor as child element");
	}
    }

    @Override
    public Attributes getAttributes() {
	return null;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement>();
	for (int i = 0;i < processorList.size();i++)
	    returnvalue.add((XMLElement)processorList.get(i));
	return returnvalue;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.INSTRUCTIONprocessStringTag;
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
