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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.ihsinformatics.childhoodtb_mobile.custom.MyButton;
import com.ihsinformatics.childhoodtb_mobile.custom.MyCheckBox;
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
import java.util.Date;
import java.util.Locale;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class CustomerInfoActivity extends AbstractFragmentActivity {
	static final int GET_PATIENT_ID = 1;
	// Views displayed in pages, sorted w.r.t. appearance on pager
	MyTextView formDateTextView;
	MyButton formDateButton;

	MyCheckBox contactProvided;
	MyTextView phone1TextView;
	MyEditText phone1;
	MyCheckBox phone1Owned;
	MyEditText phone1OwnerName;
	MyTextView phone2TextView;
	MyEditText phone2;
	MyCheckBox phone2Owned;
	MyEditText phone2OwnerName;

	MyTextView addressTextView;
	MyEditText address1;
	MyTextView townTextView;
	MySpinner town;

	MyTextView patientIdTextView;
	MyEditText patientId;
	MyButton scanBarcode;

	/**
	 * Subclass representing Fragment for customer info form
	 *
	 * @author owais.hussain@irdresearch.org
	 *
	 */
	@SuppressLint("ValidFragment")
	class CustomerInfoFragment extends Fragment {
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
			return null;
		}
	}

	/**
	 * Subclass for Pager Adapter. Uses FeedbackFragment subclass as items
	 *
	 * @author owais.hussain@irdresearch.org
	 *
	 */
	class CustomerInfoFragmentPagerAdapter extends FragmentPagerAdapter {
		/** Constructor of the class */
		public CustomerInfoFragmentPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem(int arg0) {
			CustomerInfoFragment fragment = new CustomerInfoFragment();
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
	public void createViews(final Context context) {
		TAG = "CustomerInfoActivity";
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
		CustomerInfoFragmentPagerAdapter pagerAdapter = new CustomerInfoFragmentPagerAdapter(
				fragmentManager);
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit(PAGE_COUNT);
		// Create views for pages
		formDateTextView = new MyTextView(context, R.style.text,
				R.string.form_date);
		formDateButton = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_date,
				R.string.form_date);

		contactProvided = new MyCheckBox(context, R.string.contact_provided,
				R.style.edit, R.string.contact_provided, true);
		phone1TextView = new MyTextView(context, R.style.text,
				R.string.primary_phone);
		phone1 = new MyEditText(context, R.string.primary_phone,
				R.string.primary_phone_hint, InputType.TYPE_CLASS_PHONE,
				R.style.edit, 20, false);
		phone1Owned = new MyCheckBox(context, R.string.self_owned,
				R.style.edit, R.string.self_owned, true);
		phone1OwnerName = new MyEditText(context, R.string.owner_name,
				R.string.owner_name_hint,
				InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 50,
				false);
		phone2TextView = new MyTextView(context, R.style.text,
				R.string.secondary_phone);
		phone2 = new MyEditText(context, R.string.secondary_phone,
				R.string.secondary_phone_hint, InputType.TYPE_CLASS_PHONE,
				R.style.edit, 20, false);
		phone2Owned = new MyCheckBox(context, R.string.self_owned,
				R.style.edit, R.string.self_owned, false);
		phone2OwnerName = new MyEditText(context, R.string.owner_name,
				R.string.owner_name_hint,
				InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 50,
				false);

		addressTextView = new MyTextView(context, R.style.text,
				R.string.address);
		address1 = new MyEditText(context, R.string.colony,
				R.string.colony_hint, InputType.TYPE_CLASS_TEXT, R.style.edit,
				0, false);
		townTextView = new MyTextView(context, R.style.text, R.string.town);
		town = new MySpinner(context, getResources().getStringArray(
				R.array.towns), R.string.town, R.string.option_hint);

		patientIdTextView = new MyTextView(context, R.style.text,
				R.string.patient_id);
		patientId = new MyEditText(context, R.string.patient_id,
				R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, RegexUtil.idLength, false);
		patientId.setCompoundDrawables(
				getResources().getDrawable(R.drawable.barcode), null, null,
				null);
		scanBarcode = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.scan_barcode,
				R.string.scan_barcode);
		View[][] viewGroups = {
				{ formDateTextView, formDateButton, patientIdTextView,
						patientId, scanBarcode },
				{ contactProvided, phone1TextView, phone1, phone1Owned,
						phone1OwnerName },
				{ phone2TextView, phone2, phone2Owned, phone2OwnerName },
				{ addressTextView, address1, townTextView, town } };
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
		views = new View[] { contactProvided, phone1, phone1Owned,
				phone1OwnerName, phone2, phone2Owned, phone2OwnerName,
				address1, patientId };
		for (View v : views) {
			if (v instanceof Spinner) {
				((Spinner) v).setOnItemSelectedListener(this);
			} else if (v instanceof CheckBox) {
				((CheckBox) v).setOnCheckedChangeListener(this);
			}
		}
		pager.setOnPageChangeListener(this);
		patientId.setOnLongClickListener(this);
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
		contactProvided.setChecked(true);
		phone1Owned.setChecked(true);
		phone2Owned.setChecked(false);
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
		View[] mandatory = { address1, town, patientId };
		for (View v : mandatory) {
			if (App.get(v).equals("")) {
				valid = false;
				message.append(v.getTag() + ". ");
				((EditText) v).setHintTextColor(getResources().getColor(
						R.color.Red));
			}
		}
		if (contactProvided.isChecked() && App.get(phone1).equals("")) {
			valid = false;
			message.append(phone1.getTag() + ". ");
			phone1.setHintTextColor(getResources().getColor(R.color.Red));
		}
		if (!valid) {
			message.append(getResources().getString(R.string.empty_data) + "\n");
		}
		// Validate data
		if (valid) {
			if (!RegexUtil.isValidId(App.get(patientId))) {
				valid = false;
				message.append(patientId.getTag() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				patientId.setTextColor(getResources().getColor(R.color.Red));
			}
			if (!App.get(phone1).equals("")) {
				if (!RegexUtil.isContactNumber(App.get(phone1))) {
					valid = false;
					message.append(phone1.getTag() + ": "
							+ getResources().getString(R.string.invalid_data)
							+ "\n");
					phone1.setTextColor(getResources().getColor(R.color.Red));
				}
			}
			if (!App.get(phone2).equals("")) {
				if (!RegexUtil.isContactNumber(App.get(phone2))) {
					valid = false;
					message.append(phone2.getTag() + ": "
							+ getResources().getString(R.string.invalid_data)
							+ "\n");
					phone2.setTextColor(getResources().getColor(R.color.Red));
				}
			}
		}
		// Validate range
		if (formDate.getTime().after(new Date())) {
			valid = false;
			message.append(formDateButton.getTag() + ": "
					+ getResources().getString(R.string.invalid_date_or_time)
					+ "\n");
		}
		if (!valid) {
			App.getAlertDialog(this, AlertType.ERROR, message.toString())
					.show();
		}
		return valid;
	}

	public boolean submit() {
		if (validate()) {
			final ContentValues values = new ContentValues();
			values.put("formDate", App.getSqlDate(formDate));
			values.put("location", App.getLocation());
			values.put("patientId", App.get(patientId));
			if (contactProvided.isChecked()) {
				values.put("phone1", App.get(phone1));
				if (!phone1Owned.isChecked()) {
					values.put("phone1Owner", App.get(phone1OwnerName));
				}
				values.put("phone2", App.get(phone2));
				if (!phone2Owned.isChecked()) {
					values.put("phone2Owner", App.get(phone2OwnerName));
				}
			}
			values.put("address1", App.get(address1));
			values.put("town", App.get(town));
			values.put("city", App.getCity());
			values.put("country", App.getCountry());
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
					String result = serverService.saveCustomerInfo(
							FormType.CUSTOMER_INFO, values);
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
						App.getAlertDialog(CustomerInfoActivity.this,
								AlertType.INFO,
								getResources().getString(R.string.inserted))
								.show();
						initView(views);
					} else {
						App.getAlertDialog(CustomerInfoActivity.this,
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
		// Retrieve barcode scan results or Search for ID
		if (requestCode == Barcode.BARCODE_RESULT
				|| requestCode == GET_PATIENT_ID) {
			if (resultCode == RESULT_OK) {
				String str = "";
				if (requestCode == Barcode.BARCODE_RESULT)
					str = data.getStringExtra(Barcode.SCAN_RESULT);
				else if (requestCode == GET_PATIENT_ID)
					// str = data.getStringExtra
					// (PatientSearchActivity.SEARCH_RESULT);
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
	public void onCheckedChanged(CompoundButton button, boolean state) {
		if (button == contactProvided) {
			phone1TextView.setEnabled(button.isChecked());
			phone1Owned.setEnabled(button.isChecked());
			phone1.setEnabled(button.isChecked());
			phone2TextView.setEnabled(button.isChecked());
			phone2Owned.setEnabled(button.isChecked());
			phone2.setEnabled(button.isChecked());
		} else if (button == phone1Owned) {
			if (button.isChecked())
				phone1OwnerName.setEnabled(false);
			else
				phone1OwnerName.setEnabled(true);
		} else if (button == phone2Owned) {
			if (button.isChecked())
				phone2OwnerName.setEnabled(false);
			else
				phone2OwnerName.setEnabled(true);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// Not implemented
	}

	@Override
	public boolean onLongClick(View view) {
		// Not implemented
		return false;
	}
}
