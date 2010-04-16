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

public class STRING_PROCESSORfindAndReplace implements XMLElement, StringProcessor {
    
    private GENERICfind find;
    private GENERICreplace replace;
    private boolean caseSensitive;


	    public String[] processString(String[] source) {
		if (source == null) return null;
		for (int i = 0;i < source.length;i ++)
		    if (caseSensitive)
			source[i] = StringHelper.replace(source[i], find.getFind(), replace.getReplace());
		    else 
			source[i] = net.johandegraeve.getcontents.Utilities.replaceIgnoreCase(source[i], find.getFind(), replace.getReplace());
		return source;
	    }

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

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (find == null)
	    throw new SAXException("Element of type " + TagAndAttributeNames.STRING_PROCESSORfindAndReplaceTag + " must have a child of type " +
		    TagAndAttributeNames.GENERICfindTag);
	if (replace == null)
	    throw new SAXException("Element of type " + TagAndAttributeNames.STRING_PROCESSORfindAndReplaceTag + " must have a child of type " +
		    TagAndAttributeNames.GENERICreplaceTag);
    }

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


    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement>  returnvalue = new ArrayList<XMLElement> ();
	returnvalue.add(find);
	returnvalue.add(replace);
	return returnvalue;
    }


    @Override
    public String getTagName() {
	return TagAndAttributeNames.STRING_PROCESSORfindAndReplaceTag;
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
