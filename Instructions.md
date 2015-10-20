

# Introduction #

This page explains the contents of the XML instruction file.


# getContentItemList #

This is the root element in the instruction file.

**Attributes**
  * none

**Occurrence**
> since it is the root element, one and only one

**Allowed Children**
  * mandatory Children
    * getContentItem (1, 2, ...)


**Example**
```
<?xml version="1.0" encoding="UTF-8" ?>
<getContentItemList>
...
</getContentItemList>
```

## getContentItem ##

A getContentItem defines how and where to get the contents for one specific goal.
The XML element has one attribute called "id".
For example the goal could be to get the list of news titles. The requesting application needs to know the "id" to use to find the corresponding instructions.

Another example could be to fetch a TV guide. Typically a TV guide will consist of several columns, like "Program", "Channel", "Time", .. In such a case there would be three getContentItem's, each with another "id". To avoid having to get the same source (which is typicall an HTML or XML page downloaded over the internet), in such case there would be one first getContentItem whose goal is simply to download the complete page and to store in a String object. Then the next three getContentItem's would use that String as input and get a list of Programs, Channels and Time's; that means also that the source can also be a string supplied by the application.

**Attributes**
  * mandatory attributes
    * id
> > > A free format text used by the application to define the getContentItem to use.

**Occurrence**

> A getContentItemList must have at least one getContentItem

**Allowed Children**
  * mandatory Children
    * description (1)
    * instructionList (1)
  * optional Children
    * url : 0, 1 or more. If there's 1 or more url, then the first url must be reachable.
    * any element for which a class exists with the same name and derived from the class CustomObject. In this case, the GetContents object must be created with the constructor that takes a package name as argument, being the name of the package where this new class is defined.

**Example**
```
<?xml version="1.0" encoding="UTF-8" ?>
<getContentItemList>
   <getContentItem id="1"> 
...
   </getContentItem>
   <getContentItem id="another free format text"> 
...
   </getContentItem>
...
</getContentItemList>
```

### description ###

This is free format description. Not really used yet.

**Attributes**
  * none

**Occurrence**
> none or one

**Allowed Children**
> none


**Example**
```
<?xml version="1.0" encoding="UTF-8" ?>
<getContentItemList>
   <getContentItem id="1"> 
      <description>
          Android World forum
      </description>
...
   </getContentItem>
...
</getContentItemList>
```

### url ###

The url where the source needs to be downloaded.<br>
Can also be a local file.<br>
Format for a remote url : http://...<br>
Format for a local file : <a href='file://localhost/'>file://localhost/</a>....<br>

Is optional, in case not present, then application must start the instructionList with source parameter which can again be a url (anything starting with '<') or the actual source content (anything not starting with '<').<br>
<br>
<b>Attributes</b>
<ul><li>none</li></ul>

<b>Occurrence</b>
<blockquote>none or one</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>Example</b>

a remote url<br>
<pre><code>&lt;?xml version="1.0" encoding="UTF-8" ?&gt;<br>
&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt; <br>
      &lt;url&gt;<br>
          http://www.androidworld.nl/forum/external.php<br>
      &lt;/url&gt;<br>
...<br>
   &lt;/getContentItem&gt;<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>


a local file<br>
<pre><code>&lt;?xml version="1.0" encoding="UTF-8" ?&gt;<br>
&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt; <br>
      &lt;url&gt;<br>
          file://localhost/Users/Johan/temp/test.html<br>
      &lt;/url&gt;<br>
...<br>
   &lt;/getContentItem&gt;<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>


<h3>instructionList</h3>

List of instructions to be executed on the source.<br>
Instructions take as input an array of strings.<br>
An htmlFilter, htmlGetter, XMLFilter and XMLGetter can take a string<br>
or a url (remote or local file) as input. <br>
Also the instruction getUnFilteredContent takes as input a url.<br>

There can be consecuive XMLFilter and XMLGetter, however if the last instruction in an XMLGetter is the instruction "text", then it's not allowed to have it followed by additional XMLFilter or XMLGetter instructions.<br>
<br>
<b>Attributes</b>
<ul><li>none</li></ul>

<b>Occurrence</b>
<blockquote>at least 1</blockquote>

<b>Allowed Children</b>
<blockquote>at least one off :<br>
</blockquote><ul><li>addXMLDeclaration<br>
</li><li>getUnFilteredContent<br>
</li><li>htmlFilter<br>
</li><li>htmlGetter<br>
</li><li>processString<br>
</li><li>XMLFilter<br>
</li><li>XMLGetter</li></ul>

<b>Example</b>
<pre><code>&lt;?xml version="1.0" encoding="UTF-8" ?&gt;<br>
&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt; <br>
<br>
...<br>
   one or more instructions<br>
...<br>
   &lt;/getContentItem&gt;<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

<h4>addXMLDeclaration</h4>

Instruction to add an XML declaration on top of the input.<br>
This is not really needed anymore, if not needed will be deleted.<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<ul><li>Mandatory Children<br>
<ul><li>version<br>
</li><li>encoding</li></ul></li></ul>

<b>Example</b>


<h4>getUnFilteredContent</h4>

If the input is a URL (anything not starting with '<'), it will fetch the content.<br>
If the input is not a URL, it returns the input.<br>
So it is important, if the input is to be seen as a source text (ie not a URL), then the first non blank character should be a '<'.<br>re<br>
<br>
If the source is a url, an InputStreamReader is created with the url and the encoding as parameter (encoding is an optional child). An array will be returned with one element that has the retrieved content.<br>
If the source is not a url, then the source is returned.<br>

<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<ul><li>encoding, at most 1,  optional, default value the default characterset for the platform<br>
</li><li>requestProperty, 0, 1 or more, optional, in case http headers need to be added</li></ul>

<h5>encoding</h5>

