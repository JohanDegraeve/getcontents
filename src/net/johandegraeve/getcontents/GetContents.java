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

import com.Ostermiller.util.StringHelper;

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
	this(instructions, null);
    }

    /**
     * Creating instance with XML document that contains the necessary instructions.<br>
     * If instructions is a url then the constructor will try to connect to the url so this may take some time.
     * @param instructions can be url (http:... or file:...), html (<...) or XML (< ...)
     * @param logger if null then there's no logging, if not null then logging will be done through the logger
     * @throws Exception 
     */
    public GetContents(String instructions, Logger logger) throws Exception  {
	this.logger = logger;
	EasyXMLDataParser myParser = new EasyXMLDataParser(
    	    new String[] {"net.johandegraeve.getcontents","net.johandegraeve.getcontents","net.johandegraeve.getcontents", "net.johandegraeve.getcontents"},
    	    new String[] {"INSTRUCTION","GENERIC","GETorFILTER", "STRING_PROCESSOR"}, 
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
     * @return the url, null if id was not found or if the corresponding getContentItem does not have a url element
     * @throws Exception
     */
    public String getURL(String id) throws Exception {
	for (int i = 0;i < result.size();i ++) {
	    if (result.elementAt(i).getId().equals(id)) {
		return result.elementAt(i).getURL();
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
     * @return the result
     * @throws Exception
     */
    public String[] getResult(String id, String input) throws Exception {
	for (int i = 0;i < result.size();i ++) {
	    if (result.elementAt(i).getId().equals(id)) {
		return result.elementAt(i).executeInstructionSet(input,logger);
	    }
	}
	if (logger != null) {
    	    if (StringHelper.equalsAnyIgnoreCase(logger.getLogLevel(), new String[] {"debug","critical","warning"})) {
    		logger.Log(System.currentTimeMillis() + " : did not find a getContentItem with id " + id + " - check the instructions xml file");
    	    }
	}
	return new String[0];
    }
}
