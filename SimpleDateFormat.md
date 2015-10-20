<font color='red'>THIS PAGE IS UNDER CONSTRUCTION</font>



# Introduction #

A SimpleDateFormat element can be created right as there exist Java constructors for the corresponding class.

# SimpleDateFormat #

a SimpleDateFormat

**Attributes**
> none

**Occurrence**
  1. 

**Allowed Children**
  * allowed combinations of children :
    * no children : a default constructor of SimpleDateFormat will be used
    * 1 SimpleDateFormatPattern : the constructor SimpleDateFormat(String pattern) will be used
    * 1 SimpleDateFormatPattern & 1 DateFormatSymbols : the constructor SimpleDateFormat(String pattern, DateFormatSymbols formatSymbols) will be used
    * 1 SimpleDateFormatPattern & 1 Locale : the constructor  SimpleDateFormat(String pattern, Locale locale) will be used

**text field**
none

## SimpleDateFormatPattern ##

a SimpleDateFormatPattern

**Attributes**
> none

**Occurrence**
  1. 

**text**
untrimmed, the date format pattern

## DateFormatSymbols ##

DateFormatSymbols

**Attributes**
> none

**Occurrence**
  1. 

**Children**
  * Locale
  * AmPmStrings
  * Eras ?
  * Months : a list of twelve strings, seperated by comma
  * ShortMonths : a list of twelve strings, seperated by comma
  * WeekDays : a list of seven, seperated by comma
  * ShortWeekays : a list of seven strings, seperated by comma

**text**
> none

### Locale ###

Locale

**Attributes**
  * language
  * country
  * variant
Allowed combinations of language, country and variant follow the three constructors that exist for {@link java.util.Locale}, in addition it is possible to create a locale without specifying any attribute.<br>
if language attribute present then country attribute is allowed<br>
if language and country attribute present, then variant is allowed<br>
if language not present then all get default values<br>
if language present but not country and variant then country and variant get default values<br>
if language and country present, but not variant, then variant gets default value<br></li></ul>


<b>Occurrence</b>
<ol><li></li></ol>

<b>Children</b>
<blockquote>none</blockquote>

<b>text</b>
<blockquote>none</blockquote>

<h3>AmPmStrings</h3>

the ampm Strings<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>


<b>Occurrence</b>
<ol><li></li></ol>

<b>Children</b>
<blockquote>none</blockquote>

<b>text</b>
<blockquote>a list of two strings, seperated by comma</blockquote>

<h3>Eras</h3>

the Eras Strings<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>


<b>Occurrence</b>
<ol><li></li></ol>

<b>Children</b>
<blockquote>none</blockquote>

<b>text</b>
<blockquote>a list of strings, seperated by comma</blockquote>


<h3>Months</h3>

the Months Strings<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>


<b>Occurrence</b>
<ol><li></li></ol>

<b>Children</b>
<blockquote>none</blockquote>

<b>text</b>
<blockquote>a list of 12 strings, seperated by comma</blockquote>


<h3>ShortMonths</h3>

the ShortMonths Strings<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>


<b>Occurrence</b>
<ol><li></li></ol>

<b>Children</b>
<blockquote>none</blockquote>

<b>text</b>
<blockquote>a list of 12 strings, seperated by comma</blockquote>

<h3>WeekDays</h3>

the WeekDays Strings<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>


<b>Occurrence</b>
<ol><li></li></ol>

<b>Children</b>
<blockquote>none</blockquote>

<b>text</b>
<blockquote>a list of 7 strings, seperated by comma</blockquote>

<h3>ShortWeekDays</h3>

the ShortWeekDays Strings<br>
<br>
<b>Attributes</b>
<blockquote>none</blockquote>


<b>Occurrence</b>
<ol><li></li></ol>

<b>Children</b>
<blockquote>none</blockquote>

<b>text</b>
<blockquote>a list of 7 strings, seperated by comma</blockquote>


<h2>Locale</h2>

Locale<br>
<br>
<b>Attributes</b>
<ul><li>language<br>
</li><li>country<br>
</li><li>variant<br>
Allowed combinations of language, country and variant follow the three constructors that exist for {@link java.util.Locale}, in addition it is possible to create a locale without specifying any attribute.<br>
if language attribute present then country attribute is allowed<br>
if language and country attribute present, then variant is allowed<br>
if language not present then all get default values<br>
if language present but not country and variant then country and variant get default values<br>
if language and country present, but not variant, then variant gets default value<br></li></ul>


<b>Occurrence</b>
<ol><li></li></ol>

<b>Children</b>
<blockquote>none</blockquote>

<b>text</b>
<blockquote>none</blockquote>
