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
import java.util.Collection;

import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

/*
 * this is similar to htmlfilterinstruction but for getting content
 * recursive attribute exists but it doesn't seem to make sense, probably to be removed
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class INSTRUCTIONhtmlGetter extends Instruction implements XMLElement {

    private ArrayList<HTMLGetter> getterList;
    private boolean recursive;
    

    @Override
    String[] execute(String[] source) {
	String[] returnvalue;
	NodeList parsedNodeList = null;
	StringBuilder temp;

	//first put the whole source in a NodeList
	temp  = new StringBuilder();
	for (int i = 0;i < source.length; i++)
	    temp.append(source[i]);
	try {
	    parsedNodeList = (new Parser(temp.toString())).parse(null);
	} catch (ParserException e) {
	    e.printStackTrace();
	}
	
	//apply all filters to the nodelist
	for (int i = 0;i < getterList.size();i++) {
	    parsedNodeList = applyOneGetterToNodeList(parsedNodeList, getterList.get(i));
	}
	    
	//prepare string array to return
	returnvalue = new String[parsedNodeList.size()];
	for (int i = 0;i < parsedNodeList.size(); i++)
	    returnvalue[i] = parsedNodeList.elementAt(i).toHtml();
	
	return returnvalue;
    }

    private NodeList applyOneGetterToNodeList(NodeList nodeList, HTMLGetter getter) {
	NodeList newNodeList = new NodeList();
	int size;
	
	for (int i = 0; i < nodeList.size(); i++) {
	    size = newNodeList.size();
	    newNodeList.add(getter.getList(nodeList.elementAt(i)));
	    if ((size == newNodeList.size()) && 
		    (recursive) && 
		    ((nodeList.elementAt(i).getChildren() == null ? 
			    false :nodeList.elementAt(i).getChildren().size() > 0))
	       )
		newNodeList.add(applyOneGetterToNodeList(nodeList.elementAt(i).getChildren(), getter));
	}
	return newNodeList;
    }
    
    public INSTRUCTIONhtmlGetter() {
	recursive = false;
	getterList = new ArrayList<HTMLGetter>();
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	try {
	    String[] attrValues = Utilities.getOptionalAttributeValues(
	    	attributes, 
	    	new String[] {
	    		TagAndAttributeNames.recursiveAttribute,
	    	}, 
	    	new String[]  {
	    		"false",
	    	});
	    if (attrValues[0].equalsIgnoreCase("true")) 
	        recursive = true;
	    else
	        recursive = false;
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

    @Override
    public void addChild(XMLElement child) throws SAXException {
	if (child instanceof HTMLGetter)
	    getterList.add((HTMLGetter)child);
	else {
	    StringBuilder exceptionString = new StringBuilder();
	    exceptionString.append("Element " + TagAndAttributeNames.INSTRUCTIONhtmlGetterTag +
		    " can only have a htmlgetter as child element.\nAllowed html getters are : \n");
	    for (int i = 0; i < TagAndAttributeNames.htmlgetterTags.length; i ++)
		exceptionString.append(TagAndAttributeNames.htmlgetterTags[i] + "\n");
	    exceptionString.append("But received element is of type " + Utilities.getClassname(child.getClass()) + ". (if it's a DefaultXMLElement then" +
	    " probably your XML is having an element with an unknown tag");
	    throw new SAXException(exceptionString.toString());
	}
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (getterList.size() == 0) {
	    throw new SAXException("Element " + TagAndAttributeNames.INSTRUCTIONhtmlGetterTag +
		    " must have at least one getter as child element");
	}
    }

    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, 
		TagAndAttributeNames.recursiveAttribute, 
		TagAndAttributeNames.recursiveAttribute, 
		"CDATA", 
		(recursive ? "true":"false"));
	return attr;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement>();
	for (int i = 0;i < getterList.size();i++)
	    returnvalue.add((XMLElement)getterList.get(i));
	return returnvalue;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.INSTRUCTIONhtmlGetterTag;
    }

    @Override
    public String getText() {
	return null;
    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
	// XXX Auto-generated method stub
	
    }

    @Override
    public boolean preserveSpaces() {
	// XXX Auto-generated method stub
	return false;
    }
}
