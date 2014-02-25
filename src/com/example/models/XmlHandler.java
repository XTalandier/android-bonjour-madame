package com.example.models;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class XmlHandler extends DefaultHandler {
	private Madame feedStr = new Madame();
	private List<Madame> rssList = new ArrayList<Madame>();

	private int articlesAdded = 0;

	// Number of articles to download
	private static final int ARTICLES_LIMIT = 25;

	StringBuffer chars = new StringBuffer();

	public void startElement(String uri, String localName, String qName,
			Attributes atts) {
		chars = new StringBuffer();
		Log.v("startElement", qName);
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		Log.v("endElement", localName);

		if (localName.equalsIgnoreCase("item")) {
			rssList.add(feedStr);

			feedStr = new Madame();
			articlesAdded++;
			if (articlesAdded >= ARTICLES_LIMIT) {
				throw new SAXException();
			}
		}
	}

	public void characters(char ch[], int start, int length) {
		chars.append(new String(ch, start, length));
	}

	public List<Madame> getLatestArticles(String feedUrl) {
		Log.v("OUTPUT", "URL : " + feedUrl);
		URL url = null;
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			url = new URL(feedUrl);
			xr.setContentHandler(this);
			xr.parse(new InputSource(url.openStream()));
		} catch (IOException e) {
		} catch (SAXException e) {

		} catch (ParserConfigurationException e) {

		}

		return rssList;
	}

}