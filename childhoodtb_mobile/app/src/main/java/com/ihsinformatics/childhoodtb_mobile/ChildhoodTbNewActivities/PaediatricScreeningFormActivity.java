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
import android.view.Gravity;
import android.view.KeyEvent;
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
import android.widget.TextView;

import com.ihsinformatics.childhoodtb_mobile.AbstractFragmentActivity;
import com.ihsinformatics.childhoodtb_mobile.App;
import com.ihsinformatics.childhoodtb_mobile.Barcode;
import com.ihsinformatics.childhoodtb_mobile.custom.MyButton;
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

/**
 * Created by Shujaat on 3/14/2017.
 */
public class PaediatricScreeningFormActivity extends AbstractFragmentActivity implements TextView.OnEditorActionListener {

    MyTextView formDateTextView;
    MyButton formDateButton;

    MyTextView screenedBeforeTextView;
    MySpinner screenedBefore;

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
    MyTextView ageModifierTextView;
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

    MyTextView contactHistoryConclusionTextView;
    MySpinner contactHistoryConclusion;

    MyTextView presumptiveTbCaseTextView;
    MySpinner presumptiveTbCase;

    MyTextView smokingConfirmationTextView;
    MySpinner smokingConfirmation;

    MyTextView patientIdTextView;
    MyEditText patientId;

    MyButton scanBarcode;

    String result = "";