defines the encoding to be used for opening the url('s)<br>
<br>
<h5>requestProperty</h5>

defines a header key and value<br>
the getUnfilteredContent instruction will use URLConnection.setRequestProperty(String key, String value)<br>

<h6>key</h6>

key of the request parameter<br>
<br>
<br>
<h6>value</h6>

value of the request parameter<br>
<br>
<br>
<h4>htmlFilter</h4>

An htmlFilter instruction can have an unlimited number of html filters. For filtering, the package <a href='http://htmlparser.sourceforge.net/'>HTML Parser</a> is used.<br>
<br>
The input to the htmlFilter instruction doesn't need to be a full HTML page that includes a body, head tag and so on. So you could have the output of one htmlFilter instruction, use that as input for example to a processString instruction, and use the output of that instruction again as input for a new htmlFilter instruction.<br>
<br>
The output of an htmlFilter will be an array of strings, where each string is the result of applying toHTML on each Node (as defined in the HTML Parser package).<br>
This output can then be used again as input to either another instruction like htmlFilter, htmlGetter, processString (it is unlikely that the output of an htmlFilter will be the  input to an XMLFilter or XMLGetter).<br>
<br>
<b>Attributes</b>
<ul><li>recursive, optional<br>
<blockquote>Values : true or false.<br>
If a first level node does not match a filter, and if recursive is true, then the filter will continue searching in the second level node and so on.<br>
</blockquote></li><li>charset, optional<br>
<blockquote>values : for instance "ISO-8859-1" or "UTF-8"<br>
Even if it is not always necessary to specify the charset to be used, because mostly it is available in the HTML page in a META TAG, it is safer to specify it.<br>
</blockquote></li><li>unknowntag : a list of tags not known by default by the HTML Parser, tags are separated by space. For example a site uses "small" as tag. This tag is not known to the HTML Parser so will not create decent child list. By adding the attribute tag="small", the parser will consider the element small as a decent TagNode.<br>
List can be longer eg "small script noscript"</li></ul>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>At least one child of the following list :<br>
</blockquote><ul><li>and<br>
</li><li>children<br>
</li><li>cssSelectorNode<br>
</li><li>hasAttribute<br>
</li><li>hasChild<br>
</li><li>hasParent<br>
</li><li>hasSibling<br>
</li><li>linkRegexFilter<br>
</li><li>linkStringFilter<br>
</li><li>not<br>
</li><li>or<br>
</li><li>regexFilter<br>
</li><li>stringFilter<br>
</li><li>tagName</li></ul>

<h5>and</h5>

An AndFilter has at least two other html filters as child.<br>
Nodes that match every filer match the and filter.<br>
<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>At least two other html filters :<br>
</blockquote><ul><li>and<br>
</li><li>children<br>
</li><li>cssSelectorNode<br>
</li><li>hasAttribute<br>
</li><li>hasChild<br>
</li><li>hasParent<br>
</li><li>hasSibling<br>
</li><li>linkRegexFilter<br>
</li><li>linkStringFilter<br>
</li><li>not<br>
</li><li>or<br>
</li><li>regexFilter<br>
</li><li>stringFilter<br>
</li><li>tagName</li></ul>

<b>Example</b><br>
In this example we filter on nodes that have a tagname "DIV" and an attribute border="0"<br>
<pre><code>&lt;getContentItemList&gt;<br>
...<br>
         &lt;htmlFilter recursive="true" charset="utf-8"&gt;<br>
            &lt;and&gt;<br>
               &lt;tagName&gt;<br>
                  DIV<br>
               &lt;/tagName&gt;<br>
               &lt;hasAttribute&gt;<br>
                   &lt;attributename&gt;<br>
                        &lt;![CDATA[border]]&gt;<br>
                   &lt;/attributename&gt;<br>
                   &lt;attributevalue&gt;<br>
                        &lt;![CDATA[0]]&gt;<br>
                   &lt;/attributevalue&gt;<br>
               &lt;/hasAttribute&gt;<br>
            &lt;/and&gt; <br>
         &lt;/htmlFilter&gt;<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

<h5>children</h5>

will get the children if any<br>
<br>
<b>Attributes</b>
none<br>
<br>
<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<b>Example</b><br>

<h5>cssSelectorNode</h5>

a CssSelectorNodeFilter as defined in the HTML parser package :<br>
<a href='http://htmlparser.sourceforge.net/'>HTML Parser</a>


<b>Attributes</b>
none<br>
<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>the Selector</blockquote>

<b>Example</b><br>


<h5>hasAttribute</h5>

checks for presence of a specified attribute, optionally with specified value.<br>
In case you want to filter on tags not known to the HTML Parser, then you must add a "tag" attribute to the first htmlFilter or htmlGetter preceding this htmlFilter.<br>
<br>
<br>
<b>Attributes</b>
<ul><li>type determines if the value (if present) should be considered 'equals' or 'startswith'. Hence the possible values are 'equals' and 'startswith'.<br>
Default value is 'equals'.</li></ul>

<b>Occurrence</b>
<blockquote>none</blockquote>

<b>Allowed Children</b>
<ul><li>attributename<br>
</li><li>attributevalue</li></ul>

<b>text field</b>
<blockquote>none</blockquote>

<b>Example</b><br>
In this example we filter on nodes that have an attribute border="0"<br>
<pre><code>&lt;getContentItemList&gt;<br>
...<br>
         &lt;htmlFilter recursive="true"&gt;<br>
...<br>
               &lt;hasAttribute&gt;<br>
                   &lt;attributename&gt;<br>
                        &lt;![CDATA[border]]&gt;<br>
                   &lt;/attributename&gt;<br>
                   &lt;attributevalue&gt;<br>
                        &lt;![CDATA[0]]&gt;<br>
                   &lt;/attributevalue&gt;<br>
               &lt;/hasAttribute&gt;<br>
....<br>
         &lt;/htmlFilter&gt;<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>


<h5>hasChild</h5>

a HasChildFilter as defined in the HTML parser package :<br>
<a href='http://htmlparser.sourceforge.net/'>HTML Parser</a>

Filters on nodes that have a child that matches another included filter<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<ul><li>mandatory child<br>
<blockquote>any other HTML filter</blockquote></li></ul>

<b>text field</b>
<blockquote>none</blockquote>

<b>Example</b><br>
In this example we filter on nodes that have a child node that has an attribute border="0"<br>
<pre><code>&lt;getContentItemList&gt;<br>
...<br>
         &lt;htmlFilter recursive="true"&gt;<br>
...<br>
           &lt;hasChild&gt;<br>
               &lt;hasAttribute&gt;<br>
                   &lt;attributename&gt;<br>
                        &lt;![CDATA[border]]&gt;<br>
                   &lt;/attributename&gt;<br>
                   &lt;attributevalue&gt;<br>
                        &lt;![CDATA[0]]&gt;<br>
                   &lt;/attributevalue&gt;<br>
               &lt;/hasAttribute&gt;<br>
           &lt;/hasChild&gt;<br>
....<br>
         &lt;/htmlFilter&gt;<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>


<h5>hasParent</h5>

a HasParentFilter as defined in the HTML parser package :<br>
<a href='http://htmlparser.sourceforge.net/'>HTML Parser</a>

Filters on nodes that have a parent node that matches another included filter<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<ul><li>mandatory child<br>
<blockquote>any other HTML filter</blockquote></li></ul>

<b>text field</b>
<blockquote>none</blockquote>

<b>Example</b><br>
In this example we filter on nodes that have a parent node that has an attribute border="0"<br>
<pre><code>&lt;getContentItemList&gt;<br>
...<br>
         &lt;htmlFilter recursive="true"&gt;<br>
...<br>
           &lt;hasParent&gt;<br>
               &lt;hasAttribute&gt;<br>
                   &lt;attributename&gt;<br>
                        &lt;![CDATA[border]]&gt;<br>
                   &lt;/attributename&gt;<br>
                   &lt;attributevalue&gt;<br>
                        &lt;![CDATA[0]]&gt;<br>
                   &lt;/attributevalue&gt;<br>
               &lt;/hasAttribute&gt;<br>
           &lt;/hasParent&gt;<br>
....<br>
         &lt;/htmlFilter&gt;<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>


<h5>hasSibling</h5>

a HasSiblingFilter as defined in the HTML parser package :<br>
<a href='http://htmlparser.sourceforge.net/'>HTML Parser</a>


<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<ul><li>mandatory child<br>
<blockquote>any other HTML filter</blockquote></li></ul>

<b>text field</b>
<blockquote>none</blockquote>


<h5>linkRegexFilter</h5>

a LinkRegexFilter  as defined in the HTML parser package :<br>
<a href='http://htmlparser.sourceforge.net/'>HTML Parser</a>


<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<ul><li>mandatory child<br>
<blockquote>pattern</blockquote></li></ul>

<b>text field</b>
<blockquote>none</blockquote>

<h6>pattern</h6>

a pattern being a string and a case_sensitive attribute.<br>
The pattern needs to compile with<br>
Pattern.compile(pattern,  case_sensitive == true ? 0: Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE));<br>
<br>
<b>Attributes</b>
<blockquote>case_sensitive true or false, default value = true</blockquote>

<b>Occurrence</b>
<ol><li></li></ol>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>the pattern string, untrimmed</blockquote>


<h5>linkStringFilter</h5>

a LinkStringFilter  as defined in the HTML parser package :<br>
<a href='http://htmlparser.sourceforge.net/'>HTML Parser</a>


<b>Attributes</b>
<blockquote>case_sensitive, value "true" or "false"</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>the pattern is defined by the textfield. The text field is stored untrimmed, that means for example inclusive carriage returns, ...</blockquote>


<h5>not</h5>

a NotFilter  as defined in the HTML parser package :<br>
<a href='http://htmlparser.sourceforge.net/'>HTML Parser</a>


<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>any HTML filter, only one</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>
Filters nodes that do not have text matching the regular expression<br>
<pre><code>&lt;getContentItemList&gt;<br>
...<br>
         &lt;htmlFilter recursive="true"&gt;<br>
...<br>
            &lt;not&gt;<br>
		&lt;regexFilter&gt;([01][0-9]|2[0-3]):[0-5][0-9]&lt;/regexFilter&gt;<br>
             &lt;/not&gt;<br>
....<br>
         &lt;/htmlFilter&gt;<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>


<h5>or</h5>

an OrFilter  as defined in the HTML parser package :<br>
<a href='http://htmlparser.sourceforge.net/'>HTML Parser</a>


<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>any HTML filter, at least two</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<b>Example</b><br>
In this example we filter on nodes that have a tagname "DIV" or an attribute border="0"<br>
<pre><code>&lt;getContentItemList&gt;<br>
...<br>
         &lt;htmlFilter recursive="true"&gt;<br>
            &lt;or&gt;<br>
               &lt;tagName&gt;<br>
                  DIV<br>
               &lt;/tagName&gt;<br>
               &lt;hasAttribute&gt;<br>
                   &lt;attributename&gt;<br>
                        &lt;![CDATA[border]]&gt;<br>
                   &lt;/attributename&gt;<br>
                   &lt;attributevalue&gt;<br>
                        &lt;![CDATA[0]]&gt;<br>
                   &lt;/attributevalue&gt;<br>
               &lt;/hasAttribute&gt;<br>
            &lt;/or&gt; <br>
         &lt;/htmlFilter&gt;<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

<h5>regexFilter</h5>

a RegexFilter as defined in the HTML parser package :<br>
<a href='http://htmlparser.sourceforge.net/'>HTML Parser</a>


<b>Attributes</b>
<blockquote>strategy : possible values are "find", "lookingat" or "match"</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>the pattern, untrimmed</blockquote>

<b>Example</b><br>
In this example we filter on nodes that have text with a time in the format HH:mm<br>
<pre><code>&lt;getContentItemList&gt;<br>
...<br>
        &lt;htmlFilter recursive="true"&gt;<br>
            &lt;regexFilter&gt;([01][0-9]|2[0-3]):[0-5][0-9]&lt;/regexFilter&gt;<br>
         &lt;/htmlFilter&gt;<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>


<h5>stringFilter</h5>

a StringFilter as defined in the HTML parser package :<br>
<a href='http://htmlparser.sourceforge.net/'>HTML Parser</a>
This is a fairly simplistic filter, so for more sophisticated string matching, for example newline and whitespace handling, use a regexFilter instead.<br>
<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<ul><li>Mandatory children<br>
<blockquote>pattern</blockquote></li></ul>

