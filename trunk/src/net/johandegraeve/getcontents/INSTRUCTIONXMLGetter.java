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
 * XMLGetter Instruction
 * 
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONXMLGetter extends Instruction implements XMLElement {

    /**
     * list of XMLGetter
     */
    private ArrayList<XMLGetter> getterList;

    /**
     * the attribute charsetname
     */
    private String charsetName;

    
    /**
     * constructor, initializes {@link #getterList}
     */
    public INSTRUCTIONXMLGetter() {
	getterList = new  ArrayList<XMLGetter>();
    }

    /**
     * run the instructions on the source, one by one<br>
     * 
     * @see net.johandegraeve.getcontents.Instruction#execute(java.lang.String[], Logger)
     */
    @Override
    String[] execute(String[] source, Logger thelogger) throws Exception {
	String[] returnvalue;
	XMLXMLGetterResultList startList = null;
	GenericXMLGetterResultList resultList = null;
	
	if (thelogger != null) {
	    thelogger.Log(System.currentTimeMillis() + " : method execute in XMLGetter, applying XML parser" );
	}
	startList = new XMLXMLGetterResultList(net.johandegraeve.getcontents.Utilities.makeList(source,charsetName));
	
	//start by applying the first getter to the nodeLIst
	resultList = getterList.get(0).getList(startList);
	
	//apply all getters to the nodelist
	if (thelogger != null) {
	    thelogger.Log(System.currentTimeMillis() + " : method execute in XMLGetter, applying all filters");
	}
	for (int i = 1;i < getterList.size();i++) {
	    if (thelogger != null && thelogger.getLogLevel().equalsIgnoreCase("debug")) {
		thelogger.Log(System.currentTimeMillis() + " : method execute in XMLGetter, applying  " + ((XMLElement)getterList.get(i)).getTagName());
	    }
	    resultList = getterList.get(i).getList(resultList);
	}
	    
	//prepare string array to return
	if (resultList != null) {
	    returnvalue = new String[resultList.size()];
	    if (thelogger != null) {
		thelogger.Log(System.currentTimeMillis() + " : result has  " + returnvalue.length + " elements");
	    }
	}
	else {
	    if (thelogger != null) {
		thelogger.Log(System.currentTimeMillis() + " : result has  0 elements");
	    }
	    return new String[0];
	}
	for (int i = 0;i < resultList.size(); i++)
	    returnvalue[i] = resultList.elementAt(i).convertToString();
	
	if (thelogger != null && thelogger.getLogLevel().equalsIgnoreCase("debug")) {
	    if (returnvalue.length == 0)
		thelogger.Log("There are no results");
	    else {
		for (int i = 0;i < returnvalue.length;i++) {
		    thelogger.Log("Result " + i + " =\n" );
		    thelogger.Log("returnvalue[i]");
		    thelogger.Log("\n" );
		}
	    }
	}

	return returnvalue;
    }

    /**
     * gets attribute {@link #charsetName}
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	    String[] attrValues = Utilities.getOptionalAttributeValues(
		    	attributes, 
		    	new String[] {
		    		TagAndAttributeNames.charsetnameAttribute
		    	}, 
		    	new String[]  {
		    		"ISO-8859-1"
		    	});
		    charsetName = attrValues[0];
    }

    /**
     * 
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (child instanceof  GETorFILTERchildren || child instanceof GETorFILTERremoveNodes) {
	    if (getterList.size() > 0 && (getterList.get(getterList.size() - 1) instanceof GETorFILTERtext))
		throw new SAXException("An instruction of type gettext can not be followed by an instruction" +
			" of type getchildren");
	}
	if (child instanceof XMLGetter)
		getterList.add((XMLGetter)child);
	else {
	    StringBuilder exceptionString = new StringBuilder();
	    exceptionString.append("Element " + TagAndAttributeNames.INSTRUCTIONXMLGetterTag +
		    " can only have a xmlgetter as child element.\nAllowed xmlgetters are : \n");
	    for (int i = 0; i < TagAndAttributeNames.XMLgetterTags.length; i ++)
		exceptionString.append(TagAndAttributeNames.XMLgetterTags[i] + "\n");
	    exceptionString.append("But received element is of type " + Utilities.getClassname(child.getClass()) + ". (if it's a DefaultXMLElement then" +
	    " probably your XML is having an element with an unknown tag");
	    throw new SAXException(exceptionString.toString());
	}
    }

    /** does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
    }

    /**
     * throws an exception if {@link #getterList} has size 0
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (getterList.size() == 0) {
	    throw new SAXException("Element " + TagAndAttributeNames.INSTRUCTIONXMLGetterTag +
		    " must have at least one getter as child element");
	}
    }

    /**
     * @return attribute {@link #charsetName} in an Attribute
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.charsetnameAttribute, TagAndAttributeNames.charsetnameAttribute, "CDATA", charsetName);
	return attr;
    }

    /**
     * @return the list of XML Getter in an ArrayList
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement>();
	for (int i = 0;i < getterList.size();i++)
	    returnvalue.add((XMLElement)getterList.get(i));
	return returnvalue;
    }

    /**
     * @return the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.INSTRUCTIONXMLGetterTag;
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
