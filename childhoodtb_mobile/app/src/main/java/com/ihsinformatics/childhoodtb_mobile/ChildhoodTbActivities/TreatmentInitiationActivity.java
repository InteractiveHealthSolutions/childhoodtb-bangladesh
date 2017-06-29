package com.ihsinformatics.childhoodtb_mobile.ChildhoodTbActivities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.ihsinformatics.childhoodtb_mobile.AbstractFragmentActivity;
import com.ihsinformatics.childhoodtb_mobile.App;
import com.ihsinformatics.childhoodtb_mobile.Barcode;
import com.ihsinformatics.childhoodtb_mobile.R;
import com.ihsinformatics.childhoodtb_mobile.custom.MyButton;
import com.ihsinformatics.childhoodtb_mobile.custom.MyEditText;
import com.ihsinformatics.childhoodtb_mobile.custom.MySpinner;
import com.ihsinformatics.childhoodtb_mobile.custom.MyTextView;
import com.ihsinformatics.childhoodtb_mobile.shared.AlertType;
import com.ihsinformatics.childhoodtb_mobile.shared.FormType;
import com.ihsinformatics.childhoodtb_mobile.util.RegexUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by Shujaat on 4/10/2017.
 */
public class TreatmentInitiationActivity extends AbstractFragmentActivity {

    MyTextView formDateTextView;
    MyButton formDateButton;

    MyTextView nidTextView;
    MyEditText nidEditText;

    MyTextView nidBelongsTextView;
    MySpinner nidBelongsSpinner;

    MyTextView otherSpecifyNIDTextView;
    MyEditText otherSpecifyNIDEditText;

    MyTextView registrationDateTextView;
    MyButton registrationDateButton;

    MyTextView tbRegisterNumberTextView;
    MyEditText tbRegisterNumberEditText;

    MyTextView referredWithoutRegistrationTextView;
    MySpinner referredWithoutRegistrationSpinner;

    MyTextView patientTypeTextView;
    MySpinner patientTypeSpinner;

    MyTextView patientCategoryTextView;
    MySpinner patientCategorySpinner;

    MyTextView nameOfSupporterTextView;
    MyEditText nameOfTreatmentSupporterEditText;

    MyTextView phoneNumberOfSupporterTextView;
    MyEditText phoneNumberOfSupporterEditText;

    MyTextView patientAndSupporterRelationTextView;
    MySpinner patientAndSupporterRelationSpinner;

    MyTextView otherTextView;
    MyEditText other;

    MyTextView patientIdTextView;
    MyEditText patientId;
    MyButton scanBarcode;

    MyTextView treatmentInitiationDateTextView;
    MyButton treatmentInitiationDateButton;

    String result = "";
    Calendar registrationDate;
    Calendar treatmentInitDate;
    boolean isOtherRequired, isNIDOtherRequired = false;

