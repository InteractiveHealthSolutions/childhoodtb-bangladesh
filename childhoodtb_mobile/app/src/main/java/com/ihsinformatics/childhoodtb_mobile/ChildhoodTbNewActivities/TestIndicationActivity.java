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
 * Created by Shujaat on 3/29/2017.
 */
public class TestIndicationActivity extends AbstractFragmentActivity {

    MyTextView formDateTextView;
    MyButton formDateButton;

    MyTextView screeningFacilityTextView;
    MySpinner screeningFacilityOptions;

    MyTextView chestXrayTextView;
    MySpinner chestXraySpinner;

    MyTextView ultrasoundAbdomenTextView;
    MySpinner ultrasoundAbdomenSpinner;

    MyTextView ctScanTextView;
    MySpinner ctScanSpinner;

    MyTextView ctScanAreaTextView;
    MyEditText ctScanAreaEditText;

    MyTextView xpertMTBRIFTextView;
    MySpinner xpertMtbRifSpinner;

    MyTextView mantouxTextView;
    MySpinner mantouxSpinner;

    MyTextView smearMicroscopyTextView;
    MySpinner smearMicroscopySpinner;

    MyTextView histopathologyTextView;
    MySpinner histopathologySpinner;

    MyTextView esrTextView;
    MySpinner esrSpinner;

    MyTextView histopathologySampleTextView;
    MyEditText histopathologySampleSiteEditText;

    MyTextView patientIdTextView;
    MyEditText patientId;
    MyButton scanBarcode;
    MyButton validatePatientId;
    String result = "";

    @Override
    public void createViews(Context context) {
        //this  piece of code is used for  hide the softKey from the screen initially ...
        TestIndicationActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        pager = (ViewPager) findViewById(R.template_id.pager);
        TAG = "EndFollowUpActivity";

        formDateTextView = new MyTextView(context,
                R.style.text, R.string.form_date);
        formDateButton = new MyButton(context,
                R.style.button, R.drawable.custom_button_beige,
                R.string.form_date, R.string.form_date);

        chestXrayTextView = new MyTextView(context,
                R.style.text, R.string.chest_x_ray);
        chestXraySpinner = new MySpinner(context,
                getResources().getStringArray(R.array.chest_x_ray_options),
                R.string.chest_x_ray, R.string.option_hint);

        ultrasoundAbdomenTextView = new MyTextView(context,
                R.style.text, R.string.ultrasound_abdomen);
        ultrasoundAbdomenSpinner = new MySpinner(context,
                getResources().getStringArray(R.array.ultrasound_abdomen_options),
                R.string.ultrasound_abdomen, R.string.option_hint);
        ctScanTextView = new MyTextView(context,
                R.style.text, R.string.ct_scan);
        ctScanSpinner = new MySpinner(context,
                getResources().getStringArray(R.array.ct_scan_options),
                R.string.ct_scan, R.string.option_hint);

        ctScanAreaTextView = new MyTextView(context,
                R.style.text, R.string.ct_scan);
        ctScanAreaEditText = new MyEditText(context, R.string.ct_scan_area,
                R.string.ct_scan_area_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, 25, false);

        xpertMTBRIFTextView = new MyTextView(context,
                R.style.text, R.string.xpert_mtb_rif);
        xpertMtbRifSpinner = new MySpinner(context,
                getResources().getStringArray(R.array.xpert_mtb_rif_options),
                R.string.xpert_mtb_rif, R.string.option_hint);

        mantouxTextView = new MyTextView(context,
                R.style.text, R.string.mantoux);
        mantouxSpinner = new MySpinner(context,
                getResources().getStringArray(R.array.mantoux_options),
                R.string.mantoux, R.string.option_hint);

        smearMicroscopyTextView = new MyTextView(context,
                R.style.text, R.string.smear_microscopy);
        smearMicroscopySpinner = new MySpinner(context,
                getResources().getStringArray(R.array.smear_microscopy_options),
                R.string.smear_microscopy, R.string.option_hint);

        histopathologyTextView = new MyTextView(context,
                R.style.text, R.string.histopathology);
        histopathologySpinner = new MySpinner(context,
                getResources().getStringArray(R.array.histopathology_options),
                R.string.histopathology, R.string.option_hint);

        esrTextView = new MyTextView(context,
                R.style.text, R.string.esr);
        esrSpinner = new MySpinner(context,
                getResources().getStringArray(R.array.esr_options),
                R.string.esr, R.string.option_hint);

        histopathologySampleTextView = new MyTextView(context, R.style.text,
                R.string.histopathology_sample);
        histopathologySampleSiteEditText = new MyEditText(context, R.string.histopathology_sample,
                R.string.histopathology_sample_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, RegexUtil.idLength, false);

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
                        chestXrayTextView, chestXraySpinner, ctScanTextView, ctScanSpinner,
                        ctScanAreaTextView, ctScanAreaEditText,ultrasoundAbdomenTextView, ultrasoundAbdomenSpinner
                },
                { mantouxTextView, mantouxSpinner, smearMicroscopyTextView,
                        smearMicroscopySpinner, xpertMTBRIFTextView, xpertMtbRifSpinner, histopathologyTextView, histopathologySpinner, histopathologySampleTextView,
                        histopathologySampleSiteEditText, esrTextView, esrSpinner,
                }
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

