/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * This class reads and writes JSON objects/arrays
 */

package com.ihsinformatics.childhoodtb_web.server.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class JsonUtil
{
	public static JSONObject getJSONObject (String jsonText)
	{
		// try parse the string to a JSON object
		try
		{
			JSONObject jsonObj = new JSONObject (jsonText);
			return jsonObj;
		}
		catch (JSONException e)
		{
			e.printStackTrace ();
			return null;
		}
	}

	public static JSONObject[] getJSONArrayFromObject (JSONObject jsonObj, String arrayElement)
	{
		try
		{
			JSONArray jsonArray = jsonObj.getJSONArray (arrayElement);
			JSONObject[] jsonObjects = new JSONObject[jsonArray.length ()];
			for (int i = 0; i < jsonArray.length (); i++)
			{
				jsonObjects[i] = JsonUtil.getJSONObject (jsonArray.getString (i));
			}
			return jsonObjects;
		}
		catch (JSONException e)
		{
			e.printStackTrace ();
			return null;
		}
	}

	public static JSONObject getJsonError (String errorMessage)
	{
		try
		{
			JSONObject jsonObj = new JSONObject ();
			jsonObj.put ("ERROR", errorMessage);
			return jsonObj;
		}
		catch (JSONException e)
		{
			e.printStackTrace ();
			return null;
		}
	}
	
	public static JSONObject getJsonMessage (String message)
	{
		try
		{
			JSONObject jsonObj = new JSONObject ();
			jsonObj.put ("MESSAGE", message);
			return jsonObj;
		}
		catch (JSONException e)
		{
			e.printStackTrace ();
			return null;
		}
	}
}
