package com.ihsinformatics.childhoodtb_mobile.ChildhoodTbActivities;

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
 * Created by Shujaat on 3/28/2017.
 */
public class EndFollowUpActivity extends AbstractFragmentActivity {

    MyTextView formDateTextView;
    MyButton formDateButton;

    MyTextView reasonForEndOfFollowUpTextView;
    MySpinner reasonForEndOfFollowUp;

    MyTextView otherTextView;
    MyEditText other;

    MyTextView reasonForlossFollowUpTextView;
    MyEditText reasonForlossFollowUp;

    MyTextView otherFacilityNameTextView;
    MyEditText otherFacilityName;

    MyTextView patientIdTextView;
    MyEditText patientId;
    MyButton scanBarcode;
    boolean isOtherFieldIsRequired, isOtherFacilityNameIsRequired,
            isReasonLosFollowUpIsRequired = false;

    String result = "";


    @Override
    public void createViews(Context context) {
        //this  piece of code is used for  hide the softKey from the screen initially ...
        EndFollowUpActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        pager = (ViewPager) findViewById(R.template_id.pager);
        TAG = "EndFollowUpActivity";

        formDateTextView = new MyTextView(context,
                R.style.text, R.string.form_date);
        formDateButton = new MyButton(context,
                R.style.button, R.drawable.custom_button_beige,
                R.string.form_date, R.string.form_date);

        reasonForEndOfFollowUpTextView = new MyTextView(context,
                R.style.text, R.string.reason_for_end_to_followup);
        reasonForEndOfFollowUp = new MySpinner(context,
                getResources().getStringArray(R.array.reason_for_followup_options),
                R.string.reason_for_end_to_followup, R.string.option_hint);
        otherTextView = new MyTextView(context,
                R.style.text, R.string.other);
        other = new MyEditText(context, R.string.other,
                R.string.other_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, 25, false);
        otherFacilityNameTextView = new MyTextView(context,
                R.style.text, R.string.other_facility_name);
        otherFacilityName = new MyEditText(context, R.string.other_facility_name,
                R.string.other_facility_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, 25, false);
        reasonForlossFollowUpTextView = new MyTextView(context,
                R.style.text, R.string.reason_loss_to_followup);

        reasonForlossFollowUp = new MyEditText(context, R.string.reason_loss_to_followup,
                R.string.reason_loss_followup_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, 25, false);

        patientIdTextView = new MyTextView(context, R.style.text,
                R.string._patient_id);

        patientId = new MyEditText(context, R.string._patient_id,
                R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT,
                R.style.edit, RegexUtil.idLength, false);

        scanBarcode = new MyButton(context, R.style.button,
                R.drawable.custom_button_beige, R.string.scan_barcode,
                R.string.scan_barcode);
        //define the navigation Fragments
        View[][] viewGroups = {
                {formDateTextView, formDateButton, patientIdTextView, patientId,
                        scanBarcode, reasonForEndOfFollowUpTextView, reasonForEndOfFollowUp,
                        reasonForlossFollowUpTextView, reasonForlossFollowUp, otherTextView, other,
                        otherFacilityNameTextView, otherFacilityName,
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

        views = new View[]{other, otherFacilityName, patientId,
                reasonForEndOfFollowUp};


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
        updateDisplay();
        otherFacilityNameTextView.setVisibility(View.VISIBLE);
        other.setVisibility(View.GONE);
        otherTextView.setVisibility(View.GONE);
        otherFacilityName.setVisibility(View.VISIBLE);
        reasonForlossFollowUpTextView.setVisibility(View.GONE);
        reasonForlossFollowUp.setVisibility(View.GONE);
    }

    @Override
    public void updateDisplay() {
        formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));
    }

