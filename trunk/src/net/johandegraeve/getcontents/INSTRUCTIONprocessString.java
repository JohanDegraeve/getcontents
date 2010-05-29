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

/**
 * to process Strings. 
 * For most of the string processors, I've been using a package called com.Ostermiller.util, class StringHelper.
 * 
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONprocessString extends Instruction {

    /**
     * the list of string processors
     */
    private ArrayList<StringProcessor> processorList;

    /**
     * to execute the list of string processors
     * @see net.johandegraeve.getcontents.Instruction#execute(java.lang.String[])
     */
    @Override
    String[] execute(String[] source)  {
	if (source == null) return null;
	
	//apply all processors to the list
	for (int i = 0;i < processorList.size();i++) {
	    source = processorList.get(i).processString(source);
	}
	return source;
    }
    
    /**
     * constructor
     */
    public INSTRUCTIONprocessString() {
	processorList = new ArrayList<StringProcessor>();
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    /**
     * if child is a StringProcessor, then it's added to the list, otherwise an Exceptio is thrown
     * 
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (child instanceof StringProcessor)
	    processorList.add((StringProcessor)child);
	else {
	    StringBuilder exceptionString = new StringBuilder();
	    exceptionString.append("Element " + Utilities.getClassname(this.getClass())  +
		    " can only have a StringProcessor as child element.\nAllowed stringprocessors are : \n");
	    for (int i = 0; i < TagAndAttributeNames.stringProcessorTags.length; i ++)
		exceptionString.append(TagAndAttributeNames.stringProcessorTags[i] + "\n");
	    exceptionString.append("But received element is of type " + Utilities.getClassname(child.getClass()) + ". (if it's a DefaultXMLElement then" +
		    " probably your XML is having an element with an unknown tag");
	    throw new SAXException(exceptionString.toString());
	}
    }

    /**
     * none
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
    }

    /**
     * throws an exception if processorList has size 0
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (processorList.size() == 0) {
	    throw new SAXException("Element " + TagAndAttributeNames.INSTRUCTIONprocessStringTag +
		    " must have at least one string processor as child element");
	}
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
     * @return the list of StringProcessor in an ArrayList
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement>();
	for (int i = 0;i < processorList.size();i++)
	    returnvalue.add((XMLElement)processorList.get(i));
	return returnvalue;
    }

    /**
     * @return the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.INSTRUCTIONprocessStringTag;
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
     * @return false;
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	return false;
    }
}
