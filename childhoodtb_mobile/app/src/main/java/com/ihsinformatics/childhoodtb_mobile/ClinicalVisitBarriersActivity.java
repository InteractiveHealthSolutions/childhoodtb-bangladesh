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

public class ClinicalVisitBarriersActivity extends AbstractFragmentActivity
		implements OnEditorActionListener {
	// Views displayed in pages, sorted w.r.t. appearance on pager
	MyTextView formDateTextView;
	MyButton formDateButton;

	MyTextView contactTracingStrategyTextView;
	MySpinner contactTracingStrategy;

	MyTextView typeOfVisitTextView;
	MySpinner typeOfVisit;

	MyTextView indexCaseIdTextView;
	MyEditText indexCaseId;

	MyButton scanBarcode;
	MyButton validateIndexCaseId;

	MyTextView indexDistrictTbNumberTextView;
	MyEditText indexDistrictTbNumber;

	MyTextView familyScreeningTextView;
	MySpinner familyScreening;

	MyTextView unableToBringFamilyTextView;
	MySpinner unableToBringFamily;

	MyTextView othersTextView;
	MyEditText others;

	/**
	 * Subclass representing Fragment for ClinicalVisitBarriers form
	 *
	 * @author owais.hussain@ihsinformatics.com
	 *
	 */
	@SuppressLint("ValidFragment")
	class ClinicalVisitBarriersFragment extends Fragment {
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
	class ClinicalVisitBarriersFragmentPagerAdapter extends
			FragmentPagerAdapter {
		/** Constructor of the class */
		public ClinicalVisitBarriersFragmentPagerAdapter(
				FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem(int arg0) {
			ClinicalVisitBarriersFragment fragment = new ClinicalVisitBarriersFragment();
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
		TAG = "ClinicalVisitBarriersActivity";
		PAGE_COUNT = 2;
		pager = (ViewPager) findViewById(R.template_id.pager);
		navigationSeekbar.setMax(PAGE_COUNT - 1);
		navigatorLayout = (LinearLayout) findViewById(R.template_id.navigatorLayout);
		// If the form consists only of single page, then hide the
		// navigatorLayout
		if (PAGE_COUNT < 2) {
			navigatorLayout.setVisibility(View.GONE);
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		ClinicalVisitBarriersFragmentPagerAdapter pagerAdapter = new ClinicalVisitBarriersFragmentPagerAdapter(
				fragmentManager);
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit(PAGE_COUNT);
		// Create views for pages
		formDateTextView = new MyTextView(context, R.style.text,
				R.string.form_date);
		formDateButton = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_date,
				R.string.form_date);

		contactTracingStrategyTextView = new MyTextView(context, R.style.text,
				R.string.contact_tracing_strategy);
		contactTracingStrategy = new MySpinner(context, getResources()
				.getStringArray(R.array.tracing_strategies),
				R.string.contact_tracing_strategy, R.string.option_hint);

		typeOfVisitTextView = new MyTextView(context, R.style.text,
				R.string.visit_type);
		typeOfVisit = new MySpinner(context, getResources().getStringArray(
				R.array.visit_type_options), R.string.visit_type,
				R.string.option_hint);

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

		scanBarcode = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.scan_barcode,
				R.string.scan_barcode);

		validateIndexCaseId = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.validateID,
				R.string.validateID);

		familyScreeningTextView = new MyTextView(context, R.style.text,
				R.string.family_screening);
		familyScreening = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.family_screening,
				R.string.option_hint);

		unableToBringFamilyTextView = new MyTextView(context, R.style.text,
				R.string.unable_to_bring_family);
		unableToBringFamily = new MySpinner(context, getResources()
				.getStringArray(R.array.unable_to_bring_family_options),
				R.string.unable_to_bring_family, R.string.option_hint);

		othersTextView = new MyTextView(context, R.style.text, R.string.others);
		others = new MyEditText(context, R.string.others, R.string.others_hint,
				InputType.TYPE_CLASS_TEXT, R.style.edit, 100, false);

		View[][] viewGroups = {
				{ formDateTextView, formDateButton,
						contactTracingStrategyTextView, contactTracingStrategy,
						typeOfVisitTextView, typeOfVisit, indexCaseIdTextView,
						indexCaseId, scanBarcode, validateIndexCaseId,
						indexDistrictTbNumberTextView, indexDistrictTbNumber },
				{ familyScreeningTextView, familyScreening,
						unableToBringFamilyTextView, unableToBringFamily,
						othersTextView, others } };

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
		validateIndexCaseId.setOnClickListener(this);
		navigationSeekbar.setOnSeekBarChangeListener(this);

		views = new View[] { contactTracingStrategy, typeOfVisit, indexCaseId,
				indexDistrictTbNumber, familyScreening, unableToBringFamily,
				others };

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
		othersTextView.setEnabled(false);
		others.setEnabled(false);
		indexDistrictTbNumber.setEnabled(false);
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
		} else if (view == validateIndexCaseId) {
			final String patId = App.get(indexCaseId);
			final String[] conceptArray = { "Index Case District TB Number" };
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

						// TODO: check this
						indexDistrictTbNumber.setText("");

						if (result != null && result.length > 0) {
							saveButton.setEnabled(true);
							for (int i = 0; i < result.length; i++) {
								indexDistrictTbNumber
										.setTextColor(getResources().getColor(
												R.color.Chocolate));
								indexDistrictTbNumber.setText(result[i][1]);
							}
						} else {
							saveButton.setEnabled(false);
							App.getAlertDialog(
									ClinicalVisitBarriersActivity.this,
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
		boolean visible = spinner.getSelectedItemPosition() == 6;

		if (parent == unableToBringFamily) {
			othersTextView.setEnabled(visible);
			others.setEnabled(visible);
			((TextView) parent.getChildAt(0)).setTextSize(19);
			// ((TextView) parent.getChildAt(4)).setTextSize(5);
		}

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

	}

	@Override
	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		View[] mandatory = { indexCaseId, indexDistrictTbNumber };
		for (View v : mandatory) {
			if (App.get(v).equals("")) {
				valid = false;
				message.append(v.getTag().toString() + ". ");
				((EditText) v).setHintTextColor(getResources().getColor(
						R.color.Red));
			}
		}

		if (unableToBringFamily.getSelectedItem().toString()
				.equals("Other Reason")
				&& App.get(others).equals("")) {
			valid = false;
			message.append(others.getTag().toString() + ". ");
			others.setHintTextColor(getResources().getColor(R.color.Red));

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

			observations.add(new String[] { "Contact Tracing Strategy",
					App.get(contactTracingStrategy) });
			observations
					.add(new String[] { "Visit Type", App.get(typeOfVisit) });

			observations.add(new String[] { "Index Case District TB Number",
					App.get(indexDistrictTbNumber) });
			// observations.add(new String[] { "Index Case Diagnosis",
			// App.get(diagnosis) });

			observations.add(new String[] { "Family Screening",
					App.get(familyScreening) });
			observations.add(new String[] {
					"Family Unavailability for Screening",
					App.get(unableToBringFamily) });

			if (unableToBringFamily.getSelectedItem().toString()
					.equals("Other Reason")) {
				observations
						.add(new String[] { "Other Reason", App.get(others) });
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
					result = serverService.saveReverseContact(
							FormType.CLINICAL_VISIT_BARRIERS, values,
							observations.toArray(new String[][] {}));

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
						App.getAlertDialog(ClinicalVisitBarriersActivity.this,
								AlertType.INFO,
								getResources().getString(R.string.inserted))
								.show();
						initView(views);
					} else {
						App.getAlertDialog(ClinicalVisitBarriersActivity.this,
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
