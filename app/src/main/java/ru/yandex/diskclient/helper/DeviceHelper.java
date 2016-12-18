package ru.yandex.diskclient.helper;

import android.os.Build;
import android.text.TextUtils;

/**
 * Предоставляет информацию о девайсе
 *
 * @author Lord (Kuleshov M.V.)
 * @since 10.10.16
 */
public class DeviceHelper {
	/**
	 * Returns the consumer friendly device name
	 */
	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		}
		return capitalize(manufacturer) + " " + model;
	}

	private static String capitalize(String str) {
		if (TextUtils.isEmpty(str)) {
			return str;
		}
		char[] arr = str.toCharArray();
		boolean capitalizeNext = true;

//        String phrase = "";
		StringBuilder phrase = new StringBuilder();
		for (char c : arr) {
			if (capitalizeNext && Character.isLetter(c)) {
//                phrase += Character.toUpperCase(c);
				phrase.append(Character.toUpperCase(c));
				capitalizeNext = false;
				continue;
			} else if (Character.isWhitespace(c)) {
				capitalizeNext = true;
			}
//            phrase += c;
			phrase.append(c);
		}

		return phrase.toString();
	}
}
