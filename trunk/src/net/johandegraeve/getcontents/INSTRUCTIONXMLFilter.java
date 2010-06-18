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
 * XML Filter instruction<br>
 *
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONXMLFilter extends Instruction implements XMLElement {
    
    /**
     * the XML filters
     */
    private ArrayList<XMLFilter> filterList;
    /**
     * recursive attribute
     */
    private boolean recursive;
    /**
     * charsetName, only useful in case the source is a String, which is read via String.getBytes(charsetName)
     */
    private String charsetName;
    
    /**
     * for logging
     */
    private Logger thelogger;
    
    /**
     * run the instructions on the source, one by one<br>
     * 
     * @see net.johandegraeve.getcontents.Instruction#execute(java.lang.String[], Logger)
     */
    @Override
    String[] execute(String[] source, Logger logger) throws Exception {
	String[] returnvalue;
	ArrayList<XMLElement> parsedList = null;
	thelogger = logger;

	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in XMLFilter, applying XML Parser to the source");
	}
	parsedList = net.johandegraeve.getcontents.Utilities.makeList(source,charsetName);
	
	//apply all filters to the nodelist
	if (thelogger != null) {
	    thelogger.Log(System.currentTimeMillis() + " : method execute in XMLFilter, applying all filters");
	}
	for (int i = 0;i < filterList.size();i++) {
	    parsedList = applyOneFilterToNodeList(parsedList, filterList.get(i));
	}
	    
	//prepare string array to return
	returnvalue = new String[parsedList.size()];
	for (int i = 0;i < parsedList.size(); i++)
	    returnvalue[i] = net.johandegraeve.easyxmldata.Utilities.createXML(parsedList.get(i));
	
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
     * Filters the nodes, if {@link #recursive}, then filtering continues on each branch until know more children or until 
     * at least one node is found that matches the filter
     * @param nodeList list of XMLElements
     * @param filter the filter to be applied to the nodeList
     * @return a new list, after filtering
     */
    private ArrayList<XMLElement> applyOneFilterToNodeList(ArrayList<XMLElement> nodeList, XMLFilter filter) {
	ArrayList<XMLElement> newNodeList = new ArrayList<XMLElement>();
	int size;
	boolean accepted;
	
	if (thelogger != null && thelogger.getLogLevel().equalsIgnoreCase("debug")) {
	    thelogger.Log(System.currentTimeMillis() + " : method execute in XMLFilter, applying filter " + ((XMLElement)filter).getTagName());
	}
	
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

	if (thelogger != null && thelogger.getLogLevel().equalsIgnoreCase("debug")) {
	    thelogger.Log(System.currentTimeMillis() + " : new nodeList has  " + newNodeList.size() + " elements");
	}
	return newNodeList;
    }
    
    /**
     * helper function to add an XMLElement to an ArrayList of XMLELement
     * @param source the XMLElement to add
     * @param toAddto the ArrayList to which the source must be added
     */
    private void addLists(ArrayList<XMLElement> source, ArrayList<XMLElement> toAddto) {
	for (int i = 0;i < source.size();i++)
	    toAddto.add(source.get(i));
    }

    /**
     * constructor, setting {@link #recursive} to false, and {@link #charsetName} to ISO-8859-1
     */
    public INSTRUCTIONXMLFilter() {
	filterList = null;
	recursive = false;
	filterList = new ArrayList<XMLFilter>();
	charsetName = "ISO-8859-1";
    }

    /**
     * reads attributes {@link #recursive} and {@link #charsetName}, default values false and ISO-8859-1
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
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

    /**
     * if child is an XMLFilter, then it's added to the {@link #filterList}, otherwise an Exception is thrown
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
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

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
    }

    /**
     * throws an exception if there's no XML Filters in the {@link #filterList}
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (filterList.size() == 0) {
	    throw new SAXException("Element " + TagAndAttributeNames.INSTRUCTIONXMLFilterTag +
		    " must have at least one filter as child element");
	}
    }

    /**
     * @return the attributes {@link #recursive} and {@link #charsetName} in an {@link AttributesImpl}
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
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

    /**
     * @return the {@link #filterList} in an ArrayList
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement>();
	for (int i = 0;i < filterList.size();i++)
	    returnvalue.add((XMLElement)filterList.get(i));
	return returnvalue;
    }

    /**
     * @return {@link TagAndAttributeNames#INSTRUCTIONXMLFilterTag}
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.INSTRUCTIONXMLFilterTag;
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
