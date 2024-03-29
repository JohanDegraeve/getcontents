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

import net.johandegraeve.easyxmldata.XMLElement;

/**
 * abstract class for all instructions<br>
 * 
 *
 * @author Johan Degraeve
 *
 */
abstract class Instruction implements XMLElement {
    
    /**
     * Executes an instruction<br>
     * @param source the source to process
     * @param logger will be used for logging, if null then there's no logging
     * @return  the result of executing the instruction on the source
     * @throws Exception 
     */
     abstract String[] execute(String[] source, Logger logger) throws Exception;

}
