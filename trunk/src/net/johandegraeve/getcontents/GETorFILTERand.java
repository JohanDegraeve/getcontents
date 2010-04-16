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

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.AndFilter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import net.johandegraeve.easyxmldata.Utilities;
import net.johandegraeve.easyxmldata.XMLElement;

public class GETorFILTERand implements XMLElement, HTMLFilter {
    
    private ArrayList<HTMLFilter> filters;

    public GETorFILTERand() {
	filters = new  ArrayList<HTMLFilter>();
    }
    
    @Override
    public NodeFilter getHTMLFilter() {
	NodeFilter[] predicates = new NodeFilter[filters.size()];
	for (int i = 0;i < filters.size();i++)
	    predicates[i] = filters.get(i).getHTMLFilter();
	return new AndFilter(predicates);
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
    }

    @Override
    public void addChild(XMLElement child) throws SAXException {
	Utilities.verifyChildType(child, TagAndAttributeNames.GETorFILTERPrefix, TagAndAttributeNames.htmlfilterTags, TagAndAttributeNames.GETorFILTERandTag);
	filters.add((HTMLFilter)child);
    }

    @Override
    public void addText(String text) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (filters.size() < 2) throw new SAXException("An " + TagAndAttributeNames.GETorFILTERandTag + " should have at least two child filters");
    }

    @Override
    public Attributes getAttributes() {
	return null;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	ArrayList<XMLElement> returnvalue = new ArrayList<XMLElement>();
	for (int i = 0;i < filters.size(); i ++)
	    returnvalue.add((XMLElement)filters.get(i));
	return 	returnvalue;
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GETorFILTERandTag;
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
