/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * This class is used to hold data and other methods that are globally accessed
 */

package com.ihsinformatics.childhoodtb_mobile;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ihsinformatics.childhoodtb_mobile.model.OpenMrsObject;
import com.ihsinformatics.childhoodtb_mobile.shared.AlertType;
import com.ihsinformatics.childhoodtb_mobile.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author owais.hussain@irdresearch.org
 */
public class App {
    private static String server = "";
    private static String username = "";
    private static String password = "";
    private static String location = "";
    private static String supportContact = "";
    private static String city = "";
    private static String country = "";
    private static String version = "";
    private static int delay = 3000;
    private static boolean useSsl = true;
    private static boolean autoLogin = true;
    private static boolean offlineMode = false;

    private static OpenMrsObject currentUser;
    private static Locale currentLocale;

    public static void setThreadSafety(boolean state) {
        StrictMode.ThreadPolicy policy = StrictMode.getThreadPolicy();
        if (state) {
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        } else {
            policy = new StrictMode.ThreadPolicy.Builder().detectAll().build();
        }
        StrictMode.setThreadPolicy(policy);
    }

    public static String getServer() {
        return server;
    }

    public static void setServer(String server) {
        App.server = server;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        App.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        App.password = password;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        App.location = location;
    }

    public static String getSupportContact() {
        return supportContact;
    }

    public static void setSupportContact(String supportContact) {
        App.supportContact = supportContact;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        App.city = city;
    }

    public static String getCountry() {
        return country;
    }

    public static void setCountry(String country) {
        App.country = country;
    }

    public static int getTheme() {
        return R.style.DayTheme;
    }

    public static int getDelay() {
        return delay;
    }

    public static void setDelay(int delay) {
        App.delay = delay;
    }

    public static boolean isUseSsl() {
        return useSsl;
    }

    public static boolean isAutoLogin() {
        return autoLogin;
    }

    public static void setAutoLogin(boolean autoLogin) {
        App.autoLogin = autoLogin;
    }

    public static void setUseSsl(boolean useSsl) {
        App.useSsl = useSsl;
    }

    public static boolean isOfflineMode() {
        return offlineMode;
    }

    public static void setOfflineMode(boolean offlineMode) {
        App.offlineMode = offlineMode;
    }

    public static OpenMrsObject getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(OpenMrsObject currentUser) {
        App.currentUser = currentUser;
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static void setCurrentLocale(Locale currentLocale) {
        App.currentLocale = currentLocale;
    }

    /**
     * Returns true if system language is Right-to-Left
     *
     * @return
     */
    public static boolean isLanguageRTL() {
        String code = currentLocale.getLanguage();
        if (code.equals("ar") || code.equals("fa") || code.equals("he")
                || code.equals("ur"))
            return true;
        return false;
    }

    public static void setVersion(String version) {
        App.version = version;
    }

    public static String getVersion() {
        return version;
    }

    /**
     * Returns selected value in string, depending on the view passed. If no
     * value is present, an empty string will be returned
     *
     * @param view
     * @return
     */
    public static String get(View view) {
        String str = null;
        if (view instanceof TextView) {
            str = ((TextView) view).getText().toString();
        } else if (view instanceof Spinner) {
            str = ((Spinner) view).getSelectedItem().toString();
        }
        return (str == null ? "" : str);
    }

    /**
     * Returns instance of AlertDialog, based on the type provided
     *
     * @param context
     * @param type
     * @param message
     * @return
     */
    public static AlertDialog getAlertDialog(Context context, AlertType type,
                                             String message) {
        AlertDialog dialog;
        Builder builder = new Builder(context);
        builder.setMessage(message);
        switch (type) {
            case ERROR:
                builder.setIcon(R.drawable.error);
                break;
            case INFO:
                builder.setIcon(R.drawable.info);
                break;
            case QUESTION:
                builder.setIcon(R.drawable.question);
                break;
        }
        dialog = builder.create();
        dialog.setTitle(type.toString());
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", listener);
        return dialog;
    }

    /**
     * Returns date in sql date string format
     *
     * @param date
     * @return
     */
    public static String getSqlDate(Calendar date) {

        return DateFormat.format("dd-MMM-yyyy", date).toString();
    }

    /**
     * Returns date in sql date string format
     *
     * @param date
     * @return
     */
    public static String getSqlDateTime(Calendar date) {
        return DateFormat.format("yyyy-MM-dd hh:mm:ss", date).toString();
    }

    /**
     * Returns date in sql date string format
     *
     * @param date
     * @return
     */
    public static String getSqlDate(Date date) {
        return DateFormat.format("yyy-MM-dd", date).toString();
    }

    /**
     * Returns date in sql date string format
     *
     * @param date
     * @return
     */
    public static String getSqlDateTime(Date date) {
        return DateFormat.format("yyy-MM-dd hh:mm:ss", date).toString();
    }

    /**
     * Returns string array adapter containing the items passed in parameter.
     *
     * @param context
     * @param list
     * @return
     */
    public static ArrayAdapter<String> getAdapter(Context context, String[] list) {
        return new ArrayAdapter<String>(context,
                android.R.layout.select_dialog_item, list);
    }
}
