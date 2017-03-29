/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */

package com.ihsinformatics.childhoodtb_mobile.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	public static final String FE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String SQL_DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String FE_FORMAT_TRUNC = "dd/MM/yyyy";
	public static final String DOB_FORMAT = "dd/MM/yyyy";
	public static final String SQL_DATE = "yyyy-MM-dd";

	public static Date getDateFromString(String string, String format)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(string);
	}

	public static String convertToSQL(String string, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date1;
		try {
			date1 = sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

		sdf.applyPattern(SQL_DATETIME);
		return sdf.format(date1);
	}

	public static String getSQLDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(SQL_DATETIME);
		return sdf.format(date);
	}
}
