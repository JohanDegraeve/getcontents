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
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.Ostermiller.util.StringHelper;

/**
 * remove all nodes matching the given tagname<br>
 * 
 * @author Johan Degraeve
 *
 */
public class GETorFILTERremoveNodes implements XMLElement, HTMLGetter, XMLGetter  {

    private boolean recursive;

    String tagName;
    
    public GETorFILTERremoveNodes() {
	tagName = null;
	recursive = false;
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
    }

    @Override
    public void addText(String text) throws SAXException {
	tagName = text;
    }

    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (tagName == null)
		throw new SAXException("No child elements allowed for " + TagAndAttributeNames.GETorFILTERremoveNodesTag);
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
	return null;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERremoveNodesTag;
    }

    @Override
    public String getText() {
	return tagName;
    }

    @Override
    public boolean preserveSpaces() {
	return false;
    }

    /**
     * @return although we return here a {@link NodeList}, the NodeList will be either null or contain just one element being the original elementAt if
     * it does not have a tag matching {@link #tagName}, and if  {@link #recursive}, then any nodes within elementAt that match {@link #tagName} will be removed
     * @see net.johandegraeve.getcontents.HTMLGetter#getList(org.htmlparser.Node)
     */
    @Override
    public NodeList getList(Node elementAt) {
	NodeList childrenOfelementAt = elementAt.getChildren();
	NodeList childrenOfelementAtAfterRemoving = new NodeList();
	
	if (elementAt == null)
	    return null;
	if (elementAt instanceof TagNode) {
	    if (((TagNode)elementAt).getIds().length > 0)
		if (StringHelper.equalsAnyIgnoreCase(tagName, ((TagNode)elementAt).getIds())) {
		    //it's a node to re removed so stop digging deeper, remove it
		    return null;
	    }
	}
	//it's not a node to be removed, if recursive, dig deeper to see if it has nodes matching tagname, that need to be removed 
	if (recursive) {
	    	if (childrenOfelementAt != null) {
	    	    for (int i = 0;i < childrenOfelementAt.size(); i++) {
	    		//for every child; we'll do the same, call getList to get back the nodes that still match
	    		NodeList RemainingChildren = getList(childrenOfelementAt.elementAt(i));
	    		if (RemainingChildren != null)
	    		    if (RemainingChildren.size() > 0)
	    			childrenOfelementAtAfterRemoving.add(RemainingChildren);
	    	    }
	    	}
		if (childrenOfelementAtAfterRemoving.size() > 0)
		    elementAt.setChildren(childrenOfelementAtAfterRemoving);
		else
		    elementAt.setChildren(null);
	}
	return new NodeList(elementAt);
    }

    /**
     * @return  we return here a {@link GenericXMLGetterResultList}, the result will be either null or contain sublist of the original list if, namely 
     * those elements that do not have a tag matching {@link #tagName}, and if  {@link #recursive}, each element will be recursively searched for children elements matching
     *  {@link #tagName}, any such children will be removed - this goes on to the deepest possible level.
     * @see net.johandegraeve.getcontents.HTMLGetter#getList(org.htmlparser.Node)
     */
    @Override
    public GenericXMLGetterResultList getList(GenericXMLGetterResultList list) {
	XMLXMLGetterResultList childrenOfelementFromListAfterRemoving ;
	
	if (list instanceof StringXMLGetterResultList)
	    return list;

	childrenOfelementFromListAfterRemoving = new XMLXMLGetterResultList();
	
	if (list == null)
	    return null;
	if (list.size() == 0)
	    return null;
	for (int j = 0;j < list.size();j++) {
	    if (StringHelper.equalsAnyIgnoreCase((((XMLXMLGetterResult)(list.elementAt(j))).getDefaultXMLElement().getTagName()),new String[]{tagName})) {
		//no need to process that node any further, it is not added to the returnlist
	    } else {
		childrenOfelementFromListAfterRemoving.add(list.elementAt(j));
		//if recursive, dig deeper to see if it has nodes matching tagname, that need to be removed 
		if (recursive) {
       		    ((XMLXMLGetterResult)list.elementAt(j)).//the element of which we need to dig through the children
       		    	setChildren(//set the children to the new list
       		    		(XMLXMLGetterResultList)getList(((XMLXMLGetterResult)(list.elementAt(j))).getChildren())
       		    		);
		}
	    }
	}
	return childrenOfelementFromListAfterRemoving;
    }

}
