/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * 
 */

package com.ihsinformatics.childhoodtb_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.ihsinformatics.childhoodtb_mobile.R;


import java.util.Locale;

/**
 * @author Owais
 * 
 */
public class Preferences extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {
	public static final String CITY = "city";
	public static final String COUNTRY = "country";
	public static final String DELAY = "delay";
	public static final String LANGUAGE = "language";
	public static final String PASSWORD = "password";
	public static final String SERVER = "server";
	public static final String USE_SSL = "use_ssl";
	public static final String SUPPORT_CONTACT = "support_contact";
	public static final String USERNAME = "username";
	public static final String LOCATION = "location";
	public static final String AUTO_LOGIN = "auto_login";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent loginIntent = new Intent(getApplicationContext(),
				LoginActivity.class);
		startActivity(loginIntent);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// Use the code below if preferences are meant to be changed instantly
		// without having to re-open the application
		if (key.equals(CITY)) {
			App.setCity(sharedPreferences.getString(key, ""));
		} else if (key.equals(COUNTRY)) {
			App.setCountry(sharedPreferences.getString(key, ""));
		} else if (key.equals(DELAY)) {
			App.setDelay(sharedPreferences.getInt(key, 3000));
		} else if (key.equals(LANGUAGE)) {
			Locale locale = new Locale(sharedPreferences.getString(key, "en")
					.substring(0, 2));
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			getApplicationContext().getResources().updateConfiguration(config,
					null);
			App.setCurrentLocale(locale);
		} else if (key.equals(SERVER)) {
			App.setServer(sharedPreferences.getString(key, ""));
		} else if (key.equals(USE_SSL)) {
			App.setUseSsl(sharedPreferences.getBoolean(key, true));
		} else if (key.equals(SUPPORT_CONTACT)) {
			App.setSupportContact(sharedPreferences.getString(key, ""));
		} else if (key.equals(USERNAME)) {
			App.setUsername(sharedPreferences.getString(key, App.getUsername()));
		} else if (key.equals(PASSWORD)) {
			App.setPassword(sharedPreferences.getString(key, App.getPassword()));
		} else if (key.equals(LOCATION)) {
			App.setLocation(sharedPreferences.getString(key, App.getLocation()));
		} else if (key.equals(AUTO_LOGIN)) {
			App.setAutoLogin(sharedPreferences.getBoolean(key,
					App.isAutoLogin()));
		}
	}
}
