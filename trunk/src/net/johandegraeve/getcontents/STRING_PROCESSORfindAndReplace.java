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

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
* Find and Replace<br>
* the find and replace string can include carriage return, line feed, ...
* Using {@link com.Ostermiller.util.StringHelper#replace(String, String, String)}
 *
 * @author Johan Degraeve
 *
 */
public class STRING_PROCESSORfindAndReplace implements XMLElement, StringProcessor {
    
    /**
     * the string to find
     */
    private GENERICfind find;
    /**
     * the replacement string
     */
    private GENERICreplace replace;
    /**
     * case sensitive attribute
     */
    private boolean caseSensitive;


    /**
     * replaces in each string in source, find by replace
     * @return the source with {@link #find} replaced by {@link #replace}
     * @see net.johandegraeve.getcontents.StringProcessor#processString(java.lang.String[])
     */
    public String[] processString(String[] source) {
	if (source == null) return null;
	for (int i = 0;i < source.length;i ++)
	    if (caseSensitive) {
		source[i] = source[i].replaceAll(find.getFind(), replace.getReplace());
		//source[i] = StringHelper.replace(source[i], find.getFind(), replace.getReplace());
	    }
	    else {
		source[i] = net.johandegraeve.getcontents.Utilities.replaceIgnoreCase(source[i], find.getFind(), replace.getReplace());
	    }
	return source;
    }

    /**
     * adds the case sensitive attribute, default value = false
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	String[] attrValues = Utilities.getOptionalAttributeValues(
		attributes, 
		new String[] {
			TagAndAttributeNames.case_sensitiveAttribute,
		}, 
		new String[]  {
			"false",
		});
	if (attrValues[0].equalsIgnoreCase("true")) 
	    caseSensitive = true;
    }

    /**
     * assigns child to find or replace or throws exception if another child is present 
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(
		child, 
		TagAndAttributeNames.genericPrefix, 
		new String[] {TagAndAttributeNames.GENERICfindTag, TagAndAttributeNames.GENERICreplaceTag}, 
		TagAndAttributeNames.STRING_PROCESSORfindAndReplaceTag);
	if (child instanceof GENERICfind)
	    find = (GENERICfind)child;
	if (child instanceof GENERICreplace)
	    replace = (GENERICreplace)child;
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
    }

    /**
     * throws an exception if find or replace is empty
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (find == null)
	    throw new SAXException("Element of type " + TagAndAttributeNames.STRING_PROCESSORfindAndReplaceTag + " must have a child of type " +
		    TagAndAttributeNames.GENERICfindTag);
	if (replace == null)
	    throw new SAXException("Element of type " + TagAndAttributeNames.STRING_PROCESSORfindAndReplaceTag + " must have a child of type " +
		    TagAndAttributeNames.GENERICreplaceTag);
    }

    /**
     * @return the case sensitive attribute
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, 
		TagAndAttributeNames.case_sensitiveAttribute, 
		TagAndAttributeNames.case_sensitiveAttribute, 
		"CDATA", 
		(caseSensitive ? "true":"false"));
	return attr;
    }


    /**
     * @return find and replace in an arraylist
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement>  returnvalue = new ArrayList<XMLElement> ();
	returnvalue.add(find);
	returnvalue.add(replace);
	return returnvalue;
    }


    /**
     * @return the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.STRING_PROCESSORfindAndReplaceTag;
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
     * @return false
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	return false;
    }
}
