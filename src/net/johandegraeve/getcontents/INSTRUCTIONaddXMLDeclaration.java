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

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/**
 * instruction to add an XML Declaration
 *
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONaddXMLDeclaration  extends Instruction implements XMLElement {
    /**
     * the version
     */
    private GENERICversion version;
    /**
     * the encoding
     */
    private GENERICencoding encoding;
    
    /**
     * adds an XML declaration with version and encoding
     * @return null if source = null, a new String array with one additional String as first string containing the
     * XML declaration
     * @see net.johandegraeve.getcontents.Instruction#execute(java.lang.String[], Logger)
     */
    @Override
    String[] execute(String[] source, Logger logger) {
	String[]  returnvalue = new String[(source != null ? source.length:0) + 1];
	returnvalue[0] = "<?xml version=\"" + version.getVersion() + "\" encoding=\"" + encoding.getEncoding() + "\" ?>\n";
	for (int i = 1;i < returnvalue.length;i++)
	    returnvalue[i] = source[i-1];
	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in addXMLDeclaration");
	}
	
	return returnvalue;
    }

    /**
     * get the version
     * @return the version
     */
    String getVersion() {
	return version.getVersion();
    }
    
    /**
     * get the encoding
     * @return return the encoding
     */
    String getEncoding() {
	return encoding.getEncoding();
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    /**
     * accepts version and encoding as child and assigns them<br>
     * throws an exception if any other child received
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child, 
		new String []{
	    		TagAndAttributeNames.genericPrefix,
	    		TagAndAttributeNames.genericPrefix
		},  
		new String []{
		    TagAndAttributeNames.GENERICencodingTag,
		    TagAndAttributeNames.GENERICversionTag
		}, 
		TagAndAttributeNames.INSTRUCTIONaddXMLDeclarationTag);
	
	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICencodingTag)) {
	    if (encoding != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.INSTRUCTIONaddXMLDeclarationTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICencodingTag);
	    encoding = (GENERICencoding) child;
	} else if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICversionTag)) {
	    if (version != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.INSTRUCTIONaddXMLDeclarationTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICversionTag);
	    version = (GENERICversion) child;
	}
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
    }

    /**
     * throws an exception of encoding or version = null
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (encoding == null ) throw new SAXException("Element of type " + TagAndAttributeNames.INSTRUCTIONaddXMLDeclarationTag +
		" must have a child of type " + TagAndAttributeNames.GENERICencodingTag);
	if (version == null ) throw new SAXException("Element of type " + TagAndAttributeNames.INSTRUCTIONaddXMLDeclarationTag +
		" must have a child of type " + TagAndAttributeNames.GENERICversionTag);
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
     * @return an XMLELement list with version and encoding
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement> ();
	if (version != null) returnvalue.add(version);
	if (encoding != null) returnvalue.add(encoding);
	return returnvalue;
    }

    /**
     * the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.INSTRUCTIONaddXMLDeclarationTag;
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
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    /**
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	// XXX Auto-generated method stub
	return false;
    }

}
