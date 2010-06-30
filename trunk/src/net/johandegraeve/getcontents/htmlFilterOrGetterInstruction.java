package net.johandegraeve.getcontents;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Iterator;
import java.util.Locale;

import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/**
 * abstract parent class for {@link net.johandegraeve.getcontents.INSTRUCTIONhtmlFilter} and
 * {@link net.johandegraeve.getcontents.INSTRUCTIONhtmlGetter} because these two class have a lot of similarities.
 *
 * @author Johan Degraeve
 *
 */
public abstract class htmlFilterOrGetterInstruction extends Instruction
implements XMLElement {

    /**
     * constructor, sets recursive attribute to false, encoding to ISO-8859-1 and creates prototypicalNodeFactory 
     * with {@link org.htmlparser.PrototypicalNodeFactory#PrototypicalNodeFactory()}
     */
    protected htmlFilterOrGetterInstruction() {
	recursive = false;
	charset = "ISO-8859-1";
	prototypicalNodeFactory = new PrototypicalNodeFactory();
    }

    /**
     * charset to be used, the charset can be used as attribute in the xml element.<br>
     * It is safer to specify the character set , even though it may be specified in the html page
     */
    protected String charset;

    /**
     * {@link org.htmlparser.PrototypicalNodeFactory} to use for parsing the html source
     */
    protected PrototypicalNodeFactory prototypicalNodeFactory;

    /**
     * If recursive and if one specific node returns no NodeList then dig deeper into that node 
     * recursive attribute
     */
    protected boolean recursive;

    /**
     * Identical to {@link net.johandegraeve.easyxmldata.XMLElement#addAttributes(Attributes)} but takes as
     * additional argument the type of filter which is either &quot;htmlGetter&quot; or &quot;htmlFilter&quot;
     * considers recursive as optional attribute, default value = false, allowed values are true or false
     * @param attributes the attributes
     * @param getterOrFilter 
     * @throws SAXException 
     */
    public void addAttributes(Attributes attributes,String getterOrFilter) throws SAXException {
	try {
	    String[] attrValues = Utilities.getOptionalAttributeValues(
		    attributes, 
		    new String[] {
			    TagAndAttributeNames.recursiveAttribute,
			    TagAndAttributeNames.charsetAttribute,
			    TagAndAttributeNames.unknowntagAttrribute
		    }, 
		    new String[]  {
			    "false",
			    "ISO-8859-1",
			    ""
		    });
	    if (attrValues[0].equalsIgnoreCase("true")) 
		recursive = true;
	    else
		recursive = false;
	    charset = attrValues[1];
	    if (!Charset.isSupported(charset))
		throw new SAXException(getterOrFilter + " has an unsupported character encoding attribute - Charset.isSupported(" + charset + ") failed");
	    if (!attrValues[2].equals("")) {
		String[]  tags = attrValues[2].split(" ");
		for (int i = 0;i < tags.length;i++) {
		    if (!prototypicalNodeFactory.getTagNames().contains(tags[i].trim().toUpperCase(Locale.ENGLISH))) {
			//it's an unknown tag
			prototypicalNodeFactory.registerTag(new UnknownHTMLTag(tags[i].trim().toUpperCase(Locale.ENGLISH)));
		    }
		}
	    }
	} catch (IllegalCharsetNameException ex) {
	    throw new SAXException(getterOrFilter + " has an illegal charset attribute");
	} catch (Exception e) {
	    StringBuilder exceptionString = new StringBuilder();
	    exceptionString.append(e.toString() + "\n");
	    StackTraceElement[] traceElement = e.getStackTrace();
	    for (int i = 0;i < traceElement.length; i++) {
		exceptionString.append("FileName = " + traceElement[i].getFileName()+ "\n");
		exceptionString.append("LineNumber = " + traceElement[i].getLineNumber()+ "\n");
		exceptionString.append("MethodName = " + traceElement[i].getMethodName()+ "\n"+ "\n");
	    }
	    throw new SAXException(exceptionString.toString());
	}
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
     * @return the recursive attribute
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @SuppressWarnings("unchecked")
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	StringBuilder tags = new StringBuilder();
	attr.addAttribute(null, 
		TagAndAttributeNames.recursiveAttribute, 
		TagAndAttributeNames.recursiveAttribute, 
		"CDATA", 
		(recursive ? "true":"false"));
	attr.addAttribute(null, 
		TagAndAttributeNames.charsetAttribute, 
		TagAndAttributeNames.charsetAttribute, 
		"CDATA", 
		charset);
	PrototypicalNodeFactory tempProtoTypicalNodeFactory = new PrototypicalNodeFactory(); 
	Iterator<String> tagNameIterator = prototypicalNodeFactory.getTagNames().iterator();
	while (tagNameIterator.hasNext()) {
	    String unknowntagname = tagNameIterator.next();
	    if (!tempProtoTypicalNodeFactory.getTagNames().contains(unknowntagname)) {
		if (tags.length() == 0)
		    tags.append(unknowntagname);
		else
		    tags.append(" " + unknowntagname);
	    }
	}
	if (tags.length() > 0)
	    attr.addAttribute(
		    null, 
		    TagAndAttributeNames.unknowntagAttrribute, 
		    TagAndAttributeNames.unknowntagAttrribute, 
		    "CDATA", 
		    tags.toString());
	return attr;
    }
    /**
     * similar to {@link #execute(String[], Logger)} but taking a NodeList as input
     * @param source
     * @param logger
     * @return Nodes that match the htmlFilter, one node per String.
     * @throws ParserException
     */
    String[] executeInputNodeListOutputStringArr(NodeList  source, Logger logger) throws ParserException {
	String[] returnvalue;	
	
	source = executeInputNodeListOutputNodeList(source, logger);
	//prepare string array to return
	returnvalue = new String[source == null ? 0: source.size()];
	for (int i = 0;i < source.size(); i++)
	    returnvalue[i] = source.elementAt(i).toHtml();
	
	if (logger != null && logger.getLogLevel().equalsIgnoreCase("debug")) {
	    if (returnvalue.length == 0)
		logger.Log("There are no results");
	    else {
		for (int i = 0;i < returnvalue.length;i++) {
		    logger.Log("Result " + i + " =\n" );
		    logger.Log("returnvalue[i]");
		    logger.Log("\n" );
		}
	    }
	}
	
	return returnvalue;
    }

    /**
     * similar to {@link #execute(String[], Logger)} but taking a String Array as input, and giving a NodeList as output<br>
     * @param source
     * @param logger
     * @param getterOrFilter  value should be &quot;htmlFilter&quot; or &quot;htmlGetter&quot; depending if it is called from {@link INSTRUCTIONhtmlFilter}
     * or {@link INSTRUCTIONhtmlGetter}
     * @return Nodes that match the htmlFilter, one node per String.
     * @throws ParserException
     */
    NodeList executeInputStringArrOutputNodeList( String[]  source, Logger logger, String getterOrFilter) throws ParserException {
	StringBuilder temp = new StringBuilder();
	Parser htmlParser;
	NodeList parsedNodeList = null;
	
	for (int i = 0; i < source.length; i++)
	    temp.append(source[i]);

	if (logger != null) {
	    logger.Log(System.currentTimeMillis() + " : method execute in" + getterOrFilter + ", applying HTML Parser to the source");
	}

	htmlParser = new Parser(temp.toString());
	htmlParser.setEncoding(charset);
	Lexer lexer = htmlParser.getLexer();
	lexer.setNodeFactory(prototypicalNodeFactory);
	htmlParser.setLexer(lexer);
	parsedNodeList = htmlParser.parse(null);
	
	return executeInputNodeListOutputNodeList(parsedNodeList, logger);
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
    abstract protected NodeList executeInputNodeListOutputNodeList(NodeList source,
	    Logger logger) throws ParserException ;
    
    
}
