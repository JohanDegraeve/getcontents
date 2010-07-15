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

import net.johandegraeve.easyxmldata.XMLElement;

import org.htmlparser.util.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * class for list of instructions
 * @author Johan Degraeve
 *
 */
public class GENERICinstructionList implements XMLElement {
    /**
     * instructionSet : arrayList of {@link Instruction instructions}
     */
    private ArrayList<Instruction> instructionSet;
    
    /**
     * constructor
     */
    public GENERICinstructionList() {
	instructionSet = new ArrayList<Instruction>();
    }

    /**
     * Executes the list of instructions.<br>
     * 
     * @param input a url or the source, anything starting with &lt; is considered to be not a url
     * @param logger 
     * @return the result A string array of size 0 if input = null, the result of executing all instructions one by one
     * on the input, if input != null
     * @throws Exception
     */
    String[] execute(String input, Logger logger) throws Exception {
	String[] returnvalue;
	NodeList parsednodeList;
	ArrayList<XMLElement> XMLElementList;

	if (input == null || input.length() == 0) {
	    if (logger != null) {
		logger.Log(System.currentTimeMillis() + " : method execute in instructionList : the input is empty, instructions will not be executed, returnvalue is empty");
	    }
	    return new String[0];
	}

	returnvalue  = new String[]{input};
	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in instructionList : starting to execute the list of instructions");
	}

