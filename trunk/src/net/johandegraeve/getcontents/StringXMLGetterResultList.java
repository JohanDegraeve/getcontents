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

/*
 * The same as XMLElementList but in string format.
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
class StringXMLGetterResultList extends GenericXMLGetterResultList {
    
    ArrayList<StringXMLGetterResult> list;
    
    StringXMLGetterResultList() {
	list = new ArrayList<StringXMLGetterResult>();
    }

    @Override
    GenericXMLGetterResultList add(GenericXMLGetterResult newElement) {
	list.add((StringXMLGetterResult)newElement);
	return this;
    }    

    @Override
    int size() {
	return list.size();
    }

    @Override
    GenericXMLGetterResult elementAt(int i) {
	return list.get(i);
    }

    @Override
    GenericXMLGetterResultList set(GenericXMLGetterResult newElement, int index) {
	list.set(index, (StringXMLGetterResult)newElement);
	return this;
    }

    @Override
    GenericXMLGetterResultList remove(int i) {
	list.remove(i);
	return this;
    }

}
