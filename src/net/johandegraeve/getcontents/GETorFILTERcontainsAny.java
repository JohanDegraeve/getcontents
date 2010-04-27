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

import org.htmlparser.Node;
import org.htmlparser.util.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.Ostermiller.util.StringHelper;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

public class GETorFILTERcontainsAny implements XMLElement,  XMLGetter, StringProcessor {
    
    private boolean caseSensitive;

    private ArrayList<GENERICstring> stringChildList;
    
    public GETorFILTERcontainsAny() {
	stringChildList = new ArrayList<GENERICstring> ();
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
	Utilities.verifyChildType(child,
		TagAndAttributeNames.genericPrefix,
		 new String []{TagAndAttributeNames.GENERICstringTag },
		TagAndAttributeNames.GETorFILTERcontainsAnyTag);
	stringChildList.add((GENERICstring)child);
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if(stringChildList.size() == 0) 
	    throw new SAXException("Element of type " + TagAndAttributeNames.GETorFILTERcontainsAnyTag + " should have at least one child of type " +
		    TagAndAttributeNames.GENERICstringTag);
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
	return new ArrayList<XMLElement> (stringChildList);
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERcontainsAnyTag;
    }

    @Override
    public String getText() {
	return null;
    }

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
		if (StringHelper.containsAny(list.elementAt(i).convertToString(),terms))
		    returnvalue.add(list.elementAt(i));
	    } else {
		if (StringHelper.containsAnyIgnoreCase(list.elementAt(i).convertToString(),terms))
		    returnvalue.add(list.elementAt(i));
	    }
	return returnvalue;
	
    }

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
		if (StringHelper.containsAny(source[i], terms))
		    returnvalue.add(source[i]);
	    } else
		if (StringHelper.containsAnyIgnoreCase(source[i], terms))
		    returnvalue.add(source[i]);
	}
	return (String[]) returnvalue.toArray(actualReturnValue);
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
