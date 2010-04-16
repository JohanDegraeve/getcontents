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

public class INSTRUCTIONaddXMLDeclaration  extends Instruction implements XMLElement {
    private GENERICversion version;
    private GENERICencoding encoding;
    
    @Override
    String[] execute(String[] source) {
	String[]  returnvalue = new String[(source != null ? source.length:0) + 1];
	returnvalue[0] = "<?xml version=\"" + version.getVersion() + "\" encoding=\"" + encoding.getEncoding() + "\" ?>\n";
	for (int i = 1;i < returnvalue.length;i++)
	    returnvalue[i] = source[i-1];
	return returnvalue;
    }

    String getVersion() {
	return version.getVersion();
    }
    
    String getEncoding() {
	return encoding.getEncoding();
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

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

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (encoding == null ) throw new SAXException("Element of type " + TagAndAttributeNames.INSTRUCTIONaddXMLDeclarationTag +
		" must have a child of type " + TagAndAttributeNames.GENERICencodingTag);
	if (version == null ) throw new SAXException("Element of type " + TagAndAttributeNames.INSTRUCTIONaddXMLDeclarationTag +
		" must have a child of type " + TagAndAttributeNames.GENERICversionTag);
    }

    @Override
    public Attributes getAttributes() {
	return null;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement> ();
	if (version != null) returnvalue.add(version);
	if (encoding != null) returnvalue.add(encoding);
	return returnvalue;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.INSTRUCTIONaddXMLDeclarationTag;
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
