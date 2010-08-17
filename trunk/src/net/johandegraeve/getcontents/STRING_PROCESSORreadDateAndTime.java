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
 * To read a date and time and to parse, returns the date and time using Date.toString();<br>
 *
 * The attribute &quot;offset&quot; can be used to add for instance the current year, year and month, year and month and day, ..
 * This is useful in case the input contains date and time which are not fully qualified, for instance 15:23,10:23, 9:24 .. in which case
 * you should set offset=day. The current date and time will always be used to correct the values.<br>
 * 
 * The attribute &quot;chronology&quot; can be used to correct values, for instance in the above example 15:23, 10,23, 9:34 .. assume the list
 * also has values off the day before ..22:30, 15:30 .. With chronology, it is possible to correct the two last values, so that they get the 
 * correct date, namely the day before the current date.<br>
     * Example, assume the source represents following list of dates and times: (represented in a readable format, the actual source is an array of long<br>
     * 2010 08 16 11:10<br> 
     * 2010 08 16 08:05<br> 
     * 2010 08 16 22:50<br> 
     * 2010 08 16 22:10<br> 
     * 2010 08 16 15:10<br> 
     * 2010 08 16 20:10<br>
     * This could be the result of parsing an input with following dates, whereby the source only contained Hours and minutes (HH:mm)<br>  
     * 2010 08 16 11:10<br> 
     * 2010 08 16 08:05<br> 
     * 2010 08 15 22:50<br> 
     * 2010 08 15 22:10<br> 
     * 2010 08 15 15:10<br> 
     * 2010 08 14 20:10<br>
     * But because we assume the dates are chronological (descending in this case) we should be able to correct the source (ie the list where the date is always 
     * 16th of August, the date that the input was parsed<br>
     * This is what happens here. There are some prerequisites :<br>
     * - there should be no gaps in the source, larger than the {@link #offset}. Example assume the input list is : <br> 
     * 2010 08 16 11:10<br> 
     * 2010 08 16 08:05<br> 
     * 2010 08 16 22:50<br> 
     * 2010 08 16 22:10<br> 
     * 2010 08 16 20:10<br>
     * then the result after applying the method here would be <br>
     * 2010 08 16 11:10<br> 
     * 2010 08 16 08:05<br> 
     * 2010 08 15 22:50<br> 
     * 2010 08 15 22:10<br> 
     * 2010 08 15 20:10<br>
     * We see that the last date is set to 15, and not 14 as in the previous example<br>
     * - another prerequisite is that for descending chronology, the first timeStamp is correct, for ascending chronology, the last timeStamp is correct<br>
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
     * indicates the chronology to apply, none (null), &quot;ascending&quot; or &quot;descending&quot;
     */
    private String chronology;
    
    /**
     * defines the offset to add to the parsed date, for instance when input is something like 1430, offset would be day
     */
    private String offset;
    
    /**
     * valid values for {@link #offset}
     */
    private static final String[] validOffsets = {"","year","month","day","halfday","hour"};
    
    /**
     * constructor setting {@link #simpleDateFormat} to null, {@link #offset} to empty string, {@link #chronology} to null
     */
    public STRING_PROCESSORreadDateAndTime() {
	simpleDateFormat = null;
	offset="";
	chronology=null;
    }
    

    /**
     * reads attributes {@link #offset} and {@link #chronology} with default values respectively empty string 
     * and true
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	String[] attrValues = Utilities.getOptionalAttributeValues(
		attributes, 
		new String[] {
			TagAndAttributeNames.offsetAttribute,
			TagAndAttributeNames.chronologyAttribute
		}, 
		new String[]  {
			"",
			"",
		});
	if (StringHelper.equalsAny(attrValues[0],validOffsets))
		offset = attrValues[0];
	else {
	    StringBuilder exceptionString = new StringBuilder();
	    exceptionString.append("Element of type " + TagAndAttributeNames.STRING_PROCESSORreadDateAndTimeTag + " can have an attribute" +
		    " with name "  + TagAndAttributeNames.offsetAttribute + " with one of the following values : \n");
	    for (int i = 0;i < validOffsets.length;i++) {
		exceptionString.append(validOffsets[i] + "\n");
	    }
	    throw new SAXException(exceptionString.toString());
	}
	if (attrValues[1].equalsIgnoreCase("")) 
	    chronology = null;
	else if (attrValues[1].equalsIgnoreCase(TagAndAttributeNames.ascendingAttributeName))
	    chronology = TagAndAttributeNames.ascendingAttributeName;
	else if (attrValues[1].equalsIgnoreCase(TagAndAttributeNames.descendingAttributeName))
	    chronology = TagAndAttributeNames.descendingAttributeName;
	else 
	    throw new SAXException("ELement of type " + TagAndAttributeNames.STRING_PROCESSORreadDateAndTimeTag + " can have an attribute" +
		    " with name "  + TagAndAttributeNames.chronologyAttribute + " with one of the values " + TagAndAttributeNames.ascendingAttributeName
		    + " or " + TagAndAttributeNames.descendingAttributeName);
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
	if (chronology != null && offset.equalsIgnoreCase(""))
	    throw new SAXException("Element of type " + TagAndAttributeNames.STRING_PROCESSORreadDateAndTimeTag + " :" +
	    		" if chronology attribute is present then there must be a valid attributevalue for attribute " + TagAndAttributeNames.offsetAttribute);
    }

    /**
     * @return the attributes {@link #offset}, {@link #chronology}
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.offsetAttribute, TagAndAttributeNames.offsetAttribute, "CDATA", offset);
	if (chronology != null)
	    attr.addAttribute(null , 
		    TagAndAttributeNames.chronologyAttribute, 
		    TagAndAttributeNames.chronologyAttribute, 
		    "CDATA", 
		    chronology);
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
	
	if (source == null) return null;
	
	long[] timeStamps = new long[source.length];
	for (int i = 0;i<source.length;i++) {
	    timeStamps[i] = format.parse(source[i]).getTime();
	    timeStamps[i] = addOffset(timeStamps[i]);
	}
	
	if (chronology != null) {
		timeStamps = correctTimeStamp(timeStamps,chronology.equalsIgnoreCase(TagAndAttributeNames.ascendingAttributeName));
	}

	//convert all results to string
	String[] returnvalue = new String[timeStamps.length];
	for (int i = 0;i < returnvalue.length;i++)
	    returnvalue[i] = Long.toString(timeStamps[i]);
	return returnvalue;

    }
    
    /**
     * Calculate the real date. For example, parsedDate could be a parsed value of "23:25" today, here in Belgium,
     * wintertime. In that case, parsedDate would correspond to "Thu Jan 01 23:25:00 CET 1970". The method here will change that into today (for example today it's 21st of 
     * March 2010, so the returnvalue would be a Date corresponding to "Sun Mar 21 23:25:00 CET 2010", Date in milliseconds off course, I didn't
     * take the time to calculate how much that would be right now.<br>
     * In this example, offset would need to be set to "day", which means the string "23:25", is a time accurate only on a specific day.<br>
     * Other example : "21 March, 22:00:15", if this would have been parsed, the result would be  "Sat Mar 21 23:25:00 CET 1970". So the offset would need to be
     * set to "year", and the result would then be "Sun Mar 21 23:25:00 CET 2010", so the method will add the current year which is 2010.<br><br>
     *  
     * @param parsedDate
     * @return new Date in a long
     */
    private long addOffset(long parsedDate) {
	if (offset.equals("")) return parsedDate;
	
	//get today in Calendar
	Calendar today = new GregorianCalendar();
	if (timeZone != null)
	    today.setTimeZone(timeZone.getTimeZone());
	today.setTime(new Date());

	//put parsedDate in Calendar
	Calendar parsedCalendar = new GregorianCalendar();
	if (timeZone != null)
	    parsedCalendar.setTimeZone(timeZone.getTimeZone());
	parsedCalendar.setTimeInMillis(parsedDate);
	
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
	
	return parsedCalendar.getTimeInMillis();
	
    }

    /**
     * corrects the date and time. Input is assumed to be in chronological order, ascending or descending, but this method
     * will correct the dates and times so that the really are chronological<br>
     * Example, assume the source represents following list of dates and times: (represented in a readable format, the actual source is an array of long<br>
     * 2010 08 16 11:10<br> 
     * 2010 08 16 08:05<br> 
     * 2010 08 16 22:50<br> 
     * 2010 08 16 22:10<br> 
     * 2010 08 16 15:10<br> 
     * 2010 08 16 20:10<br>
     * This could be the result of parsing an input with following dates, whereby the source only contained Hours and minutes (HH:mm)<br>  
     * 2010 08 16 11:10<br> 
     * 2010 08 16 08:05<br> 
     * 2010 08 15 22:50<br> 
     * 2010 08 15 22:10<br> 
     * 2010 08 15 15:10<br> 
     * 2010 08 14 20:10<br>
     * But because we assume the dates are chronological (descending in this case) we should be able to correct the source (ie the list where the date is always 
     * 16th of August, the date that the input was parsed<br>
     * This is what happens here. There are some prerequisites :<br>
     * - there should be no gaps in the source, larger than the {@link #offset}. Example assume the input list is : <br> 
     * 2010 08 16 11:10<br> 
     * 2010 08 16 08:05<br> 
     * 2010 08 16 22:50<br> 
     * 2010 08 16 22:10<br> 
     * 2010 08 16 20:10<br>
     * then the result after applying the method here would be <br>
     * 2010 08 16 11:10<br> 
     * 2010 08 16 08:05<br> 
     * 2010 08 15 22:50<br> 
     * 2010 08 15 22:10<br> 
     * 2010 08 15 20:10<br>
     * We see that the last date is set to 15, and not 14 as in the previous example<br>
     * - another prerequisite is that for descending chronology, the first timeStamp is correct, for ascending chronology, the last timeStamp is correct<br>
     * 
     * @param timeStamps the dates that should be corrected 
     * @param ascending the chronology in which the source is supposed to be.
     * @return corrected list of timeStamps
     *  
     */
    private long[]  correctTimeStamp(long[] timeStamps, boolean ascending) {
	if (timeStamps == null)
	    return null;
	if (timeStamps.length == 0)
	    return new long[0];
	if (timeStamps.length == 1)
	    return new long[]{timeStamps[0]};
	
	//initialze calendar objects that will be used to manipulate timestamps
	Calendar previousTimeStamp  = new GregorianCalendar();
	Calendar currentTimeStamp =   new GregorianCalendar();
	Calendar timeStampToModify = new GregorianCalendar();
	if (timeZone != null) {
	    previousTimeStamp.setTimeZone(timeZone.getTimeZone());
	    currentTimeStamp.setTimeZone(timeZone.getTimeZone());
	    timeStampToModify.setTimeZone(timeZone.getTimeZone());
	}
	
	//there's at least two entries in the timeStamps array
	if (!offset.equals("")) {
	    if (!ascending) {
		    // this is for descending = newest first, we assume the first date is correct
		    //start by putting first timeStamp in a Calendar
		    previousTimeStamp.setTime(new Date(timeStamps[0]));
		    

		    for (int i = 1;i < timeStamps.length;i++) {
			currentTimeStamp.setTime(new Date(timeStamps[i]));
			if (currentTimeStamp.getTimeInMillis() > previousTimeStamp.getTimeInMillis()) {
			 // all following timestamps need to be corrected
			    for (int j = i;j< timeStamps.length;j++) {
				timeStampToModify.setTime(new Date(timeStamps[j]));
				if (offset.equalsIgnoreCase("year"))
				    timeStampToModify.add(Calendar.YEAR,  -1);
				if (offset.equalsIgnoreCase("month"))
				    timeStampToModify.add(Calendar.MONTH,  -1);
				if (offset.equalsIgnoreCase("day"))
				    timeStampToModify.add(Calendar.DAY_OF_MONTH,  -1);
				if (offset.equalsIgnoreCase("halfday"))
				    timeStampToModify.add(Calendar.HOUR_OF_DAY,  -1);
				if (offset.equalsIgnoreCase("hour"))
				    timeStampToModify.add(Calendar.HOUR_OF_DAY,  -1);
				timeStamps[j] = timeStampToModify.getTimeInMillis();
			    }
			}
			    previousTimeStamp.setTimeInMillis(currentTimeStamp.getTimeInMillis());
		    }
	    } else {
		// this is for ascending = oldest first, we assume the last date is correct
		//start by putting last timeStamp in a Calendar
		previousTimeStamp.setTime(new Date(timeStamps[timeStamps.length - 1]));

		for (int i = timeStamps.length - 2;i > -1;i--) {
		    currentTimeStamp.setTime(new Date(timeStamps[i]));
		    if (currentTimeStamp.getTimeInMillis() > previousTimeStamp.getTimeInMillis()) {
			// all following timestamps need to be corrected
			for (int j = i;j > -1;j--) {
			    timeStampToModify.setTime(new Date(timeStamps[j]));
			    if (offset.equalsIgnoreCase("year"))
				timeStampToModify.add(Calendar.YEAR,  -1);
			    if (offset.equalsIgnoreCase("month"))
				timeStampToModify.add(Calendar.MONTH,  -1);
			    if (offset.equalsIgnoreCase("day"))
				timeStampToModify.add(Calendar.DAY_OF_MONTH,  -1);
			    if (offset.equalsIgnoreCase("halfday"))
				timeStampToModify.add(Calendar.HOUR_OF_DAY,  -1);
			    if (offset.equalsIgnoreCase("hour"))
				timeStampToModify.add(Calendar.HOUR_OF_DAY,  -1);
			    timeStamps[j] = timeStampToModify.getTimeInMillis();
			}
		    }
		    previousTimeStamp.setTimeInMillis(currentTimeStamp.getTimeInMillis());
		}

	    }
	}
	
	return timeStamps;
	
    }

}
