package com.ihsinformatics.childhoodtb_mobile.ChildhoodTbActivities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ihsinformatics.childhoodtb_mobile.AbstractFragmentActivity;
import com.ihsinformatics.childhoodtb_mobile.App;
import com.ihsinformatics.childhoodtb_mobile.Barcode;
import com.ihsinformatics.childhoodtb_mobile.R;
import com.ihsinformatics.childhoodtb_mobile.custom.MyButton;
import com.ihsinformatics.childhoodtb_mobile.custom.MyEditText;
import com.ihsinformatics.childhoodtb_mobile.custom.MyRadioButton;
import com.ihsinformatics.childhoodtb_mobile.custom.MyRadioGroup;
import com.ihsinformatics.childhoodtb_mobile.custom.MySpinner;
import com.ihsinformatics.childhoodtb_mobile.custom.MyTextView;
import com.ihsinformatics.childhoodtb_mobile.model.Patient;
import com.ihsinformatics.childhoodtb_mobile.shared.AlertType;
import com.ihsinformatics.childhoodtb_mobile.shared.FormType;
import com.ihsinformatics.childhoodtb_mobile.util.RegexUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by Shujaat on 3/20/2017.
 */
public class PaedsPresumptiveConfirmationActivity extends AbstractFragmentActivity implements TextView.OnEditorActionListener {

    MyTextView formDateTextView, patientIdTextView,
            firstNameTextView, motherNameTextView, genderTextView,
            ageTextView, weightTextView, weightPercentileTextView,
            coughTextView, coughDurationTextView, feverTextView,
            nightSweatsTextView, weightLossTextView, poorAppetiteTextView, chestExaminationTextVew,
            lymphNodeExaminationTextView, abdominalExaminationTextView, otherExaminationTextView,
            bcgScarTextView, familyMemberTBTextView, adultFamilyMemberTBTextView,
            formOfTbTextView, typeOfTbTextView, testAdvisedTextView,
            probableDiagnosisTextView;


    EditText presumptiveFirstName, presumptiveMotherName, age,
            weight, patientId, otherExamination, weightPercentile;

    MyTextView ageModifierTextView;
    MySpinner ageModifier;

    MySpinner
            cough, coughDuration, fever, nightSweats, weightLoss,
            poorAppetite, chestExamination, lymphNodeExamination,
            abdominalExamination, bcgScar, familyMemberTB, adultFamilyMemberTB,
            formOfTb, typeOfTb, testAdvised, probableDiagnosis;


    MyButton formDateButton;
    MyButton validatePatientId;

    MyRadioGroup gender;
    MyRadioButton male, female;
    MyButton scanBarcode;
    String result = "";

