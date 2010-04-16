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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * class for list of instructions
 * @author Johan Degraeve
 *
 */
public class GENERICinstructionList implements XMLElement {
    private ArrayList<Instruction> instructionSet;
    
    /**
     * constructor
     */
    public GENERICinstructionList() {
	instructionSet = new ArrayList<Instruction>();
    }

    /**
     * Executes the list of instructions.<br>
     * 
     * @param input a url or the source, anything starting with &lt; is considered to be a url
     * @return the result A string array of size 0 if input = null, the result of executing all instructions one by one
     * on the input, if input != null
     * @throws Exception
     */
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

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    public void addAttributes(Attributes arg0) throws SAXException {

    }

    /**
     * if arg0 is an instructions it will be added to the list, else throws an exception
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    public void addChild(XMLElement arg0) throws SAXException {
	if (arg0 instanceof Instruction)
		instructionSet.add((Instruction)arg0);
	else
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICinstructionListTag +
		    " can only have a child element of type Instruction");

    }

    /**
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    public void addText(String arg0) throws SAXException {
    }

    /**
     * throws an exception if the instructionlist is empty.
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    public void complete() throws SAXException {
	if (instructionSet.size() == 0)
	    throw new SAXException("Element " + TagAndAttributeNames.GENERICinstructionListTag +
		    " must have at least one instruction child element");
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
     * @return the list of instructions
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    public ArrayList<XMLElement> getChildren() {
	return new ArrayList<XMLElement>(instructionSet);
    }

    /**
     * @return the tag name
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    public String getTagName() {
	return TagAndAttributeNames.GENERICinstructionListTag;
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
     * does nothing
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    public void addUnTrimmedText(String text) throws SAXException {
    }

    /**
     * @return flase
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    public boolean preserveSpaces() {
	// XXX Auto-generated method stub
	return false;
    }


}
