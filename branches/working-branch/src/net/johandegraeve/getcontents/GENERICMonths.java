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

import java.text.DateFormatSymbols;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.Ostermiller.util.StringHelper;

import net.johandegraeve.easyxmldata.XMLElement;

/**
 * represents Months as used in a {@link java.text.DateFormatSymbols DateFormatSymbol}<br>
 *
 * @author Johan Degraeve
 *
 */
public class GENERICMonths implements XMLElement {
    
    /**
     * months
     */
    private String[] Months;
    
    /**
     * @return {@link #Months}
     */
    String[] getMonths() {
	return Months;
    }
    
    /**
     * constructor , setting {@link #Months} to default values as set by default constructor 
     * {@link java.text.DateFormatSymbols#DateFormatSymbols()}
     */
    public GENERICMonths() {
	Months = new DateFormatSymbols().getMonths();
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    /**
     * throws an exception
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GENERICMonthsTag);
    }

    /**
     * expects twelve substrings seperated by &quot;,&quot;, splits, trims and assigns to {@link #Months}
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
	Months = StringHelper.split(text, ",");
	if (Months.length != 12)
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICMonthsTag + " must have text with 12 strings that represent months, separated" + 
		    "by comma. Example : \"January,February,March,April,May,June,July,August,September,October,November,December\"");
	for (int i = 0;i < Months.length;i++)
	    Months[i] = Months[i].trim();
    }
    
    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
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
     * @return null
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	return null;
    }

    /**
     * @return {@link TagAndAttributeNames#GENERICMonthsTag}
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICMonthsTag;
    }

    /**
     * @return the months, seprated by &quot;,&quot;
     * @see net.johandegraeve.easyxmldata.XMLElement#getText()
     */
    @Override
    public String getText() {
	StringBuilder returnvalue  = new StringBuilder();
	int i;
	
	for (i = 0;i < Months.length;i++) {
	    returnvalue.append(Months[i]);
	    returnvalue.append(",");
	}
	if (i > 0)
	    returnvalue.delete(returnvalue.length()-1, returnvalue.length()-1);
	return returnvalue.toString();
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
