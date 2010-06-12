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
import net.johandegraeve.easyxmldata.Utilities;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


/**
 * Repeat all string processors a specified number of times<br>
 *  
 * @author Johan Degraeve
 *
 */
public class STRING_PROCESSORrepeat extends INSTRUCTIONprocessString implements StringProcessor    {

    /**
     * how many times to repeat
     */
    private int repeat;
    
    /**
     * constructor, setting {@link #repeat} to null
     */
    public STRING_PROCESSORrepeat() {
	repeat=0;
    }
    
    /**
     * adds attribute {@link #repeat}
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	try {
	    String[] attrValues = Utilities.getOptionalAttributeValues(
	    	attributes, 
	    	new String[] {
	    		TagAndAttributeNames.repeatAttribute
	    	}, 
	    	new String[]  {
	    		"1"
	    	});
	    repeat = Integer.parseInt(attrValues[0]); 
	    if (repeat < 1)
		throw new SAXException("Frequency should be Integer greater dan 0");
	} catch (NumberFormatException ex) {
	    throw new SAXException("Parsing frequency failed, should be Integer greater dan 0");
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
     * @return {@link #repeat} in an attribute
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl(super.getAttributes());
	attr.addAttribute(null, 
		TagAndAttributeNames.repeatAttribute,
		TagAndAttributeNames.repeatAttribute,
		"CDATA", 
		Integer.toString(repeat));
	return attr;
    }

    /**
     * @return {@link TagAndAttributeNames#STRING_PROCESSORrepeatTag}
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.STRING_PROCESSORrepeatTag;
    }

    /**
     * unfortunately, the child string processors will be executed without logging
     * @return the children  applied {@link #repeat} times
     * @throws Exception 
     * @see net.johandegraeve.getcontents.StringProcessor#processString(java.lang.String[])
     */
    @Override
    public String[] processString(String[] source) throws Exception {
	for (int i = 0; i < repeat; i ++)
	    source = execute(source,null);
	return source;
    }


}
