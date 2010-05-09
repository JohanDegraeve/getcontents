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

/**
 * represents a {@link java.text.DateFormatSymbols}<br>
 * It is possible to create a {@link GENERICDateFormatSymbols DateFormatSymbols object} with {@link GENERIClocale Locale}, {@link GENERICAmPmStrings AmPmStrings},
 * {@link GENERICEras Eras}, {@link GENERICMonths Months}, {@link GENERICShortMonths ShortMonths}, {@link GENERICWeekDays WeekDays}, {@link GENERICShortWeekDays ShortWeekDays},
 * 
 * 
 * @author Johan Degraeve
 *
 */
public class GENERICDateFormatSymbols implements XMLElement {
    
    /**
     * locale
     */
    private GENERIClocale locale;
    /**
     * AmPmStrings
     */
    private GENERICAmPmStrings amPMStrings;
    /**
     * eras
     */
    private GENERICEras eras;
    /**
     * months
     */
    private GENERICMonths months;
    /**
     * shortMonths
     */
    private GENERICShortMonths shortMonths;
    /**
     * weekDays
     */
    private GENERICWeekDays weekDays;
    /**
     * shortWeekDays
     */
    private GENERICShortWeekDays shortWeekDays;
    
    /**
     * @return the DateFormatSymbol represented by this object
     */
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

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    /**
     * depending on type of child, assigns {@link #locale}, {@link #amPMStrings}, {@link #eras}, {@link #months},
     * {@link #shortMonths}, {@link #weekDays}, {@link #shortWeekDays}
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
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
		TagAndAttributeNames.GENERICWeekDaysTag)) {
	    if (weekDays != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.GENERICDateFormatSymbolsTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICWeekDaysTag);
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

    /**
     * does nothing
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
     * tries to create a {@link java.text.DateFormatSymbols DateFormatSymbols Object}
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	//try to create the dateformatsymbols object to see if no exception is thrown
	try {
	    getDateFormatSymbols();
	} catch (MissingResourceException e) {
	    throw new SAXException("A MissingResourceException was thrown while trying to create a DateFormatSymbols object." + 
		    " Check the contents of the child elements. Here's the result of MissingResourceException.toString() :\n" +
		    e.toString());
	}
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
     * @return {@link #locale}, {@link #eras}, {@link #amPMStrings}, {@link #shortWeekDays}, {@link #weekDays},
     * {@link #shortMonths}, {@link #months} in an ArrayList
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
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

    /**
     * @return {@link TagAndAttributeNames#GENERICDateFormatSymbolsTag}
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICDateFormatSymbolsTag;
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