    @Override
    public void createViews(Context context) {
        //this  piece of code is used for  hide the softKey from the screen initially ...
        TreatmentInitiationActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pager = (ViewPager) findViewById(R.template_id.pager);
        TAG = "TreatmentInitiationActivity";
        formDateTextView = new MyTextView(context,
                R.style.text, R.string.form_date);
        formDateButton = new MyButton(context,
                R.style.button, R.drawable.custom_button_beige,
                R.string.form_date, R.string.form_date);

        registrationDateTextView = new MyTextView(context,
                R.style.text, R.string.registration_date);
        registrationDateButton = new MyButton(context,
                R.style.button, R.drawable.custom_button_beige,
                R.string.registration_date, R.string.registration_date);

        patientTypeTextView = new MyTextView(context,
                R.style.text, R.string.patient_type);
        patientTypeSpinner = new MySpinner(context,
                getResources().getStringArray(R.array.patient_type_options),
                R.string.patient_type, R.string.option_hint);

        otherSpecifyNIDTextView = new MyTextView(context,
                R.style.text, R.string.other_belongs);
        otherSpecifyNIDEditText = new MyEditText(context, R.string.other_belongs,
                R.string.other_belongs_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, 15, false);

        tbRegisterNumberTextView = new MyTextView(context,
                R.style.text, R.string.tb_reg_number);
        tbRegisterNumberEditText = new MyEditText(context, R.string.tb_reg_number,
                R.string.tb_reg_number_hint, InputType.TYPE_CLASS_NUMBER,
                R.style.edit, 10, false);

        referredWithoutRegistrationTextView = new MyTextView(context,
                R.style.text, R.string.patient_referred_without_Registration);
        referredWithoutRegistrationSpinner = new MySpinner(context,
                getResources().getStringArray(R.array.patient_referred_without_Registration_option),
                R.string.patient_referred_without_Registration, R.string.option_hint);

        patientCategoryTextView = new MyTextView(context,
                R.style.text, R.string.patient_category);
        patientCategorySpinner = new MySpinner(context,
                getResources().getStringArray(R.array.patient_category_options),
                R.string.patient_category, R.string.option_hint);

        nidTextView = new MyTextView(context,
                R.style.text, R.string.nid);
        nidEditText = new MyEditText(context, R.string.nid,
                R.string.nid_hint, InputType.TYPE_CLASS_NUMBER,
                R.style.edit, 17, false);

        nidBelongsTextView = new MyTextView(context,
                R.style.text, R.string.nid_belongs);
        nidBelongsSpinner = new MySpinner(context,
                getResources().getStringArray(R.array.nid_belongs_options),
                R.string.nid_belongs, R.string.option_hint);


        nameOfSupporterTextView = new MyTextView(context,
                R.style.text, R.string.name_treatment_supporter);
        nameOfTreatmentSupporterEditText = new MyEditText(context, R.string.name_treatment_supporter,
                R.string.name_treatment_supporter_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, 25, false);

        phoneNumberOfSupporterTextView = new MyTextView(context,
                R.style.text, R.string.treatment_supporter_phone_Number);
        phoneNumberOfSupporterEditText = new MyEditText(context, R.string.treatment_supporter_phone_Number,
                R.string.treatment_supporter_phone_hint, InputType.TYPE_CLASS_NUMBER,
                R.style.edit, 13, false);

        patientAndSupporterRelationTextView = new MyTextView(context,
                R.style.text, R.string.treatment_supporter_relationship);
        patientAndSupporterRelationSpinner = new MySpinner(context,
                getResources().getStringArray(R.array.treatment_supporter_relationships_options),
                R.string.treatment_supporter_relationship, R.string.option_hint);

        treatmentInitiationDateTextView = new MyTextView(context,
                R.style.text, R.string.treatment_initiation_date);
        treatmentInitiationDateButton = new MyButton(context,
                R.style.button, R.drawable.custom_button_beige,
                R.string.treatment_initiation_date, R.string.treatment_initiation_date);
        otherTextView = new MyTextView(context,
                R.style.text, R.string.other_specify);
        other = new MyEditText(context, R.string.other_specify,
                R.string.supporter_other_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, 25, false);

        patientIdTextView = new MyTextView(context, R.style.text,
                R.string.patient_id);

        patientId = new MyEditText(context, R.string.patient_id,
                R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, RegexUtil.idLength, false);

        scanBarcode = new MyButton(context, R.style.button,
                R.drawable.custom_button_beige, R.string.scan_barcode,
                R.string.scan_barcode);


        //define the navigation Fragments
        View[][] viewGroups = {
                {formDateTextView, formDateButton, patientIdTextView, patientId, scanBarcode,
                        registrationDateTextView, registrationDateButton, nidTextView, nidEditText,
                        nidBelongsTextView, nidBelongsSpinner, otherSpecifyNIDTextView, otherSpecifyNIDEditText
                },
                {referredWithoutRegistrationTextView, referredWithoutRegistrationSpinner, tbRegisterNumberTextView,
                        tbRegisterNumberEditText, patientTypeTextView, patientTypeSpinner, patientCategoryTextView, patientCategorySpinner,
                        treatmentInitiationDateTextView, treatmentInitiationDateButton,nameOfSupporterTextView, nameOfTreatmentSupporterEditText
                },
                {phoneNumberOfSupporterTextView, phoneNumberOfSupporterEditText, patientAndSupporterRelationTextView,
                        patientAndSupporterRelationSpinner, otherTextView, other}
        };

        // Create layouts and store in ArrayList
        groups = new ArrayList<ViewGroup>();
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

        views = new View[]{other, patientId, registrationDateButton, patientCategorySpinner,
                patientAndSupporterRelationSpinner, phoneNumberOfSupporterEditText,
                nameOfTreatmentSupporterEditText, otherSpecifyNIDEditText, other, nidEditText, nidBelongsSpinner, registrationDateButton, tbRegisterNumberEditText, treatmentInitiationDateButton
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
        // Set event listeners
        formDateButton.setOnClickListener(this);
        firstButton.setOnClickListener(this);
        lastButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        scanBarcode.setOnClickListener(this);
        registrationDateButton.setOnClickListener(this);
        treatmentInitiationDateButton.setOnClickListener(this);
        navigationSeekbar.setOnSeekBarChangeListener(this);

    }

    @Override
    public void initView(View[] views) {
        super.initView(views);
        formDate = Calendar.getInstance();
        registrationDate = Calendar.getInstance();
        treatmentInitDate = Calendar.getInstance();
        otherSpecifyNIDEditText.setEnabled(false);
        otherSpecifyNIDTextView.setEnabled(false);
        otherTextView.setEnabled(false);
        other.setEnabled(false);
        updateDisplay();
    }

    @Override
    public void updateDisplay() {
        formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));
        registrationDateButton.setText(DateFormat.format("dd-MM-yyyy", registrationDate.getTime()));
        treatmentInitiationDateButton.setText(DateFormat.format("dd-MM-yyyy", treatmentInitDate.getTime()));
    }

    @Override
    public boolean validate() {
        boolean valid = true;
        StringBuffer message = new StringBuffer();
        View[] mandatory = {nidEditText, nameOfTreatmentSupporterEditText, phoneNumberOfSupporterEditText,
                tbRegisterNumberEditText};

        for (View v : mandatory) {
            if (App.get(v).equals("")) {
                valid = false;
                message.append(v.getTag().toString() + ": " +
                        getResources().getString(R.string.empty_data) + "\n");
                ((EditText) v).setHintTextColor(getResources().getColor(
                        R.color.Red));
            }
        }

        if (!RegexUtil.isContactNumber(App.get(phoneNumberOfSupporterEditText))) {
            valid = false;
            message.append(phoneNumberOfSupporterEditText.getTag().toString() + ". "
                    + getResources().getString(R.string.invalid_data)
                    + "\n");
            phoneNumberOfSupporterEditText.setHintTextColor(getResources().getColor(R.color.Red));
        }
        if (!RegexUtil.isCnic(App.get(nidEditText))) {
            valid = false;
            message.append(nidEditText.getTag().toString()
                    + ": "
                    + getResources().getString(
                    R.string.invalid_data) + "\n");
            nidEditText.setTextColor(getResources().getColor(
                    R.color.Red));
        }
        if (!RegexUtil.isNumeric(App.get(tbRegisterNumberEditText), false)) {
            valid = false;
            message.append(tbRegisterNumberEditText.getTag().toString() + ".\n ");
            tbRegisterNumberEditText.setHintTextColor(getResources().getColor(R.color.Red));
        }
        if (App.get(nameOfTreatmentSupporterEditText).length() < 3) {
            valid = false;
            message.append(nameOfTreatmentSupporterEditText.getTag().toString() + ":" +
                    getResources().getString(R.string.invalid_string_length));
            nameOfTreatmentSupporterEditText.setHintTextColor(
                    getResources().getColor(R.color.Red));
        } else if (!RegexUtil.isWord(App.get(nameOfTreatmentSupporterEditText))) {

            valid = false;
            message.append(nameOfTreatmentSupporterEditText.getTag().toString() + ". "
                    + getResources().getString(R.string.invalid_data)
                    + "\n");
            nameOfTreatmentSupporterEditText.setHintTextColor(getResources().getColor(R.color.Red));
        }
        if (isOtherRequired) {
            if (App.get(other).equals("")) {
                valid = false;
                message.append(other.getTag().toString() + ": \n" +
                        getResources().getString(R.string.empty_data));
                other.setHintTextColor(getResources().getColor(R.color.Red));
            } else if (!RegexUtil.isWord(App.get(other))) {
                valid = false;
                message.append(other.getTag().toString() + ". "
                        + getResources().getString(R.string.invalid_data)
                        + "\n");
                other.setHintTextColor(getResources().getColor(R.color.Red));
            }
        }
        if (isNIDOtherRequired) {
            if (App.get(otherSpecifyNIDEditText).equals("")) {
                valid = false;
                message.append(otherSpecifyNIDEditText.getTag().toString() + ": \n" +
                        getResources().getString(R.string.empty_data));
                otherSpecifyNIDEditText.setHintTextColor(getResources().getColor(R.color.Red));
            } else if (!RegexUtil.isWord(App.get(otherSpecifyNIDEditText))) {
                valid = false;
                message.append(otherSpecifyNIDEditText.getTag().toString() + ". "
                        + getResources().getString(R.string.invalid_data)
                        + "\n");
                otherSpecifyNIDEditText.setHintTextColor(getResources().getColor(R.color.Red));
            }
        }
        if (App.get(patientId).equals("")) {
            valid = false;
            message.append(patientId.getTag().toString() + ": " +
                    getResources().getString(R.string.empty_data) + "\n");
            patientId.setHintTextColor(getResources().getColor(R.color.Red));
        }
        ///here not check whether the Child is tb Suspected or not ....
        else if (RegexUtil.matchId(App.get(patientId))) {
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
            if (treatmentInitDate.getTime().after(Calendar.getInstance().getTime())) {
                valid = false;
                message.append(treatmentInitiationDateButton.getTag()
                        + ": "
                        + getResources().getString(
                        R.string.invalid_future_date) + "\n");
            }
            if (registrationDate.getTime().after(Calendar.getInstance().getTime())) {
                valid = false;
                message.append(registrationDateButton.getTag()
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
            values.put("patientId", App.get(patientId));
            values.put("treatmentSupporterName", App.get(nameOfTreatmentSupporterEditText));
            values.put("treatmentSupporterPhone", App.get(phoneNumberOfSupporterEditText));

            final ArrayList<String[]> observations = new ArrayList<String[]>();
            observations.add(new String[]{"NID",
                    App.get(nidEditText)});
            observations.add(new String[]{"NID Holder",
                    App.get(nidBelongsSpinner)});

            if (nidBelongsSpinner.getSelectedItem().toString().equals(
                    getResources().getString(R.string.other))) {
                observations.add(new String[]{"Other",
                        App.get(otherSpecifyNIDEditText)});
            }
            observations.add(new String[]{"Referred to another Facility",
                    App.get(referredWithoutRegistrationSpinner)});
            observations.add(new String[]{"TB Registration Number",
                    App.get(tbRegisterNumberEditText)});
            observations.add(new String[]{"Registration Group",
                    App.get(patientTypeSpinner)});
            observations.add(new String[]{"Patient Category",
                    App.get(patientCategoryTextView)});
            observations.add(new String[]{"Treatment Supporter Relationship",
                    App.get(patientAndSupporterRelationSpinner)});
            if (patientAndSupporterRelationSpinner.getSelectedItem().toString()
                    .equals(getResources().getString(R.string.other))) {
                observations.add(new String[]{"Other",
                        App.get(other)});
            }
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
                    result = serverService.insertTreatmentInitiationForm(
                            FormType.TREATMENT_INITIATION, values,
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
                        App.getAlertDialog(TreatmentInitiationActivity.this,
                                AlertType.INFO,
                                getResources().getString(R.string.inserted))
                                .show();
                        initView(views);
                    } else {
                        App.getAlertDialog(TreatmentInitiationActivity.this,
                                AlertType.ERROR, result).show();
                    }
                }
            };
            updateTask.execute("");

        }
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
    }

    @Override
    public void onClick(View view) {
        if (view == formDateButton) {

            showDialog(DATE_DIALOG_ID);

        } else if (view == registrationDateButton) {

            new DatePickerDialog(this, date, registrationDate
                    .get(Calendar.YEAR), registrationDate.get(Calendar.MONTH),
                    registrationDate.get(Calendar.DAY_OF_MONTH)).show();

        } else if (view == treatmentInitiationDateButton)

        {

            new DatePickerDialog(this, treatmentDate, treatmentInitDate
                    .get(Calendar.YEAR), treatmentInitDate.get(Calendar.MONTH),
                    treatmentInitDate.get(Calendar.DAY_OF_MONTH)).show();

        } else if (view == firstButton)

        {

            gotoFirstPage();

        } else if (view == lastButton)

        {

            gotoLastPage();

        } else if (view == clearButton)

        {
            initView(views);

        } else if (view == saveButton)

        {
            submit();
        } else if (view == scanBarcode)

        {
            Intent intent = new Intent(Barcode.BARCODE_INTENT);
            intent.putExtra(Barcode.SCAN_MODE, Barcode.QR_MODE);
            startActivityForResult(intent, Barcode.BARCODE_RESULT);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView == nidBelongsSpinner) {

            if (nidBelongsSpinner.getSelectedItem().toString().equals(
                    getResources().getString(R.string.other))) {
                otherSpecifyNIDEditText.setEnabled(true);
                otherSpecifyNIDTextView.setEnabled(true);
                isNIDOtherRequired = true;
            } else {
                otherSpecifyNIDEditText.setEnabled(false);
                otherSpecifyNIDTextView.setEnabled(false);
                isNIDOtherRequired = false;
            }
        }
        if (adapterView == patientAndSupporterRelationSpinner) {

            if (patientAndSupporterRelationSpinner.getSelectedItem().toString().equals(
                    getResources().getString(R.string.other))) {

                otherTextView.setEnabled(true);
                other.setEnabled(true);
                isOtherRequired = true;
            } else {
                otherTextView.setEnabled(false);
                other.setEnabled(false);
                isOtherRequired = false;
            }

        }

        updateDisplay();
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    public DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            registrationDate.set(Calendar.YEAR, year);
            registrationDate.set(Calendar.MONTH, monthOfYear);
            registrationDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateDisplay();
        }

    };
    public DatePickerDialog.OnDateSetListener treatmentDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            treatmentInitDate.set(Calendar.YEAR, year);
            treatmentInitDate.set(Calendar.MONTH, monthOfYear);
            treatmentInitDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDisplay();
        }

    };

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