	//execute the instructions one by one
	for (int i = 0;i < instructionSet.size(); i++) {
	    if (logger != null) {
		logger.Log(System.currentTimeMillis() + " : method execute in instructionList : start of instruction " + i +
			" " + instructionSet.get(i).getTagName());
	    }

	    //all this has been done to avoid that html parsing needs to be done if there are two or more subsequent html filter or getter
	    //if the next instruction is also a htmlFilter or htmlGetter then we'll avoid that two times parsing will occur
	    if (i+1 < instructionSet.size() && (instructionSet.get(i) instanceof INSTRUCTIONhtmlFilter || instructionSet.get(i) instanceof INSTRUCTIONhtmlGetter)) {
		//there's still an instruction after this one, let's see if it's an htmlGetter or htmlFilter
		if (instructionSet.get(i+1) instanceof INSTRUCTIONhtmlFilter || instructionSet.get(i+1) instanceof INSTRUCTIONhtmlGetter) {
		    //yes we should apply another instance of execute that returns parsednode
		    //so let's apply it but first check if it's an html filter or html getter
		    if (instructionSet.get(i) instanceof INSTRUCTIONhtmlFilter) {
			parsednodeList = ((INSTRUCTIONhtmlFilter)instructionSet.get(i)).executeInputStringArrOutputNodeList(returnvalue, logger);
		    } else 
			parsednodeList = ((INSTRUCTIONhtmlGetter)instructionSet.get(i)).executeInputStringArrOutputNodeList(returnvalue, logger);
		    if (logger != null) {
			logger.Log(System.currentTimeMillis() + " : method execute in instructionList : end of instruction " + i +
				" : " + instructionSet.get(i).getTagName() + "\n" + "The result has " + (returnvalue == null ? 0:returnvalue.length) + " elements\n\n");
			if (logger.getLogLevel().equalsIgnoreCase("debug") && returnvalue != null) {
			    for (int j = 0;j < returnvalue.length;j++) {
				logger.Log ("result " + j + " = " + returnvalue[j] + "\n");
			    }
			}
		    }
		    i = i + 1;
		    //now we have a nodelist as returnvalue
		    // we can go on no, just as long as the next instruction is an html filter or getter
		    while (i+1 < instructionSet.size() && (instructionSet.get(i+1) instanceof INSTRUCTIONhtmlFilter || instructionSet.get(i+1) instanceof INSTRUCTIONhtmlGetter)) {
			if (logger != null) {
			    logger.Log(System.currentTimeMillis() + " : method execute in instructionList : start of instruction " + i +
				    " " + instructionSet.get(i).getTagName());
			}
			if (instructionSet.get(i) instanceof INSTRUCTIONhtmlFilter) {
			    parsednodeList = ((INSTRUCTIONhtmlFilter)instructionSet.get(i)).executeInputNodeListOutputNodeList(parsednodeList, logger);
			} else 
			    parsednodeList = ((INSTRUCTIONhtmlGetter)instructionSet.get(i)).executeInputNodeListOutputNodeList(parsednodeList, logger);
			if (logger != null) {
			    logger.Log(System.currentTimeMillis() + " : method execute in instructionList : end of instruction " + i +
				    " : " + instructionSet.get(i).getTagName() + "\n" + "The result has " + (returnvalue == null ? 0:returnvalue.length) + " elements\n\n");
			    if (logger.getLogLevel().equalsIgnoreCase("debug") && returnvalue != null) {
				for (int j = 0;j < returnvalue.length;j++) {
				    logger.Log ("result " + j + " = " + returnvalue[j] + "\n");
				}
			    }
			}
			i = i + 1;
		    }
		    //so there's no more next instruction that is an htmlgetter or htmlfilter, we need to execute the current one, using the parsednodelist as input, giving a string array
		    if (logger != null) {
			logger.Log(System.currentTimeMillis() + " : method execute in instructionList : start of instruction " + i +
				" " + instructionSet.get(i).getTagName());
		    }
		    if (instructionSet.get(i) instanceof INSTRUCTIONhtmlFilter) {
			returnvalue = ((INSTRUCTIONhtmlFilter)instructionSet.get(i)).executeInputNodeListOutputStringArr(parsednodeList, logger);
		    } else 
			returnvalue = ((INSTRUCTIONhtmlGetter)instructionSet.get(i)).executeInputNodeListOutputStringArr(parsednodeList, logger);
		    if (logger != null) {
			logger.Log(System.currentTimeMillis() + " : method execute in instructionList : end of instruction " + i +
				" : " + instructionSet.get(i).getTagName() + "\n" + "The result has " + (returnvalue == null ? 0:returnvalue.length) + " elements\n\n");
			if (logger.getLogLevel().equalsIgnoreCase("debug") && returnvalue != null) {
			    for (int j = 0;j < returnvalue.length;j++) {
				logger.Log ("result " + j + " = " + returnvalue[j] + "\n");
			    }
			}
		    }
		} else {
		    returnvalue = instructionSet.get(i).execute(returnvalue, logger);
		    if (logger != null) {
			logger.Log(System.currentTimeMillis() + " : method execute in instructionList : end of instruction " + i +
				" : " + instructionSet.get(i).getTagName() + "\n" + "The result has " + (returnvalue == null ? 0:returnvalue.length) + " elements\n\n");
			if (logger.getLogLevel().equalsIgnoreCase("debug") && returnvalue != null) {
			    for (int j = 0;j < returnvalue.length;j++) {
				logger.Log ("result " + j + " = " + returnvalue[j] + "\n");
			    }
			}
		    }
		}
	    } else {

		//all this has been done to avoid that xml parsing needs to be done if there are two or more subsequent xml filter or getter
		//if the next instruction is also a XMLFilter or XMLGetter then we'll avoid that two times parsing will occur
		if (i+1 < instructionSet.size() && (instructionSet.get(i) instanceof INSTRUCTIONXMLFilter || instructionSet.get(i) instanceof INSTRUCTIONXMLGetter)) {
		    //there's still an instruction after this one, let's see if it's an XMLGetter or XMLFilter
		    if (instructionSet.get(i+1) instanceof INSTRUCTIONXMLFilter || instructionSet.get(i+1) instanceof INSTRUCTIONXMLGetter) {
			//yes we should apply another instance of execute that returns parsednode
			//so let's apply it but first check if it's an xml filter or xml getter
			if (instructionSet.get(i) instanceof INSTRUCTIONXMLFilter) {
			    XMLElementList = ((INSTRUCTIONXMLFilter)instructionSet.get(i)).executeInputStringArrOutputXMLElementList(returnvalue, logger);
			} else 
			    XMLElementList = ((INSTRUCTIONXMLGetter)instructionSet.get(i)).executeInputStringArrOutputXMLElementList(returnvalue, logger);
			if (logger != null) {
			    logger.Log(System.currentTimeMillis() + " : method execute in instructionList : end of instruction " + i +
				    " : " + instructionSet.get(i).getTagName() + "\n" + "The result has " + (returnvalue == null ? 0:returnvalue.length) + " elements\n\n");
			    if (logger.getLogLevel().equalsIgnoreCase("debug") && returnvalue != null) {
				for (int j = 0;j < returnvalue.length;j++) {
				    logger.Log ("result " + j + " = " + returnvalue[j] + "\n");
				}
			    }
			}
			i = i + 1;
			//now we have a XMLElement ArrayList as returnvalue
			// we can go on no, just as long as the next instruction is an xml filter or getter
			while (i+1 < instructionSet.size() && (instructionSet.get(i+1) instanceof INSTRUCTIONXMLFilter || instructionSet.get(i+1) instanceof INSTRUCTIONXMLGetter)) {
			    if (logger != null) {
				logger.Log(System.currentTimeMillis() + " : method execute in instructionList : start of instruction " + i +
					" " + instructionSet.get(i).getTagName());
			    }
			    if (instructionSet.get(i) instanceof INSTRUCTIONXMLFilter) {
				XMLElementList = ((INSTRUCTIONXMLFilter)instructionSet.get(i)).executeInputXMLElementListOutputXMLElementList(XMLElementList, logger);
			    } else 
				XMLElementList = ((INSTRUCTIONXMLGetter)instructionSet.get(i)).executeInputXMLElementListOutputXMLElementList(XMLElementList, logger);
			    if (logger != null) {
				logger.Log(System.currentTimeMillis() + " : method execute in instructionList : end of instruction " + i +
					" : " + instructionSet.get(i).getTagName() + "\n" + "The result has " + (returnvalue == null ? 0:returnvalue.length) + " elements\n\n");
				if (logger.getLogLevel().equalsIgnoreCase("debug") && returnvalue != null) {
				    for (int j = 0;j < returnvalue.length;j++) {
					logger.Log ("result " + j + " = " + returnvalue[j] + "\n");
				    }
				}
			    }
			    i = i + 1;
			}
			//so there's no more next instruction that is an xml getter or xml filter, we need to execute the current one, using the XMLElementList as input, giving a string array
			if (logger != null) {
			    logger.Log(System.currentTimeMillis() + " : method execute in instructionList : start of instruction " + i +
				    " " + instructionSet.get(i).getTagName());
			}
			if (instructionSet.get(i) instanceof INSTRUCTIONXMLFilter) {
			    returnvalue = ((INSTRUCTIONXMLFilter)instructionSet.get(i)).executeInputXMLElementListOutputStringArr(XMLElementList, logger);
			} else 
			    returnvalue = ((INSTRUCTIONXMLGetter)instructionSet.get(i)).executeInputXMLElementListOutputStringArr(XMLElementList, logger);
			if (logger != null) {
			    logger.Log(System.currentTimeMillis() + " : method execute in instructionList : end of instruction " + i +
				    " : " + instructionSet.get(i).getTagName() + "\n" + "The result has " + (returnvalue == null ? 0:returnvalue.length) + " elements\n\n");
			    if (logger.getLogLevel().equalsIgnoreCase("debug") && returnvalue != null) {
				for (int j = 0;j < returnvalue.length;j++) {
				    logger.Log ("result " + j + " = " + returnvalue[j] + "\n");
				}
			    }
			}
		    } else {
			returnvalue = instructionSet.get(i).execute(returnvalue, logger);
			if (logger != null) {
			    logger.Log(System.currentTimeMillis() + " : method execute in instructionList : end of instruction " + i +
				    " : " + instructionSet.get(i).getTagName() + "\n" + "The result has " + (returnvalue == null ? 0:returnvalue.length) + " elements\n\n");
			    if (logger.getLogLevel().equalsIgnoreCase("debug") && returnvalue != null) {
				for (int j = 0;j < returnvalue.length;j++) {
				    logger.Log ("result " + j + " = " + returnvalue[j] + "\n");
				}
			    }
			}
		    }
		} else {
		//the next instruction is not an html filter or getter and also not an xml filter or getter
		    returnvalue = instructionSet.get(i).execute(returnvalue, logger);
		    if (logger != null) {
			logger.Log(System.currentTimeMillis() + " : method execute in instructionList : end of instruction " + i +
				" : " + instructionSet.get(i).getTagName() + "\n" + "The result has " + (returnvalue == null ? 0:returnvalue.length) + " elements\n\n");
			if (logger.getLogLevel().equalsIgnoreCase("debug") && returnvalue != null) {
			    for (int j = 0;j < returnvalue.length;j++) {
				logger.Log ("result " + j + " = " + returnvalue[j] + "\n");
			    }
			}
		    }
		    if (returnvalue == null || returnvalue.length == 0)
			break;
		}
	    }
	}
	return returnvalue;
    }
    
    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes arg0) throws SAXException {

    }

    /**
     * if arg0 is an instructions it will be added to the list, else throws an exception
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement arg0) throws SAXException {
	if (arg0 instanceof Instruction)
		instructionSet.add((Instruction)arg0);
	else
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICinstructionListTag +
		    " can only have a child element of type Instruction");

    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String arg0) throws SAXException {
    }

    /**
     * throws an exception if the instructionlist is empty.
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (instructionSet.size() == 0)
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICinstructionListTag +
		    " must have at least one instruction child element");
    }

    /**
     * @return null
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	return null;
    }

    /**
     * @return the list of instructions
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	return new ArrayList<XMLElement>(instructionSet);
    }

    /**
     * @return the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICinstructionListTag;
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
     * @return flase
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	return false;
    }
    
    /**
     * get the size of the instructionSet
     * @return the size of {@link #instructionSet}
     */
    int size() {
	return instructionSet.size();
    }


}
