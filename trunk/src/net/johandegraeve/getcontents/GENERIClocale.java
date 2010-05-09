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
import java.util.Locale;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import net.johandegraeve.easyxmldata.XMLElement;

/**
 * represents a local as defined in {@link java.util.Locale}
 *
 * @author Johan Degraeve
 *
 */
public class GENERIClocale implements XMLElement {
    
    /**
     * language
     */
    private String language;
    /**
     * country
     */
    private String country;
    /**
     * variant
     */
    private String variant;
    
    /**
     * constructor, intializes {@link #language}, {@link #country}, {@link #variant} using {@link java.util.Locale#getLanguage()},
     * {@link java.util.Locale#getCountry()}, {@link java.util.Locale#getVariant()}
     */
    public GENERIClocale() {
	createDefaultAttributes();
    }
    
    /**
     * @return the local represtend by this object
     */
    Locale getLocal() {
	return new Locale(language,country,variant);
    }

    /**
     * assigns {@link #language}, {@link #country}, {@link #variant} using following rules :<br>
     *  if language attribute present then country attribute is allowed, otherwise throw exception<br>
     *  if country present, then variant is allowed<br>
     *  if language not present then all get default values<br>
     *  if language present but not country and variant then country and variant get default values<br>
     *  if language and country present, but not variant, then variant gets default value<br>
     *  
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	//if language attribute present then country attribute is allowed, otherwise throw exception
	//if country present, then variant is allowed
	//if language not present then all get default values
	//if language present but not country and variant then country and variant get default values
	//if language and country present, but not variant, then variant gets default value
	
	if ((language = attributes.getValue(TagAndAttributeNames.languageAttribute)) == null) {
	    if( (!(attributes.getValue(TagAndAttributeNames.countryAttribute) == null) || 
		    !(attributes.getValue(TagAndAttributeNames.variantAttribute) == null)))
	    	throw new SAXException("Element type " + TagAndAttributeNames.GENERIClocaleTag + " : if " + TagAndAttributeNames.languageAttribute +
	    		"is not present then " + TagAndAttributeNames.countryAttribute + " and " + TagAndAttributeNames.variantAttribute +
	    		" are also not allowed.");
	    else {
		createDefaultAttributes();
	    }
	    
	} else {
	    if ((country = attributes.getValue(TagAndAttributeNames.countryAttribute)) == null) {
		if (!(attributes.getValue(TagAndAttributeNames.variantAttribute) == null))
		    	throw new SAXException("Element type " + TagAndAttributeNames.GENERIClocaleTag + " : if " + TagAndAttributeNames.countryAttribute +
		    		"is not present then " + TagAndAttributeNames.variantAttribute +
		    		" are also not allowed.");
		else {
			country = (new Locale(language)).getCountry();
			variant = (new Locale(language)).getVariant();
		}
	    } else {
		if ((variant = attributes.getValue(TagAndAttributeNames.variantAttribute)) == null)
		    variant = (new Locale(language,country)).getVariant();
	    } 
	}
    }

    /**
     * throws an Exception
     * 
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GENERIClocaleTag);
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
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
    }
    
    /**
     * initialize {@link #language}, {@link #country}, {@link #variant} using {@link java.util.Locale#getDefault()}
     */
    private void createDefaultAttributes() {
		language = Locale.getDefault().getLanguage();
		country = Locale.getDefault().getCountry();
		variant = Locale.getDefault().getVariant();
    }

    /**
     * @return the attributes {@link #language}, {@link #country}, {@link #variant}
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.languageAttribute, TagAndAttributeNames.languageAttribute, "CDATA", language);
	attr.addAttribute(null, TagAndAttributeNames.countryAttribute, TagAndAttributeNames.countryAttribute, "CDATA", country);
	attr.addAttribute(null, TagAndAttributeNames.variantAttribute, TagAndAttributeNames.variantAttribute, "CDATA", variant);
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
     * @return {@link TagAndAttributeNames#GENERIClocaleTag}
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERIClocaleTag;
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
