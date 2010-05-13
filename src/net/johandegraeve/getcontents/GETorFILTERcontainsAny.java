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

import com.Ostermiller.util.StringHelper;

/**
 * Filters on nodes that have text that contains any of a list of strings<br>
 * It is possible to define inclusion or exclusion of elements that match.<br>
 * Using com.Ostermiller.util, class StringHelper, method containsAnyIgnoreCase and containsAny
 *
 * @author Johan Degraeve
 *
 */
public class GETorFILTERcontainsAny implements XMLElement,  XMLFilter, StringProcessor {
    
    /**
     * defines if matching elements should be included or excluded
     */
    private boolean include;

    /**
     * case sensitive attribute
     */
    private boolean caseSensitive;

    /**
     * list of strings 
     */
    private ArrayList<GENERICstring> stringChildList;
    
    /**
     * constructor
     */
    public GETorFILTERcontainsAny() {
	stringChildList = new ArrayList<GENERICstring> ();
	caseSensitive = false;
	include = true;
    }

    /**
     * adds case_sensitive attribute, default value = false, adds include attribute, default value = true
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	String[] attrValues = Utilities.getOptionalAttributeValues(
		attributes, 
		new String[] {
			TagAndAttributeNames.case_sensitiveAttribute,
			TagAndAttributeNames.includeAttribute
		}, 
		new String[]  {
			"false",
			"true"
		});
	if (attrValues[0].equalsIgnoreCase("true")) 
	    caseSensitive = true;
	if (attrValues[1].equalsIgnoreCase("true")) 
	    include = true;
	else 
	    include = false;
    }

    /**
     * if child is a string, then the string is added to the list of strings, otherwise an exception is thrown
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child,
		TagAndAttributeNames.genericPrefix,
		 new String []{TagAndAttributeNames.GENERICstringTag },
		TagAndAttributeNames.GETorFILTERcontainsAnyTag);
	stringChildList.add((GENERICstring)child);
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
    }

    /**
     * throws an exception if the list of string is empty
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if(stringChildList.size() == 0) 
	    throw new SAXException("Element of type " + TagAndAttributeNames.GETorFILTERcontainsAnyTag + " should have at least one child of type " +
		    TagAndAttributeNames.GENERICstringTag);
    }

    /**
     * @return the {@link #caseSensitive} and the {@link #include} attribute
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
	attr.addAttribute(null, 
		TagAndAttributeNames.includeAttribute, 
		TagAndAttributeNames.includeAttribute,
		"CDATA", 
		(include ? "true":"false"));
	return attr;
    }

    /**
     * @return the list of string in an ArrayList
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	return new ArrayList<XMLElement> (stringChildList);
    }

    /**
     * @return the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERcontainsAnyTag;
    }

    /**
     * @return null
     * 
     * @see net.johandegraeve.easyxmldata.XMLElement#getText()
     */
    @Override
    public String getText() {
	return null;
    }

    /**
     * get list of strings that match the processor
     * @return an array of Strings that contain any of the strings in {@link #stringChildList}; can be null or an arraylist 
     * with size 0; 
     * @see net.johandegraeve.getcontents.StringProcessor#processString(java.lang.String[])
     */
    @Override
    public String[] processString(String[] source) {
	String[] actualReturnValue = new String[0];
	String[] terms = new String [stringChildList.size()];
	for (int i = 0; i < stringChildList.size();i++)
	    terms[i] = stringChildList.get(i).getText();

	if (source == null) return null;
	ArrayList<String> returnvalue = new ArrayList<String> ();
	for (int i = 0; i < source.length;i++) {
	    if (caseSensitive) {
		if (StringHelper.containsAny(source[i], terms)) {
		    if (include) 
			    returnvalue.add(source[i]);
		} else 
		    if (!include) 
			    returnvalue.add(source[i]);
	    } else
		if (StringHelper.containsAnyIgnoreCase(source[i], terms)) {
		    if (include) 
			    returnvalue.add(source[i]);
		} else 
		    if (!include) 
			    returnvalue.add(source[i]);
	}
	return (String[]) returnvalue.toArray(actualReturnValue);
    }

    /** 
     * does nothing
     * 
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

    /**
     * @return the filter that checks if the text in the XMLElement contains any of the terms
     * @see net.johandegraeve.getcontents.XMLFilter#getXMLFilter()
     */
    @Override
    public XMLElementFilter getXMLFilter() {
	return new XMLElementFilter() {
	    @Override
	    public boolean accept(XMLElement element) {
		String[] terms = new String [stringChildList.size()];
		for (int i = 0; i < stringChildList.size();i++)
		    terms[i] = stringChildList.get(i).getText();
		if (caseSensitive) {
		    boolean returnvalue = (StringHelper.containsAny(element.getText(),terms));
		    if (include)
			return returnvalue;
		    else 
			return !returnvalue;
		}
		else {
		    boolean returnvalue = (StringHelper.containsAnyIgnoreCase(element.getText(),terms));
		    if (include)
			return returnvalue;
		    else 
			return !returnvalue;
		}
	    }

	};
    }

}
