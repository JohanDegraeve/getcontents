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

import net.johandegraeve.easyxmldata.XMLElement;

import org.htmlparser.Node;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class GETorFILTERtext implements XMLElement, HTMLGetter, XMLGetter {

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	throw new SAXException("Element type gettext does not accept children");
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
    }

    @Override
    public NodeList getList(Node elementAt) {
	return new NodeList(new TextNode(elementAt.toPlainTextString()));
    }

    @Override
    public GenericXMLGetterResultList getList(GenericXMLGetterResultList list) {
	GenericXMLGetterResultList returnvalue = null;
       returnvalue = new StringXMLGetterResultList();
	if (list instanceof XMLXMLGetterResultList) {
	    for (int i = 0;i < list.size();i ++)
		returnvalue.add(new StringXMLGetterResult(((XMLXMLGetterResult)list.elementAt(i)).getText()));
	} else if (list instanceof StringXMLGetterResultList)  {
	    for (int i = 0;i < list.size();i ++)
		returnvalue.add(list.elementAt(i));
	}
	return returnvalue;
    }

    @Override
    public Attributes getAttributes() {
	return null;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	return null;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERtextTag;
    }

    @Override
    public String getText() {
	// XXX Auto-generated method stub
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
