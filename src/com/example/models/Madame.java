package com.example.models;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.os.StrictMode;
import android.util.Log;

public class Madame {
	public String title;
	public String image;
	public String link;

	public Madame() {
		this.title = "";
		this.image = "";
	}

	public Madame(String title, String image) {
		this.title = title;
		this.image = image;
	}

	public static List<Madame> readMamades(String feed) {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		List<Madame> madames = new ArrayList<Madame>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			Log.e("OUTOUT", "ERROR #0");
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = db.parse(feed);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			Log.e("OUTOUT", "ERROR #1");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("OUTOUT", "ERROR #2");
			e.printStackTrace();
		} catch (Exception e) {
			Log.e("OUTOUT", "ERROR #3");
			e.printStackTrace();
			// parse(new InputSource(url.openStream()));
		}
		Log.e("OUTOUT", "URL =>" + feed);
		doc.getDocumentElement().normalize();

		NodeList nodeList = doc.getElementsByTagName("item");
		Log.e("OUTPUT", "NUMBER: " + nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			// Item
			Node node = nodeList.item(i);
			NodeList subNodes = node.getChildNodes();
			Madame madame = new Madame();
			for (int j = 0; j < subNodes.getLength(); j++) {
				String name = subNodes.item(j).getNodeName();
				String value = subNodes.item(j).getTextContent();
				if (name.equals("title")) {
					madame.setTitle(value);
				} else if (name.equals("description")) {
					List<String> values = extractUrls(value);
					if(values.size() > 0){
						value = values.get(0);
					}
					//value = value.replaceFirst("<img src=\"", "");
					//value = value.replaceFirst("\"/><br/><br/>", "");
					madame.setImage(value);
				} else if (name.equals("link")) {
					madame.setLink(value);
				}
			}
			Log.v("OUTPUT", "MADAME: " + madame.toString());
			madames.add(madame);
		}

		return madames;
	}

	public String getTitle() {
		return title;
	}

	public String getImage() {
		return image;
	}

	public String getLink() {
		return link;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String toString() {
		return getTitle() + " - " + getImage() + " - " + getLink();
	}
	
	public static List<String> extractUrls(String input) {
        List<String> result = new ArrayList<String>();

        Pattern pattern = Pattern.compile(
            "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" + 
            "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" + 
            "|mil|biz|info|mobi|name|aero|jobs|museum" + 
            "|travel|[a-z]{2}))(:[\\d]{1,5})?" + 
            "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" + 
            "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + 
            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" + 
            "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + 
            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" + 
            "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }
}
