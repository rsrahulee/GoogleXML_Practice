package com.pack.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;

import com.pack.controller.GoogleXML_PracticeActivity;
import com.pack.datamodels.Entry;
import com.pack.networkConnection.Connect;
import com.pack.parser.Parser;

public class XmlTask extends AsyncTask<String, Void, ArrayList<Entry>> {
	
	GoogleXML_PracticeActivity context;
	
	public XmlTask(GoogleXML_PracticeActivity context) {
		this.context = context;
	}

	@Override
	protected ArrayList<Entry> doInBackground(String... xml_url) {
		ArrayList<Entry> out = new ArrayList<Entry>();
		try {
			InputStream inputStream = Connect.INSTANCE.getXmlFromUrl(xml_url[0]);
			out = Parser.INSTANCE.parse(inputStream);
		} catch (XmlPullParserException pullParserException) {

		} catch (IOException ioException) {

		} catch (Exception e) {

		}
		return out;
	};

	@Override
	protected void onPostExecute(ArrayList<Entry> result) {
		System.out.println("");
		context.getXmlOutput(result);
	}
}
