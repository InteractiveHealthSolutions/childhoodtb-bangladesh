/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * Custom Button view for ease
 */

package com.ihsinformatics.childhoodtb_mobile.custom;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import com.ihsinformatics.childhoodtb_mobile.R;


/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class MyButton extends Button {
	public MyButton(Context context) {
		super(context);
	}

	/**
	 * 
	 * @param context
	 * @param textStyle
	 *            Style Id from resources. Pass -1 to keep default style
	 * @param backgroundResource
	 *            Drawable to be used as background. Pass -1 to keep default
	 * @param tag
	 *            Tag to be used. Pass -1 if not to be set
	 * @param text
	 *            Text Id from resources. Pass -1 if not to be set
	 */
	public MyButton(Context context, int textStyle, int backgroundResource,
			int tag, int text) {
		super(context);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		setText(text);
		if (tag != -1) {
			setTag(getResources().getString(tag));
		}
		if (backgroundResource != -1) {
			setBackgroundResource(backgroundResource);
		}
		if (textStyle != -1) {
			setTextAppearance(context, textStyle);
		}
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
