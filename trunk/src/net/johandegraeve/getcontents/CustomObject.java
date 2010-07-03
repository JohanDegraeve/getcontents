package net.johandegraeve.getcontents;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import net.johandegraeve.easyxmldata.XMLElement;

/**
 * This class allows applications to add custom elements to the XML instruction List, typicall in the 
 *
 * @author Johan Degraeve
 *
 */
public abstract class CustomObject implements XMLElement {

    /**
     * @see net.johandegraeve.easyxmldata.XMLElement#addAttributes(org.xml.sax.Attributes)
     */
    @Override
    abstract public void addAttributes(Attributes attributes) throws SAXException;

    /**
     * @see net.johandegraeve.easyxmldata.XMLElement#addChild(net.johandegraeve.easyxmldata.XMLElement)
     */
    @Override
    abstract public void addChild(XMLElement child) throws SAXException;

    /**
     * @see net.johandegraeve.easyxmldata.XMLElement#addText(java.lang.String)
     */
    @Override
    abstract public void addText(String text) throws SAXException;

    /**
     * @see net.johandegraeve.easyxmldata.XMLElement#addUnTrimmedText(java.lang.String)
     */
    @Override
    abstract public void addUnTrimmedText(String text) throws SAXException;

    /**
     * @see net.johandegraeve.easyxmldata.XMLElement#complete()
     */
    @Override
    abstract public void complete() throws SAXException;

    /**
     * @see net.johandegraeve.easyxmldata.XMLElement#getAttributes()
     */
    @Override
    abstract public Attributes getAttributes();

    /**
     * @see net.johandegraeve.easyxmldata.XMLElement#getChildren()
     */
    @Override
    abstract public ArrayList<XMLElement> getChildren();

    /**
     * @see net.johandegraeve.easyxmldata.XMLElement#getTagName()
     */
    @Override
    abstract public String getTagName();

    /**
     * @see net.johandegraeve.easyxmldata.XMLElement#getText()
     */
    @Override
    abstract public String getText();

    /**
     * @see net.johandegraeve.easyxmldata.XMLElement#preserveSpaces()
     */
    @Override
    abstract public boolean preserveSpaces();

}