    @Override
    public void createViews(Context context) {
        //this  piece of code is used for  hide the softKey from the screen initially ...
        PaedsPresumptiveConfirmationActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        pager = (ViewPager) findViewById(R.template_id.pager);
        TAG = "PaedsPresumptiveConfirmationActivity";
        //Initialize Views ..
        formDateTextView = new MyTextView(context, R.style.text, R.string.form_date);
        formDateButton = new MyButton(context, R.style.button,
                R.drawable.custom_button_beige, R.string.form_date,
                R.string.form_date);
        firstNameTextView = new MyTextView(context,
                R.style.text, R.string.presumptive_first_name);
        presumptiveFirstName = new MyEditText(context,
                R.string.first_name, R.string.first_name_hint,
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 25, false);
        motherNameTextView = new MyTextView(context,
                R.style.text, R.string.mother_name);
        presumptiveMotherName = new MyEditText(context,
                R.string.mother_name, R.string.mother_name_hint,
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 25, false);

        genderTextView = new MyTextView(context, R.style.text, R.string.gender);
        male = new MyRadioButton(context, R.string.male, R.style.radio,
                R.string.male);
        female = new MyRadioButton(context, R.string.female, R.style.radio,
                R.string.female);
        gender = new MyRadioGroup(context,
                new MyRadioButton[]{male, female}, R.string.gender,
                R.style.radio, App.isLanguageRTL());

        ageTextView = new MyTextView(context, R.style.text, R.string.age);
        age = new MyEditText(context, R.string.age,
                R.string.age_hint, InputType.TYPE_CLASS_NUMBER,
                R.style.edit, 2, false);

        weightTextView = new MyTextView(context,
                R.style.text, R.string.weight);
        weight = new MyEditText(context, R.string.weight,
                R.string.weight_hint, InputType.TYPE_CLASS_NUMBER,
                R.style.edit, 2, false);
        weightPercentileTextView = new MyTextView(context,
                R.style.text, R.string.weight_percentile);
        weightPercentile = new MyEditText(context, R.string.weight_percentile,
                R.string.weight_percentile_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, 50, false);

        coughTextView = new MyTextView(context,
                R.style.text, R.string.cough);
        cough = new MySpinner(context, getResources()
                .getStringArray(R.array.cough_options),
                R.string.weight_percentile, R.string.option_hint);
        coughDurationTextView = new MyTextView(context,
                R.style.text, R.string._cough_weeks);
        coughDuration = new MySpinner(context, getResources()
                .getStringArray(R.array.cough_durations),
                R.string._cough_weeks, R.string.option_hint);
        feverTextView = new MyTextView(context,
                R.style.text, R.string._fever);
        fever = new MySpinner(context,
                getResources().getStringArray(R.array.fever_options)
                , R.string._fever, R.string.option_hint);
        nightSweatsTextView = new MyTextView(context,
                R.style.text, R.string.night_sweats);
        nightSweats = new MySpinner(context,
                getResources().getStringArray(R.array.night_sweats_options)
                , R.string.night_sweats, R.string.option_hint
        );
        weightLossTextView = new MyTextView(context,
                R.style.text, R.string._weight);
        weightLoss = new MySpinner(context,
                getResources().getStringArray(R.array.weight_loss_options),
                R.string.weight_loss, R.string.option_hint);
        poorAppetiteTextView = new MyTextView(context,
                R.style.text, R.string.poor_appetite);
        poorAppetite = new MySpinner(context,
                getResources().getStringArray(R.array.appetite_decreased_options),
                R.string.poor_appetite, R.string.option_hint);
        chestExaminationTextVew = new MyTextView(context,
                R.style.text, R.string.chest_examination);
        chestExamination = new MySpinner(context,
                getResources().getStringArray(R.array.chest_examination_list),
                R.string.chest_examination, R.string.option_hint);
        lymphNodeExaminationTextView = new MyTextView(context,
                R.style.text, R.string.lymph_node_exam);
        lymphNodeExamination = new MySpinner(context,
                getResources().getStringArray(R.array.lymph_node_examination_list),
                R.string.lymph_node_exam, R.string.option_hint);
        abdominalExaminationTextView = new MyTextView(context,
                R.style.text, R.string.abdominal_exam);
        abdominalExamination = new MySpinner(context,
                getResources().getStringArray(R.array.abdominal_exam_list),
                R.string.abdominal_exam, R.string.option_hint);
        otherExaminationTextView = new MyTextView(context,
                R.style.text, R.string.other_exam);
        otherExamination = new MyEditText(context, R.string.other_exam,
                R.string.other_exam_hint, InputType.TYPE_TEXT_VARIATION_PERSON_NAME,
                R.style.edit, 25, false);
        bcgScarTextView = new MyTextView(context, R.style.text, R.string.child_bcg_scar);
        bcgScar = new MySpinner(context,
                getResources().getStringArray(R.array.bcg_scar_list)
                , R.string.child_bcg_scar, R.string.option_hint);
        familyMemberTBTextView = new MyTextView(context,
                R.style.text, R.string.member_family_tb);
        familyMemberTB = new MySpinner(context,
                getResources().getStringArray(R.array.family_members),
                R.string.member_family_tb, R.string.option_hint);

        adultFamilyMemberTBTextView = new MyTextView(context,
                R.style.text, R.string.adult_family_member_tb);
        adultFamilyMemberTB = new MySpinner(context,
                getResources().getStringArray(R.array.adult_tb_in_family_list),
                R.string.adult_family_member_tb, R.string.option_hint);
        formOfTbTextView = new MyTextView(context,
                R.style.text, R.string.form_of_tb);
        formOfTb = new MySpinner(context,
                getResources().getStringArray(R.array.form_of_tb_list),
                R.string.form_of_tb, R.string.option_hint);
        typeOfTbTextView = new MyTextView(context,
                R.style.text, R.string.type_of_tb);
        typeOfTb = new MySpinner(context,
                getResources().getStringArray(R.array.type_of_tb_list),
                R.string.type_of_tb, R.string.option_hint);
        testAdvisedTextView = new MyTextView(context,
                R.style.text, R.string.test_advised);
        testAdvised = new MySpinner(context,
                getResources().getStringArray(R.array.test_advised_list),
                R.string.test_advised, R.string.option_hint);
        probableDiagnosisTextView = new MyTextView(context,
                R.style.text, R.string.probable_diagnosis);
        probableDiagnosis = new MySpinner(context,
                getResources().getStringArray(R.array.probable_diagnosis_list),
                R.string.probable_diagnosis, R.string.option_hint);
        patientIdTextView = new MyTextView(context, R.style.text,
                R.string._patient_id);

        patientId = new MyEditText(context, R.string._patient_id,
                R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, RegexUtil.idLength, false);

        scanBarcode = new MyButton(context, R.style.button,
                R.drawable.custom_button_beige, R.string.scan_barcode,
                R.string.scan_barcode);
        validatePatientId = new MyButton(context, R.style.button,
                R.drawable.custom_button_beige, R.string.validate_patient_id,
                R.string.validate_patient_id);
        ageModifierTextView = new MyTextView(context, R.style.text, R.string.age_modifier);
        ageModifier = new MySpinner(context, getResources().getStringArray(
                R.array.age_modifier_options), R.string.age_modifier, R.string.option_hint);

        View[][] viewGroups = {
                {formDateTextView, formDateButton, patientIdTextView, patientId, scanBarcode, validatePatientId, firstNameTextView, presumptiveFirstName,
                        motherNameTextView, presumptiveMotherName, genderTextView, gender

                },
                {ageTextView, age, ageModifierTextView, ageModifier, weightTextView, weight, weightPercentileTextView, weightPercentile,
                        coughTextView, cough, coughDurationTextView, coughDuration
                },
                {feverTextView, fever, nightSweatsTextView, nightSweats, weightLossTextView, weightLoss, poorAppetiteTextView, poorAppetite,
                        chestExaminationTextVew, chestExamination, lymphNodeExaminationTextView, lymphNodeExamination
                },
                {abdominalExaminationTextView, abdominalExamination, otherExaminationTextView, otherExamination, bcgScarTextView, bcgScar,
                        adultFamilyMemberTBTextView, adultFamilyMemberTB, familyMemberTBTextView, familyMemberTB, formOfTbTextView, formOfTb

                },
                {typeOfTbTextView, typeOfTb, testAdvisedTextView, testAdvised,
                        probableDiagnosisTextView, probableDiagnosis
                }
        };

        // Create layouts and store in ArrayList
        groups = new ArrayList<ViewGroup>();
        Log.i("viewGroupLength", "" + viewGroups.length);
        for (int i = 0; i < viewGroups.length; i++) {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            for (int j = 0; j < viewGroups[i].length; j++) {
                layout.addView(viewGroups[i][j]);
            }
            ScrollView scrollView = new ScrollView(context);
            scrollView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            scrollView.addView(layout);
            groups.add(scrollView);
        }


        navigationSeekbar.setMax(groups.size() - 1);
        navigatorLayout = (LinearLayout) findViewById(R.template_id.navigatorLayout);
        // If the form consists only of single page, then hide the navigatorLayout
        if (groups.size() < 2) {
            navigatorLayout.setVisibility(View.GONE);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        PediatricPresumptveFragmentPagerAdapter pagerAdapter = new PediatricPresumptveFragmentPagerAdapter(
                fragmentManager, groups.size());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(groups.size());

        views = new View[]{
                presumptiveMotherName, presumptiveFirstName, age, weight,
                weightPercentile, cough, coughDuration, fever, patientId, nightSweats,
                weightLoss, abdominalExamination, bcgScar, adultFamilyMemberTB,
                formOfTb, typeOfTb, testAdvised, familyMemberTB, probableDiagnosis,
                otherExamination, ageModifier, poorAppetite, chestExamination

        };


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
    protected void onStart() {
        super.onStart();

        //Clicked Events...
        formDateButton.setOnClickListener(this);
        male.setChecked(true);
        firstButton.setOnClickListener(this);
        lastButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        scanBarcode.setOnClickListener(this);
        weightPercentile.setFocusable(false);
        presumptiveFirstName.setFocusable(false);
        validatePatientId.setOnClickListener(this);
        navigationSeekbar.setOnSeekBarChangeListener(this);

        //weight Text Watcher
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    if (male.isChecked()) {
                        weightPercentile.setText(serverService.getPercentile(App.get(age), "1", editable.toString()));
                    } else if (female.isChecked()) {
                        weightPercentile.setText(serverService.getPercentile(App.get(age), "2", editable.toString()));
                    }
                }
                updateDisplay();
            }
        });


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }

    @Override
    public void initView(View[] views) {
        super.initView(views);
        formDate = Calendar.getInstance();
        saveButton.setEnabled(false);
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
        } else if (view == validatePatientId) {
            final String indexPatientId = App.get(patientId);
            if (checkPatientId()) {

                AsyncTask<String, String, Object> getTask = new AsyncTask<String, String, Object>() {
                    @Override
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


                        ArrayList<Patient> response = serverService.getPatientInformation(indexPatientId);
                        return response;
                    }

                    @Override
                    protected void onProgressUpdate(String... values) {
                    }

                    @Override
                    protected void onPostExecute(Object result) {
                        super.onPostExecute(result);
                        loading.dismiss();

                        ArrayList<Patient> patientDetails = (ArrayList<Patient>) result;

                        StringBuilder errorMessage = new StringBuilder();
                        //Handle when internet is not connectivityInternet();
                        if (patientDetails == null) {
                            AlertDialog alertDialog = App.getAlertDialog(PaedsPresumptiveConfirmationActivity.this,
                                    AlertType.ERROR,
                                    getResources()
                                            .getString(R.string.data_connection_error));
                            alertDialog.setTitle(getResources().getString(
                                    R.string.error_title));
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                    new AlertDialog.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                        }
                                    });
                            alertDialog.show();
                        } else {
                            if (patientDetails.size() == 0 || patientDetails.isEmpty()) {
                                errorMessage.append(
                                        getResources().getString(R.string.patient_id_missing)
                                );
                                App.getAlertDialog(PaedsPresumptiveConfirmationActivity.this,
                                        AlertType.ERROR, errorMessage.toString()).show();
                                //clear the data ...
                                initView(views);

                            } else {

                                saveButton.setEnabled(true);
                                //here  we get the age modifier of same patient...
                                presumptiveFirstName.setText(patientDetails.get(0).getName());
                                presumptiveFirstName.setFocusable(false);
                                age.setText(Integer.toString(patientDetails.get(0).getAge()));
                                age.setFocusable(false);
                                male.setChecked(patientDetails.get(0).getGender().equals("M") ? true : false);
                                female.setChecked(patientDetails.get(0).getGender().equals("F") ? true : false);
                                //here we set the age modifier..
                                String[] testArray = getResources().getStringArray(R.array.age_modifier_options);
                                for (int i = 0; i < testArray.length; i++) {
                                    if (patientDetails.get(0).getAgeModifier().equals(testArray[i])) {
                                        ageModifier.setSelection(i);
                                        ageModifier.setEnabled(false);
                                    }
                                }
                            }
                        }//end else condition
                    }
                };
                getTask.execute("");

            }//end checkPatient id ..
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        MySpinner spinner = (MySpinner) adapterView;
        boolean visible = spinner.getSelectedItemPosition() == 0;
        if (adapterView == cough) {
            coughDurationTextView.setEnabled(visible);
            coughDuration.setEnabled(visible);
        }
        if (adapterView == adultFamilyMemberTB) {

            familyMemberTBTextView.setEnabled(visible);
            familyMemberTB.setEnabled(visible);
        }

    }

    @Override
    public boolean onLongClick(View view) {
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
        View[] mandatory = {presumptiveFirstName, presumptiveMotherName, age, weight, otherExamination};

        for (View v : mandatory) {
            if (App.get(v).equals("")) {
                valid = false;
                message.append(v.getTag().toString() + ". ");
                ((EditText) v).setHintTextColor(getResources().getColor(
                        R.color.Red));
            }
        }
        if (App.get(patientId).equals("")) {
            valid = false;
            message.append(patientId.getTag().toString() + ". ");
            patientId.setHintTextColor(getResources().getColor(R.color.Red));
        }
        if (!valid) {

            message.append(getResources().getString(R.string.empty_data) + "\n");
        }
        if (valid) {
            if (!RegexUtil.isWord(App.get(presumptiveFirstName))) {
                valid = false;
                message.append(presumptiveFirstName.getTag().toString() + ": "
                        + getResources().getString(R.string.invalid_data)
                        + "\n");
                presumptiveFirstName.setTextColor(getResources().getColor(R.color.Red));
            }
            if (!RegexUtil.isWord(App.get(presumptiveMotherName))) {
                valid = false;
                message.append(presumptiveMotherName.getTag().toString() + ": "
                        + getResources().getString(R.string.invalid_data)
                        + "\n");
                presumptiveMotherName.setTextColor(getResources().getColor(R.color.Red));
            }
        }
        ///here not check whether the Child is tb Suspected or not ....
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
        //check is the selected date and time is in future ...
        try {
            if (formDate.getTime().after(Calendar.getInstance().getTime())) {
                valid = false;
                message.append(formDateButton.getTag()
                        + ": "
                        + getResources().getString(
                        R.string.invalid_future_date) + "\n");
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

        if (validate()) {

            final ContentValues values = new ContentValues();
            values.put("formDate", App.getSqlDate(formDate));
            values.put("location", App.getLocation());
            values.put("motherName", App.get(presumptiveMotherName));
            values.put("patientId", App.get(patientId));

            String weightPercent = App.get(weightPercentile);
            Log.d(TAG, "submit: " + App.get(weightPercentile));

            final ArrayList<String[]> observations = new ArrayList<String[]>();

            observations.add(new String[]{"Weight",
                    App.get(weight)});
            if (weightPercent.equals(getResources().getString(
                    R.string.less_then_or_equal_5th_percentile))) {
                observations.add(new String[]{"Weight Percentile",
                        getResources().getString(R.string.five_Percentile_or_Less)});
            } else if (weightPercent.equals(getString(
                    R.string.six_less_then_or_equal_to_10th_percentile))) {
                observations.add(new String[]{"Weight Percentile",
                        getString(R.string.six_to_ten_percentile)});
            } else if (weightPercent.equals(getString(
                    R.string.eleven_less_then_or_equal_to_25th_percentile))) {
                observations.add(new String[]{"Weight Percentile",
                        getString(R.string.eleven_to_twenty_five_percentile)});
            } else if (weightPercent.equals(getString(
                    R.string.twentySix_less_then_or_equal_to_50th_percentile))) {
                observations.add(new String[]{"Weight Percentile",
                        getString(R.string.twenty_six_to_fifty_percentile)});
            } else if (weightPercent.equals(getString(
                    R.string.greater_then_50th_percentile))) {
                observations.add(new String[]{"Weight Percentile",
                        getString(R.string.more_then_fifty_percentile)});
            }
            observations.add(new String[]{"Fever",
                    App.get(fever).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(fever)});
            observations.add(new String[]{"Night Sweats",
                    App.get(nightSweats).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(nightSweats)});
            observations.add(new String[]{"Cough",
                    App.get(cough).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(cough)});
            if (cough.getSelectedItem().toString()
                    .equals(getResources().getString(R.string.yes))) {
                observations.add(new String[]{"Cough Duration",
                        App.get(coughDuration).equals(getResources().getString(R.string.do_not_know)) ?
                                getResources().getString(R.string.unknown) : App.get(coughDuration)});
            }
            observations.add(new String[]{"Weight Loss",
                    App.get(weightLoss).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(weightLoss)});
            observations.add(new String[]{"Poor Appetite",
                    App.get(poorAppetite).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(poorAppetite)});
            observations.add(new String[]{"Chest Examination",
                    App.get(chestExamination)});
            observations.add(new String[]{"Lymph Node Examination",
                    App.get(lymphNodeExamination)});
            observations.add(new String[]{"Abdominal Examination",
                    App.get(abdominalExamination)});
            observations.add(new String[]{"Other Examination",
                    App.get(otherExamination)});
            observations.add(new String[]{"BCG Scar",
                    App.get(bcgScar).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(bcgScar)});
            observations.add(new String[]{"Adult Family Member TB",
                    App.get(adultFamilyMemberTB).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(adultFamilyMemberTB)});
            if (App.get(adultFamilyMemberTB).equals(
                    getResources().getString(R.string.yes))) {
                observations.add(new String[]{"Member TB",
                        App.get(familyMemberTB)});
            }
            observations.add(new String[]{"Family TB Form",
                    App.get(formOfTb)});
            observations.add(new String[]{"Family TB Type",
                    App.get(typeOfTb)});
            observations.add(new String[]{"Test Advised",
                    App.get(testAdvised)});
            observations.add(new String[]{"Probable diagnosis",
                    App.get(probableDiagnosis)});

            ///Create the AsyncTask ()
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
                    ///insertPaediatricScreenForm method use to Server call and also use for makign the JsonObject..
                    result = serverService.insertPaedsPresumptiveConfirmationForm(
                            FormType.PAEDS_PRESUMPTIVE_CONFIRMATION, values,
                            observations.toArray(new String[][]{}));

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
                        App.getAlertDialog(PaedsPresumptiveConfirmationActivity.this,
                                AlertType.INFO,
                                getResources().getString(R.string.inserted))
                                .show();
                        initView(views);
                    } else {
                        App.getAlertDialog(PaedsPresumptiveConfirmationActivity.this,
                                AlertType.ERROR, result).show();
                    }
                }
            };

            updateTask.execute("");

        }
        return true;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return true;
    }

    ///Barcode Scanner Result ....
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Retrieve barcode scan results
        if (requestCode == Barcode.BARCODE_RESULT) {
            if (resultCode == RESULT_OK) {
                String str = data.getStringExtra(Barcode.SCAN_RESULT);
                // Check for valid Id
                if (RegexUtil.isValidId(str) && !RegexUtil.isNumeric(str, false)) {
                    patientId.setText(str);
                } else {
                    App.getAlertDialog(this, AlertType.ERROR, patientId.getTag().toString()
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

    //validate the checkPatientId();
    public boolean checkPatientId() {
        boolean isIndexId = true;
        final String indexPatientId = App.get(patientId);
        if (!indexPatientId.equals("")) {
            if (RegexUtil.matchId(App.get(patientId))) {

                if (!RegexUtil.isValidId(App.get(patientId))) {
                    isIndexId = false;
                    App.getAlertDialog(PaedsPresumptiveConfirmationActivity.this,
                            AlertType.ERROR, patientId.getTag().toString() + ":"
                                    + getResources().getString(R.string.invalid_data)).show();

                    patientId.setTextColor(getResources().getColor(
                            R.color.Red));
                }
            } else {
                isIndexId = false;
                App.getAlertDialog(PaedsPresumptiveConfirmationActivity.this,
                        AlertType.ERROR, patientId.getTag().toString() + ":"
                                + getResources().getString(R.string.invalid_data)).show();

                patientId.setTextColor(getResources().getColor(
                        R.color.Red));
            }
        } else {
            isIndexId = false;
            App.getAlertDialog(PaedsPresumptiveConfirmationActivity.this,
                    AlertType.ERROR, getResources().getString(R.string.empty_data_indexId)).show();

        }
        return isIndexId;
    }

    @SuppressLint("ValidFragment")
    class PediatricPresumptiveFragment extends Fragment {
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

    class PediatricPresumptveFragmentPagerAdapter extends
            FragmentPagerAdapter {
        int pageCount;

        public PediatricPresumptveFragmentPagerAdapter(
                FragmentManager fragmentManager, int pageCount) {
            super(fragmentManager);
            this.pageCount = pageCount;
        }

        /**
         * This method will be invoked when a page is requested to create
         */
        @Override
        public Fragment getItem(int arg0) {
            PediatricPresumptiveFragment fragment = new PediatricPresumptiveFragment();
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
            return pageCount;
        }
    }

}


