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
import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/**
 * Class for holding a getContentItemList XML element.<br>
 * A getContentItemList can hold one or more XML elements of the type getContentItem 
 * (class GENERICgetContentItemList)<br>
 * 
 *
 * For more details see http://code.google.com/p/getcontents/w/list
 * 
 * @author Johan Degraeve
 *
 */
public class GENERICgetContentItemList implements XMLElement  {
    
    private ArrayList<GENERICgetContentItem> getContentItemList;
    
    /**
     * constructor
     */
    public  GENERICgetContentItemList () {
	getContentItemList = new ArrayList<GENERICgetContentItem>();	
    }
    
    /**
     * @return the size of the list, the number of elements of type getContentItem
     */
    int size() {
	if (getContentItemList == null) return 0;
	return getContentItemList.size();
    }
    
    /**
     * @param index
     * @return the getContentItem at the specified index, starting at 0
     */
    GENERICgetContentItem elementAt(int index) {
	return getContentItemList.get(index);
    }
    
    /**
     * Only  elements of the type getContentItem can be added.
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
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

    /**
     * does nothing as the element does not need text.
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String arg0) throws SAXException {
    }

    /**
     * the list must contain at least one element, if not an exception is thrown.
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (getContentItemList.size() == 0)
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICgetcontentitemlistTag +
		    " must have at least one" + TagAndAttributeNames.GENERICgetcontentitemTag + "  child element");
    }

    /**
     * does nothing as the element does not need attributes
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
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
     * @return list of elements of type getContentItem
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	return new ArrayList<XMLElement>(getContentItemList);
    }

    /**
     * @return the name of the tag
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICgetcontentitemlistTag;
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
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
	// XXX Auto-generated method stub
	
    }

    /**
     * @return false
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	// XXX Auto-generated method stub
	return false;
    }
}
