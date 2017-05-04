package com.ihsinformatics.childhoodtb_mobile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ihsinformatics.childhoodtb_mobile.ChildhoodTbActivities.ContactRegistryActivity;
import com.ihsinformatics.childhoodtb_mobile.model.Report;
import com.ihsinformatics.childhoodtb_mobile.shared.AlertType;
import com.ihsinformatics.childhoodtb_mobile.shared.FormType;
import com.ihsinformatics.childhoodtb_mobile.util.RegexUtil;
import com.ihsinformatics.childhoodtb_mobile.util.ServerService;

import java.util.ArrayList;
import java.util.Locale;


public class ContactReportActivity extends Activity implements View.OnClickListener {

    public static ServerService serverService;
    public static ProgressDialog loading;
    public static final String TAG = "ContactReportActivity";
    Button scanBarcodeButton;
    Button searchContactButton;
    ScrollView searchResultsScrollView;
    Animation alphaAnimation;
    EditText patientId;
    TextView numberOfContact, numberOfContactAdult, numberOfChildhood,
            numberOfContactScreened, numberOfSymptomatic, numberOfContactEligible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_repor);
        ContactReportActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        serverService = new ServerService(this);
        loading = new ProgressDialog(this);

        scanBarcodeButton = (Button) findViewById(R.reports_id.scanBarcodeButton);
        searchContactButton = (Button) findViewById(R.reports_id.searchContactButton);
        patientId = (EditText) findViewById(R.reports_id.patientIdEditText);
        searchResultsScrollView = (ScrollView) findViewById(R.reports_id.resultsScrollView);
        numberOfContact = (TextView) findViewById(R.id.no_contact_result);
        numberOfContactAdult = (TextView) findViewById(R.id.no_contact_adult_result);
        numberOfChildhood = (TextView) findViewById(R.id.no_contact_childhood_result);
        numberOfContactScreened = (TextView) findViewById(R.id.no_contact_screened_result);
        numberOfContactEligible = (TextView) findViewById(R.id.no_contact_eligible_result);
        numberOfSymptomatic = (TextView) findViewById(R.id.no_contact_symptomatic_result);
        alphaAnimation = AnimationUtils.loadAnimation(this,
                R.anim.alpha_animation);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //we click listener here ...
        scanBarcodeButton.setOnClickListener(this);
        searchContactButton.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent loginIntent = new Intent(getApplicationContext(),
                MainMenuActivity.class);
        startActivity(loginIntent);
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(alphaAnimation);
        if (view == scanBarcodeButton) {

            Intent intent = new Intent(Barcode.BARCODE_INTENT);
            intent.putExtra(Barcode.SCAN_MODE, Barcode.QR_MODE);
            startActivityForResult(intent, Barcode.BARCODE_RESULT);
        } else if (view == searchContactButton) {
            //checkPatientId method is use for validation of patient id ...
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

                        ArrayList<Report> response = serverService.searchContact(App.get(patientId));
                        return response;
                    }

                    @Override
                    protected void onProgressUpdate(String... values) {
                    }

                    @Override
                    protected void onPostExecute(Object result) {
                        super.onPostExecute(result);
                        loading.dismiss();
                        ArrayList<Report> reports = (ArrayList<Report>) result;
                        StringBuilder errorMessage = new StringBuilder();
                        if (result == null) {
                            AlertDialog alertDialog = App.getAlertDialog(ContactReportActivity.this,
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
                                            //clearScreen();
                                        }
                                    });
                            alertDialog.show();
                        } else {
                            if (reports.size() == 0
                                    || reports.isEmpty()) {
                                errorMessage.append(
                                        getResources().getString(R.string.not_found_contact_registry)
                                );
                                App.getAlertDialog(ContactReportActivity.this,
                                        AlertType.ERROR, errorMessage.toString()).show();
                            } else if (reports.size() > 0 && reports != null) {

                                numberOfContact.setText(reports.get(0).getNumberOfContact());
                                numberOfChildhood.setText(reports.get(0).getNumberOfChildhood());
                                numberOfContactAdult.setText(reports.get(0).getNumberOfAdult());
                                numberOfContactScreened.setText(reports.get(0).getNumberOfScreened());
                                numberOfSymptomatic.setText(reports.get(0).getNumberOfSymptomatic());
                                numberOfContactEligible.setText(reports.get(0).getNumberOfContactsEligibleForPti());
                            }
                        }
                    }
                };
                getTask.execute("");
            }

        }
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

    public boolean checkPatientId() {
        boolean isIndexId = true;
        final String indexPatientId = App.get(patientId);
        if (!indexPatientId.equals("")) {
            if (RegexUtil.matchId(App.get(patientId))) {

                if (!RegexUtil.isValidId(App.get(patientId))) {
                    isIndexId = false;
                    App.getAlertDialog(ContactReportActivity.this,
                            AlertType.ERROR, patientId.getTag().toString() + ":"
                                    + getResources().getString(R.string.invalid_data)).show();

                    patientId.setTextColor(getResources().getColor(
                            R.color.Red));
                }
            } else {
                isIndexId = false;
                App.getAlertDialog(ContactReportActivity.this,
                        AlertType.ERROR, patientId.getTag().toString() + ":"
                                + getResources().getString(R.string.invalid_data)).show();

                patientId.setTextColor(getResources().getColor(
                        R.color.Red));
            }
        } else {
            isIndexId = false;
            App.getAlertDialog(ContactReportActivity.this,
                    AlertType.ERROR, getResources().getString(R.string.empty_data_indexId)).show();

        }
        return isIndexId;
    }

    private void clearScreen() {
        patientId.setText("");
        numberOfContact.setText("");
        numberOfContactScreened.setText("");
        numberOfContactAdult.setText("");
        numberOfChildhood.setText("");
        numberOfContactEligible.setText("");
        numberOfSymptomatic.setText("");
    }
}