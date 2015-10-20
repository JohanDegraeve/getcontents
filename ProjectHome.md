getContents is a package to fetch contents from html or xml pages.
The way the content needs to be retrieved can be stored in a separate XML file. This file defines instructions to be executed to fetch the content.

For example this can be useful to develop an application for mobile devices (eg Android) to fetch tv guide contents from an existing website. The XML instruction file will determine where and how to get the tv guide content. If the website changes it's layout, then only the XML  instruction file needs to be changed. The XML instruction file is stored on another http server, and the Android application can do a regular update of the instruction file and store it locally. This allows to cope with changes in the source website layout, without having to update the Android application.

The package can be used to fetch HTML and XML content, or a combination of both, for instance RSS feeds which typically have HTML content within XML.