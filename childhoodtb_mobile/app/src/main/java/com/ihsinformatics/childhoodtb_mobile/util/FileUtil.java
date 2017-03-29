/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * This class is used to read and write files to and from Android OS
 */

package com.ihsinformatics.childhoodtb_mobile.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class FileUtil {
	/**
	 * 
	 */
	public FileUtil() {
	}

	/**
	 * Create a folder in external storage (SD card); returns false if one
	 * already exists
	 * 
	 * @param directoryName
	 * @return
	 */
	public static boolean createDirectoryInStorage(String directoryName) {
		File directory = new File(directoryName);
		if (!directory.exists()) {
			return directory.mkdir();
		}
		return false;
	}

	/**
	 * Return list of files in given directory
	 * 
	 * @param directoryPath
	 * @return
	 */
	public static String[] getFiles(String directoryPath) {
		File dir = new File(directoryPath);
		if (dir.exists()) {
			return dir.list();
		}
		return null;
	}

	/**
	 * Return contents of a file as text
	 * 
	 * @return
	 */
	public String getText(String filePath) {
		File file = new File(filePath);
		StringBuilder text = new StringBuilder();
		try {
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				text.append(strLine);
			}
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text.toString();
	}

	/**
	 * Reads binary data from a file
	 * 
	 * @param filePath
	 * @return
	 */
	public byte[] readByteStream(String filePath) {
		File file = new File(filePath);
		byte[] buffer = new byte[(int) file.length()];
		try {
			FileInputStream fis = new FileInputStream(file);
			fis.read(buffer);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * Appends binary data to a file
	 * 
	 * @param filePath
	 * @param bytes
	 */
	public void writeByteStream(String filePath, byte[] bytes) {
		File file = new File(filePath);
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file, true));
			bos.write(bytes);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return lines of text in a file
	 * 
	 * @return
	 */
	public String[] getLines(String filePath) {
		File file = new File(filePath);
		ArrayList<String> lines = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				lines.add(strLine);
			}
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines.toArray(new String[] {});
	}

	/**
	 * Append text to existing file. If a file doesn't exists, it will be
	 * created
	 * 
	 * @param text
	 */
	public void appendText(String filePath, String text) {
		appendText(filePath, text, false);
	}

	/**
	 * Append text to existing file. If a file doesn't exists, it will be
	 * created
	 * 
	 * @param text
	 */
	public void appendText(String filePath, String text, boolean encrypt) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
			buf.append(text);
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
