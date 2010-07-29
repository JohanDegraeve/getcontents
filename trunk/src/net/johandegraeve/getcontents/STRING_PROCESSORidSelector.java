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
import java.util.Iterator;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.Ostermiller.util.StringHelper;

/**
 * Using an array as input (array of strings  or XML elements), keep or filter out a number of elements in the array
 *
 * @author Johan Degraeve
 *
 */
public class STRING_PROCESSORidSelector implements XMLElement,
	XMLGetter, StringProcessor {
    
    /**
     * delimiter to use between id&#146;s
     */
   private static final String delimiter = ",";
    /**
     * to use for range of id&#146;s
     */
    private String rangeDelimiter = ":";
    /**
     * list of id&#146;s
     */
    private ArrayList<Integer> integerList;
    /**
     * if true then elements in list of id&#146;s will be included, otherwise excluded
     */
    private boolean include;

    /**
     * error string in case list of id&146;s is not correctly formatted
     */
    private static final String exceptionString = 
	"Element type " + TagAndAttributeNames.STRING_PROCESSORidSelectorTag + " must have a valid textfield.\n" +
	"Valid text is a list of comma separated range of id's or just single id's, which can be negative.\n" +
	"Example :\n" +
	"\"-10:-5, 1:5, 8:9, 12, 0\".\n" +
	"This would mean : last - 10 to last - 5 inclusive, 1 to 5, 8 to 9, last";
    
    /**
     * the list of id's as found in the xml instructionlist
     */
    private String idSelector;

    /**
     * constructor
     */
    public STRING_PROCESSORidSelector() {
	integerList = new ArrayList<Integer> ();
	include = true;
    }
    
    /**
     * adds attribute include, default value = true
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	String[] attrValues = Utilities.getOptionalAttributeValues(
		attributes, 
		new String[] {
			TagAndAttributeNames.includeAttribute,
		}, 
		new String[]  {
			"true",
		});
	if (attrValues[0].equalsIgnoreCase("true")) 
	    include = true;
	else
	    include = false;
    }

    /**
     * throws an exception
     * 
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.STRING_PROCESSORidSelectorTag);
    }

    /**
     * assigns text to {@link #idSelector}
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
	idSelector = text;
	createIntegerList();
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    /**
     * throws an exception if {@link #idSelector} is null
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (idSelector == null)
	    throw new SAXException(exceptionString);
    }

    /**
     * @return attribute {@link #include} 
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.includeAttribute, TagAndAttributeNames.includeAttribute, "CDATA", include ? "true":"false");
	return attr;
    }

    /**
     * @return null
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	return null;
    }

    /**
     * @return {@link TagAndAttributeNames#STRING_PROCESSORidSelectorTag}
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.STRING_PROCESSORidSelectorTag;
    }

    /**
     * @return {@link #idSelector}
     * @see net.johandegraeve.easyxmldata.XMLElement#getText()
     */
    @Override
    public String getText() {
	return idSelector;
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
     * get list of elements matching the {@link #idSelector} and {@link #include}
     * @return a {@link GenericXMLGetterResultList} with the same type as the input parameter list, with the elements from list 
     * that match the {@link #idSelector} and {@link #include} values, size can be 0
     * @see net.johandegraeve.getcontents.XMLGetter#getList(net.johandegraeve.getcontents.GenericXMLGetterResultList)
     */
    @Override
    public GenericXMLGetterResultList getList(GenericXMLGetterResultList list) {
	int sourceLength = list.size();
	GenericXMLGetterResultList returnvalueArrayList;
	
	if (list instanceof XMLXMLGetterResultList)
	    returnvalueArrayList = new XMLXMLGetterResultList();
	else
	    returnvalueArrayList = new StringXMLGetterResultList();
	
	if (!include)
	    for (int i = 0;i < list.size();i++)
		returnvalueArrayList.add(list.elementAt(i));
	
	for (int i = 1;i < list.size() + 1;i++)
    	    if ((integerList.contains(new Integer(i))) || (integerList.contains(new Integer(i - sourceLength))))
	    		if (include)
	    		    returnvalueArrayList.add(list.elementAt(i-1));
	    		else
	    		    returnvalueArrayList.set( null,i-1);
	if (!include) {
	    for (int i = 0;i < returnvalueArrayList.size() ;i++) {
		if (returnvalueArrayList.elementAt(i) == null) {
		    returnvalueArrayList.remove(i);
		    i--;
		}
	    }
	}
	return returnvalueArrayList;
    }

    /**
     * get list of elements matching the {@link #idSelector} and {@link #include}
     * @return an array of String with the elements from list 
     * that match the {@link #idSelector} and {@link #include} values, size can be 0
     * @see net.johandegraeve.getcontents.StringProcessor#processString(java.lang.String[])
     */
    @Override
    public String[] processString(String[] source) {
	int sourceLength = source.length;
	ArrayList<String> returnvalueArrayList = new ArrayList<String> ();
	
	if (!include)
	    for (int i = 0;i < source.length;i++)
		returnvalueArrayList.add(source[i]);
	
	for (int i = 1;i < source.length + 1;i++)
    	    if ((integerList.contains(new Integer(i))) || (integerList.contains(new Integer(i - sourceLength))))
	    		if (include)
	    		    returnvalueArrayList.add(source[i-1]);
	    		else
	    		    returnvalueArrayList.set(i-1, null);
	if (!include) {
	    Iterator<String> itr = returnvalueArrayList.iterator();
	    while (itr.hasNext()) {
		if (itr.next() == null)
		    itr.remove();
	    }
	}
	return (String[]) returnvalueArrayList.toArray(new String[0]);
    }
    
    /**
     * creates IntegerList based in value of {@link #idSelector}, throws an exception if format of {@link #idSelector} is invalid.
     * @throws SAXException
     */
    private void createIntegerList() throws SAXException {
	String[] split2;
	String[] split1;
	
	//check if text is valid format
	if (idSelector == null || idSelector.length() == 0)
	    throw new SAXException(exceptionString);
	split1 = StringHelper.split(idSelector, delimiter);
	if (split1.length == 0)
	    throw new SAXException(exceptionString);
	for (int i = 0;i < split1.length;i++) {
	    split2 = StringHelper.split(split1[i], rangeDelimiter);
	    if (split2.length == 0 || split2.length > 2) {
		throw new SAXException(exceptionString);
	    }
	    if (split2.length ==2 ) {
		if (!(Integer.parseInt(split2[0].trim()) < Integer.parseInt(split2[1].trim())))
		    throw new SAXException(exceptionString);
	    for (int j = Integer.parseInt(split2[0].trim());j < Integer.parseInt(split2[1].trim()) + 1;j++)
		try {
		    integerList.add(new Integer(j));
		} catch (NumberFormatException e) {
		    throw new SAXException(exceptionString);
		}
	    } else {
		try {
		    integerList.add(new Integer(split2[0].trim()));
		} catch (NumberFormatException e) {
		    throw new SAXException(exceptionString);
		}
	    }
	}
    }

}