<b>text field</b>
<blockquote>none</blockquote>

<h6>pattern</h6>

Basically a string with one attribute named "case_sensitive" with value "true" or "false"<br>
The pattern needs to compile with<br>
Pattern.compile(pattern,  case_sensitive == true ? 0: Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE));<br>
Untrimmed.<br>
<br>
<b>Attributes</b>
<blockquote>"case_sensitive" with value "true" or "false", default value = true</blockquote>

<b>Occurrence</b>
<ol><li></li></ol>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>the pattern, untrimmed</blockquote>


<h5>tagName</h5>

a TagNameFilter as defined in the HTML parser package :<br>
<a href='http://htmlparser.sourceforge.net/'>HTML Parser</a>

In case you want to filter on tags not known to the HTML Parser, then you must add a "tag" attribute to the first htmlFilter or htmlGetter preceding this htmlFilter.<br>
<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>the tag name</blockquote>

<b>Example</b><br>
In this example we filter on nodes that have a tagname "DIV"<br>
<pre><code>&lt;getContentItemList&gt;<br>
...<br>
         &lt;htmlFilter recursive="true"&gt;<br>
               &lt;tagName&gt;<br>
                  DIV<br>
               &lt;/tagName&gt;<br>
         &lt;/htmlFilter&gt;<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>


<h4>htmlGetter</h4>

gets from html nodes.<br>
<br>
<br>
<b>Attributes</b>
<ul><li>recursive, optional,  with value true or false. If recursive and if one specific node returns no NodeList then dig deeper into that node<br>
</li><li>charset, optional<br>
<blockquote>values : for instance "ISO-8859-1" or "UTF-8"<br>
Even if it is not always necessary to specify the charset to be used, because mostly it is available in the HTML page in a META TAG, it is safer to specify it.<br>
</blockquote></li><li>unknowntag : a list of tags not known by default by the HTML Parser, tags are separated by space. For example a site uses "small" as tag. This tag is not known to the HTML Parser so will not create decent child list. By adding the attribute tag="small", the parser will consider the element small as a decent TagNode.<br>
List can be longer eg "small script noscript"</li></ul>



<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>One ore more of following HTMLGetter :<br>
</blockquote><ul><li>attributeValue<br>
</li><li>children<br>
</li><li>removeNodes<br>
</li><li>tagName<br>
</li><li>text</li></ul>

<b>text field</b>
<blockquote>none</blockquote>

<h5>attributeValue</h5>

allows to get the value of a specific attribute<br>
Will work on each first level node<br>
If a node does not have an attribute with the specified name, then an empty string is returned<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>the attributename</blockquote>

<b>example</b>

<h5>children</h5>

will get the children nodes of each node<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>
Let's assume we have following HTML as input, stored in /Users/Johan/temp/temp.txt<br>
<br>
<pre><code>&lt;TBODY&gt;<br>
  &lt;TR bgColor=#f0f0f0&gt;<br>
   &lt;TD width="40%"&gt;&lt;strong&gt;Item&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%"&gt;&lt;strong&gt;Hoeveel&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%"&gt;&lt;strong&gt;Eenheid&lt;/strong&gt;&lt;/TD&gt;<br>
<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;kcal&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;Eiwit (g)&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;koolh (g)&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;vet (g)&lt;/strong&gt;&lt;/TD&gt;<br>
  &lt;/TR&gt;<br>
&lt;/TBODY&gt;<br>
</code></pre>

