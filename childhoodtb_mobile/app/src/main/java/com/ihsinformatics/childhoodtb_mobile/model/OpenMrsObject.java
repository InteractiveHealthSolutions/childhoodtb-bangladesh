/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * Abstract class to be inherited by other model persistance classes
 */

package com.ihsinformatics.childhoodtb_mobile.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class OpenMrsObject {
	private String id;
	private String type;
	private String name;

	public OpenMrsObject(String id) {
		this.id = id;
	}

	public OpenMrsObject(String id, String type, String name) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
	}

	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", id);
			jsonObject.put("type", type);
			jsonObject.put("name", name);
		} catch (JSONException e) {
			e.printStackTrace();
			jsonObject = null;
		}
		return jsonObject;
	}

	public static OpenMrsObject parseJSONObject(JSONObject json) {
		OpenMrsObject openMrsObject = null;
		String id = "";
		String type = "";
		String name = "";
		try {
			id = json.getString("id");
			type = json.getString("type");
			name = json.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
			openMrsObject = null;
		}
		openMrsObject = new OpenMrsObject(id, type, name);
		return openMrsObject;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return id + ", " + type + ", " + name;
	}

}
