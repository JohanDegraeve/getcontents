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


/*
 * this class is made to serve as parent for two other classes that can hold return values of XML getters.
 * First derived class will hold DefaultXMLElementList, second class will be list of strings, so it's not only for XML stuff
 *
 * @author Johan Degraeve
 *
 */
abstract class GenericXMLGetterResultList {
    /**
     * @return the number of elements in the list
     */
    abstract int size();
    
    abstract GenericXMLGetterResult elementAt(int i);
    
    abstract GenericXMLGetterResultList add(GenericXMLGetterResult newElement);
    
    abstract GenericXMLGetterResultList set(GenericXMLGetterResult newElement, int index);

    abstract GenericXMLGetterResultList remove(int i);

}