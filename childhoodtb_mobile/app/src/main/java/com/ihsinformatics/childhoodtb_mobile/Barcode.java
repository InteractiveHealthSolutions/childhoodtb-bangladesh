/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * Barcode Scanning Activity. This will require Barcode scanner to be installed in the device
 */

package com.ihsinformatics.childhoodtb_mobile;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class Barcode {

	public static final int BARCODE_RESULT = 0;
	public static final int BARCODE_RESULT_INDEX_ID = 1;
	public static final String BARCODE_INTENT = "com.google.zxing.client.android.SCAN";
	public static final String SCAN_MODE = "SCAN_MODE";
	public static final String QR_MODE = "QR_MODE";
	public static final String SCAN_RESULT = "SCAN_RESULT";
	public static final String SCAN_RESULT_FORMAT = "SCAN_RESULT_FORMAT";
}