    /**
     * Subclass representing Fragment for Pediatric-screening suspect form
     *
     * @author shujaat.hussain@ihsinformatics.com
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
        //this  piece of code is used for  hide the softKey from the screen initially ...
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TAG = "PaediatricScreeningFormActivity";
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
                R.array.screened_before_options), R.string.screened_before,
                R.string.option_hint);

        firstNameTextView = new MyTextView(context, R.style.text,
                R.string.first_name);

        firstName = new MyEditText(context, R.string.first_name,
                R.string.first_name_hint,
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 16,
                false);
        lastNameTextView = new MyTextView(context, R.style.text,
                R.string.last_name);
        lastName = new MyEditText(context, R.string.last_name,
                R.string.last_name_hint,
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 16,
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
        ageModifierTextView = new MyTextView(context, R.style.text, R.string.age_modifier);
        ageModifier = new MySpinner(context, getResources().getStringArray(
                R.array.age_modifier_options), R.string.age_modifier, R.string.option_hint);

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
                R.string.cough);
        cough = new MySpinner(context, getResources().getStringArray(
                R.array.cough_options), R.string.cough,
                R.string.cough_selection_hint);

        coughDurationTextView = new MyTextView(context, R.style.text,
                R.string.cough_weeks);

        coughDuration = new MySpinner(context, getResources().getStringArray(
                R.array.cough_durations), R.string.cough_weeks,
                R.string.cough_duration_options_hint);

        feverTextView = new MyTextView(context, R.style.text,
                R.string.fever_week);

        fever = new MySpinner(context, getResources().getStringArray(
                R.array.fever_options), R.string.fever_week,
                R.string.option_hint);

        nightSweatsTextView = new MyTextView(context, R.style.text,
                R.string.night_sweats);
        nightSweats = new MySpinner(context, getResources().getStringArray(
                R.array.night_sweats_options), R.string.night_sweats,
                R.string.option_hint);

        weightLossTextView = new MyTextView(context, R.style.text,
                R.string.weight_loss);
        weightLoss = new MySpinner(context, getResources().getStringArray(
                R.array.weight_loss_options), R.string.weight_loss,
                R.string.option_hint);

        childAppetiteTextView = new MyTextView(context, R.style.text,
                R.string.appetite_decreased);
        childAppetite = new MySpinner(context, getResources().getStringArray(
                R.array.appetite_decreased_options), R.string.appetite_decreased,
                R.string.option_hint);

        contactHistoryConclusionTextView = new MyTextView(context, R.style.text, R.string.conclusion);
        contactHistoryConclusion = new MySpinner(context, getResources().getStringArray(
                R.array.conclusion_options),
                R.string.conclusion, R.string.option_hint);

        presumptiveTbCaseTextView = new MyTextView(context, R.style.text, R.string.presumptive_tb_case);
        presumptiveTbCase = new MySpinner(context, getResources().getStringArray(
                R.array.presumptive_tb_case_options),
                R.string.presumptive_tb_case, R.string.option_hint);

        smokingConfirmationTextView = new MyTextView(context, R.style.text, R.string.smoking_confirmation_at_home);
        smokingConfirmation = new MySpinner(context, getResources().getStringArray(
                R.array.smoking_confirmation_options),
                R.string.smoking_confirmation_at_home, R.string.option_hint);

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

                {formDateTextView, formDateButton, screenedBeforeTextView, screenedBefore, firstNameTextView, firstName, lastNameTextView, lastName,
                        genderTextView, gender,
                },
                {dobTextView, dobPicker, contactHistoryConclusionTextView, contactHistoryConclusion,
                        ageTextView, age, ageModifierTextView, ageModifier, coughTextView, cough
                },
                {coughDurationTextView, coughDuration, presumptiveTbCaseTextView, presumptiveTbCase, smokingConfirmationTextView,
                        smokingConfirmation, feverTextView, fever, nightSweatsTextView, nightSweats
                },

                {childAppetiteTextView, childAppetite, patientIdTextView, patientId, scanBarcode}

        };

        // Create layouts and store in ArrayList
        groups = new ArrayList<ViewGroup>();
        for (int i = 0; i < PAGE_COUNT; i++) {
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

        // Set event listeners
        formDateButton.setOnClickListener(this);
        firstButton.setOnClickListener(this);
        lastButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        scanBarcode.setOnClickListener(this);
        navigationSeekbar.setOnSeekBarChangeListener(this);


        views = new View[]{ageModifier, screenedBefore,
                cough, coughDuration, fever, nightSweats, weightLoss,
                childAppetite, firstName, lastName, age,
                patientId, contactHistoryConclusion, presumptiveTbCase, smokingConfirmation};


        for (View v : views) {
            if (v instanceof Spinner) {
                ((Spinner) v).setOnItemSelectedListener(this);
            } else if (v instanceof CheckBox) {
                ((CheckBox) v).setOnCheckedChangeListener(this);
            }
        }

        pager.setOnPageChangeListener(this);
        age.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean state) {
                if (!state) {
                    updateDob();
                }
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
        boolean visible = spinner.getSelectedItemPosition() == 0;
        if (parent == cough) {

            coughDurationTextView.setEnabled(visible);
            coughDuration.setEnabled(visible);

        } else if (parent == ageModifier) {
            if (!"".equals(App.get(age))) {
                updateDob();
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

        //it update the selected date...
        dobPicker.init(dob.get(Calendar.YEAR),
                dob.get(Calendar.MONTH),
                dob.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker datePicker, int year,
                                              int month, int dayOfMonth) {
                        dob.set(year, month, dayOfMonth);
                    }
                });

        patientIdTextView.setEnabled(true);
        patientId.setEnabled(true);
        scanBarcode.setEnabled(true);

    }

    @Override
    public boolean validate() {

        boolean valid = true;

        StringBuffer message = new StringBuffer();
        // Validate mandatory controls
        View[] mandatory = {firstName, lastName};

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
            //check the entered dob is in future or not ....
            if (dob.getTime().after(Calendar.getInstance().getTime())) {

                valid = false;
                message.append(getResources().getString(R.string.date_of_birth) +
                        getResources().getString(
                                R.string.invalid_future_date) + "\n");
            }

        }
        // Validate ranges
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
            values.put("firstName", App.get(firstName));
            values.put("lastName", App.get(lastName));
            values.put("dob", App.getSqlDate(dob));
            values.put("gender", male.isChecked() ? "M" : "F");
            values.put("patientId", App.get(patientId));


            ///Create observations List (Each Observation have concept  with values)...e.g obs.add(String[]("concept name",concept values))
            final ArrayList<String[]> observations = new ArrayList<String[]>();

            observations.add(new String[]{"Screened Before",
                    App.get(screenedBefore).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(screenedBefore)});
            observations.add(new String[]{"Age Modifier",
                    App.get(ageModifier)});
            observations.add(new String[]{"Cough",
                    App.get(cough).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(cough)});
            if (cough.getSelectedItem().toString()
                    .equals(getResources().getString(R.string.yes))) {
                observations.add(new String[]{"Cough Duration",
                        App.get(coughDuration).equals(getResources().getString(R.string.do_not_know)) ?
                                getResources().getString(R.string.unknown) : App.get(coughDuration)});
            }
            observations.add(new String[]{"Fever",
                    App.get(fever).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(fever)});
            observations.add(new String[]{"Night Sweats",
                    App.get(nightSweats).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(nightSweats)});
            observations.add(new String[]{"Weight Loss",
                    App.get(weightLoss).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(weightLoss)});
            observations.add(new String[]{"Contact History",
                    App.get(contactHistoryConclusion).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(contactHistoryConclusion)});
            observations.add(new String[]{"TB Suspect",
                    App.get(presumptiveTbCase)});
            observations.add(new String[]{"Family Smoking",
                    App.get(smokingConfirmation)});
            observations.add(new String[]{"Appetite",
                    App.get(childAppetite).equals(getResources().getString(R.string.do_not_know)) ?
                            getResources().getString(R.string.unknown) : App.get(childAppetite)});


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
                    result = serverService.insertPaediatricScreenForm(
                            FormType.PAEDIATRIC_SCREENING, values,
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
                        App.getAlertDialog(PaediatricScreeningFormActivity.this,
                                AlertType.INFO,
                                getResources().getString(R.string.inserted))
                                .show();
                        initView(views);
                    } else {
                        App.getAlertDialog(PaediatricScreeningFormActivity.this,
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

    @Override
    public boolean onEditorAction(TextView v, int arg1, KeyEvent arg2) {
       /* if (v == age) {
            updateDisplay();
        }*/
        return true;
    }

    //this method use for updating the DatePicker using age..
    private void updateDob() {
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


}
