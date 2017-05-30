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

public class ContactTracingActivity extends AbstractFragmentActivity implements
		OnEditorActionListener {
	// Views displayed in pages, sorted w.r.t. appearance on pager
	MyTextView formDateTextView;
	MyButton formDateButton;

	MyTextView indexPatientTypeTextView;
	MySpinner indexPatientType;

	MyTextView contactTracingStrategyTextView;
	MySpinner contactTracingStrategy;

	MyTextView indexCaseIdTextView;
	MyEditText indexCaseId;

	MyTextView indexDistrictTbNumberTextView;
	MyEditText indexDistrictTbNumber;

	MyTextView diagnosisTextView;
	MySpinner diagnosis;

	//
	MyTextView totalNumberAdultMembersTextView;
	MyEditText totalNumberAdultMembers;
	//
	MyTextView totalNumberAdultMembersWithSymptomsTextView;
	MyEditText totalNumberAdultMembersWithSymptoms;

	MyTextView namesAdultSymptomaticMembersTextView;
	MyEditText namesAdultSymptomaticMembers;
	//
	MyTextView totalNumberPaediatricMembersTextView;
	MyEditText totalNumberPaediatricMembers;
	//
	MyTextView totalNumberPaediatricMembersWithSymptomsTextView;
	MyEditText totalNumberPaediatricMembersWithSymptoms;

	MyTextView namesPaediatricSymptomaticMembersTextView;
	MyEditText namesPaediatricSymptomaticMembers;

	MyTextView totalNumberMembersTextView;
	MyTextView totalNumberMembers;

	MyTextView totalNumberMembersWithSymptomsTextView;
	MyTextView totalNumberMembersWithSymptoms;

	// MyTextView namesSymptomaticMembersTextView;
	// MyEditText namesSymptomaticMembers;
	MyButton scanBarcode;

	/**
	 * Subclass representing Fragment for Pediatric-screeening suspect form
	 * 
	 * @author owais.hussain@ihsinformatics.com
	 * 
	 */
	@SuppressLint("ValidFragment")
	class ReverseContactTracingFragment extends Fragment {
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
	 * Subclass for Pager Adapter. Uses PediatricScreeningSuspect subclass as
	 * items
	 * 
	 * @author owais.hussain@irdresearch.org
	 * 
	 */
	class ReverseContactTracingFragmentPagerAdapter extends
			FragmentPagerAdapter {
		/** Constructor of the class */
		public ReverseContactTracingFragmentPagerAdapter(
				FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem(int arg0) {
			ReverseContactTracingFragment fragment = new ReverseContactTracingFragment();
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
		TAG = "ReverseContactTracingActivity";
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
		ReverseContactTracingFragmentPagerAdapter pagerAdapter = new ReverseContactTracingFragmentPagerAdapter(
				fragmentManager);
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit(PAGE_COUNT);
		// Create views for pages
		formDateTextView = new MyTextView(context, R.style.text,
				R.string.form_date);
		formDateButton = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_date,
				R.string.form_date);

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

		indexDistrictTbNumberTextView = new MyTextView(context, R.style.text,
				R.string.index_district_number);
		indexDistrictTbNumber = new MyEditText(context,
				R.string.index_district_number,
				R.string.index_district_number_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, 12, false);

		diagnosisTextView = new MyTextView(context, R.style.text,
				R.string.diagnosis);
		diagnosis = new MySpinner(context, getResources().getStringArray(
				R.array.diagnosis_options), R.string.diagnosis,
				R.string.option_hint);

		totalNumberAdultMembersTextView = new MyTextView(context, R.style.text,
				R.string.total_adult_household_members);
		totalNumberAdultMembers = new MyEditText(context,
				R.string.total_adult_household_members,
				R.string.total_adult_household_members_hint,
				InputType.TYPE_CLASS_NUMBER, R.style.edit, 2, false);

		totalNumberAdultMembersWithSymptomsTextView = new MyTextView(context,
				R.style.text, R.string.adult_members_with_symptoms);
		totalNumberAdultMembersWithSymptoms = new MyEditText(context,
				R.string.adult_members_with_symptoms,
				R.string.adult_members_with_symptoms_hint,
				InputType.TYPE_CLASS_NUMBER, R.style.edit, 2, false);

		totalNumberPaediatricMembersTextView = new MyTextView(context,
				R.style.text, R.string.total_paediatric_household_members);
		totalNumberPaediatricMembers = new MyEditText(context,
				R.string.total_paediatric_household_members,
				R.string.total_paediatric_household_members_hint,
				InputType.TYPE_CLASS_NUMBER, R.style.edit, 2, false);

		totalNumberPaediatricMembersWithSymptomsTextView = new MyTextView(
				context, R.style.text,
				R.string.paediatric_members_with_symptoms);
		totalNumberPaediatricMembersWithSymptoms = new MyEditText(context,
				R.string.paediatric_members_with_symptoms,
				R.string.paediatric_members_with_symptoms_hint,
				InputType.TYPE_CLASS_NUMBER, R.style.edit, 2, false);

		totalNumberMembersTextView = new MyTextView(context, R.style.text,
				R.string.total_household_members);
		totalNumberMembers = new MyTextView(context, R.style.text,
				R.string.total_members_count);

		// totalNumberMembers = new MyEditText(context,
		// R.string.total_household_members,
		// R.string.total_household_members_hint,
		// InputType.TYPE_CLASS_NUMBER, R.style.edit, 2, false);

		totalNumberMembersWithSymptomsTextView = new MyTextView(context,
				R.style.text, R.string.total_household_members_with_symptoms);

		totalNumberMembersWithSymptoms = new MyTextView(context, R.style.text,
				R.string.total_members_count);

		// totalNumberMembersWithSymptoms = new MyEditText(context,
		// R.string.total_household_members_with_symptoms,
		// R.string.total_household_members_with_symptoms_hint,
		// InputType.TYPE_CLASS_NUMBER, R.style.edit, 2, false);

		namesAdultSymptomaticMembersTextView = new MyTextView(context,
				R.style.text, R.string.names_adult_symptomatic_members);
		namesAdultSymptomaticMembers = new MyEditText(context,
				R.string.names_adult_symptomatic_members,
				R.string.names_adult_symptomatic_members_hint,
				InputType.TYPE_CLASS_TEXT, R.style.edit, 100, false);

		namesPaediatricSymptomaticMembersTextView = new MyTextView(context,
				R.style.text, R.string.names_paediatric_symptomatic_members);
		namesPaediatricSymptomaticMembers = new MyEditText(context,
				R.string.names_paediatric_symptomatic_members,
				R.string.names_paediatric_symptomatic_members_hint,
				InputType.TYPE_CLASS_TEXT, R.style.edit, 100, false);

		scanBarcode = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.scan_barcode,
				R.string.scan_barcode);

		View[][] viewGroups = {
				{ formDateTextView, formDateButton, indexPatientTypeTextView,
						indexPatientType, contactTracingStrategyTextView,
						contactTracingStrategy, indexCaseIdTextView,
						indexCaseId, scanBarcode,
						indexDistrictTbNumberTextView, indexDistrictTbNumber,
						diagnosisTextView, diagnosis },
				{ totalNumberAdultMembersTextView, totalNumberAdultMembers,
						totalNumberAdultMembersWithSymptomsTextView,
						totalNumberAdultMembersWithSymptoms,
						namesAdultSymptomaticMembersTextView,
						namesAdultSymptomaticMembers },
				{ totalNumberPaediatricMembersTextView,
						totalNumberPaediatricMembers,
						totalNumberPaediatricMembersWithSymptomsTextView,
						totalNumberPaediatricMembersWithSymptoms,
						namesPaediatricSymptomaticMembersTextView,
						namesPaediatricSymptomaticMembers },
				{ totalNumberMembersTextView, totalNumberMembers,
						totalNumberMembersWithSymptomsTextView,
						totalNumberMembersWithSymptoms } };

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
		totalNumberAdultMembersWithSymptoms
				.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						if (!"".equals(App
								.get(totalNumberAdultMembersWithSymptoms))) {
							boolean hasSymptomaticMembers = Integer.parseInt(App
									.get(totalNumberAdultMembersWithSymptoms)) > 0;
							namesAdultSymptomaticMembersTextView
									.setEnabled(hasSymptomaticMembers);
							namesAdultSymptomaticMembers
									.setEnabled(hasSymptomaticMembers);
						} else {
							namesAdultSymptomaticMembersTextView
									.setEnabled(false);
							namesAdultSymptomaticMembers.setEnabled(false);
						}
					}
				});

		totalNumberPaediatricMembersWithSymptoms
				.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// TODO Auto-generated method stub

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable s) {
						if (!"".equals(App
								.get(totalNumberPaediatricMembersWithSymptoms))) {
							boolean hasSymptomaticMembers = Integer.parseInt(App
									.get(totalNumberPaediatricMembersWithSymptoms)) > 0;
							namesPaediatricSymptomaticMembersTextView
									.setEnabled(hasSymptomaticMembers);
							namesPaediatricSymptomaticMembers
									.setEnabled(hasSymptomaticMembers);
						} else {
							namesPaediatricSymptomaticMembersTextView
									.setEnabled(false);
							namesPaediatricSymptomaticMembers.setEnabled(false);
						}

					}
				});

		totalNumberAdultMembers.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!"".equals(App.get(totalNumberAdultMembers))) {
					boolean hasMembers = Integer.parseInt(App
							.get(totalNumberAdultMembers)) > 0;
					totalNumberAdultMembersWithSymptomsTextView
							.setEnabled(hasMembers);
					totalNumberAdultMembersWithSymptoms.setEnabled(hasMembers);
				} else {
					totalNumberAdultMembersWithSymptomsTextView
							.setEnabled(false);
					totalNumberAdultMembersWithSymptoms.setEnabled(false);
				}
			}
		});

		totalNumberPaediatricMembers.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!"".equals(App.get(totalNumberPaediatricMembers))) {
					boolean hasMembers = Integer.parseInt(App
							.get(totalNumberPaediatricMembers)) > 0;
					totalNumberPaediatricMembersWithSymptomsTextView
							.setEnabled(hasMembers);
					totalNumberPaediatricMembersWithSymptoms
							.setEnabled(hasMembers);
				} else {
					totalNumberPaediatricMembersWithSymptomsTextView
							.setEnabled(false);
					totalNumberPaediatricMembersWithSymptoms.setEnabled(false);
				}
			}
		});

		views = new View[] { indexPatientType, contactTracingStrategy,
				indexCaseId, indexDistrictTbNumber, diagnosis,
				totalNumberAdultMembers, totalNumberAdultMembersWithSymptoms,
				totalNumberPaediatricMembers,
				totalNumberPaediatricMembersWithSymptoms,
				namesAdultSymptomaticMembers, namesPaediatricSymptomaticMembers };

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

	@Override
	public void initView(View[] views) {
		super.initView(views);
		formDate = Calendar.getInstance();

		totalNumberAdultMembersWithSymptomsTextView.setEnabled(false);
		totalNumberAdultMembersWithSymptoms.setEnabled(false);

		totalNumberPaediatricMembersWithSymptomsTextView.setEnabled(false);
		totalNumberPaediatricMembersWithSymptoms.setEnabled(false);

		// totalNumberMembersWithSymptomsTextView.setEnabled(false);
		// totalNumberMembersWithSymptoms.setEnabled(false);
		// namesSymptomaticMembersTextView.setEnabled(false);
		// namesSymptomaticMembers.setEnabled(false);
		updateDisplay();
	}

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

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		MySpinner spinner = (MySpinner) parent;
		boolean visible = spinner.getSelectedItemPosition() == 1;
		updateDisplay();
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

	}

	@Override
	public boolean onLongClick(View arg0) {
		return false;
	}

	@Override
	public void updateDisplay() {
		formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));

		int totalMembersCount = 0;

		int totalSymptomaticMembersCount = 0;

		// calculating of total of both Adult and Paediatric Members

		if (!"".equals(App.get(totalNumberAdultMembers))
				&& !"".equals(App.get(totalNumberPaediatricMembers))) {
			totalMembersCount = Integer.parseInt(App
					.get(totalNumberAdultMembers))
					+ Integer.parseInt(App.get(totalNumberPaediatricMembers));
		} else if (!"".equals(App.get(totalNumberAdultMembers))) {
			totalMembersCount = Integer.parseInt(App
					.get(totalNumberAdultMembers));
		} else if (!"".equals(App.get(totalNumberPaediatricMembers))) {
			totalMembersCount = Integer.parseInt(App
					.get(totalNumberPaediatricMembers));
		}

		// calculating total of Symptomatic Members

		if (!"".equals(App.get(totalNumberAdultMembersWithSymptoms))
				&& !"".equals(App.get(totalNumberPaediatricMembersWithSymptoms))) {
			totalSymptomaticMembersCount = Integer.parseInt(App
					.get(totalNumberAdultMembersWithSymptoms))
					+ Integer.parseInt(App
							.get(totalNumberPaediatricMembersWithSymptoms));
		} else if (!"".equals(App.get(totalNumberAdultMembersWithSymptoms))) {
			totalSymptomaticMembersCount = Integer.parseInt(App
					.get(totalNumberAdultMembersWithSymptoms));
		} else if (!""
				.equals(App.get(totalNumberPaediatricMembersWithSymptoms))) {
			totalSymptomaticMembersCount = Integer.parseInt(App
					.get(totalNumberPaediatricMembersWithSymptoms));
		}

		totalNumberMembers.setText(String.valueOf(totalMembersCount));
		totalNumberMembersWithSymptoms.setText(String
				.valueOf(totalSymptomaticMembersCount));

	}

	@Override
	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		View[] mandatory = { totalNumberAdultMembers,
				totalNumberPaediatricMembers };
		for (View v : mandatory) {
			if (App.get(v).equals("")) {
				valid = false;
				message.append(v.getTag().toString() + ". ");
				((EditText) v).setHintTextColor(getResources().getColor(
						R.color.Red));
			}
		}
		// if(!"".equals(App.get(totalNumberMembers)))
		// {
		// if (Integer.parseInt(App.get(totalNumberMembers)) > 0
		// && App.get(totalNumberMembersWithSymptoms).equals(""))
		// {
		// valid = false;
		// message.append(totalNumberMembersWithSymptoms.getTag().toString() +
		// ". ");
		// totalNumberMembersWithSymptoms.setHintTextColor(getResources().getColor(R.color.Red));
		// }
		// }

		if (!"".equals(App.get(totalNumberAdultMembersWithSymptoms))) {
			if (Integer.parseInt(App.get(totalNumberAdultMembersWithSymptoms)) > 0
					&& App.get(namesAdultSymptomaticMembers).equals("")) {
				valid = false;
				message.append(namesAdultSymptomaticMembers.getTag().toString()
						+ ". ");
				namesAdultSymptomaticMembers.setHintTextColor(getResources()
						.getColor(R.color.Red));
			}
		}

		if (!"".equals(App.get(totalNumberPaediatricMembersWithSymptoms))) {
			if (Integer.parseInt(App
					.get(totalNumberPaediatricMembersWithSymptoms)) > 0
					&& App.get(namesPaediatricSymptomaticMembers).equals("")) {
				valid = false;
				message.append(namesPaediatricSymptomaticMembers.getTag()
						.toString() + ". ");
				namesPaediatricSymptomaticMembers
						.setHintTextColor(getResources().getColor(R.color.Red));
			}
		}

		if (!valid) {
			message.append(getResources().getString(R.string.empty_data) + "\n");
		}

		// // Validate data
		if (valid) {
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

			if (!RegexUtil.isNumeric(App.get(totalNumberAdultMembers), false)) {
				valid = false;
				message.append(totalNumberAdultMembers.getTag().toString()
						+ ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				totalNumberAdultMembers.setTextColor(getResources().getColor(
						R.color.Red));
			}

			if (!RegexUtil.isNumeric(
					App.get(totalNumberAdultMembersWithSymptoms), false)) {
				valid = false;
				message.append(totalNumberAdultMembersWithSymptoms.getTag()
						.toString()
						+ ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				totalNumberAdultMembersWithSymptoms.setTextColor(getResources()
						.getColor(R.color.Red));
			}

			if (!RegexUtil.isNumeric(App.get(totalNumberPaediatricMembers),
					false)) {
				valid = false;
				message.append(totalNumberPaediatricMembers.getTag().toString()
						+ ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				totalNumberPaediatricMembers.setTextColor(getResources()
						.getColor(R.color.Red));
			}

			if (totalNumberPaediatricMembersWithSymptoms.isEnabled()
					&& !RegexUtil.isNumeric(
							App.get(totalNumberPaediatricMembersWithSymptoms),
							false)) {
				valid = false;
				message.append(totalNumberPaediatricMembersWithSymptoms
						.getTag().toString()
						+ ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				totalNumberPaediatricMembersWithSymptoms
						.setTextColor(getResources().getColor(R.color.Red));
			}

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
			values.put("formDate", App.getSqlDate(formDate));
			values.put("location", App.getLocation());
			// values.put ("age", ageInYears);
			values.put("patientId", App.get(indexCaseId));
			final ArrayList<String[]> observations = new ArrayList<String[]>();

			// if(App.get(indexPatientType).equals(getResources().getString(R.string.tbr4pk_patient)))
			// {
			//
			// }

			observations.add(new String[] { "Contact Tracing Strategy",
					App.get(contactTracingStrategy) });

			observations.add(new String[] { "Index Case District TB Number",
					App.get(indexDistrictTbNumber) });
			observations.add(new String[] { "Index Case Diagnosis",
					App.get(diagnosis) });

			observations.add(new String[] { "Total Adult Household Members",
					App.get(totalNumberAdultMembers) });

			if (Integer.parseInt(App.get(totalNumberAdultMembers)) > 0) {
				observations.add(new String[] {
						"Total Adult Symptomatic Members",
						App.get(totalNumberAdultMembersWithSymptoms) });
			}

			if (Integer.parseInt(App.get(totalNumberAdultMembersWithSymptoms)
					.equals("") ? "0" : App
					.get(totalNumberAdultMembersWithSymptoms)) > 0) {
				observations.add(new String[] {
						"Symptomatic Adult Members Names",
						App.get(namesAdultSymptomaticMembers) });
			}

			// -----------------------------------------------------------------------

			observations.add(new String[] {
					"Total Paediatric Household Members",
					App.get(totalNumberPaediatricMembers) });

			if (Integer.parseInt(App.get(totalNumberPaediatricMembers)) > 0) {
				observations.add(new String[] {
						"Total Paediatric Symptomatic Members",
						App.get(totalNumberPaediatricMembersWithSymptoms) });
			}

			if (Integer.parseInt(App.get(
					totalNumberPaediatricMembersWithSymptoms).equals("") ? "0"
					: App.get(totalNumberPaediatricMembersWithSymptoms)) > 0) {
				observations.add(new String[] {
						"Symptomatic Paediatric Members Names",
						App.get(namesPaediatricSymptomaticMembers) });
			}

			observations.add(new String[] { "Total Household Members",
					App.get(totalNumberMembers) });

			if (Integer.parseInt(App.get(totalNumberMembers)) > 0) {
				observations.add(new String[] {
						"Total Household Members with Symptoms",
						App.get(totalNumberMembersWithSymptoms) });
			}

			// if (Integer.parseInt(App.get(totalNumberMembersWithSymptoms)) >
			// 0)
			// {
			// observations.add(new String[] {
			// "Symptomatic Household Members Names",
			// App.get(namesSymptomaticMembers) });
			// }

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
					result = serverService.saveReverseContact(
							FormType.REVERSE_CONTACT_TRACING, values,
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
						App.getAlertDialog(ContactTracingActivity.this,
								AlertType.INFO,
								getResources().getString(R.string.inserted))
								.show();
						initView(views);
					} else {
						App.getAlertDialog(ContactTracingActivity.this,
								AlertType.ERROR, result).show();
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

	@Override
	public boolean onEditorAction(TextView v, int arg1, KeyEvent arg2) {
		return false;
	}
}
