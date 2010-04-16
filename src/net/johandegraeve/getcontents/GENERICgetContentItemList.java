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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/**
 * The class that holds the instructions that need to be done to fetch contents from an HTML page.<br>
 *
 *
 * @author Johan Degraeve
 *
 */
public class GENERICgetContentItemList implements XMLElement  {
    
    private ArrayList<GENERICgetContentItem> getContentItemList;
    
    public  GENERICgetContentItemList () {
	getContentItemList = new ArrayList<GENERICgetContentItem>();	
    }
    
    int size() {
	if (getContentItemList == null) return 0;
	return getContentItemList.size();
    }
    
    GENERICgetContentItem elementAt(int index) {
	return getContentItemList.get(index);
    }
    
    @Override
    public void addChild(XMLElement arg0) throws SAXException {
	if (Utilities.getClassname(arg0.getClass()).equalsIgnoreCase(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICgetcontentitemTag))
		getContentItemList.add((GENERICgetContentItem)arg0);
	else 	    
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICgetcontentitemlistTag +
		    " can only have " + 
		    TagAndAttributeNames.GENERICgetcontentitemTag + 
		    " as child.");

    }

    @Override
    public void addText(String arg0) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (getContentItemList.size() == 0)
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICgetcontentitemlistTag +
		    " must have at least one" + TagAndAttributeNames.GENERICgetcontentitemTag + "  child element");
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    @Override
    public Attributes getAttributes() {
	return null;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	return new ArrayList<XMLElement>(getContentItemList);
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICgetcontentitemlistTag;
    }

    @Override
    public String getText() {
	return null;
    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
	// XXX Auto-generated method stub
	
    }

    @Override
    public boolean preserveSpaces() {
	// XXX Auto-generated method stub
	return false;
    }
}
