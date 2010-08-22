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

import org.htmlparser.util.ParserException;
import org.xml.sax.SAXException;

/**
 * XML Filter instruction<br>
 *
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONXMLFilter extends XMLFilterOrGetterInstruction {
    
    /**
     * the XML filters
     */
    private ArrayList<XMLFilter> filterList;
    
    /**
     * run the instructions on the source, one by one<br>
     * 
     * @see net.johandegraeve.getcontents.Instruction#execute(java.lang.String[], Logger)
     */
    @Override
    String[] execute(String[] source, Logger logger) throws Exception {
	String[] returnvalue;
	ArrayList<XMLElement> parsedList = null;

	parsedList = executeInputStringArrOutputXMLElementList(source,logger);
	    
	//prepare string array to return
	returnvalue = new String[parsedList.size()];
	for (int i = 0;i < parsedList.size(); i++)
	    returnvalue[i] = net.johandegraeve.easyxmldata.Utilities.createXML(parsedList.get(i));
	
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
     * Filters the nodes, if {@link #recursive}, then filtering continues on each branch until know more children or until 
     * at least one node is found that matches the filter
     * @param nodeList list of XMLElements
     * @param filter the filter to be applied to the nodeList
     * @param logger the logger
     * @return a new list, after filtering
     */
    private ArrayList<XMLElement> applyOneFilterToNodeList(ArrayList<XMLElement> nodeList, XMLFilter filter, Logger logger) {
	ArrayList<XMLElement> newNodeList = new ArrayList<XMLElement>();
	int size;
	boolean accepted;
	
	if (logger != null && logger.getLogLevel().equalsIgnoreCase("debug")) {
	    logger.Log(System.currentTimeMillis() + " : method execute in XMLFilter, applying filter " + ((XMLElement)filter).getTagName());
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
		addLists((applyOneFilterToNodeList(nodeList.get(i).getChildren(), filter, logger)),newNodeList);
	}

	if (logger != null && logger.getLogLevel().equalsIgnoreCase("debug")) {
	    logger.Log(System.currentTimeMillis() + " : new nodeList has  " + newNodeList.size() + " elements");
	}
	return newNodeList;
    }
    
    /**
     * helper function to add an XMLElement to an ArrayList of XMLELement
     * @param source the XMLElement to add
     * @param toAddto the ArrayList to which the source must be added
     */
    private void addLists(ArrayList<XMLElement> source, ArrayList<XMLElement> toAddto) {
	if (source == null)
	    return;
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
     * similar to {@link #execute(String[], Logger)} but taking an ArrayList of XMLElement as input and giving ArrayList of XMLElement 
     * as output
     * @param source
     * @param logger
     * @return Nodes that match the htmlFilter, one node per String.
     */
    protected ArrayList<XMLElement> executeInputXMLElementListOutputXMLElementList( ArrayList<XMLElement> source, Logger logger) {
	if (source == null)
	    return new ArrayList<XMLElement>();
	//apply all filters to source
	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in XMLFilter, applying all filters");
	}
	for (int i = 0;i < filterList.size();i++) {
	    source = applyOneFilterToNodeList(source, filterList.get(i), logger);
	}
	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in XMLFilter, finished applying all filters");
	}
	    
	return source;
    }
    
    /**
     * similar to {@link #execute(String[], Logger)} but taking a String Array as input, and giving an ArrayList of XMLElement as output<br>
     * This will just call {@link XMLFilterOrGetterInstruction#executeInputStringArrOutputXMLElementList(String[], Logger, String)} 
     * @param source
     * @param logger
     * @return Nodes that match the tagfilter, one node per String.
     * @throws Exception 
     */
    protected ArrayList<XMLElement> executeInputStringArrOutputXMLElementList( String[]  source, Logger logger) throws Exception  {
	return super.executeInputStringArrOutputXMLElementList(source, logger, "XMLFilter");
    }

    /**
     * similar to {@link #execute(String[], Logger)} but taking an ArrayList of XMLElement as input
     * @param source
     * @param logger
     * @return XMLElements that match the XMLFilter.
     * @throws ParserException
     */
    String[] executeInputXMLElementListOutputStringArr(ArrayList<XMLElement>  source, Logger logger) throws ParserException {
	String[] returnvalue;	
	
	source = executeInputXMLElementListOutputXMLElementList(source, logger);

	//prepare string array to return
	returnvalue = new String[source == null ? 0: source.size()];
	for (int i = 0;i < returnvalue.length; i++)
	    returnvalue[i] = net.johandegraeve.easyxmldata.Utilities.createXML(source.get(i));
	
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

  
    
}
