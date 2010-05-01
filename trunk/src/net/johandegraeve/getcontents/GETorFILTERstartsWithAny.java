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
 * Filters on nodes that have text that starts with any of a list of strings<br>
 * Using com.Ostermiller.util, class StringHelper, method startsWithAnyIgnoreCase and startsWithAny
 *
 * @author Johan Degraeve
 *
 */
public class GETorFILTERstartsWithAny implements XMLElement,  XMLGetter, StringProcessor {
    
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
    public GETorFILTERstartsWithAny() {
	stringChildList = new ArrayList<GENERICstring> ();
    }

    /**
     * adds case_sensitive attribute, default value = false
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
     * if child is a string, then the string is added to the list of strings, otherwise an exception is thrown
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child,
		TagAndAttributeNames.genericPrefix,
		 new String []{TagAndAttributeNames.GENERICstringTag },
		TagAndAttributeNames.GETorFILTERstartsWithAnyTag);
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
	    throw new SAXException("Element of type " + TagAndAttributeNames.GETorFILTERstartsWithAnyTag + " should have at least one child of type " +
		    TagAndAttributeNames.GENERICstringTag);
    }

    /**
     * @return the case_sensitive attribute
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
	return TagAndAttributeNames.GETorFILTERstartsWithAnyTag;
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
     * get list of elements that match the processor
     * 
     * @return a list of elements that have text that starts with any of the strings in {@link #stringChildList}; can be null or an arraylist 
     * with size 0; if stringList is an {@link XMLXMLGetterResultList}, then a {@link XMLXMLGetterResultList} is returned; if stringList
     * is a {@link StringXMLGetterResultList}, then a {@link StringXMLGetterResultList} is returned;
     */
    @Override
    public GenericXMLGetterResultList getList(GenericXMLGetterResultList list) {
	String[] terms = new String [stringChildList.size()];
	GenericXMLGetterResultList returnvalue = null;

	for (int i = 0; i < stringChildList.size();i++)
	    terms[i] = stringChildList.get(i).getText();
	
	if (list instanceof XMLXMLGetterResultList) {
	     returnvalue = new  XMLXMLGetterResultList();
	}  else if (list instanceof StringXMLGetterResultList) {
	     returnvalue = new  StringXMLGetterResultList();
	}
	
	for (int i = 0;i < list.size();i ++)
	    if (caseSensitive) {
		if (StringHelper.startsWithAny(list.elementAt(i).convertToString(),terms))
		    returnvalue.add(list.elementAt(i));
	    } else {
		if (StringHelper.startsWithAnyIgnoreCase(list.elementAt(i).convertToString(),terms))
		    returnvalue.add(list.elementAt(i));
	    }
	return returnvalue;
    }

    /**
     * get list of strings that match the processor
     * @return an array of Strings that starts with any of the strings in {@link #stringChildList}; can be null or an arraylist 
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
		if (StringHelper.startsWithAny(source[i], terms))
		    returnvalue.add(source[i]);
	    } else
		if (StringHelper.startsWithAnyIgnoreCase(source[i], terms))
		    returnvalue.add(source[i]);
	}
	return (String[]) returnvalue.toArray(actualReturnValue);
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
