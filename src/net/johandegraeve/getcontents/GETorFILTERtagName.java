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

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * a TagNameFilter  filters on nodes with a specific tagname.<br>
 * not case sensitive<br>
 * for HTML : not using the HTML parser filter, doing own filtering.<br>
 * 
 * XML : filters on tagname.  
 * 
 * @author Johan Degraeve
 *
 */
public class GETorFILTERtagName implements XMLElement, HTMLFilter, XMLFilter, HTMLGetter  {

    /**
     * the name of the tag to filter on
     */
    private String mName;
    
    /**
     * defines if exact match or startswith to be performed, startswith is case sensitive.<br>
     * possible values &quot;equals&quot; or &quot;startswith&quot;
     */
    private String type;
    
    
    /**
     * @return mName
     */
    public String getName() {
	return mName;
    }

    /**
     * @return the TagNameFilter
     * @see net.johandegraeve.getcontents.HTMLFilter#getHTMLFilter()
     */
    @SuppressWarnings("serial")
    @Override
    public NodeFilter getHTMLFilter() {
	return new NodeFilter() {
	    @Override
	    public boolean accept(Node node) {
		if (node == null) return false;
		if (!(node instanceof TagNode)) return false;
		if (type.equalsIgnoreCase("equals"))
		    return node.getText().trim().split(" ")[0].equalsIgnoreCase(mName);
		return node.getText().trim().split(" ")[0].startsWith(mName);
	    }
	};
//	return new TagNameFilter(mName);
    }

    /**
     * @return an XMLElementFilter that will filter on elements with tag name = mName
     * @see net.johandegraeve.getcontents.XMLFilter#getXMLFilter()
     */
    @Override
    public XMLElementFilter getXMLFilter() {
	return new XMLElementFilter() {
	    @Override
	    public boolean accept(XMLElement element) {
		if (type.equalsIgnoreCase("equals"))
		    return (element.getTagName().equals(mName));
		return (element.getTagName().startsWith(mName));
	    }

	};
    }
    
    /**
     * constructor setting mName to null
     */
    public GETorFILTERtagName() {
	mName = null;
	type = "equals";
    }
    
    /**
     * evaluates attribute &quot;type&quot;<br>
     * default value = &quot;equals&quot;, other allowed value is &quot;startswith&quot;
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
	String[] attrValues = Utilities.getOptionalAttributeValues(
	    	attributes, 
	    	new String[] {
	    		"type"
	    	}, 
	    	new String[]  {
	    		"equals"
	    	});
	    if (attrValues[0].equalsIgnoreCase("startswith")) 
	        type = "startswith";
	    else
	        type = "equals";
    }

    /**
     * throws an exception
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GETorFILTERtagNameTag);
    }

    /**
     * assigns text to mName
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String text) throws SAXException {
	mName = text;
    }

    /**
     * throws an exception of mName = null or mName.length = 0
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if ((mName == null) || (mName.length() == 0) )
		throw new SAXException(
		    "Element " + 
		    TagAndAttributeNames.GETorFILTERtagNameTag +
		    " must have text");
    }

    /**
     * @return null
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    public Attributes getAttributes() {
	AttributesImpl attr = new AttributesImpl();
	attr.addAttribute(null, "type", "type", "CDATA", type);
	return attr;
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
     * @return the tag name (not the name of the tag on which this filter will filter but really "tagName" 
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERtagNameTag;
    }

    /**
     * @return mName
     * @see net.johandegraeve.easyxmldata.XMLElement#getText()
     */
    @Override
    public String getText() {
	return mName;
    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    /**
     * does nothing
     */
    @Override
    public boolean preserveSpaces() {
	return false;
    }

    /**
     * @return if elementAt is a tagnode with tag equal to {@link #mName} then elementAt in a NodeList, otherwise null
     * @see net.johandegraeve.getcontents.HTMLGetter#getList(org.htmlparser.Node)
     */
    @Override
    public NodeList getList(Node elementAt) {
	if (elementAt == null) return null;
	if (elementAt instanceof TagNode) 
	    //Node.getText returns everyhting between < and >, inclusive any attributes
	    //so before comparing with mName, I'm splitting with space as delimiter, maximum 2 elements, taking
	    //the first, this is the actual tag
	    if (type.equalsIgnoreCase("equals")){
		if (elementAt.getText().trim().split(" ")[0].equalsIgnoreCase(mName))
		    return new NodeList(elementAt);
	    }  else
		if (elementAt.getText().trim().split(" ")[0].startsWith(mName))
		    return new NodeList(elementAt);
	return null;	
    }
}
