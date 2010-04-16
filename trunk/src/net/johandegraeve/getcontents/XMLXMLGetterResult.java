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

import net.johandegraeve.easyxmldata.DefaultXMLElement;
import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

class XMLXMLGetterResult extends GenericXMLGetterResult {
    private XMLElement defaultXMLElement;
    
    XMLXMLGetterResult(XMLElement element) {
	defaultXMLElement = element;
    }

    @Override
    String convertToString() {
	return defaultXMLElement.getText();
    }
    
    /**
     * @return the text in the xml element
     */
    String getText() {
	return defaultXMLElement.getText();
    }

    XMLXMLGetterResultList getChildren() {
	return new XMLXMLGetterResultList(defaultXMLElement.getChildren());
    }
    
    XMLElement getDefaultXMLElement() {
	return defaultXMLElement;
    }
}
