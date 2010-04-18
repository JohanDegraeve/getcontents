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

import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * html filter instruction<br>
 * 
 *
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONhtmlFilter extends Instruction implements XMLElement {
    
    /**
     * list of filters
     */
    private ArrayList<HTMLFilter> filterList;
    /**
     * recursive attribute
     */
    private boolean recursive;
    
    /**
     * Executes the list of filters to the source<br>
     * The strings in the source array will be concatenated to one string which is then parsed to a NodeList.<br>
     * Returns Nodes that match the tagfilter.
     * @see net.johandegraeve.getcontents.Instruction#execute(String[])
     */
    @Override
    String[] execute(String[] source) {
	String[] returnvalue;
	NodeList parsedNodeList = null;
	StringBuilder temp;

	//first put the whole source in a NodeList
	temp  = new StringBuilder();
	for (int i = 0;i < source.length; i++)
	    temp.append(source[i]);
	try {
	    parsedNodeList = (new Parser(temp.toString())).parse(null);
	} catch (ParserException e) {
	    e.printStackTrace();
	}
	
	//apply all filters to the nodelist
	for (int i = 0;i < filterList.size();i++) {
	    parsedNodeList = applyOneFilterToNodeList(parsedNodeList, filterList.get(i));
	}
	    
	//prepare string array to return
	returnvalue = new String[parsedNodeList.size()];
	for (int i = 0;i < parsedNodeList.size(); i++)
	    returnvalue[i] = parsedNodeList.elementAt(i).toHtml();
	
	return returnvalue;
    }
    
    /**
     * private method
     * @param nodeList
     * @param filter
     * @return a nodelist
     */
    private NodeList applyOneFilterToNodeList(NodeList nodeList, HTMLFilter filter) {
	NodeList newNodeList = new NodeList();
	int size;
	
	for (int i = 0; i < nodeList.size(); i++) {
	    size = newNodeList.size();
	    newNodeList.add((new NodeList(nodeList.elementAt(i))).extractAllNodesThatMatch(filter.getHTMLFilter()));
	    if ((size == newNodeList.size()) && 
		    (recursive) && 
		    ((nodeList.elementAt(i).getChildren() == null ? 
			    false :nodeList.elementAt(i).getChildren().size() > 0))
	       )
		newNodeList.add(applyOneFilterToNodeList(nodeList.elementAt(i).getChildren(), filter));
	}
	return newNodeList;
    }

    /**
     * constructor, sets recursive attribute to false
     */
    public INSTRUCTIONhtmlFilter() {
	recursive = false;
	filterList = new ArrayList<HTMLFilter>();
    }

    /**
     * adds the recursive attribute, default value = false
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	try {
	    String[] attrValues = Utilities.getOptionalAttributeValues(
	    	attributes, 
	    	new String[] {
	    		TagAndAttributeNames.recursiveAttribute,
	    	}, 
	    	new String[]  {
	    		"false",
	    	});
	    if (attrValues[0].equalsIgnoreCase("true")) 
	        recursive = true;
	    else
	        recursive = false;
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
     * if child is an HTMLFilter, child is added to the list of filters.<br>
     * if child is not an HTML fitler, an exception is thrown
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (child instanceof HTMLFilter)
		filterList.add((HTMLFilter)child);
	else {
	    StringBuilder exceptionString = new StringBuilder();
	    exceptionString.append("Element " + TagAndAttributeNames.INSTRUCTIONhtmlFilterTag +
		    " can only have a htmlfilter as child element.\nAllowed html filters are : \n");
	    for (int i = 0; i < TagAndAttributeNames.htmlfilterTags.length; i ++)
		exceptionString.append(TagAndAttributeNames.htmlfilterTags[i] + "\n");
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
     * throws an exception of the list of filters is empty
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (filterList.size() == 0) {
	    throw new SAXException("Element " + TagAndAttributeNames.INSTRUCTIONhtmlFilterTag +
		    " must have at least one filter as child element");
	}
    }

    /**
     * @return the recursive attribute
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
	return attr;
    }

    /**
     * @return the HTML filters
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
     * @return the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.INSTRUCTIONhtmlFilterTag;
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
