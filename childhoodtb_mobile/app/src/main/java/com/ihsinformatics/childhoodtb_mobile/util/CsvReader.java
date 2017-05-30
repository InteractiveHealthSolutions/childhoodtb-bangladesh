package com.ihsinformatics.childhoodtb_mobile.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.ihsinformatics.childhoodtb_mobile.App;
import com.ihsinformatics.childhoodtb_mobile.LoginActivity;
import com.ihsinformatics.childhoodtb_mobile.Preferences;
import com.ihsinformatics.childhoodtb_mobile.R;
import com.ihsinformatics.childhoodtb_mobile.model.Percentile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by Shujaat on 5/24/2017.
 */

public class CsvReader {
    private static CsvReader instance = null;
    Context context;
    ArrayList<Percentile> listPercentile;
    private static DatabaseUtil dbUtil;
    ServerService serverService;

    public CsvReader(Context context) {

        this.context = context;
        listPercentile = new ArrayList<Percentile>();
        serverService = new ServerService(context);
    }

    //this singleton Constructor
    public static CsvReader getInstance(Context singleTonContext) {
        if (instance == null)
            instance = new CsvReader(singleTonContext);
        return instance;
    }

    public void getPercentileVal() {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(
                    context.getResources().openRawResource(R.raw.weightpercentile)));
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                Percentile table = new Percentile();
                table.setGender(nextLine[0]);
                table.setAge(nextLine[1]);
                table.setP3(nextLine[5]);
                table.setP5(nextLine[6]);
                table.setP10(nextLine[7]);
                table.setP25(nextLine[8]);
                table.setP50(nextLine[9]);
                table.setP75(nextLine[10]);
                table.setP90(nextLine[11]);
                table.setP95(nextLine[12]);
                table.setP97(nextLine[13]);
                listPercentile.add(table);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //first delete the old data
        if (serverService.deletePercentile()) {
            Toast.makeText(context, "Successfully delete the Percentile Values",
                    Toast.LENGTH_SHORT).show();
        }
        //here we pass the list value for insertion purpose
        if (serverService.insertPercentile(listPercentile)) {
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            App.setIsPercentileRead(preferences.getBoolean(Preferences.IS_PERCENTILE_READ, true));
            Toast.makeText(context, "Successfully Insert the Percentile Values", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.insert_error), Toast.LENGTH_SHORT).show();

        }

    }

}
