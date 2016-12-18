package ru.yandex.diskclient.helper;

/**
 * Помощник для работы с номерами телефонов
 *
 * @author Lord (Kuleshov M.V.)
 * @since 26.09.16
 */
public class PhoneHelper {
	/**
	 * Преобразует номер телефона из формата:
	 * +7 (123) 456-78-90
	 * в формат
	 * 1234567890
	 * @param phone
	 * @return
	 */
	public static String stripPhoneNumber(String phone) {
		String p = phone;
		if (p.startsWith("+7")) {
			p = p.substring(2);
		}

		p = p.replace("(", "");
		p = p.replace(")", "");
		p = p.replace(" ", "");
		p = p.replace("-", "");

		return p;
	}


	/**
	 * Форматирует номер телефона из формата 9185448339 в формат +7 (918) 544-83-39
	 * @param p номер телефона в формате 9001234567
	 * @return номер телефона в формате +7 (900) 123-45-66
	 */
	public static String formatPhone(String p) {
		String phone = "";

		if (p.length() == 10) {
			phone = "+7 (" + p.substring(0, 3) + ") " + p.substring(3, 6) + "-" +
					p.substring(6, 8) + "-" + p.substring(8, 10);
		}

		return phone;
	}

	/**
	 * Проверяет номер телефона на валидность
	 * @param p номер телефона в формате +7 (901) 123-45-67
	 * @return
	 */
	public static boolean isValidPhone(String p) {
		String phone = stripPhoneNumber(p);

		return phone.startsWith("9") && phone.length() == 10;
	}
}
