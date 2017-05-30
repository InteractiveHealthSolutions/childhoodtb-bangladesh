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
import com.ihsinformatics.childhoodtb_mobile.model.Patient;
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
            formDateTextView, patientIdTextView, contactAgeTextView, contactGenderTextView,
            contactSymptomsTextView, contactCoughTextView, contactFeverTextView, contactNightSweatsTextView,
            contactWeightTextView, contactPoorAppetiteTextView, referTextView, outcomeCodeTextView, remarksTextView,
            indexNameTextView, indexCaseIDTextView, contactFirstNameTextView, contactLastNameTextView;


    EditText
            age, patientId, indexCaseId, indexName, contactFirstName, contactLastName, refer, remarks;

    MySpinner
            contactSymptoms, contactWeightLoss, contactCough, contactFever, contactNightSweats, poorAppetite,
            outcomeCode;


    MyButton formDateButton, validatePatientId, scanBarcode, scanBarcodeIndexId;

    MyRadioGroup gender;
    MyRadioButton male, female;
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

        indexCaseIDTextView = new MyTextView(context,
                R.style.text, R.string.index_case_id);
        indexCaseId = new MyEditText(context,
                R.string.index_case_id, R.string.index_case_id_hint,
                InputType.TYPE_CLASS_TEXT, R.style.edit, 13, false);

        scanBarcodeIndexId = new MyButton(context, R.style.button,
                R.drawable.custom_button_beige, R.string.scan_barcode,
                R.string.scan_barcode);
        validatePatientId = new MyButton(context, R.style.button,
                R.drawable.custom_button_beige, R.string.validateID,
                R.string.validateID);
        indexNameTextView = new MyTextView(context,
                R.style.text, R.string.index_name);
        indexName = new MyEditText(context, R.string.age,
                R.string.index_name, InputType.TYPE_CLASS_TEXT,
                R.style.edit, 25, false);
        contactFirstNameTextView = new MyTextView(context,
                R.style.text, R.string.contact_first_name);
        contactFirstName = new MyEditText(context, R.string.age,
                R.string.contact_first_name, InputType.TYPE_CLASS_TEXT,
                R.style.edit, 25, false);
        contactLastNameTextView = new MyTextView(context,
                R.style.text, R.string.contact_last_name);
        contactLastName = new MyEditText(context, R.string.age,
                R.string.contact_last_name, InputType.TYPE_CLASS_TEXT,
                R.style.edit, 25, false);

        contactGenderTextView = new MyTextView(context,
                R.style.text, R.string.gender);
        male = new MyRadioButton(context, R.string.male, R.style.radio,
                R.string.male);
        female = new MyRadioButton(context, R.string.female, R.style.radio,
                R.string.female);
        gender = new MyRadioGroup(context,
                new MyRadioButton[]{male, female}, R.string.gender,
                R.style.radio, App.isLanguageRTL());
        contactAgeTextView = new MyTextView(context, R.style.text, R.string.age);
        age = new MyEditText(context, R.string.age,
                R.string.age_hint, InputType.TYPE_CLASS_NUMBER,
                R.style.edit, 3, false);
        contactWeightTextView = new MyTextView(context,
                R.style.text, R.string.weight_loss);
        contactWeightLoss = new MySpinner(context,
                getResources().getStringArray(R.array.weight_loss_options),
                R.string.weight_loss, R.string.option_hint);

        contactSymptomsTextView = new MyTextView(context,
                R.style.text, R.string.symptoms);
        contactSymptoms = new MySpinner(context,
                getResources().getStringArray(R.array.symptoms_options),
                R.string.symptoms, R.string.option_hint);

        contactCoughTextView = new MyTextView(context,
                R.style.text, R.string.contact_cough);
        contactCough = new MySpinner(context, getResources()
                .getStringArray(R.array.cough_options),
                R.string.contact_cough, R.string.option_hint);

        contactFeverTextView = new MyTextView(context,
                R.style.text, R.string._fever);
        contactFever = new MySpinner(context,
                getResources().getStringArray(R.array.fever_options)
                , R.string._fever, R.string.option_hint);
        contactNightSweatsTextView = new MyTextView(context, R.style.text,
                R.string.night_sweats);
        contactNightSweats = new MySpinner(context,
                getResources().getStringArray(R.array.night_sweats_options)
                , R.string.night_sweats, R.string.option_hint);
        contactPoorAppetiteTextView = new MyTextView(context,
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

                {formDateTextView, formDateButton, indexCaseIDTextView, indexCaseId, scanBarcodeIndexId,
                        validatePatientId, indexNameTextView, indexName, contactFirstNameTextView, contactFirstName,
                        contactLastNameTextView, contactLastName, contactAgeTextView, age
                },
                {contactGenderTextView, gender, contactSymptomsTextView, contactSymptoms,
                        contactCoughTextView, contactCough, contactFeverTextView, contactFever, contactNightSweatsTextView,
                        contactNightSweats, contactWeightTextView, contactWeightLoss
                },
                {contactPoorAppetiteTextView, poorAppetite, referTextView, refer, outcomeCodeTextView, outcomeCode
                        , remarksTextView, remarks,patientIdTextView, patientId, scanBarcode}
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

        views = new View[]{age, contactWeightLoss, contactCough, contactFever, patientId, contactNightSweats,
                refer, remarks, contactFirstName, contactLastName, indexCaseId, indexName
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
        saveButton.setEnabled(false);
        scanBarcode.setOnClickListener(this);
        validatePatientId.setOnClickListener(this);
        indexName.setFocusable(false);
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
        } else if (view == validatePatientId) {
            //check Patient I validation
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

                        ArrayList<Patient> response = serverService.getPatientInfo(App.get(indexCaseId));
                        return response;
                    }

                    @Override
                    protected void onProgressUpdate(String... values) {
                    }

                    @Override
                    protected void onPostExecute(Object result) {
                        super.onPostExecute(result);
                        loading.dismiss();
                        ArrayList<Patient> patients = (ArrayList<Patient>) result;
                        StringBuilder errorMessage = new StringBuilder();
                        if (result == null) {
                            AlertDialog alertDialog = App.getAlertDialog(PaediatricContactTracingAtHomeActivity.this,
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
                                            initView(views);
                                        }
                                    });
                            alertDialog.show();
                        } else {
                            if (patients.size() == 0
                                    || patients.isEmpty()) {
                                errorMessage.append(
                                        getResources().getString(R.string.not_found_contact_registry)
                                );
                                App.getAlertDialog(PaediatricContactTracingAtHomeActivity.this,
                                        AlertType.ERROR, errorMessage.toString()).show();
                                initView(views);

                            } else {
                                saveButton.setEnabled(true);
                                indexName.setText(patients.get(0).getName());
                            }//end else
                        }
                    }
                };
                getTask.execute("");
            }
        }//end else if condition...


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

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
        View[] mandatory = {age, contactWeightLoss, refer, remarks, outcomeCode, contactFirstName,
                contactLastName, indexName};

        for (View v : mandatory) {
            if (App.get(v).equals("")) {
                valid = false;
                message.append(v.getTag().toString() + ": " +
                        getResources().getString(R.string.empty_data) + "\n");
                ((EditText) v).setHintTextColor(getResources().getColor(
                        R.color.Red));
            }
        }
        if (!RegexUtil.isWord(App.get(contactFirstName))) {
            valid = false;
            message.append(contactFirstName.getTag().toString()
                    + ": "
                    + getResources().getString(
                    R.string.invalid_data) + "\n");
            contactFirstName.setTextColor(getResources().getColor(
                    R.color.Red));
        }
        if (!RegexUtil.isWord(App.get(contactLastName))) {
            valid = false;
            message.append(contactLastName.getTag().toString()
                    + ": "
                    + getResources().getString(
                    R.string.invalid_data) + "\n");
            contactLastName.setTextColor(getResources().getColor(
                    R.color.Red));
        }
        if (!RegexUtil.isWord(App.get(refer))) {
            valid = false;
            message.append(refer.getTag().toString()
                    + ": "
                    + getResources().getString(
                    R.string.invalid_data) + "\n");
            refer.setTextColor(getResources().getColor(
                    R.color.Red));
        }
        if (App.get(patientId).equals("")) {
            valid = false;
            message.append(patientId.getTag().toString() + ". ");
            patientId.setHintTextColor(getResources().getColor(R.color.Red));
        } else if (RegexUtil.matchId(App.get(patientId))) {
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
        ///check the index id
        if (App.get(indexCaseId).equals("")) {
            valid = false;
            message.append(indexCaseId.getTag().toString() + ". ");
            indexCaseId.setHintTextColor(getResources().getColor(R.color.Red));
        } else if (RegexUtil.matchId(App.get(indexCaseId))) {
            if (!RegexUtil.isValidId(App.get(indexCaseId))) {

                valid = false;
                message.append(indexCaseId.getTag().toString()
                        + ": "
                        + getResources().getString(
                        R.string.invalid_data) + "\n");
                indexCaseId.setTextColor(getResources().getColor(
                        R.color.Red));
            }
        } else {

            valid = false;
            message.append(indexCaseId.getTag().toString() + ": "
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
    public boolean submit()     {

        if (validate()) {

            final ContentValues values = new ContentValues();
            values.put("formDate", App.getSqlDate(formDate));
            values.put("gender", male.isChecked() ? "M" : "F");
            values.put("age", App.get(age));
            values.put("firstName", App.get(contactFirstName));
            values.put("lastName", App.get(contactLastName));
            values.put("location", App.getLocation());
            values.put("patientId", App.get(patientId));
            values.put("index_id",App.get(indexCaseId));
            final ArrayList<String[]> observations = new ArrayList<String[]>();
            observations.add(new String[]{"Index Case ID",
                    App.get(indexCaseId)});
            observations.add(new String[]{"Weight Loss",
                    App.get(contactWeightLoss)});
            observations.add(new String[]{"Symptoms",
                    App.get(contactSymptoms)});
            observations.add(new String[]{"Fever",
                    App.get(contactFever).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(contactFever)});
            observations.add(new String[]{"Night Sweats",
                    App.get(contactNightSweats).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(contactNightSweats)});
            observations.add(new String[]{"Cough",
                    App.get(contactCough).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(contactCough)});
            observations.add(new String[]{"Poor Appetite",
                    App.get(poorAppetite).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(poorAppetite)});
            observations.add(new String[]{"Reffered",
                    App.get(refer)});
            observations.add(new String[]{"Outcome Code",
                    App.get(outcomeCode)});
            observations.add(new String[]{"Remarks",
                    App.get(remarks)});


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
                    result = serverService.savePaediatricContactTracing(
                            FormType.PAEDIATRIC_CONTACT_TRACING, values,
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

    public boolean checkPatientId() {
        boolean isIndexId = true;
        final String indexPatientId = App.get(indexCaseId);
        if (!indexPatientId.equals("")) {
            if (RegexUtil.matchId(App.get(indexCaseId))) {

                if (!RegexUtil.isValidId(App.get(indexCaseId))) {
                    isIndexId = false;
                    App.getAlertDialog(PaediatricContactTracingAtHomeActivity.this,
                            AlertType.ERROR, indexCaseId.getTag().toString() + ":"
                                    + getResources().getString(R.string.invalid_data)).show();

                    indexCaseId.setTextColor(getResources().getColor(
                            R.color.Red));
                }
            } else {
                isIndexId = false;
                App.getAlertDialog(PaediatricContactTracingAtHomeActivity.this,
                        AlertType.ERROR, indexCaseId.getTag().toString() + ":"
                                + getResources().getString(R.string.invalid_data)).show();

                indexCaseId.setTextColor(getResources().getColor(
                        R.color.Red));
            }
        } else {
            isIndexId = false;
            App.getAlertDialog(PaediatricContactTracingAtHomeActivity.this,
                    AlertType.ERROR, getResources().getString(R.string.empty_data_indexId)).show();

        }
        return isIndexId;
    }
}