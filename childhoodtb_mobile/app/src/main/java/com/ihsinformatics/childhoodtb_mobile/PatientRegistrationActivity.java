/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
package com.ihsinformatics.childhoodtb_mobile;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.ihsinformatics.childhoodtb_mobile.custom.MyButton;
import com.ihsinformatics.childhoodtb_mobile.custom.MyEditText;
import com.ihsinformatics.childhoodtb_mobile.custom.MySpinner;
import com.ihsinformatics.childhoodtb_mobile.custom.MyTextView;
import com.ihsinformatics.childhoodtb_mobile.shared.AlertType;
import com.ihsinformatics.childhoodtb_mobile.shared.FormType;
import com.ihsinformatics.childhoodtb_mobile.util.RegexUtil;
import com.ihsinformatics.childhoodtb_mobile.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class PatientRegistrationActivity extends AbstractFragmentActivity
		implements OnEditorActionListener, OnFocusChangeListener {

	MyTextView formDateTextView;
	MyButton formDateButton;

	MyTextView patientIdTextView;
	MyEditText patientId;

	MyTextView suspectFatherHusbandTextView;
	MyEditText suspectFatherHusband;

	MyTextView suspectFatherInLawTextView;
	MyEditText suspectFatherInLaw;

	MyTextView ethnicityTextView;
	MySpinner ethnicity;

	MyTextView maritalStatusTextView;
	MySpinner maritalStatus;

	MyTextView phone1TextView;
	MyEditText phone1;

	MyTextView phone2TextView;
	MyEditText phone2;

	MyTextView addressTextView;
	MyEditText address;

	// MyTextView houseNumberTextView;
	// MyEditText houseNumber;
	//
	// MyTextView streetNameTextView;
	// MyEditText streetName;
	//
	// MyTextView colonyNameTextView;
	// MyEditText colonyName;
	//
	// MyTextView townTextView;
	// MyEditText town;

	MyTextView cityTextView;
	MyEditText city;

	MyTextView landmarkTextView;
	MyEditText landmark;

	MyButton validatePatientID;

	MyButton scanBarcode;

	String patientID;

	// patientID as a passed variable

	/**
	 * Subclass representing Fragment for adult-screeening suspect form
	 *
	 * @author owais.hussain@ihsinformatics.com
	 *
	 */
	@SuppressLint("ValidFragment")
	class AdultScreeningSuspectFragment extends Fragment {
		int currentPage;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Bundle data = getArguments();
			currentPage = data.getInt("current_page", 0);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Return a layout of views from pre-filled ArrayList of groups
			if (currentPage != 0 && groups.size() != 0)
				return groups.get(currentPage - 1);
			else
				return null;
		}
	}

	/**
	 * Subclass for Pager Adapter. Uses AdultScreeningSuspect subclass as items
	 *
	 * @author owais.hussain@irdresearch.org
	 *
	 */
	class AdultScreeningSuspectFragmentPagerAdapter extends
			FragmentPagerAdapter {
		/** Constructor of the class */
		public AdultScreeningSuspectFragmentPagerAdapter(
				FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem(int arg0) {
			AdultScreeningSuspectFragment fragment = new AdultScreeningSuspectFragment();
			Bundle data = new Bundle();
			data.putInt("current_page", arg0 + 1);
			fragment.setArguments(data);
			return fragment;
		}

		/** Returns the number of pages */
		@Override
		public int getCount() {
			return PAGE_COUNT;
		}
	}

	@Override
	public void createViews(Context context) {
		TAG = "PaediatricScreeningActivity";
		PAGE_COUNT = 4;
		pager = (ViewPager) findViewById(R.template_id.pager);
		navigationSeekbar.setMax(PAGE_COUNT - 1);
		navigatorLayout = (LinearLayout) findViewById(R.template_id.navigatorLayout);
		// If the form consists only of single page, then hide the
		// navigatorLayout
		if (PAGE_COUNT < 2) {
			navigatorLayout.setVisibility(View.GONE);
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		AdultScreeningSuspectFragmentPagerAdapter pagerAdapter = new AdultScreeningSuspectFragmentPagerAdapter(
				fragmentManager);
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit(PAGE_COUNT);
		// Create views for pages
		formDateTextView = new MyTextView(context, R.style.text,
				R.string.form_date);
		formDateButton = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_date,
				R.string.form_date);

		patientIdTextView = new MyTextView(context, R.style.text,
				R.string.patient_id);
		patientId = new MyEditText(context, R.string.patient_id,
				R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, RegexUtil.idLength, false);

		// validatePatientID = new MyButton(context, R.style.button,
		// R.drawable.custom_button_beige, R.string.validateID,
		// R.string.validateID);

		suspectFatherHusbandTextView = new MyTextView(context, R.style.text,
				R.string.father_husband_name);
		suspectFatherHusband = new MyEditText(context,
				R.string.father_husband_name,
				R.string.father_husband_name_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, 20, false);
		suspectFatherInLawTextView = new MyTextView(context, R.style.text,
				R.string.father_in_law_name);
		suspectFatherInLaw = new MyEditText(context,
				R.string.father_in_law_name, R.string.father_in_law_name_hint,
				InputType.TYPE_CLASS_TEXT, R.style.edit, 20, false);
		ethnicityTextView = new MyTextView(context, R.style.text,
				R.string.ethnicity);
		ethnicity = new MySpinner(context, getResources().getStringArray(
				R.array.ethnicity_options), R.string.ethnicity,
				R.string.option_hint);
		maritalStatusTextView = new MyTextView(context, R.style.text,
				R.string.marital_status);
		maritalStatus = new MySpinner(context, getResources().getStringArray(
				R.array.marital_options), R.string.marital_status,
				R.string.option_hint);
		phone1TextView = new MyTextView(context, R.style.text, R.string.phone1);
		phone1 = new MyEditText(context, R.string.phone1, R.string.phone1_hint,
				InputType.TYPE_CLASS_TEXT, R.style.edit, 12, false);
		phone2TextView = new MyTextView(context, R.style.text, R.string.phone2);
		phone2 = new MyEditText(context, R.string.phone2, R.string.phone2_hint,
				InputType.TYPE_CLASS_TEXT, R.style.edit, 12, false);
		addressTextView = new MyTextView(context, R.style.text,
				R.string.address);
		address = new MyEditText(context, R.string.address,
				R.string.address_hint, InputType.TYPE_CLASS_TEXT, R.style.edit,
				100, false);
		// houseNumberTextView = new MyTextView(context, R.style.text,
		// R.string.house_number);
		// houseNumber = new MyEditText(context, R.string.house_number,
		// R.string.house_number_hint, InputType.TYPE_CLASS_TEXT, R.style.edit,
		// 5, false);
		// streetNameTextView = new MyTextView(context, R.style.text,
		// R.string.street_name);
		// streetName = new MyEditText(context, R.string.street_name,
		// R.string.street_name_hint, InputType.TYPE_CLASS_TEXT, R.style.edit,
		// 15, false);
		// colonyNameTextView = new MyTextView(context, R.style.text,
		// R.string.colony_name);
		// colonyName = new MyEditText(context, R.string.colony_name,
		// R.string.colony_name_hint, InputType.TYPE_CLASS_TEXT, R.style.edit,
		// 15, false);
		// townTextView = new MyTextView(context, R.style.text, R.string.town);
		// town = new MyEditText(context, R.string.town, R.string.town_hint,
		// InputType.TYPE_CLASS_TEXT, R.style.edit, 15, false);
		cityTextView = new MyTextView(context, R.style.text, R.string.city);
		city = new MyEditText(context, R.string.city, R.string.city_hint,
				InputType.TYPE_CLASS_TEXT, R.style.edit, 15, false);
		landmarkTextView = new MyTextView(context, R.style.text,
				R.string.landmark);
		landmark = new MyEditText(context, R.string.landmark,
				R.string.landmark_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, 20, false);
		scanBarcode = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.scan_barcode,
				R.string.scan_barcode);

		View[][] viewGroups = {
				{ formDateTextView, formDateButton, patientIdTextView,
						patientId, scanBarcode },
				{ suspectFatherHusbandTextView, suspectFatherHusband,
						suspectFatherInLawTextView, suspectFatherInLaw },
				{ ethnicityTextView, ethnicity, maritalStatusTextView,
						maritalStatus, phone1TextView, phone1, phone2TextView,
						phone2 },
				{ addressTextView, address, cityTextView, city,
						landmarkTextView, landmark } };

		// Create layouts and store in ArrayList
		groups = new ArrayList<ViewGroup>();
		for (int i = 0; i < PAGE_COUNT; i++) {
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.VERTICAL);
			for (int j = 0; j < viewGroups[i].length; j++) {
				layout.addView(viewGroups[i][j]);
			}
			ScrollView scrollView = new ScrollView(context);
			scrollView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			scrollView.addView(layout);
			groups.add(scrollView);
		}

		// Set event listeners
		// validatePatientID.setOnClickListener(this);
		formDateButton.setOnClickListener(this);
		firstButton.setOnClickListener(this);
		lastButton.setOnClickListener(this);
		clearButton.setOnClickListener(this);
		scanBarcode.setOnClickListener(this);
		saveButton.setOnClickListener(this);
		navigationSeekbar.setOnSeekBarChangeListener(this);
		landmark.setOnFocusChangeListener(this);
		views = new View[] { patientId, suspectFatherHusband,
				suspectFatherInLaw, ethnicity, maritalStatus, phone1, phone2,
				address, city, landmark };
		for (View v : views) {
			if (v instanceof Spinner) {
				((Spinner) v).setOnItemSelectedListener(this);
			} else if (v instanceof CheckBox) {
				((CheckBox) v).setOnCheckedChangeListener(this);
			}
		}
		pager.setOnPageChangeListener(this);

		// Detect RTL language
		if (App.isLanguageRTL()) {
			Collections.reverse(groups);
			for (ViewGroup g : groups) {
				LinearLayout linearLayout = (LinearLayout) g.getChildAt(0);
				linearLayout.setGravity(Gravity.RIGHT);
			}
			for (View v : views) {
				if (v instanceof EditText) {
					((EditText) v).setGravity(Gravity.RIGHT);
				}
			}
		}
	}

	@Override
	public void initView(View[] views) {
		super.initView(views);
		formDate = Calendar.getInstance();
		updateDisplay();
		// address.setEnabled(false);
	}

	@Override
	public void onClick(View view) {
		if (view == formDateButton) {
			showDialog(DATE_DIALOG_ID);
		}

		// else if(view == validatePatientID)
		// {
		// patientId.setTextColor (getResources ().getColor
		// (R.color.Chocolate));
		// final String id = App.get(patientId);
		// if(validatePatientId())
		// {
		// if(!"".equals(id))
		// {
		// AsyncTask<String, String, Object> searchTask = new AsyncTask<String,
		// String, Object> ()
		// {
		//
		// @Override
		// protected Object doInBackground(String... params)
		// {
		// runOnUiThread(new Runnable()
		// {
		//
		// @Override
		// public void run()
		// {
		// loading.setIndeterminate(true);
		// loading.setCancelable(false);
		// loading.setMessage(getResources().getString(R.string.loading_message));
		// loading.show();
		// }
		// });
		// String[][] personDetail = serverService.getPersonDetail(id);
		// return personDetail;
		// }
		//
		// @Override
		// protected void onPostExecute(Object result)
		// {
		// super.onPostExecute(result);
		// loading.dismiss();
		//
		// try
		// {
		// String[][] personDetails = (String[][]) result;
		// // Display a message if no details were found
		// if(personDetails == null)
		// {
		// App.getAlertDialog(PatientRegistrationActivity.this, AlertType.INFO,
		// getResources().getString(R.string.patients_not_found)).show();
		// }
		// else
		// {
		// String message = "Patient ID is valid.";
		// App.getAlertDialog(PatientRegistrationActivity.this, AlertType.INFO,
		// message).show();
		// }
		// }
		// catch (Exception e)
		// {
		// Log.e (TAG, e.getMessage ());
		// App.getAlertDialog (PatientRegistrationActivity.this,
		// AlertType.ERROR, getResources ().getString
		// (R.string.parsing_error)).show ();
		// }
		// }
		// };
		// searchTask.execute("");
		// }
		// }
		// }

		else if (view == firstButton) {
			gotoFirstPage();
		} else if (view == lastButton) {
			gotoLastPage();
		} else if (view == clearButton) {
			initView(views);
		} else if (view == saveButton) {
			submit();
		} else if (view == scanBarcode) {
			Intent intent = new Intent(Barcode.BARCODE_INTENT);
			intent.putExtra(Barcode.SCAN_MODE, Barcode.QR_MODE);
			startActivityForResult(intent, Barcode.BARCODE_RESULT);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		MySpinner spinner = (MySpinner) parent;
		boolean visible = spinner.getSelectedItemPosition() == 1;
		updateDisplay();
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateDisplay() {
		formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));
	}

	@Override
	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		View[] mandatory = { suspectFatherHusband, suspectFatherInLaw, phone1,
				address, city, landmark };
		for (View v : mandatory) {
			if (App.get(v).equals("")) {
				valid = false;
				message.append(v.getTag().toString() + ". ");
				((EditText) v).setHintTextColor(getResources().getColor(
						R.color.Red));
			}
		}
		if (!valid) {
			message.append(getResources().getString(R.string.empty_data) + "\n");
		}
		// Validate data
		if (valid) {
			if (!RegexUtil.isWord(App.get(suspectFatherHusband))) {
				valid = false;
				message.append(suspectFatherHusband.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				suspectFatherHusband.setTextColor(getResources().getColor(
						R.color.Red));
			}

			if (!RegexUtil.isWord(App.get(suspectFatherInLaw))) {
				valid = false;
				message.append(suspectFatherInLaw.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				suspectFatherInLaw.setTextColor(getResources().getColor(
						R.color.Red));
			}
			if (!RegexUtil.isWord(App.get(city))) {
				valid = false;
				message.append(city.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				city.setTextColor(getResources().getColor(R.color.Red));
			}

			if (!App.get(phone1).equals("")) {
				if (!RegexUtil.isContactNumber(App.get(phone1))) {
					valid = false;
					message.append(phone1.getTag().toString() + ": "
							+ getResources().getString(R.string.invalid_data)
							+ "\n");
					phone1.setTextColor(getResources().getColor(R.color.Red));
				}
			}
			if (!App.get(phone2).equals("")) {
				if (!RegexUtil.isContactNumber(App.get(phone2))) {
					valid = false;
					message.append(phone2.getTag().toString() + ": "
							+ getResources().getString(R.string.invalid_data)
							+ "\n");
					phone2.setTextColor(getResources().getColor(R.color.Red));
				}
			}

			if ("".equals(App.get(patientId))) {
				message.append(patientId.getTag().toString() + ". ");
				patientId
						.setHintTextColor(getResources().getColor(R.color.Red));
				message.append(getResources().getString(R.string.empty_data)
						+ "\n");
				valid = false;
			} else {
				if (RegexUtil.matchId(App.get(patientId))) {
					if (!RegexUtil.isValidId(App.get(patientId))) {
						valid = false;
						message.append(patientId.getTag().toString()
								+ ": "
								+ getResources().getString(
										R.string.invalid_data) + "\n");
						patientId.setTextColor(getResources().getColor(
								R.color.Red));
					}
				} else {
					valid = false;
					message.append(patientId.getTag().toString() + ": "
							+ getResources().getString(R.string.invalid_data)
							+ "\n");
					patientId
							.setTextColor(getResources().getColor(R.color.Red));
				}
			}

		}
		// Validate ranges
		try {
			// Form date range
			if (formDate.getTime().after(Calendar.getInstance().getTime())) {
				valid = false;
				message.append(formDateButton.getTag()
						+ ": "
						+ getResources().getString(
								R.string.invalid_date_or_time) + "\n");
			}

			// Age range
		} catch (NumberFormatException e) {
		}
		if (!valid) {
			App.getAlertDialog(this, AlertType.ERROR, message.toString())
					.show();
		}
		return valid;
	}

	@Override
	public boolean submit() {
		if (validate()) {
			final ContentValues values = new ContentValues();
			values.put("patientId", App.get(patientId));
			values.put("formDate", App.getSqlDate(formDate));
			values.put("location", App.getLocation());
			values.put("suspectFatherHusbandName",
					App.get(suspectFatherHusband));
			values.put("suspectGrandfatherName", App.get(suspectFatherInLaw));
			values.put("ethnicity", App.get(ethnicity));
			values.put("maritalStatus", App.get(maritalStatus));
			values.put("primaryPhone", App.get(phone1));
			values.put("secondaryPhone", App.get(phone2));
			values.put("address", App.get(address));
			values.put("city", App.get(city));
			values.put("landmark", App.get(landmark));

			// final ArrayList<String[]> observations = new ArrayList<String[]>
			// ();

			AsyncTask<String, String, String> updateTask = new AsyncTask<String, String, String>() {
				@Override
				protected String doInBackground(String... params) {
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

					String result = "";
					result = serverService.savePatientRegistration(
							FormType.PATIENT_REGISTRATION, values);

					return result;
				}

				@Override
				protected void onProgressUpdate(String... values) {
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					loading.dismiss();
					if (result.equals("SUCCESS")) {
						App.getAlertDialog(PatientRegistrationActivity.this,
								AlertType.INFO,
								getResources().getString(R.string.inserted))
								.show();
						initView(views);
					} else {
						App.getAlertDialog(PatientRegistrationActivity.this,
								AlertType.ERROR, result).show();
						patientId.setTextColor(getResources().getColor(
								R.color.Red));
					}
				}
			};
			updateTask.execute("");
		}
		return true;
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
	}

	@Override
	public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		if (view == landmark) {
			if (!hasFocus) {
				if (!landmark.getText().toString().equals("")) {
					StringBuilder completeAddressBuilder = new StringBuilder();
					completeAddressBuilder.append(App.get(address) + ", ");
					completeAddressBuilder.append(App.get(city) + ", "
							+ App.get(landmark));

					// address.setEnabled(true);
					// address.setText(completeAddressBuilder.toString());
					// address.setEnabled(false);

				}
			}
		}
		updateDisplay();
	}

	// public boolean validatePatientId()
	// {
	// StringBuffer message = new StringBuffer ();
	// boolean valid = true;
	// if("".equals(App.get(patientId)))
	// {
	// message.append(patientId.getTag().toString() + ". ");
	// patientId.setHintTextColor(getResources().getColor(R.color.Red));
	// message.append (getResources ().getString (R.string.empty_data) + "\n");
	// valid = false;
	// }
	// else
	// {
	// if(RegexUtil.matchId(App.get(patientId)))
	// {
	// if (!RegexUtil.isValidId (App.get (patientId)))
	// {
	// valid = false;
	// message.append (patientId.getTag ().toString () + ": " + getResources
	// ().getString (R.string.invalid_data) + "\n");
	// patientId.setTextColor (getResources ().getColor (R.color.Red));
	// }
	// }
	// else
	// {
	// valid = false;
	// message.append (patientId.getTag ().toString () + ": " + getResources
	// ().getString (R.string.invalid_data) + "\n");
	// patientId.setTextColor (getResources ().getColor (R.color.Red));
	// }
	// }
	// if(!valid)
	// {
	// App.getAlertDialog (this, AlertType.ERROR, message.toString ()).show ();
	// }
	// return valid;
	// }
}
