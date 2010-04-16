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
import net.johandegraeve.easyxmldata.XMLElement;

/*
 * this class holds a DefaultXMLElementList
 *
 * @author Johan Degraeve
 *
 */
class XMLXMLGetterResultList extends GenericXMLGetterResultList {
    
    private ArrayList<XMLXMLGetterResult> theList;

    XMLXMLGetterResultList(XMLXMLGetterResult element) {
	theList = new ArrayList<XMLXMLGetterResult>();
	theList.add(element);
    }

    XMLXMLGetterResultList(DefaultXMLElement element) {
	theList = new ArrayList<XMLXMLGetterResult>();
	theList.add(new XMLXMLGetterResult(element));
    }

    XMLXMLGetterResultList(ArrayList<XMLElement> elementList) {
	theList = new ArrayList<XMLXMLGetterResult>();
	for (int i = 0;i < elementList.size(); i++)
	theList.add(new XMLXMLGetterResult(elementList.get(i)));
    }

    XMLXMLGetterResultList() {
	theList = new ArrayList<XMLXMLGetterResult>();
    }

    @Override
    int size() {
	return theList.size();
    }

    @Override
    GenericXMLGetterResult elementAt(int i) {
	return theList.get(i);
    }
    
    XMLXMLGetterResultList add(XMLXMLGetterResult newelement) {
	theList.add(newelement);
	return this;
    }
    
    XMLXMLGetterResultList add(XMLXMLGetterResultList newElementList) {
	for (int i = 0;i < newElementList.size();i++)
	    theList.add((XMLXMLGetterResult)newElementList.elementAt(i));
	return this;
    }

    @Override
    GenericXMLGetterResultList add(GenericXMLGetterResult newElement) {
	theList.add((XMLXMLGetterResult)newElement);
	return this;
    }

    @Override
    GenericXMLGetterResultList set(GenericXMLGetterResult newElement, int index) {
	theList.set(index,(XMLXMLGetterResult)newElement);
	return this;
    }

    @Override
    GenericXMLGetterResultList remove(int i) {
	theList.remove(i);
	return this;
    }

    

}
