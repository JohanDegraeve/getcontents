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

import org.htmlparser.tags.CompositeTag;

/**
 * Used as generic composite tag in the HTML parser
 * 
 * @author Johan Degraeve
 *
 */
@SuppressWarnings("serial")
public class UnknownHTMLTag extends CompositeTag {
    
    
    /**
     * The set of names handled by this tag.
     */
    private String[] mIds ;

    /**
     * The set of end tag names that indicate the end of this tag.
     */
    private String[] mEndTagEnders ;

    /**
     * Create a new Tag with tagName.
     * @param tagName 
     */
    public UnknownHTMLTag (String tagName)
    {
	mIds = new String[]{tagName};
	mEndTagEnders = new String[] {tagName};
    }

    /**
     * Return the set of names handled by this tag.
     * @return The names to be matched that create tags of this type.
     */
    public String[] getIds ()
    {
        return (mIds);
    }

    /**
     * Return the set of end tag names that cause this tag to finish.
     * @return The names of following end tags that stop further scanning.
     */
    public String[] getEndTagEnders ()
    {
        return (mEndTagEnders);
    }


    /**
     * Return a string .
     * @return the tag as HTML.
     */
    public String toString()
    {
        return
            toHtml();
    }

    
}
