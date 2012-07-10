package com.pack.controller;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.pack.datamodels.Entry;
import com.pack.datamodels.XmlID;
import com.pack.datamodels.XmlUrl;
import com.pack.task.XmlTask;

public class GoogleXML_PracticeActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		getParsingData();
	}

	public void getParsingData() {
		String xml_url = XmlUrl.getXmlUrl(XmlID.mainpage_xml);
		new XmlTask(this).execute(xml_url);

		// InputStream inputStream = Connect.INSTANCE.getXmlFromUrl(xml_url);
		// ArrayList<Entry> out = Parser.INSTANCE.parse(inputStream);
		System.out.println("");
	}

	public void getXmlOutput(ArrayList<Entry> result) {
		System.out.println("------------" + result);

		ArrayList<Entry> output = result;
		System.out.println("------------" + output);
	}
}