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
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/*
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class GENERICinstructionList implements XMLElement {
    private ArrayList<Instruction> instructionSet;
    
    public GENERICinstructionList() {
	instructionSet = new ArrayList<Instruction>();
    }

    String[] execute(String input) throws Exception {
	String[] returnvalue;

	if (input == null || input.length() == 0)
	    return new String[0];
	
	returnvalue  = new String[]{input};
		
	//execute the instructions one by one
	for (int i = 0;i < instructionSet.size(); i++) {
	    returnvalue = instructionSet.get(i).execute(returnvalue);
	    if (returnvalue == null || returnvalue.length == 0)
		break;
	}
	
	return returnvalue;
    }

    @Override
    public void addAttributes(Attributes arg0) throws SAXException {

    }

    @Override
    public void addChild(XMLElement arg0) throws SAXException {
	if (arg0 instanceof Instruction)
		instructionSet.add((Instruction)arg0);
	else
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICinstructionListTag +
		    " can only have a child element of type Instruction");

    }

    @Override
    public void addText(String arg0) throws SAXException {
    }

    @Override
    public void complete() throws SAXException {
	if (instructionSet.size() == 0)
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICinstructionListTag +
		    " must have at least one instruction child element");
    }

    @Override
    public Attributes getAttributes() {
	return null;
    }

    @Override
    public ArrayList<XMLElement> getChildren() {
	return new ArrayList<XMLElement>(instructionSet);
    }

    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICinstructionListTag;
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
