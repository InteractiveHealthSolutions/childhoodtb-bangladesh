/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * This class handles all HTTPS (secure) calls
 */

package com.ihsinformatics.childhoodtb_mobile.util;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.ihsinformatics.childhoodtb_mobile.App;

import com.ihsinformatics.childhoodtb_mobile.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class HttpsClient extends DefaultHttpClient {
	private static final String TAG = "HttpsClient";
	private static final int HTTP_PORT = 80;
	private static final int HTTPS_PORT = 443;
	private final Context context;

	public HttpsClient(Context context) {
		this.context = context;
	}

	/**
	 * Makes HTTPS GET call to server and returns the response. The method
	 * automatically appends authentication header using App.getUsername() and
	 * App.getPassword() methods
	 * 
	 * @param requestUri
	 * @return
	 */
	public String request(String requestUri) {
		return request(new HttpGet(requestUri));
	}

	/**
	 * Overloaded method
	 * 
	 * @param request
	 * @return
	 */
	public String request(HttpUriRequest request) {
		String responseString = null;
		try {
			HttpsClient client = new HttpsClient(context);
			HttpResponse response = client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			Log.d(TAG, "Http response code: " + statusLine.getStatusCode());
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
				// Log.d (TAG, responseString);
			} else {
				response.getEntity().getContent().close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseString;
	}

	/**
	 * Makes HTTPS GET call to client via REST call and returns the response.
	 * The method automatically appends authentication header using
	 * App.getUsername() and App.getPassword() methods.
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

	@Override
	protected ClientConnectionManager createClientConnectionManager() {
		// Get an instance of the Bouncy Castle KeyStore format
		try {
			KeyStore trusted = KeyStore.getInstance("BKS");
			// Get the raw resource, containing keystore with your trusted
			// certificates (root & intermediate certs)
			/*
			 * This keystore was created using a utility called Keystore
			 * Explorer
			 */
			InputStream in = context.getResources().openRawResource(
					R.raw.ihskeystore);
			// Initialize the keystore with the provided trusted certificates
			// Also provide the password of the keystore
			trusted.load(
					in,
					context.getResources()
							.getString(R.string.trust_store_password)
							.toCharArray());
			// Pass the keystore to the SSLSocketFactory, which is responsible
			// for the server certificate verification
			SSLSocketFactory socketFactory = new SSLSocketFactory(trusted);
			// Hostname verification from certificate. Use
			// SSLSocketFactory.STRICT_HOSTNAME_VERIFIER for production
			socketFactory
					.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), HTTP_PORT));
			// Register for port 443 our SSLSocketFactory with our keystore to
			// the ConnectionManager
			registry.register(new Scheme("https", socketFactory, HTTPS_PORT));
			return new SingleClientConnManager(getParams(), registry);
		} catch (KeyStoreException e) {
			throw new AssertionError(e);
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		} catch (CertificateException e) {
			throw new AssertionError(e);
		} catch (KeyManagementException e) {
			throw new AssertionError(e);
		} catch (UnrecoverableKeyException e) {
			throw new AssertionError(e);
		} catch (IOException e) {
			throw new AssertionError(e);
		}
	}
}
