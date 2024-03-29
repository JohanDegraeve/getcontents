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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * the remove redundant invisible Characters like carriage returns, line feeds, tabs, ...<br>
 * Maximum number of consecutive line feeds can be defined in an attribute, default value = 2, minimum value = 0. If 0 
 * then every line feed (or series of consecutive linefeeds) will be replaced by one blanc<br>
 *
 */
public class STRING_PROCESSORremoveInvisibleChars implements XMLElement, StringProcessor {
    
    /**
     * Maximum allowed number of consecutive linefeeds. 
     */
    private int maxNrOfNewLinesToKeep;
    
    /**
     * default constructor
     */
    public STRING_PROCESSORremoveInvisibleChars() {
	maxNrOfNewLinesToKeep = 2;
    }

    /**
     * keeps no more than two subsequent newlines and one space<br>
     * @return the result off course
     * @see net.johandegraeve.getcontents.StringProcessor#processString(java.lang.String[])
     */
    public String[] processString(String[] source) throws IOException {
	if (source == null) return null;
	ArrayList<String> returnvalue = new ArrayList<String> () ;
	StringBuilder helper;
	BufferedReader reader;
	String readline;
	int newlineCounter = 0;
	boolean itsTheFirstLine = true;
	String[] splitline;
	int returnvaluelength, stringlength;
	
	for (int i = 0;i < source.length; i++) {
	    helper = new StringBuilder();
	    reader = new BufferedReader(new StringReader(source[i]));
	    while ((readline = reader.readLine()) != null) {//read line by line
		readline = readline.trim();
		if (readline.length() > 0) {//it's a line having something else than just carriage return.
		    splitline = readline.split(" ");
		    for (int j = 0;j < splitline.length - 1;j++) {
			if (splitline[j].trim().length() > 0)
			    helper.append(splitline[j].trim() + " ");
		    }
		    helper.append(splitline[splitline.length - 1].trim() + (maxNrOfNewLinesToKeep == 0 ? " ":"\n"));
		    newlineCounter = 0;
		    itsTheFirstLine = false;
		} else {
		    if (!itsTheFirstLine) {
			newlineCounter++;
			if (newlineCounter < maxNrOfNewLinesToKeep) {
			    helper.append("\n");
			}
		    }
		}
	    }
	    if (helper.length() > 0) {
		returnvalue.add(helper.toString());
	    }
	}
	//remove the last newline from the last line.
	if ((returnvaluelength = returnvalue.size()) > 0)
	    if ((stringlength = returnvalue.get(returnvaluelength - 1).length()) > 0)
		if (returnvalue.get(returnvaluelength - 1).substring(stringlength - 1, stringlength - 1).equalsIgnoreCase("\n"))
		    returnvalue.set(returnvaluelength - 1, returnvalue.get(returnvaluelength - 1).substring(0, stringlength - 1));
		
	return returnvalue.toArray(new String[0]);
	
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	    String[] attrValues = Utilities.getOptionalAttributeValues(
		    attributes, 
		    new String[] {
			    TagAndAttributeNames.maxNewLinesAttribute
		    }, 
		    new String[]  {
			    "2"
		    });
	    try {
		maxNrOfNewLinesToKeep = Integer.parseInt(attrValues[0]);
		if (maxNrOfNewLinesToKeep < 0)
		    throw new NumberFormatException();
	    } catch (NumberFormatException e) {
		throw new SAXException("Attribute " + TagAndAttributeNames.maxNewLinesAttribute + " must be integer value larger than or equal to 0");
	    }
    }

    /**
     * throws an Exception
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No children allowed for element of type " + TagAndAttributeNames.STRING_PROCESSORremoveInvisibleCharsTag);
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
     * @return {@link #maxNrOfNewLinesToKeep} in an attribute
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.maxNewLinesAttribute, TagAndAttributeNames.maxNewLinesAttribute, "CDATA", Integer.toString(maxNrOfNewLinesToKeep));
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
     * @return the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.STRING_PROCESSORremoveInvisibleCharsTag;
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
     * @return false
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	return false;
    }
}
