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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.Ostermiller.util.StringHelper;

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
 * @author Johan Degraeve
 *
 */
public class GENERICgetContentItem implements XMLElement {
    /**
     * description
     */
    private GENERICdescription theDescription;
    /**
     * instructionList
     */
    private GENERICinstructionList instructionList;
    /**
     * identifier for the getContentItem
     */
    private String id;

    /**
     * url
     */
    private ArrayList<GENERICurl> theUrls;
    
    /**
     * allows applications to add customObjects to the getContentItem element<br>
     * Applications must override {@link net.johandegraeve.getcontents#CustomObject}
     */
    private CustomObject customObject;
     
    /**
     * constructor
     */
    public GENERICgetContentItem() {
	theDescription = null;
	instructionList = null;
	theUrls = new ArrayList<GENERICurl>();
    }
    
    /**
     * @return the description
     */
    String getDescription() {
	return theDescription.getDescription();
    }
    
    /**
     * get the identifier for the getContentItem
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
     * (anything not starting with &lt; is considered to be a url), or the actual source text (if starting with &lt;). If input is
     * a url, it will first be downloaded.<br>
     * If there are multiple url, then the first url openening must succeed, otherwise an exception will be thrown.<br>
     * 
     * @param input
     * @param logger used for logging
     * @return the result
     * @throws Exception
     * @trhows FileNotFoundException in case the first url could not be opened
     */
    String[] executeInstructionSet(String input, Logger logger) throws Exception {
	String[][] returnvalueArray;
	int totalSize = 0;
	String[] returnvalue;
	int counter;

	if (input == null) {
	    if (theUrls.size() == 0) {
		if (logger != null) {
		    if (StringHelper.equalsAnyIgnoreCase(logger.getLogLevel(), new String[] {"debug","critical","warning"})) {
			logger.Log(System.currentTimeMillis() + " : It seems a url child element is missing in Element \"" + TagAndAttributeNames.GENERICgetcontentitemlistTag + 
				"\" with id \"" + input + "\".");
		    }
		}
		throw new Exception("It seems a url child element is missing in Element \"" + TagAndAttributeNames.GENERICgetcontentitemlistTag + 
			"\" with id \"" + input + "\".");
	    }
	    
	    returnvalueArray = new String[theUrls.size()][];
	    for (counter = 0;counter < theUrls.size();counter++) {
		try {
		    returnvalueArray[counter] = instructionList.execute(theUrls.get(counter).getUrl(), logger);
		    totalSize = totalSize + returnvalueArray[counter].length;
		} catch (java.io.FileNotFoundException e) {
		    if (counter == 0) //if it's the first url that failed to open, then throw an exception 
			throw e;
		    else
			break;
		}
	    }
	    returnvalue = new String[totalSize];
	    totalSize = 0;
	    for (int i = 0;i < counter; i++)
		for (int j = 0;j < returnvalueArray[i].length;j++) {
		    returnvalue[totalSize] = returnvalueArray[i][j];
		    totalSize++;
		}
	    return returnvalue;
	    
	} else	{
	    return instructionList.execute(input, logger);
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
	    instructionList = (GENERICinstructionList) arg0;
	} else if (Utilities.getClassname(arg0.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICdescriptionTag)) {
	    theDescription = (GENERICdescription) arg0;
	} else if (Utilities.getClassname(arg0.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICurlTag)) {
	    theUrls.add((GENERICurl) arg0);
	} else if (arg0 instanceof CustomObject) {
	    if (customObject != null) {
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICgetcontentitemTag +
			" can only have on element of type" + Utilities.getClassname(arg0.getClass()));
	    } else
	     customObject = (CustomObject) arg0;
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
	if (instructionList == null)
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
	if (theUrls.size() != 0) 
	    for (int i = 0;i < theUrls.size();i++)
		returnvalue.add(theUrls.get(i));
	if (theDescription != null) returnvalue.add(theDescription);
	if (instructionList != null) returnvalue.add(instructionList);
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
     * get the url at index 
     * @param index 
     * @return the URL, null if there's no url
     */
    public String getURL(int index) {
	if (theUrls == null) return null;
	return theUrls.get(index).getUrl();
    }
    
    /**
     * get {@link #customObject}
     * @return {@link #customObject}
     */
    CustomObject getCustomObject() {
	return customObject;
    }
    
    /**
     * get the size of the instructionList
     * @return size of {@link #instructionList}
     */
    int getInstructionListSize() {
	return instructionList.size();
    }
}
