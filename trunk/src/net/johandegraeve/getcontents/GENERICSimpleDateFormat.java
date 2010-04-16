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
import java.util.Date;
import java.util.Locale;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.Ostermiller.util.StringHelper;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

public class GENERICSimpleDateFormat implements XMLElement {
    
    private GENERICsimpleDateFormatPattern pattern;
    private GENERICDateFormatSymbols symbols;
    private GENERIClocale locale;

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    SimpleDateFormat getSimpleDateFormat() {
	if (pattern == null) return new SimpleDateFormat();
	if (symbols != null) return new SimpleDateFormat(pattern.getPattern(),symbols.getDateFormatSymbols());
	if (locale != null) return new SimpleDateFormat(pattern.getPattern(),locale.getLocal());
	return new SimpleDateFormat(pattern.getPattern());
    }
    
    Locale getLocale() {
	return locale.getLocal();
    }
    
    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICsimpleDateFormatPatternTag)) {
	    if (symbols != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICSimpleDateFormatTag +
			pattern	+ " should have only one child of type " + TagAndAttributeNames.GENERICsimpleDateFormatPatternTag);
	    pattern = (GENERICsimpleDateFormatPattern) child;
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
		TagAndAttributeNames.GENERIClocaleTag)) {
	    if (locale != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICSimpleDateFormatTag +
			" should have only one child of type " + TagAndAttributeNames.GENERIClocaleTag);
	    locale = (GENERIClocale) child;
	    return;
	}
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICSimpleDateFormatTag +
		    " can only have " + 
		    TagAndAttributeNames.GENERICsimpleDateFormatPatternTag + 
		    " or " +
		    TagAndAttributeNames.GENERICDateFormatSymbolsTag + 
		    " or " +
		    TagAndAttributeNames.GENERIClocaleTag + 
		    " as child.");
	
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	String exceptionString = "Element of type " + TagAndAttributeNames.GENERICSimpleDateFormatTag + 
	"can have following children :\n" +
	" - no children, in that case the Default constructor will be used to create a SimpleDateFormat object\n" +
	" - one child of type " + TagAndAttributeNames.GENERICsimpleDateFormatPatternTag + ". In this case a SimpleDateFormat" +
	" object will be created with the constructor SimpleDateFormat(String pattern).\n" +
	" - one child of type "  + TagAndAttributeNames.GENERICsimpleDateFormatPatternTag + " and one child of type "  + 
	TagAndAttributeNames.GENERICDateFormatSymbolsTag + ". Here a SimpleDateFormat" +
	" object will be created with the constructor SimpleDateFormat(String pattern, DateFormatSymbols formatSymbols.\n" +
	" - one child of type "  + TagAndAttributeNames.GENERICsimpleDateFormatPatternTag + " and one child of type "  + 
	TagAndAttributeNames.GENERIClocaleTag + ". Here a SimpleDateFormat" +
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

    @Override
    public Attributes getAttributes() { return null; }

    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement>();
	if (locale != null) returnvalue.add(locale);
	if (symbols != null) returnvalue.add(symbols);
	if (pattern != null) returnvalue.add(pattern);
	return returnvalue;
    }

    @Override
    public String getTagName() {
	// XXX Auto-generated method stub
	return TagAndAttributeNames.GENERICSimpleDateFormatTag;
    }

    @Override
    public String getText() {
	return null;
    }

    @Override
    public boolean preserveSpaces() {
	return false;
    }

}
