/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * Custom Spinner view for ease
 */

package com.ihsinformatics.childhoodtb_mobile.custom;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.ihsinformatics.childhoodtb_mobile.R;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class MySpinner extends Spinner {
	private String[] itemList;

	public MySpinner(Context context) {
		super(context);
	}

	/**
	 * 
	 * @param context
	 * @param adapter
	 *            String list adapter
	 * @param tag
	 *            Text Id from resources. Pass -1 if no tag is to be set
	 * @param hint
	 *            Text Id from resources. Pass -1 if no hint is to be set
	 */
	public MySpinner(Context context, String[] itemList, int tag, int hint) {
		super(context);
		this.itemList = itemList;
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
				R.drawable.custom_spinner_item_enabled, this.itemList);
		setAdapter(arrayAdapter);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				getResources().getDimensionPixelSize(R.dimen.gigantic)));
		if (tag != -1) {
			setTag(getResources().getString(tag));
		}
		if (hint != -1) {
			setPromptId(hint);
		}
		int backgroundResource = R.drawable.custom_spinner_beige;
		if (backgroundResource != -1) {
			setBackgroundResource(backgroundResource);
		}
	}

	private int position = -1;

	@Override
	public void setEnabled(boolean enabled) {
		if (!enabled) {
			position = getSelectedItemPosition();
		}

		super.setEnabled(enabled);
		int drawable = enabled ? R.drawable.custom_spinner_item_enabled
				: R.drawable.custom_spinner_item_disabled;
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getContext(), drawable, this.itemList);
		setAdapter(arrayAdapter);
		if (position != -1) {
			setSelection(position);
		}
	}
}
