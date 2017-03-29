/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * Regular Expression provider class to verify a valid expression (e.g. valid email address, name, etc.)
Characters:
x	The character x
\\	The backslash character
\0n	The character with octal value 0n (0 <= n <= 7)
\0nn	The character with octal value 0nn (0 <= n <= 7)
\0mnn	The character with octal value 0mnn (0 <= m <= 3, 0 <= n <= 7)
\xhh	The character with hexadecimal value 0xhh
\t	The tab character ('\u0009')
\n	The newline (line feed) character ('
')
\r	The carriage-return character ('
')
\f	The form-feed character ('\u000C')
\a	The alert (bell) character ('\u0007')
\e	The escape character ('\u001B')
\cx	The control character corresponding to x
 
Character classes:
[abc]	a, b, or c (simple class)
[^abc]	Any character except a, b, or c (negation)
[a-zA-Z]	a through z or A through Z, inclusive (range)
[a-d[m-p]]	a through d, or m through p: [a-dm-p] (union)
[a-z&&[def]]	d, e, or f (intersection)
[a-z&&[^bc]]	a through z, except for b and c: [ad-z] (subtraction)
[a-z&&[^m-p]]	a through z, and not m through p: [a-lq-z](subtraction)
 
Predefined character classes:
.	Any character (may or may not match line terminators)
\d	A digit: [0-9]
\D	A non-digit: [^0-9]
\s	A whitespace character: [ \t\n\x0B\f\r]
\S	A non-whitespace character: [^\s]
\w	A word character: [a-zA-Z_0-9]
\W	A non-word character: [^\w]

Classes for Unicode blocks and categories
\p{InGreek}	A character in the Greek block (simple block)
\p{Lu}	An uppercase letter (simple category)
\p{Sc}	A currency symbol
\P{InGreek}	Any character except one in the Greek block (negation)
[\p{L}&&[^\p{Lu}]] 	Any letter except an uppercase letter (subtraction)
 
Boundary matchers
^	The beginning of a line
$	The end of a line
\b	A word boundary
\B	A non-word boundary
\A	The beginning of the input
\G	The end of the previous match
\Z	The end of the input but for the final terminator, if any
\z	The end of the input
 
Greedy quantifiers
X?	X, once or not at all
X*	X, zero or more times
X+	X, one or more times
X{n}	X, exactly n times
X{n,}	X, at least n times
X{n,m}	X, at least n but not more than m times
 
Reluctant quantifiers
X??	X, once or not at all
X*?	X, zero or more times
X+?	X, one or more times
X{n}?	X, exactly n times
X{n,}?	X, at least n times
X{n,m}?	X, at least n but not more than m times
 
Possessive quantifiers
X?+	X, once or not at all
X*+	X, zero or more times
X++	X, one or more times
X{n}+	X, exactly n times
X{n,}+	X, at least n times
X{n,m}+	X, at least n but not more than m times
 
Logical operators
XY	X followed by Y
X|Y	Either X or Y
(X)	X, as a capturing group
 */

package com.ihsinformatics.childhoodtb_web.shared;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class RegexUtil
{
	private static final String	numericPattern			= "^[0-9]+";
	private static final String	floatingPointPattern	= "^[0-9]+.[0-9]+";
	private static final String	alphaPattern			= "^[A-Za-z_ ]+";
	private static final String	alphaNumPattern			= "^[A-Za-z0-9]+";
	private static final String	emailPattern			= "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String	contactNoPattern		= "[+0-9]+[0-9]+";
	private static final String	datePattern				= "(0[1-9]|[1-9]|[12][0-9]|3[01])[-/](0[1-9]|1[012]|[1-9])[-/](19|20)\\d{2}";
	private static final String	timePattern_am_pm		= "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
	private static final String	timePattern_24			= "([01]?[0-9]|2[0-3]):[0-5][0-9]";
	private static final String	nicPattern				= "^[]";
	private static final String	urlPattern				= "^(((ht|f)tp(s?))\\://)?(www.|[a-zA-Z].)[a-zA-Z0-9\\-\\.]+\\.(com|edu|gov|mil|net|org|biz|info|name|museum|us|ca|uk|pk|co|)(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\;\\?\\'\\\\\\+&amp;%\\$#\\=~_\\-]+))*$";

	/**
	 * Checks whether given input is a valid number
	 * 
	 * @param string
	 *            input String
	 * @return true/false
	 */
	public static boolean isNumeric (String string, boolean floating)
	{
		try
		{
			if (floating)
				return string.matches (floatingPointPattern);
			return string.matches (numericPattern);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return false;
		}
	}

	/**
	 * Checks whether given input is a valid word
	 * 
	 * @param string
	 *            input String
	 * @return true/false
	 */
	public static boolean isWord (String string)
	{
		try
		{
			return string.matches (alphaPattern);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return false;
		}
	}

	/**
	 * Checks whether given input is a valid alphanumeric string
	 * 
	 * @param string
	 *            input String
	 * @return true/false
	 */
	public static boolean isAlphaNumeric (String string)
	{
		try
		{
			return string.matches (alphaNumPattern);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return false;
		}
	}

	/**
	 * Checks whether given input is a valid contact number (e.g. Mobile no,
	 * fax)
	 * 
	 * @param string
	 *            input String
	 * @return true/false
	 */
	public static boolean isContactNumber (String string)
	{
		try
		{
			return string.matches (contactNoPattern);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return false;
		}
	}

	/**
	 * Checks whether given input is a valid email address
	 * 
	 * @param string
	 *            input String
	 * @return true/false
	 */
	public static boolean isEmailAddress (String string)
	{
		try
		{
			return string.matches (emailPattern);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return false;
		}
	}

	/**
	 * Checks whether given input is a valid date
	 * 
	 * @param string
	 *            input String
	 * @return true/false
	 */
	public static boolean isValidDate (String string)
	{
		try
		{
			return string.matches (datePattern);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return false;
		}
	}

	/**
	 * Checks whether given input is a valid time
	 * 
	 * @param string
	 *            input String
	 * @return true/false
	 */
	public static boolean isValidTime (String string, boolean am_pm)
	{
		try
		{
			if (am_pm)
				return string.matches (timePattern_am_pm);
			return string.matches (timePattern_24);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return false;
		}
	}

	/**
	 * Checks whether given input is a valid national ID number
	 * 
	 * @param string
	 *            input String
	 * @return true/false
	 */
	public static boolean isValidNIC (String string)
	{
		try
		{
			return string.matches (nicPattern);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return false;
		}
	}

	/**
	 * Checks whether given input is a valid URL
	 * 
	 * @param string
	 *            input String
	 * @return true/false
	 */
	public static boolean isValidURL (String string)
	{
		try
		{
			return string.matches (urlPattern);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return false;
		}
	}
}
