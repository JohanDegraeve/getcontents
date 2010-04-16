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
import java.util.MissingResourceException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

public class GENERICDateFormatSymbols implements XMLElement {
    
    private GENERIClocale locale;
    private GENERICAmPmStrings amPMStrings;
    private GENERICEras eras;
    private GENERICMonths months;
    private GENERICShortMonths shortMonths;
    private GENERICWeekDays weekDays;
    private GENERICShortWeekDays shortWeekDays;
    
    DateFormatSymbols getDateFormatSymbols() {
	DateFormatSymbols returnvalue;
	if (locale == null) 
	    returnvalue = new DateFormatSymbols();
	else
	    returnvalue = new DateFormatSymbols(locale.getLocal());
	if (eras != null) returnvalue.setEras(eras.getEras());
	if (amPMStrings != null) returnvalue.setAmPmStrings(amPMStrings.getAmPmStrings());
	if (shortWeekDays != null) returnvalue.setShortWeekdays(shortWeekDays.getShortWeekDays());
	if (weekDays != null) returnvalue.setWeekdays(weekDays.getWeekDays());
	if (shortMonths != null) returnvalue.setShortMonths(shortMonths.getShortMonths());
	if (months != null) returnvalue.setMonths(months.getMonths());
	return returnvalue;    
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERIClocaleTag)) {
	    if (locale != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICDateFormatSymbolsTag +
			" should have only one child of type " + TagAndAttributeNames.GENERIClocaleTag);
	    locale = (GENERIClocale) child;
	    return;
	}
	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICAmPmStringsTag)) {
	    if (amPMStrings != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICDateFormatSymbolsTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICAmPmStringsTag);
	    amPMStrings = (GENERICAmPmStrings) child;
	    return;
	}
	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICErasTag)) {
	    if (eras != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICDateFormatSymbolsTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICErasTag);
	    eras = (GENERICEras) child;
	    return;
	}
	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICMonthsTag)) {
	    if (months != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICDateFormatSymbolsTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICMonthsTag);
	    months = (GENERICMonths) child;
	    return;
	}
	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICShortMonthsTag)) {
	    if (shortMonths != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICDateFormatSymbolsTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICShortMonthsTag);
	    shortMonths = (GENERICShortMonths) child;
	    return;
	}
	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICSWeekDaysTag)) {
	    if (weekDays != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICDateFormatSymbolsTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICSWeekDaysTag);
	    weekDays = (GENERICWeekDays) child;
	    return;
	}
	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICShortWeekDaysTag)) {
	    if (shortWeekDays != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICDateFormatSymbolsTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICShortWeekDaysTag);
	    shortWeekDays = (GENERICShortWeekDays) child;
	    return;
	}
	
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	//try to create the dateformatsymbols object to see if no exception is thrown
	try {
	    getDateFormatSymbols();
	} catch (MissingResourceException e) {
	    throw new SAXException("An MissingResourceException was thrown while trying to create a DateFormatSymbols object." + 
		    " Check the contents of the child elements. Here's the result of MissingResourceException.toString() :\n" +
		    e.toString());
	}
    }

    @Override
    public Attributes getAttributes() {
	return null;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement>();
	if (locale != null) returnvalue.add(locale);
	if (eras != null) returnvalue.add(eras);
	if (amPMStrings != null) returnvalue.add(amPMStrings);
	if (shortWeekDays != null) returnvalue.add(shortWeekDays);
	if (weekDays != null) returnvalue.add(weekDays);
	if (shortMonths != null) returnvalue.add(shortMonths);
	if (months != null) returnvalue.add(months);
	return returnvalue;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICDateFormatSymbolsTag;
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
