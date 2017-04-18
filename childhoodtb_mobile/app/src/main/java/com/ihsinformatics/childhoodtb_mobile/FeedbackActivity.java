/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 *
 */

package com.ihsinformatics.childhoodtb_mobile;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ihsinformatics.childhoodtb_mobile.custom.MyEditText;
import com.ihsinformatics.childhoodtb_mobile.custom.MySpinner;
import com.ihsinformatics.childhoodtb_mobile.custom.MyTextView;
import com.ihsinformatics.childhoodtb_mobile.shared.AlertType;
import com.ihsinformatics.childhoodtb_mobile.shared.FormType;
import com.ihsinformatics.childhoodtb_mobile.R;


import java.util.ArrayList;
import java.util.Collections;

/**
 * @author owais.hussain@irdresearch.org
 */
public class FeedbackActivity extends AbstractFragmentActivity {
    // Views displayed in pages, sorted w.r.t. appearance on pager
    private MyTextView feedbackTypeTextView;
    private MySpinner feedbackType;
    private MyTextView feedbackTextView;
    private MyEditText feedback;

    /**
     * Subclass representing Fragment for feedback form
     *
     * @author owais.hussain@irdresearch.org
     */
    @SuppressLint("ValidFragment")
    class FeedbackFragment extends Fragment {
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
     * Subclass for Pager Adapter. Uses FeedbackFragment subclass as items
     *
     * @author owais.hussain@irdresearch.org
     */
    class FeedbackFragmentPagerAdapter extends FragmentPagerAdapter {
        /**
         * Constructor of the class
         */
        public FeedbackFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        /**
         * This method will be invoked when a page is requested to create
         */
        @Override
        public Fragment getItem(int arg0) {
            FeedbackFragment fragment = new FeedbackFragment();
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
    public void createViews(final Context context) {
        FORM_NAME = FormType.FEEDBACK;
        TAG = "FeedbackActivity";
        PAGE_COUNT = 1;
        pager = (ViewPager) findViewById(R.template_id.pager);
        navigationSeekbar.setMax(PAGE_COUNT - 1);
        navigatorLayout = (LinearLayout) findViewById(R.template_id.navigatorLayout);
        // If the form consists only of single page, then hide the
        // navigatorLayout
        if (PAGE_COUNT < 2) {
            navigatorLayout.setVisibility(View.GONE);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FeedbackFragmentPagerAdapter pagerAdapter = new FeedbackFragmentPagerAdapter(
                fragmentManager);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(PAGE_COUNT);
        // Create views for pages
        feedbackTypeTextView = new MyTextView(context, R.style.text,
                R.string.feedback_type);
        feedbackType = new MySpinner(context, getResources().getStringArray(
                R.array.feedback_types), R.string.feedback_type,
                R.string.option_hint);
        feedbackTextView = new MyTextView(context, R.style.text,
                R.string.feedback);
        feedback = new MyEditText(context, R.string.feedback,
                R.string.feedback_hint, InputType.TYPE_TEXT_FLAG_MULTI_LINE,
                R.style.edit, -1, true);
        feedback.setMinLines(5);
        feedback.setMaxHeight(10);
        View[][] viewGroups = {{feedbackTypeTextView, feedbackType,
                feedbackTextView, feedback}};
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
        firstButton.setOnClickListener(this);
        lastButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        navigationSeekbar.setOnSeekBarChangeListener(this);
        views = new View[]{feedbackType, feedback};
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
    public void initView(View[] views) {
        super.initView(views);
    }

    @Override
    public void updateDisplay() {
        // Not implemented
    }

    @Override
    public boolean validate() {
        boolean valid = true;
        StringBuffer message = new StringBuffer();
        // Validate mandatory controls
        View[] mandatory = {feedback};
        for (View view : mandatory) {
            if (App.get(view).equals("")) {
                valid = false;
                message.append(view.getTag() + ". ");
                // Turn hint color Red
                ((EditText) view).setHintTextColor(getResources().getColor(
                        R.color.Red));
            } else {
                // Turn hint color back to Black
                ((TextView) view).setHintTextColor(getResources().getColor(
                        R.color.Black));
            }
        }
        if (!valid) {
            message.append(getResources().getString(R.string.empty_data) + "\n");
        }
        // Validate data
        // Validate range
        if (!valid) {
            App.getAlertDialog(this, AlertType.ERROR, message.toString())
                    .show();
        }
        return valid;
    }

    public boolean submit() {
        if (validate()) {
            final ContentValues values = new ContentValues();
            values.put("formDate", App.getSqlDate(formDate));
            values.put("location", App.getLocation());
            values.put("feedbackType", App.get(feedbackType));
            values.put("feedback", App.get(feedback));
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
                    // Send an SMS to support phone number
                    String smsText = App.getUsername() + " "
                            + App.get(feedbackType) + " \"" + App.get(feedback)
                            + "\"";
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(App.getSupportContact(), null, smsText,
                            null, null);
                    String result = serverService.saveFeedback(FORM_NAME,
                            values);
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
                        App.getAlertDialog(FeedbackActivity.this,
                                AlertType.INFO,
                                getResources().getString(R.string.inserted))
                                .show();
                        initView(views);
                    } else {
                        App.getAlertDialog(FeedbackActivity.this,
                                AlertType.ERROR, result).show();
                    }
                }
            };
            updateTask.execute("");
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == firstButton) {
            gotoFirstPage();
        } else if (view == lastButton) {
            gotoLastPage();
        } else if (view == clearButton) {
            initView(views);
        } else if (view == saveButton) {
            submit();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean state) {
        // Not implemented
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // Not implemented
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
