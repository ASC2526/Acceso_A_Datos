package com.asc2526.da.unit5.util;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLParser {

    public static SAXParser createParser() throws Exception {
        return SAXParserFactory.newInstance().newSAXParser();
    }

}
