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

/**
 * use String.split<br>
 * can have idSelector as child to indicate which of the split results needs to be included or excluded
 *
 * @author Johan Degraeve
 *
 */
public class STRING_PROCESSORsplit implements StringProcessor , XMLElement {

    /**
     * delimiter to be used, 
     */
    private String delimiter;
    
    /**
     * idSelector
     */
    private STRING_PROCESSORidSelector idSelector;
    
    /**
     * constructor setting {@link #delimiter} to blank space, {@link #idSelector} to null
     */
    /*private static final String [][] replacementStrings = new String[][] {
	{"\\t","\t"},
	{"\\b","\b"},
	{"\\n","\n"},
	{"\\r","\r"},
	{"\\f","\f"},
	{"\\'","\'"},
	{"\\\"","\""},
	{"\\\\","\\"}
    };*/
    
    public STRING_PROCESSORsplit() {
	delimiter = " ";
	idSelector = null;
    }

    /**
     * @return each string in source is split using {@link #delimiter}, if {@link #idSelector} is not null then idSelector is
     * applied, each result of split is added to returnvalue
     * @see net.johandegraeve.getcontents.StringProcessor#processString(java.lang.String[])
     */
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
	    //I DONT REMEMBER WHY I DID THIS///////
		//for (int k = 0; k < replacementStrings.length;k++)
		  //StringHelper.replace(returnvalue[j], replacementStrings[k][0], replacementStrings[k][1]);
	    ////////////////////////////////////////
		
		stringArrayList.add(returnvalue[j]);
		
	    }	
	}
	returnvalue = (String[]) stringArrayList.toArray(returnvalue);
	return returnvalue;
    }

    /**
     * gets attribute delimiter, if not present then default value blank space is assigned
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	delimiter = Utilities.getOptionalAttributeValues(attributes, new String[] {TagAndAttributeNames.delimiterAttribute}, new String[] {" "})[0];
	
    }

    /**
     * if child is an idSelector, then child is assigned to {@link #idSelector}, otherwise exception is thrown
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child, 
		TagAndAttributeNames.STRING_PROCESSORPrefix,
		new String[]  {TagAndAttributeNames.STRING_PROCESSORidSelectorTag},
		TagAndAttributeNames.STRING_PROCESSORsplitTag);
	idSelector = (STRING_PROCESSORidSelector) child;
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
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
    }

    /**
     * @return {@link #delimiter} in an attribute
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.delimiterAttribute, TagAndAttributeNames.delimiterAttribute, "CDATA", delimiter);
	return attr;
    }

    /**
     * @return {@link #idSelector} in an arraylist
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	return Utilities.createXMLElementList(idSelector);
    }

    /**
     * @return {@link TagAndAttributeNames#STRING_PROCESSORsplitTag}
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.STRING_PROCESSORsplitTag;
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
     * @return false
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	return false;
    }

}
