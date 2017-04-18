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
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
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


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class PediatricScreeningActivity extends AbstractFragmentActivity
        implements OnEditorActionListener {
    // Views displayed in pages, sorted w.r.t. appearance on pager
    MyTextView formDateTextView;
    MyButton formDateButton;

    //Screening  facility
    MyTextView screenFacilityText;
    MySpinner screenFacilityOptions;


    MyTextView screenedBeforeTextView;
    MySpinner screenedBefore;

    MyTextView screeningTypeTextView;
    MySpinner screeningType;

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

    MyTextView childAppetiteTextView;
    MySpinner childAppetite;

    MyTextView positiveReportTextView;
    MySpinner positiveReport;

    MyTextView lymphNodeSwellingTextView;
    MySpinner lymphNodeSwelling;

    MyTextView jointSpineSwellingTextView;
    MySpinner jointSpineSwelling;

    MyTextView contactHistoryTextView;
    MySpinner contactHistory;

    MyTextView tbSuspectTextView;
    MyCheckBox tbSuspect;

    MyTextView patientIdTextView;
    MyEditText patientId;

    MyButton scanBarcode;

    boolean isSuspect;

    /**
     * Subclass representing Fragment for Pediatric-screeening suspect form
     *
     * @author owais.hussain@ihsinformatics.com
     */
    @SuppressLint("ValidFragment")
    class PediatricScreeningSuspectFragment extends Fragment {
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
     */
    class PediatricScreeningSuspectFragmentPagerAdapter extends
            FragmentPagerAdapter {
        /**
         * Constructor of the class
         */
        public PediatricScreeningSuspectFragmentPagerAdapter(
                FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        /**
         * This method will be invoked when a page is requested to create
         */
        @Override
        public Fragment getItem(int arg0) {
            PediatricScreeningSuspectFragment fragment = new PediatricScreeningSuspectFragment();
            Bundle data = new Bundle();
            data.putInt("current_page", arg0 + 1);
            fragment.setArguments(data);
            return fragment;
        }

        /**
         * Returns the number of pages
         */
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

    @Override
    public void createViews(Context context) {
        TAG = "PaediatricScreeningActivity";
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
        PediatricScreeningSuspectFragmentPagerAdapter pagerAdapter = new PediatricScreeningSuspectFragmentPagerAdapter(
                fragmentManager);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(PAGE_COUNT);
        // Create views for pages
        formDateTextView = new MyTextView(context, R.style.text,
                R.string.form_date);

        formDateButton = new MyButton(context, R.style.button,
                R.drawable.custom_button_beige, R.string.form_date,
                R.string.form_date);

        screenedBeforeTextView = new MyTextView(context, R.style.text,
                R.string.screened_before);
        screenedBefore = new MySpinner(context, getResources().getStringArray(
                R.array.four_options), R.string.screened_before,
                R.string.option_hint);
  /*
        screeningTypeTextView = new MyTextView(context, R.style.text,
                R.string.screening_type);*//*
        screeningType = new MySpinner(context, getResources().getStringArray(
                R.array.screening_types), R.string.screening_type,
                R.string.option_hint);*/

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
                new MyRadioButton[]{male, female}, R.string.gender,
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

        coughTextView = new MyTextView(context, R.style.text,
                R.string.child_cough);
        cough = new MySpinner(context, getResources().getStringArray(
                R.array.four_options), R.string.child_cough,
                R.string.option_hint);
        coughDurationTextView = new MyTextView(context, R.style.text,
                R.string.child_cough_duration);
        coughDuration = new MySpinner(context, getResources().getStringArray(
                R.array.child_cough_durations), R.string.child_cough_duration,
                R.string.option_hint);

        feverTextView = new MyTextView(context, R.style.text,
                R.string.child_fever);
        fever = new MySpinner(context, getResources().getStringArray(
                R.array.four_options), R.string.child_fever,
                R.string.option_hint);

        nightSweatsTextView = new MyTextView(context, R.style.text,
                R.string.child_night_sweats);
        nightSweats = new MySpinner(context, getResources().getStringArray(
                R.array.four_options), R.string.child_night_sweats,
                R.string.option_hint);

        weightLossTextView = new MyTextView(context, R.style.text,
                R.string.child_weight_loss);
        weightLoss = new MySpinner(context, getResources().getStringArray(
                R.array.four_options), R.string.child_weight_loss,
                R.string.option_hint);

        childAppetiteTextView = new MyTextView(context, R.style.text,
                R.string.child_appetite);
        childAppetite = new MySpinner(context, getResources().getStringArray(
                R.array.four_options_with_poor), R.string.child_appetite,
                R.string.option_hint);

        positiveReportTextView = new MyTextView(context, R.style.text,
                R.string.positive_report);
        positiveReport = new MySpinner(context, getResources().getStringArray(
                R.array.four_options), R.string.positive_report,
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

        contactHistoryTextView = new MyTextView(context, R.style.text,
                R.string.contact_history);
        contactHistory = new MySpinner(context, getResources().getStringArray(
                R.array.four_options), R.string.contact_history,
                R.string.option_hint);

        tbSuspect = new MyCheckBox(context, R.string.client_suspect,
                R.style.edit, R.string.client_suspect, false);
        tbSuspect.setClickable(false);

        patientIdTextView = new MyTextView(context, R.style.text,
                R.string.patient_id);
        patientId = new MyEditText(context, R.string.patient_id,
                R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, RegexUtil.idLength, false);
        scanBarcode = new MyButton(context, R.style.button,
                R.drawable.custom_button_beige, R.string.scan_barcode,
                R.string.scan_barcode);

        View[][] viewGroups = {
                {formDateTextView, formDateButton, screenFacilityText, screenFacilityOptions, screenedBeforeTextView,
                        screenedBefore
                },
                {genderTextView, gender, ageTextView, age, ageModifier, firstNameTextView, firstName, lastNameTextView,
                        lastName},
                {dobTextView, dobPicker},
                {coughTextView, cough, coughDurationTextView, coughDuration,
                        feverTextView, fever, nightSweatsTextView, nightSweats,
                        weightLossTextView, weightLoss},
                {childAppetiteTextView, childAppetite, positiveReportTextView,
                        positiveReport, lymphNodeSwellingTextView,
                        lymphNodeSwelling, jointSpineSwellingTextView,
                        jointSpineSwelling},
                {contactHistoryTextView, contactHistory, tbSuspect,
                        patientIdTextView, patientId, scanBarcode}};

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
        views = new View[]{age, ageModifier, screenedBefore, screeningType, screenFacilityOptions,
                cough, coughDuration, fever, nightSweats, weightLoss,
                childAppetite, positiveReport, lymphNodeSwelling,
                jointSpineSwelling, contactHistory, firstName, lastName,
                tbSuspect, patientId};

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
        patientIdTextView.setEnabled(false);
        patientId.setEnabled(false);
        scanBarcode.setEnabled(false);
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
                                PediatricScreeningActivity.this,
                                MainMenuActivity.class);
                        startActivity(mainMenuIntent);
                    }
                };
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", listener);
                dialog.show();
                // return false;
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
        scanBarcode.setEnabled(false);

        boolean hasContactHistory = App.get(contactHistory).equals(
                getResources().getString(R.string.yes));
        boolean isCough = App.get(cough).equals(
                getResources().getString(R.string.yes));
        boolean isDurationLong = App.get(coughDuration).equals(
                getResources().getString(R.string.more_than_three_weeks))
                | App.get(coughDuration).equals(
                getResources().getString(R.string.two_to_three_weeks));
        boolean hasfever = App.get(fever).equals(
                getResources().getString(R.string.yes));
        boolean hasNightSweats = App.get(nightSweats).equals(
                getResources().getString(R.string.yes));
        boolean hasWeightLoss = App.get(weightLoss).equals(
                getResources().getString(R.string.yes));
        boolean hasPoorAppetite = App.get(childAppetite).equals(
                getResources().getString(R.string.poor));

        boolean[] array = {hasfever, hasNightSweats, hasWeightLoss,
                hasPoorAppetite};
        int count = 0;

        if (hasContactHistory || (isCough && isDurationLong)) {
            tbSuspect.setChecked(true);
            patientIdTextView.setEnabled(true);
            patientId.setEnabled(true);
            scanBarcode.setEnabled(true);
        } else {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == true)
                    count++;
            }
            if (count >= 2) {
                tbSuspect.setChecked(true);
                patientIdTextView.setEnabled(true);
                patientId.setEnabled(true);
                scanBarcode.setEnabled(true);
            }
            count = 0;
        }

        // if(hasfever || hasNightSweats || hasWeightLoss)
        // {
        // tbSuspect.setChecked(true);
        // patientIdTextView.setEnabled (true);
        // patientId.setEnabled (true);
        // scanBarcode.setEnabled (true);
        // }
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
        View[] mandatory = {firstName, lastName, age};
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
                Log.i("test",""+App.getSqlDate(dob));
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
        Log.i("test2",""+App.getSqlDate(dob));
        if (validate()) {
            final ContentValues values = new ContentValues();
            values.put("formDate", App.getSqlDate(formDate));
            values.put("location", App.getLocation());
            values.put("firstName", App.get(firstName));
            values.put("lastName", App.get(lastName));
            //values.put ("age", ageInYears);
            values.put("f", App.getSqlDate(dob));
            values.put("gender", male.isChecked() ? "M" : "F");
            values.put("patientId", App.get(patientId));
            final ArrayList<String[]> observations = new ArrayList<String[]>();

            observations.add(new String[]{"Screened Before",
                    App.get(screenedBefore)});


           /* observations.add(new String[]{"Screening Type",
                    App.get(screeningType)});*/

            observations.add(new String[]{"Age", App.get(age)});
            observations.add(new String[]{"Age Modifier",
                    App.get(ageModifier)});
            observations.add(new String[]{"Cough", App.get(cough)});
            if (cough.getSelectedItem().toString()
                    .equals(getResources().getString(R.string.yes))) {
                observations.add(new String[]{"Cough Duration",
                        App.get(coughDuration)});
            }

            observations.add(new String[]{"Fever", App.get(fever)});
            observations.add(new String[]{"Night Sweats",
                    App.get(nightSweats)});
            observations
                    .add(new String[]{"Weight Loss", App.get(weightLoss)});
            observations.add(new String[]{"Contact History",
                    App.get(contactHistory)});
            observations.add(new String[]{"Child Appetite",
                    App.get(childAppetite)});
            observations.add(new String[]{"Positive Report Possession",
                    App.get(positiveReport)});
            observations.add(new String[]{"Lymph Node Swelling",
                    App.get(lymphNodeSwelling)});
            observations.add(new String[]{"Joint Spine Swelling",
                    App.get(jointSpineSwelling)});

            observations.add(new String[]{"TB Suspect",
                    tbSuspect.isChecked() ? "Yes" : "No"});

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
                        result = serverService.saveScreening(
                                FormType.PAEDIATRIC_SCREENING, values,
                                observations.toArray(new String[][]{}));
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
                    // TODO: check this method

                    super.onPostExecute(result);
                    loading.dismiss();
                    if (result.equals("SUCCESS")) {
                        App.getAlertDialog(PediatricScreeningActivity.this,
                                AlertType.INFO,
                                getResources().getString(R.string.inserted))
                                .show();
                        initView(views);
                    } else {
                        App.getAlertDialog(PediatricScreeningActivity.this,
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
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int arg1, KeyEvent arg2) {
        if (v == age) {
            updateDisplay();
        }
        return true;
    }

}