    @Override
    public boolean validate() {
        boolean valid = true;
        StringBuilder message = new StringBuilder();
        View[] mandatory = {};

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
        if (isOtherFieldIsRequired) {
            if (App.get(other).equals("")) {
                valid = false;
                message.append(other.getTag().toString() + ":\n" +
                        getResources().getString(R.string.empty_data));
                other.setHintTextColor(getResources().getColor(R.color.Red));
            } else if (!RegexUtil.isWord(App.get(other))) {

                valid = false;
                message.append(other.getTag().toString()
                        + ": "
                        + getResources().getString(
                        R.string.invalid_data) + "\n");
                other.setTextColor(getResources().getColor(
                        R.color.Red));
            }
        }
        if (isReasonLosFollowUpIsRequired) {
            if (App.get(reasonForlossFollowUp).equals("")) {
                valid = false;
                message.append(reasonForlossFollowUp.getTag().toString() + ":\n" +
                        getResources().getString(R.string.empty_data));
                reasonForlossFollowUp.setHintTextColor(getResources().getColor(R.color.Red));
            } else if (!RegexUtil.isWord(App.get(reasonForlossFollowUp))) {

                valid = false;
                message.append(reasonForlossFollowUp.getTag().toString()
                        + ": "
                        + getResources().getString(
                        R.string.invalid_data) + "\n");
                reasonForlossFollowUp.setTextColor(getResources().getColor(
                        R.color.Red));
            }
        }
        if (isOtherFacilityNameIsRequired) {
            if (App.get(otherFacilityName).equals("")) {
                valid = false;
                message.append(otherFacilityName.getTag().toString() + ":\n" +
                        getResources().getString(R.string.empty_data));
                otherFacilityName.setHintTextColor(getResources().getColor(R.color.Red));
            } else if (!RegexUtil.isWord(App.get(otherFacilityName))) {

                valid = false;
                message.append(otherFacilityName.getTag().toString()
                        + ": "
                        + getResources().getString(
                        R.string.invalid_data) + "\n");
                otherFacilityName.setTextColor(getResources().getColor(
                        R.color.Red));
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
            values.put("patientId", App.get(patientId));


            final ArrayList<String[]> observations = new ArrayList<String[]>();

            observations.add(new String[]{"Reason End Follow-up",
                    App.get(reasonForEndOfFollowUp)});

            if (reasonForEndOfFollowUp.getSelectedItem().toString()
                    .equals(getResources().getString(R.string.referred_to_another_facility))) {
                observations.add(new String[]{"Referred to another Facility",
                        App.get(otherFacilityName)});
            } else if (reasonForEndOfFollowUp.getSelectedItem().toString()
                    .equals(getResources().getString(R.string.other))) {
                observations.add(new String[]{"Other Reason",
                        App.get(other)});
            } else if (reasonForEndOfFollowUp.getSelectedItem().toString()
                    .equals(getResources().getString(R.string.loss_to_followup))) {
                observations.add(new String[]{"Follow-up Lost",
                        App.get(reasonForlossFollowUp)});
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
                    ///insertPaediatricScreenForm method use to Server call and also use for makign the JsonObject..
                    result = serverService.insertEndFollowUpForm(
                            FormType.END_FOLLOW_UP, values,
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
                        App.getAlertDialog(EndFollowUpActivity.this,
                                AlertType.INFO,
                                getResources().getString(R.string.inserted))
                                .show();
                        initView(views);
                    } else {
                        App.getAlertDialog(EndFollowUpActivity.this,
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView == reasonForEndOfFollowUp) {

            if (adapterView.getSelectedItem().toString().equals(getString(R.string.referred_to_another_facility))) {
                otherFacilityNameTextView.setVisibility(View.VISIBLE);
                otherFacilityName.setVisibility(View.VISIBLE);
                otherTextView.setVisibility(View.GONE);
                other.setVisibility(View.GONE);
                reasonForlossFollowUpTextView.setVisibility(View.GONE);
                reasonForlossFollowUp.setVisibility(View.GONE);
                isOtherFieldIsRequired = false;
                isOtherFacilityNameIsRequired = true;
                isReasonLosFollowUpIsRequired = false;

            } else if (adapterView.getSelectedItem().toString().equals(getString(R.string.other))) {
                Log.i("test", "other");

                otherTextView.setVisibility(View.VISIBLE);
                other.setVisibility(View.VISIBLE);
                isOtherFieldIsRequired = true;
                otherFacilityNameTextView.setVisibility(View.GONE);
                otherFacilityName.setVisibility(View.GONE);
                reasonForlossFollowUpTextView.setVisibility(View.GONE);
                reasonForlossFollowUp.setVisibility(View.GONE);
                isOtherFacilityNameIsRequired = false;
                isReasonLosFollowUpIsRequired = false;

            } else if (adapterView.getSelectedItem().toString().equals(getString(R.string.loss_to_followup))) {

                otherTextView.setVisibility(View.GONE);
                other.setVisibility(View.GONE);
                otherFacilityNameTextView.setVisibility(View.GONE);
                otherFacilityName.setVisibility(View.GONE);
                reasonForlossFollowUpTextView.setVisibility(View.VISIBLE);
                reasonForlossFollowUp.setVisibility(View.VISIBLE);
                isOtherFieldIsRequired = false;
                isOtherFacilityNameIsRequired = false;
                isReasonLosFollowUpIsRequired = true;
            } else {
                otherTextView.setVisibility(View.GONE);
                other.setVisibility(View.GONE);
                otherFacilityNameTextView.setVisibility(View.GONE);
                otherFacilityName.setVisibility(View.GONE);
                reasonForlossFollowUpTextView.setVisibility(View.GONE);
                reasonForlossFollowUp.setVisibility(View.GONE);
                isOtherFieldIsRequired = false;
                isOtherFacilityNameIsRequired = false;
                isReasonLosFollowUpIsRequired = false;
            }
        }

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
