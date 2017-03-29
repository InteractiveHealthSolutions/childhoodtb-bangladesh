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
import android.widget.DatePicker;
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
import java.util.Date;
import java.util.Locale;

public class PaediatricContactTracingActivity extends AbstractFragmentActivity
		implements KeyListener, OnEditorActionListener {
	// Views displayed in pages, sorted w.r.t. appearance on pager
	MyTextView formDateTextView;
	MyButton formDateButton;

	MyTextView indexPatientTypeTextView;
	MySpinner indexPatientType;

	MyTextView contactTracingStrategyTextView;
	MySpinner contactTracingStrategy;

	MyTextView screenedBeforeTextView;
	MySpinner screenedBefore;

	MyTextView visitTypeTextView;
	MySpinner visitType;

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
	MySpinner ageModifier;

	MyTextView dobTextView;
	DatePicker dobPicker;
	Calendar dob;

	MyTextView indexCaseIdTextView;
	MyEditText indexCaseId;

	MyTextView indexDistrictTbNumberTextView;
	MyEditText indexDistrictTbNumber;

	MyTextView diagnosisTextView;
	MySpinner diagnosis;

	MyTextView weightTextView;
	MyEditText weight;

	MyTextView weightPercentileTextView;
	MySpinner weightPercentile;

	MyTextView coughTextView;
	MySpinner cough;
	MyTextView coughDurationTextView;
	MySpinner coughDuration;

	MyTextView feverTextView;
	MySpinner fever;

	MyTextView nightSweatsTextView;
	MySpinner nightSweats;

	MyTextView weightLossTextView;
	MySpinner weightLoss;

	MyTextView contactAppetiteTextView;
	MySpinner contactAppetite;

	MyTextView lymphNodeSwellingTextView;
	MySpinner lymphNodeSwelling;

	MyTextView jointSpineSwellingTextView;
	MySpinner jointSpineSwelling;

	MyTextView historyContactTbTextView;
	MySpinner historyContactTb;

	MyTextView chestExaminationTextView;
	MySpinner chestExamination;

	MyTextView lymphNodeExaminationTextView;
	MySpinner lymphNodeExamination;

	MyTextView abdominalExaminationTextView;
	MySpinner abdominalExamination;

	MyTextView otherExaminationTextView;
	MyEditText otherExamination;

	MyTextView childBcgScarTextView;
	MySpinner childBcgScar;

	MyTextView adultFamilyMemberTBTextView;
	MySpinner adultFamilyMemberTB;

	MyTextView memberFamilyTBTextView;
	MySpinner memberFamilyTB;

	MyTextView memberTBFormTextView;
	MySpinner memberTBForm;

	MyTextView memberTBTypeTextView;
	MySpinner memberTBType;

	MyTextView confirmSputumSmearPositiveTBTextView;
	MySpinner confirmSputumSmearPositiveTB;

	MyTextView exposureScoreTextView;
	MyTextView exposureScore;

	MyTextView exposureScoreTextView1;
	MyTextView exposureScore1;

	MyTextView exposureScoreTextView2;
	MyTextView exposureScore2;

	MyTextView exposureScoreTextView3;
	MyTextView exposureScore3;

	MyTextView ppaScoreTextView;
	MyEditText ppaScore;

	MyTextView sourceCaseMotherTextView;
	MySpinner sourceCaseMother;

	MyTextView sourceCasePrimaryCareGiverTextView;
	MySpinner sourceCasePrimaryCareGiver;

	MyTextView sourceCaseSleepSameBedTextView;
	MySpinner sourceCaseSleepSameBed;

	MyTextView sourceCaseSleepSameRoomTextView;
	MySpinner sourceCaseSleepSameRoom;

	MyTextView sourceCaseLiveSameHouseholdTextView;
	MySpinner sourceCaseLiveSameHousehold;

	MyTextView sourceCaseSeeChildEverydayTextView;
	MySpinner sourceCaseSeeChildEveryday;

	MyTextView sourceCaseCoughingTextView;
	MySpinner sourceCaseCoughing;

	MyTextView sourceCaseHasPulmonaryTBTextView;
	MySpinner sourceCaseHasPulmonaryTB;

	MyTextView sourceCasePositiveSputumSmearTextView;
	MySpinner sourceCasePositiveSputumSmear;

	MyTextView moreThanOneSourceCaseTextView;
	MySpinner moreThanOneSourceCase;

	MyCheckBox tbSuspect;

	MyTextView patientIdTextView;
	MyEditText patientId;

	MyButton scanBarcodeIndexId;
	MyButton scanBarcode;
	MyButton validatePatientId;

	View[] sourceCaseQuestions;

	boolean isSuspect;

	/**
	 * Subclass representing Fragment for Pediatric-screeening suspect form
	 *
	 * @author owais.hussain@ihsinformatics.com
	 *
	 */
	@SuppressLint("ValidFragment")
	class PaediatricContactTracingFragment extends Fragment {
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
	class PaediatricContactTracingFragmentPagerAdapter extends
			FragmentPagerAdapter {
		/** Constructor of the class */
		public PaediatricContactTracingFragmentPagerAdapter(
				FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem(int arg0) {
			PaediatricContactTracingFragment fragment = new PaediatricContactTracingFragment();
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
		TAG = "PaediatricContactTracingActivity";
		PAGE_COUNT = 14;
		pager = (ViewPager) findViewById(R.template_id.pager);
		navigationSeekbar.setMax(PAGE_COUNT - 1);
		navigatorLayout = (LinearLayout) findViewById(R.template_id.navigatorLayout);
		// If the form consists only of single page, then hide the
		// navigatorLayout
		if (PAGE_COUNT < 2) {
			navigatorLayout.setVisibility(View.GONE);
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		PaediatricContactTracingFragmentPagerAdapter pagerAdapter = new PaediatricContactTracingFragmentPagerAdapter(
				fragmentManager);
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit(PAGE_COUNT);
		// Create views for pages

		// following (until dob ) defined in strings.xml

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
		ageModifier = new MySpinner(context, getResources().getStringArray(
				R.array.duration_modifiers), R.string.age, R.string.option_hint);
		dobTextView = new MyTextView(context, R.style.text, R.string.dob);
		dobPicker = new DatePicker(context);
		ArrayList<View> touchables = dobPicker.getTouchables();
		for (int i = 0; i < touchables.size(); i++) {
			if (i == 2 || i == 5 || i == 8)
				touchables.get(i).setBackgroundResource(
						R.drawable.numberpicker_down_normal);
			else if (i == 0 || i == 3 || i == 6)
				touchables.get(i).setBackgroundResource(
						R.drawable.numberpicker_up_normal);
			else
				touchables.get(i).setBackgroundResource(
						R.drawable.custom_button_beige);
		}
		dob = Calendar.getInstance();

		// following (until diagnosis) defined in rever_contact_tracing .xml
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

		weightTextView = new MyTextView(context, R.style.text, R.string.weight);
		weight = new MyEditText(context, R.string.weight, R.string.weight_hint,
				InputType.TYPE_CLASS_NUMBER, R.style.edit, 3, false);

		weightPercentileTextView = new MyTextView(context, R.style.text,
				R.string.weight_percentile);
		weightPercentile = new MySpinner(context, getResources()
				.getStringArray(R.array.weight_percentile_options),
				R.string.weight_percentile, R.string.option_hint);

		coughTextView = new MyTextView(context, R.style.text,
				R.string.contact_cough);
		cough = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.child_cough,
				R.string.option_hint);
		coughDurationTextView = new MyTextView(context, R.style.text,
				R.string.contact_cough_duration);
		coughDuration = new MySpinner(context, getResources().getStringArray(
				R.array.child_cough_durations),
				R.string.contact_cough_duration, R.string.option_hint);

		feverTextView = new MyTextView(context, R.style.text,
				R.string.contact_fever);
		fever = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.contact_fever,
				R.string.option_hint);

		nightSweatsTextView = new MyTextView(context, R.style.text,
				R.string.contact_night_sweats);
		nightSweats = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.contact_night_sweats,
				R.string.option_hint);

		weightLossTextView = new MyTextView(context, R.style.text,
				R.string.contact_weight_loss);
		weightLoss = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.contact_weight_loss,
				R.string.option_hint);

		contactAppetiteTextView = new MyTextView(context, R.style.text,
				R.string.contact_appetite);
		contactAppetite = new MySpinner(context, getResources().getStringArray(
				R.array.four_options_with_poor), R.string.contact_appetite,
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
				R.string.contact_history);
		historyContactTb = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.contact_history, R.string.option_hint);

		chestExaminationTextView = new MyTextView(context, R.style.text,
				R.string.chest_examination);
		chestExamination = new MySpinner(context, getResources()
				.getStringArray(R.array.chest_exam_options),
				R.string.chest_examination, R.string.option_hint);

		lymphNodeExaminationTextView = new MyTextView(context, R.style.text,
				R.string.lymph_node_exam);
		lymphNodeExamination = new MySpinner(context, getResources()
				.getStringArray(R.array.chest_exam_options),
				R.string.lymph_node_exam, R.string.option_hint);

		abdominalExaminationTextView = new MyTextView(context, R.style.text,
				R.string.abdominal_exam);
		abdominalExamination = new MySpinner(context, getResources()
				.getStringArray(R.array.chest_exam_options),
				R.string.abdominal_exam, R.string.option_hint);

		otherExaminationTextView = new MyTextView(context, R.style.text,
				R.string.other_exam);
		otherExamination = new MyEditText(context, R.string.other_exam,
				R.string.other_exam_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, 25, false);

		childBcgScarTextView = new MyTextView(context, R.style.text,
				R.string.child_bcg_scar);
		childBcgScar = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.child_bcg_scar,
				R.string.option_hint);

		adultFamilyMemberTBTextView = new MyTextView(context, R.style.text,
				R.string.adult_family_member_tb);
		adultFamilyMemberTB = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.adult_family_member_tb, R.string.option_hint);

		memberFamilyTBTextView = new MyTextView(context, R.style.text,
				R.string.member_family_tb);
		memberFamilyTB = new MySpinner(context, getResources().getStringArray(
				R.array.family_members), R.string.member_family_tb,
				R.string.option_hint);

		memberTBFormTextView = new MyTextView(context, R.style.text,
				R.string.member_tb_form);
		memberTBForm = new MySpinner(context, getResources().getStringArray(
				R.array.tb_forms), R.string.member_tb_form,
				R.string.option_hint);

		memberTBTypeTextView = new MyTextView(context, R.style.text,
				R.string.member_tb_type);
		memberTBType = new MySpinner(context, getResources().getStringArray(
				R.array.tb_types), R.string.member_tb_type,
				R.string.option_hint);

		confirmSputumSmearPositiveTBTextView = new MyTextView(context,
				R.style.text, R.string.sputum_smear_positive_tb);
		confirmSputumSmearPositiveTB = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.sputum_smear_positive_tb, R.string.option_hint);

		exposureScoreTextView = new MyTextView(context, R.style.text,
				R.string.exposure_score);
		exposureScore = new MyTextView(context, R.style.text,
				R.string.exposure_value);
		// exposureScore = new MyEditText(context, R.string.exposure_score,
		// R.string.exposure_score_hint, InputType.TYPE_CLASS_NUMBER,
		// R.style.edit, 3, false);

		exposureScoreTextView1 = new MyTextView(context, R.style.text,
				R.string.exposure_score);
		exposureScore1 = new MyTextView(context, R.style.text,
				R.string.exposure_value);
		// exposureScore1 = new MyEditText(context, R.string.exposure_score,
		// R.string.exposure_score_hint, InputType.TYPE_CLASS_NUMBER,
		// R.style.edit, 3, false);

		exposureScoreTextView2 = new MyTextView(context, R.style.text,
				R.string.exposure_score);
		exposureScore2 = new MyTextView(context, R.style.text,
				R.string.exposure_value);
		// exposureScore2 = new MyEditText(context, R.string.exposure_score,
		// R.string.exposure_score_hint, InputType.TYPE_CLASS_NUMBER,
		// R.style.edit, 3, false);

		exposureScoreTextView3 = new MyTextView(context, R.style.text,
				R.string.exposure_score);
		exposureScore3 = new MyTextView(context, R.style.text,
				R.string.exposure_value);
		// exposureScore3 = new MyEditText(context, R.string.exposure_score,
		// R.string.exposure_score_hint, InputType.TYPE_CLASS_NUMBER,
		// R.style.edit, 3, false);

		ppaScoreTextView = new MyTextView(context, R.style.text,
				R.string.ppa_score);
		ppaScore = new MyEditText(context, R.string.ppa_score,
				R.string.ppa_score_hint, InputType.TYPE_CLASS_NUMBER,
				R.style.edit, 3, false);

		sourceCaseMotherTextView = new MyTextView(context, R.style.text,
				R.string.source_case_mother);
		sourceCaseMother = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.source_case_mother, R.string.option_hint);

		sourceCasePrimaryCareGiverTextView = new MyTextView(context,
				R.style.text, R.string.source_case_primary_care_giver);
		sourceCasePrimaryCareGiver = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.source_case_primary_care_giver, R.string.option_hint);

		sourceCaseSleepSameBedTextView = new MyTextView(context, R.style.text,
				R.string.source_case_sleep_same_bed);
		sourceCaseSleepSameBed = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.source_case_sleep_same_bed, R.string.option_hint);

		sourceCaseSleepSameRoomTextView = new MyTextView(context, R.style.text,
				R.string.source_case_sleep_same_room);
		sourceCaseSleepSameRoom = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.source_case_sleep_same_room, R.string.option_hint);

		sourceCaseLiveSameHouseholdTextView = new MyTextView(context,
				R.style.text, R.string.source_case_live_same_household);
		sourceCaseLiveSameHousehold = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.source_case_live_same_household, R.string.option_hint);

		sourceCaseSeeChildEverydayTextView = new MyTextView(context,
				R.style.text, R.string.source_case_see_child_everyday);
		sourceCaseSeeChildEveryday = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.source_case_see_child_everyday, R.string.option_hint);

		sourceCaseCoughingTextView = new MyTextView(context, R.style.text,
				R.string.source_case_coughing);
		sourceCaseCoughing = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.source_case_coughing, R.string.option_hint);

		sourceCaseHasPulmonaryTBTextView = new MyTextView(context,
				R.style.text, R.string.source_case_pulmonary_tb);
		sourceCaseHasPulmonaryTB = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.source_case_pulmonary_tb, R.string.option_hint);

		sourceCasePositiveSputumSmearTextView = new MyTextView(context,
				R.style.text, R.string.source_case_positive_sputum_smear);
		sourceCasePositiveSputumSmear = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.source_case_positive_sputum_smear,
				R.string.option_hint);

		moreThanOneSourceCaseTextView = new MyTextView(context, R.style.text,
				R.string.more_than_one_source_case);
		moreThanOneSourceCase = new MySpinner(context, getResources()
				.getStringArray(R.array.four_options),
				R.string.more_than_one_source_case, R.string.option_hint);

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

		sourceCaseQuestions = new View[] { sourceCaseMother,
				sourceCasePrimaryCareGiver, sourceCaseSleepSameBed,
				sourceCaseSleepSameRoom, sourceCaseLiveSameHousehold,
				sourceCaseSeeChildEveryday, sourceCaseCoughing,
				sourceCaseHasPulmonaryTB, sourceCasePositiveSputumSmear,
				moreThanOneSourceCase };

		// TODO: complete it and remove the fields from the Pediatiric Screening
		// forms

		View[][] viewGroups = {
				{ formDateTextView, formDateButton, indexPatientTypeTextView,
						indexPatientType, contactTracingStrategyTextView,
						contactTracingStrategy, visitTypeTextView, visitType,
						screenedBeforeTextView, screenedBefore,
						firstNameTextView, firstName, lastNameTextView,
						lastName },
				{ genderTextView, gender, ageTextView, age, ageModifier },
				{ dobTextView, dobPicker },
				{ indexCaseIdTextView, indexCaseId, scanBarcodeIndexId,
						validatePatientId, indexDistrictTbNumberTextView,
						indexDistrictTbNumber, diagnosisTextView, diagnosis,
						weightTextView, weight, weightPercentileTextView,
						weightPercentile },
				{ coughTextView, cough, coughDurationTextView, coughDuration,
						feverTextView, fever, nightSweatsTextView, nightSweats,
						weightLossTextView, weightLoss,
						contactAppetiteTextView, contactAppetite },
				{ lymphNodeSwellingTextView, lymphNodeSwelling,
						jointSpineSwellingTextView, jointSpineSwelling,
						historyContactTbTextView, historyContactTb,
						chestExaminationTextView, chestExamination },
				{ lymphNodeExaminationTextView, lymphNodeExamination,
						abdominalExaminationTextView, abdominalExamination,
						otherExaminationTextView, otherExamination },
				{ childBcgScarTextView, childBcgScar,
						adultFamilyMemberTBTextView, adultFamilyMemberTB,
						memberFamilyTBTextView, memberFamilyTB,
						memberTBFormTextView, memberTBForm },
				{ memberTBTypeTextView, memberTBType,
						confirmSputumSmearPositiveTBTextView,
						confirmSputumSmearPositiveTB },
				{ sourceCaseMotherTextView, sourceCaseMother,
						sourceCasePrimaryCareGiverTextView,
						sourceCasePrimaryCareGiver,
						sourceCaseSleepSameBedTextView, sourceCaseSleepSameBed,
						exposureScoreTextView, exposureScore },
				{ sourceCaseSleepSameRoomTextView, sourceCaseSleepSameRoom,
						sourceCaseLiveSameHouseholdTextView,
						sourceCaseLiveSameHousehold,
						sourceCaseSeeChildEverydayTextView,
						sourceCaseSeeChildEveryday, exposureScoreTextView1,
						exposureScore1 },
				{ sourceCaseCoughingTextView, sourceCaseCoughing,
						sourceCaseHasPulmonaryTBTextView,
						sourceCaseHasPulmonaryTB, exposureScoreTextView2,
						exposureScore2 },
				{ sourceCasePositiveSputumSmearTextView,
						sourceCasePositiveSputumSmear,
						moreThanOneSourceCaseTextView, moreThanOneSourceCase,
						exposureScoreTextView3, exposureScore3 },
				{ ppaScoreTextView, ppaScore, tbSuspect, patientIdTextView,
						patientId, scanBarcode } };

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
		views = new View[] { age, ageModifier, indexPatientType,
				contactTracingStrategy, visitType, screenedBefore, indexCaseId,
				indexDistrictTbNumber, diagnosis, weight, weightPercentile,
				cough, coughDuration, fever, nightSweats, weightLoss,
				contactAppetite, lymphNodeSwelling, jointSpineSwelling,
				historyContactTb, chestExamination, lymphNodeExamination,
				abdominalExamination, otherExamination, childBcgScar,
				adultFamilyMemberTB, memberFamilyTB, memberTBForm,
				memberTBType, confirmSputumSmearPositiveTB, exposureScore,
				sourceCaseMother, sourceCasePrimaryCareGiver,
				sourceCaseSleepSameBed, sourceCaseSleepSameRoom,
				sourceCaseLiveSameHousehold, sourceCaseSeeChildEveryday,
				sourceCaseCoughing, sourceCaseHasPulmonaryTB,
				sourceCasePositiveSputumSmear, moreThanOneSourceCase,
				firstName, lastName, tbSuspect, patientId, ppaScore };

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
		dob.setTime(new Date());
		male.setChecked(true);
		tbSuspect.setChecked(true); // always true; Always assign patient id as
									// even though the suspect was not
									// confirmed, he/she was initially a suspect
									// due to history of contact

		memberFamilyTBTextView.setEnabled(false);
		memberFamilyTB.setEnabled(false);
		memberTBFormTextView.setEnabled(false);
		memberTBForm.setEnabled(false);
		memberTBTypeTextView.setEnabled(false);
		memberTBType.setEnabled(false);
		confirmSputumSmearPositiveTBTextView.setEnabled(false);
		confirmSputumSmearPositiveTB.setEnabled(false);

		dobPicker.updateDate(dob.get(Calendar.YEAR), dob.get(Calendar.MONTH),
				dob.get(Calendar.DAY_OF_MONTH));
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
			final String indexPatientId = App.get(indexCaseId);
			final String[] conceptArray = { "Index Case Diagnosis",
					"Index Case District TB Number" };
			if (!indexPatientId.equals("")) {
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
								.getSpecificPatientObs(indexPatientId,
										conceptArray,
										FormType.REVERSE_CONTACT_TRACING);
						return patientObs;
					}

					@Override
					protected void onProgressUpdate(String... values) {
					};

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
									indexDistrictTbNumber
											.setTextColor(getResources()
													.getColor(R.color.Chocolate));

									indexDistrictTbNumber.setText(result[i][1]);
								}
							}
						} else {
							App.getAlertDialog(
									PaediatricContactTracingActivity.this,
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
		} else if (parent == fever) {
		} else if (parent == ageModifier) {
			if (!"".equals(App.get(age))) {
				updateDob();
			}
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
								PaediatricContactTracingActivity.this,
								MainMenuActivity.class);
						startActivity(mainMenuIntent);
					}
				};
				dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", listener);
				dialog.show();
				// return false;
			}
		} else if (parent == adultFamilyMemberTB) {
			memberFamilyTBTextView.setEnabled(visible);
			memberFamilyTB.setEnabled(visible);
			memberTBFormTextView.setEnabled(visible);
			memberTBForm.setEnabled(visible);
			memberTBTypeTextView.setEnabled(visible);
			memberTBType.setEnabled(visible);
			confirmSputumSmearPositiveTBTextView.setEnabled(visible);
			confirmSputumSmearPositiveTB.setEnabled(visible);
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

	}

	@Override
	public boolean onLongClick(View arg0) {
		return false;
	}

	@Override
	public void updateDisplay() {
		formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));
		tbSuspect.setChecked(true);

		int exposureScore = 0;

		for (int questionCounter = 0; questionCounter < sourceCaseQuestions.length; questionCounter++) {
			if (sourceCaseQuestions[questionCounter] != null) {
				if (App.get(sourceCaseQuestions[questionCounter]).equals("Yes")) {
					exposureScore++;
				}
			}
		}

		exposureScore1.setText(String.valueOf(exposureScore));
		this.exposureScore.setText(String.valueOf(exposureScore));
		exposureScore2.setText(String.valueOf(exposureScore));
		exposureScore3.setText(String.valueOf(exposureScore));
	}

	/**
	 * Updates the DOB picker date
	 */
	private void updateDob() {
		// Calculate dob by subtracting age in days from dob object
		if (!"".equals(App.get(age))) {
			int index = ageModifier.getSelectedItemPosition();
			int multiplier = index == 0 ? 1 : index == 1 ? 7 : index == 2 ? 30
					: index == 3 ? 365 : 0;
			int a = Integer.parseInt(App.get(age)) * multiplier;
			dob = Calendar.getInstance();
			dob.add(Calendar.DAY_OF_YEAR, -a);
			dobPicker.updateDate(dob.get(Calendar.YEAR),
					dob.get(Calendar.MONTH), dob.get(Calendar.DAY_OF_MONTH));
		}
	}

	@Override
	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		View[] mandatory = { firstName, lastName, age, indexCaseId,
				otherExamination, exposureScore, ppaScore };
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

				if (ageModifier.getSelectedItem().toString().equals("Year(s)")) {
					ageInDays = Integer.parseInt(App.get(age)) * 365;
				}
				if (ageModifier.getSelectedItem().toString().equals("Week(s)")) {
					ageInDays = Integer.parseInt(App.get(age)) * 7;
				}
				if (ageModifier.getSelectedItem().toString().equals("Month(s)")) {
					ageInDays = Integer.parseInt(App.get(age)) * 30;
				}
				if (ageModifier.getSelectedItem().toString().equals("Day(s)")) {
					ageInDays = Integer.parseInt(App.get(age));
				}

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

	@Override
	public boolean submit() {
		updateDob();
		if (validate()) {
			final ContentValues values = new ContentValues();
			values.put("formDate", App.getSqlDate(formDate));
			values.put("location", App.getLocation());
			values.put("firstName", App.get(firstName));
			values.put("lastName", App.get(lastName));
			// values.put ("age", ageInYears);
			values.put("dob", App.getSqlDate(dob));
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
			observations.add(new String[] { "Age", App.get(age) });
			observations.add(new String[] { "Age Modifier",
					App.get(ageModifier) });
			observations.add(new String[] { "Weight (kg)", App.get(weight) });
			observations.add(new String[] { "Weight Percentile",
					App.get(weightPercentile) });
			observations.add(new String[] { "Cough", App.get(cough) });
			if (cough.getSelectedItem().toString()
					.equals(getResources().getString(R.string.yes))) {
				observations.add(new String[] { "Cough Duration",
						App.get(coughDuration) });
			}

			observations.add(new String[] { "Fever", App.get(fever) });
			observations.add(new String[] { "Night Sweats",
					App.get(nightSweats) });
			observations
					.add(new String[] { "Weight Loss", App.get(weightLoss) });
			observations.add(new String[] { "Child Appetite",
					App.get(contactAppetite) });
			observations.add(new String[] { "Lymph Node Swelling",
					App.get(lymphNodeSwelling) });
			observations.add(new String[] { "Joint Spine Swelling",
					App.get(jointSpineSwelling) });
			observations.add(new String[] { "Contact History",
					App.get(historyContactTb) });
			observations.add(new String[] { "Chest Examination",
					App.get(chestExamination) });
			observations.add(new String[] { "Lymph Node Examination",
					App.get(lymphNodeExamination) });
			observations.add(new String[] { "Abdominal Examination",
					App.get(abdominalExamination) });
			observations.add(new String[] { "Other Examination",
					App.get(otherExamination) });
			observations
					.add(new String[] { "BCG Scar", App.get(childBcgScar) });
			observations.add(new String[] { "Adult Family Member TB",
					App.get(adultFamilyMemberTB) });

			if (adultFamilyMemberTB.getSelectedItem().toString()
					.equals(getResources().getString(R.string.yes))) {
				observations.add(new String[] { "Member TB",
						App.get(memberFamilyTB) });
				observations.add(new String[] { "Family TB Form",
						App.get(memberTBForm) });
				observations.add(new String[] { "Family TB Type",
						App.get(memberTBType) });
				observations.add(new String[] { "Sputum Smear Positive TB",
						App.get(confirmSputumSmearPositiveTB) });
			}
			observations.add(new String[] { "Exposure Score",
					App.get(exposureScore) });
			observations.add(new String[] { "Source Case Child Mother",
					App.get(sourceCaseMother) });
			observations.add(new String[] { "Source Primary Care Giver",
					App.get(sourceCasePrimaryCareGiver) });
			observations.add(new String[] { "Source Case Seeing Child",
					App.get(sourceCaseSeeChildEveryday) });
			observations.add(new String[] { "Source Case Living",
					App.get(sourceCaseLiveSameHousehold) });
			observations.add(new String[] { "Source Case Sleep Room",
					App.get(sourceCaseSleepSameRoom) });
			observations.add(new String[] { "Source Case Sleep",
					App.get(sourceCaseSleepSameBed) });
			observations.add(new String[] { "Source Case Cough",
					App.get(sourceCaseCoughing) });
			observations.add(new String[] { "Source Case Pulmonary TB",
					App.get(sourceCaseHasPulmonaryTB) });
			observations.add(new String[] {
					"Source Case Positive Sputum Smear",
					App.get(sourceCasePositiveSputumSmear) });
			observations.add(new String[] { "More Source Cases",
					App.get(moreThanOneSourceCase) });
			observations.add(new String[] { "PPA Score", App.get(ppaScore) });
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
			observations.add(new String[] { "TB Suspect",
					tbSuspect.isChecked() ? "Yes" : "No" });

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
						result = serverService.savePaediatricContactTracing(
								FormType.PAEDIATRIC_CONTACT_TRACING, values,
								observations.toArray(new String[][] {}));
					else
						result = serverService.saveNonSuspect(
								FormType.NON_SUSPECT, values);

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
						App.getAlertDialog(
								PaediatricContactTracingActivity.this,
								AlertType.INFO,
								getResources().getString(R.string.inserted))
								.show();
						initView(views);
					} else {
						App.getAlertDialog(
								PaediatricContactTracingActivity.this,
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
	};

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
