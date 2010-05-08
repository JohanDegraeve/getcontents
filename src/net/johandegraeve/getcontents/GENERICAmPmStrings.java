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
 * AmPmStrings as defined in {@link java.text.DateFormatSymbols#setAmPmStrings(String[])}
 *
 * @author Johan Degraeve
 *
 */
public class GENERICAmPmStrings implements XMLElement {
    
    /**
     * the AmPmStrings
     */
    String[] AmPmStrings;
    
    /**
     * @return {@link #AmPmStrings}
     */
    String[] getAmPmStrings() {
	return AmPmStrings;
    }
    
    /**
     * constructor , setting {@link #AmPmStrings} to default values as set by default constructor 
     * {@link java.text.DateFormatSymbols#DateFormatSymbols()}
     */
    public GENERICAmPmStrings() {
	AmPmStrings = new DateFormatSymbols().getAmPmStrings();
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
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GENERICAmPmStringsTag);
    }

    /**
     * splits text in two parts using &quot;,&quot; fas separator, expects two fields, first field a string representing &quot;am&quot;, 
     * second field a string representing &quot;pm&quot;<br>
     * <br>
     * After splitting the text, the individiual strings are trimmed using {@link java.lang.String#trim()}
     * If there are less or more than two fields then an exception is thrown.
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
	AmPmStrings = StringHelper.split(text, ",");
	if (AmPmStrings.length != 2)
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICAmPmStringsTag + " must have text with am string and pm string separated" + 
		    "by comma. Example : \"AM,PM\"");
	AmPmStrings[0] = AmPmStrings[0].trim();
	AmPmStrings[1] = AmPmStrings[1].trim();
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
     * @return {@link TagAndAttributeNames#GENERICAmPmStringsTag}
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICAmPmStringsTag;
    }

    /**
     * @return the AmPmStrings, concatenated to one string with a &quot;'&quot; in between
     * @see net.johandegraeve.easyxmldata.XMLElement#getText()
     */
    @Override
    public String getText() {
	return AmPmStrings[0] + "," + AmPmStrings[1];
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
