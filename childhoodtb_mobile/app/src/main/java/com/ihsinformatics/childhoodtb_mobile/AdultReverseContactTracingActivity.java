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
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import android.text.format.DateFormat;
import android.text.method.KeyListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
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
import com.ihsinformatics.childhoodtb_mobile.custom.MyCheckBox;
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
import java.util.Locale;

public class AdultReverseContactTracingActivity extends
		AbstractFragmentActivity implements KeyListener, OnEditorActionListener {
	// Views displayed in pages, sorted w.r.t. appearance on pager
	MyTextView formDateTextView;
	MyButton formDateButton;

	MyTextView indexPatientTypeTextView;
	MySpinner indexPatientType;

	MyTextView contactTracingStrategyTextView;
	MySpinner contactTracingStrategy;

	MyTextView visitTypeTextView;
	MySpinner visitType;

	MyTextView screenedBeforeTextView;
	MySpinner screenedBefore;

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

	MyTextView indexCaseIdTextView;
	MyEditText indexCaseId;

	MyTextView indexDistrictTbNumberTextView;
	MyEditText indexDistrictTbNumber;

	MyTextView diagnosisTextView;
	MySpinner diagnosis;

	MyTextView coughTextView;
	MySpinner cough;
	MyTextView coughDurationTextView;
	MySpinner coughDuration;

	MyTextView productiveCoughTextView;
	MySpinner productiveCough;

	MyTextView feverTextView;
	MySpinner fever;

	MyTextView nightSweatsTextView;
	MySpinner nightSweats;

	MyTextView weightLossTextView;
	MySpinner weightLoss;

	MyTextView lymphNodeSwellingTextView;
	MySpinner lymphNodeSwelling;

	MyTextView jointSpineSwellingTextView;
	MySpinner jointSpineSwelling;

	MyTextView historyContactTbTextView;
	MySpinner historyContactTb;

	MyTextView tbBeforeTextView;
	MySpinner tbBefore;

	MyTextView tbMedicationTextView;
	MySpinner tbMedication;

	MyTextView tbSuspectTextView;
	MyCheckBox tbSuspect;

	MyTextView patientIdTextView;
	MyEditText patientId;

	MyButton scanBarcodeIndexId;
	MyButton scanBarcode;
	MyButton validatePatientId;

	boolean isSuspect;

	/**
	 * Subclass representing Fragment for Pediatric-screeening suspect form
	 * 
	 * @author owais.hussain@ihsinformatics.com
	 * 
	 */
	@SuppressLint("ValidFragment")
	class AdultReverseContactTracingFragment extends Fragment {
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
	class AdultReverseContactTracingFragmentPagerAdapter extends
			FragmentPagerAdapter {
		/** Constructor of the class */
		public AdultReverseContactTracingFragmentPagerAdapter(
				FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem(int arg0) {
			AdultReverseContactTracingFragment fragment = new AdultReverseContactTracingFragment();
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
		TAG = "AdultReverseContactTracingActivity";
		PAGE_COUNT = 6;
		pager = (ViewPager) findViewById(R.template_id.pager);
		navigationSeekbar.setMax(PAGE_COUNT - 1);
		navigatorLayout = (LinearLayout) findViewById(R.template_id.navigatorLayout);
		// If the form consists only of single page, then hide the
		// navigatorLayout
		if (PAGE_COUNT < 2) {
			navigatorLayout.setVisibility(View.GONE);
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		AdultReverseContactTracingFragmentPagerAdapter pagerAdapter = new AdultReverseContactTracingFragmentPagerAdapter(
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

		visitTypeTextView = new MyTextView(context, R.style.text,
				R.string.visit_type);
		visitType = new MySpinner(context, getResources().getStringArray(
				R.array.visit_type_options), R.string.visit_type,
				R.string.option_hint);

		screenedBeforeTextView = new MyTextView(context, R.style.text,
				R.string.screened_before);
		screenedBefore = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.screened_before,
				R.string.option_hint);

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

		// following (until diagnosis) defined in reverse_contact_tracing.xml
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
				R.style.edit, 10, false);
		// indexDistrictTbNumber = new MyTextView(context, R.style.text,
		// R.string.empty_string);

		diagnosisTextView = new MyTextView(context, R.style.text,
				R.string.diagnosis);
		diagnosis = new MySpinner(context, getResources().getStringArray(
				R.array.diagnosis_options), R.string.diagnosis,
				R.string.option_hint);

		coughTextView = new MyTextView(context, R.style.text,
				R.string.adult_cough);
		cough = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.adult_cough,
				R.string.option_hint);
		coughDurationTextView = new MyTextView(context, R.style.text,
				R.string.adult_cough_duration);
		coughDuration = new MySpinner(context, getResources().getStringArray(
				R.array.adult_cough_durations), R.string.child_cough_duration,
				R.string.option_hint);
		productiveCoughTextView = new MyTextView(context, R.style.text,
				R.string.cough_productive);
		productiveCough = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.cough_productive,
				R.string.option_hint);

		feverTextView = new MyTextView(context, R.style.text,
				R.string.fever_weeks);
		fever = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.fever_weeks,
				R.string.option_hint);

		nightSweatsTextView = new MyTextView(context, R.style.text,
				R.string.adult_night_sweats);
		nightSweats = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.adult_night_sweats,
				R.string.option_hint);

		weightLossTextView = new MyTextView(context, R.style.text,
				R.string.adult_weight_loss);
		weightLoss = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.adult_weight_loss,
				R.string.option_hint);

		lymphNodeSwellingTextView = new MyTextView(context, R.style.text,
				R.string.lymph_node_swelling);
		lymphNodeSwelling = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options_with_poor),
				R.string.lymph_node_swelling, R.string.option_hint);

		jointSpineSwellingTextView = new MyTextView(context, R.style.text,
				R.string.joint_spine_swelling);
		jointSpineSwelling = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.joint_spine_swelling, R.string.option_hint);

		historyContactTbTextView = new MyTextView(context, R.style.text,
				R.string.contact_history_adult);
		historyContactTb = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.contact_history_adult, R.string.option_hint);

		tbBeforeTextView = new MyTextView(context, R.style.text,
				R.string.tb_before);
		tbBefore = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.tb_before, R.string.option_hint);

		tbMedicationTextView = new MyTextView(context, R.style.text,
				R.string.tb_medication);
		tbMedication = new MySpinner(context, getResources().getStringArray(
				R.array.two_options), R.string.tb_medication,
				R.string.option_hint);

		tbSuspect = new MyCheckBox(context, R.string.client_suspect,
				R.style.edit, R.string.client_suspect, false);
		tbSuspect.setClickable(false);

		patientIdTextView = new MyTextView(context, R.style.text,
				R.string.patient_id);
		patientId = new MyEditText(context, R.string.patient_id,
				R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, RegexUtil.idLength, false);
		scanBarcodeIndexId = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.scan_barcode,
				R.string.scan_barcode);
		scanBarcode = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.scan_barcode,
				R.string.scan_barcode);
		validatePatientId = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.validateID,
				R.string.validateID);

		View[][] viewGroups = {
				{ formDateTextView, formDateButton, indexPatientTypeTextView,
						indexPatientType, contactTracingStrategyTextView,
						contactTracingStrategy, visitTypeTextView, visitType,
						screenedBeforeTextView, screenedBefore,
						firstNameTextView, firstName, lastNameTextView,
						lastName },
				{ genderTextView, gender, ageTextView, age },
				{ indexCaseIdTextView, indexCaseId, scanBarcodeIndexId,
						validatePatientId, indexDistrictTbNumberTextView,
						indexDistrictTbNumber, diagnosisTextView, diagnosis },
				{ coughTextView, cough, coughDurationTextView, coughDuration,
						productiveCoughTextView, productiveCough,
						feverTextView, fever },
				{ nightSweatsTextView, nightSweats, weightLossTextView,
						weightLoss, lymphNodeSwellingTextView,
						lymphNodeSwelling, jointSpineSwellingTextView,
						jointSpineSwelling },
				{ historyContactTbTextView, historyContactTb, tbBeforeTextView,
						tbBefore, tbMedicationTextView, tbMedication,
						tbSuspect, patientIdTextView, patientId, scanBarcode } };

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
		scanBarcodeIndexId.setOnClickListener(this);
		validatePatientId.setOnClickListener(this);
		navigationSeekbar.setOnSeekBarChangeListener(this);
		age.setOnEditorActionListener(this);
		views = new View[] { age, indexPatientType, contactTracingStrategy,
				visitType, screenedBefore, indexCaseId, indexDistrictTbNumber,
				diagnosis, cough, coughDuration, productiveCough, fever,
				nightSweats, weightLoss, lymphNodeSwelling, jointSpineSwelling,
				historyContactTb, tbBefore, tbMedication, firstName, lastName,
				tbSuspect, patientId };

		for (View v : views) {
			if (v instanceof Spinner) {
				((Spinner) v).setOnItemSelectedListener(this);
			} else if (v instanceof CheckBox) {
				((CheckBox) v).setOnCheckedChangeListener(this);
			}
		}
		pager.setOnPageChangeListener(this);
		age.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View view, boolean state) {
				if (!state) {
					updateDob();
				}
			}
		});
		patientId.setOnLongClickListener(new OnLongClickListener() {
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
		updateDisplay();
		male.setChecked(true);
		cough.setSelection(1);
		productiveCough.setSelection(1);
		fever.setSelection(1);
		nightSweats.setSelection(1);
		weightLoss.setSelection(1);
		tbBefore.setSelection(1);
		tbMedication.setSelection(1);
		patientIdTextView.setEnabled(false);
		patientId.setEnabled(false);
		scanBarcode.setEnabled(false);
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
		} else if (view == scanBarcodeIndexId) {
			Intent intent = new Intent(Barcode.BARCODE_INTENT);
			intent.putExtra(Barcode.SCAN_MODE, Barcode.QR_MODE);
			startActivityForResult(intent, Barcode.BARCODE_RESULT_INDEX_ID);
		} else if (view == validatePatientId) {
			final String patId = App.get(indexCaseId);
			final String[] conceptArray = { "Index Case Diagnosis",
					"Index Case District TB Number" };
			if (!patId.equals("")) {
				AsyncTask<String, String, String[][]> getTask = new AsyncTask<String, String, String[][]>() {
					@Override
					protected String[][] doInBackground(String... params) {
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
						String[][] patientObs = serverService
								.getSpecificPatientObs(patId, conceptArray,
										FormType.REVERSE_CONTACT_TRACING);
						return patientObs;
					}

					@Override
					protected void onProgressUpdate(String... values) {
					}
					@Override
					protected void onPostExecute(String[][] result) {
						super.onPostExecute(result);
						loading.dismiss();

						indexDistrictTbNumber.setText("");

						if (result != null && result.length > 0) {
							for (int i = 0; i < result.length; i++) {
								if (result[i][0].equals("Index Case Diagnosis")) {
									if (result[i][1].equals("Pulmonary"))
										diagnosis.setSelection(0);
									else
										diagnosis.setSelection(1);

									diagnosis.setClickable(false);
								} else {
									// indexDistrictTbNumber.setTextColor(getResources().getColor(R.color.Chocolate));
									indexDistrictTbNumber.setText(result[i][1]);
								}
							}
						} else {
							App.getAlertDialog(
									AdultReverseContactTracingActivity.this,
									AlertType.ERROR,
									indexCaseId.getTag().toString()
											+ ": "
											+ getResources().getString(
													R.string.item_not_found))
									.show();
						}
					}
				};
				getTask.execute("");
			} else {
				App.getAlertDialog(
						this,
						AlertType.ERROR,
						indexCaseId.getTag().toString() + ": "
								+ getResources().getString(R.string.empty_data))
						.show();
			}
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		MySpinner spinner = (MySpinner) parent;
		boolean visible = spinner.getSelectedItemPosition() == 1;
		if (parent == cough) {
			coughDurationTextView.setEnabled(visible);
			coughDuration.setEnabled(visible);
			productiveCoughTextView.setEnabled(visible);
			productiveCough.setEnabled(visible);
		}

		else if (parent == tbBefore) {
			tbMedicationTextView.setEnabled(visible);
			tbMedication.setEnabled(visible);
		} else if (parent == screenedBefore) {
			if (screenedBefore.getSelectedItem().toString()
					.equals(getResources().getString(R.string.yes))) {
				String message = "The client is screened already. Form will now close.";
				AlertDialog dialog;
				Builder builder = new Builder(this);
				builder.setMessage(message);
				builder.setIcon(R.drawable.info);
				dialog = builder.create();
				dialog.setTitle("Information");
				OnClickListener listener = new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
						Intent mainMenuIntent = new Intent(
								AdultReverseContactTracingActivity.this,
								MainMenuActivity.class);
						startActivity(mainMenuIntent);
					}
				};
				dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", listener);
				dialog.show();
			}
		} else if (parent == indexPatientType) {
			boolean isPrivatePatient = false;
			boolean isTbr4Patient;
			isTbr4Patient = indexPatientType.getSelectedItem().toString()
					.equals(getResources().getString(R.string.tbr4pk_patient));
			isPrivatePatient = !isTbr4Patient;

			if (isPrivatePatient) {

				// indexDisrictTbNumber should be editable for private patient
				indexDistrictTbNumber.setKeyListener(this);
				indexDistrictTbNumber.setInputType(InputType.TYPE_CLASS_TEXT);
				indexDistrictTbNumber.setText("");
				diagnosis.setClickable(isPrivatePatient);
			} else {
				indexDistrictTbNumber.setText("");
				indexDistrictTbNumber.setKeyListener(null);
			}
		}
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
		// Auto-populate TB suspect logic
		tbSuspect.setChecked(false);
		patientIdTextView.setEnabled(false);
		patientId.setEnabled(false);

		boolean isCough = App.get(cough).equals(
				getResources().getString(R.string.yes));
		boolean isDurationLong = App.get(coughDuration).equals(
				getResources().getString(R.string.more_than_three_weeks))
				|| App.get(coughDuration).equals(
						getResources().getString(R.string.two_to_three_weeks));
		boolean hasProductiveCough = App.get(productiveCough).equals(
				getResources().getString(R.string.yes));
		boolean hasTbBefore = App.get(tbBefore).equals(
				getResources().getString(R.string.yes));
		boolean hasFever = App.get(fever).equals(
				getResources().getString(R.string.yes));
		boolean hasNightSweats = App.get(nightSweats).equals(
				getResources().getString(R.string.yes));
		boolean hasWeightLoss = App.get(weightLoss).equals(
				getResources().getString(R.string.yes));

		if (isCough && isDurationLong && (hasProductiveCough)
				|| (hasTbBefore || hasFever || hasNightSweats || hasWeightLoss)) {
			tbSuspect.setChecked(true);
			patientIdTextView.setEnabled(true);
			patientId.setEnabled(true);
			scanBarcode.setEnabled(true);
		}
	}

	/**
	 * Updates the DOB picker date
	 */
	private void updateDob() {
		// Calculate dob by subtracting age in days from dob object
		if (!"".equals(App.get(age))) {

		}
	}

	@Override
	public boolean validate() {
		boolean valid = true;
		StringBuilder message = new StringBuilder();
		// Validate mandatory controls
		View[] mandatory = { firstName, lastName, age, indexCaseId };
		for (View v : mandatory) {
			if (App.get(v).equals("")) {
				valid = false;
				message.append(v.getTag().toString() + ". ");
				((EditText) v).setHintTextColor(getResources().getColor(
						R.color.Red));
			}
		}
		if (tbSuspect.isChecked() && App.get(patientId).equals("")) {
			valid = false;
			message.append(patientId.getTag().toString() + ". ");
			patientId.setHintTextColor(getResources().getColor(R.color.Red));
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

			if (tbSuspect.isChecked()) {
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

			// Age range
			if (!App.get(age).equals("")) {
				int a = Integer.parseInt(App.get(age));
				if (a < 0 || a > 99) {
					valid = false;
					message.append(age.getTag().toString() + ": "
							+ getResources().getString(R.string.out_of_range)
							+ "\n");
				}
			}

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
		updateDob();
		if (validate()) {
			final ContentValues values = new ContentValues();
			values.put("formDate", App.getSqlDate(formDate));
			values.put("location", App.getLocation());
			values.put("firstName", App.get(firstName));
			values.put("lastName", App.get(lastName));
			values.put("age", App.get(age));
			// values.put ("dob", App.getSqlDate (dob));
			values.put("gender", male.isChecked() ? "M" : "F");

			values.put("patientId", App.get(patientId));
			final ArrayList<String[]> observations = new ArrayList<String[]>();

			observations.add(new String[] { "Contact Tracing Strategy",
					App.get(contactTracingStrategy) });
			observations.add(new String[] { "Visit Type", App.get(visitType) });
			observations.add(new String[] { "Screened Before",
					App.get(screenedBefore) });

			observations.add(new String[] { "Index Case ID",
					App.get(indexCaseId) });
			observations.add(new String[] { "Cough", App.get(cough) });
			if (cough.getSelectedItem().toString()
					.equals(getResources().getString(R.string.yes))) {
				observations.add(new String[] { "Cough Duration",
						App.get(coughDuration) });
				observations.add(new String[] { "Productive Cough",
						App.get(productiveCough) });
			}

			observations.add(new String[] { "Fever", App.get(fever) });
			observations.add(new String[] { "Night Sweats",
					App.get(nightSweats) });
			observations
					.add(new String[] { "Weight Loss", App.get(weightLoss) });
			observations.add(new String[] { "Lymph Node Swelling",
					App.get(lymphNodeSwelling) });
			observations.add(new String[] { "Joint Spine Swelling",
					App.get(jointSpineSwelling) });
			observations.add(new String[] { "Contact History",
					App.get(historyContactTb) });

			observations.add(new String[] { "TB Past", App.get(tbBefore) });

			if (tbBefore.getSelectedItem().toString()
					.equals(getResources().getString(R.string.yes))) {
				observations.add(new String[] { "TB Medication History",
						App.get(tbMedication) });
			}

			observations.add(new String[] { "Age Modifier", "Year(s)" });

			observations.add(new String[] { "Index Patient Type",
					App.get(indexPatientType) });
			if (!indexPatientType.getSelectedItem().toString()
					.equals(getResources().getString(R.string.tbr4pk_patient))) {
				if (!"".equals(App.get(indexDistrictTbNumber))) {
					observations.add(new String[] {
							"Index Case District TB Number",
							App.get(indexDistrictTbNumber) });
				}
				observations.add(new String[] { "Index Case Diagnosis",
						App.get(diagnosis) });
			}

			if (!tbSuspect.isChecked()) {
				values.put("reverseNonSuspect", "Adult Reverse Non Suspect");
				values.put("indexCaseId", App.get(indexCaseId));
			}

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
					if (tbSuspect.isChecked())
						result = serverService.saveAdultReverseContactTracing(
								FormType.ADULT_REVERSE_CONTACT_TRACING, values,
								observations.toArray(new String[][] {}));
					else
						result = serverService.saveNonSuspect(
								FormType.NON_SUSPECT, values);

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
						App.getAlertDialog(
								AdultReverseContactTracingActivity.this,
								AlertType.INFO,
								getResources().getString(R.string.inserted))
								.show();
						initView(views);
					} else {
						App.getAlertDialog(
								AdultReverseContactTracingActivity.this,
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
		} else if (requestCode == Barcode.BARCODE_RESULT_INDEX_ID) {
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
	}

	@Override
	public boolean onEditorAction(TextView v, int arg1, KeyEvent arg2) {
		if (v == age) {
			updateDisplay();
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.method.KeyListener#clearMetaKeyState(android.view.View,
	 * android.text.Editable, int)
	 */
	@Override
	public void clearMetaKeyState(View view, Editable content, int states) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.method.KeyListener#getInputType()
	 */
	@Override
	public int getInputType() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.method.KeyListener#onKeyDown(android.view.View,
	 * android.text.Editable, int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(View view, Editable text, int keyCode,
			KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.method.KeyListener#onKeyOther(android.view.View,
	 * android.text.Editable, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyOther(View view, Editable text, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.method.KeyListener#onKeyUp(android.view.View,
	 * android.text.Editable, int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
