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

/**
 * allows application defined logging
 * 
 * @author Johan Degraeve
 *
 */
public interface Logger {
    
    /**
     * will be called by getcontents when it wants to log something
     * @param log the string to log
     */
    void Log(String log);
    
    /**
     * will be called to get the level of logging. Allowed values are :<br>
     * - Debug : Information that helps debug a problem, this is the highest level of logging<br>
     * - Critical : The health of the system is in jeopardy; for example, an operation has failed because there is not enough memory.<br>
     * - Warning : An error has occurred that the system might or might not be able to work around<br>
     * - Info : general information<br>
     *<br> 
     * If the return value is different from the above values, then the default value &quot;Info&quot; will be used
     * @return the log level
     */
    String getLogLevel();
}