        Log.i("groupSize", "" + groups.size());
        FragmentManager fragmentManager = getSupportFragmentManager();
        PediatricPresumptveFragmentPagerAdapter pagerAdapter = new PediatricPresumptveFragmentPagerAdapter(
                fragmentManager, groups.size());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(groups.size());

        views = new View[]{screeningFacilityOptions, ctScanSpinner, chestXraySpinner,
                ultrasoundAbdomenSpinner, esrSpinner, xpertMtbRifSpinner, histopathologySampleSiteEditText, histopathologySpinner,
                formDateButton, ctScanSpinner, ctScanAreaEditText, smearMicroscopySpinner, mantouxSpinner,
                patientId};


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
        navigationSeekbar.setOnSeekBarChangeListener(this);

    }

    @Override
    public void initView(View[] views) {
        super.initView(views);

        formDate = Calendar.getInstance();
        ctScanAreaTextView.setEnabled(false);
        ctScanAreaEditText.setEnabled(false);
        histopathologySampleTextView.setEnabled(false);
        histopathologySampleSiteEditText.setEnabled(false);

        updateDisplay();
    }

    @Override
    public void updateDisplay() {
        formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));
    }

    @Override
    public boolean validate() {
        boolean valid = true;
        StringBuffer message = new StringBuffer();

        if (App.get(patientId).equals("")) {
            valid = false;
            message.append(patientId.getTag().toString() + ". ");
            patientId.setHintTextColor(getResources().getColor(R.color.Red));
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

        if (ctScanSpinner.getSelectedItem().toString()
                .equals(getResources().getString(R.string.yes))
                && App.get(ctScanAreaEditText).equals("")) {
            valid = false;
            message.append(ctScanAreaEditText.getTag().toString() + ". ");
            ctScanAreaEditText.setHintTextColor(getResources().getColor(R.color.Red));

        }

        if (histopathologySpinner.getSelectedItem().toString()
                .equals(getResources().getString(R.string.yes))
                && App.get(histopathologySampleSiteEditText).equals("")) {
            valid = false;
            message.append(histopathologySampleSiteEditText.getTag().toString() + ". ");
            histopathologySampleSiteEditText.setHintTextColor(getResources().getColor(R.color.Red));

        }

        //check for form date. Form date must not be in future ...
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
            values.put("patientId", App.get(patientId));

            final ArrayList<String[]> observations = new ArrayList<String[]>();

            observations
                    .add(new String[]{"CXR", App.get(chestXraySpinner)});

            observations.add(new String[]{"Ultrasound Abdomen",
                    App.get(ultrasoundAbdomenSpinner)});

            observations.add(new String[]{"CT Scan",
                    App.get(ctScanSpinner)});

            if (ctScanSpinner.getSelectedItem().toString()
                    .equals(getResources().getString(R.string.yes))) {
                observations.add(new String[]{"CT Scan Area",
                        App.get(ctScanAreaEditText)});
            }

            observations.add(new String[]{"GXP",
                    App.get(xpertMtbRifSpinner)});

            observations.add(new String[]{
                    "Mantoux",
                    App.get(mantouxSpinner)});

            observations.add(new String[]{
                    "Smear Microscopy",
                    App.get(smearMicroscopySpinner)});

            observations.add(new String[]{
                    "Histopathology",
                    App.get(histopathologySpinner)});

            if (histopathologySpinner.getSelectedItem().toString()
                    .equals(getResources().getString(R.string.yes))) {
                observations.add(new String[]{
                        "Histopathology Site",
                        App.get(histopathologySampleSiteEditText)});
            }

            observations.add(new String[]{
                    "CBC/ESR",
                    App.get(esrSpinner)});


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
                    result = serverService.saveTestIndication(
                            FormType.TEST_INDICATION, values,
                            observations.toArray(new String[][]{}));

                    return result;
                }

                @Override
                protected void onProgressUpdate(String... values) {
                }

                ;

                @Override
                protected void onPostExecute(String result) {

                    super.onPostExecute(result);
                    loading.dismiss();
                    if (result.equals("SUCCESS")) {
                        App.getAlertDialog(TestIndicationActivity.this,
                                AlertType.INFO,
                                getResources().getString(R.string.inserted))
                                .show();
                        initView(views);
                    } else {
                        App.getAlertDialog(TestIndicationActivity.this,
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
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

        MySpinner spinner = (MySpinner) parent;
        boolean visible = spinner.getSelectedItemPosition() == 0;

        if (parent == ctScanSpinner) {
            ctScanAreaTextView.setEnabled(visible);
            ctScanAreaEditText.setEnabled(visible);
        }
        if (parent == histopathologySpinner) {
            histopathologySampleTextView.setEnabled(visible);
            histopathologySampleSiteEditText.setEnabled(visible);

        }

        updateDisplay();

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
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