Now we run following instruction file :<br>
<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
      &lt;url&gt;<br>
         &lt;![CDATA[file://localhost/Users/Johan/temp/temp.txt]]&gt;<br>
      &lt;/url&gt;<br>
      &lt;description&gt;<br>
         &lt;![CDATA[example htmlGetter - children]]&gt;<br>
      &lt;/description&gt;<br>
      &lt;instructionList&gt;<br>
         &lt;htmlGetter&gt;<br>
            &lt;children&gt;<br>
            &lt;/children&gt;<br>
         &lt;/htmlGetter&gt;   <br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

the result is<br>
<br>
<pre><code>&lt;TR bgColor=#f0f0f0&gt;<br>
   &lt;TD width="40%"&gt;&lt;strong&gt;Item&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%"&gt;&lt;strong&gt;Hoeveel&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%"&gt;&lt;strong&gt;Eenheid&lt;/strong&gt;&lt;/TD&gt;<br>
<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;kcal&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;Eiwit (g)&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;koolh (g)&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;vet (g)&lt;/strong&gt;&lt;/TD&gt;<br>
  &lt;/TR&gt;<br>
</code></pre>


we can go one step further with the instructin file :<br>
<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
      &lt;url&gt;<br>
         &lt;![CDATA[file://localhost/Users/Johan/temp/temp.txt]]&gt;<br>
      &lt;/url&gt;<br>
      &lt;description&gt;<br>
         &lt;![CDATA[Programs http://www.belgids.be/television haal de zenders op]]&gt;<br>
      &lt;/description&gt;<br>
      &lt;instructionList&gt;<br>
         &lt;htmlGetter&gt;<br>
            &lt;children&gt;<br>
            &lt;/children&gt;<br>
            &lt;children&gt;<br>
            &lt;/children&gt;<br>
         &lt;/htmlGetter&gt;   <br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

now the result is (I must admit that I removed a lot of blank lines, I'm sure there will be other instructions to remove the blank lines like String Processors :<br>
<pre><code>&lt;TD width="40%"&gt;&lt;strong&gt;Item&lt;/strong&gt;&lt;/TD&gt;<br>
&lt;TD width="10%"&gt;&lt;strong&gt;Hoeveel&lt;/strong&gt;&lt;/TD&gt;   <br>
&lt;TD width="10%"&gt;&lt;strong&gt;Eenheid&lt;/strong&gt;&lt;/TD&gt;   <br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;kcal&lt;/strong&gt;&lt;/TD&gt;   <br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;Eiwit (g)&lt;/strong&gt;&lt;/TD&gt;   <br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;koolh (g)&lt;/strong&gt;&lt;/TD&gt;   <br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;vet (g)&lt;/strong&gt;&lt;/TD&gt;<br>
</code></pre>

<h5>removeNodes</h5>

Two criteria can be defined to remove a node :<br>
- all nodes matching the specified tag<br>
- all nodes having an attribute with the specified name and optionally with the specified value (case insensitive)<br>

both can be defined within one single removeNodes element<br>
- tag : just a a tag as text in the removeNodes element<br>
- attribute : add a child attributename and optionally a child attributevalue in the removeNodes element.<br>

<b>Attributes</b>
<blockquote>recursive, if true will search till the end, otherwise false</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<ul><li>attributename (optional)<br>
</li><li>attributevalue (optional but may not occur if there's no attributename child)</li></ul>

<b>text field</b>
<blockquote>the tagname</blockquote>

<b>example</b>

<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
      &lt;url&gt;<br>
         ...<br>
      &lt;/url&gt;<br>
...<br>
      &lt;instructionList&gt;<br>
         &lt;htmlGetter recursive="false"&gt;<br>
            &lt;removeNodes recursive="true"&gt;script&lt;/removeNodes&gt;<br>
         &lt;/htmlGetter&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;  <br>
</code></pre>

<h5>tagName</h5>

will keep tags with specified node, works as HTMLFilter tagName<br>
When using a list of unknowntag (as attribute in the HTMLGetter) then it is not always working exactly as HTMLFiler, sometimes it is better to us an HTMLFilter.<br>
<br>
<br>
<h5>text</h5>

will get the text using org.htmlparser.Node.toPlainTextString()<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>

we start with following input, stored in  /Users/Johan/temp/temp.txt :<br>
<br>
<pre><code> &lt;TBODY&gt;<br>
  &lt;TR bgColor=#f0f0f0&gt;<br>
   &lt;TD width="40%"&gt;&lt;strong&gt;Item&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%"&gt;&lt;strong&gt;Hoeveel&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%"&gt;&lt;strong&gt;Eenheid&lt;/strong&gt;&lt;/TD&gt;<br>
<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;kcal&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;Eiwit (g)&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;koolh (g)&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;vet (g)&lt;/strong&gt;&lt;/TD&gt;<br>
  &lt;/TR&gt;<br>
&lt;/TBODY&gt;<br>
</code></pre>

to which we apply following instructionlist :<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
      &lt;url&gt;<br>
         &lt;![CDATA[file://localhost/Users/Johan/temp/temp.txt]]&gt;<br>
      &lt;/url&gt;<br>
      &lt;description&gt;<br>
         &lt;![CDATA[example]]&gt;<br>
      &lt;/description&gt;<br>
      &lt;instructionList&gt;<br>
         &lt;htmlGetter recursive="false"&gt;<br>
            &lt;text&gt;<br>
            &lt;/text&gt;<br>
         &lt;/htmlGetter&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;  <br>
</code></pre>

resulting in two Strings :<br>
<br>
<pre><code>String 0, length = 1<br>
 <br>
String 1, length = 83<br>
<br>
  <br>
   Item<br>
   Hoeveel<br>
   Eenheid<br>
<br>
   kcal<br>
   Eiwit (g)<br>
   koolh (g)<br>
   vet (g)<br>
</code></pre>

The method toPlainTextString will work here on two Nodes. The first node being a text node which is appearing on top of the input, before the TBODY tag, namely there's one blank before the TBODY opening tag, which has length 1. Then a TBODY node, of which all text is taken, inclusive carriage returns.<br>
<br>
<h4>processString</h4>

To do stuff with strings. Usually that will be the last list of instructions and will take a list of strings as input. That list of strings is the output of the last html- or XML- Filter or Getter.<br>
For some of the string processors, I've been using a package called com.Ostermiller.util, class StringHelper.<br>

A string processor can never be the first instruction in an instructionList. In case you want to use non-HTML or XML as input to a the list of instructions, start with the instruction "getUnFilteredContent"<br>
<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>One ore more of following StringProcessor :<br>
</blockquote><ul><li>append<br>
</li><li>containsAny<br>
</li><li>endsWithAny<br>
</li><li>equalsAny<br>
</li><li>escapeHTML<br>
</li><li>findAndReplace<br>
</li><li>html2text<br>
</li><li>idSelector<br>
</li><li>midPad<br>
</li><li>postPad<br>
</li><li>prepend<br>
</li><li>prePad<br>
</li><li>readDateAndTime<br>
</li><li>removeInvisibleChars<br>
</li><li>repeat<br>
</li><li>split<br>
</li><li>startsWithAny<br>
</li><li>trim<br>
</li><li>unescapeHTML</li></ul>

<b>text field</b>
<blockquote>none</blockquote>

<h5>append</h5>

appends a string to each string in the input<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>the string to append, untrimmed</blockquote>

<b>example</b>

<pre><code>&lt;getContentItemList&gt;<br>
...<br>
         &lt;processString&gt;<br>
            &lt;append&gt;&lt;![CDATA[piece of text to append to each string]]&gt;&lt;/append&gt;<br>
         &lt;/processString&gt;<br>
...         <br>
&lt;/getContentItemList&gt;<br>
</code></pre>

<h5>containsAny</h5>

Filters on nodes that have text that contains any of a list of strings<br>
Using com.Ostermiller.util, class StringHelper, method containsAnyIgnoreCase and containsAny<br>
<br>
<b>Attributes</b>
<ul><li>case_sensitive, values true or false, default true<br>
</li><li>include, values true or false, default false</li></ul>


<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>one or more string</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>
As input for an example we can start with a small piece of HTML<br>
<br>
<pre><code>&lt;TBODY&gt;<br>
  &lt;TR bgColor=#f0f0f0&gt;<br>
   &lt;TD width="40%"&gt;&lt;strong&gt;Item&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%"&gt;&lt;strong&gt;Hoeveel&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%"&gt;&lt;strong&gt;Eenheid&lt;/strong&gt;&lt;/TD&gt;<br>
<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;kcal&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;Eiwit (g)&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;koolh (g)&lt;/strong&gt;&lt;/TD&gt;<br>
   &lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;vet (g)&lt;/strong&gt;&lt;/TD&gt;<br>
  &lt;/TR&gt;<br>
&lt;/TBODY&gt;<br>
</code></pre>

Now we first apply a tagName Filter for tag "TD" :<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
      &lt;url&gt;<br>
         &lt;![CDATA[file://localhost/Users/Johan/temp/temp.txt]]&gt;<br>
      &lt;/url&gt;<br>
      &lt;description&gt;<br>
         &lt;![CDATA[example]]&gt;<br>
      &lt;/description&gt;<br>
      &lt;instructionList&gt;<br>
         &lt;htmlFilter recursive="true"&gt;<br>
            &lt;tagName&gt;<br>
                TD<br>
            &lt;/tagName&gt;<br>
         &lt;/htmlFilter&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

the result is a list of 7 Strings :<br>
<pre><code>7 matching lines<br>
Result 0, length = 42<br>
&lt;TD width="40%"&gt;&lt;strong&gt;Item&lt;/strong&gt;&lt;/TD&gt;<br>
Result 1, length = 45<br>
&lt;TD width="10%"&gt;&lt;strong&gt;Hoeveel&lt;/strong&gt;&lt;/TD&gt;<br>
Result 2, length = 45<br>
&lt;TD width="10%"&gt;&lt;strong&gt;Eenheid&lt;/strong&gt;&lt;/TD&gt;<br>
Result 3, length = 57<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;kcal&lt;/strong&gt;&lt;/TD&gt;<br>
Result 4, length = 62<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;Eiwit (g)&lt;/strong&gt;&lt;/TD&gt;<br>
Result 5, length = 62<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;koolh (g)&lt;/strong&gt;&lt;/TD&gt;<br>
Result 6, length = 60<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;vet (g)&lt;/strong&gt;&lt;/TD&gt;<br>
</code></pre>

Now we extend the instructionlist with a containsAny string processor, with two strings, "ei" and "cal", not case_sensitive :<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
      &lt;url&gt;<br>
         &lt;![CDATA[file://localhost/Users/Johan/temp/temp.txt]]&gt;<br>
      &lt;/url&gt;<br>
      &lt;description&gt;<br>
         &lt;![CDATA[example]]&gt;<br>
      &lt;/description&gt;<br>
      &lt;instructionList&gt;<br>
         &lt;htmlFilter recursive="true"&gt;<br>
            &lt;tagName&gt;<br>
                TD<br>
            &lt;/tagName&gt;<br>
         &lt;/htmlFilter&gt;<br>
         &lt;processString&gt;<br>
           &lt;containsAny case_sensitive="false" include="true"&gt;<br>
			  &lt;string&gt;ei&lt;/string&gt;<br>
			  &lt;string&gt;Cal&lt;/string&gt;<br>
		   &lt;/containsAny&gt;<br>
         &lt;/processString&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

resulting in :<br>
<br>
3 matching lines<br>
<pre><code>&lt;TD width="10%"&gt;&lt;strong&gt;Eenheid&lt;/strong&gt;&lt;/TD&gt;<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;kcal&lt;/strong&gt;&lt;/TD&gt;<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;Eiwit (g)&lt;/strong&gt;&lt;/TD&gt;<br>
</code></pre>

The processor checks the complete String. I might as well define a search string "width" which would return all 7 strings.<br>
<br>
<h5>equalsAny</h5>

Filters on nodes that have text that equals any of a list of strings<br>
Using com.Ostermiller.util, class StringHelper, method equalsAnyIgnoreCase and equalsAny<br>
<br>
see also the string processor "containsAny"<br>
<br>
<br>
<h5>endsWithAny</h5>

Filters on nodes that have text that ends with any of a list of strings<br>
Using com.Ostermiller.util, class StringHelper, method endsWithAnyIgnoreCase and endsWithAny<br>
<br>
see also the string processor "containsAny"<br>
<br>
<br>
<h5>escapeHTML</h5>

escapes HTML<br>
Using com.Ostermiller.util, class StringHelper, method escameHTML.<br>
<br>
This will normally not be used as the goal of this package is to retrieve content from HTML (and XML) pages, not the other way around.<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>
Let's start with following input :<br>
<br>
<pre><code>This is a less than sign : &lt;<br>
</code></pre>

Now we apply following instructionlist :<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
      &lt;url&gt;<br>
         &lt;![CDATA[file://localhost/Users/Johan/temp/temp.txt]]&gt;<br>
      &lt;/url&gt;<br>
      &lt;description&gt;<br>
         &lt;![CDATA[example]]&gt;<br>
      &lt;/description&gt;<br>
      &lt;instructionList&gt;<br>
         &lt;getUnFilteredContent&gt;<br>
         &lt;/getUnFilteredContent&gt;<br>
         &lt;processString&gt;<br>
            &lt;escapeHTML&gt;<br>
            &lt;/escapeHTML&gt;<br>
         &lt;/processString&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

the result is :<br>
<pre><code>This is a less than sign : &amp;lt;<br>
</code></pre>

<h5>findAndReplace</h5>

to find and replace a string<br>
Using com.Ostermiller.util, class StringHelper, method replace.<br>
In the find and replace elements (children of findAndReplace) it is possible to define specific invisible characters with "\t", "\b","\n","\r","\f".<br>
<br>
<b>Attributes</b>
<blockquote>case sensitive, value "true" or "false"</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<ul><li>find<br>
</li><li>replace</li></ul>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>
Let's start an instructionlist that fetches a list of TV programs :<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
      &lt;url&gt;<br>
         &lt;![CDATA[http://www.belgids.be/television]]&gt;<br>
      &lt;/url&gt;<br>
      &lt;description&gt;<br>
         &lt;![CDATA[Programs http://www.belgids.be/television haal de programa's op]]&gt;<br>
      &lt;/description&gt;<br>
      &lt;instructionList&gt;<br>
         &lt;htmlFilter recursive="true"&gt;<br>
            &lt;and&gt;<br>
               &lt;hasChild&gt;<br>
                  &lt;hasChild&gt;<br>
                     &lt;hasChild&gt;<br>
                        &lt;stringFilter case_sensitive="false"&gt;<br>
                           &lt;pattern case_sensitive="false"&gt;&lt;![CDATA[Time]]&gt;&lt;/pattern&gt;<br>
                        &lt;/stringFilter&gt;<br>
                     &lt;/hasChild&gt;<br>
                  &lt;/hasChild&gt;<br>
               &lt;/hasChild&gt;<br>
               &lt;and&gt;<br>
                  &lt;tagName&gt;<br>
                     &lt;![CDATA[table]]&gt;<br>
                  &lt;/tagName&gt;<br>
                  &lt;hasAttribute&gt;<br>
                     &lt;attributename&gt;<br>
                        &lt;![CDATA[border]]&gt;<br>
                     &lt;/attributename&gt;<br>
                     &lt;attributevalue&gt;<br>
                        &lt;![CDATA[0]]&gt;<br>
                     &lt;/attributevalue&gt;<br>
                  &lt;/hasAttribute&gt;<br>
               &lt;/and&gt;<br>
            &lt;/and&gt; <br>
            &lt;hasAttribute&gt;<br>
               &lt;attributename&gt;<br>
                  &lt;![CDATA[width]]&gt;<br>
               &lt;/attributename&gt;<br>
               &lt;attributevalue&gt;<br>
                  &lt;![CDATA[60%]]&gt;<br>
               &lt;/attributevalue&gt;<br>
            &lt;/hasAttribute&gt;<br>
         &lt;/htmlFilter&gt;<br>
         &lt;htmlGetter recursive="false"&gt;<br>
            &lt;text&gt;<br>
            &lt;/text&gt;<br>
         &lt;/htmlGetter&gt;<br>
         &lt;processString&gt;<br>
            &lt;trim&gt;<br>
            &lt;/trim&gt;<br>
         &lt;/processString&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;}}}<br>
</code></pre>

This may result in a list of tv programs, but unfortunately it may contains some unreadable characters, like for example :<br>
<pre><code>Kookstrijders BelgiÃ«-Nederland:<br>
</code></pre>

So we can add a find and replace instruction which will replace Ã« into ë<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
      &lt;url&gt;<br>
         &lt;![CDATA[http://www.belgids.be/television]]&gt;<br>
      &lt;/url&gt;<br>
      &lt;description&gt;<br>
         &lt;![CDATA[Programs http://www.belgids.be/television haal de programa's op]]&gt;<br>
      &lt;/description&gt;<br>
      &lt;instructionList&gt;<br>
         &lt;htmlFilter recursive="true"&gt;<br>
            &lt;and&gt;<br>
               &lt;hasChild&gt;<br>
                  &lt;hasChild&gt;<br>
                     &lt;hasChild&gt;<br>
                        &lt;stringFilter case_sensitive="false"&gt;<br>
                           &lt;pattern case_sensitive="false"&gt;&lt;![CDATA[Time]]&gt;&lt;/pattern&gt;<br>
                        &lt;/stringFilter&gt;<br>
                     &lt;/hasChild&gt;<br>
                  &lt;/hasChild&gt;<br>
               &lt;/hasChild&gt;<br>
               &lt;and&gt;<br>
                  &lt;tagName&gt;<br>
                     &lt;![CDATA[table]]&gt;<br>
                  &lt;/tagName&gt;<br>
                  &lt;hasAttribute&gt;<br>
                     &lt;attributename&gt;<br>
                        &lt;![CDATA[border]]&gt;<br>
                     &lt;/attributename&gt;<br>
                     &lt;attributevalue&gt;<br>
                        &lt;![CDATA[0]]&gt;<br>
                     &lt;/attributevalue&gt;<br>
                  &lt;/hasAttribute&gt;<br>
               &lt;/and&gt;<br>
            &lt;/and&gt; <br>
            &lt;hasAttribute&gt;<br>
               &lt;attributename&gt;<br>
                  &lt;![CDATA[width]]&gt;<br>
               &lt;/attributename&gt;<br>
               &lt;attributevalue&gt;<br>
                  &lt;![CDATA[60%]]&gt;<br>
               &lt;/attributevalue&gt;<br>
            &lt;/hasAttribute&gt;<br>
         &lt;/htmlFilter&gt;<br>
         &lt;htmlGetter recursive="false"&gt;<br>
            &lt;text&gt;<br>
            &lt;/text&gt;<br>
         &lt;/htmlGetter&gt;<br>
         &lt;processString&gt;<br>
            &lt;trim&gt;<br>
            &lt;/trim&gt;<br>
            &lt;findAndReplace&gt;<br>
                &lt;find&gt;Ã«&lt;/find&gt;<br>
                &lt;replace&gt;ë&lt;/replace&gt;<br>
            &lt;/findAndReplace&gt;<br>
         &lt;/processString&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;}}}<br>
</code></pre>

Now we get a nicer result , here's the result (I didn't add the full list of tv programs, only the one whic contained the dirty character :<br>
<br>
<pre><code>...<br>
Kookstrijders België-Nederland:<br>
...<br>
</code></pre>

<h5>html2text : remove everything between < and ></h5>

removes everything between < and ><br>

<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>

with following input stored in /Users/Johan/temp/temp.txt<br>
<br>
<pre><code>&lt;p&gt;&lt;a href="http://www.iwm.org.uk/server/show/ConWebDoc.3648"&gt;&lt;strong&gt;Current Annual Report&lt;/strong&gt;&lt;/a&gt;<br>
&lt;br&gt;The Annual Report and Account examines the performance of the Museum over the financial year through the auditor&amp;#8217;s report, visitor figures, costs, government funding and information on assets. The report also includes information on internal structure and management as well as plans for the future.&lt;/p&gt;<br>
<br>
&lt;p&gt;&lt;a href="http://www.iwm.org.uk/server/show/ConWebDoc.5812"&gt;&lt;strong&gt;Current Annual Review&lt;/strong&gt;&lt;/a&gt;<br>
&lt;br&gt;The Annual Review acts as a condensed version of the Annual Report and Account, focusing on the key information related to the Museum&amp;#8217;s performance. &lt;/p&gt;<br>
&lt;h1&gt;Plans and Governance&lt;/h1&gt;<br>
<br>
&lt;p&gt;&lt;a href="http://www.iwm.org.uk/upload/pdf/IWMCP10web2.pdf"&gt;&lt;strong&gt;Corporate Plan 2010 - 2011&lt;/strong&gt;&lt;/a&gt;<br>
&lt;br&gt;The Corporate Plan is the key to coordinating activities across the Museum&amp;#8217;s branches along the same organisation-wide priorities and is the starting point for all staff forward job plans. It also serves as a benchmark to monitor the Museum&amp;#8217;s progress against plans throughout the year and it seeks to respond to the risks and opportunities facing the Museum.&lt;/p&gt;<br>
<br>
&lt;p&gt;&lt;a href="http://www.iwm.org.uk/server/show/ConWebDoc.3978"&gt;&lt;strong&gt;Current Funding Agreement&lt;/strong&gt;&lt;/a&gt;&lt;/p&gt;<br>
<br>
&lt;p&gt;&lt;a href="http://www.iwm.org.uk/server/show/ConWebDoc.4621"&gt;&lt;strong&gt;Corporate Governance Code&lt;/strong&gt;&lt;/a&gt;&lt;/p&gt;<br>
</code></pre>

with following instructionlist :<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
      &lt;url&gt;<br>
         &lt;![CDATA[file://localhost/Users/Johan/temp/temp.txt]]&gt;<br>
      &lt;/url&gt;<br>
      &lt;description&gt;<br>
         &lt;![CDATA[example]]&gt;<br>
      &lt;/description&gt;<br>
      &lt;instructionList&gt;<br>
         &lt;getUnFilteredContent&gt;<br>
         &lt;/getUnFilteredContent&gt;<br>
         &lt;processString&gt;<br>
            &lt;html2text&gt;<br>
            &lt;/html2text&gt;<br>
         &lt;/processString&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

the result is 14 Strings :<br>
<br>
<pre><code>Current Annual Report<br>
The Annual Report and Account examines the performance of the Museum over the financial year through the auditor&amp;#8217;s report, visitor figures, costs, government funding and information on assets. The report also includes information on internal structure and management as well as plans for the future.<br>
<br>
Current Annual Review<br>
The Annual Review acts as a condensed version of the Annual Report and Account, focusing on the key information related to the Museum&amp;#8217;s performance. <br>
Plans and Governance<br>
<br>
Corporate Plan 2010 - 2011<br>
The Corporate Plan is the key to coordinating activities across the Museum&amp;#8217;s branches along the same organisation-wide priorities and is the starting point for all staff forward job plans. It also serves as a benchmark to monitor the Museum&amp;#8217;s progress against plans throughout the year and it seeks to respond to the risks and opportunities facing the Museum.<br>
<br>
Current Funding Agreement<br>
<br>
Corporate Governance Code<br>
</code></pre>

<h5>idSelector : include or exclude specific elements identified by id</h5>

It is possible to specify the number of an element, to be included or excluded.<br>
The first element has id 1, the second element has id 2 ... , but it is also possible<br>
to identify the last element which has id 0, the last but one element has id -1<br>
More than one element can be specified or a range of elements.<br>
<br>
<b>Attributes</b>
<blockquote>include, possible values "true" or "false", default value "true"</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>the id's, see example</blockquote>

<b>example</b>

Let us take following input<br>
<br>
<br>
<pre><code>&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;kcal&lt;/strong&gt;&lt;/TD&gt;<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;Eiwit (g)&lt;/strong&gt;&lt;/TD&gt;<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;koolh (g)&lt;/strong&gt;&lt;/TD&gt;<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;vet (g)&lt;/strong&gt;&lt;/TD&gt;<br>
</code></pre>

we first push it through a getUnFilteredContent instruction which will give 4 strings. Then we can select for example the first line by adding an idSelector<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
      &lt;url&gt;<br>
         &lt;![CDATA[file://localhost/Users/Johan/temp/temp.txt]]&gt;<br>
      &lt;/url&gt;<br>
      &lt;description&gt;<br>
         &lt;![CDATA[example]]&gt;<br>
      &lt;/description&gt;<br>
      &lt;instructionList&gt;<br>
         &lt;getUnFilteredContent&gt;<br>
         &lt;/getUnFilteredContent&gt;<br>
         &lt;processString&gt;<br>
            &lt;idSelector include="true"&gt;<br>
            1<br>
            &lt;/idSelector&gt;<br>
         &lt;/processString&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

This gives us the first line as result<br>
<br>
<pre><code>&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;kcal&lt;/strong&gt;&lt;/TD&gt;<br>
</code></pre>

we can also define include="false", which will exclude the first line<br>
<br>
<pre><code>...<br>
            &lt;idSelector include="false"&gt;<br>
            1<br>
            &lt;/idSelector&gt;<br>
...<br>
</code></pre>

resulting in<br>
<br>
<pre><code>&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;Eiwit (g)&lt;/strong&gt;&lt;/TD&gt;<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;koolh (g)&lt;/strong&gt;&lt;/TD&gt;<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;vet (g)&lt;/strong&gt;&lt;/TD&gt;<br>
</code></pre>

or define a range<br>
<br>
<br>
<pre><code>...<br>
            &lt;idSelector include="false"&gt;<br>
            1:3<br>
            &lt;/idSelector&gt;<br>
...<br>
</code></pre>

resulting in<br>
<br>
<pre><code>&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;kcal&lt;/strong&gt;&lt;/TD&gt;<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;Eiwit (g)&lt;/strong&gt;&lt;/TD&gt;<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;koolh (g)&lt;/strong&gt;&lt;/TD&gt;<br>
</code></pre>

or identify for example the last but one element, and include only that row<br>
<br>
<pre><code>...<br>
            &lt;idSelector include="true"&gt;<br>
            -1<br>
            &lt;/idSelector&gt;<br>
...<br>
</code></pre>

resulting in :<br>
<br>
<pre><code>&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;koolh (g)&lt;/strong&gt;&lt;/TD&gt;<br>
</code></pre>

or this time we exclude two specific lines :<br>
<br>
<pre><code>...<br>
            &lt;idSelector include="false"&gt;<br>
            1,3<br>
            &lt;/idSelector&gt;<br>
...<br>
</code></pre>

resulting in<br>
<br>
<pre><code>&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;Eiwit (g)&lt;/strong&gt;&lt;/TD&gt;<br>
&lt;TD width="10%" ALIGN="center"&gt;&lt;strong&gt;vet (g)&lt;/strong&gt;&lt;/TD&gt;<br>
</code></pre>

<h5>midPad : pad beginning and end</h5>

Pad the beginning and end of each String with the given character until the result is the desired length. The result is that the original String is centered in the middle of the new string.<br>

If the number of characters to pad is even, then the padding will be distributed evenly between the beginning and end, otherwise, the extra character will be added to the end.<br>
<br>
<b>Attributes</b>
<ul><li>mandatory attribute<br>
<blockquote>length : defines the minimum length that the new String should have<br>
</blockquote></li><li>optional attribute<br>
<blockquote>character : defines the character to pad, default is one blank space</blockquote></li></ul>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>

start with following input<br>
<br>
<pre><code>this is text 1<br>
this is a long text, it's a very long text<br>
this is text 2<br>
</code></pre>

apply following instruction list :<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
      &lt;url&gt;<br>
         &lt;![CDATA[file://localhost/Users/Johan/temp/temp.txt]]&gt;<br>
      &lt;/url&gt;<br>
      &lt;description&gt;<br>
         &lt;![CDATA[example]]&gt;<br>
      &lt;/description&gt;<br>
      &lt;instructionList&gt;<br>
         &lt;getUnFilteredContent&gt;<br>
         &lt;/getUnFilteredContent&gt;<br>
         &lt;processString&gt;<br>
            &lt;midPad character="+" length = "20"&gt;<br>
            &lt;/midPad&gt;<br>
         &lt;/processString&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

and this is the result :<br>
<br>
<pre><code>+++this is text 1+++<br>
this is a long text, it's a very long text<br>
+++this is text 2+++<br>
</code></pre>

<h5>postPad : pad end</h5>

Append the given character to the String until the result is the desired length. If a String is longer than the desired length, it will not be truncated, however no padding will be added.<br>


<b>Attributes</b>
<ul><li>mandatory attribute<br>
<blockquote>length : defines the minimum length that the new String should have<br>
</blockquote></li><li>optional attribute<br>
<blockquote>character : defines the character to pad, default is one blank space</blockquote></li></ul>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>

see midPad<br>
<br>
<h5>prepend</h5>

prepends a string to each string in the input<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>the string to prepend, untrimmed</blockquote>

<b>example</b>

<pre><code>&lt;getContentItemList&gt;<br>
...<br>
         &lt;processString&gt;<br>
            &lt;prepend&gt;&lt;![CDATA[piece of text to prepend to each string]]&gt;&lt;/prepend&gt;<br>
         &lt;/processString&gt;<br>
...         <br>
&lt;/getContentItemList&gt;<br>
</code></pre>

<h5>prePad : pad beginning</h5>

Prepend the given character to the String until the result is the desired length. If a String is longer than the desired length, it will not be truncated, however no padding will be added.<br>


<b>Attributes</b>
<ul><li>mandatory attribute<br>
<blockquote>length : defines the minimum length that the new String should have<br>
</blockquote></li><li>optional attribute<br>
<blockquote>character : defines the character to pad, default is one blank space</blockquote></li></ul>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>

see midPad<br>
<br>
<h5>readDateAndTime</h5>

To parse a date and time field. The Date and Time is a string representation of the long value of the date and time , as created by the Java method Date.getTime(), ie the number of milliseconds since January 1, 1970, 00:00:00 GMT.<br>
Has a mandatory child element named SimpleDateFormat. This SimpleDateFormat can be created in exactly the same way as a the corresponding java object.<br>
Another child element is the optional element TimeZone.<br>

<b>Attributes</b>
<ul><li>chronology, optional, values "ascending" or "descending", default none. A value of ascending or descending does not mean that the resulting list will be sorted. It means the instruction will try to correct the values, but this is only possible if the attribute offset is defined. This value will be used while trying to put the result in chronological order. Chronology assume that the values are already in correct order (ascending or descending) before they are being treated by the readDateAndTime instruction.<br>
</li><li>offset, default value = "", allowed values are "", year", "month", "day", "halfday", "hour". Offset value has two purposes. 1 is in case the parsed date/time field was not complete, for example "11h30". If this would be parsed, the result would have been January 1st, 1970, 11h30. By defining offset value "day", the date and time fields will be corrected, to today at 11h30. Offset is also used when the "chronology" attribute is defined. For example assuming the result of parsing the timestamps would be "08:00", "07:07", "02:23", "23:23", "22:25". If we would use a simpledateformat parser, the result of the last two timestamps would be today at 23:23 and 22:25. However, assuming that the original source was a list of chronologically (descending = newest first) sorted values, it should be yesterday 23:23 and 22:25. So here we would define an offset="day" and chronology = "descending".<br>
(at the moment more details in the Javadoc , module STRING_PROCESSORreadDateAndTime.java)</li></ul>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<ul><li>mandatory child<br>
<blockquote>SimpleDateFormat<br>
</blockquote></li><li>optional child<br>
<blockquote>TimeZone</blockquote></li></ul>

<b>text field</b>
<blockquote>none</blockquote>

<b>examples</b>

This is the input stored in /Users/Johan/temp/temp.txt<br>
<br>
<pre><code>15 februari 2010 22:03<br>
28 oktober 2009 20:37<br>
5 april 2009 15:47<br>
27 december 2008 20:40<br>
19 april 2010 10:11<br>
17 april 2010 12:56<br>
12 april 2010 20:05<br>
9 april 2010 17:51<br>
6 april 2010 10:13<br>
27 maart 2010 22:18<br>
14 maart 2010 21:22<br>
1 maart 2010 11:48<br>
19 februari 2010 14:07<br>
14 februari 2010 23:12<br>
14 februari 2010 13:29<br>
13 februari 2010 18:46<br>
13 februari 2010 18:43<br>
31 januari 2010 16:54<br>
25 januari 2010 21:56<br>
17 januari 2010 21:43<br>
13 januari 2010 17:26<br>
6 januari 2010 21:35<br>
4 januari 2010 19:16<br>
6 december 2009 15:49<br>
</code></pre>

we apply following instructionlist, which has a String Processor called readDateAndTime with a SimpleDateFormat, with SimpleDateFormatPattern "d MMMM y HH:mm", and a DateFormatSymbols element with Months "januari", ..."December"<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
       &lt;url&gt;&lt;![CDATA[file://localhost/Users/Johan/temp/temp.txt]]&gt;&lt;/url&gt;<br>
      &lt;description&gt;<br>
         &lt;![CDATA[example]]&gt;<br>
      &lt;/description&gt;<br>
      &lt;instructionList&gt;<br>
         &lt;getUnFilteredContent&gt;<br>
         &lt;/getUnFilteredContent&gt;<br>
         &lt;processString&gt;<br>
        	&lt;readDateAndTime&gt;<br>
        		&lt;SimpleDateFormat&gt;<br>
    				&lt;SimpleDateFormatPattern&gt;d MMMM y HH:mm&lt;/SimpleDateFormatPattern&gt;<br>
    			  	&lt;DateFormatSymbols&gt;<br>
    			      &lt;Months&gt;<br>
    			      januari, <br>
    			      februari, <br>
    			      maart, <br>
    			      april, <br>
    			      mei, <br>
    			      juni, <br>
    			      juli, <br>
    			      augustus, <br>
    			      september, <br>
    			      oktober, <br>
    			      november, <br>
    			      december<br>
    			      &lt;/Months&gt;<br>
    			   	&lt;/DateFormatSymbols&gt;<br>
	       		&lt;/SimpleDateFormat&gt;<br>
        	&lt;/readDateAndTime&gt;<br>
         &lt;/processString&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

and the result is :<br>
<br>
<pre><code>Mon Feb 15 22:03:00 CET 2010<br>
Wed Oct 28 20:37:00 CET 2009<br>
Sun Apr 05 15:47:00 CEST 2009<br>
Sat Dec 27 20:40:00 CET 2008<br>
Mon Apr 19 10:11:00 CEST 2010<br>
Sat Apr 17 12:56:00 CEST 2010<br>
Mon Apr 12 20:05:00 CEST 2010<br>
Fri Apr 09 17:51:00 CEST 2010<br>
Tue Apr 06 10:13:00 CEST 2010<br>
Sat Mar 27 22:18:00 CET 2010<br>
Sun Mar 14 21:22:00 CET 2010<br>
Mon Mar 01 11:48:00 CET 2010<br>
Fri Feb 19 14:07:00 CET 2010<br>
Sun Feb 14 23:12:00 CET 2010<br>
Sun Feb 14 13:29:00 CET 2010<br>
Sat Feb 13 18:46:00 CET 2010<br>
Sat Feb 13 18:43:00 CET 2010<br>
Sun Jan 31 16:54:00 CET 2010<br>
Mon Jan 25 21:56:00 CET 2010<br>
Sun Jan 17 21:43:00 CET 2010<br>
Wed Jan 13 17:26:00 CET 2010<br>
Wed Jan 06 21:35:00 CET 2010<br>
Mon Jan 04 19:16:00 CET 2010<br>
Sun Dec 06 15:49:00 CET 2009<br>
</code></pre>

<h6>simpleDateFormat</h6>

see <a href='http://code.google.com/p/getcontents/wiki/SimpleDateFormat'>http://code.google.com/p/getcontents/wiki/SimpleDateFormat</a>

<h6>TimeZone</h6>

a TimeZone<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<ol><li></li></ol>

<b>Allowed Children</b>
<blockquote>none</blockquote>


<b>text field</b>
<blockquote>a timeZone ID</blockquote>

<h5>repeat</h5>

it is possible to repeat a number of stringProcessors, by adding the element "repeat" as child of the element processString.<br>
Element repeat extends processString and so can have a number of<br>
stringProcessor children , the whole list will be repeated.<br>
Typically useful for example to process a findAndReplace processor multiple times, to replace two carriage returns by one, resulting in a list of strings where there are no more double carriage returns.<br>
<br>
<b>Attributes</b>
<ul><li>attributes inherited from processString<br>
</li><li>repeat , minimum value 1, defines the number of repetitions</li></ul>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<ul><li>same as for processString</li></ul>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>

the following example gets text from a news site, at the end all double carriage returns are replaced by one carriage return, this is repeated 15 times.<br>
As a result, suppose the original text occurrences of 15 subsequent carriage returns, this will be replaced finally by just one carriage return.<br>
<br>
<pre><code>&lt;?xml version="1.0" encoding="UTF-8" ?&gt;<br>
&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="hln-fulltext"&gt;<br>
...<br>
      &lt;instructionList&gt;<br>
         &lt;htmlFilter recursive="true"  charset="UTF-8"&gt;<br>
...<br>
         &lt;/htmlFilter&gt;<br>
         &lt;htmlGetter charset="UTF-8"&gt;<br>
            &lt;text/&gt;<br>
         &lt;/htmlGetter&gt;<br>
         &lt;processString&gt;<br>
         	&lt;unescapeHTML/&gt;&lt;trim/&gt;<br>
         	&lt;repeat repeat="15"&gt;<br>
	         	&lt;findAndReplace&gt;<br>
         		   &lt;find&gt;<br>
<br>
&lt;/find&gt;<br>
					&lt;replace&gt;<br>
&lt;/replace&gt;<br>
				&lt;/findAndReplace&gt;<br>
			&lt;/repeat&gt;<br>
         &lt;/processString&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

<h5>removeInvisibleChars</h5>

Removes redundant line feeds, spaces, ...<br>
At most two consecusive line feeds are retained.<br>
<br>
<b>Attributes</b>
<ul><li>maxNewLines : optional, default value 2, defines the maximum number of consecutive linefeeds. If value = "0" then any newline will be replaced by " ". If value > 0, then if there's a sequence of more than "maxNewLines" newlines, then a replacement if the list of newlines will be done by just two newlines.</li></ul>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<ul><li>none</li></ul>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>


<h5>split</h5>

Split each string with String.split(delimiter)<br>


<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<ul><li>idSelector<br>
</li><li>string<br>
<blockquote>default value = " ", can be any String</blockquote></li></ul>


<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>

We start with the result of previous example (readDateAndTime) which is a lilst of dates :<br>
<br>
<pre><code>...<br>
Wed Oct 28 20:37:00 CET 2009<br>
Sun Apr 05 15:47:00 CEST 2009<br>
Sat Dec 27 20:40:00 CET 2008<br>
Mon Apr 19 10:11:00 CEST 2010<br>
...<br>
</code></pre>

we can add a split, on " "<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
...<br>
        	&lt;readDateAndTime&gt;<br>
        		&lt;SimpleDateFormat&gt;<br>
    				&lt;SimpleDateFormatPattern&gt;d MMMM y HH:mm&lt;/SimpleDateFormatPattern&gt;<br>
    			  	&lt;DateFormatSymbols&gt;<br>
    			      &lt;Months&gt;januari, februari, maart, april, mei, juni, juli, augustus, september, oktober, november,  december<br>
    			      &lt;/Months&gt;<br>
    			   	&lt;/DateFormatSymbols&gt;<br>
	       		&lt;/SimpleDateFormat&gt;<br>
        	&lt;/readDateAndTime&gt;<br>
        	&lt;split&gt;&lt;string&gt; &lt;/string&gt;&lt;/split&gt;<br>
<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

which results in<br>
<br>
<pre><code>...<br>
Mon<br>
Feb<br>
15<br>
22:03:00<br>
CET<br>
2010<br>
Wed<br>
Oct<br>
...<br>
</code></pre>

<h6>idSelector</h6>

to filter out results based on id.<br>
This works similarly to stringProcessor idSelector, but works on individual results of split.<br>
Example if splitting one string results in 5 new strings, the idSelector will work on those 5 strings.<br>
<br>
<b>Attributes</b>
<blockquote>include, possible values "true" or "false", default value "true"</blockquote>

<b>Occurrence</b>
<blockquote>0 or 1</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>the id's, see example</blockquote>

<b>example</b>

For more examples, see the stringProcessor instruction idSelector<br>
<br>
We will use an idSelector to get only the first result of each split instruction in the previous example :<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
...<br>
        	&lt;readDateAndTime&gt;<br>
        		&lt;SimpleDateFormat&gt;<br>
    				&lt;SimpleDateFormatPattern&gt;d MMMM y HH:mm&lt;/SimpleDateFormatPattern&gt;<br>
    			  	&lt;DateFormatSymbols&gt;<br>
    			      &lt;Months&gt;januari, februari, maart, april, mei, juni, juli, augustus, september, oktober, november,  december<br>
    			      &lt;/Months&gt;<br>
    			   	&lt;/DateFormatSymbols&gt;<br>
	       		&lt;/SimpleDateFormat&gt;<br>
        	&lt;/readDateAndTime&gt;<br>
        	&lt;split&gt;<br>
                    &lt;string&gt; &lt;/string&gt;<br>
                    &lt;idSelector include="true"&gt;<br>
                       1<br>
                    &lt;/idSelector&gt;<br>
                &lt;/split&gt;<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

resulting in<br>
<br>
<pre><code>Mon<br>
Wed<br>
Sun<br>
Sat<br>
Mon<br>
Sat<br>
Mon<br>
Fri<br>
Tue<br>
Sat<br>
Sun<br>
Mon<br>
Fri<br>
Sun<br>
Sun<br>
Sat<br>
Sat<br>
Sun<br>
Mon<br>
Sun<br>
Wed<br>
Wed<br>
Mon<br>
Sun<br>
</code></pre>

In another example we start with following input, 24 strings (being the result of another set of instructions not shown here):<br>
<br>
<pre><code>...<br>
Result 1, length = 38<br>
28 oktober 2009 20:37<br>
			door Steffie<br>
Result 2, length = 38<br>
5 april 2009 15:47<br>
			door clouseauke<br>
Result 3, length = 38<br>
27 december 2008 20:40<br>
			door Marina<br>
Result 4, length = 39<br>
19 april 2010 10:11<br>
			door Dennis1978<br>
Result 5, length = 39<br>
17 april 2010 12:56<br>
			door meurtelken<br>
Result 6, length = 38<br>
12 april 2010 20:05<br>
			door angelique<br>
...<br>
</code></pre>

now we apply the split instruction with a delimiter that will check on \r, \n or \r followed by \n, and an idSelector that will pick the first result of each split.<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
...<br>
        	&lt;split&gt;<br>
                        &lt;string&gt;&lt;![CDATA[\r\n|\r|\n]]&gt;&lt;/string&gt;<br>
	        	&lt;idSelector include="true"&gt;<br>
	        	   1<br>
	        	&lt;/idSelector&gt;<br>
        	&lt;/split&gt;<br>
...<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

resulting in<br>
<br>
<pre><code>...<br>
15 februari 2010 22:03<br>
28 oktober 2009 20:37<br>
5 april 2009 15:47<br>
27 december 2008 20:40<br>
19 april 2010 10:11<br>
17 april 2010 12:56<br>
12 april 2010 20:05<br>
9 april 2010 17:51<br>
6 april 2010 10:13<br>
27 maart 2010 22:18<br>
14 maart 2010 21:22<br>
1 maart 2010 11:48<br>
19 februari 2010 14:07<br>
14 februari 2010 23:12<br>
14 februari 2010 13:29<br>
13 februari 2010 18:46<br>
13 februari 2010 18:43<br>
31 januari 2010 16:54<br>
25 januari 2010 21:56<br>
17 januari 2010 21:43<br>
13 januari 2010 17:26<br>
6 januari 2010 21:35<br>
4 januari 2010 19:16<br>
6 december 2009 15:49<br>
...<br>
</code></pre>




<h5>startsWithAny</h5>

Filters on nodes that have text that starts with any of a list of strings<br>
Using com.Ostermiller.util, class StringHelper, method startsWithAnyIgnoreCase and startsWithAny<br>
<br>
see also the string processor "containsAny"<br>
<br>
<h5>trim</h5>

Trims each string with String.trim()<br>


<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>

there are already a few examples here.<br>
<br>
<br>
<h5>unescapeHTML</h5>

Trims each string with String.trim()<br>


<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<b>example</b>
<blockquote>Here a simple example with input "Fiscalité locale&nbsp;: la réformer pour plus d&#8217;équité"</blockquote>

Instructions :<br>
<br>
<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
       &lt;url&gt;&lt;![CDATA[file://localhost/Users/Johan/temp/temp.txt]]&gt;&lt;/url&gt;<br>
      &lt;description&gt;<br>
         &lt;![CDATA[example]]&gt;<br>
      &lt;/description&gt;<br>
      &lt;instructionList&gt;<br>
         &lt;getUnFilteredContent&gt;<br>
         &lt;/getUnFilteredContent&gt;<br>
         &lt;processString&gt;<br>
            &lt;unescapeHTML&gt;<br>
            &lt;/unescapeHTML&gt;<br>
         &lt;/processString&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;<br>
</code></pre>

Result<br>
<br>
Fiscalité locale : la réformer pour plus d’équité<br>
<br>
<h4>XMLFilter</h4>

An XMLFilter instruction can have an unlimited number of XML filters.<br>

The input to the first XMLFilter instruction must be valid XML.<br>
<br>
The output of an XMLFilter will be an array of strings, where each string is the XML representation of a node that matches the filters.<br>
This output can then be used again as input to either another instruction like another XMLFilter, XMLGetter or processString.<br>
<br>
<b>Attributes</b>
<ul><li>recursive<br>
<blockquote>Values : true or false.<br>
If a first level node does not match a filter, and if recursive is true, then the filter will continue searching in the second level node and so on.</blockquote></li></ul>

<ul><li>charsetname<br>
<blockquote>in case the source is a String, charsetname defines the charset to be used by String.getBytes</blockquote></li></ul>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>One ore more of following StringProcessor :<br>
</blockquote><ul><li>tagName<br>
</li><li>containsAny<br>
</li><li>endsWithAny<br>
</li><li>equalsAny<br>
</li><li>hasAttribute<br>
</li><li>startsWithAny</li></ul>

<b>text field</b>
<blockquote>none</blockquote>


<h5>tagName</h5>

filters elements with corresponding tagname<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>the tag to filter on</blockquote>

<b>example</b>


<h5>containsAny filter on elements with text that contain any of the supplied strings</h5>

expects as as input XML elements, takes the text in the XML element and verifies if it contains any of the strings.<br>
<br>
As example check out processString - containsAny<br>
<br>
<h5>endsWithAny filter on elements with text that end with any of the supplied strings</h5>

expects as as input XML elements, takes the text in the XML element and verifies if it ends with any of the strings.<br>
<br>
As example check out processString - containsAny<br>
<br>
<h5>equalsAny filter on elements with text that equals any of the supplied strings</h5>

expects as as input XML elements, takes the text in the XML element and verifies if it equals any of the strings.<br>
<br>
As example check out processString - containsAny<br>
<br>
<h5>hasAttribute</h5>

see HTML filter 'hasAttribute'<br>
Not fully tested.<br>

<h5>startsWithAny : filter on elements with text that start with any of the supplied strings</h5>

expects as as input XML elements, takes the text in the XML element and verifies if it starts with any of the strings.<br>
<br>
As example check out processString - containsAny<br>
<br>
<br>
<h4>XMLGetter</h4>

An XMLGetter instruction can have an unlimited number of XML getters.<br>

The output of an XMLGetter will be an array of strings. What each string represents depends on the type of XMLGetter. It may be the text retrieved from an XML ELement, or the XML representation of an XML ELement.<br>
This output can then be used as input to processString, and if the output represents XML elements, it can also be the input to a new XMLFilter or XMLGetter.<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>One ore more of following XMLGetter :<br>
</blockquote><ul><li>children<br>
</li><li>text<br>
</li><li>idSelector<br>
</li><li>removeNodes</li></ul>

<b>text field</b>
<blockquote>none</blockquote>

<h5>children : if the input is XML, get the children</h5>

if the input is XML elements, it gets the children of each root element in the input.<br>
if the input is strings, it returns null<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<h5>removeNodes</h5>

will remove all nodes matching the specified tag<br>
<br>
<b>Attributes</b>
<blockquote>recursive, if true will search till the end, otherwise false</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>the tagname</blockquote>

<b>example</b>

<pre><code>&lt;getContentItemList&gt;<br>
   &lt;getContentItem id="1"&gt;<br>
      &lt;url&gt;<br>
         ...<br>
      &lt;/url&gt;<br>
...<br>
      &lt;instructionList&gt;<br>
         &lt;XMLGetter&gt;<br>
            &lt;removeNodes recursive="true"&gt;script&lt;/removeNodes&gt;<br>
         &lt;/XMLGetter&gt;<br>
      &lt;/instructionList&gt;<br>
   &lt;/getContentItem&gt;<br>
&lt;/getContentItemList&gt;  <br>
</code></pre>



<h5>text : get the text inside the elements</h5>

if the input is XML elements, it gets the text of each root element in the input.<br>
if the input is strings, it returns the input<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>

<b>Occurrence</b>
<blockquote>not defined</blockquote>

<b>Allowed Children</b>
<blockquote>none</blockquote>

<b>text field</b>
<blockquote>none</blockquote>

<h5>idSelector : include or exclude specific elements</h5>

works as the other idSelectors (see for processString - idSelector)<br>
If the input are just strings, then the output will also be strings.<br>
If the input are strings with an XML representation of XML elements, then the output will also be XML.