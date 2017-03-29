/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * This class handles all HTTP calls
 */

package com.ihsinformatics.childhoodtb_mobile.util;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.ihsinformatics.childhoodtb_mobile.App;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @author owais.hussain@irdinformatics.org
 * 
 */
public class HttpRequest {
	private static final String TAG = "HttpRequest";
	private final Context context;
	HttpClient httpClient = new DefaultHttpClient();

	public HttpRequest(Context context) {
		this.context = context;
	}

	/**
	 * Makes HTTP GET call to server and returns the response. The method
	 * automatically appends authentication header using App.getUsername() and
	 * App.getPassword() methods.
	 * 
	 * @param requestUri
	 *            fully qualified URI, e.g.
	 *            https://myserver:port/ws/rest/v1/concept
	 * @return
	 */
	public String clientGet(String requestUri) {
		HttpsClient client = new HttpsClient(context);
		HttpUriRequest request = null;
		String response = "";
		String auth = "";
		try {
			request = new HttpGet(requestUri);
			auth = Base64.encodeToString((App.getUsername() + ":" + App
					.getPassword()).getBytes("UTF-8"), Base64.NO_WRAP);
			request.addHeader("Authorization", auth);
			response = client.request(request);
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
		}
		return response;
	}

	/**
	 * Makes a POST call to the server and returns the attached Entity in a
	 * String
	 *
	 * @param postUri
	 * @param content
	 * @return
	 */
	public String clientPost(String postUri, String content) {
		HttpsClient client = new HttpsClient(context);
		HttpUriRequest request = null;
		HttpResponse response = null;
		HttpEntity entity;
		StringBuilder builder = new StringBuilder();
		String auth = "";
		try {
			/*
			 * Uncomment if you do not want to send data in Parameters HttpPost
			 * httpPost = new HttpPost (postUri); httpPost.setHeader ("Accept",
			 * "application/json"); httpPost.setHeader ("Content-Type",
			 * "application/json"); StringEntity stringEntity = new StringEntity
			 * (content); httpPost.setEntity (stringEntity); request = httpPost;
			 */
			auth = Base64.encodeToString((App.getUsername() + ":" + App
					.getPassword()).getBytes("UTF-8"), Base64.NO_WRAP);
			request = new HttpGet(postUri);
			request.addHeader("Authorization", auth);
			response = client.execute(request);
			entity = response.getEntity();
			InputStream is = entity.getContent();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(is));
			builder = new StringBuilder();
			String line = null;
			while ((line = bufferedReader.readLine()) != null)
				builder.append(line);
			entity.consumeContent();
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
			builder.append("UNSUPPORTED_ENCODING");
		} catch (ClientProtocolException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			builder.append("SERVER_NOT_RESPONDING");
		}
		return builder.toString();
	}
}
