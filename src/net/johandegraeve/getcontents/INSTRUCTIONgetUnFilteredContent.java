package net.johandegraeve.getcontents;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * an instruction of type getUnFilteredContent get the content and return as is<br>
 * one optional child being encoding, default value set to default charset.
 *
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONgetUnFilteredContent extends Instruction implements XMLElement {
    
    /**
     * list of request properties
     */
    private ArrayList<GENERICrequestProperty> requestPropertyList;
    
    /**
     * constructor that initializes {@link #requestPropertyList}
     */
    public INSTRUCTIONgetUnFilteredContent() {
	requestPropertyList = new ArrayList<GENERICrequestProperty>();
    }
    
    /**
     * the encoding
     */
    private GENERICencoding encoding;

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    /**
     * accepts encoding as child and assigns them<br>
     * accepts also {@link GENERICrequestProperty} as child and adds it to {@link #requestPropertyList}
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child, 
	    	TagAndAttributeNames.genericPrefix,  
		new String []{
		    TagAndAttributeNames.GENERICencodingTag,
		    TagAndAttributeNames.GENERICrequestPropertyTag
		}, 
		TagAndAttributeNames.INSTRUCTIONgetUnfilteredContent);
	
	if (Utilities.getClassname(child.getClass()).equals(
		TagAndAttributeNames.genericPrefix +
		TagAndAttributeNames.GENERICencodingTag)) {
	    if (encoding != null)
		throw new SAXException("Element of type " + TagAndAttributeNames.INSTRUCTIONgetUnfilteredContent +
			" should have only one child of type " + TagAndAttributeNames.GENERICencodingTag);
	    encoding = (GENERICencoding) child;
	} 
	
	if (child instanceof GENERICrequestProperty)
	    requestPropertyList.add((GENERICrequestProperty)child);
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
     * if {@link #encoding} is null then assigns to default character set
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (encoding == null) {
	    encoding = new GENERICencoding();
	    encoding.addText(Charset.defaultCharset().displayName());
	    encoding.complete();//just to be sure not forgetting anything
	}
    }

    /**
     * @return null
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	return null;
    }

    /**
     * @return null
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	return null;
    }

    /**
     * @return tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.INSTRUCTIONgetUnfilteredContent;
    }

    /**
     * return null
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
     * If source represents a URL then fetches the contents and returns it an Array with one String.<br>
     * Source is considered a URL if the first character not equal to blank does not equal &lt; All strings are searched one by
     * one, character by character until the first non blank character is found.<br>
     * If source does not represent a URL then returns the source.
     * @return unfiltered result
     * @see net.johandegraeve.getcontents.Instruction#execute(java.lang.String[], Logger )
     */
    @Override
    String[] execute(String[] source, Logger thelogger) throws Exception {
	BufferedReader in = null;
	StringBuffer resultList = new StringBuffer();
	int chr;
	URL yahoo;
	HttpURLConnection yc;

	//first check if source is a url or not
	boolean ltfound = false;
	int i = 0;
	int j = 0;
	while (!ltfound) {
	    if (source[i].charAt(j) == '<') {
		ltfound = true;
		//less than sign has been found time to stop
		break;
	    }
	    if (source[i].charAt(j) != ' ') {
		//character not blank and not less than sign has been found, time to stop
		//ltfound is still false
		break;
	    }
	    if (j == source[i].length()) {
		i++;
		j = 0;
		if (i == source.length)
		    //went through all strings, each character, time to stop, ltfound is false
		    break;
	    } else
		j++;
	}

	if (!ltfound) {
	    //we need to read from a URL, the URL is in source[i], the first character not blank is in source[i][j]
	    if (thelogger != null) {
		thelogger.Log(System.currentTimeMillis() + " : method execute in getUnFilteredContent, source is a URL, trying to open and download");
	    }
	    yahoo = new URL(source[i].substring(j));
	    yc = (HttpURLConnection)yahoo.openConnection();
	    //add the properties
	    for (int i1 = 0;i1 < requestPropertyList.size();i1++)
		yc.setRequestProperty(requestPropertyList.get(i1).getKey().getKey(), requestPropertyList.get(i1).getValue().getValue());
	    
	    in = new BufferedReader(
				new InputStreamReader(
				yc.getInputStream(),encoding.getEncoding()));
	    chr = in.read();
	    while (chr != -1) {
		resultList.append((char)chr);
		chr = in.read();
	    }
	    in.close();
	} else {
	    if (thelogger != null) {
		thelogger.Log(System.currentTimeMillis() + " : method execute in getUnFilteredContent, source is not a URL, returnvalue will be equal to the input");
	    }
	    
	    //source is not a URL, 
		return source;
	}

	if (thelogger != null) {
	    thelogger.Log(System.currentTimeMillis() + " : method execute in getUnFilteredContent, source is  a URL, returnvalue is the result of opening that url");
	}

	if (thelogger != null && thelogger.getLogLevel().equalsIgnoreCase("debug")) {
		    thelogger.Log("And here is the result  =\n" + resultList.toString());
	}

	return new String[]{resultList.toString()} ;
    }

}
