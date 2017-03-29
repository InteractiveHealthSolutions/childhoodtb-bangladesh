/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * Search Activity to search for Patients using Patient ID and Personal Information 
 */

package com.ihsinformatics.childhoodtb_mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ihsinformatics.childhoodtb_mobile.custom.MyTextView;
import com.ihsinformatics.childhoodtb_mobile.shared.AlertType;
import com.ihsinformatics.childhoodtb_mobile.util.RegexUtil;
import com.ihsinformatics.childhoodtb_mobile.util.ServerService;
import com.ihsinformatics.childhoodtb_mobile.R;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class PatientSearchActivity extends Activity implements
		OnCheckedChangeListener, OnClickListener {
	public static final String TAG = "PatientSearchActivity";
	public static final String SEARCH_RESULT = "SEARCH_RESULT";
	private static final AtomicInteger counter = new AtomicInteger();
	public static ServerService serverService;
	public static ProgressDialog loading;

	TableLayout searchLayout;

	EditText patientId;
	Button scanBarcode;

	RadioGroup genderGroup;
	RadioButton male;
	RadioButton female;

	Spinner ageGroup;
	EditText firstName;
	EditText lastName;

	Button search;
	Button searchAgain;

	ScrollView searchResultsScrollView;
	LinearLayout searchResultsLayout;
	RadioGroup patientsRadioGroup;
	Animation alphaAnimation;

	protected void onCreate(Bundle savedInstanceState) {
		setTheme(App.getTheme());
		setContentView(R.layout.patient_search);
		super.onCreate(savedInstanceState);
		serverService = new ServerService(this);
		loading = new ProgressDialog(this);
		searchLayout = (TableLayout) findViewById(R.search_id.searchLayout);
		patientId = (EditText) findViewById(R.search_id.patientIdEditText);
		scanBarcode = (Button) findViewById(R.search_id.scanBarcodeButton);
		firstName = (EditText) findViewById(R.search_id.firstNameEditText);
		lastName = (EditText) findViewById(R.search_id.lastNameEditText);
		genderGroup = (RadioGroup) findViewById(R.search_id.genderRadioGroup);
		male = (RadioButton) findViewById(R.search_id.maleRadioButton);
		female = (RadioButton) findViewById(R.search_id.femaleRadioButton);
		ageGroup = (Spinner) findViewById(R.search_id.ageGroupSpinner);
		search = (Button) findViewById(R.search_id.searchButton);
		searchAgain = (Button) findViewById(R.search_id.searchAgainButton);
		searchResultsScrollView = (ScrollView) findViewById(R.search_id.resultsScrollView);
		searchResultsLayout = (LinearLayout) findViewById(R.search_id.searchResultsLayout);
		patientsRadioGroup = (RadioGroup) findViewById(R.search_id.patientsRadioGroup);
		alphaAnimation = AnimationUtils.loadAnimation(this,
				R.anim.alpha_animation);
		scanBarcode.setOnClickListener(this);
		patientsRadioGroup.setOnCheckedChangeListener(this);
		searchAgain.setOnClickListener(this);
		search.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		finish();
		// changed from login to MainMenu
		Intent mainMenuIntent = new Intent(getApplicationContext(),
				MainMenuActivity.class);
		startActivity(mainMenuIntent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Retrieve barcode scan results
		if (requestCode == Barcode.BARCODE_RESULT) {
			if (resultCode == RESULT_OK) {
				String str = data.getStringExtra(Barcode.SCAN_RESULT);
				// Check for valid Id
				if (RegexUtil.isValidId(str)
						&& !RegexUtil.isNumeric(str, false)) {
					patientId.setText(str);
				} else {
					App.getAlertDialog(
							this,
							AlertType.ERROR,
							patientId.getTag().toString()
									+ ": "
									+ getResources().getString(
											R.string.invalid_data)).show();
				}
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				App.getAlertDialog(this, AlertType.ERROR,
						getResources().getString(R.string.operation_cancelled))
						.show();
			}
			// Set the locale again, since the Barcode app restores system's
			// locale because of orientation
			Locale.setDefault(App.getCurrentLocale());
			Configuration config = new Configuration();
			config.locale = App.getCurrentLocale();
			getApplicationContext().getResources().updateConfiguration(config,
					null);
		}
	};

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		if (radioGroup == genderGroup) {
			RadioButton radio = (RadioButton) findViewById(radioGroup
					.getCheckedRadioButtonId());
			if (radio.isChecked()) {
				String patientId = radio.getTag().toString();
				Intent intent = new Intent();
				intent.putExtra(PatientSearchActivity.SEARCH_RESULT, patientId);
				setResult(RESULT_OK, intent);
			}
			finish();
		}
	}

	@Override
	public void onClick(View view) {
		if (view == scanBarcode) {
			Intent intent = new Intent(Barcode.BARCODE_INTENT);
			intent.putExtra(Barcode.SCAN_MODE, Barcode.QR_MODE);
			startActivityForResult(intent, Barcode.BARCODE_RESULT);
		} else if (view == searchAgain) {
			view.startAnimation(alphaAnimation);
			searchLayout.setVisibility(View.VISIBLE);
			searchResultsScrollView.setVisibility(View.GONE);
		} else if (view == search) {
			final String id = App.get(patientId);
			// If searching by Id, then get details
			if (!"".equals(id)) {
				AsyncTask<String, String, Object> searchTask = new AsyncTask<String, String, Object>() {
					protected Object doInBackground(String... params) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								loading.setIndeterminate(true);
								loading.setCancelable(false);
								loading.setMessage(getResources().getString(
										R.string.loading_message));
								loading.show();
							}
						});
						String[][] patientDetail = serverService
								.getPatientDetail(id);
						return patientDetail;
					}

					protected void onPostExecute(Object result) {
						super.onPostExecute(result);
						loading.dismiss();
						try {
							String[][] patientDetails = (String[][]) result;
							// Display a message if no results were found
							if (patientDetails == null) {
								App.getAlertDialog(
										PatientSearchActivity.this,
										AlertType.INFO,
										getResources().getString(
												R.string.patients_not_found))
										.show();
							} else {
								patientsRadioGroup.removeAllViews();
								for (String[] pair : patientDetails) {
									MyTextView textView = new MyTextView(
											PatientSearchActivity.this,
											R.style.text, R.string.empty_string);
									textView.setId(counter.getAndIncrement());
									if (pair == null) {
										continue;
									}
									textView.setText(pair[0] + ": " + pair[1]);
									textView.setTag(id);
									patientsRadioGroup.addView(textView);
								}
								searchLayout.setVisibility(View.GONE);
								searchResultsScrollView
										.setVisibility(View.VISIBLE);
							}
						} catch (Exception e) {
							Log.e(TAG, e.getMessage());
							App.getAlertDialog(
									PatientSearchActivity.this,
									AlertType.ERROR,
									getResources().getString(
											R.string.parsing_error)).show();
						}
					}
				};
				searchTask.execute("");
			}
			// Otherwise, search for Ids
			else {
				final String gender = male.isChecked() ? "M" : "F";
				final String[] ages = App.get(ageGroup).replaceAll(" ", "")
						.split("-");
				final int ageStart = Integer.parseInt(ages[0]);
				final int ageEnd = Integer.parseInt(ages[1]);
				final String first = App.get(firstName);
				final String last = App.get(lastName);
				// Check if the names are provided
				if ("".equals(first) && "".equals(last)) {
					App.getAlertDialog(PatientSearchActivity.this,
							AlertType.ERROR,
							getResources().getString(R.string.empty_data))
							.show();
					return;
				}
				AsyncTask<String, String, Object> searchTask = new AsyncTask<String, String, Object>() {
					protected Object doInBackground(String... params) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								loading.setIndeterminate(true);
								loading.setCancelable(false);
								loading.setMessage(getResources().getString(
										R.string.loading_message));
								loading.show();
							}
						});
						HashMap<String, String[]> patientData = null;
						try {
							patientData = serverService.searchPatients(id,
									first, last, gender, ageStart, ageEnd);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						return patientData;
					}

					protected void onPostExecute(Object result) {
						super.onPostExecute(result);
						loading.dismiss();
						@SuppressWarnings("unchecked")
						HashMap<String, String[]> patientList = ((HashMap<String, String[]>) result);
						// Display a message if no results were found
						if (patientList.isEmpty()) {
							App.getAlertDialog(
									PatientSearchActivity.this,
									AlertType.INFO,
									getResources().getString(
											R.string.patients_not_found))
									.show();
						} else {
							patientsRadioGroup.removeAllViews();
							Set<String> patientIdSet = patientList.keySet();
							for (String id : patientIdSet) {
								String[] attributes = patientList.get(id);
								StringBuilder concatenatedAttributes = new StringBuilder();
								for (String s : attributes) {
									concatenatedAttributes.append(s + " ");
								}
								MyTextView textView = new MyTextView(
										PatientSearchActivity.this,
										R.style.text, R.string.empty_string);
								textView.setId(counter.getAndIncrement());
								textView.setText(concatenatedAttributes);
								textView.setTag(id);
								// Copy Patient Id to clipboard on Tap
								textView.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View view) {
										TextView tv = (TextView) view;
										ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
										clipboard.setText(tv.getTag()
												.toString());
										AlertDialog alertDialog = App
												.getAlertDialog(
														PatientSearchActivity.this,
														AlertType.INFO,
														getApplicationContext()
																.getString(
																		R.string.text_copied));
										alertDialog.show();
										finish();
									}
								});
								patientsRadioGroup.addView(textView);
							}
							searchLayout.setVisibility(View.GONE);
							searchResultsScrollView.setVisibility(View.VISIBLE);
						}
					}
				};
				searchTask.execute("");
			}
		}
	}
}
