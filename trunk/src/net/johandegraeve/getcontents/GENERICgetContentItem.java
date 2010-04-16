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

/**
 * a getcontentitem can take as starting point for the first instruction either a URL, which
 * has been supplied in the XML, as optional child of 
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

    

    
    /**
     * constructor
     */
    public GENERICgetContentItem() {
	theDescription = null;
	instructionSet = null;
	theUrl = null;
    }
    
    /**
     * @return the description
     */
    String getDescription() {
	return theDescription.getDescription();
    }
    
    /**
     * @return the id
     */
    String getId() {
	return id;
    }
    
    /**
     * Executes the list of instructions.<br>
     * input can be null or non null. <br>
     * If input is null and there's no url child then an exception will be thrown<br>
     * If input is null and there's a url, the url will first be downloaded and this is where the first instruction will start<br>
     * If input is not null, then the input will be used by the first instruction, which may still be a url
     * (anything starting with &lt; is considered to be a url), or the actual source text (anything else). If input is
     * a url, it will first be downloaded.
     * 
     * @param input
     * @return the result
     * @throws Exception
     */
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
    
    /**
     * considers id as a mandatory attribute and stores it.
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes arg0) throws SAXException {
	id = Utilities.getMandatoryAttributeValues(this, arg0, new String[]{TagAndAttributeNames.idAttribute})[0];
    }

    /**
     * Allowed children are instructionList, description and url, stores the child.
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
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
    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String arg0) throws SAXException {
    }
    
    /**
     * the getContentItem must contain a description, instructionSet and id attribute, if not an exception is thrown
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (theDescription == null)
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICgetcontentitemTag +
		    " must have a " + TagAndAttributeNames.GENERICdescriptionTag + " child element");
	if (instructionSet == null)
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICgetcontentitemTag +
		    " must have a " + TagAndAttributeNames.GENERICinstructionListTag + "  child element");
	if (id == null || id.equals("")) {
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICgetcontentitemTag +
	    " must have a " + TagAndAttributeNames.idAttribute + " as attribute");
	}
    }

    /**
     * @return the id in an AttributesImpl
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.idAttribute, TagAndAttributeNames.idAttribute, "CDATA", id);
	return attr;
    }

    /**
     * the url (if not null), description and instructionSet
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement>  returnvalue = new ArrayList<XMLElement> ();
	if (theUrl != null) returnvalue.add(theUrl);
	if (theDescription != null) returnvalue.add(theDescription);
	if (instructionSet != null) returnvalue.add(instructionSet);
	return returnvalue;
    }

    /**
     * @return the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICgetcontentitemTag;
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
	// XXX Auto-generated method stub
	
    }

    /**
     * @return flase
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	// XXX Auto-generated method stub
	return false;
    }
}
