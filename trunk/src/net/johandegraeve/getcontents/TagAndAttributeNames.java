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

/**
 * Names of the tags used in the XML. Changing these values requires also to changing some class names.
 *
 * @author Johan Degraeve
 *
 */
 class TagAndAttributeNames {

    /**
     * every class representing a specific Element will have as name 'prefix' followed by the tag name.
     */
    static final String instructionPrefix = "INSTRUCTION";
    static final String genericPrefix = "GENERIC";
    static final String GETorFILTERPrefix = "GETorFILTER";
    static final String STRING_PROCESSORPrefix = "STRING_PROCESSOR"; 
    
    /*
     * the tag names
     */
    //actually all  names with GENERIC in front should be changed, ie with GENERIC
    static final String GENERICreplaceTag = "replace";
    static final String GENERICfindTag = "find";
    static final String GENERICattributenameTag = "attributename";
    static final String GENERICattributevalueTag = "attributevalue";
    static final String GENERICdescriptionTag = "description";
    static final String GENERICencodingTag ="encoding";
    static final String GENERICgetcontentitemlistTag = "getContentItemList";
    static final String GENERICgetcontentitemTag = "getContentItem";
    static final String GETorFILTERandTag = "and"; 
    static final String GETorFILTERchildrenTag ="children";
    static final String GETorFILTERcssSelectorNodeTag = "cssSelectorNode";
    static final String GETorFILTERhasAttributeTag = "hasAttribute";
    static final String GETorFILTERhasChildTag = "hasChild";
    static final String GETorFILTERhasParentTag = "hasParent";
    static final String GETorFilterhasSiblingTag = "hasSibling";
    static final String GETorFILTERlinkRegexFilterTag = "linkRegexFilter";
    static final String GETorFILTERlinkStringFilterTag = "linkStringFilter";
    static final String GETorFILTERnotTag = "not";
    static final String GETorFILTERorTag = "or";
    static final String GETorFILTERregexFilterTag = "regexFilter";
    static final String GETorFILTERstringFilterTag = "stringFilter";
    static final String GETorFILTERtagNameTag = "tagName"; 
    static final String GETorFILTERtextTag = "text";
    static final String INSTRUCTIONaddRootElementTag = "addRootElement"; 
    static final String INSTRUCTIONaddXMLDeclarationTag = "addXMLDeclaration";
    static final String STRING_PROCESSORhtml2textTag = "html2text";
    static final String INSTRUCTIONhtmlFilterTag = "htmlFilter";
    static final String INSTRUCTIONhtmlGetterTag = "htmlGetter";
    static final String GENERICinstructionListTag = "instructionList";
    static final String STRING_PROCESSORtrimTag = "trim";
    static final String STRING_PROCESSORunescapeHtmlTag = "unescapeHTML";
    static final String INSTRUCTIONgetUnfilteredContent = "getUnfilteredContent";
    static final String INSTRUCTIONXMLFilterTag = "XMLFilter";
    static final String INSTRUCTIONXMLGetterTag = "XMLGetter";
    static final String INSTRUCTIONprocessStringTag = "processString";
    static final String GENERICpatternTag = "pattern";
    static final String GENERICurlTag = "url";
    static final String GENERICversionTag = "version";
    static final String GENERICstringTag ="string";
    static final String GETorFILTERcontainsAnyTag="containsAny";
    static final String GETorFILTERequalsAny = "equalsAny";
    static final String GETorFILTERendsWithAnyTag = "endsWithAny";
    static final String STRING_PROCESSORescapeHtmlTag = "escapeHtml";
    static final String STRING_PROCESSORfindAndReplaceTag = "findAndReplace";
    static final String STRING_PROCESSORmidPadTag = "midPad";
    static final String STRING_PROCESSORpostPadTag = "postPad";
    static final String STRING_PROCESSORprePadTag = "prePad";
    static final String STRING_PROCESSORprependTag = "prepend";
    static final String STRING_PROCESSORappendTag = "append";
    static final String STRING_PROCESSORsplitTag = "split";
    static final String STRING_PROCESSORidSelectorTag = "idSelector";
    static final String GENERICLocaleTag = "Locale";
    static final String GENERICSimpleDateFormatPatternTag = "SimpleDateFormatPattern";
    static final String GENERICAmPmStringsTag = "AmPmStrings";
    static final String GENERICErasTag = "Eras";
    static final String GENERICMonthsTag = "Months";
    static final String GENERICShortMonthsTag = "ShortMonths";
    static final String GENERICShortWeekDaysTag = "ShortWeekDays";
    static final String GENERICWeekDaysTag = "WeekDays";
    static final String GENERICDateFormatSymbolsTag = "DateFormatSymbols";
    static final String GENERICSimpleDateFormatTag = "SimpleDateFormat";
    static final String STRING_PROCESSORreadDateAndTimeTag = "readDateAndTime";
    static final String GENERICTimeZoneTag = "TimeZone";
    static final String GETorFILTERstartsWithAnyTag = "startsWithAny";
    static final String GETorFILTERremoveNodesTag = "removeNodes";

    
    /**
     * list of String processors
     */
    static final String[] stringProcessorTags = new String[] {
	STRING_PROCESSORhtml2textTag,
	STRING_PROCESSORescapeHtmlTag,
	STRING_PROCESSORunescapeHtmlTag,
	STRING_PROCESSORtrimTag,
	GETorFILTERequalsAny,
	GETorFILTERcontainsAnyTag,
	GETorFILTERendsWithAnyTag,
	STRING_PROCESSORfindAndReplaceTag,
	STRING_PROCESSORmidPadTag,
	STRING_PROCESSORpostPadTag,
	STRING_PROCESSORprePadTag,
	STRING_PROCESSORsplitTag,
	STRING_PROCESSORidSelectorTag,
	STRING_PROCESSORreadDateAndTimeTag,
	GETorFILTERstartsWithAnyTag,
	STRING_PROCESSORprependTag
    };
    
     /**
     * list of html filters 
     */
    static final String[] htmlfilterTags = new String[] {
	GETorFILTERandTag,
	GETorFILTERcssSelectorNodeTag,
	GETorFILTERhasAttributeTag,
	GETorFILTERhasChildTag,
	GETorFILTERhasParentTag,
	GETorFilterhasSiblingTag,
  	GETorFILTERlinkRegexFilterTag,
	GETorFILTERlinkStringFilterTag,
	GETorFILTERnotTag,
	GETorFILTERorTag,
	GETorFILTERregexFilterTag,
	GETorFILTERstringFilterTag,
	GETorFILTERtagNameTag
    };
    static String[] htmlgetterTags = new String[] {
	GETorFILTERchildrenTag,
	GETorFILTERtextTag,
	STRING_PROCESSORidSelectorTag,
	GETorFILTERremoveNodesTag
    };

    static final String[] XMLfilterTags = new String[] {
	GETorFILTERtagNameTag
    };

    static final String[] XMLgetterTags = new String[] {
	GETorFILTERchildrenTag,
	GETorFILTERtextTag,
	GETorFILTERcontainsAnyTag,
	GETorFILTERequalsAny,
	GETorFILTERendsWithAnyTag,
	GETorFILTERstartsWithAnyTag,
	STRING_PROCESSORidSelectorTag,
	GETorFILTERremoveNodesTag
    };

    /*
     * the attribute names
     */
     static final String idAttribute = "id";
     static final String variantAttribute = "variant";
     static final String case_sensitiveAttribute = "case_sensitive";
     static final String charsetnameAttribute = "charsetname";
     static  final String countryAttribute = "country";
     static final String languageAttribute = "language";
     static final String includeAttribute = "include";
     static final  String strategyAttribute = "strategy";
     static final String recursiveAttribute = "recursive";
     static final String lengthAttribute = "length";
     static final String characterAttribute = "character";
     static final String offsetAttribute = "offset";
     static final String chronologicalAttribute = "chronological";
     static final String charsetAttribute = "charset";
     
}
