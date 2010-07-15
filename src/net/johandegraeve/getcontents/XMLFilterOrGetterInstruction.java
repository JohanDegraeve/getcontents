package net.johandegraeve.getcontents;

import java.util.ArrayList;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

import org.htmlparser.util.ParserException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * abstract parent class for {@link net.johandegraeve.getcontents.INSTRUCTIONXMLFilter} and
 * {@link net.johandegraeve.getcontents.INSTRUCTIONXMLGetter} because these two class have a lot of similarities.
 *
 */

abstract public class XMLFilterOrGetterInstruction extends Instruction implements XMLElement {

    /**
     * the attribute charsetname
     */
    protected String charsetName;

    /**
     * recursive attribute
     */
    protected boolean recursive;
 
    /**
     * default constructor, setting charsetName to ISO-8859-1
     */
    XMLFilterOrGetterInstruction() {
	charsetName = "ISO-8859-1";
    }
    
    /**
     * reads attributes {@link #recursive} and {@link #charsetName}, default values false and ISO-8859-1
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	    String[] attrValues = Utilities.getOptionalAttributeValues(
	    	attributes, 
	    	new String[] {
	    		TagAndAttributeNames.recursiveAttribute,
	    		TagAndAttributeNames.charsetnameAttribute
	    	}, 
	    	new String[]  {
	    		"false",
	    		"ISO-8859-1"
	    	});
	    if (attrValues[0].equalsIgnoreCase("true")) 
	        recursive = true;
	    else
	        recursive = false;
	    charsetName = attrValues[1];
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    /**
     * @return the attributes {@link #recursive} and {@link #charsetName} in an {@link AttributesImpl}
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, 
		TagAndAttributeNames.recursiveAttribute, 
		TagAndAttributeNames.recursiveAttribute, 
		"CDATA", 
		(recursive ? "true":"false"));
	attr.addAttribute(null, TagAndAttributeNames.charsetnameAttribute, TagAndAttributeNames.charsetnameAttribute, "CDATA", charsetName);
	return attr;
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
     * @return false
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	return false;
    }

    /**
     * similar to {@link #execute(String[], Logger)} but taking a String Array as input, and giving an ArrayList of XMLElement as output<br>
     * @param source
     * @param logger
     * @param getterOrFilter  value should be &quot;XMLFilter&quot; or &quot;XMLGetter&quot; depending if it is called from {@link INSTRUCTIONXMLFilter}
     * or {@link INSTRUCTIONXMLGetter}
     * @return XMLElements that match the XMLFilter.
     * @throws Exception 
     */
    ArrayList<XMLElement> executeInputStringArrOutputXMLElementList( String[]  source, Logger logger, String getterOrFilter) throws Exception {
	StringBuilder temp = new StringBuilder();
	ArrayList<XMLElement> startList = null;

	for (int i = 0; i < source.length; i++)
	    temp.append(source[i]);

	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in" + getterOrFilter + ", applying XML Parser to the source");
	}
	
	startList = net.johandegraeve.getcontents.Utilities.makeList(source,charsetName);

	
	return executeInputXMLElementListOutputXMLElementList(startList, logger);
    }
    
    /**
     * similar to {@link #execute(String[], Logger)} but taking a NodeList as input and giving NodeList as output<br>
     * Defined abstract because the derived classes {@link INSTRUCTIONhtmlFilter} and {@link INSTRUCTIONhtmlGetter} have a bit different
     * versions of this method and so implement it, but it's used in the parent class also.
     * @param source
     * @param logger
     * @return Nodes that match the htmlFilter, one node per String.
     * @throws ParserException
     */
    abstract protected ArrayList<XMLElement> executeInputXMLElementListOutputXMLElementList(ArrayList<XMLElement> source,
	    Logger logger) throws ParserException ;
    
    

}
