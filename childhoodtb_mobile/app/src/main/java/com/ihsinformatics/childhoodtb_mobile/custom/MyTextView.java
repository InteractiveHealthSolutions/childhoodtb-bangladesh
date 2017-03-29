/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * Custom TextView for ease
 */

package com.ihsinformatics.childhoodtb_mobile.custom;

import android.content.Context;
import android.widget.TextView;

import com.ihsinformatics.childhoodtb_mobile.R;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class MyTextView extends TextView {
	public MyTextView(Context context) {
		super(context);
	}

	/**
	 * 
	 * @param context
	 * @param style
	 *            Style Id from resources. Pass -1 to keep default style
	 * @param text
	 *            Text Id from resources. Pass -1 if not to be set
	 */
	public MyTextView(Context context, int style, int text) {
		super(context);
		if (style != -1) {
			setTextAppearance(context, style);
		}
		if (text != -1) {
			setText(text);
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			setTextColor(getResources().getColor(R.color.Black));
		} else {
			setTextColor(getResources().getColor(R.color.DarkGray));
		}
	}
}
