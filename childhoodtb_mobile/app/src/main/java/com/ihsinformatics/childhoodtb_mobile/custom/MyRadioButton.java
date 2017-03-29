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

package com.ihsinformatics.childhoodtb_mobile.custom;

import android.content.Context;
import android.widget.RadioButton;
import com.ihsinformatics.childhoodtb_mobile.R;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class MyRadioButton extends RadioButton {
	public MyRadioButton(Context context) {
		super(context);
	}

	/**
	 * 
	 * @param context
	 * @param tag
	 *            Text Id from resources. Pass -1 if no tag is to be set
	 * @param style
	 *            Style Id from resources. Pass -1 to keep default style
	 * @param text
	 *            Text Id from resources. Pass -1 if not to be set
	 */
	public MyRadioButton(Context context, int tag, int style, int text) {
		super(context);
		if (tag != -1) {
			setText(getResources().getString(tag));
		}
		setTextAppearance(context, style);
		setText(text);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			setTextColor(getResources().getColor(R.color.Chocolate));
		} else {
			setTextColor(getResources().getColor(R.color.DarkGray));
		}
	}

}
