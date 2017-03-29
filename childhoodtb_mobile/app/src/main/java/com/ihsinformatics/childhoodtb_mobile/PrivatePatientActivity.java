/**
 * Copyright(C) 2016 Interactive Health Solutions, Pvt. Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
 * You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
 * Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 * Contributors: Tahira Niazi
 */
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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
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
import com.ihsinformatics.childhoodtb_mobile.custom.MyRadioButton;
import com.ihsinformatics.childhoodtb_mobile.custom.MyRadioGroup;
import com.ihsinformatics.childhoodtb_mobile.custom.MySpinner;
import com.ihsinformatics.childhoodtb_mobile.custom.MyTextView;
import com.ihsinformatics.childhoodtb_mobile.shared.AlertType;
import com.ihsinformatics.childhoodtb_mobile.shared.FormType;
import com.ihsinformatics.childhoodtb_mobile.util.RegexUtil;
import com.ihsinformatics.childhoodtb_mobile.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author tahira.niazi@ihsinformatics.com
 * 
 */
public class PrivatePatientActivity extends AbstractFragmentActivity implements
		OnEditorActionListener {

	// Views displayed in pages, sorted w.r.t. appearance on pager
	MyTextView formDateTextView;
	MyButton formDateButton;

	MyTextView firstNameTextView;
	MyEditText firstName;
	MyTextView lastNameTextView;
	MyEditText lastName;

	MyTextView genderTextView;
	MyRadioGroup gender;
	MyRadioButton male;
	MyRadioButton female;

	MyTextView ageTextView;
	MyEditText age;

	MyTextView indexPatientTypeTextView;
	MySpinner indexPatientType;

	MyTextView contactTracingStrategyTextView;
	MySpinner contactTracingStrategy;

	MyTextView indexCaseIdTextView;
	MyEditText indexCaseId;

	MyButton scanBarcode;

	MyTextView indexDistrictTbNumberTextView;
	MyEditText indexDistrictTbNumber;

	MyTextView diagnosisTextView;
	MySpinner diagnosis;

	MyTextView totalPediatricsScreenedTextView;
	MyEditText totalPediatricsScreened;

	LinearLayout pediatricLinearLayout;

	MyTextView totalAdultsScreenedTextView;
	MyEditText totalAdultsScreened;

	LinearLayout adultLinearLayout;

	/**
	 * Subclass representing Fragment for ClinicalVisitBarriers form
	 *
	 * @author owais.hussain@ihsinformatics.com
	 *
	 */
	@SuppressLint("ValidFragment")
	class PrivatePatientFragment extends Fragment {
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
	 * Subclass for Pager Adapter. Uses ClinicalVisitBarriers subclass as items
	 *
	 * @author owais.hussain@irdresearch.org
	 *
	 */
	class PrivatePatientFragmentPagerAdapter extends FragmentPagerAdapter {
		/** Constructor of the class */
		public PrivatePatientFragmentPagerAdapter(
				FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem(int arg0) {
			PrivatePatientFragment fragment = new PrivatePatientFragment();
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
	public void initView(View[] views) {
		super.initView(views);
		formDate = Calendar.getInstance();
		updateDisplay();
		male.setChecked(true);
		adultLinearLayout.removeAllViews();
		pediatricLinearLayout.removeAllViews();
		// ageModifier.setSelection(3);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		if (view == formDateButton) {
			showDialog(DATE_DIALOG_ID);
		} else if (view == firstButton) {
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android
	 * .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged
	 * (android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
	 */
	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ihsinformatics.tbr4mobile_pk.AbstractFragmentActivity#createViews
	 * (android.content.Context)
	 */
	@Override
	public void createViews(Context context) {
		TAG = "PrivatePatientActivity";
		PAGE_COUNT = 5;
		pager = (ViewPager) findViewById(R.template_id.pager);
		navigationSeekbar.setMax(PAGE_COUNT - 1);
		navigatorLayout = (LinearLayout) findViewById(R.template_id.navigatorLayout);
		// If the form consists only of single page, then hide the
		// navigatorLayout
		if (PAGE_COUNT < 2) {
			navigatorLayout.setVisibility(View.GONE);
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		PrivatePatientFragmentPagerAdapter pagerAdapter = new PrivatePatientFragmentPagerAdapter(
				fragmentManager);
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit(PAGE_COUNT);
		// Create views for pages
		formDateTextView = new MyTextView(context, R.style.text,
				R.string.form_date);
		formDateButton = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_date,
				R.string.form_date);

		firstNameTextView = new MyTextView(context, R.style.text,
				R.string.first_name);
		firstName = new MyEditText(context, R.string.first_name,
				R.string.first_name_hint,
				InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 20,
				false);
		lastNameTextView = new MyTextView(context, R.style.text,
				R.string.last_name);
		lastName = new MyEditText(context, R.string.last_name,
				R.string.last_name_hint,
				InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 20,
				false);

		genderTextView = new MyTextView(context, R.style.text, R.string.gender);
		male = new MyRadioButton(context, R.string.male, R.style.radio,
				R.string.male);
		female = new MyRadioButton(context, R.string.female, R.style.radio,
				R.string.female);
		gender = new MyRadioGroup(context,
				new MyRadioButton[] { male, female }, R.string.gender,
				R.style.radio, App.isLanguageRTL());

		ageTextView = new MyTextView(context, R.style.text, R.string.age);
		age = new MyEditText(context, R.string.age, R.string.age_hint,
				InputType.TYPE_CLASS_NUMBER, R.style.edit, 2, false);

		indexPatientTypeTextView = new MyTextView(context, R.style.text,
				R.string.index_patient_type);
		indexPatientType = new MySpinner(context, getResources()
				.getStringArray(R.array.index_patient_types),
				R.string.index_patient_type, R.string.option_hint);

		contactTracingStrategyTextView = new MyTextView(context, R.style.text,
				R.string.contact_tracing_strategy);
		contactTracingStrategy = new MySpinner(context, getResources()
				.getStringArray(R.array.tracing_strategies),
				R.string.contact_tracing_strategy, R.string.option_hint);

		indexCaseIdTextView = new MyTextView(context, R.style.text,
				R.string.index_case_id);
		indexCaseId = new MyEditText(context, R.string.index_case_id,
				R.string.index_case_id_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, 12, false);

		scanBarcode = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.scan_barcode,
				R.string.scan_barcode);

		indexDistrictTbNumberTextView = new MyTextView(context, R.style.text,
				R.string.index_district_number);
		indexDistrictTbNumber = new MyEditText(context,
				R.string.index_district_number,
				R.string.index_district_number_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, 10, false);
		// indexDistrictTbNumber = new MyTextView(context, R.style.text,
		// R.string.empty_string);

		diagnosisTextView = new MyTextView(context, R.style.text,
				R.string.diagnosis);
		diagnosis = new MySpinner(context, getResources().getStringArray(
				R.array.diagnosis_options), R.string.diagnosis,
				R.string.option_hint);

		totalPediatricsScreenedTextView = new MyTextView(context, R.style.text,
				R.string.total_paediatric_contacts_screened);
		totalPediatricsScreened = new MyEditText(context,
				R.string.total_paediatric_contacts_screened,
				R.string.total_paediatric_contacts_screened_hint,
				InputType.TYPE_CLASS_NUMBER, R.style.edit, 2, false);

		pediatricLinearLayout = new LinearLayout(this);

		totalAdultsScreenedTextView = new MyTextView(context, R.style.text,
				R.string.total_adult_contacts_screened);
		totalAdultsScreened = new MyEditText(context,
				R.string.total_adult_contacts_screened,
				R.string.total_adult_contacts_screened_hint,
				InputType.TYPE_CLASS_NUMBER, R.style.edit, 2, false);

		adultLinearLayout = new LinearLayout(this);
		View[][] viewGroups = {
				{ formDateTextView, formDateButton, indexPatientTypeTextView,
						indexPatientType, contactTracingStrategyTextView,
						contactTracingStrategy, firstNameTextView, firstName,
						lastNameTextView, lastName },
				{ genderTextView, gender, ageTextView, age },
				{ indexCaseIdTextView, indexCaseId, scanBarcode,
						indexDistrictTbNumberTextView, indexDistrictTbNumber,
						diagnosisTextView, diagnosis },
				{ totalPediatricsScreenedTextView, totalPediatricsScreened,
						pediatricLinearLayout },
				{ totalAdultsScreenedTextView, totalAdultsScreened,
						adultLinearLayout } };

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
		formDateButton.setOnClickListener(this);
		firstButton.setOnClickListener(this);
		lastButton.setOnClickListener(this);
		clearButton.setOnClickListener(this);
		saveButton.setOnClickListener(this);
		scanBarcode.setOnClickListener(this);
		navigationSeekbar.setOnSeekBarChangeListener(this);
		age.setOnEditorActionListener(this);
		totalPediatricsScreened.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!"".equals(App.get(totalPediatricsScreened))) {
					if (pediatricLinearLayout.getChildCount() > 0) {
						pediatricLinearLayout.removeAllViews();
					}
					pediatricLinearLayout.removeAllViews();
					pediatricLinearLayout.setOrientation(LinearLayout.VERTICAL);
					int pediatricScreenedCount = Integer.parseInt(App
							.get(totalPediatricsScreened));
					MyTextView patientIdTextView;
					if (pediatricScreenedCount > 0) {
						List<View> pediatricPatientIdsList = new ArrayList<View>();
						for (int i = 0; i < pediatricScreenedCount; i++) {
							patientIdTextView = new MyTextView(
									PrivatePatientActivity.this, R.style.text,
									R.string.patient_id);
							patientIdTextView.setText("Pediatric Patient ID "
									+ (i + 1));
							pediatricPatientIdsList.add(patientIdTextView);

							pediatricPatientIdsList.add(new MyEditText(
									PrivatePatientActivity.this,
									R.string.patient_id,
									R.string.patient_id_hint,
									InputType.TYPE_CLASS_TEXT, R.style.edit,
									12, false));
						}

						for (int j = 0; j < pediatricPatientIdsList.size(); j++) {
							pediatricLinearLayout.addView(
									pediatricPatientIdsList.get(j), j);
						}
					}
				}
			}
		});

		totalAdultsScreened.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!"".equals(App.get(totalAdultsScreened))) {
					if (adultLinearLayout.getChildCount() > 0) {
						adultLinearLayout.removeAllViews();
					}
					adultLinearLayout.setOrientation(LinearLayout.VERTICAL);
					int adultScreenedCount = Integer.parseInt(App
							.get(totalAdultsScreened));
					MyTextView patientIdTextView;
					if (adultScreenedCount > 0) {
						List<View> adultPatientIdsList = new ArrayList<View>();
						for (int i = 0; i < adultScreenedCount; i++) {

							patientIdTextView = new MyTextView(
									PrivatePatientActivity.this, R.style.text,
									R.string.patient_id);
							patientIdTextView.setText("Adult Patient ID "
									+ (i + 1));
							adultPatientIdsList.add(patientIdTextView);

							adultPatientIdsList.add(new MyEditText(
									PrivatePatientActivity.this,
									R.string.patient_id,
									R.string.patient_id_hint,
									InputType.TYPE_CLASS_TEXT, R.style.edit,
									12, false));
						}

						for (int j = 0; j < adultPatientIdsList.size(); j++) {
							adultLinearLayout.addView(
									adultPatientIdsList.get(j), j);
						}
					}
				}
			}
		});

		views = new View[] { indexPatientType, contactTracingStrategy,
				firstName, lastName, age, indexCaseId, indexDistrictTbNumber,
				diagnosis, totalPediatricsScreened, totalAdultsScreened };

		for (View v : views) {
			if (v instanceof Spinner) {
				((Spinner) v).setOnItemSelectedListener(this);
			} else if (v instanceof CheckBox) {
				((CheckBox) v).setOnCheckedChangeListener(this);
			}
		}
		pager.setOnPageChangeListener(this);

		indexCaseId.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View view) {
				// Intent intent = new Intent (context,
				// PatientSearchActivity.class);
				// startActivity (intent);
				return false;
			}
		});
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ihsinformatics.tbr4mobile_pk.AbstractFragmentActivity#updateDisplay()
	 */
	@Override
	public void updateDisplay() {
		formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ihsinformatics.tbr4mobile_pk.AbstractFragmentActivity#validate()
	 */
	@Override
	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		View[] mandatory = { firstName, lastName, age, indexCaseId,
				totalPediatricsScreened, totalAdultsScreened };
		for (View v : mandatory) {
			if (App.get(v).equals("")) {
				valid = false;
				message.append(v.getTag().toString() + ". ");
				((EditText) v).setHintTextColor(getResources().getColor(
						R.color.Red));
			}
		}

		int adultContactsCount = adultLinearLayout.getChildCount();
		int editTextNumber = 0;
		for (int i = 0; i < adultContactsCount; i++) {
			if (adultLinearLayout.getChildAt(i) instanceof MyEditText) {
				editTextNumber++;
				String value = App.get(((EditText) adultLinearLayout
						.getChildAt(i)));
				if (App.get(((EditText) adultLinearLayout.getChildAt(i)))
						.equals("")) {
					valid = false;
					message.append("Adult "
							+ adultLinearLayout.getChildAt(i).getTag()
									.toString() + editTextNumber + ". ");
					((EditText) adultLinearLayout.getChildAt(i))
							.setHintTextColor(getResources().getColor(
									R.color.Red));
				}
			}
		}

		int pediatricContactsCount = pediatricLinearLayout.getChildCount();
		editTextNumber = 0;
		for (int i = 0; i < pediatricContactsCount; i++) {
			if (pediatricLinearLayout.getChildAt(i) instanceof MyEditText) {
				editTextNumber++;
				String value = App.get(((EditText) pediatricLinearLayout
						.getChildAt(i)));
				if (App.get(((EditText) pediatricLinearLayout.getChildAt(i)))
						.equals("")) {
					valid = false;
					message.append("Pediatric "
							+ pediatricLinearLayout.getChildAt(i).getTag()
									.toString() + editTextNumber + ". ");
					((EditText) pediatricLinearLayout.getChildAt(i))
							.setHintTextColor(getResources().getColor(
									R.color.Red));
				}
			}
		}

		if (!valid) {
			message.append(getResources().getString(R.string.empty_data) + "\n");
		}
		// Validate data
		if (valid) {
			if (!RegexUtil.isWord(App.get(firstName))) {
				valid = false;
				message.append(firstName.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				firstName.setTextColor(getResources().getColor(R.color.Red));
			}
			if (!RegexUtil.isWord(App.get(lastName))) {
				valid = false;
				message.append(lastName.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				lastName.setTextColor(getResources().getColor(R.color.Red));
			}

			if (RegexUtil.matchId(App.get(indexCaseId))) {
				if (!RegexUtil.isValidId(App.get(indexCaseId))) {
					valid = false;
					message.append(indexCaseId.getTag().toString() + ": "
							+ getResources().getString(R.string.invalid_data)
							+ "\n");
					indexCaseId.setTextColor(getResources().getColor(
							R.color.Red));
				}
			} else {
				valid = false;
				message.append(indexCaseId.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				indexCaseId.setTextColor(getResources().getColor(R.color.Red));
			}

			// checking for invalid pediatric patient IDs
			editTextNumber = 0;
			for (int i = 0; i < pediatricContactsCount; i++) {
				if (pediatricLinearLayout.getChildAt(i) instanceof MyEditText) {
					editTextNumber++;
					String value = App.get(((EditText) pediatricLinearLayout
							.getChildAt(i)));

					if (RegexUtil.matchId(App
							.get(((EditText) pediatricLinearLayout
									.getChildAt(i))))) {
						if (!RegexUtil.isValidId(App
								.get(((EditText) pediatricLinearLayout
										.getChildAt(i))))) {
							valid = false;
							message.append("Pediatric "
									+ pediatricLinearLayout.getChildAt(i)
											.getTag().toString()
									+ editTextNumber
									+ ": "
									+ getResources().getString(
											R.string.invalid_data) + "\n");
							((EditText) pediatricLinearLayout.getChildAt(i))
									.setTextColor(getResources().getColor(
											R.color.Red));
						}
					} else {
						valid = false;
						message.append("Pediatric "
								+ pediatricLinearLayout.getChildAt(i).getTag()
										.toString()
								+ editTextNumber
								+ ": "
								+ getResources().getString(
										R.string.invalid_data) + "\n");
						((EditText) pediatricLinearLayout.getChildAt(i))
								.setTextColor(getResources().getColor(
										R.color.Red));
					}
				}
			}

			// checking for invalid adult patient IDs
			editTextNumber = 0;
			for (int i = 0; i < adultContactsCount; i++) {
				if (adultLinearLayout.getChildAt(i) instanceof MyEditText) {
					editTextNumber++;
					String value = App.get(((EditText) adultLinearLayout
							.getChildAt(i)));

					if (RegexUtil.matchId(App.get(((EditText) adultLinearLayout
							.getChildAt(i))))) {
						if (!RegexUtil.isValidId(App
								.get(((EditText) adultLinearLayout
										.getChildAt(i))))) {
							valid = false;
							message.append("Adult "
									+ adultLinearLayout.getChildAt(i).getTag()
											.toString()
									+ editTextNumber
									+ ": "
									+ getResources().getString(
											R.string.invalid_data) + "\n");
							((EditText) adultLinearLayout.getChildAt(i))
									.setTextColor(getResources().getColor(
											R.color.Red));
						}
					} else {
						valid = false;
						message.append("adult "
								+ adultLinearLayout.getChildAt(i).getTag()
										.toString()
								+ editTextNumber
								+ ": "
								+ getResources().getString(
										R.string.invalid_data) + "\n");
						((EditText) adultLinearLayout.getChildAt(i))
								.setTextColor(getResources().getColor(
										R.color.Red));
					}
				}
			}

			if (App.get(firstName).length() < 3) {
				valid = false;
				message.append(firstName.getTag().toString()
						+ ": Min 3 characters should be entered." + "\n");
				firstName.setTextColor(getResources().getColor(R.color.Red));
			}
			if (App.get(lastName).length() < 3) {
				valid = false;
				message.append(lastName.getTag().toString()
						+ ": Min 3 characters should be entered." + "\n");
				lastName.setTextColor(getResources().getColor(R.color.Red));
			}

			if (!RegexUtil.isNumeric(App.get(age), false)) {
				valid = false;
				message.append(age.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				age.setTextColor(getResources().getColor(R.color.Red));
			} else {
				int ageInDays = 0;

				if (ageInDays > 365 * 15) {
					valid = false;
					message.append(age.getTag().toString()
							+ ": Age must be less than 15 years. "
							+ getResources().getString(R.string.out_of_range)
							+ "\n");
					age.setTextColor(getResources().getColor(R.color.Red));
					ageInDays = 0;
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

			// // Age range
			// if (Calendar.getInstance ().get (Calendar.YEAR) - dob.get
			// (Calendar.YEAR) > 99)
			// {
			// valid = false;
			// message.append (age.getTag ().toString () + ": " + getResources
			// ().getString (R.string.out_of_range) + "\n");
			// }

		} catch (NumberFormatException e) {
		}
		if (!valid) {
			App.getAlertDialog(this, AlertType.ERROR, message.toString())
					.show();
		}
		return valid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ihsinformatics.tbr4mobile_pk.AbstractFragmentActivity#submit()
	 */
	@Override
	public boolean submit() {
		if (validate()) {
			final ContentValues values = new ContentValues();
			values.put("formDate", App.getSqlDate(formDate));
			values.put("location", App.getLocation());
			values.put("patientId", App.get(indexCaseId));
			values.put("firstName", App.get(firstName));
			values.put("lastName", App.get(lastName));
			values.put("gender", male.isChecked() ? "M" : "F");
			values.put("age", App.get(age));
			final ArrayList<String[]> observations = new ArrayList<String[]>();
			observations.add(new String[] { "Index Patient Type",
					App.get(indexPatientType) });
			observations.add(new String[] { "Contact Tracing Strategy",
					App.get(contactTracingStrategy) });
			observations.add(new String[] { "Index Case District TB Number",
					App.get(indexDistrictTbNumber) });
			observations.add(new String[] { "Index Case Diagnosis",
					App.get(diagnosis) });
			observations.add(new String[] {
					"Total Paediatric Contacts Screened",
					App.get(totalPediatricsScreened) });
			int pediatricContactsCount = pediatricLinearLayout.getChildCount();
			StringBuilder pediatricPatientIds = new StringBuilder();
			for (int i = 0; i < pediatricContactsCount; i++) {
				i++;
				pediatricPatientIds.append(((MyEditText) pediatricLinearLayout
						.getChildAt(i)).getText());
				pediatricPatientIds.append(",");
			}

			String allPediatricPatientIds = pediatricPatientIds.toString();
			allPediatricPatientIds = allPediatricPatientIds.replaceAll(", $",
					"");
			observations.add(new String[] { "Paediatric Contacts Patient Ids",
					allPediatricPatientIds });

			observations.add(new String[] { "Total Adult Contacts Screened",
					App.get(totalAdultsScreened) });
			int adultContactsCount = adultLinearLayout.getChildCount();
			StringBuilder adultPatientIds = new StringBuilder();
			for (int i = 0; i < adultContactsCount; i++) {
				i++;
				adultPatientIds.append(((MyEditText) adultLinearLayout
						.getChildAt(i)).getText());
				adultPatientIds.append(",");
			}

			String allAdultPatientIds = adultPatientIds.toString();
			allAdultPatientIds = allAdultPatientIds.replaceAll(", $", "");
			observations.add(new String[] { "Adult Contacts Patient Ids",
					allAdultPatientIds });

			// attribute Type - Person Attribute
			values.put("districtTbNumber", App.get(indexDistrictTbNumber));

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
					result = serverService.savePrivatePatient(
							FormType.PRIVATE_PATIENT, values,
							observations.toArray(new String[][] {}));
					return result;
				}

				@Override
				protected void onProgressUpdate(String... values) {
				};

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					loading.dismiss();
					if (result.equals("SUCCESS")) {
						App.getAlertDialog(PrivatePatientActivity.this,
								AlertType.INFO,
								getResources().getString(R.string.inserted))
								.show();
						initView(views);
					} else {
						App.getAlertDialog(PrivatePatientActivity.this,
								AlertType.ERROR, result).show();
					}
				}
			};
			updateTask.execute("");
		}
		return false;
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
					indexCaseId.setText(str);
				} else {
					App.getAlertDialog(
							this,
							AlertType.ERROR,
							indexCaseId.getTag().toString()
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.TextView.OnEditorActionListener#onEditorAction(android
	 * .widget.TextView, int, android.view.KeyEvent)
	 */
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
