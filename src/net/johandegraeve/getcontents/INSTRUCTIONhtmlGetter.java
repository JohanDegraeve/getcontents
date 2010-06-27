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

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
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
 * This is similar to htmlfilterinstruction but for getting content, applies to one specific node<br>
 * 
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONhtmlGetter extends Instruction implements XMLElement {

    /**
     * list of HTMLGetter to be applied one after the other
     */
    private ArrayList<HTMLGetter> getterList;
    
    /**
     * If recursive and if one specific node returns no NodeList then dig deeper into that node 
     *
     */
    private boolean recursive;

    /**
     * charset to be used, the charset can be used as attribute in the xml element.<br>
     * It is safer to specify the character set , even though it may be specified in the html page
     */
    private String charset;
    
    /**
     * similar to {@link #execute(String[], Logger)} but taking a NodeList as input
     * @param source
     * @param logger
     * @return Nodes that match the htmlGetter, one node per String.
     * @throws ParserException
     */
    String[] executeInputNodeListOutputStringArr(NodeList  source, Logger logger) throws ParserException {
	String[] returnvalue;	
	
	source = executeInputNodeListOutputNodeList(source, logger);
	//prepare string array to return
	returnvalue = new String[source == null ? 0: source.size()];
	for (int i = 0;i < source.size(); i++)
	    returnvalue[i] = source.elementAt(i).toHtml();
	
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
     * similar to {@link #execute(String[], Logger)} but taking a NodeList as input and giving NodeList as output
     * @param source
     * @param logger
     * @return Nodes that match the htmlGetter, one node per String.
     * @throws ParserException
     */
    NodeList executeInputNodeListOutputNodeList(NodeList  source, Logger logger) throws ParserException {
	if (source == null)
	    return new NodeList();
	//apply all filters to the nodelist
	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in htmlGetter, applying all getters");
	}
	for (int i = 0;i < getterList.size();i++) {
	    source = applyOneGetterToNodeList(source, getterList.get(i), logger);
	}
	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in htmlGetter, finished applying all getters");
	}
	    
	return source;
    }
    
    /**
     * similar to {@link #execute(String[], Logger)} but taking a String Array as input, and giving a NodeList as output
     * @param source
     * @param logger
     * @return Nodes that match the tagfilter, one node per String.
     * @throws ParserException
     */
    NodeList executeInputStringArrOutputNodeList( String[]  source, Logger logger) throws ParserException {
	StringBuilder temp = new StringBuilder();
	Parser htmlParser;
	NodeList parsedNodeList = null;
	
	for (int i = 0; i < source.length; i++)
	    temp.append(source[i]);

	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in htmlGetter, applying HTML Parser to the source");
	}

	htmlParser = new Parser(temp.toString());
	htmlParser.setEncoding(charset);
	parsedNodeList = htmlParser.parse(null);
	
	return executeInputNodeListOutputNodeList(parsedNodeList, logger);
	
    }
    /**
     * executes the HTMLGetter in the getterList one after the other
     * @throws ParserException can be thrown by html Parser
     * @see net.johandegraeve.getcontents.Instruction#execute(java.lang.String[], Logger)
     */
    @Override
    String[] execute(String[] source, Logger logger) throws ParserException {
	String[] returnvalue;
	NodeList parsedNodeList = null;
	Parser htmlParser;
	StringBuilder temp;

	//first put the whole source in a NodeList
	temp  = new StringBuilder();
	for (int i = 0;i < source.length; i++)
	    temp.append(source[i]);

	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in htmlGetter, applying HTML Parser to the source");
	}
	htmlParser = new Parser(temp.toString());
	htmlParser.setEncoding(charset);
	parsedNodeList = htmlParser.parse(null);
	
	//apply all filters to the nodelist
	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in htmlGetter, applying all getters");
	}
	for (int i = 0;i < getterList.size();i++) {
	    if (parsedNodeList.size() > 0) {
		parsedNodeList = applyOneGetterToNodeList(parsedNodeList, getterList.get(i), logger);
	    } else
		break;
	}
	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in htmlGetter, finished applying all getters");
	}
	    
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
     * applies one getter
     * @param nodeList
     * @param getter
     * @return a NodeList
     */
    private NodeList applyOneGetterToNodeList(NodeList nodeList, HTMLGetter getter, Logger logger) {
	NodeList newNodeList = new NodeList();
	
	if (logger != null && logger.getLogLevel().equalsIgnoreCase("debug")) {
	    logger.Log(System.currentTimeMillis() + " : method execute in htmlGetter, applying getter " + ((XMLElement)getter).getTagName());
	}
	
	for (int i = 0; i < nodeList.size(); i++) {
	    NodeList resultList = getter.getList(nodeList.elementAt(i));
	    if (resultList != null) {
		newNodeList.add(resultList);
	    } else if ((recursive) && 
		    	((nodeList.elementAt(i).getChildren() == null ? 
			    false :nodeList.elementAt(i).getChildren().size() > 0))
	               )
		newNodeList.add(applyOneGetterToNodeList(nodeList.elementAt(i).getChildren(), getter, logger));
	}
	if (logger != null && logger.getLogLevel().equalsIgnoreCase("debug")) {
	    logger.Log(System.currentTimeMillis() + " : new nodeList has  " + newNodeList.size() + " elements");
	}
	
	return newNodeList;
    }
    
    /**
     * constructor, sets resursive to false
     */
    public INSTRUCTIONhtmlGetter() {
	recursive = false;
	getterList = new ArrayList<HTMLGetter>();
	charset = "ISO-8859-1";
    }

    /**
     * considers recursive as optional attribute, default value = false, allowed values are true or false
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	try {
	    String[] attrValues = Utilities.getOptionalAttributeValues(
	    	attributes, 
	    	new String[] {
	    		TagAndAttributeNames.recursiveAttribute,
	    		TagAndAttributeNames.charsetAttribute
	    	}, 
	    	new String[]  {
	    		"false",
	    		"ISO-8859-1"
	    	});
	    if (attrValues[0].equalsIgnoreCase("true")) 
	        recursive = true;
	    else
	        recursive = false;
	    charset = attrValues[1];
	    if (!Charset.isSupported(charset))
		throw new SAXException("htmlGetter has an unsupported character encoding attribute - Charset.isSupported(" + charset + ") failed");
	} catch (IllegalCharsetNameException ex) {
	    throw new SAXException("htmlGetter has an illegal charset attribute");
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
     * if child is an HTMLGetter then it's added to the list, otherwise an Exception is thrown
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (child instanceof HTMLGetter)
	    getterList.add((HTMLGetter)child);
	else {
	    StringBuilder exceptionString = new StringBuilder();
	    exceptionString.append("Element " + TagAndAttributeNames.INSTRUCTIONhtmlGetterTag +
		    " can only have a htmlgetter as child element.\nAllowed html getters are : \n");
	    for (int i = 0; i < TagAndAttributeNames.htmlgetterTags.length; i ++)
		exceptionString.append(TagAndAttributeNames.htmlgetterTags[i] + "\n");
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
     * throws an exception of there's no HTMLGetter in the list
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (getterList.size() == 0) {
	    throw new SAXException("Element " + TagAndAttributeNames.INSTRUCTIONhtmlGetterTag +
		    " must have at least one getter as child element");
	}
    }

    /**
     * @return the recursive attribute in an AttributesImpl
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
	attr.addAttribute(null, 
		TagAndAttributeNames.charsetAttribute, 
		TagAndAttributeNames.charsetAttribute, 
		"CDATA", 
		charset);
	return attr;
    }

    /**
     * the HTMLGetters in an ArrayList
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
     * the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.INSTRUCTIONhtmlGetterTag;
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
