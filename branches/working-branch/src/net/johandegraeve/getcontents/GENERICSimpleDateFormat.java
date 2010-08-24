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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * class for SimpleDateFormat element
 *
 * @author Johan Degraeve
 *
 */
public class GENERICSimpleDateFormat implements XMLElement {
    
    /**
     * the pattern
     */
    private GENERICSimpleDateFormatPattern pattern;
    /**
     * the symbols
     */
    private GENERICDateFormatSymbols symbols;
    /**
     * the locale
     */
    private GENERICLocale locale;

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    /**
     * @return the SimpleDateFormat stored to this element
     */
    SimpleDateFormat getSimpleDateFormat() {
	if (pattern == null) return new SimpleDateFormat();
	if (symbols != null) return new SimpleDateFormat(pattern.getPattern(),symbols.getDateFormatSymbols());
	if (locale != null) return new SimpleDateFormat(pattern.getPattern(),locale.getLocal());
	return new SimpleDateFormat(pattern.getPattern());
    }
    
    /**
     * @return the locale stored in {@link #locale}
     */
    Locale getLocale() {
	return locale.getLocal();
    }
    
    /**
     * throws an exception if child is not {@link #locale}, {@link #pattern}, {@link #symbols}, otherwise assignes corresponding variable
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICSimpleDateFormatPatternTag)) {
	    if (symbols != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICSimpleDateFormatTag +
			pattern	+ " should have only one child of type " + TagAndAttributeNames.GENERICSimpleDateFormatPatternTag);
	    pattern = (GENERICSimpleDateFormatPattern) child;
	    return;
	}
	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICDateFormatSymbolsTag)) {
	    if (symbols != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICSimpleDateFormatTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICDateFormatSymbolsTag);
	    symbols = (GENERICDateFormatSymbols) child;
	    return;
	}
	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICLocaleTag)) {
	    if (locale != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICSimpleDateFormatTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICLocaleTag);
	    locale = (GENERICLocale) child;
	    return;
	}
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICSimpleDateFormatTag +
		    " can only have " + 
		    TagAndAttributeNames.GENERICSimpleDateFormatPatternTag + 
		    " or " +
		    TagAndAttributeNames.GENERICDateFormatSymbolsTag + 
		    " or " +
		    TagAndAttributeNames.GENERICLocaleTag + 
		    " as child.");
	
    }

    /**
     *  does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    /**
     * throws an exception if combination of children is not correct, should be as allowed formats of simpleDateFormat constructor
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	String exceptionString = "Element of type " + TagAndAttributeNames.GENERICSimpleDateFormatTag + 
	" can have following children :\n" +
	" - no children, in that case the Default constructor will be used to create a SimpleDateFormat object\n" +
	" - one child of type " + TagAndAttributeNames.GENERICSimpleDateFormatPatternTag + ". In this case a SimpleDateFormat" +
	" object will be created with the constructor SimpleDateFormat(String pattern).\n" +
	" - one child of type "  + TagAndAttributeNames.GENERICSimpleDateFormatPatternTag + " and one child of type "  + 
	TagAndAttributeNames.GENERICDateFormatSymbolsTag + ". Here a SimpleDateFormat" +
	" object will be created with the constructor SimpleDateFormat(String pattern, DateFormatSymbols formatSymbols.\n" +
	" - one child of type "  + TagAndAttributeNames.GENERICSimpleDateFormatPatternTag + " and one child of type "  + 
	TagAndAttributeNames.GENERICLocaleTag + ". Here a SimpleDateFormat" +
	" object will be created with the constructor SimpleDateFormat(String pattern, Locale locale.\n" +
	"Other combinations are not allowed.";
	if (symbols != null) {
	    if (pattern == null) {
		throw new SAXException(exceptionString);
	    } else {
		if (locale != null)
		    throw new SAXException(exceptionString);
	    }
	} else {
	    if (locale != null)
		if (pattern == null)
		    throw new SAXException(exceptionString);
	}
	
	//try to create the simpledateformatobject, if constructor throws an exceptino, throw a new saxexception
	try {
	    getSimpleDateFormat();
	} catch (IllegalArgumentException e) {
	    throw new SAXException("An IllegalArgumentException was thrown while trying to create a SimpleDateFormat object." + 
		    " Check the contents of the child elements. Here's a the result of IllegalArgumentException.toString() :\n" +
		    e.toString());
	}
    }

    /**
     * @return null
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() { return null; }

    /**
     * @return {@link #locale}, {@link #symbols}, {@link #pattern} in an ArrayList
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement>();
	if (locale != null) returnvalue.add(locale);
	if (symbols != null) returnvalue.add(symbols);
	if (pattern != null) returnvalue.add(pattern);
	return returnvalue;
    }

    /**
     * @return {@link TagAndAttributeNames#GENERICSimpleDateFormatTag}
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	// XXX Auto-generated method stub
	return TagAndAttributeNames.GENERICSimpleDateFormatTag;
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
     * @return false
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	return false;
    }

}
