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

import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * html filter instruction<br>
 *
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONhtmlFilter extends htmlFilterOrGetterInstruction {
    
    /**
     * list of filters
     */
    private ArrayList<HTMLFilter> filterList;

    /**
     * similar to {@link #execute(String[], Logger)} but taking a NodeList as input and giving NodeList as output
     * @param source
     * @param logger
     * @return Nodes that match the htmlFilter, one node per String.
     * @throws ParserException
     */
    protected NodeList executeInputNodeListOutputNodeList(NodeList  source, Logger logger) throws ParserException {
	if (source == null)
	    return new NodeList();
	//apply all filters to the nodelist
	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in htmlFilter, applying all filters");
	}
	for (int i = 0;i < filterList.size();i++) {
	    source = applyOneFilterToNodeList(source, filterList.get(i), logger);
	}
	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in htmlFilter, finished applying all filters");
	}
	    
	return source;
    }
    
    
    /**
     * private method
     * @param nodeList
     * @param filter
     * @param logger 
     * @return a nodelist
     */
    private NodeList applyOneFilterToNodeList(NodeList nodeList, HTMLFilter filter, Logger logger) {
	NodeList newNodeList = new NodeList();
	int size;
	
	if (logger != null && logger.getLogLevel().equalsIgnoreCase("debug")) {
	    logger.Log(System.currentTimeMillis() + " : method execute in htmlFilter, applying filter " + ((XMLElement)filter).getTagName());
	}
	
	for (int i = 0; i < nodeList.size(); i++) {
	    size = newNodeList.size();
	    newNodeList.add((new NodeList(nodeList.elementAt(i))).extractAllNodesThatMatch(filter.getHTMLFilter()));
	    if ((size == newNodeList.size()) && 
		    (recursive) && 
		    ((nodeList.elementAt(i).getChildren() == null ? 
			    false :nodeList.elementAt(i).getChildren().size() > 0))
	       )
		newNodeList.add(applyOneFilterToNodeList(nodeList.elementAt(i).getChildren(), filter, logger));
	}
	if (logger != null && logger.getLogLevel().equalsIgnoreCase("debug")) {
	    logger.Log(System.currentTimeMillis() + " : new nodeList has  " + newNodeList.size() + " elements");
	}
	
	return newNodeList;
    }

    /**
     * constructor, initialiazes {@link #filterList}
     */
    public INSTRUCTIONhtmlFilter() {
	filterList = new ArrayList<HTMLFilter>();
    }

    /**
     * calls {@link htmlFilterOrGetterInstruction#addAttributes(Attributes, String)}
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	super.addAttributes(attributes, "htmlFilter");
    }

    /**
     * if child is an HTMLFilter, child is added to the list of filters.<br>
     * if child is not an HTML fitler, an exception is thrown
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (child instanceof HTMLFilter) {
		filterList.add((HTMLFilter)child);
	}
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
     * Executes the list of filters to the source<br>
     * The strings in the source array will be concatenated to one string which is then parsed to a NodeList.<br>
     * Returns Nodes that match the htmlFilter, one node per String.
     * @throws ParserException can be thrown by html Parser
     * @see net.johandegraeve.getcontents.Instruction#execute(String[], Logger)
     */
    @Override
    String[] execute(String[] source, Logger logger) throws ParserException {
	String[] returnvalue;
	NodeList parsedNodeList = null;
	    
	parsedNodeList = executeInputStringArrOutputNodeList(source, logger);
	
	//prepare string array to return
	returnvalue = new String[parsedNodeList.size()];
	for (int i = 0;i < parsedNodeList.size(); i++)
	    returnvalue[i] = parsedNodeList.elementAt(i).toHtml();
	if (logger != null && logger.getLogLevel().equalsIgnoreCase("debug")) {
	    if (returnvalue.length == 0)
		logger.Log("There are no results");
	    else {
		for (int i = 0;i < returnvalue.length;i++) {
		    logger.Log("Result " + i + " =\n" );
		    logger.Log("returnvalue[i]");
		    logger.Log("\n" );
		}
	    }
	}
	
	return returnvalue;
    }
    /**
     * similar to {@link #execute(String[], Logger)} but taking a String Array as input, and giving a NodeList as output<br>
     * This will just call {@link htmlFilterOrGetterInstruction#executeInputStringArrOutputNodeList(String[], Logger, String)} 
     * @param source
     * @param logger
     * @return Nodes that match the tagfilter, one node per String.
     * @throws ParserException
     */
    NodeList executeInputStringArrOutputNodeList( String[]  source, Logger logger) throws ParserException {
	return super.executeInputStringArrOutputNodeList(source, logger, "htmlFilter");
    }

    
}
