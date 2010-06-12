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
