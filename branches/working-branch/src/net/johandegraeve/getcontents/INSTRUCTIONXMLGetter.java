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
 * XMLGetter Instruction
 * 
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONXMLGetter extends XMLFilterOrGetterInstruction  {

    /**
     * list of XMLGetter
     */
    private ArrayList<XMLGetter> getterList;


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

	if (source == null) {
	    thelogger.Log(System.currentTimeMillis() + " : method execute in XMLGetter, source = null, returning an empty string array" );
	    return new String[0];
	}

	if (thelogger != null) {
	    thelogger.Log(System.currentTimeMillis() + " : method execute in XMLGetter, applying XML parser" );
	}
	startList = new XMLXMLGetterResultList(net.johandegraeve.getcontents.Utilities.makeList(source,charsetName));

	if (thelogger != null) {
	    thelogger.Log(System.currentTimeMillis() + " : method execute in XMLGetter, applying all getters");
	}
	//start by applying the first getter to the nodeLIst
	resultList = getterList.get(0).getList(startList);
	//apply all getters to the nodelist
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
     * 
     * @see net.johandegraeve.getcontents.XMLFilterOrGetterInstruction#executeInputXMLElementListOutputXMLElementList(java.util.ArrayList, net.johandegraeve.getcontents.Logger)
     */
    @Override
    protected ArrayList<XMLElement> executeInputXMLElementListOutputXMLElementList(
	    ArrayList<XMLElement> source, Logger logger) throws ParserException {
	ArrayList<XMLElement> returnvalue;
	XMLXMLGetterResultList startList = null;
	GenericXMLGetterResultList resultList = null;


	//start by applying the first getter to the nodeLIst
	resultList = getterList.get(0).getList(startList);

	//apply all getters to the nodelist
	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in XMLGetter, applying all getters");
	}
	for (int i = 1;i < getterList.size();i++) {
	    if (logger != null && logger.getLogLevel().equalsIgnoreCase("debug")) {
		logger.Log(System.currentTimeMillis() + " : method execute in XMLGetter, applying  " + ((XMLElement)getterList.get(i)).getTagName());
	    }
	    resultList = getterList.get(i).getList(resultList);
	}


	returnvalue = new ArrayList<XMLElement>();
	for (int i = 0;i < resultList.size();i ++)
	    returnvalue.add(((XMLXMLGetterResult)resultList.elementAt(i)).getDefaultXMLElement());

	return returnvalue;
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
	return super.executeInputStringArrOutputXMLElementList(source, logger, "XMLGetter");
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
	GenericXMLGetterResultList resultList = null;

	if (source == null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in XMLGetter, source = null, returning an empty string array" );
	    return new String[0];
	}

	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in XMLGetter, applying all getters");
	}
	//start by applying the first getter to the nodeLIst
	resultList = getterList.get(0).getList(new XMLXMLGetterResultList(source));
	//then apply the remaining getters
	for (int i = 1;i < getterList.size();i++) {
	    if (logger != null && logger.getLogLevel().equalsIgnoreCase("debug")) {
		logger.Log(System.currentTimeMillis() + " : method execute in XMLGetter, applying  " + ((XMLElement)getterList.get(i)).getTagName());
	    }
	    resultList = getterList.get(i).getList(resultList);
	}

	//prepare string array to return
	if (resultList != null) {
	    returnvalue = new String[resultList.size()];
	    if (logger != null) {
		logger.Log(System.currentTimeMillis() + " : result has  " + returnvalue.length + " elements");
	    }
	}
	else {
	    if (logger != null) {
		logger.Log(System.currentTimeMillis() + " : result has  0 elements");
	    }
	    return new String[0];
	}
	for (int i = 0;i < resultList.size(); i++)
	    returnvalue[i] = resultList.elementAt(i).convertToString();

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
