package com.ihsinformatics.childhoodtb_mobile.ChildhoodTbNewActivities;

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
import com.ihsinformatics.childhoodtb_mobile.shared.AlertType;
import com.ihsinformatics.childhoodtb_mobile.shared.FormType;
import com.ihsinformatics.childhoodtb_mobile.util.RegexUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by Shujaat on 3/24/2017.
 */
public class PaediatricContactTracingAtHomeActivity extends AbstractFragmentActivity implements TextView.OnEditorActionListener {

    MyTextView
            formDateTextView, patientIdTextView,
            addressTextView, phoneNoTextView, dotsCenterNameTextView,
            treatmentInitiationCenterTextView, dsTBRegNoTextView, contactInvestigatorNameTextView,
            contactInvestigatorDesignationTextView, nameOfDotProviderTextView, designationOfDotProviderTextView,
            phoneNoDotProviderTextView,
            contactOneNameTextView, contactOneAgeTextView, contactOneGenderTextView, contactOneSymptomsTextView,
            contactOneCoughTextView, contactOneFeverTextView, contactOneNightSweatsTextView, contactOneWeightTextView,
            contactOnePoorAppetiteTextView, referTextView, outcomeCodeTextView, remarksTextView, numberOfContactPersonTextView;


    EditText
            phoneNo, age, patientId, address, dotsCenterName,
            treatmentInitiationCenter, dsTBRegNo, contactInvestigatorName,
            contactInvestigatorDesignation, nameOfDotProvider, designationOfDotProvider,
            phoneNoDotProvider, contactOneName, refer, remarks;

    MySpinner
            contactOneSymptoms, contactWeightLoss,
            contactOneCough, contactOneFever, contactOneNightSweats, poorAppetite,
            numberOfContactPerson, outcomeCode;


    MyButton formDateButton;

    MyRadioGroup gender;
    MyRadioButton male, female;
    MyButton scanBarcode;
    String result = "";


