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

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.RegexFilter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * RegexFiler as defined in the HTML parser package.<br>
 * Always case sensitive.
 *<br><br>
 *Example, regexfilter created with pattern=&quot;([01][0-9]|2[0-3]):[0-5][0-9]&quot; This filters on text being a time
 *in the format HH:mm<br>
 *Nodes being filtered represent following html code :<br>
 *                &lt;TR VALIGN=TOP&gt;<br>
 *                &lt;TD width=15%&gt;23:40&lt;/td&gt;<br>
 *                &lt;TD width=15%&gt; Canvas &lt;/td&gt;<br>
 *                &lt;TD width=60%&gt; <br>
 *                &lt;b&gt;Bobsleenacht:&lt;/b&gt;<br>
 *                &lt;/td&gt;<br>
 *                &lt;TD width=10%&gt; sport &lt;/td&gt;<br>
 *                &lt;/TR&gt;<br>
 * The result will be <br>               
 * &quot;23:40&quot;
 */
public class GETorFILTERregexFilter implements XMLElement, HTMLFilter {
    
    /**
     * mStrategy as defined in RegexFilter in HTML Parser package<br>
     * mStrategy is the sum of attributes MATCH, LOOKINGAT and FIND
     */
    private int mStrategy;
    /**
     * the Pattern
     */
    private String mPattern;
    
    /**
     * constructor, default value for mStrategy = FIND
     */
    public GETorFILTERregexFilter() {
	mStrategy = RegexFilter.FIND;
	mPattern = null;
    }

    /**
     * @return the RegexFilter
     * @see net.johandegraeve.getcontents.HTMLFilter#getHTMLFilter()
     */
    @Override
    public NodeFilter getHTMLFilter() {
	return new RegexFilter(mPattern,mStrategy);
    }

    /**
     * assigns attribute "strategy" to mStrategy. Default value = find.<br>
     * Possible values = find, lookingat, match.<br>
     * Values are case insensitive.<br>
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	String[] attrValues = Utilities.getOptionalAttributeValues(
		attributes, 
		new String[] {
			TagAndAttributeNames.strategyAttribute,
		}, 
		new String[]  {
			"find",
		});
	if (attrValues[0].equalsIgnoreCase("find")) 
	    mStrategy = RegexFilter.FIND;
	else if (attrValues[0].equalsIgnoreCase("lookingat")) 
	    mStrategy = RegexFilter.LOOKINGAT;
	else if (attrValues[0].equalsIgnoreCase("match")) 
	    mStrategy = RegexFilter.MATCH;
	else throw new SAXException("Invalid value for attribute " + TagAndAttributeNames.strategyAttribute + "." +
		"Should be \"find\", \"lookingat\" or \"match\".\n");
    }

    /**
     * throws an Exception
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child, "", 
		new String[] {}, 
		TagAndAttributeNames.GETorFILTERregexFilterTag);
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
    }

    /**
     * throws an exceptino if mPattern = null
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (mPattern == null)
	    throw new SAXException("Element " + TagAndAttributeNames.GETorFILTERregexFilterTag + " must have a text that represents the pattern.");
    }

    /**
     * @return the strategy attribute 
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	String attrValue = null;
	if (mStrategy == RegexFilter.FIND) attrValue = "find";
	if (mStrategy == RegexFilter.LOOKINGAT) attrValue =  "lookingat";
	if (mStrategy == RegexFilter.MATCH) attrValue =  "match";
	attr.addAttribute(null, 
		"strategy", 
		"strategy", 
		"CDATA", 
		attrValue);
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
     * @return the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERregexFilterTag;
    }

    /**
     * @return null
     * @see net.johandegraeve.easyxmldata.XMLElement#getText()
     */
    @Override
    public String getText() {
	return mPattern;
    }

    /**
     * assigns text to mPattern
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
	mPattern = text;
    }

    /**
     * @return true;
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	return true;
    }
}
