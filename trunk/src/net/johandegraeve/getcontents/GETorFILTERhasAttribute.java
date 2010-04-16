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

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/**
 * Class for creating hasattributefilter as defined in {@link org.htmlparser.filters.HasAttributeFilter}.<br>
 * {@link org.htmlparser.filters.HasAttributeFilter#mAttribute}  is case-<b>in<b>sensitive, and 
 * {@link org.htmlparser.filters.HasAttributeFilter#mValue} is case-sensitive.<br>
 * {@link org.htmlparser.filters.HasAttributeFilter#mAttribute} is a mandatory child of type 
 * {@link TagAndAttributeNames#GENERICattributenameTag},
 * {@link org.htmlparser.filters.HasAttributeFilter#mValue} is an optional child of type 
 * {@link TagAndAttributeNames#GENERICattributevalueTag}.
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class GETorFILTERhasAttribute implements XMLElement, HTMLFilter {

    private GENERICattributename attrName;
    private GENERICattributevalue attrValue;
    
    private String test;
    
    public GETorFILTERhasAttribute() {
	attrName = null;
	attrValue = null;
    }

    @Override
    public NodeFilter getHTMLFilter() {
	return new HasAttributeFilter(
		attrName.getAttributeName().toUpperCase(),
		(attrValue != null ? attrValue.getAttributeValue() : null));
    }

    @Override
    public void addAttributes(Attributes arg0) throws SAXException {
    }

    @Override
    public void addChild(XMLElement arg0) throws SAXException {
	Utilities.verifyChildType(arg0, 
		new String []{
	    		TagAndAttributeNames.genericPrefix,
	    		TagAndAttributeNames.genericPrefix
		},  
		new String []{
		    TagAndAttributeNames.GENERICattributenameTag,
		    TagAndAttributeNames.GENERICattributevalueTag
		}, 
		TagAndAttributeNames.GETorFILTERhasAttributeTag);
	
	if (Utilities.getClassname(arg0.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICattributevalueTag)) {
	    if (attrValue != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GETorFILTERhasAttributeTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICattributevalueTag);
	    attrValue = (GENERICattributevalue) arg0;
	} else if (Utilities.getClassname(arg0.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICattributenameTag)) {
	    if (attrName != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GETorFILTERhasAttributeTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICattributenameTag);
	    attrName = (GENERICattributename) arg0;
	}
    } 

    @Override
    public void addText(String arg0) throws SAXException {
	test = arg0;
    }

    @Override
    public void complete() throws SAXException {
	if (attrName == null)
	    throw new SAXException("Element " + TagAndAttributeNames.GETorFILTERhasAttributeTag +
		    " must have a " + TagAndAttributeNames.GENERICattributenameTag + "  child element");
    }

    @Override
    public Attributes getAttributes() {
	return null;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement> ();
	if (attrName != null) returnvalue.add(attrName);
	if (attrValue != null) returnvalue.add(attrValue);
	return returnvalue;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERhasAttributeTag;
    }

    @Override
    public String getText() {
	return test;
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