    @Override
    public void createViews(Context context) {
        //this  piece of code is used for  hide the softKey from the screen initially ...
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        pager = (ViewPager) findViewById(R.template_id.pager);
        TAG = "PaediatricContactTracingAtHomeActivity";
        //Initialize Views ..
        formDateTextView = new MyTextView(context, R.style.text, R.string.form_date);
        formDateButton = new MyButton(context, R.style.button,
                R.drawable.custom_button_beige, R.string.form_date,
                R.string.form_date);
        addressTextView = new MyTextView(context,
                R.style.text, R.string.address);
        address = new MyEditText(context,
                R.string.address, R.string.address_hint,
                InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS, R.style.edit, 25, false);
        phoneNoTextView = new MyTextView(context,
                R.style.text, R.string.phone_No);
        phoneNo = new MyEditText(context,
                R.string.phone_No, R.string.phone_hint,
                InputType.TYPE_CLASS_NUMBER, R.style.edit, 15, false);
        dotsCenterNameTextView = new MyTextView(context,
                R.style.text, R.string.dots_center_name);
        dotsCenterName = new MyEditText(context,
                R.string.dots_center_name, R.string.dots_center_name_hint,
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 15, false);
        treatmentInitiationCenterTextView = new MyTextView(context,
                R.style.text, R.string.treatment_initiation_center);
        treatmentInitiationCenter = new MyEditText(context,
                R.string.treatment_initiation_center, R.string.treatment_initiation_center_hint,
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 25, false);
        dsTBRegNoTextView = new MyTextView(context,
                R.style.text, R.string.ds_tb_reg_no);
        dsTBRegNo = new MyEditText(context,
                R.string.ds_tb_reg_no, R.string.ds_tb_reg_no_hint,
                InputType.TYPE_CLASS_NUMBER, R.style.edit, 11, false);
        contactInvestigatorNameTextView = new MyTextView(context,
                R.style.text, R.string.contact_investigator_name);
        contactInvestigatorName = new MyEditText(context,
                R.string.contact_investigator_name, R.string.contact_investigator_name_hint,
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 25, false);
        contactInvestigatorDesignationTextView = new MyTextView(context,
                R.style.text, R.string.designation_contact_investigator);
        contactInvestigatorDesignation = new MyEditText(context,
                R.string.contact_investigator_name, R.string.contact_investigator_name_hint,
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 15, false);
        nameOfDotProviderTextView = new MyTextView(context,
                R.style.text, R.string.dot_provider_name);
        nameOfDotProvider = new MyEditText(context,
                R.string.dots_center_name, R.string.dots_center_name_hint,
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 25, false);
        designationOfDotProviderTextView = new MyTextView(context,
                R.style.text, R.string.dot_provider_designation);
        designationOfDotProvider = new MyEditText(context,
                R.string.dot_provider_designation, R.string.dot_provider_designation_hint,
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 15, false);

        phoneNoDotProviderTextView = new MyTextView(context,
                R.style.text, R.string.dot_provider_phone_no);
        phoneNoDotProvider = new MyEditText(context,
                R.string.dot_provider_phone_no, R.string.dot_provider_phone_no_hint,
                InputType.TYPE_CLASS_NUMBER, R.style.edit, 15, false);
        numberOfContactPersonTextView = new MyTextView(context,
                R.style.text, R.string.number_of_contact_person);
        numberOfContactPerson = new MySpinner(context,
                getResources().getStringArray(R.array.contact_person_options),
                R.string.number_of_contact_person, R.string.option_hint);

          /* Contact persons fields */
        contactOneNameTextView = new MyTextView(context,
                R.style.text, R.string.contact_person_name);
        contactOneName = new MyEditText(context,
                R.string.contact_person_name, R.string.name_hint,
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 25, false);

        contactOneGenderTextView = new MyTextView(context, R.style.text, R.string.gender);
        male = new MyRadioButton(context, R.string.male, R.style.radio,
                R.string.male);
        female = new MyRadioButton(context, R.string.female, R.style.radio,
                R.string.female);
        gender = new MyRadioGroup(context,
                new MyRadioButton[]{male, female}, R.string.gender,
                R.style.radio, App.isLanguageRTL());
        contactOneAgeTextView = new MyTextView(context, R.style.text, R.string.age);
        age = new MyEditText(context, R.string.age,
                R.string.age_hint, InputType.TYPE_CLASS_NUMBER,
                R.style.edit, 3, false);
        contactOneWeightTextView = new MyTextView(context,
                R.style.text, R.string.weight_loss);
        contactWeightLoss = new MySpinner(context,
                getResources().getStringArray(R.array.weight_loss_options),
                R.string.weight_loss, R.string.option_hint);

        contactOneSymptomsTextView = new MyTextView(context,
                R.style.text, R.string.symptoms);
        contactOneSymptoms = new MySpinner(context,
                getResources().getStringArray(R.array.symptoms_options),
                R.string.symptoms, R.string.option_hint);

        contactOneCoughTextView = new MyTextView(context,
                R.style.text, R.string.contact_cough);
        contactOneCough = new MySpinner(context, getResources()
                .getStringArray(R.array.cough_options),
                R.string.contact_cough, R.string.option_hint);

        contactOneFeverTextView = new MyTextView(context,
                R.style.text, R.string._fever);
        contactOneFever = new MySpinner(context,
                getResources().getStringArray(R.array.fever_options)
                , R.string._fever, R.string.option_hint);
        contactOneNightSweatsTextView = new MyTextView(context, R.style.text,
                R.string.night_sweats);
        contactOneNightSweats = new MySpinner(context,
                getResources().getStringArray(R.array.night_sweats_options)
                , R.string.night_sweats, R.string.option_hint);
        contactOnePoorAppetiteTextView = new MyTextView(context,
                R.style.text, R.string.poor_appetite);
        poorAppetite = new MySpinner(context,
                getResources().getStringArray(R.array.appetite_decreased_options),
                R.string.poor_appetite, R.string.option_hint);

        referTextView = new MyTextView(context,
                R.style.text, R.string.refer);
        refer = new MyEditText(context,
                R.string.refer, R.string.refer_hint,
                InputType.TYPE_CLASS_TEXT, R.style.edit, 25, false);
        outcomeCodeTextView = new MyTextView(context,
                R.style.text, R.string.outcome_code);
        outcomeCode = new MySpinner(context,
                getResources().getStringArray(R.array.outcome_code_options),
                R.string.outcome_code, R.string.option_hint);

        remarksTextView = new MyTextView(context,
                R.style.text, R.string.remarks);
        remarks = new MyEditText(context,
                R.string.remarks, R.string.remarks_hint,
                InputType.TYPE_CLASS_TEXT, R.style.edit, 50, false);

        patientIdTextView = new MyTextView(context, R.style.text,
                R.string._patient_id);

        patientId = new MyEditText(context, R.string._patient_id,
                R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, RegexUtil.idLength, false);

        scanBarcode = new MyButton(context, R.style.button,
                R.drawable.custom_button_beige, R.string.scan_barcode,
                R.string.scan_barcode);

       View[][] viewGroups = {

                {formDateTextView, formDateButton, patientIdTextView, patientId, scanBarcode,
                         phoneNoTextView, phoneNo, addressTextView, address, dotsCenterNameTextView, dotsCenterName
                },
                {treatmentInitiationCenterTextView, treatmentInitiationCenter, dsTBRegNoTextView, dsTBRegNo,
                        contactInvestigatorNameTextView, contactInvestigatorName, contactInvestigatorDesignationTextView,
                        contactInvestigatorDesignation, nameOfDotProviderTextView, nameOfDotProvider, designationOfDotProviderTextView,
                        designationOfDotProvider},
                {phoneNoDotProviderTextView, phoneNoDotProvider, numberOfContactPersonTextView, numberOfContactPerson}

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

        Log.i("groupSize", "" + groups.size());
        FragmentManager fragmentManager = getSupportFragmentManager();
        PediatricPresumptveFragmentPagerAdapter pagerAdapter = new PediatricPresumptveFragmentPagerAdapter(
                fragmentManager, groups.size());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(groups.size());

        views = new View[]{

                age, contactWeightLoss, numberOfContactPerson,
                contactOneCough, contactOneFever, patientId, contactOneNightSweats,
                phoneNo, address, dotsCenterName, treatmentInitiationCenter, dsTBRegNo, contactInvestigatorName,
                contactInvestigatorDesignation, designationOfDotProvider, nameOfDotProvider, phoneNoDotProvider,
                refer, remarks
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
        firstButton.setOnClickListener(this);
        lastButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        scanBarcode.setOnClickListener(this);
        navigationSeekbar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }

    @Override
    public void initView(View[] views) {
        super.initView(views);
        formDate = Calendar.getInstance();
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        MySpinner spinner = (MySpinner) adapterView;
        boolean visible = spinner.getSelectedItemPosition() == 0;

        if(adapterView==numberOfContactPerson)
        {
           View[][] viewGroups = {
                    {contactOneAgeTextView, age, contactOneGenderTextView, gender, contactOneSymptomsTextView,
                            contactOneSymptoms, contactOneCoughTextView, contactOneCough,
                            contactOneFeverTextView, contactOneFever
                    },
                    {referTextView, refer, outcomeCodeTextView, outcomeCode, remarksTextView, remarks,
                            contactOneWeightTextView, contactWeightLoss, contactOnePoorAppetiteTextView,
                            poorAppetite, contactOneNightSweatsTextView, contactOneNightSweats
                    }
            };

            for (int k = 0; k < viewGroups.length; k++) {

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                for (int j = 0; j < viewGroups[k].length; j++) {
                    layout.addView(viewGroups[k][j]);
                }

                ScrollView scrollView = new ScrollView(this);
                scrollView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                scrollView.addView(layout);
                groups.add(scrollView);
            }

            Log.i("logTest", "" + spinner.getSelectedItem().toString());
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
        View[] mandatory = {phoneNo, address, nameOfDotProvider, dotsCenterName, treatmentInitiationCenter,
                dsTBRegNo, contactInvestigatorDesignation, contactInvestigatorName, nameOfDotProvider,
                phoneNoDotProvider};

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
            if (!RegexUtil.isNumeric(App.get(phoneNo), false)) {
                valid = false;
                message.append(phoneNo.getTag().toString() + ": "
                        + getResources().getString(R.string.invalid_data)
                        + "\n");
                phoneNo.setTextColor(getResources().getColor(R.color.Red));
            }
            if (!RegexUtil.isWord(App.get(dotsCenterName))) {

                valid = false;
                message.append(dotsCenterName.getTag().toString() + ": "
                        + getResources().getString(R.string.invalid_data)
                        + "\n");
                dotsCenterName.setTextColor(getResources().getColor(R.color.Red));
            }
            if (!RegexUtil.isNumeric(App.get(treatmentInitiationCenter), false)) {

                valid = false;
                message.append(treatmentInitiationCenter.getTag().toString() + ": "
                        + getResources().getString(R.string.invalid_data)
                        + "\n");
                treatmentInitiationCenter.setTextColor(getResources().getColor(R.color.Red));
            }
            if (!RegexUtil.isWord(App.get(contactInvestigatorName))) {

                valid = false;
                message.append(contactInvestigatorName.getTag().toString() + ": "
                        + getResources().getString(R.string.invalid_data)
                        + "\n");
                contactInvestigatorName.setTextColor(getResources().getColor(R.color.Red));
            }


        }

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
            values.put("gender", male.isChecked() ? "M" : "F");
            values.put("age", App.get(age));
            values.put("location", App.getLocation());
            values.put("patientId", App.get(patientId));

            final ArrayList<String[]> observations = new ArrayList<String[]>();

            observations.add(new String[]{"Weight",
                    App.get(contactWeightLoss)});
            observations.add(new String[]{"Fever",
                    App.get(contactOneFever).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(contactOneFever)});
            observations.add(new String[]{"Night Sweats",
                    App.get(contactOneNightSweats).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(contactOneNightSweats)});
            observations.add(new String[]{"Cough",
                    App.get(contactOneCough).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(contactOneCough)});
            observations.add(new String[]{"Poor Appetite",
                    App.get(poorAppetite).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(poorAppetite)});


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
                        App.getAlertDialog(PaediatricContactTracingAtHomeActivity.this,
                                AlertType.INFO,
                                getResources().getString(R.string.inserted))
                                .show();
                        initView(views);
                    } else {
                        App.getAlertDialog(PaediatricContactTracingAtHomeActivity.this,
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