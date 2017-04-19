package com.ihsinformatics.childhoodtb_mobile.ChildhoodTbActivities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Shujaat on 4/7/2017.
 */
public class TSTTestResultActivity extends AbstractFragmentActivity {

    MyTextView formDateTextView;
    MyButton formDateButton;

    MyTextView testResultDateTextView;
    MyButton testResultDateButton;

    MyTextView tstResultTextView;
    MySpinner tstResultSpinner;

    MyTextView interpretationOfTSTTextView;
    MySpinner interpretationOfTSTSpinner;

    MyTextView patientIdTextView;
    MyEditText patientId;
    MyButton scanBarcode;

    MyTextView testIdTextView;
    MyEditText testId;

    String result = "";
    Calendar testResultCalender;
    MyButton getWeightPercentile;
    String weightPercentile = "";
    MyTextView weightPercentileTextView;
    MyEditText weightPercentileEditText;

    @Override
    public void createViews(Context context) {
        //this  piece of code is used for  hide the softKey from the screen initially ...
        TSTTestResultActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        pager = (ViewPager) findViewById(R.template_id.pager);
        TAG = "TSTTestResultActivity";

        formDateTextView = new MyTextView(context,
                R.style.text, R.string.form_date);
        formDateButton = new MyButton(context,
                R.style.button, R.drawable.custom_button_beige,
                R.string.form_date, R.string.form_date);


        testResultDateTextView = new MyTextView(context,
                R.style.text, R.string.test_result_date);
        testResultDateButton = new MyButton(context,
                R.style.button, R.drawable.custom_button_beige,
                R.string.test_result_date, R.string.test_result_date);

        tstResultTextView = new MyTextView(context,
                R.style.text, R.string.tst_result);
        tstResultSpinner = new MySpinner(context,
                getResources().getStringArray(R.array.tst_result_option),
                R.string.tst_result, R.string.option_hint);
        interpretationOfTSTTextView = new MyTextView(context,
                R.style.text, R.string.interpretation_tst);
        interpretationOfTSTSpinner = new MySpinner(context,
                getResources().getStringArray(R.array.interpretation_tst_option),
                R.string.interpretation_tst, R.string.option_hint);

        patientIdTextView = new MyTextView(context, R.style.text,
                R.string.patient_id);

        getWeightPercentile = new MyButton(context, R.style.button,
                R.drawable.custom_button_beige, R.string.get_weight_percentile,
                R.string.get_weight_percentile);

        patientId = new MyEditText(context, R.string.patient_id,
                R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, RegexUtil.idLength, false);

        scanBarcode = new MyButton(context, R.style.button,
                R.drawable.custom_button_beige, R.string.scan_barcode,
                R.string.scan_barcode);
        testIdTextView = new MyTextView(context, R.style.text,
                R.string.test_id);
        testId = new MyEditText(context, R.string.test_id,
                R.string.test_id_hint, InputType.TYPE_CLASS_NUMBER,
                R.style.edit, 5, false);
        weightPercentileTextView = new MyTextView(context, R.style.text,
                R.string.weight_percentile);
        weightPercentileEditText = new MyEditText(context, R.string.weight_percentile,
                R.string.weight_percentile, InputType.TYPE_CLASS_NUMBER,
                R.style.edit, 25, false);

        //define the navigation Fragments
        View[][] viewGroups = {
                {formDateTextView, formDateButton, patientIdTextView, patientId, scanBarcode, getWeightPercentile,
                        testResultDateTextView, testResultDateButton, tstResultTextView, tstResultSpinner, weightPercentileTextView,
                        weightPercentileEditText, interpretationOfTSTTextView, interpretationOfTSTSpinner, testIdTextView, testId}
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

        views = new View[]{patientId, testResultDateButton, interpretationOfTSTSpinner,
                tstResultSpinner, testId};


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
        getWeightPercentile.setOnClickListener(this);
        testResultDateButton.setOnClickListener(this);
        navigationSeekbar.setOnSeekBarChangeListener(this);

    }

    @Override
    public void initView(View[] views) {
        super.initView(views);
        formDate = Calendar.getInstance();
        testResultCalender = Calendar.getInstance();
        weightPercentileEditText.setFocusable(false);
        updateDisplay();
    }

    @Override
    public void updateDisplay() {
        formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));
        testResultDateButton.setText(DateFormat.format("dd-MM-yyyy", testResultCalender.getTime()));
    }

    @Override
    public boolean validate() {
        boolean valid = true;
        StringBuffer message = new StringBuffer();
        View[] mandatory = {testId};

        for (View v : mandatory) {
            if (App.get(v).equals("")) {
                valid = false;
                message.append(v.getTag().toString() + ": " +
                        getResources().getString(R.string.empty_data) + "\n");
                ((EditText) v).setHintTextColor(getResources().getColor(
                        R.color.Red));
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
            if (testResultCalender.getTime().after(Calendar.getInstance().getTime())) {
                valid = false;
                message.append(testResultDateButton.getTag()
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
            values.put("testId", App.get(testId));
            values.put("conceptName", "TST Barcode");

            final ArrayList<String[]> observations = new ArrayList<String[]>();
            observations.add(new String[]{"TST Barcode",
                    App.get(testId)});
            observations.add(new String[]{"Test Result Date",
                    App.get(testResultDateButton)});
            observations.add(new String[]{"TST Result",
                    App.get(tstResultSpinner)});
            Log.i("spinnerVal", "" + App.get(interpretationOfTSTSpinner));
            observations.add(new String[]{"Interpretation of TST",
                    App.get(interpretationOfTSTSpinner)});

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
                    result = serverService.insertTestOrderResultForm(
                            FormType.TST_RESULT, values,
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
                        App.getAlertDialog(TSTTestResultActivity.this,
                                AlertType.INFO,
                                getResources().getString(R.string.inserted))
                                .show();
                        initView(views);
                    } else {
                        App.getAlertDialog(TSTTestResultActivity.this,
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

        } else if (view == testResultDateButton) {

            new DatePickerDialog(this,
                    resultDate, testResultCalender
                    .get(Calendar.YEAR), testResultCalender.get(Calendar.MONTH),
                    testResultCalender.get(Calendar.DAY_OF_MONTH)).show();
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
        } else if (view == getWeightPercentile) {
            final String indexPatientId = App.get(patientId);

            if (!indexPatientId.equals("")) {
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

                        String[] response = serverService.getPatientObs(indexPatientId, "Weight Percentile");
                        return response;
                    }

                    @Override
                    protected void onProgressUpdate(String... values) {
                    }

                    @Override
                    protected void onPostExecute(Object result) {
                        super.onPostExecute(result);
                        loading.dismiss();
                        String[] res = (String[]) result;

                        if (res != null && res.length > 0) {
                            if (res[0].equals("InternetConnectionFail")) {
                                AlertDialog alertDialog = App.getAlertDialog(TSTTestResultActivity.this,
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
                                weightPercentile = res[0].toString();
                                weightPercentileEditText.setText(weightPercentile);
                            }
                        }
                    }
                };
                getTask.execute("");
            }//end if condition
        }//else if condition
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Log.i("weightPer", "" + weightPercentile);

        if (adapterView == tstResultSpinner) {
            String selectedResult = tstResultSpinner.getSelectedItem().toString();
            if ((selectedResult.equals(getResources().getString(R.string.greater_then_ten_mm)) ||
                    (selectedResult.equals(getResources().getString(R.string.ten_mm))))) {

                interpretationOfTSTSpinner.setSelection(0);
                interpretationOfTSTSpinner.setEnabled(false);

            } else if (selectedResult.equals(getResources().getString(R.string.five_to_nine_mm)) &&
                    weightPercentile.equals("5th Percentile or Less")) {
                //get the percentile values on selection of five to nine mm...
                interpretationOfTSTSpinner.setSelection(0);
                interpretationOfTSTSpinner.setEnabled(false);

            } else {
                interpretationOfTSTSpinner.setSelection(1);
                interpretationOfTSTSpinner.setEnabled(false);
            }

        }
        updateDisplay();
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    DatePickerDialog.OnDateSetListener resultDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            testResultCalender.set(Calendar.YEAR, year);
            testResultCalender.set(Calendar.MONTH, monthOfYear);
            testResultCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
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
