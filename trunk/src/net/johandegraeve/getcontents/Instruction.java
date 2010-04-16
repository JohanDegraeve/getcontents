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

import org.htmlparser.util.NodeList;

import net.johandegraeve.easyxmldata.XMLElement;

/**
 * abstract class for all instructions
 *
 * @author Johan Degraeve
 *
 */
abstract class Instruction implements XMLElement {
    
    /**
     * Executes an instruction<br>
     * @param source the source to process, can be a text, html page or url
     * @return  one String per matching result
     */
    /*pcakge private */  abstract String[] execute(String[] source) throws Exception;

}
