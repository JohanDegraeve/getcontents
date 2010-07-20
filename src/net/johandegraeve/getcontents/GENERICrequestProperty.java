package net.johandegraeve.getcontents;

import java.util.ArrayList;

import net.johandegraeve.easyxmldata.XMLElement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * class for an element that holds a {@link GENERICkey} and (optionally) a {@link GENERICvalue}
 * @author Johan Degraeve
 *
 */
public class GENERICrequestProperty implements XMLElement{
    
    /**
     * the key
     */
    private GENERICkey key;
    /**
     * the value
     */
    private GENERICvalue value;

    /**
     * @return the key
     */
    GENERICkey getKey() {
        return key;
    }

    /**
     * @return the value
     */
    GENERICvalue getValue() {
        return value;
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    /**
     * accepts {@link #key} and {@link #value}
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (child instanceof GENERICkey) {
	    if (key != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICrequestPropertyTag + "can" +
				"only have one child of type" + TagAndAttributeNames.GENERICkeyTag);
	    key = (GENERICkey)child;
	    return;
	}
	if (child instanceof GENERICvalue) {
	    if (value != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICrequestPropertyTag + "can" +
				"only have one child of type" + TagAndAttributeNames.GENERICvalueTag);
	    value = (GENERICvalue)child;
	    return;
	}
	
	throw new SAXException("Element of tpye " + TagAndAttributeNames.GENERICrequestPropertyTag + "can only have " +
			"children of type " + TagAndAttributeNames.GENERICkeyTag + " (mandatory), or " +
			TagAndAttributeNames.GENERICvalueTag +
			" (optional)");
    }

    /**
     * throws an exception 
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
	throw new SAXException("No text allowed for element of type " + TagAndAttributeNames.GENERICrequestPropertyTag);
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    /**
     * throws an exception if {@link #key} is null
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (key == null)
		throw new SAXException("Element of tpye " + TagAndAttributeNames.GENERICrequestPropertyTag + "must have " +
			"children of type " + TagAndAttributeNames.GENERICkeyTag + " (mandatory), and " +
			TagAndAttributeNames.GENERICvalueTag +
			" (optional)");
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
     * @return {@link #key} and {@link #value} in an ArrayList of XMLElement
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement>();
	returnvalue.add(key);
	if (value != null)
		returnvalue.add(value);
	return returnvalue;
    }

    /**
     * @return {@link TagAndAttributeNames#GENERICrequestPropertyTag}
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICrequestPropertyTag;
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
     * @return false
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	return false;
    }

}
