package com.pack.networkConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;

public class Connect {
	
	public static Connect INSTANCE = new Connect();

	InputStream inputStream;

	public InputStream getXmlFromUrl(String url) {
		try {
			URL xmlUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) xmlUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml");

			connection.connect();

			inputStream = connection.getInputStream();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
}
