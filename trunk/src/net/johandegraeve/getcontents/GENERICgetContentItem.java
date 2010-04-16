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

import org.htmlparser.util.ParserException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/*
 * a getcontentitem can take as starting point for the first instruction either a URL, which as been supplied in the XML getcontentitemlist, as optional child of 
 * getcontentitem, or a string as supplied in executeInstructionSet
 * if theUrl is null (ie no url child supplied in the XML), then executeInstructionSet must be called with a non-null input parameter.
 * if theUrl is not null (ie a child supplied in the XML), then if executeInstructionSet is called with a null input parameter, the url will be used (ie content will be
 * fetched first). If executeInstructionSet is called with a non-null input parameter, then the input parameter is used as input.
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class GENERICgetContentItem implements XMLElement {
    private GENERICdescription theDescription;
    private GENERICinstructionList instructionSet;
    private String id;
    private GENERICurl theUrl;
    
    /**
     * copied those silly exception strings from org.htmlparser.http
     */
    @SuppressWarnings("unused")
    private static final String[] FOUR_OH_FOUR =
    {
        "The web site you seek cannot be located,"
            + " but countless more exist",
        "You step in the stream, but the water has moved on."
            + " This page is not here.",
        "Yesterday the page existed. Today it does not."
            + " The internet is like that.",
        "That page was so big. It might have been very useful."
            + " But now it is gone.",
        "Three things are certain: death, taxes and broken links."
            + " Guess which has occured.",
        "Chaos reigns within. Reflect, repent and enter the correct URL."
            + " Order shall return.",
        "Stay the patient course. Of little worth is your ire."
            + " The page is not found.",
        "A non-existant URL reduces your expensive computer to a simple stone.",
        "Many people have visited that page."
            + " Today, you are not one of the lucky ones.",
        "Cutting the wind with a knife. Bookmarking a URL."
            + " Both are ephemeral.",
    };

    

    
    public GENERICgetContentItem() {
	theDescription = null;
	instructionSet = null;
	theUrl = null;
    }
    
    String getDescription() {
	return theDescription.getDescription();
    }
    
    String getId() {
	return id;
    }
    
    String[] executeInstructionSet(String input) throws Exception {
	if (input == null)
	    try {
		input = theUrl.getUrl();
	    } catch (RuntimeException e1) {
		throw new Exception("It seems a url child element is missing in Element \"" + TagAndAttributeNames.GENERICgetcontentitemlistTag + 
			"\" with id \"" + input + "\".");
	    }
	
	try {
	    return instructionSet.execute(input);
	} catch (Exception e) {
	        String newExceptionString = e.toString();
	        for (int i = 0; i < FOUR_OH_FOUR.length; i++) {
	            if (newExceptionString.contains(FOUR_OH_FOUR[i])) {
	    		newExceptionString = newExceptionString.replaceAll(FOUR_OH_FOUR[i],"404 page not found");
	    		throw new ParserException (newExceptionString, e);	    		
	            }
	        }
	        throw e;
	}
    }
    
    @Override
    public void addAttributes(Attributes arg0) throws SAXException {
	id = Utilities.getMandatoryAttributeValues(this, arg0, new String[]{TagAndAttributeNames.idAttribute})[0];
    }

    @Override
    public void addChild(XMLElement arg0) throws SAXException {
	if (Utilities.getClassname(arg0.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICinstructionListTag)) {
	    instructionSet = (GENERICinstructionList) arg0;
	} else if (Utilities.getClassname(arg0.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICdescriptionTag)) {
	    theDescription = (GENERICdescription) arg0;
	} else if (Utilities.getClassname(arg0.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICurlTag)) {
	    theUrl = (GENERICurl) arg0;
	} else 
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICgetcontentitemTag +
		    " can only have " + 
		    TagAndAttributeNames.GENERICinstructionListTag + 
		    " or " +
		    TagAndAttributeNames.GENERICdescriptionTag + 
		    " or " +
		    TagAndAttributeNames.GENERICurlTag + 
		    " as child.");
    }
    @Override
    public void addText(String arg0) throws SAXException {
    }
    @Override
    public void complete() throws SAXException {
	if (theDescription == null)
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICgetcontentitemTag +
		    " must have a " + TagAndAttributeNames.GENERICdescriptionTag + " child element");
	if (instructionSet == null)
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICgetcontentitemTag +
		    " must have a " + TagAndAttributeNames.GENERICinstructionListTag + "  child element");
    }

    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.idAttribute, TagAndAttributeNames.idAttribute, "CDATA", id);
	return attr;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement>  returnvalue = new ArrayList<XMLElement> ();
	if (theUrl != null) returnvalue.add(theUrl);
	if (theDescription != null) returnvalue.add(theDescription);
	if (instructionSet != null) returnvalue.add(instructionSet);
	return returnvalue;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICgetcontentitemTag;
    }

    @Override
    public String getText() {
	return null;
    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
	// XXX Auto-generated method stub
	
    }

    @Override
    public boolean preserveSpaces() {
	// XXX Auto-generated method stub
	return false;
    }
}
