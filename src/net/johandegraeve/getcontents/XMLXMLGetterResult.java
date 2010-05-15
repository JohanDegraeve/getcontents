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

import net.johandegraeve.easyxmldata.DefaultXMLElement;
import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

class XMLXMLGetterResult extends GenericXMLGetterResult {
    private DefaultXMLElement defaultXMLElement;
    
    XMLXMLGetterResult(XMLElement element) {
	defaultXMLElement = new DefaultXMLElement(element);
    }

    @Override
    String convertToString() {
	return Utilities.createXML(defaultXMLElement);
    }
    
    /**
     * @return the text in the xml element
     */
    String getText() {
	return defaultXMLElement.getText();
    }

    /**
     * get the children
     * @return the children, null if there are none
     */
    XMLXMLGetterResultList getChildren() {
	ArrayList<XMLElement> children = defaultXMLElement.getChildren();
	if (children != null)
	    return new XMLXMLGetterResultList(defaultXMLElement.getChildren());
	return null;
    }
    
    void setChildren(XMLXMLGetterResultList children) {
	if (children == null)
	    defaultXMLElement.setChildren(null);
	else if (children.size() == 0 ) 
	    defaultXMLElement.setChildren(null);
	else {
	    ArrayList<XMLElement> newList = new ArrayList<XMLElement>();
	    for (int i = 0;i < children.size();i++)
		newList.add((((XMLXMLGetterResult)children.elementAt(i)).getDefaultXMLElement()));
	    defaultXMLElement.setChildren(newList);
	}
    }
    
    XMLElement getDefaultXMLElement() {
	return defaultXMLElement;
    }
}
