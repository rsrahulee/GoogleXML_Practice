package com.pack.controller;

import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.pack.datamodels.Entry;
import com.pack.datamodels.XmlID;
import com.pack.datamodels.XmlUrl;
import com.pack.networkConnection.Connect;
import com.pack.parser.Parser;

public class GoogleXML_PracticeActivity extends Activity {

//	private InputStream inputStream;	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		getParsingData();
	}
	
	public void getParsingData(){
		try {
			String xml_url = XmlUrl.getXmlUrl(XmlID.mainpage_xml);

			InputStream inputStream = Connect.INSTANCE.getXmlFromUrl(xml_url);

			List<Entry> out = Parser.INSTANCE.parse(inputStream);
			System.out.println("-------------" + out);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}