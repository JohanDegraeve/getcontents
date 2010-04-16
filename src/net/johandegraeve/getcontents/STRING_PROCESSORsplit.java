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
import org.xml.sax.helpers.AttributesImpl;

import com.Ostermiller.util.StringHelper;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/*
 * use String.split or StringHelper.split ?
 * can have idSelector as child to indicate which of the split results needs to be included or excluded
 *
 * @author Johan Degraeve
 *
 */
public class STRING_PROCESSORsplit implements StringProcessor , XMLElement {

    private String delimiter;
    
    private STRING_PROCESSORidSelector idSelector;
    
    private static final String [][] replacementStrings = new String[][] {
	{"\\t","\t"},
	{"\\b","\b"},
	{"\\n","\n"},
	{"\\r","\r"},
	{"\\f","\f"},
	{"\\'","\'"},
	{"\\\"","\""},
	{"\\\\","\\"}
    };
    
    public STRING_PROCESSORsplit() {
	delimiter = " ";
	idSelector = null;
    }

    @Override
    public String[] processString(String[] source) {
	ArrayList<String> stringArrayList = new ArrayList<String>(); 
	String[] returnvalue = null;
	for (int i = 0; i < source.length; i ++) {
	    returnvalue = source[i].split(delimiter);
	    if (idSelector != null) {
		returnvalue = idSelector.processString(returnvalue);
	    }
	    for (int j = 0; j < returnvalue.length;j++) {
		for (int k = 0; k < replacementStrings.length;k++)
		    StringHelper.replace(returnvalue[j], replacementStrings[k][0], replacementStrings[k][1]);
		stringArrayList.add(returnvalue[j]);
	    }	
	}
	returnvalue = (String[]) stringArrayList.toArray(returnvalue);
	return returnvalue;
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	delimiter = Utilities.getOptionalAttributeValues(attributes, new String[] {TagAndAttributeNames.delimiterAttribute}, new String[] {" "})[0];
	
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child, 
		TagAndAttributeNames.STRING_PROCESSORPrefix,
		new String[]  {TagAndAttributeNames.STRING_PROCESSORidSelectorTag},
		TagAndAttributeNames.STRING_PROCESSORsplitTag);
	idSelector = (STRING_PROCESSORidSelector) child;
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
    }

    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.delimiterAttribute, TagAndAttributeNames.delimiterAttribute, "CDATA", delimiter);
	return attr;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	return Utilities.createXMLElementList(idSelector);
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.STRING_PROCESSORsplitTag;
    }

    @Override
    public String getText() {
	return null;
    }

    @Override
    public boolean preserveSpaces() {
	return false;
    }

}
