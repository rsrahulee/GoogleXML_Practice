package com.pack.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.pack.datamodels.Entry;
import com.pack.datamodels.XmlTags;

public class Parser extends XmlTags {

	private static final String ns = null;
	public static Parser INSTANCE = new Parser();

	public List<Entry> parse(InputStream in) throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readRss(parser);
		} finally {
			in.close();
		}
	}

	private List<Entry> readRss(XmlPullParser parser) throws XmlPullParserException, IOException {
		List<Entry> entries = new ArrayList<Entry>();

		parser.require(XmlPullParser.START_TAG, ns, RSS);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals(CHANNEL)) {
				entries = readXmlTag(parser);
			}
		}
		return entries;
	}

	private List<Entry> readXmlTag(XmlPullParser parser) throws XmlPullParserException, IOException {

		List<Entry> entries = new ArrayList<Entry>();
		parser.require(XmlPullParser.START_TAG, ns, CHANNEL);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String nameItem = parser.getName();
			if (nameItem.equals(ITEM)) {
				entries.add(readEntry(parser));
			} else {
				skip(parser);
			}
		}
		return entries;
	}

	private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, ITEM);
		String title = null;
		String description = null;
		String pub_date = null;
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals(TITLE)) {
				title = readTitle(parser);
			} else if (name.equals(DESCRIPTION)) {
				String strSummary = readDescription(parser);
				description = strSummary.replaceAll("<(.|\n)*?>", "");
			} else if (name.equals(PUB_DATE)) {
				pub_date = readPubDate(parser);
			} else {
				skip(parser);
			}
		}
		return new Entry(title, description, pub_date);
	}

	private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, TITLE);
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, TITLE);
		return title;
	}

	private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, DESCRIPTION);
		String description = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, DESCRIPTION);
		return description;
	}

	private String readPubDate(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, PUB_DATE);
		String pub_date = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, PUB_DATE);
		return pub_date;
	}

	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
}
