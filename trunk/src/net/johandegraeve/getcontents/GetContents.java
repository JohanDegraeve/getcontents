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

import java.net.URL;

import org.xml.sax.SAXParseException;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.EasyXMLDataParser;

/*
 * to get contents, only class that should be used by application using this library
 *
 * @author Johan Degraeve
 *
 */
public class GetContents {
    
    private String instructions;
    private GENERICgetContentItemList result;
    private Boolean debug =  true;

    /**
     * Creating instance with XML document that contains the necessary instructions.<br>
     * If instructions is a url then the constructor will try to connect to the url so this may take some time.
     * @param instructions can be url (http:... or file:...), html (<...) or XML (< ...)
     * @throws Exception 
     * @throws SAXParseException 
     */
    public GetContents(String instructions) throws Exception  {
	this.instructions = instructions;
        EasyXMLDataParser myParser = new EasyXMLDataParser(
    	    new String[] {"net.johandegraeve.getcontents","net.johandegraeve.getcontents","net.johandegraeve.getcontents", "net.johandegraeve.getcontents"},
    	    new String[] {"INSTRUCTION","GENERIC","GETorFILTER", "STRING_PROCESSOR"}, 
    	    true);
	try {
	    result = (GENERICgetContentItemList) myParser.parse(instructions);
	} catch (SAXParseException e) {
	    String exceptionresult = "";
	    	if (e.toString().contains("cannot be cast to")) 
	 		exceptionresult = 
	 		    "It seems a class cast exception will be thrown. This probably means your XML contains an element with an unkonwn tag.\n" +
	    	    	"Check the location of the exception, then the element at this position, check the parent element of that elment.\n" +
	    	    	" This is probably an unkonwn type";
	    	exceptionresult = exceptionresult + e.toString() +
	    	"\n" +
	    	"LineNumber = " +   e.getLineNumber() + "\n" +
	    	"ColumnNumber = " +   e.getColumnNumber() + "\n" +
	    	"PublicId = " +   e.getPublicId() + "\n" +
	    	"SystemId = " +   e.getSystemId() + "\n";
	    	throw new Exception(exceptionresult);
	}
	if (debug) {
	    System.out.println(Utilities.createXML(result));
	}
    }
    
    public String[] getResult(String id) throws Exception {
	return getResult(id, null);
    }

    public String[] getResult(String id, String input) throws Exception {
	for (int i = 0;i < result.size();i ++) {
	    if (result.elementAt(i).getId().equals(id)) {
		return result.elementAt(i).executeInstructionSet(input);
	    }
	}
	return new String[0];
    }
}
