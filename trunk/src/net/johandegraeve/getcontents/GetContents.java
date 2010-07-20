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

import net.johandegraeve.easyxmldata.EasyXMLDataParser;

import org.xml.sax.SAXParseException;

/**
 * to get contents, the only class that should be used by application using this library
 *
 * @author Johan Degraeve
 *
 */
public class GetContents {
    
    /**
     * the logger, if null then there's no logging
     */
    Logger logger;
    
    /**
     * this is the result from parsing the XML in the instructions file
     */
    private GENERICgetContentItemList result;
    
    /**
     * Creating instance with XML document that contains the necessary instructions.<br>
     * If instructions is a url then the constructor will try to connect to the url so this may take some time.<br>
     * No logging will be done.
     * @param instructions can be url (http:... or file:...), html (<...) or XML (< ...)
     * @throws Exception 
     * @throws SAXParseException 
     */
    public GetContents(String instructions) throws Exception  {
	this(instructions, null,null,null);
    }

    /**
     * Creating instance with XML document that contains the necessary instructions.<br>
     * If instructions is a url then the constructor will try to connect to the url so this may take some time.
     * @param instructions can be url (http:... or file:...), html (<...) or XML (< ...)
     * @param logger if null then there's no logging, if not null then logging will be done through the logger
     * @throws Exception 
     */
    public GetContents(String instructions, Logger logger) throws Exception  {
	this(instructions, logger,null,null);
    }
    
    /**
     * Creating instance with XML document that contains the necessary instructions.<br>
     * If instructions is a url then the constructor will try to connect to the url so this may take some time.
     * @param instructions can be url (http:... or file:...), html (<...) or XML (< ...)
     * @param logger if null then there's no logging, if not null then logging will be done through the logger
     * @param packagename will be used in case customObject is used, this is the packagename where a class is defined that overrides  {@link net.johandegraeve.getcontents#CustomObject} 
     * @param prefix will be used in case customObject is used, this is the prefix used in defining the class name that overrides  {@link net.johandegraeve.getcontents#CustomObject} 
     * @throws Exception 
     */
    public GetContents(String instructions, Logger logger, String packagename, String prefix) throws Exception  {
	this.logger = logger;
	EasyXMLDataParser myParser = new EasyXMLDataParser(
    	    new String[] {packagename == null ? "just a dummy name because I'm too lazy to add an if statement":packagename, 
    		    "net.johandegraeve.getcontents","net.johandegraeve.getcontents","net.johandegraeve.getcontents", "net.johandegraeve.getcontents"},
    	    new String[] {prefix  == null ? "just a dummy name because I'm too lazy to add an if statement":prefix, "INSTRUCTION","GENERIC","GETorFILTER", "STRING_PROCESSOR"}, 
    	    false);
	
	    result = (GENERICgetContentItemList) myParser.parse(instructions);
	    if (logger != null) {
		logger.Log(System.currentTimeMillis() + " : Instructions parsed");
	} 
    }
    
    /**
     * the set of instructions in the element getContentItem identified by &quot;id&quot; will be executed and the result is returned.<br>
     * This method assumes that the element getContentItem has a url as child element, this is where the content will be fetched. If there's no url
     * in the getContentItem element, an exception will be thrown
     * @param id identifies the getContentItem element to be used
     * @return the result
     * @throws Exception
     */
    public String[] getResult(String id) throws Exception {
	return getResult(id, null);
    }
    
    /**
     * @param id
     * @param index index of the url to retrieve (there can be multiple urls)
     * @return the url, null if id was not found or if the corresponding getContentItem does not have a url element
     * @throws Exception
     */
    public String getURL(String id, int index) throws Exception {
	for (int i = 0;i < result.size();i ++) {
	    if (result.elementAt(i).getId().equals(id)) {
		return result.elementAt(i).getURL(index);
	    }
	}
	return null;
    }

    /**
     * the set of instructions in the element getContentItem identified by &quot;id&quot; will be executed and the result is returned.<br>
     * This method assumes that the element getContentItem has a url as child element, this is where the content will be fetched
     * @param id identifies the getContentItem element to be used
     * @param input this is the source that will be used, it may be the actual content or a url (file or http); if the getContentItem element
     * has a url as child, it will be ignored
     * @return the result, if id was not found in the instruction list, then a string array with zero elements is returned
     * @throws Exception
     */
    public String[] getResult(String id, String input) throws Exception {
	for (int i = 0;i < result.size();i ++) {
	    if (result.elementAt(i).getId().equals(id)) {
		return result.elementAt(i).executeInstructionSet(input,logger);
	    }
	}
	if (logger != null) {
    		logger.Log(System.currentTimeMillis() + " : did not find a getContentItem with id " + id + " - check the instructions xml file");
	}
	return new String[0];
    }
    
    /**
     * to get the id of the getContentItem at position elementAt, index 0  is the first element
     * @param elementAt the index of the elemenet of which the id will be returned
     * @return the id
     */
    public String getGetContentItemId (int elementAt) {
	if (elementAt > result.size() - 1) {
	    throw new IndexOutOfBoundsException("Index for getting a getcontentItem is out of range");
	}
	return result.elementAt(elementAt).getId();
    }
    
    /**
     * get a CustomObject for the getContentItem with specified id
     * @param getContentItemId id of the  getContentItem off which the customObject should be returned.
     * @return the customObject of the getContentItem that has the specified id,  null if id not found
     */
    CustomObject getCustomObject (String getContentItemId) {
	for (int i = 0;i < result.size();i ++) {
	    if (result.elementAt(i).getId().equals(getContentItemId)) {
		return result.elementAt(i).getCustomObject();
	    }
	}
	return null;
    }
    /**
     * get the list of id&quot;s for all the getContentItem elements
     * @return  the list of id
     */
    public String[] getListOfIds() {
	return result.getListOfIds();
    }
    
    /**
     * creates XML representation of the instruction list
     * @return ML representation of the instruction list
     */
    public String toXML() {
	return net.johandegraeve.easyxmldata.Utilities.createXML(result);
    }
}
