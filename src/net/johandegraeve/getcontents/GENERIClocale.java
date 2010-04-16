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

public class GENERIClocale implements XMLElement {
    
    private String language;
    private String country;
    private String variant;
    
    public GENERIClocale() {
	createDefaultAttributes();
    }
    
    Locale getLocal() {
	return new Locale(language,country,variant);
    }

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

    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GENERIClocaleTag);
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
    }
    
    private void createDefaultAttributes() {
		language = Locale.getDefault().getLanguage();
		country = Locale.getDefault().getCountry();
		variant = Locale.getDefault().getVariant();
    }

    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, TagAndAttributeNames.languageAttribute, TagAndAttributeNames.languageAttribute, "CDATA", language);
	attr.addAttribute(null, TagAndAttributeNames.countryAttribute, TagAndAttributeNames.countryAttribute, "CDATA", country);
	attr.addAttribute(null, TagAndAttributeNames.variantAttribute, TagAndAttributeNames.variantAttribute, "CDATA", variant);
	return null;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	return null;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERIClocaleTag;
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
