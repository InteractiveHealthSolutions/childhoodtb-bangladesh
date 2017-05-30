/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * Enum to represent type of Exception occurred in Application
 */

package com.ihsinformatics.childhoodtb_web.shared;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public enum ErrorType
{
	AUTHENTICATION_ERROR,LIMIT_EXCEEDED, DATA_ACCESS_ERROR, DATA_CONNECTION_ERROR, DATA_MISMATCH_ERROR, DELETE_ERROR, DUPLICATION_ERROR,TEST_ORDER_NOT_EXIST ,TEST_ID_NOT_BELONG_TO_PATIENT,TEST_RESULT_EXIST,EMPTY_DATA_ERROR, INSERT_ERROR, INVALID_DATA_ERROR, ITEM_NOT_FOUND, PARAMETER_MISSING, PARSING_ERROR, SESSION_EXPIRED, UNKNOWN_ERROR, UPDATE_ERROR, USER_NOT_FOUND, USER_ROLE_UNDEFINED, VERSION_MISMATCH_ERROR
}
