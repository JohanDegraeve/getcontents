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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.Ostermiller.util.StringHelper;

/**
 * To read a date and time and to parse, returns the date and time using Date.toString();
 *
 * @author Johan Degraeve
 *
 */
public class STRING_PROCESSORreadDateAndTime implements XMLElement,
	StringProcessor {
    
    /**
     * the simpleDateFormat will be used to parse the date and time
     */
    GENERICSimpleDateFormat simpleDateFormat;

    /**
     * timezone used for parsing the input
     */
    private GENERICTimeZone timeZone;

    /**
     * indicates if the individual source strings should represent a date and time in chronological order
     */
    private boolean chronological;
    
    /**
     * defines the offset to add to the parsed date, for instance when input is something like 1430, offset would be day
     */
    private String offset;
    
    /**
     * valid values for {@link #offset}
     */
    private static final String[] validOffsets = {"","year","month","day","halfday","hour"};
    
    /**
     * constructor setting {@link #simpleDateFormat} to null, {@link #offset} to empty string, {@link #chronological} to true
     */
    public STRING_PROCESSORreadDateAndTime() {
	simpleDateFormat = null;
	offset="";
	chronological=true;
    }
    

    /**
     * reads attributes {@link #offset} and {@link #chronological} with default values respectively empty string 
     * and true
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	String[] attrValues = Utilities.getOptionalAttributeValues(
		attributes, 
		new String[] {
			TagAndAttributeNames.offsetAttribute,
			TagAndAttributeNames.chronologicalAttribute
		}, 
		new String[]  {
			"",
			"true",
		});
	if (StringHelper.equalsAny(attrValues[0],validOffsets))
		offset = attrValues[0];
	else {
	    StringBuilder exceptionString = new StringBuilder();
	    exceptionString.append("Element of type " + TagAndAttributeNames.GENERICSimpleDateFormatTag + " can have an attribute" +
		    " with name "  + TagAndAttributeNames.offsetAttribute + " with one of the following values : \n");
	    for (int i = 0;i < validOffsets.length;i++) {
		exceptionString.append(validOffsets[i] + "\n");
	    }
	    throw new SAXException(exceptionString.toString());
	}
	if (attrValues[1].equalsIgnoreCase("true")) 
	    chronological = true;
	else
	    chronological = false;
    }

    /**
     * if child is not a {@link TagAndAttributeNames#GENERICTimeZoneTag} pr {@link TagAndAttributeNames#GENERICSimpleDateFormatTag}
     * then an exception is thrown<br>
     * 
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child,
		TagAndAttributeNames.genericPrefix,
		 new String []{TagAndAttributeNames.GENERICSimpleDateFormatTag, TagAndAttributeNames.GENERICTimeZoneTag },
		TagAndAttributeNames.STRING_PROCESSORreadDateAndTimeTag);

	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICTimeZoneTag)) {
	    if (timeZone != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.STRING_PROCESSORreadDateAndTimeTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICTimeZoneTag);
	    timeZone = (GENERICTimeZone) child;
	}  else if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICSimpleDateFormatTag)) {
	    if (simpleDateFormat != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.STRING_PROCESSORreadDateAndTimeTag +
			" should have only one child of type " + TagAndAttributeNames.GENERICSimpleDateFormatTag);
	    simpleDateFormat = (GENERICSimpleDateFormat)child;
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
     * throws an exception if {@link #simpleDateFormat} is null
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (simpleDateFormat == null)
	    throw new SAXException("Element of type " + TagAndAttributeNames.STRING_PROCESSORreadDateAndTimeTag + 
		    "must have a child of type " + TagAndAttributeNames.GENERICSimpleDateFormatTag);
    }

    /**
     * @return the attributes {@link #offset}, {@link #chronological}
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.offsetAttribute, TagAndAttributeNames.offsetAttribute, "CDATA", offset);
	attr.addAttribute(null, TagAndAttributeNames.chronologicalAttribute, TagAndAttributeNames.chronologicalAttribute, "CDATA", chronological==true?"true":"false");
	return attr;
    }

    /**
     * @return {@link #simpleDateFormat} in an ArrayList
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue  = new ArrayList<XMLElement>();
	returnvalue.add(simpleDateFormat);
	returnvalue.add(timeZone);
	return returnvalue;
    }

    /**
     * @return {@link TagAndAttributeNames#STRING_PROCESSORreadDateAndTimeTag}
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.STRING_PROCESSORreadDateAndTimeTag;
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

    /**
     * Here's where the actual parsing happens. 
     * @return array of strings with parsed dates, using Date.toString()
     * @throws ParseException can be thrown by  {@link java.text.DateFormat#parse(String source)} 
     * @see net.johandegraeve.getcontents.StringProcessor#processString(java.lang.String[])
     */
    @Override
    public String[] processString(String[] source) throws ParseException {
	DateFormat format = simpleDateFormat.getSimpleDateFormat();
	if (timeZone != null)
	    format.setTimeZone(timeZone.getTimeZone());
	Date previousDate = null;
	Date newDate = null;
	
	if (source == null) return null;
	if (source.length > 0)
		previousDate = format.parse(source[0]);
		source[0] = Long.toString(previousDate.getTime());
	for (int i = 1;i<source.length;i++) {
		newDate = addOffset(format.parse(source[i]),previousDate);
		source[i] = Long.toString(newDate.getTime());
		previousDate = newDate;
	}

	return source;
    }
    
    /**
     * Calculate the real date. For example, parsedDate could be a parsed value of "23:25" today, here in Belgium,
     * wintertime. In that case, parsedDate would correspond to "Thu Jan 01 23:25:00 CET 1970". The method here will change that into today (for example today it's 21st of 
     * March 2010, so the returnvalue would be a Date corresponding to "Sun Mar 21 23:25:00 CET 2010", Date in milliseconds off course, I didn't
     * take the time to calculate how much that would be right now.<br>
     * In this example, offset would need to be set to "day", which means the string "23:25", is a time accurate only on a specific day.<br>
     * Other example : "21 March, 22:00:15", if this would have been parsed, the result would be  "Sat Mar 21 23:25:00 CET 1970". So the offset would need to be
     * set to "year", and the result would then be "Sun Mar 21 23:25:00 CET 2010", so the method will add the current year which is 2010.<br><br>
     * Parameter previousDate is used in conjunction with field "chronological". If chronological is true, then the returnvalue should come after the previousDate.
     * So if previousDate is later, then the method will increase the date before being returned, with a value dependent on the field "offset".
     * 
     * For instance, if previousDate was a value corresponding to "Fri May 21 23:25:00 CET 2010", and if parsedDate would be "Thu Jan 01 23:25:00 CET 1970", then,
     * after having added the necessary offset, the result of parsedDate would be "Sun Mar 21 23:25:00 CET 2010". But "chronological" value of true, means we expect
     * parsedDate to be after previousDate, so a value will be added to previousDate, which is dependent on offset. For instance, if offset = "year" (which would be the
     * case here), then 1 year will be added, resulting in "Mon Mar 21 23:23:00 CET 2011", and now parsedDate is after previousDate, parsedDate will be returned.
     *  
     * @param parsedDate
     * @param previousDate
     * @return new Date
     */
    private Date addOffset(Date parsedDate, Date previousDate) {
	if (offset.equals("")) return parsedDate;
	
	Calendar today = new GregorianCalendar();
	if (timeZone != null)
	    today.setTimeZone(timeZone.getTimeZone());
	today.setTime(new Date());

	Calendar parsedCalendar = new GregorianCalendar();
	if (timeZone != null)
	    parsedCalendar.setTimeZone(timeZone.getTimeZone());
	parsedCalendar.setTime(parsedDate);
	
	//for sure current year will need to be added, since offset is not ""
	parsedCalendar.set(GregorianCalendar.YEAR,today.get(GregorianCalendar.YEAR));
	if (!offset.equals("year")) {
	    parsedCalendar.set(GregorianCalendar.MONTH,today.get(GregorianCalendar.MONTH));
	    if (!offset.equals("month"))
		parsedCalendar.set(GregorianCalendar.DAY_OF_MONTH,today.get(GregorianCalendar.DAY_OF_MONTH));
		if (offset.equals("halfday"))
		    if (today.get(GregorianCalendar.AM_PM) == Calendar.PM)
			parsedCalendar.set(GregorianCalendar.AM_PM,Calendar.PM);
		if (!offset.equals("day"))
		    parsedCalendar.set(GregorianCalendar.HOUR_OF_DAY,today.get(GregorianCalendar.HOUR_OF_DAY));
	}
	if (previousDate != null && chronological) {
	    if (parsedCalendar.getTimeInMillis() < previousDate.getTime()){
		if (offset.equals("year"))
		    parsedCalendar.add(GregorianCalendar.YEAR,1);
		if (offset.equals("month"))
		    parsedCalendar.add(GregorianCalendar.MONTH,1);
		if (offset.equals("day"))
		    parsedCalendar.add(GregorianCalendar.DAY_OF_MONTH,1);
		if (offset.equals("halfday"))
		    parsedCalendar.add(GregorianCalendar.HOUR_OF_DAY,12);
		if (offset.equals("hout"))
		    parsedCalendar.add(GregorianCalendar.HOUR_OF_DAY,1);
	    }
	}
	
	return new Date(parsedCalendar.getTimeInMillis());
	
    }

}
