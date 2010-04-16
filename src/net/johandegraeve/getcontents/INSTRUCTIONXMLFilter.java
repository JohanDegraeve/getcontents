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
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.AttributesImpl;

import net.johandegraeve.easyxmldata.DefaultXMLElement;
import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/*
 * A filterinstruction can have many filters.
 * recursive attribute will apply  to all filters
 * SAXParseException thrown by the parser, are not thrown back by the execute. The XML must be valid.
 *
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONXMLFilter extends Instruction implements XMLElement {
    
    private ArrayList<XMLFilter> filterList;
    private boolean recursive;
    private String charsetName;
    
    @Override
    String[] execute(String[] source) throws Exception {
	String[] returnvalue;
	ArrayList<XMLElement> parsedList = null;
	DefaultXMLElement root = null;

	parsedList = net.johandegraeve.getcontents.Utilities.makeList(source,charsetName);
	
	//apply all filters to the nodelist
	for (int i = 0;i < filterList.size();i++) {
	    parsedList = applyOneFilterToNodeList(parsedList, filterList.get(i));
	}
	    
	//prepare string array to return
	returnvalue = new String[parsedList.size()];
	for (int i = 0;i < parsedList.size(); i++)
	    returnvalue[i] = net.johandegraeve.easyxmldata.Utilities.createXML(parsedList.get(i));
	
	return returnvalue;
    }
    
    private ArrayList<XMLElement> applyOneFilterToNodeList(ArrayList<XMLElement> nodeList, XMLFilter filter) {
	ArrayList<XMLElement> newNodeList = new ArrayList<XMLElement>();
	int size;
	boolean accepted;
	
	for (int i = 0; i < nodeList.size(); i++) {
	    size = newNodeList.size();
	    accepted = filter.getXMLFilter().accept(nodeList.get(i));
	    if (accepted)
		newNodeList.add(nodeList.get(i));
	    if ((size == newNodeList.size()) && 
		    (recursive) && 
		    ((nodeList.get(i).getChildren() == null ? 
			    false :nodeList.get(i).getChildren().size() > 0))
	       )
		addLists((applyOneFilterToNodeList(nodeList.get(i).getChildren(), filter)),newNodeList);
	}
	return newNodeList;
    }
    
    private void addLists(ArrayList<XMLElement> source, ArrayList<XMLElement> toAddto) {
	for (int i = 0;i < source.size();i++)
	    toAddto.add(source.get(i));
    }

    public INSTRUCTIONXMLFilter() {
	filterList = null;
	recursive = false;
	filterList = new ArrayList<XMLFilter>();
	charsetName = "ISO-8859-1";
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	try {
	    String[] attrValues = Utilities.getOptionalAttributeValues(
	    	attributes, 
	    	new String[] {
	    		TagAndAttributeNames.recursiveAttribute,
	    		TagAndAttributeNames.charsetnameAttribute
	    	}, 
	    	new String[]  {
	    		"false",
	    		"ISO-8859-1"
	    	});
	    if (attrValues[0].equalsIgnoreCase("true")) 
	        recursive = true;
	    else
	        recursive = false;
	    charsetName = attrValues[1];
	} catch (Exception e) {
	    StringBuilder exceptionString = new StringBuilder();
	    exceptionString.append(e.toString() + "\n");
	    StackTraceElement[] traceElement = e.getStackTrace();
	    for (int i = 0;i < traceElement.length; i++) {
		exceptionString.append("FileName = " + traceElement[i].getFileName()+ "\n");
		exceptionString.append("LineNumber = " + traceElement[i].getLineNumber()+ "\n");
		exceptionString.append("MethodName = " + traceElement[i].getMethodName()+ "\n"+ "\n");
	    }
	    throw new SAXException(exceptionString.toString());
	}
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (child instanceof XMLFilter)
		filterList.add((XMLFilter)child);
	else {
	    StringBuilder exceptionString = new StringBuilder();
	    exceptionString.append("Element " + TagAndAttributeNames.INSTRUCTIONXMLFilterTag +
		    " can only have a xmlfilter as child element.\nAllowed xmlfilters are : \n");
	    for (int i = 0; i < TagAndAttributeNames.XMLfilterTags.length; i ++)
		exceptionString.append(TagAndAttributeNames.XMLfilterTags[i] + "\n");
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
	if (filterList.size() == 0) {
	    throw new SAXException("Element " + TagAndAttributeNames.INSTRUCTIONXMLFilterTag +
		    " must have at least one filter as child element");
	}
    }

    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, 
		TagAndAttributeNames.recursiveAttribute, 
		TagAndAttributeNames.recursiveAttribute, 
		"CDATA", 
		(recursive ? "true":"false"));
	attr.addAttribute(null, TagAndAttributeNames.charsetnameAttribute, TagAndAttributeNames.charsetnameAttribute, "CDATA", charsetName);
	return attr;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement>();
	for (int i = 0;i < filterList.size();i++)
	    returnvalue.add((XMLElement)filterList.get(i));
	return returnvalue;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.INSTRUCTIONXMLFilterTag;
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
