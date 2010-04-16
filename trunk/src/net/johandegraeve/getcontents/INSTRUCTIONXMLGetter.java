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

/*
 * Input to execute must be valid xml.
 * recursive attribute does  not exist here 
 * 
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONXMLGetter extends Instruction implements XMLElement {

    private ArrayList<XMLGetter> getterList;
    private String charsetName;

    
    public INSTRUCTIONXMLGetter() {
	getterList = new  ArrayList<XMLGetter>();
    }

    @Override
    String[] execute(String[] source) throws Exception {
	String[] returnvalue;
	XMLXMLGetterResultList startList = null;
	GenericXMLGetterResultList resultList = null;
	
	startList = new XMLXMLGetterResultList(net.johandegraeve.getcontents.Utilities.makeList(source,charsetName));
	
	//start by applying the first getter to the nodeLIst
	resultList = getterList.get(0).getList(startList);
	
	//apply all getters to the nodelist
	for (int i = 1;i < getterList.size();i++) {
	    resultList = getterList.get(i).getList(resultList);
	}
	    
	//prepare string array to return
	returnvalue = new String[resultList.size()];
	for (int i = 0;i < resultList.size(); i++)
	    returnvalue[i] = resultList.elementAt(i).convertToString();
	
	return returnvalue;
    }

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

    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (child instanceof  GETorFILTERchildren) {
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

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (getterList.size() == 0) {
	    throw new SAXException("Element " + TagAndAttributeNames.INSTRUCTIONXMLGetterTag +
		    " must have at least one getter as child element");
	}
    }

    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.charsetnameAttribute, TagAndAttributeNames.charsetnameAttribute, "CDATA", charsetName);
	return attr;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement>();
	for (int i = 0;i < getterList.size();i++)
	    returnvalue.add((XMLElement)getterList.get(i));
	return returnvalue;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.INSTRUCTIONXMLGetterTag;
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
